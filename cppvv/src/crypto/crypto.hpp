/**
 * @file   crypto.hpp
 * @author Léo Perrin
 * @date   Time-stamp: <2013-03-11 22:16:44 leo>
 * 
 * @brief Allows other parts of the program to include only one file
 * when using classes from the crypto library.
 */
#ifndef _CRYPTOTOOLS_H_
#define _CRYPTOTOOLS_H_

// We need integers of known size
#include <cstdint>

// This is always useful
#include <vector>
#include <string>

// To have exceptions and bytetrees
#include "../utils/utils.hpp"

// The files we are actually interested in.
#include "sha/sha256.hpp"
#include "sha/sha384.hpp"
#include "sha/sha512.hpp"
#include "prg/prg.hpp"
#include "ro/ro.hpp"




namespace cppVerifier
{

/**
 * @namespace cppVerifier::crypto
 * 
 * @brief Contains cryptographic tools, namely implementations of the
 * SHA-2 hashfunctions, a pseudo-random generator and a random oracle.
 *
 * The SHA hashfunctions all inherits from the same SHAx virtual class
 * so it should be easy to add new hashfunctions. The pseudo-random
 * generators relies on a hashfunction and the random oracle depends
 * on a hashfunction and on a pseuo-random generator.
 **/
namespace crypto
{
        /**
         * @brief Returns the hashfunction whose name is given by
         * hashName.
         *
         * @throw If the hashName is unknown, throws an exception.
         */
        SHAx * getHash(std::string hashName);
}

}
        
#endif
