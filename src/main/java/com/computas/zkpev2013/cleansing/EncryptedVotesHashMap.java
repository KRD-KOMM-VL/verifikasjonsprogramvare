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

import com.computas.zkpev2013.ElGamalEncryptionTuple;
import com.computas.zkpev2013.EncryptedVote;
import com.computas.zkpev2013.ResultsList;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;

import java.math.BigInteger;

import java.util.HashMap;


/**
 * An implementation of EncryptedVotesMap using a HashMap.
 */
public class EncryptedVotesHashMap extends HashMap<String, EncryptedVoteRetentionCounter>
    implements EncryptedVotesMap {
    private static final int TICKS_TO_LOG_NO_OF_LINES_READ = 5000;
    private static final String COMMA = ",";
    private final BigInteger modulus;

    EncryptedVotesHashMap(BigInteger modulus) {
        this.modulus = modulus;
    }

    BigInteger getModulus() {
        return modulus;
    }

    @Override
    public void addReaderContent(BufferedReader bufferedReader,
        ResultsList results, Logger logger) throws IOException {
        int noOfLines = 0;
        String line = readNextLine(bufferedReader);

        while (line != null) {
            addEncryptedVoteOrAddIncident(line, results);
            noOfLines++;

            if ((noOfLines % TICKS_TO_LOG_NO_OF_LINES_READ) == 0) {
                logger.info(String.format(
                        "Read in %d encrypted votes from the VCS so far.",
                        noOfLines));
            }

            line = readNextLine(bufferedReader);
        }
    }

    void addEncryptedVoteOrAddIncident(String line, ResultsList results) {
        EncryptedVote encryptedVote = tryToInstantiateEncryptedVote(line,
                results);

        if (encryptedVote != null) {
            addEncryptedVote(encryptedVote, results);
        }
    }

    private void addEncryptedVote(EncryptedVote encryptedVote,
        ResultsList results) {
        String key = calculateKeyFromEncryptedVote(encryptedVote);

        if (containsKey(key)) {
            results.add(new EncryptedVotesMapKeyCollisionIncident(
                    get(key).getEncryptedVote().getUuid(),
                    encryptedVote.getUuid()));
        } else {
            put(key, new EncryptedVoteRetentionCounter(encryptedVote));
        }
    }

    private String calculateKeyFromEncryptedVote(EncryptedVote encryptedVote) {
        return calculateKeyFromKeyParts(encryptedVote.getEncVoteOptIds(),
            encryptedVote.getContestId(), encryptedVote.getElectionId(),
            encryptedVote.getElectionEventId());
    }

    private String calculateKeyFromKeyParts(
        ElGamalEncryptionTuple elGamalEncryptionTuple, String contestId,
        String electionId, String electionEventId) {
        StringBuffer messageComponentsWithComma = new StringBuffer();

        for (BigInteger messageComponent : elGamalEncryptionTuple.getMessageComponents()) {
            messageComponentsWithComma.append(messageComponent).append(COMMA);
        }

        return elGamalEncryptionTuple.getPublicKeyComponent() + COMMA +
        messageComponentsWithComma.toString() + contestId + COMMA + electionId +
        COMMA + electionEventId;
    }

    private String calculateKeyFromCleansedEncryptedVote(
        CleansedVote cleansedVote) {
        String key = calculateKeyFromKeyParts(cleansedVote.getEncryptedVoteOptIds(),
                cleansedVote.getContestId(), cleansedVote.getElectionId(),
                cleansedVote.getElectionEventId());

        return key;
    }

    private EncryptedVote tryToInstantiateEncryptedVote(String line,
        ResultsList results) {
        try {
            return new EncryptedVote(line);
        } catch (IllegalArgumentException iae) {
            results.add(new EncryptedVoteBrokenLineIncident(line));

            return null;
        }
    }

    private String readNextLine(BufferedReader bufferedReader)
        throws IOException {
        return bufferedReader.readLine();
    }

    @Override
    public EncryptedVoteRetentionCounter findMatchForCleansedVote(
        CleansedVote cleansedVote) {
        return get(calculateKeyFromCleansedEncryptedVote(cleansedVote));
    }
}
