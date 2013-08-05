package com.computas.zkpev.cleansing;

import com.computas.zkpev.Incident;


/**
 * Incident relating to an extra encrypted vote in the result of the
 * cleansing server compared to the VCS encrypted votes list.
 *
 * @version $Id: InjectedCleansedVoteLineIncident.java 10836 2011-09-26 11:06:08Z fvl $
 */
public class InjectedCleansedVoteLineIncident extends Incident {
    private final CleansedVote cleansedVote;

    InjectedCleansedVoteLineIncident(CleansedVote cleansedVote) {
        this.cleansedVote = cleansedVote;
    }

    @Override
    public boolean equals(Object other) {
        return sameSubclass(other) &&
        privateEqual((InjectedCleansedVoteLineIncident) other);
    }

    private boolean sameSubclass(Object other) {
        return (other != null) && other.getClass().equals(this.getClass());
    }

    private boolean privateEqual(InjectedCleansedVoteLineIncident other) {
        return cleansedVote.equals(other.getCleansedVote());
    }

    private CleansedVote getCleansedVote() {
        return cleansedVote;
    }

    @Override
    public int hashCode() {
        return cleansedVote.hashCode();
    }

    @Override
    protected String[] getValuesForCsvLine() {
        return new String[] {
            cleansedVote.getEncryptedVoteOptIdsAsString(),
            cleansedVote.getContestId(), cleansedVote.getElectionId(),
            cleansedVote.getElectionEventId()
        };
    }
}
