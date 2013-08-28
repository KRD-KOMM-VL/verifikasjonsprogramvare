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
package com.computas.zkpev2013.rcgvcs;

import com.computas.zkpev2013.EncryptedVote;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * Unit tests on the EncryptedVoteWithoutVotingReceiptIncident class.
 *
 */
public class EncryptedVoteWithoutVotingReceiptIncidentUnitTest {
    private static final String GIVEN_UUID = "8a84806e40521c6d01405224937601d5";
    private static final String GIVEN_SAMPLE_LINE_BODY = ",rO0ABXNyADtjb20uc2N5dGwuZXZvdGUucHJvdG9jb2wuaW50ZWdyYXRpb24udm90aW5nLm1vZGVsLkF1dGhUb2tlbvz+Wz5pky7LAgAWSQAhX2FkdmFuY2VPZkV4cGlyYXRpb25UaW1lSW5NaW51dGVzSQAaX2FmdGVyQ2xvc2luZ1RpbWVJblNlY29uZHNKAAVfYXNJZEkAGF9leHBpcmF0aW9uVGltZUluTWludXRlc0oAA190c1sAD19hdXRoU2VydmljZVNpZ3QAAltCTAAJX2NvbnRlc3RzdAAQTGphdmEvdXRpbC9MaXN0O0wAGl9jb250cm9sbGVkUmV0dXJuY29kZXNUeXBldAASTGphdmEvbGFuZy9TdHJpbmc7TAAQX2VsZWN0aW9uRXZlbnRJZHEAfgADTAALX2VsZWN0aW9uSWRxAH4AA0wAA19pZHEAfgADTAAJX2ludFRva2VudABBTGNvbS9zY3l0bC9ldm90ZS9wcm90b2NvbC9pbnRlZ3JhdGlvbi92b3RpbmcvbW9kZWwvSW50ZXJuYWxUb2tlbjtMABdfaW50VG9rZW5FbnRpdHlQcm92aWRlcnEAfgADTAADX2lwcQB+AANMAA1fcG9sbGluZ1BsYWNldABATGNvbS9zY3l0bC9ldm90ZS9wcm90b2NvbC9pbnRlZ3JhdGlvbi92b3RpbmcvbW9kZWwvUG9sbGluZ1BsYWNlO0wAHF91bmNvbnRyb2xsZWRSZXR1cm5jb2Rlc1R5cGVxAH4AA0wACl92b3RlckFyZWFxAH4AA0wADF92b3RlckNvbW1JZHEAfgADTAAPX3ZvdGVyRmlyc3ROYW1lcQB+AANMABBfdm90ZXJJZGVudGlmaWVydABDTGNvbS9zY3l0bC9ldm90ZS9wcm90b2NvbC9pbnRlZ3JhdGlvbi92b3RpbmcvbW9kZWwvVm90ZXJJZGVudGlmaWVyO0wADl92b3Rlckxhc3ROYW1lcQB+AANMAAxfdm90aW5nTW9kZXNxAH4AAnhwAAAABQAAAlgAAAAAAAAIAAAAAB4AAAFAUiRRBHVyAAJbQqzzF/gGCFTgAgAAeHAAAAEAWzZ/Qnv9zj6YBzUVPoVBfTmpgLoxt/btSUID6Vpo6cUdN2aFtVhahR0Pu/jgS8nl+FrIumIdu/tuQdtOyaYwow7alTzagG7ketOmKUtOjt/vp0BGjo78OsXI+XVnwMluoEpSOk9p/u+yebz86kc0ttuafUYe82kMRyv/SUQxXWYhpSnI2oC3o9dcZMnrfx9sdAkFjZOJlABxyxNgAq+DI5dn27h0Qhmq068S2duuBliz5M5CDNwu8SqWJw/CiWQeH2x5wAh0DmmjtUbois72w4ybrBRzqIuUfZgwp93j7WScg+myOYb/WFyumrruwkcdkI3JnksiT1XjsMSDDOV2T3NyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAABdwQAAAABc3IAQGNvbS5zY3l0bC5ldm90ZS5wcm90b2NvbC5pbnRlZ3JhdGlvbi52b3RpbmcubW9kZWwuQWxsb3dlZENvbnRlc3QiWqLrfd15KQIAA0wACl9jb250ZXN0SWRxAH4AA0wAC19lbGVjdGlvbklkcQB+AANMAAZfdm90ZWRxAH4AA3hwdAAGMDAwMDA3dAACMDF0AAEweHQABlNDUkVFTnQABjczMDA3MXQAA2lkNXQAJDI1ZWUzM2JmLTdmN2YtNDVhZC1iNDk1LTliMWQ4ZmNlM2VhZnNyADxjb20uc2N5dGwuZXZvdGUucHJvdG9jb2wuaW50ZWdyYXRpb24udm90aW5nLm1vZGVsLk1pbklEVG9rZW7g1pITYjnPpQIABEoAA190c0wAA19pZHEAfgADTAAFX2luZm9xAH4AA0wACF92b3RlcklkcQB+AAN4cAAAAUBSJE8ydAAkM2UyODc4NjMtNmNiNi00NzRhLWJlNzgtNmU5MWZmYmE4NDIwdABrI1R1ZSBBdWcgMDYgMDc6NDA6NDYgQ0VTVCAyMDEzCnVzZXJJZD0wMTAxNDgyNjM5MAp0cz0xMzc1NzY3NjQ2MDAyCmlkPTNlMjg3ODYzLTZjYjYtNDc0YS1iZTc4LTZlOTFmZmJhODQyMAp0AAswMTAxNDgyNjM5MHQADFVTRVJQQVNTV09SRHQACzEwLjAuMzIuMjQzc3IAPmNvbS5zY3l0bC5ldm90ZS5wcm90b2NvbC5pbnRlZ3JhdGlvbi52b3RpbmcubW9kZWwuUG9sbGluZ1BsYWNl1/fPlAehz24CAAdMAA1fYWRkcmVzc0xpbmUxcQB+AANMAA1fYWRkcmVzc0xpbmUycQB+AANMAA1fYWRkcmVzc0xpbmUzcQB+AANMAAVfbmFtZXEAfgADTAANX29wZW5pbmdIb3Vyc3EAfgACTAAJX3Bvc3RUb3ducQB+AANMAAtfcG9zdGFsQ29kZXEAfgADeHBwcHBwc3EAfgAKAAAAAHcEAAAAAHhwcHEAfgARdAAdNzMwMDcxLjQ3LjA3LjA3MTYuMDcxNjAwLjAwMDF0AAVmYWxzZXQAB1JhZ25hcnJzcgBBY29tLnNjeXRsLmV2b3RlLnByb3RvY29sLmludGVncmF0aW9uLnZvdGluZy5tb2RlbC5Wb3RlcklkZW50aWZpZXLKvqZX4QyCdgIAAkwACF9zcGFyZUlkcQB+AANMAAhfdm90ZXJJZHEAfgADeHB0AAswMTAxNDgyNjM5MHQACzAxMDE0ODI2MzkwdAARTG9cdTAwRjBiclx1MDBGM2tzcQB+AAoAAAABdwQAAAABc3IARGNvbS5zY3l0bC5ldm90ZS5wcm90b2NvbC5pbnRlZ3JhdGlvbi52b3RpbmcubW9kZWwuRWxlY3Rpb25Wb3RpbmdNb2RllMcp1WIDaxECAAJMAAtfZWxlY3Rpb25JZHEAfgADTAALX3ZvdGluZ01vZGVxAH4AA3hwdAACMDF0AAEyeA==,25ee33bf-7f7f-45ad-b495-9b1d8fce3eaf,IWvRkimscLc7ZnzHRhq5HkKtL0XXlMwZEFu2w7iIdSBCWDAKuxgUNPkA66dqZ7Oy#n#uvqePFoXVHLgemivhxRHAtkKALAtdc3ihfDN99u3vrfiPinPLHcidemy056uxPO+#n#9mb96Cs4RVhFDY3DnW6BuZQUuITmrBOEhaaxja7qbijKD7SNX2bwqRv0j4GeasLa#n#GosqDfFqEYd9aLr7x7M6xkk4AhyoPHG7qAI/A1SiwDxF2ocycQgJk7kl4yeJDgBK#n#PFw0F3xZ1f8yVJEr6d8Su8OwdXN/NiFlX9bjKifFK2bgMqJvlsAPXLjK5ms/2VeS#n#IE4atuMnZquzJMpjEICHcQ==#Yu1feSQrnt33I9OVYi1PImFZ6k4VzFDrE+cYdGTweKlIHrBVlrAiGgvbT9mjC/L6#n#bnjBvUntSLm/Qk8TI1vYCf2NpuF9m27aNxl6gmu/aeT9hIy+0sFGx/v2xk0YnBod#n#FQMJhxE/arxBlUyrT7q2QRh5w9Mpy9oz294QzpjnUSk5tCy40FFtDz+BU5J+VQf5#n#5XMNVinuoMn9LOB5UtqTYfQKa2AbLj7VA1ZQkkIso5avKe9qgG3steKOe2lKTOAA#n#ulnLe6anq0SE/Gf3PaScKZmKnwY71zU1Dx4kaG0gtr9s0R6La2J1wtW8+YDm/Cax#n#MahnwgV0cat+EollDur5tA==#AIsk+GgmlDnc9ms5MUjSKOH4GPourhsb/TOsmUfkHKCWfwE1cl08cY6MI53W6moO#n#q0cAQWkTuVLUMURTNW0mVjbT4CUKKyh1y3XZv82RnKEflr9KHJGRS1Di//q4uSKq#n#QZzL3+Oq36P/XDATnLjrEWTHyv7lWDueTIJRVuBk9/uoMCWLqOiEMlRqCP9HQNwO#n#0iH+B9XXaDXZ5jRhRtc5Sft7cKTiNx2lP/EIz+YheH3RuhuOAt7RDDUa7D3d11FI#n#71mfyx5nc6BsdS7HogF0HuZg6zxQsHmSVaBNqVAHQY4l/zZtXSPLpPDM7hYKZPae#n#WHGIU+p4Z/sC0IHqoBl3voc=#YwhLNfKSakeTlh5U9sUj5VqZwPERb87cFt4bVkujf6CDSEpgTliEM2tVk750D36W#n#QDk+OUXOA5UpIMT/8k0w5OYP0mIWQxJvWadQb5HmQ3ui94mkhvw7PCl4OlCQz6JC#n#lqO5J/hHP060FLsCdBx15C29PlxPu9WA5bCTLJD1ygAx2Yo6jhVKVbyiU4VviAd1#n#pFYK+0+t/J5gzLFys3wolZLzvWtw0vc65z9JYZiUdRR9FpUx0hT1r/t4/qEPGI+C#n#pdEdHNAlsKpSmfanyCkTOu4xaMfP1sRKopSJZxDh3n1ByCJLJjHbN2hzzbJqJuPB#n#PWRd1jTKBbbBxowG4OzjtA==#AJFkOCtnPk6RN1au+EpD/mqGG/xb5TZZ2HhHdX5ZmefvxIO0J+2JQ4DJdIVFpOrq#n#z19WCxm+87XCsaG0mZQe9yL7tG7G02xAxNQqv+N2tXH9ExKhX7lm0c9ydhrrFd4i#n#ALQSn9BbVkr4XPWYA+rcxIpiJeyXeqQAsr7aUPikxUT0WtxZtJybejM2ZJZcUR0a#n#l7+OpP/ftcdVzH7DLQlRENjUavkz7ODlJ59SYI4zm34QnFXGYksv5PsyH6cD7jIu#n#eia5Gn0OkcS89O14PIHUNMVELCLO4XI+tlOUq0V8LImy8nOR+vm5dIqdBsmqHQ8A#n#uylDx5J6J3LyhTUN1hhBq24=#AKnNhCuncAhD5KJOoW0/RM5LV3I/pY1cKo/kLIlv/o14DCu7XVbC0rjjhEam2wMY#n#QYu/4TveX+2d7Zs5r3jYp0pYAZhbmRWvlzAIH/7HobMFQhofZhDNn/1GgaOMGJgk#n#9gjdR2lto2T1jPK7yo4KjDpd/xdfq2bsrl8X6eAZwPWXRxgsuNp5u16o/NuiovUT#n#JLTMoi701g1pBA8JrPGXYumL9xNEt26d657OgNSt19QXU6lnaoKbHd2KIMPNh1lF#n#crHl3Fe341Q97dm1D/bIh56nKE8JazHlQlnAP3Pe10q5rID+oPFob+B39Wig0Kkm#n#C9slJS8XPwnFr9FDyez35V4=#AJHXJFM5oOcblNGEmN8wLfMzy14M7Q5/QRc7qnOEF+69AxNV9qC4mVXwxNOvptIP#n#DeUURDtGVpqMISVx1refjeRO0PL6g/BYDzOf8PjRS653HaZACWClleGrUsz3N59k#n#2Pbr7yYCVBBvhA/efhAIhHJe3ne9zd61nNnyYh7KPjBzc9aJnaTH+WVl3zAmz5sR#n#ZraHBMpkuGhAXerfqYGUqUZuUTX5G6d+nhoy3B21xaOQn0+VHtuI/Sbo6PJQbOUt#n#XFr0ZP9JQAg/ZxFmMllafpwDmGKT74iADaCZ1LlxlFnulPL6EJxOP66wogKXkUGv#n#VGeOu8p9Xn36802z9tsrFFs=#MNdfe2Av03KtuidPJ9JkwUzFxGyNU/j2xXbSvJbhBOScQQy4aomJtpk0KsKj/BN+#n#KEpCsOeekkHE85NxRuShaputGZUzRhv5RllW3nvhWO2zGkzPxWRJrWuJzPbKkAP9#n#cWDa9H5G7Nim+Y9ydj3Lsf2Wz/O/frEi55n+sZlVuAQixJCaTJvqLR8NdKMMwbNv#n#VF38WSU3tQ5ibu5tFPc/fNOjZdojIX9QG8hfddqRI0ckBsuzN4WzwQI0tVXixvsn#n#4qoJdH+sWZYmDLPFIeNZj+MvZtVbxd8AHA6HzRGDp0zvOssj+xv98qNlNAXJphWz#n#O0pJp3P716bGEH6pw9I=#AJzbGhvwrk+gDLzKAVwj6GO9hLaFW3Z6WJ+gjInerOoacf1lQBN4FqsZmm8QiGlN#n#cisaw6VJhSGkwyFpsl6HadXQZcJi1d++7jmm+02XPStlTbWHmotIsDZ01JnmYh7D#n#hsARmyv1NWR8jU3HpkS85C7/HPudowNWIDguwMBYl7mijP5EhY76h5lg1zn/tJA4#n#1jrOjykf8dsRXbyvKCMk5juzINPk3Bg01SDCDQaC5mmphBK1iWCRc/TBZuoUC8ad#n#9ly+FrdWd7Zz1bv5pqNMHw518Jyal7jfyX2xmdlC0zCXBw1skdu8OvUqzD3I9aCM#n#ZjVwWbAlZ2VFNbmN8kysqT4=#AJFAnmPFLJNsDlXzHfJ3t+qn+6agok8MCpLjQHBYa4kVaEUkC7vVmDAtHTiXEJW+#n#f5bPUHF+tHPFlPbU/lYzppbn3xI4s8uzt170bs15796z8Kajrg1Kkr3IbO2nC1Br#n#KujxAtWEEh0Zy/9MMudeosoiC6ACFpNxSS2TPPYJXDrZUXGd0mJ4CQi6vr67S+A+#n#dDy+24r3oJombaY/1AShST9CodPnB/D78j7iYzI9G3bF1DjPNJNluOG9iOXb80sx#n#+7jLSST1mOPCvwhZH5kqjcJeeteGiaWeQXcFxD+C0smFHkBnIrQpcy4NfaXry5Z5#n#KZYdisqHkYNQ8rQc67G3lhA=#AIpk/6Ovv+wdNvHWNG/0Po3aQAaPJc/7XEJN4Ga21y3qwo2R8XQ8jgD5RrhRA3JB#n#Ak6JFFyzoxJPXdL6A1YiC13XE73udfnz3oqS+TD6DLAx0ffeI+2UuteT1ANe3lwQ#n#QcN/qkXsfYTkkyM4MiA+hv5hHF9p01wzmT9UHSIAN7r7xuibvYe23JMn9AcYQPBX#n#NbfPfP6fYxcd/LeiANgjrXW41oo1+fzXEUimMYlXnbQYeB78OwCglHdFW5o1E5/t#n#cjg7LKdsY7cDHhmksy8lDAdGrYkochwAB1rmmEXB07FzbCt426WLOg3pZdtfVGoM#n#4KmScCnidHTlQ4+Dm3y5Reg=#AKhdSy0Segn/z41yAtJXfQq9Hy+rXdOLlvoS6S12kQD2WqooswmIUpy9WQjFewpH#n#Wi01hz9bKJbGzy3AQE09edfuF8kKbu7v29lyCqYhTsYZ0y02gGHMUtEFFrbnEShd#n#qoZX/IWsSeBJnu/Yr97Bi957Cq4oRO0Q/dHG1swFRH1d4xidiieWspLIXEhwjaiA#n#1p41rO2l3rgvlj4zh43QbuqbhFzj1XuUZlJ3UefejV+VrhTaIIa4oTf0ONksYcCh#n#ybavNO+RS4WYCW20Pt582mhDdRqlv94NMa7s5u+knE6dA8GmhFUnUN5w3cdbZ83E#n#d02h4vdQmbqqjPbBelueru8=#TGmAQvOeFSoek4zJAFVXojTDNGtj7q4UnMK1gEv6UAnbjHKMWdR7rVoAQTJttcFF#n#c8wQBzHe+qgdG8DjR4PGjZ00FJ73licBxYOTA4tUWI19JuNo/0/ezMm2MgUQgkkn#n#ehhIvsro6VIHp886GjyTmcq7xhTBdlhxY+68JNZ9CyqcQQOjfKWuHrX/b8WS0o9R#n#j3GP2FW4wWdSHWyr4OHfo8VeWV224LLttHGuxgeu6dluo9ebuUUIoFT8hEAeU4Uy#n#qwwiNqzooGUbWgp2rLcxSZUEkNPzTmYcw1GlSMyXXCJhuNTmyO9tN5hdz9nI/3PQ#n#xBI8kBDKKYMMNn1KtRqlWQ==#GRAI2UQKfG6zoDgMKNbCoWwQzG/0Gc2RITARmlAcdAjj4tue1kL48reSV2ywAjoY#n#iX5xNIXlJNHCE/2Df2j3gBuLXwZ/66a3wX/Qi9twK6IWs0PyRp53zMdsx9XPqO42#n#hPbOWicy3DTr18hXAz0LrmvxC8GkF7yX5GTPCdGNm9xaEsXALbqZec5DflDyalXT#n#g6vdJ8fUeT1owxTYU+ykvJr153gDtCBNSg32M5mS6pzwLxj7qmOCDnRQtPCnYaQv#n#zjeVAOb/WIwjuu8BuOdnmHTZvp+8WSCD1eg7F/aXk/+Y94VwOamxz5sNo+N7/MeM#n#P5D/TCgvUILimoH31I9tfw==#,HCK3OL4uIY6Py9nbGQr1rmHpcIc2ZnC2WuVPYRViafIod91UViJUBeDMliw290SSikgivbB2FCdZLP4x1/+7JkB+P+gQArNGrYracljKs7KNJ0+ekYJG++CUd7bqX0bjpfdLfaUn+I1vz1156RyE53feV7N14jx8wBxuIeP/iywyNZVtUgAUUivQ6hZKc9OZX/d3PRIMHgCRN+isEt0I8Iz3IVqyd4BxJt6kraake0v3jbWXdRYI1NlBTAsQnHnAmKpd51I0aOwsHM/bua1r1HOzzxEvk3uWkW+A00qmg22vHKyxqm8PLqzurYdyJ1Pz4wAnvUIVhvJvH4WfZh6QHQ==,3e287863-6cb6-474a-be78-6e91ffba8420,parlamentary,1375767646468,-----BEGIN CERTIFICATE-----#r##n#MIIDcTCCAlmgAwIBAgIEYRY7QzANBgkqhkiG9w0BAQUFADBaMQ0wCwYDVQQKDART#r##n#dGF0MQswCQYDVQQGEwJOTzEPMA0GA1UEAwwGNzMwMDcxMSswKQYDVQQLDCJLb21t#r##n#dW5hbC0gb2cgcmVnaW9uYWxkZXBhcnRlbWVudGV0MB4XDTEyMDgwNjA1MjkzMloX#r##n#DTI1MDgwNjA1MjkzMlowgYkxDDAKBgNVBAoMA09yZzEOMAwGA1UECAwFU3RhdGUx#r##n#EDAOBgNVBAYTB0NvdW50cnkxSDBGBgNVBAMMP1ZvdGVyX08zalpHMnI4M3Iwd2lj#r##n#ckNIWmxkVjJFeTFva096WUd3RmVySU9ZMjhKTEE9X1NpZ25fXzczMDA3MTENMAsG#r##n#A1UECwwEVW5pdDCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAJiguOvS#r##n#fijRB2ngM17Dwrq0XacSBDcz8edrCWbp2Xlr8HtRJBuhe6toBgVJ/1riOymeLevq#r##n#kqIuYkzq4vYQUsCRga+cP1HbE05lxBx8ClmiaMqSQ/rslgiunyZYJD4AIgqGXhhm#r##n#pcqDNWtQ30JQXi3suPc4Q9J3dmDw5t7pR6DOHq7Vf09ShCMnxYJgzy7zgRm0AX4M#r##n#HErxnmmhbdiEMP+lCX+2kDnnLKsqZzpP1jeovL1HX8CUFRDfLF9tjnQIjGNcDqZ9#r##n#v8F/oEXzUKYCVcNEyctWuwX8Ly+xD7oR+BaDaPesbt+3o+0JQ6h98e8nqGTm8x6L#r##n#l+AzQWH6Oyz3qzsCAwEAAaMPMA0wCwYDVR0PBAQDAgbAMA0GCSqGSIb3DQEBBQUA#r##n#A4IBAQAMYmDAEHnUYHJkQJxs+BxdvgaoqO3JmZ2fZK/nESeIMAJ9EjuYREw0V/ZW#r##n#5Mtks/jblJkyvFhimFmkOVOkv7SR2HZJaRCd7qbzkUu8a2VEHseyoEm5CjCOGI4L#r##n#QShJMBC/5HVZ/n0WcdfMuAmx2Krgp2Na+3zq7lHVN08ezPgGg04D0WCWjE0hcIST#r##n#Ah4WzygNrGNial0Gr8uUPqg+ZrNyc7ekvW5Y8JvSR/G/b2klLl8YB/kspANHucgH#r##n#lhHREDp0EBnjd15dUAgyuVBo9JGTZcD9G3IKmb3MLAfN8mpILG0/oonMrAnNbIt2#r##n#jAEMNmMioympRU4RtDnM1YQt+uGh#r##n#-----END CERTIFICATE-----,730071.47.07.0716.071600.0001,000007,01,730071,01014826390,CHANNEL_ID_UNCONTROLLED,1375767661520,NzU0ODQ3MDQ5MjIzNTQ3NTg2MjgyOTEyOTUzNzc0NTI3OTE5NjExNDg1MjY3MDQ3NTQ1Mjk2NDQ1OTAzNDUyOTg5ODI2MTkzMjk4MjF8NTM1OTAwNTIwNDA4NjY4MzUyNjQyNjM2Mzc5ODAyMjMxNDEwMjQ3NDc3NzAyMDgwNzk1NjM3ODUwMTI3NTUzOTE5NjA1MDYyOTExODgzMDg4NDI2NDU0NDE4ODcyODQ3NTE3NjUzMzMwNDQ2OTcxMjc1ODAyNTc3MDc3NTA2MzQ5NjExNzA5Nzg4MDU1OTYxNzA5MzE0NzA1Mjk0NjAwMzY2MzI1MDcwMzE1OTkxNjcwODExODI3NjI1NjUyNzI5NzUzNDQ4MTA2ODMyMjkwMDk4ODM1OTQ4NjgyMTgxOTk0ODUwNzc2NzA1MDY3MjUzOTA2MzUzNjQ0MjAyNzEwOTMyMjUwODY0MTg5MTQyOTI4ODEzMjMxNTQ5MzUwMDM5ODU4ODkxMjU2NjQ2MjE5ODUzNDI0MDAwMjQ5NjUyNTAwOTI4MDY1NzE2MDQ2MjkxNjM0MjY5NzUwMzI5NjgxNzI3Mzc5Mzk5MjYyMjIyNzc2ODg4MjQxNDA5ODIwMDc1MjE3MzE4ODk0NjQyNzg2NDI3NjYwNzU0NjE3OTQ5NTk3NDIzNjA2MjkzNzQyMTI2MzAyODk5MjU4MDA4NjYwNzg2NTcwOTcxNzI1NzIxMDU4MzQ5MTM2NjY5ODg3NTcwNzcyOTk4MTg0OTQwMjQxMTkxMzQ2MzI0ODM2NDc3MDA5MTgyNTY2OTQyMjM0NTE2NDc1NTA4NTUwMDE1NzU2OTQwOTgxMDY3MzU2Nzc2NjAzODg2MjMwNDczMDg3MTMyMzc5MTIwNzA5ODAzOTAyNDk3OTQwNTk3MzAxMzAyMjUxNTE3NzY5OXxudWxs";
    private static final String GIVEN_SAMPLE_LINE = GIVEN_UUID +
        GIVEN_SAMPLE_LINE_BODY;
    private static final String OTHER_SAMPLE_LINE = "8a84806e40521c6d01405224937601d6" +
        GIVEN_SAMPLE_LINE_BODY;
    private EncryptedVoteWithoutVotingReceiptIncident incident;

