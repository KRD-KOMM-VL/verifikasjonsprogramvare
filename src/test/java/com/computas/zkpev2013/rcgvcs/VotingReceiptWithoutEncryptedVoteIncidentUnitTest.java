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
 * Unit tests on the VotingReceiptWithoutEncryptedVoteIncident class.
 */
public class VotingReceiptWithoutEncryptedVoteIncidentUnitTest {
    private static final String GIVEN_VOTING_RECEIPT = "3fy3CmmwLn8TV4/krRcLFJu0ZvmdzyW3pg0zypdTdlI=";
    private static final String GIVEN_SAMPLE_LINE = "8a84806e40521c6d01405224937601d5,25ee33bf-7f7f-45ad-b495-9b1d8fce3eaf,rO0ABXNyAD1jb20uc2N5dGwuZXZvdGUucHJvdG9jb2wuaW50ZWdyYXRpb24udm90aW5nLm1vZGVsLlJlY2VpcHRCZWFuw7/82u7t7McCAApKAAZfcmNnSURKAApfdGltZXN0YW1wSgAGX3Zjc0lETAAKX2NvbnRlc3RJZHQAEkxqYXZhL2xhbmcvU3RyaW5nO0wAEF9lbGVjdGlvbkV2ZW50SWRxAH4AAUwAC19lbGVjdGlvbklkcQB+AAFbABRfcmVjZWlwdFNpZ25hdHVyZVJDR3QAAltCWwAUX3JlY2VpcHRTaWduYXR1cmVWQ1NxAH4AAkwAE19yZXR1cm5Db2Rlc01lc3NhZ2VxAH4AAVsADl92b3RpbmdSZWNlaXB0cQB+AAJ4cAAAAAAAAB+cAAABQFIki9AAAAAAAAAAAHQABjAwMDAwN3QABjczMDA3MXQAAjAxdXIAAltCrPMX+AYIVOACAAB4cAAAAQCB9Gqa5CPip7k7xEHfLMni3L1eRPr9bTTaGN+acMcIF8J/LS8sUNR7XEnOT7/FUbgdbtKLSMnyK4qhYuwAFK+3eKox4aStQMlaAIy8NIhkAzDLRz0tI/iRiwk3Ox9Nyl0VaQbKpvXNjw8KLVLt+DUmHBm3J1wgJLdKn4LEguxKZtYygJ4oN00O8W0d4Oixw51DePSuM9VVhxiS34/dXBFX9pJTaZzqQN+23kCdR5n5S2eY/MvsZhVYgXMoY7ZqR56Z9TsMh3eFbLEb1jm2GHwXPXOGmdJlqYSFbwk7xR07zKWmLhaBXCBRmtxfSru+VkGXhOMLpeKu3Nfy61KDW5andXEAfgAHAAABAL29eS1PHxwhRnLT8IJbfGXTosIsxoRRb1nZLBVoN3cDESv7mRBeJNCIFrq1Lw1sQaTA2jde+wmXVzhNC+dPkdWoO0y9zdyDTreAfDdWw5tjrxWDpJCBl4v/Wx2rxJSQ3uc6r3Zbg9nuG/HlU5zwF8AnzWYC4Nb2hwqvEiPy8+p32EfM5h2JeqFpG8fDz0/IQAc0i78COKyHzLGThFPlIqAcK4QPuYVaWJ59KiAMEDlSD/Iqu08DdQK3if+ok4LKBdYBO5YKvnmTGR05qhB8d4TCIE4s7rW8xx9xn3mhi8xgwGv8ZngwZCZNWzT6+SMR7DRddBO2fiT7j0+AM2fBDeBwdXEAfgAHAAAALDNmeTNDbW13TG44VFY0L2tyUmNMRkp1MFp2bWR6eVczcGcwenlwZFRkbEk9,3e287863-6cb6-474a-be78-6e91ffba8420,8092," +
        GIVEN_VOTING_RECEIPT + ",000007,01,730071";
    private static final String OTHER_SAMPLE_LINE = "8a84806e40521c6d01405225475701d7,a7a1158d-e5fb-47f8-b8e6-f3acf263f44a,rO0ABXNyAD1jb20uc2N5dGwuZXZvdGUucHJvdG9jb2wuaW50ZWdyYXRpb24udm90aW5nLm1vZGVsLlJlY2VpcHRCZWFuw7/82u7t7McCAApKAAZfcmNnSURKAApfdGltZXN0YW1wSgAGX3Zjc0lETAAKX2NvbnRlc3RJZHQAEkxqYXZhL2xhbmcvU3RyaW5nO0wAEF9lbGVjdGlvbkV2ZW50SWRxAH4AAUwAC19lbGVjdGlvbklkcQB+AAFbABRfcmVjZWlwdFNpZ25hdHVyZVJDR3QAAltCWwAUX3JlY2VpcHRTaWduYXR1cmVWQ1NxAH4AAkwAE19yZXR1cm5Db2Rlc01lc3NhZ2VxAH4AAVsADl92b3RpbmdSZWNlaXB0cQB+AAJ4cAAAAAAAAB+cAAABQFIlQqAAAAAAAAAAAHQABjAwMDAwN3QABjczMDA3MXQAAjAxdXIAAltCrPMX+AYIVOACAAB4cAAAAQBiKaW2vl2y9tsMeYg17AdDB913xJobH6Xwh0gF4QR607fVBMJtdsV0nr60KPzOKpgn3BOR6kLC1hHEif0Gf91OlqiFXG9HExDr0dA7Cp0Rwwd8hlcRW/wo2hPng+IQomGG5UyHwnQpWLWGgIrHAjJ2jBYhazHMY786vyc6GoPPEnmcEqfrj/Ku2a79bksC3o2cMLklPqKC5HdmAOiwsG1eyjJpyCB7+aosTh25cDsD6qU7aC/1hie0Z0QhHbdNXslwDHmYSa2nsceP5H65v6stlN7QkUct2wLszDHbgseJRhkzGFIWbgr0NrzRwFKs9N4cAbv/hjkRagEy3uXptrTvdXEAfgAHAAABAKFdMMRzeHus0MMralhJdFzfd5ZbTqGAWBcV9EehM4vFEhe6L/VU2Oc09JIg1T2aWgMkoYncS6/28vD9DchnXQMKgbOua/TraWU6Zl4RUnNKOJc6sqR4i2AGvrDhPpBW9oysBPOIh6u0pKpge+4We9bQ6EOczR8zKM2QO+MQcfFRVtCuo+lM3Hq4pPeeV7Y/qkLRwYfd5kOMkbZGsy617n4/M6HDKaBFRdXixpm3EHDvm7PXqyuit2lrY+JzXtO+QUVMDO6PeP7esJkTMni1mmaI7jEIMNvFThOslF8JxxvXq1lsy/2x+uhruh2aI1dixTo4ICEQSY+5T22wSxTM0ylwdXEAfgAHAAAALE83Q1Y2b3NkamFWQkorMGpKUzgwY28wZXY3K1hLRk1sU3ZwTjdGU212NlU9,dc2574de-cef9-4a45-9960-c88f27ba8a13,8092,O7CV6osdjaVBJ+0jJS80co0ev7+XKFMlSvpN7FSmv6U=,000007,01,730071";
    private VotingReceiptWithoutEncryptedVoteIncident incident;

