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

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import java.security.NoSuchAlgorithmException;


/**
 * Unit tests against the EncryptedVote class.
 *
 */
public class EncryptedVoteUnitTest {
    private static final String COMMA = ",";
    private static final Object GIVEN_CONTEST_ID = "000018";
    private static final Object GIVEN_ELECTION_ID = "01";
    private static final Object GIVEN_ELECTION_EVENT_ID = "730071";
    private static final String GIVEN_UUID = "8a84806e40521c6d014052329b3401ed";
    private static final String GIVEN_SAMPLE_LINE = GIVEN_UUID + COMMA +
        "rO0ABXNyADtjb20uc2N5dGwuZXZvdGUucHJvdG9jb2wuaW50ZWdyYXRpb24udm90aW5nLm1vZGVsLkF1dGhUb2tlbvz+Wz5pky7LAgAWSQAhX2FkdmFuY2VPZkV4cGlyYXRpb25UaW1lSW5NaW51dGVzSQAaX2FmdGVyQ2xvc2luZ1RpbWVJblNlY29uZHNKAAVfYXNJZEkAGF9leHBpcmF0aW9uVGltZUluTWludXRlc0oAA190c1sAD19hdXRoU2VydmljZVNpZ3QAAltCTAAJX2NvbnRlc3RzdAAQTGphdmEvdXRpbC9MaXN0O0wAGl9jb250cm9sbGVkUmV0dXJuY29kZXNUeXBldAASTGphdmEvbGFuZy9TdHJpbmc7TAAQX2VsZWN0aW9uRXZlbnRJZHEAfgADTAALX2VsZWN0aW9uSWRxAH4AA0wAA19pZHEAfgADTAAJX2ludFRva2VudABBTGNvbS9zY3l0bC9ldm90ZS9wcm90b2NvbC9pbnRlZ3JhdGlvbi92b3RpbmcvbW9kZWwvSW50ZXJuYWxUb2tlbjtMABdfaW50VG9rZW5FbnRpdHlQcm92aWRlcnEAfgADTAADX2lwcQB+AANMAA1fcG9sbGluZ1BsYWNldABATGNvbS9zY3l0bC9ldm90ZS9wcm90b2NvbC9pbnRlZ3JhdGlvbi92b3RpbmcvbW9kZWwvUG9sbGluZ1BsYWNlO0wAHF91bmNvbnRyb2xsZWRSZXR1cm5jb2Rlc1R5cGVxAH4AA0wACl92b3RlckFyZWFxAH4AA0wADF92b3RlckNvbW1JZHEAfgADTAAPX3ZvdGVyRmlyc3ROYW1lcQB+AANMABBfdm90ZXJJZGVudGlmaWVydABDTGNvbS9zY3l0bC9ldm90ZS9wcm90b2NvbC9pbnRlZ3JhdGlvbi92b3RpbmcvbW9kZWwvVm90ZXJJZGVudGlmaWVyO0wADl92b3Rlckxhc3ROYW1lcQB+AANMAAxfdm90aW5nTW9kZXNxAH4AAnhwAAAABQAAAlgAAAAAAAAIAAAAAB4AAAFAUjJdWXVyAAJbQqzzF/gGCFTgAgAAeHAAAAEAkiHd/dQKfImbVY7Yq5Sfbw5qNsYwffE0FJRcycaOAte4A2eu0GhYFs/LRzcp5FsX4dV/wmIRuItJlkUIsvz2CKD95kut6P3+OUbGfdSJWv1olkvvdds3WicfpvlXTp081eiAv2lPjsqZPTz3Ebuce7c+nO09PEWCZRI6KcG0Iw5DisP71OEUuoTBxzP56bVI8Sq2/d/G7WMLFaFWRyw9QwDTvdOwW/iTZ6FJ7bGFkqH51rcDfrvl/8SgybUlQwHg1Hx6tw2P32ZDREvkPidQW9BlvBp8NXc0UbUTGt16d1+aHdw/7E1UtVmaqgkcIEC/aZn95lSP4ueoKF2wPqR17XNyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAABdwQAAAABc3IAQGNvbS5zY3l0bC5ldm90ZS5wcm90b2NvbC5pbnRlZ3JhdGlvbi52b3RpbmcubW9kZWwuQWxsb3dlZENvbnRlc3QiWqLrfd15KQIAA0wACl9jb250ZXN0SWRxAH4AA0wAC19lbGVjdGlvbklkcQB+AANMAAZfdm90ZWRxAH4AA3hwdAAGMDAwMDE4dAACMDF0AAEweHQABlNDUkVFTnQABjczMDA3MXQAA2lkNXQAJDMyNzc1NGMzLWFjNWQtNDRiYS04NDEzLWRjMjljMGYzNmI0MnNyADxjb20uc2N5dGwuZXZvdGUucHJvdG9jb2wuaW50ZWdyYXRpb24udm90aW5nLm1vZGVsLk1pbklEVG9rZW7g1pITYjnPpQIABEoAA190c0wAA19pZHEAfgADTAAFX2luZm9xAH4AA0wACF92b3RlcklkcQB+AAN4cAAAAUBSMly1dAAkNTZlMGFmNDMtMTFjNS00OTljLWFiMWItODBhZWE1OTFhOWNkdABrI1R1ZSBBdWcgMDYgMDc6NTY6MDYgQ0VTVCAyMDEzCnVzZXJJZD0wMTAyNDEzMjk5MAp0cz0xMzc1NzY4NTY2OTY1CmlkPTU2ZTBhZjQzLTExYzUtNDk5Yy1hYjFiLTgwYWVhNTkxYTljZAp0AAswMTAyNDEzMjk5MHQADFVTRVJQQVNTV09SRHQACzEwLjAuMzIuMjQzc3IAPmNvbS5zY3l0bC5ldm90ZS5wcm90b2NvbC5pbnRlZ3JhdGlvbi52b3RpbmcubW9kZWwuUG9sbGluZ1BsYWNl1/fPlAehz24CAAdMAA1fYWRkcmVzc0xpbmUxcQB+AANMAA1fYWRkcmVzc0xpbmUycQB+AANMAA1fYWRkcmVzc0xpbmUzcQB+AANMAAVfbmFtZXEAfgADTAANX29wZW5pbmdIb3Vyc3EAfgACTAAJX3Bvc3RUb3ducQB+AANMAAtfcG9zdGFsQ29kZXEAfgADeHBwcHBwc3EAfgAKAAAAAHcEAAAAAHhwcHEAfgARdAAdNzMwMDcxLjQ3LjE4LjE4MjQuMTgyNDAwLjAwMDV0AAVmYWxzZXQAB1JhZ25hcnJzcgBBY29tLnNjeXRsLmV2b3RlLnByb3RvY29sLmludGVncmF0aW9uLnZvdGluZy5tb2RlbC5Wb3RlcklkZW50aWZpZXLKvqZX4QyCdgIAAkwACF9zcGFyZUlkcQB+AANMAAhfdm90ZXJJZHEAfgADeHB0AAswMTAyNDEzMjk5MHQACzAxMDI0MTMyOTkwdAARTG9cdTAwRjBiclx1MDBGM2tzcQB+AAoAAAABdwQAAAABc3IARGNvbS5zY3l0bC5ldm90ZS5wcm90b2NvbC5pbnRlZ3JhdGlvbi52b3RpbmcubW9kZWwuRWxlY3Rpb25Wb3RpbmdNb2RllMcp1WIDaxECAAJMAAtfZWxlY3Rpb25JZHEAfgADTAALX3ZvdGluZ01vZGVxAH4AA3hwdAACMDF0AAEyeA==,327754c3-ac5d-44ba-8413-dc29c0f36b42,IWvRkimscLc7ZnzHRhq5HkKtL0XXlMwZEFu2w7iIdSBCWDAKuxgUNPkA66dqZ7Oy#n#uvqePFoXVHLgemivhxRHAtkKALAtdc3ihfDN99u3vrfiPinPLHcidemy056uxPO+#n#9mb96Cs4RVhFDY3DnW6BuZQUuITmrBOEhaaxja7qbijKD7SNX2bwqRv0j4GeasLa#n#GosqDfFqEYd9aLr7x7M6xkk4AhyoPHG7qAI/A1SiwDxF2ocycQgJk7kl4yeJDgBK#n#PFw0F3xZ1f8yVJEr6d8Su8OwdXN/NiFlX9bjKifFK2bgMqJvlsAPXLjK5ms/2VeS#n#IE4atuMnZquzJMpjEICHcQ==#BuuEgmh0HZdfhNAaD+pVdg8Kgw/lAvU8Bq/07J6ooFz+QzD2uuhexGlqkRCUIq2s#n#bEwYzTLLy+QS+ZeBK5t4k7Gq/wOg85/VLvFcW7wi2sWO5o9HR9k11lX8rW0WxCIn#n#vFzYsZNwWbPiHc7hNswodqTJsIkNpdxWajO8nEMsn4jbSmvcSI1XNTd6mKANuNSJ#n#PUYsyEoUSmEPO8VB29mvEcG4O/EhGLBaEFqLQCFn3Da0NoxO20BiR7D99jHz1M70#n#YeP459wziI94UpetuwTaUKW+Pw8qifwyU/AH8r1Th/MGWncvriPNnEx3Sv6GOeUr#n#t/ImHHGmlYoPrvbGKGQUxw==#AIXBRFx299j0+knJHeC61pJUdJc9RD9teNfo9vAtQh+cMEp7OvGbp3Y2M7tlSklS#n#ovAP3TVjltlqfwteGJXqP/r/eTWCCPmivypjQ7FeOAFTtjrjVa2SMRaAEEaNxq7h#n#/xE1KE9lto+XXZ/NB69m035763kQQemFHIuDeNoDNu0sVpkgtkViJzWGu2b0ZCy2#n#2Sxf69qTntXCMv68tlQWNmFx889DSRpOhDXV3ivkV28J1GfO6sjS5MIWM3a7CZFC#n#NPTf30f4/IZCrnepKjGINxTn/18TWLt3jFYC/jM7I0bLTGVUs8hweC36F9nMKR32#n#dOn65AizJOtKzaYn4cmTxw4=#RLHu2eykn2di6LrMPG/ov+pR/hkx1THELRV5RYPOexiGHjKecIJCe83tskFZFeCr#n#vFWPjgNlQxl1L4GkHICSQ7rJ9rpj9rOSQvGgjm2WqRMO329FmWUCu/MQFQP3u5EO#n#27WUNxeVB9gRufDTtd4f+SZ+nDkW4D/k8GsaJ/GaNTLSgAH7WMuj0H7AVsvHB0y6#n#abJUu8QFZkewmW9RC+Bw2NKimXdlHu9TbhuG3ZuPeXK2ccWpt5HVwRVgYI+/d+p7#n#Gbhl+f1CAh3rWTaAhrsl2ExLav/IrAiXLOrcmEQtnAFq4aXVZP+YwQmfW0NHmkow#n#9H03qfEhPX6f8UH3o6lEmw==#AIJrTHjlP6vjvMaEwkxdYlX7TYgV5WsTqBax91Vt6trRnHs2j+hZE29anlIS3VRE#n#+epvUA5j3WX8i0KEdX/+bi7AF3lYowuHtWGPvNq8CL9Pt8RnteomIqObNx9dglX7#n#7Ic5q4FawXA0dSEpTlZS9YxFSq7sduycaF+h5cfofV5nvYFxapv7k4hJG2XEEZIy#n#ZsCR3AmxVybfnrKDG2QBHBFicbTasZt6ne5apx5i0I5u9Qd+Lt8PvUcfyNSMa4Lm#n#GXxeAkzkhsjgF5ABI/fMZ+pbPf58cdbVsbadpGi5bNAlOtqmAYICPjN6M1cYCPdC#n#VTrN4Egu/hlqSaI7X/SpnP0=#QTvEyNopYkTzzmGk1Pm7Xk32SdxmIfQYPGiHaws2llXa9JC71sCnHXqHSnsSQsmY#n#YheZ28Cpz0nuF+Qc578kgX3e9warGYfdPtu0VH5B1ShSAC/aXmLqwq3wd5z+rt6+#n#fGYURuGfISrVHzmu+1g5mx/pe3BTXhB0KvwEsVd02BtasFcrfcBvlT3QPkzYaAqv#n#HTMdpj6k19E7WFLlzESiAvnIYj3nCrlQmPHY0RoKpDEIYAllzI6NaCRkW+qn6eEx#n#kKhgV/t0/6tjiZz2za6D3ULFOIThixdtIyPgnhekRzioFygWD8jAUke23lor0mKd#n#8dVTW6nbaw6Rg4MEBS5Iuw==#XIcOxtOtI3Zoz5gkw9GyV/r0SY8anhz4EehsyYHzm0vMHqxY1RAzLVlwZtbJS2FD#n#n4D/pdGqLfoNh/oy/teSV/Fn6f4aVNvlJqp+YhrZXKIbTMb4TT5UjTcipqXluaDj#n#3kLIs4mKfZh9S7g4Km4TrKgC7oXV/Tt7/BmO4eUYaJ+R7d9OGJM/soHIhYEFvn6h#n#xhPUxpNOq51KloXHhIf4KrrusneOWbgY0yvCrLPfCmSbgX2v3ZXkIJrlYn1nKt31#n#4rNuvLyTLB3twzERfjKQSRmJVmeA3ZCw4CA+Jg8vnc1FThlfKVBDaL8liusHsTWK#n#KuGG2COfzTUeM0v7nidKYw==#VIBFMDoM7ZnBtmEsfVRju/gaw3ZSCr6/UfbHEPv4qlVeYVdaBuGb1NnBn60tGNcd#n#/G+1TgunD6J7xr94fWgH5jHFNH8axp4x3plewSQMICtZWg3p7BJlx760k8tkoBXL#n#49bNnk1pL+JpgMlBkv5tIdoCg9KSyKVOZEgouoFLtT/6xKLgdpc8Hk37ocwSJ5Gh#n#5gRaVQ4e2w5J/pLOvM45TR4GPUS4EBVYIHJOR3e8sVUv5m15qI4X4IABkzjfJyb8#n#geucJvhKsPRI4u1Xt2jBap/jvRfQhYnWmAng6ZFkdxGHbmFtsBMaxZ5COwPgMiim#n#Zixp1oi7pnc7V8+erscySw==#AJJuIUlLz21lAbEfyhJv1/Dybxes9ODxZSrMBi371L1vfxJlvmQnJbQwY0IaTLiF#n#ClNVth1/9CiLZBeT/G1wQyPS0aqw+6kUVqd/rH19XGPqhCPVqE3Bk4ZxZ0qQ23nu#n#YHVNnoSY6MLvwT/BGxXlZggTqAZVpLwENR5Jsfd1aRsQRoc5lBaeTEySFHpxXnw/#n#4pBfiQCTeIZ3sV2jsdbSZtvPsvfclP2GrjcXXhcF/97rqtuB+ueTlvd+WneoKcdr#n#NmvM2QHrEk22LAK8MuO1gL5prd2OHoQzZTsuS1Js4B5a3KUhlQ+qLzh20IKAxnq9#n#S7RgEcfR/3GFlGlA156mBqQ=#ALE/kidnHGKAlQZcrMXP3Q2Yf9OGR1KfQIOxXoG3HFuBByAWRQlUh+GTLI9DLL/Z#n#jXduedD5gKfyhtRcDHNDTQOZ62azsLcFWqSmyyUZNEbAA1o93wGfBfb5bP7Atobg#n#hdn5RaOwH91lrSI+bprkFSrbrcX3WFuzEUkyVMtqF6+Hkv42KodyZOa5Y77RHRHt#n#zFq93twQIljWwggl0tRMzF6X091Niu5hd6dxhYPLj2bIMyVYIEuzwEpmBdx6wa06#n#0rZ0JJHWZOFfx30DI1FIOB/sLu8E8DvT1XGID+pypoIRXl1o4VVadO+Nz3ZK0E5c#n#3cNqphhZHiwCChI3uZ6CS+Q=#Wlb1S5QPEQXJbgpodceF32KhehlI0nVxeiZyaL0Y6fvufve0PLRYujYmjr2Blqq9#n#8TACo+zSF9JgD7yQ0p13qY7P6qbjaUDHo7u1CODPHI7FW6qJY+F6YljlUx3K+Q3X#n#1W90h8R37hqHEKn5tjkf2BGGflpTcmuozGY92nWBP9QJF1xEY7eOVb2tW94jul/r#n#DWSDJubN5lHdLa2ZkRMAdHUkZoauLlia7pcaxPtIvwkUf0UnHcgLKmv1T9fE3/u0#n#wRHhJNRa3XGWXXN9qoPnl1g0bGxBNVONOXa8QwIry1UN/4H398mSTYb3/4eqwMfz#n#LpaaJ4ZN8XqIYraOgpTfcQ==#AKhdSy0Segn/z41yAtJXfQq9Hy+rXdOLlvoS6S12kQD2WqooswmIUpy9WQjFewpH#n#Wi01hz9bKJbGzy3AQE09edfuF8kKbu7v29lyCqYhTsYZ0y02gGHMUtEFFrbnEShd#n#qoZX/IWsSeBJnu/Yr97Bi957Cq4oRO0Q/dHG1swFRH1d4xidiieWspLIXEhwjaiA#n#1p41rO2l3rgvlj4zh43QbuqbhFzj1XuUZlJ3UefejV+VrhTaIIa4oTf0ONksYcCh#n#ybavNO+RS4WYCW20Pt582mhDdRqlv94NMa7s5u+knE6dA8GmhFUnUN5w3cdbZ83E#n#d02h4vdQmbqqjPbBelueru8=#TGmAQvOeFSoek4zJAFVXojTDNGtj7q4UnMK1gEv6UAnbjHKMWdR7rVoAQTJttcFF#n#c8wQBzHe+qgdG8DjR4PGjZ00FJ73licBxYOTA4tUWI19JuNo/0/ezMm2MgUQgkkn#n#ehhIvsro6VIHp886GjyTmcq7xhTBdlhxY+68JNZ9CyqcQQOjfKWuHrX/b8WS0o9R#n#j3GP2FW4wWdSHWyr4OHfo8VeWV224LLttHGuxgeu6dluo9ebuUUIoFT8hEAeU4Uy#n#qwwiNqzooGUbWgp2rLcxSZUEkNPzTmYcw1GlSMyXXCJhuNTmyO9tN5hdz9nI/3PQ#n#xBI8kBDKKYMMNn1KtRqlWQ==#ZWqr/funAgzcemNtAG1z+pLXphzOFiQM9PSAc6oDrXiawICQ0dvFj5nKqoeNNYAG#n#ti3wGAlKQ1i2iqe2dVfnTKRsPdKKnFJbShgoz4Z0s5N8iVH0Ig/aa0z0CJPA5OzC#n#Hco7+LmDLgLER+21EfEsdK1eWg+yxZg1PRvXruHxnO96Gq4fxIZNPIJnoSUzMbEH#n#+f3ZvdQ890fo2GSR4HDDPy0aQgBxp6GNHhDwiaer7hFp7GLwwEq1H3LkWHThTtiv#n#s2Ug+clhWIp2u8fmooHjNKRK/3IcUjkFbnY1hwbQ1nbcfLO10sFaPfN1I7JBuTxR#n#s7hjtVD4Gpb8iv9HCGonzg==#cPZ8/eIi4++oG4ZLj0/tuO0xZgjTy9Vi6nm/0HKQvjFzXfngcyU/aU5GDKwSl3DH#n#7UTXCmaterJjMxidefNUVaZkqVoP7hrTH7sxZVvnOZZyvHue2P+pdv1jWrCZx9ry#n#FUFz8bsC6VHhj5uWviX0yBVOCr8Vr0La6y4O4NE+zENTqKLdB+eYpMrliTgj3FX2#n#ffyLuDtRnpqEIVbSGJDa9uJXMCieB+QN/gKC7GXGZNkbrRU8pRfGbyvnzAzlHLAo#n#ehY8cxjxE//tmAcpjPoFzJgMTMqpxBwIaCvQljWZsXZq+VP9EIUJVnc9W4wl3K9t#n#X6CpWtRXyCPzSUreoZF4DA==#ENg8sH42gHyMELsTRK0SWQIdsjNyxLtWUF7jtx0xAzjmGonRJjWCV81loejju7Xm#n#oIn01avDt6FqBdbT3kWtQGVRGVL6UL8nZpB+h8M527RBBsmsKcY92NIbF/tZ3YUf#n#noQn/fVT25eKt+0dS15q8p7o4zKnANBy24uktYzl5B//aS589SvkpUcl74N0imnZ#n#kyDkGSwgv4gu/8BgVU6ad5/socwFSKP56NJTiKPqJAqAHAXW24I3pNoD7wz6T/1G#n#AEY8yeteeqpqc1EtmS6XANKhpQT6C9No4+ydyQVbvUMjoNFJIj1nrw3LTfkwxKX4#n#yc48tTBx1KQTvVzWaSm0tg==#,prIuVEjY/ZgHq/R5k149fl/5sOg2d9zaq/5LMlmAmRA6Z3xBKRqtEfYsEp//+4nTLqbDrZ2GQ0zZLEUoEX1dTZ/xcf4LElCLlwN5VfQHZ5mlWI9Ppn6B952Ge5OY/XS5gZ7wXCrBjfx2taw7KPWbvw+dJSiFACBFAdO5/CtVjAVoLVNR6aukXCmamHeamJMixY+OkYEXA4wGpv75Ni1YwE7kswhx+6/GS7X/rGR1KIkzob8sQEGK4fKZKlFddMaGT1lGlvlAWxpXkJxUeIRfraq+HkFfJSCUG16YyyPl89ExEyHKB85mIPestp6i2iekhZZbdOOoRvrIbjU5VDhiWg==,56e0af43-11c5-499c-ab1b-80aea591a9cd,parlamentary,1375768567129,-----BEGIN CERTIFICATE-----#r##n#MIIDcjCCAlqgAwIBAgIFAIlL9yswDQYJKoZIhvcNAQEFBQAwWjENMAsGA1UECgwE#r##n#U3RhdDELMAkGA1UEBhMCTk8xDzANBgNVBAMMBjczMDA3MTErMCkGA1UECwwiS29t#r##n#bXVuYWwtIG9nIHJlZ2lvbmFsZGVwYXJ0ZW1lbnRldDAeFw0xMjA4MDYwNTMxMDJa#r##n#Fw0yNTA4MDYwNTMxMDJaMIGJMQwwCgYDVQQKDANPcmcxDjAMBgNVBAgMBVN0YXRl#r##n#MRAwDgYDVQQGEwdDb3VudHJ5MUgwRgYDVQQDDD9Wb3Rlcl8xWXpMQ2RxcmxySGIz#r##n#T3BuQ1RNbkxraGhrcWtORUxQNGlMa0xRcTNmOW1FPV9TaWduX183MzAwNzExDTAL#r##n#BgNVBAsMBFVuaXQwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQC++o00#r##n#70U9EqFB5ISFDaCpIp33h5Lild7CbfHGd2eXZqF29a3LTDZJ0xcj6ML6uJHfAGHb#r##n#+Wp3HBj1bkk77VbQ56DB82ntz88neMrKKiZA3WIB/TgVD0U71Djh6czsd2q+UxgW#r##n#U/6TAHAMpXurb7pxw6AIHGrRnLPOGBNIcTikqqux0+IMpPDWEv6gK99CWN2GtvO+#r##n#Ds8oxPsGx3qIpKLkGq4ozBNaFE10kqLoO/WzIeETknzQy5YvF9v3Tu/BJufcI9LS#r##n#+XqczDX0G5HPJHWap9rB/YL/2Wa79HLP6I84LaF0VI+5rtKs5mOwXuSoVKBoSG9k#r##n#QmFSuQWd9JOYONrzAgMBAAGjDzANMAsGA1UdDwQEAwIGwDANBgkqhkiG9w0BAQUF#r##n#AAOCAQEAQ7ntcMb0kZZOk49M5rEgRtsac1u/upj6/xsS9aWFz/iZsHaPQKnnUFV2#r##n#e1Q4FnsGDfSXefLJDWyqOaObrMJ6c2pa8W09RVRcrUibkeLmUxC1PmifD3Gsr3jV#r##n#xnk+xMrGsjrjfHR3mCZ934Pboy3g4Wwz8heRwUA1mahRW6FO1QfGrDE2NcWXVM8V#r##n#FnY9RVYhoQkYqV2s34YhCD7WsmnK3dDnz3DI9w50LcWmf4oqGHbcqcO9LOPodF+O#r##n#BcprDKV5dPumapHKhKrXXJn6zcI8uESoFb0DtAN4LClJtsy2qDzOIrM8Ye4yOGMS#r##n#3x9u80KJrjmw0Skc/L6CWzGDOWyQOw==#r##n#-----END CERTIFICATE-----,730071.47.18.1824.182400.0005" +
        COMMA + GIVEN_CONTEST_ID + COMMA + GIVEN_ELECTION_ID + COMMA +
        GIVEN_ELECTION_EVENT_ID + COMMA +
        "01024132990,CHANNEL_ID_UNCONTROLLED,1375768580765,NTc5OTI3ODI5Mzk3MjY3Mzg2NDg5MTMxMDA5MTc1MDA5MzY3MjA5NTkzNDg5NTU2NTEzODc0MzY5MzI3OTkxNzU2MDEzNjU4MjU4NTZ8NTM1OTAwNTIwNDA4NjY4MzUyNjQyNjM2Mzc5ODAyMjMxNDEwMjQ3NDc3NzAyMDgwNzk1NjM3ODUwMTI3NTUzOTE5NjA1MDYyOTExODgzMDg4NDI2NDU0NDE4ODcyODQ3NTE3NjUzMzMwNDQ2OTcxMjc1ODAyNTc3MDc3NTA2MzQ5NjExNzA5Nzg4MDU1OTYxNzA5MzE0NzA1Mjk0NjAwMzY2MzI1MDcwMzE1OTkxNjcwODExODI3NjI1NjUyNzI5NzUzNDQ4MTA2ODMyMjkwMDk4ODM1OTQ4NjgyMTgxOTk0ODUwNzc2NzA1MDY3MjUzOTA2MzUzNjQ0MjAyNzEwOTMyMjUwODY0MTg5MTQyOTI4ODEzMjMxNTQ5MzUwMDM5ODU4ODkxMjU2NjQ2MjE5ODUzNDI0MDAwMjQ5NjUyNTAwOTI4MDY1NzE2MDQ2MjkxNjM0MjY5NzUwMzI5NjgxNzI3Mzc5Mzk5MjYyMjIyNzc2ODg4MjQxNDA5ODIwMDc1MjE3MzE4ODk0NjQyNzg2NDI3NjYwNzU0NjE3OTQ5NTk3NDIzNjA2MjkzNzQyMTI2MzAyODk5MjU4MDA4NjYwNzg2NTcwOTcxNzI1NzIxMDkxMDM5Mzg2MTkwMzk4NjkxOTM0MDMzNzQzODY0NzI1MDY0Nzc5NDM4NTQ5NzMwMTMzOTQxNzE5NTA0MzU2OTgzMTA4NjA5MjEyMzE3NDY0MTEyOTg0NTM2OTcxNDAzODEyMDI1MTk5OTE5NTI3MDQ0Mzk5NTY0MTMzNjI2NTIyOTAyMzM5ODgxMDkwOTA0NTQ1OTE4NDQ2NHxudWxs";
    private static final String GIVEN_VOTING_RECEIPT = "rO0ABXNyAD1jb20uc2N5dGwuZXZvdGUucHJvdG9jb2wuaW50ZWdyYXRpb24udm90aW5nLm1vZGVsLlJlY2VpcHRCZWFuw7/82u7t7McCAApKAAZfcmNnSURKAApfdGltZXN0YW1wSgAGX3Zjc0lETAAKX2NvbnRlc3RJZHQAEkxqYXZhL2xhbmcvU3RyaW5nO0wAEF9lbGVjdGlvbkV2ZW50SWRxAH4AAUwAC19lbGVjdGlvbklkcQB+AAFbABRfcmVjZWlwdFNpZ25hdHVyZVJDR3QAAltCWwAUX3JlY2VpcHRTaWduYXR1cmVWQ1NxAH4AAkwAE19yZXR1cm5Db2Rlc01lc3NhZ2VxAH4AAVsADl92b3RpbmdSZWNlaXB0cQB+AAJ4cAAAAAAAAB+cAAABQFIykp0AAAAAAAAAAHQABjAwMDAxOHQABjczMDA3MXQAAjAxdXIAAltCrPMX+AYIVOACAAB4cAAAAQB+xHkHj5yiOnRUOjNgHlAcbraUfO9gaaUxdVJX3eUruirmh5XfPwS8Na+btvq+pXBc5QVVsu83iHYSymjYDQBBLv9m3MbKku23xj2MNQDvCDaC2thxt8GIQRl66WwtsOvKT8i57602N71QMwbxFTd6VIjvR+w3LSVkb2M91TxRKi19DFf9DEoe2rUezzNSMUKKGXF89qOgDv6DrjUTygsbInssz3cJBVEP+YfRZphpzUe3kifNuVCfSzZFwgKgJngrvCkPPdl4w3+NUj9youmpAHiWn0GpIFjvyn3wG5pSwzLwSaUk2z7HQ11zf0xSVSOjYUPehXJiUT9ZVFFx+ieDdXEAfgAHAAABAHF9BM0CkwHL4hp/tOSc4/yUFGE8M+dbb/fRBZlygckJNItPFC0YLlXgrIUQIKn90ILMlMDIT4a98qBbVSmS0j9gzTIlmFO85RQ2+CDNh5zMEmOn04sgEIUv+CDyBkbbDOy30hasinnMrQdRCRSXUsttKKD4B/CWq/ylHFg53b5Vu7aJaSpBxMj5+Z+ieQ3c4GcjC4QXEyp2C81mx5kpEtYpSooQGSuxzsW60MKLgqGJ+VxX6eC8CW9K/qR2BG+14hcW9zmA+qNGpyapyg4Y9JYTcJi+9ZiJ9PbchDNrpm8X8b6TXc0lfWc0vvKjb/TRD4uNc0qBgHhi1kaZOTF5Q5FwdXEAfgAHAAAALEVTUmNpQXdHNE03MVVXcElZSVcwUlc3UkY0ZVNEWUNUMVY5WmF0bTF3c009";
    private EncryptedVote encryptedVote;

