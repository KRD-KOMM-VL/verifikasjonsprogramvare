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
package com.computas.zkpev2013.rcgvcs;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * Unit tests on the VotingReceipt class.
 */
public class VotingReceiptUnitTest {
    private static final String COMMA = ",";
    private static final String GIVEN_CONTEST_ID = "000018";
    private static final String GIVEN_ELECTION_ID = "01";
    private static final String GIVEN_ELECTION_EVENT_ID = "730071";
    private static final String OTHER_ID = "9999";
    private static final String OTHER_VOTING_RECEIPT = "uNrncIP7IwlNZfTcOQLveajaLIi7Ly1dpa7SJNjEybI=";
    private static final String GIVEN_VOTING_RECEIPT = "ESRciAwG4M71UWpIYIW0RW7RF4eSDYCT1V9Zatm1wsM=";
    private static final String GIVEN_SAMPLE_LINE = "8a84806e40521c6d014052329b3401ed,327754c3-ac5d-44ba-8413-dc29c0f36b42,rO0ABXNyAD1jb20uc2N5dGwuZXZvdGUucHJvdG9jb2wuaW50ZWdyYXRpb24udm90aW5nLm1vZGVsLlJlY2VpcHRCZWFuw7/82u7t7McCAApKAAZfcmNnSURKAApfdGltZXN0YW1wSgAGX3Zjc0lETAAKX2NvbnRlc3RJZHQAEkxqYXZhL2xhbmcvU3RyaW5nO0wAEF9lbGVjdGlvbkV2ZW50SWRxAH4AAUwAC19lbGVjdGlvbklkcQB+AAFbABRfcmVjZWlwdFNpZ25hdHVyZVJDR3QAAltCWwAUX3JlY2VpcHRTaWduYXR1cmVWQ1NxAH4AAkwAE19yZXR1cm5Db2Rlc01lc3NhZ2VxAH4AAVsADl92b3RpbmdSZWNlaXB0cQB+AAJ4cAAAAAAAAB+cAAABQFIykp0AAAAAAAAAAHQABjAwMDAxOHQABjczMDA3MXQAAjAxdXIAAltCrPMX+AYIVOACAAB4cAAAAQB+xHkHj5yiOnRUOjNgHlAcbraUfO9gaaUxdVJX3eUruirmh5XfPwS8Na+btvq+pXBc5QVVsu83iHYSymjYDQBBLv9m3MbKku23xj2MNQDvCDaC2thxt8GIQRl66WwtsOvKT8i57602N71QMwbxFTd6VIjvR+w3LSVkb2M91TxRKi19DFf9DEoe2rUezzNSMUKKGXF89qOgDv6DrjUTygsbInssz3cJBVEP+YfRZphpzUe3kifNuVCfSzZFwgKgJngrvCkPPdl4w3+NUj9youmpAHiWn0GpIFjvyn3wG5pSwzLwSaUk2z7HQ11zf0xSVSOjYUPehXJiUT9ZVFFx+ieDdXEAfgAHAAABAHF9BM0CkwHL4hp/tOSc4/yUFGE8M+dbb/fRBZlygckJNItPFC0YLlXgrIUQIKn90ILMlMDIT4a98qBbVSmS0j9gzTIlmFO85RQ2+CDNh5zMEmOn04sgEIUv+CDyBkbbDOy30hasinnMrQdRCRSXUsttKKD4B/CWq/ylHFg53b5Vu7aJaSpBxMj5+Z+ieQ3c4GcjC4QXEyp2C81mx5kpEtYpSooQGSuxzsW60MKLgqGJ+VxX6eC8CW9K/qR2BG+14hcW9zmA+qNGpyapyg4Y9JYTcJi+9ZiJ9PbchDNrpm8X8b6TXc0lfWc0vvKjb/TRD4uNc0qBgHhi1kaZOTF5Q5FwdXEAfgAHAAAALEVTUmNpQXdHNE03MVVXcElZSVcwUlc3UkY0ZVNEWUNUMVY5WmF0bTF3c009,56e0af43-11c5-499c-ab1b-80aea591a9cd,8092" +
        COMMA + GIVEN_VOTING_RECEIPT + COMMA + GIVEN_CONTEST_ID + COMMA +
        GIVEN_ELECTION_ID + COMMA + GIVEN_ELECTION_EVENT_ID;
    private static final String SAMPLE_LINE_WITH_OTHER_VOTING_RECEIPT_VALUE = "8a84806e40521c6d014052329b3401ed,327754c3-ac5d-44ba-8413-dc29c0f36b42,rO0ABXNyAD1jb20uc2N5dGwuZXZvdGUucHJvdG9jb2wuaW50ZWdyYXRpb24udm90aW5nLm1vZGVsLlJlY2VpcHRCZWFuw7/82u7t7McCAApKAAZfcmNnSURKAApfdGltZXN0YW1wSgAGX3Zjc0lETAAKX2NvbnRlc3RJZHQAEkxqYXZhL2xhbmcvU3RyaW5nO0wAEF9lbGVjdGlvbkV2ZW50SWRxAH4AAUwAC19lbGVjdGlvbklkcQB+AAFbABRfcmVjZWlwdFNpZ25hdHVyZVJDR3QAAltCWwAUX3JlY2VpcHRTaWduYXR1cmVWQ1NxAH4AAkwAE19yZXR1cm5Db2Rlc01lc3NhZ2VxAH4AAVsADl92b3RpbmdSZWNlaXB0cQB+AAJ4cAAAAAAAAB+cAAABQFIykp0AAAAAAAAAAHQABjAwMDAxOHQABjczMDA3MXQAAjAxdXIAAltCrPMX+AYIVOACAAB4cAAAAQB+xHkHj5yiOnRUOjNgHlAcbraUfO9gaaUxdVJX3eUruirmh5XfPwS8Na+btvq+pXBc5QVVsu83iHYSymjYDQBBLv9m3MbKku23xj2MNQDvCDaC2thxt8GIQRl66WwtsOvKT8i57602N71QMwbxFTd6VIjvR+w3LSVkb2M91TxRKi19DFf9DEoe2rUezzNSMUKKGXF89qOgDv6DrjUTygsbInssz3cJBVEP+YfRZphpzUe3kifNuVCfSzZFwgKgJngrvCkPPdl4w3+NUj9youmpAHiWn0GpIFjvyn3wG5pSwzLwSaUk2z7HQ11zf0xSVSOjYUPehXJiUT9ZVFFx+ieDdXEAfgAHAAABAHF9BM0CkwHL4hp/tOSc4/yUFGE8M+dbb/fRBZlygckJNItPFC0YLlXgrIUQIKn90ILMlMDIT4a98qBbVSmS0j9gzTIlmFO85RQ2+CDNh5zMEmOn04sgEIUv+CDyBkbbDOy30hasinnMrQdRCRSXUsttKKD4B/CWq/ylHFg53b5Vu7aJaSpBxMj5+Z+ieQ3c4GcjC4QXEyp2C81mx5kpEtYpSooQGSuxzsW60MKLgqGJ+VxX6eC8CW9K/qR2BG+14hcW9zmA+qNGpyapyg4Y9JYTcJi+9ZiJ9PbchDNrpm8X8b6TXc0lfWc0vvKjb/TRD4uNc0qBgHhi1kaZOTF5Q5FwdXEAfgAHAAAALEVTUmNpQXdHNE03MVVXcElZSVcwUlc3UkY0ZVNEWUNUMVY5WmF0bTF3c009,56e0af43-11c5-499c-ab1b-80aea591a9cd,8092" +
        COMMA + OTHER_VOTING_RECEIPT + COMMA + GIVEN_CONTEST_ID + COMMA +
        GIVEN_ELECTION_ID + COMMA + GIVEN_ELECTION_EVENT_ID;
    private static final String SAMPLE_LINE_WITH_OTHER_VOTING_CONTEST_ID_VALUE = "8a84806e40521c6d014052329b3401ed,327754c3-ac5d-44ba-8413-dc29c0f36b42,rO0ABXNyAD1jb20uc2N5dGwuZXZvdGUucHJvdG9jb2wuaW50ZWdyYXRpb24udm90aW5nLm1vZGVsLlJlY2VpcHRCZWFuw7/82u7t7McCAApKAAZfcmNnSURKAApfdGltZXN0YW1wSgAGX3Zjc0lETAAKX2NvbnRlc3RJZHQAEkxqYXZhL2xhbmcvU3RyaW5nO0wAEF9lbGVjdGlvbkV2ZW50SWRxAH4AAUwAC19lbGVjdGlvbklkcQB+AAFbABRfcmVjZWlwdFNpZ25hdHVyZVJDR3QAAltCWwAUX3JlY2VpcHRTaWduYXR1cmVWQ1NxAH4AAkwAE19yZXR1cm5Db2Rlc01lc3NhZ2VxAH4AAVsADl92b3RpbmdSZWNlaXB0cQB+AAJ4cAAAAAAAAB+cAAABQFIykp0AAAAAAAAAAHQABjAwMDAxOHQABjczMDA3MXQAAjAxdXIAAltCrPMX+AYIVOACAAB4cAAAAQB+xHkHj5yiOnRUOjNgHlAcbraUfO9gaaUxdVJX3eUruirmh5XfPwS8Na+btvq+pXBc5QVVsu83iHYSymjYDQBBLv9m3MbKku23xj2MNQDvCDaC2thxt8GIQRl66WwtsOvKT8i57602N71QMwbxFTd6VIjvR+w3LSVkb2M91TxRKi19DFf9DEoe2rUezzNSMUKKGXF89qOgDv6DrjUTygsbInssz3cJBVEP+YfRZphpzUe3kifNuVCfSzZFwgKgJngrvCkPPdl4w3+NUj9youmpAHiWn0GpIFjvyn3wG5pSwzLwSaUk2z7HQ11zf0xSVSOjYUPehXJiUT9ZVFFx+ieDdXEAfgAHAAABAHF9BM0CkwHL4hp/tOSc4/yUFGE8M+dbb/fRBZlygckJNItPFC0YLlXgrIUQIKn90ILMlMDIT4a98qBbVSmS0j9gzTIlmFO85RQ2+CDNh5zMEmOn04sgEIUv+CDyBkbbDOy30hasinnMrQdRCRSXUsttKKD4B/CWq/ylHFg53b5Vu7aJaSpBxMj5+Z+ieQ3c4GcjC4QXEyp2C81mx5kpEtYpSooQGSuxzsW60MKLgqGJ+VxX6eC8CW9K/qR2BG+14hcW9zmA+qNGpyapyg4Y9JYTcJi+9ZiJ9PbchDNrpm8X8b6TXc0lfWc0vvKjb/TRD4uNc0qBgHhi1kaZOTF5Q5FwdXEAfgAHAAAALEVTUmNpQXdHNE03MVVXcElZSVcwUlc3UkY0ZVNEWUNUMVY5WmF0bTF3c009,56e0af43-11c5-499c-ab1b-80aea591a9cd,8092" +
        COMMA + GIVEN_VOTING_RECEIPT + COMMA + OTHER_ID + COMMA +
        GIVEN_ELECTION_ID + COMMA + GIVEN_ELECTION_EVENT_ID;
    private static final String SAMPLE_LINE_WITH_OTHER_ELECTION_ID_VALUE = "8a84806e40521c6d014052329b3401ed,327754c3-ac5d-44ba-8413-dc29c0f36b42,rO0ABXNyAD1jb20uc2N5dGwuZXZvdGUucHJvdG9jb2wuaW50ZWdyYXRpb24udm90aW5nLm1vZGVsLlJlY2VpcHRCZWFuw7/82u7t7McCAApKAAZfcmNnSURKAApfdGltZXN0YW1wSgAGX3Zjc0lETAAKX2NvbnRlc3RJZHQAEkxqYXZhL2xhbmcvU3RyaW5nO0wAEF9lbGVjdGlvbkV2ZW50SWRxAH4AAUwAC19lbGVjdGlvbklkcQB+AAFbABRfcmVjZWlwdFNpZ25hdHVyZVJDR3QAAltCWwAUX3JlY2VpcHRTaWduYXR1cmVWQ1NxAH4AAkwAE19yZXR1cm5Db2Rlc01lc3NhZ2VxAH4AAVsADl92b3RpbmdSZWNlaXB0cQB+AAJ4cAAAAAAAAB+cAAABQFIykp0AAAAAAAAAAHQABjAwMDAxOHQABjczMDA3MXQAAjAxdXIAAltCrPMX+AYIVOACAAB4cAAAAQB+xHkHj5yiOnRUOjNgHlAcbraUfO9gaaUxdVJX3eUruirmh5XfPwS8Na+btvq+pXBc5QVVsu83iHYSymjYDQBBLv9m3MbKku23xj2MNQDvCDaC2thxt8GIQRl66WwtsOvKT8i57602N71QMwbxFTd6VIjvR+w3LSVkb2M91TxRKi19DFf9DEoe2rUezzNSMUKKGXF89qOgDv6DrjUTygsbInssz3cJBVEP+YfRZphpzUe3kifNuVCfSzZFwgKgJngrvCkPPdl4w3+NUj9youmpAHiWn0GpIFjvyn3wG5pSwzLwSaUk2z7HQ11zf0xSVSOjYUPehXJiUT9ZVFFx+ieDdXEAfgAHAAABAHF9BM0CkwHL4hp/tOSc4/yUFGE8M+dbb/fRBZlygckJNItPFC0YLlXgrIUQIKn90ILMlMDIT4a98qBbVSmS0j9gzTIlmFO85RQ2+CDNh5zMEmOn04sgEIUv+CDyBkbbDOy30hasinnMrQdRCRSXUsttKKD4B/CWq/ylHFg53b5Vu7aJaSpBxMj5+Z+ieQ3c4GcjC4QXEyp2C81mx5kpEtYpSooQGSuxzsW60MKLgqGJ+VxX6eC8CW9K/qR2BG+14hcW9zmA+qNGpyapyg4Y9JYTcJi+9ZiJ9PbchDNrpm8X8b6TXc0lfWc0vvKjb/TRD4uNc0qBgHhi1kaZOTF5Q5FwdXEAfgAHAAAALEVTUmNpQXdHNE03MVVXcElZSVcwUlc3UkY0ZVNEWUNUMVY5WmF0bTF3c009,56e0af43-11c5-499c-ab1b-80aea591a9cd,8092" +
        COMMA + GIVEN_VOTING_RECEIPT + COMMA + GIVEN_CONTEST_ID + COMMA +
        OTHER_ID + COMMA + GIVEN_ELECTION_EVENT_ID;
    private static final String SAMPLE_LINE_WITH_OTHER_ELECTION_EVENT_ID_VALUE = "8a84806e40521c6d014052329b3401ed,327754c3-ac5d-44ba-8413-dc29c0f36b42,rO0ABXNyAD1jb20uc2N5dGwuZXZvdGUucHJvdG9jb2wuaW50ZWdyYXRpb24udm90aW5nLm1vZGVsLlJlY2VpcHRCZWFuw7/82u7t7McCAApKAAZfcmNnSURKAApfdGltZXN0YW1wSgAGX3Zjc0lETAAKX2NvbnRlc3RJZHQAEkxqYXZhL2xhbmcvU3RyaW5nO0wAEF9lbGVjdGlvbkV2ZW50SWRxAH4AAUwAC19lbGVjdGlvbklkcQB+AAFbABRfcmVjZWlwdFNpZ25hdHVyZVJDR3QAAltCWwAUX3JlY2VpcHRTaWduYXR1cmVWQ1NxAH4AAkwAE19yZXR1cm5Db2Rlc01lc3NhZ2VxAH4AAVsADl92b3RpbmdSZWNlaXB0cQB+AAJ4cAAAAAAAAB+cAAABQFIykp0AAAAAAAAAAHQABjAwMDAxOHQABjczMDA3MXQAAjAxdXIAAltCrPMX+AYIVOACAAB4cAAAAQB+xHkHj5yiOnRUOjNgHlAcbraUfO9gaaUxdVJX3eUruirmh5XfPwS8Na+btvq+pXBc5QVVsu83iHYSymjYDQBBLv9m3MbKku23xj2MNQDvCDaC2thxt8GIQRl66WwtsOvKT8i57602N71QMwbxFTd6VIjvR+w3LSVkb2M91TxRKi19DFf9DEoe2rUezzNSMUKKGXF89qOgDv6DrjUTygsbInssz3cJBVEP+YfRZphpzUe3kifNuVCfSzZFwgKgJngrvCkPPdl4w3+NUj9youmpAHiWn0GpIFjvyn3wG5pSwzLwSaUk2z7HQ11zf0xSVSOjYUPehXJiUT9ZVFFx+ieDdXEAfgAHAAABAHF9BM0CkwHL4hp/tOSc4/yUFGE8M+dbb/fRBZlygckJNItPFC0YLlXgrIUQIKn90ILMlMDIT4a98qBbVSmS0j9gzTIlmFO85RQ2+CDNh5zMEmOn04sgEIUv+CDyBkbbDOy30hasinnMrQdRCRSXUsttKKD4B/CWq/ylHFg53b5Vu7aJaSpBxMj5+Z+ieQ3c4GcjC4QXEyp2C81mx5kpEtYpSooQGSuxzsW60MKLgqGJ+VxX6eC8CW9K/qR2BG+14hcW9zmA+qNGpyapyg4Y9JYTcJi+9ZiJ9PbchDNrpm8X8b6TXc0lfWc0vvKjb/TRD4uNc0qBgHhi1kaZOTF5Q5FwdXEAfgAHAAAALEVTUmNpQXdHNE03MVVXcElZSVcwUlc3UkY0ZVNEWUNUMVY5WmF0bTF3c009,56e0af43-11c5-499c-ab1b-80aea591a9cd,8092" +
        COMMA + GIVEN_VOTING_RECEIPT + COMMA + GIVEN_CONTEST_ID + COMMA +
        GIVEN_ELECTION_ID + COMMA + OTHER_ID;
    private VotingReceipt votingReceipt;

