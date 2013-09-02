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
package com.computas.zkpev2013.decryption;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigInteger;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;


/**
 * Unit test against a decryption line.
 */
public class DecryptionLineUnitTest {
    static final BigInteger SAMPLE_P = new BigInteger(
            "13213513213213513213513213513513213513213563");
    static final String COMMA = ",";
    static final String ELECTION_EVENT_ID = "730071";
    static final String ELECTION_ID = "01";
    static final String CONTEST_ID = "000007";
    static final String ENCRYPTED_VOTING_OPTIONS_IDS_PRODUCT = "AIJ1jejPt76pKEIpr1BPUt5z0mT9FExthLmUbm/QZYAJv21+8rsw/tG4bFPe7N5D#n#0rfh+Ir7IJns5p6ehk98I2LxNgJzi+fOm82tNjhejsjIC4vWqFRZ0tF6Ry1Si5io#n#pUUOllrt8DxdmcI30fanp/0hNAdi3GPQ61v+ABOrZonjDvCFirAv9nnpHuTTlYlr#n#1L5oiHG3r6mjLy+e8vVAHur8xMCH/fQ1XJLvRby9wnSkZrAEMb1C0OLreaalVkSv#n#l4SiDebrdKgBrA3xR4ERCKwzeevif2uAcAirOtW/ph2k78mgMDsgI/7vDOMJElD+#n#WwN5+MQu4/58W9vh2x5aSCI=#AJ88iT77CUB5YuklJWiN56+DnsmoJkO2WUh7966m7RVYsMaiM/nCuut4CKeA6mxY#n#nM9BfVXHE5aM9vRW8W5Fu4vMQO721xRkSBCRDs1tAVe9Yjy3BaV8duL34sQkKTeA#n#YKklOB4gcMZjdBj1W2Il/2D/vniwUFeyMXbSqkynxiwS16LFDwyq58OUOVDDKLG6#n#AiovTltRtwHE7mrQJLwgqAH6NCUmx2dOE6K1WDdI4Twa+bGEZZGXO3qankiQpxuC#n#Lnp+U77I26/jXoFQzZC0VuC/sdxNqUhoLx75j28Qq0DstZ3SYNRH8M226K5maBQS#n#oQZwUE4PSONpA2AUjdVUtEQ=#";
    static final BigInteger DECRYPTED_VOTING_OPTION_IDS_PRODUCT = new BigInteger(
            "3703190944206196621039291123507742527194453");
    static final String SCHNORR_SIGNATURE = "NzA3NDIxOTc2ODc5NzEyMTEyMjAyMzMzMzc0OTMxOTU0MjgxNTQ1OTQ4ODU1NTc4MDg1NTYzNzQxMjIzNzkzMTE4NTk1NjA0Mzk4NXwtNDY1NDUzNzQzNDA0NTY4MTYyNzQ0NTM4NzU0ODU4NjAzNzIxMjAzNTI4NDcyNDU2MzQzNTQ5MTg3ODk4MjU4Njg1MTgyNjcxODk2MzcxMDEyOTMzMDc2NjAyNjkzMDQxNjgwNDM0MDg3Mjc0NzE4MjUzNjI2MDkxMzEyNzYyMDQ2OTAwOTczNTIzMzYyODA3ODA2MzY0NjkxNDg2NjE5OTIyMDgwNzg3Nzc0ODg1MzQ5MDYxMDk5MjIwOTExMzM4NTkyMDE2OTE2NjQ3ODU1Nzk2NTI5NzAzMzk2MDY1ODEwNzgxMDg2NzQ5ODY5ODc5MTQ3MjY3MTQ5NzI3ODczMjA5NDg3MDQwNDM5Nzc1Nzk3NzQzMTY5Mjg0MTAyNjcyNTgyMjM4NDg0MDc1NDUwMjIxMDI0MTM4NjA1MDEwMDExMTE5NjU5OTU3MzM1MDQ4MDc2MzY0OTY2MDQyNjgzNTg2NzY3NDk5ODczNzcyNDQ1ODkyMjAyNjQxNjA1NDU5ODY0MzQ1MzUzNDI3OTk5MTM5OTU0NzUwMTE1NTY2MTU5Njc1ODMwNzIxODgyMDE2MTM3NzczMzczMTg2NTk0NDU0ODc0NTEwOTcwODM1Mzc4MzY2OTM1ODE4MDg3MDE4MTYzMzU4OTYwMzcyMDY5MzA4ODAxNzQ5NTY4NDQyODkyMTEzMzA2MDE2Mjc4MjIzNTMxNjkwNDA5Mzg3NzEyMDA0MDI3MzgxNzI3NzQ2NzY5NDQ1MTM5NzQ2NDYwNzk2Njg3MjQ2MzMzNjgwNTgzODM5NzIyODI1MzQ1MjU3NTYwOTA0MDI5NTEwNTAwMTA3NjIyMDgyODU3NDcyMTc2NzM2MDA4OTM1MjMxMzc4OTMyOTIzNzc0NDk4NTkyMDQ2NDAwMDE1NjM0ODk4ODU0MzkwNTN8bnVsbA==";
    static final String SAMPLE_LINE_WITH_CORRECT_PROOF = ELECTION_EVENT_ID +
        COMMA + ELECTION_ID + COMMA + CONTEST_ID + COMMA +
        ENCRYPTED_VOTING_OPTIONS_IDS_PRODUCT + COMMA +
        DECRYPTED_VOTING_OPTION_IDS_PRODUCT + COMMA + SCHNORR_SIGNATURE;
    private static final String SAMPLE_LINE_WITH_INCORRECT_PROOF = ELECTION_EVENT_ID +
        COMMA + ELECTION_ID + COMMA + CONTEST_ID + COMMA +
        ENCRYPTED_VOTING_OPTIONS_IDS_PRODUCT + COMMA + "161195548" + COMMA +
        SCHNORR_SIGNATURE;
    private DecryptionLine decryptionLine;

