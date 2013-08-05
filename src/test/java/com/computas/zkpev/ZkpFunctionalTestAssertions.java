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
package com.computas.zkpev;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * A set of assertions used by the functional tests for the ZKPs.
 *
 * @version $Id: ZkpFunctionalTestAssertions.java 11011 2011-10-20 08:26:55Z fvl $
 */
public final class ZkpFunctionalTestAssertions {
    private ZkpFunctionalTestAssertions() {
        // This is an assertions class, and should never be instantiated.
    }

    /**
     * Asserts that the results of the ZKP contain the given incident.
     *
     * @param zkp The ZKP whose results should contain the incident.
     * @param incident The incident to check for.
     */
    public static void assertZkpContainsIncident(ZeroKnowledgeProof zkp,
        Incident incident) {
        assertTrue(zkp.getResults().contains(incident));
    }

    /**
     * Asserts that the results of the ZKP don't contain the given incident.
     *
     * @param zkp The ZKP whose results shouldn't contain the incident.
     * @param incident The incident to check for.
     */
    public static void assertZkpDoesNotContainIncident(ZeroKnowledgeProof zkp,
        Incident incident) {
        assertFalse(zkp.getResults().contains(incident));
    }
}
