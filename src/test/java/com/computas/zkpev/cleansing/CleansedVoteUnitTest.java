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
package com.computas.zkpev.cleansing;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigInteger;


/**
 * Unit tests on CleansedVote.
 *
 */
public class CleansedVoteUnitTest {
    private static final String COMMA = ",";
    private static final String GIVEN_ENC_VOTE_OPT_IDS = "MTMxOTQ3Njc3NjczMTYyMzYxNzM4MTk0Mzg1NTQ3ODEzMjEzMDI5NTIzMTgyMjQyNjkwNzM0Nzg1MTU2NjQ1NzM1NTc5Nzg3ODMzMDUzMzc4MzE3NTQyMjI1ODgyNDk4NDUyNjQyMTQ0NjI3NTg1MTQ2Mjk5MDI5MzEyNTkxMzM0MzE0MjE3NDQyNTgzNzQwMTQ4MjEzOTQ0NTA3NDM4MDk2NjQ4MDg0MDAwODM3OTQ3ODU1MDEyNTkyNDk0MjA5OTcwMjI3MjI5ODIwNzkzMDg0NzM1NzAzMjU4MzExMDU1NTU5Nzk1MDI3NjQyNDE2NDkzNzA5MjMyNDk0OTA4NTMzMTQwMDc3NzQ0MTUyNzYyNTU2Mzk0MzczMDYyNDI2NDQ0MTU4ODYxNzg1MzI2NjY1NDI4MzY5MjkwMDE1OTgwODI3NzgwNjUwNjE2ODA3NTAzNjg2MzQwNDc1NDIyMjc3OTM2MTQwNjIwMzkwNjA0NjgyMjg5MDU2OTEyMzIzNzc3NDkyNTk4NjE2NzYyNzk1MjY2MDI5OTg3NzIzODI4NDEwODExODY1MDI4OTM4MzMxMzAxNzUzMjUwMDQ1NTExMDUyMTIwNTk1MDQyMTE1MDkzODA5OTM2NzMwODE1OTI3MzQ1MzQ3NzUzNTUwNzUzOTcxMTExODIyOTU2NzEwMTU1ODAwNjA2MzQwMTI1MjQ5NzE5MTgwMjIzNDg5ODQ0MzY2OTEzMDgwNDMwMTQ4OTUxNzc2OTkxOTAxNzA4NTQ2ODQxNzE2MjQ2OTc1ODgwMTY5MDM0MzAzOTczMDY0ODE4ODU1Njd8MjAwNTYwOTE0ODEzOTg2NjI3MzU5NDEzOTk0MTA3ODgzNzExNTg3MzA3NDEzMDg0NDY0NDA2NDEzODAwOTk4NzMyMjgwNzczNjAxMzUyNjE1MzgwNjg3Nzg0OTk2NDI1MjA1MjQ3NTE3OTE5OTgwOTk3OTc3MDc2MDg1MTk3MzM3MjQ5Nzc4NTkyNDUwNzE5MTQzNDkyMTE2Mjc4NjU3MTQ1NjQzMzUzOTg3NjEwODQ4NjIwMjc2MzkxNzcwNjY0ODYyOTk5NTQ2NDYyNTgyNDgwMDQ3MzM4OTcwMTA4NDE3NDc3NjY5ODA3ODcyMjQ5MzAxMzcyMjkxMDkwMzc1NDEwMjgxODUwNDk1NTAwMDc0NTQ3NDk0Njc0NDU3NTI1MjM3ODQ1NTMwNjY4MjgxNDA0NDY2MTY5NTgyNTAzNDU2NDgxNzk3ODcxMTQzNzY3NjE0MzE1ODg4MTAwMjk1MzcxMTYzODI1NDkyODM4NzkyNTQyODEwNzEwNzg1NTM2NDMyOTgwNzU2NDU1OTU0NTc2ODY5MjU0MDIwMzU0NzMxNTI4NzA4MTg0NjYyMDkzMjY0NDU2Njk5MzQ0MjA0MTczOTQ4Mzk1ODk3MzE1OTQzNjQyOTE4NTU4ODMzMTAyMDQ4ODExNDUyNjU4MDAwODc5Nzg0MDYwMTQzOTQ4NjA3MDk5NTczNzI2NjI4NzcyNDE3ODgxNjQzMTMzNzY4NjM1NjQwNjg5MzczMjQ1NTk2NTYyOTU4OTUwOTMyOTUwNjAzNDIzMjE5MzI4MjU2OTQzMDY1NTA4NjkyOTE1OTUzNzE1OTIzNjc=#";
    private static final String OTHER_ENC_VOTE_OPT_IDS = "MjExOTM5NDU1NzI3MjI3MDI4ODgzMjI3NDgxODE0NjU3NzcyOTgzMjAyMDg3NDMxNTY3NjMxNzk0NzQzMTU4NTM1Njk1OTI2MjM0MzQ0MzgyODI2NTg1NTgxMzg3MTQ2NDMyMzEyNjc4MzMzODA1MDk1MDEyODIxODI0NTI1NDk5MDQ1OTUyNDA1MTQ3ODUxMzk0MDUwMTk0NTU5MTYyMDcwNjEzOTU1MDY0MzMzODAyMzk5MjA5MDIzMzMzNDgxOTE4MTAyMTg3MDQ1MDY1NDYxMzAxODQ0NjM4MDUxOTExNzUwMzI4OTc5ODY4NjYyOTI4MzkzNTMyNjUwNjUxNzkxOTM1MjE3MTk3NjkzNTU2NjMxOTcwMjY3MDgxNzcxODQxMjgwOTg0NzQyMzAxMjE0NTUwNjE2MjM4ODgwNDQ1MTQ4ODQ0MDgzNDIxOTE5NTE4MjQzMTY2NDA4NDE3MTAzOTUyOTY0MDUxMjA4NzgzNTM2MTYwODM1NDIxMzkyMDc5MTQ4ODk1NDE2MDEwOTIwNTE5MDU3ODMwOTc4MzYxMTQ4Njc5NDA5ODU3MDczNjMzODAxMzc2MzgxMTU5Mzg4MDMwOTg5ODM0NDg2NzY5Mzk1ODY4NjY5OTk2NDM0NjczNzUzNjM2NDA4NjAzOTIyMzA5MTIwMDgzNjQ3OTk1ODIxMjMxNDIyNjEzNjU1OTI5NTQyNjgyNjYxMzE1Mjg3OTY4NzYyODE0MzM2NTc4MzU5NTYzMDQ1MjMyNzI2MzcxMDQwNTcyNTg4MzQwNzc0OTE2MjEyODE2MTU5NTQyMTE5MTM5ODJ8MTk1MDYyMzQ5MDMwOTE3NDQxMTI3NjYwODkxODI1NzM5MDgyMDM5NjY3Mjc1MDc5NTA2MTk4OTcyNDA4MzY4MDc2NjE5MTA5MzY4OTI2MTc4OTU2MjE2MzU5NjI3Njg1MzMxNTk3NzUxNDEwMjg3NjkyODcxNTQ2NDg0NjA0Mzk3MTUxOTIwOTQ2NDYzMzA2OTEwNDk5Mzk1OTU2NzAzNDMxODUwODE3MDUyMDgzMzE1MjUxNzEwOTQ1OTUyNzQ5Mzk4MTY3OTY5ODA1NDExMzMxNzc3MjEzNzEzMDMyNDkxOTI5MjY1NjU2ODY3NjY2NzUzMzg2MzA5OTQ3MTYwNjAyNTIyMzQyODI2MjQyNzQ5NDUzMjg4OTQ0OTc1MjQxNTY4NjQ2NDE1NzY1MTYyODA2MzU2MTIyOTU4MTkwNTU5MDkxNzcxMTc3ODE1OTc1MTc3NzM2MTY3MTkzOTEwMzc3MjcyNTEwMzgxNDIyMjI0MTI3NjIyMTU0MjgyODAyMzYyNTAwNjU5MDkzODYwMDA2ODQ4Njk5MTUwNDkzMjg1NTQ0MTU0MTQwNDQ0NDczMzgwNzcyNzgyMTQxNjYyMzMxNTIwNzA1MTE2MTUwODE2NjIwMTUxMzA2OTU5MzY0MTI1NDY3MjEwMzI0ODk3NTg0MTk2MjkxNzM5NDM5NDUyMTQ3MTU0MDEyNDE0MjA2NDUyNzQ3NTYxMDEyNDk2ODE0NDkzMTAwODc0NDA5MDc2MTM3MDE4MDEwNzI0MzkyNzY0NDEzNDA2MDA5MDY5NjMxNjI1MjA2MzExMTUyMzAwNTg1MDY1NTk=#";
    private static final String GIVEN_CONTEST_ID = "000004";
    private static final String GIVEN_ELECTION_ID = "01";
    private static final String GIVEN_ELECTION_EVENT_ID = "999902";
    private static final String GIVEN_SAMPLE_LINE = GIVEN_ENC_VOTE_OPT_IDS +
        COMMA + GIVEN_CONTEST_ID + COMMA + GIVEN_ELECTION_ID + COMMA +
        GIVEN_ELECTION_EVENT_ID + ",CHANNEL_ID_UNCONTROLLED";
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
    private static final BigInteger GIVEN_ENC_VOTE_OPT_IDS_PUBLIC_KEY_COMPONENT = new BigInteger(
            "13194767767316236173819438554781321302952318224269073478515664573557978783305337831754222588249845264214462758514629902931259133431421744258374014821394450743809664808400083794785501259249420997022722982079308473570325831105555979502764241649370923249490853314007774415276255639437306242644415886178532666542836929001598082778065061680750368634047542227793614062039060468228905691232377749259861676279526602998772382841081186502893833130175325004551105212059504211509380993673081592734534775355075397111182295671015580060634012524971918022348984436691308043014895177699190170854684171624697588016903430397306481885567");
    private static final BigInteger GIVEN_ENC_VOTE_OPT_IDS_MESSAGE_COMPONENT = new BigInteger(
            "13194767767316236173819438554781321302952318224269073478515664573557978783305337831754222588249845264214462758514629902931259133431421744258374014821394450743809664808400083794785501259249420997022722982079308473570325831105555979502764241649370923249490853314007774415276255639437306242644415886178532666542836929001598082778065061680750368634047542227793614062039060468228905691232377749259861676279526602998772382841081186502893833130175325004551105212059504211509380993673081592734534775355075397111182295671015580060634012524971918022348984436691308043014895177699190170854684171624697588016903430397306481885567");
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
     * Verifies that the constructor sets the message component of the encrypted vote option IDs correctly from
     * a line.
     */
    @Test
    public void constructorMustSetTheMessageComponentOfTheEncryptedVoteOptionIdsCorrectly() {
        assertEquals(cleansedVote.getEncryptedVoteOptIds()
                                 .getPublicKeyComponent(),
            GIVEN_ENC_VOTE_OPT_IDS_MESSAGE_COMPONENT);
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
     * Verifies that the cleansed vote is not equal to another cleansed vote with another contest ID.
     */
    @Test
    public void mustNotBeEqualToAnotherCleansedVoteWithOtherContestId() {
        assertFalse(cleansedVote.equals(
                new CleansedVote(CLEANSED_VOTE_LINE_WITH_OTHER_CONTEST_ID)));
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
     * Verifies that the cleansed vote is not equal to another cleansed vote with another election event ID.
     */
    @Test
    public void mustNotBeEqualToAnotherCleansedVoteWithOtherElectionEventId() {
        assertFalse(cleansedVote.equals(
                new CleansedVote(
                    CLEANSED_VOTE_LINE_WITH_OTHER_ELECTION_EVENT_IT)));
    }
}
