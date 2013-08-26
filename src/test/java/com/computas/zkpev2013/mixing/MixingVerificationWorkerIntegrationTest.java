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
package com.computas.zkpev2013.mixing;

import com.computas.zkpev2013.ElGamalPublicKeyList;
import com.computas.zkpev2013.ResultsArrayList;
import com.computas.zkpev2013.ResultsList;

import org.apache.commons.codec.binary.Base64;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigInteger;

import java.security.NoSuchAlgorithmException;


/**
 * Integration tests on the MixingVerificationWorker.
 *
 */
public class MixingVerificationWorkerIntegrationTest {
    private static final BigInteger SAMPLE_P = new BigInteger(
            "22519781860318881430187237378393910440433456793106883439191554045609533190204716026094503488051043531257695232100353994296431999733305913289830606623675094806877884255872439714678914992056169353692036021770097223778392105262307803104951171429150982767069700653909195647599098780046724703785991755259095912786508845222597772887203546632493935590809326329822837682361511439054458165467044490658668908755516611075852591340913731324282531411301527453756791057107929172839003743485012313000403534330922416540828874783338650662007436059441348150784982317988527563812882812455109992843656727186872083932493433216403334110087");
    private static final BigInteger SAMPLE_G = new BigInteger(
            "19140805144017540497707786625792286921405966026933054372546212478081606825277100641152753685549997641757412202354820556035283634167831414111680419792323513134593333242130321485704646466132023972092638166348661889738666905149514346301514811888481584122903522192426522565163431677342704016489172931295206771271956675417455593733493182654653217734161457736905965163738973158663182502896276372310802897020170186481607829975539047679142654262779602075975560918037248175928164089234991713304225443874682021320096580809741466636539107495416015820825091232630444699803381650489285110532680106190202164342326569938521995903077");
    private static final BigInteger SAMPLE_H = new BigInteger(
            "15767103161188431726810732547407071177925682470096161113662719329970151989521808605211708980148853646358184785265698288206467886519416824938097511897175394153183299970057910661112235919650701007926207445783398837008410584396746210082523938166164511910717655019379900382402878897548938693825625577255875241671695927055614575927068409397924078583190963658265084557648260898854231641007945637962701179737860118584226735942584888595742172827013353350499066429923853690118418094376964200302533698193066579535824852762698049107984716754445670202902488607524608492482523694573929391910902766007429761724512335207851342013142");
    private static final String SAMPLE_MIXING_UUID = "2ed7bd918b6f4a4dbbb75a1fc6f9d3e0";
    private static final byte[] SAMPLE_INPUT_VOTES = ("IWvRkimscLc7ZnzHRhq5HkKtL0XXlMwZEFu2w7iIdSBCWDAKuxgUNPkA66dqZ7OyuvqePFoXVHLgemivhxRHAtkKALAtdc3ihfDN99u3vrfiPinPLHcidemy056uxPO+9mb96Cs4RVhFDY3DnW6BuZQUuITmrBOEhaaxja7qbijKD7SNX2bwqRv0j4GeasLaGosqDfFqEYd9aLr7x7M6xkk4AhyoPHG7qAI/A1SiwDxF2ocycQgJk7kl4yeJDgBKPFw0F3xZ1f8yVJEr6d8Su8OwdXN/NiFlX9bjKifFK2bgMqJvlsAPXLjK5ms/2VeSIE4atuMnZquzJMpjEICHcQ==#GUM3T8tSx72bLMES4Zh4qKeJun1rkd5i9opb7tcA0bk736GIE/pgeYytK2Igo4fZRgfMou81guVfcL4J6/1Wq2s+NNlFil5HU+9OwnJLc0LV2Q8MXvOs/Vex4nBkbMz4BMORlt0w542Rj2LZX2FdWKkQOSIX1d1BKTcvPljLAzpl3QIMSoK86to7+4+hv16ANUZ6wIepS7giTfFfutWv4nvhLLDO4bCrthVg+YTr+F0urssfMNZcM7CyEd8/EHMYhIhfoOGT8wDZN9bPMp0Z1cLbrOw9nm6luEPD0VEjgjHk+enGXsqQuiQ6rnWSGFn+MV9rkYxTwKXOAgBOqVSnXg==#,000018,01,730071,\n" +
        "WhV3wkXBpR7hSC7aQ9chNur0/fD8fLi91SLp5XrAnsBSHwQbJKRKiO3sIGPitVcnTTZXR3d9M8wLYLCaJ3GGIaLcLSHeiqDf4PLPZRM/WTUZErbhw1rcqIEgGqyqfVhXFSVAUvAYNmhXnkhYrXw82nEs8v2oBlLs2Eg/zsDn1j1LcA6jyrPhgVmKc3Qixxhn+wCJ/IxDxD6lV/o/0T3HrDjyo2ufeUEJdZVjiYi6EXVF6IWQx4dK5R1CTtvPptV+fegbis421NrVug6yt2Nk55fUJW1kowHyp0EscQGbJosTb/FEHNVI1K8mDcYklSl53+K/9QZLJIEl2JsdBp+upQ==#KhVJEp/GpYr8B6bBR9/P2nl6xdhJ3vejWn5FRUKz0TLSS/Y8h2nUaF6DzPg2NyI3qYl/4b/ykM80DWZYjFky2oDqztLinnHSrLrefBMY3dV+UJYDc1vHs6XnM+4VsAQ6jguquW8km3XtKu3bItafsqnzGvfImbG2TPnswnmJO00rq03qKgOBWv5JZWc3ttN1+/19gxwgpo2WJv8Fevqx5CgYk4nzpPDYqnSEVc1anxKo6j7odBXfPOpgGGqwHek7C82gDqHitZ9pw1Ye8VXQ/qH52gpSrTBamZztyX36dlDSLiwDzRJ83N+sdHEcqy4G/ACAzvv/99L5qaBxMPT8hA==#,000018,01,730071,\n" +
        "IWvRkimscLc7ZnzHRhq5HkKtL0XXlMwZEFu2w7iIdSBCWDAKuxgUNPkA66dqZ7OyuvqePFoXVHLgemivhxRHAtkKALAtdc3ihfDN99u3vrfiPinPLHcidemy056uxPO+9mb96Cs4RVhFDY3DnW6BuZQUuITmrBOEhaaxja7qbijKD7SNX2bwqRv0j4GeasLaGosqDfFqEYd9aLr7x7M6xkk4AhyoPHG7qAI/A1SiwDxF2ocycQgJk7kl4yeJDgBKPFw0F3xZ1f8yVJEr6d8Su8OwdXN/NiFlX9bjKifFK2bgMqJvlsAPXLjK5ms/2VeSIE4atuMnZquzJMpjEICHcQ==#TTYm5BNmyn4KwHLv6tiUPWajPOgMLEfI2iC+um2wnYxTL3go1N4cd6e4Ft70gmYovuIa9HAvhTcMdvlAqNSh5oWylb7WoRaI7p3yoQ6s+kwWWTlv3J0UBrpUu4Ez8uOsdn3kD6baDWvqs1Sl20Ty5842T4kPJvBbI6pJEOQxe17ldFpk49bweWYPTXkuKC5Wx38MQ/bT6nocd8uvo8WrxJvRkZl8y/7wa/KqtAZI1WUY6Ry8I2QgZ/8atNBPGlBMTMA63ZwxYl0bLDB1dfdxxYFFFWpRsfHeGvZ3EKDspRas97uCpieU321BB3xthXlICcb6Pz4heHnJX7jGaq24ow==#,000018,01,730071,\n" +
        "p1").getBytes();
    private static final byte[] SAMPLE_OUTPUT_VOTES = ("SZmUOVwGOF620ra6KljO3dpvCEPpOP3CZCJAfqDv4MwMdG9H5V/jsOuC6sJ3sbJp#n#SPIzGGH3jq90NAuTl9M2RZLKqp5MDxP1/RQdI236HWZE2Ilz8VeLEfLvm35mE+Rw#n#3ydXcZhnUCpOWcqcrGbc6D3vekSlDPYy7k6Kd8OkcuDfHDsX9m0BccmurnbdCXYu#n#D6MEjZ6G634X1UFxr41QguPFHv/aerT2Eu6nrD8kaaTSf7WQfAjjJOpuoIGCJset#n#lrZ2FbDn66DswnVemZt0wD2ml4Ps5dU24mzdkGzmtQN0YcUWmmQHFVJ2S/AXfzcH#n#6q3g7RIiMjApkR8h+euCtQ==#YzbQsRSEw5XycaXv3Wnpcq2K8ShRTEk0C9lA69u67ZkCa+hOuGaKu3XDaYjzfD4M#n#MwVvDiVw7j3mu50Up5T1fHB7dSvxmNiXuhPtsGNPjlC7SZu2Kwx5xXg2mZqp8sRQ#n#i8pK9qiMaQu7owGBL9UYS0zETYGMl64AU/BvHZWIuLw4PR+WUAXzr1kBWKsYKGUN#n#8tkY4Z9A3K4zLrSiEPxj3YMg9jaeP/3lbKNod573jcY+fMybt8Z6HexbQdq3BEL1#n#hfNvX/jH8yLj5IFivn6DmlRLre6ELSsViv+crGa1oltrlIgi4q2Yj1a7A63G9Zoj#n#OBSyCjOEmDYYNwI1Ms3ltA==#,000018,01,730071,p1\n" +
        "eDQ3xjcL4bHvtgsHuJ25yJvmsM84NQjNS4uokOKPqvFK9EZNUEjmAA1lThP3iPY+#n#jYJMzDihLpGkBVDjko5qjutAtFU0vms1+jECU/9fTPJOmiBgao9r+eJuXwWizZDn#n#5wI7ULkKh9RMaAEPCSJv9w7YW1AlTI1u0jqCzP90KKFwFmgjt7gp3G/s7aFPPXFX#n#ysciZ0ezYoHmenLMvJCWOcc/zfLxhEWQKzq9oW45NpCqEbkMByZ2Sfbw0WSxnETt#n#lm7p5wDs3o2IEQ9Zg+53GV8DcXSOnev2P6erUfxRvgKjLqluYLaR6qABJmUro374#n#N0KwBVBoKBQLYOoYO8lcTA==#XvE9HvyqDEQk+ODPpPaNrOZy+BKsrGYQWsYBiv1gwPyj4BGjyNAHzxaip24bMYcR#n#2fT10FHQ7OgaNjwifRQufwKIi2X/Vs11zTC+iyygDjRfv+suXhZUFZQHU8wRHnEm#n#KSrAyrK/tiTX7kB1o18oEeCo2hLbqnLRgRMOeGeJBGemDrXuINxgv7mfQEdz3YOg#n#LWfxecKJegRf/qhLjsYVPcqwAjIWNAlZZ3otCyBpL8kIVTMwghoTFltUxZeE/5kL#n#w/WdvYRQjajqwueWi8YB6jRdbpR2OMEde8pCTgMHtvDHkc4Z+lvvJrCFnSTSs0u3#n#amaFDEagw8hLyIRQ2Bir2g==#,000018,01,730071,p1\n" +
        "AJhisjVoXiq1AtxH5eju85Pg4s1QpNK03VpgNHi6wHvKqLvxKiKdx60eAyl4qpz4#n#X8yULppZ/1aaWSjPZKJEa95jiy1OCiSz60PIJga39l+u69BW9PVzDvwgTq9PMNlw#n#b4lqWxRFXjCxkDK8g73atHzUSureldEuDD3evauuePEaK26DJFyidOSLheDly5QO#n#U4OxeFgsZYQUP+Rz7+WkPUHIfDJtCJShAiJikoOMaLOkAhCAreGCS5L7UcPilFyf#n#guUyCs7uAb3Kf3WSIH/vaQs1HShJ5Qaa46eyibGQ3a6vbbDkZ0wz+15n69QvRbUs#n#hHCwmQsynHbBrE7oWVicgAI=#AJd+vhPVzLwGQHvK6EwzB+FJL5j5eJYeMvLU/2/Fz6nvmxQee62BPqzAZBRN3A+K#n#0GTNDli/wyD5lvjkKzDFQaNkYINWNHcpaJoYVRZWNgCDSYEWBAC4n3CDh6FvCoXC#n#ZFUpe+QCDzCEHXE1+/f25+aXT5V/vZK2tljc7TBKH+67zN2L/ZrXAJ1fohF+7Q1Z#n#/h7ZWgCUNYUeuAYibueargohGcKo1x76XChONS7ZdlyNL5jBbm8ADSgJjDOaZi0N#n#MuWZoK2kXL4ha+4XXfCL7mD/wpYyWLWi5Gackfx2AFGuu5zW3rYgBoUeoFNkJj9M#n#/Y55lF5OdZJUYU9J57ljwGU=#,000018,01,730071,p1\n" +
        "01").getBytes();
    private static final byte[] SAMPLE_INCORRECT_OUTPUT_VOTES = ("sZmUOVwGOF620ra6KljO3dpvCEPpOP3CZCJAfqDv4MwMdG9H5V/jsOuC6sJ3sbJp#n#SPIzGGH3jq90NAuTl9M2RZLKqp5MDxP1/RQdI236HWZE2Ilz8VeLEfLvm35mE+Rw#n#3ydXcZhnUCpOWcqcrGbc6D3vekSlDPYy7k6Kd8OkcuDfHDsX9m0BccmurnbdCXYu#n#D6MEjZ6G634X1UFxr41QguPFHv/aerT2Eu6nrD8kaaTSf7WQfAjjJOpuoIGCJset#n#lrZ2FbDn66DswnVemZt0wD2ml4Ps5dU24mzdkGzmtQN0YcUWmmQHFVJ2S/AXfzcH#n#6q3g7RIiMjApkR8h+euCtQ==#YzbQsRSEw5XycaXv3Wnpcq2K8ShRTEk0C9lA69u67ZkCa+hOuGaKu3XDaYjzfD4M#n#MwVvDiVw7j3mu50Up5T1fHB7dSvxmNiXuhPtsGNPjlC7SZu2Kwx5xXg2mZqp8sRQ#n#i8pK9qiMaQu7owGBL9UYS0zETYGMl64AU/BvHZWIuLw4PR+WUAXzr1kBWKsYKGUN#n#8tkY4Z9A3K4zLrSiEPxj3YMg9jaeP/3lbKNod573jcY+fMybt8Z6HexbQdq3BEL1#n#hfNvX/jH8yLj5IFivn6DmlRLre6ELSsViv+crGa1oltrlIgi4q2Yj1a7A63G9Zoj#n#OBSyCjOEmDYYNwI1Ms3ltA==#,000018,01,730071,p1\n" +
        "eDQ3xjcL4bHvtgsHuJ25yJvmsM84NQjNS4uokOKPqvFK9EZNUEjmAA1lThP3iPY+#n#jYJMzDihLpGkBVDjko5qjutAtFU0vms1+jECU/9fTPJOmiBgao9r+eJuXwWizZDn#n#5wI7ULkKh9RMaAEPCSJv9w7YW1AlTI1u0jqCzP90KKFwFmgjt7gp3G/s7aFPPXFX#n#ysciZ0ezYoHmenLMvJCWOcc/zfLxhEWQKzq9oW45NpCqEbkMByZ2Sfbw0WSxnETt#n#lm7p5wDs3o2IEQ9Zg+53GV8DcXSOnev2P6erUfxRvgKjLqluYLaR6qABJmUro374#n#N0KwBVBoKBQLYOoYO8lcTA==#XvE9HvyqDEQk+ODPpPaNrOZy+BKsrGYQWsYBiv1gwPyj4BGjyNAHzxaip24bMYcR#n#2fT10FHQ7OgaNjwifRQufwKIi2X/Vs11zTC+iyygDjRfv+suXhZUFZQHU8wRHnEm#n#KSrAyrK/tiTX7kB1o18oEeCo2hLbqnLRgRMOeGeJBGemDrXuINxgv7mfQEdz3YOg#n#LWfxecKJegRf/qhLjsYVPcqwAjIWNAlZZ3otCyBpL8kIVTMwghoTFltUxZeE/5kL#n#w/WdvYRQjajqwueWi8YB6jRdbpR2OMEde8pCTgMHtvDHkc4Z+lvvJrCFnSTSs0u3#n#amaFDEagw8hLyIRQ2Bir2g==#,000018,01,730071,p1\n" +
        "AJhisjVoXiq1AtxH5eju85Pg4s1QpNK03VpgNHi6wHvKqLvxKiKdx60eAyl4qpz4#n#X8yULppZ/1aaWSjPZKJEa95jiy1OCiSz60PIJga39l+u69BW9PVzDvwgTq9PMNlw#n#b4lqWxRFXjCxkDK8g73atHzUSureldEuDD3evauuePEaK26DJFyidOSLheDly5QO#n#U4OxeFgsZYQUP+Rz7+WkPUHIfDJtCJShAiJikoOMaLOkAhCAreGCS5L7UcPilFyf#n#guUyCs7uAb3Kf3WSIH/vaQs1HShJ5Qaa46eyibGQ3a6vbbDkZ0wz+15n69QvRbUs#n#hHCwmQsynHbBrE7oWVicgAI=#AJd+vhPVzLwGQHvK6EwzB+FJL5j5eJYeMvLU/2/Fz6nvmxQee62BPqzAZBRN3A+K#n#0GTNDli/wyD5lvjkKzDFQaNkYINWNHcpaJoYVRZWNgCDSYEWBAC4n3CDh6FvCoXC#n#ZFUpe+QCDzCEHXE1+/f25+aXT5V/vZK2tljc7TBKH+67zN2L/ZrXAJ1fohF+7Q1Z#n#/h7ZWgCUNYUeuAYibueargohGcKo1x76XChONS7ZdlyNL5jBbm8ADSgJjDOaZi0N#n#MuWZoK2kXL4ha+4XXfCL7mD/wpYyWLWi5Gackfx2AFGuu5zW3rYgBoUeoFNkJj9M#n#/Y55lF5OdZJUYU9J57ljwGU=#,000018,01,730071,p1\n" +
        "01").getBytes();
    private static final String SAMPLE_AUDIT_UUID = "97b089bb78034cb3a193e8b9d308ba13";
    private static final byte[] SAMPLE_INPUT_VOTE_GROUPS = Base64.decodeBase64(
            "rO0ABXNyAEZjb20uc2N5dGwuZXZvdGUucHJvdG9jb2wuaW50ZWdyYXRpb24ubWl4aW5nLmJhc2UuVm90ZUdyb3VwQWZmaWxpYXRpb25zaaNW0vQmEAwCAAFMAA5fdm90ZUdyb3VwQWZmc3QAEExqYXZhL3V0aWwvTGlzdDt4cHNyABBqYXZhLnV0aWwuVmVjdG9y2Zd9W4A7rwEDAANJABFjYXBhY2l0eUluY3JlbWVudEkADGVsZW1lbnRDb3VudFsAC2VsZW1lbnREYXRhdAATW0xqYXZhL2xhbmcvT2JqZWN0O3hwAAAAAAAAAAN1cgATW0xqYXZhLmxhbmcuT2JqZWN0O5DOWJ8QcylsAgAAeHAAAAAKc3IAEWphdmEubGFuZy5JbnRlZ2VyEuKgpPeBhzgCAAFJAAV2YWx1ZXhyABBqYXZhLmxhbmcuTnVtYmVyhqyVHQuU4IsCAAB4cAAAAABxAH4ACnEAfgAKcHBwcHBwcHg=");
    private static final byte[] SAMPLE_OUTPUT_VOTE_GROUPS = Base64.decodeBase64(
            "rO0ABXNyAEZjb20uc2N5dGwuZXZvdGUucHJvdG9jb2wuaW50ZWdyYXRpb24ubWl4aW5nLmJhc2UuVm90ZUdyb3VwQWZmaWxpYXRpb25zaaNW0vQmEAwCAAFMAA5fdm90ZUdyb3VwQWZmc3QAEExqYXZhL3V0aWwvTGlzdDt4cHNyABBqYXZhLnV0aWwuVmVjdG9y2Zd9W4A7rwEDAANJABFjYXBhY2l0eUluY3JlbWVudEkADGVsZW1lbnRDb3VudFsAC2VsZW1lbnREYXRhdAATW0xqYXZhL2xhbmcvT2JqZWN0O3hwAAAAAAAAAAN1cgATW0xqYXZhLmxhbmcuT2JqZWN0O5DOWJ8QcylsAgAAeHAAAAAKc3IAEWphdmEubGFuZy5JbnRlZ2VyEuKgpPeBhzgCAAFJAAV2YWx1ZXhyABBqYXZhLmxhbmcuTnVtYmVyhqyVHQuU4IsCAAB4cAAAAABxAH4ACnEAfgAKcHBwcHBwcHg=");
    private static final byte[] SAMPLE_REENCRYPTION_PROOFS = Base64.decodeBase64(
            "rO0ABXNyAENjb20uc2N5dGwuZXZvdGUucHJvdG9jb2wuaW50ZWdyYXRpb24ubWl4aW5nLmJhc2UuUmVFbmNyeXB0aW9uUHJvb2ZzTD/h4+Cu7VUCAAFMAAxfcmVFbmNQcm9vZnN0ABBMamF2YS91dGlsL0xpc3Q7eHBzcgAQamF2YS51dGlsLlZlY3RvctmXfVuAO68BAwADSQARY2FwYWNpdHlJbmNyZW1lbnRJAAxlbGVtZW50Q291bnRbAAtlbGVtZW50RGF0YXQAE1tMamF2YS9sYW5nL09iamVjdDt4cAAAAAAAAAABdXIAE1tMamF2YS5sYW5nLk9iamVjdDuQzlifEHMpbAIAAHhwAAAAAXNyADFjb20uc2N5dGwuZXZvdGUucHJvdG9jb2wuc2lnbmVycy5TY2hub3JyU2lnbmF0dXJlcoV1T8sRBe8CAANMAAJfZXQAFkxqYXZhL21hdGgvQmlnSW50ZWdlcjtMAAJfc3EAfgAJTAAGX3NDb2xscQB+AAF4cHNyABRqYXZhLm1hdGguQmlnSW50ZWdlcoz8nx+pO/sdAwAGSQAIYml0Q291bnRJAAliaXRMZW5ndGhJABNmaXJzdE5vbnplcm9CeXRlTnVtSQAMbG93ZXN0U2V0Qml0SQAGc2lnbnVtWwAJbWFnbml0dWRldAACW0J4cgAQamF2YS5sYW5nLk51bWJlcoaslR0LlOCLAgAAeHD///////////////7////+AAAAAXVyAAJbQqzzF/gGCFTgAgAAeHAAAAEAsmQa3BqbzngsHvK7BDeVPI7NsI567i2nAyZx3cOirClxXsN98KtZlZ/2QBJ25ezN9k+WuPS+zmHYnEVxhpYlPlcistyndkhFC7ZOcV7kbwmWTl7OeA/A79+/SIGhNolJGXBsmMP7yNYZvuunmCjZQJkqa+Ss/kez3HgDXC2MGESnnqlSP5/oLqcONa1SQWrq7hlDjKxwH6YywoBSyW5aMf9xFH7rCbdg/XaiK0eBQaO2iThx221lIGwKUwBtnnH+9l6+sSeQvLesXJt+4QAix9XQy0lz5cN2KDFJebISpBYghu9mm8DKW/jQZ0GbudnX7fmQkVKkN2gmgeGa5dYvNHhzcQB+AAv///////////////7////+/////3VxAH4ADwAAAgBIfwJdyQdjimVmPsXEV1Vidi+1rI9JVR1nUfgGYhOz4UmCLQmmAy4xWFQKvaKqPFWxImxVQh59fQdXSaL84RZoaplCNIn2sQAbdsMLLGUUFmqpHyK+V3DrPcxSY0TTuo/V7PR40tbys8QbgVLG0HA08A73gCbApA1bwB3amaKK0rXwyfzqhZQHLx7qRQgh+Qm/TrlXAsxk+EaCa05C9m/aST8nHr72ppRcJ/Tt2FvZf6UkG7AS8Qik0t38qWrAUjaJJa2Q7uiL+XFcnGkovQY5loHcQyzoaeZdKW1atv41S0VoeJQCDqh9OZxsvv11RchInBXGBj7fd1G7bwqpH7bRBgMCrR058BQ7+gGok91hZtFlHT82Wz7gcxxArH7wkuho+Ij4OE40sOC2CiRFxJNp3n3VS7xQgTf8iRMKP1PuHBcVt8kULo+FRsCP0sC50VBVM5HRkqy560qNjUCo//7NP6yNx/JCRO84Nb8wxIdt7tPzAz5ykaEeBYnOIHl+FdetVIoMWjKI/vk3S3otLQrxDrVJi5ske8qVR2D4wEsexXaIEcoZqsAqa7kUXUzkZC0ac2WruAEeywfHk5qZ4hh2tVHOL7Hf6OFoU3h4KrEMMFBx8jg2Z2ptTV1gLc/lIoFAJgtNisto9KA6FN0qGbpHixkIT/nDU78kA0tynEl0ZHhweA==");
    private static final byte[] SAMPLE_INVALID_FIELD = "Foo".getBytes();
    private com.computas.zkpev2013.mixing.MixingVerificationWorker worker;
    private ResultsList results;

