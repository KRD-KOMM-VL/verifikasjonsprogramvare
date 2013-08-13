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
 *
 */
public class DecryptionLineProofUnitTest {
    private static final String COMMA = ",";
    private static final String ELECTION_EVENT_ID = "999901";
    private static final String ELECTION_ID = "01";
    private static final String CONTEST_ID = "000004";
    private static final String ENCRYPTED_VOTING_OPTIONS_IDS_PRODUCT = "MzY2MDI1Njg4NTgzMDQ5OTQ5NTE2NTQ2ODU5NTAzNjQxMDQxNzUzMDMyODE3NDM2#n#MDM5ODc4OTgwODYxNzgxOTU4Mjk4Mjg3Mjc0MzMyMzI1MDExNzA2NTc0NzA2Mzkw#n#NDQwMzcyMjQ5MjE4MzAxNTUxMDA5NjY2ODA0ODYzNDc4MTE4NjI4OTM4NzE2MzM2#n#MDEwNTk0NzY0NzUzNTM2OTAxNTgyNTE1MDEyMTgwNjUwMDI4OTg2MTQ1Nzk3ODMx#n#OTMyNjkzODAxNjA0NTUyODA1NDQ1OTY2MTcwNTgyNTkxNTM4NzMyNDg4NTA2Mzg4#n#MzIzMzk2MzUyNDQ4MTQzNjUwMzE2NDc2MDgwMjQwMjY3NTc3NjMyMzgyMTY5OTMz#n#MTU1MjIyNzg0MTU2Njk4ODU0OTE1NTE5MzQxODA4OTk3MDIwODg0NTAxNzA4MTY2#n#NzY5Mjk2ODc2NTE1NDYwNTM5MDAxNDM1MTI0NDk1Mzk5MjgzMTQ2NTU4ODk0NTM1#n#NjM3MDA4NTMwMDM5MjgyMjAwMTI4NzQ2NTYxNDEyMzE5OTc4MDM3OTUxMzk1NTkz#n#Mzg0Mzg1MjMwNDAyNzk0NjMyNzE4NDE2MTg4OTk5NDMyNTM1MzA3NDU0MjIwODU2#n#ODQ5NDU0NzYxODg0MDU2NzI1NzgyMTczMTkzNzM5NjQyMjg3MDI2ODI0NTY0OTc1#n#MTcwOTQxNTUyMTY3MTI2NzE0OTY1MTI5NTI3ODYwMTUwODg0MjE0NjUyNzUzNTg3#n#MjczMTkyODc3MjczMzM2NzMyNTEwNDE3MjM5MjIzMDA4MTY3ODI3MnwyMjQ5NDA3#n#MjA0NzM4OTYzNzc0OTc0MTI2NzIzODA1MzQ0ODM5ODMzOTkyMTY2MzEzNTIzNDI3#n#MDY3NDE0MDgwOTMyNDgxNzQ2MDMzNTA4NzM3NTgxNDc5MTY1MzY1MzYwNDIwOTY0#n#MDA0MjI3NjEzNjA3MzU4MTE1ODc0MDY2MDA3NTM0NjIwNjcyMjU5NTQ5MTEzMDUy#n#NTI2MjM3NjU2NDY4MTg3OTQ0OTIwNjMxODc1NjQ2OTU0MTEyOTkxNDA0OTAxNDIx#n#NTkwNDU1MDEwODA1ODM4ODE3OTUxMDgwNjY2MDI0MTkwNTQ0NTcwMTc4NTcwNTgy#n#NjUwMDkxMzg4MTI5ODg0Mjk3MzcxMTAwNzgyMzIxNzc0OTUzNjU0OTYxMDE2MDM2#n#OTE5NDI0MjQzODQ0NTMxMDA3MzYwMzg5NDUzMTQ2MjM2NjM0NTczNTk2MzgyOTk3#n#MzA0NDY2NjU1MjE1OTE4OTc0ODg3NjM3MzkwMDkwMjgxMzczNTk4NDAyNjE2MDU0#n#NDgyNzI5Mzg3MzYwMDkzNzAyODgwMDEwNjU0Nzg5MjA4MTUwMzAwOTk2NTE0MDA0#n#Njk0NzQyNzY4NzYxMjA0MDM2NDU2ODUzMDIzMDU2NjU3OTQ4NjY2NjI4MzEyNTg5#n#MjM0NjYzMTQ3NDEzOTg4ODgyNjM4OTMzMzIzODY2NjcyMzM2ODg2NzE5MjU3MTUx#n#NzU4MDQyNDYzNDM0MzMyMTQ1ODI2MzgwMTgxMjg4NTM2MTM2MjMwNDM2OTcxMzAx#n#OTUzMTE3OTIzNjcyMzQ1NzUzMjg4MjgzMzkwMDcwNTU5MQ==#";
    private static final BigInteger DECRYPTED_VOTING_OPTION_IDS_PRODUCT = new BigInteger(
            "161195549");
    private static final String SCHNORR_SIGNATURE = "OTgxMzYzNTc0Mjk2MzcxMjg0MTQyMzQzMjQ2MTA3ODQ5Mzc3NzYyNDI1NDIxMjg0NDY0MTUyNTczMDU2MTc4MTIzNjIwNTIzMTE5MHwtMjQyNjQ1NTkwNzk0NjE5NTE4NDI3MTA1ODM4MzEwNjA1MzcxODg2NjUzMTUxMjkxMDgyMzUwMzE5MjAxOTI5MzkwNzQ3MDU3NzkyNjk0NTk3MTYzMDU4Mzk4NTMyODA3MDMyNDcxODU1MzE0NTkwMzMwMzkzNDk4Mjc1NTYyMTA5MzQ0MTkwOTM1MTcwOTkyODU3MjQ5NjI1MjA0NTEzMTI3NTkxNzM5MjkyNDM5OTQzNDUxNTgyNDk0OTk3ODE5MDU0MzM4NDk0MTM1NDk5OTQ4OTIzMjMwMjk3NzA3NjcxMTA5NDUxNjc0OTg4NDU0MTUwNzkyMDYyNjk5MzkyOTM2ODMyNjI3MzEyMzc2ODUxMDE1ODIzMjU2NTM0MDkyNzYwNjk3MDUzMDUzMzczMDM0Nzg2MzIzNDAwOTg5MjY3MzAyNDg0MzE3NzYwMjQ5ODMxMDg2NTUwMjY0NDgxNzYyNzA5NDE4MjUyODU0MzM3OTY2MjY0NzM3NDgxMTg5Njg3MzUzODkxNDU0NzQ2MjY3OTA2OTgyNTE4Njk5NjI4MDg3MTEyMTQ1MzA3NjY1NzQwNzg3NTQ3MzA4NzM2ODMwNjgzNDIyMTQwMjk4OTg0ODQ2MTA4ODM2ODQwOTM5NTEyMTgzNDk0OTk2Njg2OTEzNzczMTI3OTM5MTI4NzMzNjQ5MjgzNjY3OTE3MzM0NDEyNDQ4MDQ3MjYzNTY3Nzg1MDM1NzIxNTE3NjM5MTM5NDQ3MjkyMjYwNjk2NzI5MDk1MjU0NDAxMjY3ODE2ODQ0OTUyNDgxOTY0ODQ1Mzk2MTM1MTI2NjQ2MTEyMDQzMDY4NzQyMDUwNTQwNzUwODcxNjg4OTM0OTk3MTY0ODM2MzMxMTAyNDg3OTM4NjUxMzY2NDMxMzUxNjE2ODYxMzYwNTYzfG51bGw=";
    private static final String SAMPLE_LINE_WITH_CORRECT_PROOF = ELECTION_EVENT_ID +
        COMMA + ELECTION_ID + COMMA + CONTEST_ID + COMMA +
        ENCRYPTED_VOTING_OPTIONS_IDS_PRODUCT + COMMA +
        DECRYPTED_VOTING_OPTION_IDS_PRODUCT + COMMA + SCHNORR_SIGNATURE;
    private static final String SAMPLE_LINE_WITH_INCORRECT_PROOF = ELECTION_EVENT_ID +
        COMMA + ELECTION_ID + COMMA + CONTEST_ID + COMMA +
        ENCRYPTED_VOTING_OPTIONS_IDS_PRODUCT + COMMA + "161195548" + COMMA +
        SCHNORR_SIGNATURE;
    private static final BigInteger SAMPLE_P = new BigInteger(
            "29694415524868694547282871321880984267031202022823458864263257041746568986471778015259667234110543751555113634616443805962139375046285078323401829260858510032472390190012966034657851484353923231832833968810402532032494318895211750110236165551314661715095509445135638500218831685578342981671946134859719906699191932415489251560912384727057670390637154579803857573469321963289377111726536574860351061875178273535112945394251357336335684559401757590147014741913258905190579567289255726071032119443829261401440375354596782799327343291944647081589372656096031740111929583828004522492506728802713800768666951272442136845783");
    private static final String SAMPLE_LINE2_WITH_INCORRECT_PROOF = ELECTION_EVENT_ID +
        COMMA + ELECTION_ID + COMMA + CONTEST_ID + COMMA +
        ENCRYPTED_VOTING_OPTIONS_IDS_PRODUCT + COMMA +
        (SAMPLE_P.add(BigInteger.ONE)).toString() + COMMA + SCHNORR_SIGNATURE;
    private static final BigInteger SAMPLE_G = new BigInteger(
            "16607256183981132826111668724675137293553423470178029392703636254000969394915443593242285816463816790426469586965883439283181173446757152929279437091642058440068441103826027168433618136872412560244916441006584731350927653326916445930401739767998471384710244087881257847593365527651986814075608831145980451029618369720139766764004455372496817995803944919662728125201034586001478350225527400952749262951762332829236079364709002703456076303893110894893613234330913761817022994734349150303068453656498677460108478426213585514767886482135559791761743101950937740219411992056170836018674456284097692960655755753742616051943");
    private static final BigInteger SAMPLE_H = new BigInteger(
            "27380262206318096550495688428792750654110022610006149621600587917298082071012773554026447485571657443971483324170655283973518507004500287455327740839356442943770244599017380739062423651508548481271584341014324805166361141262088002445299124829820947262650156492111282824787667956818168008087871072128016873760681437232348269612527309252938505632122636541929219878363517682803146360694531469155019530676967710439930622301545462728912498663423272960417603709176177136289744976414162381107898312393089706679778554247207263723846919341801769778166550694031344671762499335596550945811743409373167998755532743262930223682469");
    private static final BigInteger NON_INTERACTIVE_CHALLENGE_X = new BigInteger(
            "10209250963475676969884324333814819242907620713954513370822236718553645132301");
    private static final BigInteger GENERATOR_G1 = new BigInteger(
            "7300756896783348518682270642526696667652621253995263822636317247299202752639285775112660436962076051159971433258571244594150936197332218566247284891045610642251088850338238554087859166659289733812304284771428204851102952058753607179617611396861055069124435474394952955470227195895970572745312661418914411540258443090683068051902071249912222040346140542857255746510530289858978802980301837539047256963850917341671868520330456164139941816132154180202811223658639920432094668728535498173483829665780832137083209301003145710282550960493007597552873831368948915207364293996622613429288428862131084722794522302630874100877");
    private static final BigInteger PUBLIC_KEY_H1 = new BigInteger(
            "16560587255894776169173258201329593878372226945714449687785102756792925404246257815636035238354845369415659588928067449128548232361445196956403160713831263083059469850050249352668887172830481909877903010802095936336058465670268084450001701314577535572633713676272069333418526780192819301630150344796371725978135583556373861741502067005096601090928115977336753912769389666301326758731643967102608403474714914534166015408457040978183663373454646424693285567089878761742318097286239879300539899778535246901700899634111581053580782020621566222493594836911562477396885641409219701234367561234897028264218471387848878038291");
    private static final BigInteger SCHNORR_MESSAGE_W1 = new BigInteger(
            "6428874708984865784073720647703962124533131103335677292308721132399191884701890103985913692200394551853860394827846823656233678404056797225968127288728773345141774170906385301613803463878049919367131245190506554278588659067570736452373339471388835860629131352706742480999668821269833519303104780383214664654814525946284980707274997459202284175171878381915833399755416680901157756105827462251320608694337329669118430940110897472073856059416851323344130766244727461684345561197658181468888547258841245959015812586636243747354978155589256471387362308309362317815412304193049255203830114665627026196879849054218242646219");
    private static final BigInteger SCHNORR_SIGNATURE_C1 = new BigInteger(
            "9813635742963712841423432461078493777624254212844641525730561781236205231190");
    private static final BigInteger SAMPLE_P2 = new BigInteger(
            "17312396432581981580082190897223306685078388934445663610081959661991268556046218704752902703549137760202385764805905093548375235316344862202335059600756979252209414418395820250774917064512150050319288755330141549697515847291918002586715614321304133894649250312630630452561195478934902991197220606699872183448985224400971078192308280610575220770572965229787346702188577070094281720879989868794352019870542081675785471867934328383519378690265632557641223653098223911604825521131690175552258941946860339362396293347346594639155605207874619696896736094913523391002359232901724372123502538254705894038777994765334841792223");
    private static final BigInteger SAMPLE_H2 = new BigInteger(
            "675129691584723594113758006833884011376394421915375844462735193566461953466372771872370900759261984419929138296002662911259105716547347784504812855089452234080210330496110643431297092658028802536895855481482935734913496966118641078145398016179919662989518524591177492573060717642657486750212213231471524379104800687744892180241923763076468055742543906176214104062391955606191598971865292979473903330516666292385327992390908555566494407414509268138519923464635927726675314464372771688440216846538452214910075483838289008370120071675103657581742286791813820809557013664803615227732095931812619337802689549489787953856");
    private static final BigInteger SAMPLE_G2 = new BigInteger(
            "13644405161015302882016047829242932852620332408721754752942601291830644699966466342834588554918929233794661337885102815605593083270789145237859087473923174238517766011766898482409235947066622650031526070319187324894543252996187755971475275509772513413703917605777919615958596851377333609346472222246953118731536891610477042506195979415052803070837639347921875885733956941006410159354156198330336224374840599241657272268443272539844687318759174882164819513749378621462039255073866764424986883140287264294879481976139098356325921647208467642521257270762967013702853761847487315542121690080455766666368742292009710872761");
    private static final String SAMPLE_LINE2_WITH_CORRECT_PROOF = ELECTION_EVENT_ID +
        COMMA + ELECTION_ID + COMMA + CONTEST_ID + COMMA +
        "OTg0ODM1Mzg2MTEzODM5NTc1MTEyMDkxOTAwNjAxMzc5OTMyMjM3NDYyMDEyMDM5#n#NTAzNDAzMjg1OTkyMDE4NDM2MzA5ODE5MDY1ODI5Mjc0ODE0Mjc5MDE5Njk4MTAw#n#MjAyNDgwMzc3MjUwNjI0NjcwMzYwODA5NTM1Mjk1NzA5NDY1OTA1NTkxMzU5MjMw#n#NzU5Njk5NzY2ODc1MjM4NjMwMjI0MjMzOTg1MzA4MTUxODE4NDcwMjYxMDM3NDA2#n#NzA5ODIzMzY3NDU1MzU1NjYyMDgxNzc0NDUxMDc5NTc0NDY3MzMwMzA0MjE1MDIw#n#NDY1MzQ4MTY2MzgxNjc3NDg4OTQ1NTIwMTg0MzY1MjgwNjEyNzM0MDQ2MDgyMTg1#n#NjQwNjQzNTU0Njc0OTY0NjgzODY4MTgwOTcxOTMxMTQwMDU0NzI2NjIzNDIyNDI4#n#NDAyMTcwODc3NTQzNjc0MDQ5MDY2NDQzMDIwMDc4NzQ2MjAzMjYxMTA3MTk0MDgw#n#OTY3MzM1MzkwNjkwNDkwOTAxMzE3NTEyMzg3OTczMjM3NTUxMzQ4Mjc1OTI1MTk0#n#OTY2MjQ0ODk0ODk3NjQxOTQyMDYyMTQ3MzM4OTM1MDI3MjkwNzA3OTAwNDMyMDg0#n#OTIxODY0MjcxMDMyNDM3OTUxOTY3NzQ3OTMyNjY5MTU3ODk2NzMwNDA0MDMwNzM1#n#Mjg2MzA5ODkyNzQ1NDA3NzIyOTA2MjkzMDQxNDE2NzQ2MDE2NDI4MzY2MjEwMTA1#n#ODA4NTM1MTUyMzM5NjMyMjAxNTAwOTc0OTAwNjkzODYyMDA0MjI1fDk0NTgzODA5#n#MDczNzYxOTYxNjEwODI4NDk0NzIwOTcxMjc0MDI2ODAwNDIxMjk4OTE0NjY4NDA3#n#NDI4MDg3MDg2NTIyNjQ4OTcyNTMzMzU3MjQ4ODA0NjAxMjA4MzY1MTU0NzMwMTI1#n#OTM5MDc3MjEzNDAwNjI2OTYxMjAwNjU1ODYwNzkyOTAxMTMwOTMzNjYwODg3Mjc5#n#NzM3ODgzMTg1MDM1MzMxNTM4NDMxODAzMzAzMDA5MTA1OTk0NTk2MTk3Mjc3NDE5#n#OTQ2NTgxNjY2NzU1NDI3MzE3NTAxMTUzNTk3NzM4NjgwMDcyODE0MTgwMTQ3MjMw#n#MjA5Mzk1Njc5NjUwMTQzNDA5OTg0Mzc4NjQyOTI5MDI4OTE2NjQ5NDczODY1MDI0#n#ODA4MjgwMjczMjAzOTM3ODgxMzU1ODEwNjI2NzI3NjAzNjE2OTczNzcxNDg3OTM0#n#MDQ3MDc2NTYxNTQwNjg1NzA0OTUwMDEwMDY4MTcyMDM5NzE1MTU5NjA1OTMzMTcx#n#NjQyNzE3OTg3OTc5MDQyMTA2MzY0ODc4ODkxNzIyNDgyMjkxOTY5MDA3MTg3MzUy#n#NDc4NTI1MDMxMjY1NTYyOTMwNjI1OTgyNjU4NzY4OTI4NTY4NzA2MzM4ODUzNTQ3#n#MzMxNjQxNTQzNTgxMjYwMjM3MjU3NzgzMDc5MzkzMjgwMTA4MDI3MjEwMzQ2OTE2#n#MTY1MjM2OTg1NDQ1MjI1MDAwNzcxMjU2MjQwMjY0NTkyMDQ1NzUyMDgxMjc5NzY1#n#OTk1NDMzMTQ1MDEzNzk3OTk1OTE3NjUyOTI5MjI4MTA=#,17312396432581981580082190897223306685078388934445663610081959661991268556046218704752902703549137760202385764805905093548375235316344862202335059600756979252209414418395820250774917064512150050319288755330141549697515847291918002586715614321304133894649250312630630452561195478934902991197220606699872183448985224400971078192308280610575220770572965229787346702188577070094281720879989868794352019870542081675785471867934328383519378690265632557641223653098223911604825521131690175552258941946860339362396293347346594639155605207874619696896736094913523391002359232901724372123502538254705894038777994765334841792220,NDE3NzgxMDQ5NTI4MjY5NTA0NjAxMTI0NDkyODQwODY2MTk2MDA3NTQzNTEwNTczNDI2MzQyMTQ0NDE0Mjc3NDYyNjk0OTk2MjU2OTR8LTE1NDQ0NDE4MzYwMTI1NzM4ODk4NDkyODI4NTc0NDM3OTU2NTMyMTY4MDA1MDY5NDQ2NzI1NTc0MDI2MzM1MDg0NDQ2NzU1MDkwMDg5MDE4Nzg1NDkzMjczOTY5ODU0MzYzODM5Mzk1NjY1NzYzNTcxOTg4MTY0NDA4MDE1NjcyMTkzNjA2ODQ5NDY1MzI4NzUxODY0NTg5MjIyMTE4Njg4NTMyNzQ3NjYzODU4NDY2NTU5NDA2MTIyOTEzMzEwMTc5MTQ2ODI5ODgyNzI4MjUyNDU1MDQxOTk5MjExNDI2Mjk1MzIzNTk1OTQ4OTMwMjQ0NzU5NDg4NDM0MTI4ODAyOTQwNjg5OTc3ODg1NDY2NTQ1MTU0OTQwMDEyNTAxNjk3NzI4MTQ5OTkwNzEwMDM3NTM2ODA3MDE0NTcxNTM1MTI2MjU1NzU3MTE2MTgyMDMwMzQ0MzQzMzAwMzM5MjUxODQ3MDA5Mzc2NTkyOTE1MDg0ODk1MTc1MzkwOTU2OTM5ODc3MjIyNzg1MDQ0MTIxMTk4OTM2NjE2MTI5NzIyMjk5MTY2MTg5OTgwMzE3NjEyNzA1NjYyNjI4NTQzNjUwNDk5MzI3MTkzNTA0ODMxNjYwMzMzMzIwNjk4MDEwNzM0NTI4NzkzMDgyMzc5MTgwNjA0NTM5MDk0NzYyNzk1NTExNTQ5Nzc0NDg1OTEwNzgzNjg1OTQwODA4OTEzNzU1Nzg0OTczNTc3NDI4NzM0MjA4OTY3NzQzMDc2MzcwOTA3MzA0OTMxODkxOTcwODUzMzM0Mjk2NDI1NzQyOTE1NjgxMDk1ODY2NjU2NjU1NjgyMDY2NTI3MTI1MTE5NDY1ODc4Nzc2MzIzODA2ODUzNTEzNjk2OTg4NTczMzAwOTUzOTg2MDY5NzE0MjA3MTc4OTE5N3xudWxs";
    private DecryptionLine decryptionLine;

    /**
     * Creates a decryption line to run the tests against.
     */
    @BeforeMethod
    public void createDecryptionLine() {
        decryptionLine = new DecryptionLine(SAMPLE_LINE_WITH_CORRECT_PROOF);
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
        assertTrue(new DecryptionLine(SAMPLE_LINE2_WITH_CORRECT_PROOF).verifyProof(
                SAMPLE_P2, SAMPLE_G2, SAMPLE_H2));
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
