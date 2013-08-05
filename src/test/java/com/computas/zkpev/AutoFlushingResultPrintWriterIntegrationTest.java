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

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;


/**
 * Integration tests on the AutoFlushingResultPrintWriter class.
 *
 */
public class AutoFlushingResultPrintWriterIntegrationTest {
    private static final String TEST_FILE_NAME = "AutoFlushingResultPrintWriterIntegrationTestFile.txt";
    private AutoFlushingResultPrintWriter writer;

    /**
     * Creates a new writer against the TEST_FILE_NAME after deleting the file
     * first.
     * @throws FileNotFoundException Thrown if something goes wrong during the construction.
     */
    @BeforeMethod
    public void createAWriterAgainstAFreshFile() throws FileNotFoundException {
        File file = new File(TEST_FILE_NAME);
        file.delete();

        writer = new AutoFlushingResultPrintWriter(TEST_FILE_NAME);
    }

    /**
     * Verifies that the referenced file exists as soon as the writer has been
     * created.
     */
    @Test
    public void fileMustExistAfterConstructionOfWriter() {
        File file = new File(TEST_FILE_NAME);
        assertTrue(file.exists());
        writer.close();
    }

    /**
     * Verifies that it is possible to write an Incident to the writer
     * without an exception being thrown.
     */
    @Test
    public void canWriteAnIncidentWithoutAnExceptionBeingThrown() {
        writer.write(new Incident() {
                @Override
                protected String[] getValuesForCsvLine() {
                    return new String[] {  };
                }
            });
    }
}
