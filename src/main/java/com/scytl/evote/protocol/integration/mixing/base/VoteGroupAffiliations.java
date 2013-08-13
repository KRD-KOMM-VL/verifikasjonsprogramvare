/**
 * Source Code, High Level Architecture Documentation and Common Criteria
 * Documentation Copyright (C) 2010-2011 and ownership belongs to The Norwegian
 * Ministry of Local Government and Regional Development and Scytl Secure
 * Electronic Voting SA ("Licensor").
 *
 * The Norwegian Ministry of Local Government and Regional Development has the
 * right to use, modify (whether by itself or by the use of contractors) and
 * copy the software for the sole purposes of performing Norwegian Public Sector
 * Elections, including to install and run the code on the necessary number of
 * locations centrally and in any number of counties and municipalities, and to
 * allow access to the solution from anywhere in the world by persons who have
 * the right to participate in Norwegian national or local elections. This also
 * applies to elections to the Longyearbyen Community Council at Svalbard and
 * any possible future public elections in Norway arranged by the Election
 * Authorities.
 *
 * Patents, relevant to the software, are licensed by Scytl Secure Electronic
 * Voting SA to the Norwegian Ministry of Local Government and Regional
 * Development for the purposes set out above.
 *
 * Scytl Secure Electronic Voting SA (or whom it appoints) has the right, inside
 * and outside of Norway to use, copy, modify and enhance the materials, as well
 * as a right of licensing and transfer, internally and externally, either by
 * itself or with the assistance of a third party, as part of the further
 * development and customization of its own standard solutions or delivered
 * together with its own standard solutions.
 *
 * The Norwegian Ministry of Local Government and Regional Development and Scytl
 * Secure Electronic Voting SA hereby grant to you (any third party) the right
 * to copy, modify, inspect, compile, debug and run the software for the sole
 * purpose of testing, reviewing or evaluating the code or the system solely for
 * non-commercial purposes. Any other use of the source code (or parts of it)
 * for any other purpose (including but not limited to any commercial purposes)
 * by any third party is subject to Scytl Secure Electronic Voting SA's prior
 * written approval.
 */
package com.scytl.evote.protocol.integration.mixing.base;

import java.io.Serializable;

import java.util.Vector;


/**
 * Container class for the collection of vote group affiliations of the
 * encrypted votes provided as input to a given mix-node or the shuffled and
 * re-encrypted votes produced as output by a given mix-node. The vote group
 * affiliation is defined as the unique identifier of the vote group to which
 * the given vote is associated.
 */
public class VoteGroupAffiliations implements Serializable {
    private static final long serialVersionUID = 7612023259225526284L;

    /**
     * Collection of vote group affiliations.
     */
    private final Vector<Integer> _voteGroupAffs;

    /**
     * Constructor.
     */
    public VoteGroupAffiliations() {
        _voteGroupAffs = new Vector<Integer>();
    }

    public VoteGroupAffiliations(final Vector<Integer> voteGroupAffs) {
        _voteGroupAffs = voteGroupAffs;
    }

    /**
     * Adds the vote group affiliation of a given vote to the collection.
     *
     * @param voteGroupAff
     *            Vote group affiliation of vote.
     */
    public void add(final Integer voteGroupAff) {
        _voteGroupAffs.add(voteGroupAff);
    }

    /**
     * Retrieves the collection of vote group affiliations.
     *
     * @return Collection of vote group affiliations.
     */
    public Vector<Integer> get() {
        return _voteGroupAffs;
    }

    /**
     * Retrieves the vote group affiliation of a given vote from the collection,
     * given its index.
     *
     * @param index
     *            Index of vote group affiliation in collection.
     * @return Vote group affiliation.
     */
    public Integer get(final int index) {
        return _voteGroupAffs.get(index);
    }

    /**
     * Retrieves the size of the vote group affiliation collection.
     *
     * @return Size of vote group affiliation collection.
     */
    public int size() {
        return _voteGroupAffs.size();
    }

    /**
     * Clears the vote group affiliation collection.
     */
    public void clear() {
        _voteGroupAffs.clear();
    }
}
