/**
 * Zero-Knowledge Protocols for E-Vote (ZKPEV).
 *
 * Copyright © 2011, The Norwegian Ministry of Local Government and Regional
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

import java.io.IOException;

import java.util.List;


/**
 * Interface for the List holding all results.
 *
 * @version $Id: ResultsList.java 10998 2011-10-18 14:04:09Z fvl $
 */
public interface ResultsList extends List<Result> {
    /**
     * Sets a Writer to which the results can be written.
     * @param writer Where the results should be written to.
     */
    void setWriter(ResultWriter writer);

    /**
     * Returns true if the ResultsList has a Writer set.
     * @return True if the ResultsList has a Writer set.
     */
    boolean hasWriter();

    /**
     * Closes the Writer.
     * @throws IOException Thrown if an exception occurred while trying to close the Writer.
     */
    void closeWriter() throws IOException;
}
