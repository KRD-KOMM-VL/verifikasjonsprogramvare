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
 * Unit tests on CleansedVote related to equality.
 */
public class CleansedVoteEqualityUnitTest {
    private static final String COMMA = ",";
    private static final String GIVEN_ENC_VOTE_OPT_IDS = "IWvRkimscLc7ZnzHRhq5HkKtL0XXlMwZEFu2w7iIdSBCWDAKuxgUNPkA66dqZ7OyuvqePFoXVHLgemivhxRHAtkKALAtdc3ihfDN99u3vrfiPinPLHcidemy056uxPO+9mb96Cs4RVhFDY3DnW6BuZQUuITmrBOEhaaxja7qbijKD7SNX2bwqRv0j4GeasLaGosqDfFqEYd9aLr7x7M6xkk4AhyoPHG7qAI/A1SiwDxF2ocycQgJk7kl4yeJDgBKPFw0F3xZ1f8yVJEr6d8Su8OwdXN/NiFlX9bjKifFK2bgMqJvlsAPXLjK5ms/2VeSIE4atuMnZquzJMpjEICHcQ==#bJF2NzTcKGDnDIiq4GrZ+wGlZ3qOFk+Qr+dQNNB9lvkFOFKlDgv+3aAhno5UrQNYnYX8CSGKknuOVzCRsEffwXUQdKamWBs6M0psJQ0rtMwzCyrgMNMe+DQm9mK2cgi+j24qmZxCM8SD7bocdlx9JgTYra5NHMYzC7pQwqYUkCQBkoJBFPVaCSOoEmOS03h8qQG1A37d6rSwPgUiScfO0x1bxyrSNbFOgi/l1paJHV5DD5DVnEyqSnZuBqxrf7x2ZQDWZ50fk9QJkIXjJl3rKJ+PVO7RCBJh5UngZw9O6T8lKsfQ8e9SLYCa17vWESPpq4zHqa5SM4dJ0L127NzRWg==#";
    private static final String OTHER_ENC_VOTE_OPT_IDS = "IWvRkimscLc7ZnzHRhq5HkKtL0XXlMwZEFu2w7iIdSBCWDAKuxgUNPkA66dqZ7OyuvqePFoXVHLgemivhxRHAtkKALAtdc3ihfDN99u3vrfiPinPLHcidemy056uxPO+9mb96Cs4RVhFDY3DnW6BuZQUuITmrBOEhaaxja7qbijKD7SNX2bwqRv0j4GeasLaGosqDfFqEYd9aLr7x7M6xkk4AhyoPHG7qAI/A1SiwDxF2ocycQgJk7kl4yeJDgBKPFw0F3xZ1f8yVJEr6d8Su8OwdXN/NiFlX9bjKifFK2bgMqJvlsAPXLjK5ms/2VeSIE4atuMnZquzJMpjEICHcQ==#XA4JtE8x0+Q9Se27se5FPn+zLp1yOa/diFk4K8icSdnuKqJ0C5c8ZdCy+TgLujjDJKDYpXvzqC7AfeblsYtVkdZ7Hj2O8Qwdgd9QYvMKKPE09EEMt6OpvtODNhCOnKYpEMsGdWAbCE0MpsDiqzTTg1LhubqQPNF8SxWysBqZHu3X8O3UOrohdBd5vqW1S0EX6sHj94FksXBXZnFc3x64kLiNRna4McjV/jdNXCRQJ2/+GFhsSW/AANjjvUI7a3ZpxVXH9/1CTJSVJpWa3p0kHdEKnofvc7NBpmI+bzX+Sx0lrGKBDKuGthUMofTWb3XqW32ffEVXcGk5WVMpklbvSQ==#";
    private static final String GIVEN_CONTEST_ID = "000007";
    private static final String GIVEN_ELECTION_ID = "01";
    private static final String GIVEN_ELECTION_EVENT_ID = "730071";
    private static final String GIVEN_SAMPLE_LINE = GIVEN_ENC_VOTE_OPT_IDS +
        COMMA + GIVEN_CONTEST_ID + COMMA + GIVEN_ELECTION_ID + COMMA +
        GIVEN_ELECTION_EVENT_ID;
    private static final String CLEANSED_VOTE_LINE_WITH_OTHER_ENC_OPT_IDS = OTHER_ENC_VOTE_OPT_IDS +
        COMMA + GIVEN_CONTEST_ID + COMMA + GIVEN_ELECTION_ID + COMMA +
        GIVEN_ELECTION_EVENT_ID + ",CHANNEL_ID_UNCONTROLLED";
    private static final String CLEANSED_VOTE_LINE_WITH_OTHER_CONTEST_ID = GIVEN_ENC_VOTE_OPT_IDS +
        COMMA + "Other" + COMMA + GIVEN_ELECTION_ID + COMMA +
        GIVEN_ELECTION_EVENT_ID + ",CHANNEL_ID_UNCONTROLLED";
    private static final String CLEANSED_VOTE_LINE_WITH_OTHER_ELECTION_ID = GIVEN_ENC_VOTE_OPT_IDS +
        COMMA + GIVEN_CONTEST_ID + COMMA + "Other" + COMMA +
        GIVEN_ELECTION_EVENT_ID + ",CHANNEL_ID_UNCONTROLLED";
    private static final String CLEANSED_VOTE_LINE_WITH_OTHER_ELECTION_EVENT_IT = GIVEN_ENC_VOTE_OPT_IDS +
        COMMA + GIVEN_CONTEST_ID + COMMA + GIVEN_ELECTION_ID + COMMA + "Other" +
        ",CHANNEL_ID_UNCONTROLLED";
    private CleansedVote cleansedVote;

