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

import com.computas.zkpev2013.Collection;
import com.computas.zkpev2013.EncryptedVote;
import com.computas.zkpev2013.ResultsList;

import java.util.List;


/**
 * Interface for the List holding all the encrypted votes.
 */
public interface EncryptedVotesList extends List<EncryptedVote>, Collection {
    /**
     * Tries to create an encrypted vote and add it to the list, or creates
     * an incident which is added to the Results list if something goes
     * wrong.
     *
     * @param line The line form the encrypted votes file, comma-separated.
    * @param results The results lists where incidents should be created.
     */
    void addEncryptedVoteOrCreateIncident(String line, ResultsList results);
}
