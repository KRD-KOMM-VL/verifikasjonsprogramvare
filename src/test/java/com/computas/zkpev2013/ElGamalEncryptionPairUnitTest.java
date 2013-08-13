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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigInteger;


/**
 * Unit tests on ElGamalEncryptionPair.
 *
 */
public class ElGamalEncryptionPairUnitTest {
    private static final BigInteger PUBLIC_KEY_COMPONENT = new BigInteger(
            "1234");
    private static final BigInteger MESSAGE_COMPONENT = new BigInteger("5678");
    private static final BigInteger MODULUS = new BigInteger("7919");
    private static final BigInteger PUBLIC_KEY_COMPONENT_SQUARED = new BigInteger(
            "2308");
    private static final BigInteger MESSAGE_COMPONENT_SQUARED = new BigInteger(
            "1435");
    private ElGamalEncryptionPair pair;

    /**
     * Creates an ElGamalEncryptionPair to run the tests on.
     */
    @BeforeMethod
    public void createElGamalEncryptionPair() {
        pair = new ElGamalEncryptionPair(PUBLIC_KEY_COMPONENT, MESSAGE_COMPONENT);
    }

    /**
     * Verifies that the constructor sets the public key component correctly.
     */
    @Test
    public void constructorMustSetPublicKeyComponentCorrectly() {
        assertEquals(pair.getPublicKeyComponent(), PUBLIC_KEY_COMPONENT);
    }

    /**
     * Verifies that the constructor sets the message component correctly.
     */
    @Test
    public void constructorMustSetMessageComponentCorrectly() {
        assertEquals(pair.getMessageComponent(), MESSAGE_COMPONENT);
    }

    /**
     * Verifies that the constructor with byte array behaves well.
     */
    @Test
    public void constructorWithStringBehavesWell() {
        assertEquals(pair,
            new ElGamalEncryptionPair(
                (PUBLIC_KEY_COMPONENT + "|" + MESSAGE_COMPONENT).getBytes()));
    }

    /**
     * Verifies that a pair is equal to itself.
     */
    @Test
    public void mustBeEqualToItself() {
        assertEquals(pair, pair);
    }

    /**
     * Verifies that a pair has a consistent hashCode.
     */
    @Test
    public void mustHaveSameHashCodeAsItself() {
        assertEquals(pair.hashCode(), pair.hashCode());
    }

    /**
     * Verifies that a pair is not equal to null.
     */
    @Test
    public void mustNotBeEqualToNull() {
        Object nullObject = null;
        assertFalse(pair.equals(nullObject));
    }

    /**
     * Verifies that a pair is not equal to an object of another class. This is used as the test case.
     */
    @Test
    public void mustNotBeEqualToAnObjectOfAnotherClass() {
        assertFalse(pair.equals(this));
    }

    /**
     * Verifies that a pair is equal to another pair having the same components.
     */
    @Test
    public void mustBeEqualToAnotherElGamalEncryptionPairWithTheSameComponents() {
        assertEquals(pair,
            new ElGamalEncryptionPair(PUBLIC_KEY_COMPONENT, MESSAGE_COMPONENT));
    }

    /**
     * Verifies that a pair has the same hash code as another pair with the same components.
     */
    @Test
    public void mustHaveSameHashCodeAsAnotherElGamalEncryptionPairWithTheSameComponents() {
        assertEquals(pair.hashCode(),
            new ElGamalEncryptionPair(PUBLIC_KEY_COMPONENT, MESSAGE_COMPONENT).hashCode());
    }

    /**
     * Verifies that a pair is not equal to another pair with another different public key component.
     */
    @Test
    public void mustNotBeEqualToAnotherElGamalEncryptionPairWithAnotherPublicKeyComponent() {
        assertFalse(pair.equals(
                new ElGamalEncryptionPair(BigInteger.ONE, MESSAGE_COMPONENT)));
    }

    /**
     * Verifies that a pair is not equal to another pair with another different message component.
     */
    @Test
    public void mustNotBeEqualToAnotherElGamalEncryptionPairWithAnotherMessageComponent() {
        assertFalse(pair.equals(
                new ElGamalEncryptionPair(PUBLIC_KEY_COMPONENT, BigInteger.ONE)));
    }

    /**
     * Verifies the multiplication of a single vote.
     */
    @Test
    public void multiplicationOfSingleVoteIsIdentityOperation() {
        assertEquals(ElGamalEncryptionPair.multiply(
                new ElGamalEncryptionPair[] { pair }, MODULUS), pair);
    }

    /**
     * Verifies the multiplication of a two identical votes.
     */
    @Test
    public void multiplicationOfTwoVotesCalculatedCorrectly() {
        assertEquals(ElGamalEncryptionPair.multiply(
                new ElGamalEncryptionPair[] { pair, pair }, MODULUS),
            new ElGamalEncryptionPair(PUBLIC_KEY_COMPONENT_SQUARED,
                MESSAGE_COMPONENT_SQUARED));
    }

    /**
     * Verifies the multiplication of a vote with a single BigInteger.
     */
    @Test
    public void multiplicationVotesWithBigIntegerCalculatedCorrectly() {
        assertEquals(pair.multiply(MESSAGE_COMPONENT, MODULUS),
            new ElGamalEncryptionPair(PUBLIC_KEY_COMPONENT,
                MESSAGE_COMPONENT_SQUARED));
    }
}
