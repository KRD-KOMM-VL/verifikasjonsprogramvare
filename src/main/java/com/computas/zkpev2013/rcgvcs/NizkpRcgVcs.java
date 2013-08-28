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
package com.computas.zkpev2013.rcgvcs;

import com.computas.zkpev2013.EncryptedVote;
import com.computas.zkpev2013.ZeroKnowledgeProof;

import java.io.IOException;


/**
 * Class performing the non-interactive zero-knowledge proof on
 * the Return Code Generator (RCG) and the Vote Collector Server (VCS).
 *
 */
public class NizkpRcgVcs extends ZeroKnowledgeProof {
    private static final int MIN_NO_OF_PARAMETERS_ALLOWED = 2;
    private String encryptedVotesFileName;
    private String receiptsFileName;

    NizkpRcgVcs(String[] arguments) {
        super(arguments);
    }

    @Override
    protected void parseArguments(String[] arguments) {
        checkNoOfParameters(arguments, MIN_NO_OF_PARAMETERS_ALLOWED);

        encryptedVotesFileName = arguments[0];
        receiptsFileName = arguments[1];

        setOptionalResultsFileName(arguments, MIN_NO_OF_PARAMETERS_ALLOWED);
    }

    /**
    * Main entry method.
    *
    * @param arguments The arguments to be passed to the constructor.
    */
    public static void main(String[] arguments) {
        try {
            NizkpRcgVcs nizkp = new NizkpRcgVcs(arguments);
            nizkp.run();
            LOGGER.info("Done.");
        } catch (IllegalArgumentException iae) {
            LOGGER.fatal(
                "Could not parse the arguments provided.\nCorrect usage:\n\tNizkpRcgVcs <EncryptedVotesFileName> <ReceiptsFileName> [<ResultsFileName>]");
        } catch (Exception exception) {
            LOGGER.fatal("A fatal error occurred.", exception);
        }
    }

    String getEncryptedVotesFileName() {
        return encryptedVotesFileName;
    }

    String getReceiptsFileName() {
        return receiptsFileName;
    }

    @Override
    protected void run() throws IOException {
        openResultsFileIfRequired();

        VotingReceiptsMap votingReceipts = loadVotingReceipts();
        EncryptedVotesList encryptedVotes = loadEncryptedVotes();

        for (EncryptedVote encryptedVote : encryptedVotes) {
            checkEncryptedVoteAgainstHashes(encryptedVote, votingReceipts);
        }

        for (VotingReceiptCounter votingReceiptCounter : votingReceipts.values()) {
            checkVotingReceiptCounter(votingReceiptCounter);
        }

        closeResultsFileIfNeeded();
    }

    VotingReceiptsMap loadVotingReceipts() throws IOException {
        LOGGER.info(String.format("Loading the voting receipts from %s.",
                receiptsFileName));

        VotingReceiptsMap votingReceipts = new VotingReceiptsHashMap();
        addFileContentToCollection(receiptsFileName, votingReceipts);
        LOGGER.info(String.format("All %d voting receipts loaded.",
                votingReceipts.size()));

        return votingReceipts;
    }

    EncryptedVotesList loadEncryptedVotes() throws IOException {
        LOGGER.info(String.format("Loading the encrypted votes from %s.",
                encryptedVotesFileName));

        EncryptedVotesList encryptedVotes = new EncryptedVotesArrayList();
        addFileContentToCollection(encryptedVotesFileName, encryptedVotes);
        LOGGER.info(String.format("All %d encrypted votes loaded.",
                encryptedVotes.size()));

        return encryptedVotes;
    }

    private void checkEncryptedVoteAgainstHashes(EncryptedVote encryptedVote,
        VotingReceiptsMap votingReceipts) {
        String votingReceipt = encryptedVote.getVotingReceipt();

        if (votingReceipts.containsVotingReceipt(votingReceipt,
                    encryptedVote.getContestId(),
                    encryptedVote.getElectionId(),
                    encryptedVote.getElectionEventId())) {
            VotingReceiptCounter votingReceiptCounter = votingReceipts.get(votingReceipt,
                    encryptedVote.getContestId(),
                    encryptedVote.getElectionId(),
                    encryptedVote.getElectionEventId());
            votingReceiptCounter.addEncryptedVote(encryptedVote);
        } else {
            results.add(new EncryptedVoteWithoutVotingReceiptIncident(
                    encryptedVote));
        }
    }

    private void checkVotingReceiptCounter(
        VotingReceiptCounter votingReceiptCounter) {
        if (votingReceiptCounter.getNoOfMatches() == 0) {
            results.add(new VotingReceiptWithoutEncryptedVoteIncident(
                    votingReceiptCounter.getVotingReceipt()));
        } else if (votingReceiptCounter.getNoOfMatches() > 1) {
            results.add(new VotingReceiptCollisionIncident(
                    votingReceiptCounter.getVotingReceipt(),
                    votingReceiptCounter.getEncryptedVotes()));
        }
    }
}
