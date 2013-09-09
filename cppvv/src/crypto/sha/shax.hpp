/**
 * @file   shax.hpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2012-10-20 21:34:03 leo>
 * 
 * @brief The header of the virtual class SHAx which provides a common
 * interface for hashfunctions of the SHA family.
 * 
 * @see SHA256
 * @see SHA384
 * @see SHA512
 */


#ifndef _SHAX_H_
#define _SHAX_H_

#include <cstdint>
#include <vector>
#include <string>

namespace cppVerifier {
namespace crypto {

/**
 * @class SHAx
 *
 * @brief Provides a common interface for all the hashfunctions of the
 * SHA-2 family.
 *
 * These were implemented using the NIST specification. It is
 * available <a
 * href="http://csrc.nist.gov/publications/fips/fips180-2/fips180-2withchangenotice.pdf">
 * here</a>.
 *
 * @see shatest.cpp
 */

class SHAx
{
public:
        /** 
         * @brief Empty constructor.
         */
        SHAx();

        /**
         * @brief Parses a vector of bytes and applies to it the
         * transformations specified in the NIST's specification.
         *
         * Once the call to this function is finished, the M attribute
         * is correctly assigned i.e. it contains 32 bits words, the
         * padding and the length of the initial input. The internal
         * state is also initialised.
         *
         * @param initialMessage A byte vector containing the message
         * to hash.
         */
        virtual void preprocess(std::vector<uint8_t> initialMessage);

        /**
         * @brief Performs a round over the 16 message bytes contained
         * in M, starting at counter and finishing at
         * counter+"something". In the end, the internal state H is
         * updated to the new value.
         */
        virtual void round(unsigned int counter);

        /**
         * @brief Performs all the steps necessary to hash the message
         * given on the input.
         *
         * Calls preprocess over its input and then uses a loop over
         * the length of the message to call round on each of its
         * blocks.
         * 
         * @param initialMessage A byte vector containing the message
         * to hash.
         */
        virtual void hash(std::vector<uint8_t> initialMessage);

        /**
         * @brief Returns a vector of uint8_t containing the hash.
         */
        virtual std::vector<uint8_t> getHash();

        /**
         * @brief To know which hashfunction it is.
         *
         * @return The name of the hashfunction.
         */
        virtual std::string getType();

        /**
         * @brief To know the bitlength of the hash we should expect.
         * 
         * @return The length of the hash, in bits.
         */
        virtual uint32_t getHashLength();


};

}
} // closing namespace


#endif


