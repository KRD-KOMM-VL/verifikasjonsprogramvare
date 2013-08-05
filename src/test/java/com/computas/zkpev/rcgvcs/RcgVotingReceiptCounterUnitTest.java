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
package com.computas.zkpev.rcgvcs;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * Unit tests on the RcgVotingReceiptCounter class.
 *
 * @version $Id: RcgVotingReceiptCounterUnitTest.java 11011 2011-10-20 08:26:55Z fvl $
 */
public class RcgVotingReceiptCounterUnitTest {
    private static final String SAMPLE_VOTING_RECEIPT_LINE = "4028806a2fde07b5012fde439de0000a,7a75e04b-6e7e-4430-b0d3-ae5637c2f08d,rO0ABXNyAD1jb20uc2N5dGwuZXZvdGUucHJvdG9jb2wuaW50ZWdyYXRpb24udm90aW5nLm1vZGVsLlJlY2VpcHRCZWFuw7/82u7t7McCAAhKAAZfcmNnSURKAApfdGltZXN0YW1wTAAKX2NvbnRlc3RJZHQAEkxqYXZhL2xhbmcvU3RyaW5nO0wAEF9lbGVjdGlvbkV2ZW50SWRxAH4AAUwAC19lbGVjdGlvbklkcQB+AAFbABFfcmVjZWlwdFNpZ25hdHVyZXQAAltCTAATX3JldHVybkNvZGVzTWVzc2FnZXEAfgABWwAOX3ZvdGluZ1JlY2VpcHRxAH4AAnhwAAAAAAAAH5wAAAEv3kOdknQABjAwMDEwNnQABjIwMDcwMXQAAjA2dXIAAltCrPMX+AYIVOACAAB4cAAAAQArbVBU2jABOoExcUaxZfWRJpSeZUuVdjZG9qWy9dJzHikPfljkVYDSLD36WdTB6D+c3uGgSZsEZilQs9+D8f/k+xIFJLVsfywCl/Exrfjsyn9z/hfJdbzLXD/vxlmWPX7LRyLu0pkt6GeS7pQ/7mvWTu1zrC4Km0rs9A6AYwoDzGCkXUk4LTAC3zY6K3r2Roxnz/XGACEOv+XyzgR5XnG/GP7hpabMknu7bO2PNm/X8UJUrPTlex4mt49p84WRdBRuT4tGlMX+ZIK7H2uWMaeJMp7eMbwmQQjHXqs8Qs46wP+s09nb2m6du3Si2D5IBa/Wu5ppitTmB3wdfqFPq+O8cHVxAH4ABwAAABxrTU5CWXBCOU81VytSeTVsUW9hVGhNL1dRK0E9,eba388f8-a494-4b33-a8f4-b3f98f900885,8092,a01OQllwQjlPNVcrUnk1bFFvYVRoTS9XUStBPQ==,000106,06,200701,200701.40.40.40.000000.1";
    private static final String SAMPLE_ENCRYPTED_VOTE_LINE = "4028806a2fda69fd012fda7a46b10012,rO0ABXNyADtjb20uc2N5dGwuZXZvdGUucHJvdG9jb2wuaW50ZWdyYXRpb24udm90aW5nLm1vZGVsLkF1dGhUb2tlbvz+Wz5pky7LAgATSgAFX2FzSWRJABhfZXhwaXJhdGlvblRpbWVJbk1pbnV0ZXNKAANfdHNbAA9fYXV0aFNlcnZpY2VTaWd0AAJbQkwACV9jb250ZXN0c3QAEExqYXZhL3V0aWwvTGlzdDtMABpfY29udHJvbGxlZFJldHVybmNvZGVzVHlwZXQAEkxqYXZhL2xhbmcvU3RyaW5nO0wAEF9lbGVjdGlvbkV2ZW50SWRxAH4AA0wAC19lbGVjdGlvbklkcQB+AANMAANfaWRxAH4AA0wACV9pbnRUb2tlbnQAQUxjb20vc2N5dGwvZXZvdGUvcHJvdG9jb2wvaW50ZWdyYXRpb24vdm90aW5nL21vZGVsL0ludGVybmFsVG9rZW47TAAXX2ludFRva2VuRW50aXR5UHJvdmlkZXJxAH4AA0wADV9wb2xsaW5nUGxhY2V0AEBMY29tL3NjeXRsL2V2b3RlL3Byb3RvY29sL2ludGVncmF0aW9uL3ZvdGluZy9tb2RlbC9Qb2xsaW5nUGxhY2U7TAAcX3VuY29udHJvbGxlZFJldHVybmNvZGVzVHlwZXEAfgADTAAKX3ZvdGVyQXJlYXEAfgADTAAMX3ZvdGVyQ29tbUlkcQB+AANMAA9fdm90ZXJGaXJzdE5hbWVxAH4AA0wAEF92b3RlcklkZW50aWZpZXJ0AENMY29tL3NjeXRsL2V2b3RlL3Byb3RvY29sL2ludGVncmF0aW9uL3ZvdGluZy9tb2RlbC9Wb3RlcklkZW50aWZpZXI7TAAOX3ZvdGVyTGFzdE5hbWVxAH4AA0wADF92b3RpbmdNb2Rlc3EAfgACeHAAAAAAAAAIAAAAAB4AAAEv2nmn4XVyAAJbQqzzF/gGCFTgAgAAeHAAAAEALWwj6PRNzzPhkIpBQ85NfJKKkayUOIRY51k+onz95FyzXwwNH/a/rupMUh1cIjCac2o+X3l2zxIn2h2C9HQtVeBJBCkJ9POELMlbGqh6gGvQxF3mx0FuOO0Eg8MQAIhFtm5QSBIOZF2DM3TZxJaAHTl9vuJfp6ME7BD1w7jMMZL54jUUD0+UHs91NOPqbq2ik8P4r3Cn7TNhe/GZa0kN81VIp4viD9hsQgRqArAk0MXrChE6qFXvFyA48f+h60X/9+rarZYBkbsQDg2/+lpa2Yu3fXuhRF3vQfgpDPRB4RxpNDN64n5Ab5+/m9VRyqnWeP5tVdMkiOyHMCghw4wvQHNyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAAUdwQAAAAZc3IAQGNvbS5zY3l0bC5ldm90ZS5wcm90b2NvbC5pbnRlZ3JhdGlvbi52b3RpbmcubW9kZWwuQWxsb3dlZENvbnRlc3QiWqLrfd15KQIAA0wACl9jb250ZXN0SWRxAH4AA0wAC19lbGVjdGlvbklkcQB+AANMAAZfdm90ZWRxAH4AA3hwdAAGMDAwMTAxdAACMDF0AAEwc3EAfgAMcQB+AA50AAIwMnEAfgAQc3EAfgAMcQB+AA50AAIwM3EAfgAQc3EAfgAMdAAGMDAwMTA2dAACMDRxAH4AEHNxAH4ADHQABjAwMDAwMXQAAjA1cQB+ABBzcQB+AAxxAH4AFnQAAjA2cQB+ABBzcQB+AAxxAH4AFnEAfgAPcQB+ABBzcQB+AAx0AAYwMDAwNDdxAH4AF3EAfgAQc3EAfgAMcQB+AA50AAIxMHEAfgAQc3EAfgAMcQB+ABlxAH4AD3EAfgAQc3EAfgAMdAADaWQ3dAADaWQ1cQB+ABBzcQB+AAx0AARpZDExcQB+ACVxAH4AEHNxAH4ADHQABGlkMTN0AARpZDE3cQB+ABBzcQB+AAx0AAJOT3QAAkVVcQB+ABBzcQB+AAxxAH4AD3QAAUZxAH4AEHNxAH4ADHQABDE1MDRxAH4AD3EAfgAQc3EAfgAMdAAEMDEwMXQAAUtxAH4AEHNxAH4ADHQABDAyMDJxAH4ANHEAfgAQc3EAfgAMdAAEOTAxOHEAfgAPcQB+ABBzcQB+AAx0AAQ5MDE5cQB+AA9xAH4AEHh0AAZTQ1JFRU50AAYyMDA3MDF0AANpZDV0ACRlZGI0NDNlMi1jYjZmLTRmNDEtYmZjMy1hNGUwYzFiODhiNGJzcgA8Y29tLnNjeXRsLmV2b3RlLnByb3RvY29sLmludGVncmF0aW9uLnZvdGluZy5tb2RlbC5NaW5JRFRva2Vu4NaSE2I5z6UCAARKAANfdHNMAANfaWRxAH4AA0wABV9pbmZvcQB+AANMAAhfdm90ZXJJZHEAfgADeHAAAAEv2nmdsHQAJGY0ZDZjYjYzLTI2ZDktNDg0OS04MDI1LWQxOWQ2NDJjNzQ2MHQAbyNUdWUgTWF5IDEwIDE3OjE0OjU1IENFU1QgMjAxMQ0KdXNlcklkPTAzMDE1MjAwMjQyDQp0cz0xMzA1MDQwNDk0MDAwDQppZD1mNGQ2Y2I2My0yNmQ5LTQ4NDktODAyNS1kMTlkNjQyYzc0NjANCnQACzAzMDE1MjAwMjQydAAMVVNFUlBBU1NXT1JEc3IAPmNvbS5zY3l0bC5ldm90ZS5wcm90b2NvbC5pbnRlZ3JhdGlvbi52b3RpbmcubW9kZWwuUG9sbGluZ1BsYWNl1/fPlAehz24CAAdMAA1fYWRkcmVzc0xpbmUxcQB+AANMAA1fYWRkcmVzc0xpbmUycQB+AANMAA1fYWRkcmVzc0xpbmUzcQB+AANMAAVfbmFtZXEAfgADTAANX29wZW5pbmdIb3Vyc3EAfgACTAAJX3Bvc3RUb3ducQB+AANMAAtfcG9zdGFsQ29kZXEAfgADeHB0AA5BZGRyZXNzIGxpbmUgMXQADkFkZHJlc3MgbGluZSAydAAOQWRkcmVzcyBsaW5lIDN0AAROYW1lc3EAfgAKAAAAA3cEAAAACnNyAD5jb20uc2N5dGwuZXZvdGUucHJvdG9jb2wuaW50ZWdyYXRpb24udm90aW5nLm1vZGVsLk9wZW5pbmdIb3Vyc+1QMq6vw1dtAgADTAAEX2RheXEAfgADTAAIX2VuZFRpbWVxAH4AA0wACl9zdGFydFRpbWVxAH4AA3hwdAALT3BlbmluZyBkYXl0ABBPcGVuaW5nIGVuZCB0aW1ldAAST3BlbmluZyBzdGFydCB0aW1lc3EAfgBMcQB+AE5xAH4AT3EAfgBQc3EAfgBMcQB+AE5xAH4AT3EAfgBQeHQACFBvc3RUb3dudAALUG9zdGFsIENvZGV0AANTTVN0ABgyMDA3MDEuNDAuNDAuNDAuMDAwMDAwLjF0AAR0cnVldAAFRHVtbXlzcgBBY29tLnNjeXRsLmV2b3RlLnByb3RvY29sLmludGVncmF0aW9uLnZvdGluZy5tb2RlbC5Wb3RlcklkZW50aWZpZXLKvqZX4QyCdgIAAkwACF9zcGFyZUlkcQB+AANMAAhfdm90ZXJJZHEAfgADeHB0AAswMzAxNTIwMDI0MnEAfgBbcQB+AFhzcQB+AAoAAAAUdwQAAAAZc3IARGNvbS5zY3l0bC5ldm90ZS5wcm90b2NvbC5pbnRlZ3JhdGlvbi52b3RpbmcubW9kZWwuRWxlY3Rpb25Wb3RpbmdNb2RllMcp1WIDaxECAAJMAAtfZWxlY3Rpb25JZHEAfgADTAALX3ZvdGluZ01vZGVxAH4AA3hwcQB+AA90AAEzc3EAfgBdcQB+ABJxAH4AX3NxAH4AXXEAfgAUcQB+AF9zcQB+AF1xAH4AF3EAfgBfc3EAfgBdcQB+ABpxAH4AX3NxAH4AXXEAfgAccQB+AF9zcQB+AF1xAH4AD3EAfgBfc3EAfgBdcQB+ABdxAH4AX3NxAH4AXXEAfgAhdAABM3NxAH4AXXEAfgAPcQB+AF9zcQB+AF1xAH4AJXEAfgBoc3EAfgBdcQB+ACVxAH4AaHNxAH4AXXEAfgAqcQB+AGhzcQB+AF1xAH4ALXEAfgBoc3EAfgBdcQB+AC9xAH4AaHNxAH4AXXEAfgAPcQB+AF9zcQB+AF1xAH4ANHEAfgBoc3EAfgBdcQB+ADRxAH4AaHNxAH4AXXEAfgAPcQB+AF9zcQB+AF1xAH4AD3EAfgBfeA==,edb443e2-cb6f-4f41-bfc3-a4e0c1b88b4b,Njc3NTQ1MTU4OTM3NDQyNzE1ODQwMjkwMTU2MTQ3NjE5NjIyMzcwNzQwMTYxMTYz#n#NTUwOTg5NTA1MjY2OTgwNTE0MjIyMTkxOTc2ODU2MjMyMzExMTc1NDc1MjAyNjUx#n#ODQ1MzcyNzI5MDk5NjA4NTM5ODg5OTA3MzM4NTc4NzM1MDg5MjkwODEwMDE4MTQy#n#OTkwOTk0ODgwMzAwNDgyNjQ5NzkzMjQ5MDkzMzAwNzgyOTgwMjA5OTAwMTEzNDA0#n#MTA2NzU0NzQzODkzMDc0NjkzMzIwNTc0NTMyNzAzMDE2MDYxMzUwMzc1MTE2Mzc2#n#MDI1NTgxNjAyNjc3NzMxNDM3NzUyOTQyODgxOTQxMTA3NDE1NzcwNzM0NTU5OTYy#n#NDgxNTc5NDcwOTQ0NTg3MDQ4MDA2MjUzOTMyNTIzNTI3NTcxOTI2NzQ2MzkxNzk0#n#MDc3MTE4MTQzNDIyMDA1NTQ3MjM4Njg3NDExODQxNzIxMjY1NDAxMjYyMTYwODUx#n#OTk5NDY1NzEzODUzMDE5MDM4Mzg4NjMxOTQ0ODY1MzI3ODQ4ODEwNTU3NjI0NjQ4#n#NTcwNDY5NDQ3MjYyNDEyMTM4MzkyMjgwNjYyOTY1NTA4MTI4NDk5NjcwNjY2NTk2#n#MDcyMjIxNTk3MjkxNDUyNjI0Mjg0Njc1MTk5MDQyNDUxMTg1NDEwNTMxMDYyMDIx#n#OTE1NjkzOTc1NzU0MDgzNzM4NjY3Nzc4NTg5MjYyNzY5NTE1NDM2MjM5NzA2NDg3#n#NTkyMjE5NzUyOTM5NTEwODA1Mzc5NDk0MDEzOTcxMjMwNDUxODkyMXwyNTgxNDE0#n#NDgwODgxNzYyNTE1MTAxMTY4Mzk5NjY3NzU5NjgwNDI0MzY0MzQ5MTE4MjA1OTI3#n#NTE5NzMzMjI1NTU4MTMxMjU2Nzg1NzYyNjE4NTEyODgxMjkyNzc4MzQxNTEwMjgz#n#OTcyMTU1NjczODcyNjY1Mjk5NjE3MDMxNDAyMzE1ODg0NDEzMjcyNDA3OTk1ODM0#n#NDAxMDQyMTQ1OTkyOTI1Mjg2MjExODcxODcyODc4MTkwMDU5NTU3MDM4ODE2MTQ5#n#Njk4NTM0MTQ1OTk1MzYzODU1NDI1Mzk5Nzc1ODgwNzY0MTIwNzA0NDMwMTM4MDk1#n#OTIxMzg3MjkxMzAxMjQ5Mjk3NDQ1MzU0Mzc3MzE2NDE2NTc4MTM3MDMyODY5OTU0#n#NzE5MjM0MzkwNzA3MzE3MDU0MDk1OTE5NTI1Mjg1NDI5Mzc4NDEzMjA5MzYxMzY3#n#ODM4OTg5MTU4MjY2MzQ0MjUxNzk4MzIzODEwOTA2NDYyOTQ1ODI2MzU2NzIyMjA0#n#NjYzNDA2ODUwMTcyNDY5MDk1NTc2Mzk2MjkzMTM1NjQ3NzY3MTk4MjcwNTc3NDg2#n#OTEyNjk1OTY5OTgyMjQ0Nzc3MDk4NTcyMzcyMjM2MDAwNjA3ODAyNTExMDYwNzUx#n#MTk4MjAzNjI5OTE1OTYyNDI3Njc5MDc4MzYxNzAxNjc0NTM4OTMwMjM4OTAyNDYw#n#MjI5NzcwMTM2Nzc0NzE4Nzk1MTk1ODE3NjY4Njg4OTM0NTIyMDU4OTA0ODcwNjk0#n#NzYzNDM5NDU5Mzc0MzUyNTA3MTcyNDg2NzMxNDU0NzkwNg==#,UjNoNWNsVnR2NFVGM1Y2ck1uUE1yUjFrWWxCem1hV2RueFRrMFJ1NHU1M0hneWM0M0h5T1VhRG1IMG5kbG4xQgo5Uy9MWE4yYm5JOExDNDMwYkpOZDhnUnpIRi9YWjJJR0dBeHYyWDF0b0w3VERtOUJ0Tk1mK0VtSkpwd1BJcnhNCjZBN1RJUldJY2kzQW1Yd29yWDlha3pCakxxSWpZUkRqbGZUaVlUSDFiTGMybEcxV2NndGFCSUZVUi9WZzJJb0QKNUZoR1pTVFpBZ0ZSVksvcDdneWFNai95Qm43RXE5Vit2Z1pZcmNWb2Nic3krVXBVOGljZE84SDcxTVB2bi9qRwpsTzdrNFRTK2wvbUtYb28wQi8zSUJvVDRDckFWeXV2aXFlUkF4cFBHdFN6MFBNNjFZWDgwMEFmcUZCRk9TNVJWClFzKzF4Zks2RGttczFMMXBtemxmUGc9PQ==,f4d6cb63-26d9-4849-8025-d19d642c7460,1305040496609,MjM1MzY2Nzc1MTc5NzY3OTIwNjgzODE3MzE4NTc3MTU2MDM5NDQ3OTQwMzgyMjk5OTUyMTU3MTMwOTk4MjY3NzM4MTM2MzM2NDQyNjF8bnVsbHwtMjE5NzI2MjUyMDQ2OTAyNDI1Nzc1ODQyOTU0OTY3MzM4MDQ0NDUzNDE4NzcxOTAzODM4ODIzMjY4ODk4NDgwODEzOTQ1OTIxODY2NTI0NTE3MDk4MDg2NzY4MDA2OTE3NTg0NDE4MDI0NTgyMzU1Njg0MzU4NTkyMjk0OTEwODkxOTAwMzUyNDM5NjM3MTk1MjM2MzY4NDcwMTgwODYxMjYzNjkyOTM2Njg1MDg5NDQ0NjEzNTkxODc3OTAzODYwMzAyNjkzNTM2OTQ4NjI0MzA4MTg2Mzg4NjY3NDg1Nzc0ODUzMTY4NjcwODc2NDEyMTk5Mjk2NTM2MzMzNDAzNTk3NTYzNDI0NTM5MTg1ODAxMzQ5NDQ3MDY2MjY5OTQ0MTA4OTQ4NTI4ODcwOTA5MTc3NjkyNTk5MTQwNTA5NTg4Nzc0MTE5NzQ5NDQwNjY5ODQ3NDU2Mjc3NDg5NzYyNTgzOTMxNTMzNDgzMDMwODEzOTMyOTkwMTM1MzIxNTUzNzcwNjA3MTY2NzY2ODQ4ODc0Mjk5OTMyOTA2ODA5NzU3NDEzODk1MjAzMzQzNjYzMjczMDgxMzQ0Njg0OTUxMTkxMzU2NjE4NTMyNDYzMTQxNDExMTM2ODEyMjY5MTM1MDI1MTQyNjgxNTY4OTU2MDM1OTMzNzYyODE2NDUxMDQ5NTEyOTIxODAxNDUyNTAwMDY2NDczNTgzMTA0MTQ2OTM0MzM0MzU0MDI1OTU1NzA4MTAxMzIyOTQ1NTgwNDQ1Njk1MjgzODIxNDI3NjQ1MDQzOTcwNjA1NjI3MzEwMzA3OTY4MTkyMjYxNDEwNjI4ODk4NTU4Mjk2MDA0MzM5Mjk1MzA0MDMyOTEzMjI3MTY4MzY5MDg3MDE5MjU2MzEzNTI5ODYzNzI2NjA2NDgyNDA4NDc0,-----BEGIN CERTIFICATE-----#r##n#MIIDWTCCAkGgAwIBAgIFAI9U0jYwDQYJKoZIhvcNAQEFBQAwUjEbMBkGA1UECgwS#r##n#U2N5dGwgVU5JVCBURVNUIENBMQswCQYDVQQGEwJFUzESMBAGA1UEAwwJVU5JVCBU#r##n#RVNUMRIwEAYDVQQLDAlVTklUIFRFU1QwHhcNMTAwNTEwMTUwMjEwWhcNMjMwNTEw#r##n#MTUwMjEwWjB5MQwwCgYDVQQKDANPcmcxDjAMBgNVBAgMBVN0YXRlMRAwDgYDVQQG#r##n#EwdDb3VudHJ5MTgwNgYDVQQDDC9Wb3Rlcl9xTHZ5ekZhZVQ4TnMzRWppVjVvZ3gx#r##n#YmplY3c9X1NpZ25fXzIwMDcwMTENMAsGA1UECwwEVW5pdDCCASIwDQYJKoZIhvcN#r##n#AQEBBQADggEPADCCAQoCggEBALfZYnXFYoBLfKZTpW3Sf1xQpv6kKztSU8ZNiZ+z#r##n#Ppflv7eSd8SDq6dMIUA/PK4+2dDh+RCRo235puK6q7nTbO7rfQNUhKHdGF46ptdE#r##n#qP5BJxS9cl1aeRTuHJ2pOjwAeirO270/p6fDwAZLaZf+1X5wCzCl20VoXTTaHXUr#r##n#yIBwUMFGO3CQN81FZIHf0JGM/4fLSpaRCNk8hsKTgbaaY8MdERIXW0X0PtXjH7cy#r##n#B3F8U/dLPshdIgc2FThgdFjhquqPySd2+IVEF8DmbfmW6izBoizmiDSv2iWak0VU#r##n#+AWyjGLtgcbtvLDE0HMPXyNZ0/qitlN28DVNjli8sFMCMKUCAwEAAaMPMA0wCwYD#r##n#VR0PBAQDAgbAMA0GCSqGSIb3DQEBBQUAA4IBAQCZ4A7tJDyo6xwKGyIbrnlfZaL4#r##n#gHqsKArlDfePPwT++CtubD3inn42uNO2i6vzv4yrc2fLuESrD8Yijw0mqqAqdkrh#r##n#mu6Gxsxdku76kSlKqqHLxM/idlK/9d6Wayt9/Pkg/0nIdQZjkRrsbul39KBoqmLJ#r##n#5gCTFwY5PPjSDJJ7d/Wizm1Zk2Cg1/6bOTSmI30Avzezzqc37LJCJrBr+oOd3VRi#r##n#cq5P2c09fv19/PCyo+npkjEPuQ7AO2knCjgTJr/ECNMn0jdyg7e9Ffe/m2qqFjdv#r##n#343h04OJGBgmt/brv+kR5LL9as526OqQ6l4c+LM9G1p+XNuz2ErlScX+ZPsi#r##n#-----END CERTIFICATE-----,200701.47.02.0000.000000.0001,000101,03,200701,03015200242,CHANNEL_ID_UNCONTROLLED";
    private RcgVotingReceiptCounter counter;

