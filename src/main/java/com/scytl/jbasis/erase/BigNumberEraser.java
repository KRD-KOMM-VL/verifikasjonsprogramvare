/**
 * $Id: BigNumberEraser.java 1439 2010-02-04 15:21:03Z gmercadal $
 * @author gmercadal
 * @date   04/02/2010 16:07:59
 *
 * Copyright (C) 2010 Scytl Secure Electronic Voting SA
 *
 * All rights reserved.
 *
 */
package com.scytl.jbasis.erase;

import com.scytl.jbasis.math.BigNumber;


/**
 *
 */
public class BigNumberEraser implements ObjectEraser {
    /**
     * @see com.scytl.jbasis.erase.ObjectEraser#erase(java.lang.Object)
     */
    public void erase(final Object o) {
        BigNumber number = (BigNumber) o;
        BigIntegerEraser eraser = new BigIntegerEraser();
        eraser.erase(number.getValue());
    }
}
