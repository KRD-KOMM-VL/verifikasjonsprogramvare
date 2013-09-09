/**
 * @file   sha512.hpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2012-10-20 22:17:14 leo>
 * 
 * @brief  The header for the SHA-512 hashfunction of the SHA-2 family.
 *
 * @see sha512.cpp
 */
#ifndef _SHA512_H_
#define _SHA512_H_

#include "sha2_64bits.hpp"


namespace cppVerifier {
namespace crypto {


/**
 * @class SHA512
 *
 * @brief Implements the SHA512 hashfunction, i.e. sha-512.
 */
class SHA512 : public SHA2_64bits
{
public:
        SHA512();
        std::vector<uint8_t> getHash();
        std::string getType();
        uint32_t getHashLength();
};


}
} // closing namespace

#endif


