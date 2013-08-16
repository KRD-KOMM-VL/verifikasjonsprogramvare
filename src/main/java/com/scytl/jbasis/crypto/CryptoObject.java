/**
 * $Id: CryptoObject.java 942 2006-05-29 14:44:52Z daniel $
 * @author <a href="mailto:daniel@scytl.com">Daniel Pinol</a>
 * @date    20-jul-2005 9:43:16
 *
 * Copyright (C) 2005 Scytl Secure Electronic Voting SA
 *
 * All rights reserved.
 *
 */
package com.scytl.jbasis.crypto;

import java.io.IOException;
import java.io.OutputStream;


/**
 * Base class for all classes which wrap a cryptographic file, such us pkcs12,
 * cert, keys or pkcs7. Objects implementing this interface should always be in
 * a correct state, so that getEncoded and write never need to raise any
 * exception. Methods to add in future: equals, hashcode, getCryptoFactory
 */
public interface CryptoObject {
    /**
     * @return the DER encoded form Throws RuntimeException if the object was
     *         incorrect, but this shouldn't happen.
     */
    byte[] getEncoded();

    /**
     * Write PEM to out.
     *
     * @param out
     * @throws IOException
     *             if cannot write to stream, or there was an internal problem
     *             generating the PEM (which shouldn't happen)
     */
    void write(final OutputStream out) throws IOException;

    /**
     * Recommended to implement it so that object from different factories may
     * be compared, for instance calling
     * {@link CryptoUtils#equals(PrivateKey, PrivateKey)} or
     * {@link CryptoUtils#equals(PublicKey, PublicKey)}.
     *
     * @param o
     * @return whether they're equal
     */
    boolean equals(final Object o);

    /**
     * Equals requires implementing hashCode.
     *
     * @return the hash code
     */
    int hashCode();

    /**
     * @return a short string identifying the CryptoObject. Should never fail
     *         (unless co is corrupt)
     */
    public String getIdentification();
}
