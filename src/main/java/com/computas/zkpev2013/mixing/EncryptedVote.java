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

import com.computas.zkpev2013.CsvLineParseable;
import com.computas.zkpev2013.ElGamalEncryptionTuple;


/**
 * Class representing an encrypted vote in the context of the mixing process.
 */
public class EncryptedVote extends CsvLineParseable {
    private ElGamalEncryptionTuple elGamalEncryptionTuple;

    protected EncryptedVote(String line) {
        super(line);
    }

    @Override
    protected void setAttributes(String[] attributes) throws Exception {
        elGamalEncryptionTuple = getAttributeAsElGamalEncryptionTuple(attributes,
                EncryptedVoteCsvIndex.ENC_VOTE_OPT_IDS);
    }

    ElGamalEncryptionTuple getElGamalEncryptionTuple() {
        return elGamalEncryptionTuple;
    }
    private enum EncryptedVoteCsvIndex {ENC_VOTE_OPT_IDS;
    }
}
