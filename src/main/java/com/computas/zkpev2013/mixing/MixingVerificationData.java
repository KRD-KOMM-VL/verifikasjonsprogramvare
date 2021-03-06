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
package com.computas.zkpev2013.mixing;

import com.computas.zkpev2013.ElGamalPublicKeyList;

import com.scytl.evote.protocol.integration.mixing.base.ReEncryptionProofs;
import com.scytl.evote.protocol.integration.mixing.base.VoteGroupAffiliations;
import com.scytl.evote.protocol.signers.SchnorrSignature;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import java.math.BigInteger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.List;


/**
 * Class containing all the data related to the mixing verification.
 */
public class MixingVerificationData {
    private static final String HASHING_ALGORITHM = "SHA-256";
    private String mixingUuid;
    private String auditUuid;
    private String inputVotesString;
    private String outputVotesString;
    private VoteGroupAffiliations inputVoteGroups;
    private VoteGroupAffiliations outputVoteGroups;
    private ReEncryptionProofs reencryptionProofs;
    private ElGamalVoteGroupsFactors inputVoteGroupsFactors;
    private ElGamalVoteGroupsFactors outputVoteGroupsFactors;
    private final IzkpMixing master;

    MixingVerificationData(IzkpMixing master) {
        this.master = master;
    }

    String getMixingUuid() {
        return mixingUuid;
    }

    String getInputVotesString() {
        return inputVotesString;
    }

    String getOutputVotesString() {
        return outputVotesString;
    }

    String getAuditUuid() {
        return auditUuid;
    }

    VoteGroupAffiliations getInputVoteGroups() {
        return inputVoteGroups;
    }

    VoteGroupAffiliations getOutputVoteGroups() {
        return outputVoteGroups;
    }

    ReEncryptionProofs getReencryptionProofs() {
        return reencryptionProofs;
    }

    List<ElGamalVoteGroupFactors> getInputVoteGroupsFactors() {
        return inputVoteGroupsFactors;
    }

    List<ElGamalVoteGroupFactors> getOutputVoteGroupsFactors() {
        return outputVoteGroupsFactors;
    }

    void setMixingData(String newMixingUuid, byte[] newInputVotes,
        byte[] newOutputVotes) {
        this.mixingUuid = newMixingUuid;
        this.inputVotesString = new String(newInputVotes);
        this.outputVotesString = new String(newOutputVotes);
    }

    void setAuditData(String newAuditUuid, byte[] newInputVoteGroups,
        byte[] newOutputVoteGroups, byte[] newReencryptionProofs) {
        this.auditUuid = newAuditUuid;
        this.inputVoteGroups = deserializeVoteGroupAffiliations(newInputVoteGroups,
                InvalidAuditDataRecordIncident.INPUT_VOTE_GROUPS_INVALID);
        this.outputVoteGroups = deserializeVoteGroupAffiliations(newOutputVoteGroups,
                InvalidAuditDataRecordIncident.OUTPUT_VOTE_GROUPS_INVALID);
        this.reencryptionProofs = deserializeReEncryptionProofs(newReencryptionProofs);
    }

    private VoteGroupAffiliations deserializeVoteGroupAffiliations(
        byte[] voteGroups, String incidentCause) {
        try {
            return tryToDeserializeVoteGroupAffiliations(voteGroups);
        } catch (IOException e) {
            master.addResult(new InvalidAuditDataRecordIncident(auditUuid,
                    incidentCause));
        } catch (ClassNotFoundException e) {
            master.addResult(new InvalidAuditDataRecordIncident(auditUuid,
                    incidentCause));
        }

        return null;
    }

