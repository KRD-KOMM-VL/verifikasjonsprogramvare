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

import com.computas.zkpev.MemoryConsumptionAssertions;
import static com.computas.zkpev.MemoryConsumptionAssertions.assertDoesNotConsumeMoreMemoryThanAllowed;
import com.computas.zkpev.ResultsArrayList;
import com.computas.zkpev.ResultsList;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * Class testing the memory usage of the creation of the VCS encrypted votes.
 *
 */
public class VcsEncryptedVotesMemoryConsumptionTest {
    private static final int ALLOWED_MEMORY_CONSUMPTION = 10000;
    private VcsEncryptedVoteLineGenerator vcsEncryptedVoteLineGenerator;
    private VcsEncryptedVotesList list;
    private ResultsList results;

    /**
     * Creates a VcsEncryptedVotesList for the tests.
     */
    @BeforeClass(alwaysRun = true)
    public void createVcsEncryptedVotesArrayList() {
        list = new VcsEncryptedVotesArrayList();
    }

    /**
     * Creates a results list for the tests.
     */
    @BeforeClass(alwaysRun = true)
    public void createResultsList() {
        results = new ResultsArrayList();
    }

    /**
     * Creates a new VCS encrypted vote line generator. Since such a generator
     * does not have state, it can be generated once for the whole class.
     */
    @BeforeClass(alwaysRun = true)
    public void createVcsEncryptedVoteLineGenerator() {
        vcsEncryptedVoteLineGenerator = new VcsEncryptedVoteLineGenerator();
    }

    /**
     * Tries to run the garbage collection before every test.
     */
    @BeforeMethod(alwaysRun = true)
    public void tryToMakeGarbageCollectionRun() {
        MemoryConsumptionAssertions.tryToMakeGarbageCollectionRun();
    }

    /**
     * Checks a number of times whether the creation of a VCS encrypted vote
     * consumes more memory than allowed.
     *
     * Disabled because of incompatibilities with the new source code from Scytl.
     */
    @Test(enabled = false)
    public void mustNotConsumeMoreMemoryThanAllowed() {
        assertDoesNotConsumeMoreMemoryThanAllowed(ALLOWED_MEMORY_CONSUMPTION,
            new Runnable() {
                @Override
                public void run() {
                    String vcsEncryptedVoteLine = vcsEncryptedVoteLineGenerator.generateVcsEncryptedVoteLine();
                    list.addEncryptedVoteOrCreateIncident(vcsEncryptedVoteLine,
                        results);
                    vcsEncryptedVoteLine = null;
                }
            });
    }
}
