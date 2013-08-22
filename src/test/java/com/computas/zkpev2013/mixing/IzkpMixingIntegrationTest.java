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

import java.io.IOException;

import static org.testng.Assert.*;


/**
 * Integration tests on the IzkpMixing class.
 *
 */
public class IzkpMixingIntegrationTest {
    private static final String ELGAMAL_PROPERTIES_FILE_NAME = "IzkpMixingIntegrationTestElGamalProperties.properties";
    private static final String ELGAMAL_PUBLIC_KEY_FILE_NAME = "IzkpMixingIntegrationTestElGamalPublicKey.properties";
    private static final String DATABASE_PROPERTIES_FILE_NAME = "IzkpMixingIntegrationTestDatabaseProperties.properties";
    private static final Object SAMPLE_DATABASE_NAME = "mxmanagertest";
    private static final String RESULTS_LIST_FILE_NAME = "IzkpMixingIntegrationTestResultsList.csv";
    private static final String SAMPLE_USERNAME = "usertestmxsmanager";
    private static final String SAMPLE_PASSWORD = "secret";
    private IzkpMixing izkp;

    /**
     * Creates a IzkpMixing object without a results file.
     */
    @BeforeMethod
    public void createIzkp() {
        izkp = new IzkpMixing(new String[] {
                    ELGAMAL_PROPERTIES_FILE_NAME, ELGAMAL_PUBLIC_KEY_FILE_NAME,
                    DATABASE_PROPERTIES_FILE_NAME
                });
    }

    /**
     * Verifies that when no file name for the results list is provided, no
     * writer will be set.
     */
    @Test
    public void resultsListMustHaveNoWriterIfNoFileNameSpecified() {
        izkp.openResultsFileIfRequired();
        assertFalse(izkp.getResults().hasWriter());
    }

    /**
     * Verifies that when a file name for the results list is provided, a
     * writer will be set.
     */
    @Test
    public void resultsListMustHaveAWriterIfAFileNameIsSpecified() {
        IzkpMixing izkpWithResultsFile = new IzkpMixing(new String[] {
                    ELGAMAL_PROPERTIES_FILE_NAME, ELGAMAL_PUBLIC_KEY_FILE_NAME,
                    DATABASE_PROPERTIES_FILE_NAME, RESULTS_LIST_FILE_NAME
                });
        izkpWithResultsFile.openResultsFileIfRequired();
        assertTrue(izkpWithResultsFile.getResults().hasWriter());
    }

    /**
     * Verifies that the correct database name is loaded from the database
     * properties file.
     * @throws java.io.IOException Thrown if something goes wrong while loading the database properties file.
     */
    @Test
    public void mustLoadTheCorrectDatabaseName() throws IOException {
        izkp.loadDatabaseProperties();
        assertEquals(izkp.getDatabaseName(), SAMPLE_DATABASE_NAME);
    }

    /**
     * Verifies that the correct username is loaded from the database
     * properties file.
     * @throws java.io.IOException Thrown if something goes wrong while loading the database properties file.
     */
    @Test
    public void mustLoadTheCorrectUsername() throws IOException {
        izkp.loadDatabaseProperties();
        assertEquals(izkp.getUsername(), SAMPLE_USERNAME);
    }

    /**
     * Verifies that the correct password is loaded from the database
     * properties file.
     * @throws java.io.IOException Thrown if something goes wrong while loading the database properties file.
     */
    @Test
    public void mustLoadTheCorrectPassword() throws IOException {
        izkp.loadDatabaseProperties();
        assertEquals(izkp.getPassword(), SAMPLE_PASSWORD);
    }
}
