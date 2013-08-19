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

import com.computas.zkpev2013.ZeroKnowledgeProof;


/**
 * Class performing the non-interactive zero-knowledge proof on
 * the Return Code Generator (RCG) and the Vote Collector Server (VCS).
 *
 */
public class NizkpRcgVcs extends ZeroKnowledgeProof {
    private static final int MIN_NO_OF_PARAMETERS_ALLOWED = 2;
    private String encryptedVotesFileName;
    private String receiptsFileName;

    NizkpRcgVcs(String[] arguments) {
        super(arguments);
    }

    @Override
    protected void parseArguments(String[] arguments) {
        checkNoOfParameters(arguments, MIN_NO_OF_PARAMETERS_ALLOWED);

        encryptedVotesFileName = arguments[0];
        receiptsFileName = arguments[1];

        setOptionalResultsFileName(arguments, MIN_NO_OF_PARAMETERS_ALLOWED);
    }

    String getEncryptedVotesFileName() {
        return encryptedVotesFileName;
    }

    String getReceiptsFileName() {
        return receiptsFileName;
    }

    @Override
    protected void run() throws Exception {
        // TODO Auto-generated method stub
    }
}
