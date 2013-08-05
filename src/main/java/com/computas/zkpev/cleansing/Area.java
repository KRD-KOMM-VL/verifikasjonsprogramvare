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
package com.computas.zkpev.cleansing;

import com.computas.zkpev.CsvLineParseable;

import java.math.BigInteger;


/**
 * Class for the area codes and primes.
 *
 */
public class Area extends CsvLineParseable {
    private String path;
    private BigInteger prime;
    private String thresholdAreaId;
    private String area100Id;

    Area(String line) {
        super(line);
    }

    String getPath() {
        return path;
    }

    @Override
    protected void setAttributes(String[] attributes) throws Exception {
        path = getAttribute(attributes, AreaCsvIndex.PATH);
        prime = getBigIntegerOrNull(getAttribute(attributes, AreaCsvIndex.PRIME));
        thresholdAreaId = getAttribute(attributes, AreaCsvIndex.THRESHOLD_AREA);
        area100Id = getAttribute(attributes, AreaCsvIndex.AREA_100);
    }

    private BigInteger getBigIntegerOrNull(String attribute) {
        return "".equals(attribute) ? null : new BigInteger(attribute);
    }

    BigInteger getPrime() {
        return prime;
    }

    String getThresholdAreaId() {
        return thresholdAreaId;
    }

    String getArea100Id() {
        return area100Id;
    }

    /**
      * Indexes of the various fields in the CSV file.
      */
    private enum AreaCsvIndex {PATH,
        PRIME,
        THRESHOLD_AREA,
        AREA_100;
    }
}
