/**
 * Zero-Knowledge Protocols for E-Vote (ZKPEV).
 *
 * Copyright © 2011, The Norwegian Ministry of Local Government and Regional
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
package com.computas.zkpev;


/**
 * Object representing an Incident that occurred during a proof.
 *
 * @version $Id: Incident.java 10998 2011-10-18 14:04:09Z fvl $
 */
public abstract class Incident implements Result {
    protected static final String COMMA = ",";

    @Override
    public final String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(this.getClass().getSimpleName());

        for (String value : getValuesForCsvLine()) {
            buffer.append(COMMA);
            buffer.append(value);
        }

        return buffer.toString();
    }

    protected abstract String[] getValuesForCsvLine();
}