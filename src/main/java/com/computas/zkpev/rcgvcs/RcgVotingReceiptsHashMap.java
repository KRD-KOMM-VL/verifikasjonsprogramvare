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
package com.computas.zkpev.rcgvcs;

import com.computas.zkpev.ResultsList;

import java.io.BufferedReader;
import java.io.IOException;

import java.util.HashMap;


/**
 * Implementation of the RcgVotingReceiptsMap using a HashMap.
 *
 */
public class RcgVotingReceiptsHashMap extends HashMap<String, RcgVotingReceiptCounter>
    implements RcgVotingReceiptsMap {
    private static final String COMMA = ",";

    private boolean addVotingReceipt(RcgVotingReceipt rcgVotingReceipt) {
        String key = calculateKeyFromVotingReceipt(rcgVotingReceipt);

        if (containsKey(key)) {
            return false;
        }

        put(key, new RcgVotingReceiptCounter(rcgVotingReceipt));

        return true;
    }

    private String calculateKeyFromVotingReceipt(
        RcgVotingReceipt rcgVotingReceipt) {
        return calculateKeyFromVotingReceipt(rcgVotingReceipt.getVotingReceipt(),
            rcgVotingReceipt.getContestId(), rcgVotingReceipt.getElectionId(),
            rcgVotingReceipt.getElectionEventId());
    }

    @Override
    public void addReaderContent(BufferedReader bufferedReader,
        ResultsList results) throws IOException {
        String line = readNextLine(bufferedReader);

        while (line != null) {
            addVotingReceiptOrAddIncident(line, results);

            line = readNextLine(bufferedReader);
        }
    }

    @Override
    public void addVotingReceiptOrAddIncident(String line, ResultsList results) {
        RcgVotingReceipt rcgVotingReceipt = tryToInstantiateRcgVotingReceipt(line,
                results);

        if ((rcgVotingReceipt != null) && !addVotingReceipt(rcgVotingReceipt)) {
            results.add(new DoubleOccurrenceOfRcgVotingReceiptIncident(
                    rcgVotingReceipt));
        }
    }

    private RcgVotingReceipt tryToInstantiateRcgVotingReceipt(String line,
        ResultsList results) {
        try {
            return new RcgVotingReceipt(line);
        } catch (IllegalArgumentException iae) {
            results.add(new RcgVotingReceiptBrokenLineIncident(line));

            return null;
        }
    }

    private String readNextLine(BufferedReader bufferedReader)
        throws IOException {
        return bufferedReader.readLine();
    }

    @Override
    public boolean containsVotingReceipt(String votingReceipt,
        String contestId, String electionId, String electionEventId) {
        return containsKey(calculateKeyFromVotingReceipt(votingReceipt,
                contestId, electionId, electionEventId));
    }

    private String calculateKeyFromVotingReceipt(String votingReceipt,
        String contestId, String electionId, String electionEventId) {
        return votingReceipt + COMMA + contestId + COMMA + electionId + COMMA +
        electionEventId;
    }

    @Override
    public RcgVotingReceiptCounter get(String votingReceipt, String contestId,
        String electionId, String electionEventId) {
        return get(calculateKeyFromVotingReceipt(votingReceipt, contestId,
                electionId, electionEventId));
    }
}
