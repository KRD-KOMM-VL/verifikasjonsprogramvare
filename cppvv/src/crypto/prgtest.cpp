/**
 * @file   prgtest.cpp
 * @author Léo Perrin
 * @date   Time-stamp: <2012-11-17 16:10:04 leo>
 * 
 * @brief Performs a brief test of the pseudo-random generator,
 * i.e. the crypto::PRG class.
 * 
 * This file contains its own data so the test is not really
 * impressive and should not considered to be sufficient. You have to
 * check that its output is identical to the content of the
 * corresponding expected_output.txt file.
 */

#include <iostream>
#include <iomanip>


#include "crypto.hpp"

using namespace cppVerifier;
using namespace cppVerifier::crypto;


int main(void)
{
        SHAx * hash;
        PRG * p;
        std::vector<uint8_t> seed;

        // Testing with SHA-256

        std::cout<<"SHA-256";
        hash = new SHA256();
        p = new PRG(hash);
        seed.clear();
        for (uint8_t byte=0; byte<32; byte++)
                seed.push_back(byte);
        p->initialize(seed);
        for (unsigned int i=0; i<128; i++)
        {
                if ((i%32)==0)
                        std::cout<<std::endl;
                std::cout<<std::hex<<std::setfill('0')
                         <<std::setw(2)<<(int)p->getNextRandByte();
        }
        std::cout<<std::endl;


        // Testing with SHA-384

        std::cout<<std::endl<<"SHA-384";
        hash = new SHA384();
        p = new PRG(hash);
        seed.clear();
        for (uint8_t byte=0; byte<48; byte++)
                seed.push_back(byte);
        p->initialize(seed);
        for (unsigned int i=0; i<128; i++)
        {
                if ((i%32)==0)
                        std::cout<<std::endl;
                std::cout<<std::hex<<std::setfill('0')
                         <<std::setw(2)<<(int)p->getNextRandByte();
        }
        std::cout<<std::endl;


        // Testing with SHA-512

        std::cout<<std::endl<<"SHA-512";
        hash = new SHA512();
        p = new PRG(hash);
        seed.clear();
        for (uint8_t byte=0; byte<64; byte++)
                seed.push_back(byte);
        p->initialize(seed);
        for (unsigned int i=0; i<128; i++)
        {
                if ((i%32)==0)
                        std::cout<<std::endl;
                std::cout<<std::hex<<std::setfill('0')
                         <<std::setw(2)<<(int)p->getNextRandByte();
        }
        std::cout<<std::endl;

        return 0;
}