    /**
     * Creates an incident to test against.
     */
    @BeforeMethod
    public void createEncryptedVoteWithoutVotingReceiptIncident() {
        incident = new EncryptedVoteWithoutVotingReceiptIncident(new EncryptedVote(
                    GIVEN_SAMPLE_LINE));
    }

    /**
     * Verifies that an incident is equal to itself.
     */
    @Test
    public void mustBeEqualToItself() {
        assertEquals(incident, incident);
    }

    /**
     * Verifies that an incident has a consistent hashCode.
     */
    @Test
    public void mustHaveSameHashCodeAsItself() {
        assertEquals(incident.hashCode(), incident.hashCode());
    }

    /**
     * Verifies that an incident is not equal to null.
     */
    @Test
    public void mustNotBeEqualToNull() {
        Object nullObject = null;
        assertFalse(incident.equals(nullObject));
    }

    /**
     * Verifies that an incident is not equal to an object of another class. This is used as the test case.
     */
    @Test
    public void mustNotBeEqualToAnObjectOfAnotherClass() {
        assertFalse(incident.equals(this));
    }

    /**
     * Verifies that the incident is equal to another incident with the same encrypted vote.
     */
    @Test
    public void mustBeEqualToAnotherEncryptedVoteWithoutVotingReceiptIncidentWithTheSameEncryptedVote() {
        assertEquals(incident,
            new EncryptedVoteWithoutVotingReceiptIncident(
                new EncryptedVote(GIVEN_SAMPLE_LINE)));
    }

