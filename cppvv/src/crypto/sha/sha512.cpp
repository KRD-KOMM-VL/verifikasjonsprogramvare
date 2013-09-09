/**
 * @file   sha512.cpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2012-10-21 12:43:59 leo>
 * 
 * @brief Contains the code implementing the SHA-512 hashfunction.
 * 
 * @see sha512.hpp
 */

#include "sha512.hpp"

using namespace cppVerifier;
using namespace cppVerifier::crypto;


SHA512::SHA512()
{
        init.resize(8);
        init[0] = 0x6a09e667f3bcc908;
        init[1] = 0xbb67ae8584caa73b;
        init[2] = 0x3c6ef372fe94f82b;
        init[3] = 0xa54ff53a5f1d36f1;
        init[4] = 0x510e527fade682d1;
        init[5] = 0x9b05688c2b3e6c1f;
        init[6] = 0x1f83d9abfb41bd6b;
        init[7] = 0x5be0cd19137e2179;
}


std::vector<uint8_t> SHA512::getHash()
{                
        std::vector<uint8_t> digest;
        for (unsigned int i=0; i<8; i++)
        {
                digest.push_back( (H[i]>>56) % 0x100);
                digest.push_back( (H[i]>>48) % 0x100);
                digest.push_back( (H[i]>>40) % 0x100);
                digest.push_back( (H[i]>>32) % 0x100);
                digest.push_back( (H[i]>>24) % 0x100);
                digest.push_back( (H[i]>>16) % 0x100);
                digest.push_back( (H[i]>>8)  % 0x100);
                digest.push_back(  H[i]      % 0x100);
        }
        return digest;
}


std::string SHA512::getType()
{
        return "SHA-512";
}


uint32_t SHA512::getHashLength()
{
        return 512;
}
