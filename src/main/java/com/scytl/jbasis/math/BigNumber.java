package com.scytl.jbasis.math;

import com.scytl.jbasis.util.UnsupportedException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.math.BigInteger;

import java.security.SecureRandom;

import java.util.Random;


/**
 * This class implements the ElementOps interface (required by matrix and
 * gauss_jordan()) for modular big numbers. That is, it provides member
 * functions that implement arithmetic operations between BigNumber instances,
 * modulo a ant.
 */
public class BigNumber implements Cloneable, Serializable {
    /* don't add implement Comparable because it's not in JRE 1.1 */

    /**
     *
     */
    private static final long serialVersionUID = 8995814075188114673L;

    /**
     * Confidence level, this is used for Rabin Miller method for primarility
     * testing of numbers.
     */
    private static final int PRIME_CONFIDENCE = 100;

    /**
     * Constant representing 1, it's quite used. So, better not to instantiate
     * it every time.
     */
    public static final BigNumber ONE = new BigNumber(1);

    /**
     * Constant representing 0, it's quite used. So, better not to instantiate
     * it every time.
     */
    public static final BigNumber ZERO = new BigNumber(0);

    /**
     * The BigInteger constant zero.
     *
     * @since 1.2
     */
    public static final BigInteger BI_ZERO = BigInteger.valueOf(0);

    /**
     * The BigInteger constant one.
     *
     * @since 1.2
     */
    public static final BigInteger BI_ONE = BigInteger.valueOf(1);

    /**
     * Show kaffe warning only once.
     */
    private static boolean _kaffeWarningAlreadyDisplayed = false;

    /**
     * BigNumber is a Decorator around BigInteger, _value is the inner value of
     * BigNumber.
     */
    private java.math.BigInteger _value;

    /**
     * Construct a BigNumber from a given BigInteger.
     *
     * @param value
     *            the BigInteger to use
     */
    public BigNumber(final BigInteger value) {
        _value = value;
    }

    /**
     * Construct a BigNumber from a given BigNumber.
     *
     * @param bn
     *            the BigNumber to use
     */
    public BigNumber(final BigNumber bn) {
        _value = bn.toBigInteger();
    }

    /**
     * Translates a byte array containing the two's-complement binary
     * representation of a BigInteger into a BigNumber. The input array is
     * assumed to be in <i>big-endian</i> byte-order: the most significant byte
     * is in the zeroth element.
     *
     * @param byteArray
     *            big-endian two's-complement binary representation of
     *            BigInteger.
     */
    public BigNumber(final byte[] byteArray) {
        _value = new BigInteger(byteArray);
    }

    /**
     * Construct a BigNumber from a given int.
     *
     * @param i
     *            the int value to use
     */
    public BigNumber(final int i) {
        _value = new BigInteger(String.valueOf(i));
    }

    /**
     * Construct a randomly generated BigNumber.
     *
     * @param bitLength
     *            the bit length of the resulting BigNumber
     * @param rnd
     *            the random source
     */
    public BigNumber(final long bitLength, final Random rnd) {
        // final int maxTries = 20;
        // int tries = maxTries;
        // do {
        _value = new BigInteger((int) Math.min(bitLength, Integer.MAX_VALUE),
                rnd);

        _value = forceBitLength(_value, (int) bitLength);

        // } while (tries != 0);
    }

    /**
     * Construct a BigNumber from a given String.
     *
     * @param str
     *            the String to use
     */
    public BigNumber(final String str) {
        _value = new BigInteger(str);
    }

    /**
     * Construct a BigNumber from a given String and radix.
     *
     * @param str
     *            the String to use
     * @param radix
     *            the radix of the number represented by str
     */
    public BigNumber(final String str, final int radix) {
        _value = new BigInteger(str, radix);
    }

    /**
     * Construct a BigNumber from a given long.
     *
     * @param val
     *            the long value to use
     */
    public BigNumber(final long val) {
        _value = new BigInteger(new Long(val).toString());
    }

    /**
     * Assigns the <code>rhm</code> value to <code>lhm</code>, and returns
     * <code>lhm</code>.
     *
     * @param rhm
     *            the value to assign
     * @return the BigNumber, with the new value
     */
    public BigNumber assign(final BigNumber rhm) {
        _value = rhm._value;

        return this;
    }

