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

import static org.testng.Assert.*;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigInteger;

import java.security.NoSuchAlgorithmException;


/**
 * Unit test against a decryption line dealing with the zero-knowledge proof specifically.
 */
public class DecryptionLineProofUnitTest {
    private static final String COMMA = ",";
    private static final String ELECTION_EVENT_ID = "730071";
    private static final String ELECTION_ID = "01";
    private static final String CONTEST_ID = "000007";
    private static final String ENCRYPTED_VOTING_OPTIONS_IDS_PRODUCT = "AIJ1jejPt76pKEIpr1BPUt5z0mT9FExthLmUbm/QZYAJv21+8rsw/tG4bFPe7N5D#n#0rfh+Ir7IJns5p6ehk98I2LxNgJzi+fOm82tNjhejsjIC4vWqFRZ0tF6Ry1Si5io#n#pUUOllrt8DxdmcI30fanp/0hNAdi3GPQ61v+ABOrZonjDvCFirAv9nnpHuTTlYlr#n#1L5oiHG3r6mjLy+e8vVAHur8xMCH/fQ1XJLvRby9wnSkZrAEMb1C0OLreaalVkSv#n#l4SiDebrdKgBrA3xR4ERCKwzeevif2uAcAirOtW/ph2k78mgMDsgI/7vDOMJElD+#n#WwN5+MQu4/58W9vh2x5aSCI=#AJ88iT77CUB5YuklJWiN56+DnsmoJkO2WUh7966m7RVYsMaiM/nCuut4CKeA6mxY#n#nM9BfVXHE5aM9vRW8W5Fu4vMQO721xRkSBCRDs1tAVe9Yjy3BaV8duL34sQkKTeA#n#YKklOB4gcMZjdBj1W2Il/2D/vniwUFeyMXbSqkynxiwS16LFDwyq58OUOVDDKLG6#n#AiovTltRtwHE7mrQJLwgqAH6NCUmx2dOE6K1WDdI4Twa+bGEZZGXO3qankiQpxuC#n#Lnp+U77I26/jXoFQzZC0VuC/sdxNqUhoLx75j28Qq0DstZ3SYNRH8M226K5maBQS#n#oQZwUE4PSONpA2AUjdVUtEQ=#";
    private static final BigInteger DECRYPTED_VOTING_OPTION_IDS_PRODUCT = new BigInteger(
            "3741264158469368466404212269249748092711943731820583364814408291710732477410869254686238207275582240120832");
    private static final String SCHNORR_SIGNATURE = "NzA3NDIxOTc2ODc5NzEyMTEyMjAyMzMzMzc0OTMxOTU0MjgxNTQ1OTQ4ODU1NTc4MDg1NTYzNzQxMjIzNzkzMTE4NTk1NjA0Mzk4NXwtNDY1NDUzNzQzNDA0NTY4MTYyNzQ0NTM4NzU0ODU4NjAzNzIxMjAzNTI4NDcyNDU2MzQzNTQ5MTg3ODk4MjU4Njg1MTgyNjcxODk2MzcxMDEyOTMzMDc2NjAyNjkzMDQxNjgwNDM0MDg3Mjc0NzE4MjUzNjI2MDkxMzEyNzYyMDQ2OTAwOTczNTIzMzYyODA3ODA2MzY0NjkxNDg2NjE5OTIyMDgwNzg3Nzc0ODg1MzQ5MDYxMDk5MjIwOTExMzM4NTkyMDE2OTE2NjQ3ODU1Nzk2NTI5NzAzMzk2MDY1ODEwNzgxMDg2NzQ5ODY5ODc5MTQ3MjY3MTQ5NzI3ODczMjA5NDg3MDQwNDM5Nzc1Nzk3NzQzMTY5Mjg0MTAyNjcyNTgyMjM4NDg0MDc1NDUwMjIxMDI0MTM4NjA1MDEwMDExMTE5NjU5OTU3MzM1MDQ4MDc2MzY0OTY2MDQyNjgzNTg2NzY3NDk5ODczNzcyNDQ1ODkyMjAyNjQxNjA1NDU5ODY0MzQ1MzUzNDI3OTk5MTM5OTU0NzUwMTE1NTY2MTU5Njc1ODMwNzIxODgyMDE2MTM3NzczMzczMTg2NTk0NDU0ODc0NTEwOTcwODM1Mzc4MzY2OTM1ODE4MDg3MDE4MTYzMzU4OTYwMzcyMDY5MzA4ODAxNzQ5NTY4NDQyODkyMTEzMzA2MDE2Mjc4MjIzNTMxNjkwNDA5Mzg3NzEyMDA0MDI3MzgxNzI3NzQ2NzY5NDQ1MTM5NzQ2NDYwNzk2Njg3MjQ2MzMzNjgwNTgzODM5NzIyODI1MzQ1MjU3NTYwOTA0MDI5NTEwNTAwMTA3NjIyMDgyODU3NDcyMTc2NzM2MDA4OTM1MjMxMzc4OTMyOTIzNzc0NDk4NTkyMDQ2NDAwMDE1NjM0ODk4ODU0MzkwNTN8bnVsbA==";
    private static final String SAMPLE_LINE_WITH_CORRECT_PROOF = ELECTION_EVENT_ID +
        COMMA + ELECTION_ID + COMMA + CONTEST_ID + COMMA +
        ENCRYPTED_VOTING_OPTIONS_IDS_PRODUCT + COMMA +
        DECRYPTED_VOTING_OPTION_IDS_PRODUCT + COMMA + SCHNORR_SIGNATURE;
    private static final String SAMPLE_LINE_WITH_INCORRECT_PROOF = ELECTION_EVENT_ID +
        COMMA + ELECTION_ID + COMMA + CONTEST_ID + COMMA +
        ENCRYPTED_VOTING_OPTIONS_IDS_PRODUCT + COMMA + "161195548" + COMMA +
        SCHNORR_SIGNATURE;
    private static final BigInteger SAMPLE_P = new BigInteger(
            "22519781860318881430187237378393910440433456793106883439191554045609533190204716026094503488051043531257695232100353994296431999733305913289830606623675094806877884255872439714678914992056169353692036021770097223778392105262307803104951171429150982767069700653909195647599098780046724703785991755259095912786508845222597772887203546632493935590809326329822837682361511439054458165467044490658668908755516611075852591340913731324282531411301527453756791057107929172839003743485012313000403534330922416540828874783338650662007436059441348150784982317988527563812882812455109992843656727186872083932493433216403334110087");
    private static final String SAMPLE_LINE2_WITH_INCORRECT_PROOF = ELECTION_EVENT_ID +
        COMMA + ELECTION_ID + COMMA + CONTEST_ID + COMMA +
        ENCRYPTED_VOTING_OPTIONS_IDS_PRODUCT + COMMA +
        (SAMPLE_P.add(BigInteger.ONE)).toString() + COMMA + SCHNORR_SIGNATURE;
    private static final BigInteger SAMPLE_G = new BigInteger(
            "19140805144017540497707786625792286921405966026933054372546212478081606825277100641152753685549997641757412202354820556035283634167831414111680419792323513134593333242130321485704646466132023972092638166348661889738666905149514346301514811888481584122903522192426522565163431677342704016489172931295206771271956675417455593733493182654653217734161457736905965163738973158663182502896276372310802897020170186481607829975539047679142654262779602075975560918037248175928164089234991713304225443874682021320096580809741466636539107495416015820825091232630444699803381650489285110532680106190202164342326569938521995903077");
    private static final BigInteger SAMPLE_H = new BigInteger(
            "15767103161188431726810732547407071177925682470096161113662719329970151989521808605211708980148853646358184785265698288206467886519416824938097511897175394153183299970057910661112235919650701007926207445783398837008410584396746210082523938166164511910717655019379900382402878897548938693825625577255875241671695927055614575927068409397924078583190963658265084557648260898854231641007945637962701179737860118584226735942584888595742172827013353350499066429923853690118418094376964200302533698193066579535824852762698049107984716754445670202902488607524608492482523694573929391910902766007429761724512335207851342013142");
    private static final BigInteger NON_INTERACTIVE_CHALLENGE_X = new BigInteger(
            "-33445384085591256350540927631536215588676376758228127143444281486564067839059");
    private static final BigInteger GENERATOR_G1 = new BigInteger(
            "19429884795956629129917769183187635478059186346425091057779247694005688350154466352982335692735817782966541128126165919924110245917350843295664047926349244204460558161453410420638101517901766743517305238547302421016135589768010146914220208698529470642205084315184998918648064855441439355187385600126655587351572571143803523624215440203886089396126262475643083965151151197930923629016222940470397008687304779471793877180012412553427326676191923895607582431430257585616170969160086481252387485628971021662439122866152051085883942846002795947563266675075984061823328109764029951477155764689580078355164019039633044413264");
    private static final BigInteger PUBLIC_KEY_H1 = new BigInteger(
            "10897738252362030163356320093503365534570272896458816972158937990219534607895521890880160732971123974805558717676651757153255216302868886149187337181427052852971706320625421194386125536939716164612352212516562197742416182404933987284104578093177346230164157725802773887379299941066348014396332331726004775536865143754774657523584750448805325209381531420342825549979266280252721228682174672995350543146808280594283974765477752540773217538910725740392357475614838521314865210711860526914928369548556800459376178892162796679901065427097778268649690803476936880333220537169264707369767720692972759292177228014599652673961");
    private static final BigInteger SCHNORR_MESSAGE_W1 = new BigInteger(
            "818410491287811095125665958362975103397322525148075343115915733160293280820678676162026541162219064522113362252274353098527656862269957365926540708257927847383478323305312365527901828839506984755764974875778343233801411113469293197040285710037665700766578516675916797889048379638195320619670853642295368197098410532914441053232555731300822465537329684195656836273905287494215726468216381609664349929792463588703675362276249971781665005099950575108492629939810103603256479359980886782765553550375213650585784086759103356975282931807630417416781852552395910705777993835719728741914658773131753313639523901788938752292");
    private static final BigInteger SCHNORR_SIGNATURE_C1 = new BigInteger(
            "7074219768797121122023333749319542815459488555780855637412237931185956043985");
    private DecryptionLine decryptionLine;

