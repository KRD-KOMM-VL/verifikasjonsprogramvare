/**
 * @file   ro.cpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2012-05-20 11:34:18 leo>
 * 
 * @brief  Contains the code of the random oracle RO.
 * 
 * @see ro.hpp
 */

#include <cstdint>
#include <iostream>

#include "ro.hpp"

using namespace cppVerifier;
using namespace cppVerifier::crypto;


RO::RO(SHAx * hash, uint32_t outlen)
{
        hashfunction = hash;
        prg = new PRG(hash);
        nout = outlen;
        vectNout.push_back( (outlen>>24) % 0x100);
        vectNout.push_back( (outlen>>16) % 0x100);
        vectNout.push_back( (outlen>> 8) % 0x100);
        vectNout.push_back(  outlen      % 0x100);
}


std::vector<uint8_t> RO::query(std::vector<uint8_t> d)
{
        // computing the length of the output and the number of bits
        // to set to zero.
        unsigned int len = (nout%8 == 0) ? nout/8 : (nout/8) +1,
                padding = (nout%8 == 0) ? 0 :
                (nout%8 > 0) ? 8-(nout%8) : 8+(nout%8);
        // computing the seed
        std::vector<uint8_t> in(vectNout);
        in.insert(in.end(), d.begin(), d.end());
        hashfunction->hash(in);
        std::vector<uint8_t> digest(hashfunction->getHash());
        // calling the PRG
        prg->initialize(digest);
        std::vector<uint8_t> out;
        for (unsigned int i=0; i<len; i++)
                out.push_back(prg->getNextRandByte());
        // setting the padding first bits to zero.
        for (unsigned int i=8; i>=8-padding; i--)
                out[0] &= ~(1<<i);
        return out;

}
