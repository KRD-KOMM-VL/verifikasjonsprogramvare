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

import com.computas.zkpev2013.Result;
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
 */
public class NizkpCleansingIntegrationTest {
    private static final String ELGAMAL_PROPERTIES_FILE_NAME = "NizkpCleansing2013IntegrationTestElGamalProperties.properties";
    private static final String AREAS_FILE_NAME = "NizkpCleansing2013IntegrationTestAreas.csv";
    private static final String ELECTION_EML_FILE_NAME = "NizkpCleansing2013IntegrationElectionDefinition.eml";
    private static final String CLEANSED_FILES_DIR_NAME = "NizkpCleansing2013IntegrationTestCleansedFilesDirectory";
    private static final String ENCRYPTED_VOTES_FILE_NAME = "NizkpCleansing2013IntegrationTestEncryptedVotes.csv";
    private static final String RESULTS_LIST_FILE_NAME = "NizkpCleansing2013IntegrationTestResultsList.csv";
    private static final int NO_OF_AREAS_IN_SAMPLE_AREAS_FILE = 3408;
    private static final int NO_OF_CLEANSED_ENCRYPTED_VOTES_IN_SAMPLE_CLEANSED_ENCRYPTED_VOTES_FILE =
        6;
    private static final int NO_OF_ENCRYPTED_VOTES_IN_SAMPLE_ENCRYPTED_VOTES_FILE =
        6;
    private static final BigInteger SAMPLE_P = new BigInteger(
            "22519781860318881430187237378393910440433456793106883439191554045609533190204716026094503488051043531257695232100353994296431999733305913289830606623675094806877884255872439714678914992056169353692036021770097223778392105262307803104951171429150982767069700653909195647599098780046724703785991755259095912786508845222597772887203546632493935590809326329822837682361511439054458165467044490658668908755516611075852591340913731324282531411301527453756791057107929172839003743485012313000403534330922416540828874783338650662007436059441348150784982317988527563812882812455109992843656727186872083932493433216403334110087");
    private static final Object LARGEST_AREA_PRIME = new BigInteger("70309");
    private static final Object CONTROLLED_ENVIRONMENT_PRIME = new BigInteger(
            "211");
    private static final Object UNCONTROLLED_ENVIRONMENT_PRIME = new BigInteger(
            "227");
    private NizkpCleansing nizkp;

    /**
     * Creates the NIZKP for cleansing.
     */
    @BeforeMethod
    public void createNizkpCleansing() {
        nizkp = new NizkpCleansing(new String[] {
                    ELGAMAL_PROPERTIES_FILE_NAME, AREAS_FILE_NAME,
                    ELECTION_EML_FILE_NAME, ENCRYPTED_VOTES_FILE_NAME,
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
                    ELGAMAL_PROPERTIES_FILE_NAME, AREAS_FILE_NAME,
                    ELECTION_EML_FILE_NAME, ENCRYPTED_VOTES_FILE_NAME,
                    CLEANSED_FILES_DIR_NAME, RESULTS_LIST_FILE_NAME
                });
        loggingNizkp.openResultsFileIfRequired();
        assertTrue(loggingNizkp.getResults().hasWriter());
    }

    /**
     * Verifies that the correct modulus p is loaded from the ElGamal properties file.
     *
     * @throws IOException Thrown if something goes wrong while loading the ElGamal properties file.
     */
    @Test
    public void mustLoadTheCorrectElGamalEncryptionSystemModulus()
        throws IOException {
        assertEquals(nizkp.loadElGamalModulus(), SAMPLE_P);
    }

    /**
     * Verifies that the correct number of areas are loaded from the file.
     *
     * @throws IOException Should not be thrown.
     */
    @Test
    public void mustLoadTheCorrectNumberOfAreas() throws IOException {
        AreasMap areas = nizkp.loadAreas();
        assertEquals(areas.size(), NO_OF_AREAS_IN_SAMPLE_AREAS_FILE);
    }

    /**
     * Verifies that the correct largest area prime is found.
     *
     * TODO: Should be calculated per municipality.
     * @throws IOException Should not be thrown.
     */
    @Test
    public void mustFindTheLargestAreaPrime() throws IOException {
        AreasMap areas = nizkp.loadAreas();
        assertEquals(nizkp.findLargestAreaPrime(areas), LARGEST_AREA_PRIME);
    }

    /**
     * Verifies that the correct prime for the controlled environment is loaded.
     */
    @Test
    public void mustLoadTheCorrectControlledEnvironmentPrime() {
        EnvironmentsMap environments = nizkp.loadEnvironments();
        assertEquals(environments.getPrime(
                EnvironmentsMap.Environment.CONTROLLED),
            CONTROLLED_ENVIRONMENT_PRIME);
    }

    /**
     * Verifies that the correct prime for the uncontrolled environment is loaded.
     */
    @Test
    public void mustLoadTheCorrectUncontrolledEnvironmentPrime() {
        EnvironmentsMap environments = nizkp.loadEnvironments();
        assertEquals(environments.getPrime(
                EnvironmentsMap.Environment.UNCONTROLLED),
            UNCONTROLLED_ENVIRONMENT_PRIME);
    }

    /**
     * Verifies that the correct compression factor is calculated.
     *
     * TODO: Should be calculated per municipality and per contest.
     * @throws IOException Should not be thrown.
     */
    @Test(enabled = false)
    public void mustCalculateTheCorrectCompressionFactor()
        throws IOException {
        AreasMap areas = nizkp.loadAreas();
        EnvironmentsMap environments = nizkp.loadEnvironments();
        assertEquals(nizkp.calculateCompressionFactor(areas, environments,
                SAMPLE_P), 100);
    }

    /**
     * Verifies that the correct number of encrypted votes is loaded
     * from the file.
     * @throws IOException Should not be thrown.
     *
     * TODO: Public key component seems to be reused -- establish whether that's correct or not.
     */
    @Test(enabled = false)
    public void mustLoadTheCorrectNumberOfEncryptedVotes()
        throws IOException {
        EncryptedVotesMap encryptedVotes = nizkp.loadEncryptedVotes(SAMPLE_P);
        assertEquals(encryptedVotes.size(),
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
