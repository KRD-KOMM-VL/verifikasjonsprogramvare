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

import com.computas.zkpev2013.Incident;


/**
 * An incident representing the situation in which no encrypted vote
 * matched the voting receipt.
 *
 */
public class VotingReceiptWithoutEncryptedVoteIncident extends Incident {
    private final VotingReceipt votingReceipt;

    VotingReceiptWithoutEncryptedVoteIncident(VotingReceipt votingReceipt) {
        this.votingReceipt = votingReceipt;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof VotingReceiptWithoutEncryptedVoteIncident &&
        privateEqual((VotingReceiptWithoutEncryptedVoteIncident) other);
    }

    @Override
    public int hashCode() {
        return votingReceipt.hashCode();
    }

    private boolean privateEqual(
        VotingReceiptWithoutEncryptedVoteIncident other) {
        return votingReceipt.equals(other.getVotingReceipt());
    }

    private VotingReceipt getVotingReceipt() {
        return votingReceipt;
    }

    @Override
    protected String[] getValuesForCsvLine() {
        return new String[] { votingReceipt.getVotingReceipt() };
    }
}