    /**
     * @name Binops Binary arithmetic operations between BigNumber instances.
     * @param rhm
     *            the value to add
     * @return a new BigNumber, which is the sum of this._value and rhm
     */
    public BigNumber add(final BigNumber rhm) {
        return new BigNumber(_value.add(rhm._value));
    }

    /**
     * Right way to clone (class constructor cannot be called, Only way to get
     * instance of identical class is through eventually calling Object.clone().
     *
     * @see Object#clone()
     */
    public final Object clone() {
        try {
            final BigNumber cloned = (BigNumber) super.clone();
            cloned._value = new BigInteger(this.toString());

            return cloned;
        } catch (final CloneNotSupportedException e) {
            throw new UnsupportedException(
                "Bignumber parents should support clone: " + e.getMessage());
        }
    }

    /**
     * Compares this BigNumber with the specified BigNumber.
     *
     * @param val
     *            BigInteger to which this BigInteger is to be compared.
     * @return -1, 0 or 1 as this BigNumber is numerically less than, equal to,
     *         or greater than <tt>val</tt>.
     */
    public int compareTo(final BigNumber val) {
        return _value.compareTo(val._value);
    }

    /**
     * @see Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(final Object o) {
        return this.compareTo((BigNumber) o);
    }

    /**
     * Divide this BigNumber by another value.
     *
     * @param rhm
     *            the value by which this is to be divided
     * @return a new BigNumber whose value is (this / val)
     */
    public BigNumber divide(final BigNumber rhm) {
        return new BigNumber(_value.divide(rhm._value));
    }

    /**
     * Returns the bit length.
     *
     * @return the bit length of this
     */
    public long getBitLength() {
        return _value.bitLength();
    }

    /**
     * Returns a new randomly generated BigNumber, which is greater than this
     * and which is a prime with a 100 confidence, using the Rabin Miller
     * method.
     *
     * @return a new BigNumber prime
     */
    public BigNumber higherPrime() {
        final BigNumber bn = new BigNumber(_value.bitLength(),
                new SecureRandom());
        final BigNumber addition = bn.add(this);

        return addition.nextPrime();
    }

    /**
     * Returns the inverse of this given BigNumber modulus m, this does not
     * modifies the current value, much like BigInteger#modInverse(BigNumber).
     *
     * @param m
     *            the modulus
     * @return the inverse of this given BigNumber modulus m.
     * @see BigInteger#modInverse(java.math.BigInteger)
     */
    public BigNumber modInverse(final BigNumber m) {
        return new BigNumber(_value.modInverse(m._value));
    }

    /**
     * Predicate checking equality.
     */
    public boolean equals(final Object obj) {
        boolean result = false;

        if (obj instanceof BigNumber) {
            final BigNumber d = (BigNumber) obj;
            result = _value.equals(d._value);
        }

        return result;
    }

    /**
     * Predicate checking if the number is prime.
     *
     * @return the primarility of this
     */
    public boolean isPrime() {
        // Kaffe java big math implementation (native impl. is ok) tells 1
        // is a prime number, which should be
        return !equals(ONE) && _value.isProbablePrime(PRIME_CONFIDENCE);
    }

    /**
     * Returns the long value of this BigNumber. If the BigNumber is too big for
     * a long value, only the low-order 64 bits are returned.
     *
     * @return this BigNumber converted to a long.
     */
    public long longValue() {
        return _value.longValue();
    }

    /**
     * @param element
     *            value with with the maximum is to be computed.
     * @return the max number between current and given.
     */
    public BigNumber max(final BigNumber element) {
        return new BigNumber(_value.max(element._value));
    }

    /**
     * @param rhm
     *            the modulus
     * @return the requested module of the BigNumber .
     */
    public BigNumber mod(final BigNumber rhm) {
        return new BigNumber(_value.mod(rhm._value));
    }

    /**
     * @param exp
     *            the exponent
     * @param mod
     *            the modulus
     * @return <tt>this<sup>exp</sup> mod mod</tt>
     */
    public BigNumber modPow(final BigNumber exp, final BigNumber mod) {
        return new BigNumber(_value.modPow(exp._value, mod._value));
    }

