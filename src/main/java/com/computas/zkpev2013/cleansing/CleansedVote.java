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

import com.computas.zkpev2013.CsvLineParseable;
import com.computas.zkpev2013.ElGamalEncryptionPair;


/**
 * Class representing a cleansed vote from the list of cleansed votes.
 */
public class CleansedVote extends CsvLineParseable {
    private String contestId;
    private String electionId;
    private String electionEventId;
    private ElGamalEncryptionPair encryptedVoteOptIds;
    private String encryptedVoteOptIdsAsString;

    CleansedVote(String line) {
        super(line);
    }

    @Override
    protected void setAttributes(String[] attributes) throws Exception {
        encryptedVoteOptIds = getAttributeAsElGamalEncryptionPair(attributes,
                VcsEncryptedVoteCsvIndex.ENC_VOTE_OPT_IDS);

        encryptedVoteOptIdsAsString = getAttribute(attributes,
                VcsEncryptedVoteCsvIndex.ENC_VOTE_OPT_IDS);
        contestId = getAttribute(attributes, VcsEncryptedVoteCsvIndex.CONTEST_ID);
        electionId = getAttribute(attributes,
                VcsEncryptedVoteCsvIndex.ELECTION_ID);
        electionEventId = getAttribute(attributes,
                VcsEncryptedVoteCsvIndex.ELECTION_EVENT_ID);
    }

    String getContestId() {
        return contestId;
    }

    String getElectionId() {
        return electionId;
    }

    String getElectionEventId() {
        return electionEventId;
    }

    ElGamalEncryptionPair getEncryptedVoteOptIds() {
        return encryptedVoteOptIds;
    }

    String getEncryptedVoteOptIdsAsString() {
        return encryptedVoteOptIdsAsString;
    }

    @Override
    public boolean equals(Object other) {
        return sameSubclass(other) && privateEqual((CleansedVote) other);
    }

    private boolean sameSubclass(Object other) {
        return (other != null) && other.getClass().equals(this.getClass());
    }

    private boolean privateEqual(CleansedVote other) {
        return encryptedVoteOptIds.equals(other.getEncryptedVoteOptIds()) &&
        contestId.equals(other.getContestId()) &&
        electionId.equals(other.getElectionId()) &&
        electionEventId.equals(other.getElectionEventId());
    }

    @Override
    public int hashCode() {
        return encryptedVoteOptIds.hashCode() + contestId.hashCode() +
        electionId.hashCode() + electionEventId.hashCode();
    }

    /**
     * Indexes of the various fields in the CSV file.
     */
    private enum VcsEncryptedVoteCsvIndex {ENC_VOTE_OPT_IDS,
        CONTEST_ID,
        ELECTION_ID,
        ELECTION_EVENT_ID,
        CHANNEL_ID;
    }
}