    /**
     * Creates a cleansed vote to run the tests against.
     */
    @BeforeMethod
    public void createCleansedVote() {
        cleansedVote = new CleansedVote(GIVEN_SAMPLE_LINE);
    }

    /**
     * Verifies that a cleansed vote is equal to itself.
     */
    @Test
    public void mustBeEqualToItself() {
        assertEquals(cleansedVote, cleansedVote);
    }

    /**
     * Verifies that a cleansed vote has a consistent hashCode.
     */
    @Test
    public void mustHaveSameHashCodeAsItself() {
        assertEquals(cleansedVote.hashCode(), cleansedVote.hashCode());
    }

    /**
     * Verifies that a cleansed vote is not equal to null.
     */
    @Test
    public void mustNotBeEqualToNull() {
        Object nullObject = null;
        assertFalse(cleansedVote.equals(nullObject));
    }

    /**
     * Verifies that a cleansed vote is not equal to an object of another class. This is used as the test case.
     */
    @Test
    public void mustNotBeEqualToAnObjectOfAnotherClass() {
        assertFalse(cleansedVote.equals(this));
    }

    /**
     * Verifies that the cleansed vote is equal to another cleansed vote generated from the same line.
     */
    @Test
    public void mustBeEqualToAnotherInjectedCleansedVoteLineIncidentWithTheSameCleansedVote() {
        assertEquals(cleansedVote, new CleansedVote(GIVEN_SAMPLE_LINE));
    }

    /**
     * Verifies that the cleansed vote has the same hashCode as another cleansed vote generated from the same line.
     */
    @Test
    public void mustHaveSameHashCodeAsAnotherCleansedVoteGeneratedFromTheSameLine() {
        assertEquals(cleansedVote.hashCode(),
            new CleansedVote(GIVEN_SAMPLE_LINE).hashCode());
    }

    /**
     * Verifies that the cleansed vote is not equal to another cleansed vote with other encrypted vote option IDs.
     */
    @Test
    public void mustNotBeEqualToAnotherCleansedVoteWithOtherOptionIDs() {
        assertFalse(cleansedVote.equals(
                new CleansedVote(CLEANSED_VOTE_LINE_WITH_OTHER_ENC_OPT_IDS)));
    }

    /**
     * Verifies that the cleansed vote doesn't have the same hashCode as another cleansed vote with other encrypted vote option IDs.
     */
    @Test
    public void mustNotHaveSameHashCodeAsAnotherCleansedVoteWithOtherOptionIDs() {
        assertFalse(cleansedVote.hashCode() == new CleansedVote(
                CLEANSED_VOTE_LINE_WITH_OTHER_ENC_OPT_IDS).hashCode());
    }

    /**
     * Verifies that the cleansed vote is not equal to another cleansed vote with another contest ID.
     */
    @Test
    public void mustNotBeEqualToAnotherCleansedVoteWithOtherContestId() {
        assertFalse(cleansedVote.equals(
                new CleansedVote(CLEANSED_VOTE_LINE_WITH_OTHER_CONTEST_ID)));
    }

    /**
     * Verifies that the cleansed vote doesn't have the same hashCode as another cleansed vote with other contest ID.
     */
    @Test
    public void mustNotHaveSameHashCodeAsAnotherCleansedVoteWithOtherContestId() {
        assertFalse(cleansedVote.hashCode() == new CleansedVote(
                CLEANSED_VOTE_LINE_WITH_OTHER_CONTEST_ID).hashCode());
    }

    /**
     * Verifies that the cleansed vote is not equal to another cleansed vote with another election ID.
     */
    @Test
    public void mustNotBeEqualToAnotherCleansedVoteWithOtherElectionId() {
        assertFalse(cleansedVote.equals(
                new CleansedVote(CLEANSED_VOTE_LINE_WITH_OTHER_ELECTION_ID)));
    }

    /**
     * Verifies that the cleansed vote doesn't have the same hashCode as another cleansed vote with other election ID.
     */
    @Test
    public void mustNotHaveSameHashCodeAsAnotherCleansedVoteWithOtherElectionId() {
        assertFalse(cleansedVote.hashCode() == new CleansedVote(
                CLEANSED_VOTE_LINE_WITH_OTHER_ELECTION_ID).hashCode());
    }

    /**
     * Verifies that the cleansed vote is not equal to another cleansed vote with another election event ID.
     */
    @Test
    public void mustNotBeEqualToAnotherCleansedVoteWithOtherElectionEventId() {
        assertFalse(cleansedVote.equals(
                new CleansedVote(
                    CLEANSED_VOTE_LINE_WITH_OTHER_ELECTION_EVENT_IT)));
    }

    /**
     * Verifies that the cleansed vote doesn't have the same hashCode as another cleansed vote with other election event ID.
     */
    @Test
    public void mustNotHaveSameHashCodeAsAnotherCleansedVoteWithOtherElectionEventId() {
        assertFalse(cleansedVote.hashCode() == new CleansedVote(
                CLEANSED_VOTE_LINE_WITH_OTHER_ELECTION_EVENT_IT).hashCode());
    }
}
