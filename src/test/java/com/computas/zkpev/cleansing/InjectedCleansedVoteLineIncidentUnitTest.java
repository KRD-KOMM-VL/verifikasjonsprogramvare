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

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * Unit tests on InjectedCleansedEncryptedVoteLineIncident.
 *
 */
public class InjectedCleansedVoteLineIncidentUnitTest {
    private static final String COMMA = ",";
    private static final String ENC_OPT_IDS = "MTMxOTQ3Njc3NjczMTYyMzYxNzM4MTk0Mzg1NTQ3ODEzMjEzMDI5NTIzMTgyMjQyNjkwNzM0Nzg1MTU2NjQ1NzM1NTc5Nzg3ODMzMDUzMzc4MzE3NTQyMjI1ODgyNDk4NDUyNjQyMTQ0NjI3NTg1MTQ2Mjk5MDI5MzEyNTkxMzM0MzE0MjE3NDQyNTgzNzQwMTQ4MjEzOTQ0NTA3NDM4MDk2NjQ4MDg0MDAwODM3OTQ3ODU1MDEyNTkyNDk0MjA5OTcwMjI3MjI5ODIwNzkzMDg0NzM1NzAzMjU4MzExMDU1NTU5Nzk1MDI3NjQyNDE2NDkzNzA5MjMyNDk0OTA4NTMzMTQwMDc3NzQ0MTUyNzYyNTU2Mzk0MzczMDYyNDI2NDQ0MTU4ODYxNzg1MzI2NjY1NDI4MzY5MjkwMDE1OTgwODI3NzgwNjUwNjE2ODA3NTAzNjg2MzQwNDc1NDIyMjc3OTM2MTQwNjIwMzkwNjA0NjgyMjg5MDU2OTEyMzIzNzc3NDkyNTk4NjE2NzYyNzk1MjY2MDI5OTg3NzIzODI4NDEwODExODY1MDI4OTM4MzMxMzAxNzUzMjUwMDQ1NTExMDUyMTIwNTk1MDQyMTE1MDkzODA5OTM2NzMwODE1OTI3MzQ1MzQ3NzUzNTUwNzUzOTcxMTExODIyOTU2NzEwMTU1ODAwNjA2MzQwMTI1MjQ5NzE5MTgwMjIzNDg5ODQ0MzY2OTEzMDgwNDMwMTQ4OTUxNzc2OTkxOTAxNzA4NTQ2ODQxNzE2MjQ2OTc1ODgwMTY5MDM0MzAzOTczMDY0ODE4ODU1Njd8MjAwNTYwOTE0ODEzOTg2NjI3MzU5NDEzOTk0MTA3ODgzNzExNTg3MzA3NDEzMDg0NDY0NDA2NDEzODAwOTk4NzMyMjgwNzczNjAxMzUyNjE1MzgwNjg3Nzg0OTk2NDI1MjA1MjQ3NTE3OTE5OTgwOTk3OTc3MDc2MDg1MTk3MzM3MjQ5Nzc4NTkyNDUwNzE5MTQzNDkyMTE2Mjc4NjU3MTQ1NjQzMzUzOTg3NjEwODQ4NjIwMjc2MzkxNzcwNjY0ODYyOTk5NTQ2NDYyNTgyNDgwMDQ3MzM4OTcwMTA4NDE3NDc3NjY5ODA3ODcyMjQ5MzAxMzcyMjkxMDkwMzc1NDEwMjgxODUwNDk1NTAwMDc0NTQ3NDk0Njc0NDU3NTI1MjM3ODQ1NTMwNjY4MjgxNDA0NDY2MTY5NTgyNTAzNDU2NDgxNzk3ODcxMTQzNzY3NjE0MzE1ODg4MTAwMjk1MzcxMTYzODI1NDkyODM4NzkyNTQyODEwNzEwNzg1NTM2NDMyOTgwNzU2NDU1OTU0NTc2ODY5MjU0MDIwMzU0NzMxNTI4NzA4MTg0NjYyMDkzMjY0NDU2Njk5MzQ0MjA0MTczOTQ4Mzk1ODk3MzE1OTQzNjQyOTE4NTU4ODMzMTAyMDQ4ODExNDUyNjU4MDAwODc5Nzg0MDYwMTQzOTQ4NjA3MDk5NTczNzI2NjI4NzcyNDE3ODgxNjQzMTMzNzY4NjM1NjQwNjg5MzczMjQ1NTk2NTYyOTU4OTUwOTMyOTUwNjAzNDIzMjE5MzI4MjU2OTQzMDY1NTA4NjkyOTE1OTUzNzE1OTIzNjc=#";
    private static final String CONTEST_ID = "000004";
    private static final String ELECTION_ID = "01";
    private static final String ELECTION_EVENT_ID = "999902";
    private static final String CLEANSED_VOTE_LINE = ENC_OPT_IDS + COMMA +
        CONTEST_ID + COMMA + ELECTION_ID + COMMA + ELECTION_EVENT_ID +
        ",CHANNEL_ID_UNCONTROLLED";
    private static final String OTHER_CLEANSED_VOTE_LINE = "OTMwMTA3MDUxNzM0MTMwMjE1MjQ4OTM3MzkwODU5NTY3NTE2MjEzNzMxNzE2MjE0OTc3NzEyMDk3MzgyNjA2MDIwMDIwODI2OTgxNzI5NDQ2MDAwMTgwOTA0NTAyOTY2ODE3ODM5ODQyOTExODkzNzM5ODYzNTI5Nzk2ODY2Nzg3MzUxNzI2MTk4OTM4MDI4MDU5MzgxMTM5MTAzNDg2MTUzNDczOTk2NTU2MTc4NTgzNjY0OTUwNjI5NTc2NTc3NzI2MjI1MzI3NzM4MTA4NjA2NTkzMTA5NTA0MDI4NDUyNzI0NDY5NTM4MDQwODU1NjAwODYxMjYwOTkzMzcwNzIyMTM2NTQ5NzMyMTUwOTA2ODg4ODI5NDUwMzUxNjczNjkxODgyNTQ4ODE4MzkyMTY3OTEyOTg0MDA3ODg2NTU4ODUyNjg4MDIwMjI0NDA1OTIxNjQ3MzMzNDA2NDI1OTMzNzk4Mjg1OTIxMDA2ODM5Mjc4MzY2MjYyMjAzOTE2NzExNjU0ODMyMDU3NzMwODA1NDM0Nzg1MzgyOTQ4MDE3NTc1MjcwNjE4NDM4MzgzMzc3ODc0MzQ5MzY4OTAzOTg2NzQ2MjI4NDM3NTg5OTQwOTc3NDMwNzc5MTQ1MDYxNjI3MDA4MDUwNzkwNjUwMTYzMDMzNTA3NzAyNDk2MDAzNzk5NjE1MjU1MzQyOTczMTg3ODg2NTEzMTE3MzE4NjcwODgzOTAzNTIwMTI4MzkwNzE1MTQwMTEyNzIxNDA3NjI2NjI5NzgyNzA3MjM4NDk5MzA5MjU5NjgwNzkwMDk1ODY4NDkwfDMwMzE0MzM3MTgyNDQ5NjQwNjk4OTk1MTMyMTM3NTk4MTQyNjM4MTMzNzk0NjkzODYwMDYwOTExODE3OTA4MDgzNTg2NjkzODkzOTA3MzY4Njk5OTkzMTMyOTA0NzI0NTI3NDk5Njg4NzQ2NjEwNDcxMTU5NjYyNjk1NjE5Njk4MDgyNTAxNjI0MTQ5ODg5MjIxMTgxMTUyMzkxNzIwNDc5MDE5NjEzODA5ODU0NjQyNzk1NDkwNzI5Nzc2MDYyMTk5MDg3NjA1MTcwMjU4NzMzODE4NTE1Nzc3ODczNDY3NTY4MTkzMDg3NzU0OTk4MjQ0MDc1Mjg2OTc5MDA2NjcyODc3NjI2NDk1NjkzNjIxNjkwNzk5NTkwNTkyNjQxMzM2MjA2NjkzNzQyNDU5MDI4Mzk2MjM0MDQ4MzU1MzI3MjQzODkxMTY3OTY1ODY5Nzc4NTYwNTY3MTc4Mjg0NDIxNTU5MDQyNDI2NjcwNzQyOTE1ODg1NjYzOTI2MDE4Nzk3MDE4NjQ1MjA0OTA4MTgyMzEwNDU5Mzc5Njc0ODQyNzU2NzY3NjYwODE3MjExOTIzNTU2NjUyMjkxOTgxMTI2NDI4NzI0NTYyNzU2Njg3NzM1OTExODYyMzU5ODQ0MTc0MTY0NzY5MzAzNDI2MjE1Nzc2ODcyNDk2Mzk3MDA4Mjg4NDE2Njg4ODAxNTE2MjY2MzcxNDk2MzY0Mzg1MjUwMjE1MTMxNzIyMzMwMDMxNzcxMjcyNDI2MjU1NDk4NTgzNzU5ODY2NTM2NzYwNTAxMTIyNzI2ODk3NjEwOTU0MTE4OTI0Mzcz#,000004,01,666601,CHANNEL_ID_UNCONTROLLED";
    private InjectedCleansedVoteLineIncident incident;

