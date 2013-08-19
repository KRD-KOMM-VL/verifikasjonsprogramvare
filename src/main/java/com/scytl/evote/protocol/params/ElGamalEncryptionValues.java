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
package com.scytl.evote.protocol.params;

import org.apache.commons.lang.ArrayUtils;

import java.io.Serializable;

import java.math.BigInteger;


/**
 * Container class for the components that define an ElGamal encrypted list of
 * voting options.
 */
public class ElGamalEncryptionValues implements Serializable {
    private static final long serialVersionUID = -5720842329460422313L;

    // Constant used by override of method "hashCode".
    private static final int HASH_FACTOR = 31;

    // Public key component of ElGamal encryption.
    private final BigInteger _gamma;

    // Message component of ElGamal encryption.
    private final BigInteger[] _phis;

    /**
     * Constructor.
     *
     * @param gamma
     *            Public key component of ElGamal encryption.
     * @param phis
     *            Message components of ElGamal encryption.
     */
    public ElGamalEncryptionValues(final BigInteger gamma,
        final BigInteger[] phis) {
        super();
        _gamma = gamma;
        _phis = phis.clone();
    }

    /**
     * Constructor (cloning tasks).
     *
     * @param elGamalEncryptionValues
     *            the object to be cloned.
     */
    public ElGamalEncryptionValues(
        final ElGamalEncryptionValues elGamalEncryptionValues) {
        this(elGamalEncryptionValues._gamma, elGamalEncryptionValues._phis);
    }

    /**
     * Constructor.
     *
     * @param gamma
     *            Public key component of ElGamal encryption.
     * @param numPhis
     *            Number of message components of ElGamal encryption.
     */
    public ElGamalEncryptionValues(final BigInteger gamma, final int numPhis) {
        this(gamma, new BigInteger[numPhis]);
    }

    /**
     * Constructor
     *
     * @param encoded
     *            Encoded version of ElGamal encryption pair.
     */
    public ElGamalEncryptionValues(final byte[] encodedGamma,
        final byte[][] encodedPhis) {
        this(new BigInteger(encodedGamma), encodedPhis.length);

        for (int i = 0; i < encodedPhis.length; i++) {
            _phis[i] = new BigInteger(encodedPhis[i]);
        }
    }

    /**
     * @return Returns the gamma.
     */
    public BigInteger getGamma() {
        return _gamma;
    }

    /**
     * @return the number of phis (vote option IDs)
     */
    public int numPhis() {
        return _phis.length;
    }

    /**
     * Adds a phi (public key component) value to the array of phis
     *
     * @param phi
     *            the value to be added
     * @param idx
     *            the index of the array where the value will be added
     */
    public void addPhi(final BigInteger phi, final int idx) {
        _phis[idx] = phi;
    }

    /**
     * Encrypts a vote option ID by multiplying the existing encrypted vote
     * option at a specified position by the vote option ID and performs
     * modulus.
     *
     * @param option
     *            the vote option identifier to be encrypted.
     * @param modulus
     *            the modulus to be used in the operation
     * @param idx
     *            the index
     */
    public void multiplyPhi(final BigInteger option, final BigInteger modulus,
        final int idx) {
        _phis[idx] = _phis[idx].multiply(option).mod(modulus);
    }

    /**
     * Checks for object equality.
     *
     * @return True if objects are equal.
     */
    @Override
    public boolean equals(final Object obj) {
        boolean result = false;

        if (obj instanceof ElGamalEncryptionValues) {
            ElGamalEncryptionValues encValuesObj = (ElGamalEncryptionValues) obj;

            boolean gammasEqual = _gamma.equals(encValuesObj.getGamma());
            boolean phisEqual = true;
            int i = 0;

            while ((i < _phis.length) && phisEqual) {
                phisEqual = _phis[i].equals(encValuesObj._phis[i]);
                i++;
            }

            result = (gammasEqual && phisEqual);
        }

        return result;
    }

    /**
     * Generates object hash code.
     *
     * @return Object hash code.
     */
    @Override
    public int hashCode() {
        int hash = 7;

        int hashFactor = 0;

        if (_gamma != null) {
            hashFactor = _gamma.hashCode();
        }

        hash = (HASH_FACTOR * hash) + hashFactor;

        for (BigInteger phi : _phis) {
            hashFactor = 0;

            if (phi != null) {
                hashFactor = phi.hashCode();
            }

            hash = (HASH_FACTOR * hash) + hashFactor;
        }

        return hash;
    }

    /**
     * Encodes the ElGamal gamma value.
     *
     * @return the ElGamal gamma value encoded.
     */
    public byte[] encodeGamma() {
        return _gamma.toByteArray();
    }

    /**
     * Encodes the ElGamal idxth phi value.
     *
     * @param idx
     *            the index of the phi value to get
     * @return the elGamal idxth phi value encoded.
     */
    public byte[] encodePhi(final int idx) {
        return _phis[idx].toByteArray();
    }

    /**
     * Encodes the ElGamal encrypted option IDs.
     *
     * @return the ElGamal encrypted option IDs encoded.
     */
    public byte[][] encodePhis() {
        byte[][] result = new byte[_phis.length][];

        for (int i = 0; i < _phis.length; i++) {
            result[i] = _phis[i].toByteArray();
        }

        return result;
    }

    /**
     * Returns the indexth phi
     *
     * @param idx
     *            the index of the phi value to get
     * @return the indexth phi. If the index value is bigger than the length of
     *         the phis it will return an exception.
     */
    public BigInteger getPhi(final int idx) {
        return _phis[idx];
    }

    /**
     * @return
     */
    public BigInteger[] getPhis() {
        return _phis.clone();
    }

    /**
     * Generates a bitwise concatenation of the ElGamal encryption pair. Can be
     * used as the data input of a hash function.
     *
     * @return Bitwise concatenation of ElGamal encryption pair.
     */
    public byte[] toByteArray() {
        byte[] byteArray = null;
        byteArray = ArrayUtils.addAll(byteArray, _gamma.toByteArray());

        for (BigInteger phi : _phis) {
            byteArray = ArrayUtils.addAll(byteArray, phi.toByteArray());
        }

        return byteArray;
    }
}
