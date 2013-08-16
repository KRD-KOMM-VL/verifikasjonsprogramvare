/**
 * $Id: StringEraser.java 1438 2010-02-04 11:17:42Z gmercadal $
 * @author gmercadal
 * @date   04/02/2010 10:55:58
 *
 * Copyright (C) 2010 Scytl Secure Electronic Voting SA
 *
 * All rights reserved.
 *
 */
package com.scytl.jbasis.erase;

import java.lang.reflect.Field;


/**
 *
 */
public class StringEraser implements ObjectEraser {
    public synchronized void erase(final Object o) {
        try {
            Field valueField = String.class.getDeclaredField("value");
            valueField.setAccessible(true);

            char[] value = (char[]) valueField.get(o);

            for (int i = 0; i < value.length; i++) {
                value[i] = '*';
            }

            valueField.setAccessible(false);
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
