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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import java.security.NoSuchAlgorithmException;


/**
 * Class to generate test files for the NizkRcgVcs class.
 *
 */
public final class NizkpRcgVcsTestFileGenerator {
    private static final int[] TEST_FILE_LENGTHS = new int[] {
            3, 10, 30, 100, 300, 1000, 3000, 10000, 30000, 100000
        };
    private final VcsEncryptedVoteLineGenerator vcsEncryptedVoteLineGenerator;

    private NizkpRcgVcsTestFileGenerator() {
        vcsEncryptedVoteLineGenerator = new VcsEncryptedVoteLineGenerator();
    }

    /**
    * Main entry.
    *
    * @param args Arguments. Not used.
    * @throws FileNotFoundException Should not be thrown.
     * @throws UnsupportedEncodingException  Should not be thrown.
     * @throws NoSuchAlgorithmException  Should not be thrown.
    */
    public static void main(String[] args)
        throws FileNotFoundException, NoSuchAlgorithmException,
            UnsupportedEncodingException {
        NizkpRcgVcsTestFileGenerator generator = new NizkpRcgVcsTestFileGenerator();

        for (int i : TEST_FILE_LENGTHS) {
            generator.generate(i);
        }
    }

    private void generate(int n)
        throws FileNotFoundException, NoSuchAlgorithmException,
            UnsupportedEncodingException {
        PrintWriter rcgFile = createPrintWriter(String.format(
                    "NizkpRcgVcsRcgVotingReceiptsTestFile-%d.csv", n));
        PrintWriter vcsFile = createPrintWriter(String.format(
                    "NizkpRcgVcsVcsEncryptedVotesTestFile-%d.csv", n));

        for (int i = 0; i < n; i++) {
            addNewElement(i, rcgFile, vcsFile);
        }

        rcgFile.close();
        vcsFile.close();
    }

    private void addNewElement(int i, PrintWriter rcgFile, PrintWriter vcsFile)
        throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String encryptedVoteLine = vcsEncryptedVoteLineGenerator.generateVcsEncryptedVoteLine(i);
        vcsFile.println(encryptedVoteLine);

        String votingReceiptLine = generateVotingReceiptLine(encryptedVoteLine,
                i);
        rcgFile.println(votingReceiptLine);
    }

    private String generateVotingReceiptLine(String encryptedVoteLine, int i)
        throws NoSuchAlgorithmException, UnsupportedEncodingException {
        VcsEncryptedVote encryptedVote = new VcsEncryptedVote(encryptedVoteLine);

        return String.format("%d,7a75e04b-6e7e-4430-b0d3-ae5637c2f08d,rO0ABXNyAD1jb20uc2N5dGwuZXZvdGUucHJvdG9jb2wuaW50ZWdyYXRpb24udm90aW5nLm1vZGVsLlJlY2VpcHRCZWFuw7/82u7t7McCAAhKAAZfcmNnSURKAApfdGltZXN0YW1wTAAKX2NvbnRlc3RJZHQAEkxqYXZhL2xhbmcvU3RyaW5nO0wAEF9lbGVjdGlvbkV2ZW50SWRxAH4AAUwAC19lbGVjdGlvbklkcQB+AAFbABFfcmVjZWlwdFNpZ25hdHVyZXQAAltCTAATX3JldHVybkNvZGVzTWVzc2FnZXEAfgABWwAOX3ZvdGluZ1JlY2VpcHRxAH4AAnhwAAAAAAAAH5wAAAEv3kOdknQABjAwMDEwNnQABjIwMDcwMXQAAjA2dXIAAltCrPMX+AYIVOACAAB4cAAAAQArbVBU2jABOoExcUaxZfWRJpSeZUuVdjZG9qWy9dJzHikPfljkVYDSLD36WdTB6D+c3uGgSZsEZilQs9+D8f/k+xIFJLVsfywCl/Exrfjsyn9z/hfJdbzLXD/vxlmWPX7LRyLu0pkt6GeS7pQ/7mvWTu1zrC4Km0rs9A6AYwoDzGCkXUk4LTAC3zY6K3r2Roxnz/XGACEOv+XyzgR5XnG/GP7hpabMknu7bO2PNm/X8UJUrPTlex4mt49p84WRdBRuT4tGlMX+ZIK7H2uWMaeJMp7eMbwmQQjHXqs8Qs46wP+s09nb2m6du3Si2D5IBa/Wu5ppitTmB3wdfqFPq+O8cHVxAH4ABwAAABxrTU5CWXBCOU81VytSeTVsUW9hVGhNL1dRK0E9,eba388f8-a494-4b33-a8f4-b3f98f900885,8092,%s,%s,%s,%s,200701.40.40.40.000000.1",
            i, encryptedVote.getVotingReceipt(), encryptedVote.getContestId(),
            encryptedVote.getElectionId(), encryptedVote.getElectionEventId());
    }

    private PrintWriter createPrintWriter(String fileName)
        throws FileNotFoundException {
        FileOutputStream fos = new FileOutputStream(fileName);

        return new PrintWriter(fos, true);
    }
}
