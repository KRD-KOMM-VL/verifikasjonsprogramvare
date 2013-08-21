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
package com.computas.zkpev2013.rcgvcs;

import com.computas.zkpev2013.ResultsArrayList;
import com.computas.zkpev2013.ResultsList;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * Unit tests against the VotingReceiptsHashMap class.
 */
public class VotingReceiptsHashMapUnitTest {
    private static final String GIVEN_ABSENT_VOTING_RECEIPT = "Absent";
    private static final String COMMA = ",";
    private static final String GIVEN_VOTING_RECEIPT = "3fy3CmmwLn8TV4/krRcLFJu0ZvmdzyW3pg0zypdTdlI=";
    private static final String GIVEN_CONTEST_ID = "000007";
    private static final String GIVEN_ELECTION_ID = "01";
    private static final String GIVEN_ELECTION_EVENT_ID = "730071";
    private static final String GIVEN_SAMPLE_LINE_BODY = "8a84806e40521c6d01405224937601d5,25ee33bf-7f7f-45ad-b495-9b1d8fce3eaf,rO0ABXNyAD1jb20uc2N5dGwuZXZvdGUucHJvdG9jb2wuaW50ZWdyYXRpb24udm90aW5nLm1vZGVsLlJlY2VpcHRCZWFuw7/82u7t7McCAApKAAZfcmNnSURKAApfdGltZXN0YW1wSgAGX3Zjc0lETAAKX2NvbnRlc3RJZHQAEkxqYXZhL2xhbmcvU3RyaW5nO0wAEF9lbGVjdGlvbkV2ZW50SWRxAH4AAUwAC19lbGVjdGlvbklkcQB+AAFbABRfcmVjZWlwdFNpZ25hdHVyZVJDR3QAAltCWwAUX3JlY2VpcHRTaWduYXR1cmVWQ1NxAH4AAkwAE19yZXR1cm5Db2Rlc01lc3NhZ2VxAH4AAVsADl92b3RpbmdSZWNlaXB0cQB+AAJ4cAAAAAAAAB+cAAABQFIki9AAAAAAAAAAAHQABjAwMDAwN3QABjczMDA3MXQAAjAxdXIAAltCrPMX+AYIVOACAAB4cAAAAQCB9Gqa5CPip7k7xEHfLMni3L1eRPr9bTTaGN+acMcIF8J/LS8sUNR7XEnOT7/FUbgdbtKLSMnyK4qhYuwAFK+3eKox4aStQMlaAIy8NIhkAzDLRz0tI/iRiwk3Ox9Nyl0VaQbKpvXNjw8KLVLt+DUmHBm3J1wgJLdKn4LEguxKZtYygJ4oN00O8W0d4Oixw51DePSuM9VVhxiS34/dXBFX9pJTaZzqQN+23kCdR5n5S2eY/MvsZhVYgXMoY7ZqR56Z9TsMh3eFbLEb1jm2GHwXPXOGmdJlqYSFbwk7xR07zKWmLhaBXCBRmtxfSru+VkGXhOMLpeKu3Nfy61KDW5andXEAfgAHAAABAL29eS1PHxwhRnLT8IJbfGXTosIsxoRRb1nZLBVoN3cDESv7mRBeJNCIFrq1Lw1sQaTA2jde+wmXVzhNC+dPkdWoO0y9zdyDTreAfDdWw5tjrxWDpJCBl4v/Wx2rxJSQ3uc6r3Zbg9nuG/HlU5zwF8AnzWYC4Nb2hwqvEiPy8+p32EfM5h2JeqFpG8fDz0/IQAc0i78COKyHzLGThFPlIqAcK4QPuYVaWJ59KiAMEDlSD/Iqu08DdQK3if+ok4LKBdYBO5YKvnmTGR05qhB8d4TCIE4s7rW8xx9xn3mhi8xgwGv8ZngwZCZNWzT6+SMR7DRddBO2fiT7j0+AM2fBDeBwdXEAfgAHAAAALDNmeTNDbW13TG44VFY0L2tyUmNMRkp1MFp2bWR6eVczcGcwenlwZFRkbEk9,3e287863-6cb6-474a-be78-6e91ffba8420,8092,";
    private static final String GIVEN_SAMPLE_LINE = GIVEN_SAMPLE_LINE_BODY +
        GIVEN_VOTING_RECEIPT + COMMA + GIVEN_CONTEST_ID + COMMA +
        GIVEN_ELECTION_ID + COMMA + GIVEN_ELECTION_EVENT_ID;
    private static final String SAMPLE_LINE_WITH_OTHER_VOTING_RECEIPT_VALUE = GIVEN_SAMPLE_LINE_BODY +
        "OtherVotingReceiptValue" + COMMA + GIVEN_CONTEST_ID + COMMA +
        GIVEN_ELECTION_ID + COMMA + GIVEN_ELECTION_EVENT_ID;
    private static final String SAMPLE_LINE_WITH_OTHER_VOTING_CONTEST_ID_VALUE = GIVEN_SAMPLE_LINE_BODY +
        GIVEN_VOTING_RECEIPT + COMMA + "999999" + COMMA + GIVEN_ELECTION_ID +
        COMMA + GIVEN_ELECTION_EVENT_ID;
    private static final String SAMPLE_LINE_WITH_OTHER_ELECTION_ID_VALUE = GIVEN_SAMPLE_LINE_BODY +
        GIVEN_VOTING_RECEIPT + COMMA + GIVEN_CONTEST_ID + COMMA + "99" + COMMA +
        GIVEN_ELECTION_EVENT_ID;
    private static final String SAMPLE_LINE_WITH_OTHER_ELECTION_EVENT_ID_VALUE = GIVEN_SAMPLE_LINE_BODY +
        GIVEN_VOTING_RECEIPT + COMMA + GIVEN_CONTEST_ID + COMMA +
        GIVEN_ELECTION_ID + COMMA + "999999";
    private static final String BROKEN_LINE = "*";
    private VotingReceiptsMap map;
    private ResultsList results;

