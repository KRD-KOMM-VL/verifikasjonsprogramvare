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

import com.computas.zkpev2013.CsvLineParseable;


/**
 * Domain object holding all information about an voting receipt.
 *
 */
public class VotingReceipt extends CsvLineParseable {
    private String votingReceiptValue;
    private String contestId;
    private String electionId;
    private String electionEventId;

    VotingReceipt(String line) {
        super(line);
    }

    @Override
    protected void setAttributes(String[] attributes) throws Exception {
        votingReceiptValue = getAttribute(attributes,
                VotingReceiptCsvIndex.VOTING_RECEIPT);
        contestId = getAttribute(attributes, VotingReceiptCsvIndex.CONTEST_ID);
        electionId = getAttribute(attributes, VotingReceiptCsvIndex.ELECTION_ID);
        electionEventId = getAttribute(attributes,
                VotingReceiptCsvIndex.ELECTION_EVENT_ID);
    }

    String getVotingReceipt() {
        return votingReceiptValue;
    }

    String getContestId() {
        return contestId;
    }

    String getElectionId() {
        return electionId;
    }

    String getElectionEventId() {
        return electionEventId;
    }

    /**
     * Indexes of the various fields in the CSV file.
     */
    private enum VotingReceiptCsvIndex {UUID,
        AUTH_TOKEN_ID,
        FULL_VOTING_RECEIPT,
        INTERNAL_AUTH_TOKEN_ID,
        RCG_ID,
        VOTING_RECEIPT,
        CONTEST_ID,
        ELECTION_ID,
        ELECTION_EVENT_ID;
    }
}
