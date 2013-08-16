//
// $Id: PrivateKey.java 873 2006-05-09 09:27:09Z daniel $
//
// (c) Copyright 2002 Scytl Online World Security
//
//
// Created: Wed Jul  24 18:18:00 2002
// 
package com.scytl.jbasis.crypto;

import com.scytl.jbasis.util.UnsupportedException;

import java.math.BigInteger;


/**
 * Interface for generic private keys.
 *
 * @see "http://www.rsasecurity.com/rsalabs/node.asp?id=2308"
 * @author <a href="mailto:merce.lazo@scytl.com">Merce Lazo Rodriguez</a>
 */
public interface PrivateKey extends Key {
    /**
     * The header of a PEM private key as pkcs1.
     */
    String PEM_PKCS1_BEGIN = "-----BEGIN RSA PRIVATE KEY-----";

    /**
     * The footer of a PEM private key as pkcs1.
     */
    String PEM_PKCS1_END = "-----END RSA PRIVATE KEY-----";

    /**
     * The header of a PEM private key as pkcs8 (more general than pkcs1) .
     */
    String PEM_PKCS8_BEGIN = "-----BEGIN PRIVATE KEY-----";

    /**
     * The footer of a PEM private key as pkcs8 (more general than pkcs1).
     */
    String PEM_PKCS8_END = "-----END PRIVATE KEY-----";

    /**
     * The header of a PEM private key.
     *
     * @see #PEM_PKCS8_BEGIN
     */
    String PEM_BEGIN = PEM_PKCS8_BEGIN;

    /**
     * The footer of a PEM private key.
     *
     * @see #PEM_PKCS8_END
     */
    String PEM_END = PEM_PKCS8_END;

    /**
     * @return the RSA private exponent
     * @throws UnsupportedException
     *             if not available.
     */
    BigInteger getPrivateExponent() throws UnsupportedException;
} // PrivateKey
