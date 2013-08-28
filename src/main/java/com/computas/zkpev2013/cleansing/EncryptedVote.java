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
import com.computas.zkpev2013.ElGamalEncryptionTuple;

import java.math.BigInteger;


/**
 * Class representing an encrypted vote.
 */
public class EncryptedVote extends CsvLineParseable {
    private String uuid;
    private ElGamalEncryptionTuple encVoteOptIds;
    private String contestId;
    private String electionId;
    private String electionEventId;
    private String voterAreaId;

    EncryptedVote(String line) {
        super(line);
    }

    String getUuid() {
        return uuid;
    }

    ElGamalEncryptionTuple getEncVoteOptIds() {
        return encVoteOptIds;
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

    String getVoterAreaId() {
        return voterAreaId;
    }

    @Override
    public boolean equals(Object other) {
        return sameSubclass(other) && privateEqual((EncryptedVote) other);
    }

    private boolean sameSubclass(Object other) {
        return (other != null) && other.getClass().equals(this.getClass());
    }

    private boolean privateEqual(EncryptedVote other) {
        return privateBasicFieldsEqual(other) && encVoteOptIdsEqual(other);
    }

    private boolean privateBasicFieldsEqual(EncryptedVote other) {
        return privateVoteFieldsEqual(other) &&
        privateElectionFieldsEqual(other) && privateVoterFieldsEqual(other);
    }

    private boolean privateVoteFieldsEqual(EncryptedVote other) {
        return uuid.equals(other.getUuid());
    }

    private boolean privateElectionFieldsEqual(EncryptedVote other) {
        return contestId.equals(other.getContestId()) &&
        electionEventId.equals(other.getElectionEventId()) &&
        electionId.equals(other.getElectionId());
    }

    private boolean privateVoterFieldsEqual(EncryptedVote other) {
        return voterAreaId.equals(other.getVoterAreaId());
    }

    private boolean encVoteOptIdsEqual(EncryptedVote other) {
        return this.getEncVoteOptIds().equals(other.getEncVoteOptIds());
    }

    @Override
    public int hashCode() {
        return uuid.hashCode() + contestId.hashCode() +
        electionEventId.hashCode() + electionId.hashCode() +
        voterAreaId.hashCode();
    }

    @Override
    protected void setAttributes(String[] attributes) throws Exception {
        uuid = getAttribute(attributes, VcsEncryptedVoteCsvIndex.UUID);
        contestId = getAttribute(attributes, VcsEncryptedVoteCsvIndex.CONTEST_ID);
        electionId = getAttribute(attributes,
                VcsEncryptedVoteCsvIndex.ELECTION_ID);
        electionEventId = getAttribute(attributes,
                VcsEncryptedVoteCsvIndex.ELECTION_EVENT_ID);

        encVoteOptIds = getAttributeAsElGamalEncryptionTuple(attributes,
                VcsEncryptedVoteCsvIndex.ENC_VOTE_OPT_IDS);
        voterAreaId = getAttribute(attributes,
                VcsEncryptedVoteCsvIndex.VOTER_AREA_ID);
    }

    // TODO: Compression based on compression factor and possible areaPrime.
    ElGamalEncryptionTuple getCompressedEncVoteOptIds(BigInteger modulus,
        BigInteger areaPrime) {
        return encVoteOptIds;
    }

    /**
    * Indexes of the various fields in the CSV file.
    */
    private enum VcsEncryptedVoteCsvIndex {UUID,
        AUTH_TOKEN,
        AUTH_TOKEN_ID,
        ENC_VOTE_OPT_IDS,
        ENC_VOTE_SIG,
        INTERNAL_AUTH_TOKEN_ID,
        VOTE_TIMESTAMP,
        VOTE_Z_K_PROOF_SIG,
        VOTER_CERTIFICATE,
        VOTER_AREA_ID,
        CONTEST_ID,
        ELECTION_ID,
        ELECTION_EVENT_ID,
        VOTER_ID,
        CHANNEL_ID;
    }
}
