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
package com.computas.zkpev.rcgvcs;

import com.computas.zkpev.ResultsArrayList;
import com.computas.zkpev.ResultsList;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * Unit tests against the RcgVotingReceiptsHashMap class.
 *
 * @version $Id: RcgVotingReceiptsHashMapUnitTest.java 11011 2011-10-20 08:26:55Z fvl $
 */
public class RcgVotingReceiptsHashMapUnitTest {
    private static final String GIVEN_ABSENT_VOTING_RECEIPT = "Absent";
    private static final String COMMA = ",";
    private static final String GIVEN_VOTING_RECEIPT = "a01OQllwQjlPNVcrUnk1bFFvYVRoTS9XUStBPQ==";
    private static final String GIVEN_CONTEST_ID = "000106";
    private static final String GIVEN_ELECTION_ID = "06";
    private static final String GIVEN_ELECTION_EVENT_ID = "200701";
    private static final String GIVEN_SAMPLE_LINE = "4028806a2fde07b5012fde439de0000a,7a75e04b-6e7e-4430-b0d3-ae5637c2f08d,rO0ABXNyAD1jb20uc2N5dGwuZXZvdGUucHJvdG9jb2wuaW50ZWdyYXRpb24udm90aW5nLm1vZGVsLlJlY2VpcHRCZWFuw7/82u7t7McCAAhKAAZfcmNnSURKAApfdGltZXN0YW1wTAAKX2NvbnRlc3RJZHQAEkxqYXZhL2xhbmcvU3RyaW5nO0wAEF9lbGVjdGlvbkV2ZW50SWRxAH4AAUwAC19lbGVjdGlvbklkcQB+AAFbABFfcmVjZWlwdFNpZ25hdHVyZXQAAltCTAATX3JldHVybkNvZGVzTWVzc2FnZXEAfgABWwAOX3ZvdGluZ1JlY2VpcHRxAH4AAnhwAAAAAAAAH5wAAAEv3kOdknQABjAwMDEwNnQABjIwMDcwMXQAAjA2dXIAAltCrPMX+AYIVOACAAB4cAAAAQArbVBU2jABOoExcUaxZfWRJpSeZUuVdjZG9qWy9dJzHikPfljkVYDSLD36WdTB6D+c3uGgSZsEZilQs9+D8f/k+xIFJLVsfywCl/Exrfjsyn9z/hfJdbzLXD/vxlmWPX7LRyLu0pkt6GeS7pQ/7mvWTu1zrC4Km0rs9A6AYwoDzGCkXUk4LTAC3zY6K3r2Roxnz/XGACEOv+XyzgR5XnG/GP7hpabMknu7bO2PNm/X8UJUrPTlex4mt49p84WRdBRuT4tGlMX+ZIK7H2uWMaeJMp7eMbwmQQjHXqs8Qs46wP+s09nb2m6du3Si2D5IBa/Wu5ppitTmB3wdfqFPq+O8cHVxAH4ABwAAABxrTU5CWXBCOU81VytSeTVsUW9hVGhNL1dRK0E9,eba388f8-a494-4b33-a8f4-b3f98f900885,8092," +
        GIVEN_VOTING_RECEIPT + COMMA + GIVEN_CONTEST_ID + COMMA +
        GIVEN_ELECTION_ID + COMMA + GIVEN_ELECTION_EVENT_ID +
        ",200701.40.40.40.000000.1";
    private static final String SAMPLE_LINE_WITH_OTHER_VOTING_RECEIPT_VALUE = "4028806a2fde07b5012fde439de0000a,7a75e04b-6e7e-4430-b0d3-ae5637c2f08d,rO0ABXNyAD1jb20uc2N5dGwuZXZvdGUucHJvdG9jb2wuaW50ZWdyYXRpb24udm90aW5nLm1vZGVsLlJlY2VpcHRCZWFuw7/82u7t7McCAAhKAAZfcmNnSURKAApfdGltZXN0YW1wTAAKX2NvbnRlc3RJZHQAEkxqYXZhL2xhbmcvU3RyaW5nO0wAEF9lbGVjdGlvbkV2ZW50SWRxAH4AAUwAC19lbGVjdGlvbklkcQB+AAFbABFfcmVjZWlwdFNpZ25hdHVyZXQAAltCTAATX3JldHVybkNvZGVzTWVzc2FnZXEAfgABWwAOX3ZvdGluZ1JlY2VpcHRxAH4AAnhwAAAAAAAAH5wAAAEv3kOdknQABjAwMDEwNnQABjIwMDcwMXQAAjA2dXIAAltCrPMX+AYIVOACAAB4cAAAAQArbVBU2jABOoExcUaxZfWRJpSeZUuVdjZG9qWy9dJzHikPfljkVYDSLD36WdTB6D+c3uGgSZsEZilQs9+D8f/k+xIFJLVsfywCl/Exrfjsyn9z/hfJdbzLXD/vxlmWPX7LRyLu0pkt6GeS7pQ/7mvWTu1zrC4Km0rs9A6AYwoDzGCkXUk4LTAC3zY6K3r2Roxnz/XGACEOv+XyzgR5XnG/GP7hpabMknu7bO2PNm/X8UJUrPTlex4mt49p84WRdBRuT4tGlMX+ZIK7H2uWMaeJMp7eMbwmQQjHXqs8Qs46wP+s09nb2m6du3Si2D5IBa/Wu5ppitTmB3wdfqFPq+O8cHVxAH4ABwAAABxrTU5CWXBCOU81VytSeTVsUW9hVGhNL1dRK0E9,eba388f8-a494-4b33-a8f4-b3f98f900885,8092," +
        "OtherVotingReceiptValue" + COMMA + GIVEN_CONTEST_ID + COMMA +
        GIVEN_ELECTION_ID + COMMA + GIVEN_ELECTION_EVENT_ID +
        ",200701.40.40.40.000000.1";
    private static final String SAMPLE_LINE_WITH_OTHER_VOTING_CONTEST_ID_VALUE = "4028806a2fde07b5012fde439de0000a,7a75e04b-6e7e-4430-b0d3-ae5637c2f08d,rO0ABXNyAD1jb20uc2N5dGwuZXZvdGUucHJvdG9jb2wuaW50ZWdyYXRpb24udm90aW5nLm1vZGVsLlJlY2VpcHRCZWFuw7/82u7t7McCAAhKAAZfcmNnSURKAApfdGltZXN0YW1wTAAKX2NvbnRlc3RJZHQAEkxqYXZhL2xhbmcvU3RyaW5nO0wAEF9lbGVjdGlvbkV2ZW50SWRxAH4AAUwAC19lbGVjdGlvbklkcQB+AAFbABFfcmVjZWlwdFNpZ25hdHVyZXQAAltCTAATX3JldHVybkNvZGVzTWVzc2FnZXEAfgABWwAOX3ZvdGluZ1JlY2VpcHRxAH4AAnhwAAAAAAAAH5wAAAEv3kOdknQABjAwMDEwNnQABjIwMDcwMXQAAjA2dXIAAltCrPMX+AYIVOACAAB4cAAAAQArbVBU2jABOoExcUaxZfWRJpSeZUuVdjZG9qWy9dJzHikPfljkVYDSLD36WdTB6D+c3uGgSZsEZilQs9+D8f/k+xIFJLVsfywCl/Exrfjsyn9z/hfJdbzLXD/vxlmWPX7LRyLu0pkt6GeS7pQ/7mvWTu1zrC4Km0rs9A6AYwoDzGCkXUk4LTAC3zY6K3r2Roxnz/XGACEOv+XyzgR5XnG/GP7hpabMknu7bO2PNm/X8UJUrPTlex4mt49p84WRdBRuT4tGlMX+ZIK7H2uWMaeJMp7eMbwmQQjHXqs8Qs46wP+s09nb2m6du3Si2D5IBa/Wu5ppitTmB3wdfqFPq+O8cHVxAH4ABwAAABxrTU5CWXBCOU81VytSeTVsUW9hVGhNL1dRK0E9,eba388f8-a494-4b33-a8f4-b3f98f900885,8092," +
        GIVEN_VOTING_RECEIPT + COMMA + "999999" + COMMA + GIVEN_ELECTION_ID +
        COMMA + GIVEN_ELECTION_EVENT_ID + ",200701.40.40.40.000000.1";
    private static final String SAMPLE_LINE_WITH_OTHER_ELECTION_ID_VALUE = "4028806a2fde07b5012fde439de0000a,7a75e04b-6e7e-4430-b0d3-ae5637c2f08d,rO0ABXNyAD1jb20uc2N5dGwuZXZvdGUucHJvdG9jb2wuaW50ZWdyYXRpb24udm90aW5nLm1vZGVsLlJlY2VpcHRCZWFuw7/82u7t7McCAAhKAAZfcmNnSURKAApfdGltZXN0YW1wTAAKX2NvbnRlc3RJZHQAEkxqYXZhL2xhbmcvU3RyaW5nO0wAEF9lbGVjdGlvbkV2ZW50SWRxAH4AAUwAC19lbGVjdGlvbklkcQB+AAFbABFfcmVjZWlwdFNpZ25hdHVyZXQAAltCTAATX3JldHVybkNvZGVzTWVzc2FnZXEAfgABWwAOX3ZvdGluZ1JlY2VpcHRxAH4AAnhwAAAAAAAAH5wAAAEv3kOdknQABjAwMDEwNnQABjIwMDcwMXQAAjA2dXIAAltCrPMX+AYIVOACAAB4cAAAAQArbVBU2jABOoExcUaxZfWRJpSeZUuVdjZG9qWy9dJzHikPfljkVYDSLD36WdTB6D+c3uGgSZsEZilQs9+D8f/k+xIFJLVsfywCl/Exrfjsyn9z/hfJdbzLXD/vxlmWPX7LRyLu0pkt6GeS7pQ/7mvWTu1zrC4Km0rs9A6AYwoDzGCkXUk4LTAC3zY6K3r2Roxnz/XGACEOv+XyzgR5XnG/GP7hpabMknu7bO2PNm/X8UJUrPTlex4mt49p84WRdBRuT4tGlMX+ZIK7H2uWMaeJMp7eMbwmQQjHXqs8Qs46wP+s09nb2m6du3Si2D5IBa/Wu5ppitTmB3wdfqFPq+O8cHVxAH4ABwAAABxrTU5CWXBCOU81VytSeTVsUW9hVGhNL1dRK0E9,eba388f8-a494-4b33-a8f4-b3f98f900885,8092," +
        GIVEN_VOTING_RECEIPT + COMMA + GIVEN_CONTEST_ID + COMMA + "99" + COMMA +
        GIVEN_ELECTION_EVENT_ID + ",200701.40.40.40.000000.1";
    private static final String SAMPLE_LINE_WITH_OTHER_ELECTION_EVENT_ID_VALUE = "4028806a2fde07b5012fde439de0000a,7a75e04b-6e7e-4430-b0d3-ae5637c2f08d,rO0ABXNyAD1jb20uc2N5dGwuZXZvdGUucHJvdG9jb2wuaW50ZWdyYXRpb24udm90aW5nLm1vZGVsLlJlY2VpcHRCZWFuw7/82u7t7McCAAhKAAZfcmNnSURKAApfdGltZXN0YW1wTAAKX2NvbnRlc3RJZHQAEkxqYXZhL2xhbmcvU3RyaW5nO0wAEF9lbGVjdGlvbkV2ZW50SWRxAH4AAUwAC19lbGVjdGlvbklkcQB+AAFbABFfcmVjZWlwdFNpZ25hdHVyZXQAAltCTAATX3JldHVybkNvZGVzTWVzc2FnZXEAfgABWwAOX3ZvdGluZ1JlY2VpcHRxAH4AAnhwAAAAAAAAH5wAAAEv3kOdknQABjAwMDEwNnQABjIwMDcwMXQAAjA2dXIAAltCrPMX+AYIVOACAAB4cAAAAQArbVBU2jABOoExcUaxZfWRJpSeZUuVdjZG9qWy9dJzHikPfljkVYDSLD36WdTB6D+c3uGgSZsEZilQs9+D8f/k+xIFJLVsfywCl/Exrfjsyn9z/hfJdbzLXD/vxlmWPX7LRyLu0pkt6GeS7pQ/7mvWTu1zrC4Km0rs9A6AYwoDzGCkXUk4LTAC3zY6K3r2Roxnz/XGACEOv+XyzgR5XnG/GP7hpabMknu7bO2PNm/X8UJUrPTlex4mt49p84WRdBRuT4tGlMX+ZIK7H2uWMaeJMp7eMbwmQQjHXqs8Qs46wP+s09nb2m6du3Si2D5IBa/Wu5ppitTmB3wdfqFPq+O8cHVxAH4ABwAAABxrTU5CWXBCOU81VytSeTVsUW9hVGhNL1dRK0E9,eba388f8-a494-4b33-a8f4-b3f98f900885,8092," +
        GIVEN_VOTING_RECEIPT + COMMA + GIVEN_CONTEST_ID + COMMA +
        GIVEN_ELECTION_ID + COMMA + "999999" + ",200701.40.40.40.000000.1";
    private static final String BROKEN_LINE = "*";
    private RcgVotingReceiptsMap map;
    private ResultsList results;

