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
package com.computas.zkpev.cleansing;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import java.math.BigInteger;

import java.net.URISyntaxException;


/**
 * Integration tests on NizkpCleansing.
 *
 */
public class NizkpCleansingIntegrationTest {
    private static final String ELGAMAL_PROPERTIES_FILE_NAME = "NizkpCleansingIntegrationTestElGamalProperties.properties";
    private static final String AREAS_FILE_NAME = "NizkpCleansingIntegrationTestAreas.csv";
    private static final String CLEANSED_FILES_DIR_NAME = "NizkpCleansingIntegrationTestCleansedFilesDirectory";
    private static final String VCS_FILE_NAME = "NizkpCleansingIntegrationTestVcsEncryptedVotes.csv";
    private static final String RESULTS_LIST_FILE_NAME = "NizkpCleansingIntegrationTestResultsList.csv";
    private static final int NO_OF_AREAS_IN_SAMPLE_AREAS_FILE = 3765;
    private static final int NO_OF_CLEANSED_ENCRYPTED_VOTES_IN_SAMPLE_CLEANSED_ENCRYPTED_VOTES_FILE =
        49;
    private static final int NO_OF_VCS_ENCRYPTED_VOTES_IN_SAMPLE_VCS_ENCRYPTED_VOTES_FILE =
        85;
    private static final BigInteger SAMPLE_P = new BigInteger(
            "30953935016171929405181725048691475597165054658172086800611741385717085886966135022787053043294599719402052783457906554840050466598200845836350069464569830849652328849164597015834019636250184391187039519241529509539396137383783691026385997868581485251353115862362707856496254648707792083733847740839833577843038160760489606588067058523761002425294322414298639802035594840360412346667825330259855947401569537064580849961903216800408490338672611947294048038395112383055004376963978967260637379317563369771664058743286447728833162866458048271828606452285839103402380751993665831120012178542555391431510173573250387144139");
    private NizkpCleansing nizkp;

    /**
     * Creates the NIZKP for cleansing.
     */
    @BeforeMethod
    public void createNizkpCleansing() {
        nizkp = new NizkpCleansing(new String[] {
                    ELGAMAL_PROPERTIES_FILE_NAME, AREAS_FILE_NAME, VCS_FILE_NAME,
                    CLEANSED_FILES_DIR_NAME
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
                    ELGAMAL_PROPERTIES_FILE_NAME, AREAS_FILE_NAME, VCS_FILE_NAME,
                    CLEANSED_FILES_DIR_NAME, RESULTS_LIST_FILE_NAME
                });
        loggingNizkp.openResultsFileIfRequired();
        assertTrue(loggingNizkp.getResults().hasWriter());
    }

    /**
     * Verifies that the correct modulus p is loaded from the ElGamal properties file.
     * @throws IOException Thrown if something goes wrong while loading the ElGamal properties file.
     */
    @Test
    public void mustLoadTheCorrectElGamalEncryptionSystemModulus()
        throws IOException {
        assertEquals(nizkp.loadElGamalModulus(), SAMPLE_P);
    }

    /**
     * Verifies that the correct number of areas are loaded from the file.
     * @throws IOException Should not be thrown.
     */
    @Test
    public void mustLoadTheCorrectNumberOfAreas() throws IOException {
        AreasMap areas = nizkp.loadAreas();
        assertEquals(areas.size(), NO_OF_AREAS_IN_SAMPLE_AREAS_FILE);
    }

    /**
     * Verifies that the correct number of VCS encrypted votes is loaded
     * from the file.
     * @throws IOException Should not be thrown.
     */
    @Test
    public void mustLoadTheCorrectNumberOfVcsEncryptedVotes()
        throws IOException {
        VcsEncryptedVotesMap vcsEncryptedVotes = nizkp.loadVcsEncryptedVotes(SAMPLE_P);
        assertEquals(vcsEncryptedVotes.size(),
            NO_OF_VCS_ENCRYPTED_VOTES_IN_SAMPLE_VCS_ENCRYPTED_VOTES_FILE);
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
