/**
 * $Id: PrivateKeyEraser.java 1438 2010-02-04 11:17:42Z gmercadal $
 * @author gmercadal
 * @date   04/02/2010 11:37:08
 *
 * Copyright (C) 2010 Scytl Secure Electronic Voting SA
 *
 * All rights reserved.
 *
 */
package com.scytl.jbasis.erase;

import com.scytl.jbasis.crypto.PrivateKey;

import java.math.BigInteger;


/**
 *
 */
public class PrivateKeyEraser implements ObjectEraser {
    /**
     * @see com.scytl.jbasis.erase.ObjectEraser#erase(java.lang.Object)
     */
    public void erase(final Object o) {
        PrivateKey key = (PrivateKey) o;
        BigInteger exponent = key.getPrivateExponent();
        BigIntegerEraser eraser = new BigIntegerEraser();
        eraser.erase(exponent);
    }
}
