/**
 * Source Code, High Level Architecture Documentation and Common Criteria
 * Documentation Copyright (C) 2013 and ownership belongs to The Norwegian
 * Ministry of Local Government and Regional Development and Scytl Secure
 * Electronic Voting SA ("Licensor").
 *
 * The Norwegian Ministry of Local Government and Regional Development has the
 * right to use, modify (whether by itself or by the use of contractors) and
 * copy the software for the sole purposes of performing Norwegian Public Sector
 * Elections, including to install and run the code on the necessary number of
 * locations centrally and in any number of counties and municipalities, and to
 * allow access to the solution from anywhere in the world by persons who have
 * the right to participate in Norwegian national or local elections. This also
 * applies to elections to the Longyearbyen Community Council at Svalbard and
 * any possible future public elections in Norway arranged by the Election
 * Authorities.
 *
 * Patents, relevant to the software, are licensed by Scytl Secure Electronic
 * Voting SA to the Norwegian Ministry of Local Government and Regional
 * Development for the purposes set out above.
 *
 * Scytl Secure Electronic Voting SA (or whom it appoints) has the right, inside
 * and outside of Norway to use, copy, modify and enhance the materials, as well
 * as a right of licensing and transfer, internally and externally, either by
 * itself or with the assistance of a third party, as part of the further
 * development and customization of its own standard solutions or delivered
 * together with its own standard solutions.
 *
 * The Norwegian Ministry of Local Government and Regional Development and Scytl
 * Secure Electronic Voting SA hereby grant to you (any third party) the right
 * to copy, modify, inspect, compile, debug and run the software for the sole
 * purpose of testing, reviewing or evaluating the code or the system solely for
 * non-commercial purposes. Any other use of the source code (or parts of it)
 * for any other purpose (including but not limited to any commercial purposes)
 * by any third party is subject to Scytl Secure Electronic Voting SA's prior
 * written approval.
 */
package com.scytl.evote.protocol.integration.eraser;

import java.util.HashMap;
import java.util.Map;


public final class Eraser {
    // private final List<Object> _eraseOnExit;

    // private final Map<String, ObjectEraser> _erasers;
    private static final String SONAR = "sonar";
    private static final String DUMMY_STR = "dummy";
    private static final Map<String, Eraser> INSTANCES = new HashMap<String, Eraser>();
    private final String _key;

    private Eraser(final String key) {
        _key = key;

        // _eraseOnExit =
        // Collections.synchronizedList(new ArrayList<Object>());
        // _erasers = new HashMap<String, ObjectEraser>();

        // register default erasers
        // _erasers.put(String.class.getName(), new StringEraser());
        // _erasers.put(BigInteger.class.getName(), new BigIntegerEraser());
        // _erasers.put(StringBuffer.class.getName(),
        // new StringBufferEraser());
        // _erasers.put("com.scytl.jbasis.crypto.bc.BCPrivateKey",
        // new PrivateKeyEraser());
        // _erasers.put("com.scytl.jbasis.crypto.iaik.ae.IAIKPrivateKey",
        // new PrivateKeyEraser());
        // _erasers.put(PrivateKey.class.getName(), new PrivateKeyEraser());
        // _erasers.put(BigNumber.class.getName(), new BigNumberEraser());
    }

    public static Eraser get(final Class<?> clazz) {
        return get(clazz.getName());
    }

    public static Eraser get(final String someKey) {
        synchronized (SONAR) {
            String key = DUMMY_STR;
            Eraser eraser = INSTANCES.get(key);

            if (eraser == null) {
                eraser = new Eraser(key);
                INSTANCES.put(key, eraser);
            }

            return eraser;
        }
    }

    /**
     * Convenience factory method that allows creating a new "unnamed" eraser
     * object (ie, not tracked in INSTANCES) for local scopes (like handlers,
     * for instance).
     *
     * <pre>Usage:
     * Eraser transientEraser = Eraser.getNewTransientEraser();
     * transientEraser.eraseOnExit(stuff) ;
     * </pre>
     *
     * @return
     */
    public static Eraser getNewTransientEraser() {
        return get(DUMMY_STR); // new Eraser(null);
    }

