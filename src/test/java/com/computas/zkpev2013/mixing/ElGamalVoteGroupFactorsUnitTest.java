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
package com.computas.zkpev2013.mixing;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigInteger;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;


/**
 * Unit tests on ElGamalVoteGroupFactors.
 *
 */
public class ElGamalVoteGroupFactorsUnitTest {
    private ElGamalVoteGroupFactors factors;

    /**
      * Creates an ElGamalVoteGroupFactors to test against.
      */
    @BeforeMethod
    public void createElGamalVoteGroupFactors() {
        factors = new ElGamalVoteGroupFactors(BigInteger.ZERO, BigInteger.ONE,
                BigInteger.TEN);
    }

    /**
     * Verifies that an ElGamalVoteGroupFactors is equal to itself.
     */
    @Test
    public void mustBeEqualToItself() {
        assertEquals(factors, factors);
    }

    /**
     * Verifies that an ElGamalVoteGroupFactors has a consistent hashCode.
     */
    @Test
    public void mustHaveSameHashCodeAsItself() {
        assertEquals(factors.hashCode(), factors.hashCode());
    }

    /**
     * Verifies that an ElGamalVoteGroupFactors is not equal to null.
     */
    @Test
    public void mustNotBeEqualToNull() {
        Object nullObject = null;
        assertFalse(factors.equals(nullObject));
    }

    /**
     * Verifies that an ElGamalVoteGroupFactors is not equal to an object of another class. This is used as the test case.
     */
    @Test
    public void mustNotBeEqualToAnObjectOfAnotherClass() {
        assertFalse(factors.equals(this));
    }

    /**
     * Verifies that the ElGamalVoteGroupFactors is equal to another ElGamalVoteGroupFactors with the same factors.
     */
    @Test
    public void mustBeEqualToAnotherElGamalVoteGroupFactorsWithTheSameFactors() {
        assertEquals(factors,
            new ElGamalVoteGroupFactors(BigInteger.ZERO, BigInteger.ONE,
                BigInteger.TEN));
    }

    /**
     * Verifies that the ElGamalVoteGroupFactors is not equal to another ElGamalVoteGroupFactors with another p.
     */
    @Test
    public void mustNotBeEqualToAnotherElGamalVoteGroupFactorsWithAnotherP() {
        assertFalse(factors.equals(
                new ElGamalVoteGroupFactors(BigInteger.ONE, BigInteger.ONE,
                    BigInteger.TEN)));
    }

    /**
     * Verifies that the ElGamalVoteGroupFactors is not equal to another ElGamalVoteGroupFactors with another public key factor.
     */
    @Test
    public void mustNotBeEqualToAnotherElGamalVoteGroupFactorsWithAnotherPublicKeyFactor() {
        assertFalse(factors.equals(
                new ElGamalVoteGroupFactors(BigInteger.ZERO, BigInteger.ZERO,
                    BigInteger.TEN)));
    }

    /**
     * Verifies that the ElGamalVoteGroupFactors is not equal to another ElGamalVoteGroupFactors with another message factor.
     */
    @Test
    public void mustNotBeEqualToAnotherElGamalVoteGroupFactorsWithAnotherMessageFactor() {
        assertFalse(factors.equals(
                new ElGamalVoteGroupFactors(BigInteger.ZERO, BigInteger.ONE,
                    BigInteger.ZERO)));
    }
}
