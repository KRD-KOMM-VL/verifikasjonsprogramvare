/**
 * @file   prg.cpp
 * @author Léo Perrin
 * @date   Time-stamp: <2013-03-15 17:07:47 leo>
 * 
 * @brief The code of the pseudo-random generator class, PRG.
 * 
 * @see prg.hpp
 */

#include <cstdint>
#include <cstdlib>
#include <iostream>

#include "../../utils/utils.hpp"
#include "../sha/shax.hpp"
#include "prg.hpp"

using namespace cppVerifier;
using namespace cppVerifier::crypto;


PRG::PRG(SHAx * hash)
{
        hashfunction = hash;
        seedLen = hash->getHashLength();
}


void PRG::updateDigest()
{
        std::vector<uint8_t> toHash (seed);
        toHash.push_back( (counter>>24) % 0x100);
        toHash.push_back( (counter>>16) % 0x100);
        toHash.push_back( (counter>> 8) % 0x100);
        toHash.push_back(  counter      % 0x100);
        hashfunction->hash(toHash);
        digest = hashfunction->getHash();
        counter++;
}


void PRG::initialize(std::vector<uint8_t> newSeed)
{
        if (newSeed.size()*8 != seedLen)
        {
                throw utils::Exception("In PRG.initialize: wrong seed length "
                                       "for the PRG.");
        }
        seed = newSeed;
        counter = 0;
        index = 0;
        updateDigest();
}


uint8_t PRG::getNextRandByte()
{
        uint8_t out = digest[index];
        index++;
        if (index == hashfunction->getHashLength()/8)
        {
                updateDigest();
                index = 0;
        }
        return out;
}
