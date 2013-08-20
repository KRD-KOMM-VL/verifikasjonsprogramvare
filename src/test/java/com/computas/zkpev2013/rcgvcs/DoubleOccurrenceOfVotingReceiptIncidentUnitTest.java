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
 * Unit tests on the DoubleOccurrenceOfVotingReceiptIncident class.
 *
 */
public class DoubleOccurrenceOfVotingReceiptIncidentUnitTest {
    private static final String GIVEN_VOTING_RECEIPT = "ESRciAwG4M71UWpIYIW0RW7RF4eSDYCT1V9Zatm1wsM=";
    private static final String GIVEN_SAMPLE_LINE = "8a84806e40521c6d014052329b3401ed,327754c3-ac5d-44ba-8413-dc29c0f36b42,rO0ABXNyAD1jb20uc2N5dGwuZXZvdGUucHJvdG9jb2wuaW50ZWdyYXRpb24udm90aW5nLm1vZGVsLlJlY2VpcHRCZWFuw7/82u7t7McCAApKAAZfcmNnSURKAApfdGltZXN0YW1wSgAGX3Zjc0lETAAKX2NvbnRlc3RJZHQAEkxqYXZhL2xhbmcvU3RyaW5nO0wAEF9lbGVjdGlvbkV2ZW50SWRxAH4AAUwAC19lbGVjdGlvbklkcQB+AAFbABRfcmVjZWlwdFNpZ25hdHVyZVJDR3QAAltCWwAUX3JlY2VpcHRTaWduYXR1cmVWQ1NxAH4AAkwAE19yZXR1cm5Db2Rlc01lc3NhZ2VxAH4AAVsADl92b3RpbmdSZWNlaXB0cQB+AAJ4cAAAAAAAAB+cAAABQFIykp0AAAAAAAAAAHQABjAwMDAxOHQABjczMDA3MXQAAjAxdXIAAltCrPMX+AYIVOACAAB4cAAAAQB+xHkHj5yiOnRUOjNgHlAcbraUfO9gaaUxdVJX3eUruirmh5XfPwS8Na+btvq+pXBc5QVVsu83iHYSymjYDQBBLv9m3MbKku23xj2MNQDvCDaC2thxt8GIQRl66WwtsOvKT8i57602N71QMwbxFTd6VIjvR+w3LSVkb2M91TxRKi19DFf9DEoe2rUezzNSMUKKGXF89qOgDv6DrjUTygsbInssz3cJBVEP+YfRZphpzUe3kifNuVCfSzZFwgKgJngrvCkPPdl4w3+NUj9youmpAHiWn0GpIFjvyn3wG5pSwzLwSaUk2z7HQ11zf0xSVSOjYUPehXJiUT9ZVFFx+ieDdXEAfgAHAAABAHF9BM0CkwHL4hp/tOSc4/yUFGE8M+dbb/fRBZlygckJNItPFC0YLlXgrIUQIKn90ILMlMDIT4a98qBbVSmS0j9gzTIlmFO85RQ2+CDNh5zMEmOn04sgEIUv+CDyBkbbDOy30hasinnMrQdRCRSXUsttKKD4B/CWq/ylHFg53b5Vu7aJaSpBxMj5+Z+ieQ3c4GcjC4QXEyp2C81mx5kpEtYpSooQGSuxzsW60MKLgqGJ+VxX6eC8CW9K/qR2BG+14hcW9zmA+qNGpyapyg4Y9JYTcJi+9ZiJ9PbchDNrpm8X8b6TXc0lfWc0vvKjb/TRD4uNc0qBgHhi1kaZOTF5Q5FwdXEAfgAHAAAALEVTUmNpQXdHNE03MVVXcElZSVcwUlc3UkY0ZVNEWUNUMVY5WmF0bTF3c009,56e0af43-11c5-499c-ab1b-80aea591a9cd,8092," +
        GIVEN_VOTING_RECEIPT + ",000018,01,730071";
    private static final String OTHER_SAMPLE_LINE = "8a84806e40521c6d01405231e67c01eb,9007d001-c9b9-47da-acc7-9973353899dc,rO0ABXNyAD1jb20uc2N5dGwuZXZvdGUucHJvdG9jb2wuaW50ZWdyYXRpb24udm90aW5nLm1vZGVsLlJlY2VpcHRCZWFuw7/82u7t7McCAApKAAZfcmNnSURKAApfdGltZXN0YW1wSgAGX3Zjc0lETAAKX2NvbnRlc3RJZHQAEkxqYXZhL2xhbmcvU3RyaW5nO0wAEF9lbGVjdGlvbkV2ZW50SWRxAH4AAUwAC19lbGVjdGlvbklkcQB+AAFbABRfcmVjZWlwdFNpZ25hdHVyZVJDR3QAAltCWwAUX3JlY2VpcHRTaWduYXR1cmVWQ1NxAH4AAkwAE19yZXR1cm5Db2Rlc01lc3NhZ2VxAH4AAVsADl92b3RpbmdSZWNlaXB0cQB+AAJ4cAAAAAAAAB+cAAABQFIx4bcAAAAAAAAAAHQABjAwMDAxOHQABjczMDA3MXQAAjAxdXIAAltCrPMX+AYIVOACAAB4cAAAAQCCekImLedOpbmK7K+/F1c16NVAMdvUm8Hu0+P1+q4xtLNo0hKrPIKKxv1csptsw10QFukwfXU++sUy+os1EgKfWQJjbiMNGTlhl6gxtdrzL129rufYLpuBP6RnxjRn+igua8hDz5tlvvtFyjRjyZrdRY94LOaneQtykT9Hl32gW97lQ9OIJEvNEtG5havNh/LP88k1OdK17os7iSuelB7Ia4oHW/fgUv//D0LgMKZzI+QModTkdgrVsppkgwgx/3r92Xnj9cFJGrt3UFB9opBHtqW8bDgsph+kyrV+amm/tM5kqRMnSdlOn8U4/RIxQ/F1vsP5VzR+jOj9Jx1Q1lsrdXEAfgAHAAABAHPtzM/3G4l1qYzgI1WGXAB5hrm5ZZlESP2VTXeikXfqXwk9RoZYsOtb09Gxg7ShDeSWazuxmvC1u3hOxD0WFyc4TYv+J5zjXkbtWiZk9kFMIl60LYT2IW+ej7NF+5T8cUnZJmupCaCevdbgng8t4EVAh1OEvDZVU1jZ7VEuhrWH0kXC5h9r7ZVkKGSxRjEZWYlEx2+9CpEwlmLtdc3LGrhBMk4C1eiHd0Rgo+UUOHYjm8z6heeALSpUailpSC/7lHnNZtgOHpkgeEZgfEy6ffAR8DJwBMIe2o1O6qVNJQyDDLL4E+Xz1VTYnOVlXuFfn6k8ZM0FwpwGEr4xLri4CQZwdXEAfgAHAAAALHVOcm5jSVA3SXdsTlpmVGNPUUx2ZWFqYUxJaTdMeTFkcGE3U0pOakV5Ykk9,41136d91-f9ff-47e0-afbe-d1c45b38d1b6,8092,uNrncIP7IwlNZfTcOQLveajaLIi7Ly1dpa7SJNjEybI=,000018,01,730071";
    private DoubleOccurrenceOfVotingReceiptIncident incident;

