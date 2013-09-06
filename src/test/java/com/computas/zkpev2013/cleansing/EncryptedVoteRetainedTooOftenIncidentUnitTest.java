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
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * Unit tests on EncryptedVoteRetainedTooOftenIncident.
 */
public class EncryptedVoteRetainedTooOftenIncidentUnitTest {
    private static final int EXPECTED_NO_OF_MATCHES = 8;
    private static final int ACTUAL_NO_OF_MATCHES = 49;
    private static final int OTHER_NO_OF_MATCHES = 25;
    private static final String COMMA = ",";
    private static final String GIVEN_UUID = "8a84806e40521c6d01405230956701e7";
    private static final String OTHER_UUID = "8a84806e40521c6d01405230956701e8";
    private static final String GIVEN_AUTH_TOKEN = "rO0ABXNyADtjb20uc2N5dGwuZXZvdGUucHJvdG9jb2wuaW50ZWdyYXRpb24udm90aW5nLm1vZGVsLkF1dGhUb2tlbvz+Wz5pky7LAgAWSQAhX2FkdmFuY2VPZkV4cGlyYXRpb25UaW1lSW5NaW51dGVzSQAaX2FmdGVyQ2xvc2luZ1RpbWVJblNlY29uZHNKAAVfYXNJZEkAGF9leHBpcmF0aW9uVGltZUluTWludXRlc0oAA190c1sAD19hdXRoU2VydmljZVNpZ3QAAltCTAAJX2NvbnRlc3RzdAAQTGphdmEvdXRpbC9MaXN0O0wAGl9jb250cm9sbGVkUmV0dXJuY29kZXNUeXBldAASTGphdmEvbGFuZy9TdHJpbmc7TAAQX2VsZWN0aW9uRXZlbnRJZHEAfgADTAALX2VsZWN0aW9uSWRxAH4AA0wAA19pZHEAfgADTAAJX2ludFRva2VudABBTGNvbS9zY3l0bC9ldm90ZS9wcm90b2NvbC9pbnRlZ3JhdGlvbi92b3RpbmcvbW9kZWwvSW50ZXJuYWxUb2tlbjtMABdfaW50VG9rZW5FbnRpdHlQcm92aWRlcnEAfgADTAADX2lwcQB+AANMAA1fcG9sbGluZ1BsYWNldABATGNvbS9zY3l0bC9ldm90ZS9wcm90b2NvbC9pbnRlZ3JhdGlvbi92b3RpbmcvbW9kZWwvUG9sbGluZ1BsYWNlO0wAHF91bmNvbnRyb2xsZWRSZXR1cm5jb2Rlc1R5cGVxAH4AA0wACl92b3RlckFyZWFxAH4AA0wADF92b3RlckNvbW1JZHEAfgADTAAPX3ZvdGVyRmlyc3ROYW1lcQB+AANMABBfdm90ZXJJZGVudGlmaWVydABDTGNvbS9zY3l0bC9ldm90ZS9wcm90b2NvbC9pbnRlZ3JhdGlvbi92b3RpbmcvbW9kZWwvVm90ZXJJZGVudGlmaWVyO0wADl92b3Rlckxhc3ROYW1lcQB+AANMAAxfdm90aW5nTW9kZXNxAH4AAnhwAAAABQAAAlgAAAAAAAAIAAAAAB4AAAFAUjBdMHVyAAJbQqzzF/gGCFTgAgAAeHAAAAEArogXUbdbFSXDVt8wB+v4IEjgReVLt+OThf2GFQZ+R9Ej3sPPhHb3F0bm8oCul25cqc8MzTU7KsA1KlsUV6BtNIn5/PTKjde59RSdV/UnQ/ZZILohlguWiPR74z82YH0fjfwCrz8Yov6ZyqhyNWFsHl/Sn6KZYX0EuOdLsIhi5HkompvJF/7UejkXpb4zzTA/lxQcVNx3Gkd0OnHTLRZgPDdCS1JKj/CJ0kJSYNR1PcP9fc3NasqHJUUniQRwisjmJsotpiC6uM/t1pE0UDGHr0SiJDgXu3K2J1E1blUTSDem0hVQlP2TniLBTFkFEOK6CzpJWnDSzol/WJGihNQfe3NyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAABdwQAAAABc3IAQGNvbS5zY3l0bC5ldm90ZS5wcm90b2NvbC5pbnRlZ3JhdGlvbi52b3RpbmcubW9kZWwuQWxsb3dlZENvbnRlc3QiWqLrfd15KQIAA0wACl9jb250ZXN0SWRxAH4AA0wAC19lbGVjdGlvbklkcQB+AANMAAZfdm90ZWRxAH4AA3hwdAAGMDAwMDE4dAACMDF0AAEweHQABlNDUkVFTnQABjczMDA3MXQAA2lkNXQAJDllZDFlOGRlLTdiNjgtNDhlMC1iNWMyLTlmN2ZhMzM1NWYyMXNyADxjb20uc2N5dGwuZXZvdGUucHJvdG9jb2wuaW50ZWdyYXRpb24udm90aW5nLm1vZGVsLk1pbklEVG9rZW7g1pITYjnPpQIABEoAA190c0wAA19pZHEAfgADTAAFX2luZm9xAH4AA0wACF92b3RlcklkcQB+AAN4cAAAAUBSMFyLdAAkMTlhNGZkNzItMjYxZC00MWY3LTk5ODctZjIxMjMzNmMyZmVkdABrI1R1ZSBBdWcgMDYgMDc6NTM6NTUgQ0VTVCAyMDEzCnVzZXJJZD0wMTAxMjEzNDg3NAp0cz0xMzc1NzY4NDM1ODUxCmlkPTE5YTRmZDcyLTI2MWQtNDFmNy05OTg3LWYyMTIzMzZjMmZlZAp0AAswMTAxMjEzNDg3NHQADFVTRVJQQVNTV09SRHQACzEwLjAuMzIuMjQzc3IAPmNvbS5zY3l0bC5ldm90ZS5wcm90b2NvbC5pbnRlZ3JhdGlvbi52b3RpbmcubW9kZWwuUG9sbGluZ1BsYWNl1/fPlAehz24CAAdMAA1fYWRkcmVzc0xpbmUxcQB+AANMAA1fYWRkcmVzc0xpbmUycQB+AANMAA1fYWRkcmVzc0xpbmUzcQB+AANMAAVfbmFtZXEAfgADTAANX29wZW5pbmdIb3Vyc3EAfgACTAAJX3Bvc3RUb3ducQB+AANMAAtfcG9zdGFsQ29kZXEAfgADeHBwcHBwc3EAfgAKAAAAAHcEAAAAAHhwcHEAfgARdAAdNzMwMDcxLjQ3LjE4LjE4MjQuMTgyNDAwLjAwMDF0AAVmYWxzZXQAB1JhZ25hcnJzcgBBY29tLnNjeXRsLmV2b3RlLnByb3RvY29sLmludGVncmF0aW9uLnZvdGluZy5tb2RlbC5Wb3RlcklkZW50aWZpZXLKvqZX4QyCdgIAAkwACF9zcGFyZUlkcQB+AANMAAhfdm90ZXJJZHEAfgADeHB0AAswMTAxMjEzNDg3NHQACzAxMDEyMTM0ODc0dAARTG9cdTAwRjBiclx1MDBGM2tzcQB+AAoAAAABdwQAAAABc3IARGNvbS5zY3l0bC5ldm90ZS5wcm90b2NvbC5pbnRlZ3JhdGlvbi52b3RpbmcubW9kZWwuRWxlY3Rpb25Wb3RpbmdNb2RllMcp1WIDaxECAAJMAAtfZWxlY3Rpb25JZHEAfgADTAALX3ZvdGluZ01vZGVxAH4AA3hwdAACMDF0AAEyeA==";
    private static final String GIVEN_AUTH_TOKEN_ID = "9ed1e8de-7b68-48e0-b5c2-9f7fa3355f21";
    private static final String GIVEN__ENC_VOTE_OPT_IDS_PUBLIC_KEY_COMPONENT = "dVvO+laT6TnPk94mP47zbzpTeKCyD04Mb5YCCIT9mGkdvhIRzm1iOAYw5qSN2uCC#n#WzP5aVYD6u9sRpDW/l20cwIXNfLQ0/BOEm7Sr2gaTC5tAbb/xWxrTHvjT1si633d#n#+ZYntCf77xK7yQiq/IVluHHukzFDHl6ShW+c6LH+dSfuN4IhsbaIkHFVjqkVgUTO#n#H8lXEQGTZ/OykiC5/ELbNccrMRIPW8Ii79lRkCuIObswWpaYzC8bAyIXT1FrqFJd#n#xZ7c3t2tjOsBc+U28pwWB2OOHyb2NL7+gpj4VD2EBho0omYn45wlovvI1X/d/C6v#n#3d6r/9/VWUYj7j1Qp2g/Dg==";
    private static final String GIVEN_ENC_VOTE_OPT_IDS_MESSAGE_COMPONENT = "DMddolbmKzAFhQwmWH4MaFhlGKdbCZ1+MlYBPUTzkYFdAP8VF+UkmjGd305Ws/Kc#n#czuipMuO6ig8cTAQKDNuL5eBNouAMhE+qM9ga67KVy0foaDTCGZjQGtqgxzGqdvC#n#lC/Ak/tlMCFW5yLqkmhm9JffCaSgZFOap7trNm5DQPcaj3e5TS1Q/8Jw18RyKuXW#n#TYMN5z3G3rjCZ7RzGA/O/HVRNNxZ+4NKvYyq9eMkKGO+XA1ep73lgmsv7PI1ukQE#n#rVsWiCBPFvfy+I8D77IGI4C2PuVNlLhplpVMHUOGTfkG1GhuTvU/cRJk2TyfLnZb#n#e2YZoXTDb+GFK6Ngq+8NqQ==#Qha8a+OAfoloc7LeuqzrZ2sCh6NIFqo396iPvVaXSQ1gjsBTOUdWQCLLbJkUPJCT#n#B9ESGDW2ecZt8Gr9pwskhxiLj2fhxZf/+pGGyBasU106vxfbTROG9AnT38Po9BK/#n#inlkUUx3YcEZEcU2ZRfiIeA680fQHfB24yAGZCug/4aGNshxxbADICHbiEc75gfr#n#AqVVDDJ5srvVfJHKrSfEfCpfpB7lEFP39Mhs7qcoVax9nZEQ0UiXAt6FKr03mUuG#n#oVInjCA9hv1fTW3qLMnsfe3mzREQzkzhS87HQwYc8dzyNNelixjYiqIX8++ujIM5#n#1csrqC9+hZBH6Nu58V8GHA==#AJhk05QZoMHl8LskNv/xCBZRxuQdcuyASdblDvtj4ATTX0kyf2QiTJyLR06KHtFt#n#rm2ZAsajeq5aN4CedZRQXe/jtdhW/qmAFROaMM/0U/OLiaw+qemHImQXjGnDrGnk#n#k73RGsznvKYGUiRohU5dZqTYyCShqp1iShu/G7zUQc+0/RzxMERCNEKhKTeMNUC8#n#KbWQO6L2BYVhQAfImh2ne3OvqCQOD8Emqz12BeEOPfdUlruwCoLH4u7FZc5kHqks#n#m021HQ8VGRwmAQ5IAGJjI0lf675z5Ce8MBH+sxEMfSdDSN/+ICfnzq87gEBCsZuX#n#7LsCW++raNMWhtauzTHVU9g=#S8HVnPtZw9l9vQiSeHBcEX8AKg+n3BEjqs0IM86Zvi8QY0wtrtBmOlXK0724GkIg#n#THgaRll1shqFEk2vpjLT81aLWatP4N5N1O7F+naDXsm3gPqZ7VUD4cFzVfmvmHIk#n#l5p/ItqfFd0JujisXiigRKF/QaO9dGmFuhrIT11KeWtXbhSbdwXn8y64HcbB98rc#n#pMuiIjWBBwjqhMxsuDljsbtu6Nmbq5YX4ItxGsbgjBF82i+v1As+sCFJnvzU7KWB#n#7OWyfi9n9BoRcxwKbid4jgGRQYP6Rq9wSnVu3MlL1hWv9ihOXuNqgZV5taafY10s#n#Jtb9tD9nUq/DxksTPnsHsg==#AI3i741IT0bCDT1HKWoASybReFixmnj9baENpcOmjk65CG89DutZ7dVaIUvzLfme#n#dP1j7hB5Tf4t404bSz7nyUFUOJ1Zj7mnsXzieLDMjj/TCoJA5LKh1Zd6DZpY6pl/#n#/iUnmxfV3smkVUi0ASXBH41qneu1LnAG6Q866kHMkRP0mz1bBESEeC/64Vk4oKkB#n#6Juq1bzlNvzmhA41HjHv4eDwGZHbUmmrAoYgTxQm7gYsYHRYSMn2NZF0L2NS73q1#n#+ZRn7rWbWwMtC2r/D/kTpuaEZgsX6LZdKynDdAsudDyyDl24rV6YVg9TUOEqDp4d#n#ObrjDefPlfwqAlkUY+3vxQ==#Aud3rd1dN+bIf6Y7ZhBPIMmkxJ7hDwe2HMZRAlSF609fC9txDS2iHrXaLsyqnuCL#n#FKMNCKGn0bPTCu5YfUVq8QYYrBfCYNGxqYkKtqv82p2/sGMdqMascmQNHXxr2eFO#n#cMKq0qHFLcZCyuzBbUGMt55MKTLW348BTvBC3kMTPvhrU94tKsll+2E718hvxFAu#n#poZeodIJeBi29CS0QH3g3WOA9HsQKQRrunH75STRErTDG2XaY6+ekL6XgdIzxrPL#n#tXYk8Bc6lkuSqc9VfUawTwhVDttqaIqKK2uzFYzrTbp6tdjgTIz4E/pwUPOpTSRC#n#F63LMXLPQzCzb6c7E3DM9A==#AK8cSOQemjcguU4PsHSKov/d6/t9r2fPg0zuGJuKpz7Q4eBApFrly9blw+iq5v4W#n#NjlXI0Pcp7ur6vKr2yhZsIabtTbYMHbU4a34l2hiGMwpYiyV8SNC1C3lCodjDIp+#n#/Wx4OmZhtz85F3/f/TB8BjL8cUJ9synUtiw010+jjIWVOaS7uRqE1SXRKI12qboC#n#o6Wv2rlZ/3ReskcGD8E2IMKeGs2CSkLb+id/dmj+6aK3KpmNhAXEo5WOIK+AGIXr#n#Va6wZ3ym0DPUItMI7DkvT+nxELH0No3ar0LGcdVG0q25Z336MKWBqVp/ntiNFTdA#n#+a2TRudkCzErjANDc1c7SeA=#ZxYCEj6oduvP7QOO/sUfnVirSIvPhbX/cZdH8rOE/ASMk81cjQN1KzncFL5kNb8z#n#wqII/OxGe/L8evIYzNi3WnCoEdJ2iLAi+G7srOc/yvVn6hekOdi2KHiXV0I/L4RA#n#qFkzUQEydG4gsKq44WfM9m6vcroQiTfK5Ldc7hr9jy4gmy9OJ4/i2HFGEOQ+2IeI#n#0PDrlBGQZmle/wIQ1GnAPti5RXGel36PvS8qkaMQ6aXe4GthLBnUji1SUGzuIeUS#n#2mTO7g9Rwd7Ab8sWTXtLraJWKnyhXeWsyGaHENJr5df1Hq1EvI71hc47zrMBVRSS#n#P2RaOPNO0QhGbTkFyYVmfg==#ALhNvDDk9Nho6p49TQOXXUfEAeFuFNJAQPk8JmJSgpK2nmYjaUtwvtpxqcPwqAXK#n#tZ1fOJG2NrucFxXDWhDCp86bcKf0DiJ/nOHggxG/8pysQLv45aWLSyi+7Ln2XKmM#n#guKF+emWMQzlTQxqSt2+a6Ov3WmyO1xoZpLg2OLoRbh/sDxTAfs0Eyj3gKeLit1P#n#4nWh1rSpAufflTVdH6GtF3yiB29D7PvRn1UwriOyc62Uj1n6TGKXa2ka5XZM0JEw#n#eK/BYqut/noKseltqodnRYm/P7egnFg+wh+EdK8gtln3XnTjZ6Sdak35ltqry07C#n#PLr84DdwyPxA+KY3rgvR0w==#AJjMVa27cK55EoqQUNaVZsjkOkVXV1svrue2xNBdhYtg9R1MqkQ2ugry2enCe28f#n#BOFjFCP66j8IIqnKWB+3G/T47RLLSv6ipbsCCUJ+UojGcSzGe82K2WT5dEpnioyK#n#RnDJSKHqcfO3xzFn01+QkLUCT4Dz/NqHgtiyu7i4dMlxZ32MbKk+s0drGmla8ryG#n#5wv2s+sU8qqvwzflCKOcTzNK+iVf+VS4GEwUOZ+T/X4lppPl8FSIOq7JJw3rld7t#n#cPAAOKBTb3BPGeMHuGDTojxBYAg8/VJx3oZI/i52kZdljAnRubiArjX7fLQc9q56#n#csA40e8tQOV9kCn0MVL67WE=#YpnsOWy5jLOuwfCH44Zhd30cjdNdQRdmp/51LqgT9xWeFLvweYJ33/GRgl0f4NRh#n#rtPj8fDMMAbKEHwQwy/ABLXjSfno/LxbdUgMD6ynOJQPORkMnngC1NrxTRerG56t#n#C3qbOssR2+OAqPgXOiPYi5YIqurYiJYo9B3dfkCqsmUO6SHBtbsv9cCBKxMacv/K#n#RErJvFb3tSRavQWVWeCaVaO1xzz20rk1svd83qGIeqh8+FmFY/83eefBxTg2Wd16#n#EA61WyTvB1Uh+qF0TbBxK9EBVZvXJCv4S4ZLaebNwqJQyElyyeGgW4weFL1livo5#n#lu0xjso/2GNFPg8ro8Wuxg==#aDZfTztaC7xe490EeVUHUn+JtwLn+rL+cZa76ccrkfL2WNydhRdYe6UedXO9ZIqE#n#Avn3+CijY3n87HVrp6T1CbfMmf14WuJ2G8kl0yzU8nYvjIQfGtzkhatDN2xXrYEq#n#fW2gjMngbdMhjbP+2m6536NFAle+szl33Kbmz24Yfv0C0LSfSrXi6Byf9uG3k74g#n#SGtsgEArWrfIXVwymTiC4Wrjc3e4GgmzCYmXTxgcdVCbUp1p4si9NyhD2eWNReeZ#n#sQ1KyUwcQynd4JeR+onIakLcnKZUWNL7hS0KyNgfV+6f+7JCTX6PO5AvuQida7vd#n#n2LXlHypvvcYWSlQAYvQGg==#L9g8oPhGncg4K6ByI8v4Jsl40nl1nv1Y0pculKCBIFYsaqeofIAga/Fhn3TQ00t2#n#EBKLPGBwUJYMS/WWUz63MPQZlsi+LV9Ub7d4JI3ZvLTgZb743dg9HWdFpZGvtJJf#n#VdVPsXN4j1ZKvCUKnKOj/7PNwbdBlG1VIqfbEePE5aRCZpdq7B6k4etAfleujALr#n#TuzPL9b+WxF+lzlgEwk2AhvF40TqOyikTUvGpAM2GFlC38/JR0/BWOlgZLVrjT0a#n#W/IhR1eh3Y0n4KhjbWH8qUMasuu2svmDOrrrOWzpvVX6DOTmV4Ixfb0vJe1Hflvq#n#4BumdDNv12hEagkPJp5TkA==#JOgsHEfnKzmDU4ZFgm3ReArVl3yCwWxoyhheW5CQ5RejoR/FkvO8Y+giEQZLBdJl#n#7VC8mMWEj2lN2rRftHfYr3BcQD8lhDvvVrvkEOkEUiqZ6pJy/66iVRUL6hHbJtCS#n#vTaWabBk/kZfpeAtVMkUlWZLIr6UTC5um4SByYtVmlaTa/9BbT3bZN3Rd0rDjCZ6#n#w0rt1B4A1P0jOisHD2XETc+aTo3CV5bfFBc7Mjb7uZswtmSQfCfxByladZ0rZ5SL#n#34x7G5DX+bcUuhJyS2bzZddT6QLc4oUtlXvhe3FG1dc0E9/stv/mMNKwnKuoDytk#n#4/ButnCQfV3WssPnQcyIug==#AIay8TD4bm+2pp3i+G755qej5PLQKeWr6Em7xhNwdErNUTi8mNiv3AcgrB6Xny5R#n#ww0OWY7ePOsAzIsssx9lyfh2gBDLMCPjNPxJbPcT9E4RWESxYHyUQX+UiyRTk4iE#n#vH9Inizz+ixbLpsNJtBhzFxinmJAKEO74ByIXMeWUGLMQVLPUSyQcxhibHXPz6OR#n#ybeDrjpWPvKVYl2XBsoL3FiZyHFOEbNtCU0hGV2ykdf0UhePJ3TWcuBb8Zd0S/k6#n#th8mf33dmYqLDk7v8MeKn3QQrC57+WXtMlGojD3vSQ2SCAvhyu3FKlb14kC6VoSU#n#mMToRpOitzLBGs15HcUCxac=#";
    private static final String GIVEN_ENC_VOTE_SIG = "NIxcfcFlQjgRupRqkuD/KSCcWynBEfPl9+/RzqhLLITWuzT/1hCcyMmLC12kY09trKbz/T9ShtKoFMlmCW3OQ/uBZBjGyY/B256C9gJVnAfSUDSWp/L9eLCdzyuzuAJV6kq8w4F7qpD9gqE8DAAkLaEY62gJeIttH4q5jPDfYtONI3NgsXUunIn1/LVx9Kmkc81t/x1wiq/6j+nuH2/grcPoMWnaxo/Ptzwag91xY63g1yVeq+69mXdcA2+zOlzJHGrItVMW1zG2Y6hPRJmMzq2GckavGge3sacB5hwcd0JouHnEEm0Xwq5TND5aSz0sGXw+AxVWtLHIG5NRH7eRvw==";
    private static final String GIVEN_INTERNAL_AUTH_TOKEN_ID = "19a4fd72-261d-41f7-9987-f212336c2fed";
    private static final String GIVEN_ELECTION_TYPE = "parlamentary";
    private static final long GIVEN_VOTE_TIMESTAMP = 1375768436016L;
    private static final String GIVEN_VOTER_CERTIFICATE = "-----BEGIN CERTIFICATE-----#r##n#MIIDcTCCAlmgAwIBAgIEIgt0CjANBgkqhkiG9w0BAQUFADBaMQ0wCwYDVQQKDART#r##n#dGF0MQswCQYDVQQGEwJOTzEPMA0GA1UEAwwGNzMwMDcxMSswKQYDVQQLDCJLb21t#r##n#dW5hbC0gb2cgcmVnaW9uYWxkZXBhcnRlbWVudGV0MB4XDTEyMDgwNjA1MzEwMloX#r##n#DTI1MDgwNjA1MzEwMlowgYkxDDAKBgNVBAoMA09yZzEOMAwGA1UECAwFU3RhdGUx#r##n#EDAOBgNVBAYTB0NvdW50cnkxSDBGBgNVBAMMP1ZvdGVyX2NkMGVzbFMydEQwZ2pP#r##n#MERnZnhCYzdsNFlFZjlkZ1NOYW1mb205bmlKS009X1NpZ25fXzczMDA3MTENMAsG#r##n#A1UECwwEVW5pdDCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAJCg4PuQ#r##n#87EC6968a9sSkJ/ubKG/Bwi8XEX+OH7fhYH0MKV5q1ZtjlKxSQRPEC3HMici0gKN#r##n#i6VE2vMTOLRAA8twhU/5qtQQ+6TlfnAmRRmZRtJyZQAbV9B9LKh2w1CTx3u7+6to#r##n#d7cKWUyatYRCQtRVn8wo4jSkhziF5DXrXIEmzdpr8XJ01hlJXwsldxI+jDeWtI3B#r##n#xiZ2yfdECzoMvkQxkZ4JrV54Ad/kvptdV4BOTP3CZjWMb363Ia5/GrsJ66rCcJo9#r##n#JIzAADc0XQIj6huzlnZ5m3L7RWOV9odczAt2rxTY5JGk0XrN0TVj3e4F66HRUAH3#r##n#6zWhBaMRqjc05C0CAwEAAaMPMA0wCwYDVR0PBAQDAgbAMA0GCSqGSIb3DQEBBQUA#r##n#A4IBAQBcRFxeCE/s+p//DGWvkZ9sSO1eaMmidJKmi70mpjN8ToRk7V2HMP/OwNQG#r##n#hHYCWb1mkqQJNXpTqtdRYsKTitVQ2RnPaoZQ8kc9tOBV1JG5Ny46PE4qsumR/9ca#r##n#F1m6ZxcloS1r1SoBN6jEqj4Md5rtzNI4T/xMVT+Bj4t32qEKdGdSBXbscsoPf2fc#r##n#/XkIVK6FsG4Gup0o8yHksqHJkxptwIUalY3pd484wtRNjfuTO6boV5oMk9L2CjYG#r##n#VwTIJoF5iTqCzZfMqUfTN97vO+9lxQctlW5k3RrOSvSeKlyLUSDSoZNoEXCWreBs#r##n#3Zy3chOAb+02VOh8v0ZKfOevMEIL#r##n#-----END CERTIFICATE-----";
    private static final String GIVEN_VOTER_AREA = "730071.47.18.1824.182400.0001";
    private static final String GIVEN_CONTEST_ID = "000018";
    private static final String GIVEN_ELECTION_ID = "01";
    private static final String GIVEN_ELECTION_EVENT_ID = "730071";
    private static final String GIVEN_VOTER_ID = "01012134874";
    private static final String CHANNEL_ID = "CHANNEL_ID_UNCONTROLLED";
    private static final long RECEIPT_VOTE_TIMESTAMP = 1375768448853L;
    private static final String GIVEN_VOTE_Z_K_PROOF_SIG = "MTAzMTIyMDcwMDMxNjU0MzE4NDY5MTE0ODkzNTQ4MjUwODczNjI0NDE4NjQ2MTQ4MzcxMDAwNTAzMTgwMTM2NDQ3OTk0OTA0NDc0Nzc0fDI2MzA4NzkyNDMwMDg2Mjg5NjI5NDQzNTEzNDY3ODA1MjA4NDQ0NTk4MzQ4ODA1MjAwMzkyNjQwNTA2OTQwNDg0Njg4NDI3MjkwOTI2NjM0MDgxNzY0MjA2NTE2MjczNzA2MDQwMjY4NjM2NjQzNjgyOTMzMjUxMTg0NTk0NjE1ODM4MjA5OTY2MDAxNjY5OTI0NDMxNjI0MDM5OTY3OTQ4NDI2OTY3MjA2MTc0ODkwNDExODAzOTY5MDA4MTYzOTU0MjA0MDIxMzM2OTc0ODQ2MDE2NjY3NzI2Mzg0NjA1Nzg0Mzg0NTQ4MTE5MTk1MjYxNTQ2OTg3NjA5MTkxNTM1MTcyNzY5MjIxNDAyNTkxNzIxMTU3NTgyMjQ3NDY4NjU4NTAwODU1Njk0NTcwNTQ1NjExNjEyOTM1OTkzNTQ2MDYzOTcwNzcwNDczNjI0NTY0NTc5MTQ5MjIwOTM2MTQyNDU3NzY0NTU4NTYyNDYxNzkwMDQwOTUxMzc3Mzg1MTU2MDYzMzI4NDU5Nzc4NDIxNzA0OTQxMDY0NzMyMTA4Mzg5Mzk5MjI1NDYzMjgzODAxMTU5NzUxMjg0NjM2Njk1MTYxODc3Mjg1NzI4NTg5MDA3Mzk5ODYyMTE0OTAyOTE0NzQ4Mjg3Njk2NDYyMjg5ODMyMTMwMjMwNTQ2MzgwNzUwNzEwNzU4MzA3NjM2NTgxODcyNDIzOTAwNjE3MjI0MTg2MTI3NDM0MTYxNjAwNjU1MTcwNjM4ODIzMzI1Mjg5NzUzMTIxMDgzNjk5MjU2MTc1Nzg5NDEwMzQxMzM1NDgzMDY3NDF8bnVsbA==";
    private static final String GIVEN_SAMPLE_LINE = GIVEN_UUID + COMMA +
        GIVEN_AUTH_TOKEN + COMMA + GIVEN_AUTH_TOKEN_ID + COMMA +
        GIVEN__ENC_VOTE_OPT_IDS_PUBLIC_KEY_COMPONENT + "#" +
        GIVEN_ENC_VOTE_OPT_IDS_MESSAGE_COMPONENT + COMMA + GIVEN_ENC_VOTE_SIG +
        COMMA + GIVEN_INTERNAL_AUTH_TOKEN_ID + COMMA + GIVEN_ELECTION_TYPE +
        COMMA + GIVEN_VOTE_TIMESTAMP + COMMA + GIVEN_VOTER_CERTIFICATE + COMMA +
        GIVEN_VOTER_AREA + COMMA + GIVEN_CONTEST_ID + COMMA +
        GIVEN_ELECTION_ID + COMMA + GIVEN_ELECTION_EVENT_ID + COMMA +
        GIVEN_VOTER_ID + COMMA + CHANNEL_ID + COMMA + RECEIPT_VOTE_TIMESTAMP +
        COMMA + GIVEN_VOTE_Z_K_PROOF_SIG;
    private static final String SAMPLE_LINE_WITH_OTHER_UUID = OTHER_UUID +
        COMMA + GIVEN_AUTH_TOKEN + COMMA + GIVEN_AUTH_TOKEN_ID + COMMA +
        GIVEN__ENC_VOTE_OPT_IDS_PUBLIC_KEY_COMPONENT + "#" +
        GIVEN_ENC_VOTE_OPT_IDS_MESSAGE_COMPONENT + COMMA + GIVEN_ENC_VOTE_SIG +
        COMMA + GIVEN_INTERNAL_AUTH_TOKEN_ID + COMMA + GIVEN_ELECTION_TYPE +
        COMMA + GIVEN_VOTE_TIMESTAMP + COMMA + GIVEN_VOTER_CERTIFICATE + COMMA +
        GIVEN_VOTER_AREA + COMMA + GIVEN_CONTEST_ID + COMMA +
        GIVEN_ELECTION_ID + COMMA + GIVEN_ELECTION_EVENT_ID + COMMA +
        GIVEN_VOTER_ID + COMMA + CHANNEL_ID + COMMA + RECEIPT_VOTE_TIMESTAMP +
        COMMA + GIVEN_VOTE_Z_K_PROOF_SIG;
    private EncryptedVoteRetainedTooOftenIncident incident;

