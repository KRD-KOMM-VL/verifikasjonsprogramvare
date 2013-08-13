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
package com.computas.zkpev.cleansing;

import com.computas.zkpev.Collection;
import com.computas.zkpev.ResultsList;

import java.util.List;


/**
 * Interface defining the API for a list of cleansed votes.
 *
 */
public interface CleansedVotesList extends List<CleansedVote>, Collection {
    /**
     * Tries to create a cleansed vote and add it to the list, or creates
     * an incident which is added to the Results list if something goes
     * wrong.
     *
     * @param line The line form the cleansed votes file, comma-separated.
    * @param results The results lists where incidents should be created.
     */
    void addCleansedVoteOrCreateIncident(String line, ResultsList results);
}