    /**
     * Creates a results list and a worker to run the tests against.
     * @param results
     */
    @BeforeMethod
    public void createResultsAndWorker() {
        results = new ResultsArrayList();
        worker = new com.computas.zkpev2013.mixing.MixingVerificationWorker(results,
                SAMPLE_P, SAMPLE_G, new ElGamalPublicKeyList(SAMPLE_H));
    }

    /**
     * Verifies that an incident is created if the input votes are
     * corrupt.
     * @throws java.security.NoSuchAlgorithmException Should not be thrown.
     */
    @Test
    public void mustCreateAnInvalidMixingDataRecordIncidentIfInputVotesInvalid()
        throws NoSuchAlgorithmException {
        worker.setMixingData(SAMPLE_MIXING_UUID, SAMPLE_INVALID_FIELD,
            SAMPLE_OUTPUT_VOTES);
        worker.setAuditData(SAMPLE_AUDIT_UUID, SAMPLE_INPUT_VOTE_GROUPS,
            SAMPLE_OUTPUT_VOTE_GROUPS, SAMPLE_REENCRYPTION_PROOFS);
        worker.tryToRun();
        assertTrue(results.contains(
                new InvalidMixingDataRecordIncident(SAMPLE_MIXING_UUID,
                    InvalidMixingDataRecordIncident.INPUT_VOTES_INVALID)));
    }

