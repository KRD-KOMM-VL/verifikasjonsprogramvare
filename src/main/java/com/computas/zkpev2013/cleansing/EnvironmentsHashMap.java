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
package com.computas.zkpev2013.cleansing;

import java.math.BigInteger;

import java.util.HashMap;


/**
 * An implementation of EnvironmentsMap using a HashMap.
 */
public class EnvironmentsHashMap extends HashMap<EnvironmentsMap.Environment, BigInteger>
    implements EnvironmentsMap {
    @Override
    public void setControlledPrime(BigInteger prime) {
        put(Environment.CONTROLLED, prime);
    }

    @Override
    public void setUncontrolledPrime(BigInteger prime) {
        put(Environment.UNCONTROLLED, prime);
    }

    @Override
    public BigInteger getPrime(EnvironmentsMap.Environment environment) {
        return this.get(environment);
    }

    static Environment getEnvironment(String channelId) {
        return Environment.valueOf(channelId.substring("CHANNEL_ID_".length()));
    }
}
