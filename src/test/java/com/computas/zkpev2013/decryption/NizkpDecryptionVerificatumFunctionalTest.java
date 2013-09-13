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

import static com.computas.zkpev2013.ZkpFunctionalTestAssertions.assertZkpContainsIncident;
import static com.computas.zkpev2013.ZkpFunctionalTestAssertions.assertZkpDoesNotContainIncident;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import java.security.NoSuchAlgorithmException;


/**
 * Functional tests against the NizkpDecryption class.
 */
public class NizkpDecryptionVerificatumFunctionalTest {
    private static final String DECRYPTION_FILE_NAME = "NizkpDecryption2013FunctionalTestDecryptionFileVerificatum.csv";
    private static final String ELGAMAL_PROPERTIES_FILE_NAME = "NizkpDecryption2013FunctionalTestElGamalPropertiesVerificatum.properties";
    private static final String ELGAMAL_PUBLIC_KEYS_FILE_NAME = "NizkpDecryption2013FunctionalTestElGamalPublicKeysVerificatum.properties";
    private static final String MIXING_TYPE_NAME = "VERIFICATUM";
    private static final String DECRYPTION_LINE_WITH_CORRECT_PROOF = "730071,01,000001,AK1aF65NqQ2QNH9cOnizQi3ssKD9vAdq/s1Vu7hQmjWKDvP3/TdsNjD4pWGn8e2M1kTRfEm+GdHwJCpB3UFVBmOBsFwFN9RjQt3qVGqq+xor/kX0AkJTrkVWsRcv9R+yV7SE57JwtZ8z4itcvEBqZzSz7JMnD1JS0GCfLYrfnVbBf2xArfqE9yEd57JL0oeAQX6pHYOeit4kjlUqy+sEFncDQ++sUITuBfoFfEqQ5AgDStUTpA6lT6FEGL/oo/4v3/cYAQQTojwOf1hU1ofjjGnD/Zyvxb2qN1tuLZkZHSRnVi4/EEZg6udMYX3hccArTzfYYa4jTvx28ZbqWE3Y2pU=#AJk0mAv/52nj7VW4iz4Xle1UCX3cp4hRDp+p6him5xyqHPzehVoDB9rk+YhAjac9PZyMTUbgPNCX+qfNUeYpK6DamJof1hb+WQ6bscrbKwkglrX3rPy1SpoZNETpT7qnGWzU7mXzpaXdQ1z7zUSQ0lCq2G1fvU5IPCkY8huq2xKAfGwCGcOQHcX2wlweY/gJTMH1IIYoC0QL+4AgncmFF4Eo2laCNLV70IOH0cy4Wp3pEov/jOMBGD3zjff21eIwd/GbmMPc7AVT23aYOTyFZZEUd+ETCwbK1vkcHqwIMrT8KARdSFwQSRoviZBieTuphrbME4CbMJHKvgud2iiLXPc=#,57826110722057,MTE5MDcyNTE0MTQxOTU3MzQ3NjE5NDExNjY4Nzk5NTIxODI3NzA2NDY1MjM5NjAyNTQxNTExMzA5MTM0MjMyNjYwOTEyOTgxNzE4NTJ8LTIzOTM1MTI4NDcwNTEzOTAxNzMzNjQwMTM4MjU3MDI2NTg3ODk5Nzk5MjE0MzU3OTA3MTg4OTYzMDg1MzI2MzY0OTI2ODE0ODAyMjUwNjA4Njg0MzU2OTgwODkxOTczMjQ5MTQxNzMxNzY0ODE5NzAyMDAwMzQxNDM5MzcwOTE3NzIxMTU2MjI1MzM0ODA1MDgzODA0MDY2NjY3MTk5NTI0NTE4NTUxNTE4OTg3MzQ0MDM0MTc3OTQ5MTQ3NjcwOTc2NDE0Mjk1NTA3MDAyOTU5ODI3MjQ3NjA0NjYwNTcwODEyMzg2OTIxNjU4MjA0MDMwMzA2MTEzNzY0NDY2MTQ3Njg5OTg5MTE1MjAzNjgzMDAwMzk4MDY0OTk1OTI0MzY4MTk5OTczODk1NzUxNTM3MzU4ODY1MTMyNzUxNTA1MzAwOTcwNTc4MzYzNDMyMjgwNDQ5MDgwMTA3MjUyMTE5ODgzMjYyMTk0Mzk0NjE0NDgzMTM0OTc0NTk0MzMxNjgyNzA3OTU0MDM1NDUwNDg2NjA5MDQxOTYxMjU5MTMxMjU1MDA0NzI4NTA4MzQwODQxOTY1Mzg5MTE3ODc3OTk0Njk5NTY5MzM5NDg0OTY4NzY5NjYxNjM3MTEzMTE5MjQxMTg4ODUzMTUxMTU3OTAwMTQwMzMzNDcwMDM3NjE3MDE3NzUwNTUyOTI1NjUzNjQxOTQwNzkyODI5NTgwODg5MTY2NTkxOTg5NDcwNDY4MTcyMzM2NDg3NjcyMjYwOTYyMjI1Mjk0NDEwODQyNzM4NzU3MTk5NDcxMjk1MzYyNzg1MDA5NTYwOTk3NzIzMDczNTcyNTM2NzIxNjQwMjUxNjY0NDEwNTY3MDk4MzA4MTU5NzAwMTk4NDQ4NzM3NDQyMjUzNjMwMjY5MjMxMTc4ODg3MHxudWxsIw==";
    private static final String DECRYPTION_LINE_WITH_INCORRECT_PROOF = "730071,01,000001,aAK1aF65NqQ2QNH9cOnizQi3ssKD9vAdq/s1Vu7hQmjWKDvP3/TdsNjD4pWGn8e2M1kTRfEm+GdHwJCpB3UFVBmOBsFwFN9RjQt3qVGqq+xor/kX0AkJTrkVWsRcv9R+yV7SE57JwtZ8z4itcvEBqZzSz7JMnD1JS0GCfLYrfnVbBf2xArfqE9yEd57JL0oeAQX6pHYOeit4kjlUqy+sEFncDQ++sUITuBfoFfEqQ5AgDStUTpA6lT6FEGL/oo/4v3/cYAQQTojwOf1hU1ofjjGnD/Zyvxb2qN1tuLZkZHSRnVi4/EEZg6udMYX3hccArTzfYYa4jTvx28ZbqWE3Y2pU=#AJk0mAv/52nj7VW4iz4Xle1UCX3cp4hRDp+p6him5xyqHPzehVoDB9rk+YhAjac9PZyMTUbgPNCX+qfNUeYpK6DamJof1hb+WQ6bscrbKwkglrX3rPy1SpoZNETpT7qnGWzU7mXzpaXdQ1z7zUSQ0lCq2G1fvU5IPCkY8huq2xKAfGwCGcOQHcX2wlweY/gJTMH1IIYoC0QL+4AgncmFF4Eo2laCNLV70IOH0cy4Wp3pEov/jOMBGD3zjff21eIwd/GbmMPc7AVT23aYOTyFZZEUd+ETCwbK1vkcHqwIMrT8KARdSFwQSRoviZBieTuphrbME4CbMJHKvgud2iiLXPc=#,57826110722057,MTE5MDcyNTE0MTQxOTU3MzQ3NjE5NDExNjY4Nzk5NTIxODI3NzA2NDY1MjM5NjAyNTQxNTExMzA5MTM0MjMyNjYwOTEyOTgxNzE4NTJ8LTIzOTM1MTI4NDcwNTEzOTAxNzMzNjQwMTM4MjU3MDI2NTg3ODk5Nzk5MjE0MzU3OTA3MTg4OTYzMDg1MzI2MzY0OTI2ODE0ODAyMjUwNjA4Njg0MzU2OTgwODkxOTczMjQ5MTQxNzMxNzY0ODE5NzAyMDAwMzQxNDM5MzcwOTE3NzIxMTU2MjI1MzM0ODA1MDgzODA0MDY2NjY3MTk5NTI0NTE4NTUxNTE4OTg3MzQ0MDM0MTc3OTQ5MTQ3NjcwOTc2NDE0Mjk1NTA3MDAyOTU5ODI3MjQ3NjA0NjYwNTcwODEyMzg2OTIxNjU4MjA0MDMwMzA2MTEzNzY0NDY2MTQ3Njg5OTg5MTE1MjAzNjgzMDAwMzk4MDY0OTk1OTI0MzY4MTk5OTczODk1NzUxNTM3MzU4ODY1MTMyNzUxNTA1MzAwOTcwNTc4MzYzNDMyMjgwNDQ5MDgwMTA3MjUyMTE5ODgzMjYyMTk0Mzk0NjE0NDgzMTM0OTc0NTk0MzMxNjgyNzA3OTU0MDM1NDUwNDg2NjA5MDQxOTYxMjU5MTMxMjU1MDA0NzI4NTA4MzQwODQxOTY1Mzg5MTE3ODc3OTk0Njk5NTY5MzM5NDg0OTY4NzY5NjYxNjM3MTEzMTE5MjQxMTg4ODUzMTUxMTU3OTAwMTQwMzMzNDcwMDM3NjE3MDE3NzUwNTUyOTI1NjUzNjQxOTQwNzkyODI5NTgwODg5MTY2NTkxOTg5NDcwNDY4MTcyMzM2NDg3NjcyMjYwOTYyMjI1Mjk0NDEwODQyNzM4NzU3MTk5NDcxMjk1MzYyNzg1MDA5NTYwOTk3NzIzMDczNTcyNTM2NzIxNjQwMjUxNjY0NDEwNTY3MDk4MzA4MTU5NzAwMTk4NDQ4NzM3NDQyMjUzNjMwMjY5MjMxMTc4ODg3MHxudWxsIw==";
    private static final String BROKEN_DECRYPTION_LINE = "*";
    private NizkpDecryption nizkp;

