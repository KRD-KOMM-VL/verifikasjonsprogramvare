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
package com.computas.zkpev;

import static org.testng.Assert.assertTrue;

/**
 * Helper class with assertions about memory consumption for the memory
 * consumption tests.
 *
 */
public final class MemoryConsumptionAssertions {
    private static final Runtime RUNTIME = Runtime.getRuntime();
    private static final int NO_OF_CALLS_TO_GC = 10;
    private static final int NO_OF_TEST_INVOCATIONS = 10;
    private static final double ALLOWED_FAILURE_REATE = 0.2;

    private MemoryConsumptionAssertions() {
        // This is an assertions class, and should never be instantiated.
    }

    /**
     * This method sends multiple request to the garbage collection, hoping
     * that it will run effectively at least once.
     */
    public static void tryToMakeGarbageCollectionRun() {
        for (int i = 0; i < NO_OF_CALLS_TO_GC; i++) {
            RUNTIME.gc();
        }
    }

    /**
     * Checks a number of times whether the memory intensive action
     * consumes more memory than allowed. If the number of failures is below
     * the allowed failure, the test is successful.
     *
     * The reason why a small number of failures must be allowed, is that the
     * GC isn't always a reliable.
     *
     * @param allowedMemoryConsumption The maximum allowed memory consumption.
     * @param runnable A runnable object that performs the memory intensive action.
     */
    public static void assertDoesNotConsumeMoreMemoryThanAllowed(
        long allowedMemoryConsumption, Runnable runnable) {
        int counter = 0;

        for (int i = 0; i < NO_OF_TEST_INVOCATIONS; i++) {
            if (!consumesLessMemoryThanAllowed(allowedMemoryConsumption,
                        runnable)) {
                counter++;
            }
        }

        assertTrue(counter <= (ALLOWED_FAILURE_REATE * NO_OF_TEST_INVOCATIONS),
            String.format("Consumed more memory than allowed %d times.", counter));
    }

    /**
     * Verifies that the memory intensive action doesn't consume more
     * memory than what's allowed.
     *
     * The method should be run a couple of times, e.g. 10, as it may produce
     * negative results the first few times. This happens because the garbage
     * collection may start with a delay, and therefore doesn't start to run for
     * the first time before it entered the area where a new VCS encrypted vote
     * line is created.
     */
    private static boolean consumesLessMemoryThanAllowed(
        long allowedMemoryConsumption, Runnable runnable) {
        tryToMakeGarbageCollectionRun();

        long memoryUsedBefore = calculateMemoryUsed();
        runnable.run();
        tryToMakeGarbageCollectionRun();

        long memoryUsedAfter = calculateMemoryUsed();

        long memoryConsumed = memoryUsedAfter - memoryUsedBefore;

        return memoryConsumed < allowedMemoryConsumption;
    }

    private static long calculateMemoryUsed() {
        return RUNTIME.totalMemory() - RUNTIME.freeMemory();
    }
}
