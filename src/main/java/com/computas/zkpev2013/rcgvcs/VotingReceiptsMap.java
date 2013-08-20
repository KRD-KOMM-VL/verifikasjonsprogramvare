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

import com.computas.zkpev2013.Collection;
import com.computas.zkpev2013.ResultsList;

import java.util.Map;


/**
 * Interface for the Map holding all voting receipts.
 *
 *
 */
public interface VotingReceiptsMap extends Map<String, VotingReceiptCounter>,
    Collection {
    /**
     * Creates an voting receipt based on a String, and tries to add it to the map. If the voting receipt was already present, an incident
     * will be created in the results list.
     * @param line A line in the voting receipts file, comma-separated.
     * @param results The results lists where incidents should be created.
     */
    void addVotingReceiptOrAddIncident(String line, ResultsList results);

    /**
     * Returns true if the Map contains a voting receipt for the given contest,
     * election and election event ID.
     * @param votingReceipt The voting receipt.
     * @param contestId The contest ID.
     * @param electionId The election ID.
     * @param electionEventId The election event ID.
     * @return True if a match can be found.
     */
    boolean containsVotingReceipt(String votingReceipt, String contestId,
        String electionId, String electionEventId);

    /**
     * Returns a voting receipt counter with the given voting receipt value, contest
     * ID, election ID and election event ID.
     * @param votingReceipt The voting receipt.
     * @param contestId The contest ID.
     * @param electionId The election ID.
     * @param electionEventId The election event ID.
     * @return The voting receipt, if it is present.
     */
    VotingReceiptCounter get(String votingReceipt, String contestId,
        String electionId, String electionEventId);
}
