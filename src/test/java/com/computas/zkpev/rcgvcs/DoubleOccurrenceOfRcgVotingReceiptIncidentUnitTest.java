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
 * Unit tests on the DoubleOccurrenceOfRcgVotingReceiptIncident class.
 *
 */
public class DoubleOccurrenceOfRcgVotingReceiptIncidentUnitTest {
    private static final String GIVEN_VOTING_RECEIPT = "a01OQllwQjlPNVcrUnk1bFFvYVRoTS9XUStBPQ==";
    private static final String GIVEN_SAMPLE_LINE = "4028806a2fde07b5012fde439de0000a,7a75e04b-6e7e-4430-b0d3-ae5637c2f08d,rO0ABXNyAD1jb20uc2N5dGwuZXZvdGUucHJvdG9jb2wuaW50ZWdyYXRpb24udm90aW5nLm1vZGVsLlJlY2VpcHRCZWFuw7/82u7t7McCAAhKAAZfcmNnSURKAApfdGltZXN0YW1wTAAKX2NvbnRlc3RJZHQAEkxqYXZhL2xhbmcvU3RyaW5nO0wAEF9lbGVjdGlvbkV2ZW50SWRxAH4AAUwAC19lbGVjdGlvbklkcQB+AAFbABFfcmVjZWlwdFNpZ25hdHVyZXQAAltCTAATX3JldHVybkNvZGVzTWVzc2FnZXEAfgABWwAOX3ZvdGluZ1JlY2VpcHRxAH4AAnhwAAAAAAAAH5wAAAEv3kOdknQABjAwMDEwNnQABjIwMDcwMXQAAjA2dXIAAltCrPMX+AYIVOACAAB4cAAAAQArbVBU2jABOoExcUaxZfWRJpSeZUuVdjZG9qWy9dJzHikPfljkVYDSLD36WdTB6D+c3uGgSZsEZilQs9+D8f/k+xIFJLVsfywCl/Exrfjsyn9z/hfJdbzLXD/vxlmWPX7LRyLu0pkt6GeS7pQ/7mvWTu1zrC4Km0rs9A6AYwoDzGCkXUk4LTAC3zY6K3r2Roxnz/XGACEOv+XyzgR5XnG/GP7hpabMknu7bO2PNm/X8UJUrPTlex4mt49p84WRdBRuT4tGlMX+ZIK7H2uWMaeJMp7eMbwmQQjHXqs8Qs46wP+s09nb2m6du3Si2D5IBa/Wu5ppitTmB3wdfqFPq+O8cHVxAH4ABwAAABxrTU5CWXBCOU81VytSeTVsUW9hVGhNL1dRK0E9,eba388f8-a494-4b33-a8f4-b3f98f900885,8092," +
        GIVEN_VOTING_RECEIPT + ",000106,06,200701,200701.40.40.40.000000.1";
    private static final String OTHER_SAMPLE_LINE = "4028806a2fde07b5012fde41fa140009,d6c8a580-3d53-4c6d-b25e-ed803059d584,rO0ABXNyAD1jb20uc2N5dGwuZXZvdGUucHJvdG9jb2wuaW50ZWdyYXRpb24udm90aW5nLm1vZGVsLlJlY2VpcHRCZWFuw7/82u7t7McCAAhKAAZfcmNnSURKAApfdGltZXN0YW1wTAAKX2NvbnRlc3RJZHQAEkxqYXZhL2xhbmcvU3RyaW5nO0wAEF9lbGVjdGlvbkV2ZW50SWRxAH4AAUwAC19lbGVjdGlvbklkcQB+AAFbABFfcmVjZWlwdFNpZ25hdHVyZXQAAltCTAATX3JldHVybkNvZGVzTWVzc2FnZXEAfgABWwAOX3ZvdGluZ1JlY2VpcHRxAH4AAnhwAAAAAAAAH5wAAAEv3kH5xXQABjAwMDEwNnQABjIwMDcwMXQAAjA2dXIAAltCrPMX+AYIVOACAAB4cAAAAQBvk0a4Ss641jKXSzEmfs62QPGhI6YaYLSxxB6IF4CCG964XF0lr9vOJUMtqgS8pxMP/l8qWLjYRiZUTiplR71YbkVdQJ8i9ZNGETvpo5RycOHSlG8ei5ZOcVeRh0U5UIQOafYEHvnLHvL/AlR44rWxqhYY8w0ew6UhXVXdehMylDU5j97poXdYGKONTJ0Joz/k2tAjhAeCqDlCXrm9rNgUe4yjZe788vIiK2kbm/tyOERbBxRcIkQzqL8/mjt1CMHmUqUn2uqahAGpQDhYv7ZuOpFahC/XLJJWOlvucsThz3JfHTsJjjGoyZ4qslRnsY0+X51GnWpAhuk+cQ4FUuDXcHVxAH4ABwAAABxNcmZPakQwTHJIczRnMVh2UlZMSTM4Z05INGM9,306da024-0e5c-480a-8233-5c024f68d927,8092,TXJmT2pEMExySHM0ZzFYdlJWTEkzOGdOSDRjPQ==,000106,06,200701,200701.40.40.40.000000.1";
    private DoubleOccurrenceOfRcgVotingReceiptIncident incident;

    /**
     * Creates an incident to test against.
     */
    @BeforeMethod
    public void createDoubleOccurrenceOfRcgVotingReceiptIncident() {
        incident = new DoubleOccurrenceOfRcgVotingReceiptIncident(new RcgVotingReceipt(
                    GIVEN_SAMPLE_LINE));
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
     * Verifies that the incident is equal to another incident with the same voting receipt.
     */
    @Test
    public void mustBeEqualToAnotherDoubleOccurrenceOfRcgVotingReceiptIncidentWithTheSameVotingReceipt() {
        assertEquals(incident,
            new DoubleOccurrenceOfRcgVotingReceiptIncident(
                new RcgVotingReceipt(GIVEN_SAMPLE_LINE)));
    }

    /**
     * Verifies that the incident has the same hashCode as another incident with the same voting receipt.
     */
    @Test
    public void mustHaveSameHashCodeAsAnotherRcgHashDoubleOccurrenceIncidentWithTheSameVotingReceipt() {
        assertEquals(incident.hashCode(),
            new DoubleOccurrenceOfRcgVotingReceiptIncident(
                new RcgVotingReceipt(GIVEN_SAMPLE_LINE)).hashCode());
    }

    /**
     * Verifies that the incident is not equal to another incident with another voting receipt.
     */
    @Test
    public void mustNotBeEqualToAnotherRcgHashDoubleOccurrenceIncidentWithAnotherVotingReceipt() {
        assertFalse(incident.equals(
                new DoubleOccurrenceOfRcgVotingReceiptIncident(
                    new RcgVotingReceipt(OTHER_SAMPLE_LINE))));
    }

    /**
     * Verifies that toString produced a comma-separated line with the correct
     * values.
     */
    @Test
    public void toStringMustBeCommaSeparatedline() {
        assertEquals(incident.toString(),
            "DoubleOccurrenceOfRcgVotingReceiptIncident," +
            GIVEN_VOTING_RECEIPT);
    }
}
