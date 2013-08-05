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

import com.computas.zkpev.CsvLineParseable;


/**
 * Domain object holding all information about an RCG voting receipt.
 *
 */
public class RcgVotingReceipt extends CsvLineParseable {
    private String votingReceipt;
    private String contestId;
    private String electionId;
    private String electionEventId;

    RcgVotingReceipt(String line) {
        super(line);
    }

    protected void setAttributes(String[] attributes) {
        votingReceipt = getAttribute(attributes,
                RcgVotingReceiptCsvIndex.VOTING_RECEIPT);
        contestId = getAttribute(attributes, RcgVotingReceiptCsvIndex.CONTEST_ID);
        electionId = getAttribute(attributes,
                RcgVotingReceiptCsvIndex.ELECTION_ID);
        electionEventId = getAttribute(attributes,
                RcgVotingReceiptCsvIndex.ELECTION_EVENT_ID);
    }

    String getVotingReceipt() {
        return votingReceipt;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof RcgVotingReceipt &&
        privateEqual((RcgVotingReceipt) other);
    }

    private boolean privateEqual(RcgVotingReceipt other) {
        return votingReceipt.equals(other.getVotingReceipt()) &&
        contestId.equals(other.getContestId()) &&
        electionId.equals(other.getElectionId()) &&
        electionEventId.equals(other.getElectionEventId());
    }

    @Override
    public int hashCode() {
        return votingReceipt.hashCode() + contestId.hashCode() +
        electionId.hashCode() + electionEventId.hashCode();
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
    private enum RcgVotingReceiptCsvIndex {UUID,
        AUTH_TOKEN_ID,
        FULL_VOTING_RECEIPT,
        INTERNAL_AUTH_TOKEN_ID,
        RCG_ID,
        VOTING_RECEIPT,
        CONTEST_ID,
        ELECTION_ID,
        ELECTION_EVENT_ID,
        VOTER_AREA;
    }
}
