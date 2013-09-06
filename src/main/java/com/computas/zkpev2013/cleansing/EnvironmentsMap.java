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

import java.util.Map;


/**
 * Definition of a map holding the environments and their primes.
 */
public interface EnvironmentsMap extends Map<EnvironmentsMap.Environment, BigInteger> {
    /**
     * Sets the prime for the controlled environment.
     *
     * @param prime The prime for the controlled environment.
     */
    void setControlledPrime(BigInteger prime);

    /**
     * Sets the prime for the uncontrolled environment.
     *
     * @param prime The prime for the uncontrolled environment.
     */
    void setUncontrolledPrime(BigInteger prime);

    /**
     * Returns the prime for an environment.
     *
     * @param key The environment.
     * @return The prime.
     */
    BigInteger getPrime(Environment key);

    /**
     * Enumeration of all possible environments used by EnvironmentsHashMap.
     */
    public enum Environment {CONTROLLED,
        UNCONTROLLED;
    }
}
