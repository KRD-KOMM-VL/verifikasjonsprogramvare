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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * Unit tests on the DecryptionLineWithIncorrectProofIncident class.
 */
public class DecryptionLineWithIncorrectProofIncidentUnitTest {
    private static final String COMMA = ",";
    private static final String SAMPLE_ELECTION_EVENT_ID = "730071";
    private static final String SAMPLE_ELECTION_ID = "01";
    private static final String SAMPLE_CONTEST_ID = "000007";
    private static final String SAMPLE_ENC_VOTE_OPT_IDS = "AIJ1jejPt76pKEIpr1BPUt5z0mT9FExthLmUbm/QZYAJv21+8rsw/tG4bFPe7N5D#n#0rfh+Ir7IJns5p6ehk98I2LxNgJzi+fOm82tNjhejsjIC4vWqFRZ0tF6Ry1Si5io#n#pUUOllrt8DxdmcI30fanp/0hNAdi3GPQ61v+ABOrZonjDvCFirAv9nnpHuTTlYlr#n#1L5oiHG3r6mjLy+e8vVAHur8xMCH/fQ1XJLvRby9wnSkZrAEMb1C0OLreaalVkSv#n#l4SiDebrdKgBrA3xR4ERCKwzeevif2uAcAirOtW/ph2k78mgMDsgI/7vDOMJElD+#n#WwN5+MQu4/58W9vh2x5aSCI=#AJ88iT77CUB5YuklJWiN56+DnsmoJkO2WUh7966m7RVYsMaiM/nCuut4CKeA6mxY#n#nM9BfVXHE5aM9vRW8W5Fu4vMQO721xRkSBCRDs1tAVe9Yjy3BaV8duL34sQkKTeA#n#YKklOB4gcMZjdBj1W2Il/2D/vniwUFeyMXbSqkynxiwS16LFDwyq58OUOVDDKLG6#n#AiovTltRtwHE7mrQJLwgqAH6NCUmx2dOE6K1WDdI4Twa+bGEZZGXO3qankiQpxuC#n#Lnp+U77I26/jXoFQzZC0VuC/sdxNqUhoLx75j28Qq0DstZ3SYNRH8M226K5maBQS#n#oQZwUE4PSONpA2AUjdVUtEQ=#";
    private static final String SAMPLE_DEC_VOTE_OPT_IDS_PROD = "3741264158469368466404212269249748092711943731820583364814408291710732477410869254686238207275582240120832";
    private static final String SAMPLE_SIGNATURE = "NzA3NDIxOTc2ODc5NzEyMTEyMjAyMzMzMzc0OTMxOTU0MjgxNTQ1OTQ4ODU1NTc4MDg1NTYzNzQxMjIzNzkzMTE4NTk1NjA0Mzk4NXwtNDY1NDUzNzQzNDA0NTY4MTYyNzQ0NTM4NzU0ODU4NjAzNzIxMjAzNTI4NDcyNDU2MzQzNTQ5MTg3ODk4MjU4Njg1MTgyNjcxODk2MzcxMDEyOTMzMDc2NjAyNjkzMDQxNjgwNDM0MDg3Mjc0NzE4MjUzNjI2MDkxMzEyNzYyMDQ2OTAwOTczNTIzMzYyODA3ODA2MzY0NjkxNDg2NjE5OTIyMDgwNzg3Nzc0ODg1MzQ5MDYxMDk5MjIwOTExMzM4NTkyMDE2OTE2NjQ3ODU1Nzk2NTI5NzAzMzk2MDY1ODEwNzgxMDg2NzQ5ODY5ODc5MTQ3MjY3MTQ5NzI3ODczMjA5NDg3MDQwNDM5Nzc1Nzk3NzQzMTY5Mjg0MTAyNjcyNTgyMjM4NDg0MDc1NDUwMjIxMDI0MTM4NjA1MDEwMDExMTE5NjU5OTU3MzM1MDQ4MDc2MzY0OTY2MDQyNjgzNTg2NzY3NDk5ODczNzcyNDQ1ODkyMjAyNjQxNjA1NDU5ODY0MzQ1MzUzNDI3OTk5MTM5OTU0NzUwMTE1NTY2MTU5Njc1ODMwNzIxODgyMDE2MTM3NzczMzczMTg2NTk0NDU0ODc0NTEwOTcwODM1Mzc4MzY2OTM1ODE4MDg3MDE4MTYzMzU4OTYwMzcyMDY5MzA4ODAxNzQ5NTY4NDQyODkyMTEzMzA2MDE2Mjc4MjIzNTMxNjkwNDA5Mzg3NzEyMDA0MDI3MzgxNzI3NzQ2NzY5NDQ1MTM5NzQ2NDYwNzk2Njg3MjQ2MzMzNjgwNTgzODM5NzIyODI1MzQ1MjU3NTYwOTA0MDI5NTEwNTAwMTA3NjIyMDgyODU3NDcyMTc2NzM2MDA4OTM1MjMxMzc4OTMyOTIzNzc0NDk4NTkyMDQ2NDAwMDE1NjM0ODk4ODU0MzkwNTN8bnVsbA==";
    private static final String SAMPLE_LINE = SAMPLE_ELECTION_EVENT_ID + COMMA +
        SAMPLE_ELECTION_ID + COMMA + SAMPLE_CONTEST_ID + COMMA +
        SAMPLE_ENC_VOTE_OPT_IDS + COMMA + SAMPLE_DEC_VOTE_OPT_IDS_PROD + COMMA +
        SAMPLE_SIGNATURE;
    private static final String OTHER_SAMPLE_LINE = SAMPLE_ELECTION_EVENT_ID +
        COMMA + SAMPLE_ELECTION_ID + COMMA + SAMPLE_CONTEST_ID + COMMA +
        SAMPLE_ENC_VOTE_OPT_IDS + ",92413908," + SAMPLE_SIGNATURE;
    private DecryptionLineWithIncorrectProofIncident incident;

