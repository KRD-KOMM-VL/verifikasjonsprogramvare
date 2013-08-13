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
 * Unit test against RCG voting receipts.
 *
 */
public class RcgVotingReceiptUnitTest {
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
    private RcgVotingReceipt rcgVotingReceipt;

    /**
     * Creates a RCG voting receipt to run the tests against.
     */
    @BeforeMethod
    public void createRcgVotingReceipt() {
        rcgVotingReceipt = new RcgVotingReceipt(GIVEN_SAMPLE_LINE);
    }

    /**
     * Verifies that the constructor sets the voting receipt correctly from
     * a line.
     */
    @Test
    public void constructorMustSetTheVotingReceiptCorrectly() {
        assertEquals(rcgVotingReceipt.getVotingReceipt(), GIVEN_VOTING_RECEIPT);
    }

    /**
     * Verifies that the constructor sets the contest ID correctly from
     * a line.
     */
    @Test
    public void constructorMustSetTheContestIdCorrectly() {
        assertEquals(rcgVotingReceipt.getContestId(), GIVEN_CONTEST_ID);
    }

    /**
     * Verifies that the constructor sets the election ID correctly from
     * a line.
     */
    @Test
    public void constructorMustSetTheElectionIdCorrectly() {
        assertEquals(rcgVotingReceipt.getElectionId(), GIVEN_ELECTION_ID);
    }

    /**
     * Verifies that the constructor sets the election event ID correctly from
     * a line.
     */
    @Test
    public void constructorMustSetTheElectionEventIdCorrectly() {
        assertEquals(rcgVotingReceipt.getElectionEventId(),
            GIVEN_ELECTION_EVENT_ID);
    }

    /**
     * Verifies that a voting receipt is equal to itself.
     */
    @Test
    public void mustBeEqualToItself() {
        assertEquals(rcgVotingReceipt, rcgVotingReceipt);
    }

    /**
     * Verifies that a voting receipt has a consistent hashCode.
     */
    @Test
    public void mustHaveSameHashCodeAsItself() {
        assertEquals(rcgVotingReceipt.hashCode(), rcgVotingReceipt.hashCode());
    }

    /**
     * Verifies that voting receipt is not equal to null.
     */
    @Test
    public void mustNotBeEqualToNull() {
        Object nullObject = null;
        assertFalse(rcgVotingReceipt.equals(nullObject));
    }

    /**
     * Verifies that a voting receipt is not equal to an object of another class. This is used as the test case.
     */
    @Test
    public void mustNotBeEqualToAnObjectOfAnotherClass() {
        assertFalse(rcgVotingReceipt.equals(this));
    }

    /**
     * Verifies that the voting receipt is equal to another voting receipt parsed from the same line.
     */
    @Test
    public void mustBeEqualToAnotherRcgVotingReceiptParsedFromTheSameLine() {
        assertEquals(rcgVotingReceipt, new RcgVotingReceipt(GIVEN_SAMPLE_LINE));
    }

    /**
     * Verifies that the voting receipt has the same hashCode as another voting receipt parsed from the same line.
     */
    @Test
    public void mustHaveSameHashCodeAsAnotherRcgVotingReceiptParsedFromTheSameLine() {
        assertEquals(rcgVotingReceipt.hashCode(),
            new RcgVotingReceipt(GIVEN_SAMPLE_LINE).hashCode());
    }

    /**
     * Verifies that the voting receipt is not equal to another voting receipted parsed from a line with a different voting receipt value.
     */
    @Test
    public void mustNotBeEqualToAnotherVotingReceiptWithADifferentVotingReceiptValue() {
        assertFalse(rcgVotingReceipt.equals(
                new RcgVotingReceipt(
                    SAMPLE_LINE_WITH_OTHER_VOTING_RECEIPT_VALUE)));
    }

    /**
     * Verifies that the voting receipt is not equal to another voting receipted parsed from a line with a different contest ID value.
     */
    @Test
    public void mustNotBeEqualToAnotherVotingReceiptWithADifferentContestIdValue() {
        assertFalse(rcgVotingReceipt.equals(
                new RcgVotingReceipt(
                    SAMPLE_LINE_WITH_OTHER_VOTING_CONTEST_ID_VALUE)));
    }

    /**
     * Verifies that the voting receipt is not equal to another voting receipted parsed from a line with a different election ID value.
     */
    @Test
    public void mustNotBeEqualToAnotherVotingReceiptWithADifferentElectionIdValue() {
        assertFalse(rcgVotingReceipt.equals(
                new RcgVotingReceipt(SAMPLE_LINE_WITH_OTHER_ELECTION_ID_VALUE)));
    }

    /**
     * Verifies that the voting receipt is not equal to another voting receipted parsed from a line with a different election event ID value.
     */
    @Test
    public void mustNotBeEqualToAnotherVotingReceiptWithADifferentElectionEventIdValue() {
        assertFalse(rcgVotingReceipt.equals(
                new RcgVotingReceipt(
                    SAMPLE_LINE_WITH_OTHER_ELECTION_EVENT_ID_VALUE)));
    }
}
