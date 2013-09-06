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

import com.computas.zkpev2013.EncryptedVote;
import com.computas.zkpev2013.Incident;

import org.apache.commons.codec.binary.Base64;


/**
 * Incident relating to an encrypted vote retained more than once
 * in the list of cleansed votes.
 *
 */
public class EncryptedVoteRetainedTooOftenIncident extends Incident {
    private final EncryptedVote encryptedVote;
    private final int expectedNoOfMatches;
    private final int actualNoOfMatches;

    EncryptedVoteRetainedTooOftenIncident(EncryptedVote encryptedVote,
        int expectedNoOfMatches, int actualNoOfMatches) {
        this.encryptedVote = encryptedVote;
        this.expectedNoOfMatches = expectedNoOfMatches;
        this.actualNoOfMatches = actualNoOfMatches;
    }

    @Override
    public boolean equals(Object other) {
        return sameSubclass(other) &&
        privateEqual((EncryptedVoteRetainedTooOftenIncident) other);
    }

    private boolean sameSubclass(Object other) {
        return (other != null) && other.getClass().equals(this.getClass());
    }

    private boolean privateEqual(EncryptedVoteRetainedTooOftenIncident other) {
        return encryptedVote.equals(other.getEncryptedVote()) &&
        (expectedNoOfMatches == other.getExpectedNoOfMatches()) &&
        (actualNoOfMatches == other.getActualNoOfMatches());
    }

    private int getActualNoOfMatches() {
        return actualNoOfMatches;
    }

    private int getExpectedNoOfMatches() {
        return expectedNoOfMatches;
    }

    private EncryptedVote getEncryptedVote() {
        return encryptedVote;
    }

    @Override
    public int hashCode() {
        return encryptedVote.hashCode() + expectedNoOfMatches +
        actualNoOfMatches;
    }

    @Override
    protected String[] getValuesForCsvLine() {
        return new String[] {
            Base64.encodeBase64String(encryptedVote.getEncVoteOptIds()
                                                   .getPublicKeyComponent()
                                                   .toByteArray()),
            String.format("%d", expectedNoOfMatches),
            String.format("%d", actualNoOfMatches)
        };
    }
}