    /**
    * Creates a voting receipt to run the tests against.
    */
    @BeforeMethod
    public void createVotingReceipt() {
        votingReceipt = new VotingReceipt(GIVEN_SAMPLE_LINE);
    }

    /**
     * Verifies that the constructor sets the voting receipt correctly from
     * a line.
     */
    @Test
    public void constructorMustSetTheVotingReceiptCorrectly() {
        assertEquals(votingReceipt.getVotingReceipt(), GIVEN_VOTING_RECEIPT);
    }

    /**
     * Verifies that the constructor sets the contest ID correctly from
     * a line.
     */
    @Test
    public void constructorMustSetTheContestIdCorrectly() {
        assertEquals(votingReceipt.getContestId(), GIVEN_CONTEST_ID);
    }

    /**
     * Verifies that the constructor sets the election ID correctly from
     * a line.
     */
    @Test
    public void constructorMustSetTheElectionIdCorrectly() {
        assertEquals(votingReceipt.getElectionId(), GIVEN_ELECTION_ID);
    }

    /**
     * Verifies that the constructor sets the election event ID correctly from
     * a line.
     */
    @Test
    public void constructorMustSetTheElectionEventIdCorrectly() {
        assertEquals(votingReceipt.getElectionEventId(), GIVEN_ELECTION_EVENT_ID);
    }

