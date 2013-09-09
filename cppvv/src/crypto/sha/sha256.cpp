/**
 * @file   sha256.cpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2012-10-21 12:29:16 leo>
 * 
 * @brief Contains the code implementing the SHA-256 hashfunction.
 *
 * @see sha256.hpp
 */

#include "sha256.hpp"
#include <iostream>
#include <iomanip>


using namespace cppVerifier;
using namespace cppVerifier::crypto;


// ================================================= Constants

SHA256::SHA256()
{
        K.resize(64);
        K[  0 ] = 0x428a2f98;
        K[  1 ] = 0x71374491;
        K[  2 ] = 0xb5c0fbcf;
        K[  3 ] = 0xe9b5dba5;
        K[  4 ] = 0x3956c25b;
        K[  5 ] = 0x59f111f1;
        K[  6 ] = 0x923f82a4;
        K[  7 ] = 0xab1c5ed5;
        K[  8 ] = 0xd807aa98;
        K[  9 ] = 0x12835b01;
        K[ 10 ] = 0x243185be;
        K[ 11 ] = 0x550c7dc3;
        K[ 12 ] = 0x72be5d74;
        K[ 13 ] = 0x80deb1fe;
        K[ 14 ] = 0x9bdc06a7;
        K[ 15 ] = 0xc19bf174;
        K[ 16 ] = 0xe49b69c1;
        K[ 17 ] = 0xefbe4786;
        K[ 18 ] = 0x0fc19dc6;
        K[ 19 ] = 0x240ca1cc;
        K[ 20 ] = 0x2de92c6f;
        K[ 21 ] = 0x4a7484aa;
        K[ 22 ] = 0x5cb0a9dc;
        K[ 23 ] = 0x76f988da;
        K[ 24 ] = 0x983e5152;
        K[ 25 ] = 0xa831c66d;
        K[ 26 ] = 0xb00327c8;
        K[ 27 ] = 0xbf597fc7;
        K[ 28 ] = 0xc6e00bf3;
        K[ 29 ] = 0xd5a79147;
        K[ 30 ] = 0x06ca6351;
        K[ 31 ] = 0x14292967;
        K[ 32 ] = 0x27b70a85;
        K[ 33 ] = 0x2e1b2138;
        K[ 34 ] = 0x4d2c6dfc;
        K[ 35 ] = 0x53380d13;
        K[ 36 ] = 0x650a7354;
        K[ 37 ] = 0x766a0abb;
        K[ 38 ] = 0x81c2c92e;
        K[ 39 ] = 0x92722c85;
        K[ 40 ] = 0xa2bfe8a1;
        K[ 41 ] = 0xa81a664b;
        K[ 42 ] = 0xc24b8b70;
        K[ 43 ] = 0xc76c51a3;
        K[ 44 ] = 0xd192e819;
        K[ 45 ] = 0xd6990624;
        K[ 46 ] = 0xf40e3585;
        K[ 47 ] = 0x106aa070;
        K[ 48 ] = 0x19a4c116;
        K[ 49 ] = 0x1e376c08;
        K[ 50 ] = 0x2748774c;
        K[ 51 ] = 0x34b0bcb5;
        K[ 52 ] = 0x391c0cb3;
        K[ 53 ] = 0x4ed8aa4a;
        K[ 54 ] = 0x5b9cca4f;
        K[ 55 ] = 0x682e6ff3;
        K[ 56 ] = 0x748f82ee;
        K[ 57 ] = 0x78a5636f;
        K[ 58 ] = 0x84c87814;
        K[ 59 ] = 0x8cc70208;
        K[ 60 ] = 0x90befffa;
        K[ 61 ] = 0xa4506ceb;
        K[ 62 ] = 0xbef9a3f7;
        K[ 63 ] = 0xc67178f2;
}                                                                           


void SHA256::preprocess(std::vector<uint8_t> initialMessage)
{
        // padding the initial message by...
        l = initialMessage.size()*8;
        // ... adding 10000000
        initialMessage.push_back(0x80);
        // ... adding the correct amount of zeroes
        unsigned int k = ( (448-(l+8))%512 >= 0) ?  (448-(l+8))%512 :  512+(448-(l+8))%512;
        for (unsigned int i=0; i<k; i+=8)
                initialMessage.push_back(0x00);
        // ... and appending the length of the input.
        initialMessage.push_back((l>>56)%0x100);
        initialMessage.push_back((l>>48)%0x100);
        initialMessage.push_back((l>>40)%0x100);
        initialMessage.push_back((l>>32)%0x100);
        initialMessage.push_back((l>>24)%0x100);
        initialMessage.push_back((l>>16)%0x100);
        initialMessage.push_back((l>>8 )%0x100);
        initialMessage.push_back(l%0x100);
        // Turning the initial byte message into a 32words one
        M.clear();
        for (unsigned int i=0; i<initialMessage.size() ; i+=4)
                M.push_back(  (initialMessage[i]   << 24)
                            | (initialMessage[i+1] << 16)
                            | (initialMessage[i+2] << 8)
                            |  initialMessage[i+3] );
        // initialising the hash values H_i
        H[0] = 0x6a09e667;
        H[1] = 0xbb67ae85;
        H[2] = 0x3c6ef372;
        H[3] = 0xa54ff53a;
        H[4] = 0x510e527f;
        H[5] = 0x9b05688c;
        H[6] = 0x1f83d9ab;
        H[7] = 0x5be0cd19;
}


void SHA256::round(unsigned int counter)
{
        uint32_t W[64], T1, T2;
        // initializing the working variables
        a = H[0];
        b = H[1];
        c = H[2];
        d = H[3];
        e = H[4];
        f = H[5];
        g = H[6];
        h = H[7];

        // loop over the currently processed hash
        for (unsigned int t=0; t<64; t++)
        {
                // preparing the message schedule
                W[t] = (t<=15) ?
                        M[counter+t] :
                        smallSigma1_256(W[t-2]) + W[t-7]
                        + smallSigma0_256(W[t-15]) + W[t-16];
                // Re-assigning the temporary variables
                T1 = h + bigSigma1_256(e) + ch(e,f,g) + K[t] + W[t];
                T2 = bigSigma0_256(a) + maj(a,b,c);
                h = g;
                g = f;
                f = e;
                e = d + T1;
                d = c;
                c = b;
                b = a;
                a = T1 + T2;
        }
        // computing the ith hash value H[8]
        H[0] = a + H[0];
        H[1] = b + H[1];
        H[2] = c + H[2];
        H[3] = d + H[3];
        H[4] = e + H[4];
        H[5] = f + H[5];
        H[6] = g + H[6];
        H[7] = h + H[7];
}


void SHA256::hash(std::vector<uint8_t> initialMessage)
{
        preprocess(initialMessage);
        for (unsigned int counter=0; counter<M.size(); counter+=16)
                round(counter);
}


std::vector<uint8_t> SHA256::getHash()
{
        std::vector<uint8_t> digest;
        for (unsigned int i=0; i<8; i++)
        {
                digest.push_back( (H[i]>>24) % 0x100);
                digest.push_back( (H[i]>>16) % 0x100);
                digest.push_back( (H[i]>>8)  % 0x100);
                digest.push_back(  H[i]      % 0x100);
        }
        return digest;
}


std::string SHA256::getType()
{
        return "SHA-256";
}


uint32_t SHA256::getHashLength()
{
        return 256;
}

