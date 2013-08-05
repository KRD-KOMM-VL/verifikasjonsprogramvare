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
package com.computas.zkpev;


/**
 * Incident related to the presence of a broken line in a file.
 *
 * @version $Id: BrokenLineIncident.java 10998 2011-10-18 14:04:09Z fvl $
 */
public abstract class BrokenLineIncident extends Incident {
    private static final int MAX_LENGTH_OF_CSV_QUOTE = 80;
    private final String line;

    protected BrokenLineIncident(String line) {
        this.line = line;
    }

    @Override
    public boolean equals(Object other) {
        return sameSubclass(other) && privateEqual((BrokenLineIncident) other);
    }

    private boolean sameSubclass(Object other) {
        return (other != null) && other.getClass().equals(this.getClass());
    }

    private boolean privateEqual(BrokenLineIncident other) {
        return shortenAndEscapeLine().equals(other.getLine());
    }

    @Override
    public int hashCode() {
        return shortenAndEscapeLine().hashCode();
    }

    private String getLine() {
        return shortenAndEscapeLine();
    }

    @Override
    protected String[] getValuesForCsvLine() {
        return new String[] { shortenAndEscapeLine() };
    }

    private String shortenAndEscapeLine() {
        String result = line.replaceAll(",", "\'");

        if (result.length() > MAX_LENGTH_OF_CSV_QUOTE) {
            return result.substring(0, MAX_LENGTH_OF_CSV_QUOTE);
        } else {
            return result;
        }
    }
}
