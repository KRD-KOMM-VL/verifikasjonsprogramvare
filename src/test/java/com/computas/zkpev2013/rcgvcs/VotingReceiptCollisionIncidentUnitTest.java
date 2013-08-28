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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * Unit tests on the VotingReceiptCollisionIncident class.
 */
public class VotingReceiptCollisionIncidentUnitTest {
    private static final String FIRST_ENCRYPTED_VOTE_UUID = "8a84806e40521c6d01405224937601d5";
    private static final String ENCRYPTED_VOTE_BODY = ",rO0ABXNyADtjb20uc2N5dGwuZXZvdGUucHJvdG9jb2wuaW50ZWdyYXRpb24udm90aW5nLm1vZGVsLkF1dGhUb2tlbvz+Wz5pky7LAgAWSQAhX2FkdmFuY2VPZkV4cGlyYXRpb25UaW1lSW5NaW51dGVzSQAaX2FmdGVyQ2xvc2luZ1RpbWVJblNlY29uZHNKAAVfYXNJZEkAGF9leHBpcmF0aW9uVGltZUluTWludXRlc0oAA190c1sAD19hdXRoU2VydmljZVNpZ3QAAltCTAAJX2NvbnRlc3RzdAAQTGphdmEvdXRpbC9MaXN0O0wAGl9jb250cm9sbGVkUmV0dXJuY29kZXNUeXBldAASTGphdmEvbGFuZy9TdHJpbmc7TAAQX2VsZWN0aW9uRXZlbnRJZHEAfgADTAALX2VsZWN0aW9uSWRxAH4AA0wAA19pZHEAfgADTAAJX2ludFRva2VudABBTGNvbS9zY3l0bC9ldm90ZS9wcm90b2NvbC9pbnRlZ3JhdGlvbi92b3RpbmcvbW9kZWwvSW50ZXJuYWxUb2tlbjtMABdfaW50VG9rZW5FbnRpdHlQcm92aWRlcnEAfgADTAADX2lwcQB+AANMAA1fcG9sbGluZ1BsYWNldABATGNvbS9zY3l0bC9ldm90ZS9wcm90b2NvbC9pbnRlZ3JhdGlvbi92b3RpbmcvbW9kZWwvUG9sbGluZ1BsYWNlO0wAHF91bmNvbnRyb2xsZWRSZXR1cm5jb2Rlc1R5cGVxAH4AA0wACl92b3RlckFyZWFxAH4AA0wADF92b3RlckNvbW1JZHEAfgADTAAPX3ZvdGVyRmlyc3ROYW1lcQB+AANMABBfdm90ZXJJZGVudGlmaWVydABDTGNvbS9zY3l0bC9ldm90ZS9wcm90b2NvbC9pbnRlZ3JhdGlvbi92b3RpbmcvbW9kZWwvVm90ZXJJZGVudGlmaWVyO0wADl92b3Rlckxhc3ROYW1lcQB+AANMAAxfdm90aW5nTW9kZXNxAH4AAnhwAAAABQAAAlgAAAAAAAAIAAAAAB4AAAFAUiRRBHVyAAJbQqzzF/gGCFTgAgAAeHAAAAEAWzZ/Qnv9zj6YBzUVPoVBfTmpgLoxt/btSUID6Vpo6cUdN2aFtVhahR0Pu/jgS8nl+FrIumIdu/tuQdtOyaYwow7alTzagG7ketOmKUtOjt/vp0BGjo78OsXI+XVnwMluoEpSOk9p/u+yebz86kc0ttuafUYe82kMRyv/SUQxXWYhpSnI2oC3o9dcZMnrfx9sdAkFjZOJlABxyxNgAq+DI5dn27h0Qhmq068S2duuBliz5M5CDNwu8SqWJw/CiWQeH2x5wAh0DmmjtUbois72w4ybrBRzqIuUfZgwp93j7WScg+myOYb/WFyumrruwkcdkI3JnksiT1XjsMSDDOV2T3NyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAABdwQAAAABc3IAQGNvbS5zY3l0bC5ldm90ZS5wcm90b2NvbC5pbnRlZ3JhdGlvbi52b3RpbmcubW9kZWwuQWxsb3dlZENvbnRlc3QiWqLrfd15KQIAA0wACl9jb250ZXN0SWRxAH4AA0wAC19lbGVjdGlvbklkcQB+AANMAAZfdm90ZWRxAH4AA3hwdAAGMDAwMDA3dAACMDF0AAEweHQABlNDUkVFTnQABjczMDA3MXQAA2lkNXQAJDI1ZWUzM2JmLTdmN2YtNDVhZC1iNDk1LTliMWQ4ZmNlM2VhZnNyADxjb20uc2N5dGwuZXZvdGUucHJvdG9jb2wuaW50ZWdyYXRpb24udm90aW5nLm1vZGVsLk1pbklEVG9rZW7g1pITYjnPpQIABEoAA190c0wAA19pZHEAfgADTAAFX2luZm9xAH4AA0wACF92b3RlcklkcQB+AAN4cAAAAUBSJE8ydAAkM2UyODc4NjMtNmNiNi00NzRhLWJlNzgtNmU5MWZmYmE4NDIwdABrI1R1ZSBBdWcgMDYgMDc6NDA6NDYgQ0VTVCAyMDEzCnVzZXJJZD0wMTAxNDgyNjM5MAp0cz0xMzc1NzY3NjQ2MDAyCmlkPTNlMjg3ODYzLTZjYjYtNDc0YS1iZTc4LTZlOTFmZmJhODQyMAp0AAswMTAxNDgyNjM5MHQADFVTRVJQQVNTV09SRHQACzEwLjAuMzIuMjQzc3IAPmNvbS5zY3l0bC5ldm90ZS5wcm90b2NvbC5pbnRlZ3JhdGlvbi52b3RpbmcubW9kZWwuUG9sbGluZ1BsYWNl1/fPlAehz24CAAdMAA1fYWRkcmVzc0xpbmUxcQB+AANMAA1fYWRkcmVzc0xpbmUycQB+AANMAA1fYWRkcmVzc0xpbmUzcQB+AANMAAVfbmFtZXEAfgADTAANX29wZW5pbmdIb3Vyc3EAfgACTAAJX3Bvc3RUb3ducQB+AANMAAtfcG9zdGFsQ29kZXEAfgADeHBwcHBwc3EAfgAKAAAAAHcEAAAAAHhwcHEAfgARdAAdNzMwMDcxLjQ3LjA3LjA3MTYuMDcxNjAwLjAwMDF0AAVmYWxzZXQAB1JhZ25hcnJzcgBBY29tLnNjeXRsLmV2b3RlLnByb3RvY29sLmludGVncmF0aW9uLnZvdGluZy5tb2RlbC5Wb3RlcklkZW50aWZpZXLKvqZX4QyCdgIAAkwACF9zcGFyZUlkcQB+AANMAAhfdm90ZXJJZHEAfgADeHB0AAswMTAxNDgyNjM5MHQACzAxMDE0ODI2MzkwdAARTG9cdTAwRjBiclx1MDBGM2tzcQB+AAoAAAABdwQAAAABc3IARGNvbS5zY3l0bC5ldm90ZS5wcm90b2NvbC5pbnRlZ3JhdGlvbi52b3RpbmcubW9kZWwuRWxlY3Rpb25Wb3RpbmdNb2RllMcp1WIDaxECAAJMAAtfZWxlY3Rpb25JZHEAfgADTAALX3ZvdGluZ01vZGVxAH4AA3hwdAACMDF0AAEyeA==,25ee33bf-7f7f-45ad-b495-9b1d8fce3eaf,IWvRkimscLc7ZnzHRhq5HkKtL0XXlMwZEFu2w7iIdSBCWDAKuxgUNPkA66dqZ7Oy#n#uvqePFoXVHLgemivhxRHAtkKALAtdc3ihfDN99u3vrfiPinPLHcidemy056uxPO+#n#9mb96Cs4RVhFDY3DnW6BuZQUuITmrBOEhaaxja7qbijKD7SNX2bwqRv0j4GeasLa#n#GosqDfFqEYd9aLr7x7M6xkk4AhyoPHG7qAI/A1SiwDxF2ocycQgJk7kl4yeJDgBK#n#PFw0F3xZ1f8yVJEr6d8Su8OwdXN/NiFlX9bjKifFK2bgMqJvlsAPXLjK5ms/2VeS#n#IE4atuMnZquzJMpjEICHcQ==#Yu1feSQrnt33I9OVYi1PImFZ6k4VzFDrE+cYdGTweKlIHrBVlrAiGgvbT9mjC/L6#n#bnjBvUntSLm/Qk8TI1vYCf2NpuF9m27aNxl6gmu/aeT9hIy+0sFGx/v2xk0YnBod#n#FQMJhxE/arxBlUyrT7q2QRh5w9Mpy9oz294QzpjnUSk5tCy40FFtDz+BU5J+VQf5#n#5XMNVinuoMn9LOB5UtqTYfQKa2AbLj7VA1ZQkkIso5avKe9qgG3steKOe2lKTOAA#n#ulnLe6anq0SE/Gf3PaScKZmKnwY71zU1Dx4kaG0gtr9s0R6La2J1wtW8+YDm/Cax#n#MahnwgV0cat+EollDur5tA==#AIsk+GgmlDnc9ms5MUjSKOH4GPourhsb/TOsmUfkHKCWfwE1cl08cY6MI53W6moO#n#q0cAQWkTuVLUMURTNW0mVjbT4CUKKyh1y3XZv82RnKEflr9KHJGRS1Di//q4uSKq#n#QZzL3+Oq36P/XDATnLjrEWTHyv7lWDueTIJRVuBk9/uoMCWLqOiEMlRqCP9HQNwO#n#0iH+B9XXaDXZ5jRhRtc5Sft7cKTiNx2lP/EIz+YheH3RuhuOAt7RDDUa7D3d11FI#n#71mfyx5nc6BsdS7HogF0HuZg6zxQsHmSVaBNqVAHQY4l/zZtXSPLpPDM7hYKZPae#n#WHGIU+p4Z/sC0IHqoBl3voc=#YwhLNfKSakeTlh5U9sUj5VqZwPERb87cFt4bVkujf6CDSEpgTliEM2tVk750D36W#n#QDk+OUXOA5UpIMT/8k0w5OYP0mIWQxJvWadQb5HmQ3ui94mkhvw7PCl4OlCQz6JC#n#lqO5J/hHP060FLsCdBx15C29PlxPu9WA5bCTLJD1ygAx2Yo6jhVKVbyiU4VviAd1#n#pFYK+0+t/J5gzLFys3wolZLzvWtw0vc65z9JYZiUdRR9FpUx0hT1r/t4/qEPGI+C#n#pdEdHNAlsKpSmfanyCkTOu4xaMfP1sRKopSJZxDh3n1ByCJLJjHbN2hzzbJqJuPB#n#PWRd1jTKBbbBxowG4OzjtA==#AJFkOCtnPk6RN1au+EpD/mqGG/xb5TZZ2HhHdX5ZmefvxIO0J+2JQ4DJdIVFpOrq#n#z19WCxm+87XCsaG0mZQe9yL7tG7G02xAxNQqv+N2tXH9ExKhX7lm0c9ydhrrFd4i#n#ALQSn9BbVkr4XPWYA+rcxIpiJeyXeqQAsr7aUPikxUT0WtxZtJybejM2ZJZcUR0a#n#l7+OpP/ftcdVzH7DLQlRENjUavkz7ODlJ59SYI4zm34QnFXGYksv5PsyH6cD7jIu#n#eia5Gn0OkcS89O14PIHUNMVELCLO4XI+tlOUq0V8LImy8nOR+vm5dIqdBsmqHQ8A#n#uylDx5J6J3LyhTUN1hhBq24=#AKnNhCuncAhD5KJOoW0/RM5LV3I/pY1cKo/kLIlv/o14DCu7XVbC0rjjhEam2wMY#n#QYu/4TveX+2d7Zs5r3jYp0pYAZhbmRWvlzAIH/7HobMFQhofZhDNn/1GgaOMGJgk#n#9gjdR2lto2T1jPK7yo4KjDpd/xdfq2bsrl8X6eAZwPWXRxgsuNp5u16o/NuiovUT#n#JLTMoi701g1pBA8JrPGXYumL9xNEt26d657OgNSt19QXU6lnaoKbHd2KIMPNh1lF#n#crHl3Fe341Q97dm1D/bIh56nKE8JazHlQlnAP3Pe10q5rID+oPFob+B39Wig0Kkm#n#C9slJS8XPwnFr9FDyez35V4=#AJHXJFM5oOcblNGEmN8wLfMzy14M7Q5/QRc7qnOEF+69AxNV9qC4mVXwxNOvptIP#n#DeUURDtGVpqMISVx1refjeRO0PL6g/BYDzOf8PjRS653HaZACWClleGrUsz3N59k#n#2Pbr7yYCVBBvhA/efhAIhHJe3ne9zd61nNnyYh7KPjBzc9aJnaTH+WVl3zAmz5sR#n#ZraHBMpkuGhAXerfqYGUqUZuUTX5G6d+nhoy3B21xaOQn0+VHtuI/Sbo6PJQbOUt#n#XFr0ZP9JQAg/ZxFmMllafpwDmGKT74iADaCZ1LlxlFnulPL6EJxOP66wogKXkUGv#n#VGeOu8p9Xn36802z9tsrFFs=#MNdfe2Av03KtuidPJ9JkwUzFxGyNU/j2xXbSvJbhBOScQQy4aomJtpk0KsKj/BN+#n#KEpCsOeekkHE85NxRuShaputGZUzRhv5RllW3nvhWO2zGkzPxWRJrWuJzPbKkAP9#n#cWDa9H5G7Nim+Y9ydj3Lsf2Wz/O/frEi55n+sZlVuAQixJCaTJvqLR8NdKMMwbNv#n#VF38WSU3tQ5ibu5tFPc/fNOjZdojIX9QG8hfddqRI0ckBsuzN4WzwQI0tVXixvsn#n#4qoJdH+sWZYmDLPFIeNZj+MvZtVbxd8AHA6HzRGDp0zvOssj+xv98qNlNAXJphWz#n#O0pJp3P716bGEH6pw9I=#AJzbGhvwrk+gDLzKAVwj6GO9hLaFW3Z6WJ+gjInerOoacf1lQBN4FqsZmm8QiGlN#n#cisaw6VJhSGkwyFpsl6HadXQZcJi1d++7jmm+02XPStlTbWHmotIsDZ01JnmYh7D#n#hsARmyv1NWR8jU3HpkS85C7/HPudowNWIDguwMBYl7mijP5EhY76h5lg1zn/tJA4#n#1jrOjykf8dsRXbyvKCMk5juzINPk3Bg01SDCDQaC5mmphBK1iWCRc/TBZuoUC8ad#n#9ly+FrdWd7Zz1bv5pqNMHw518Jyal7jfyX2xmdlC0zCXBw1skdu8OvUqzD3I9aCM#n#ZjVwWbAlZ2VFNbmN8kysqT4=#AJFAnmPFLJNsDlXzHfJ3t+qn+6agok8MCpLjQHBYa4kVaEUkC7vVmDAtHTiXEJW+#n#f5bPUHF+tHPFlPbU/lYzppbn3xI4s8uzt170bs15796z8Kajrg1Kkr3IbO2nC1Br#n#KujxAtWEEh0Zy/9MMudeosoiC6ACFpNxSS2TPPYJXDrZUXGd0mJ4CQi6vr67S+A+#n#dDy+24r3oJombaY/1AShST9CodPnB/D78j7iYzI9G3bF1DjPNJNluOG9iOXb80sx#n#+7jLSST1mOPCvwhZH5kqjcJeeteGiaWeQXcFxD+C0smFHkBnIrQpcy4NfaXry5Z5#n#KZYdisqHkYNQ8rQc67G3lhA=#AIpk/6Ovv+wdNvHWNG/0Po3aQAaPJc/7XEJN4Ga21y3qwo2R8XQ8jgD5RrhRA3JB#n#Ak6JFFyzoxJPXdL6A1YiC13XE73udfnz3oqS+TD6DLAx0ffeI+2UuteT1ANe3lwQ#n#QcN/qkXsfYTkkyM4MiA+hv5hHF9p01wzmT9UHSIAN7r7xuibvYe23JMn9AcYQPBX#n#NbfPfP6fYxcd/LeiANgjrXW41oo1+fzXEUimMYlXnbQYeB78OwCglHdFW5o1E5/t#n#cjg7LKdsY7cDHhmksy8lDAdGrYkochwAB1rmmEXB07FzbCt426WLOg3pZdtfVGoM#n#4KmScCnidHTlQ4+Dm3y5Reg=#AKhdSy0Segn/z41yAtJXfQq9Hy+rXdOLlvoS6S12kQD2WqooswmIUpy9WQjFewpH#n#Wi01hz9bKJbGzy3AQE09edfuF8kKbu7v29lyCqYhTsYZ0y02gGHMUtEFFrbnEShd#n#qoZX/IWsSeBJnu/Yr97Bi957Cq4oRO0Q/dHG1swFRH1d4xidiieWspLIXEhwjaiA#n#1p41rO2l3rgvlj4zh43QbuqbhFzj1XuUZlJ3UefejV+VrhTaIIa4oTf0ONksYcCh#n#ybavNO+RS4WYCW20Pt582mhDdRqlv94NMa7s5u+knE6dA8GmhFUnUN5w3cdbZ83E#n#d02h4vdQmbqqjPbBelueru8=#TGmAQvOeFSoek4zJAFVXojTDNGtj7q4UnMK1gEv6UAnbjHKMWdR7rVoAQTJttcFF#n#c8wQBzHe+qgdG8DjR4PGjZ00FJ73licBxYOTA4tUWI19JuNo/0/ezMm2MgUQgkkn#n#ehhIvsro6VIHp886GjyTmcq7xhTBdlhxY+68JNZ9CyqcQQOjfKWuHrX/b8WS0o9R#n#j3GP2FW4wWdSHWyr4OHfo8VeWV224LLttHGuxgeu6dluo9ebuUUIoFT8hEAeU4Uy#n#qwwiNqzooGUbWgp2rLcxSZUEkNPzTmYcw1GlSMyXXCJhuNTmyO9tN5hdz9nI/3PQ#n#xBI8kBDKKYMMNn1KtRqlWQ==#GRAI2UQKfG6zoDgMKNbCoWwQzG/0Gc2RITARmlAcdAjj4tue1kL48reSV2ywAjoY#n#iX5xNIXlJNHCE/2Df2j3gBuLXwZ/66a3wX/Qi9twK6IWs0PyRp53zMdsx9XPqO42#n#hPbOWicy3DTr18hXAz0LrmvxC8GkF7yX5GTPCdGNm9xaEsXALbqZec5DflDyalXT#n#g6vdJ8fUeT1owxTYU+ykvJr153gDtCBNSg32M5mS6pzwLxj7qmOCDnRQtPCnYaQv#n#zjeVAOb/WIwjuu8BuOdnmHTZvp+8WSCD1eg7F/aXk/+Y94VwOamxz5sNo+N7/MeM#n#P5D/TCgvUILimoH31I9tfw==#,HCK3OL4uIY6Py9nbGQr1rmHpcIc2ZnC2WuVPYRViafIod91UViJUBeDMliw290SSikgivbB2FCdZLP4x1/+7JkB+P+gQArNGrYracljKs7KNJ0+ekYJG++CUd7bqX0bjpfdLfaUn+I1vz1156RyE53feV7N14jx8wBxuIeP/iywyNZVtUgAUUivQ6hZKc9OZX/d3PRIMHgCRN+isEt0I8Iz3IVqyd4BxJt6kraake0v3jbWXdRYI1NlBTAsQnHnAmKpd51I0aOwsHM/bua1r1HOzzxEvk3uWkW+A00qmg22vHKyxqm8PLqzurYdyJ1Pz4wAnvUIVhvJvH4WfZh6QHQ==,3e287863-6cb6-474a-be78-6e91ffba8420,parlamentary,1375767646468,-----BEGIN CERTIFICATE-----#r##n#MIIDcTCCAlmgAwIBAgIEYRY7QzANBgkqhkiG9w0BAQUFADBaMQ0wCwYDVQQKDART#r##n#dGF0MQswCQYDVQQGEwJOTzEPMA0GA1UEAwwGNzMwMDcxMSswKQYDVQQLDCJLb21t#r##n#dW5hbC0gb2cgcmVnaW9uYWxkZXBhcnRlbWVudGV0MB4XDTEyMDgwNjA1MjkzMloX#r##n#DTI1MDgwNjA1MjkzMlowgYkxDDAKBgNVBAoMA09yZzEOMAwGA1UECAwFU3RhdGUx#r##n#EDAOBgNVBAYTB0NvdW50cnkxSDBGBgNVBAMMP1ZvdGVyX08zalpHMnI4M3Iwd2lj#r##n#ckNIWmxkVjJFeTFva096WUd3RmVySU9ZMjhKTEE9X1NpZ25fXzczMDA3MTENMAsG#r##n#A1UECwwEVW5pdDCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAJiguOvS#r##n#fijRB2ngM17Dwrq0XacSBDcz8edrCWbp2Xlr8HtRJBuhe6toBgVJ/1riOymeLevq#r##n#kqIuYkzq4vYQUsCRga+cP1HbE05lxBx8ClmiaMqSQ/rslgiunyZYJD4AIgqGXhhm#r##n#pcqDNWtQ30JQXi3suPc4Q9J3dmDw5t7pR6DOHq7Vf09ShCMnxYJgzy7zgRm0AX4M#r##n#HErxnmmhbdiEMP+lCX+2kDnnLKsqZzpP1jeovL1HX8CUFRDfLF9tjnQIjGNcDqZ9#r##n#v8F/oEXzUKYCVcNEyctWuwX8Ly+xD7oR+BaDaPesbt+3o+0JQ6h98e8nqGTm8x6L#r##n#l+AzQWH6Oyz3qzsCAwEAAaMPMA0wCwYDVR0PBAQDAgbAMA0GCSqGSIb3DQEBBQUA#r##n#A4IBAQAMYmDAEHnUYHJkQJxs+BxdvgaoqO3JmZ2fZK/nESeIMAJ9EjuYREw0V/ZW#r##n#5Mtks/jblJkyvFhimFmkOVOkv7SR2HZJaRCd7qbzkUu8a2VEHseyoEm5CjCOGI4L#r##n#QShJMBC/5HVZ/n0WcdfMuAmx2Krgp2Na+3zq7lHVN08ezPgGg04D0WCWjE0hcIST#r##n#Ah4WzygNrGNial0Gr8uUPqg+ZrNyc7ekvW5Y8JvSR/G/b2klLl8YB/kspANHucgH#r##n#lhHREDp0EBnjd15dUAgyuVBo9JGTZcD9G3IKmb3MLAfN8mpILG0/oonMrAnNbIt2#r##n#jAEMNmMioympRU4RtDnM1YQt+uGh#r##n#-----END CERTIFICATE-----,730071.47.07.0716.071600.0001,000007,01,730071,01014826390,CHANNEL_ID_UNCONTROLLED,1375767661520,NzU0ODQ3MDQ5MjIzNTQ3NTg2MjgyOTEyOTUzNzc0NTI3OTE5NjExNDg1MjY3MDQ3NTQ1Mjk2NDQ1OTAzNDUyOTg5ODI2MTkzMjk4MjF8NTM1OTAwNTIwNDA4NjY4MzUyNjQyNjM2Mzc5ODAyMjMxNDEwMjQ3NDc3NzAyMDgwNzk1NjM3ODUwMTI3NTUzOTE5NjA1MDYyOTExODgzMDg4NDI2NDU0NDE4ODcyODQ3NTE3NjUzMzMwNDQ2OTcxMjc1ODAyNTc3MDc3NTA2MzQ5NjExNzA5Nzg4MDU1OTYxNzA5MzE0NzA1Mjk0NjAwMzY2MzI1MDcwMzE1OTkxNjcwODExODI3NjI1NjUyNzI5NzUzNDQ4MTA2ODMyMjkwMDk4ODM1OTQ4NjgyMTgxOTk0ODUwNzc2NzA1MDY3MjUzOTA2MzUzNjQ0MjAyNzEwOTMyMjUwODY0MTg5MTQyOTI4ODEzMjMxNTQ5MzUwMDM5ODU4ODkxMjU2NjQ2MjE5ODUzNDI0MDAwMjQ5NjUyNTAwOTI4MDY1NzE2MDQ2MjkxNjM0MjY5NzUwMzI5NjgxNzI3Mzc5Mzk5MjYyMjIyNzc2ODg4MjQxNDA5ODIwMDc1MjE3MzE4ODk0NjQyNzg2NDI3NjYwNzU0NjE3OTQ5NTk3NDIzNjA2MjkzNzQyMTI2MzAyODk5MjU4MDA4NjYwNzg2NTcwOTcxNzI1NzIxMDU4MzQ5MTM2NjY5ODg3NTcwNzcyOTk4MTg0OTQwMjQxMTkxMzQ2MzI0ODM2NDc3MDA5MTgyNTY2OTQyMjM0NTE2NDc1NTA4NTUwMDE1NzU2OTQwOTgxMDY3MzU2Nzc2NjAzODg2MjMwNDczMDg3MTMyMzc5MTIwNzA5ODAzOTAyNDk3OTQwNTk3MzAxMzAyMjUxNTE3NzY5OXxudWxs";
    private static final String FIRST_ENCRYPTED_VOTE_FOR_RECEIPT_COLLISION = FIRST_ENCRYPTED_VOTE_UUID +
        ENCRYPTED_VOTE_BODY;
    private static final String SECOND_ENCRYPTED_VOTE_UUID = "8a84806e40521c6d01405224937601d6";
    private static final String SECOND_ENCRYPTED_VOTE_FOR_RECEIPT_COLLISION = SECOND_ENCRYPTED_VOTE_UUID +
        ENCRYPTED_VOTE_BODY;
    private static final String VOTING_RECEIPT_COLLISION = "3fy3CmmwLn8TV4/krRcLFJu0ZvmdzyW3pg0zypdTdlI=";
    private static final String COLLIDING_VOTING_RECEIPT = "8a84806e40521c6d01405224937601d5,25ee33bf-7f7f-45ad-b495-9b1d8fce3eaf,rO0ABXNyAD1jb20uc2N5dGwuZXZvdGUucHJvdG9jb2wuaW50ZWdyYXRpb24udm90aW5nLm1vZGVsLlJlY2VpcHRCZWFuw7/82u7t7McCAApKAAZfcmNnSURKAApfdGltZXN0YW1wSgAGX3Zjc0lETAAKX2NvbnRlc3RJZHQAEkxqYXZhL2xhbmcvU3RyaW5nO0wAEF9lbGVjdGlvbkV2ZW50SWRxAH4AAUwAC19lbGVjdGlvbklkcQB+AAFbABRfcmVjZWlwdFNpZ25hdHVyZVJDR3QAAltCWwAUX3JlY2VpcHRTaWduYXR1cmVWQ1NxAH4AAkwAE19yZXR1cm5Db2Rlc01lc3NhZ2VxAH4AAVsADl92b3RpbmdSZWNlaXB0cQB+AAJ4cAAAAAAAAB+cAAABQFIki9AAAAAAAAAAAHQABjAwMDAwN3QABjczMDA3MXQAAjAxdXIAAltCrPMX+AYIVOACAAB4cAAAAQCB9Gqa5CPip7k7xEHfLMni3L1eRPr9bTTaGN+acMcIF8J/LS8sUNR7XEnOT7/FUbgdbtKLSMnyK4qhYuwAFK+3eKox4aStQMlaAIy8NIhkAzDLRz0tI/iRiwk3Ox9Nyl0VaQbKpvXNjw8KLVLt+DUmHBm3J1wgJLdKn4LEguxKZtYygJ4oN00O8W0d4Oixw51DePSuM9VVhxiS34/dXBFX9pJTaZzqQN+23kCdR5n5S2eY/MvsZhVYgXMoY7ZqR56Z9TsMh3eFbLEb1jm2GHwXPXOGmdJlqYSFbwk7xR07zKWmLhaBXCBRmtxfSru+VkGXhOMLpeKu3Nfy61KDW5andXEAfgAHAAABAL29eS1PHxwhRnLT8IJbfGXTosIsxoRRb1nZLBVoN3cDESv7mRBeJNCIFrq1Lw1sQaTA2jde+wmXVzhNC+dPkdWoO0y9zdyDTreAfDdWw5tjrxWDpJCBl4v/Wx2rxJSQ3uc6r3Zbg9nuG/HlU5zwF8AnzWYC4Nb2hwqvEiPy8+p32EfM5h2JeqFpG8fDz0/IQAc0i78COKyHzLGThFPlIqAcK4QPuYVaWJ59KiAMEDlSD/Iqu08DdQK3if+ok4LKBdYBO5YKvnmTGR05qhB8d4TCIE4s7rW8xx9xn3mhi8xgwGv8ZngwZCZNWzT6+SMR7DRddBO2fiT7j0+AM2fBDeBwdXEAfgAHAAAALDNmeTNDbW13TG44VFY0L2tyUmNMRkp1MFp2bWR6eVczcGcwenlwZFRkbEk9,3e287863-6cb6-474a-be78-6e91ffba8420,8092," +
        VOTING_RECEIPT_COLLISION + ",000007,01,730071";
    private static final String OTHER_VOTING_RECEIPT = "8a84806e40521c6d01405225475701d7,a7a1158d-e5fb-47f8-b8e6-f3acf263f44a,rO0ABXNyAD1jb20uc2N5dGwuZXZvdGUucHJvdG9jb2wuaW50ZWdyYXRpb24udm90aW5nLm1vZGVsLlJlY2VpcHRCZWFuw7/82u7t7McCAApKAAZfcmNnSURKAApfdGltZXN0YW1wSgAGX3Zjc0lETAAKX2NvbnRlc3RJZHQAEkxqYXZhL2xhbmcvU3RyaW5nO0wAEF9lbGVjdGlvbkV2ZW50SWRxAH4AAUwAC19lbGVjdGlvbklkcQB+AAFbABRfcmVjZWlwdFNpZ25hdHVyZVJDR3QAAltCWwAUX3JlY2VpcHRTaWduYXR1cmVWQ1NxAH4AAkwAE19yZXR1cm5Db2Rlc01lc3NhZ2VxAH4AAVsADl92b3RpbmdSZWNlaXB0cQB+AAJ4cAAAAAAAAB+cAAABQFIlQqAAAAAAAAAAAHQABjAwMDAwN3QABjczMDA3MXQAAjAxdXIAAltCrPMX+AYIVOACAAB4cAAAAQBiKaW2vl2y9tsMeYg17AdDB913xJobH6Xwh0gF4QR607fVBMJtdsV0nr60KPzOKpgn3BOR6kLC1hHEif0Gf91OlqiFXG9HExDr0dA7Cp0Rwwd8hlcRW/wo2hPng+IQomGG5UyHwnQpWLWGgIrHAjJ2jBYhazHMY786vyc6GoPPEnmcEqfrj/Ku2a79bksC3o2cMLklPqKC5HdmAOiwsG1eyjJpyCB7+aosTh25cDsD6qU7aC/1hie0Z0QhHbdNXslwDHmYSa2nsceP5H65v6stlN7QkUct2wLszDHbgseJRhkzGFIWbgr0NrzRwFKs9N4cAbv/hjkRagEy3uXptrTvdXEAfgAHAAABAKFdMMRzeHus0MMralhJdFzfd5ZbTqGAWBcV9EehM4vFEhe6L/VU2Oc09JIg1T2aWgMkoYncS6/28vD9DchnXQMKgbOua/TraWU6Zl4RUnNKOJc6sqR4i2AGvrDhPpBW9oysBPOIh6u0pKpge+4We9bQ6EOczR8zKM2QO+MQcfFRVtCuo+lM3Hq4pPeeV7Y/qkLRwYfd5kOMkbZGsy617n4/M6HDKaBFRdXixpm3EHDvm7PXqyuit2lrY+JzXtO+QUVMDO6PeP7esJkTMni1mmaI7jEIMNvFThOslF8JxxvXq1lsy/2x+uhruh2aI1dixTo4ICEQSY+5T22wSxTM0ylwdXEAfgAHAAAALE83Q1Y2b3NkamFWQkorMGpKUzgwY28wZXY3K1hLRk1sU3ZwTjdGU212NlU9,dc2574de-cef9-4a45-9960-c88f27ba8a13,8092,O7CV6osdjaVBJ+0jJS80co0ev7+XKFMlSvpN7FSmv6U=,000007,01,730071";
    private static final String OTHER_ENCRYPTED_VOTE = "8a84806e40521c6d014052260fec01d9,rO0ABXNyADtjb20uc2N5dGwuZXZvdGUucHJvdG9jb2wuaW50ZWdyYXRpb24udm90aW5nLm1vZGVsLkF1dGhUb2tlbvz+Wz5pky7LAgAWSQAhX2FkdmFuY2VPZkV4cGlyYXRpb25UaW1lSW5NaW51dGVzSQAaX2FmdGVyQ2xvc2luZ1RpbWVJblNlY29uZHNKAAVfYXNJZEkAGF9leHBpcmF0aW9uVGltZUluTWludXRlc0oAA190c1sAD19hdXRoU2VydmljZVNpZ3QAAltCTAAJX2NvbnRlc3RzdAAQTGphdmEvdXRpbC9MaXN0O0wAGl9jb250cm9sbGVkUmV0dXJuY29kZXNUeXBldAASTGphdmEvbGFuZy9TdHJpbmc7TAAQX2VsZWN0aW9uRXZlbnRJZHEAfgADTAALX2VsZWN0aW9uSWRxAH4AA0wAA19pZHEAfgADTAAJX2ludFRva2VudABBTGNvbS9zY3l0bC9ldm90ZS9wcm90b2NvbC9pbnRlZ3JhdGlvbi92b3RpbmcvbW9kZWwvSW50ZXJuYWxUb2tlbjtMABdfaW50VG9rZW5FbnRpdHlQcm92aWRlcnEAfgADTAADX2lwcQB+AANMAA1fcG9sbGluZ1BsYWNldABATGNvbS9zY3l0bC9ldm90ZS9wcm90b2NvbC9pbnRlZ3JhdGlvbi92b3RpbmcvbW9kZWwvUG9sbGluZ1BsYWNlO0wAHF91bmNvbnRyb2xsZWRSZXR1cm5jb2Rlc1R5cGVxAH4AA0wACl92b3RlckFyZWFxAH4AA0wADF92b3RlckNvbW1JZHEAfgADTAAPX3ZvdGVyRmlyc3ROYW1lcQB+AANMABBfdm90ZXJJZGVudGlmaWVydABDTGNvbS9zY3l0bC9ldm90ZS9wcm90b2NvbC9pbnRlZ3JhdGlvbi92b3RpbmcvbW9kZWwvVm90ZXJJZGVudGlmaWVyO0wADl92b3Rlckxhc3ROYW1lcQB+AANMAAxfdm90aW5nTW9kZXNxAH4AAnhwAAAABQAAAlgAAAAAAAAIAAAAAB4AAAFAUiW/FnVyAAJbQqzzF/gGCFTgAgAAeHAAAAEACJI8iw2fYLwLuQHRvZldYd4tYRcyYaQyx36EaPflp0cQ9cwMoKXlyGs3rLiejcdT5nBfaozUU1GZGrlpfL8ylOGuM+22SWIwNdF9oGKJ5d+DgfBH5xzOyDG5jv5BCehtylz40La6fAIoz7hnGAfqXGWx6Zci5Kuax38Max8t9CYdOCqp0cMLk6OL9Fpdvr7+M4u8f3N8REz5yKwpQrrgLnlKFTR8LiMx2IW5PV433JjVcjTttBuMtO0BKOET5ak7Y5haIQH6HofRsCnohYMq7QnclfLeyOHz+/UHvfFXW9Zk0kRZFFMmw8zKDl9m3a+LGQb3Yo/T8EtPR+X6jzT7RHNyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAABdwQAAAABc3IAQGNvbS5zY3l0bC5ldm90ZS5wcm90b2NvbC5pbnRlZ3JhdGlvbi52b3RpbmcubW9kZWwuQWxsb3dlZENvbnRlc3QiWqLrfd15KQIAA0wACl9jb250ZXN0SWRxAH4AA0wAC19lbGVjdGlvbklkcQB+AANMAAZfdm90ZWRxAH4AA3hwdAAGMDAwMDA3dAACMDF0AAEweHQABlNDUkVFTnQABjczMDA3MXQAA2lkNXQAJDhjZDE4MmMyLWFiNTItNGY5Yy04NzAxLWVjMmVjMjM0YzY2N3NyADxjb20uc2N5dGwuZXZvdGUucHJvdG9jb2wuaW50ZWdyYXRpb24udm90aW5nLm1vZGVsLk1pbklEVG9rZW7g1pITYjnPpQIABEoAA190c0wAA19pZHEAfgADTAAFX2luZm9xAH4AA0wACF92b3RlcklkcQB+AAN4cAAAAUBSJb7udAAkMTI4YTNhMDAtNmU1Ni00ZTA3LTg0ZWQtZWI5ZWI3MDA2NDMxdABrI1R1ZSBBdWcgMDYgMDc6NDI6MjAgQ0VTVCAyMDEzCnVzZXJJZD0wMTAxNTIyNDcyNgp0cz0xMzc1NzY3NzQwMTQyCmlkPTEyOGEzYTAwLTZlNTYtNGUwNy04NGVkLWViOWViNzAwNjQzMQp0AAswMTAxNTIyNDcyNnQADFVTRVJQQVNTV09SRHQACzEwLjAuMzIuMjQzc3IAPmNvbS5zY3l0bC5ldm90ZS5wcm90b2NvbC5pbnRlZ3JhdGlvbi52b3RpbmcubW9kZWwuUG9sbGluZ1BsYWNl1/fPlAehz24CAAdMAA1fYWRkcmVzc0xpbmUxcQB+AANMAA1fYWRkcmVzc0xpbmUycQB+AANMAA1fYWRkcmVzc0xpbmUzcQB+AANMAAVfbmFtZXEAfgADTAANX29wZW5pbmdIb3Vyc3EAfgACTAAJX3Bvc3RUb3ducQB+AANMAAtfcG9zdGFsQ29kZXEAfgADeHBwcHBwc3EAfgAKAAAAAHcEAAAAAHhwcHEAfgARdAAdNzMwMDcxLjQ3LjA3LjA3MTYuMDcxNjAwLjAwMDF0AAVmYWxzZXQAB1JhZ25hcnJzcgBBY29tLnNjeXRsLmV2b3RlLnByb3RvY29sLmludGVncmF0aW9uLnZvdGluZy5tb2RlbC5Wb3RlcklkZW50aWZpZXLKvqZX4QyCdgIAAkwACF9zcGFyZUlkcQB+AANMAAhfdm90ZXJJZHEAfgADeHB0AAswMTAxNTIyNDcyNnQACzAxMDE1MjI0NzI2dAARTG9cdTAwRjBiclx1MDBGM2tzcQB+AAoAAAABdwQAAAABc3IARGNvbS5zY3l0bC5ldm90ZS5wcm90b2NvbC5pbnRlZ3JhdGlvbi52b3RpbmcubW9kZWwuRWxlY3Rpb25Wb3RpbmdNb2RllMcp1WIDaxECAAJMAAtfZWxlY3Rpb25JZHEAfgADTAALX3ZvdGluZ01vZGVxAH4AA3hwdAACMDF0AAEyeA==,8cd182c2-ab52-4f9c-8701-ec2ec234c667,W1zg9AiTmHKE/cNkyDBpO2iB4R9z89nh4cBkJWiFySjj2AglLbvwyyY4VdGoXudq#n#VZicfV2MosnZMMZQIBCUCzpAF+a93LoB2Z1n5c2RmmjdLgOd42MGfC+0xc/BQrZ7#n#0DR+Sa9iRUoZVRLDwESxPBVIInPYnFVYDKKfhJRqqafwsp6xdBSwm0TMOZrSM4Ma#n#0/yVL6EhdxRyiXlCpbk8bX38h/R95szQ8okXcvbY6gUNZ2AClzlRgSi3OxqDWROQ#n#8cU5CIxkAdYwFXDm3Qqk5uNnW78H+tCWiRouek7M932kzfzBC++EUPsHfUKub3ZI#n#ph55pkjKDk+3fIePLfjnQA==#AKnHjN6l0Gq/j5EPbJRyHApWdy/QogmzVdlJhxZY8Wck/3v3HQWCAh8XNNMw1J8r#n#jHS0T9NTCDAghZfVHOPYiV5FL/Kr+LDQpHgewykol31rJNKJEWuIlNOGMO0hp8yt#n#kJH+qdcsqNX+JaBVYtC424fNvJPqgZGpaHdvZdjD26nK/NPnZh8YaTA2mimY7Z5c#n#m15/p9e4GZe4ZK0woSI4JxDxDLyJmfMinSNnjeAYtxq977dCWMZ0jJjKgl+KunBu#n#mGhuZqqmR+JTjs3lOU68EPTUQL6wNUO2kZdBAw0i/D0pakKT7QDlnEXQsAJh1q3w#n#s1PVInqgFFja3Y3tTSX/djE=#AIoirjXs9GOMKjt5H9gfS7xIo7ewnyKZvtcg9xrEhtL65BqSj8kcQ5Ey1vd4H7PQ#n#rJr6avAyiJIZzjdRT1a1zU/gh445MElG8m+IEIXv0NNvu9TCYV4VSfndTKfPvDds#n#36C1AdC6zQySGll43Y/mbJAoM+gFByp//tHlRp2sPeHahRmqQWNBUhct+hPV9pgX#n#GuTVlgROkonVyGJzZArFtte2dGXU5ncdi/PBvwAmo2HKIgOxzpFD9uuH1kisuTQ7#n#7vlLvJ3loQe9AvH4O7dnNPONDXLEd1nq6qsfproEQiyDmozJAXZ5l41brYonDxbk#n#uKYqh1asS/VU/iuvUcjIhPk=#eX17bJJTScjGsOChHCHtiB51QQJtETT5mtW3m5pa6WBGVuVE4rDt5lNNX9ElIA18#n#oC0MtS9Pml09mexpajQ/KsvLkY9+jhhRpa/kPR3lVBHfc26VZ+zRxwmVr2w5UQ1R#n#N3hpU4ry/jk8bY/JQOKrB/xHbd2x3jFG471pAWyGPv5+gzi9wpFjjKFXsTtscNIh#n#cx3rhLxYoUL7uUb/Hon853G/h7ljpDojm6TJbanJkyPbvdhJ6HTFv7n6ys98/Nc4#n#qD9evfgNRkqnjj25EINrpalk5FYAzRUPZDpMsb8CT75kPAqyxjGHjUeEKuoPgnK0#n#chVBqiyejT5qcN9rv3ocYQ==#D+LCkCK5TMRobqQO/D62e4xUJ3OFgOr98MjfyHjG0sh0qHg1CeFOcxURtZj7vKVa#n#XkktR9uKgViYES68/QDwxdlqO6rXVMwS3KC9A64kB42u9fxgwHwYw2fNwK6vsBTr#n#VhV8A2dqJwWTfbuyP9Py0KlnqARdhAxlWTCcLyUeF2fw0wo8giZE8IHgdCZQJrAQ#n#uf7Pu4ITyp4DDmu00AeNjE/r8jNuu099X4PwCqddM2v+05OcnBzDUhX9E6tpJDus#n#4ScJMZHLilRdVuOLxMibCR7sJioHR3ovZrzSERDJF1PJJVH5lvlrV6frig8XUCGn#n#+EI37AcG+4B8b2pC9avFrA==#KEPOKjeQilqjox3IHC/fxpUMXyPtwEg5pdbeoLa3nTg9r6xgJZKGh6pv3Q+Iupn+#n#WFAk6yXoUhsnSsV7jM5xmcEVEoBI0buxzAhJiev8AUOqx0ald8c1qc8EyKowm0UN#n#v/qx02fKLE+/TWg0FJzmyCmgI522iFcfKWLxt2KqPWj7a3McWYHYMWSiE8fWPIqP#n#5kLgxkxvE8iK0Gxn7JphHwHgcCyNZiG3oW2hRlzhUF3gefw0fF6o80RrjkSz8jD2#n#3gLpJTstKRuQc4ggJ6cYBGsoHlI9FBP20xJp4bcXjyBVqU4EB3xQ51vN5PtjvLiE#n#x8jY/vGX7Lg7modYMf4lXQ==#AJKFKJxb2IqFh/zecaF4fLSbiLY3kM1syQG1+rquvnlX8B6B1FhQm6B5u2KMWzJi#n#TSmqVqzWxIng16qzoPWX5gx9R7Kdf8CmYY/BWTKu63VpxNKTIsmhEiNFtmL8kndH#n#AdiziptvKdZckUJw6t8IuEi13w2kDFlDRZkamkkU9uRYot4L2ysrurWn1TBU5uLS#n#amZolZ87Oy6r3rKkIbyZsgU8+SnJbmDsXxCSWbaJ0kEDqlPvb8c58nvhIxSaYkpN#n#wcn7qO0TIGae019OnWmppvySiAc2qNDBOGgycEyvgBdp8uwv3rRbjyNltmnF9JS8#n#VgNThA2rvlGg14IqnftDZRQ=#AKzHmxdJJ0fVSS9E46BO5uDRThwtmYZ1PsB/LVkvR9gs5N4BeowNz2k0wrMs+lIK#n#S9upXQ6h4pSTYpL0Oy50OmOTD56Qfz/aVyE4Mv3Vv+a2WXLXL63jOKxOuIeKmuIf#n#D15mR3zA6AtlcWr6tWcYAcR804yrr9Ipdnv2jbHFujrxCk2gGhfSBnt4+l+WgXWj#n#q/yhO8xW6yx11VsV2dEUIeo2HWoHZh05k617gQg/V1XNOZQFRcuJMggwDO3C3GcT#n#vcTYdlaLWa4uVVyL9tQ+e7pvvLtdhiyay/lwmP/9XbJJt04XQ9x/ujPZ01Rl536R#n#sVCHG2hhuNLo7RLIgbU32jY=#dp5QLBrhDbkXHBOulYc6K3zqQ6/5cUq6lFxswNi93HDV2kjRbQ01UbEbjyY2z2VW#n#shK7a76hNrHPVD3v6UGbpzXt4HYdnH1g0/v2/OplHkZbNWOq834f+irhHx6xYDBH#n#pkNA0uFecllao4SPulNONx7THott3pqdQtradiUEL0XmFfSLbXGfgaDxMyIYcXZP#n#LWluvZrrJhb4ATDtaA12dCIidg0+fCpleu7HJigB1nP5+bZpgXLbgupPKjV/YPX0#n#ZQM4HGjOFH0ryblesA64y6A0pM9k2jxwGnOJZTIUlL6GuISHsM9+fYr6vm3k15Gv#n#JYbmGkmBJesPgwkY13Q8cw==#JU10mHLqad0fQnuwJ2iBJI1trKSvXvnTnzu7AU2b8XTr05YN13+O+6MNdKIi2yLj#n#W0C//5Lw+4nHJ4XVbm6L1VL8a/BiLHD5sxGQECaaU1WBzt+8oFZocKrhgm5TVvKH#n#BEMNccCcXTY38RYqTlrobYZY/PnEAniT/jZXKHgFiAykb7nfyfbqX3fmL3Fvjg1F#n#X/er0BceCII7ylOlbERHP7DBlx1yS5qxvv3TJzGJxYsyVJioBUJ/NRgqTt6lz7ol#n#oQAsPdouAEY9SCS1W2Q7/8C1+9DQpdm9jQiLlVpQ3yFql5CLQuTa9UCBDGG8Jctn#n#Tgbk/QsV8yPZLHjGMPn1gg==#AIn7zJ6vBGgK1Bay8VMOp/VJY7yaIIrv4W5+lKVv1SvsXLqqjroNzowqws9FIYwD#n#1K0qF91x8tTPXPFbqPvcP1hWMEtIlFXdPtrocan0GU+n12KCOKJjF20M6h2KI709#n#VLEHS9sc43Rz8DDU6EpZ1CmhWcKys7+8CNw4YS5kRtEPhdMPCGzm9pCr+5zMHxWh#n#D52Qzz18PEWblqMOygCE/1U1OaCi/+e5bKFozzDxW+xhLY+KWE2gt6XOo/kEevJi#n#VXhn5g57ll9sLSFX3GuI1sf3SHioSZviKRrcfmTcVoFeNM99MyWZgZQ0kWUCwc1W#n#4IYsqXNhHr5s17ovtcsKxIk=#AJnVkOl5xWCfbJis4vp2Dio1lf2OmHAPVgidXVWcwHNlboZhuckxHDGARNXQU0ZI#n#vpxNyJ4hJ0wwVyN8WsPxPFbPDR+mQtw5fLleOBnGcBq8ER64JoM2WXsUzlneTNS+#n#Hlc5qTGE0L9bcoFmC3nKkCdrOQRg8YLQtEc7rSYxHNmo33Lf2P3PL8+O82pBZZKp#n#sE/Y1dMJsPYoRtbUMZmOls4VfZclyXe+HhDvk82QXD21JD6fQEuzILHLO+C2geGe#n#ZucQ7MwIdCRmPg3ZQUlyEQAuKnoFYUUWX86cbX48DcrolNB+6b9djD8srBQZBxsi#n#MPEgp89WaU8YUEqkf4QcvbQ=#H2M1WFCGA3OcLJNJXsbU1VeaVlRQ6RhcSZSIKl1rxobMAevtmL+WG7kelnd773nC#n#5Pgk4kWCEGGqpVe7e2I9Frd0gf5PMSUjjtQyJDktE9V2aHWurBYaBYnnOoEKlHQI#n#TJPDS5MBFfdIEcDhW78vxOWuSF37sRRpC1wImuSthW8oJM1TBNqUrtCvQNDD3+zF#n#Ugs7qaJjy50AhIFBmkma+umCEFKCqZUeL5amfcWmKjbn4Gq9JRTO2Nb30/gLso6z#n#9ROJFg5D5nqIXT/VwUu9fGd5zEleJXmSwXO8IifOkpj8kUEsGuejczkkC4krwoo8#n#Gq0sxQ+/ghp0hLQcWgThIg==#JD9CfFO872T+Y6P5/TUhkCX03tnf3vLTUQm4/mBSGdwZynU3Koq4uyxC4BkgKFHv#n#eOH6NxMzoJUMgcvVT0yYRDrs9uPko8uo2jmoPNGUhFVtfEGz9DZaWXga3/x1G13C#n#Ap0pJ0hiGa4RVhZjNlqyDrqsjSa3xYLjNfDMhOtBuDZbUghHeE+02fE7cx9alAPi#n#VL3uQUg3s9odqO2H40u8srD2HXTSmVGG2cceXai2CmWDZ2dE7U7st8K3wBiouNEZ#n#D9Ub+wtZVhqeGenBtz+Jh/WpQ52l8o8r2Grja8pW9BYbIButwBuUYg/zHOijj1ut#n#kbOMOKhUuVO4m0rMThryGg==#,VfXhh8FBRpERmSF50pXY7QSb5fX98oACNE6oJK3W2sSRrnc0IjjfcL5MQocm3qpINDRcyUftDIdv8jc5BFDrK/prLV2ajfi7v9Vt74OG3y5R3t+veC4SCB4Mn2X3UVfLLNs6qe1tvvokRQMjik2lgZGlBL1oJWDJb0wO5mfELyjg7IejpH+mqWwfqTz2jz5FU+M0web29Uvh0RYOqGOVsW6v8DcqzobfpxY7O5t8DYi/y6xkdBOEkNsAUqDBtvFt0FliWdO3+okyxqyuQ3Yhz0mHc5F8o9FdWU8zBfdGNSWcJuPNxhjsi5E0zt6XfB+u1CNEfpdmdggH65Q+K44cew==,128a3a00-6e56-4e07-84ed-eb9eb7006431,parlamentary,1375767740182,-----BEGIN CERTIFICATE-----#r##n#MIIDcTCCAlmgAwIBAgIEfXpA0zANBgkqhkiG9w0BAQUFADBaMQ0wCwYDVQQKDART#r##n#dGF0MQswCQYDVQQGEwJOTzEPMA0GA1UEAwwGNzMwMDcxMSswKQYDVQQLDCJLb21t#r##n#dW5hbC0gb2cgcmVnaW9uYWxkZXBhcnRlbWVudGV0MB4XDTEyMDgwNjA1MjkzM1oX#r##n#DTI1MDgwNjA1MjkzM1owgYkxDDAKBgNVBAoMA09yZzEOMAwGA1UECAwFU3RhdGUx#r##n#EDAOBgNVBAYTB0NvdW50cnkxSDBGBgNVBAMMP1ZvdGVyX0tJSE05UG5MZUVmaHFM#r##n#cFhLeDR3VGhiTktBRmllVjMzT0N4UTdadGc3WW89X1NpZ25fXzczMDA3MTENMAsG#r##n#A1UECwwEVW5pdDCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAKW3Qe1b#r##n#FnaJmbX8vtxztdnALixCwAFUOxGDF5xkMxHKfQmZ4hUu+6+3EEg1PRhDqWvcmGGm#r##n#JTgXIanxDNWW6ACo+dOJZT8fP/ReiGGkOtE7qdbPq1Fp2hYCWg0ruRw4hHvj9Nb/#r##n#kMP0LgZxPZcaYcQ2LNXxsAeo8V+HEKnrcLB9SR7C5UeeGolDCJPxFNGtJ3FkKjgo#r##n#/lZEiYHfVDUTsGrOtQuXzcPvy5gA9Ak/4fKaopM6YwrYr/qN6UuRIGVSPPjLRp22#r##n#XKfZGlwDZhLGu50ETpGhrRnRpXkQEA/t2CrNN4Z6gHS17CGpEvUljMwCWCJK40p1#r##n#LRJMGufn2L9WwVUCAwEAAaMPMA0wCwYDVR0PBAQDAgbAMA0GCSqGSIb3DQEBBQUA#r##n#A4IBAQBC+3TX0zPybGhwBASow/B0lpsvDbIssAg18esfRjq3c196hD4XBG0kEq60#r##n#k3oOct6PyQ4wS6GMSxru4V69chWnbj4XsHbWzzPfEOS4J6Lbbl2YY6gDEZorVKHK#r##n#5EPZFaQ74OtrdyAMPwOLUMjAkN5qVhqyAbXVwxjfBcp3seoRlkdHz9q47fz1mOCh#r##n#ghPUwK45yo/ZdjM4q/gC7iPls/hCoNL51jlSFIGhYIa/ZannkyGORnrCHjj5aB/J#r##n#GoqH8LqI2PeeP/QnNQ3B0nKWWMQyzoXUKH6DwBYKuSp3SkDhagIGjPUKMEJAJ2Lw#r##n#eHLVxfFAVFwJFj6BsxqyNv2YPku3#r##n#-----END CERTIFICATE-----,730071.47.07.0716.071600.0001,000007,01,730071,01015224726,CHANNEL_ID_UNCONTROLLED,1375767759507,MTQ1NzkwMjc3OTM3ODEyMTY0NjA5MDY2MTE0NTM1Nzk3NDE1OTkwNzE2NTQyMzkyODk0MTQ3NjAxNzI3Njc5ODI4NjQ5MzIxMDY1NTh8MjYzMDg3OTI0MzAwODYyODk2Mjk0NDM1MTM0Njc4MDUyMDg0NDQ1OTgzNDg4MDUyMDAzOTI2NDA1MDY5NDA0ODQ2ODg0MjcyOTA5MjY2MzQwODE3NjQyMDY1MTYyNzM3MDYwNDAyNjg2MzY2NDM2ODI5MzMyNTExODQ1OTQ2MTU4MzgyMDk5NjYwMDE2Njk5MjQ0MzE2MjQwMzk5Njc5NDg0MjY5NjcyMDYxNzQ4OTA0MTE4MDM5NjkwMDgxNjM5NTQyMDQwMjEzMzY5NzQ4NDYwMTY2Njc3MjYzODQ2MDU3ODQzODQ1NDgxMTkxOTUyNjE1NDY5ODc2MDkxOTE1MzUxNzI3NjkyMjE0MDI1OTE3MjExNTc1ODIyNDc0Njg2NTg1MDA4NTU2OTQ1NzA1NDU2MTE2MTI5MzU5OTM1NDYwNjM5NzA3NzA0NzM2MjQ1NjQ1NzkxNDkyMjA5MzYxNDI0NTc3NjQ1NTg1NjI0NjE3OTAwNDA5NTEzNzczODUxNTYwNjMzMjg0NTk3Nzg0MjE3MDQ5NDEwNjQ3MzIxMDgzODkzOTkyMjU0NjMyODM4MDExNTk3NTEyODQ2MzY2OTUxNjE4NzcyODU3Mjg2MTgzOTUxNjc1ODI5NjYxNjI1Nzc4OTA1ODIzNTczMDIzNDU0Nzg2MjIzOTMzNDE0MTE3NjAzMDIyNTQ3NTYyMDM3NzAyMzQ4NzI3OTk2ODIyNzI5MzE1ODY4MTg1MzQ3NTU5MDk0NTk0NTEwNTY4Nzk0ODcwMTIwMjUxMTkyNjc4ODk4ODQ1NDAxNDkyMDc2MzkwODgxNDc5MXxudWxs";
    private VotingReceiptCollisionIncident incident;