    /**
     * Creates a decryption line to run the tests against.
     */
    @BeforeMethod
    public void createDecryptionLine() {
        decryptionLine = new DecryptionLine(SAMPLE_LINE_WITH_CORRECT_PROOF);
        decryptionLine.setEncodedVotingOptionIdsProduct(SAMPLE_P);
        decryptionLine.calculateDecryptedVotingOptionIdsProduct(SAMPLE_P);
    }

    /**
     * Verifies that the non-interactive challenge x is calculated correctly.
     * @throws java.security.NoSuchAlgorithmException Should not be thrown.
     */
    @Test
    public void mustCalculateNonInteractiveChallengeXCorrectly()
        throws NoSuchAlgorithmException {
        assertEquals(decryptionLine.calculateNonInteractiveChallengeX(),
            NON_INTERACTIVE_CHALLENGE_X);
    }

    /**
     * Verifies that the generator g1 is calculated correctly.
     */
    @Test
    public void mustCalculateGeneratorG1Correctly() {
        assertEquals(decryptionLine.calculateGeneratorG1(SAMPLE_P, SAMPLE_G,
                NON_INTERACTIVE_CHALLENGE_X), GENERATOR_G1);
    }

    /**
     * Verifies that the public key h1 is calculated correctly.
     */
    @Test
    public void mustCalculatePublicKeyH1Correctly() {
        assertEquals(decryptionLine.calculatePublicKeyH1(SAMPLE_P, SAMPLE_H,
                NON_INTERACTIVE_CHALLENGE_X), PUBLIC_KEY_H1);
    }