    /**
     * Creates a RcgVotingReceiptsHashMap for the tests.
     */
    @BeforeMethod(alwaysRun = true)
    public void createRcgVotingReceiptsHashMap() {
        map = new RcgVotingReceiptsHashMap();
    }

    /**
     * Creates a results list for the tests.
     */
    @BeforeMethod(alwaysRun = true)
    public void createResultsList() {
        results = new ResultsArrayList();
    }

    /**
     * Verifies that the map is empty by default.
     */
    @Test
    public void mustBeEmptyByDefault() {
        assertTrue(map.isEmpty());
    }

    /**
     * Verifies that the map does not contain a given sample RCG voting receipt by default.
     */
    @Test
    public void mustNotContainAGivenSampleRcgVotingReceiptByDefault() {
        assertFalse(map.containsVotingReceipt(GIVEN_VOTING_RECEIPT,
                GIVEN_CONTEST_ID, GIVEN_ELECTION_ID, GIVEN_ELECTION_EVENT_ID));
    }

    /**
     * Adding must not create an incident if the voting receipt to be added didn't already exist in the map.
     */
    @Test
    public void mustNotCreateAnIncidentWhenTryingToAddANewVotingReceipt() {
        map.addVotingReceiptOrAddIncident(GIVEN_SAMPLE_LINE, results);

        assertTrue(results.isEmpty());
    }

