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
package com.computas.zkpev;

import com.scytl.evote.protocol.integration.voting.model.AuthToken;

import org.apache.commons.lang.ArrayUtils;

import java.io.UnsupportedEncodingException;


/**
 * Facade to Apache's commons-lang ArrayUtils for byte array concatenation.
 *
 */
public final class ByteArrayUtils {
    static final String UTF_8 = "UTF-8";

    private ByteArrayUtils() {
        // This utility class should not be instantiated.
    }

    /**
     * Concatenates objects as byte arrays to each other.
     * @param objects The objects to be concatenated into a byte array.
     * @return A byte array.
     * @throws UnsupportedEncodingException Should normally not be thrown.
     */
    public static byte[] concatenateAsByteArrays(Object... objects)
        throws UnsupportedEncodingException {
        byte[] result = new byte[] {  };

        for (Object object : objects) {
            result = concatenateAsByteArray(result, object);
        }

        return result;
    }

    private static byte[] concatenateAsByteArray(byte[] result, Object object)
        throws UnsupportedEncodingException {
        byte[] newResult = null;

        if (object instanceof byte[]) {
            newResult = concatenateByteArrays(result, (byte[]) object);
        } else if (object instanceof String) {
            newResult = convertToByteArrayAndConcatenate(result, (String) object);
        } else if (object instanceof Long) {
            newResult = convertToByteArrayAndConcatenate(result, (Long) object);
        } else if (object instanceof byte[][]) {
            newResult = convertToByteArrayAndConcatenate(result,
                    (byte[][]) object);
        } else if (object instanceof AuthToken) {
            newResult = convertToByteArrayAndConcatenate(result,
                    (AuthToken) object);
        } else {
            throw new IllegalArgumentException(String.format(
                    "Cannot concatenate an object of class %s to a byte array.",
                    object.getClass().getName()));
        }

        return newResult;
    }

    private static byte[] convertToByteArrayAndConcatenate(byte[] result,
        byte[][] arrayOfByteArrays) {
        byte[] newResult = result;

        for (byte[] byteArray : arrayOfByteArrays) {
            newResult = ByteArrayUtils.concatenateByteArrays(newResult,
                    byteArray);
        }

        return newResult;
    }

    private static byte[] convertToByteArrayAndConcatenate(byte[] result, Long l)
        throws UnsupportedEncodingException {
        return concatenateByteArrays(result, String.valueOf(l).getBytes(UTF_8));
    }

    private static byte[] convertToByteArrayAndConcatenate(byte[] result,
        AuthToken thisAuthToken) {
        return concatenateByteArrays(result, thisAuthToken.toByteArray());
    }

    private static byte[] convertToByteArrayAndConcatenate(byte[] result,
        String string) throws UnsupportedEncodingException {
        return concatenateByteArrays(result, string.getBytes(UTF_8));
    }

    private static byte[] concatenateByteArrays(byte[] result, byte[] object) {
        return ArrayUtils.addAll(result, object);
    }
}
