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


/**
 * Abstract class supporting zero-knowledge proofs.
 */
public abstract class ZeroKnowledgeProof {
    protected final ResultsList results;
    private String resultsFileName;

    protected ZeroKnowledgeProof(String[] arguments) {
        parseArguments(arguments);
        results = new ResultsArrayList();
    }

    protected abstract void parseArguments(String[] arguments);

    protected abstract void run() throws Exception;

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
}
