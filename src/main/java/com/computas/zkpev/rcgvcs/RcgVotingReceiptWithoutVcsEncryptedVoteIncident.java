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

import com.computas.zkpev.Incident;


/**
 * An incident representing the situation in which no VCS encrypted vote
 * matched the RCG voting receipt.
 *
 * @version $Id: RcgVotingReceiptWithoutVcsEncryptedVoteIncident.java 10998 2011-10-18 14:04:09Z fvl $
 */
public class RcgVotingReceiptWithoutVcsEncryptedVoteIncident extends Incident {
    private final RcgVotingReceipt rcgVotingReceipt;

    RcgVotingReceiptWithoutVcsEncryptedVoteIncident(
        RcgVotingReceipt rcgVotingReceipt) {
        this.rcgVotingReceipt = rcgVotingReceipt;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof RcgVotingReceiptWithoutVcsEncryptedVoteIncident &&
        privateEqual((RcgVotingReceiptWithoutVcsEncryptedVoteIncident) other);
    }

    @Override
    public int hashCode() {
        return rcgVotingReceipt.hashCode();
    }

    private boolean privateEqual(
        RcgVotingReceiptWithoutVcsEncryptedVoteIncident other) {
        return rcgVotingReceipt.equals(other.getRcgVotingReceipt());
    }

    private RcgVotingReceipt getRcgVotingReceipt() {
        return rcgVotingReceipt;
    }

    @Override
    protected String[] getValuesForCsvLine() {
        return new String[] { rcgVotingReceipt.getVotingReceipt() };
    }
}
