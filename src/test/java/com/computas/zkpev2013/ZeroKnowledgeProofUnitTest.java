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
package com.computas.zkpev2013;

import com.computas.zkpev.decryption.NizkpDecryption;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;


/**
 * Unit tests against the AbstractZkp class.
 *
 */
public class ZeroKnowledgeProofUnitTest {
    private ZeroKnowledgeProof zkp;

    /**
     * Creates a default Zkp object to run tests against.
     */
    @BeforeMethod
    public void createZkp() {
        zkp = new ZeroKnowledgeProof(new String[] {  }) {
                    @Override
                    protected void parseArguments(String[] arguments) {
                        // No implementation needed as part of the unit test.
                    }

                    /**
                     * No need to do anything for unit test purposes.
                     */
                    @Override
                    protected void run() throws IOException {
                        // No implementation needed as part of the unit test.
                    }
                };
    }

    /**
     * Verifies that the results are empty by default.
     */
    @Test
    public void resultsMustBeEmptyByDefault() {
        assertTrue(zkp.getResults().isEmpty());
    }

    /**
     * Verifies that getLogger doesn't return null.
     */
    @Test
    public void getLoggerShouldNotBeNull() {
        assertNotNull(ZeroKnowledgeProof.getLogger());
    }

    /**
     * Verifies that getLogger returns the same object when called twice.
     */
    @Test
    public void getLoggerReturnsSameObject() {
        assertEquals(ZeroKnowledgeProof.getLogger(),
            ZeroKnowledgeProof.getLogger());
    }
}
