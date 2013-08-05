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

import java.io.IOException;

import java.util.ArrayList;


/**
 * Implementation of ResultsList using an ArrayList.
 *
 * @version $Id: ResultsArrayList.java 10998 2011-10-18 14:04:09Z fvl $
 */
public class ResultsArrayList extends ArrayList<Result> implements ResultsList {
    private ResultWriter writer;

    @Override
    public void setWriter(ResultWriter writer) {
        this.writer = writer;
    }

    @Override
    public boolean hasWriter() {
        return writer != null;
    }

    @Override
    public void closeWriter() throws IOException {
        writer.close();
    }

    /**
     * Adds the result to the list, and sends it to the writer.
     * @param result The result to be added.
     * @return True if the result could be added.
     */
    @Override
    public boolean add(Result result) {
        synchronized (this) {
            return synchronizedAdd(result);
        }
    }

    private boolean synchronizedAdd(Result result) {
        boolean addResult = super.add(result);

        writeToWriterIfThereIsOne(result);

        return addResult;
    }

    private void writeToWriterIfThereIsOne(Result result) {
        if (hasWriter()) {
            writer.write(result);
        }
    }
}
