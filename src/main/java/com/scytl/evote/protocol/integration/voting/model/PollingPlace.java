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

import java.util.ArrayList;
import java.util.List;


/**
 * Class that represents a polling place.
 */
public class PollingPlace implements Serializable, KeepMembers {
    private static final long serialVersionUID = -2884608801617358994L;
    protected static final String UTF_8 = "UTF-8";
    private java.lang.String _addressLine1;
    private java.lang.String _addressLine2;
    private java.lang.String _addressLine3;
    private java.lang.String _name;
    private List<OpeningHours> _openingHours;
    private java.lang.String _postTown;
    private java.lang.String _postalCode;

    /**
     * Default constructor which sets an empty list of opening hours.
     */
    public PollingPlace() {
        _openingHours = new ArrayList<OpeningHours>();
    }

    /**
     * Main constructor.
     *
     * @param addressLine1
     *            The first address line of the polling place.
     * @param addressLine2
     *            The second address line of the polling place.
     * @param addressLine3
     *            The third address line of the polling place.
     * @param name
     *            The name of the polling place.
     * @param openingHours
     *            A list of opening hours for this polling place.
     * @param postTown
     *            The town of the polling place.
     * @param postalCode
     *            The postal code of the polling place.
     */
    public PollingPlace(final java.lang.String addressLine1,
        final java.lang.String addressLine2,
        final java.lang.String addressLine3, final java.lang.String name,
        final List<OpeningHours> openingHours, final java.lang.String postTown,
        final java.lang.String postalCode) {
        _addressLine1 = addressLine1;
        _addressLine2 = addressLine2;
        _addressLine3 = addressLine3;
        _name = name;
        _openingHours = openingHours;
        _postTown = postTown;
        _postalCode = postalCode;
    }

    /**
     * Gets the addressLine1 value for this PollingPlace.
     *
     * @return addressLine1
     */
    public java.lang.String getAddressLine1() {
        return _addressLine1;
    }

    /**
     * Sets the addressLine1 value for this PollingPlace.
     *
     * @param addressLine1
     */
    public void setAddressLine1(final java.lang.String addressLine1) {
        _addressLine1 = addressLine1;
    }

    /**
     * Gets the addressLine2 value for this PollingPlace.
     *
     * @return addressLine2
     */
    public java.lang.String getAddressLine2() {
        return _addressLine2;
    }

    /**
     * Sets the addressLine2 value for this PollingPlace.
     *
     * @param addressLine2
     */
    public void setAddressLine2(final java.lang.String addressLine2) {
        _addressLine2 = addressLine2;
    }

    /**
     * Gets the addressLine3 value for this PollingPlace.
     *
     * @return addressLine3
     */
    public java.lang.String getAddressLine3() {
        return _addressLine3;
    }

    /**
     * Sets the addressLine3 value for this PollingPlace.
     *
     * @param addressLine3
     */
    public void setAddressLine3(final java.lang.String addressLine3) {
        _addressLine3 = addressLine3;
    }

    /**
     * Gets the name value for this PollingPlace.
     *
     * @return name
     */
    public java.lang.String getName() {
        return _name;
    }

    /**
     * Sets the name value for this PollingPlace.
     *
     * @param name
     */
    public void setName(final java.lang.String name) {
        _name = name;
    }

    /**
     * Gets the openingHours value for this PollingPlace.
     *
     * @return openingHours
     */
    public List<OpeningHours> getOpeningHours() {
        return _openingHours;
    }

    /**
     * Sets the openingHours value for this PollingPlace.
     *
     * @param openingHours
     */
    public void setOpeningHours(final List<OpeningHours> openingHours) {
        _openingHours = openingHours;
    }

    /**
     * Gets the postTown value for this PollingPlace.
     *
     * @return postTown
     */
    public java.lang.String getPostTown() {
        return _postTown;
    }

    /**
     * Sets the postTown value for this PollingPlace.
     *
     * @param postTown
     */
    public void setPostTown(final java.lang.String postTown) {
        _postTown = postTown;
    }

    /**
     * Gets the postalCode value for this PollingPlace.
     *
     * @return postalCode
     */
    public java.lang.String getPostalCode() {
        return _postalCode;
    }

    /**
     * Sets the postalCode value for this PollingPlace.
     *
     * @param postalCode
     */
    public void setPostalCode(final java.lang.String postalCode) {
        _postalCode = postalCode;
    }

    /**
     * @return The polling place as a byte array.
     */
    public byte[] toByteArray() {
        byte[] data = null;

        try {
            data = ArrayUtils.addAll(data,
                    String.valueOf(_addressLine1).getBytes(UTF_8));
            data = ArrayUtils.addAll(data,
                    String.valueOf(_addressLine2).getBytes(UTF_8));
            data = ArrayUtils.addAll(data,
                    String.valueOf(_addressLine3).getBytes(UTF_8));
            data = ArrayUtils.addAll(data, String.valueOf(_name).getBytes(UTF_8));

            if (_openingHours != null) {
                data = ArrayUtils.addAll(data,
                        _openingHours.toString().getBytes(UTF_8));
            }

            data = ArrayUtils.addAll(data,
                    String.valueOf(_postTown).getBytes(UTF_8));
            data = ArrayUtils.addAll(data,
                    String.valueOf(_postalCode).getBytes(UTF_8));
        } catch (UnsupportedEncodingException e1) {
            throw new FatalProtocolException(e1);
        }

        return data;
    }
}
