/**
 * Source Code, High Level Architecture Documentation and Common Criteria
 * Documentation Copyright (C) 2010-2011 and ownership belongs to The Norwegian
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
package com.scytl.evote.protocol.integration.voting.model;

import com.scytl.evote.protocol.integration.voting.exception.FatalProtocolException;
import com.scytl.evote.protocol.integration.voting.impl.saml.InternalAssertionType;

import org.apache.commons.lang.ArrayUtils;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import java.util.Arrays;
import java.util.List;


/**
 * Authentication Token that is issued by the Authentication Service and that
 * allows the other components that the users has the right to vote for a given
 * contest.
 */
public class AuthToken implements Serializable, KeepMembers {
    private static final long serialVersionUID = -216635408449851701L;
    public static final int DEFAULT_EXPIRATION_TIME = 30;
    protected static final String UTF_8 = "UTF-8";
    private final String _id;
    private String _electionEventId;
    @Deprecated
    private final String _electionId;
    private final List<AllowedContest> _contests;
    private final long _ts;
    private final int _expirationTimeInMinutes;
    private final VoterIdentifier _voterIdentifier;
    private final String _intTokenEntityProvider;
    private final InternalToken _intToken;
    private final String _voterFirstName;
    private final String _voterLastName;
    private final String _voterCommId;
    private final String _voterArea;
    private String _controlledReturncodesType;
    private String _uncontrolledReturncodesType;
    private byte[] _authServiceSig;
    private long _asId;
    private final List<ElectionVotingMode> _votingModes;
    private final PollingPlace _pollingPlace;

    /**
     * Creates an auth token instance.
     *
     * @param id
     *            An unique id for the authentication token.
     * @param electionEventId
     *            The election event in which the token will be used.
     * @param electionId
     *            The election in which the token will be used (Not Used).
     * @param contests
     *            A list of allowed contests for the voter.
     * @param ts
     *            The issuing time stamp of the authentication token.
     * @param voterIdentifier
     *            The voter identifier for which the token is issued.
     * @param intTokenEntityProvider
     *            The type of identification token that has been used to
     *            identify the voter.
     * @param intToken
     *            The identification token that has been used to identify the
     *            voter.
     * @param voterFirstName
     *            The voter's first name provided by the electoral roll.
     * @param voterLastName
     *            The voter's last name provided by the electoral roll.
     * @param voterArea
     *            The voter's area provided by the electoral roll.
     * @param voterCommId
     *            true if the voter cell number could be retrieved, false
     *            elsewhere.
     * @param expirationTimeInMinutes
     *            The expiration time of the token.
     * @param controlled
     *            The return codes type of this token for controlled
     *            environment.
     * @param uncontrolled
     *            The return codes type of this token for the uncontrolled
     *            environment.
     * @param votingModes
     *            The voter's rights to use paper voter, electronic vote or
     *            both.
     * @param pollingPlace
     *            The polling place where the voter has to paper vote.
     */
    public AuthToken(final long asId, final String id,
        final String electionEventId, final String electionId,
        final List<AllowedContest> contests, final long ts,
        final VoterIdentifier voterIdentifier,
        final String intTokenEntityProvider, final InternalToken intToken,
        final String voterFirstName, final String voterLastName,
        final String voterArea, final String voterCommId,
        final int expirationTimeInMinutes,
        final ReturnCodesType controlledReturncodesType,
        final ReturnCodesType uncontrolledReturncodesType,
        final List<ElectionVotingMode> votingModes,
        final PollingPlace pollingPlace) {
        super();
        _asId = asId;
        _id = id;
        _contests = contests;
        _ts = ts;
        _voterIdentifier = voterIdentifier;
        _intTokenEntityProvider = intTokenEntityProvider;
        _intToken = intToken;
        _electionId = electionId;
        _electionEventId = electionEventId;
        _voterFirstName = voterFirstName;
        _voterLastName = voterLastName;
        _voterCommId = voterCommId;
        _voterArea = voterArea;
        _expirationTimeInMinutes = expirationTimeInMinutes;
        _controlledReturncodesType = controlledReturncodesType.name();
        _uncontrolledReturncodesType = uncontrolledReturncodesType.name();
        _votingModes = votingModes;
        _pollingPlace = (pollingPlace != null) ? pollingPlace : new PollingPlace();
    }

