/**
 * @file   utils.cpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2012-08-08 21:18:03 leo>
 * 
 * @brief Functions I really didn't know where to put. These are of
 * general use.
 * 
 * @see utils.h
 */

#include <cstdlib>
#include <iostream>
#include <sstream>
#include <fstream>


#include "utils.hpp"

using namespace cppVerifier;
using namespace cppVerifier::utils;

std::string utils::num2str(unsigned int number)
{
        std::string result;
        std::ostringstream oss;
        oss<<std::hex<<number;
        result = oss.str();
        for (unsigned int i=result.size(); i<8; i++)
                result = "0"+result;
        return result;
}


std::string utils::byte2str(uint8_t b)
{
        std::string r;
        switch ((b/0x10) % 0x10)
        {
        case (0x0): { r = '0'; break; }
        case (0x1): { r = '1'; break; }
        case (0x2): { r = '2'; break; }
        case (0x3): { r = '3'; break; }
        case (0x4): { r = '4'; break; }
        case (0x5): { r = '5'; break; }
        case (0x6): { r = '6'; break; }
        case (0x7): { r = '7'; break; }
        case (0x8): { r = '8'; break; }
        case (0x9): { r = '9'; break; }
        case (0xa): { r = 'a'; break; }
        case (0xb): { r = 'b'; break; }
        case (0xc): { r = 'c'; break; }
        case (0xd): { r = 'd'; break; }
        case (0xe): { r = 'e'; break; }
        case (0xf): { r = 'f'; break; }
        }
        switch (b%0x10)
        {
        case (0x0): { r += '0'; break; }
        case (0x1): { r += '1'; break; }
        case (0x2): { r += '2'; break; }
        case (0x3): { r += '3'; break; }
        case (0x4): { r += '4'; break; }
        case (0x5): { r += '5'; break; }
        case (0x6): { r += '6'; break; }
        case (0x7): { r += '7'; break; }
        case (0x8): { r += '8'; break; }
        case (0x9): { r += '9'; break; }
        case (0xa): { r += 'a'; break; }
        case (0xb): { r += 'b'; break; }
        case (0xc): { r += 'c'; break; }
        case (0xd): { r += 'd'; break; }
        case (0xe): { r += 'e'; break; }
        case (0xf): { r += 'f'; break; }
        }
        return r;
}


uint8_t utils::doublon2byte(char b1, char b2)
{
        uint8_t r = 0;
        switch (b2)
        {
        case ('0'): { r = 0x0 ; break; }
        case ('1'): { r = 0x1 ; break; }
        case ('2'): { r = 0x2 ; break; }
        case ('3'): { r = 0x3 ; break; }
        case ('4'): { r = 0x4 ; break; }
        case ('5'): { r = 0x5 ; break; }
        case ('6'): { r = 0x6 ; break; }
        case ('7'): { r = 0x7 ; break; }
        case ('8'): { r = 0x8 ; break; }
        case ('9'): { r = 0x9 ; break; }
        case ('a'): { r = 0xa; break; }
        case ('A'): { r = 0xa; break; }
        case ('b'): { r = 0xb; break; }
        case ('B'): { r = 0xb; break; }
        case ('c'): { r = 0xc; break; }
        case ('C'): { r = 0xc; break; }
        case ('d'): { r = 0xd; break; }
        case ('D'): { r = 0xd; break; }
        case ('e'): { r = 0xe; break; }
        case ('E'): { r = 0xe; break; }
        case ('f'): { r = 0xf; break; }
        case ('F'): { r = 0xf; break; }
        default:
        {
                std::cout<<"ERROR: in utils::doublon2byte(): "
                        "unknown character ("<<b1<<","<<b2<<"). "
                        "Aborting."<<std::endl;
                exit(1);
        }
        } // end switch on b2
        switch (b1)
        {
        case ('0'): { r += 0x00 ; break; }
        case ('1'): { r += 0x10 ; break; }
        case ('2'): { r += 0x20 ; break; }
        case ('3'): { r += 0x30 ; break; }
        case ('4'): { r += 0x40 ; break; }
        case ('5'): { r += 0x50 ; break; }
        case ('6'): { r += 0x60 ; break; }
        case ('7'): { r += 0x70 ; break; }
        case ('8'): { r += 0x80 ; break; }
        case ('9'): { r += 0x90 ; break; }
        case ('a'): { r += 0xa0 ; break; }
        case ('A'): { r += 0xa0 ; break; }
        case ('b'): { r += 0xb0 ; break; }
        case ('B'): { r += 0xb0 ; break; }
        case ('c'): { r += 0xc0 ; break; }
        case ('C'): { r += 0xc0 ; break; }
        case ('d'): { r += 0xd0 ; break; }
        case ('D'): { r += 0xd0 ; break; }
        case ('e'): { r += 0xe0 ; break; }
        case ('E'): { r += 0xe0 ; break; }
        case ('f'): { r += 0xf0 ; break; }
        case ('F'): { r += 0xf0 ; break; }
        default:
        {
                std::cout<<"ERROR: in utils::doublon2byte(): "
                        "unknown character ("<<b1<<","<<b2<<"). "
                        "Aborting."<<std::endl;
                exit(1);
        }
        } // end switch on b1
                return r;
}


uint32_t utils::octuple2num(std::string s)
{
        if (s.size() != 8)
        {
                std::cout<<"ERROR in utils::octople2num: input"
                        " is not of length 8: "<<s<<std::endl;
                exit(1);
        }
        else
        {
                uint32_t l = (doublon2byte(s[0],s[1]) * 0x1000000)
                        +    (doublon2byte(s[2],s[3]) *   0x10000)
                        +    (doublon2byte(s[4],s[5]) *     0x100)
                        +     doublon2byte(s[6],s[7]);
                return l;
        }
}

std::vector<uint8_t> utils::largeNum2byteVector(
        mpz_class number)
{
        std::vector<uint8_t> bytes;
        mpz_class prov = number;
        bytes.resize(((unsigned int)mpz_sizeinbase(prov.get_mpz_t(),16)+1)/2);
        for (int i = bytes.size()-1; i>=0; i--)
        {
                bytes[i] = prov.get_ui() % 0x100;
                prov = prov / 0x100;
        }
        return bytes;
}


std::vector<uint8_t> utils::file2bytes(std::string path)
{
        std::ifstream content(path);
        uint8_t character;
        std::vector<uint8_t> result;
        while (content.good())
        {
                character = content.get();
                if (content.good())
                        result.push_back(character);
        }
        content.close();
        return result;
}
