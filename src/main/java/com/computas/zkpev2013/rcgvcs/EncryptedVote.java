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

import com.computas.zkpev2013.CsvLineParseable;


/**
 * Domain object holding all relevant information about an encrypted vote.
 * Since the objects are rather large, only the information that is necessary
 * to match it with a voting receipt are retained after construction of the
 * object.
 */
public class EncryptedVote extends CsvLineParseable {
    private String uuid;

    EncryptedVote(String line) {
        super(line);
    }

    String getUuid() {
        return uuid;
    }

    @Override
    protected void setAttributes(String[] attributes) throws Exception {
        uuid = getAttribute(attributes, EncryptedVoteCsvIndex.UUID);
    }

    /**
     * Indexes of the various fields in the CSV file.
     */
    private enum EncryptedVoteCsvIndex {UUID;
    }
}
