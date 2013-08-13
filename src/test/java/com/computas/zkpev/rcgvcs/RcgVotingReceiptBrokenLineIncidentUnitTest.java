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
package com.computas.zkpev.rcgvcs;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * Unit tests on the RcgVotingReceiptBrokenLineIncident class.
 *
 */
public class RcgVotingReceiptBrokenLineIncidentUnitTest {
    private static final String SAMPLE_LINE = "*";
    private static final String OTHER_SAMPLE_LINE = "?";
    private static final String LONG_BROKEN_LINE_WITH_COMMAS = "4028806a2fde07b5012fde439de0000a,7a75e04b-6e7e-4430-b0d3-ae5637c2f08d,rO0ABXNyAD1jb20uc2N5dGwuZXZvdGUucHJvdG9jb2wuaW50ZWdyYXRpb24udm90aW5nLm1vZGVsLlJlY2VpcHRCZWFuw7/82u7t7McCAAhKAA";
    private static final String SHORTENED_LONG_BROKEN_LINE_WITH_COMMAS = "4028806a2fde07b5012fde439de0000a'7a75e04b-6e7e-4430-b0d3-ae5637c2f08d'rO0ABXNyAD";
    private RcgVotingReceiptBrokenLineIncident incident;

    /**
    * Creates an incident to test against.
    */
    @BeforeMethod
    public void createRcgVotingReceiptBrokenLineIncident() {
        incident = new RcgVotingReceiptBrokenLineIncident(SAMPLE_LINE);
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
     * Verifies that the incident is equal to another incident with the same broken line.
     */
    @Test
    public void mustBeEqualToAnotherRcgVotingReceiptBrokenLineIncidentWithTheSameLine() {
        assertEquals(incident,
            new RcgVotingReceiptBrokenLineIncident(SAMPLE_LINE));
    }

    /**
     * Verifies that the incident has the same hashCode as another incident with the same voting receipt.
     */
    @Test
    public void mustHaveSameHashCodeAsAnotherRcgVotingReceiptBrokenLineIncidentWithTheSameLine() {
        assertEquals(incident.hashCode(),
            new RcgVotingReceiptBrokenLineIncident(SAMPLE_LINE).hashCode());
    }

    /**
     * Verifies that the incident is not equal to another incident with another voting receipt.
     */
    @Test
    public void mustNotBeEqualToAnotherRcgVotingReceiptBrokenLineIncidentWithAnotherLine() {
        assertFalse(incident.equals(
                new RcgVotingReceiptBrokenLineIncident(OTHER_SAMPLE_LINE)));
    }

    /**
     * Verifies that toString produced a comma-separated line with the correct
     * values for a short broken line.
     */
    @Test
    public void toStringMustBeCommaSeparatedlineForAShortBrokenLine() {
        assertEquals(incident.toString(),
            "RcgVotingReceiptBrokenLineIncident," + SAMPLE_LINE);
    }

    /**
     * Verifies that toString produced a comma-separated line with the correct
     * values for a long broken line with commas.
     */
    @Test
    public void toStringMustBeCommaSeparatedlineForLongBrokenLineWithCommas() {
        assertEquals(new RcgVotingReceiptBrokenLineIncident(
                LONG_BROKEN_LINE_WITH_COMMAS).toString(),
            "RcgVotingReceiptBrokenLineIncident," +
            SHORTENED_LONG_BROKEN_LINE_WITH_COMMAS);
    }
}
