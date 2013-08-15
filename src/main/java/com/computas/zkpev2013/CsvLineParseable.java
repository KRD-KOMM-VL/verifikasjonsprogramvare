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
package com.computas.zkpev2013;


/**
 * Abstract superclass for domain objects that can be parsed in from a CSV
 * line.
 */
public abstract class CsvLineParseable {
    protected static final String COMMA = ",";

    protected CsvLineParseable(String line) {
        try {
            parseLineAndSetAttributes(line);
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format(
                    "Could not parse the line '%s'.", line), e);
        }
    }

    private void parseLineAndSetAttributes(String line)
        throws Exception {
        String[] attributes = line.split(COMMA, -1);

        setAttributes(attributes);
    }

    protected abstract void setAttributes(String[] attributes)
        throws Exception;

    protected String getAttribute(String[] attributes, Enum index) {
        return new String(attributes[index.ordinal()]);
    }
}
