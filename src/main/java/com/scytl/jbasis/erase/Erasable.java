/**
 * $Id: Erasable.java 1439 2010-02-04 15:21:03Z gmercadal $
 * @author gmercadal
 * @date   04/02/2010 16:14:38
 *
 * Copyright (C) 2010 Scytl Secure Electronic Voting SA
 *
 * All rights reserved.
 *
 */
package com.scytl.jbasis.erase;


/**
 * Object should erase all the private data it receives and the work data.
 */
public interface Erasable {
    void erase();
}
