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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Counter for voting receipts. In particular, it counts the
 * encrypted votes that have a voting receipt equal to the voting receipt
 * stored in the file with voting receipts.
 *
 */
public class VotingReceiptCounter {
    private final VotingReceipt votingReceipt;
    private final List<EncryptedVote> encryptedVotes = new ArrayList<EncryptedVote>();

    VotingReceiptCounter(VotingReceipt votingReceipt) {
        this.votingReceipt = votingReceipt;
    }

    int getNoOfMatches() {
        return encryptedVotes.size();
    }

    VotingReceipt getVotingReceipt() {
        return votingReceipt;
    }

    void addEncryptedVote(EncryptedVote encryptedVote) {
        encryptedVotes.add(encryptedVote);
    }

    List<EncryptedVote> getEncryptedVotes() {
        return Collections.unmodifiableList(encryptedVotes);
    }
}
