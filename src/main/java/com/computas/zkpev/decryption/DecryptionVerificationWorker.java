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
package com.computas.zkpev.decryption;

import com.computas.zkpev.ElGamalVerificationWorker;

import java.math.BigInteger;

import java.security.NoSuchAlgorithmException;


/**
 * A worker thread performing the decryption verification.
 *
 * @version $Id: DecryptionVerificationWorker.java 10998 2011-10-18 14:04:09Z fvl $
 */
public class DecryptionVerificationWorker extends ElGamalVerificationWorker {
    private final NizkpDecryption master;

    protected DecryptionVerificationWorker(NizkpDecryption master,
        BigInteger p, BigInteger g, BigInteger h) {
        super(p, g, h);
        this.master = master;
    }

    @Override
    public void run() {
        DecryptionLinesList batch = getDecryptionLineBatch();

        while (!batch.isEmpty()) {
            verifyDecryptionLineProofs(batch);
            batch = getDecryptionLineBatch();
        }
    }

    private DecryptionLinesList getDecryptionLineBatch() {
        return master.getNextDecryptionLineBatch();
    }

    private void verifyDecryptionLineProofs(DecryptionLinesList batch) {
        try {
            tryToVerifyDecryptionLineProofs(batch);
        } catch (NoSuchAlgorithmException e) {
            NizkpDecryption.getLogger()
                           .error("An error occured while trying to verify decryption line proofs.",
                e);
        }
    }

    private void tryToVerifyDecryptionLineProofs(DecryptionLinesList batch)
        throws NoSuchAlgorithmException {
        for (DecryptionLine decryptionLine : batch) {
            verifyDecryptionLineProof(decryptionLine);
        }
    }

    private void verifyDecryptionLineProof(DecryptionLine decryptionLine)
        throws NoSuchAlgorithmException {
        if (!decryptionLine.verifyProof(getP(), getG(), getH())) {
            master.addResult(new DecryptionLineWithIncorrectProofIncident(
                    decryptionLine));
        }
    }
}
