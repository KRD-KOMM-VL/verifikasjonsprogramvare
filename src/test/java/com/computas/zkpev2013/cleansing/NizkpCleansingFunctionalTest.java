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

import com.computas.zkpev2013.EncryptedVote;
import com.computas.zkpev2013.Result;

import static com.computas.zkpev2013.ZkpFunctionalTestAssertions.assertZkpContainsIncident;
import static com.computas.zkpev2013.ZkpFunctionalTestAssertions.assertZkpDoesNotContainIncident;
import static org.testng.Assert.assertEquals;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

import java.net.URISyntaxException;


/**
 * Functional tests on NizkpCleansing.
 *
 */
public class NizkpCleansingFunctionalTest {
    private static final String ELGAMAL_PROPERTIES_FILE_NAME = "NizkpCleansing2013FunctionalTestElGamalProperties.properties";
    private static final String AREAS_FILE_NAME = "NizkpCleansing2013FunctionalTestAreas.csv";
    private static final String CLEANSED_FILES_DIR_NAME = "NizkpCleansing2013FunctionalTestCleansedFilesDirectory";
    private static final String ENCRYPTED_VOTES_FILE_NAME = "NizkpCleansing2013FunctionalTestEncryptedVotes.csv";
    private static final String ENCRYPTED_VOTE_LINE = "8a84806e40521c6d014052260fec01d9,rO0ABXNyADtjb20uc2N5dGwuZXZvdGUucHJvdG9jb2wuaW50ZWdyYXRpb24udm90aW5nLm1vZGVsLkF1dGhUb2tlbvz+Wz5pky7LAgAWSQAhX2FkdmFuY2VPZkV4cGlyYXRpb25UaW1lSW5NaW51dGVzSQAaX2FmdGVyQ2xvc2luZ1RpbWVJblNlY29uZHNKAAVfYXNJZEkAGF9leHBpcmF0aW9uVGltZUluTWludXRlc0oAA190c1sAD19hdXRoU2VydmljZVNpZ3QAAltCTAAJX2NvbnRlc3RzdAAQTGphdmEvdXRpbC9MaXN0O0wAGl9jb250cm9sbGVkUmV0dXJuY29kZXNUeXBldAASTGphdmEvbGFuZy9TdHJpbmc7TAAQX2VsZWN0aW9uRXZlbnRJZHEAfgADTAALX2VsZWN0aW9uSWRxAH4AA0wAA19pZHEAfgADTAAJX2ludFRva2VudABBTGNvbS9zY3l0bC9ldm90ZS9wcm90b2NvbC9pbnRlZ3JhdGlvbi92b3RpbmcvbW9kZWwvSW50ZXJuYWxUb2tlbjtMABdfaW50VG9rZW5FbnRpdHlQcm92aWRlcnEAfgADTAADX2lwcQB+AANMAA1fcG9sbGluZ1BsYWNldABATGNvbS9zY3l0bC9ldm90ZS9wcm90b2NvbC9pbnRlZ3JhdGlvbi92b3RpbmcvbW9kZWwvUG9sbGluZ1BsYWNlO0wAHF91bmNvbnRyb2xsZWRSZXR1cm5jb2Rlc1R5cGVxAH4AA0wACl92b3RlckFyZWFxAH4AA0wADF92b3RlckNvbW1JZHEAfgADTAAPX3ZvdGVyRmlyc3ROYW1lcQB+AANMABBfdm90ZXJJZGVudGlmaWVydABDTGNvbS9zY3l0bC9ldm90ZS9wcm90b2NvbC9pbnRlZ3JhdGlvbi92b3RpbmcvbW9kZWwvVm90ZXJJZGVudGlmaWVyO0wADl92b3Rlckxhc3ROYW1lcQB+AANMAAxfdm90aW5nTW9kZXNxAH4AAnhwAAAABQAAAlgAAAAAAAAIAAAAAB4AAAFAUiW/FnVyAAJbQqzzF/gGCFTgAgAAeHAAAAEACJI8iw2fYLwLuQHRvZldYd4tYRcyYaQyx36EaPflp0cQ9cwMoKXlyGs3rLiejcdT5nBfaozUU1GZGrlpfL8ylOGuM+22SWIwNdF9oGKJ5d+DgfBH5xzOyDG5jv5BCehtylz40La6fAIoz7hnGAfqXGWx6Zci5Kuax38Max8t9CYdOCqp0cMLk6OL9Fpdvr7+M4u8f3N8REz5yKwpQrrgLnlKFTR8LiMx2IW5PV433JjVcjTttBuMtO0BKOET5ak7Y5haIQH6HofRsCnohYMq7QnclfLeyOHz+/UHvfFXW9Zk0kRZFFMmw8zKDl9m3a+LGQb3Yo/T8EtPR+X6jzT7RHNyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAABdwQAAAABc3IAQGNvbS5zY3l0bC5ldm90ZS5wcm90b2NvbC5pbnRlZ3JhdGlvbi52b3RpbmcubW9kZWwuQWxsb3dlZENvbnRlc3QiWqLrfd15KQIAA0wACl9jb250ZXN0SWRxAH4AA0wAC19lbGVjdGlvbklkcQB+AANMAAZfdm90ZWRxAH4AA3hwdAAGMDAwMDA3dAACMDF0AAEweHQABlNDUkVFTnQABjczMDA3MXQAA2lkNXQAJDhjZDE4MmMyLWFiNTItNGY5Yy04NzAxLWVjMmVjMjM0YzY2N3NyADxjb20uc2N5dGwuZXZvdGUucHJvdG9jb2wuaW50ZWdyYXRpb24udm90aW5nLm1vZGVsLk1pbklEVG9rZW7g1pITYjnPpQIABEoAA190c0wAA19pZHEAfgADTAAFX2luZm9xAH4AA0wACF92b3RlcklkcQB+AAN4cAAAAUBSJb7udAAkMTI4YTNhMDAtNmU1Ni00ZTA3LTg0ZWQtZWI5ZWI3MDA2NDMxdABrI1R1ZSBBdWcgMDYgMDc6NDI6MjAgQ0VTVCAyMDEzCnVzZXJJZD0wMTAxNTIyNDcyNgp0cz0xMzc1NzY3NzQwMTQyCmlkPTEyOGEzYTAwLTZlNTYtNGUwNy04NGVkLWViOWViNzAwNjQzMQp0AAswMTAxNTIyNDcyNnQADFVTRVJQQVNTV09SRHQACzEwLjAuMzIuMjQzc3IAPmNvbS5zY3l0bC5ldm90ZS5wcm90b2NvbC5pbnRlZ3JhdGlvbi52b3RpbmcubW9kZWwuUG9sbGluZ1BsYWNl1/fPlAehz24CAAdMAA1fYWRkcmVzc0xpbmUxcQB+AANMAA1fYWRkcmVzc0xpbmUycQB+AANMAA1fYWRkcmVzc0xpbmUzcQB+AANMAAVfbmFtZXEAfgADTAANX29wZW5pbmdIb3Vyc3EAfgACTAAJX3Bvc3RUb3ducQB+AANMAAtfcG9zdGFsQ29kZXEAfgADeHBwcHBwc3EAfgAKAAAAAHcEAAAAAHhwcHEAfgARdAAdNzMwMDcxLjQ3LjA3LjA3MTYuMDcxNjAwLjAwMDF0AAVmYWxzZXQAB1JhZ25hcnJzcgBBY29tLnNjeXRsLmV2b3RlLnByb3RvY29sLmludGVncmF0aW9uLnZvdGluZy5tb2RlbC5Wb3RlcklkZW50aWZpZXLKvqZX4QyCdgIAAkwACF9zcGFyZUlkcQB+AANMAAhfdm90ZXJJZHEAfgADeHB0AAswMTAxNTIyNDcyNnQACzAxMDE1MjI0NzI2dAARTG9cdTAwRjBiclx1MDBGM2tzcQB+AAoAAAABdwQAAAABc3IARGNvbS5zY3l0bC5ldm90ZS5wcm90b2NvbC5pbnRlZ3JhdGlvbi52b3RpbmcubW9kZWwuRWxlY3Rpb25Wb3RpbmdNb2RllMcp1WIDaxECAAJMAAtfZWxlY3Rpb25JZHEAfgADTAALX3ZvdGluZ01vZGVxAH4AA3hwdAACMDF0AAEyeA==,8cd182c2-ab52-4f9c-8701-ec2ec234c667,W1zg9AiTmHKE/cNkyDBpO2iB4R9z89nh4cBkJWiFySjj2AglLbvwyyY4VdGoXudq#n#VZicfV2MosnZMMZQIBCUCzpAF+a93LoB2Z1n5c2RmmjdLgOd42MGfC+0xc/BQrZ7#n#0DR+Sa9iRUoZVRLDwESxPBVIInPYnFVYDKKfhJRqqafwsp6xdBSwm0TMOZrSM4Ma#n#0/yVL6EhdxRyiXlCpbk8bX38h/R95szQ8okXcvbY6gUNZ2AClzlRgSi3OxqDWROQ#n#8cU5CIxkAdYwFXDm3Qqk5uNnW78H+tCWiRouek7M932kzfzBC++EUPsHfUKub3ZI#n#ph55pkjKDk+3fIePLfjnQA==#AKnHjN6l0Gq/j5EPbJRyHApWdy/QogmzVdlJhxZY8Wck/3v3HQWCAh8XNNMw1J8r#n#jHS0T9NTCDAghZfVHOPYiV5FL/Kr+LDQpHgewykol31rJNKJEWuIlNOGMO0hp8yt#n#kJH+qdcsqNX+JaBVYtC424fNvJPqgZGpaHdvZdjD26nK/NPnZh8YaTA2mimY7Z5c#n#m15/p9e4GZe4ZK0woSI4JxDxDLyJmfMinSNnjeAYtxq977dCWMZ0jJjKgl+KunBu#n#mGhuZqqmR+JTjs3lOU68EPTUQL6wNUO2kZdBAw0i/D0pakKT7QDlnEXQsAJh1q3w#n#s1PVInqgFFja3Y3tTSX/djE=#AIoirjXs9GOMKjt5H9gfS7xIo7ewnyKZvtcg9xrEhtL65BqSj8kcQ5Ey1vd4H7PQ#n#rJr6avAyiJIZzjdRT1a1zU/gh445MElG8m+IEIXv0NNvu9TCYV4VSfndTKfPvDds#n#36C1AdC6zQySGll43Y/mbJAoM+gFByp//tHlRp2sPeHahRmqQWNBUhct+hPV9pgX#n#GuTVlgROkonVyGJzZArFtte2dGXU5ncdi/PBvwAmo2HKIgOxzpFD9uuH1kisuTQ7#n#7vlLvJ3loQe9AvH4O7dnNPONDXLEd1nq6qsfproEQiyDmozJAXZ5l41brYonDxbk#n#uKYqh1asS/VU/iuvUcjIhPk=#eX17bJJTScjGsOChHCHtiB51QQJtETT5mtW3m5pa6WBGVuVE4rDt5lNNX9ElIA18#n#oC0MtS9Pml09mexpajQ/KsvLkY9+jhhRpa/kPR3lVBHfc26VZ+zRxwmVr2w5UQ1R#n#N3hpU4ry/jk8bY/JQOKrB/xHbd2x3jFG471pAWyGPv5+gzi9wpFjjKFXsTtscNIh#n#cx3rhLxYoUL7uUb/Hon853G/h7ljpDojm6TJbanJkyPbvdhJ6HTFv7n6ys98/Nc4#n#qD9evfgNRkqnjj25EINrpalk5FYAzRUPZDpMsb8CT75kPAqyxjGHjUeEKuoPgnK0#n#chVBqiyejT5qcN9rv3ocYQ==#D+LCkCK5TMRobqQO/D62e4xUJ3OFgOr98MjfyHjG0sh0qHg1CeFOcxURtZj7vKVa#n#XkktR9uKgViYES68/QDwxdlqO6rXVMwS3KC9A64kB42u9fxgwHwYw2fNwK6vsBTr#n#VhV8A2dqJwWTfbuyP9Py0KlnqARdhAxlWTCcLyUeF2fw0wo8giZE8IHgdCZQJrAQ#n#uf7Pu4ITyp4DDmu00AeNjE/r8jNuu099X4PwCqddM2v+05OcnBzDUhX9E6tpJDus#n#4ScJMZHLilRdVuOLxMibCR7sJioHR3ovZrzSERDJF1PJJVH5lvlrV6frig8XUCGn#n#+EI37AcG+4B8b2pC9avFrA==#KEPOKjeQilqjox3IHC/fxpUMXyPtwEg5pdbeoLa3nTg9r6xgJZKGh6pv3Q+Iupn+#n#WFAk6yXoUhsnSsV7jM5xmcEVEoBI0buxzAhJiev8AUOqx0ald8c1qc8EyKowm0UN#n#v/qx02fKLE+/TWg0FJzmyCmgI522iFcfKWLxt2KqPWj7a3McWYHYMWSiE8fWPIqP#n#5kLgxkxvE8iK0Gxn7JphHwHgcCyNZiG3oW2hRlzhUF3gefw0fF6o80RrjkSz8jD2#n#3gLpJTstKRuQc4ggJ6cYBGsoHlI9FBP20xJp4bcXjyBVqU4EB3xQ51vN5PtjvLiE#n#x8jY/vGX7Lg7modYMf4lXQ==#AJKFKJxb2IqFh/zecaF4fLSbiLY3kM1syQG1+rquvnlX8B6B1FhQm6B5u2KMWzJi#n#TSmqVqzWxIng16qzoPWX5gx9R7Kdf8CmYY/BWTKu63VpxNKTIsmhEiNFtmL8kndH#n#AdiziptvKdZckUJw6t8IuEi13w2kDFlDRZkamkkU9uRYot4L2ysrurWn1TBU5uLS#n#amZolZ87Oy6r3rKkIbyZsgU8+SnJbmDsXxCSWbaJ0kEDqlPvb8c58nvhIxSaYkpN#n#wcn7qO0TIGae019OnWmppvySiAc2qNDBOGgycEyvgBdp8uwv3rRbjyNltmnF9JS8#n#VgNThA2rvlGg14IqnftDZRQ=#AKzHmxdJJ0fVSS9E46BO5uDRThwtmYZ1PsB/LVkvR9gs5N4BeowNz2k0wrMs+lIK#n#S9upXQ6h4pSTYpL0Oy50OmOTD56Qfz/aVyE4Mv3Vv+a2WXLXL63jOKxOuIeKmuIf#n#D15mR3zA6AtlcWr6tWcYAcR804yrr9Ipdnv2jbHFujrxCk2gGhfSBnt4+l+WgXWj#n#q/yhO8xW6yx11VsV2dEUIeo2HWoHZh05k617gQg/V1XNOZQFRcuJMggwDO3C3GcT#n#vcTYdlaLWa4uVVyL9tQ+e7pvvLtdhiyay/lwmP/9XbJJt04XQ9x/ujPZ01Rl536R#n#sVCHG2hhuNLo7RLIgbU32jY=#dp5QLBrhDbkXHBOulYc6K3zqQ6/5cUq6lFxswNi93HDV2kjRbQ01UbEbjyY2z2VW#n#shK7a76hNrHPVD3v6UGbpzXt4HYdnH1g0/v2/OplHkZbNWOq834f+irhHx6xYDBH#n#pkNA0uFecllao4SPulNONx7THott3pqdQtradiUEL0XmFfSLbXGfgaDxMyIYcXZP#n#LWluvZrrJhb4ATDtaA12dCIidg0+fCpleu7HJigB1nP5+bZpgXLbgupPKjV/YPX0#n#ZQM4HGjOFH0ryblesA64y6A0pM9k2jxwGnOJZTIUlL6GuISHsM9+fYr6vm3k15Gv#n#JYbmGkmBJesPgwkY13Q8cw==#JU10mHLqad0fQnuwJ2iBJI1trKSvXvnTnzu7AU2b8XTr05YN13+O+6MNdKIi2yLj#n#W0C//5Lw+4nHJ4XVbm6L1VL8a/BiLHD5sxGQECaaU1WBzt+8oFZocKrhgm5TVvKH#n#BEMNccCcXTY38RYqTlrobYZY/PnEAniT/jZXKHgFiAykb7nfyfbqX3fmL3Fvjg1F#n#X/er0BceCII7ylOlbERHP7DBlx1yS5qxvv3TJzGJxYsyVJioBUJ/NRgqTt6lz7ol#n#oQAsPdouAEY9SCS1W2Q7/8C1+9DQpdm9jQiLlVpQ3yFql5CLQuTa9UCBDGG8Jctn#n#Tgbk/QsV8yPZLHjGMPn1gg==#AIn7zJ6vBGgK1Bay8VMOp/VJY7yaIIrv4W5+lKVv1SvsXLqqjroNzowqws9FIYwD#n#1K0qF91x8tTPXPFbqPvcP1hWMEtIlFXdPtrocan0GU+n12KCOKJjF20M6h2KI709#n#VLEHS9sc43Rz8DDU6EpZ1CmhWcKys7+8CNw4YS5kRtEPhdMPCGzm9pCr+5zMHxWh#n#D52Qzz18PEWblqMOygCE/1U1OaCi/+e5bKFozzDxW+xhLY+KWE2gt6XOo/kEevJi#n#VXhn5g57ll9sLSFX3GuI1sf3SHioSZviKRrcfmTcVoFeNM99MyWZgZQ0kWUCwc1W#n#4IYsqXNhHr5s17ovtcsKxIk=#AJnVkOl5xWCfbJis4vp2Dio1lf2OmHAPVgidXVWcwHNlboZhuckxHDGARNXQU0ZI#n#vpxNyJ4hJ0wwVyN8WsPxPFbPDR+mQtw5fLleOBnGcBq8ER64JoM2WXsUzlneTNS+#n#Hlc5qTGE0L9bcoFmC3nKkCdrOQRg8YLQtEc7rSYxHNmo33Lf2P3PL8+O82pBZZKp#n#sE/Y1dMJsPYoRtbUMZmOls4VfZclyXe+HhDvk82QXD21JD6fQEuzILHLO+C2geGe#n#ZucQ7MwIdCRmPg3ZQUlyEQAuKnoFYUUWX86cbX48DcrolNB+6b9djD8srBQZBxsi#n#MPEgp89WaU8YUEqkf4QcvbQ=#H2M1WFCGA3OcLJNJXsbU1VeaVlRQ6RhcSZSIKl1rxobMAevtmL+WG7kelnd773nC#n#5Pgk4kWCEGGqpVe7e2I9Frd0gf5PMSUjjtQyJDktE9V2aHWurBYaBYnnOoEKlHQI#n#TJPDS5MBFfdIEcDhW78vxOWuSF37sRRpC1wImuSthW8oJM1TBNqUrtCvQNDD3+zF#n#Ugs7qaJjy50AhIFBmkma+umCEFKCqZUeL5amfcWmKjbn4Gq9JRTO2Nb30/gLso6z#n#9ROJFg5D5nqIXT/VwUu9fGd5zEleJXmSwXO8IifOkpj8kUEsGuejczkkC4krwoo8#n#Gq0sxQ+/ghp0hLQcWgThIg==#JD9CfFO872T+Y6P5/TUhkCX03tnf3vLTUQm4/mBSGdwZynU3Koq4uyxC4BkgKFHv#n#eOH6NxMzoJUMgcvVT0yYRDrs9uPko8uo2jmoPNGUhFVtfEGz9DZaWXga3/x1G13C#n#Ap0pJ0hiGa4RVhZjNlqyDrqsjSa3xYLjNfDMhOtBuDZbUghHeE+02fE7cx9alAPi#n#VL3uQUg3s9odqO2H40u8srD2HXTSmVGG2cceXai2CmWDZ2dE7U7st8K3wBiouNEZ#n#D9Ub+wtZVhqeGenBtz+Jh/WpQ52l8o8r2Grja8pW9BYbIButwBuUYg/zHOijj1ut#n#kbOMOKhUuVO4m0rMThryGg==#,VfXhh8FBRpERmSF50pXY7QSb5fX98oACNE6oJK3W2sSRrnc0IjjfcL5MQocm3qpINDRcyUftDIdv8jc5BFDrK/prLV2ajfi7v9Vt74OG3y5R3t+veC4SCB4Mn2X3UVfLLNs6qe1tvvokRQMjik2lgZGlBL1oJWDJb0wO5mfELyjg7IejpH+mqWwfqTz2jz5FU+M0web29Uvh0RYOqGOVsW6v8DcqzobfpxY7O5t8DYi/y6xkdBOEkNsAUqDBtvFt0FliWdO3+okyxqyuQ3Yhz0mHc5F8o9FdWU8zBfdGNSWcJuPNxhjsi5E0zt6XfB+u1CNEfpdmdggH65Q+K44cew==,128a3a00-6e56-4e07-84ed-eb9eb7006431,parlamentary,1375767740182,-----BEGIN CERTIFICATE-----#r##n#MIIDcTCCAlmgAwIBAgIEfXpA0zANBgkqhkiG9w0BAQUFADBaMQ0wCwYDVQQKDART#r##n#dGF0MQswCQYDVQQGEwJOTzEPMA0GA1UEAwwGNzMwMDcxMSswKQYDVQQLDCJLb21t#r##n#dW5hbC0gb2cgcmVnaW9uYWxkZXBhcnRlbWVudGV0MB4XDTEyMDgwNjA1MjkzM1oX#r##n#DTI1MDgwNjA1MjkzM1owgYkxDDAKBgNVBAoMA09yZzEOMAwGA1UECAwFU3RhdGUx#r##n#EDAOBgNVBAYTB0NvdW50cnkxSDBGBgNVBAMMP1ZvdGVyX0tJSE05UG5MZUVmaHFM#r##n#cFhLeDR3VGhiTktBRmllVjMzT0N4UTdadGc3WW89X1NpZ25fXzczMDA3MTENMAsG#r##n#A1UECwwEVW5pdDCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAKW3Qe1b#r##n#FnaJmbX8vtxztdnALixCwAFUOxGDF5xkMxHKfQmZ4hUu+6+3EEg1PRhDqWvcmGGm#r##n#JTgXIanxDNWW6ACo+dOJZT8fP/ReiGGkOtE7qdbPq1Fp2hYCWg0ruRw4hHvj9Nb/#r##n#kMP0LgZxPZcaYcQ2LNXxsAeo8V+HEKnrcLB9SR7C5UeeGolDCJPxFNGtJ3FkKjgo#r##n#/lZEiYHfVDUTsGrOtQuXzcPvy5gA9Ak/4fKaopM6YwrYr/qN6UuRIGVSPPjLRp22#r##n#XKfZGlwDZhLGu50ETpGhrRnRpXkQEA/t2CrNN4Z6gHS17CGpEvUljMwCWCJK40p1#r##n#LRJMGufn2L9WwVUCAwEAAaMPMA0wCwYDVR0PBAQDAgbAMA0GCSqGSIb3DQEBBQUA#r##n#A4IBAQBC+3TX0zPybGhwBASow/B0lpsvDbIssAg18esfRjq3c196hD4XBG0kEq60#r##n#k3oOct6PyQ4wS6GMSxru4V69chWnbj4XsHbWzzPfEOS4J6Lbbl2YY6gDEZorVKHK#r##n#5EPZFaQ74OtrdyAMPwOLUMjAkN5qVhqyAbXVwxjfBcp3seoRlkdHz9q47fz1mOCh#r##n#ghPUwK45yo/ZdjM4q/gC7iPls/hCoNL51jlSFIGhYIa/ZannkyGORnrCHjj5aB/J#r##n#GoqH8LqI2PeeP/QnNQ3B0nKWWMQyzoXUKH6DwBYKuSp3SkDhagIGjPUKMEJAJ2Lw#r##n#eHLVxfFAVFwJFj6BsxqyNv2YPku3#r##n#-----END CERTIFICATE-----,730071.47.07.0716.071600.0001,000007,01,730071,01015224726,CHANNEL_ID_UNCONTROLLED,1375767759507";
    private static final String INJECTED_CLEANSED_VOTE_LINE = "IWvRkimscLc7ZnzHRhq5HkKtL0XXlMwZEFu2w7iIdSBCWDAKuxgUNPkA66dqZ7OyuvqePFoXVHLgemivhxRHAtkKALAtdc3ihfDN99u3vrfiPinPLHcidemy056uxPO+9mb96Cs4RVhFDY3DnW6BuZQUuITmrBOEhaaxja7qbijKD7SNX2bwqRv0j4GeasLaGosqDfFqEYd9aLr7x7M6xkk4AhyoPHG7qAI/A1SiwDxF2ocycQgJk7kl4yeJDgBKPFw0F3xZ1f8yVJEr6d8Su8OwdXN/NiFlX9bjKifFK2bgMqJvlsAPXLjK5ms/2VeSIE4atuMnZquzJMpjEICHcQ==#bJF2NzTcKGDnDIiq4GrZ+wGlZ3qOFk+Qr+dQNNB9lvkFOFKlDgv+3aAhno5UrQNYnYX8CSGKknuOVzCRsEffwXUQdKamWBs6M0psJQ0rtMwzCyrgMNMe+DQm9mK2cgi+j24qmZxCM8SD7bocdlx9JgTYra5NHMYzC7pQwqYUkCQBkoJBFPVaCSOoEmOS03h8qQG1A37d6rSwPgUiScfO0x1bxyrSNbFOgi/l1paJHV5DD5DVnEyqSnZuBqxrf7x2ZQDWZ50fk9QJkIXjJl3rKJ+PVO7RCBJh5UngZw9O6T8lKsfQ8e9SLYCa17vWESPpq4zHqa5SM4dJ0L127NzRWg==#,000007,01,730071,";
    private static final String INJECTED_AREA_SHIFTED_CLEANSED_VOTE_LINE = "Mjk3Mjk1NzMzMDkyMjAxNjg4MDA0MTg4Njk0MTk1ODM5MTcxOTg1MjA5OTEzMzU5OTg4MTU1NTEwMjY0ODkzNTU3NzM5NTg2MjU1OTAyMTY4ODg1NTQ1ODE5OTA1NjIxMDk0Njc1NTI1MDI0NzU2OTAwNDE2OTY3OTY3MzkxMjgxMDYyNzAxNTU5MDk3Nzc2MDIwMDU4MzY4Njk1NDM4NTE5NjgzOTc3ODIyNDg3NjQ0NTUyNzY5MzM0ODE2MzUxNDY2OTcyMTU2ODU2ODYwNzgyNzQ3MTQwNTA3ODk2NzI5ODc2MjgzNjY3NzE4NDQ3MzQxMjMxOTgxNjg2ODE4NDEyMzUzNzk4NDc3MTE5MDY2NDMyMDY1MzY5MTI0NTk2NjcwMTc3NjAxODUyMzE2Njc2NTE0OTMxMDE5MTgwMzk4MzE0NjM5OTQ2NzA5OTUyMzYwNjk0NTIwMzQzNjI2NTM1NzUyNjAyOTM0OTYxODc2NzkyNTQ5Njg5MjUxMDk1MjMwNDM2MzQ2ODAyMzczODM4MDA0NjY5NjU2NzY0OTg5MTg1NjM2MjE0MDc1NDg1MDgyMTY5NzI4ODMyNTI2NTcyMTU2MjEzMzExOTQwOTExMDc2ODIxMDUzNjIyODY5MTI4MTQ1NTIxNzgwNTQ4NjI2NDk1NzYwMDAwNDUwMTYyNDA4NDM2NTU0NDE5MzgzMzY0MTE2NjE1MTY4NTc4NTAxNDE5NjMyNjYyNDU4MDI2MTU0MTc0NTk4NTc5MTAwNjYyMzY4MTgwNTY2NDk0NjgzMTEwNTI2NzU0NTUwMjY5MDY5MjkzMTJ8MTU0NDI2NDAxMTA5NTEwMzA4NzM2NjI3MTI0MDcwMDY5NTAyNjc1OTg3MDQ4ODk4MTAzNjg2NTcxNDAyMTIyMDQ1NTE2NjIyODg0NDc1MDY1NDIzNzUxNjAzMjAwODgzMTU3MDY1MTc5ODc3ODg0NzcxMTA3NTU3NzM4MTkwOTgxNjQyNjI0MTA2NDIwNDA2NDUxMTY3NjgxNTc0ODA4MDY5ODcyNzE2NzQ4OTU1NzIzMTk1MjA4NzAzMzE4OTUyNDI2OTg0ODc3ODE4ODg2NDA1MjAxNDAwODMyOTI3MDY5OTA5NTExNjk2OTcyMzUyMzg5NjQ3MjMzMTI1MTkzNzk4NjQ4MzA0MTE5MDQzMjMyNjAzNDU1NTkxNzczMTk2MTc0NjcxMjkzODY2MzI2MTQxMDg2OTc2MDI4OTc3MzY1MjI5MTMzNDExNTQ0MTExMjY0MzkzNjMzMzQ4MDY5NDMyODA3MTE1NDcyNTUwMjIyNTU0MDQzMTczNDkyNzkwMDg0Nzg4MjQzNTk2ODY2NTk0NDMyMTIzODA1NjU2MjYzMTQ3NTQ1NjAyMDQ0ODcwNDU4ODU0Mjg5MDg2MTQwMjIxNTEyMDg0ODUyOTQ0Nzc5MTg1MDIwNzA0OTU2NTg2OTgwMTA5OTA4MzM4MjAzNDQ5NTQwOTQ1NjAyNjAwNTk5OTkwNDkxODEzOTE2MjM0NzcyNjQ3MTUzNDgwNTIwMTU4MTcwNjU5NDgwMzk3NzMzNjE3NDQ5NTUwMzg5MjgxMjg2NDA3NTM4NDE4NDkxNjYzNTE3NTI4MTA5MDEwOTc4NzY3ODg4NjE=#,001102,02,666601,CHANNEL_ID_UNCONTROLLED";
    private static final String BROKEN_ENCRYPTED_VOTE_LINE = "#";
    private static final String BROKEN_CLEANSED_VOTE_LINE = "*";
    private static final String CLOSING_CLEANSED_VOTES_LINE = "p1";
    private static final int NO_OF_INCIDENTS_IN_TEST_FILES = 6;
    private static final String ENCRYPTED_VOTE_WITH_COLLIDING_KEYS_1_UUID = "8a80d79b3206038a0132163b996e005c";
    private static final String ENCRYPTED_VOTE_WITH_COLLIDING_KEYS_2_UUID = "8a80d79b3206038a0132163b996e005d";
    private NizkpCleansing nizkp;
    private EncryptedVote vcsEncryptedVoteRetainedMoreThanOnce;

