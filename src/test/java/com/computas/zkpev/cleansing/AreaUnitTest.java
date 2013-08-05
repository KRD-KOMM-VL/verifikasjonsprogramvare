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
package com.computas.zkpev.cleansing;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigInteger;


/**
 * Unit tests on Area.
 *
 * @version $Id: AreaUnitTest.java 11011 2011-10-20 08:26:55Z fvl $
 */
public class AreaUnitTest {
    private static final String MAIN_AREA_PATH = "47.01.0101.010100";
    private static final String MAIN_AREA_PRIME = "65687";
    private static final String COMMON_AREA_PATH = "47.01.0101.010100.0000";
    private static final String COMMON_AREA_PRIME = "35533";
    private static final String AREA_100_PATH = "47.18.1804.180400.0060";
    private static final String SAMPLE_MAIN_LINE = MAIN_AREA_PATH + "," +
        MAIN_AREA_PRIME + ",,";
    private static final String SAMPLE_COMMON_LINE = COMMON_AREA_PATH + "," +
        COMMON_AREA_PRIME + "," + MAIN_AREA_PATH + ",";
    private static final String SAMPLE_AREA_BELOW_100_LINE = "47.18.1804.180400.0022,,," +
        AREA_100_PATH;
    private Area commonArea;
    private Area mainArea;
    private Area fullyDefinedArea;

    /**
     * Creates an Area object to run the tests on.
     */
    @BeforeMethod
    public void createArea() {
        mainArea = new Area(SAMPLE_MAIN_LINE);
        commonArea = new Area(SAMPLE_COMMON_LINE);
        fullyDefinedArea = new Area(SAMPLE_AREA_BELOW_100_LINE);
    }

    /**
     * Verifies that the path is set correctly for a main area.
     */
    @Test
    public void mustSetPathCorrectlyForMainArea() {
        assertEquals(mainArea.getPath(), MAIN_AREA_PATH);
    }

    /**
     * Verifies that the path is set correctly for a common area.
     */
    @Test
    public void mustSetPathCorrectlyForCommonArea() {
        assertEquals(commonArea.getPath(), COMMON_AREA_PATH);
    }

    /**
     * Verifies that the prime number is set correctly for a main area.
     */
    @Test
    public void mustSetPrimeNumberCorrectlyForMainArea() {
        assertEquals(mainArea.getPrime(), new BigInteger(MAIN_AREA_PRIME));
    }

    /**
     * Verifies that the prime number is set correctly for a common area.
     */
    @Test
    public void mustSetPrimeNumberCorrectlyForCommonArea() {
        assertEquals(commonArea.getPrime(), new BigInteger(COMMON_AREA_PRIME));
    }

    /**
     * Verifies that the threshold area is null for a main area.
     */
    @Test
    public void mustSetThresholdAreaEmptyForMainArea() {
        assertEquals(mainArea.getThresholdAreaId(), "");
    }

    /**
     * Verifies that the threshold area is set correctly for a common area.
     */
    @Test
    public void mustSetThresholdAreaCorrectlyForCommonArea() {
        assertEquals(commonArea.getThresholdAreaId(), MAIN_AREA_PATH);
    }

    /**
     * Verifies that the area 100 is null for a main area.
     */
    @Test
    public void mustSetArea100EmptyForMainArea() {
        assertEquals(mainArea.getArea100Id(), "");
    }

    /**
     * Verifies that the area 100 is set correctly for a fully defined area.
     */
    @Test
    public void mustSetArea100CorrectlyForFullyDefinedArea() {
        assertEquals(fullyDefinedArea.getArea100Id(), AREA_100_PATH);
    }
}
