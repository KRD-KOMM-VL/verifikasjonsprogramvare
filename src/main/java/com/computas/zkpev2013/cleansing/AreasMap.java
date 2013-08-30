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

import com.computas.zkpev2013.Collection;

import java.math.BigInteger;

import java.util.Map;


/**
 * Interface defining a map with Areas.
 */
public interface AreasMap extends Map<String, Area>, Collection {
    /**
     * Adds an area to the map.
     *
     * @param area The area to be added.
     */
    void add(Area area);

    /**
     * Finds the largest prime for an area in the map.
     *
     * @return The largest prime in the map.
     */
    BigInteger findLargestPrime();
}