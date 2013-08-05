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
package com.computas.zkpev.decryption;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * Unit tests on the DecryptionLineWithIncorrectProofIncident class.
 *
 */
public class DecryptionLineWithIncorrectProofIncidentUnitTest {
    private static final String COMMA = ",";
    private static final String SAMPLE_ELECTION_EVENT_ID = "999901";
    private static final String SAMPLE_ELECTION_ID = "01";
    private static final String SAMPLE_CONTEST_ID = "000004";
    private static final String SAMPLE_ENC_VOTE_OPT_IDS = "MzY2MDI1Njg4NTgzMDQ5OTQ5NTE2NTQ2ODU5NTAzNjQxMDQxNzUzMDMyODE3NDM2#n#MDM5ODc4OTgwODYxNzgxOTU4Mjk4Mjg3Mjc0MzMyMzI1MDExNzA2NTc0NzA2Mzkw#n#NDQwMzcyMjQ5MjE4MzAxNTUxMDA5NjY2ODA0ODYzNDc4MTE4NjI4OTM4NzE2MzM2#n#MDEwNTk0NzY0NzUzNTM2OTAxNTgyNTE1MDEyMTgwNjUwMDI4OTg2MTQ1Nzk3ODMx#n#OTMyNjkzODAxNjA0NTUyODA1NDQ1OTY2MTcwNTgyNTkxNTM4NzMyNDg4NTA2Mzg4#n#MzIzMzk2MzUyNDQ4MTQzNjUwMzE2NDc2MDgwMjQwMjY3NTc3NjMyMzgyMTY5OTMz#n#MTU1MjIyNzg0MTU2Njk4ODU0OTE1NTE5MzQxODA4OTk3MDIwODg0NTAxNzA4MTY2#n#NzY5Mjk2ODc2NTE1NDYwNTM5MDAxNDM1MTI0NDk1Mzk5MjgzMTQ2NTU4ODk0NTM1#n#NjM3MDA4NTMwMDM5MjgyMjAwMTI4NzQ2NTYxNDEyMzE5OTc4MDM3OTUxMzk1NTkz#n#Mzg0Mzg1MjMwNDAyNzk0NjMyNzE4NDE2MTg4OTk5NDMyNTM1MzA3NDU0MjIwODU2#n#ODQ5NDU0NzYxODg0MDU2NzI1NzgyMTczMTkzNzM5NjQyMjg3MDI2ODI0NTY0OTc1#n#MTcwOTQxNTUyMTY3MTI2NzE0OTY1MTI5NTI3ODYwMTUwODg0MjE0NjUyNzUzNTg3#n#MjczMTkyODc3MjczMzM2NzMyNTEwNDE3MjM5MjIzMDA4MTY3ODI3MnwyMjQ5NDA3#n#MjA0NzM4OTYzNzc0OTc0MTI2NzIzODA1MzQ0ODM5ODMzOTkyMTY2MzEzNTIzNDI3#n#MDY3NDE0MDgwOTMyNDgxNzQ2MDMzNTA4NzM3NTgxNDc5MTY1MzY1MzYwNDIwOTY0#n#MDA0MjI3NjEzNjA3MzU4MTE1ODc0MDY2MDA3NTM0NjIwNjcyMjU5NTQ5MTEzMDUy#n#NTI2MjM3NjU2NDY4MTg3OTQ0OTIwNjMxODc1NjQ2OTU0MTEyOTkxNDA0OTAxNDIx#n#NTkwNDU1MDEwODA1ODM4ODE3OTUxMDgwNjY2MDI0MTkwNTQ0NTcwMTc4NTcwNTgy#n#NjUwMDkxMzg4MTI5ODg0Mjk3MzcxMTAwNzgyMzIxNzc0OTUzNjU0OTYxMDE2MDM2#n#OTE5NDI0MjQzODQ0NTMxMDA3MzYwMzg5NDUzMTQ2MjM2NjM0NTczNTk2MzgyOTk3#n#MzA0NDY2NjU1MjE1OTE4OTc0ODg3NjM3MzkwMDkwMjgxMzczNTk4NDAyNjE2MDU0#n#NDgyNzI5Mzg3MzYwMDkzNzAyODgwMDEwNjU0Nzg5MjA4MTUwMzAwOTk2NTE0MDA0#n#Njk0NzQyNzY4NzYxMjA0MDM2NDU2ODUzMDIzMDU2NjU3OTQ4NjY2NjI4MzEyNTg5#n#MjM0NjYzMTQ3NDEzOTg4ODgyNjM4OTMzMzIzODY2NjcyMzM2ODg2NzE5MjU3MTUx#n#NzU4MDQyNDYzNDM0MzMyMTQ1ODI2MzgwMTgxMjg4NTM2MTM2MjMwNDM2OTcxMzAx#n#OTUzMTE3OTIzNjcyMzQ1NzUzMjg4MjgzMzkwMDcwNTU5MQ==#";
    private static final String SAMPLE_DEC_VOTE_OPT_IDS_PROD = "92413907";
    private static final String SAMPLE_SIGNATURE = "OTgxMzYzNTc0Mjk2MzcxMjg0MTQyMzQzMjQ2MTA3ODQ5Mzc3NzYyNDI1NDIxMjg0NDY0MTUyNTczMDU2MTc4MTIzNjIwNTIzMTE5MHwtMjQyNjQ1NTkwNzk0NjE5NTE4NDI3MTA1ODM4MzEwNjA1MzcxODg2NjUzMTUxMjkxMDgyMzUwMzE5MjAxOTI5MzkwNzQ3MDU3NzkyNjk0NTk3MTYzMDU4Mzk4NTMyODA3MDMyNDcxODU1MzE0NTkwMzMwMzkzNDk4Mjc1NTYyMTA5MzQ0MTkwOTM1MTcwOTkyODU3MjQ5NjI1MjA0NTEzMTI3NTkxNzM5MjkyNDM5OTQzNDUxNTgyNDk0OTk3ODE5MDU0MzM4NDk0MTM1NDk5OTQ4OTIzMjMwMjk3NzA3NjcxMTA5NDUxNjc0OTg4NDU0MTUwNzkyMDYyNjk5MzkyOTM2ODMyNjI3MzEyMzc2ODUxMDE1ODIzMjU2NTM0MDkyNzYwNjk3MDUzMDUzMzczMDM0Nzg2MzIzNDAwOTg5MjY3MzAyNDg0MzE3NzYwMjQ5ODMxMDg2NTUwMjY0NDgxNzYyNzA5NDE4MjUyODU0MzM3OTY2MjY0NzM3NDgxMTg5Njg3MzUzODkxNDU0NzQ2MjY3OTA2OTgyNTE4Njk5NjI4MDg3MTEyMTQ1MzA3NjY1NzQwNzg3NTQ3MzA4NzM2ODMwNjgzNDIyMTQwMjk4OTg0ODQ2MTA4ODM2ODQwOTM5NTEyMTgzNDk0OTk2Njg2OTEzNzczMTI3OTM5MTI4NzMzNjQ5MjgzNjY3OTE3MzM0NDEyNDQ4MDQ3MjYzNTY3Nzg1MDM1NzIxNTE3NjM5MTM5NDQ3MjkyMjYwNjk2NzI5MDk1MjU0NDAxMjY3ODE2ODQ0OTUyNDgxOTY0ODQ1Mzk2MTM1MTI2NjQ2MTEyMDQzMDY4NzQyMDUwNTQwNzUwODcxNjg4OTM0OTk3MTY0ODM2MzMxMTAyNDg3OTM4NjUxMzY2NDMxMzUxNjE2ODYxMzYwNTYzfG51bGw=";
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
