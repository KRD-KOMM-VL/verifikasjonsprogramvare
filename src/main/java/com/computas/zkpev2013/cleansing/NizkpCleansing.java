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
package com.computas.zkpev2013.cleansing;

import com.computas.zkpev2013.ZeroKnowledgeProof;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import java.math.BigInteger;

import java.net.URISyntaxException;
import java.net.URL;

import java.util.Properties;


/**
 * Class performing the non-interactive zero-knowledge proof verifying that the
 * result from the cleansing process doesn't contain any votes that were not
 * present in the VCS.
 *
 */
public class NizkpCleansing extends ZeroKnowledgeProof {
    private static final String EL_GAMAL_MODULUS_PROPERTY_NAME = "p";
    private static final int TICKS_TO_LOG_NO_OF_CLEANSED_VOTES_CHECKED = 5000;
    private static final int TICKS_TO_LOG_NO_OF_ENCRYPTED_VOTE_RETENTION_COUNTERS_CHECKED =
        5000;
    private String encryptedVotesFileName;
    private String cleansedFilesDir;
    private String elGamalPropertiesFileName;
    private String areasFileName;

    NizkpCleansing(String[] arguments) {
        super(arguments);
    }

    @Override
    protected void parseArguments(String[] arguments) {
        checkNoOfParameters(arguments,
            NizkpCleansingMandatoryArgumentIndex.values().length);
        elGamalPropertiesFileName = arguments[NizkpCleansingMandatoryArgumentIndex.EL_GAMAL_PROPERTIES_FILE_NAME_INDEX.ordinal()];
        areasFileName = arguments[NizkpCleansingMandatoryArgumentIndex.AREAS_FILE_NAME_INDEX.ordinal()];
        encryptedVotesFileName = arguments[NizkpCleansingMandatoryArgumentIndex.ENCRYPTED_VOTES_FILE_NAME_INDEX.ordinal()];
        cleansedFilesDir = arguments[NizkpCleansingMandatoryArgumentIndex.CLEANSED_FILES_DIR_INDEX.ordinal()];

        setOptionalResultsFileName(arguments,
            NizkpCleansingMandatoryArgumentIndex.values().length);
    }

    @Override
    protected void run() throws IOException, URISyntaxException {
        openResultsFileIfRequired();

        BigInteger modulus = loadElGamalModulus();
        AreasMap areas = loadAreas();
        EncryptedVotesMap encryptedVotes = loadEncryptedVotes(modulus);
        CleansedVotesList cleansedEncryptedVotes = loadCleansedVotes();
        checkCleansedVotesAgainstEncryptedVotes(cleansedEncryptedVotes,
            encryptedVotes, areas, modulus);

        checkEncryptedVoteRetentionCounters(encryptedVotes);

        closeResultsFileIfNeeded();
    }

    private void checkEncryptedVoteRetentionCounters(
        EncryptedVotesMap encryptedVotes) {
        int noOfEncryptedVoteRetentionCounterChecked = 0;

        for (EncryptedVoteRetentionCounter encryptedVoteRetentionCounter : encryptedVotes.values()) {
            checkEncryptedVoteRetentionCounter(encryptedVoteRetentionCounter);
            noOfEncryptedVoteRetentionCounterChecked++;

            if ((noOfEncryptedVoteRetentionCounterChecked % TICKS_TO_LOG_NO_OF_ENCRYPTED_VOTE_RETENTION_COUNTERS_CHECKED) == 0) {
                NizkpCleansing.getLogger()
                              .info(String.format(
                        "Checked %d cleansed votes against the encrypted votes so far.",
                        noOfEncryptedVoteRetentionCounterChecked));
            }
        }
    }

    private void checkCleansedVotesAgainstEncryptedVotes(
        CleansedVotesList cleansedEncryptedVotes,
        EncryptedVotesMap encryptedVotes, AreasMap areas, BigInteger modulus) {
        int noOfCleansedVotesChecked = 0;

        for (CleansedVote cleansedVote : cleansedEncryptedVotes) {
            checkCleansedVoteAgainstEncryptedVotes(cleansedVote,
                encryptedVotes, areas, modulus);
            noOfCleansedVotesChecked++;

            if ((noOfCleansedVotesChecked % TICKS_TO_LOG_NO_OF_CLEANSED_VOTES_CHECKED) == 0) {
                NizkpCleansing.getLogger()
                              .info(String.format(
                        "Checked %d cleansed votes against the encrypted votes so far.",
                        noOfCleansedVotesChecked));
            }
        }
    }

