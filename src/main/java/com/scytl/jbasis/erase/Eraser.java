/**
 * $Id: Eraser.java 1488 2011-02-07 14:53:09Z gmercadal $
 * @author gmercadal
 * @date   04/02/2010 10:42:49
 *
 * Copyright (C) 2010 Scytl Secure Electronic Voting SA
 *
 * All rights reserved.
 *
 */
package com.scytl.jbasis.erase;

import com.scytl.jbasis.math.BigNumber;

import java.math.BigInteger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *
 */
public class Eraser {
    private static final Map INSTANCES = new HashMap();
    private final List _objects;
    private final Map _erasers;
    private final String _key;

    private Eraser(final String key) {
        _key = key;
        _objects = Collections.synchronizedList(new ArrayList());
        _erasers = new HashMap();

        // register default erasers
        _erasers.put(String.class.getName(), new StringEraser());
        _erasers.put(BigInteger.class.getName(), new BigIntegerEraser());
        _erasers.put("com.scytl.jbasis.crypto.bc.BCPrivateKey",
            new PrivateKeyEraser());
        _erasers.put("com.scytl.jbasis.crypto.iaik.ae.IAIKPrivateKey",
            new PrivateKeyEraser());
        _erasers.put(BigNumber.class.getName(), new BigNumberEraser());
    }

    public static synchronized Eraser get(final Class clazz) {
        return get(clazz.getName());
    }

    public static synchronized Eraser get(final String key) {
        Eraser eraser = (Eraser) INSTANCES.get(key);

        if (eraser == null) {
            eraser = new Eraser(key);
            INSTANCES.put(key, eraser);
        }

        return eraser;
    }

    /**
     * Sets the garbage collector to erase this object.
     *
     * @param object
     */
    public byte[] eraseOnExit(final byte[] bytes) {
        _objects.add(new ByteArrayWrapper(bytes));

        return bytes;
    }

    /**
     * Sets the garbage collector to erase this object.
     *
     * @param object
     */
    public Object eraseOnExit(final Object object) {
        if (!object.getClass().isArray()) {
            _objects.add(object);
        } else {
            Object[] arrObjects = (Object[]) object;

            for (int i = 0; i < arrObjects.length; i++) {
                _objects.add(arrObjects[i]);
            }
        }

        return object;
    }

    public void erase(final byte[] bytes) {
        erase(new ByteArrayWrapper(bytes));
    }

    /**
     * Erases an object by getting a registered eraser for it's class.
     *
     * @param object
     */
    public void erase(final Object object) {
        if (object instanceof Wrapper) {
            Wrapper w = (Wrapper) object;
            w.erase();
        } else if (object instanceof List) {
            List l = (List) object;

            for (int i = 0; i < l.size(); i++) {
                erase(l.get(i));
            }
        } else if (object instanceof Erasable) {
            Erasable e = (Erasable) object;
            e.erase();
        } else {
            String name = object.getClass().getName();
            ObjectEraser eraser = (ObjectEraser) _erasers.get(name);

            if (eraser != null) {
                eraser.erase(object);
            } else {
                throw new RuntimeException("There is no registered eraser for " +
                    name);
            }
        }
    }

    /**
     * Registers a new eraser for the given class name.
     *
     * @param name
     * @param eraser
     */
    public void registerEraser(final String name, final ObjectEraser eraser) {
        _erasers.put(name, eraser);
    }

    /**
     * Erases all pending objects.
     */
    public void erase() {
        for (int i = 0; i < _objects.size(); i++) {
            try {
                Object o = _objects.get(i);
                erase(o);
            } catch (Throwable t) {
            }
        }

        _objects.clear();
    }

    /**
     * @see java.lang.Object#finalize()
     */
    protected void finalize() throws Throwable {
        erase();
    }

    interface Wrapper {
        void erase();
    }

    class ByteArrayWrapper implements Wrapper {
        private final byte[] _bytes;

        public ByteArrayWrapper(final byte[] bytes) {
            _bytes = bytes;
        }

        public void erase() {
            for (int i = 0; i < _bytes.length; i++) {
                _bytes[i] = 0;
            }
        }
    }
}