    /**
     * Creates an incident to test against.
     */
    @BeforeMethod
    public void createDoubleOccurrenceOfVotingReceiptIncident() {
        incident = new DoubleOccurrenceOfVotingReceiptIncident(new VotingReceipt(
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
    public void mustBeEqualToAnotherDoubleOccurrenceOfVotingReceiptIncidentWithTheSameVotingReceipt() {
        assertEquals(incident,
            new DoubleOccurrenceOfVotingReceiptIncident(
                new VotingReceipt(GIVEN_SAMPLE_LINE)));
    }

    /**
     * Verifies that the incident has the same hashCode as another incident with the same voting receipt.
     */
    @Test
    public void mustHaveSameHashCodeAsAnotherDoubleOccurrenceOfVotingReceiptIncidentWithTheSameVotingReceipt() {
        assertEquals(incident.hashCode(),
            new DoubleOccurrenceOfVotingReceiptIncident(
                new VotingReceipt(GIVEN_SAMPLE_LINE)).hashCode());
    }

    /**
     * Verifies that the incident is not equal to another incident with another voting receipt.
     */
    @Test
    public void mustNotBeEqualToAnotherDoubleOccurrenceOfVotingReceiptIncidentWithAnotherVotingReceipt() {
        assertFalse(incident.equals(
                new DoubleOccurrenceOfVotingReceiptIncident(
                    new VotingReceipt(OTHER_SAMPLE_LINE))));
    }

    /**
     * Verifies that toString produced a comma-separated line with the correct
     * values.
     */
    @Test
    public void toStringMustBeCommaSeparatedline() {
        assertEquals(incident.toString(),
            "DoubleOccurrenceOfVotingReceiptIncident," + GIVEN_VOTING_RECEIPT);
    }
}
