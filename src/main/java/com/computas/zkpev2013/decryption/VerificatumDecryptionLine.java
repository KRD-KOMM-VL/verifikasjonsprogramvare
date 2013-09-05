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

import com.computas.zkpev2013.ElGamalEncryptionPair;

import org.opensaml.xml.util.Base64;

import java.math.BigInteger;

import java.security.NoSuchAlgorithmException;

import java.util.ArrayList;


/**
 * Decryption line based on Verificatum mixing.
 */
public class VerificatumDecryptionLine extends DecryptionLine {
    private ArrayList<SchnorrSignature> schnorrSignaturesVerificatum;
    private ArrayList<ElGamalEncryptionPair> encodedVotingOptionsIdsVerificatum;

    VerificatumDecryptionLine(String line) {
        super(line);
    }

    @Override
    void setEncodedVotionOptionsIds(String[] attributes,
        DecryptionLineCsvIndex encVoteOptIds) {
        String[] encodedVotingOptionIdsStrings = getAttributeAsString(attributes,
                encVoteOptIds).split("#");
        BigInteger publicKeyComponent;
        BigInteger messageComponent;
        encodedVotingOptionsIdsVerificatum = new ArrayList<ElGamalEncryptionPair>();

        for (int i = 0; i < (encodedVotingOptionIdsStrings.length - 1);
                i += 2) {
            publicKeyComponent = new BigInteger(Base64.decode(
                        (encodedVotingOptionIdsStrings[i])));
            messageComponent = new BigInteger(Base64.decode(
                        encodedVotingOptionIdsStrings[i + 1]));
            encodedVotingOptionsIdsVerificatum.add(new ElGamalEncryptionPair(
                    publicKeyComponent, messageComponent));
        }
    }

    @Override
    void setSchnorrSignature(String[] attributes, DecryptionLineCsvIndex index) {
        String[] schnorrSignaturesStrings = new String(Base64.decode(
                    getAttributeAsString(attributes, index))).split("\\|null#");
        schnorrSignaturesVerificatum = new ArrayList<SchnorrSignature>();

        for (String str : schnorrSignaturesStrings) {
            schnorrSignaturesVerificatum.add(new SchnorrSignature(
                    str.getBytes()));
        }
    }

    @Override
    boolean verifyProof(BigInteger p, BigInteger g, BigInteger h)
        throws NoSuchAlgorithmException {
        if (encodedVotingOptionsIdsVerificatum.size() != schnorrSignaturesVerificatum.size()) {
            return false;
        }

        if (encodedVotingOptionsIdsVerificatum.size() != decryptedVotingOptionIds.length) {
            return false;
        }

        for (int i = 0; i < encodedVotingOptionsIdsVerificatum.size(); i++) {
            decryptedVotingOptionIdsProduct = new BigInteger(decryptedVotingOptionIds[i]);
            encodedVotingOptionsIdsProduct = encodedVotingOptionsIdsVerificatum.get(i);
            schnorrSignature = schnorrSignaturesVerificatum.get(i);

            BigInteger x = calculateNonInteractiveChallengeXVerificatum(i);
            BigInteger g1 = calculateGeneratorG1(p, g, x);
            BigInteger h1 = calculatePublicKeyH1(p, h, x);
            BigInteger w1 = calculateSchnorrMessageW1(p, g1, h1);
            BigInteger c1 = calculateSchnorrChallengeC1(p, g, h, w1);

            if (!verifySchnorrChallenge(c1)) {
                return false;
            }
        }

        return true;
    }
}
