/**
 * Zero-Knowledge Protocols for E-Vote (ZKPEV).
 *
 * Copyright © 2011, The Norwegian Ministry of Local Government and Regional
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
package com.computas.zkpev.rcgvcs;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * Unit tests on the VcsEncryptedVoteNotRegisteredByRcgIncident class.
 *
 * @version $Id: VcsEncryptedVoteNotRegisteredByRcgIncidentUnitTest.java 11011 2011-10-20 08:26:55Z fvl $
 */
public class VcsEncryptedVoteNotRegisteredByRcgIncidentUnitTest {
    private static final String GIVEN_UUID = "4028806a2fda69fd012fda7a46b10012";
    private static final String GIVEN_SAMPLE_LINE = GIVEN_UUID +
        ",rO0ABXNyADtjb20uc2N5dGwuZXZvdGUucHJvdG9jb2wuaW50ZWdyYXRpb24udm90aW5nLm1vZGVsLkF1dGhUb2tlbvz+Wz5pky7LAgATSgAFX2FzSWRJABhfZXhwaXJhdGlvblRpbWVJbk1pbnV0ZXNKAANfdHNbAA9fYXV0aFNlcnZpY2VTaWd0AAJbQkwACV9jb250ZXN0c3QAEExqYXZhL3V0aWwvTGlzdDtMABpfY29udHJvbGxlZFJldHVybmNvZGVzVHlwZXQAEkxqYXZhL2xhbmcvU3RyaW5nO0wAEF9lbGVjdGlvbkV2ZW50SWRxAH4AA0wAC19lbGVjdGlvbklkcQB+AANMAANfaWRxAH4AA0wACV9pbnRUb2tlbnQAQUxjb20vc2N5dGwvZXZvdGUvcHJvdG9jb2wvaW50ZWdyYXRpb24vdm90aW5nL21vZGVsL0ludGVybmFsVG9rZW47TAAXX2ludFRva2VuRW50aXR5UHJvdmlkZXJxAH4AA0wADV9wb2xsaW5nUGxhY2V0AEBMY29tL3NjeXRsL2V2b3RlL3Byb3RvY29sL2ludGVncmF0aW9uL3ZvdGluZy9tb2RlbC9Qb2xsaW5nUGxhY2U7TAAcX3VuY29udHJvbGxlZFJldHVybmNvZGVzVHlwZXEAfgADTAAKX3ZvdGVyQXJlYXEAfgADTAAMX3ZvdGVyQ29tbUlkcQB+AANMAA9fdm90ZXJGaXJzdE5hbWVxAH4AA0wAEF92b3RlcklkZW50aWZpZXJ0AENMY29tL3NjeXRsL2V2b3RlL3Byb3RvY29sL2ludGVncmF0aW9uL3ZvdGluZy9tb2RlbC9Wb3RlcklkZW50aWZpZXI7TAAOX3ZvdGVyTGFzdE5hbWVxAH4AA0wADF92b3RpbmdNb2Rlc3EAfgACeHAAAAAAAAAIAAAAAB4AAAEv2nmn4XVyAAJbQqzzF/gGCFTgAgAAeHAAAAEALWwj6PRNzzPhkIpBQ85NfJKKkayUOIRY51k+onz95FyzXwwNH/a/rupMUh1cIjCac2o+X3l2zxIn2h2C9HQtVeBJBCkJ9POELMlbGqh6gGvQxF3mx0FuOO0Eg8MQAIhFtm5QSBIOZF2DM3TZxJaAHTl9vuJfp6ME7BD1w7jMMZL54jUUD0+UHs91NOPqbq2ik8P4r3Cn7TNhe/GZa0kN81VIp4viD9hsQgRqArAk0MXrChE6qFXvFyA48f+h60X/9+rarZYBkbsQDg2/+lpa2Yu3fXuhRF3vQfgpDPRB4RxpNDN64n5Ab5+/m9VRyqnWeP5tVdMkiOyHMCghw4wvQHNyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAAUdwQAAAAZc3IAQGNvbS5zY3l0bC5ldm90ZS5wcm90b2NvbC5pbnRlZ3JhdGlvbi52b3RpbmcubW9kZWwuQWxsb3dlZENvbnRlc3QiWqLrfd15KQIAA0wACl9jb250ZXN0SWRxAH4AA0wAC19lbGVjdGlvbklkcQB+AANMAAZfdm90ZWRxAH4AA3hwdAAGMDAwMTAxdAACMDF0AAEwc3EAfgAMcQB+AA50AAIwMnEAfgAQc3EAfgAMcQB+AA50AAIwM3EAfgAQc3EAfgAMdAAGMDAwMTA2dAACMDRxAH4AEHNxAH4ADHQABjAwMDAwMXQAAjA1cQB+ABBzcQB+AAxxAH4AFnQAAjA2cQB+ABBzcQB+AAxxAH4AFnEAfgAPcQB+ABBzcQB+AAx0AAYwMDAwNDdxAH4AF3EAfgAQc3EAfgAMcQB+AA50AAIxMHEAfgAQc3EAfgAMcQB+ABlxAH4AD3EAfgAQc3EAfgAMdAADaWQ3dAADaWQ1cQB+ABBzcQB+AAx0AARpZDExcQB+ACVxAH4AEHNxAH4ADHQABGlkMTN0AARpZDE3cQB+ABBzcQB+AAx0AAJOT3QAAkVVcQB+ABBzcQB+AAxxAH4AD3QAAUZxAH4AEHNxAH4ADHQABDE1MDRxAH4AD3EAfgAQc3EAfgAMdAAEMDEwMXQAAUtxAH4AEHNxAH4ADHQABDAyMDJxAH4ANHEAfgAQc3EAfgAMdAAEOTAxOHEAfgAPcQB+ABBzcQB+AAx0AAQ5MDE5cQB+AA9xAH4AEHh0AAZTQ1JFRU50AAYyMDA3MDF0AANpZDV0ACRlZGI0NDNlMi1jYjZmLTRmNDEtYmZjMy1hNGUwYzFiODhiNGJzcgA8Y29tLnNjeXRsLmV2b3RlLnByb3RvY29sLmludGVncmF0aW9uLnZvdGluZy5tb2RlbC5NaW5JRFRva2Vu4NaSE2I5z6UCAARKAANfdHNMAANfaWRxAH4AA0wABV9pbmZvcQB+AANMAAhfdm90ZXJJZHEAfgADeHAAAAEv2nmdsHQAJGY0ZDZjYjYzLTI2ZDktNDg0OS04MDI1LWQxOWQ2NDJjNzQ2MHQAbyNUdWUgTWF5IDEwIDE3OjE0OjU1IENFU1QgMjAxMQ0KdXNlcklkPTAzMDE1MjAwMjQyDQp0cz0xMzA1MDQwNDk0MDAwDQppZD1mNGQ2Y2I2My0yNmQ5LTQ4NDktODAyNS1kMTlkNjQyYzc0NjANCnQACzAzMDE1MjAwMjQydAAMVVNFUlBBU1NXT1JEc3IAPmNvbS5zY3l0bC5ldm90ZS5wcm90b2NvbC5pbnRlZ3JhdGlvbi52b3RpbmcubW9kZWwuUG9sbGluZ1BsYWNl1/fPlAehz24CAAdMAA1fYWRkcmVzc0xpbmUxcQB+AANMAA1fYWRkcmVzc0xpbmUycQB+AANMAA1fYWRkcmVzc0xpbmUzcQB+AANMAAVfbmFtZXEAfgADTAANX29wZW5pbmdIb3Vyc3EAfgACTAAJX3Bvc3RUb3ducQB+AANMAAtfcG9zdGFsQ29kZXEAfgADeHB0AA5BZGRyZXNzIGxpbmUgMXQADkFkZHJlc3MgbGluZSAydAAOQWRkcmVzcyBsaW5lIDN0AAROYW1lc3EAfgAKAAAAA3cEAAAACnNyAD5jb20uc2N5dGwuZXZvdGUucHJvdG9jb2wuaW50ZWdyYXRpb24udm90aW5nLm1vZGVsLk9wZW5pbmdIb3Vyc+1QMq6vw1dtAgADTAAEX2RheXEAfgADTAAIX2VuZFRpbWVxAH4AA0wACl9zdGFydFRpbWVxAH4AA3hwdAALT3BlbmluZyBkYXl0ABBPcGVuaW5nIGVuZCB0aW1ldAAST3BlbmluZyBzdGFydCB0aW1lc3EAfgBMcQB+AE5xAH4AT3EAfgBQc3EAfgBMcQB+AE5xAH4AT3EAfgBQeHQACFBvc3RUb3dudAALUG9zdGFsIENvZGV0AANTTVN0ABgyMDA3MDEuNDAuNDAuNDAuMDAwMDAwLjF0AAR0cnVldAAFRHVtbXlzcgBBY29tLnNjeXRsLmV2b3RlLnByb3RvY29sLmludGVncmF0aW9uLnZvdGluZy5tb2RlbC5Wb3RlcklkZW50aWZpZXLKvqZX4QyCdgIAAkwACF9zcGFyZUlkcQB+AANMAAhfdm90ZXJJZHEAfgADeHB0AAswMzAxNTIwMDI0MnEAfgBbcQB+AFhzcQB+AAoAAAAUdwQAAAAZc3IARGNvbS5zY3l0bC5ldm90ZS5wcm90b2NvbC5pbnRlZ3JhdGlvbi52b3RpbmcubW9kZWwuRWxlY3Rpb25Wb3RpbmdNb2RllMcp1WIDaxECAAJMAAtfZWxlY3Rpb25JZHEAfgADTAALX3ZvdGluZ01vZGVxAH4AA3hwcQB+AA90AAEzc3EAfgBdcQB+ABJxAH4AX3NxAH4AXXEAfgAUcQB+AF9zcQB+AF1xAH4AF3EAfgBfc3EAfgBdcQB+ABpxAH4AX3NxAH4AXXEAfgAccQB+AF9zcQB+AF1xAH4AD3EAfgBfc3EAfgBdcQB+ABdxAH4AX3NxAH4AXXEAfgAhdAABM3NxAH4AXXEAfgAPcQB+AF9zcQB+AF1xAH4AJXEAfgBoc3EAfgBdcQB+ACVxAH4AaHNxAH4AXXEAfgAqcQB+AGhzcQB+AF1xAH4ALXEAfgBoc3EAfgBdcQB+AC9xAH4AaHNxAH4AXXEAfgAPcQB+AF9zcQB+AF1xAH4ANHEAfgBoc3EAfgBdcQB+ADRxAH4AaHNxAH4AXXEAfgAPcQB+AF9zcQB+AF1xAH4AD3EAfgBfeA==,edb443e2-cb6f-4f41-bfc3-a4e0c1b88b4b,Njc3NTQ1MTU4OTM3NDQyNzE1ODQwMjkwMTU2MTQ3NjE5NjIyMzcwNzQwMTYxMTYz#n#NTUwOTg5NTA1MjY2OTgwNTE0MjIyMTkxOTc2ODU2MjMyMzExMTc1NDc1MjAyNjUx#n#ODQ1MzcyNzI5MDk5NjA4NTM5ODg5OTA3MzM4NTc4NzM1MDg5MjkwODEwMDE4MTQy#n#OTkwOTk0ODgwMzAwNDgyNjQ5NzkzMjQ5MDkzMzAwNzgyOTgwMjA5OTAwMTEzNDA0#n#MTA2NzU0NzQzODkzMDc0NjkzMzIwNTc0NTMyNzAzMDE2MDYxMzUwMzc1MTE2Mzc2#n#MDI1NTgxNjAyNjc3NzMxNDM3NzUyOTQyODgxOTQxMTA3NDE1NzcwNzM0NTU5OTYy#n#NDgxNTc5NDcwOTQ0NTg3MDQ4MDA2MjUzOTMyNTIzNTI3NTcxOTI2NzQ2MzkxNzk0#n#MDc3MTE4MTQzNDIyMDA1NTQ3MjM4Njg3NDExODQxNzIxMjY1NDAxMjYyMTYwODUx#n#OTk5NDY1NzEzODUzMDE5MDM4Mzg4NjMxOTQ0ODY1MzI3ODQ4ODEwNTU3NjI0NjQ4#n#NTcwNDY5NDQ3MjYyNDEyMTM4MzkyMjgwNjYyOTY1NTA4MTI4NDk5NjcwNjY2NTk2#n#MDcyMjIxNTk3MjkxNDUyNjI0Mjg0Njc1MTk5MDQyNDUxMTg1NDEwNTMxMDYyMDIx#n#OTE1NjkzOTc1NzU0MDgzNzM4NjY3Nzc4NTg5MjYyNzY5NTE1NDM2MjM5NzA2NDg3#n#NTkyMjE5NzUyOTM5NTEwODA1Mzc5NDk0MDEzOTcxMjMwNDUxODkyMXwyNTgxNDE0#n#NDgwODgxNzYyNTE1MTAxMTY4Mzk5NjY3NzU5NjgwNDI0MzY0MzQ5MTE4MjA1OTI3#n#NTE5NzMzMjI1NTU4MTMxMjU2Nzg1NzYyNjE4NTEyODgxMjkyNzc4MzQxNTEwMjgz#n#OTcyMTU1NjczODcyNjY1Mjk5NjE3MDMxNDAyMzE1ODg0NDEzMjcyNDA3OTk1ODM0#n#NDAxMDQyMTQ1OTkyOTI1Mjg2MjExODcxODcyODc4MTkwMDU5NTU3MDM4ODE2MTQ5#n#Njk4NTM0MTQ1OTk1MzYzODU1NDI1Mzk5Nzc1ODgwNzY0MTIwNzA0NDMwMTM4MDk1#n#OTIxMzg3MjkxMzAxMjQ5Mjk3NDQ1MzU0Mzc3MzE2NDE2NTc4MTM3MDMyODY5OTU0#n#NzE5MjM0MzkwNzA3MzE3MDU0MDk1OTE5NTI1Mjg1NDI5Mzc4NDEzMjA5MzYxMzY3#n#ODM4OTg5MTU4MjY2MzQ0MjUxNzk4MzIzODEwOTA2NDYyOTQ1ODI2MzU2NzIyMjA0#n#NjYzNDA2ODUwMTcyNDY5MDk1NTc2Mzk2MjkzMTM1NjQ3NzY3MTk4MjcwNTc3NDg2#n#OTEyNjk1OTY5OTgyMjQ0Nzc3MDk4NTcyMzcyMjM2MDAwNjA3ODAyNTExMDYwNzUx#n#MTk4MjAzNjI5OTE1OTYyNDI3Njc5MDc4MzYxNzAxNjc0NTM4OTMwMjM4OTAyNDYw#n#MjI5NzcwMTM2Nzc0NzE4Nzk1MTk1ODE3NjY4Njg4OTM0NTIyMDU4OTA0ODcwNjk0#n#NzYzNDM5NDU5Mzc0MzUyNTA3MTcyNDg2NzMxNDU0NzkwNg==#,UjNoNWNsVnR2NFVGM1Y2ck1uUE1yUjFrWWxCem1hV2RueFRrMFJ1NHU1M0hneWM0M0h5T1VhRG1IMG5kbG4xQgo5Uy9MWE4yYm5JOExDNDMwYkpOZDhnUnpIRi9YWjJJR0dBeHYyWDF0b0w3VERtOUJ0Tk1mK0VtSkpwd1BJcnhNCjZBN1RJUldJY2kzQW1Yd29yWDlha3pCakxxSWpZUkRqbGZUaVlUSDFiTGMybEcxV2NndGFCSUZVUi9WZzJJb0QKNUZoR1pTVFpBZ0ZSVksvcDdneWFNai95Qm43RXE5Vit2Z1pZcmNWb2Nic3krVXBVOGljZE84SDcxTVB2bi9qRwpsTzdrNFRTK2wvbUtYb28wQi8zSUJvVDRDckFWeXV2aXFlUkF4cFBHdFN6MFBNNjFZWDgwMEFmcUZCRk9TNVJWClFzKzF4Zks2RGttczFMMXBtemxmUGc9PQ==,f4d6cb63-26d9-4849-8025-d19d642c7460,1305040496609,MjM1MzY2Nzc1MTc5NzY3OTIwNjgzODE3MzE4NTc3MTU2MDM5NDQ3OTQwMzgyMjk5OTUyMTU3MTMwOTk4MjY3NzM4MTM2MzM2NDQyNjF8bnVsbHwtMjE5NzI2MjUyMDQ2OTAyNDI1Nzc1ODQyOTU0OTY3MzM4MDQ0NDUzNDE4NzcxOTAzODM4ODIzMjY4ODk4NDgwODEzOTQ1OTIxODY2NTI0NTE3MDk4MDg2NzY4MDA2OTE3NTg0NDE4MDI0NTgyMzU1Njg0MzU4NTkyMjk0OTEwODkxOTAwMzUyNDM5NjM3MTk1MjM2MzY4NDcwMTgwODYxMjYzNjkyOTM2Njg1MDg5NDQ0NjEzNTkxODc3OTAzODYwMzAyNjkzNTM2OTQ4NjI0MzA4MTg2Mzg4NjY3NDg1Nzc0ODUzMTY4NjcwODc2NDEyMTk5Mjk2NTM2MzMzNDAzNTk3NTYzNDI0NTM5MTg1ODAxMzQ5NDQ3MDY2MjY5OTQ0MTA4OTQ4NTI4ODcwOTA5MTc3NjkyNTk5MTQwNTA5NTg4Nzc0MTE5NzQ5NDQwNjY5ODQ3NDU2Mjc3NDg5NzYyNTgzOTMxNTMzNDgzMDMwODEzOTMyOTkwMTM1MzIxNTUzNzcwNjA3MTY2NzY2ODQ4ODc0Mjk5OTMyOTA2ODA5NzU3NDEzODk1MjAzMzQzNjYzMjczMDgxMzQ0Njg0OTUxMTkxMzU2NjE4NTMyNDYzMTQxNDExMTM2ODEyMjY5MTM1MDI1MTQyNjgxNTY4OTU2MDM1OTMzNzYyODE2NDUxMDQ5NTEyOTIxODAxNDUyNTAwMDY2NDczNTgzMTA0MTQ2OTM0MzM0MzU0MDI1OTU1NzA4MTAxMzIyOTQ1NTgwNDQ1Njk1MjgzODIxNDI3NjQ1MDQzOTcwNjA1NjI3MzEwMzA3OTY4MTkyMjYxNDEwNjI4ODk4NTU4Mjk2MDA0MzM5Mjk1MzA0MDMyOTEzMjI3MTY4MzY5MDg3MDE5MjU2MzEzNTI5ODYzNzI2NjA2NDgyNDA4NDc0,-----BEGIN CERTIFICATE-----#r##n#MIIDWTCCAkGgAwIBAgIFAI9U0jYwDQYJKoZIhvcNAQEFBQAwUjEbMBkGA1UECgwS#r##n#U2N5dGwgVU5JVCBURVNUIENBMQswCQYDVQQGEwJFUzESMBAGA1UEAwwJVU5JVCBU#r##n#RVNUMRIwEAYDVQQLDAlVTklUIFRFU1QwHhcNMTAwNTEwMTUwMjEwWhcNMjMwNTEw#r##n#MTUwMjEwWjB5MQwwCgYDVQQKDANPcmcxDjAMBgNVBAgMBVN0YXRlMRAwDgYDVQQG#r##n#EwdDb3VudHJ5MTgwNgYDVQQDDC9Wb3Rlcl9xTHZ5ekZhZVQ4TnMzRWppVjVvZ3gx#r##n#YmplY3c9X1NpZ25fXzIwMDcwMTENMAsGA1UECwwEVW5pdDCCASIwDQYJKoZIhvcN#r##n#AQEBBQADggEPADCCAQoCggEBALfZYnXFYoBLfKZTpW3Sf1xQpv6kKztSU8ZNiZ+z#r##n#Ppflv7eSd8SDq6dMIUA/PK4+2dDh+RCRo235puK6q7nTbO7rfQNUhKHdGF46ptdE#r##n#qP5BJxS9cl1aeRTuHJ2pOjwAeirO270/p6fDwAZLaZf+1X5wCzCl20VoXTTaHXUr#r##n#yIBwUMFGO3CQN81FZIHf0JGM/4fLSpaRCNk8hsKTgbaaY8MdERIXW0X0PtXjH7cy#r##n#B3F8U/dLPshdIgc2FThgdFjhquqPySd2+IVEF8DmbfmW6izBoizmiDSv2iWak0VU#r##n#+AWyjGLtgcbtvLDE0HMPXyNZ0/qitlN28DVNjli8sFMCMKUCAwEAAaMPMA0wCwYD#r##n#VR0PBAQDAgbAMA0GCSqGSIb3DQEBBQUAA4IBAQCZ4A7tJDyo6xwKGyIbrnlfZaL4#r##n#gHqsKArlDfePPwT++CtubD3inn42uNO2i6vzv4yrc2fLuESrD8Yijw0mqqAqdkrh#r##n#mu6Gxsxdku76kSlKqqHLxM/idlK/9d6Wayt9/Pkg/0nIdQZjkRrsbul39KBoqmLJ#r##n#5gCTFwY5PPjSDJJ7d/Wizm1Zk2Cg1/6bOTSmI30Avzezzqc37LJCJrBr+oOd3VRi#r##n#cq5P2c09fv19/PCyo+npkjEPuQ7AO2knCjgTJr/ECNMn0jdyg7e9Ffe/m2qqFjdv#r##n#343h04OJGBgmt/brv+kR5LL9as526OqQ6l4c+LM9G1p+XNuz2ErlScX+ZPsi#r##n#-----END CERTIFICATE-----,200701.47.02.0000.000000.0001,000101,03,200701,03015200242,CHANNEL_ID_UNCONTROLLED";
    private static final String OTHER_SAMPLE_LINE = "4028806a2fda69fd012fdab9ce9c0015,rO0ABXNyADtjb20uc2N5dGwuZXZvdGUucHJvdG9jb2wuaW50ZWdyYXRpb24udm90aW5nLm1vZGVsLkF1dGhUb2tlbvz+Wz5pky7LAgATSgAFX2FzSWRJABhfZXhwaXJhdGlvblRpbWVJbk1pbnV0ZXNKAANfdHNbAA9fYXV0aFNlcnZpY2VTaWd0AAJbQkwACV9jb250ZXN0c3QAEExqYXZhL3V0aWwvTGlzdDtMABpfY29udHJvbGxlZFJldHVybmNvZGVzVHlwZXQAEkxqYXZhL2xhbmcvU3RyaW5nO0wAEF9lbGVjdGlvbkV2ZW50SWRxAH4AA0wAC19lbGVjdGlvbklkcQB+AANMAANfaWRxAH4AA0wACV9pbnRUb2tlbnQAQUxjb20vc2N5dGwvZXZvdGUvcHJvdG9jb2wvaW50ZWdyYXRpb24vdm90aW5nL21vZGVsL0ludGVybmFsVG9rZW47TAAXX2ludFRva2VuRW50aXR5UHJvdmlkZXJxAH4AA0wADV9wb2xsaW5nUGxhY2V0AEBMY29tL3NjeXRsL2V2b3RlL3Byb3RvY29sL2ludGVncmF0aW9uL3ZvdGluZy9tb2RlbC9Qb2xsaW5nUGxhY2U7TAAcX3VuY29udHJvbGxlZFJldHVybmNvZGVzVHlwZXEAfgADTAAKX3ZvdGVyQXJlYXEAfgADTAAMX3ZvdGVyQ29tbUlkcQB+AANMAA9fdm90ZXJGaXJzdE5hbWVxAH4AA0wAEF92b3RlcklkZW50aWZpZXJ0AENMY29tL3NjeXRsL2V2b3RlL3Byb3RvY29sL2ludGVncmF0aW9uL3ZvdGluZy9tb2RlbC9Wb3RlcklkZW50aWZpZXI7TAAOX3ZvdGVyTGFzdE5hbWVxAH4AA0wADF92b3RpbmdNb2Rlc3EAfgACeHAAAAAAAAAIAAAAAB4AAAEv2rh9knVyAAJbQqzzF/gGCFTgAgAAeHAAAAEAeDii4DNQZYePV53jWzsPQZO1xpr40wc4Z69YfWjQNLvvLJPWa6qKWML0hD/4BcnmZFfpnwr/tCAwr+y71N+0WFrSL1Id+yLcYjvUuZrUQGWZMW4YCyq3cHUtKWEsbZJjS9bZHpPG8syDuwA3Zsxjco/rL4Tn097tflG0ux0wx0Uzfd849e30VyR4e8vRyEIA1dvLA5Izq1lrJ+PiarEE7Sky+9k8Wr9jgaGuTtU7HGbIX7mrR2uLG03a4yrJfHGZLRDW3/PDYicwaZwTDtHb+AzZOJCFHMBuzM7mbXZ6Wl3YaPIHvdFJ1if/oiyQIlQB1lOhgsIXqumByUGHg8At9HNyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAAUdwQAAAAZc3IAQGNvbS5zY3l0bC5ldm90ZS5wcm90b2NvbC5pbnRlZ3JhdGlvbi52b3RpbmcubW9kZWwuQWxsb3dlZENvbnRlc3QiWqLrfd15KQIAA0wACl9jb250ZXN0SWRxAH4AA0wAC19lbGVjdGlvbklkcQB+AANMAAZfdm90ZWRxAH4AA3hwdAAGMDAwMTAxdAACMDF0AAEwc3EAfgAMcQB+AA50AAIwMnEAfgAQc3EAfgAMcQB+AA50AAIwM3EAfgAQc3EAfgAMdAAGMDAwMTA2dAACMDRxAH4AEHNxAH4ADHQABjAwMDAwMXQAAjA1cQB+ABBzcQB+AAxxAH4AFnQAAjA2cQB+ABBzcQB+AAxxAH4AFnEAfgAPcQB+ABBzcQB+AAx0AAYwMDAwNDdxAH4AF3EAfgAQc3EAfgAMcQB+AA50AAIxMHEAfgAQc3EAfgAMcQB+ABlxAH4AD3EAfgAQc3EAfgAMdAADaWQ3dAADaWQ1cQB+ABBzcQB+AAx0AARpZDExcQB+ACVxAH4AEHNxAH4ADHQABGlkMTN0AARpZDE3cQB+ABBzcQB+AAx0AAJOT3QAAkVVcQB+ABBzcQB+AAxxAH4AD3QAAUZxAH4AEHNxAH4ADHQABDE1MDRxAH4AD3EAfgAQc3EAfgAMdAAEMDEwMXQAAUtxAH4AEHNxAH4ADHQABDAyMDJxAH4ANHEAfgAQc3EAfgAMdAAEOTAxOHEAfgAPcQB+ABBzcQB+AAx0AAQ5MDE5cQB+AA9xAH4AEHh0AAZTQ1JFRU50AAYyMDA3MDF0AANpZDV0ACQxNGJiZGVmNC00NTAwLTRiNmMtYWNiYy01OWJkM2Y2N2E0OTZzcgA8Y29tLnNjeXRsLmV2b3RlLnByb3RvY29sLmludGVncmF0aW9uLnZvdGluZy5tb2RlbC5NaW5JRFRva2Vu4NaSE2I5z6UCAARKAANfdHNMAANfaWRxAH4AA0wABV9pbmZvcQB+AANMAAhfdm90ZXJJZHEAfgADeHAAAAEv2rh2X3QAJDRkNDQ2MzZmLWE4YWItNDY3NS05MmUyLTNiNDI3ODJhZTFmN3QAbyNUdWUgTWF5IDEwIDE4OjIzOjMzIENFU1QgMjAxMQ0KdXNlcklkPTAzMDExNTAwMjkyDQp0cz0xMzA1MDQ0NjEyNzAzDQppZD00ZDQ0NjM2Zi1hOGFiLTQ2NzUtOTJlMi0zYjQyNzgyYWUxZjcNCnQACzAzMDExNTAwMjkydAAMVVNFUlBBU1NXT1JEc3IAPmNvbS5zY3l0bC5ldm90ZS5wcm90b2NvbC5pbnRlZ3JhdGlvbi52b3RpbmcubW9kZWwuUG9sbGluZ1BsYWNl1/fPlAehz24CAAdMAA1fYWRkcmVzc0xpbmUxcQB+AANMAA1fYWRkcmVzc0xpbmUycQB+AANMAA1fYWRkcmVzc0xpbmUzcQB+AANMAAVfbmFtZXEAfgADTAANX29wZW5pbmdIb3Vyc3EAfgACTAAJX3Bvc3RUb3ducQB+AANMAAtfcG9zdGFsQ29kZXEAfgADeHB0AA5BZGRyZXNzIGxpbmUgMXQADkFkZHJlc3MgbGluZSAydAAOQWRkcmVzcyBsaW5lIDN0AAROYW1lc3EAfgAKAAAAA3cEAAAACnNyAD5jb20uc2N5dGwuZXZvdGUucHJvdG9jb2wuaW50ZWdyYXRpb24udm90aW5nLm1vZGVsLk9wZW5pbmdIb3Vyc+1QMq6vw1dtAgADTAAEX2RheXEAfgADTAAIX2VuZFRpbWVxAH4AA0wACl9zdGFydFRpbWVxAH4AA3hwdAALT3BlbmluZyBkYXl0ABBPcGVuaW5nIGVuZCB0aW1ldAAST3BlbmluZyBzdGFydCB0aW1lc3EAfgBMcQB+AE5xAH4AT3EAfgBQc3EAfgBMcQB+AE5xAH4AT3EAfgBQeHQACFBvc3RUb3dudAALUG9zdGFsIENvZGV0AANTTVN0ABgyMDA3MDEuNDAuNDAuNDAuMDAwMDAwLjF0AAR0cnVldAAFRHVtbXlzcgBBY29tLnNjeXRsLmV2b3RlLnByb3RvY29sLmludGVncmF0aW9uLnZvdGluZy5tb2RlbC5Wb3RlcklkZW50aWZpZXLKvqZX4QyCdgIAAkwACF9zcGFyZUlkcQB+AANMAAhfdm90ZXJJZHEAfgADeHB0AAswMzAxMTUwMDI5MnEAfgBbcQB+AFhzcQB+AAoAAAAUdwQAAAAZc3IARGNvbS5zY3l0bC5ldm90ZS5wcm90b2NvbC5pbnRlZ3JhdGlvbi52b3RpbmcubW9kZWwuRWxlY3Rpb25Wb3RpbmdNb2RllMcp1WIDaxECAAJMAAtfZWxlY3Rpb25JZHEAfgADTAALX3ZvdGluZ01vZGVxAH4AA3hwcQB+AA90AAEzc3EAfgBdcQB+ABJxAH4AX3NxAH4AXXEAfgAUcQB+AF9zcQB+AF1xAH4AF3EAfgBfc3EAfgBdcQB+ABpxAH4AX3NxAH4AXXEAfgAccQB+AF9zcQB+AF1xAH4AD3EAfgBfc3EAfgBdcQB+ABdxAH4AX3NxAH4AXXEAfgAhdAABM3NxAH4AXXEAfgAPcQB+AF9zcQB+AF1xAH4AJXEAfgBoc3EAfgBdcQB+ACVxAH4AaHNxAH4AXXEAfgAqcQB+AGhzcQB+AF1xAH4ALXEAfgBoc3EAfgBdcQB+AC9xAH4AaHNxAH4AXXEAfgAPcQB+AF9zcQB+AF1xAH4ANHEAfgBoc3EAfgBdcQB+ADRxAH4AaHNxAH4AXXEAfgAPcQB+AF9zcQB+AF1xAH4AD3EAfgBfeA==,14bbdef4-4500-4b6c-acbc-59bd3f67a496,MTk1ODU0Mzc4NDQ4MTQzMzgyNjU0MzU4NjMwNzY4NTIwMjYxMDYyMDU1MzI3NDMx#n#OTU5NTgwNzk3ODE2MzYzOTAzMDQ2Njk0NTg5ODUyNDc2MTEzNzg0MDEyODc2MjY5#n#NDc5MTcyNTk2Mjk3MzE5OTM1NDM0NTE5NjI4NzYxODg4NjQ2NDg5MzIwODEwMjMw#n#MDAwMjQxNTQzOTIwNzY2MTIxOTQyMjU3Njg2ODgyODM2MzMzOTIwMjQ0ODg1MjU2#n#NTc4NzA3OTEzNjk1NzU2NjQxODM0MjkzNTA2MDM0NTM1ODU1MjcxOTY0NDM2MjQ0#n#MzU4NDgzMjQxMTcwNjg4NzIwODY2NTAyODcyMTg0NjU3MTQwODEwOTg0NDQ4NDI2#n#MDkxOTY5MDMwODk5OTcxOTQ4MDQ3NTU2OTk5MTY5ODk2NDg4NzExODYyMzg4NDg2#n#MzY0MDg2MTA0ODUyNDU4Mjg4ODYyNzA1MjA4MTY4MjE5NTA2NjA0ODQ1MTg3NTA0#n#MjU3MTE0MDU2NDQyMjY3MTI3ODIwNDI1MzA2Njk3MDAwMjM2ODA4NDEyMzcwNjYz#n#NzQ2MzU5NjUxNjk1ODY5MTU4NjM2OTMyMTI0MjcxNjU3MTU2MDQ0OTA0MjYxNDc5#n#ODQ3NjAyODY3MTE1ODM5OTcxOTQzMDcyNTEzNDQ5MDEyNzY2MzU4MjI1NjcxMzY5#n#ODc3NDQxNDU1MzU3NzcyNDIwMjI4OTA3NTI5MTIxMDcwODc1ODQyODgxMjU3Mzk1#n#NzEzNjA4NjgzOTkyMzU1NzIwNjMwNzAzMTE1NjA1MDM2MDQyNTkyODR8ODg3ODk3#n#Nzg4MDIzNzI1NTgwMTExOTIzMTEwMjA5NDQwODcxNDg1MDIyMzg1NDgwNDM0NTA1#n#MTM1MTY1NTE0NDk5NjYxNjYxNzY0Mzk0MjY5NTc0NzUzMDQ5MTc3NDk5MzQzMjM1#n#NTk3NDY0NTQ1MzIwNzc3MDg4MzIyODQ0MjU0MDQ4MjgyNDI3NTY1MTk2ODkzMzk0#n#MDY0NTIyMjQxNzkxMjYyOTYxNjA3MzM4NDQ2ODM4MTYxNTM3NjA1NDIyMjQ4OTAx#n#ODM2OTExMDQ4MTc1OTEwMjM1MDI5ODQ3MzQwNzg5NDA1OTQzMTAyOTQxOTUwMDMz#n#NTc1NDY0MjYyNDQ4Njc4NDcxODg0NjI1MjcxOTM0MTA3MTUwNjc2OTMyMDAwMDY5#n#ODIxMTc5MDIxODQ1NzY3ODg3MzY2NDkxODE4NjE2ODE3ODEyNjkyNjk4OTQ3MzQw#n#NjAyNDY0ODYzNTg3MjkwNTc4MTE1Nzg2NDU3NTA0MzY1MDM2NTkzNTc2MTY0ODUx#n#NTYwMTczMTQ1ODM5NDk4NDUzMTMyNzk0NDcyNzg4OTg1OTE4OTQyMTY5MTc2Njcy#n#NzkzNTU3MTUxNzcwOTk1NzM5NDMzNjYzODE2NzU4MDEzNzc5MzUyMjE0NDY4NDY4#n#ODk4MTU4MTk2MTE1ODczMTg4Nzg4MDY5ODQ0MzY5NTEwNTcwNDcwNjEyMjAwMTUy#n#NDc1MjQ3NjI1NzEzNDUyOTc2MTcyNTc3NjkzMTUxNjQwNTQ5NzA5MDU1NjY4NTAw#n#NTQ0OTc2NjE3MDMyODM0NjU4NDAyMjMwMDQyMjA4ODg4Ng==#,UGNxUFBRZmdWYlI1SVdwRWdRSHhWd1BrY2g3d3RTRjJKQ0ZIYUZvM2pjUWcvTzJoaDFpSVZ3d0RHcmRIV0xoMApRNW1WSkRoVy9VRUJIRUVKd21PTWJpYzJwcVExbWwxdlhZVDltM3JmZVdUZ3dOVFlvcGhEQVRVV3JMU3poSWpICklRMzd1QW9JRnNoVlA0QUEvQmtLalYvQmtzL0RVL0x0MkhvRkRIT1VQMkxCZkVJTFVEZXhYNVc2VDk5U3hpT0EKaWFXb2dCbGN0cU9XNVpPSDg2YXNDaFVtZHZBWXNZTWVHei9iYVAyYi90QnE3SkFwWnVHdjBGTUlRVnRUYU1UYwpRY2dKdzZqd2lhNW1ZSG0remN6dnBCek56bTNIaFd6YjNBYVFUcW9na1dnY05mYlNTZThsVDdkdk9sWEl1b3pRClBQY2ZkWWJpWlFkQUJNOHhKSjZoVXc9PQ==,4d44636f-a8ab-4675-92e2-3b42782ae1f7,1305044614546,LTI1MzAzNzYxNzcyNTg0MzgxNjgwOTAwODU2OTcwNTg0MzYxODg0ODM2NzkxMjUxNTAyNTc2NjQ3OTY2MDE5NjM3OTExNDMzNDcyNjc0fG51bGx8MjE0NTEzNzg3MjA3MTY5MjI1MTcxMTI2MDgyNDkxNzAyOTU2MjE0MTk1NTc3MDc2NzY3MzczOTMxMTI1MTI2NzA3MzYzMjE4NDQ5OTIxMDA2Njk5Nzc2MjI2MjgwNjU5MTg3MzE0MTYzOTQ0NTU5MDY4OTE1NDQyMzA0MTk5Nzc5NTQ0MDgyNzExNDExMzg5MjY2MjY3OTgwODk0MjYxNDM0NjE1MDkyNjg4ODI5NjE5MDg4Mjg2NjkzNDU5MjMyODYzMzU4NjQ3NzIzNjQyMDE4MTY0NTA2NDYxNzQwNTQ4MzEwODU2OTk3MTg5MTk0NDcyMTg5OTA0ODc2NDY5MTcwOTA4MjczNjcxNzc5MTYyMTkzMzQwNTY3NDQ4NDA4MjcwMTgyOTI4Mjc2MjA1MzgxMTUzMzQ4NTIzOTM5Mjc5MjYwOTk5NTE2ODQxNjM5MTUxNDI1MDQ3NDU3MTQ5MzE2MzEwODQxNjAyMjQwNjA3MTI5ODc2MDE4NzI0MzMzMTU2Nzc2MjE4NzM1MjM4MjYwODQ1NjcyNDUzMjc4MjExNDg0MzIyMjc5OTM2NjgwMDQ1NDU0NTI4OTI4NjEwNTQ0MzU4NjgyMzU4OTQzNTAzMTk3NzQ3ODMxOTM2Njc3NzcwNjc1MDkyNzE1Mzg0MDA0MDk2MTM3MjczODMwMTIyNDIwODQ5MzM0ODUyNTUyNjYzMzI5MDE0MTY3MDc3MzkxODQ5NjY2NzA2MjcxNjI5NDg1NzMwMDIwNzUxMTY4NDE5NTM5NTIyMDI2Nzc4MjM1ODE5MjE3MTY2MDg2MTk2NTA3NjkwOTg4NDk5NzYzMzkxMDI0NjMxNDA1MDkzNzkzOTUzNzIwMTYyOTI0NjE2MzA5ODAzNzE3NTQ1OTM1MjQ5NzAxODQ2ODYyMDczNzI1MjYw,-----BEGIN CERTIFICATE-----#r##n#MIIDWTCCAkGgAwIBAgIFALQYrIkwDQYJKoZIhvcNAQEFBQAwUjEbMBkGA1UECgwS#r##n#U2N5dGwgVU5JVCBURVNUIENBMQswCQYDVQQGEwJFUzESMBAGA1UEAwwJVU5JVCBU#r##n#RVNUMRIwEAYDVQQLDAlVTklUIFRFU1QwHhcNMTAwNTEwMTUwMjA5WhcNMjMwNTEw#r##n#MTUwMjA5WjB5MQwwCgYDVQQKDANPcmcxDjAMBgNVBAgMBVN0YXRlMRAwDgYDVQQG#r##n#EwdDb3VudHJ5MTgwNgYDVQQDDC9Wb3Rlcl85SzM2OWt0QUtKbGtHcnFwL0VsYmsx#r##n#WnBQeE09X1NpZ25fXzIwMDcwMTENMAsGA1UECwwEVW5pdDCCASIwDQYJKoZIhvcN#r##n#AQEBBQADggEPADCCAQoCggEBALWE+nPsU7oHTTVXdDYPAEUjv8cT3EiK0IL6qStZ#r##n#F4OuddRtVmUCilRuDaE1vKea7L6fMXW7p8wV5z1fWDbJNcRM42mwpZLVOGsr3JKv#r##n#i+VtINRqVxWKn5gS2wy+HUwQwZ/ORUePSaxgODOLNktatyE7fs4G7mtCu+ZRVLkQ#r##n#JrC34kNI7B1IGipt+El4EZb11werWq66OCqbil2K2v4CCoU0aP2c1sZxsqNmPkd6#r##n#60iZgEwOMRcF28lAkfTfl67d8h/wXGurjs6oTEcjekEqH4322wbFGy04NWpii7NW#r##n#UpIu2eOe8dcRrYDPW3Y61biUwcShpsHLk7kplKJZfAe7JjMCAwEAAaMPMA0wCwYD#r##n#VR0PBAQDAgbAMA0GCSqGSIb3DQEBBQUAA4IBAQBN4gYlJFiyGgK4pO3CTOSQDy5O#r##n#qmyYxwu6/yMHzxFu9MCAl0WqzlVCXmk7c20f5AgUU3PFM8/IMNsoJjXFnnTH/a6f#r##n#McHfNfPcDsL9+N2HqhCUmcWKKB7T+w8Hm9tG65P0D50itZcXbUyS8Q0TzLI0/HCu#r##n#e6YrauT0yHPXZKE4Zx85GulOBhzYG0cPDgC/iMJQpinFsgapFa5Lkg3/s6xutwrM#r##n#llLtBjLo2NGpuIz20QYUZ9G6ulUAkDC+lEcw4R8P3LVNsTGH1Z173Xxn0MxRtDzl#r##n#2C8cYXQ210u+HlhxLZMIQwXcD0WUpjqsnjqY00Z1hR0ze6tMJl5KZ1fGlAC/#r##n#-----END CERTIFICATE-----,200701.47.02.0000.000000.0001,000101,03,200701,03011500292,CHANNEL_ID_UNCONTROLLED";
    private VcsEncryptedVoteNotRegisteredByRcgIncident incident;

