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

import java.util.List;


/**
 * Incident related to two or more VCS encrypted votes resulting in the same
 * receipt.
 *
 */
public class RcgVotingReceiptCollisionIncident extends Incident {
    private final RcgVotingReceipt rcgVotingReceipt;
    private final VcsEncryptedVote[] vcsEncryptedVotes;

    RcgVotingReceiptCollisionIncident(RcgVotingReceipt rcgVotingReceipt,
        VcsEncryptedVote... vcsEncryptedVotes) {
        validateVcsEncryptedVotes(vcsEncryptedVotes);

        this.rcgVotingReceipt = rcgVotingReceipt;
        this.vcsEncryptedVotes = vcsEncryptedVotes;
    }

    RcgVotingReceiptCollisionIncident(RcgVotingReceipt rcgVotingReceipt,
        List<VcsEncryptedVote> vcsEncryptedVotes) {
        this(rcgVotingReceipt,
            vcsEncryptedVotes.toArray(new VcsEncryptedVote[] {  }));
    }

    private void validateVcsEncryptedVotes(
        VcsEncryptedVote... theVcsEncryptedVotes) {
        if (theVcsEncryptedVotes.length < 2) {
            throw new IllegalArgumentException(
                "Cannot create an instance of RcgVotingReceiptCollisionIncident for less than two VCS encrypted votes.");
        }

        for (int i = 0; i < theVcsEncryptedVotes.length; i++) {
            for (int j = i + 1; j < theVcsEncryptedVotes.length; j++) {
                if (theVcsEncryptedVotes[i].equals(theVcsEncryptedVotes[j])) {
                    throw new IllegalArgumentException(
                        "The list of VCS encrypted votes for an RcgVotingReceiptCollisionIncident must not contain the same VCS encrypted vote more than once.");
                }
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof RcgVotingReceiptCollisionIncident &&
        privateEqual((RcgVotingReceiptCollisionIncident) other);
    }

    @Override
    public int hashCode() {
        return rcgVotingReceipt.hashCode() + vcsEncryptedVotesHashCode();
    }

    private int vcsEncryptedVotesHashCode() {
        int hashCode = 0;

        for (VcsEncryptedVote vcsEncryptedVote : vcsEncryptedVotes) {
            hashCode += vcsEncryptedVote.hashCode();
        }

        return hashCode;
    }

    private boolean privateEqual(RcgVotingReceiptCollisionIncident other) {
        return rcgVotingReceipt.equals(other.getRcgVotingReceipt()) &&
        vcsEncryptedVotesEqual(vcsEncryptedVotes, other.getVcsEncryptedVotes());
    }

    private boolean vcsEncryptedVotesEqual(
        VcsEncryptedVote[] vcsEncryptedVotes1,
        VcsEncryptedVote[] vcsEncryptedVotes2) {
        return (vcsEncryptedVotes1.length == vcsEncryptedVotes2.length) &&
        vcsEncryptedVotesOfEqualLengthEqual(vcsEncryptedVotes1,
            vcsEncryptedVotes2);
    }

    private boolean vcsEncryptedVotesOfEqualLengthEqual(
        VcsEncryptedVote[] vcsEncryptedVotes1,
        VcsEncryptedVote[] vcsEncryptedVotes2) {
        for (VcsEncryptedVote vcsEncryptedVote : vcsEncryptedVotes1) {
            if (!vcsEncryptedVoteContainedInArray(vcsEncryptedVote,
                        vcsEncryptedVotes2)) {
                return false;
            }
        }

        return true;
    }

    private boolean vcsEncryptedVoteContainedInArray(
        VcsEncryptedVote vcsEncryptedVote,
        VcsEncryptedVote[] someVcsEncryptedVotes) {
        for (VcsEncryptedVote arrayElement : someVcsEncryptedVotes) {
            if (arrayElement.equals(vcsEncryptedVote)) {
                return true;
            }
        }

        return false;
    }

    private VcsEncryptedVote[] getVcsEncryptedVotes() {
        return vcsEncryptedVotes;
    }

    private Object getRcgVotingReceipt() {
        return rcgVotingReceipt;
    }

    @Override
    protected String[] getValuesForCsvLine() {
        String[] result = new String[vcsEncryptedVotes.length + 1];
        result[0] = rcgVotingReceipt.getVotingReceipt();

        for (int i = 0; i < vcsEncryptedVotes.length; i++) {
            result[i + 1] = vcsEncryptedVotes[i].getUuid();
        }

        return result;
    }
}