    /**
     * @return Returns the asId.
     */
    public long getAsId() {
        return _asId;
    }

    /**
     * @param asId
     *            The asId to set.
     */
    public void setAsId(final long asId) {
        _asId = asId;
    }

    /**
     * @return Returns the id.
     */
    public String getId() {
        return _id;
    }

    /**
     * @return Returns the intTokenEntityProvider.
     */
    public String getIntTokenEntityProvider() {
        return _intTokenEntityProvider;
    }

    /**
     * @return Returns the contests.
     */
    public List<AllowedContest> getContests() {
        return _contests;
    }

    /**
     * @return Returns the ts.
     */
    public long getTs() {
        return _ts;
    }

    /**
     * @return Returns the voterId.
     */
    public VoterIdentifier getVoterIdentifier() {
        return _voterIdentifier;
    }

    /**
     * @return Returns the intToken.
     */
    public InternalToken getIntToken() {
        return _intToken;
    }

    /**
     * @return Returns the authServiceSig.
     */
    public byte[] getAuthServiceSig() {
        return _authServiceSig;
    }

    /**
     * @param authServiceSig
     *            The authServiceSig to set.
     */
    public void setAuthServiceSig(final byte[] authServiceSig) {
        if (authServiceSig != null) {
            _authServiceSig = authServiceSig.clone();
        } else {
            _authServiceSig = null;
        }
    }

    /**
     * @return Returns the electionId.
     * @deprecated
     */
    @Deprecated
    public String getElectionId() {
        return _electionId;
    }

    /**
     * @return Returns the electionEventId.
     */
    public String getElectionEventId() {
        return _electionEventId;
    }

    /**
     * @param electionEventId
     *            The electionEventId to set.
     */
    public void setElectionEventId(final String electionEventId) {
        _electionEventId = electionEventId;
    }

    /**
     * Gets a byte[] representing the object
     *
     * @return
     */
    public byte[] toByteArray() {
        byte[] voteData = null;

        try {
            // asid
            voteData = ArrayUtils.addAll(voteData,
                    String.valueOf(_asId).getBytes(UTF_8));

            // id
            voteData = ArrayUtils.addAll(voteData, _id.getBytes(UTF_8));

            // election event id
            voteData = ArrayUtils.addAll(voteData,
                    _electionEventId.getBytes(UTF_8));

            // election id
            voteData = ArrayUtils.addAll(voteData, _electionId.getBytes(UTF_8));

            // contests
            if (_contests != null) {
                voteData = ArrayUtils.addAll(voteData,
                        _contests.toString().getBytes(UTF_8));
            }

            // ts
            voteData = ArrayUtils.addAll(voteData,
                    String.valueOf(_ts).getBytes(UTF_8));

            // voterId
            voteData = ArrayUtils.addAll(voteData,
                    _voterIdentifier.toByteArray());

            // intToken
            voteData = ArrayUtils.addAll(voteData, _intToken.toByteArray());

            // voter first name
            if (_voterFirstName != null) {
                ArrayUtils.addAll(voteData, _voterFirstName.getBytes(UTF_8));
            }

            // voter last name
            if (_voterLastName != null) {
                ArrayUtils.addAll(voteData, _voterLastName.getBytes(UTF_8));
            }

            // voter area
            if (_voterArea != null) {
                ArrayUtils.addAll(voteData, _voterArea.getBytes(UTF_8));
            }

            // voter communication id
            if (_voterCommId != null) {
                ArrayUtils.addAll(voteData, _voterCommId.getBytes(UTF_8));
            }

            // expiration time in minutes
            ArrayUtils.addAll(voteData,
                String.valueOf(_expirationTimeInMinutes).getBytes(UTF_8));

            // rc types
            ArrayUtils.addAll(voteData,
                _uncontrolledReturncodesType.getBytes(UTF_8));
            ArrayUtils.addAll(voteData,
                _controlledReturncodesType.getBytes(UTF_8));

            // voting modes
            if (_votingModes != null) {
                voteData = ArrayUtils.addAll(voteData,
                        _votingModes.toString().getBytes(UTF_8));
            }

            // polling place
            if (_pollingPlace != null) {
                voteData = ArrayUtils.addAll(voteData,
                        _pollingPlace.toByteArray());
            }
        } catch (UnsupportedEncodingException e1) {
            throw new FatalProtocolException(e1);
        }

        return voteData;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "AuthToken [_asId=" + _asId + ", _authServiceSig=" +
        Arrays.toString(_authServiceSig) + ", _contests=" + _contests +
        ", _controlledReturncodesType=" + _controlledReturncodesType +
        ", _electionEventId=" + _electionEventId + ", _electionId=" +
        _electionId + ", _expirationTimeInMinutes=" + _expirationTimeInMinutes +
        ", _id=" + _id + ", _intToken=" + _intToken +
        ", _intTokenEntityProvider=" + _intTokenEntityProvider + ", _ts=" +
        _ts + ", _uncontrolledReturncodesType=" + _uncontrolledReturncodesType +
        ", _voterArea=" + _voterArea + ", _voterCommId=" + _voterCommId +
        ", _voterFirstName=" + _voterFirstName + ", _voterIdentifier=" +
        _voterIdentifier + ", _voterLastName=" + _voterLastName +
        ", _votingModes=" + _votingModes + ", _pollingPlace=" + _pollingPlace +
        "]";
    }

