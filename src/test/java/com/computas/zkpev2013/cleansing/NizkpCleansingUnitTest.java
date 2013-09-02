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

import java.math.BigInteger;


/**
 * Unit tests on NizkpCleansing.
 */
public class NizkpCleansingUnitTest {
    private static final String ELGAMAL_PROPERTIES_FILE_NAME = "Corge";
    private static final String AREAS_FILE_NAME = "Grault";
    private static final String ELECTION_EML_FILE_NAME = "Quux";
    private static final String ENCRYPTED_VOTES_FILE_NAME = "Foo";
    private static final String CLEANSED_DIR_NAME = "Bar";
    private static final String RESULTS_FILE_NAME = "Qux";
    private static final BigInteger SAMPLE_P = new BigInteger(
            "22519781860318881430187237378393910440433456793106883439191554045609533190204716026094503488051043531257695232100353994296431999733305913289830606623675094806877884255872439714678914992056169353692036021770097223778392105262307803104951171429150982767069700653909195647599098780046724703785991755259095912786508845222597772887203546632493935590809326329822837682361511439054458165467044490658668908755516611075852591340913731324282531411301527453756791057107929172839003743485012313000403534330922416540828874783338650662007436059441348150784982317988527563812882812455109992843656727186872083932493433216403334110087");
    private NizkpCleansing nizkp;

    /**
    * Creates a default Nikzp object to run tests against.
    */
    @BeforeMethod
    public void createNizkp() {
        nizkp = new NizkpCleansing(new String[] {
                    ELGAMAL_PROPERTIES_FILE_NAME, AREAS_FILE_NAME,
                    ELECTION_EML_FILE_NAME, ENCRYPTED_VOTES_FILE_NAME,
                    CLEANSED_DIR_NAME, RESULTS_FILE_NAME
                });
    }

    /**
     * Verifies that the constructor throws an IllegalArgumentException an empty String array
     * is passed.
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void constructorMustThrowIllegalArgumentExceptionWhenAnEmptyStringArrayIsPassed() {
        new NizkpCleansing(new String[] {  });
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
     * Verifies that the constructor sets the file name of the areas file correctly.
     */
    @Test
    public void constructorSetsAreaFileName() {
        assertEquals(nizkp.getAreasFileName(), AREAS_FILE_NAME);
    }

    /**
     * Verifies that the constructor sets the file name of election EML file correctly.
     */
    @Test
    public void constructorSetsElectionEmlFileName() {
        assertEquals(nizkp.getElectionEmlFileName(), ELECTION_EML_FILE_NAME);
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
     * Verifies that the constructor sets the directory name with the cleansed
     * votes.
     */
    @Test
    public void constructorSetsCleansedFilesDir() {
        assertEquals(nizkp.getCleansedFilesDir(), CLEANSED_DIR_NAME);
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
        NizkpCleansing nonLoggingNizkp = new NizkpCleansing(new String[] {
                    ELGAMAL_PROPERTIES_FILE_NAME, AREAS_FILE_NAME,
                    ELECTION_EML_FILE_NAME, ENCRYPTED_VOTES_FILE_NAME,
                    CLEANSED_DIR_NAME
                });
        assertFalse(nonLoggingNizkp.isLoggingOfResultsRequired());
    }

    /**
     * Verifies that the logarithm is calculated correctly for log_p(p-1).
     */
    @Test
    public void mustCalculateLogarithmAsOneForLogPPMinusOne() {
        assertEquals(nizkp.calculateFlooredLogarithm(SAMPLE_P,
                SAMPLE_P.subtract(BigInteger.ONE)), 1);
    }

    /**
     * Verifies that the logarithm is calculated correctly.
     */
    @Test
    public void mustCalculateLogarithmCorrectly() {
        assertEquals(nizkp.calculateFlooredLogarithm(SAMPLE_P,
                new BigInteger("1001")), 205);
    }
}
