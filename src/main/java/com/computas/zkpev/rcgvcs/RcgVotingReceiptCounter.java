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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Counter for RCG voting receipts. In particular, it counts the VCS
 * encrypted votes that have a voting receipt equal to the voting receipt
 * stored in the RCG voting receipt.
 *
 */
public class RcgVotingReceiptCounter {
    private final RcgVotingReceipt rcgVotingReceipt;
    private final List<VcsEncryptedVote> vcsEncryptedVotes = new ArrayList<VcsEncryptedVote>();

    RcgVotingReceiptCounter(RcgVotingReceipt rcgVotingReceipt) {
        this.rcgVotingReceipt = rcgVotingReceipt;
    }

    int getNoOfMatches() {
        return vcsEncryptedVotes.size();
    }

    RcgVotingReceipt getRcgVotingReceipt() {
        return rcgVotingReceipt;
    }

    void addVcsEncryptedVote(VcsEncryptedVote vcsEncryptedVote) {
        vcsEncryptedVotes.add(vcsEncryptedVote);
    }

    List<VcsEncryptedVote> getVcsEncryptedVotes() {
        return Collections.unmodifiableList(vcsEncryptedVotes);
    }
}
