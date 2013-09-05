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
package com.computas.zkpev2013.mixing;

import com.computas.zkpev2013.ElGamalPublicKeyList;
import com.computas.zkpev2013.ElGamalVerificationWorker;

import java.math.BigInteger;

import java.security.NoSuchAlgorithmException;

import java.sql.SQLException;


/**
 * A worker thread performing the mixing verification.
 */
public class MixingVerificationWorker extends ElGamalVerificationWorker {
    private final IzkpMixing master;

    protected MixingVerificationWorker(IzkpMixing master, BigInteger p,
        BigInteger g, ElGamalPublicKeyList h) {
        super(p, g, h);
        this.master = master;
    }

    @Override
    public void run() {
        MixingVerificationData data = tryToGetMixingVerificationData();

        while (data != null) {
            runVerification(data);
            data = tryToGetMixingVerificationData();
        }
    }

    private MixingVerificationData tryToGetMixingVerificationData() {
        try {
            return master.getMixingVerificationData();
        } catch (SQLException e) {
            IzkpMixing.getLogger()
                      .error("An SQL error occured while trying to fetch more mixing verification data.",
                e);

            return null;
        }
    }

    private void runVerification(MixingVerificationData data) {
        try {
            tryToRun(data);
        } catch (NoSuchAlgorithmException t) {
            IzkpMixing.getLogger()
                      .error(String.format(
                    "An unexpected error occured while trying to verify the proofs for mixing/audit dataset %s/%s.",
                    data.getMixingUuid(), data.getAuditUuid()), t);
        }
    }

    void tryToRun(MixingVerificationData data) throws NoSuchAlgorithmException {
        boolean inputVoteGroupsFactorsComputed = data.computeInputVoteGroupsFactors(getP());
        boolean outputVoteGroupsFactorsComputed = data.computeOutputVoteGroupsFactors(getP());

        boolean result = inputVoteGroupsFactorsComputed &&
            outputVoteGroupsFactorsComputed &&
            data.verifyZeroKnowledgeProofs(getP(), getG(), getH());
        IzkpMixing.getLogger()
                  .info(String.format(
                "Worker for mixing/audit dataset %s/%s finished; %s.",
                data.getMixingUuid(), data.getAuditUuid(),
                (result ? "all proofs were correct"
                        : "problem reading data, or one or more proofs were incorrect. See results file for more information")));
    }
}
