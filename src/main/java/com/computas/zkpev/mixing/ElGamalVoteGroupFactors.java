/**
 * Zero-Knowledge Protocols for E-Vote (ZKPEV).
 *
 * Copyright © 2013, The Norwegian Ministry of Local Government and Regional
 * Development (KRD).
 *
 * This file is part of ZKPEV.
 *
 * ZKPEV is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * ZKPEV is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 *
 * You can find a copy of the GNU General Public License in
 * /src/site/resources/gpl-3.0-standalone.html. Otherwise, see also
 * http://www.gnu.org/licenses/.
 */
package com.computas.zkpev.mixing;

import com.computas.zkpev.ElGamalEncryptionPair;

import java.math.BigInteger;


/**
 * Class representing a set of factors for an ElGamal vote group.
 *
 */
public class ElGamalVoteGroupFactors {
    private final BigInteger p;
    private BigInteger publicKeyFactor;
    private BigInteger messageFactor;

    ElGamalVoteGroupFactors(BigInteger p, BigInteger publicKeyFactor,
        BigInteger messageFactor) {
        this.p = p;
        this.publicKeyFactor = publicKeyFactor;
        this.messageFactor = messageFactor;
    }

    @Override
    public boolean equals(Object other) {
        return sameSubclass(other) &&
        privateEqual((ElGamalVoteGroupFactors) other);
    }

    private boolean sameSubclass(Object other) {
        return (other != null) && other.getClass().equals(this.getClass());
    }

    private boolean privateEqual(ElGamalVoteGroupFactors other) {
        return p.equals(other.getP()) &&
        publicKeyFactor.equals(other.getPublicKeyFactor()) &&
        messageFactor.equals(other.getMessageFactor());
    }

    private BigInteger getP() {
        return p;
    }

    BigInteger getPublicKeyFactor() {
        return publicKeyFactor;
    }

    BigInteger getMessageFactor() {
        return messageFactor;
    }

    @Override
    public int hashCode() {
        return p.hashCode() + publicKeyFactor.hashCode() +
        messageFactor.hashCode();
    }

    void add(ElGamalEncryptionPair elGamalEncryptionPair) {
        publicKeyFactor = publicKeyFactor.multiply(elGamalEncryptionPair.getPublicKeyComponent())
                                         .mod(p);
        messageFactor = messageFactor.multiply(elGamalEncryptionPair.getMessageComponent())
                                     .mod(p);
    }
}
