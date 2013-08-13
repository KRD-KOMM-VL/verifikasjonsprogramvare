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
 * Defines the opening hours for a given election and polling place.
 */
public class OpeningHours implements Serializable, KeepMembers {
    private static final long serialVersionUID = -1346520562729265299L;
    protected static final String UTF_8 = "UTF-8";
    private final java.lang.String _day;
    private final java.lang.String _endTime;
    private final java.lang.String _startTime;

    /**
     * Defines the opening hours of a polling place.
     *
     * @param day
     *            The day when it is open.
     * @param startTime
     *            The opening time.
     * @param endTime
     *            The closing time.
     */
    public OpeningHours(final java.lang.String day,
        final java.lang.String startTime, final java.lang.String endTime) {
        _day = day;
        _startTime = startTime;
        _endTime = endTime;
    }

    /**
     * @return Returns the day.
     */
    public java.lang.String getDay() {
        return _day;
    }

    /**
     * @return Returns the endTime.
     */
    public java.lang.String getEndTime() {
        return _endTime;
    }

    /**
     * @return Returns the startTime.
     */
    public java.lang.String getStartTime() {
        return _startTime;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "OpeningHours [_day=" + _day + ", _startTime=" + _startTime +
        ", _endTime=" + _endTime + "]";
    }

    /**
     * @return The opening hours as byte array.
     */
    public byte[] toByteArray() {
        byte[] data = null;

        try {
            data = ArrayUtils.addAll(data, String.valueOf(_day).getBytes(UTF_8));
            data = ArrayUtils.addAll(data,
                    String.valueOf(_startTime).getBytes(UTF_8));
            data = ArrayUtils.addAll(data,
                    String.valueOf(_endTime).getBytes(UTF_8));
        } catch (UnsupportedEncodingException e1) {
            throw new FatalProtocolException(e1);
        }

        return data;
    }
}
