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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import java.security.NoSuchAlgorithmException;


/**
 * Class to generate test files for the NizkRcgVcs class.
 */
public final class NizkpRcgVcsTestFileGenerator {
    private static final int[] TEST_FILE_LENGTHS = new int[] {
            3, 10, 30, 100, 300, 1000, 3000, 10000, 20000, 30000, 50000, 100000,
            150000, 200000
        };
    private final EncryptedVoteLineGenerator encryptedVoteLineGenerator;

    private NizkpRcgVcsTestFileGenerator() {
        encryptedVoteLineGenerator = new EncryptedVoteLineGenerator();
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
        PrintWriter encryptedVotesFile = createPrintWriter(String.format(
                    "NizkpRcgVcsEncryptedVotesTestFile-%d.csv", n));
        PrintWriter votingReceiptsFile = createPrintWriter(String.format(
                    "NizkpRcgVcsVotingReceiptsTestFile-%d.csv", n));

        for (int i = 0; i < n; i++) {
            addNewElement(i, encryptedVotesFile, votingReceiptsFile);
        }

        encryptedVotesFile.close();
        votingReceiptsFile.close();
    }

    private void addNewElement(int i, PrintWriter encryptedVotesFile,
        PrintWriter votingReceiptsFile)
        throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String encryptedVoteLine = encryptedVoteLineGenerator.generateEncryptedVoteLine(i);
        encryptedVotesFile.println(encryptedVoteLine);

        String votingReceiptLine = generateVotingReceiptLine(encryptedVoteLine,
                i);
        votingReceiptsFile.println(votingReceiptLine);
    }

    private String generateVotingReceiptLine(String encryptedVoteLine, int i)
        throws NoSuchAlgorithmException, UnsupportedEncodingException {
        EncryptedVote encryptedVote = new EncryptedVote(encryptedVoteLine);

        return String.format("%d,cb3f03d5-b7a4-4c02-9ad2-3016289ebbf7,rO0ABXNyAD1jb20uc2N5dGwuZXZvdGUucHJvdG9jb2wuaW50ZWdyYXRpb24udm90aW5nLm1vZGVsLlJlY2VpcHRCZWFuw7/82u7t7McCAApKAAZfcmNnSURKAApfdGltZXN0YW1wSgAGX3Zjc0lETAAKX2NvbnRlc3RJZHQAEkxqYXZhL2xhbmcvU3RyaW5nO0wAEF9lbGVjdGlvbkV2ZW50SWRxAH4AAUwAC19lbGVjdGlvbklkcQB+AAFbABRfcmVjZWlwdFNpZ25hdHVyZVJDR3QAAltCWwAUX3JlY2VpcHRTaWduYXR1cmVWQ1NxAH4AAkwAE19yZXR1cm5Db2Rlc01lc3NhZ2VxAH4AAVsADl92b3RpbmdSZWNlaXB0cQB+AAJ4cAAAAAAAAB+cAAABQFIvPVoAAAAAAAAAAHQABjAwMDAwMXQABjczMDA3MXQAAjAxdXIAAltCrPMX+AYIVOACAAB4cAAAAQAUEg13Zyko+IYIkqbGabqolbpzpuHvErzqSaZzDMDRTUni/6AdbL+13LXufmb8GwPz5nk+lRUrrsiGKi7y/IqFY0rIA2sLoCnU6O3F0yZIi1+CACna3Y14n+PxBZbKVJFMc34MtkEztecoiZQEyeCMAosTMpwsSwBBnwlKffQj8LPH3/edV/zOfyEyY6ia1dIm21NrwnqEr5xAniUKS4xJIfzslX2nx5KpISH98XD45sdEedCAuz86+vqA0cy7OP5zdcRQ7abft4+3D6NkjYO+9GAZWJSVW9zKjGgB9Qx14ts2tK0qr5DGRlqrditsaTJqYjiYc43JSZ93Q/+EoIcRdXEAfgAHAAABAEkpiMiQGEox1TWZutzPDpBvXpzpNoZq21qYMwUO/bUFKZv7M1DE3U+Kv4c+kDmePS3WHKhCVczTaY7VCkh5rZrOrulaUQkUsArwJ9CGqm7rRJ3JLGtXXFaXnKnmtgfOdBuS22pBfCdh32W2ZX4QFBaxG4F1QRc/q1VFuVviO9hlBFau+oo4a1Nl37Wu8oyJwFlq3b5cb4eBPnQHY/7hAAuwVui/FPFlEZqISW9hiAsW+14azBZqozcFRp06mhiZ7fh1zrAjpifTuvaP+DGDVQ9vcYz6a9DHbupO1LRxzMmmYE2UsFSzv4oOUZtu7mYsMozXkGEmaFEP/MgTn7vprPZwdXEAfgAHAAAALFVNQVFRUlJiMTNUclA3T0JhRGlxNEkxQjIwdFhHOTJERTFmVFlGdzQwekk9,75137b09-dd56-4092-be1b-36b4d24bd9e7,8092,%s,%s,%s,%s",
            i, encryptedVote.getVotingReceipt(), encryptedVote.getContestId(),
            encryptedVote.getElectionId(), encryptedVote.getElectionEventId());
    }

    private PrintWriter createPrintWriter(String fileName)
        throws FileNotFoundException {
        FileOutputStream fos = new FileOutputStream(fileName);

        return new PrintWriter(fos, true);
    }
}
