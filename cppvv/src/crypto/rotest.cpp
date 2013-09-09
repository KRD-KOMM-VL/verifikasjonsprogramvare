/**
 * @file   rotest.cpp
 * @author Léo Perrin
 * @date   Time-stamp: <2013-09-07 14:55:34 leo>
 * 
 * @brief Contains a unitary test of the random oracle, i.e. the
 * crypto::RO class.
 *
 * This test is a unitary test since the behaviour of this class
 * basically boils down to outputing numbers. Note however that only
 * one number for each hashfunction is used: this is not a lot.
 *
 * Furthermore, since the RO is built on top of the PRG which is
 * itself built on top of the SHAx classes, if the output of this test
 * matches the specification then all of the module must work
 * correctly with overwelming probability.
 */


#include <iostream>
#include <iomanip>
#include <vector>

#include "crypto.hpp"

using namespace cppVerifier;
using namespace cppVerifier::crypto;


void printBytes(std::vector<uint8_t> v)
{
        for (unsigned int i=0; i<v.size(); i++)
                std::cout<<std::hex<<std::setw(2)<<std::setfill('0')<<(int)v[i];
        std::cout<<std::endl;
}


int main(void)
{
        SHAx * hash;
        RO * ro;
        std::vector<uint8_t> input;
        for (unsigned int i=0; i<32; i++)
                input.push_back(i);


        // !SUBSECTION!  Testing the SHA256 version
        hash = new SHA256();
        std::cout << "SHA-256" << std::endl;

        std::cout << " 65: ";
        ro = new RO(hash,65);
        printBytes(ro->query(input));

        std::cout << "261: ";
        ro = new RO(hash,261);
        printBytes(ro->query(input));


        // !SUBSECTION!  Testing the SHA384 version
        hash = new SHA384();
        std::cout << "SHA-384" << std::endl;

        std::cout << " 93: ";
        ro = new RO(hash,93);
        printBytes(ro->query(input));

        std::cout << "411: ";
        ro = new RO(hash,411);
        printBytes(ro->query(input));


        // !SUBSECTION!  Testing the SHA512 version
        hash = new SHA512();
        std::cout << "SHA-512" << std::endl;

        std::cout << "111: ";
        ro = new RO(hash,111);
        printBytes(ro->query(input));

        std::cout << "579: ";
        ro = new RO(hash,579);
        printBytes(ro->query(input));

        
        return 0;
}
