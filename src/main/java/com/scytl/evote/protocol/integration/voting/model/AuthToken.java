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
package com.scytl.evote.protocol.integration.voting.model;

import com.scytl.evote.protocol.exceptions.FatalProtocolException;
import com.scytl.evote.protocol.integration.eraser.Erasable;
import com.scytl.evote.protocol.integration.voting.impl.saml.InternalAssertionType;

import com.scytl.jbasis.erase.Eraser;

import org.apache.commons.lang.ArrayUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import java.util.Arrays;
import java.util.List;


/**
 * Authentication Token that is issued by the Authentication Service and that
 * allows the other components that the users has the right to vote for a given
 * contest.
 */
public class AuthToken implements Serializable, KeepMembers, Erasable {
    private static final long serialVersionUID = -216635408449851701L;
    public static final int DEFAULT_EXPIRATION_TIME = 30;
    private static final String CANNOT_DESERIALIZE_BYTE_BEAN = "Cannot deserialize bean";
    protected static final String UTF_8 = "UTF-8";
    private String _id;
    private String _electionEventId;
    @Deprecated
    private String _electionId;
    private List<AllowedContest> _contests;
    private long _ts;
    private int _expirationTimeInMinutes;
    private VoterIdentifier _voterIdentifier;
    private String _intTokenEntityProvider;
    private InternalToken _intToken;
    private String _voterFirstName;
    private String _voterLastName;
    private String _voterCommId;
    private String _voterArea;
    private String _controlledReturncodesType;
    private String _uncontrolledReturncodesType;
    private byte[] _authServiceSig;
    private long _asId;
    private List<ElectionVotingMode> _votingModes;
    private PollingPlace _pollingPlace;
    private int _afterClosingTimeInSeconds = 0;
    private int _advanceOfExpirationTimeInMinutes = 0;
    private String _ip;

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
     *            true if the voter consents to share its info and false
     *            otherwise.
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
     * @param ip
     *            The voter ip.
     * @param afterClosingTimeInSeconds
     *            The grace period
     * @param advanceOfExpirationTimeInMinutes
     *            The time to warn expiring session
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
        final PollingPlace pollingPlace, final String ip,
        final int afterClosingTimeInSeconds,
        final int advanceOfExpirationTimeInMinutes) {
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
        _afterClosingTimeInSeconds = afterClosingTimeInSeconds;
        _advanceOfExpirationTimeInMinutes = advanceOfExpirationTimeInMinutes;

        _pollingPlace = pollingPlace;

        if (pollingPlace == null) {
            _pollingPlace = new PollingPlace();
        }

        _ip = ip;

        if (ip == null) {
            _ip = "unknown";
        }
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
        return cloneByteArray(_authServiceSig);
    }

    /**
     * @param authServiceSig
     *            The authServiceSig to set.
     */
    public void setAuthServiceSig(final byte[] authServiceSig) {
        _authServiceSig = cloneByteArray(authServiceSig);
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
                voteData = ArrayUtils.addAll(voteData,
                        _voterFirstName.getBytes(UTF_8));
            }

            // voter last name
            if (_voterLastName != null) {
                voteData = ArrayUtils.addAll(voteData,
                        _voterLastName.getBytes(UTF_8));
            }

            // voter area
            if (_voterArea != null) {
                voteData = ArrayUtils.addAll(voteData,
                        _voterArea.getBytes(UTF_8));
            }

            // voter communication id
            if (_voterCommId != null) {
                voteData = ArrayUtils.addAll(voteData,
                        _voterCommId.getBytes(UTF_8));
            }

            // expiration time in minutes
            voteData = ArrayUtils.addAll(voteData,
                    String.valueOf(_expirationTimeInMinutes).getBytes(UTF_8));

            // grace time in seconds
            voteData = ArrayUtils.addAll(voteData,
                    String.valueOf(_afterClosingTimeInSeconds).getBytes(UTF_8));

            // time in advance to alert user in minutes
            voteData = ArrayUtils.addAll(voteData,
                    String.valueOf(_advanceOfExpirationTimeInMinutes)
                          .getBytes(UTF_8));

            // rc types
            voteData = ArrayUtils.addAll(voteData,
                    _uncontrolledReturncodesType.getBytes(UTF_8));
            voteData = ArrayUtils.addAll(voteData,
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

            // ip
            if (_ip != null) {
                voteData = ArrayUtils.addAll(voteData, _ip.getBytes(UTF_8));
            }

            if (_intTokenEntityProvider != null) {
                voteData = ArrayUtils.addAll(voteData,
                        _intTokenEntityProvider.getBytes(UTF_8));
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
        return "AuthToken [_id=" + _id + ", _electionEventId=" +
        _electionEventId + ", _electionId=" + _electionId + ", _contests=" +
        _contests + ", _ts=" + _ts + ", _expirationTimeInMinutes=" +
        _expirationTimeInMinutes + ", _voterIdentifier=" + _voterIdentifier +
        ", _intTokenEntityProvider=" + _intTokenEntityProvider +
        ", _intToken=" + _intToken + ", _voterFirstName=" + _voterFirstName +
        ", _voterLastName=" + _voterLastName + ", _voterCommId=" +
        _voterCommId + ", _voterArea=" + _voterArea +
        ", _controlledReturncodesType=" + _controlledReturncodesType +
        ", _uncontrolledReturncodesType=" + _uncontrolledReturncodesType +
        ", _authServiceSig=" + Arrays.toString(_authServiceSig) + ", _asId=" +
        _asId + ", _votingModes=" + _votingModes + ", _pollingPlace=" +
        _pollingPlace + ", _afterClosingTimeInSeconds=" +
        _afterClosingTimeInSeconds + ", _advanceOfExpirationTimeInMinutes=" +
        _advanceOfExpirationTimeInMinutes + ", _ip=" + _ip + "]";
    }

    /**
     * Converts a byte[] representing an AuthToken into an object
     *
     * @param data
     * @return
     */
    public static AuthToken deserialize(final byte[] data) {
        Object o = null;

        try {
            o = new ObjectInputStream(new ByteArrayInputStream(data)).readObject();
        } catch (IOException ioe) {
            throw new FatalProtocolException(CANNOT_DESERIALIZE_BYTE_BEAN, ioe);
        } catch (ClassNotFoundException ioe) {
            throw new FatalProtocolException(CANNOT_DESERIALIZE_BYTE_BEAN, ioe);
        }

        return (AuthToken) o;
    }

    /**
     * Serializes an AuthToken object
     *
     * @return
     */
    public byte[] encode() {
        byte[] token = null;

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            oos.flush();
            oos.close();
            token = baos.toByteArray();
        } catch (IOException e1) {
            throw new FatalProtocolException(e1);
        }

        return token;
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

    /**
     * Returns the optional after time that will be applied to voters that have
     * been authentication before the closing time and vote after it (the grace
     * period)
     *
     * @return Returns the afterClosingTimeInSeconds.
     */
    public int getAfterClosingTimeInSeconds() {
        return _afterClosingTimeInSeconds;
    }

    /**
     * @param afterClosingTimeInSeconds
     *            The afterClosingTimeInSeconds to set.
     */
    public void setAfterClosingTimeInSeconds(
        final int afterClosingTimeInSeconds) {
        _afterClosingTimeInSeconds = afterClosingTimeInSeconds;
    }

    /**
     * @return Returns the ip.
     */
    public String getIp() {
        return _ip;
    }

    /**
     * @see com.scytl.evote.protocol.integration.eraser.Erasable#erase()
     */
    @Override
    public void erase() {
        Eraser eraser = Eraser.get(this.getClass());
        eraser.erase(_voterIdentifier);
        eraser.erase(_intTokenEntityProvider);
        eraser.erase(_intToken);
    }

    /**
     * @param electionId
     *            The electionId to set.
     */
    public void setElectionId(final String electionId) {
        _electionId = electionId;
    }

    /**
     * @param contests
     *            The contests to set.
     */
    public void setContests(final List<AllowedContest> contests) {
        _contests = contests;
    }

    /**
     * @param ts
     *            The ts to set.
     */
    public void setTs(final long ts) {
        _ts = ts;
    }

    /**
     * @param expirationTimeInMinutes
     *            The expirationTimeInMinutes to set.
     */
    public void setExpirationTimeInMinutes(final int expirationTimeInMinutes) {
        _expirationTimeInMinutes = expirationTimeInMinutes;
    }

    /**
     * @param voterIdentifier
     *            The voterIdentifier to set.
     */
    public void setVoterIdentifier(final VoterIdentifier voterIdentifier) {
        _voterIdentifier = voterIdentifier;
    }

    /**
     * @param intTokenEntityProvider
     *            The intTokenEntityProvider to set.
     */
    public void setIntTokenEntityProvider(final String intTokenEntityProvider) {
        _intTokenEntityProvider = intTokenEntityProvider;
    }

    /**
     * @param intToken
     *            The intToken to set.
     */
    public void setIntToken(final InternalToken intToken) {
        _intToken = intToken;
    }

    /**
     * @param voterFirstName
     *            The voterFirstName to set.
     */
    public void setVoterFirstName(final String voterFirstName) {
        _voterFirstName = voterFirstName;
    }

    /**
     * @param voterLastName
     *            The voterLastName to set.
     */
    public void setVoterLastName(final String voterLastName) {
        _voterLastName = voterLastName;
    }

    /**
     * @param voterCommId
     *            The voterCommId to set.
     */
    public void setVoterCommId(final String voterCommId) {
        _voterCommId = voterCommId;
    }

    /**
     * @param voterArea
     *            The voterArea to set.
     */
    public void setVoterArea(final String voterArea) {
        _voterArea = voterArea;
    }

    /**
     * @param votingModes
     *            The votingModes to set.
     */
    public void setVotingModes(final List<ElectionVotingMode> votingModes) {
        _votingModes = votingModes;
    }

    /**
     * @param pollingPlace
     *            The pollingPlace to set.
     */
    public void setPollingPlace(final PollingPlace pollingPlace) {
        _pollingPlace = pollingPlace;
    }

    /**
     * @param ip
     *            The ip to set.
     */
    public void setIp(final String ip) {
        _ip = ip;
    }

    /**
     * @param id
     *            The id to set.
     */
    public void setId(final String id) {
        _id = id;
    }

    /**
     * @return Returns the Time in advance to alert the voter when the
     *         expiration time or grace period is to come
     */
    public int getAdvanceOfExpirationTimeInMinutes() {
        return _advanceOfExpirationTimeInMinutes;
    }

    /**
     * @param advanceOfExpirationTimeInMinutes
     *            Time in advance to alert the voter when the expiration time or
     *            grace period is to come
     */
    public void setAdvanceOfExpirationTimeInMinutes(
        final int advanceOfExpirationTimeInMinutes) {
        _advanceOfExpirationTimeInMinutes = advanceOfExpirationTimeInMinutes;
    }

    /**
     * Clones a byte array testing if the toBeClone parameter are different of
     * null
     *
     * @param toBeClone
     * @return cloned toBeClone array
     */
    private byte[] cloneByteArray(final byte[] toBeClone) {
        byte[] result = null;

        if (toBeClone != null) {
            result = toBeClone.clone();
        }

        return result;
    }
}
