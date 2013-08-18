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
* FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
* details.
*
* You can find a copy of the GNU General Public License in
* /src/site/resources/gpl-3.0-standalone.html. Otherwise, see also
* http://www.gnu.org/licenses/.
*/
package com.computas.zkpev2013;

import java.math.BigInteger;


/**
* Class representing an ElGamal encryption pair.
*
*/
public class ElGamalEncryptionPair {
    private final BigInteger publicKeyComponent;
    private final BigInteger messageComponent;

    /**
    * Constructor taking a byte array as input. It assumes that the byte array
    * represents a String containing two BigIntegers separated by a pipe ("|").
    *
    * @param encodedPublicKeyComponent BigInteger publicKeyComponent encoded as a byte array.
    * @param encodedMessageComponent   BigInteger messageComponent encoded as a byte array.
    */
    public ElGamalEncryptionPair(byte[] encodedPublicKeyComponent,
        byte[] encodedMessageComponent) {
        publicKeyComponent = new BigInteger(encodedPublicKeyComponent);
        messageComponent = new BigInteger(encodedMessageComponent);
    }

    /**
    * Constructor taking two BigIntegers as input.
    *
    * @param publicKeyComponent The public key component of the ElGamal encryption pair.
    * @param messageComponent The message component of the ElGamal encryption pair.
    */
    public ElGamalEncryptionPair(BigInteger publicKeyComponent,
        BigInteger messageComponent) {
        this.publicKeyComponent = publicKeyComponent;
        this.messageComponent = messageComponent;
    }

    /**
    * Returns the public key component of the ElGamal encryption pair.
    * @return The public key component.
    */
    public BigInteger getPublicKeyComponent() {
        return publicKeyComponent;
    }

    /**
    * Returns the message component of the ElGamal encryption pair.
    * @return The message component.
    */
    public BigInteger getMessageComponent() {
        return messageComponent;
    }

    @Override
    public boolean equals(Object other) {
        return sameSubclass(other) &&
        privateEqual((ElGamalEncryptionPair) other);
    }

    private boolean sameSubclass(Object other) {
        return (other != null) && other.getClass().equals(this.getClass());
    }

    private boolean privateEqual(ElGamalEncryptionPair other) {
        return publicKeyComponent.equals(other.getPublicKeyComponent()) &&
        messageComponent.equals(other.getMessageComponent());
    }

    @Override
    public int hashCode() {
        return publicKeyComponent.hashCode() + messageComponent.hashCode();
    }

    /**
    * Multiplies a set of ElGamal encryption pairs modulo a modulus.
    *
    * @param pairs The set of ElGamal encryption pairs that should be multiplied together.
    * @param modulus The modulus to be used under the multiplication.
    * @return An ElGamal encryption pair representing the product.
    */
    public static ElGamalEncryptionPair multiply(
        ElGamalEncryptionPair[] pairs, BigInteger modulus) {
        BigInteger publicKeyComponent = BigInteger.ONE;
        BigInteger messageComponent = BigInteger.ONE;

        for (ElGamalEncryptionPair pair : pairs) {
            publicKeyComponent = publicKeyComponent.multiply(pair.getPublicKeyComponent())
                                                   .mod(modulus);
            messageComponent = messageComponent.multiply(pair.getMessageComponent())
                                               .mod(modulus);
        }

        return new ElGamalEncryptionPair(publicKeyComponent, messageComponent);
    }

    /**
    * Multiplies an ElGamal encryption pair with a message component modulo a
    * modulus.
    *
    * @param messageComponentFactor The message component factor with which the ElGamal encryption pair should be multiplied.
    * @param modulus The modulus to be used under the multiplication.
    * @return An ElGamal encryption pair representing the product.
    */
    public ElGamalEncryptionPair multiply(BigInteger messageComponentFactor,
        BigInteger modulus) {
        BigInteger newMessageComponent = messageComponent.multiply(messageComponentFactor)
                                                         .mod(modulus);

        return new ElGamalEncryptionPair(publicKeyComponent, newMessageComponent);
    }
}