    /**
     * Creates an incident to test against.
     */
    @BeforeMethod
    public void createVotingReceiptWithoutEncryptedVoteIncident() {
        incident = new VotingReceiptWithoutEncryptedVoteIncident(new VotingReceipt(
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
    public void mustBeEqualToAnotherVotingReceiptWithoutEncryptedVoteIncidentWithTheSameVotingReceipt() {
        assertEquals(incident,
            new VotingReceiptWithoutEncryptedVoteIncident(
                new VotingReceipt(GIVEN_SAMPLE_LINE)));
    }

    /**
     * Verifies that the incident has the same hashCode as another incident with the same voting receipt.
     */
    @Test
    public void mustHaveSameHashCodeAsAnotherVotingReceiptWithoutEncryptedVoteIncidentWithTheSameVotingReceipt() {
        assertEquals(incident.hashCode(),
            new VotingReceiptWithoutEncryptedVoteIncident(
                new VotingReceipt(GIVEN_SAMPLE_LINE)).hashCode());
    }

    /**
     * Verifies that the incident is not equal to another incident with another voting receipt.
     */
    @Test
    public void mustNotBeEqualToAnotherVotingReceiptWithoutEncryptedVoteIncidentWithAnotherVotingReceipt() {
        assertFalse(incident.equals(
                new VotingReceiptWithoutEncryptedVoteIncident(
                    new VotingReceipt(OTHER_SAMPLE_LINE))));
    }

    /**
     * Verifies that the incident doesn't have the same hashCode as another incident with another voting receipt.
     */
    @Test
    public void mustNotHaveSameHashCodeAsAnotherVotingReceiptWithoutEncryptedVoteIncidentWithAnotherVotingReceipt() {
        assertFalse(incident.hashCode() == new VotingReceiptWithoutEncryptedVoteIncident(
                new VotingReceipt(OTHER_SAMPLE_LINE)).hashCode());
    }

    /**
     * Verifies that toString produced a comma-separated line with the correct
     * values.
     */
    @Test
    public void toStringMustBeCommaSeparatedline() {
        assertEquals(incident.toString(),
            "VotingReceiptWithoutEncryptedVoteIncident," +
            GIVEN_VOTING_RECEIPT);
    }
}