    /**
     * Verifies that an incident is created if the output votes are
     * corrupt.
     * @throws java.security.NoSuchAlgorithmException Should not be thrown.
     */
    @Test
    public void mustCreateAnInvalidMixingDataRecordIncidentIfOutputVotesInvalid()
        throws NoSuchAlgorithmException {
        worker.setMixingData(SAMPLE_MIXING_UUID, SAMPLE_INPUT_VOTES,
            SAMPLE_INVALID_FIELD);
        worker.setAuditData(SAMPLE_AUDIT_UUID, SAMPLE_INPUT_VOTE_GROUPS,
            SAMPLE_OUTPUT_VOTE_GROUPS, SAMPLE_REENCRYPTION_PROOFS);
        worker.tryToRun();
        assertTrue(results.contains(
                new InvalidMixingDataRecordIncident(SAMPLE_MIXING_UUID,
                    InvalidMixingDataRecordIncident.OUTPUT_VOTES_INVALID)));
    }

    /**
     * Verifies that an incident is created if the input and output votes are
     * corrupt.
     * @throws java.security.NoSuchAlgorithmException Should not be thrown.
     */
    @Test
    public void mustCreateTwoInvalidMixingDataRecordIncidentsIfInputAndOutputVotesInvalid()
        throws NoSuchAlgorithmException {
        worker.setMixingData(SAMPLE_MIXING_UUID, SAMPLE_INVALID_FIELD,
            SAMPLE_INVALID_FIELD);
        worker.setAuditData(SAMPLE_AUDIT_UUID, SAMPLE_INPUT_VOTE_GROUPS,
            SAMPLE_OUTPUT_VOTE_GROUPS, SAMPLE_REENCRYPTION_PROOFS);
        worker.tryToRun();

        boolean hasInvalidInputVotesIncident = results.contains(new InvalidMixingDataRecordIncident(
                    SAMPLE_MIXING_UUID,
                    InvalidMixingDataRecordIncident.INPUT_VOTES_INVALID));
        boolean hasInvalidOutputVotesIncident = results.contains(new InvalidMixingDataRecordIncident(
                    SAMPLE_MIXING_UUID,
                    InvalidMixingDataRecordIncident.OUTPUT_VOTES_INVALID));
        assertTrue(hasInvalidInputVotesIncident &&
            hasInvalidOutputVotesIncident);
    }

