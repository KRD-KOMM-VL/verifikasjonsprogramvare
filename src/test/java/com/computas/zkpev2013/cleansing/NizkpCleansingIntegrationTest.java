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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import java.net.URISyntaxException;


/**
 * Integration tests on NizkpCleansing.
 */
public class NizkpCleansingIntegrationTest {
    private static final String CLEANSED_FILES_DIR_NAME = "NizkpCleansing2013IntegrationTestCleansedFilesDirectory";
    private static final String ENCRYPTED_VOTES_FILE_NAME = "NizkpCleansing2013IntegrationTestEncryptedVotes.csv";
    private static final String RESULTS_LIST_FILE_NAME = "NizkpCleansing2013IntegrationTestResultsList.csv";
    private static final int NO_OF_CLEANSED_ENCRYPTED_VOTES_IN_SAMPLE_CLEANSED_ENCRYPTED_VOTES_FILE =
        6;
    private static final int NO_OF_ENCRYPTED_VOTE_KEYS_IN_SAMPLE_ENCRYPTED_VOTES_FILE =
        4;
    private static final int NO_OF_ENCRYPTED_VOTES_IN_SAMPLE_ENCRYPTED_VOTES_FILE =
        6;
    private NizkpCleansing nizkp;

    /**
     * Creates the NIZKP for cleansing.
     */
    @BeforeMethod
    public void createNizkpCleansing() {
        nizkp = new NizkpCleansing(new String[] {
                    ENCRYPTED_VOTES_FILE_NAME, CLEANSED_FILES_DIR_NAME
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
        NizkpCleansing loggingNizkp = new NizkpCleansing(new String[] {
                    ENCRYPTED_VOTES_FILE_NAME, CLEANSED_FILES_DIR_NAME,
                    RESULTS_LIST_FILE_NAME
                });
        loggingNizkp.openResultsFileIfRequired();
        assertTrue(loggingNizkp.getResults().hasWriter());
    }

    /**
     * Verifies that the correct number of encrypted vote keys is loaded
     * from the file.
     * @throws IOException Should not be thrown.
     */
    @Test
    public void mustLoadTheCorrectNumberOfEncryptedVoteKeys()
        throws IOException {
        EncryptedVotesMap encryptedVotes = nizkp.loadEncryptedVotes();
        assertEquals(encryptedVotes.size(),
            NO_OF_ENCRYPTED_VOTE_KEYS_IN_SAMPLE_ENCRYPTED_VOTES_FILE);
    }

    /**
     * Verifies that the correct number of encrypted votes is loaded
     * from the file.
     * @throws IOException Should not be thrown.
     */
    @Test
    public void mustLoadTheCorrectNumberOfEncryptedVotes()
        throws IOException {
        EncryptedVotesMap encryptedVotes = nizkp.loadEncryptedVotes();
        assertEquals(encryptedVotes.getNoOfEncryptedVotes(),
            NO_OF_ENCRYPTED_VOTES_IN_SAMPLE_ENCRYPTED_VOTES_FILE);
    }

    /**
     * Verifies that the correct number of cleansed encrypted votes is loaded
     * from the file.
     * @throws IOException Should not be thrown.
     * @throws URISyntaxException Should not be thrown.
     */
    @Test
    public void mustLoadTheCorrectNumberOfCleansedEncryptedVotes()
        throws IOException, URISyntaxException {
        CleansedVotesList cleansedEncryptedVotes = nizkp.loadCleansedVotes();
        assertEquals(cleansedEncryptedVotes.size(),
            NO_OF_CLEANSED_ENCRYPTED_VOTES_IN_SAMPLE_CLEANSED_ENCRYPTED_VOTES_FILE);
    }
}
