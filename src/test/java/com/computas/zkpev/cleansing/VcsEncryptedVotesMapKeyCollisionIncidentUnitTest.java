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
import static org.testng.Assert.assertFalse;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * Unit tests on VcsEncryptedVotesMapKeyCollisionIncident.
 *
 * @version $Id: VcsEncryptedVotesMapKeyCollisionIncidentUnitTest.java 11011 2011-10-20 08:26:55Z fvl $
 */
public class VcsEncryptedVotesMapKeyCollisionIncidentUnitTest {
    private static final String UUID1 = "Foo";
    private static final String UUID2 = "Bar";
    private static final String OTHER_UUID = "Other";
    private VcsEncryptedVotesMapKeyCollisionIncident incident;

    /**
    * Creates an incident to test against.
    */
    @BeforeMethod
    public void createVcsEncryptedVotesMapKeyCollisionIncident() {
        incident = new VcsEncryptedVotesMapKeyCollisionIncident(UUID1, UUID2);
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
     * Verifies that the incident is equal to another incident with the same UUIDs.
     */
    @Test
    public void mustBeEqualToAnotherVcsEncryptedVotesMapKeyCollisionIncidentWithTheSameUuids() {
        assertEquals(incident,
            new VcsEncryptedVotesMapKeyCollisionIncident(UUID1, UUID2));
    }

    /**
     * Verifies that the incident is equal to another incident with the same UUIDs, but interchanged.
     */
    @Test
    public void mustBeEqualToAnotherVcsEncryptedVotesMapKeyCollisionIncidentWithTheSameUuidsInterchanged() {
        assertEquals(incident,
            new VcsEncryptedVotesMapKeyCollisionIncident(UUID2, UUID1));
    }

    /**
     * Verifies that the incident has the same hashCode as another incident with the same UUIDs.
     */
    @Test
    public void mustHaveSameHashCodeAsAnotherVcsEncryptedVotesMapKeyCollisionIncidentWithTheSameUuids() {
        assertEquals(incident.hashCode(),
            new VcsEncryptedVotesMapKeyCollisionIncident(UUID1, UUID2).hashCode());
    }

    /**
     * Verifies that the incident has the same hashCode as another incident with the same UUIDs, but interchanged.
     */
    @Test
    public void mustHaveSameHashCodeAsAnotherVcsEncryptedVotesMapKeyCollisionIncidentWithTheSameUuidsInterchanged() {
        assertEquals(incident.hashCode(),
            new VcsEncryptedVotesMapKeyCollisionIncident(UUID2, UUID1).hashCode());
    }

    /**
     * Verifies that the incident is not equal to another incident with another first UUID.
     */
    @Test
    public void mustNotBeEqualToAnotherVcsEncryptedVotesMapKeyCollisionIncidentWithAnotherFirstUuid() {
        assertFalse(incident.equals(
                new VcsEncryptedVotesMapKeyCollisionIncident(OTHER_UUID, UUID2)));
    }

    /**
     * Verifies that the incident is not equal to another incident with another second UUID.
     */
    @Test
    public void mustNotBeEqualToAnotherVcsEncryptedVotesMapKeyCollisionIncidentWithAnotherSecondUuid() {
        assertFalse(incident.equals(
                new VcsEncryptedVotesMapKeyCollisionIncident(UUID1, OTHER_UUID)));
    }

    /**
     * Verifies that toString produced a comma-separated line with the correct
     * values.
     */
    @Test
    public void toStringMustBeCommaSeparatedline() {
        assertEquals(incident.toString(),
            "VcsEncryptedVotesMapKeyCollisionIncident," + UUID1 + "," + UUID2);
    }
}