    /**
     * Verifies that a voting receipt is equal to itself.
     */
    @Test
    public void mustBeEqualToItself() {
        assertEquals(votingReceipt, votingReceipt);
    }

    /**
     * Verifies that a voting receipt has a consistent hashCode.
     */
    @Test
    public void mustHaveSameHashCodeAsItself() {
        assertEquals(votingReceipt.hashCode(), votingReceipt.hashCode());
    }

    /**
     * Verifies that voting receipt is not equal to null.
     */
    @Test
    public void mustNotBeEqualToNull() {
        Object nullObject = null;
        assertFalse(votingReceipt.equals(nullObject));
    }

    /**
     * Verifies that a voting receipt is not equal to an object of another class. This is used as the test case.
     */
    @Test
    public void mustNotBeEqualToAnObjectOfAnotherClass() {
        assertFalse(votingReceipt.equals(this));
    }

    /**
     * Verifies that the voting receipt is equal to another voting receipt parsed from the same line.
     */
    @Test
    public void mustBeEqualToAnotherVotingReceiptParsedFromTheSameLine() {
        assertEquals(votingReceipt, new VotingReceipt(GIVEN_SAMPLE_LINE));
    }

    /**
     * Verifies that the voting receipt has the same hashCode as another voting receipt parsed from the same line.
     */
    @Test
    public void mustHaveSameHashCodeAsAnotherVotingReceiptParsedFromTheSameLine() {
        assertEquals(votingReceipt.hashCode(),
            new VotingReceipt(GIVEN_SAMPLE_LINE).hashCode());
    }

