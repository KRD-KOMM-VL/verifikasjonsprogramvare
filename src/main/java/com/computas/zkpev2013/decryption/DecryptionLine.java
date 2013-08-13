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
package com.computas.zkpev2013.decryption;

import com.computas.zkpev.CsvLineParseable;

import com.computas.zkpev2013.ElGamalEncryptionPair;

import java.math.BigInteger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Domain object holding all relevant information about a decryption line.
 *
 */
public class DecryptionLine extends CsvLineParseable {
    private static final String HASHING_ALGORITHM = "SHA-256";
    private final String line;
    private ElGamalEncryptionPair encodedVotingOptionsIdsProduct;
    private SchnorrSignature schnorrSignature;
    private BigInteger decryptedVotingOptionIdsProduct;
    private String electionEventId;
    private String electionId;
    private String contestId;
    private String encryptedVotingOptionsIdsProductString;
    private String schnorrSignatureString;

    DecryptionLine(String line) {
        super(line);
        this.line = line;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof DecryptionLine &&
        privateEqual((DecryptionLine) other);
    }

    @Override
    public int hashCode() {
        return line.hashCode();
    }

    private boolean privateEqual(DecryptionLine other) {
        return line.equals(other.getLine());
    }

    private String getLine() {
        return line;
    }

    String getElectionEventId() {
        return electionEventId;
    }

    String getElectionId() {
        return electionId;
    }

    String getContestId() {
        return contestId;
    }

    String getEncryptedVotingOptionsIdsProductString() {
        return encryptedVotingOptionsIdsProductString;
    }

    BigInteger getDecryptedVotingOptionIdsProduct() {
        return decryptedVotingOptionIdsProduct;
    }

    String getSchnorrSignatureString() {
        return schnorrSignatureString;
    }

    @Override
    protected void setAttributes(String[] attributes) throws Exception {
        electionEventId = getAttribute(attributes,
                DecryptionLineCsvIndex.ELECTION_EVENT_ID);
        electionId = getAttribute(attributes, DecryptionLineCsvIndex.ELECTION_ID);
        contestId = getAttribute(attributes, DecryptionLineCsvIndex.CONTEST_ID);

        byte[] encodedVotingOptionIdsProductByteArray = getAttributeAsMultilinedByteArray(attributes,
                DecryptionLineCsvIndex.ENC_VOTE_OPT_IDS);
        encodedVotingOptionsIdsProduct = new ElGamalEncryptionPair(encodedVotingOptionIdsProductByteArray);
        encryptedVotingOptionsIdsProductString = getAttribute(attributes,
                DecryptionLineCsvIndex.ENC_VOTE_OPT_IDS);

        decryptedVotingOptionIdsProduct = getAttributeAsBigInteger(attributes,
                DecryptionLineCsvIndex.DEC_VOTE_OPT_IDS_PROD);
        schnorrSignature = new SchnorrSignature(getAttributeAsByteArray(
                    attributes, DecryptionLineCsvIndex.SIGNATURE));
        schnorrSignatureString = getAttribute(attributes,
                DecryptionLineCsvIndex.SIGNATURE);
    }

    private BigInteger getAttributeAsBigInteger(String[] attributes,
        DecryptionLineCsvIndex index) {
        return new BigInteger(getAttribute(attributes, index));
    }

    boolean verifyProof(BigInteger p, BigInteger g, BigInteger h)
        throws NoSuchAlgorithmException {
        BigInteger x = calculateNonInteractiveChallengeX();
        BigInteger g1 = calculateGeneratorG1(p, g, x);
        BigInteger h1 = calculatePublicKeyH1(p, h, x);
        BigInteger w1 = calculateSchnorrMessageW1(p, g1, h1);
        BigInteger c1 = calculateSchnorrChallengeC1(p, g, h, w1);

        return verifySchnorrChallenge(c1);
    }

    BigInteger calculateNonInteractiveChallengeX()
        throws NoSuchAlgorithmException {
        MessageDigest md = getMessageDigestInstance();
        md.update(decryptedVotingOptionIdsProduct.toByteArray());

        return new BigInteger(md.digest());
    }

    private MessageDigest getMessageDigestInstance()
        throws NoSuchAlgorithmException {
        return MessageDigest.getInstance(HASHING_ALGORITHM);
    }

    BigInteger calculateGeneratorG1(BigInteger p, BigInteger g, BigInteger x) {
        return decryptedVotingOptionIdsSmallerThanEncodedVotingOptionsMessageComponent()
        ? encodedVotingOptionsIdsProduct.getPublicKeyComponent().modPow(x, p)
                                        .multiply(g).mod(p)
        : encodedVotingOptionsIdsProduct.getPublicKeyComponent().modInverse(p)
                                        .modPow(x, p).multiply(g).mod(p);
    }

    BigInteger calculatePublicKeyH1(BigInteger p, BigInteger h, BigInteger x) {
        BigInteger quotient = decryptedVotingOptionIdsSmallerThanEncodedVotingOptionsMessageComponent()
            ? modularDivision(encodedVotingOptionsIdsProduct.getMessageComponent(),
                decryptedVotingOptionIdsProduct, p)
            : modularDivision(decryptedVotingOptionIdsProduct,
                encodedVotingOptionsIdsProduct.getMessageComponent(), p);

        return quotient.modPow(x, p).multiply(h).mod(p);
    }

    /**
     * Returns (a / b) mod p.
     * @param a A BigInteger.
     * @param b A BigInteger.
     * @param p The modulus.
     * @return (a / b) mod p.
     */
    private BigInteger modularDivision(BigInteger a, BigInteger b, BigInteger p) {
        return a.multiply(b.modInverse(p)).mod(p);
    }

    private boolean decryptedVotingOptionIdsSmallerThanEncodedVotingOptionsMessageComponent() {
        return decryptedVotingOptionIdsProduct.compareTo(encodedVotingOptionsIdsProduct.getMessageComponent()) <= 0;
    }

    BigInteger calculateSchnorrMessageW1(BigInteger p, BigInteger g1,
        BigInteger h1) {
        BigInteger part1 = g1.modPow(schnorrSignature.getPrivateKeyComponent(),
                p);
        BigInteger part2 = h1.modPow(schnorrSignature.getMessageComponent(), p);

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

    private boolean verifySchnorrChallenge(BigInteger c1) {
        return c1.equals(schnorrSignature.getMessageComponent());
    }

    /**
    * Indexes of the various fields in the CSV file.
    */
    private enum DecryptionLineCsvIndex {ELECTION_EVENT_ID,
        ELECTION_ID,
        CONTEST_ID,
        ENC_VOTE_OPT_IDS,
        DEC_VOTE_OPT_IDS_PROD,
        SIGNATURE;
    }
}