    private VoteGroupAffiliations tryToDeserializeVoteGroupAffiliations(
        byte[] byteArray) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);
        ObjectInputStream ois = new ObjectInputStream(bais);

        try {
            return (VoteGroupAffiliations) ois.readObject();
        } finally {
            ois.close();
        }
    }

    private ReEncryptionProofs deserializeReEncryptionProofs(
        byte[] newReencryptionProofs) {
        try {
            return tryToDeserializeReEncryptionProofs(newReencryptionProofs);
        } catch (IOException e) {
            master.addResult(new InvalidAuditDataRecordIncident(auditUuid,
                    InvalidAuditDataRecordIncident.REENCRYPTION_PROOFS_INVALID));
        } catch (ClassNotFoundException e) {
            master.addResult(new InvalidAuditDataRecordIncident(auditUuid,
                    InvalidAuditDataRecordIncident.REENCRYPTION_PROOFS_INVALID));
        }

        return null;
    }

    private ReEncryptionProofs tryToDeserializeReEncryptionProofs(
        byte[] byteArray) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);
        ObjectInputStream ois = new ObjectInputStream(bais);

        try {
            return (ReEncryptionProofs) ois.readObject();
        } finally {
            ois.close();
        }
    }

    boolean computeInputVoteGroupsFactors(BigInteger p) {
        try {
            inputVoteGroupsFactors = ElGamalVoteGroupsFactors.computeProducts(reencryptionProofs.size(),
                    p, inputVotesString, inputVoteGroups);

            return true;
        } catch (Throwable t) {
            master.addResult(new InvalidMixingDataRecordIncident(mixingUuid,
                    InvalidMixingDataRecordIncident.INPUT_VOTES_INVALID));

            return false;
        }
    }

    boolean computeOutputVoteGroupsFactors(BigInteger p) {
        try {
            outputVoteGroupsFactors = ElGamalVoteGroupsFactors.computeProducts(reencryptionProofs.size(),
                    p, outputVotesString, outputVoteGroups);

            return true;
        } catch (Throwable t) {
            master.addResult(new InvalidMixingDataRecordIncident(mixingUuid,
                    InvalidMixingDataRecordIncident.OUTPUT_VOTES_INVALID));

            return false;
        }
    }

    boolean verifyZeroKnowledgeProofs(BigInteger p, BigInteger g,
        ElGamalPublicKeyList h) throws NoSuchAlgorithmException {
        boolean globalResult = true;

        for (int i = 0; i < reencryptionProofs.size(); i++) {
            boolean result = verifyZeroKnowledgeProof(p, g,
                    h.getAggregateKey(), inputVoteGroupsFactors.get(i),
                    outputVoteGroupsFactors.get(i), reencryptionProofs.get(i));

            if (!result) {
                master.addResult(new IncorrectMixingProofIncident(mixingUuid,
                        auditUuid, i));
                globalResult = false;
            }
        }

        return globalResult;
    }

    boolean verifyZeroKnowledgeProof(BigInteger p, BigInteger g, BigInteger h,
        ElGamalVoteGroupFactors inputVoteGroupFactors,
        ElGamalVoteGroupFactors outputVoteGroupFactors, SchnorrSignature proof)
        throws NoSuchAlgorithmException {
        BigInteger x = calculateNonInteractiveChallengeX(g, h,
                inputVoteGroupFactors, outputVoteGroupFactors);

        BigInteger g1 = calculateGeneratorG1(p, g, h, x);

        BigInteger h1 = calculatePublicKeyH1(p, inputVoteGroupFactors,
                outputVoteGroupFactors, x);

        BigInteger w1 = calculateSchnorrMessageW1(p, g1, h1, proof);

        BigInteger c1 = calculateSchnorrChallengeC1(p, g, h, w1);

        return verifySchnorrChallenge(c1, proof);
    }

    private boolean verifySchnorrChallenge(BigInteger c1, SchnorrSignature proof) {
        return c1.equals(proof.getE());
    }

    BigInteger calculateNonInteractiveChallengeX(BigInteger g, BigInteger h,
        ElGamalVoteGroupFactors inputVoteGroupFactors,
        ElGamalVoteGroupFactors outputVoteGroupFactors)
        throws NoSuchAlgorithmException {
        MessageDigest md = getMessageDigestInstance();
        md.update(inputVoteGroupFactors.getPublicKeyFactor().toByteArray());
        md.update(inputVoteGroupFactors.getMessageFactor().toByteArray());
        md.update(outputVoteGroupFactors.getPublicKeyFactor().toByteArray());
        md.update(outputVoteGroupFactors.getMessageFactor().toByteArray());
        md.update(g.toByteArray());
        md.update(h.toByteArray());

        return new BigInteger(md.digest());
    }

    private MessageDigest getMessageDigestInstance()
        throws NoSuchAlgorithmException {
        return MessageDigest.getInstance(HASHING_ALGORITHM);
    }

    BigInteger calculateGeneratorG1(BigInteger p, BigInteger g, BigInteger h,
        BigInteger x) {
        return h.modPow(x, p).multiply(g).mod(p);
    }

    BigInteger calculatePublicKeyH1(BigInteger p,
        ElGamalVoteGroupFactors inputVoteGroupFactors,
        ElGamalVoteGroupFactors outputVoteGroupFactors, BigInteger x) {
        BigInteger part1 = outputVoteGroupFactors.getMessageFactor()
                                                 .multiply(inputVoteGroupFactors.getMessageFactor()
                                                                                .modInverse(p))
                                                 .mod(p).modPow(x, p);
        BigInteger part2 = outputVoteGroupFactors.getPublicKeyFactor()
                                                 .multiply(inputVoteGroupFactors.getPublicKeyFactor()
                                                                                .modInverse(p))
                                                 .mod(p);

        return part1.multiply(part2).mod(p);
    }

    BigInteger calculateSchnorrMessageW1(BigInteger p, BigInteger g1,
        BigInteger h1, SchnorrSignature proof) {
        BigInteger part1 = g1.modPow(proof.getS(), p);
        BigInteger part2 = h1.modPow(proof.getE(), p);

        return part1.multiply(part2).mod(p);
    }

    BigInteger calculateSchnorrChallengeC1(BigInteger p, BigInteger g,
        BigInteger h, BigInteger w1) throws NoSuchAlgorithmException {
        MessageDigest md = getMessageDigestInstance();
        md.update(w1.toByteArray());
        md.update(g.toByteArray());
        md.update(h.toByteArray());

        byte[] hash = md.digest();

        return new BigInteger(hash).mod(p);
    }
}