    /**
    * Creates an incident to test against.
    */
    @BeforeMethod
    public void createInjectedCleansedVoteLineIncident() {
        incident = new InjectedCleansedVoteLineIncident(new CleansedVote(
                    CLEANSED_VOTE_LINE));
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
     * Verifies that the incident is equal to another incident with the same cleansed vote.
     */
    @Test
    public void mustBeEqualToAnotherInjectedCleansedVoteLineIncidentWithTheSameCleansedVote() {
        assertEquals(incident,
            new InjectedCleansedVoteLineIncident(
                new CleansedVote(CLEANSED_VOTE_LINE)));
    }

    /**
     * Verifies that the incident has the same hashCode as another incident with the same cleansed vote.
     */
    @Test
    public void mustHaveSameHashCodeAsAnotherInjectedCleansedVoteLineIncidentWithTheSameCleansedVote() {
        assertEquals(incident.hashCode(),
            new InjectedCleansedVoteLineIncident(
                new CleansedVote(CLEANSED_VOTE_LINE)).hashCode());
    }

    /**
     * Verifies that the incident is not equal to another incident with another cleansed vote.
     */
    @Test
    public void mustNotBeEqualToAnotherInjectedCleansedVoteLineIncidentWithAnotherCleansedVote() {
        assertFalse(incident.equals(
                new InjectedCleansedVoteLineIncident(
                    new CleansedVote(OTHER_CLEANSED_VOTE_LINE))));
    }

    /**
     * Verifies that toString produced a comma-separated line with the correct
     * values.
     */
    @Test
    public void toStringMustBeCommaSeparatedline() {
        assertEquals(incident.toString(),
            "InjectedCleansedVoteLineIncident," + ENC_OPT_IDS + COMMA +
            CONTEST_ID + COMMA + ELECTION_ID + COMMA + ELECTION_EVENT_ID);
    }
}
