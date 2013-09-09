/**
 * @file   sha2_64bits.cpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2012-10-21 12:44:08 leo>
 * 
 * @brief  The source code of the SHA2_64bits class.
 * 
 * @see sha2_64bits.cpp
 */


#include "sha2_64bits.hpp"

using namespace cppVerifier;
using namespace cppVerifier::crypto;


SHA2_64bits::SHA2_64bits()
{
        K.resize(80);
        K[ 0] = 0x428a2f98d728ae22;
        K[ 1] = 0x7137449123ef65cd;
        K[ 2] = 0xb5c0fbcfec4d3b2f;
        K[ 3] = 0xe9b5dba58189dbbc;
        K[ 4] = 0x3956c25bf348b538;
        K[ 5] = 0x59f111f1b605d019;
        K[ 6] = 0x923f82a4af194f9b;
        K[ 7] = 0xab1c5ed5da6d8118;
        K[ 8] = 0xd807aa98a3030242;
        K[ 9] = 0x12835b0145706fbe;
        K[10] = 0x243185be4ee4b28c;
        K[11] = 0x550c7dc3d5ffb4e2;
        K[12] = 0x72be5d74f27b896f;
        K[13] = 0x80deb1fe3b1696b1;
        K[14] = 0x9bdc06a725c71235;
        K[15] = 0xc19bf174cf692694;
        K[16] = 0xe49b69c19ef14ad2;
        K[17] = 0xefbe4786384f25e3;
        K[18] = 0x0fc19dc68b8cd5b5;
        K[19] = 0x240ca1cc77ac9c65;
        K[20] = 0x2de92c6f592b0275;
        K[21] = 0x4a7484aa6ea6e483;
        K[22] = 0x5cb0a9dcbd41fbd4;
        K[23] = 0x76f988da831153b5;
        K[24] = 0x983e5152ee66dfab;
        K[25] = 0xa831c66d2db43210;
        K[26] = 0xb00327c898fb213f;
        K[27] = 0xbf597fc7beef0ee4;
        K[28] = 0xc6e00bf33da88fc2;
        K[29] = 0xd5a79147930aa725;
        K[30] = 0x06ca6351e003826f;
        K[31] = 0x142929670a0e6e70;
        K[32] = 0x27b70a8546d22ffc;
        K[33] = 0x2e1b21385c26c926;
        K[34] = 0x4d2c6dfc5ac42aed;
        K[35] = 0x53380d139d95b3df;
        K[36] = 0x650a73548baf63de;
        K[37] = 0x766a0abb3c77b2a8;
        K[38] = 0x81c2c92e47edaee6;
        K[39] = 0x92722c851482353b;
        K[40] = 0xa2bfe8a14cf10364;
        K[41] = 0xa81a664bbc423001;
        K[42] = 0xc24b8b70d0f89791;
        K[43] = 0xc76c51a30654be30;
        K[44] = 0xd192e819d6ef5218;
        K[45] = 0xd69906245565a910;
        K[46] = 0xf40e35855771202a;
        K[47] = 0x106aa07032bbd1b8;
        K[48] = 0x19a4c116b8d2d0c8;
        K[49] = 0x1e376c085141ab53;
        K[50] = 0x2748774cdf8eeb99;
        K[51] = 0x34b0bcb5e19b48a8;
        K[52] = 0x391c0cb3c5c95a63;
        K[53] = 0x4ed8aa4ae3418acb;
        K[54] = 0x5b9cca4f7763e373;
        K[55] = 0x682e6ff3d6b2b8a3;
        K[56] = 0x748f82ee5defb2fc;
        K[57] = 0x78a5636f43172f60;
        K[58] = 0x84c87814a1f0ab72;
        K[59] = 0x8cc702081a6439ec;
        K[60] = 0x90befffa23631e28;
        K[61] = 0xa4506cebde82bde9;
        K[62] = 0xbef9a3f7b2c67915;
        K[63] = 0xc67178f2e372532b;
        K[64] = 0xca273eceea26619c;
        K[65] = 0xd186b8c721c0c207;
        K[66] = 0xeada7dd6cde0eb1e;
        K[67] = 0xf57d4f7fee6ed178;
        K[68] = 0x06f067aa72176fba;
        K[69] = 0x0a637dc5a2c898a6;
        K[70] = 0x113f9804bef90dae;
        K[71] = 0x1b710b35131c471b;
        K[72] = 0x28db77f523047d84;
        K[73] = 0x32caab7b40c72493;
        K[74] = 0x3c9ebe0a15c9bebc;
        K[75] = 0x431d67c49c100d4c;
        K[76] = 0x4cc5d4becb3e42b6;
        K[77] = 0x597f299cfc657e2a;
        K[78] = 0x5fcb6fab3ad6faec;
        K[79] = 0x6c44198c4a475817;
}


void SHA2_64bits::preprocess(std::vector<uint8_t> initialMessage)
{
        // padding the initial message by...
        l = initialMessage.size()*8;
        // ... adding 10000000
        initialMessage.push_back(0x80);
        // ... adding the correct amount of zeroes 
        unsigned int k = ( (896-(l+8))%1024 >= 0) ?  (896-(l+8))%1024 :1024+(960-(l+8))%1024;
        k += 64; // Adding 64 bits to zero: we don't treat cases where l>2^64
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
        for (unsigned int i=0; i<initialMessage.size() ; i+=8)
                M.push_back(    ((uint64_t)initialMessage[i]   << 56)
                              | ((uint64_t)initialMessage[i+1] << 48)
                              | ((uint64_t)initialMessage[i+2] << 40)
                              | ((uint64_t)initialMessage[i+3] << 32)
                              | ((uint64_t)initialMessage[i+4] << 24)
                              | ((uint64_t)initialMessage[i+5] << 16)
                              | ((uint64_t)initialMessage[i+6] << 8)
                              |  (uint64_t)initialMessage[i+7] );
        // initialising the hash values H_i
        for (unsigned int i=0; i<8; i++)
                H[i] = init[i];
}


void SHA2_64bits::round(unsigned int counter)
{
        uint64_t W[80], T1, T2;
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
        for (unsigned int t=0; t<80; t++)
        {
                // preparing the message schedule
                W[t] = (t<=15) ? M[counter+t] : smallSigma1_512(W[t-2]) + W[t-7] + smallSigma0_512(W[t-15]) + W[t-16];
                // Re-assigning the temporary variables
                T1 = h + bigSigma1_512(e) + ch(e,f,g) + K[t] + W[t];
                T2 = bigSigma0_512(a) + maj(a,b,c) ;
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
        H[0] += a;
        H[1] += b;
        H[2] += c;
        H[3] += d;
        H[4] += e;
        H[5] += f;
        H[6] += g;
        H[7] += h;
}


void SHA2_64bits::hash(std::vector<uint8_t> initialMessage)
{
        preprocess(initialMessage);
        for (unsigned int counter=0; counter<M.size(); counter+=16)
                round(counter);        
}
