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

import com.scytl.evote.protocol.signers.SchnorrSignature;

import java.io.Serializable;

import java.util.Vector;


/**
 * Container class for the collection of re-encryption proofs of all vote groups
 * defined by a given mix-node. Each re-encryption proof consists of a Schnorr
 * signature that acts as a zero knowledge proof of the secret exponents that
 * were used to ElGamal re-encrypt the votes of the given vote group.
 */
public class ReEncryptionProofs implements Serializable {
    private static final long serialVersionUID = 5494358439258680661L;

    /**
     * Collection of re-encryption proofs.
     */
    private final Vector<SchnorrSignature> _reEncProofs;

    /**
     * Constructor.
     */
    public ReEncryptionProofs() {
        _reEncProofs = new Vector<SchnorrSignature>();
    }

    /**
     * Adds the re-encryption proof of a given vote group to the collection.
     *
     * @param reEncProof
     *            Re-encryption proof of vote group.
     */
    public void add(final SchnorrSignature reEncProof) {
        _reEncProofs.add(reEncProof);
    }

    /**
     * Retrieves the collection of re-encryption proofs.
     *
     * @return Collection of re-encryption proofs.
     */
    public Vector<SchnorrSignature> get() {
        return _reEncProofs;
    }

    /**
     * Retrieves the re-encryption proof of a given vote group from the
     * collection, given its index.
     *
     * @param index
     *            Index of re-encryption proof in collection.
     * @return Re-encryption proof.
     */
    public SchnorrSignature get(final int index) {
        return _reEncProofs.get(index);
    }

    /**
     * Retrieves the size of the re-encryption proof collection.
     *
     * @return Size of re-encryption proof collection.
     */
    public int size() {
        return _reEncProofs.size();
    }

    /**
     * Clears the re-encryption proof collection.
     */
    public void clear() {
        _reEncProofs.clear();
    }
}
