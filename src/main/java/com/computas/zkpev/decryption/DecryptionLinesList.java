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
package com.computas.zkpev.decryption;

import com.computas.zkpev.Collection;

import java.util.List;


/**
 * Interface for the List holding all the decryption lines from the Counting server.
 *
 * @version $Id: DecryptionLinesList.java 10998 2011-10-18 14:04:09Z fvl $
 *
 */
public interface DecryptionLinesList extends List<DecryptionLine>, Collection {
    /**
     * Pops a batch from this list. The batch is returned as a new DecryptionLinesList object,
     * containing a number of decryption lines which are removed from this list. If this list is
     * empty, an empty list is returned.
     *
     * @return A new DecryptionLinesList object, representing a batch.
     */
    DecryptionLinesList popBatch();
}
