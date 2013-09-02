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
package com.computas.zkpev2013.cleansing;

import com.computas.zkpev2013.Result;
import com.computas.zkpev2013.ResultsArrayList;
import com.computas.zkpev2013.ResultsList;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * Unit tests on CleansedVotesArrayList.
 */
public class CleansedVotesArrayListUnitTest {
    private static final String BROKEN_LINE = "*";
    private static final String GIVEN_SAMPLE_LINE = "IWvRkimscLc7ZnzHRhq5HkKtL0XXlMwZEFu2w7iIdSBCWDAKuxgUNPkA66dqZ7OyuvqePFoXVHLgemivhxRHAtkKALAtdc3ihfDN99u3vrfiPinPLHcidemy056uxPO+9mb96Cs4RVhFDY3DnW6BuZQUuITmrBOEhaaxja7qbijKD7SNX2bwqRv0j4GeasLaGosqDfFqEYd9aLr7x7M6xkk4AhyoPHG7qAI/A1SiwDxF2ocycQgJk7kl4yeJDgBKPFw0F3xZ1f8yVJEr6d8Su8OwdXN/NiFlX9bjKifFK2bgMqJvlsAPXLjK5ms/2VeSIE4atuMnZquzJMpjEICHcQ==#bJF2NzTcKGDnDIiq4GrZ+wGlZ3qOFk+Qr+dQNNB9lvkFOFKlDgv+3aAhno5UrQNYnYX8CSGKknuOVzCRsEffwXUQdKamWBs6M0psJQ0rtMwzCyrgMNMe+DQm9mK2cgi+j24qmZxCM8SD7bocdlx9JgTYra5NHMYzC7pQwqYUkCQBkoJBFPVaCSOoEmOS03h8qQG1A37d6rSwPgUiScfO0x1bxyrSNbFOgi/l1paJHV5DD5DVnEyqSnZuBqxrf7x2ZQDWZ50fk9QJkIXjJl3rKJ+PVO7RCBJh5UngZw9O6T8lKsfQ8e9SLYCa17vWESPpq4zHqa5SM4dJ0L127NzRWg==#,000007,01,730071";
    private CleansedVotesList list;
    private ResultsList results;

    /**
     * Creates a CleansedVotesArrayList for the tests.
     */
    @BeforeMethod
    public void createCleansedVotesArrayList() {
        list = new CleansedVotesArrayList();
    }

    /**
     * Creates a results list for the tests.
     */
    @BeforeMethod(alwaysRun = true)
    public void createResultsList() {
        results = new ResultsArrayList();
    }

    /**
     * Verifies that the list is empty by default.
     */
    @Test
    public void mustBeEmptyByDefault() {
        assertTrue(list.isEmpty());
    }

    /**
     * Adding must not create an incident if the cleansed vote can be parsed.
     */
    @Test
    public void mustNotCreateAnIncidentWhenTryingToAddANewCleansedVote() {
        list.addCleansedVoteOrCreateIncident(GIVEN_SAMPLE_LINE, results);

        assertTrue(results.isEmpty());
    }

    /**
     * Adding a broken line should create an incident.
     */
    @Test
    public void mustCreateAnIncidentWhenTryingToAddABrokenLine() {
        list.addCleansedVoteOrCreateIncident(BROKEN_LINE, results);

        assertTrue(results.contains(
                new CleansedVoteBrokenLineIncident(BROKEN_LINE)));
    }
}
