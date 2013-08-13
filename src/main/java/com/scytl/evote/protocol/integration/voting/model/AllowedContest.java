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
package com.scytl.evote.protocol.integration.voting.model;

import com.scytl.evote.protocol.integration.voting.exception.FatalProtocolException;

import org.apache.commons.lang.ArrayUtils;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;


/**
 * Represents a contest eligible to vote.
 */
public class AllowedContest implements Serializable, KeepMembers {
    private static final long serialVersionUID = 2475470077506189609L;
    private static final String UTF_8 = "UTF-8";
    private String _electionId;
    private String _contestId;
    private String _voted = "0"; // NOT included in hash/equals

    /**
     * Default constructor without previous info.
     */
    public AllowedContest() {
    }

    /**
     * Constructor that defines the election id and the contest id of the
     * contest.
     *
     * @param electionId
     * @param contestId
     */
    public AllowedContest(final String electionId, final String contestId) {
        super();
        _electionId = electionId;
        _contestId = contestId;
    }

    /**
     * Constructor that defines the election id and the contest id of the
     * contest.
     *
     * @param electionId
     * @param contestId
     * @param voted
     *            Flag that states if the voter has already voted for this
     *            eligible contest.
     */
    public AllowedContest(final String electionId, final String contestId,
        final String voted) {
        super();
        _electionId = electionId;
        _contestId = contestId;
        _voted = voted;
    }

    /**
     * @return Returns the electionId.
     */
    public String getElectionId() {
        return _electionId;
    }

    /**
     * @return Returns the contestId.
     */
    public String getContestId() {
        return _contestId;
    }

    /**
     * @return The allowed contest as a byte array.
     */
    public byte[] toByteArray() {
        byte[] data = null;

        try {
            // electionId
            data = ArrayUtils.addAll(data, _electionId.getBytes(UTF_8));

            // contest id
            data = ArrayUtils.addAll(data, _contestId.getBytes(UTF_8));

            // voted
            data = ArrayUtils.addAll(data, _voted.getBytes(UTF_8));
        } catch (UnsupportedEncodingException e1) {
            throw new FatalProtocolException(e1);
        }

        return data;
    }

    /**
     * @param electionId
     *            The electionId to set.
     */
    public void setElectionId(final String electionId) {
        _electionId = electionId;
    }

    /**
     * @param contestId
     *            The contestId to set.
     */
    public void setContestId(final String contestId) {
        _contestId = contestId;
    }

    /**
     * @return Returns the voted.
     */
    public String getVoted() {
        return _voted;
    }

    /**
     * @param voted
     *            The voted to set.
     */
    public void setVoted(final String voted) {
        _voted = voted;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "AllowedContest [_contestId=" + _contestId + ", _electionId=" +
        _electionId + ", _voted=" + _voted + "]";
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) +
            ((_contestId == null) ? 0 : _contestId.hashCode());
        result = (prime * result) +
            ((_electionId == null) ? 0 : _electionId.hashCode());

        return result;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        AllowedContest other = (AllowedContest) obj;

        if (_contestId == null) {
            if (other._contestId != null) {
                return false;
            }
        } else if (!_contestId.equals(other._contestId)) {
            return false;
        }

        if (_electionId == null) {
            if (other._electionId != null) {
                return false;
            }
        } else if (!_electionId.equals(other._electionId)) {
            return false;
        }

        return true;
    }
}