    /**
     * Adding a broken line should create an incident.
     */
    @Test
    public void mustCreateAnIncidentWhenTryingToAddABrokenLine() {
        map.addVotingReceiptOrAddIncident(BROKEN_LINE, results);
        assertTrue(results.contains(
                new RcgVotingReceiptBrokenLineIncident(BROKEN_LINE)));
    }

    /**
     * Adding must create an incident if the voting receipt to be added already existed in the map.
     */
    @Test
    public void mustCreateAnIncidentWhenTryingToAddAVotingReceiptForTheSecondTime() {
        map.addVotingReceiptOrAddIncident(GIVEN_SAMPLE_LINE, results);
        map.addVotingReceiptOrAddIncident(GIVEN_SAMPLE_LINE, results);
        assertTrue(results.contains(
                new DoubleOccurrenceOfRcgVotingReceiptIncident(
                    new RcgVotingReceipt(GIVEN_SAMPLE_LINE))));
    }

    /**
     * Adding must not create an incident if the voting receipt value is different.
     */
    @Test
    public void mustNotCreateAnIncidentWhenTryingToAddAVotingReceiptWithADifferentVotingReceiptValue() {
        map.addVotingReceiptOrAddIncident(GIVEN_SAMPLE_LINE, results);
        map.addVotingReceiptOrAddIncident(SAMPLE_LINE_WITH_OTHER_VOTING_RECEIPT_VALUE,
            results);

        assertTrue(results.isEmpty());
    }

