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

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigInteger;


/**
 * Unit tests on CleansedVote.
 */
public class CleansedVoteUnitTest {
    private static final String COMMA = ",";
    private static final String GIVEN_ENC_VOTE_OPT_IDS = "IWvRkimscLc7ZnzHRhq5HkKtL0XXlMwZEFu2w7iIdSBCWDAKuxgUNPkA66dqZ7OyuvqePFoXVHLgemivhxRHAtkKALAtdc3ihfDN99u3vrfiPinPLHcidemy056uxPO+9mb96Cs4RVhFDY3DnW6BuZQUuITmrBOEhaaxja7qbijKD7SNX2bwqRv0j4GeasLaGosqDfFqEYd9aLr7x7M6xkk4AhyoPHG7qAI/A1SiwDxF2ocycQgJk7kl4yeJDgBKPFw0F3xZ1f8yVJEr6d8Su8OwdXN/NiFlX9bjKifFK2bgMqJvlsAPXLjK5ms/2VeSIE4atuMnZquzJMpjEICHcQ==#bJF2NzTcKGDnDIiq4GrZ+wGlZ3qOFk+Qr+dQNNB9lvkFOFKlDgv+3aAhno5UrQNYnYX8CSGKknuOVzCRsEffwXUQdKamWBs6M0psJQ0rtMwzCyrgMNMe+DQm9mK2cgi+j24qmZxCM8SD7bocdlx9JgTYra5NHMYzC7pQwqYUkCQBkoJBFPVaCSOoEmOS03h8qQG1A37d6rSwPgUiScfO0x1bxyrSNbFOgi/l1paJHV5DD5DVnEyqSnZuBqxrf7x2ZQDWZ50fk9QJkIXjJl3rKJ+PVO7RCBJh5UngZw9O6T8lKsfQ8e9SLYCa17vWESPpq4zHqa5SM4dJ0L127NzRWg==#";
    private static final String GIVEN_CONTEST_ID = "000007";
    private static final String GIVEN_ELECTION_ID = "01";
    private static final String GIVEN_ELECTION_EVENT_ID = "730071";
    private static final String GIVEN_SAMPLE_LINE = GIVEN_ENC_VOTE_OPT_IDS +
        COMMA + GIVEN_CONTEST_ID + COMMA + GIVEN_ELECTION_ID + COMMA +
        GIVEN_ELECTION_EVENT_ID;
    private static final BigInteger GIVEN_ENC_VOTE_OPT_IDS_PUBLIC_KEY_COMPONENT = new BigInteger(
            "4219031415120855299942504609686631508290959320082397563069424347828916858427890856804159869193969738669364902144363667993754123605745479140673522931091212991868010400778700170829169844186079917454816202448097013888829290929357047271541271300260054320629768553832607161189089093126196236969360986424252073774976859587371724874768871674629200817670760178957172117709698131055872791376164476829523067892192641990382538990769608470540203067181942701960800123042080732484330823445578783104296366077149809354572355812192468635653478783331586884185753771092523075420358478895917905520399779149422153854594596456827646412657");
    private CleansedVote cleansedVote;

    /**
     * Creates a cleansed vote to run the tests against.
     */
    @BeforeMethod
    public void createCleansedVote() {
        cleansedVote = new CleansedVote(GIVEN_SAMPLE_LINE);
    }

    /**
     * Verifies that the constructor sets the encrypted vote option IDs as String
     * correctly.
     */
    @Test
    public void constructorMustSetTheTheEncryptedVoteOptionIdsAsStringCorrectly() {
        assertEquals(cleansedVote.getEncryptedVoteOptIdsAsString(),
            GIVEN_ENC_VOTE_OPT_IDS);
    }

    /**
     * Verifies that the constructor sets the public key component of the encrypted vote option IDs correctly from
     * a line.
     */
    @Test
    public void constructorMustSetThePublicKeyComponentOfTheEncryptedVoteOptionIdsCorrectly() {
        assertEquals(cleansedVote.getEncryptedVoteOptIds()
                                 .getPublicKeyComponent(),
            GIVEN_ENC_VOTE_OPT_IDS_PUBLIC_KEY_COMPONENT);
    }

    /**
     * Verifies that the constructor sets the contest ID correctly from
     * a line.
     */
    @Test
    public void constructorMustSetTheContestIdCorrectly() {
        assertEquals(cleansedVote.getContestId(), GIVEN_CONTEST_ID);
    }

    /**
     * Verifies that the constructor sets the election ID correctly from
     * a line.
     */
    @Test
    public void constructorMustSetTheElectionIdCorrectly() {
        assertEquals(cleansedVote.getElectionId(), GIVEN_ELECTION_ID);
    }

    /**
     * Verifies that the constructor sets the election event ID correctly from
     * a line.
     */
    @Test
    public void constructorMustSetTheElectionEventIdCorrectly() {
        assertEquals(cleansedVote.getElectionEventId(), GIVEN_ELECTION_EVENT_ID);
    }
}
