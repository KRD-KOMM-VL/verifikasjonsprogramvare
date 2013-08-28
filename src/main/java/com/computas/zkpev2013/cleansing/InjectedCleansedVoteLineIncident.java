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
package com.computas.zkpev2013.cleansing;

import com.computas.zkpev2013.Incident;


/**
 * Incident relating to an extra encrypted vote in the result of the
 * cleansing server compared to the encrypted votes list.
 *
 */
public class InjectedCleansedVoteLineIncident extends Incident {
    private final CleansedVote cleansedVote;

    InjectedCleansedVoteLineIncident(CleansedVote cleansedVote) {
        this.cleansedVote = cleansedVote;
    }

    @Override
    public boolean equals(Object other) {
        return sameSubclass(other) &&
        privateEqual((InjectedCleansedVoteLineIncident) other);
    }

    private boolean sameSubclass(Object other) {
        return (other != null) && other.getClass().equals(this.getClass());
    }

    private boolean privateEqual(InjectedCleansedVoteLineIncident other) {
        return cleansedVote.equals(other.getCleansedVote());
    }

    private CleansedVote getCleansedVote() {
        return cleansedVote;
    }

    @Override
    public int hashCode() {
        return cleansedVote.hashCode();
    }

    @Override
    protected String[] getValuesForCsvLine() {
        return new String[] {
            cleansedVote.getEncryptedVoteOptIdsAsString(),
            cleansedVote.getContestId(), cleansedVote.getElectionId(),
            cleansedVote.getElectionEventId()
        };
    }
}
