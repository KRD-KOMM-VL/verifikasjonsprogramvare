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

import com.computas.zkpev2013.ResultsList;
import com.computas.zkpev2013.decryption.NizkpDecryption.MixingType;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;

import java.util.ArrayList;


/**
 * Implementation of DecryptionLinesList using an ArrayList.
 */
public class DecryptionLinesArrayList extends ArrayList<DecryptionLine>
    implements DecryptionLinesList {
    static final int BATCH_SIZE = 1000;
    private final MixingType mixingType;

    DecryptionLinesArrayList(MixingType mixingType) {
        this.mixingType = mixingType;
    }

    @Override
    public void addReaderContent(BufferedReader bufferedReader,
        ResultsList results, Logger logger) throws IOException {
        String line = readNextLine(bufferedReader);

        while (line != null) {
            addDecryptionLineOrCreateIncident(line, results);
            line = readNextLine(bufferedReader);
        }
    }

    private String readNextLine(BufferedReader bufferedReader)
        throws IOException {
        return bufferedReader.readLine();
    }

    private void addDecryptionLineOrCreateIncident(String line,
        ResultsList results) {
        try {
            tryToAddDecryptionLine(line);
        } catch (IllegalArgumentException iae) {
            results.add(new DecryptionFileBrokenLineIncident(line));
        }
    }

    private void tryToAddDecryptionLine(String line) {
        add(createDecryptionLine(line));
    }

    private DecryptionLine createDecryptionLine(String line) {
        if (mixingType.equals(MixingType.SCYTL)) {
            return new ScytlDecryptionLine(line);
        } else {
            return new VerificatumDecryptionLine(line);
        }
    }

    @Override
    public DecryptionLinesList popBatch(Logger logger) {
        synchronized (this) {
            return popBatch(getBatchSize(), logger);
        }
    }

    private int getBatchSize() {
        return (size() >= BATCH_SIZE) ? BATCH_SIZE : size();
    }

    private DecryptionLinesList popBatch(int batchSize, Logger logger) {
        DecryptionLinesList batch = new DecryptionLinesArrayList(mixingType);

        for (int i = 0; i < batchSize; i++) {
            batch.add(remove(0));
        }

        if ((logger != null) && (batchSize > 0)) {
            logger.info(String.format(
                    "Popped %d items from the decryption lines list, leaving %d items to be processed later.",
                    batchSize, size()));
        }

        return batch;
    }
}
