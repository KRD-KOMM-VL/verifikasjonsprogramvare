/**
 * @file   utils.hpp
 * @author Léo Perrin
 * @date   Time-stamp: <2012-08-08 20:58:22 leo>
 * 
 * @brief Allows other parts of the program to include only one file
 * when using classes from the utils library.
 */
#ifndef _UTILS_H_
#define _UTILS_H_

#include <cstdint>

// The files we are actually interested in.
#include "bytetree/bytetree.hpp"
#include "bytetree/leaf.hpp"
#include "bytetree/node.hpp"
#include "exception.hpp"


namespace cppVerifier {

/**
 * @namespace cppVerifier::utils
 * 
 * @brief Contains several classes and functions used throughout the
 * whole verifier, in particular the ByteTree.
 *
 * Classes in this module are of general utility but do not have much
 * in common apart from that. See their respective documentation for
 * more information.
 **/

namespace utils
{
        /**
         * @brief Turns a number into a string of length 8 containing
         * its hexdecimal representation. It is filled on the left
         * with '0'.
         * 
         * @param number The number to turn into a string.
         * 
         * @return The string representation of number.
         */
        std::string num2str(unsigned int number);

        /**
         * @brief Turns a byte into a two characters string.
         * 
         * @param b The byte to transform.
         * 
         * @return The string representation of the byte.
         */
        std::string byte2str(uint8_t b);

        /**
         * @brief Turns two char representing a byte into the
         * corresponding unsigned byte.
         *
         * For instance:
         * * doublon2byte("01") = 1
         * * doublon2byte("10") = 16
         * 
         * @param b1 The first char two read.
         * @param b2 The second to read.
         * 
         * @return The unsigned byte stored in the bytes b1 and b2.
         */
        uint8_t doublon2byte(char b1, char b2);

        /** 
         * @brief Turns a string of length 8 into a number of
         * bitlength 32.
         * 
         * @param s An hexadecimal representation of a number of
         * length 8.
         * 
         * @return The number represented in the string.
         */
        uint32_t octuple2num(std::string s);

        /**
         * @brief Turns an arbitrary large number into a vector
         * containing bytes representing it. For instance, returns
         * [0x1,0x0] if given 256.
         * 
         * @param number The number to turn into bytes.
         * 
         * @return The byte array corresponding to number.
         */
        std::vector<uint8_t> largeNum2byteVector(mpz_class number);

        /** 
         * @brief Returns the content of a file as a vector of bytes.
         * 
         * @param path The path to the file to read.
         * 
         * @return A byte representation of the content of the file.
         */
        std::vector<uint8_t> file2bytes(std::string path);
}


} // closing cppVerifier namespace

#endif