    /**
     * Creates a decryption line to run the tests against.
     */
    @BeforeMethod
    public void createDecryptionLine() {
        decryptionLine = new DecryptionLine(SAMPLE_LINE_WITH_CORRECT_PROOF);
        decryptionLine.calculateDecryptedVotingOptionIdsProduct(SAMPLE_P);
    }

    /**
     * Verifies that a decryption line is equal to itself.
     */
    @Test
    public void mustBeEqualToItself() {
        assertEquals(decryptionLine, decryptionLine);
    }

    /**
     * Verifies that a decryption line has a consistent hashCode.
     */
    @Test
    public void mustHaveSameHashCodeAsItself() {
        assertEquals(decryptionLine.hashCode(), decryptionLine.hashCode());
    }

    /**
     * Verifies that a decryption line is not equal to null.
     */
    @Test
    public void mustNotBeEqualToNull() {
        Object nullObject = null;
        assertFalse(decryptionLine.equals(nullObject));
    }

    /**
     * Verifies that a decryption line is not equal to an object of another class. This is used as the test case.
     */
    @Test
    public void mustNotBeEqualToAnObjectOfAnotherClass() {
        assertFalse(decryptionLine.equals(this));
    }

    /**
     * Verifies that the decryption line is equal to another decryption line with the same source string.
     */
    @Test
    public void mustBeEqualToAnotherDecryptionLineWithTheSameSourceLine() {
        assertEquals(decryptionLine,
            new DecryptionLine(SAMPLE_LINE_WITH_CORRECT_PROOF));
    }

    /**
     * Verifies that the decryption line has the same hashCode as another decryption line with the same source string.
     */
    @Test
    public void mustHaveSameHashCodeAsAnotherDecryptionLineWithTheSameSourceLine() {
        assertEquals(decryptionLine.hashCode(),
            new DecryptionLine(SAMPLE_LINE_WITH_CORRECT_PROOF).hashCode());
    }

    /**
     * Verifies that the decryption line is not equal to another decryption line with another source line.
     */
    @Test
    public void mustNotBeEqualToAnotherDecryptionLineWithAnotherSourceLine() {
        assertFalse(decryptionLine.equals(
                new DecryptionLine(SAMPLE_LINE_WITH_INCORRECT_PROOF)));
    }

    /**
     * Verifies that the decryption line has another hash code than a decryption line with another source line.
     */
    @Test
    public void mustNotHaveSameHashCodeAsAnotherDecryptionLineWithAnotherSourceLine() {
        assertFalse(decryptionLine.hashCode() == new DecryptionLine(
                SAMPLE_LINE_WITH_INCORRECT_PROOF).hashCode());
    }

    /**
     * Verifies that the constructor sets the election ID correctly from
     * a line.
     */
    @Test
    public void constructorMustSetTheElectionIdCorrectly() {
        assertEquals(decryptionLine.getElectionId(), ELECTION_ID);
    }

    /**
     * Verifies that the constructor sets the election event ID correctly from
     * a line.
     */
    @Test
    public void constructorMustSetTheElectionEventIdCorrectly() {
        assertEquals(decryptionLine.getElectionEventId(), ELECTION_EVENT_ID);
    }

    /**
     * Verifies that the constructor sets the contest ID correctly from
     * a line.
     */
    @Test
    public void constructorMustSetTheContestIdCorrectly() {
        assertEquals(decryptionLine.getContestId(), CONTEST_ID);
    }

    /**
     * Verifies that the constructor sets the encrypted voting option IDs correctly from
     * a line.
     */
    @Test
    public void constructorMustSetTheEncryptedVotingOptionsIdsProductStringCorrectly() {
        assertEquals(decryptionLine.getEncryptedVotingOptionsIdsString(),
            ENCRYPTED_VOTING_OPTIONS_IDS_PRODUCT);
    }

    /**
     * Verifies that the constructor sets the compressed decrypted voting option IDs correctly from
     * a line.
     */
    @Test
    public void constructorMustSetTheDecryptedVotingOptionIdsProductCorrectly() {
        assertEquals(decryptionLine.getDecryptedVotingOptionIdsProduct(),
            DECRYPTED_VOTING_OPTION_IDS_PRODUCT);
    }

    /**
     * Verifies that the constructor sets the Schnorr signature string correctly from
     * a line.
     */
    @Test
    public void constructorMustSetTheSchnorrSignatureStringCorrectly() {
        assertEquals(decryptionLine.getSchnorrSignatureString(),
            SCHNORR_SIGNATURE);
    }
}
