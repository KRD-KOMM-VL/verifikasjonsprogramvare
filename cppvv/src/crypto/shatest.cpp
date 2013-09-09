/**
 * @file   shatest.cpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2013-03-11 22:19:03 leo>
 * 
 * @brief A unitary test to check that the hashfunctions implementing
 * the crypto::SHAx interface compute the correct hashes using
 * test vectors from the NIST.
 *
 * The binary file obtained from the compilation of this one takes as
 * argument either "SHA-256", "SHA-384" or "SHA-512" and reads from
 * the standard input bytes to hash. These have to be "explicit" in
 * the sense that the byte 0x10 is encoded as the character '1' and
 * then the character 'zero'.
 *
 * It reads one line of input, hashes it using the hashfunction
 * specified in the argument and outputs the hash in the same way. It
 * stops when the input is empty.
 */

#include <stdint.h>
#include <string.h>
#include <iostream>
#include <iomanip>
#include <string>
#include <vector>

#include "crypto.hpp"

using namespace cppVerifier;


/**
 * @brief Reads a line from stdin and turns it into a vector of
 * unsigned char. Then returns true if there is a line left in stdin;
 * false otherwise.
 *
 * Reads characters from the input two by two and turns them into the
 * corresponding hexadecimal bytes. It is assumed that the input is
 * correctly formated; no verifications are made.
 *
 * @param in This vector is emptied in the beginning and then receives
 * the bytes read from stdin. At the end of the call, it contains the
 * bytes encoded in the current line.
 */
bool parseInput(std::vector<unsigned char> &in)
{
        in.clear();
        std::string str;
        std::getline(std::cin,str,'\n');
        if (str.size()>1)
                for (unsigned int i=0; i<str.size(); i+=2)
                        if ( (i+1) < str.size() )
                        {
                                unsigned char hexaByte = 0;
                                switch(str[i])
                                {
                                case '0': hexaByte += 0 *16; break; 
                                case '1': hexaByte += 1 *16; break; 
                                case '2': hexaByte += 2 *16; break;
                                case '3': hexaByte += 3 *16; break;
                                case '4': hexaByte += 4 *16; break;
                                case '5': hexaByte += 5 *16; break;
                                case '6': hexaByte += 6 *16; break;
                                case '7': hexaByte += 7 *16; break;
                                case '8': hexaByte += 8 *16; break;
                                case '9': hexaByte += 9 *16; break;
                                case 'a': hexaByte += 10*16; break;
                                case 'b': hexaByte += 11*16; break;
                                case 'c': hexaByte += 12*16; break;
                                case 'd': hexaByte += 13*16; break;
                                case 'e': hexaByte += 14*16; break;
                                case 'f': hexaByte += 15*16; break;
                                }
                                switch (str[i+1])
                                {
                                case '0': hexaByte += 0 ; break;
                                case '1': hexaByte += 1 ; break;
                                case '2': hexaByte += 2 ; break;
                                case '3': hexaByte += 3 ; break;
                                case '4': hexaByte += 4 ; break;
                                case '5': hexaByte += 5 ; break;
                                case '6': hexaByte += 6 ; break;
                                case '7': hexaByte += 7 ; break;
                                case '8': hexaByte += 8 ; break;
                                case '9': hexaByte += 9 ; break;
                                case 'a': hexaByte += 10; break;
                                case 'b': hexaByte += 11; break;
                                case 'c': hexaByte += 12; break;
                                case 'd': hexaByte += 13; break;
                                case 'e': hexaByte += 14; break;
                                case 'f': hexaByte += 15; break;
                                }
                                in.push_back(hexaByte);
                        }
        return (std::cin.eof());
}


int main(int argc, char ** argv)
{
        if (argc == 1)
        {
                std::cout<<"Specify \"SHA-256\", \"SHA-384\" or"
                         <<"\"SHA-512\" in the CLI arguments."
                         <<std::endl;
                return 1;
        }
        else
        {
                std::string hashName(argv[1]);
                crypto::SHAx * s = crypto::getHash(hashName);
                bool finished = false;
                std::vector<unsigned char> message;
                std::vector<uint8_t> digest;
                std::cout<<"hash function: "<<s->getType();
                std::cout<<", "<<s->getHashLength()<<" bits long"<<std::endl;
                while (!finished)
                {
                        finished = parseInput(message);
                        if (!finished)
                        {
                                s->hash(message);
                                digest = s->getHash();
                                for (unsigned int i=0; i<digest.size(); i++)
                                        std::cout<<std::hex<<std::setfill('0')<<std::setw(2)<<(int)digest[i];
                                std::cout<<std::endl;
                        }
                }
                return 0;
        }
}
        