    /**
     * Set an object to be erased on GC
     *
     * @param object
     */
    public byte[] eraseOnExit(final byte[] bytes) {
        // eraseOnExit(new ByteArrayWrapper(bytes));
        return bytes;
    }

    /**
     * Set an object to be erased on GC
     *
     * @param object
     */
    public Object eraseOnExit(final Object object) {
        // if (!object.getClass().isArray()) {
        // _eraseOnExit.add(object);
        // } else {
        // Object[] arrObjects = (Object[]) object;
        // for (int i = 0; i < arrObjects.length; i++) {
        // _eraseOnExit.add(arrObjects[i]);
        // }
        // }
        return object;
    }

    public void erase(final byte[] bytes) {
        // erase(new ByteArrayWrapper(bytes));
    }

    /**
     * Set an object to be erased on GC
     *
     * @param object
     */
    public byte[][] eraseOnExit(final byte[][] bytes) {
        // eraseOnExit(new ByteArrayWrapper2(bytes));
        return bytes;
    }

    /**
     * Erases an object by getting a registered eraser for it's class.
     *
     * @param object
     */
    public void erase(final Object object) {
        // if (object == null) {
        // return;
        // }

        // System.out.println("* erasing :" + object.getClass().getName()
        // + " -> " + object);

        // if (object instanceof Wrapper) {
        // Wrapper w = (Wrapper) object;
        // w.erase();
        // } else if (object instanceof List) {
        // List<?> l = (List<?>) object;
        // for (int i = 0; i < l.size(); i++) {
        // erase(l.get(i));
        // }
        // } else if (object instanceof Erasable) {
        // Erasable e = (Erasable) object;
        // e.erase();
        // } else {
        // String name = object.getClass().getName();
        // ObjectEraser eraser = _erasers.get(name);
        // if (eraser != null) {
        // eraser.erase(object);
        // } else {
        // throw new RuntimeException(
        // "There is no registered eraser for " + name);
        // }
        // }
    }

    /**
     * Registers a new eraser for the given class name.
     *
     * @param name
     * @param eraser
     */
    public void registerEraser(final String name, final ObjectEraser eraser) {
        // _erasers.put(name, eraser);
    }

    /**
     * Erases all pending objects.
     */
    public void erase() {
        // try {
        // for (int i = 0; i < _eraseOnExit.size(); i++) {
        // Object o = _eraseOnExit.get(i);
        // erase(o);
        // }
        // _eraseOnExit.clear();
        // } catch (Throwable t) {
        //
        // }
    }

    /**
     * @return Returns the key.
     */
    public String getKey() {
        return _key;
    }

    // class ByteArrayWrapper implements Wrapper {
    // private final byte[] _bytes;

    // public ByteArrayWrapper(final byte[] bytes) {
    // _bytes = bytes;
    // }

    // @Override
    // public void erase() {
    // System.out.println("erasing byte[] -> " + _bytes);
    // for (int i = 0; i < _bytes.length; i++) {
    // _bytes[i] = 0;
    // }
    // }
    // }

    // class ByteArrayWrapper2 implements Wrapper {
    // private final byte[][] _bytes;

    // public ByteArrayWrapper2(final byte[][] bytes) {
    // _bytes = bytes;
    // }

    // @Override
    // public void erase() {
    // System.out.println("erasing byte[]][] -> " + _bytes);
    // for (int i = 0; i < _bytes.length; i++) {
    // for (int j = 0; j < _bytes[i].length; j++) {
    // _bytes[i][j] = 0;
    // }
    // }
    // }
    // }

    /**
     * @return Returns the instances.
     */
    public static Map<String, Eraser> getInstances() {
        return INSTANCES;
    }

    interface Wrapper {
        void erase();
    }
}
