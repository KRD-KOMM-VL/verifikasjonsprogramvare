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

import com.computas.zkpev2013.ResultsArrayList;
import com.computas.zkpev2013.ResultsList;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * Unit tests on CleansedVotesArrayList.
 */
public class CleansedVotesArrayListUnitTest {
    private static final String BROKEN_LINE = "*";
    private static final String GIVEN_SAMPLE_LINE = "MTMxOTQ3Njc3NjczMTYyMzYxNzM4MTk0Mzg1NTQ3ODEzMjEzMDI5NTIzMTgyMjQyNjkwNzM0Nzg1MTU2NjQ1NzM1NTc5Nzg3ODMzMDUzMzc4MzE3NTQyMjI1ODgyNDk4NDUyNjQyMTQ0NjI3NTg1MTQ2Mjk5MDI5MzEyNTkxMzM0MzE0MjE3NDQyNTgzNzQwMTQ4MjEzOTQ0NTA3NDM4MDk2NjQ4MDg0MDAwODM3OTQ3ODU1MDEyNTkyNDk0MjA5OTcwMjI3MjI5ODIwNzkzMDg0NzM1NzAzMjU4MzExMDU1NTU5Nzk1MDI3NjQyNDE2NDkzNzA5MjMyNDk0OTA4NTMzMTQwMDc3NzQ0MTUyNzYyNTU2Mzk0MzczMDYyNDI2NDQ0MTU4ODYxNzg1MzI2NjY1NDI4MzY5MjkwMDE1OTgwODI3NzgwNjUwNjE2ODA3NTAzNjg2MzQwNDc1NDIyMjc3OTM2MTQwNjIwMzkwNjA0NjgyMjg5MDU2OTEyMzIzNzc3NDkyNTk4NjE2NzYyNzk1MjY2MDI5OTg3NzIzODI4NDEwODExODY1MDI4OTM4MzMxMzAxNzUzMjUwMDQ1NTExMDUyMTIwNTk1MDQyMTE1MDkzODA5OTM2NzMwODE1OTI3MzQ1MzQ3NzUzNTUwNzUzOTcxMTExODIyOTU2NzEwMTU1ODAwNjA2MzQwMTI1MjQ5NzE5MTgwMjIzNDg5ODQ0MzY2OTEzMDgwNDMwMTQ4OTUxNzc2OTkxOTAxNzA4NTQ2ODQxNzE2MjQ2OTc1ODgwMTY5MDM0MzAzOTczMDY0ODE4ODU1Njd8MjAwNTYwOTE0ODEzOTg2NjI3MzU5NDEzOTk0MTA3ODgzNzExNTg3MzA3NDEzMDg0NDY0NDA2NDEzODAwOTk4NzMyMjgwNzczNjAxMzUyNjE1MzgwNjg3Nzg0OTk2NDI1MjA1MjQ3NTE3OTE5OTgwOTk3OTc3MDc2MDg1MTk3MzM3MjQ5Nzc4NTkyNDUwNzE5MTQzNDkyMTE2Mjc4NjU3MTQ1NjQzMzUzOTg3NjEwODQ4NjIwMjc2MzkxNzcwNjY0ODYyOTk5NTQ2NDYyNTgyNDgwMDQ3MzM4OTcwMTA4NDE3NDc3NjY5ODA3ODcyMjQ5MzAxMzcyMjkxMDkwMzc1NDEwMjgxODUwNDk1NTAwMDc0NTQ3NDk0Njc0NDU3NTI1MjM3ODQ1NTMwNjY4MjgxNDA0NDY2MTY5NTgyNTAzNDU2NDgxNzk3ODcxMTQzNzY3NjE0MzE1ODg4MTAwMjk1MzcxMTYzODI1NDkyODM4NzkyNTQyODEwNzEwNzg1NTM2NDMyOTgwNzU2NDU1OTU0NTc2ODY5MjU0MDIwMzU0NzMxNTI4NzA4MTg0NjYyMDkzMjY0NDU2Njk5MzQ0MjA0MTczOTQ4Mzk1ODk3MzE1OTQzNjQyOTE4NTU4ODMzMTAyMDQ4ODExNDUyNjU4MDAwODc5Nzg0MDYwMTQzOTQ4NjA3MDk5NTczNzI2NjI4NzcyNDE3ODgxNjQzMTMzNzY4NjM1NjQwNjg5MzczMjQ1NTk2NTYyOTU4OTUwOTMyOTUwNjAzNDIzMjE5MzI4MjU2OTQzMDY1NTA4NjkyOTE1OTUzNzE1OTIzNjc=#,000004,01,999902,CHANNEL_ID_UNCONTROLLED";
    private CleansedVotesList list;
    private ResultsList results;

    /**
     * Creates a CleansedVotesArrayList for the tests.
     */
    @BeforeMethod
    public void createCleansedVotesArrayList() {
        list = new CleansedVotesArrayList();
    }

    /**
     * Creates a results list for the tests.
     */
    @BeforeMethod(alwaysRun = true)
    public void createResultsList() {
        results = new ResultsArrayList();
    }

    /**
     * Verifies that the list is empty by default.
     */
    @Test
    public void mustBeEmptyByDefault() {
        assertTrue(list.isEmpty());
    }

    /**
     * Adding must not create an incident if the cleansed vote can be parsed.
     */
    @Test(enabled = false)
    public void mustNotCreateAnIncidentWhenTryingToAddANewCleansedVote() {
        list.addCleansedVoteOrCreateIncident(GIVEN_SAMPLE_LINE, results);
        assertTrue(results.isEmpty());
    }

    /**
     * Adding a broken line should create an incident.
     */
    @Test
    public void mustCreateAnIncidentWhenTryingToAddABrokenLine() {
        list.addCleansedVoteOrCreateIncident(BROKEN_LINE, results);

        assertTrue(results.contains(
                new CleansedVoteBrokenLineIncident(BROKEN_LINE)));
    }
}