    /**
     * Alternate method to calculate val^exponent (MOD mod) for BigInteger
     * values using Modular Exponentiation. This implementation is faster than
     * JDK original BigInteger#modPow(BigInteger, BigInteger)
     *
     * @param exponent
     *            exponent
     * @param mod
     *            modulus (p * q)
     * @return the result of applying val ^ exponent MOD mod
     * @see BigInteger#modPow(java.math.BigInteger, java.math.BigInteger)
     * @see BigNumber#modPow(BigNumber, BigNumber)
     */
    public BigNumber modPowAlt(final BigNumber exponent, final BigNumber mod) {
        // Store intermediate results
        BigNumber a = exponent;
        BigNumber b = BigNumber.ONE;
        BigNumber c = this;

        if (a.equals(BigNumber.ZERO)) {
            return BigNumber.ONE;
        }

        while (!a.equals(BigNumber.ZERO)) {
            // If "a" bit 0 is 0, then "a" is even
            if (!a.testBit(0)) {
                // divide a by 2
                a = a.shiftRight(1);
                c = (c.multiply(c)).mod(mod);
            } else {
                // a is odd
                a = a.subtract(BigNumber.ONE);

                // b = b*c (mod n)
                b = (b.multiply(c)).mod(mod);
            }
        }

        return b;
    }

    /**
     * Returns a BigNumber whose value is <tt>(this * val)</tt>.
     *
     * @param rhm
     *            value to be multiplied by this BigInteger.
     * @return <tt>this * rhm</tt>
     */
    public BigNumber multiply(final BigNumber rhm) {
        return new BigNumber(_value.multiply(rhm._value));
    }

    /**
     * Calculate the next prime to this.
     *
     * @return the next prime to this
     */
    public BigNumber nextPrime() {
        return new BigNumber(_value.nextProbablePrime());

        /*
         * BigInteger two = new BigInteger("2"); BigInteger three = new
         * BigInteger("3"); BigInteger x = _value; if
         * ((x.remainder(two)).equals(BI_ZERO)) { x = x.add(BI_ONE); } while
         * (true) { BigInteger xM1 = x.subtract(BI_ONE); if
         * (!(xM1.remainder(three)).equals(BI_ZERO)) { if
         * (x.isProbablePrime(PRIME_CONFIDENCE)) { break; } } x = x.add(two); }
         * _value = x; return new BigNumber(x);
         */
    }

    /**
     * Convenience method to raise a BigInteger, using Chinese Remainder
     * Theorem.
     *
     * @param exponent
     *            exponent
     * @param p
     *            prime number
     * @param q
     *            prime number
     * @param n
     *            modulus (p*q)
     * @return the result from applying val ^ exp mod n
     */
    public BigNumber modPowCrt(final BigNumber exponent, final BigNumber p,
        final BigNumber q, final BigNumber n) {
        // p1 and q1 represent (val MOD p) and (val MOD q)
        BigNumber p1 = mod(p);
        BigNumber q1 = mod(q);

        // We'll do (p1 MOD p, q1 MOD q) ^ exp
        // instead of val^exp MOD n

        /*
         * Java's implementation of modPow is quite slow p1 =
         * p1.modPow(exponent, p); q1 = q1.modPow(exponent, q); Kaffe modPow,
         * compiled against GMP might be faster, evaluate that possibility
         */

        // p1 = p1.modPowAlt(exponent, p);
        // q1 = q1.modPowAlt(exponent, q);
        p1 = p1.modPow(exponent, p);
        q1 = q1.modPow(exponent, q);

        // Solve simultaneous congruences:
        // x = p1 (MOD p)
        // x = q1 (MOD q)
        //
        // We need (by CRT):
        // t1 = p1 * q * (q^-1 (MOD p))
        // t2 = q1 * p * (p^-1 (MOD q))
        // x = t1 + t2 (MOD p*q)
        final BigNumber t1 = p1.multiply(q).multiply(q.modInverse(p));
        final BigNumber t2 = q1.multiply(p).multiply(p.modInverse(q));
        final BigNumber x = (t1.add(t2)).mod(n);

        // Answer found, return x as an BigNumber
        return x;
    }