    /**
     * Creates a VotingReceiptsHashMap for the tests.
     */
    @BeforeMethod(alwaysRun = true)
    public void createVotingReceiptsHashMap() {
        map = new VotingReceiptsHashMap();
    }

    /**
     * Creates a results list for the tests.
     */
    @BeforeMethod(alwaysRun = true)
    public void createResultsList() {
        results = new ResultsArrayList();
    }

    /**
     * Verifies that the map is empty by default.
     */
    @Test
    public void mustBeEmptyByDefault() {
        assertTrue(map.isEmpty());
    }

    /**
     * Verifies that the map does not contain a given sample voting receipt by default.
     */
    @Test
    public void mustNotContainAGivenSampleVotingReceiptByDefault() {
        assertFalse(map.containsVotingReceipt(GIVEN_VOTING_RECEIPT,
                GIVEN_CONTEST_ID, GIVEN_ELECTION_ID, GIVEN_ELECTION_EVENT_ID));
    }

    /**
     * Adding must not create an incident if the voting receipt to be added didn't already exist in the map.
     */
    @Test
    public void mustNotCreateAnIncidentWhenTryingToAddANewVotingReceipt() {
        map.addVotingReceiptOrAddIncident(GIVEN_SAMPLE_LINE, results);

        assertTrue(results.isEmpty());
    }

    /**
     * Adding a broken line should create an incident.
     */
    @Test
    public void mustCreateAnIncidentWhenTryingToAddABrokenLine() {
        map.addVotingReceiptOrAddIncident(BROKEN_LINE, results);
        assertTrue(results.contains(
                new VotingReceiptBrokenLineIncident(BROKEN_LINE)));
    }

    /**
     * Adding must create an incident if the voting receipt to be added already existed in the map.
     */
    @Test
    public void mustCreateAnIncidentWhenTryingToAddAVotingReceiptForTheSecondTime() {
        map.addVotingReceiptOrAddIncident(GIVEN_SAMPLE_LINE, results);
        map.addVotingReceiptOrAddIncident(GIVEN_SAMPLE_LINE, results);
        assertTrue(results.contains(
                new DoubleOccurrenceOfVotingReceiptIncident(
                    new VotingReceipt(GIVEN_SAMPLE_LINE))));
    }

    /**
     * Adding must not create an incident if the voting receipt value is different.
     */
    @Test
    public void mustNotCreateAnIncidentWhenTryingToAddAVotingReceiptWithADifferentVotingReceiptValue() {
        map.addVotingReceiptOrAddIncident(GIVEN_SAMPLE_LINE, results);
        map.addVotingReceiptOrAddIncident(SAMPLE_LINE_WITH_OTHER_VOTING_RECEIPT_VALUE,
            results);

        assertTrue(results.isEmpty());
    }

    /**
     * Adding must not create an incident if the contest ID is different.
     */
    @Test
    public void mustNotCreateAnIncidentWhenTryingToAddAVotingReceiptWithADifferentContestId() {
        map.addVotingReceiptOrAddIncident(GIVEN_SAMPLE_LINE, results);
        map.addVotingReceiptOrAddIncident(SAMPLE_LINE_WITH_OTHER_VOTING_CONTEST_ID_VALUE,
            results);

        assertTrue(results.isEmpty());
    }

    /**
     * Adding must not create an incident if the election ID is different.
     */
    @Test
    public void mustNotCreateAnIncidentWhenTryingToAddAVotingReceiptWithADifferentElectionId() {
        map.addVotingReceiptOrAddIncident(GIVEN_SAMPLE_LINE, results);
        map.addVotingReceiptOrAddIncident(SAMPLE_LINE_WITH_OTHER_ELECTION_ID_VALUE,
            results);

        assertTrue(results.isEmpty());
    }

    /**
     * Adding must not create an incident if the election event ID is different.
     */
    @Test
    public void mustNotCreateAnIncidentWhenTryingToAddAVotingReceiptWithADifferentElectionEventId() {
        map.addVotingReceiptOrAddIncident(GIVEN_SAMPLE_LINE, results);
        map.addVotingReceiptOrAddIncident(SAMPLE_LINE_WITH_OTHER_ELECTION_EVENT_ID_VALUE,
            results);

        assertTrue(results.isEmpty());
    }

    /**
     * Verifies that the map contains a hash after it has been added.
     */
    @Test
    public void mustContainAGivenSampleVotingReceiptAfterAddingIt() {
        map.addVotingReceiptOrAddIncident(GIVEN_SAMPLE_LINE, results);
        assertTrue(map.containsVotingReceipt(GIVEN_VOTING_RECEIPT,
                GIVEN_CONTEST_ID, GIVEN_ELECTION_ID, GIVEN_ELECTION_EVENT_ID));
    }

    /**
     * Verifies that the map does not contain a hash that hasn't been added.
     */
    @Test
    public void mustNotContainAGivenAbsentVotingReceiptAfterAddingTheGivenVotingReceipt() {
        map.addVotingReceiptOrAddIncident(GIVEN_SAMPLE_LINE, results);
        assertFalse(map.containsVotingReceipt(GIVEN_ABSENT_VOTING_RECEIPT,
                GIVEN_CONTEST_ID, GIVEN_ELECTION_ID, GIVEN_ELECTION_EVENT_ID));
    }
}
