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

import java.io.UnsupportedEncodingException;


/**
 * MinID/IDPorten identification service internal token.
 */
public class MinIDToken implements InternalToken, Erasable {
    private static final long serialVersionUID = -2245446752247623771L;
    private final String _id;
    private final String _voterId;
    private final long _ts;
    private final String _info;

    /**
     * @param voterId
     * @param ts
     * @param info
     */
    public MinIDToken(final String id, final String voterId, final long ts,
        final String info) {
        super();
        _id = id;
        _voterId = voterId;
        _ts = ts;
        _info = info;
    }

    /**
     * @return Returns the id.
     */
    @Override
    public String getId() {
        return _id;
    }

    /**
     * @return Returns the voterId.
     */
    public String getVoterId() {
        return _voterId;
    }

    /**
     * @return Returns the ts.
     */
    public long getTs() {
        return _ts;
    }

    /**
     * @return Returns the info.
     */
    @Override
    public String getInfo() {
        return _info;
    }

    /**
     * @see com.scytl.evote.protocol.integration.voting.model.InternalToken#toByteArray()
     */
    @Override
    public byte[] toByteArray() {
        byte[] voteData = null;

        try {
            // id
            voteData = ArrayUtils.addAll(voteData, _id.getBytes(UTF_8));

            // ts
            voteData = ArrayUtils.addAll(voteData,
                    String.valueOf(_ts).getBytes(UTF_8));

            // voterId
            voteData = ArrayUtils.addAll(voteData, _voterId.getBytes(UTF_8));

            // info
            voteData = ArrayUtils.addAll(voteData, _info.getBytes(UTF_8));
        } catch (UnsupportedEncodingException e1) {
            throw new FatalProtocolException(e1);
        }

        return voteData;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "MinIDToken [_id=" + _id + ", _info=" + _info + ", _ts=" + _ts +
        ", _voterId=" + _voterId + "]";
    }

    /**
     * @see com.scytl.evote.protocol.integration.eraser.Erasable#erase()
     */
    @Override
    public void erase() {
        Eraser eraser = Eraser.get(this.getClass());
        eraser.erase(_id);
        eraser.erase(_voterId);
        eraser.erase(_ts);
        eraser.erase(_info);
    }
}
