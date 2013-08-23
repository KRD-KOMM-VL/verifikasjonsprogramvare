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
import com.computas.zkpev2013.ElGamalEncryptionPair;

import org.apache.commons.codec.binary.Base64;

import java.math.BigInteger;


/**
 * Class representing an encrypted vote in the context of the mixing process.
 *
 */
public class EncryptedVote extends CsvLineParseable {
    private ElGamalEncryptionPair elGamalEncryptionPair;

    protected EncryptedVote(String line) {
        super(line);
    }

    @Override
    protected void setAttributes(String[] attributes) throws Exception {
        //byte[] encodedVotingOptionIdsProductByteArray = getAttributeAsMultilinedByteArray(attributes,
        //        EncryptedVoteCsvIndex.ENC_VOTE_OPT_IDS);
        String[] parsedString = getAttributeAsString(attributes,
                EncryptedVoteCsvIndex.ENC_VOTE_OPT_IDS).split("#");
        elGamalEncryptionPair = new ElGamalEncryptionPair(new BigInteger(
                    Base64.decodeBase64(parsedString[0].getBytes())),
                new BigInteger(Base64.decodeBase64(parsedString[1].getBytes())));
    }

    ElGamalEncryptionPair getElGamalEncryptionPair() {
        return elGamalEncryptionPair;
    }
    private enum EncryptedVoteCsvIndex {ENC_VOTE_OPT_IDS;
    }
}
