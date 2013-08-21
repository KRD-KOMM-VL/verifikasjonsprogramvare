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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;


/**
 * Integration tests against the NizkpRcgVcs class.
 */
public class NizkpRcgVcsIntegrationTest {
    private static final String RESULTS_LIST_FILE_NAME = "NizkpRcgVcsIntegrationTestResultsList.csv";
    private static final String GIVEN_ABSENT_VOTING_RECEIPT = "Absent";
    private static final String GIVEN_SAMPLE_VOTING_RECEIPT = "3fy3CmmwLn8TV4/krRcLFJu0ZvmdzyW3pg0zypdTdlI=";
    private static final String GIVEN_SAMPLE_CONTEST_ID = "000007";
    private static final String GIVEN_SAMPLE_ELECTION_ID = "01";
    private static final String GIVEN_SAMPLE_ELECTION_EVENT_ID = "730071";
    private static final String VOTING_RECEIPTS_FILE_NAME = "NizkpRcgVcs2013IntegrationTestVotingReceipts.csv";
    private static final int NO_OF_VOTING_RECEIPTS_IN_SAMPLE_VOTING_RECEIPTS_FILE =
        13;
    private static final int NO_OF_ENCRYPTED_VOTES_IN_SAMPLE_ENCRYPTED_VOTES_FILE =
        13;
    private static final String ENCRYPTED_VOTES_FILE_NAME = "NizkpRcgVcs2013IntegrationTestEncryptedVotes.csv";
    private VotingReceiptsMap votingReceiptsMap;
    private EncryptedVotesList encryptedVotesSet;

    /**
     * Verifies that when no file name for the results list is provided, no
     * writer will be set.
     */
    @Test
    public void resultsListMustHaveNoWriterIfNoFileNameSpecified() {
        NizkpRcgVcs nizkp = new NizkpRcgVcs(new String[] {
                    ENCRYPTED_VOTES_FILE_NAME, VOTING_RECEIPTS_FILE_NAME
                });
        nizkp.openResultsFileIfRequired();
        assertFalse(nizkp.getResults().hasWriter());
    }

    /**
     * Verifies that when a file name for the results list is provided, a
     * writer will be set.
     */
    @Test
    public void resultsListMustHaveAWriterIfAFileNameIsSpecified() {
        NizkpRcgVcs nizkp = new NizkpRcgVcs(new String[] {
                    ENCRYPTED_VOTES_FILE_NAME, VOTING_RECEIPTS_FILE_NAME,
                    RESULTS_LIST_FILE_NAME
                });
        nizkp.openResultsFileIfRequired();
        assertTrue(nizkp.getResults().hasWriter());
    }

    /**
     * Creates a NizkpRcgVcs object and loads the voting receipts from the sample file.
     * @throws IOException Thrown if something goes wrong while loading the voting receipts file.
     */
    @BeforeMethod(groups = "NizkpRcgVcsVotingReceiptsIntegration")
    public void createNizkpAndLoadVotingReceipts() throws IOException {
        NizkpRcgVcs nizkp = new NizkpRcgVcs(new String[] {
                    ENCRYPTED_VOTES_FILE_NAME, VOTING_RECEIPTS_FILE_NAME
                });
        votingReceiptsMap = nizkp.loadVotingReceipts();
    }

    /**
     * Creates a NizkpRcgVcs object and loads the encrypted votes the sample file.
     * @throws IOException Thrown if something goes wrong while loading the encrypted votes from the sample file.
     */
    @BeforeMethod(groups = "NizkpRcgVcsEncryptedVotesIntegration")
    public void createNizkpAndLoadEncryptedVotes() throws IOException {
        NizkpRcgVcs nizkp = new NizkpRcgVcs(new String[] {
                    ENCRYPTED_VOTES_FILE_NAME, VOTING_RECEIPTS_FILE_NAME
                });
        encryptedVotesSet = nizkp.loadEncryptedVotes();
    }

    /**
     * Verifies that the correct number of voting receipts are loaded from the voting receipts file.
     */
    @Test(groups = "NizkpRcgVcsVotingReceiptsIntegration")
    public void mustLoadTheCorrectNumberOfVotingReceipts() {
        assertEquals(votingReceiptsMap.size(),
            NO_OF_VOTING_RECEIPTS_IN_SAMPLE_VOTING_RECEIPTS_FILE);
    }

    /**
     * Verifies that the correct number of encrypted votes are loaded from the file.
     */
    @Test(groups = "NizkpRcgVcsEncryptedVotesIntegration")
    public void mustLoadTheCorrectNumberOfEncryptedVotes() {
        assertEquals(encryptedVotesSet.size(),
            NO_OF_ENCRYPTED_VOTES_IN_SAMPLE_ENCRYPTED_VOTES_FILE);
    }