    /**
     * Creates a NizkpCleansing object, loads the files with the encrypted votes
     * and the result of the cleansing process, and runs the protocol.
     * @throws IOException Thrown if something goes wrong during IO.
     * @throws URISyntaxException Should not be thrown.
     */
    @BeforeTest
    public void createNizkpLoadDataFilesAndRun()
        throws IOException, URISyntaxException {
        nizkp = new NizkpCleansing(new String[] {
                    ELGAMAL_PROPERTIES_FILE_NAME, AREAS_FILE_NAME,
                    ENCRYPTED_VOTES_FILE_NAME, CLEANSED_FILES_DIR_NAME
                });
        nizkp.run();
    }

    /**
     * Method to create the sample VCS encrypted vote retained more than once.
     * This is done using a StringBuffer so that the compiler doesn't try to put
     * the String as a constant in the String pool, and fail because the String
     * is too long.
     */
    @BeforeTest
    public void createVcsEncryptedVoteRetainedMoreThanOnce() {
        vcsEncryptedVoteRetainedMoreThanOnce = new EncryptedVote(ENCRYPTED_VOTE_LINE);
    }

    /**
     * Verifies that the NIZKP produced an incident about the broken line
     * in the VCS file.
     */
    @Test
    public void resultsMustContainVcsEncryptedVoteBrokenLineIncident() {
        assertZkpContainsIncident(nizkp,
            new EncryptedVoteBrokenLineIncident(BROKEN_ENCRYPTED_VOTE_LINE));
    }