    /**
    * Creates an incident to test against.
    */
    @BeforeMethod
    public void createEncryptedVoteRetainedTooOftenIncident() {
        incident = new EncryptedVoteRetainedTooOftenIncident(new EncryptedVote(
                    GIVEN_SAMPLE_LINE), EXPECTED_NO_OF_MATCHES,
                ACTUAL_NO_OF_MATCHES);
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
     * Verifies that the incident is equal to another incident with the same encrypted vote and expected and actual number of matches.
     */
    @Test
    public void mustBeEqualToAnotherEncryptedVoteRetainedTooOftenIncidentWithTheSameEncryptedVoteAndNoOfMatches() {
        assertEquals(incident,
            new EncryptedVoteRetainedTooOftenIncident(
                new EncryptedVote(GIVEN_SAMPLE_LINE), EXPECTED_NO_OF_MATCHES,
                ACTUAL_NO_OF_MATCHES));
    }

    /**
     * Verifies that the incident has the same hashCode as another incident with the same encrypted vote and expected and actual number of matches.
     */
    @Test
    public void mustHaveSameHashCodeAsAnotherEncryptedVoteRetainedTooOftenIncidentWithTheSameEncryptedVoteAndNoOfMatches() {
        assertEquals(incident.hashCode(),
            new EncryptedVoteRetainedTooOftenIncident(
                new EncryptedVote(GIVEN_SAMPLE_LINE), EXPECTED_NO_OF_MATCHES,
                ACTUAL_NO_OF_MATCHES).hashCode());
    }

    /**
     * Verifies that the incident is not equal to another incident with another encrypted vote.
     */
    @Test
    public void mustNotBeEqualToAnotherEncryptedVoteRetainedTooOftenIncidentWithAnotherEncryptedVote() {
        assertFalse(incident.equals(
                new EncryptedVoteRetainedTooOftenIncident(
                    new EncryptedVote(SAMPLE_LINE_WITH_OTHER_UUID),
                    EXPECTED_NO_OF_MATCHES, ACTUAL_NO_OF_MATCHES)));
    }

    /**
     * Verifies that the incident doesn't have the same hashCode as another incident with another encrypted vote.
     */
    @Test
    public void mustNotHaveSameHashCodeAsAnotherEncryptedVoteRetainedTooOftenIncidentWithAnotherEncryptedVote() {
        assertFalse(incident.hashCode() == new EncryptedVoteRetainedTooOftenIncident(
                new EncryptedVote(SAMPLE_LINE_WITH_OTHER_UUID),
                EXPECTED_NO_OF_MATCHES, ACTUAL_NO_OF_MATCHES).hashCode());
    }

    /**
     * Verifies that the incident is not equal to another incident with another expected number of matches.
     */
    @Test
    public void mustNotBeEqualToAnotherEncryptedVoteRetainedTooOftenIncidentWithAnotherExpectedNoOfMatches() {
        assertFalse(incident.equals(
                new EncryptedVoteRetainedTooOftenIncident(
                    new EncryptedVote(GIVEN_SAMPLE_LINE), OTHER_NO_OF_MATCHES,
                    ACTUAL_NO_OF_MATCHES)));
    }

    /**
     * Verifies that the incident doesn't have the same hashCode as another incident with another expected number of matches.
     */
    @Test
    public void mustNotHaveSameHashCodeAsAnotherEncryptedVoteRetainedTooOftenIncidentWithAnotherExpectedNoOfMatches() {
        assertFalse(incident.hashCode() == new EncryptedVoteRetainedTooOftenIncident(
                new EncryptedVote(GIVEN_SAMPLE_LINE), OTHER_NO_OF_MATCHES,
                ACTUAL_NO_OF_MATCHES).hashCode());
    }

    /**
     * Verifies that the incident is not equal to another incident with another actual number of matches.
     */
    @Test
    public void mustNotBeEqualToAnotherEncryptedVoteRetainedTooOftenIncidentWithAnotherActualNoOfMatches() {
        assertFalse(incident.equals(
                new EncryptedVoteRetainedTooOftenIncident(
                    new EncryptedVote(GIVEN_SAMPLE_LINE),
                    EXPECTED_NO_OF_MATCHES, OTHER_NO_OF_MATCHES)));
    }

    /**
     * Verifies that the incident doesn't have the same hashCode as another incident with another actual number of matches.
     */
    @Test
    public void mustNotHaveSameHashCodeAsAnotherEncryptedVoteRetainedTooOftenIncidentWithAnotherActualNoOfMatches() {
        assertFalse(incident.hashCode() == new EncryptedVoteRetainedTooOftenIncident(
                new EncryptedVote(GIVEN_SAMPLE_LINE), EXPECTED_NO_OF_MATCHES,
                OTHER_NO_OF_MATCHES).hashCode());
    }

    /**
     * Verifies that toString produced a comma-separated line with the correct
     * values.
     */
    @Test
    public void toStringMustBeCommaSeparatedline() {
        assertEquals(incident.toString(),
            "EncryptedVoteRetainedTooOftenIncident" + COMMA +
            GIVEN__ENC_VOTE_OPT_IDS_PUBLIC_KEY_COMPONENT.replace("#n#", "") +
            COMMA + EXPECTED_NO_OF_MATCHES + COMMA + ACTUAL_NO_OF_MATCHES);
    }
}
