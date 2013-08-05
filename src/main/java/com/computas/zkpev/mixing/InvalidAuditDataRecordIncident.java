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
package com.computas.zkpev.mixing;

import com.computas.zkpev.Incident;


/**
 * Incident relating to an invalid audit data record.
 *
 * @version $Id: InvalidAuditDataRecordIncident.java 10998 2011-10-18 14:04:09Z fvl $
 */
public class InvalidAuditDataRecordIncident extends Incident {
    static final String INPUT_VOTE_GROUPS_INVALID = "Input vote groups invalid";
    static final String OUTPUT_VOTE_GROUPS_INVALID = "Output vote groups invalid";
    static final String REENCRYPTION_PROOFS_INVALID = "Reencryption proofs invalid";
    private final String auditUuid;
    private final String cause;

    InvalidAuditDataRecordIncident(String auditUuid, String cause) {
        this.auditUuid = auditUuid;
        this.cause = cause;
    }

    @Override
    public boolean equals(Object other) {
        return sameSubclass(other) &&
        privateEqual((InvalidAuditDataRecordIncident) other);
    }

    private boolean sameSubclass(Object other) {
        return (other != null) && other.getClass().equals(this.getClass());
    }

    private boolean privateEqual(InvalidAuditDataRecordIncident other) {
        return auditUuid.equals(other.getAuditUuid()) &&
        cause.equals(other.getCause());
    }

    private String getAuditUuid() {
        return auditUuid;
    }

    private String getCause() {
        return cause;
    }

    @Override
    public int hashCode() {
        return auditUuid.hashCode() + cause.hashCode();
    }

    @Override
    protected String[] getValuesForCsvLine() {
        return new String[] { auditUuid, cause };
    }
}
