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

import com.scytl.evote.protocol.integration.mixing.base.VoteGroupAffiliations;

import java.math.BigInteger;

import java.util.ArrayList;
import java.util.List;


/**
 * Class representing a list of ElGamal vote groups factors.
 */
public class ElGamalVoteGroupsFactors extends ArrayList<ElGamalVoteGroupFactors>
    implements List<ElGamalVoteGroupFactors> {
    ElGamalVoteGroupsFactors(int size, BigInteger p) {
        for (int i = 0; i < size; i++) {
            this.add(new ElGamalVoteGroupFactors(p, BigInteger.ONE,
                    BigInteger.ONE));
        }
    }

    void update(Integer voteGroup, String encryptedVoteLine) {
        get(voteGroup)
            .add(new EncryptedVote(encryptedVoteLine).getElGamalEncryptionPair());
    }

    static ElGamalVoteGroupsFactors computeProducts(int size, BigInteger p,
        String encryptedVotesString, VoteGroupAffiliations voteGroups) {
        ElGamalVoteGroupsFactors voteGroupsFactors = new ElGamalVoteGroupsFactors(size,
                p);
        String[] encryptedVoteLines = encryptedVotesString.split("\n");

        for (int i = 0; i < voteGroups.size(); i++) {
            voteGroupsFactors.update(voteGroups.get(i), encryptedVoteLines[i]);
        }

        return voteGroupsFactors;
    }
}
