/**
 * @file   shax.cpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2012-10-20 21:34:23 leo>
 * 
 * @brief  Contains the code implementing the SHAx class.
 * 
 * @see shax.hpp
 */


#include "shax.hpp"


using namespace cppVerifier;
using namespace cppVerifier::crypto;


SHAx::SHAx()
{
}


void SHAx::preprocess(std::vector<uint8_t> initialMessage)
{
}


void SHAx::round(unsigned int counter)
{
}


void SHAx::hash(std::vector<uint8_t> initialMessage)
{
}


std::vector<uint8_t> SHAx::getHash()
{
        return std::vector<uint8_t>(0);
}

        
std::string SHAx::getType()
{
        return "SHAx";
}


uint32_t SHAx::getHashLength()
{
        return 0;
}