    /**
     * A bug in Kaffe 1.1.4 & 1.1.5 (when configured to use native math) , which
     * rounds up bitlength in BigInteger creations to lengths multiples of 8.
     * That means that a {@link BigInteger#BigInteger(int, Random)} can return a
     * number longer than requested.
     *
     * @param num
     * @param numBits
     * @return num if it its bitLength is no longer than numBits. It is longer,
     *         it strips lower bits
     */
    private static BigInteger forceBitLength(final BigInteger num,
        final int numBits) {
        final int bl = num.bitLength();

        if (bl <= numBits) {
            return num;
        } else {
            if (!_kaffeWarningAlreadyDisplayed) {
                System.out.println("Retrying new BigNumber creation " +
                    "due to Kaffe rounding up. Requested " + numBits +
                    ", obtained: " + bl);
                _kaffeWarningAlreadyDisplayed = true;
            }

            final int dif = bl - numBits;
            final BigInteger ok = num.shiftRight(dif);

            if (ok.bitLength() > numBits) {
                throw new RuntimeException("bug in forceBitLength");
            }

            return ok;
        }
    }

    /**
     * HOWEVER 20 retries was not enough for bitLength=9
     *
     * @param num
     *            number to check it doesn't have more bits than numBits
     * @param numTries
     *            num of retries (including this one) that we allow to get
     *            higher bitlength
     * @param numBits
     *            expected maximum number of bits
     * @return 0 if bitlength is ok. returns numRetries -1 if bitlength is too
     *         long
     * @throws UnsupportedException
     *             if check fails and numRetries is 1
     */

    // private static int checkBitLength(
    // final BigInteger num, final int numBits,
    // final int numTries) throws UnsupportedException {
    //                
    // int bl = num.bitLength();
    // if (bl <= numBits) {
    // return 0;
    // } else {
    // if (numTries <= 1) {
    // String msg = "New BigInteger has more bits than requested: "
    // + "requested " + numBits + ", obtained: " + bl + ". ";
    // final int kaffeRoundUp = 8;
    // if (numBits % kaffeRoundUp != 0) {
    // msg += "Probably due to bug in kaffe, which rounds up "
    // + "bitlength to 8 multiples.";
    // }
    // throw new UnsupportedException(msg);
    // } else {
    // System.out.println("Retrying new BigNumber creation");
    // return numTries - 1;
    // }
    // }
    // }
    // private final boolean isPrime(final BigInteger num) {
    // if (num.isP)
    // bigNum..equals(BigNumber.ZERO))
    // }
    /**
     * Method for finding a safe prime. A safe prime is one of the form: p = 2 *
     * p' + 1, where p' is another prime
     *
     * @param bitLength
     *            Bit length of prime number
     * @param rnd
     *            random number source to gather numbers from
     * @return safe prime number
     */
    public static BigNumber safePrime(final int bitLength, final Random rnd) {
        BigNumber ret = null;
        BigNumber bigNum = null;

        /*
         * Required loop, since Kaffe's native BigInteger (compiled against GMP)
         * doesn't implement BigInteger.probablePrime()
         */
        do {
            do {
                // minus 1 because we add 1 bit when multiplying by 2
                bigNum = new BigNumber(bitLength - 1, rnd);
            } while (!bigNum.isPrime());

            ret = bigNum.shiftLeft(1).add(BigNumber.ONE);
        } while (!ret.isPrime());

        return ret;
    }

    /**
     * Returns a BigNumber whose value is <tt>(this &lt;&lt; n)</tt>. The shift
     * distance, <tt>n</tt>, may be negative, in which case this method performs
     * a right shift. (Computes <tt>floor(this * 2<sup>n</sup>)</tt>.)
     *
     * @param i
     *            shift distance, in bits.
     * @return <tt>this &lt;&lt; n</tt>
     * @see BigInteger#shiftLeft
     * @see BigNumber#shiftRight for its counterpart
     */
    public BigNumber shiftLeft(final int i) {
        return new BigNumber(_value.shiftLeft(i));
    }

