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

import com.computas.zkpev2013.EncryptedVoteLineGenerator;
import com.computas.zkpev2013.MemoryConsumptionAssertions;
import static com.computas.zkpev2013.MemoryConsumptionAssertions.assertDoesNotConsumeMoreMemoryThanAllowed;
import com.computas.zkpev2013.ResultsArrayList;
import com.computas.zkpev2013.ResultsList;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigInteger;


/**
 * Memory consumption test for the encrypted votes. The memory consumption
 * for these classes is substantial since they retain the ElGamal encryption
 * pairs.
 *
 */
public class EncryptedVotesInEncryptedVotesHashMapMemoryConsumptionTest {
    private static final int ALLOWED_MEMORY_CONSUMPTION = 30000;
    private static final BigInteger MODULUS = new BigInteger(
            "30953935016171929405181725048691475597165054658172086800611741385717085886966135022787053043294599719402052783457906554840050466598200845836350069464569830849652328849164597015834019636250184391187039519241529509539396137383783691026385997868581485251353115862362707856496254648707792083733847740839833577843038160760489606588067058523761002425294322414298639802035594840360412346667825330259855947401569537064580849961903216800408490338672611947294048038395112383055004376963978967260637379317563369771664058743286447728833162866458048271828606452285839103402380751993665831120012178542555391431510173573250387144139");
    private EncryptedVotesHashMap map;
    private EncryptedVoteLineGenerator encryptedVoteLineGenerator;
    private ResultsList results;

    /**
     * Creates a RcgVotingReceiptsHashMap for the tests.
     */
    @BeforeMethod(alwaysRun = true)
    public void createVcsEncryptedVotesHashMap() {
        map = new EncryptedVotesHashMap(MODULUS);
    }

    /**
     * Creates a results list for the tests.
     */
    @BeforeClass(alwaysRun = true)
    public void createResultsList() {
        results = new ResultsArrayList();
    }

    /**
     * Tries to run the garbage collection before every test.
     */
    @BeforeMethod(alwaysRun = true)
    public void tryToMakeGarbageCollectionRun() {
        MemoryConsumptionAssertions.tryToMakeGarbageCollectionRun();
    }

    /**
     * Creates a new encrypted vote line generator. Since such a generator
     * does not have state, it can be generated once for the whole class.
     */
    @BeforeClass(alwaysRun = true)
    public void createEncryptedVoteLineGenerator() {
        encryptedVoteLineGenerator = new EncryptedVoteLineGenerator();
    }

    /**
     * Checks a number of times whether the creation of a VCS encrypted vote
     * consumes more memory than allowed.
     */
    @Test
    public void mustNotConsumeMoreMemoryThanAllowed() {
        assertDoesNotConsumeMoreMemoryThanAllowed(ALLOWED_MEMORY_CONSUMPTION,
            new Runnable() {
                @Override
                public void run() {
                    String encryptedVoteLine = encryptedVoteLineGenerator.generateEncryptedVoteLine();

                    map.addEncryptedVoteOrAddIncident(encryptedVoteLine, results);
                    encryptedVoteLine = null;
                }
            });
    }
}