    /**
      * Creates an incident to test against.
      */
    @BeforeMethod
    public void createDecryptionLineWithIncorrectProofIncident() {
        incident = new DecryptionLineWithIncorrectProofIncident(new DecryptionLine(
                    SAMPLE_LINE));
    }

    /**
     * Verifies that an incident is equal to itself.
     */
    @Test
    public void mustBeEqualToItself() {
        assertEquals(incident, incident);
    }

    /**
     * Verifies that an incident has a consistent hashCode.
     */
    @Test
    public void mustHaveSameHashCodeAsItself() {
        assertEquals(incident.hashCode(), incident.hashCode());
    }

    /**
     * Verifies that an incident is not equal to null.
     */
    @Test
    public void mustNotBeEqualToNull() {
        Object nullObject = null;
        assertFalse(incident.equals(nullObject));
    }

    /**
     * Verifies that an incident is not equal to an object of another class. This is used as the test case.
     */
    @Test
    public void mustNotBeEqualToAnObjectOfAnotherClass() {
        assertFalse(incident.equals(this));
    }

    /**
     * Verifies that the incident is equal to another incident with the same decryption line.
     */
    @Test
    public void mustBeEqualToAnotherDecryptionLineWithIncorrectProofIncidentWithTheSameLine() {
        assertEquals(incident,
            new DecryptionLineWithIncorrectProofIncident(
                new DecryptionLine(SAMPLE_LINE)));
    }

    /**
     * Verifies that the incident has the same hashCode as another incident with the same decryption line.
     */
    @Test
    public void mustHaveSameHashCodeAsAnotherDecryptionLineWithIncorrectProofIncidentWithTheSameLine() {
        assertEquals(incident.hashCode(),
            new DecryptionLineWithIncorrectProofIncident(
                new DecryptionLine(SAMPLE_LINE)).hashCode());
    }

    /**
     * Verifies that the incident is not equal to another incident with another decryption line.
     */
    @Test
    public void mustNotBeEqualToAnotherDecryptionLineWithIncorrectProofIncidentWithAnotherLine() {
        assertFalse(incident.equals(
                new DecryptionLineWithIncorrectProofIncident(
                    new DecryptionLine(OTHER_SAMPLE_LINE))));
    }

    /**
     * Verifies that toString produced a comma-separated line with the correct
     * values.
     */
    @Test
    public void toStringMustBeCommaSeparatedline() {
        assertEquals(incident.toString(),
            "DecryptionLineWithIncorrectProofIncident," +
            SAMPLE_ELECTION_EVENT_ID + COMMA + SAMPLE_ELECTION_ID + COMMA +
            SAMPLE_CONTEST_ID + COMMA + SAMPLE_ENC_VOTE_OPT_IDS + COMMA +
            SAMPLE_DEC_VOTE_OPT_IDS_PROD + COMMA + SAMPLE_SIGNATURE);
    }
}
