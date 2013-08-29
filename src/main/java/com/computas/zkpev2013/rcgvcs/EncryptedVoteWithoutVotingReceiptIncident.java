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
import com.computas.zkpev2013.Incident;


/**
 * Incident representing an encrypted vote missing a voting receipt.
 *
 */
public class EncryptedVoteWithoutVotingReceiptIncident extends Incident {
    private final EncryptedVote encryptedVote;

    EncryptedVoteWithoutVotingReceiptIncident(EncryptedVote encryptedVote) {
        this.encryptedVote = encryptedVote;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof EncryptedVoteWithoutVotingReceiptIncident &&
        privateEqual((EncryptedVoteWithoutVotingReceiptIncident) other);
    }

    private boolean privateEqual(
        EncryptedVoteWithoutVotingReceiptIncident other) {
        return encryptedVote.equals(other.getEncryptedVote());
    }

    private EncryptedVote getEncryptedVote() {
        return encryptedVote;
    }

    @Override
    public int hashCode() {
        return encryptedVote.hashCode();
    }

    @Override
    protected String[] getValuesForCsvLine() {
        return new String[] { encryptedVote.getUuid() };
    }
}