    /**
     * Creates an encrypted vote to run the tests against.
     */
    @BeforeMethod
    public void createEncryptedVote() {
        encryptedVote = new EncryptedVote(GIVEN_SAMPLE_LINE);
    }

    /**
     * Verifies that the constructor sets the UUID correctly from
     * a line.
     */
    @Test
    public void constructorMustSetTheUuidCorrectly() {
        assertEquals(encryptedVote.getUuid(), GIVEN_UUID);
    }

    /**
     * Verifies that the constructor sets the contest ID correctly from
     * a line.
     */
    @Test
    public void constructorMustSetTheContestIdCorrectly() {
        assertEquals(encryptedVote.getContestId(), GIVEN_CONTEST_ID);
    }

    /**
     * Verifies that the constructor sets the election ID correctly from
     * a line.
     */
    @Test
    public void constructorMustSetTheElectionIdCorrectly() {
        assertEquals(encryptedVote.getElectionId(), GIVEN_ELECTION_ID);
    }

    /**
     * Verifies that the constructor sets the election event ID correctly from
     * a line.
     */
    @Test
    public void constructorMustSetTheElectionEventIdCorrectly() {
        assertEquals(encryptedVote.getElectionEventId(), GIVEN_ELECTION_EVENT_ID);
    }

    /**
     * Verifies that the voting receipt is calculated correctly on an encrypted
     * vote.
     * @throws NoSuchAlgorithmException Algorithm set to version that should be
     * provided, and should therefore never be thrown.
     * @throws ClassNotFoundException Sample line should be in line with provided
     * version of AuthToken, and should therefore never be thrown.
     * @throws IOException Sample line should be fine to read from, and should
     * therefore never be thrown.
     *
     * TODO: Not implemented yet.
     */
    @Test(enabled = false)
    public void mustCalculateVotingReceiptCorrectly()
        throws NoSuchAlgorithmException, IOException, ClassNotFoundException {
        assertEquals(encryptedVote.getVotingReceipt(), GIVEN_VOTING_RECEIPT);
    }
}
