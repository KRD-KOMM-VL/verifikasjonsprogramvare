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

import com.scytl.evote.protocol.exceptions.FatalProtocolException;
import com.scytl.evote.protocol.integration.voting.model.AllowedContest;
import com.scytl.evote.protocol.integration.voting.model.AuthToken;
import com.scytl.evote.protocol.integration.voting.model.ElectionVotingMode;
import com.scytl.evote.protocol.integration.voting.model.InternalToken;
import com.scytl.evote.protocol.integration.voting.model.OpeningHours;
import com.scytl.evote.protocol.integration.voting.model.PollingPlace;
import com.scytl.evote.protocol.integration.voting.model.ReturnCodesType;
import com.scytl.evote.protocol.integration.voting.model.VoterIdentifier;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

import org.opensaml.ws.message.MessageException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.ArrayList;
import java.util.List;


/**
 * JSON representation of the AuthToken data to be consumed by JS client.
 */
public class AuthTokenJsonParserImpl extends BaseToJson
    implements AuthTokenJsonParser {
    private static final String VOTING_MODE = "votingMode";
    private static final String START_TIME = "startTime";
    private static final String END_TIME = "endTime";
    private static final String DAY = "day";
    private static final String VOTED = "voted";
    private static final String AFTER_CLOSING_TIME_IN_SECONDS = "afterClosingTimeInSeconds";
    private static final String CONTEST_ID = "contestId";
    private static final String AS_ID = "asId";
    private static final String UNCONTROLLED_RETURNCODES_TYPE = "uncontrolledReturncodesType";
    private static final String CONTROLLED_RETURNCODES_TYPE = "controlledReturncodesType";
    private static final String VOTER_AREA = "voterArea";
    private static final String VOTER_COMM_ID = "voterCommId";
    private static final String VOTER_LAST_NAME = "voterLastName";
    private static final String VOTER_FIRST_NAME = "voterFirstName";
    private static final String INT_TOKEN_ENTITY_PROVIDER = "intTokenEntityProvider";
    private static final String CONTESTS = "contests";
    private static final String ELECTION_ID = "electionId";
    private static final String ELECTION_EVENT_ID = "electionEventId";
    private static final String INTERNAL_TOKEN = "internalToken";
    private static final String POSTAL_CODE = "postalCode";
    private static final String POLLING_PLACE = "pollingPlace";
    private static final String POST_TOWN = "postTown";
    private static final String VOTER_IDENTIFIER = "voterIdentifier";
    private static final String SPARE_ID = "spareId";
    private static final String VOTER_ID = "voterId";
    private static final String OPENING_HOURS = "openingHours";
    private static final String ADDRESS_LINE3 = "addressLine3";
    private static final String ADDRESS_LINE2 = "addressLine2";
    private static final String ADDRESS_LINE1 = "addressLine1";
    private static final String VOTING_MODES = "votingModes";
    private static final String IP = "ip";
    private static final String EXPIRATION_IN_MINUTES = "expirationInMinutes";
    private static final String AUTH_SERVICE_SIG = "authServiceSig";
    private static final String TS = "ts";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String ADVANCE_OF_EXPIRATION_TIME_IN_MINUTES = "advanceOfExpirationTimeInMinutes";

    /**
     * @throws MessageException
     * @see com.scytl.evote.protocol.integration.voting.parser.json.AuthTokenJsonParser#toJson(com.scytl.evote.protocol.integration.voting.model.AuthToken)
     */
    @Override
    public String toJson(final AuthToken token) {
        ObjectNode json = _mapper.createObjectNode();
        json.put(ID, token.getId());
        json.put(ELECTION_EVENT_ID, token.getElectionEventId());
        json.put(ELECTION_ID, token.getElectionId());
        json.put(CONTESTS, parseContests(token));
        json.put(TS, token.getTs());
        json.put(EXPIRATION_IN_MINUTES, token.getExpirationTimeInMinutes());
        json.put(VOTER_IDENTIFIER, parseVoterIdentifier(token));

        json.put(INT_TOKEN_ENTITY_PROVIDER, token.getIntTokenEntityProvider());
        json.put(INTERNAL_TOKEN, serializeInternalToken(token));
        json.put(VOTER_FIRST_NAME, token.getVoterFirstName());
        json.put(VOTER_LAST_NAME, token.getVoterLastName());
        json.put(VOTER_COMM_ID, token.getVoterCommId());
        json.put(VOTER_AREA, token.getVoterArea());
        json.put(CONTROLLED_RETURNCODES_TYPE,
            token.getControlledReturncodesType().name());
        json.put(UNCONTROLLED_RETURNCODES_TYPE,
            token.getUncontrolledReturncodesType().name());
        json.put(AUTH_SERVICE_SIG, token.getAuthServiceSig());
        json.put(AS_ID, token.getAsId());
        json.put(VOTING_MODES, parseVotingModes(token));
        json.put(POLLING_PLACE, parsePollingPlace(token));
        json.put(AFTER_CLOSING_TIME_IN_SECONDS,
            token.getAfterClosingTimeInSeconds());
        json.put(IP, token.getIp());
        json.put(ADVANCE_OF_EXPIRATION_TIME_IN_MINUTES,
            token.getAdvanceOfExpirationTimeInMinutes());

        return json.toString();
    }

    /**
     * @throws MessageException
     * @see com.scytl.evote.protocol.integration.voting.parser.json.AuthTokenJsonParser#parse(java.lang.String)
     */
    @Override
    public AuthToken parse(final String jsonAuthToken)
        throws FatalProtocolException {
        JsonNode rootNode = readValuesAsTree(jsonAuthToken);
        long asId = rootNode.get(AS_ID).getLongValue();
        String id = rootNode.get(ID).getTextValue();
        String electionEventId = rootNode.get(ELECTION_EVENT_ID).getTextValue();
        String electionId = rootNode.get(ELECTION_ID).getTextValue();
        List<AllowedContest> contests = parseContests(rootNode);
        long ts = rootNode.get(TS).getLongValue();
        VoterIdentifier voterIdentifier = parseVoterIdentifier(rootNode);
        String intTokenEntityProvider = parseString(rootNode,
                INT_TOKEN_ENTITY_PROVIDER);
        InternalToken intToken = deserializeInternalToken(parseBinaryValue(
                    rootNode, INTERNAL_TOKEN));
        String voterFirstName = parseString(rootNode, VOTER_FIRST_NAME);
        String voterLastName = parseString(rootNode, VOTER_LAST_NAME);
        String voterCommId = parseString(rootNode, VOTER_COMM_ID);
        String voterArea = parseString(rootNode, VOTER_AREA);
        int expirationTimeInMinutes = rootNode.get(EXPIRATION_IN_MINUTES)
                                              .getIntValue();

        ReturnCodesType controlledReturncodesType = ReturnCodesType.valueOf(parseString(
                    rootNode, CONTROLLED_RETURNCODES_TYPE));
        ReturnCodesType uncontrolledReturncodesType = ReturnCodesType.valueOf(parseString(
                    rootNode, UNCONTROLLED_RETURNCODES_TYPE));
        List<ElectionVotingMode> votingModes = parseVotingModes(rootNode);
        PollingPlace pollingPlace = parsePollingPlace(rootNode);
        byte[] authServiceSig = parseBinaryValue(rootNode, AUTH_SERVICE_SIG);
        int afterClosingTimeInSeconds = rootNode.get(AFTER_CLOSING_TIME_IN_SECONDS)
                                                .getIntValue();
        String ip = parseString(rootNode, IP);
        int advanceOfExpirationTimeInMinutes = rootNode.get(ADVANCE_OF_EXPIRATION_TIME_IN_MINUTES)
                                                       .getIntValue();
        AuthToken authToken = new AuthToken(asId, id, electionEventId,
                electionId, contests, ts, voterIdentifier,
                intTokenEntityProvider, intToken, voterFirstName,
                voterLastName, voterArea, voterCommId, expirationTimeInMinutes,
                controlledReturncodesType, uncontrolledReturncodesType,
                votingModes, pollingPlace, ip, afterClosingTimeInSeconds,
                advanceOfExpirationTimeInMinutes);
        authToken.setAuthServiceSig(authServiceSig);

        return authToken;
    }

    /**
     * Reads the values contained at string jsonAuthToken returning the Json
     * Structure
     *
     * @param jsonAuthToken
     * @return JsonNode
     * @throws MessageException
     */
    private JsonNode readValuesAsTree(final String jsonAuthToken)
        throws FatalProtocolException {
        JsonNode rootNode = null;

        try {
            rootNode = _mapper.getJsonFactory().createJsonParser(jsonAuthToken)
                              .readValueAsTree();
        } catch (IOException e) {
            throw new FatalProtocolException(e);
        }

        return rootNode;
    }

    /**
     * Parses a binary value cointained on rootNode identified by parameter id
     *
     * @param rootNode
     * @param id
     * @return
     */
    private byte[] parseBinaryValue(final JsonNode rootNode, final String id) {
        byte[] result = null;

        try {
            result = rootNode.get(id).getBinaryValue();
        } catch (IOException e) {
            throw new FatalProtocolException(e);
        }

        return result;
    }

    /**
     * Serializes an InternalToken contained at AuthToken passed as parameter
     *
     * @param token
     * @return byte[]
     */
    private byte[] serializeInternalToken(final AuthToken token) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(token.getIntToken());
            oos.flush();
            oos.close();
        } catch (IOException e) {
            throw new FatalProtocolException(e);
        }

        return baos.toByteArray();
    }

    /**
     * Deserializes a byte array representation of internal token passed as a
     * parameter
     *
     * @param token
     * @return byte[]
     */
    private InternalToken deserializeInternalToken(final byte[] internalToken) {
        InternalToken result = null;

        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(internalToken);
            ObjectInputStream ois = new ObjectInputStream(bais);
            result = (InternalToken) ois.readObject();
            ois.close();
        } catch (IOException e) {
            throw new FatalProtocolException(e);
        } catch (ClassNotFoundException e) {
            throw new FatalProtocolException(e);
        }

        return result;
    }

    /**
     * Parses a PollingPlace form a JsonNode passed as a parameter
     *
     * @param rootNode
     * @return PollingPlace
     */
    private PollingPlace parsePollingPlace(final JsonNode rootNode) {
        JsonNode jn = rootNode.path(POLLING_PLACE);
        String addressLine1 = parseString(jn, ADDRESS_LINE1);
        String addressLine2 = parseString(jn, ADDRESS_LINE2);
        String addressLine3 = parseString(jn, ADDRESS_LINE3);
        String name = parseString(jn, NAME);
        List<OpeningHours> openingHours = parseOpeningHours(jn);
        String postTown = parseString(jn, POST_TOWN);
        String postalCode = parseString(jn, POSTAL_CODE);

        return new PollingPlace(addressLine1, addressLine2, addressLine3, name,
            openingHours, postTown, postalCode);
    }

    /**
     * For a JsonNode return a List of OpeningHours
     *
     * @param jn
     * @return List<OpeningHours>
     */
    private List<OpeningHours> parseOpeningHours(final JsonNode rootNode) {
        JsonNode jn = rootNode.path(OPENING_HOURS);
        List<OpeningHours> openingHours = new ArrayList<OpeningHours>(jn.size());

        for (JsonNode jsonVotingMode : jn) {
            openingHours.add(new OpeningHours(parseString(jsonVotingMode, DAY),
                    parseString(jsonVotingMode, START_TIME),
                    parseString(jsonVotingMode, END_TIME)));
        }

        return openingHours;
    }

    /**
     * For a JsonNode return a List of ElectionVotingMode
     *
     * @param rootNode
     * @return List<ElectionVotingMode>
     */
    private List<ElectionVotingMode> parseVotingModes(final JsonNode rootNode) {
        JsonNode jn = rootNode.path(VOTING_MODES);
        List<ElectionVotingMode> votingModes = new ArrayList<ElectionVotingMode>(jn.size());

        for (JsonNode jsonVotingMode : jn) {
            votingModes.add(new ElectionVotingMode(parseString(jsonVotingMode,
                        ELECTION_ID), parseString(jsonVotingMode, VOTING_MODE)));
        }

        return votingModes;
    }

    /**
     * For a JsonNode return a VoterIdentifier
     *
     * @param rootNode
     * @return VoterIdentifier
     */
    private VoterIdentifier parseVoterIdentifier(final JsonNode rootNode) {
        JsonNode jn = rootNode.path(VOTER_IDENTIFIER);

        return new VoterIdentifier(parseString(jn, VOTER_ID),
            parseString(jn, SPARE_ID));
    }

    /**
     * For a JsonNode return a List of AllowedContest
     *
     * @param rootNode
     * @return
     */
    private List<AllowedContest> parseContests(final JsonNode rootNode) {
        JsonNode jn = rootNode.path(CONTESTS);
        List<AllowedContest> contests = new ArrayList<AllowedContest>(jn.size());

        for (JsonNode jsonContest : jn) {
            contests.add(new AllowedContest(parseString(jsonContest, ELECTION_ID),
                    parseString(jsonContest, CONTEST_ID),
                    parseString(jsonContest, VOTED)));
        }

        return contests;
    }

    private String parseString(final JsonNode jsonNode, final String id) {
        return jsonNode.get(id).getTextValue();
    }

    /**
     * Parses a AllowedContest form a JsonNode passed as a parameter
     *
     * @param token
     * @return
     */
    private JsonNode parseContests(final AuthToken token) {
        ArrayNode contests = _mapper.createArrayNode();
        ObjectNode allowedContest = null;

        for (AllowedContest ac : token.getContests()) {
            allowedContest = _mapper.createObjectNode();
            allowedContest.put(ELECTION_ID, ac.getElectionId());
            allowedContest.put(CONTEST_ID, ac.getContestId());
            allowedContest.put(VOTED, ac.getVoted());
            contests.add(allowedContest);
        }

        return contests;
    }

    /**
     * @param token
     * @return
     */
    private ObjectNode parseVoterIdentifier(final AuthToken token) {
        ObjectNode voterIdentifier = _mapper.createObjectNode();
        voterIdentifier.put(VOTER_ID, token.getVoterIdentifier().getVoterId());
        voterIdentifier.put(SPARE_ID, token.getVoterIdentifier().getSpareId());

        return voterIdentifier;
    }

    /**
     * @param token
     * @return
     */
    private ObjectNode parsePollingPlace(final AuthToken token) {
        ObjectNode pollingPlace = _mapper.createObjectNode();
        pollingPlace.put(ADDRESS_LINE1,
            token.getPollingPlace().getAddressLine1());
        pollingPlace.put(ADDRESS_LINE2,
            token.getPollingPlace().getAddressLine2());
        pollingPlace.put(ADDRESS_LINE3,
            token.getPollingPlace().getAddressLine3());
        pollingPlace.put(NAME, token.getPollingPlace().getName());
        pollingPlace.put(OPENING_HOURS, parseOpeningHours(token));
        pollingPlace.put(POST_TOWN, token.getPollingPlace().getPostTown());
        pollingPlace.put(POSTAL_CODE, token.getPollingPlace().getPostalCode());

        return pollingPlace;
    }

    /**
     * Returns an ArrayNode if the authToken object contains opening hours.
     *
     * @param token
     *            AuthToken containing the data.
     * @return ArrayNode with the new data, if any.
     */
    private ArrayNode parseOpeningHours(final AuthToken token) {
        ArrayNode openingHours = _mapper.createArrayNode();
        ObjectNode hours = null;

        for (OpeningHours oh : token.getPollingPlace().getOpeningHours()) {
            hours = _mapper.createObjectNode();
            hours.put(DAY, oh.getDay());
            hours.put(END_TIME, oh.getEndTime());
            hours.put(START_TIME, oh.getStartTime());
            openingHours.add(hours);
        }

        return openingHours;
    }

    /**
     * Returns an ArrayNode if the authToken object contains voting modes.
     *
     * @param token
     *            AuthToken containing the data.
     * @return ArrayNode with the new data, if any.
     */
    private ArrayNode parseVotingModes(final AuthToken token) {
        ObjectNode node = null;
        ArrayNode votingModes = _mapper.createArrayNode();

        for (ElectionVotingMode vm : token.getVotingModes()) {
            node = _mapper.createObjectNode();
            node.put(ELECTION_ID, vm.getElectionId());
            node.put(VOTING_MODE, vm.getVotingMode());
            votingModes.add(node);
        }

        return votingModes;
    }
}
