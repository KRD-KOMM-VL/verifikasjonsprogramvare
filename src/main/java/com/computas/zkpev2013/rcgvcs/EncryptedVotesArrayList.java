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

import com.computas.zkpev2013.EncryptedVote;
import com.computas.zkpev2013.ResultsList;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;

import java.util.ArrayList;


/**
 * Implementation of EncryptedVotesList using an ArrayList.
 *
 */
public class EncryptedVotesArrayList extends ArrayList<EncryptedVote>
    implements EncryptedVotesList {
    private static final int TICKS_TO_LOG_NO_OF_ENCRYPTED_VOTE_LINES_READ = 10000;

    @Override
    public void addReaderContent(BufferedReader bufferedReader,
        ResultsList results, Logger logger) throws IOException {
        String line = readNextLine(bufferedReader);
        int noOfLines = 1;

        while (line != null) {
            addEncryptedVoteOrCreateIncident(line, results);

            if ((noOfLines % TICKS_TO_LOG_NO_OF_ENCRYPTED_VOTE_LINES_READ) == 0) {
                logger.info(String.format("Read %d lines so far...", noOfLines));
            }

            line = readNextLine(bufferedReader);
            noOfLines++;
        }
    }

    private String readNextLine(BufferedReader bufferedReader)
        throws IOException {
        return bufferedReader.readLine();
    }

    @Override
    public void addEncryptedVoteOrCreateIncident(String line,
        ResultsList results) {
        try {
            tryToAddEncryptedVote(line);
        } catch (IllegalArgumentException iae) {
            results.add(new EncryptedVoteBrokenLineIncident(line));
        }
    }

    private void tryToAddEncryptedVote(String line) {
        add(new EncryptedVote(line));
    }
}