    /**
     * Verifies that when the voting receipts are loaded, a given sample voting receipt is present.
     */
    @Test(groups = "NizkpRcgVcsVotingReceiptsIntegration")
    public void votingReceiptsMustContainGivenSampleVotingReceiptAfterLoading() {
        assertTrue(votingReceiptsMap.containsVotingReceipt(
                GIVEN_SAMPLE_VOTING_RECEIPT, GIVEN_SAMPLE_CONTEST_ID,
                GIVEN_SAMPLE_ELECTION_ID, GIVEN_SAMPLE_ELECTION_EVENT_ID));
    }

    /**
     * Verifies that when the voting receipts are loaded, a given absent voting receipt is not present.
     */
    @Test(groups = "NizkpRcgVcsVotingReceiptsIntegration")
    public void votingReceiptsMustNotContainAGivenAbsentVotingReceiptAfterLoading() {
        assertFalse(votingReceiptsMap.containsVotingReceipt(
                GIVEN_ABSENT_VOTING_RECEIPT, GIVEN_SAMPLE_CONTEST_ID,
                GIVEN_SAMPLE_ELECTION_ID, GIVEN_SAMPLE_ELECTION_EVENT_ID));
    }

    /**
     * Verifies that when the voting receipts are loaded, it is possible to retrieve the
     * voting receipt for a given voting receipt, and the voting receipt has the correct voting
     * receipt.
     */
    @Test(groups = "NizkpRcgVcsVotingReceiptsIntegration")
    public void canRetrieveAGivenSampleVotingReceiptAfterLoadingAndItHasTheCorrectVotingReceipt() {
        assertEquals(votingReceiptsMap.get(GIVEN_SAMPLE_VOTING_RECEIPT,
                GIVEN_SAMPLE_CONTEST_ID, GIVEN_SAMPLE_ELECTION_ID,
                GIVEN_SAMPLE_ELECTION_EVENT_ID).getVotingReceipt()
                                      .getVotingReceipt(),
            GIVEN_SAMPLE_VOTING_RECEIPT);
    }

    /**
     * Verifies that when the voting receipts are loaded, it is possible to retrieve the
     * RCG voting receipt for a given voting receipt, and the voting receipt has the correct contest ID.
     */
    @Test(groups = "NizkpRcgVcsVotingReceiptsIntegration")
    public void canRetrieveAGivenSampleVotingReceiptAfterLoadingAndItHasTheCorrectContestId() {
        assertEquals(votingReceiptsMap.get(GIVEN_SAMPLE_VOTING_RECEIPT,
                GIVEN_SAMPLE_CONTEST_ID, GIVEN_SAMPLE_ELECTION_ID,
                GIVEN_SAMPLE_ELECTION_EVENT_ID).getVotingReceipt().getContestId(),
            GIVEN_SAMPLE_CONTEST_ID);
    }

    /**
     * Verifies that when the voting receipts are loaded, it is possible to retrieve the
     * RCG voting receipt for a given voting receipt, and the voting receipt has the correct election ID.
     */
    @Test(groups = "NizkpRcgVcsVotingReceiptsIntegration")
    public void canRetrieveAGivenSampleVotingReceiptAfterLoadingAndItHasTheCorrectElectionId() {
        assertEquals(votingReceiptsMap.get(GIVEN_SAMPLE_VOTING_RECEIPT,
                GIVEN_SAMPLE_CONTEST_ID, GIVEN_SAMPLE_ELECTION_ID,
                GIVEN_SAMPLE_ELECTION_EVENT_ID).getVotingReceipt()
                                      .getElectionId(), GIVEN_SAMPLE_ELECTION_ID);
    }

    /**
     * Verifies that when the voting receipts are loaded, it is possible to retrieve the
     * RCG voting receipt for a given voting receipt, and the voting receipt has the correct election event ID.
     */
    @Test(groups = "NizkpRcgVcsVotingReceiptsIntegration")
    public void canRetrieveAGivenSampleVotingReceiptAfterLoadingAndItHasTheCorrectElectionEventId() {
        assertEquals(votingReceiptsMap.get(GIVEN_SAMPLE_VOTING_RECEIPT,
                GIVEN_SAMPLE_CONTEST_ID, GIVEN_SAMPLE_ELECTION_ID,
                GIVEN_SAMPLE_ELECTION_EVENT_ID).getVotingReceipt()
                                      .getElectionEventId(),
            GIVEN_SAMPLE_ELECTION_EVENT_ID);
    }
}
