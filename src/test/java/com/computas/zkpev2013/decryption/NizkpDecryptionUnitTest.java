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
package com.computas.zkpev2013.decryption;

import static org.testng.Assert.*;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * Unit tests against the NizkpDecryption class.
 *
 *
 */
public class NizkpDecryptionUnitTest {
    private static final String ELGAMAL_PROPERTIES_FILE_NAME = "Foo";
    private static final String ELGAMAL_PUBLIC_KEYS_FILE_NAME = "Bar";
    private static final String DECRYPTION_FILE_NAME = "Qux";
    private static final String RESULTS_FILE_NAME = "Corge";
    private NizkpDecryption nizkp;

    /**
     * Creates a default Nikzp object to run tests against.
     */
    @BeforeMethod
    public void createNizkp() {
        nizkp = new NizkpDecryption(new String[] {
                    ELGAMAL_PROPERTIES_FILE_NAME, ELGAMAL_PUBLIC_KEYS_FILE_NAME,
                    DECRYPTION_FILE_NAME, RESULTS_FILE_NAME
                });
    }

    /**
     * Verifies that the constructor throws an IllegalArgumentException an empty String array
     * is passed.
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void constructorMustThrowIllegalArgumentExceptionWhenAnEmptyStringArrayIsPassed() {
        new NizkpDecryption(new String[] {  });
    }

    /**
     * Verifies that the constructor sets the file name of the ElGamal properties file correctly.
     */
    @Test
    public void constructorSetsElGamalPropertiesFileName() {
        assertEquals(nizkp.getElGamalPropertiesFileName(),
            ELGAMAL_PROPERTIES_FILE_NAME);
    }

    /**
     * Verifies that the constructor sets the file name of the ElGamal public key file correctly.
     */
    @Test
    public void constructorSetsElGamalPublicKeyFileName() {
        assertEquals(nizkp.getElGamalPublicKeysFileName(),
            ELGAMAL_PUBLIC_KEYS_FILE_NAME);
    }

    /**
     * Verifies that the constructor sets the file name of the RCG file correctly.
     */
    @Test
    public void constructorSetsDecryptionFileName() {
        assertEquals(nizkp.getDecryptionFileName(), DECRYPTION_FILE_NAME);
    }

    /**
     * Verifies that the constructor sets the file name for the results file correctly.
     */
    @Test
    public void constructorSetsResultsFileName() {
        assertEquals(nizkp.getResultsFileName(), RESULTS_FILE_NAME);
    }

    /**
     * Verifies that logging is required if a results file name is provided.
     */
    @Test
    public void loggingOfResultsIsRequiredIfAResultsFileNameProvided() {
        assertTrue(nizkp.isLoggingOfResultsRequired());
    }

    /**
     * Verifies that logging is not required if a results file name is not
     * provided.
     */
    @Test
    public void loggingOfResultsIsNotRequiredIfAResultsFileNameIsNotProvided() {
        NizkpDecryption nonLoggingNizkp = new NizkpDecryption(new String[] {
                    ELGAMAL_PROPERTIES_FILE_NAME, ELGAMAL_PUBLIC_KEYS_FILE_NAME,
                    DECRYPTION_FILE_NAME
                });
        assertFalse(nonLoggingNizkp.isLoggingOfResultsRequired());
    }

    /**
     * Verifies that getLogger doesn't return null.
     */
    @Test
    public void getLoggerShouldNotBeNull() {
        assertNotNull(NizkpDecryption.getLogger());
    }

    /**
     * Verifies that getLogger returns the same object when called twice.
     */
    @Test
    public void getLoggerReturnsSameObject() {
        assertEquals(NizkpDecryption.getLogger(), NizkpDecryption.getLogger());
    }
}