    /**
    * Creates an incident to test against.
    */
    @BeforeMethod
    public void createVotingReceiptCollisionIncident() {
        incident = new VotingReceiptCollisionIncident(new VotingReceipt(
                    COLLIDING_VOTING_RECEIPT),
                new EncryptedVote(FIRST_ENCRYPTED_VOTE_FOR_RECEIPT_COLLISION),
                new EncryptedVote(SECOND_ENCRYPTED_VOTE_FOR_RECEIPT_COLLISION));
    }

    /**
     * Verifies that an incident is equal to itself.
     */
    @Test(groups = "VotingReceiptCollisionIncidentCreated")
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
     * Verifies that the incident is equal to another incident with the same
     * voting receipt and encrypted votes.
     */
    @Test
    public void mustBeEqualToAnotherVotingReceiptCollisionIncidentWithTheSameVotingReceiptAndEncryptedVotes() {
        assertEquals(incident,
            new VotingReceiptCollisionIncident(
                new VotingReceipt(COLLIDING_VOTING_RECEIPT),
                new EncryptedVote(FIRST_ENCRYPTED_VOTE_FOR_RECEIPT_COLLISION),
                new EncryptedVote(SECOND_ENCRYPTED_VOTE_FOR_RECEIPT_COLLISION)));
    }

