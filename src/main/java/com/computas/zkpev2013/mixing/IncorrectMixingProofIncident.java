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
package com.computas.zkpev2013.mixing;

import com.computas.zkpev2013.Incident;


/**
 * Incident relating to an incorrect mixing proof.
 */
public class IncorrectMixingProofIncident extends Incident {
    private final String mixingUuid;
    private final String auditUuid;
    private final int voteGroup;

    IncorrectMixingProofIncident(String mixingUuid, String auditUuid,
        int voteGroup) {
        this.mixingUuid = mixingUuid;
        this.auditUuid = auditUuid;
        this.voteGroup = voteGroup;
    }

    @Override
    public boolean equals(Object other) {
        return sameSubclass(other) &&
        privateEqual((IncorrectMixingProofIncident) other);
    }

    private boolean sameSubclass(Object other) {
        return (other != null) && other.getClass().equals(this.getClass());
    }

    private boolean privateEqual(IncorrectMixingProofIncident other) {
        return mixingUuid.equals(other.getMixingUuid()) &&
        auditUuid.equals(other.getAuditUuid()) &&
        (voteGroup == other.getVoteGroup());
    }

    private String getMixingUuid() {
        return mixingUuid;
    }

    private String getAuditUuid() {
        return auditUuid;
    }

    private int getVoteGroup() {
        return voteGroup;
    }

    @Override
    public int hashCode() {
        return mixingUuid.hashCode() + auditUuid.hashCode() + voteGroup;
    }

    @Override
    protected String[] getValuesForCsvLine() {
        return new String[] { mixingUuid, auditUuid, Integer.toString(voteGroup) };
    }
}