    /**
     * Adding must not create an incident if the contest ID is different.
     */
    @Test
    public void mustNotCreateAnIncidentWhenTryingToAddAVotingReceiptWithADifferentContestId() {
        map.addVotingReceiptOrAddIncident(GIVEN_SAMPLE_LINE, results);
        map.addVotingReceiptOrAddIncident(SAMPLE_LINE_WITH_OTHER_VOTING_CONTEST_ID_VALUE,
            results);

        assertTrue(results.isEmpty());
    }

    /**
     * Adding must not create an incident if the election ID is different.
     */
    @Test
    public void mustNotCreateAnIncidentWhenTryingToAddAVotingReceiptWithADifferentElectionId() {
        map.addVotingReceiptOrAddIncident(GIVEN_SAMPLE_LINE, results);
        map.addVotingReceiptOrAddIncident(SAMPLE_LINE_WITH_OTHER_ELECTION_ID_VALUE,
            results);

        assertTrue(results.isEmpty());
    }

    /**
     * Adding must not create an incident if the election event ID is different.
     */
    @Test
    public void mustNotCreateAnIncidentWhenTryingToAddAVotingReceiptWithADifferentElectionEventId() {
        map.addVotingReceiptOrAddIncident(GIVEN_SAMPLE_LINE, results);
        map.addVotingReceiptOrAddIncident(SAMPLE_LINE_WITH_OTHER_ELECTION_EVENT_ID_VALUE,
            results);

        assertTrue(results.isEmpty());
    }

    /**
     * Verifies that the map contains an RCG hash after it has been added.
     */
    @Test
    public void mustContainAGivenSampleRcgVotingReceiptAfterAddingIt() {
        map.addVotingReceiptOrAddIncident(GIVEN_SAMPLE_LINE, results);
        assertTrue(map.containsVotingReceipt(GIVEN_VOTING_RECEIPT,
                GIVEN_CONTEST_ID, GIVEN_ELECTION_ID, GIVEN_ELECTION_EVENT_ID));
    }

    /**
     * Verifies that the map does not contain an RCG hash that hasn't been added.
     */
    @Test
    public void mustNotContainAGivenAbsentRcgVotingReceiptAfterAddingTheGivenRcgVotingReceipt() {
        map.addVotingReceiptOrAddIncident(GIVEN_SAMPLE_LINE, results);
        assertFalse(map.containsVotingReceipt(GIVEN_ABSENT_VOTING_RECEIPT,
                GIVEN_CONTEST_ID, GIVEN_ELECTION_ID, GIVEN_ELECTION_EVENT_ID));
    }
}
