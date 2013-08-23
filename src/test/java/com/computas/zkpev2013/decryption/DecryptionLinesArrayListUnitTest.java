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
package com.computas.zkpev2013.decryption;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * Unit tests against DecryptionLinesArrayList.
 *
 */
public class DecryptionLinesArrayListUnitTest {
    private DecryptionLinesArrayList list;

    /**
     * Creates a DecryptionLinesArrayList for the tests.
     */
    @BeforeMethod
    public void createDecryptionLinesArrayList() {
        list = new DecryptionLinesArrayList();
    }

    /**
     * Verifies that the list is empty by default.
     */
    @Test
    public void mustBeEmptyByDefault() {
        assertTrue(list.isEmpty());
    }

    /**
     * Verifies that an empty batch is returned when the list is empty.
     */
    @Test
    public void mustReturnEmptyBatchIfEmpty() {
        assertTrue(list.popBatch(null).isEmpty());
    }

    /**
     * Verifies that a batch with 1 decryption line is returned if the list contains only 1 decryption line.
     */
    @Test
    public void mustReturnABatchOfSize1IfSizeIs1() {
        addDecryptionLines(1);
        assertEquals(list.popBatch(null).size(), 1);
    }

    /**
     * Verifies that the list is empty after extracting the only line.
     */
    @Test
    public void mustBeEmptyAfterExtractingABatchOfSize1() {
        addDecryptionLines(1);
        list.popBatch(null);
        assertTrue(list.isEmpty());
    }

    /**
     * Verifies that a batch with BATCH_SIZE decryption lines is returned if the list contains exactly BATCH_SIZE decryption lines.
     */
    @Test
    public void mustReturnABatchOfSizeBatchSizeIfSizeIsBatchSize() {
        addDecryptionLines(DecryptionLinesArrayList.BATCH_SIZE);
        assertEquals(list.popBatch(null).size(),
            DecryptionLinesArrayList.BATCH_SIZE);
    }

    /**
     * Verifies that the list is empty after extracting a batch when the list contains
     * exactly BATCH_SIZE decryption lines.
     */
    @Test
    public void mustBeEmptyAfterExtractingABatchOfSizeBatchSizeIfSizeIsBatchSize() {
        addDecryptionLines(DecryptionLinesArrayList.BATCH_SIZE);
        list.popBatch(null);
        assertTrue(list.isEmpty());
    }

    /**
     * Verifies that a batch with BATCH_SIZE decryption lines is returned if the list contains exactly BATCH_SIZE decryption lines.
     */
    @Test
    public void mustReturnABatchOfSizeBatchSizeIfSizeIsBatchSizePlus1() {
        addDecryptionLines(DecryptionLinesArrayList.BATCH_SIZE + 1);
        assertEquals(list.popBatch(null).size(),
            DecryptionLinesArrayList.BATCH_SIZE);
    }

    /**
     * Verifies that the list is empty after extracting a batch when the list contains
     * exactly BATCH_SIZE decryption lines.
     */
    @Test
    public void mustHaveSize1AfterExtractingABatchOfSizeBatchSizeIfSizeIsBatchSizePlus1() {
        addDecryptionLines(DecryptionLinesArrayList.BATCH_SIZE + 1);
        list.popBatch(null);
        assertEquals(list.size(), 1);
    }

    private void addDecryptionLines(int numberOfDecryptionLines) {
        for (int i = 0; i < numberOfDecryptionLines; i++) {
            list.add(new DecryptionLine(
                    DecryptionLineUnitTest.SAMPLE_LINE_WITH_CORRECT_PROOF));
        }
    }
}
