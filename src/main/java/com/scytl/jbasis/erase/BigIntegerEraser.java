/**
 * $Id: BigIntegerEraser.java 1438 2010-02-04 11:17:42Z gmercadal $
 * @author gmercadal
 * @date   04/02/2010 11:11:08
 *
 * Copyright (C) 2010 Scytl Secure Electronic Voting SA
 *
 * All rights reserved.
 *
 */
package com.scytl.jbasis.erase;

import java.lang.reflect.Field;

import java.math.BigInteger;


/**
 *
 */
public class BigIntegerEraser implements ObjectEraser {
    /**
     * @see com.scytl.jbasis.erase.ObjectEraser#erase(java.lang.Object)
     */
    public synchronized void erase(final Object o) {
        try {
            Field magField = BigInteger.class.getDeclaredField("mag");
            magField.setAccessible(true);

            int[] mag = (int[]) magField.get(o);

            for (int i = 0; i < mag.length; i++) {
                mag[i] = 0;
            }

            magField.setAccessible(false);
        } catch (SecurityException e) {
            throw new RuntimeException(e);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
