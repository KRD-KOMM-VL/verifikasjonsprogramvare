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

import com.computas.zkpev.ResultsList;

import java.io.BufferedReader;
import java.io.IOException;

import java.util.HashMap;


/**
 * A class implementing the AreasMap using a HashMap.
 *
 * @version $Id: AreasHashMap.java 10998 2011-10-18 14:04:09Z fvl $
 */
public class AreasHashMap extends HashMap<String, Area> implements AreasMap {
    @Override
    public void addReaderContent(BufferedReader bufferedReader,
        ResultsList results) throws IOException {
        String line = readNextLine(bufferedReader);

        while (line != null) {
            addArea(line);

            line = readNextLine(bufferedReader);
        }
    }

    void addArea(String line) {
        add(tryToInstantiateArea(line));
    }

    private Area tryToInstantiateArea(String line) {
        return new Area(line);
    }

    @Override
    public void add(Area area) {
        put(area.getPath(), area);
    }

    private String readNextLine(BufferedReader bufferedReader)
        throws IOException {
        return bufferedReader.readLine();
    }
}
