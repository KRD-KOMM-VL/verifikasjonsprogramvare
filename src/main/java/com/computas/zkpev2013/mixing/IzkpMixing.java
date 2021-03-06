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
package com.computas.zkpev2013.mixing;

import com.computas.zkpev2013.ElGamalZkp;

import java.io.IOException;

import java.security.NoSuchAlgorithmException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Properties;


/**
 * Class implementing the interactive zero-knowledge proof for the mixing
 * process.
 */
public class IzkpMixing extends ElGamalZkp {
    private static final int QUERY_FETCH_SIZE = 50;
    private static final int MIN_NO_OF_PARAMETERS_ALLOWED = 3;
    private static final String DATABASE_NAME_KEY = "databasename";
    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "password";
    private String databasePropertiesFile;
    private String databaseName;
    private String username;
    private String password;
    private ResultSet resultSet;

    protected IzkpMixing(String[] arguments) {
        super(arguments);
    }

    /**
     * Main entry method.
     *
     * @param arguments
     *            The arguments to be passed to the constructor.
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
        setElGamalPublicKeysFileName(arguments[1]);
        this.databasePropertiesFile = arguments[2];

        setOptionalResultsFileName(arguments, MIN_NO_OF_PARAMETERS_ALLOWED);
    }

    @Override
    protected void run() throws Exception {
        openResultsFileIfRequired();
        loadElGamalProperties();
        loadElGamalPublicKeys();
        calculateElGamalAggregateKey();
        loadDatabaseProperties();

        openDatabaseAndRunWorkers();
        closeResultsFileIfNeeded();
    }

    private void openDatabaseAndRunWorkers() {
        try {
            tryToOpenDatabaseAndRunWorkers();
        } catch (ClassNotFoundException e) {
            LOGGER.error("Class missing exception while trying to connect to the database.",
                e);
        } catch (SQLException e) {
            LOGGER.error("SQL exception while trying to read the mixing data from the database.",
                e);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Algorithm missing exception while trying to read the mixing data from the database.",
                e);
        }
    }

    private void tryToOpenDatabaseAndRunWorkers()
        throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
        Class.forName("org.postgresql.Driver");

        Connection db = null;
        Statement sql = null;

        try {
            db = DriverManager.getConnection("jdbc:postgresql:" +
                    getDatabaseName(), getUsername(), getPassword());
            db.setAutoCommit(false);
            sql = db.createStatement();
            sql.setFetchSize(QUERY_FETCH_SIZE);
            resultSet = sql.executeQuery(
                    "SELECT mixingdata.uuid AS muuid, auditdata.uuid AS auuid, inputvotes, outputvotes, votegroupaffsin, votegroupaffsout, encreencproofs FROM mixingdata INNER JOIN auditdata ON mixingdata.processid = auditdata.processid AND mixingdata.nodeid = auditdata.nodeid WHERE mixingdata.isinput IS FALSE");
            runWorkers();
        } finally {
            resultSet.close();
            sql.close();
            db.close();
        }
    }

    MixingVerificationData getMixingVerificationData()
        throws SQLException {
        if (resultSet != null) {
            synchronized (resultSet) {
                if (resultSet.next()) {
                    MixingVerificationData data = new MixingVerificationData(this);
                    data.setMixingData(resultSet.getString("muuid"),
                        resultSet.getBytes("inputvotes"),
                        resultSet.getBytes("outputvotes"));
                    data.setAuditData(resultSet.getString("auuid"),
                        resultSet.getBytes("votegroupaffsin"),
                        resultSet.getBytes("votegroupaffsout"),
                        resultSet.getBytes("encreencproofs"));

                    return data;
                }
            }
        }

        return null;
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

    @Override
    protected Thread createWorker() {
        return new MixingVerificationWorker(this, getP(), getG(), getH());
    }
}
