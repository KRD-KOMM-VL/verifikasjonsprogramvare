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

import org.apache.commons.codec.binary.Base64;

import java.math.BigInteger;

import java.util.ArrayList;
import java.util.List;


/**
 * Abstract superclass for domain objects that can be parsed in from a CSV
 * line.
 */
public abstract class CsvLineParseable {
    protected static final String COMMA = ",";

    protected CsvLineParseable(String line) {
        try {
            parseLineAndSetAttributes(line);
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format(
                    "Could not parse the line '%s'.", line), e);
        }
    }

    private void parseLineAndSetAttributes(String line)
        throws Exception {
        String[] attributes = line.split(COMMA, -1);

        setAttributes(attributes);
    }

    protected abstract void setAttributes(String[] attributes)
        throws Exception;

    protected String getAttribute(String[] attributes, Enum index) {
        return new String(attributes[index.ordinal()]);
    }

    protected long getAttributeAsLong(String[] attributes, Enum index) {
        return Long.parseLong(getAttribute(attributes, index));
    }

    protected String getAttributeAsString(String[] attributes, Enum index) {
        return decodeNewLines(getAttribute(attributes, index)
                                  .replace("#r#", "\r"));
    }

    protected byte[] getAttributeAsByteArray(String[] attributes, Enum index) {
        return Base64.decodeBase64(getAttribute(attributes, index));
    }

    protected byte[][] getAttributeAsByteArrayArray(String[] attributes,
        Enum index) {
        String byteArrayArrayString = decodeNewLines(getAttribute(attributes,
                    index));

        List<byte[]> byteArrayList = new ArrayList<byte[]>();

        String[] byteArrayStrings = byteArrayArrayString.split("#");

        for (String byteArrayString : byteArrayStrings) {
            byteArrayList.add(Base64.decodeBase64(byteArrayString));
        }

        return byteArrayList.toArray(new byte[][] {  });
    }

    private String decodeNewLines(String s) {
        return s.replace("#n#", "\n");
    }

    protected ElGamalEncryptionTuple getAttributeAsElGamalEncryptionTuple(
        String[] attributes, Enum index) {
        String encodedVotingOptionIdsProductString = getAttributeAsString(attributes,
                index);
        String[] encodedVotingOptionIdsProductStrings = encodedVotingOptionIdsProductString.split(
                "#");

        if (encodedVotingOptionIdsProductStrings.length < 2) {
            throw new IllegalArgumentException(String.format(
                    "Could not parse a field as an ElGamal encryption tuple: %s.",
                    encodedVotingOptionIdsProductString));
        }

        BigInteger encodedVotingOptionIdsProductPublicKeyComponent = new BigInteger(Base64.decodeBase64(
                    encodedVotingOptionIdsProductStrings[0]));
        ArrayList<BigInteger> encodedVotingOptionIdsProductMessageComponents = new ArrayList<BigInteger>();

        for (int i = 1; i < encodedVotingOptionIdsProductStrings.length; i++) {
            encodedVotingOptionIdsProductMessageComponents.add(new BigInteger(
                    Base64.decodeBase64(encodedVotingOptionIdsProductStrings[i])));
        }

        return new ElGamalEncryptionTuple(encodedVotingOptionIdsProductPublicKeyComponent,
            encodedVotingOptionIdsProductMessageComponents);
    }
}
