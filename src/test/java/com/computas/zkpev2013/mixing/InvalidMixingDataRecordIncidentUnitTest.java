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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * Unit tests on the InvalidMixingDataRecordIncident class.
 */
public class InvalidMixingDataRecordIncidentUnitTest {
    private static final String SAMPLE_MIXING_UUID = "050ec7482895429dbae04688bf22fa57";
    private static final String SAMPLE_CAUSE = "Bar";
    private static final String OTHER_CAUSE = "Bar2";
    private static final String OTHER_MIXING_UUID = "Foo";
    private static final String COMMA = ",";
    private InvalidMixingDataRecordIncident incident;

    /**
      * Creates an incident to test against.
      */
    @BeforeMethod
    public void createInvalidMixingDataRecordIncident() {
        incident = new InvalidMixingDataRecordIncident(SAMPLE_MIXING_UUID,
                SAMPLE_CAUSE);
    }

    /**
     * Verifies that an incident is equal to itself.
     */
    @Test
    public void mustBeEqualToItself() {
        assertEquals(incident, incident);
    }

    /**
     * Verifies that an incident has a consistent hashCode.
     */
    @Test
    public void mustHaveSameHashCodeAsItself() {
        assertEquals(incident.hashCode(), incident.hashCode());
    }

    /**
     * Verifies that an incident is not equal to null.
     */
    @Test
    public void mustNotBeEqualToNull() {
        Object nullObject = null;
        assertFalse(incident.equals(nullObject));
    }

    /**
     * Verifies that an incident is not equal to an object of another class. This is used as the test case.
     */
    @Test
    public void mustNotBeEqualToAnObjectOfAnotherClass() {
        assertFalse(incident.equals(this));
    }

    /**
     * Verifies that the incident is equal to another incident with the same mixing UUID and cause.
     */
    @Test
    public void mustBeEqualToAnotherInvalidMixingDataRecordIncidentWithTheSameMixingUuidAndCause() {
        assertEquals(incident,
            new InvalidMixingDataRecordIncident(SAMPLE_MIXING_UUID, SAMPLE_CAUSE));
    }

    /**
     * Verifies that the incident has the same hash code as another incident with the same mixing UUID and cause.
     */
    @Test
    public void mustHaveSameHashCodeAsAnotherInvalidMixingDataRecordIncidentWithTheSameMixingUuidAndCause() {
        assertEquals(incident.hashCode(),
            new InvalidMixingDataRecordIncident(SAMPLE_MIXING_UUID, SAMPLE_CAUSE).hashCode());
    }

    /**
     * Verifies that the incident is not equal to another incident with another mixing UUID.
     */
    @Test
    public void mustNotBeEqualToAnotherInvalidMixingDataRecordIncidentWithAnotherMixingUuid() {
        assertFalse(incident.equals(
                new InvalidMixingDataRecordIncident(OTHER_MIXING_UUID,
                    SAMPLE_CAUSE)));
    }

    /**
     * Verifies that the incident doesn't have the same hash code as another incident with another mixing UUID.
     */
    @Test
    public void mustNotHaveSameHashCodeAsAnotherInvalidMixingDataRecordIncidentWithAnotherMixingUuid() {
        assertFalse(incident.hashCode() == new InvalidMixingDataRecordIncident(
                OTHER_MIXING_UUID, SAMPLE_CAUSE).hashCode());
    }

    /**
     * Verifies that the incident is not equal to another incident with another cause.
     */
    @Test
    public void mustNotBeEqualToAnotherInvalidMixingDataRecordIncidentWithAnotherCause() {
        assertFalse(incident.equals(
                new InvalidMixingDataRecordIncident(SAMPLE_MIXING_UUID,
                    OTHER_CAUSE)));
    }

    /**
     * Verifies that the incident doesn't have the same hash code as another incident with another cause.
     */
    @Test
    public void mustNotHaveSameHashCodeAsAnotherInvalidMixingDataRecordIncidentWithAnotherCause() {
        assertFalse(incident.hashCode() == new InvalidMixingDataRecordIncident(
                SAMPLE_MIXING_UUID, OTHER_CAUSE).hashCode());
    }

    /**
     * Verifies that toString produced a comma-separated line with the correct
     * values.
     */
    @Test
    public void toStringMustBeCommaSeparatedline() {
        assertEquals(incident.toString(),
            "InvalidMixingDataRecordIncident," + SAMPLE_MIXING_UUID + COMMA +
            SAMPLE_CAUSE);
    }
}
