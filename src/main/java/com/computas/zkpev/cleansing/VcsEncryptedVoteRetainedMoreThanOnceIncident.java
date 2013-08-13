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
package com.computas.zkpev.cleansing;

import com.computas.zkpev.Incident;


/**
 * Incident relating to an encrypted vote from the VCS retained more than once
 * in the list of cleansed votes.
 *
 */
public class VcsEncryptedVoteRetainedMoreThanOnceIncident extends Incident {
    private final VcsEncryptedVote vcsEncryptedVote;
    private final int noOfMatches;

    VcsEncryptedVoteRetainedMoreThanOnceIncident(
        VcsEncryptedVote vcsEncryptedVote, int noOfMatches) {
        this.vcsEncryptedVote = vcsEncryptedVote;
        this.noOfMatches = noOfMatches;
    }

    @Override
    public boolean equals(Object other) {
        return sameSubclass(other) &&
        privateEqual((VcsEncryptedVoteRetainedMoreThanOnceIncident) other);
    }

    private boolean sameSubclass(Object other) {
        return (other != null) && other.getClass().equals(this.getClass());
    }

    private boolean privateEqual(
        VcsEncryptedVoteRetainedMoreThanOnceIncident other) {
        return vcsEncryptedVote.equals(other.getVcsEncryptedVote()) &&
        (noOfMatches == other.getNoOfMatches());
    }

    private int getNoOfMatches() {
        return noOfMatches;
    }

    private VcsEncryptedVote getVcsEncryptedVote() {
        return vcsEncryptedVote;
    }

    @Override
    public int hashCode() {
        return vcsEncryptedVote.hashCode() + noOfMatches;
    }

    @Override
    protected String[] getValuesForCsvLine() {
        return new String[] {
            vcsEncryptedVote.getUuid(), String.format("%d", noOfMatches)
        };
    }
}
