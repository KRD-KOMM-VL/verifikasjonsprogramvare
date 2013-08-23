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

import com.computas.zkpev2013.Collection;
import com.computas.zkpev2013.ElGamalZkp;
import com.computas.zkpev2013.Result;

import org.apache.log4j.Logger;

import java.io.IOException;

import java.security.NoSuchAlgorithmException;


/**
 * Class performing the non-interactive zero-knowledge proof for the correct
 * decryption in the counting server.
 *
 */
public class NizkpDecryption extends ElGamalZkp {
    private static final int MIN_NO_OF_PARAMETERS_ALLOWED = 3;
    private String decryptionFileName;
    private DecryptionLinesArrayList decryptionLines;

    NizkpDecryption(String[] arguments) {
        super(arguments);
    }

    /**
    * Main entry method.
    *
    * @param arguments The arguments to be passed to the constructor.
    */
    public static void main(String[] arguments) {
        try {
            NizkpDecryption nizkp = new NizkpDecryption(arguments);
            nizkp.run();
            LOGGER.info("Done.");
        } catch (IllegalArgumentException iae) {
            LOGGER.fatal(
                "Could not parse the arguments provided.\nCorrect usage:\n\tNizkpDecryption <ElGamalPropertiesFileName> <ElGamalPublicKeyFileName> <DecryptionFileName> [<ResultsFileName>]");
        } catch (Exception exception) {
            LOGGER.fatal("A fatal error occurred.", exception);
        }
    }

    @Override
    protected void parseArguments(String[] arguments) {
        checkNoOfParameters(arguments, MIN_NO_OF_PARAMETERS_ALLOWED);

        setElGamalPropertiesFileName(arguments[0]);
        setElGamalPublicKeysFileName(arguments[1]);
        decryptionFileName = arguments[2];

        setOptionalResultsFileName(arguments, MIN_NO_OF_PARAMETERS_ALLOWED);
    }

    @Override
    protected void run() throws IOException, NoSuchAlgorithmException {
        openResultsFileIfRequired();
        loadElGamalProperties();
        loadElGamalPublicKeys();

        loadDecryptionLines();
        verifyDecryptionLineProofs();
        closeResultsFileIfNeeded();
    }

    private void verifyDecryptionLineProofs() throws NoSuchAlgorithmException {
        Thread[] workers = setUpWorkers();
        waitForWorkersToFinish(workers);
    }

    private Thread[] setUpWorkers() {
        return setUpWorkers(getNoOfWorkers());
    }

    private int getNoOfWorkers() {
        return Runtime.getRuntime().availableProcessors();
    }

    private Thread[] setUpWorkers(int noOfWorkers) {
        LOGGER.info(String.format("Setting up %d worker threads.", noOfWorkers));

        Thread[] workers = new Thread[noOfWorkers];

        for (int i = 0; i < noOfWorkers; i++) {
            workers[i] = setUpWorker();
        }

        return workers;
    }

    private Thread setUpWorker() {
        Thread worker = new DecryptionVerificationWorker(this, getP(), getG(),
                getH(), LOGGER);
        worker.start();

        return worker;
    }

    private void waitForWorkersToFinish(Thread[] workers) {
        for (Thread worker : workers) {
            tryToWaitForWorker(worker);
        }
    }

    private void tryToWaitForWorker(Thread worker) {
        try {
            worker.join();
        } catch (InterruptedException e) {
            LOGGER.error("An exception occured while trying to wait for a worker thread to finish",
                e);
        }
    }

    String getDecryptionFileName() {
        return decryptionFileName;
    }

    void loadDecryptionLines() throws IOException {
        LOGGER.info(String.format("Loading the decryption lines from %s.",
                decryptionFileName));

        decryptionLines = new DecryptionLinesArrayList();
        addFileContentToCollection(decryptionFileName,
            (Collection) decryptionLines);
        LOGGER.info("All decryption lines from the Counting Server loaded.");
    }

    DecryptionLinesArrayList getDecryptionLines() {
        return decryptionLines;
    }

    DecryptionLinesList getNextDecryptionLineBatch() {
        return decryptionLines.popBatch(LOGGER);
    }

    void addResult(Result result) {
        results.add(result);
    }
}
