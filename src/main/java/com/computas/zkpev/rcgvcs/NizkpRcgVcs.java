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
package com.computas.zkpev.rcgvcs;

import com.computas.zkpev.ZeroKnowledgeProof;

import java.io.IOException;


/**
 * Class performing the non-interactive zero-knowledge proof between
 * the Return Code Generator (RCG) and the Vote Collector Server (VCS).
 *
 */
public class NizkpRcgVcs extends ZeroKnowledgeProof {
    private static final int MIN_NO_OF_PARAMETERS_ALLOWED = 2;
    private String rcgFileName;
    private String vcsFileName;

    NizkpRcgVcs(String[] arguments) {
        super(arguments);
    }

    @Override
    protected void parseArguments(String[] arguments) {
        checkNoOfParameters(arguments, MIN_NO_OF_PARAMETERS_ALLOWED);

        rcgFileName = arguments[0];
        vcsFileName = arguments[1];

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
                "Could not parse the arguments provided.\nCorrect usage:\n\tNizkpRcgVcs <RcgFileName> <VcsFileName> [<ResultsFileName>]");
        } catch (Exception exception) {
            LOGGER.fatal("A fatal error occurred.", exception);
        }
    }

    @Override
    protected void run() throws IOException {
        openResultsFileIfRequired();

        RcgVotingReceiptsMap rcgVotingReceipts = loadRcgVotingReceipts();
        VcsEncryptedVotesList vcsEncryptedVotes = loadVcsEncryptedVotes();

        for (VcsEncryptedVote vcsEncryptedVote : vcsEncryptedVotes) {
            checkVcsEncryptedVoteAgainstRcgHashes(vcsEncryptedVote,
                rcgVotingReceipts);
        }

        for (RcgVotingReceiptCounter rcgVotingReceiptCounter : rcgVotingReceipts.values()) {
            checkRcgVotingReceiptCounter(rcgVotingReceiptCounter);
        }

        closeResultsFileIfNeeded();
    }

    private void checkRcgVotingReceiptCounter(
        RcgVotingReceiptCounter rcgVotingReceiptCounter) {
        if (rcgVotingReceiptCounter.getNoOfMatches() == 0) {
            results.add(new RcgVotingReceiptWithoutVcsEncryptedVoteIncident(
                    rcgVotingReceiptCounter.getRcgVotingReceipt()));
        } else if (rcgVotingReceiptCounter.getNoOfMatches() > 1) {
            results.add(new RcgVotingReceiptCollisionIncident(
                    rcgVotingReceiptCounter.getRcgVotingReceipt(),
                    rcgVotingReceiptCounter.getVcsEncryptedVotes()));
        }
    }

    private void checkVcsEncryptedVoteAgainstRcgHashes(
        VcsEncryptedVote vcsEncryptedVote,
        RcgVotingReceiptsMap rcgVotingReceipts) {
        String votingReceipt = vcsEncryptedVote.getVotingReceipt();

        if (rcgVotingReceipts.containsVotingReceipt(votingReceipt,
                    vcsEncryptedVote.getContestId(),
                    vcsEncryptedVote.getElectionId(),
                    vcsEncryptedVote.getElectionEventId())) {
            RcgVotingReceiptCounter rcgVotingReceiptCounter = rcgVotingReceipts.get(votingReceipt,
                    vcsEncryptedVote.getContestId(),
                    vcsEncryptedVote.getElectionId(),
                    vcsEncryptedVote.getElectionEventId());
            rcgVotingReceiptCounter.addVcsEncryptedVote(vcsEncryptedVote);
        } else {
            results.add(new VcsEncryptedVoteNotRegisteredByRcgIncident(
                    vcsEncryptedVote));
        }
    }

    String getRcgFileName() {
        return rcgFileName;
    }

    String getVcsFileName() {
        return vcsFileName;
    }

    RcgVotingReceiptsMap loadRcgVotingReceipts() throws IOException {
        LOGGER.info(String.format("Loading the RCG voting receipts from %s.",
                rcgFileName));

        RcgVotingReceiptsMap rcgVotingReceipts = new RcgVotingReceiptsHashMap();
        addFileContentToCollection(rcgFileName, rcgVotingReceipts);
        LOGGER.info("All RCG voting receipts loaded.");

        return rcgVotingReceipts;
    }

    VcsEncryptedVotesList loadVcsEncryptedVotes() throws IOException {
        LOGGER.info(String.format(
                "Loading the encrypted votes from the VCS from %s.", vcsFileName));

        VcsEncryptedVotesList vcsEncryptedVotes = new VcsEncryptedVotesArrayList();
        addFileContentToCollection(vcsFileName, vcsEncryptedVotes);
        LOGGER.info("All encrypted votes from the VCS loaded.");

        return vcsEncryptedVotes;
    }
}
