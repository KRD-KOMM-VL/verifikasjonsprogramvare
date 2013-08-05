/**
 * Zero-Knowledge Protocols for E-Vote (ZKPEV).
 *
 * Copyright © 2011, The Norwegian Ministry of Local Government and Regional
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

import com.computas.zkpev.ByteArrayUtils;
import com.computas.zkpev.CsvLineParseable;

import com.scytl.evote.protocol.integration.voting.model.AuthToken;

import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Domain object holding all relevant information about an encrypted vote from
 * the VCS. Since the objects are rather large, only the information that is
 * necessary to match it with an RCG voting receipt are retained after
 * construction of the object.
 *
 * @version $Id: VcsEncryptedVote.java 10998 2011-10-18 14:04:09Z fvl $
 */
public class VcsEncryptedVote extends CsvLineParseable {
    static final String UTF_8 = "UTF-8";
    private static final String HASHING_ALGORITHM = "SHA-1";
    private String contestId;
    private String electionId;
    private String electionEventId;
    private String votingReceipt;
    private String uuid;

    VcsEncryptedVote(String line) {
        super(line);
    }

    protected void setAttributes(String[] attributes)
        throws NoSuchAlgorithmException, UnsupportedEncodingException {
        uuid = getAttribute(attributes, VcsEncryptedVoteCsvIndex.UUID);

        AuthToken authToken = deserializeAuthToken(getAttributeAsByteArray(
                    attributes, VcsEncryptedVoteCsvIndex.AUTH_TOKEN), uuid);
        String authTokenId = getAttribute(attributes,
                VcsEncryptedVoteCsvIndex.AUTH_TOKEN_ID);
        contestId = getAttribute(attributes, VcsEncryptedVoteCsvIndex.CONTEST_ID);
        electionId = getAttribute(attributes,
                VcsEncryptedVoteCsvIndex.ELECTION_ID);
        electionEventId = getAttribute(attributes,
                VcsEncryptedVoteCsvIndex.ELECTION_EVENT_ID);

        byte[][] encVoteOptIds = getAttributeAsByteArrayArray(attributes,
                VcsEncryptedVoteCsvIndex.ENC_VOTE_OPT_IDS);
        byte[] encVoteSig = getAttributeAsByteArray(attributes,
                VcsEncryptedVoteCsvIndex.ENC_VOTE_SIG);
        String internalAuthTokenId = getAttribute(attributes,
                VcsEncryptedVoteCsvIndex.INTERNAL_AUTH_TOKEN_ID);
        String voterCertificate = getAttributeAsString(attributes,
                VcsEncryptedVoteCsvIndex.VOTER_CERTIFICATE);
        String voterId = getAttribute(attributes,
                VcsEncryptedVoteCsvIndex.VOTER_ID);
        long voteTimestamp = getAttributeAsLong(attributes,
                VcsEncryptedVoteCsvIndex.VOTE_TIMESTAMP);
        byte[] voteZKProofSig = getAttributeAsByteArray(attributes,
                VcsEncryptedVoteCsvIndex.VOTE_Z_K_PROOF_SIG);
        votingReceipt = calculateVotingReceipt(authTokenId, authToken,
                internalAuthTokenId, encVoteOptIds, encVoteSig, voterId,
                voterCertificate, voteTimestamp, voteZKProofSig);
    }

    private AuthToken deserializeAuthToken(byte[] binaryAuthToken,
        String thisUuid) {
        try {
            return tryToDeserializeAuthToken(binaryAuthToken);
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format(
                    "The binary AuthToken for encrypted vote %s could not be deserialized to an AuthToken Java object.",
                    thisUuid), e);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(String.format(
                    "The binary AuthToken for encrypted vote %s could not be cast to an AuthToken Java object.",
                    thisUuid), e);
        }
    }

    private AuthToken tryToDeserializeAuthToken(byte[] binaryAuthToken)
        throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(binaryAuthToken);
        ObjectInputStream ois = new ObjectInputStream(bais);

        try {
            return (AuthToken) ois.readObject();
        } finally {
            ois.close();
        }
    }

    private String calculateVotingReceipt(String authTokenId,
        AuthToken authToken, String internalAuthTokenId,
        byte[][] encVoteOptIds, byte[] encVoteSig, String voterId,
        String voterCertificate, long voteTimestamp, byte[] voteZKProofSig)
        throws NoSuchAlgorithmException, UnsupportedEncodingException {
        byte[] message = calculateMessage(authTokenId, authToken,
                internalAuthTokenId, encVoteOptIds, encVoteSig, voterId,
                voterCertificate, voteTimestamp, voteZKProofSig);
        byte[] binaryVotingReceipt = hash(message);

        return encodeBase64(encodeBase64(binaryVotingReceipt).getBytes(UTF_8));
    }

    static String encodeBase64(byte[] votingReceipt) {
        return Base64.encodeBase64String(votingReceipt);
    }

    static byte[] hash(byte[] message) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(HASHING_ALGORITHM);
        md.update(message);

        return md.digest();
    }

    private byte[] calculateMessage(String authTokenId, AuthToken authToken,
        String internalAuthTokenId, byte[][] encVoteOptIds, byte[] encVoteSig,
        String voterId, String voterCertificate, long voteTimestamp,
        byte[] voteZKProofSig) throws UnsupportedEncodingException {
        return ByteArrayUtils.concatenateAsByteArrays(encVoteOptIds, voterId,
            voteZKProofSig, electionEventId, electionId, contestId,
            voteTimestamp, authTokenId, internalAuthTokenId, encVoteSig,
            voterCertificate, authToken);
    }

    String getContestId() {
        return contestId;
    }

    String getElectionEventId() {
        return electionEventId;
    }

    String getElectionId() {
        return electionId;
    }

    String getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof VcsEncryptedVote &&
        privateEqual((VcsEncryptedVote) other);
    }

    private boolean privateEqual(VcsEncryptedVote other) {
        return uuid.equals(other.getUuid()) && electionIdsEqual(other) &&
        votingReceipt.equals(other.getVotingReceipt());
    }

    private boolean electionIdsEqual(VcsEncryptedVote other) {
        return contestId.equals(other.getContestId()) &&
        electionId.equals(other.getElectionId()) &&
        electionEventId.equals(other.getElectionEventId());
    }

    @Override
    public int hashCode() {
        return uuid.hashCode() + contestId.hashCode() + electionId.hashCode() +
        electionEventId.hashCode() + votingReceipt.hashCode();
    }

    String getVotingReceipt() {
        return votingReceipt;
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
        VOTER_AREA,
        CONTEST_ID,
        ELECTION_ID,
        ELECTION_EVENT_ID,
        VOTER_ID,
        CHANNEL_ID;
    }
}