    /**
     * Verifies that the voting receipt is not equal to another voting receipted parsed from a line with a different voting receipt value.
     */
    @Test
    public void mustNotBeEqualToAnotherVotingReceiptWithADifferentVotingReceiptValue() {
        assertFalse(votingReceipt.equals(
                new VotingReceipt(SAMPLE_LINE_WITH_OTHER_VOTING_RECEIPT_VALUE)));
    }

    /**
     * Verifies that the voting receipt is not equal to another voting receipted parsed from a line with a different contest ID value.
     */
    @Test
    public void mustNotBeEqualToAnotherVotingReceiptWithADifferentContestIdValue() {
        assertFalse(votingReceipt.equals(
                new VotingReceipt(
                    SAMPLE_LINE_WITH_OTHER_VOTING_CONTEST_ID_VALUE)));
    }

    /**
     * Verifies that the voting receipt is not equal to another voting receipted parsed from a line with a different election ID value.
     */
    @Test
    public void mustNotBeEqualToAnotherVotingReceiptWithADifferentElectionIdValue() {
        assertFalse(votingReceipt.equals(
                new VotingReceipt(SAMPLE_LINE_WITH_OTHER_ELECTION_ID_VALUE)));
    }

    /**
     * Verifies that the voting receipt is not equal to another voting receipted parsed from a line with a different election event ID value.
     */
    @Test
    public void mustNotBeEqualToAnotherVotingReceiptWithADifferentElectionEventIdValue() {
        assertFalse(votingReceipt.equals(
                new VotingReceipt(
                    SAMPLE_LINE_WITH_OTHER_ELECTION_EVENT_ID_VALUE)));
    }
}
