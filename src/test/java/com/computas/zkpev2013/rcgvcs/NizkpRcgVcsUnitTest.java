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
package com.computas.zkpev2013.rcgvcs;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * Unit tests against the NizkpRcgVcs class.
 */
public class NizkpRcgVcsUnitTest {
    private static final String ENCRYPTED_VOTES_FILE_NAME = "Foo";
    private static final String RECEIPTS_FILE_NAME = "Bar";
    private static final String RESULTS_FILE_NAME = "Qux";
    private NizkpRcgVcs nizkp;

    /**
     * Creates a default Nikzp object to run tests against.
     */
    @BeforeMethod
    public void createNizkp() {
        nizkp = new NizkpRcgVcs(new String[] {
                    ENCRYPTED_VOTES_FILE_NAME, RECEIPTS_FILE_NAME,
                    RESULTS_FILE_NAME
                });
    }

    /**
     * Verifies that the constructor throws an IllegalArgumentException an empty String array
     * is passed.
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void constructorMustThrowIllegalArgumentExceptionWhenAnEmptyStringArrayIsPassed() {
        new NizkpRcgVcs(new String[] {  });
    }

    /**
     * Verifies that the constructor sets the file name of the encrypted votes file correctly.
     */
    @Test
    public void constructorSetsEncryptedVotesFileName() {
        assertEquals(nizkp.getEncryptedVotesFileName(),
            ENCRYPTED_VOTES_FILE_NAME);
    }

    /**
     * Verifies that the constructor sets the file name of the receipts file correctly.
     */
    @Test
    public void constructorSetsReceiptsFileName() {
        assertEquals(nizkp.getReceiptsFileName(), RECEIPTS_FILE_NAME);
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
        NizkpRcgVcs nonLoggingNizkp = new NizkpRcgVcs(new String[] {
                    ENCRYPTED_VOTES_FILE_NAME, RECEIPTS_FILE_NAME
                });
        assertFalse(nonLoggingNizkp.isLoggingOfResultsRequired());
    }
}
