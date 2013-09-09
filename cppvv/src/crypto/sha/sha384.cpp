/**
 * @file   sha384.cpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2012-10-21 12:44:17 leo>
 * 
 * @brief  Contains the code implementing the SHA-384 hashfunction.
 * 
 * @see sha384.hpp
 */

#include "sha384.hpp"


using namespace cppVerifier;
using namespace cppVerifier::crypto;


SHA384::SHA384()
{
        init.resize(8);
        init[0] = 0xcbbb9d5dc1059ed8;
        init[1] = 0x629a292a367cd507;
        init[2] = 0x9159015a3070dd17;
        init[3] = 0x152fecd8f70e5939;
        init[4] = 0x67332667ffc00b31;
        init[5] = 0x8eb44a8768581511;
        init[6] = 0xdb0c2e0d64f98fa7;
        init[7] = 0x47b5481dbefa4fa4;
}


std::vector<uint8_t> SHA384::getHash()
{                
        std::vector<uint8_t> digest;
        // this time, we take only the five first elements
        for (unsigned int i=0; i<6; i++)
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


std::string SHA384::getType()
{
        return "SHA-384";
}


uint32_t SHA384::getHashLength()
{
        return 384;
}
