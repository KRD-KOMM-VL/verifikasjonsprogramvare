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

import com.computas.zkpev.ResultsList;

import java.io.BufferedReader;
import java.io.IOException;

import java.util.ArrayList;


/**
 * Implementation of VcsEncryptedVotesList using an ArrayList.
 *
 * @version $Id: VcsEncryptedVotesArrayList.java 10998 2011-10-18 14:04:09Z fvl $
 */
public class VcsEncryptedVotesArrayList extends ArrayList<VcsEncryptedVote>
    implements VcsEncryptedVotesList {
    @Override
    public void addReaderContent(BufferedReader bufferedReader,
        ResultsList results) throws IOException {
        String line = readNextLine(bufferedReader);

        while (line != null) {
            addEncryptedVoteOrCreateIncident(line, results);
            line = readNextLine(bufferedReader);
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
            results.add(new VcsEncryptedVoteBrokenLineIncident(line));
        }
    }

    private void tryToAddEncryptedVote(String line) {
        add(new VcsEncryptedVote(line));
    }
}
