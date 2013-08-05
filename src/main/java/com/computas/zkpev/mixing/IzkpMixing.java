/**
 * Zero-Knowledge Protocols for E-Vote (ZKPEV).
 *
 * Copyright © 2011, The Norwegian Ministry of Local Government and Regional
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
package com.computas.zkpev.mixing;

import com.computas.zkpev.ElGamalZkp;

import org.apache.log4j.Logger;

import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


/**
 * Class implementing the interactive zero-knowledge proof for the mixing process.
 *
 * @version $Id: IzkpMixing.java 10998 2011-10-18 14:04:09Z fvl $
 */
public class IzkpMixing extends ElGamalZkp {
    private static final int MIN_NO_OF_PARAMETERS_ALLOWED = 3;
    private static final String DATABASE_NAME_KEY = "databasename";
    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "password";
    private String databasePropertiesFile;
    private String databaseName;
    private String username;
    private String password;

    protected IzkpMixing(String[] arguments) {
        super(arguments);
    }

    /**
     * Main entry method.
     *
     * @param arguments The arguments to be passed to the constructor.
     */
    public static void main(String[] arguments) {
        try {
            IzkpMixing izkp = new IzkpMixing(arguments);
            izkp.run();
            LOGGER.info("Done.");
        } catch (IllegalArgumentException iae) {
            LOGGER.fatal(
                "Could not parse the arguments provided.\nCorrect usage:\n\tIzkpMixing <ElGamalPropertiesFileName> <ElGamalPublicKeyFileName> <DatabasePropertiesFileName> [<ResultsFileName>]");
        } catch (Exception exception) {
            LOGGER.fatal("A fatal error occurred.", exception);
        }
    }

    @Override
    protected void parseArguments(String[] arguments) {
        checkNoOfParameters(arguments, MIN_NO_OF_PARAMETERS_ALLOWED);

        setElGamalPropertiesFileName(arguments[0]);
        setElGamalPublicKeyFileName(arguments[1]);
        this.databasePropertiesFile = arguments[2];

        setOptionalResultsFileName(arguments, MIN_NO_OF_PARAMETERS_ALLOWED);
    }

    @Override
    protected void run() throws Exception {
        openResultsFileIfRequired();
        loadElGamalProperties();
        loadElGamalPublicKey();
        loadDatabaseProperties();
        loadDatabaseContent();

        verifyMixingProofs();
        closeResultsFileIfNeeded();
    }

    private void verifyMixingProofs() throws SQLException {
        List<Thread> workers = setUpWorkers();
        waitForWorkersToFinish(workers);
    }

    private List<Thread> setUpWorkers() throws SQLException {
        List<Thread> workers = new ArrayList<Thread>();

        openDatabaseAndAddWorkers(workers);

        LOGGER.info(String.format("%d worker threads set up.", workers.size()));

        return workers;
    }

    private void openDatabaseAndAddWorkers(List<Thread> workers) {
        try {
            tryToOpenDatabaseAndAddWorkers(workers);
        } catch (ClassNotFoundException e) {
            LOGGER.error("Class missing exception while trying to connect to the database.",
                e);
        } catch (SQLException e) {
            LOGGER.error("SQL exception while trying to read the mixing data from the database.",
                e);
        }
    }

    private void tryToOpenDatabaseAndAddWorkers(List<Thread> workers)
        throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");

        Connection db = null;
        Statement sql = null;
        ResultSet results = null;

        try {
            db = DriverManager.getConnection("jdbc:postgresql:" +
                    getDatabaseName(), getUsername(), getPassword());
            sql = db.createStatement();
            results = sql.executeQuery(
                    "SELECT mixingdata.uuid AS muuid, auditdata.uuid AS auuid, inputvotes, outputvotes, votegroupaffsin, votegroupaffsout, encreencproofs FROM mixingdata INNER JOIN auditdata ON mixingdata.processid = auditdata.processid AND mixingdata.nodeid = auditdata.nodeid WHERE mixingdata.isinput IS FALSE");
            addWorkersFromResultSet(workers, results);
        } finally {
            results.close();
            sql.close();
            db.close();
        }
    }

    private void addWorkersFromResultSet(List<Thread> workers, ResultSet results)
        throws SQLException {
        if (results != null) {
            while (results.next()) {
                workers.add(setUpWorkerForCurrentResult(results));
            }
        }
    }

    private Thread setUpWorkerForCurrentResult(ResultSet resultSet)
        throws SQLException {
        MixingVerificationWorker worker = new MixingVerificationWorker(results,
                getP(), getG(), getH());
        worker.setMixingData(resultSet.getString("muuid"),
            resultSet.getBytes("inputvotes"), resultSet.getBytes("outputvotes"));
        worker.setAuditData(resultSet.getString("auuid"),
            resultSet.getBytes("votegroupaffsin"),
            resultSet.getBytes("votegroupaffsout"),
            resultSet.getBytes("encreencproofs"));
        worker.start();

        return worker;
    }

    private void waitForWorkersToFinish(List<Thread> workers) {
        for (Thread worker : workers) {
            tryToWaitForWorker(worker);
        }
    }

    private void tryToWaitForWorker(Thread worker) {
        try {
            worker.join();
        } catch (InterruptedException e) {
            LOGGER.error("An exception occured while trying to wait for a worker thread to finish",
                e);
        }
    }

    private void loadDatabaseContent() {
        LOGGER.info(String.format("Loading the mixing data from %s.",
                databaseName));
        LOGGER.info("All mixing data from the Mixing Server loaded.");
    }

    String getDatabasePropertiesFileName() {
        return databasePropertiesFile;
    }

    void loadDatabaseProperties() throws IOException {
        Properties properties = loadProperties(databasePropertiesFile);
        databaseName = properties.getProperty(DATABASE_NAME_KEY);
        username = properties.getProperty(USERNAME_KEY);
        password = properties.getProperty(PASSWORD_KEY);
    }

    String getDatabaseName() {
        return databaseName;
    }

    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }

    static Logger getLogger() {
        return LOGGER;
    }
}
