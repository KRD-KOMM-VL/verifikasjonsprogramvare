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
package com.computas.zkpev2013;

import java.math.BigInteger;

import java.util.ArrayList;
import java.util.List;


/**
 * Class representing an ElGamal encryption tuple.
 *
 */
public class ElGamalEncryptionTuple {
    private final BigInteger publicKeyComponent;
    private final List<BigInteger> messageComponents;

    /**
     * Constructor taking a byte array as input. It assumes that the byte array
     * represents a String containing a number of BigIntegers separated by a hash ("#").
     *
     * @param encoded ElGamal encryption pair encoded as a byte array.
     */
    public ElGamalEncryptionTuple(byte[] encoded) {
        String[] elements = new String(encoded).split("#");
        publicKeyComponent = new BigInteger(elements[0]);

        messageComponents = new ArrayList<BigInteger>();

        for (int i = 1; i < elements.length; i++) {
            messageComponents.add(new BigInteger(elements[i]));
        }
    }

    /**
     * Constructor taking a BigInteger and a list of BigIntegers as input.
     *
     * @param publicKeyComponent The public key component of the ElGamal encryption tuple.
     * @param messageComponents The list of message components of the ElGamal encryption tuple.
     */
    public ElGamalEncryptionTuple(BigInteger publicKeyComponent,
        List<BigInteger> messageComponents) {
        this.publicKeyComponent = publicKeyComponent;
        this.messageComponents = messageComponents;
    }

    /**
    * Returns the public key component of the ElGamal encryption tuple.
    * @return The public key component.
    */
    public BigInteger getPublicKeyComponent() {
        return publicKeyComponent;
    }

    /**
     * Returns the message component of the ElGamal encryption tuple.
     * @return The message component.
     */
    public List<BigInteger> getMessageComponents() {
        return messageComponents;
    }

    @Override
    public boolean equals(Object other) {
        return sameSubclass(other) &&
        privateEqual((ElGamalEncryptionTuple) other);
    }

    private boolean sameSubclass(Object other) {
        return (other != null) && other.getClass().equals(this.getClass());
    }

    private boolean privateEqual(ElGamalEncryptionTuple other) {
        return publicKeyComponent.equals(other.getPublicKeyComponent()) &&
        messageComponents.equals(other.getMessageComponents());
    }

    @Override
    public int hashCode() {
        return publicKeyComponent.hashCode() + messageComponents.hashCode();
    }

    /**
     * Multiplies together the list of ElGamal messageComponents modulo a modulus
     * to form an ElGamal encryption pair.
     *
     * @param modulus The modulus to be used under the multiplication.
     * @return An ElGamal encryption pair representation of the tuple.
     */
    public ElGamalEncryptionPair convertToPair(BigInteger modulus) {
        BigInteger messageComponentReturnValue = BigInteger.ONE;

        for (BigInteger messageComponent : this.getMessageComponents()) {
            messageComponentReturnValue = messageComponentReturnValue.multiply(messageComponent)
                                                                     .mod(modulus);
        }

        return new ElGamalEncryptionPair(publicKeyComponent,
            messageComponentReturnValue);
    }
}