    /**
     * Returns a BigNumber whose value is <tt>(this &gt;&gt; n)</tt>. Sign
     * extension is performed. The shift distance, <tt>n</tt>, may be negative,
     * in which case this method performs a left shift. (Computes
     * <tt>floor(this / 2<sup>n</sup>)</tt>.)
     *
     * @param i
     *            shift distance, in bits.
     * @return <tt>this &gt;&gt; n</tt>
     * @see BigInteger#shiftRight
     * @see BigNumber#shiftLeft for its counterpart
     */
    public BigNumber shiftRight(final int i) {
        return new BigNumber(_value.shiftRight(i));
    }

    /**
     * Returns a BigNumber whose value is <tt>(this - val)</tt>.
     *
     * @param rhm
     *            value to be subtracted from this BigInteger.
     * @return <tt>this - val</tt>
     */
    public BigNumber subtract(final BigNumber rhm) {
        return new BigNumber(_value.subtract(rhm._value));
    }

    /**
     * Returns <tt>true</tt> if and only if the designated bit is set. (Computes
     * <tt>((this &amp; (1&lt;&lt;n)) != 0)</tt>.)
     *
     * @param i
     *            index of bit to test.
     * @return <tt>true</tt> if and only if the designated bit is set.
     */
    public boolean testBit(final int i) {
        return _value.testBit(i);
    }

    /**
     * Returns a byte array containing the two's-complement representation of
     * this BigNumber. The byte array will be in <i>big-endian</i> byte-order:
     * the most significant byte is in the zeroth element. The array will
     * contain the minimum number of bytes required to represent this BigNumber,
     * including at least one sign bit, which is <tt>(ceil((this.bitLength() +
     * 1)/8))</tt>. (This representation is compatible with the
     * {@link #BigNumber(byte[]) (byte[])} constructor.)
     *
     * @return a byte array containing the two's-complement representation of
     *         this BigInteger.
     * @see #BigNumber(byte[])
     */
    public byte[] toByteArray() {
        return _value.toByteArray();
    }

    /**
     * Returns the decimal String representation of this BigNumber. The
     * digit-to-character mapping provided by <tt>Character.forDigit</tt> is
     * used, and a minus sign is prepended if appropriate. (This representation
     * is compatible with the {@link #BigNumber(String) (String)} constructor,
     * and allows for String concatenation with Java's + operator.)
     *
     * @return decimal String representation of this BigNumber.
     * @see Character#forDigit
     * @see #BigNumber(java.lang.String)
     */
    public String toString() {
        return _value.toString();
    }

    /**
     * Returns the String representation of this BigNumber in the given radix.
     * If the radix is outside the range from {@link Character#MIN_RADIX} to
     * {@link Character#MAX_RADIX} inclusive, it will default to 10 (as is the
     * case for <tt>Integer.toString</tt>). The digit-to-character mapping
     * provided by <tt>Character.forDigit</tt> is used, and a minus sign is
     * prepended if appropriate. (This representation is compatible with the
     * {@link #BigNumber(String, int) (String, <code>int</code>)} constructor.)
     *
     * @param radix
     *            radix of the String representation.
     * @return String representation of this BigNumber in the given radix.
     * @see Integer#toString()
     * @see Character#forDigit
     * @see #BigNumber(java.lang.String, int)
     */
    public String toString(final int radix) {
        return _value.toString(radix);
    }

    /**
     * @return the inner value.
     */
    public BigInteger toBigInteger() {
        return _value;
    }

    /**
     * Returns the hash for this BigNumber.
     *
     * @see BigInteger#hashCode()
     */
    public int hashCode() {
        return _value.hashCode();
    }

    /**
     * Converts this BigInteger to an int, if this BigNumber is too big to fit
     * in an int, only the low-order 32 bits are returned. Note that this
     * conversion can lose information about the overall magnitude of the
     * BigInteger value as well as return a result with the opposite sign.
     *
     * @return this BigNumber converted to an int.
     */
    public int intValue() {
        return _value.intValue();
    }

    private void writeObject(final ObjectOutputStream out)
        throws IOException {
        out.writeObject(_value.toString());
    }

    private void readObject(final ObjectInputStream in)
        throws IOException, ClassNotFoundException {
        _value = new BigInteger((String) in.readObject());
    }

    /**
     * @return Returns the value.
     */
    public java.math.BigInteger getValue() {
        return _value;
    }
}
;
