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

import org.apache.commons.lang.StringUtils;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigInteger;

import java.util.Arrays;
import java.util.List;


/**
 * Unit tests on ElGamalEncryptionTuple.
 *
 */
public class ElGamalEncryptionTupleUnitTest {
    private static final BigInteger PUBLIC_KEY_COMPONENT = new BigInteger(
            "1234");
    private static final List<BigInteger> MESSAGE_COMPONENTS = Arrays.asList(new BigInteger(
                "1231"), new BigInteger("4131"));
    private static final BigInteger MODULUS = new BigInteger("7919");
    private static final BigInteger MESSAGE_COMPONENT_CONVERTED = new BigInteger(
            "1263");
    private ElGamalEncryptionTuple touple;

    /**
     * Creates an ElGamalEncryptionTuple to run the tests on.
     */
    @BeforeMethod
    public void createElGamalEncryptionTouple() {
        touple = new ElGamalEncryptionTuple(PUBLIC_KEY_COMPONENT,
                MESSAGE_COMPONENTS);
    }

    /**
     * Verifies that the constructor sets the public key component correctly.
     */
    @Test
    public void constructorMustSetPublicKeyComponentCorrectly() {
        assertEquals(touple.getPublicKeyComponent(), PUBLIC_KEY_COMPONENT);
    }

    /**
     * Verifies that the constructor with byte array behaves well.
     */
    @Test
    public void constructorWithStringBehavesWell() {
        assertEquals(touple,
            new ElGamalEncryptionTuple(
                (PUBLIC_KEY_COMPONENT + "#" +
                StringUtils.join(MESSAGE_COMPONENTS, "#") + "#").getBytes()));
    }

    /**
     * Verifies that the constructor sets the message component correctly.
     */
    @Test
    public void constructorMustSetMessageComponentCorrectly() {
        assertEquals(touple.getMessageComponents(), MESSAGE_COMPONENTS);
    }

    /**
     * Verifies that a pair is equal to itself.
     */
    @Test
    public void mustBeEqualToItself() {
        assertEquals(touple, touple);
    }

    /**
     * Verifies that a pair has a consistent hashCode.
     */
    @Test
    public void mustHaveSameHashCodeAsItself() {
        assertEquals(touple.hashCode(), touple.hashCode());
    }

    /**
     * Verifies that a pair is not equal to null.
     */
    @Test
    public void mustNotBeEqualToNull() {
        Object nullObject = null;
        assertFalse(touple.equals(nullObject));
    }

    /**
     * Verifies that a pair is not equal to an object of another class. This is used as the test case.
     */
    @Test
    public void mustNotBeEqualToAnObjectOfAnotherClass() {
        assertFalse(touple.equals(this));
    }

    /**
     * Verifies that a pair is equal to another pair having the same components.
     */
    @Test
    public void mustBeEqualToAnotherElGamalEncryptionToupleWithTheSameComponents() {
        assertEquals(touple,
            new ElGamalEncryptionTuple(PUBLIC_KEY_COMPONENT, MESSAGE_COMPONENTS));
    }

    /**
     * Verifies that a pair has the same hash code as another pair with the same components.
     */
    @Test
    public void mustHaveSameHashCodeAsAnotherElGamalEncryptionToupleWithTheSameComponents() {
        assertEquals(touple.hashCode(),
            new ElGamalEncryptionTuple(PUBLIC_KEY_COMPONENT, MESSAGE_COMPONENTS).hashCode());
    }

    /**
     * Verifies that a pair is not equal to another pair with another different public key component.
     */
    @Test
    public void mustNotBeEqualToAnotherElGamalEncryptionToupleWithAnotherPublicKeyComponent() {
        assertFalse(touple.equals(
                new ElGamalEncryptionTuple(BigInteger.ONE, MESSAGE_COMPONENTS)));
    }

    /**
     * Verifies that a pair is not equal to another pair with another different message component.
     */
    @Test
    public void mustNotBeEqualToAnotherElGamalEncryptionToupleWithAnotherMessageComponent() {
        assertFalse(touple.equals(
                new ElGamalEncryptionTuple(PUBLIC_KEY_COMPONENT,
                    Arrays.asList(BigInteger.ONE))));
    }

    /**
     * Verifies that a touple correctly converts to an ElgamalEncryptionPair.
     */
    @Test
    public void convertToElGamalEncryptionPairCalculation() {
        assertEquals(touple.convertToPair(MODULUS),
            new ElGamalEncryptionPair(PUBLIC_KEY_COMPONENT,
                MESSAGE_COMPONENT_CONVERTED));
    }
}
