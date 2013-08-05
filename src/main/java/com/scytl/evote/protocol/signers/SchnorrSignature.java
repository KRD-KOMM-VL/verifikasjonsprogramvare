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
package com.scytl.evote.protocol.signers;

import org.apache.commons.lang.ArrayUtils;

import java.io.Serializable;

import java.math.BigInteger;

import java.util.Vector;


/**
 * Container class for the components of a Schnorr digital signature. It allows
 * for the use of either a single private key part or a collection of such parts
 * to create the signature.
 */
public class SchnorrSignature implements Serializable {
    private static final long serialVersionUID = 8252130877796058607L;
    private static final String NULL = "null";
    private static final String SEPARATOR = "|";

    /**
     * Hashed message part of Schnorr signature.
     */
    private final BigInteger _e;

    /**
     * Private key part of Schnorr signature.
     */
    private final BigInteger _s;

    /**
     * Collection of private key parts of Schnorr signature.
     */
    private final Vector<BigInteger> _sColl;

    /**
     * Constructor that uses a single private key.
     *
     * @param e
     *            Hashed message part of Schnorr signature.
     * @param s
     *            Private key part of Schnorr signature.
     */
    public SchnorrSignature(final BigInteger e, final BigInteger s) {
        _e = e;
        _s = s;
        _sColl = null;
    }

    /**
     * Constructor that uses a collection of private keys.
     *
     * @param e
     *            Hashed message part of Schnorr signature.
     * @param sColl
     *            Collection of private key parts of Schnorr signature.
     */
    public SchnorrSignature(final BigInteger e, final Vector<BigInteger> sColl) {
        _e = e;
        _s = null;
        _sColl = sColl;
    }

    /**
     * Constructor that uses an encoded Schnorr signature.
     *
     * @param encoded
     *            Encoded Schnorr signature.
     */
    public SchnorrSignature(final byte[] encoded) {
        String[] elements = new String(encoded).split("\\" + SEPARATOR);

        if (!elements[0].equals(NULL)) {
            _e = new BigInteger(elements[0]);
        } else {
            _e = null;
        }

        if (!elements[1].equals(NULL)) {
            _s = new BigInteger(elements[1]);
        } else {
            _s = null;
        }

        if (!elements[2].equals(NULL)) {
            _sColl = new Vector<BigInteger>();

            for (int i = 2; i < elements.length; i++) {
                _sColl.add(new BigInteger(elements[i]));
            }
        } else {
            _sColl = null;
        }
    }

    /**
     * Retrieves the hashed message part of the Schnorr signature.
     *
     * @return Hashed message part of Schnorr signature.
     */
    public BigInteger getE() {
        return _e;
    }

    /**
     * Retrieves the private key part of the Schnorr signature.
     *
     * @return Private key part of Schnorr signature.
     */
    public BigInteger getS() {
        return _s;
    }

    /**
     * Retrieves the collection of private key parts of the Schnorr signature.
     *
     * @return Collection of private key parts of Schnorr signature.
     */
    public Vector<BigInteger> getSColl() {
        return _sColl;
    }

    /**
     * Returns a bitwise concatenation of the Schnorr signature components. Can
     * be used as the data input of a hash function.
     *
     * @return Bitwise concatenation of Schnorr signature components.
     */
    public byte[] toByteArray() {
        byte[] byteArray = null;
        byteArray = ArrayUtils.addAll(byteArray, _e.toByteArray());

        if (_s != null) {
            byteArray = ArrayUtils.addAll(byteArray, _s.toByteArray());
        } else {
            BigInteger[] sArray = _sColl.toArray(new BigInteger[0]);

            for (int i = 0; i < sArray.length; i++) {
                byteArray = ArrayUtils.addAll(byteArray, sArray[i].toByteArray());
            }
        }

        return byteArray;
    }

    /**
     * Encodes this Schnorr signature.
     *
     * @return Encoded Schnorr signature.
     */
    public byte[] encode() {
        StringBuilder builder = new StringBuilder();
        builder.append(_e + SEPARATOR + _s);

        if (_sColl != null) {
            for (BigInteger b : _sColl) {
                builder.append(SEPARATOR + b.toString());
            }
        } else {
            builder.append(SEPARATOR + _sColl);
        }

        return builder.toString().getBytes();
    }
}