    /**
     * Creates a NizkpCounting object, loads the files with the ElGamal properties,
     * the ElGamal public key and the decryption file, and runs the protocol.
     * @throws java.io.IOException Thrown if something goes wrong during IO.
     * @throws java.security.NoSuchAlgorithmException Should not be thrown.
     */
    @BeforeMethod
    public void createNizkpLoadDataFilesAndRun()
        throws IOException, NoSuchAlgorithmException {
        nizkp = new NizkpDecryption(new String[] {
                    ELGAMAL_PROPERTIES_FILE_NAME, ELGAMAL_PUBLIC_KEYS_FILE_NAME,
                    MIXING_TYPE_NAME, DECRYPTION_FILE_NAME
                });
        nizkp.run();
    }

    /**
     * Verifies that the NIZKP produced an incident about the broken line
     * in the decryption file.
     */
    @Test
    public void resultsMustContainBrokenLineIncident() {
        assertZkpContainsIncident(nizkp,
            new DecryptionFileBrokenLineIncident(BROKEN_DECRYPTION_LINE));
    }

    /**
     * Verifies that the NIZKP produced an incident for the line that contained
     * an incorrect proof.
     */
    @Test
    public void resultsMustContainIncorrectProofIncident() {
        assertZkpContainsIncident(nizkp,
            new DecryptionLineWithIncorrectProofIncident(
                new VerificatumDecryptionLine(
                    DECRYPTION_LINE_WITH_INCORRECT_PROOF)));
    }

    /**
     * Verifies that the NIZKP didn't produce an incident for the line that
     * contained a correct proof.
     */
    @Test
    public void resultsMustNotContainIncidentAboutCorrectProof() {
        assertZkpDoesNotContainIncident(nizkp,
            new DecryptionLineWithIncorrectProofIncident(
                new VerificatumDecryptionLine(
                    DECRYPTION_LINE_WITH_CORRECT_PROOF)));
    }
}
