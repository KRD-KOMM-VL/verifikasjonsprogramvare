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

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigInteger;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Unit tests on ElGamalPublicKeyList.
 *
 */
public class ElGamalPublicKeyListUnitTest {
    static final BigInteger SAMPLE_KEY_1 = new BigInteger("100");
    static final BigInteger SAMPLE_KEY_2 = new BigInteger("20");
    static final BigInteger SAMPLE_MODULUS = new BigInteger("113");
    static final BigInteger SAMPLE_AGGREGATE_KEY = new BigInteger("79");
    private static final ArrayList<BigInteger> SAMPLE_ARRAYLIST = new ArrayList<BigInteger>(Arrays.asList(
                new BigInteger("100"), new BigInteger("20")));
    private ElGamalPublicKeyList publicKeyList;

    /**
     * Creates an ElGamalEncryptionPair to run the tests on.
     */
    @BeforeMethod
    public void createElGamalPublicKeyList() {
        publicKeyList = new ElGamalPublicKeyList();
        publicKeyList.add(SAMPLE_KEY_1);
        publicKeyList.add(SAMPLE_KEY_2);
        publicKeyList.calculateAggregateKey(SAMPLE_MODULUS);
    }

    /**
     * Verifies alternative constructor.
     */
    @Test
    public void alternativeConstructorWorks() {
        assertEquals(publicKeyList, new ElGamalPublicKeyList(SAMPLE_ARRAYLIST));
    }

    /**
     * Verifies calculation of aggregateKey.
     */
    @Test
    public void correctCalculationOfAggregateKeys() {
        assertEquals(publicKeyList.getAggregateKey(), SAMPLE_AGGREGATE_KEY);
    }
}
