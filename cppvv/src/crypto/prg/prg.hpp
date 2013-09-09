/**
 * @file   prg.hpp
 * @author Léo Perrin
 * @date   Time-stamp: <2012-05-20 11:33:47 leo>
 * 
 * @brief  The header of the PRG class.
 * 
 * @see prg.cpp
 */

#ifndef _PRG_H_
#define _PRG_H_

#include "../sha/shax.hpp"

namespace cppVerifier {
namespace crypto {

/**
 * @class PRG
 *
 * @brief Implements a pseudo-random byte generator as explained in
 * the verificatum verifier specification.
 *
 * @see prgtest.cpp
 */
class PRG
{
private:
        /**
         * @brief The SHA function to use to generate the pseudo
         * random bytes.
         */
        SHAx * hashfunction;

        /**
         * @brief The seed to use.
         */
        std::vector<uint8_t> seed;

        /**
         * @brief The digest from which we are extracting pseudo
         * random bytes.
         */
        std::vector<uint8_t> digest;

        /**
         * @brief The index of the byte of the digest we output last.
         */
        unsigned int index;

        /**
         * @brief The length of the seed necessary for this PRG to be
         * correctly initialised.
         */
        unsigned int seedLen;

        /**
         * @brief A 32 bits long unsigned counter used during the
         * computation of the output.
         */
        uint32_t counter;

public:
        /**
         * @brief Creates a new PRG instance, using the given hash as
         * a hash function. The seedLen is set depending on the hash
         * given.
         */
        PRG(SHAx * hash);

        /**
         * @brief Updates the digest, i.e. assigns to the value of
         * H(seed||counter) and then increments counter.
         */
        void updateDigest();

        /**
         * @brief Initializes the PRG with the seed given as the
         * input.
         *
         * If the length of the seed is wrong, exits with exit code
         * 1. It also sets counter to 0.
         */
        void initialize(std::vector<uint8_t> newSeed);

        /**
         * @brief Computes the next random byte, i.e. H(seed||counter).
         *
         * @return The next random byte.
         */
        uint8_t getNextRandByte();

};        


}
} // closing namespace

#endif


