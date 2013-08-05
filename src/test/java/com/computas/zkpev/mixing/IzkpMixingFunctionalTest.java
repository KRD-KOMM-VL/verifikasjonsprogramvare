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
package com.computas.zkpev.mixing;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * Functional tests against the IzkpMixing class. These tests require
 * that there is a database available as defined in the database properties
 * file, and that all mixing/audit data sets have correct proofs. A sample
 * database dump is available in the test resources catalog.
 *
 *
 */
public class IzkpMixingFunctionalTest {
    private static final String ELGAMAL_PROPERTIES_FILE_NAME = "IzkpMixingFunctionalTestElGamalProperties.properties";
    private static final String ELGAMAL_PUBLIC_KEY_FILE_NAME = "IzkpMixingFunctionalTestElGamalPublicKey.properties";
    private static final String DATABASE_PROPERTIES_FILE_NAME = "IzkpMixingFunctionalTestDatabaseProperties.properties";
    private IzkpMixing izkp;

    /**
     * Creates a IzkpMixing object, loads the files with the ElGamal properties,
     * the ElGamal public key and the mixing data from the database, and runs the protocol.
     * @throws Exception Should not be thrown.
     */
    @BeforeMethod(groups = "MixingDatabasePresent")
    public void createIzkpLoadDataFilesAndRun() throws Exception {
        izkp = new IzkpMixing(new String[] {
                    ELGAMAL_PROPERTIES_FILE_NAME, ELGAMAL_PUBLIC_KEY_FILE_NAME,
                    DATABASE_PROPERTIES_FILE_NAME
                });
        izkp.run();
    }

    /**
     * Verifies that the IZKP didn't produce an incident.
     */
    @Test(groups = "MixingDatabasePresent")
    public void resultsMustNotContainAnIncident() {
        assertEquals(izkp.getResults().size(), 0);
    }
}
