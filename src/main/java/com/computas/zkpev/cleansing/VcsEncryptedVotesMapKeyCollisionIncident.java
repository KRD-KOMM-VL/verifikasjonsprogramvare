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
 * Class representing an incident about two VCS encrypted votes mapping to the
 * same key in the VCS encrypted vote. This would only happen in the ephemeric
 * key of the ElGamal encryption would happen to be the same in addition to
 * the chosen voting option IDs being identical. Usually this would indicate
 * a problem in the VCS, which results in the duplication of a VCS encrypted
 * vote.
 *
 * @version $Id: VcsEncryptedVotesMapKeyCollisionIncident.java 10998 2011-10-18 14:04:09Z fvl $
 */
public class VcsEncryptedVotesMapKeyCollisionIncident extends Incident {
    private final String uuid1;
    private final String uuid2;

    VcsEncryptedVotesMapKeyCollisionIncident(String uuid1, String uuid2) {
        this.uuid1 = uuid1;
        this.uuid2 = uuid2;
    }

    @Override
    public boolean equals(Object other) {
        return sameSubclass(other) &&
        privateEqual((VcsEncryptedVotesMapKeyCollisionIncident) other);
    }

    private boolean sameSubclass(Object other) {
        return (other != null) && other.getClass().equals(this.getClass());
    }

    private boolean privateEqual(VcsEncryptedVotesMapKeyCollisionIncident other) {
        return (uuid1.equals(other.getUuid1()) &&
        uuid2.equals(other.getUuid2())) ||
        (uuid1.equals(other.getUuid2()) && uuid2.equals(other.getUuid1()));
    }

    private String getUuid1() {
        return uuid1;
    }

    private String getUuid2() {
        return uuid2;
    }

    @Override
    public int hashCode() {
        return uuid1.hashCode() + uuid2.hashCode();
    }

    @Override
    protected String[] getValuesForCsvLine() {
        return new String[] { uuid1, uuid2 };
    }
}
