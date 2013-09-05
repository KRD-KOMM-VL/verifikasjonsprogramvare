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

import java.math.BigInteger;

import java.security.NoSuchAlgorithmException;


/**
 * Decryption line based on Scytl mixing.
 */
public class ScytlDecryptionLine extends DecryptionLine {
    ScytlDecryptionLine(String line) {
        super(line);
    }

    @Override
    void setEncodedVotionOptionsIds(String[] attributes,
        DecryptionLineCsvIndex index) {
        encodedVotingOptionsIds = getAttributeAsElGamalEncryptionTuple(attributes,
                index);
    }

    @Override
    void setSchnorrSignature(String[] attributes, DecryptionLineCsvIndex index) {
        schnorrSignature = new SchnorrSignature(getAttributeAsByteArray(
                    attributes, index));
    }

    @Override
    boolean verifyProof(BigInteger p, BigInteger g, BigInteger h)
        throws NoSuchAlgorithmException {
        setEncodedVotingOptionIdsProduct(p);
        calculateDecryptedVotingOptionIdsProduct(p);

        BigInteger x = calculateNonInteractiveChallengeX();
        BigInteger g1 = calculateGeneratorG1(p, g, x);
        BigInteger h1 = calculatePublicKeyH1(p, h, x);
        BigInteger w1 = calculateSchnorrMessageW1(p, g1, h1);
        BigInteger c1 = calculateSchnorrChallengeC1(p, g, h, w1);

        return verifySchnorrChallenge(c1);
    }
}
