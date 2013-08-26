/**
 * Zero-Knowledge Protocols for E-Vote (ZKPEV).
 *
 * Copyright © 2013, The Norwegian Ministry of Local Government and Regional
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
package com.computas.zkpev2013;

import java.math.BigInteger;

import java.util.ArrayList;


/**
 * Class to implement one public key for each voting option.
 * Aggregate key is the product of each option key in the group.
 */
public class ElGamalPublicKeyList extends ArrayList<BigInteger> {
    private BigInteger aggregateKey;

    /**
     *  Constructor for ElGamalPublicKeyList.
     */
    public ElGamalPublicKeyList() {
        super();
    }

    /**
    * Constructor for ElGamalPublicKeyList.
    *
    * @param optionKeys an ArrayList to be passed to the constructor.
    */
    public ElGamalPublicKeyList(ArrayList<BigInteger> optionKeys) {
        super();

        for (BigInteger optKey : optionKeys) {
            this.add(optKey);
        }
    }

    /**
    * Helper constructor to make transition from 2011 easier.
    * @param optionKey a single BigInteger public key.
    */
    public ElGamalPublicKeyList(BigInteger optionKey) {
        super();
        this.add(optionKey);
    }

    /**
     *  Getter for the BigInteger aggregateKey.
     *
     * @return BigInteger aggregateKey.
     */
    public BigInteger getAggregateKey() {
        return aggregateKey;
    }

    /**
     *  Help method to calculate aggregate keys.
     *
     * @param modulus a BigInteger that specifies the multiplication group.
     */
    public void calculateAggregateKey(BigInteger modulus) {
        aggregateKey = BigInteger.ONE;

        for (BigInteger optKey : this) {
            aggregateKey = aggregateKey.multiply(optKey).mod(modulus);
        }
    }
}
