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
 * Incident representing an encrypted vote not being registered with a voting
 * receipt by the RCG.
 *
 * @version $Id: VcsEncryptedVoteNotRegisteredByRcgIncident.java 10998 2011-10-18 14:04:09Z fvl $
 */
public class VcsEncryptedVoteNotRegisteredByRcgIncident extends Incident {
    private final VcsEncryptedVote vcsEncryptedVote;

    VcsEncryptedVoteNotRegisteredByRcgIncident(
        VcsEncryptedVote vcsEncryptedVote) {
        this.vcsEncryptedVote = vcsEncryptedVote;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof VcsEncryptedVoteNotRegisteredByRcgIncident &&
        privateEqual((VcsEncryptedVoteNotRegisteredByRcgIncident) other);
    }

    private boolean privateEqual(
        VcsEncryptedVoteNotRegisteredByRcgIncident other) {
        return vcsEncryptedVote.equals(other.getVcsEncryptedVote());
    }

    private VcsEncryptedVote getVcsEncryptedVote() {
        return vcsEncryptedVote;
    }

    @Override
    public int hashCode() {
        return vcsEncryptedVote.hashCode();
    }

    @Override
    protected String[] getValuesForCsvLine() {
        return new String[] { vcsEncryptedVote.getUuid() };
    }
}