    /**
     * Verifies that an incident is created if the input vote groups is
     * corrupt.
     */
    @Test
    public void mustCreateAnInvalidAuditDataRecordIncidentIfInputVoteGroupsInvalid() {
        worker.setAuditData(SAMPLE_AUDIT_UUID, SAMPLE_INVALID_FIELD,
            SAMPLE_OUTPUT_VOTE_GROUPS, SAMPLE_REENCRYPTION_PROOFS);
        assertTrue(results.contains(
                new InvalidAuditDataRecordIncident(SAMPLE_AUDIT_UUID,
                    InvalidAuditDataRecordIncident.INPUT_VOTE_GROUPS_INVALID)));
    }

    /**
     * Verifies that an incident is created if the output vote groups is
     * corrupt.
     */
    @Test
    public void mustCreateAnInvalidAuditDataRecordIncidentIfOutputVoteGroupsInvalid() {
        worker.setAuditData(SAMPLE_AUDIT_UUID, SAMPLE_INPUT_VOTE_GROUPS,
            SAMPLE_INVALID_FIELD, SAMPLE_REENCRYPTION_PROOFS);
        assertTrue(results.contains(
                new InvalidAuditDataRecordIncident(SAMPLE_AUDIT_UUID,
                    InvalidAuditDataRecordIncident.OUTPUT_VOTE_GROUPS_INVALID)));
    }

