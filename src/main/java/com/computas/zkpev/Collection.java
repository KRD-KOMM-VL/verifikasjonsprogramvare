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

import java.io.BufferedReader;
import java.io.IOException;


/**
 * Interface representing collections of items.
 *
 */
public interface Collection {
    /**
     * Adds the content of the BufferedReader to the collection. If any incidents are detected, they
     * will be added to the list of results.
     *
     * @param bufferedReader The BufferedReader from which to read.
     * @param results The list to which results should be added, typically incidents.
     * @throws IOException Thrown if something goes wrong while reading from the BufferedReader.
     */
    void addReaderContent(BufferedReader bufferedReader, ResultsList results)
        throws IOException;
}
