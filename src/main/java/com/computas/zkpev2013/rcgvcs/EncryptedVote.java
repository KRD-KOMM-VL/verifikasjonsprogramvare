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

import com.computas.zkpev2013.CsvLineParseable;

import com.scytl.evote.protocol.integration.voting.model.AuthToken;
import com.scytl.evote.protocol.integration.voting.model.VoteBean;
import com.scytl.evote.protocol.integration.voting.parser.json.AuthTokenJsonParser;
import com.scytl.evote.protocol.integration.voting.parser.json.AuthTokenJsonParserImpl;
import com.scytl.evote.protocol.integration.voting.parser.json.VoteBeanToJson;

import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.Arrays;


/**
 * Domain object holding all relevant information about an encrypted vote.
 * Since the objects are rather large, only the information that is necessary
 * to match it with a voting receipt are retained after construction of the
 * object.
 */
public class EncryptedVote extends CsvLineParseable {
    static final String UTF_8 = "UTF-8";
    private static final String HASHING_ALGORITHM = "SHA-256";
    private static final VoteBeanToJson VOTE_BEAN_TO_JSON = new VoteBeanToJson();
    private static final AuthTokenJsonParser AUTH_TOKEN_JSON_PARSER = new AuthTokenJsonParserImpl();
    private String contestId;
    private String electionId;
    private String electionEventId;
    private String uuid;
    private String votingReceipt;

    EncryptedVote(String line) {
        super(line);
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
    protected void setAttributes(String[] attributes) throws Exception {
        uuid = getAttribute(attributes, EncryptedVoteCsvIndex.UUID);
        contestId = getAttribute(attributes, EncryptedVoteCsvIndex.CONTEST_ID);
        electionId = getAttribute(attributes, EncryptedVoteCsvIndex.ELECTION_ID);
        electionEventId = getAttribute(attributes,
                EncryptedVoteCsvIndex.ELECTION_EVENT_ID);

        byte[][] encGammaAndVoteOptIds = getAttributeAsByteArrayArray(attributes,
                EncryptedVoteCsvIndex.ENC_GAMMA_AND_VOTE_OPT_IDS);
        byte[] encGamma = encGammaAndVoteOptIds[0];
        byte[][] encVoteOptIds = Arrays.copyOfRange(encGammaAndVoteOptIds, 1,
                encGammaAndVoteOptIds.length);
        long voteTimestamp = getAttributeAsLong(attributes,
                EncryptedVoteCsvIndex.VOTE_TIMESTAMP);
        long receiptTimestamp = getAttributeAsLong(attributes,
                EncryptedVoteCsvIndex.RECEIPT_TIMESTAMP);
        byte[] voteZKProofSig = getAttributeAsByteArray(attributes,
                EncryptedVoteCsvIndex.VOTE_Z_K_PROOF_SIG);
        String voterId = getAttribute(attributes, EncryptedVoteCsvIndex.VOTER_ID);
        String voterCertificate = getAttributeAsString(attributes,
                EncryptedVoteCsvIndex.VOTER_CERTIFICATE);
        String electionType = getAttribute(attributes,
                EncryptedVoteCsvIndex.ELECTION_TYPE);

        AuthToken authToken = deserializeAuthToken(getAttributeAsByteArray(
                    attributes, EncryptedVoteCsvIndex.AUTH_TOKEN), uuid);
        VoteBean voteBean = new VoteBean.VoteBeanBuilder().setEncGamma(encGamma)
                                                          .setEncVoteOptIDs(encVoteOptIds)
                                                          .setVoteZKProofSig(voteZKProofSig)
                                                          .setVoterId(voterId)
                                                          .setElectionId(electionId)
                                                          .setElectionEventId(electionEventId)
                                                          .setContestId(contestId)
                                                          .setTs(voteTimestamp)
                                                          .setAuthTokenId(authToken.getId())
                                                          .setCertificate(voterCertificate)
                                                          .setElectionType(electionType)
                                                          .build();
        votingReceipt = calculateVotingReceipt(authToken, voteBean,
                receiptTimestamp);
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

    private String calculateVotingReceipt(AuthToken authToken,
        VoteBean voteBean, long receiptTimestamp)
        throws NoSuchAlgorithmException {
        byte[] message = calculateMessage(authToken, voteBean, receiptTimestamp);
        byte[] binaryVotingReceipt = hash(message);

        return encodeBase64(binaryVotingReceipt);
    }

    private byte[] calculateMessage(AuthToken authToken, VoteBean voteBean,
        long receiptTimestamp) {
        String authTokenAsJson = AUTH_TOKEN_JSON_PARSER.toJson(authToken);
        String voteAsJson = VOTE_BEAN_TO_JSON.voteToJson(voteBean);
        String receiptTimestampAsString = String.valueOf(receiptTimestamp);

        return (authTokenAsJson + voteAsJson + receiptTimestampAsString).getBytes();
    }

    static String encodeBase64(byte[] binaryVotingReceipt) {
        return Base64.encodeBase64String(binaryVotingReceipt);
    }

    static byte[] hash(byte[] message) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(HASHING_ALGORITHM);
        md.update(message);

        return md.digest();
    }

    String getVotingReceipt() {
        return votingReceipt;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof EncryptedVote &&
        privateEqual((EncryptedVote) other);
    }

    private boolean privateEqual(EncryptedVote other) {
        return uuid.equals(other.getUuid()) && electionIdsEqual(other) &&
        votingReceipt.equals(other.getVotingReceipt());
    }

    private boolean electionIdsEqual(EncryptedVote other) {
        return contestId.equals(other.getContestId()) &&
        electionId.equals(other.getElectionId()) &&
        electionEventId.equals(other.getElectionEventId());
    }

    @Override
    public int hashCode() {
        return uuid.hashCode() + contestId.hashCode() + electionId.hashCode() +
        electionEventId.hashCode() + votingReceipt.hashCode();
    }

    /**
     * Indexes of the various fields in the CSV file.
     */
    private enum EncryptedVoteCsvIndex {UUID,
        AUTH_TOKEN,
        AUTH_TOKEN_ID,
        ENC_GAMMA_AND_VOTE_OPT_IDS,
        ENC_VOTE_SIG,
        INTERNAL_AUTH_TOKEN_ID,
        ELECTION_TYPE,
        VOTE_TIMESTAMP,
        VOTER_CERTIFICATE,
        VOTER_AREA,
        CONTEST_ID,
        ELECTION_ID,
        ELECTION_EVENT_ID,
        VOTER_ID,
        CHANNEL_ID,
        RECEIPT_TIMESTAMP,
        VOTE_Z_K_PROOF_SIG;
    }
}