    /**
     * Verifies that the incident has the same hashCode as another incident with the same encrypted vote.
     */
    @Test
    public void mustHaveSameHashCodeAsAnotherEncryptedVoteWithoutVotingReceiptIncidentWithTheSameEncryptedVote() {
        assertEquals(incident.hashCode(),
            new EncryptedVoteWithoutVotingReceiptIncident(
                new EncryptedVote(GIVEN_SAMPLE_LINE)).hashCode());
    }

    /**
     * Verifies that the incident is not equal to another incident with another encrypted vote.
     */
    @Test
    public void mustNotBeEqualToAnotherEncryptedVoteWithoutVotingReceiptIncidentWithAnotherEncryptedVote() {
        assertFalse(incident.equals(
                new EncryptedVoteWithoutVotingReceiptIncident(
                    new EncryptedVote(OTHER_SAMPLE_LINE))));
    }

    /**
     * Verifies that the incident doesn't have the same hashCode as another incident with the another encrypted vote.
     */
    @Test
    public void mustNotHaveSameHashCodeAsAnotherEncryptedVoteWithoutVotingReceiptIncidentWithAnotherEncryptedVote() {
        assertFalse(incident.hashCode() == new EncryptedVoteWithoutVotingReceiptIncident(
                new EncryptedVote(OTHER_SAMPLE_LINE)).hashCode());
    }

    /**
     * Verifies that toString produced a comma-separated line with the correct
     * values.
     */
    @Test
    public void toStringMustBeCommaSeparatedline() {
        assertEquals(incident.toString(),
            "EncryptedVoteWithoutVotingReceiptIncident," + GIVEN_UUID);
    }
}