    /**
     * Verifies that the incident has the same hashCode as another incident with
     * the same voting receipt and encrypted votes.
     */
    @Test
    public void mustHaveSameHashCodeAsAnotherVotingReceiptCollisionIncidentWithTheSameVotingReceiptAndEncryptedVotes() {
        assertEquals(incident.hashCode(),
            new VotingReceiptCollisionIncident(
                new VotingReceipt(COLLIDING_VOTING_RECEIPT),
                new EncryptedVote(FIRST_ENCRYPTED_VOTE_FOR_RECEIPT_COLLISION),
                new EncryptedVote(SECOND_ENCRYPTED_VOTE_FOR_RECEIPT_COLLISION)).hashCode());
    }

    /**
     * Verifies that the incident is equal to another incident with the same
     * voting receipt and the same encrypted votes in reversed order.
     */
    @Test
    public void mustBeEqualToAnotherVotingReceiptCollisionIncidentWithTheSameVotingReceiptAndEncryptedVotesInReversedOrder() {
        assertEquals(incident,
            new VotingReceiptCollisionIncident(
                new VotingReceipt(COLLIDING_VOTING_RECEIPT),
                new EncryptedVote(SECOND_ENCRYPTED_VOTE_FOR_RECEIPT_COLLISION),
                new EncryptedVote(FIRST_ENCRYPTED_VOTE_FOR_RECEIPT_COLLISION)));
    }

