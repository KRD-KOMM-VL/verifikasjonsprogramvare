/**
 * @file   ro.hpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2012-05-20 11:35:00 leo>
 * 
 * @brief  The header of the random-oracle RO.
 * 
 * @see ro.cpp
 */

#ifndef _RO_H_
#define _RO_H_

#include <cstdint>
#include <vector>

#include "../sha/shax.hpp"
#include "../prg/prg.hpp"

namespace cppVerifier {
namespace crypto {


/**
 * @class RO
 *
 * @brief Implements a random oracle as explained in the verificatum
 * verifier specification.
 *
 * @see rotests.cpp
 */
class RO
{
private:
        /**
         * @brief The hashfunction used by both the PRG and this RO
         * instance.
         */
        SHAx * hashfunction;

        /**
         * @brief The PRG to use to generate the pseudo random bytes.
         */
        PRG * prg;


        /**
         * @brief A 32 bits long unsigned representation of the length
         * of the output required.
         */
        uint32_t nout;

        /**
         * @brief A representation of nout as a vector of 4 uint8_t.
         *
         * The aim of this attribute is to avoid computing this
         * representation each time the RO is queried. Rather than
         * performances, the quest for code simplicity lead to its
         * existence.
         */
        std::vector<uint8_t> vectNout;

public:
        /**
         * @brief Creates a new RO instance.
         *
         * @param hash The hashfunction used by the PRG.  @param
         * outlen The length of the output, it is used to set the nout
         * attribute.
         */
        RO(SHAx * hash, uint32_t outlen);

        /**
         * @brief Implements a query to this random oracle.
         * 
         * @param d The input bytes.
         * 
         * @return The result of the query, a vector of bits of length
         * nout.
         */
        std::vector<uint8_t> query(std::vector<uint8_t> d);

};        


}
} // closing namespace

#endif