    BigInteger loadElGamalModulus() throws IOException {
        Properties properties = loadProperties(elGamalPropertiesFileName);

        return getBigIntegerProperty(properties, EL_GAMAL_MODULUS_PROPERTY_NAME);
    }

    private BigInteger getBigIntegerProperty(Properties properties, String key) {
        return new BigInteger(properties.getProperty(key));
    }

    protected Properties loadProperties(String fileName)
        throws IOException {
        Properties properties = new Properties();
        properties.load(getFileAsStream(fileName));

        return properties;
    }

    AreasMap loadAreas() throws IOException {
        AreasMap areas = new AreasHashMap();
        addFileContentToCollection(areasFileName, areas);

        return areas;
    }

    EncryptedVotesMap loadEncryptedVotes(BigInteger modulus)
        throws IOException {
        LOGGER.info(String.format("Loading the encrypted votes from %s.",
                encryptedVotesFileName));

        EncryptedVotesMap encryptedVotes = new EncryptedVotesHashMap(modulus);
        addFileContentToCollection(encryptedVotesFileName, encryptedVotes);
        LOGGER.info(String.format("All %d encrypted votes loaded.",
                encryptedVotes.size()));

        return encryptedVotes;
    }

    CleansedVotesList loadCleansedVotes()
        throws IOException, URISyntaxException {
        LOGGER.info(String.format("Loading the cleansed votes from %s.",
                cleansedFilesDir));

        CleansedVotesList cleansedEncryptedVotes = new CleansedVotesArrayList();
        String[] cleansedFileNames = getCleansedFileNamesInDir(cleansedFilesDir);

        for (String cleansedFileName : cleansedFileNames) {
            addFileContentToCollection(cleansedFilesDir + File.separator +
                cleansedFileName, cleansedEncryptedVotes);
        }

        LOGGER.info(String.format("All %d cleansed encrypted votes loaded.",
                cleansedEncryptedVotes.size()));

        return cleansedEncryptedVotes;
    }

    private String[] getCleansedFileNamesInDir(String dir)
        throws URISyntaxException {
        return getCsvFilesInDir(dir);
    }

    private String[] getCsvFilesInDir(String dirName) throws URISyntaxException {
        File dir = new File(dirName);

        if (!dir.exists()) {
            URL dirUrl = NizkpCleansing.class.getClassLoader()
                                             .getResource(dirName);

            dir = new File(dirUrl.toURI());
        }

        return dir.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.endsWith(".csv");
                }
            });
    }

    private void checkCleansedVoteAgainstEncryptedVotes(
        CleansedVote cleansedVote, EncryptedVotesMap encryptedVotes,
        AreasMap areas, BigInteger modulus) {
        EncryptedVoteRetentionCounter counter = encryptedVotes.findMatchForCleansedVote(cleansedVote);

        if ((counter == null) ||
                !counter.matches(cleansedVote, areas, modulus)) {
            results.add(new InjectedCleansedVoteLineIncident(cleansedVote));
        } else {
            counter.registerMatch(cleansedVote);
        }
    }

    private void checkEncryptedVoteRetentionCounter(
        EncryptedVoteRetentionCounter counter) {
        if (counter.getNoOfMatches() > 1) {
            results.add(new EncryptedVoteRetainedMoreThanOnceIncident(
                    counter.getEncryptedVote(), counter.getNoOfMatches()));
        }
    }

    /**
    * Main entry method.
    *
    * @param arguments The arguments to be passed to the constructor.
    */
    public static void main(String[] arguments) {
        try {
            NizkpCleansing nizkp = new NizkpCleansing(arguments);
            nizkp.run();
            LOGGER.info("Done.");
        } catch (IllegalArgumentException iae) {
            LOGGER.fatal(
                "Could not parse the arguments provided.\nCorrect usage:\n\tNizkpCleansing <ElGamalPropertiesFileName> <AreasFileName> <EncryptedVotesFileName> <CleansedFilesDirectory> [<ResultsFileName>]");
        } catch (Exception exception) {
            LOGGER.fatal("A fatal error occurred.", exception);
        }
    }

    String getEncryptedVotesFileName() {
        return encryptedVotesFileName;
    }

    String getCleansedFilesDir() {
        return cleansedFilesDir;
    }

    String getElGamalPropertiesFileName() {
        return elGamalPropertiesFileName;
    }

    String getAreasFileName() {
        return areasFileName;
    }
    enum NizkpCleansingMandatoryArgumentIndex {
        EL_GAMAL_PROPERTIES_FILE_NAME_INDEX,AREAS_FILE_NAME_INDEX,
        ENCRYPTED_VOTES_FILE_NAME_INDEX,
        CLEANSED_FILES_DIR_INDEX;
    }
}
