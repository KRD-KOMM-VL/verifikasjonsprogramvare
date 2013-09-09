/**
 * @file   sha384.hpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2012-10-20 22:11:05 leo>
 * 
 * @brief The header for the SHA-384 hashfunction of the SHA-2 family.
 *
 * @see sha384.cpp
 */
#ifndef _SHA384_H_
#define _SHA384_H_

#include "sha2_64bits.hpp"


namespace cppVerifier {
namespace crypto {

/**
 * @class SHA384
 *
 * @brief Implements the SHA384 hashfunction, i.e. sha-384. Inherits
 * from SHAx.
 */
class SHA384 : public SHA2_64bits
{
public:
        SHA384();
        std::vector<uint8_t> getHash();
        std::string getType();
        uint32_t getHashLength();
};


}
} // closing namespace        

#endif


