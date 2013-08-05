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

import java.util.Map;


/**
 * An interface defining the how a map with VCS encrypted votes should behave.
 *
 * @version $Id: VcsEncryptedVotesMap.java 10998 2011-10-18 14:04:09Z fvl $
 */
public interface VcsEncryptedVotesMap extends Map<String, VcsEncryptedVoteRetentionCounter>,
    Collection {
    /**
     * Returns the VCS encrypted vote retention counter with a matching key
     * for the cleansed vote.
     * @param cleansedVote The vote for which a corresponding VCS encrypted vote should be found.
     * @return The VCS encrypted vote retention counter if a match is found, null otherwise.
     */
    VcsEncryptedVoteRetentionCounter findMatchForCleansedVote(
        CleansedVote cleansedVote);
}