    /**
     * Verifies that an incident is created if the reencryption proofs are
     * corrupt.
     */
    @Test
    public void mustCreateAnInvalidAuditDataRecordIncidentIfReencryptionProofsInvalid() {
        worker.setAuditData(SAMPLE_AUDIT_UUID, SAMPLE_INPUT_VOTE_GROUPS,
            SAMPLE_OUTPUT_VOTE_GROUPS, SAMPLE_INVALID_FIELD);
        assertTrue(results.contains(
                new InvalidAuditDataRecordIncident(SAMPLE_AUDIT_UUID,
                    InvalidAuditDataRecordIncident.REENCRYPTION_PROOFS_INVALID)));
    }

    /**
     * Verifies that an incident is created if a proof is incorrect.
     * @throws java.security.NoSuchAlgorithmException Should not be thrown.
     */
    @Test(enabled = false)
    public void mustCreateAnIncorrectMixingProofIncidentIfAProofIsIncorrect()
        throws NoSuchAlgorithmException {
        worker.setMixingData(SAMPLE_MIXING_UUID, SAMPLE_INPUT_VOTES,
            SAMPLE_INCORRECT_OUTPUT_VOTES);
        worker.setAuditData(SAMPLE_AUDIT_UUID, SAMPLE_INPUT_VOTE_GROUPS,
            SAMPLE_OUTPUT_VOTE_GROUPS, SAMPLE_REENCRYPTION_PROOFS);
        worker.tryToRun();
        assertTrue(results.contains(
                new IncorrectMixingProofIncident(SAMPLE_MIXING_UUID,
                    SAMPLE_AUDIT_UUID, 0)));
    }

    /**
     * Verifies that no incident is created if all proofs are correct.
     * @throws java.security.NoSuchAlgorithmException Should not be thrown.
     */
    @Test
    public void mustNotCreateAnIncidentIfAllProofsAreCorrect()
        throws NoSuchAlgorithmException {
        worker.setMixingData(SAMPLE_MIXING_UUID, SAMPLE_INPUT_VOTES,
            SAMPLE_OUTPUT_VOTES);
        worker.setAuditData(SAMPLE_AUDIT_UUID, SAMPLE_INPUT_VOTE_GROUPS,
            SAMPLE_OUTPUT_VOTE_GROUPS, SAMPLE_REENCRYPTION_PROOFS);
        worker.tryToRun();
        assertTrue(results.size() == 0);
    }
}
