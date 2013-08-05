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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * Unit tests on AreasHashMap.
 *
 */
public class AreasHashMapUnitTest {
    private static final String MAIN_AREA_PATH = "47.01.0101.010100";
    private static final String AREA_PATH = "47.01.0101.010100.0000";
    private static final String COMMON_AREA_PRIME = "35533";
    private static final String SAMPLE_AREA_LINE = AREA_PATH + "," +
        COMMON_AREA_PRIME + "," + MAIN_AREA_PATH + ",";
    private AreasHashMap areas;

    /**
     * Creates a AreasHashMap for the tests.
     */
    @BeforeMethod(alwaysRun = true)
    public void createAreasHashMap() {
        areas = new AreasHashMap();
    }

    /**
    * Verifies that the map is empty by default.
    */
    @Test
    public void mustBeEmptyByDefault() {
        assertTrue(areas.isEmpty());
    }

    /**
     * Verifies that an area can be retrieved after it's added.
     */
    @Test
    public void mustAddArea() {
        Area area = new Area(SAMPLE_AREA_LINE);
        areas.add(area);
        assertEquals(areas.get(AREA_PATH), area);
    }

    /**
     * Verifies that an IllegalArgumentException is thrown if an area line that
     * can't be parsed is passed in.
     */
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void mustThrowIllegalArgumentExceptionWhenTryingToAddNonParseablAreaLine() {
        areas.addArea("*");
    }
}
