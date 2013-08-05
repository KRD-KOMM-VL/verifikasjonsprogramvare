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

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import java.util.ArrayList;


/**
 * Unit tests on the ResultsArrayList class.
 *
 * @version $Id: ResultsArrayListUnitTest.java 11011 2011-10-20 08:26:55Z fvl $
 */
public class ResultsArrayListUnitTest {
    /**
     * Verifies that when no writer has been set, a result can be added
     * to the results list without an exception being thrown.
     */
    @Test
    public void mustNotThrowExceptionsWhenAResultIsAddedWithoutAWriterBeingSet() {
        ResultsList list = new ResultsArrayList();

        list.add(new Incident() {
                @Override
                protected String[] getValuesForCsvLine() {
                    return new String[] {  };
                }
            });
    }

    /**
     * Verifies that a result is written to the writer when a writer is set.
     */
    @Test
    public void mustWriteToWriterWhenAResultIsAdded() {
        ResultsList list = new ResultsArrayList();
        ArrayListResultWriter writer = new ArrayListResultWriter();
        list.setWriter(writer);

        Incident incident = new Incident() {
                @Override
                protected String[] getValuesForCsvLine() {
                    return new String[] {  };
                }
            };

        list.add(incident);
        assertTrue(list.contains(incident));
    }

    private final class ArrayListResultWriter extends ArrayList<Result>
        implements ResultWriter {
        @Override
        public void close() {
        }

        @Override
        public void write(Result result) {
            add(result);
        }
    }
}
