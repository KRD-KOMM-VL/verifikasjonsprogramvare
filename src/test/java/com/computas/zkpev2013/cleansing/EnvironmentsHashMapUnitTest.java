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
package com.computas.zkpev2013.cleansing;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNull;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigInteger;


/**
 * Unit tests on the EnvironmentsHashMap class.
 */
public class EnvironmentsHashMapUnitTest {
    private final static BigInteger SAMPLE_PRIME = new BigInteger("2");
    private EnvironmentsMap environments;

    /**
     * Creates an EnvironmentsMap to run the tests on.
     */
    @BeforeMethod
    public void createEnvironments() {
        environments = new EnvironmentsHashMap();
    }

    /**
     * Verifies that null is returned by default.
     */
    @Test
    public void mustReturnNullByDefault() {
        assertNull(environments.getPrime(EnvironmentsMap.Environment.CONTROLLED));
    }

    /**
     * Verifies that when the prime for the controlled environment is set, it can be gotten.
     */
    @Test
    public void mustReturnPrimeForControlledEnvironment() {
        environments.setControlledPrime(SAMPLE_PRIME);
        assertEquals(SAMPLE_PRIME,
            environments.getPrime(EnvironmentsMap.Environment.CONTROLLED));
    }

    /**
     * Verifies that when the prime for the uncontrolled environment is set, it can be gotten.
     */
    @Test
    public void mustReturnPrimeForUncontrolledEnvironment() {
        environments.setUncontrolledPrime(SAMPLE_PRIME);
        assertEquals(SAMPLE_PRIME,
            environments.getPrime(EnvironmentsMap.Environment.UNCONTROLLED));
    }

    /**
     * Returns a set with environments and their strings.
     * @return Data set with environments and their strings.
     */
    @DataProvider(name = "EnvironmentsAndStrings")
    public static Object[][] createEnvironmentsAndTheirStrings() {
        return new Object[][] {
            new Object[] {
                EnvironmentsMap.Environment.CONTROLLED, "CHANNEL_ID_CONTROLLED"
            },
            new Object[] {
                EnvironmentsMap.Environment.UNCONTROLLED,
                "CHANNEL_ID_UNCONTROLLED"
            }
        };
    }

    /**
     * Verifies that the environments can be created from the strings specified in encrypted votes.
     */
    @Test(dataProvider = "EnvironmentsAndStrings")
    public void mustCreateEnvironmentsFromString(
        EnvironmentsMap.Environment environment, String string) {
        assertEquals(environment, EnvironmentsHashMap.getEnvironment(string));
    }
}
