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

import java.util.List;


/**
 * Incident related to two or more encrypted votes resulting in the same
 * receipt.
 *
 */
public class VotingReceiptCollisionIncident extends Incident {
    private final VotingReceipt votingReceipt;
    private final EncryptedVote[] encryptedVotes;

    VotingReceiptCollisionIncident(VotingReceipt votingReceipt,
        EncryptedVote... encryptedVotes) {
        validateEncryptedVotes(encryptedVotes);

        this.votingReceipt = votingReceipt;
        this.encryptedVotes = encryptedVotes;
    }

    VotingReceiptCollisionIncident(VotingReceipt votingReceipt,
        List<EncryptedVote> encryptedVotes) {
        this(votingReceipt, encryptedVotes.toArray(new EncryptedVote[] {  }));
    }

    private void validateEncryptedVotes(EncryptedVote... theEncryptedVotes) {
        if (theEncryptedVotes.length < 2) {
            throw new IllegalArgumentException(
                "Cannot create an instance of VotingReceiptCollisionIncident for less than two encrypted votes.");
        }

        for (int i = 0; i < theEncryptedVotes.length; i++) {
            for (int j = i + 1; j < theEncryptedVotes.length; j++) {
                if (theEncryptedVotes[i].equals(theEncryptedVotes[j])) {
                    throw new IllegalArgumentException(
                        "The list of encrypted votes for a VotingReceiptCollisionIncident must not contain the same encrypted vote more than once.");
                }
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof VotingReceiptCollisionIncident &&
        privateEqual((VotingReceiptCollisionIncident) other);
    }

    @Override
    public int hashCode() {
        return votingReceipt.hashCode() + encryptedVotesHashCode();
    }

    private int encryptedVotesHashCode() {
        int hashCode = 0;

        for (EncryptedVote encryptedVote : encryptedVotes) {
            hashCode += encryptedVote.hashCode();
        }

        return hashCode;
    }

    private boolean privateEqual(VotingReceiptCollisionIncident other) {
        return votingReceipt.equals(other.getVotingReceipt()) &&
        encryptedVotesEqual(encryptedVotes, other.getEncryptedVotes());
    }

    private boolean encryptedVotesEqual(EncryptedVote[] encryptedVotes1,
        EncryptedVote[] encryptedVotes2) {
        return (encryptedVotes1.length == encryptedVotes2.length) &&
        encryptedVotesOfEqualLengthEqual(encryptedVotes1, encryptedVotes2);
    }

    private boolean encryptedVotesOfEqualLengthEqual(
        EncryptedVote[] encryptedVotes1, EncryptedVote[] encryptedVotes2) {
        for (EncryptedVote encryptedVote : encryptedVotes1) {
            if (!encryptedVoteContainedInArray(encryptedVote, encryptedVotes2)) {
                return false;
            }
        }

        return true;
    }

    private boolean encryptedVoteContainedInArray(EncryptedVote encryptedVote,
        EncryptedVote[] someEncryptedVotes) {
        for (EncryptedVote arrayElement : someEncryptedVotes) {
            if (arrayElement.equals(encryptedVote)) {
                return true;
            }
        }

        return false;
    }

    private EncryptedVote[] getEncryptedVotes() {
        return encryptedVotes;
    }

    private Object getVotingReceipt() {
        return votingReceipt;
    }

    @Override
    protected String[] getValuesForCsvLine() {
        String[] result = new String[encryptedVotes.length + 1];
        result[0] = votingReceipt.getVotingReceipt();

        for (int i = 0; i < encryptedVotes.length; i++) {
            result[i + 1] = encryptedVotes[i].getUuid();
        }

        return result;
    }
}