    /**
     * Verifies that the incident has the same hashCode as another incident with
     * the same voting receipt and the same encrypted votes in reversed order.
     */
    @Test
    public void mustHaveSameHashCodeAsAnotherVotingReceiptCollisionIncidentWithTheSameVotingReceiptAndEncryptedVotesInReversedOrder() {
        assertEquals(incident.hashCode(),
            new VotingReceiptCollisionIncident(
                new VotingReceipt(COLLIDING_VOTING_RECEIPT),
                new EncryptedVote(SECOND_ENCRYPTED_VOTE_FOR_RECEIPT_COLLISION),
                new EncryptedVote(FIRST_ENCRYPTED_VOTE_FOR_RECEIPT_COLLISION)).hashCode());
    }

    /**
     * Verifies that the incident is not equal to another incident with another
     * voting receipt.
     */
    @Test
    public void mustNotBeEqualToAnotherVotingReceiptCollisionIncidentWithAnotherVotingReceipt() {
        assertFalse(incident.equals(
                new VotingReceiptCollisionIncident(
                    new VotingReceipt(OTHER_VOTING_RECEIPT),
                    new EncryptedVote(
                        FIRST_ENCRYPTED_VOTE_FOR_RECEIPT_COLLISION),
                    new EncryptedVote(
                        SECOND_ENCRYPTED_VOTE_FOR_RECEIPT_COLLISION))));
    }

