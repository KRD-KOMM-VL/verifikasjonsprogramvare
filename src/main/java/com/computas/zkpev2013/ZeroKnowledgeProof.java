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
package com.computas.zkpev2013;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URL;


/**
 * Abstract class supporting zero-knowledge proofs.
 */
public abstract class ZeroKnowledgeProof {
    protected static final Logger LOGGER = initializeLogger();
    private static final String LOG4J_PROPERTIES = "log4j.properties";
    protected final ResultsList results;
    private String resultsFileName;

    protected ZeroKnowledgeProof(String[] arguments) {
        parseArguments(arguments);
        results = new ResultsArrayList();
    }

    protected abstract void parseArguments(String[] arguments);

    protected abstract void run() throws Exception;

    private static Logger initializeLogger() {
        URL configurationFile = getResource(LOG4J_PROPERTIES);
        PropertyConfigurator.configure(configurationFile);

        return Logger.getLogger(ZeroKnowledgeProof.class);
    }

    private static URL getResource(String resourceName) {
        return getClassLoader().getResource(resourceName);
    }

    private static ClassLoader getClassLoader() {
        return ZeroKnowledgeProof.class.getClassLoader();
    }

    protected void checkNoOfParameters(String[] arguments,
        int minimalNumberOfParameters) {
        if (arguments.length < minimalNumberOfParameters) {
            throw new IllegalArgumentException();
        }
    }

    protected void setOptionalResultsFileName(String[] arguments,
        int minimalNumberOfParameters) {
        if (arguments.length > minimalNumberOfParameters) {
            setResultsFileName(arguments[minimalNumberOfParameters]);
        }
    }

    protected void setResultsFileName(String resultsFileName) {
        this.resultsFileName = resultsFileName;
    }

    /**
     * Returns the name of the file to which results should be logged.
     * @return The name of the file to which the results should be logged.
     */
    public String getResultsFileName() {
        return resultsFileName;
    }

    /**
     * Returns the results for the ZKP.
     * @return A ResultsList containing all results.
     */
    public ResultsList getResults() {
        return results;
    }

    /**
     * Returns whether logging of the results to a file is required or not.
     * @return True if logging to a results file is required.
     */
    public boolean isLoggingOfResultsRequired() {
        return resultsFileName != null;
    }

    /**
     * Opens the file to which the results should be logged, if logging
     * to a file is required.
     */
    public void openResultsFileIfRequired() {
        if (isLoggingOfResultsRequired()) {
            openResultsFile();
            LOGGER.info(String.format("Results file %s opened for writing.",
                    resultsFileName));
        } else {
            LOGGER.info(
                "No results file name provided -- results will not be saved to file.");
        }
    }

    private void openResultsFile() {
        ResultWriter writer = createAutoFlushingResultPrintWriter();
        results.setWriter(writer);
    }

    private ResultWriter createAutoFlushingResultPrintWriter() {
        try {
            return tryToCreateAutoFlushingResultPrintWriter();
        } catch (FileNotFoundException e) {
            LOGGER.warn(String.format(
                    "Could not open %s to write the results to.",
                    resultsFileName), e);

            return null;
        }
    }

    private ResultWriter tryToCreateAutoFlushingResultPrintWriter()
        throws FileNotFoundException {
        return new AutoFlushingResultPrintWriter(resultsFileName);
    }

    protected void closeResultsFileIfNeeded() {
        if (results.hasWriter()) {
            closeResultsFile();
            LOGGER.info(String.format(
                    "Results file %s closed for writing; wrote %d events to it.",
                    resultsFileName, results.size()));
        }
    }

    private void closeResultsFile() {
        try {
            results.closeWriter();
        } catch (IOException e) {
            LOGGER.warn("An exception occurred while trying to clode the results file.",
                e);
        }
    }

    protected void addFileContentToCollection(String fileName,
        Collection collection) throws IOException {
        InputStream stream = getFileAsStream(fileName);
        InputStreamReader reader = new InputStreamReader(stream);
        BufferedReader bufferedReader = new BufferedReader(reader);

        try {
            collection.addReaderContent(bufferedReader, results, LOGGER);
        } finally {
            bufferedReader.close();
            reader.close();
            stream.close();
        }
    }

    protected InputStream getFileAsStream(String fileName) {
        try {
            return new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            return getClassLoader().getResourceAsStream(fileName);
        }
    }
}
