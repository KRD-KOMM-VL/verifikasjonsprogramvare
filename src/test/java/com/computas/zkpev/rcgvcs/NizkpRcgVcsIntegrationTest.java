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
package com.computas.zkpev.rcgvcs;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;


/**
 * Integration tests against the NizkpRcgVcs class.
 *
 *
 */
public class NizkpRcgVcsIntegrationTest {
    private static final String RESULTS_LIST_FILE_NAME = "NizkpRcgVcsIntegrationTestResultsList.csv";
    private static final String GIVEN_ABSENT_VOTING_RECEIPT = "Absent";
    private static final String GIVEN_SAMPLE_VOTING_RECEIPT = "a01OQllwQjlPNVcrUnk1bFFvYVRoTS9XUStBPQ==";
    private static final String GIVEN_SAMPLE_CONTEST_ID = "000106";
    private static final String GIVEN_SAMPLE_ELECTION_ID = "06";
    private static final String GIVEN_SAMPLE_ELECTION_EVENT_ID = "200701";
    private static final String RCG_FILE_NAME = "NizkpRcgVcsIntegrationTestRcgVotingReceipts.csv";
    private static final int NO_OF_VOTING_RECEIPTS_IN_SAMPLE_RCG_VOTING_RECEIPTS_FILE =
        16;
    private static final int NO_OF_ENCRYPTED_VOTES_IN_SAMPLE_VCS_FILE = 16;
    private static final String VCS_FILE_NAME = "NizkpRcgVcsIntegrationTestVcsEncryptedVotes.csv";
    private RcgVotingReceiptsMap rcgVotingReceiptsMap;
    private VcsEncryptedVotesList vcsEncryptedVotesSet;

    /**
     * Verifies that when no file name for the results list is provided, no
     * writer will be set.
     */
    @Test
    public void resultsListMustHaveNoWriterIfNoFileNameSpecified() {
        NizkpRcgVcs nizkp = new NizkpRcgVcs(new String[] {
                    RCG_FILE_NAME, VCS_FILE_NAME
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
                    RCG_FILE_NAME, VCS_FILE_NAME, RESULTS_LIST_FILE_NAME
                });
        nizkp.openResultsFileIfRequired();
        assertTrue(nizkp.getResults().hasWriter());
    }

    /**
     * Creates a NizkpRcgVcs object and loads the RCG voting receipts from the sample file.
     * @throws IOException Thrown if something goes wrong while loading the RCG hashes file.
     */
    @BeforeMethod(groups = "NizkpRcgVcsRcgVotingReceiptsIntegration")
    public void createNizkpAndLoadRcgVotingReceipts() throws IOException {
        NizkpRcgVcs nizkp = new NizkpRcgVcs(new String[] {
                    RCG_FILE_NAME, VCS_FILE_NAME
                });
        rcgVotingReceiptsMap = nizkp.loadRcgVotingReceipts();
    }

    /**
     * Creates a NizkpRcgVcs object and loads the encrypted votes from the VCS from the sample file.
     * @throws IOException Thrown if something goes wrong while loading the encrypted votes from the VCS from the sample file.
     */
    @BeforeMethod(groups = "NizkpRcgVcsVcsEncryptedVotesIntegration")
    public void createNizkpAndLoadVcsEncryptedVotes() throws IOException {
        NizkpRcgVcs nizkp = new NizkpRcgVcs(new String[] {
                    RCG_FILE_NAME, VCS_FILE_NAME
                });
        vcsEncryptedVotesSet = nizkp.loadVcsEncryptedVotes();
    }

    /**
     * Verifies that the correct number of RCG voting receipts are loaded from the RCG voting receipts file.
     */
    @Test(groups = "NizkpRcgVcsRcgVotingReceiptsIntegration")
    public void mustLoadTheCorrectNumberOfRcgVotingReceipts() {
        assertEquals(rcgVotingReceiptsMap.size(),
            NO_OF_VOTING_RECEIPTS_IN_SAMPLE_RCG_VOTING_RECEIPTS_FILE);
    }

    /**
     * Verifies that the correct number of encrypted votes from the VCS are loaded from the file.
     */
    @Test(groups = "NizkpRcgVcsVcsEncryptedVotesIntegration")
    public void mustLoadTheCorrectNumberOfVcsEncryptedVotes() {
        assertEquals(vcsEncryptedVotesSet.size(),
            NO_OF_ENCRYPTED_VOTES_IN_SAMPLE_VCS_FILE);
    }