    /**
     * Verifies that the incident doesn't have the same hashCode as another incident with
     * the another voting receipt.
     */
    @Test
    public void mustNotHaveSameHashCodeAsAnotherVotingReceiptCollisionIncidentWithAnotherVotingReceipt() {
        assertFalse(incident.hashCode() == new VotingReceiptCollisionIncident(
                new VotingReceipt(OTHER_VOTING_RECEIPT),
                new EncryptedVote(FIRST_ENCRYPTED_VOTE_FOR_RECEIPT_COLLISION),
                new EncryptedVote(SECOND_ENCRYPTED_VOTE_FOR_RECEIPT_COLLISION)).hashCode());
    }

    /**
     * Verifies that the incident is not equal to another incident with other
     * encrypted votes.
     */
    @Test
    public void mustNotBeEqualToAnotherVotingReceiptCollisionIncidentWithAnotherEncryptedVote() {
        assertFalse(incident.equals(
                new VotingReceiptCollisionIncident(
                    new VotingReceipt(COLLIDING_VOTING_RECEIPT),
                    new EncryptedVote(OTHER_ENCRYPTED_VOTE),
                    new EncryptedVote(
                        SECOND_ENCRYPTED_VOTE_FOR_RECEIPT_COLLISION))));
    }

    /**
     * Verifies that the incident doesn't have the same hashCode as another incident with
     * other encrypted votes.
     */
    @Test
    public void mustNotHaveSameHashCodeAsAnotherVotingReceiptCollisionIncidentWithAnotherEncryptedVote() {
        assertFalse(incident.hashCode() == new VotingReceiptCollisionIncident(
                new VotingReceipt(COLLIDING_VOTING_RECEIPT),
                new EncryptedVote(OTHER_ENCRYPTED_VOTE),
                new EncryptedVote(SECOND_ENCRYPTED_VOTE_FOR_RECEIPT_COLLISION)).hashCode());
    }

    /**
     * Verifies that toString produced a comma-separated line with the correct
     * values.
     */
    @Test
    public void toStringMustBeCommaSeparatedline() {
        assertEquals(incident.toString(),
            "VotingReceiptCollisionIncident," + VOTING_RECEIPT_COLLISION + "," +
            FIRST_ENCRYPTED_VOTE_UUID + "," + SECOND_ENCRYPTED_VOTE_UUID);
    }
}
