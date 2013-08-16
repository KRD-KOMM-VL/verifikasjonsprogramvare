/**
 * Source Code, High Level Architecture Documentation and Common Criteria
 * Documentation Copyright (C) 2013 and ownership belongs to The Norwegian
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

import com.scytl.evote.protocol.exceptions.FatalProtocolException;
import com.scytl.evote.protocol.integration.eraser.Erasable;

import com.scytl.jbasis.erase.Eraser;

import org.apache.commons.lang.ArrayUtils;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;


/**
 * Contains the information to identify a voter.
 */
public class VoterIdentifier implements Serializable, KeepMembers, Erasable {
    protected static final String UTF_8 = "UTF-8";
    private static final long serialVersionUID = -3837446936105024906L;
    private final String _voterId;
    private final String _spareId;

    /**
     * Constructor used for spare voters.
     *
     * @param voterId
     *            The voter identifier.
     * @param spareId
     *            The spare identifier.
     */
    public VoterIdentifier(final String voterId, final String spareId) {
        _voterId = voterId;

        if (spareId == null) {
            _spareId = voterId;
        } else {
            _spareId = spareId;
        }
    }

    /**
     * Constructor used for default voters.
     *
     * @param voterId
     *            The voter identifier.
     */
    public VoterIdentifier(final String voterId) {
        this(voterId, null);
    }

    /**
     * Returns the VoterIdentifier as a byte array
     *
     * @return
     */
    public byte[] toByteArray() {
        byte[] voteData = null;

        try {
            voteData = ArrayUtils.addAll(voteData, _voterId.getBytes(UTF_8));

            if (_spareId == null) {
                voteData = ArrayUtils.addAll(voteData, "null".getBytes(UTF_8));
            } else {
                voteData = ArrayUtils.addAll(voteData, _spareId.getBytes(UTF_8));
            }
        } catch (UnsupportedEncodingException e1) {
            throw new FatalProtocolException(e1);
        }

        return voteData;
    }

    /**
     * @return Returns the voterId.
     */
    public String getVoterId() {
        return _voterId;
    }

    /**
     * @return Returns the spareId.
     */
    public String getSpareId() {
        return _spareId;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "VoterIdentifier [_spareId=" + _spareId + ", _voterId=" +
        _voterId + "]";
    }

    /**
     * @see com.scytl.evote.protocol.integration.eraser.Erasable#erase()
     */
    @Override
    public void erase() {
        Eraser eraser = Eraser.get(this.getClass());
        eraser.erase(_voterId);
        eraser.erase(_spareId);
    }
}