    /**
     * Creates an incident to test against.
     */
    @BeforeMethod
    public void createVcsEncryptedVoteNotRegisteredByRcgIncident() {
        incident = new VcsEncryptedVoteNotRegisteredByRcgIncident(new VcsEncryptedVote(
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
    public void mustBeEqualToAnotherVcsEncryptedVoteNotRegisteredByRcgIncidentWithTheSameEncryptedVote() {
        assertEquals(incident,
            new VcsEncryptedVoteNotRegisteredByRcgIncident(
                new VcsEncryptedVote(GIVEN_SAMPLE_LINE)));
    }

    /**
     * Verifies that the incident has the same hashCode as another incident with the same encrypted vote.
     */
    @Test
    public void mustHaveSameHashCodeAsAnotherVcsEncryptedVoteNotRegisteredByRcgIncidentWithTheSameEncryptedVote() {
        assertEquals(incident.hashCode(),
            new VcsEncryptedVoteNotRegisteredByRcgIncident(
                new VcsEncryptedVote(GIVEN_SAMPLE_LINE)).hashCode());
    }

    /**
     * Verifies that the incident is not equal to another incident with another encrypted vote.
     */
    @Test
    public void mustNotBeEqualToAnotherVcsEncryptedVoteNotRegisteredByRcgIncidentWithAnotherEncryptedVote() {
        assertFalse(incident.equals(
                new VcsEncryptedVoteNotRegisteredByRcgIncident(
                    new VcsEncryptedVote(OTHER_SAMPLE_LINE))));
    }

    /**
     * Verifies that toString produced a comma-separated line with the correct
     * values.
     */
    @Test
    public void toStringMustBeCommaSeparatedline() {
        assertEquals(incident.toString(),
            "VcsEncryptedVoteNotRegisteredByRcgIncident," + GIVEN_UUID);
    }
}
