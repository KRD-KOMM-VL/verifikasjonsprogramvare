/**
 * Source Code, High Level Architecture Documentation and Common Criteria
 * Documentation Copyright (C) 2013 and ownership belongs to The Norwegian
 * Ministry of Local Government and Regional Development and Scytl Secure
 * Electronic Voting SA ("Licensor").
 *
 * The Norwegian Ministry of Local Government and Regional Development has the
 * right to use, modify (whether by itself or by the use of contractors) and
 * copy the software for the sole purposes of performing Norwegian Public Sector
 * Elections, including to install and run the code on the necessary number of
 * locations centrally and in any number of counties and municipalities, and to
 * allow access to the solution from anywhere in the world by persons who have
 * the right to participate in Norwegian national or local elections. This also
 * applies to elections to the Longyearbyen Community Council at Svalbard and
 * any possible future public elections in Norway arranged by the Election
 * Authorities.
 *
 * Patents, relevant to the software, are licensed by Scytl Secure Electronic
 * Voting SA to the Norwegian Ministry of Local Government and Regional
 * Development for the purposes set out above.
 *
 * Scytl Secure Electronic Voting SA (or whom it appoints) has the right, inside
 * and outside of Norway to use, copy, modify and enhance the materials, as well
 * as a right of licensing and transfer, internally and externally, either by
 * itself or with the assistance of a third party, as part of the further
 * development and customization of its own standard solutions or delivered
 * together with its own standard solutions.
 *
 * The Norwegian Ministry of Local Government and Regional Development and Scytl
 * Secure Electronic Voting SA hereby grant to you (any third party) the right
 * to copy, modify, inspect, compile, debug and run the software for the sole
 * purpose of testing, reviewing or evaluating the code or the system solely for
 * non-commercial purposes. Any other use of the source code (or parts of it)
 * for any other purpose (including but not limited to any commercial purposes)
 * by any third party is subject to Scytl Secure Electronic Voting SA's prior
 * written approval.
 */
package com.scytl.evote.protocol.integration.voting.parser.json;

import com.scytl.evote.protocol.integration.voting.model.VoteBean;

import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

import java.math.BigInteger;


/**
 *
 */
public class VoteBeanToJson extends BaseToJson {
    private static final String ENC_GAMMA = "encGamma";
    private static final String ENC_VOTE_OPT_VALUES = "encVoteOptValues";
    private static final String TS = "ts";
    private static final String VOTER_ID = "voterID";
    private static final String ELECTION_TYPE = "electionType";
    private static final String CERTIFICATE = "certificate";
    private static final String ELECTION_EVENT_ID = "electionEventId";
    private static final String ELECTION_ID = "electionId";
    private static final String CONTEST_ID = "contestId";
    private static final String AUTH_TOKEN_ID = "authTokenId";
    private static final String ENC_VOTE_SIG = "encVoteSig";

    /**
     * Converts a VoteBean object to a JSON representation.
     *
     * @param vote
     *            The VoteBean object to convert.
     * @return String Its JSON representation.
     */
    public String voteToJson(final VoteBean vote) {
        ObjectNode root = _mapper.createObjectNode();

        root.put(ENC_GAMMA, new BigInteger(vote.getEncGamma()).toString());

        ArrayNode optValues = _mapper.createArrayNode();

        for (byte[] opts : vote.getEncodedEncVoteOptValues()) {
            optValues.add(new BigInteger(opts).toString());
        }

        root.put(ENC_VOTE_OPT_VALUES, optValues);
        root.put(VOTER_ID, vote.getVoterId());
        root.put(ELECTION_EVENT_ID, vote.getElectionEventId());
        root.put(ELECTION_ID, vote.getElectionId());
        root.put(CONTEST_ID, vote.getContestId());
        root.put(TS, vote.getTs());
        root.put(AUTH_TOKEN_ID, vote.getAuthTokenId());
        root.put(CERTIFICATE, vote.getCertificate());
        root.put(ELECTION_TYPE, vote.getElectionType());

        return root.toString();
    }
}
