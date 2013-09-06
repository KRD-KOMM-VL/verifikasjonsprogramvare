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
package com.computas.zkpev2013.cleansing;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * Unit tests on InjectedCleansedEncryptedVoteLineIncident.
 */
public class InjectedCleansedVoteLineIncidentUnitTest {
    private static final String COMMA = ",";
    private static final String GIVEN_ENC_OPT_IDS = "IWvRkimscLc7ZnzHRhq5HkKtL0XXlMwZEFu2w7iIdSBCWDAKuxgUNPkA66dqZ7OyuvqePFoXVHLgemivhxRHAtkKALAtdc3ihfDN99u3vrfiPinPLHcidemy056uxPO+9mb96Cs4RVhFDY3DnW6BuZQUuITmrBOEhaaxja7qbijKD7SNX2bwqRv0j4GeasLaGosqDfFqEYd9aLr7x7M6xkk4AhyoPHG7qAI/A1SiwDxF2ocycQgJk7kl4yeJDgBKPFw0F3xZ1f8yVJEr6d8Su8OwdXN/NiFlX9bjKifFK2bgMqJvlsAPXLjK5ms/2VeSIE4atuMnZquzJMpjEICHcQ==#bJF2NzTcKGDnDIiq4GrZ+wGlZ3qOFk+Qr+dQNNB9lvkFOFKlDgv+3aAhno5UrQNYnYX8CSGKknuOVzCRsEffwXUQdKamWBs6M0psJQ0rtMwzCyrgMNMe+DQm9mK2cgi+j24qmZxCM8SD7bocdlx9JgTYra5NHMYzC7pQwqYUkCQBkoJBFPVaCSOoEmOS03h8qQG1A37d6rSwPgUiScfO0x1bxyrSNbFOgi/l1paJHV5DD5DVnEyqSnZuBqxrf7x2ZQDWZ50fk9QJkIXjJl3rKJ+PVO7RCBJh5UngZw9O6T8lKsfQ8e9SLYCa17vWESPpq4zHqa5SM4dJ0L127NzRWg==#";
    private static final String GIVEN_CONTEST_ID = "000007";
    private static final String GIVEN_ELECTION_ID = "01";
    private static final String GIVEN_ELECTION_EVENT_ID = "730071";
    private static final String CLEANSED_VOTE_LINE = GIVEN_ENC_OPT_IDS + COMMA +
        GIVEN_CONTEST_ID + COMMA + GIVEN_ELECTION_ID + COMMA +
        GIVEN_ELECTION_EVENT_ID;
    private static final String OTHER_CLEANSED_VOTE_LINE = "IWvRkimscLc7ZnzHRhq5HkKtL0XXlMwZEFu2w7iIdSBCWDAKuxgUNPkA66dqZ7OyuvqePFoXVHLgemivhxRHAtkKALAtdc3ihfDN99u3vrfiPinPLHcidemy056uxPO+9mb96Cs4RVhFDY3DnW6BuZQUuITmrBOEhaaxja7qbijKD7SNX2bwqRv0j4GeasLaGosqDfFqEYd9aLr7x7M6xkk4AhyoPHG7qAI/A1SiwDxF2ocycQgJk7kl4yeJDgBKPFw0F3xZ1f8yVJEr6d8Su8OwdXN/NiFlX9bjKifFK2bgMqJvlsAPXLjK5ms/2VeSIE4atuMnZquzJMpjEICHcQ==#XA4JtE8x0+Q9Se27se5FPn+zLp1yOa/diFk4K8icSdnuKqJ0C5c8ZdCy+TgLujjDJKDYpXvzqC7AfeblsYtVkdZ7Hj2O8Qwdgd9QYvMKKPE09EEMt6OpvtODNhCOnKYpEMsGdWAbCE0MpsDiqzTTg1LhubqQPNF8SxWysBqZHu3X8O3UOrohdBd5vqW1S0EX6sHj94FksXBXZnFc3x64kLiNRna4McjV/jdNXCRQJ2/+GFhsSW/AANjjvUI7a3ZpxVXH9/1CTJSVJpWa3p0kHdEKnofvc7NBpmI+bzX+Sx0lrGKBDKuGthUMofTWb3XqW32ffEVXcGk5WVMpklbvSQ==#,000004,01,666601,CHANNEL_ID_UNCONTROLLED";
    private InjectedCleansedVoteLineIncident incident;

    /**
    * Creates an incident to test against.
    */
    @BeforeMethod
    public void createInjectedCleansedVoteLineIncident() {
        incident = new InjectedCleansedVoteLineIncident(new CleansedVote(
                    CLEANSED_VOTE_LINE));
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
     * Verifies that the incident is equal to another incident with the same cleansed vote.
     */
    @Test
    public void mustBeEqualToAnotherInjectedCleansedVoteLineIncidentWithTheSameCleansedVote() {
        assertEquals(incident,
            new InjectedCleansedVoteLineIncident(
                new CleansedVote(CLEANSED_VOTE_LINE)));
    }

    /**
     * Verifies that the incident has the same hashCode as another incident with the same cleansed vote.
     */
    @Test
    public void mustHaveSameHashCodeAsAnotherInjectedCleansedVoteLineIncidentWithTheSameCleansedVote() {
        assertEquals(incident.hashCode(),
            new InjectedCleansedVoteLineIncident(
                new CleansedVote(CLEANSED_VOTE_LINE)).hashCode());
    }

    /**
     * Verifies that the incident is not equal to another incident with another cleansed vote.
     */
    @Test
    public void mustNotBeEqualToAnotherInjectedCleansedVoteLineIncidentWithAnotherCleansedVote() {
        assertFalse(incident.equals(
                new InjectedCleansedVoteLineIncident(
                    new CleansedVote(OTHER_CLEANSED_VOTE_LINE))));
    }

    /**
     * Verifies that toString produced a comma-separated line with the correct
     * values.
     */
    @Test
    public void toStringMustBeCommaSeparatedline() {
        assertEquals(incident.toString(),
            "InjectedCleansedVoteLineIncident," + GIVEN_ENC_OPT_IDS + COMMA +
            GIVEN_CONTEST_ID + COMMA + GIVEN_ELECTION_ID + COMMA +
            GIVEN_ELECTION_EVENT_ID);
    }
}