    /**
     * @return Returns the voterFirstName.
     */
    public String getVoterFirstName() {
        return _voterFirstName;
    }

    /**
     * @return Returns the voterLastName.
     */
    public String getVoterLastName() {
        return _voterLastName;
    }

    /**
     * @return Returns the voterCommId.
     */
    public String getVoterCommId() {
        return _voterCommId;
    }

    /**
     * @return Returns the voterArea.
     */
    public String getVoterArea() {
        return _voterArea;
    }

    /**
     * @return Returns the expirationTimeInMinutes.
     */
    public int getExpirationTimeInMinutes() {
        return _expirationTimeInMinutes;
    }

    /**
     * @return Returns the controlledReturncodesType.
     */
    public ReturnCodesType getControlledReturncodesType() {
        return ReturnCodesType.valueOf(_controlledReturncodesType);
    }

    /**
     * @param controlledReturncodesType
     *            The controlledReturncodesType to set.
     */
    public void setControlledReturncodesType(
        final String controlledReturncodesType) {
        _controlledReturncodesType = controlledReturncodesType;
    }

    /**
     * @return Returns the uncontrolledReturncodesType.
     */
    public ReturnCodesType getUncontrolledReturncodesType() {
        return ReturnCodesType.valueOf(_uncontrolledReturncodesType);
    }

    /**
     * @param uncontrolledReturncodesType
     *            The uncontrolledReturncodesType to set.
     */
    public void setUncontrolledReturncodesType(
        final String uncontrolledReturncodesType) {
        _uncontrolledReturncodesType = uncontrolledReturncodesType;
    }

    /**
     * Returns true if the internal assertion belongs to a controlled
     * environment. Currently only smartcard assertions.
     *
     * @return
     */
    public boolean isControlledEnvironment() {
        return InternalAssertionType.SMARTCARD.name()
                                              .compareTo(_intTokenEntityProvider) == 0;
    }

    /**
     * @return Returns the votingModes.
     */
    public List<ElectionVotingMode> getVotingModes() {
        return _votingModes;
    }

    /**
     * @return Returns the pollingPlace.
     */
    public PollingPlace getPollingPlace() {
        return _pollingPlace;
    }
}
