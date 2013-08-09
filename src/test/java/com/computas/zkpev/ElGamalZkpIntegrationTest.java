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
package com.computas.zkpev;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import java.math.BigInteger;


/**
 * Integration tests on the ElGamalZkp class.
 *
 */
public class ElGamalZkpIntegrationTest {
    private static final String ELGAMAL_PROPERTIES_FILE_NAME = "ElGamalZkpIntegrationTestElGamalProperties.properties";
    private static final String ELGAMAL_PUBLIC_KEY_FILE_NAME = "ElGamalZkpIntegrationTestElGamalPublicKey.properties";
    private static final BigInteger SAMPLE_P = new BigInteger(
            "29694415524868694547282871321880984267031202022823458864263257041746568986471778015259667234110543751555113634616443805962139375046285078323401829260858510032472390190012966034657851484353923231832833968810402532032494318895211750110236165551314661715095509445135638500218831685578342981671946134859719906699191932415489251560912384727057670390637154579803857573469321963289377111726536574860351061875178273535112945394251357336335684559401757590147014741913258905190579567289255726071032119443829261401440375354596782799327343291944647081589372656096031740111929583828004522492506728802713800768666951272442136845783");
    private static final BigInteger SAMPLE_Q = new BigInteger(
            "14847207762434347273641435660940492133515601011411729432131628520873284493235889007629833617055271875777556817308221902981069687523142539161700914630429255016236195095006483017328925742176961615916416984405201266016247159447605875055118082775657330857547754722567819250109415842789171490835973067429859953349595966207744625780456192363528835195318577289901928786734660981644688555863268287430175530937589136767556472697125678668167842279700878795073507370956629452595289783644627863035516059721914630700720187677298391399663671645972323540794686328048015870055964791914002261246253364401356900384333475636221068422891");
    private static final BigInteger SAMPLE_G = new BigInteger(
            "16607256183981132826111668724675137293553423470178029392703636254000969394915443593242285816463816790426469586965883439283181173446757152929279437091642058440068441103826027168433618136872412560244916441006584731350927653326916445930401739767998471384710244087881257847593365527651986814075608831145980451029618369720139766764004455372496817995803944919662728125201034586001478350225527400952749262951762332829236079364709002703456076303893110894893613234330913761817022994734349150303068453656498677460108478426213585514767886482135559791761743101950937740219411992056170836018674456284097692960655755753742616051943");
    private static final BigInteger SAMPLE_H = new BigInteger(
            "27380262206318096550495688428792750654110022610006149621600587917298082071012773554026447485571657443971483324170655283973518507004500287455327740839356442943770244599017380739062423651508548481271584341014324805166361141262088002445299124829820947262650156492111282824787667956818168008087871072128016873760681437232348269612527309252938505632122636541929219878363517682803146360694531469155019530676967710439930622301545462728912498663423272960417603709176177136289744976414162381107898312393089706679778554247207263723846919341801769778166550694031344671762499335596550945811743409373167998755532743262930223682469");
    private ElGamalZkp zkp;

    /**
     * Creates a NizkpCounting object without a results file.
     * @throws IOException Thrown if something goes wrong while loading the decryption file.
     */
    @BeforeMethod
    public void createNizkp() throws IOException {
        zkp = new ElGamalZkp(new String[] {
                    ELGAMAL_PROPERTIES_FILE_NAME, ELGAMAL_PUBLIC_KEY_FILE_NAME
                }) {
                    @Override
                    protected void parseArguments(String[] arguments) {
                        setElGamalPropertiesFileName(arguments[0]);
                        setElGamalPublicKeyFileName(arguments[1]);
                    }

                    @Override
                    protected void run() throws Exception {
                    	// No implementation needed as part of the integration test.
                    	}
                };
    }

    /**
     * Verifies that the correct modulus p is loaded from the ElGamal properties file.
     * @throws IOException Thrown if something goes wrong while loading the ElGamal properties file.
     */
    @Test
    public void mustLoadTheCorrectElGamalEncryptionSystemModulus()
        throws IOException {
        zkp.loadElGamalProperties();
        assertEquals(zkp.getP(), SAMPLE_P);
    }

    /**
     * Verifies that the correct group order q is loaded from the ElGamal properties file.
     * @throws IOException Thrown if something goes wrong while loading the ElGamal properties file.
     */
    @Test
    public void mustLoadTheCorrectElGamalEncryptionSystemGroupOrder()
        throws IOException {
        zkp.loadElGamalProperties();
        assertEquals(zkp.getQ(), SAMPLE_Q);
    }

    /**
     * Verifies that the correct generator g is loaded from the ElGamal properties file.
     * @throws IOException Thrown if something goes wrong while loading the ElGamal properties file.
     */
    @Test
    public void mustLoadTheCorrectElGamalEncryptionSystemGenerator()
        throws IOException {
        zkp.loadElGamalProperties();
        assertEquals(zkp.getG(), SAMPLE_G);
    }

    /**
     * Verifies that the correct public key h is loaded from the ElGamal public key file.
     * @throws IOException Thrown if something goes wrong while loading the ElGamal properties file.
     */
    @Test
    public void mustLoadTheCorrectElGamalEncryptionPublicKey()
        throws IOException {
        zkp.loadElGamalPublicKey();
        assertEquals(zkp.getH(), SAMPLE_H);
    }
}