    /**
     * Verifies that the NIZKP produced an incident about the broken line
     * in the cleansed file.
     */
    @Test
    public void resultsMustContainCleansedVoteBrokenLineIncident() {
        assertZkpContainsIncident(nizkp,
            new CleansedVoteBrokenLineIncident(BROKEN_CLEANSED_VOTE_LINE));
    }

    /**
     * Verifies that the NIZKP does not produce an incident about the last line
     * in the file with cleansed votes.
     */
    @Test
    public void resultsMustNotContainCleansedVoteBrokenLineIncidentAboutLastLineInCleansedVotesFile() {
        assertZkpDoesNotContainIncident(nizkp,
            new CleansedVoteBrokenLineIncident(CLOSING_CLEANSED_VOTES_LINE));
    }

    /**
     * Verifies that the NIZKP produced an incident about an encrypted
     * vote appearing twice or more in the cleansed encrypted votes file.
     * 
     * TODO
     */
    @Test(enabled = false)
    public void resultsMustContainVcsEncryptedVoteRetainedMoreThanOnceIncidentForTheCleansedVoteLineRetainedTwice() {
        assertZkpContainsIncident(nizkp,
            new EncryptedVoteRetainedMoreThanOnceIncident(
                vcsEncryptedVoteRetainedMoreThanOnce, 2));
    }

