/**
 * $Id: UnsupportedException.java 1135 2008-02-07 17:20:36Z dimitris $
 * @author <a href="mailto:developer@scytl.com">developer name</a>
 * @date    20-dic-2005 16:29:04
 *
 * Copyright (C) 2005 Scytl Secure Electronic Voting SA
 *
 * All rights reserved.
 *
 */
package com.scytl.jbasis.util;


/**
 * To be thrown when an operation or feature is not supported.
 *
 * @not-testable
 */
public class UnsupportedException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = 284267809647158320L;

    /**
     * Default constructor.
     */
    public UnsupportedException() {
        super();
    }

    /**
     * @param message
     *            description message of the exception
     * @param cause
     *            encapsulated source exception
     */
    public UnsupportedException(final String message, final Throwable cause) {
        super(message + cause.toString());
    }

    /**
     * @param message
     *            description message of the exception
     */
    public UnsupportedException(final String message) {
        super(message);
    }

    /**
     * @param cause
     *            encapsulated source exception
     */
    public UnsupportedException(final Throwable cause) {
        super(cause.toString());
    }
} // UnsupportedException
