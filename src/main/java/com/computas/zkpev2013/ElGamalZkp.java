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

import com.computas.zkpev.ZeroKnowledgeProof;

import java.io.IOException;

import java.math.BigInteger;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;


/**
 * Superclass for ElGamal-based zero-knowledge proofs.
 *
 */
public abstract class ElGamalZkp extends ZeroKnowledgeProof {
    private static final String EL_GAMAL_PUBLIC_KEYS_PROPERTY_NAME = "publicKeys";
    private static final String EL_GAMAL_GENERATOR_PROPERTY_NAME = "generator";
    private static final String EL_GAMAL_GROUP_ORDER_PROPERTY_NAME = "q";
    private static final String EL_GAMAL_MODULUS_PROPERTY_NAME = "p";
    private String elGamalPropertiesFileName;
    private String elGamalPublicKeysFileName;
    private BigInteger p;
    private BigInteger q;
    private BigInteger g;
    private ElGamalPublicKeyList h;

    protected ElGamalZkp(String[] arguments) {
        super(arguments);
    }

    /**
     * Returns the file name where the ElGamal properties can be found.
     * @return The file name where the ElGamal properties can be found.
     */
    public String getElGamalPropertiesFileName() {
        return elGamalPropertiesFileName;
    }

    protected void setElGamalPropertiesFileName(
        String elGamalPropertiesFileName) {
        this.elGamalPropertiesFileName = elGamalPropertiesFileName;
    }

    /**
     * Returns the file name where the ElGamal public key can be found.
     * @return The file name where the ElGamal public key can be found.
     */
    public String getElGamalPublicKeysFileName() {
        return elGamalPublicKeysFileName;
    }

    protected void setElGamalPublicKeysFileName(String elGamalPublicKeyFileName) {
        this.elGamalPublicKeysFileName = elGamalPublicKeyFileName;
    }

    protected void loadElGamalProperties() throws IOException {
        Properties properties = loadProperties(elGamalPropertiesFileName);
        p = getBigIntegerProperty(properties, EL_GAMAL_MODULUS_PROPERTY_NAME);
        q = getBigIntegerProperty(properties, EL_GAMAL_GROUP_ORDER_PROPERTY_NAME);
        g = getBigIntegerProperty(properties, EL_GAMAL_GENERATOR_PROPERTY_NAME);
    }

    protected void loadElGamalPublicKeys() throws IOException {
        Properties properties = loadProperties(elGamalPublicKeysFileName);
        h = getElGamalPublicKeyListProperty(properties,
                EL_GAMAL_PUBLIC_KEYS_PROPERTY_NAME);
    }

    protected Properties loadProperties(String fileName)
        throws IOException {
        Properties properties = new Properties();
        properties.load(getFileAsStream(fileName));

        return properties;
    }

    private BigInteger getBigIntegerProperty(Properties properties, String key) {
        return new BigInteger(properties.getProperty(key));
    }

    private ElGamalPublicKeyList getElGamalPublicKeyListProperty(
        Properties properties, String key) {
        List<String> stringList = Arrays.asList(properties.getProperty(key)
                                                          .split(","));
        ElGamalPublicKeyList pkList = new ElGamalPublicKeyList();

        for (String str : stringList) {
            pkList.add(new BigInteger(str));
        }

        return pkList;
    }

    protected BigInteger getP() {
        return p;
    }

    BigInteger getQ() {
        return q;
    }

    protected BigInteger getG() {
        return g;
    }

    protected ElGamalPublicKeyList getH() {
        return h;
    }
}