    /**
     * Verifies that when the RCG voting receipts are loaded, a given sample voting receipt is present.
     */
    @Test(groups = "NizkpRcgVcsRcgVotingReceiptsIntegration")
    public void rcgVotingReceiptsMustContainGivenSampleVotingReceiptAfterLoading() {
        assertTrue(rcgVotingReceiptsMap.containsVotingReceipt(
                GIVEN_SAMPLE_VOTING_RECEIPT, GIVEN_SAMPLE_CONTEST_ID,
                GIVEN_SAMPLE_ELECTION_ID, GIVEN_SAMPLE_ELECTION_EVENT_ID));
    }

    /**
     * Verifies that when the RCG voting receipts are loaded, a given absent voting receipt is not present.
     */
    @Test(groups = "NizkpRcgVcsRcgVotingReceiptsIntegration")
    public void rcgVotingReceiptsMustNotContainAGivenAbsentVotingReceiptAfterLoading() {
        assertFalse(rcgVotingReceiptsMap.containsVotingReceipt(
                GIVEN_ABSENT_VOTING_RECEIPT, GIVEN_SAMPLE_CONTEST_ID,
                GIVEN_SAMPLE_ELECTION_ID, GIVEN_SAMPLE_ELECTION_EVENT_ID));
    }

    /**
     * Verifies that when the RCG voting receipts are loaded, it is possible to retrieve the
     * RCG voting receipt for a given voting receipt, and the RCG voting receipt has the correct voting
     * receipt.
     */
    @Test(groups = "NizkpRcgVcsRcgVotingReceiptsIntegration")
    public void canRetrieveAGivenSampleVotingReceiptAfterLoadingAndItHasTheCorrectVotingReceipt() {
        assertEquals(rcgVotingReceiptsMap.get(GIVEN_SAMPLE_VOTING_RECEIPT,
                GIVEN_SAMPLE_CONTEST_ID, GIVEN_SAMPLE_ELECTION_ID,
                GIVEN_SAMPLE_ELECTION_EVENT_ID).getRcgVotingReceipt()
                                         .getVotingReceipt(),
            GIVEN_SAMPLE_VOTING_RECEIPT);
    }

    /**
     * Verifies that when the RCG voting receipts are loaded, it is possible to retrieve the
     * RCG voting receipt for a given voting receipt, and the RCG voting receipt has the correct contest ID.
     */
    @Test(groups = "NizkpRcgVcsRcgVotingReceiptsIntegration")
    public void canRetrieveAGivenSampleVotingReceiptAfterLoadingAndItHasTheCorrectContestId() {
        assertEquals(rcgVotingReceiptsMap.get(GIVEN_SAMPLE_VOTING_RECEIPT,
                GIVEN_SAMPLE_CONTEST_ID, GIVEN_SAMPLE_ELECTION_ID,
                GIVEN_SAMPLE_ELECTION_EVENT_ID).getRcgVotingReceipt()
                                         .getContestId(),
            GIVEN_SAMPLE_CONTEST_ID);
    }

    /**
     * Verifies that when the RCG voting receipts are loaded, it is possible to retrieve the
     * RCG voting receipt for a given voting receipt, and the RCG voting receipt has the correct election ID.
     */
    @Test(groups = "NizkpRcgVcsRcgVotingReceiptsIntegration")
    public void canRetrieveAGivenSampleVotingReceiptAfterLoadingAndItHasTheCorrectElectionId() {
        assertEquals(rcgVotingReceiptsMap.get(GIVEN_SAMPLE_VOTING_RECEIPT,
                GIVEN_SAMPLE_CONTEST_ID, GIVEN_SAMPLE_ELECTION_ID,
                GIVEN_SAMPLE_ELECTION_EVENT_ID).getRcgVotingReceipt()
                                         .getElectionId(),
            GIVEN_SAMPLE_ELECTION_ID);
    }

    /**
     * Verifies that when the RCG voting receipts are loaded, it is possible to retrieve the
     * RCG voting receipt for a given voting receipt, and the RCG voting receipt has the correct election event ID.
     */
    @Test(groups = "NizkpRcgVcsRcgVotingReceiptsIntegration")
    public void canRetrieveAGivenSampleVotingReceiptAfterLoadingAndItHasTheCorrectElectionEventId() {
        assertEquals(rcgVotingReceiptsMap.get(GIVEN_SAMPLE_VOTING_RECEIPT,
                GIVEN_SAMPLE_CONTEST_ID, GIVEN_SAMPLE_ELECTION_ID,
                GIVEN_SAMPLE_ELECTION_EVENT_ID).getRcgVotingReceipt()
                                         .getElectionEventId(),
            GIVEN_SAMPLE_ELECTION_EVENT_ID);
    }
}
