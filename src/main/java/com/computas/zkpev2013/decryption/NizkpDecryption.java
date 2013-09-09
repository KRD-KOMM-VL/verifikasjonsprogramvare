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

import java.io.IOException;

import java.security.NoSuchAlgorithmException;


/**
 * Class performing the non-interactive zero-knowledge proof for the correct
 * decryption in the counting server.
 */
public class NizkpDecryption extends ElGamalZkp {
    private static final int MIN_NO_OF_PARAMETERS_ALLOWED = 4;
    private String decryptionFileName;
    private DecryptionLinesArrayList decryptionLines;
    private MixingType mixingType;

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
                "Could not parse the arguments provided.\nCorrect usage:\n\tNizkpDecryption <ElGamalPropertiesFileName> <MixingType> <ElGamalPublicKeyFileName> <DecryptionFileName> [<ResultsFileName>]");
        } catch (Exception exception) {
            LOGGER.fatal("A fatal error occurred.", exception);
        }
    }

    @Override
    protected void parseArguments(String[] arguments) {
        checkNoOfParameters(arguments, MIN_NO_OF_PARAMETERS_ALLOWED);

        setElGamalPropertiesFileName(arguments[ArgumentsIndex.EL_GAMAL_PROPERTIES_FILE_NAME.ordinal()]);
        setElGamalPublicKeysFileName(arguments[ArgumentsIndex.EL_GAMAL_PUBLIC_KEYS_FILE_NAME.ordinal()]);
        setMixingType(arguments[ArgumentsIndex.MIXING_TYPE.ordinal()]);
        decryptionFileName = arguments[ArgumentsIndex.DECRYPTION_FILE_NAME.ordinal()];

        setOptionalResultsFileName(arguments,
            ArgumentsIndex.RESULTS_FILE_NAME.ordinal());
    }

    private void setMixingType(String mixingTypeString) {
        this.mixingType = MixingType.valueOf(mixingTypeString);
    }

    @Override
    protected void run() throws IOException, NoSuchAlgorithmException {
        openResultsFileIfRequired();
        loadElGamalProperties();
        loadElGamalPublicKeys();
        calculateElGamalAggregateKey();

        loadDecryptionLines();
        runWorkers();
        closeResultsFileIfNeeded();
    }

    @Override
    protected Thread createWorker() {
        return new DecryptionVerificationWorker(this, getP(), getG(), getH());
    }

    String getDecryptionFileName() {
        return decryptionFileName;
    }

    void loadDecryptionLines() throws IOException {
        LOGGER.info(String.format("Loading the decryption lines from %s.",
                decryptionFileName));

        decryptionLines = new DecryptionLinesArrayList(mixingType);
        addFileContentToCollection(decryptionFileName,
            (Collection) decryptionLines);
        LOGGER.info(String.format(
                "All %d decryption lines from the Counting Server loaded.",
                decryptionLines.size()));
    }

    DecryptionLinesArrayList getDecryptionLines() {
        return decryptionLines;
    }

    DecryptionLinesList getNextDecryptionLineBatch() {
        return decryptionLines.popBatch(LOGGER);
    }
    enum MixingType {SCYTL,
        VERIFICATUM;
    }
    enum ArgumentsIndex {EL_GAMAL_PROPERTIES_FILE_NAME,
        EL_GAMAL_PUBLIC_KEYS_FILE_NAME,
        MIXING_TYPE,
        DECRYPTION_FILE_NAME,
        RESULTS_FILE_NAME;
    }
}
