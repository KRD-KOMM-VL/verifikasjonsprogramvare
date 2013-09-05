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

import com.computas.zkpev2013.ZeroKnowledgeProof;
import static org.testng.Assert.*;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;


/**
 * Integration tests against the NizkpDecryption class.
 */
public class NizkpDecryptionIntegrationTest {
    private static final String ELGAMAL_PROPERTIES_FILE_NAME = "Foo";
    private static final String ELGAMAL_PUBLIC_KEY_FILE_NAME = "Bar";
    private static final String DECRYPTION_FILE_NAME = "NizkpDecryption2013IntegrationTestDecryptionFile.csv";
    private static final String RESULTS_LIST_FILE_NAME = "NizkpDecryption2013IntegrationTestResultsList.csv";
    private static final int NO_OF_DECRYPTION_LINES_IN_SAMPLE_DECRYPTION_FILE = 16;
    private NizkpDecryption nizkp;

    /**
     * Creates a NizkpCounting object without a results file.
     * @throws java.io.IOException Thrown if something goes wrong while loading the decryption file.
     */
    @BeforeMethod
    public void createNizkp() throws IOException {
        nizkp = new NizkpDecryption(new String[] {
                    ELGAMAL_PROPERTIES_FILE_NAME, ELGAMAL_PUBLIC_KEY_FILE_NAME,
                    DECRYPTION_FILE_NAME
                });
    }

    /**
     * Verifies that when no file name for the results list is provided, no
     * writer will be set.
     */
    @Test
    public void resultsListMustHaveNoWriterIfNoFileNameSpecified() {
        nizkp.openResultsFileIfRequired();
        assertFalse(nizkp.getResults().hasWriter());
    }

    /**
     * Verifies that when a file name for the results list is provided, a
     * writer will be set.
     */
    @Test
    public void resultsListMustHaveAWriterIfAFileNameIsSpecified() {
        ZeroKnowledgeProof nizkpWithResultsFile = new NizkpDecryption(new String[] {
                    ELGAMAL_PROPERTIES_FILE_NAME, ELGAMAL_PUBLIC_KEY_FILE_NAME,
                    DECRYPTION_FILE_NAME, RESULTS_LIST_FILE_NAME
                });
        nizkpWithResultsFile.openResultsFileIfRequired();
        assertTrue(nizkpWithResultsFile.getResults().hasWriter());
    }

    /**
     * Verifies that the correct number of decryption lines are loaded from the decryption file.
     * @throws java.io.IOException Thrown if something goes wrong while loading the decryption file.
     */
    @Test
    public void mustLoadTheCorrectNumberOfDecryptionLines()
        throws IOException {
        nizkp.loadDecryptionLines();
        assertEquals(nizkp.getDecryptionLines().size(),
            NO_OF_DECRYPTION_LINES_IN_SAMPLE_DECRYPTION_FILE);
    }
}