    /**
     * Creates a counter object to run the tests against.
     */
    @BeforeMethod
    public void createRcgVotingReceiptCounter() {
        counter = new RcgVotingReceiptCounter(new RcgVotingReceipt(
                    SAMPLE_VOTING_RECEIPT_LINE));
    }

    /**
     * Verifies that a counter has by default 0 matches.
     */
    @Test
    public void aVotingReceiptCounterMustHaveZeroMatchesByDefault() {
        assertEquals(counter.getNoOfMatches(), 0);
    }

    /**
     * Verifies that a counter has 1 match when an encrypted vote has been
     * added.
     */
    @Test
    public void noOfMatchesShouldBeOneAfterAddingAnEncryptedVote() {
        counter.addVcsEncryptedVote(new VcsEncryptedVote(
                SAMPLE_ENCRYPTED_VOTE_LINE));
        assertEquals(counter.getNoOfMatches(), 1);
    }

    /**
     * Verifies that a counter returns an empty list by default.
     */
    @Test
    public void mustReturnAnEmptyListOfVcsEncryptedVotesByDefault() {
        assertTrue(counter.getVcsEncryptedVotes().isEmpty());
    }

    /**
     * Verifies that the counter returns the encrypted vote that has been added.
     */
    @Test
    public void mustReturnTheVcsEncryptedVoteAfterItHasBeenAdded() {
        counter.addVcsEncryptedVote(new VcsEncryptedVote(
                SAMPLE_ENCRYPTED_VOTE_LINE));
        assertTrue(counter.getVcsEncryptedVotes()
                          .contains(new VcsEncryptedVote(
                    SAMPLE_ENCRYPTED_VOTE_LINE)));
    }

    /**
     * Verifies that the constructor sets the RCG voting receipt correctly.
     */
    @Test
    public void constructorMustSetTheRcgVotingReceipt() {
        assertEquals(counter.getRcgVotingReceipt(),
            new RcgVotingReceipt(SAMPLE_VOTING_RECEIPT_LINE));
    }
}
