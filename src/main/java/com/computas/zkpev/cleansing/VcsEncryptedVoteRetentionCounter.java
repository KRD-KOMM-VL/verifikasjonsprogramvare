/**
 * Zero-Knowledge Protocols for E-Vote (ZKPEV).
 *
 * Copyright © 2011, The Norwegian Ministry of Local Government and Regional
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

import com.computas.zkpev.ElGamalEncryptionPair;

import java.math.BigInteger;

import java.util.ArrayList;
import java.util.List;


/**
 * A class counting how many times a VCS encrypted vote appears in the result
 * file from the Cleansing server.
 *
 * @version $Id: VcsEncryptedVoteRetentionCounter.java 10998 2011-10-18 14:04:09Z fvl $
 */
public class VcsEncryptedVoteRetentionCounter {
    private final VcsEncryptedVote vcsEncryptedVote;
    private final List<CleansedVote> cleansedVotes;

    VcsEncryptedVoteRetentionCounter(VcsEncryptedVote vcsEncryptedVote) {
        this.vcsEncryptedVote = vcsEncryptedVote;
        cleansedVotes = new ArrayList<CleansedVote>();
    }

    VcsEncryptedVote getVcsEncryptedVote() {
        return vcsEncryptedVote;
    }

    void registerMatch(CleansedVote cleansedVote) {
        cleansedVotes.add(cleansedVote);
    }

    int getNoOfMatches() {
        return cleansedVotes.size();
    }

    boolean matches(CleansedVote cleansedVote, AreasMap areas,
        BigInteger modulus) {
        Area voterArea = getVoterAreaFromVcsEncryptedVote(areas);

        return areaOrLinkedAreasGeneratesAMatch(cleansedVote, modulus, areas,
            voterArea);
    }

    private boolean areaOrLinkedAreasGeneratesAMatch(
        CleansedVote cleansedVote, BigInteger modulus, AreasMap areas,
        Area voterArea) {
        return areaGeneratesAMatch(cleansedVote, modulus, voterArea) ||
        thresholdAreaGeneratesAMatch(cleansedVote, modulus, areas, voterArea) ||
        area100GeneratesAMatch(cleansedVote, modulus, areas, voterArea);
    }

    private boolean areaGeneratesAMatch(CleansedVote cleansedVote,
        BigInteger modulus, Area voterArea) {
        if (voterArea.getPrime() == null) {
            return false;
        }

        ElGamalEncryptionPair compressedEncVoteOptIds = vcsEncryptedVote.getCompressedEncVoteOptIds(modulus,
                voterArea.getPrime());

        return compressedEncVoteOptIds.equals(cleansedVote.getEncryptedVoteOptIds());
    }

    private boolean thresholdAreaGeneratesAMatch(CleansedVote cleansedVote,
        BigInteger modulus, AreasMap areas, Area voterArea) {
        String thresholdAreaId = voterArea.getThresholdAreaId();

        if ("".equals(thresholdAreaId)) {
            return false;
        }

        Area thresholdArea = areas.get(thresholdAreaId);

        if (thresholdArea == null) {
            throw new IllegalArgumentException(String.format(
                    "Could not look up the threshold area %s for VCS encrypted vote with UUID %s.",
                    thresholdArea, vcsEncryptedVote.getUuid()));
        }

        return areaOrLinkedAreasGeneratesAMatch(cleansedVote, modulus, areas,
            thresholdArea);
    }

    private boolean area100GeneratesAMatch(CleansedVote cleansedVote,
        BigInteger modulus, AreasMap areas, Area voterArea) {
        String area100Id = voterArea.getArea100Id();

        if ("".equals(area100Id)) {
            return false;
        }

        Area area100 = areas.get(area100Id);

        if (area100 == null) {
            throw new IllegalArgumentException(String.format(
                    "Could not look up area 100 %s for VCS encrypted vote with UUID %s.",
                    area100Id, vcsEncryptedVote.getUuid()));
        }

        return areaOrLinkedAreasGeneratesAMatch(cleansedVote, modulus, areas,
            area100);
    }

    private String getReducedAreaId(String areaId) {
        return areaId.substring(vcsEncryptedVote.getElectionEventId().length() +
            1);
    }

    private Area getVoterAreaFromVcsEncryptedVote(AreasMap areas) {
        String voterAreaId = getReducedVoterAreaId();

        Area area = areas.get(voterAreaId);

        if (area == null) {
            throw new IllegalArgumentException(String.format(
                    "Could not look up the voter area %s for VCS encrypted vote with UUID %s.",
                    voterAreaId, vcsEncryptedVote.getUuid()));
        }

        return area;
    }

    private String getReducedVoterAreaId() {
        return getReducedAreaId(vcsEncryptedVote.getVoterAreaId());
    }
}