    /**
     * Verifies that the NIZKP produced an incident about a cleansed vote
     * that wasn't present in the VCS encrypted votes file.
     */
    @Test
    public void resultsMustContainInjectedCleansedEncryptedVoteLineIncident() {
        assertZkpContainsIncident(nizkp,
            new InjectedCleansedVoteLineIncident(
                new CleansedVote(INJECTED_CLEANSED_VOTE_LINE)));
    }

    /**
     * Verifies that the NIZKP produced an incident about a cleansed vote
     * that wasn't present in the VCS encrypted votes file.
     * 
     * TODO
     */
    @Test(enabled = false)
    public void resultsMustContainAreaShiftedCleansedEncryptedVoteLineIncident() {
        assertZkpContainsIncident(nizkp,
            new InjectedCleansedVoteLineIncident(
                new CleansedVote(INJECTED_AREA_SHIFTED_CLEANSED_VOTE_LINE)));
    }

    /**
     * Verifies that the NIZKP produced an incident about colliding VCS encrypted
     * votes map keys.
     * 
     * TODO
     */
    @Test(enabled = false)
    public void resultsMustContainVcsEncryptedVotesMapKeyCollisionIncident() {
        assertZkpContainsIncident(nizkp,
            new EncryptedVotesMapKeyCollisionIncident(
                ENCRYPTED_VOTE_WITH_COLLIDING_KEYS_1_UUID,
                ENCRYPTED_VOTE_WITH_COLLIDING_KEYS_2_UUID));
    }

    /**
     * Verifies that the NIZKP didn't produce more incidents than present in
     * the test files.
     * 
     * TODO
     */
    @Test(enabled = false)
    public void resultsMustNotContainMoreIncidentsThanInjectedInTestFiles() {
        assertEquals(nizkp.getResults().size(), NO_OF_INCIDENTS_IN_TEST_FILES);
    }
}
