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
package com.computas.zkpev.cleansing;

import com.computas.zkpev.ElGamalEncryptionPair;
import com.computas.zkpev.ResultsList;

import java.io.BufferedReader;
import java.io.IOException;

import java.math.BigInteger;

import java.util.HashMap;


/**
 * An implementation of VcsEncryptedVotesMap using a HashMap.
 *
 */
public class VcsEncryptedVotesHashMap extends HashMap<String, VcsEncryptedVoteRetentionCounter>
    implements VcsEncryptedVotesMap {
    private static final int TICKS_TO_LOG_NO_OF_LINES_READ = 5000;
    private static final String COMMA = ",";
    private final BigInteger modulus;

    VcsEncryptedVotesHashMap(BigInteger modulus) {
        this.modulus = modulus;
    }

    BigInteger getModulus() {
        return modulus;
    }

    @Override
    public void addReaderContent(BufferedReader bufferedReader,
        ResultsList results) throws IOException {
        int noOfLines = 0;
        String line = readNextLine(bufferedReader);

        while (line != null) {
            addVcsEncryptedVoteOrAddIncident(line, results);
            noOfLines++;

            if ((noOfLines % TICKS_TO_LOG_NO_OF_LINES_READ) == 0) {
                NizkpCleansing.getLogger()
                              .info(String.format(
                        "Read in %d encrypted votes from the VCS so far.",
                        noOfLines));
            }

            line = readNextLine(bufferedReader);
        }
    }

    void addVcsEncryptedVoteOrAddIncident(String line, ResultsList results) {
        VcsEncryptedVote vcsEncryptedVote = tryToInstantiateVcsEncryptedVote(line,
                results);

        if (vcsEncryptedVote != null) {
            addVcsEncryptedVote(vcsEncryptedVote, results);
        }
    }

    private void addVcsEncryptedVote(VcsEncryptedVote vcsEncryptedVote,
        ResultsList results) {
        String key = calculateKeyFromVcsEncryptedVote(vcsEncryptedVote);

        if (containsKey(key)) {
            results.add(new VcsEncryptedVotesMapKeyCollisionIncident(
                    get(key).getVcsEncryptedVote().getUuid(),
                    vcsEncryptedVote.getUuid()));
        } else {
            put(key, new VcsEncryptedVoteRetentionCounter(vcsEncryptedVote));
        }
    }

    private String calculateKeyFromVcsEncryptedVote(
        VcsEncryptedVote vcsEncryptedVote) {
        return calculateKeyFromKeyParts(vcsEncryptedVote.getMultipliedEncVoteOptIds(
                modulus), vcsEncryptedVote.getContestId(),
            vcsEncryptedVote.getElectionId(),
            vcsEncryptedVote.getElectionEventId());
    }

    private String calculateKeyFromKeyParts(
        ElGamalEncryptionPair elGamalEncryptionPair, String contestId,
        String electionId, String electionEventId) {
        return elGamalEncryptionPair.getPublicKeyComponent() + COMMA +
        contestId + COMMA + electionId + COMMA + electionEventId;
    }

    private String calculateKeyFromCleansedEncryptedVote(
        CleansedVote cleansedVote) {
        String key = calculateKeyFromKeyParts(cleansedVote.getEncryptedVoteOptIds(),
                cleansedVote.getContestId(), cleansedVote.getElectionId(),
                cleansedVote.getElectionEventId());

        return key;
    }

    private VcsEncryptedVote tryToInstantiateVcsEncryptedVote(String line,
        ResultsList results) {
        try {
            return new VcsEncryptedVote(line);
        } catch (IllegalArgumentException iae) {
            results.add(new VcsEncryptedVoteBrokenLineIncident(line));

            return null;
        }
    }

    private String readNextLine(BufferedReader bufferedReader)
        throws IOException {
        return bufferedReader.readLine();
    }

    @Override
    public VcsEncryptedVoteRetentionCounter findMatchForCleansedVote(
        CleansedVote cleansedVote) {
        return get(calculateKeyFromCleansedEncryptedVote(cleansedVote));
    }
}
