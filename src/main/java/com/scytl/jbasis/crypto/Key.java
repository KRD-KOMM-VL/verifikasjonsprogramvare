//
// $Id: Key.java 942 2006-05-29 14:44:52Z daniel $
//
// (c) Copyright 2002 Scytl Online World Security
//
//
// Created: Wed Jul  24 17:33:00 2002
// 
package com.scytl.jbasis.crypto;

import com.scytl.jbasis.util.UnsupportedException;

import java.math.BigInteger;


/**
 * Marker interface for RSA keys.
 *
 * @author <a href="mailto:merce.lazo@scytl.com">Merce Lazo Rodriguez</a>
 */
public interface Key extends CryptoObject {
    /**
     * Gets the import java.security.Key interface, if available. TODO Should be
     * deprecated Since now PrivateKey and PublicKey expose their modulus and
     * exponents, the JCE Key could be created from this info.
     *
     * @return Object (to keep jbasis.crypto independent of JCE)
     * @throws UnsupportedException
     *             if not available.
     */
    Object getJCEKey();

    /**
     * Returns the modulus.
     *
     * @return the modulus
     * @throws UnsupportedException
     *             if not available.
     */
    BigInteger getModulus();
} // Key