    /**
     * Verifies that the message part of the Schnorr signature w1 is calculated correctly.
     */
    @Test
    public void mustCalculateSchnorrMessageW1Correctly() {
        assertEquals(decryptionLine.calculateSchnorrMessageW1(SAMPLE_P,
                GENERATOR_G1, PUBLIC_KEY_H1), SCHNORR_MESSAGE_W1);
    }

    /**
     * Verifies that the challenge of the Schnorr signature c1 is calculated correctly.
     * @throws java.security.NoSuchAlgorithmException Should not be thrown.
     */
    @Test
    public void mustCalculateSchnorrChallengeC1Correctly()
        throws NoSuchAlgorithmException {
        assertEquals(decryptionLine.calculateSchnorrChallengeC1(SAMPLE_P,
                SAMPLE_G, SAMPLE_H, SCHNORR_MESSAGE_W1), SCHNORR_SIGNATURE_C1);
    }

    /**
     * Verifies that the line with the correct proof returns true when asked for the proof result.
     * @throws java.security.NoSuchAlgorithmException Should not be thrown.
     */
    @Test
    public void mustVerifyCorrectProofAsCorrect()
        throws NoSuchAlgorithmException {
        assertTrue(decryptionLine.verifyProof(SAMPLE_P, SAMPLE_G, SAMPLE_H));
    }

    /**
     * Verifies that the line with the incorrect proof returns false when asked for the proof result.
     * @throws java.security.NoSuchAlgorithmException Should not be thrown.
     */
    @Test
    public void mustVerifyIncorrectProofAsIncorrect()
        throws NoSuchAlgorithmException {
        assertFalse(new DecryptionLine(SAMPLE_LINE_WITH_INCORRECT_PROOF).verifyProof(
                SAMPLE_P, SAMPLE_G, SAMPLE_H));
    }

    /**
     * Verifies that the line with the optimized correct proof returns true when asked for the proof result.
     * @throws java.security.NoSuchAlgorithmException Should not be thrown.
     */
    @Test
    public void mustVerifyOptimizedCorrectProofAsCorrect()
        throws NoSuchAlgorithmException {
        assertTrue(new DecryptionLine(SAMPLE_LINE_WITH_CORRECT_PROOF).verifyProof(
                SAMPLE_P, SAMPLE_G, SAMPLE_H));
    }

    /**
     * Verifies that the line with the optimized incorrect proof returns false when asked for the proof result.
     * @throws java.security.NoSuchAlgorithmException Should not be thrown.
     */
    @Test
    public void mustVerifyOptimizedIncorrectProofAsIncorrect()
        throws NoSuchAlgorithmException {
        assertFalse(new DecryptionLine(SAMPLE_LINE2_WITH_INCORRECT_PROOF).verifyProof(
                SAMPLE_P, SAMPLE_G, SAMPLE_H));
    }
}
