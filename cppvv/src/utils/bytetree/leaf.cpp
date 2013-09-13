/**
 * @file   leaf.cpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2013-09-09 17:17:05 leo>
 * 
 * @brief  The source code of the Leaf class.
 * 
 * @see leaf.hpp
 */

#include <iomanip>
#include <iostream>
#include <sstream>
#include <cstdlib>

#include "leaf.hpp"
#include "../utils.hpp"


using namespace cppVerifier;
using namespace cppVerifier::utils;


Leaf::Leaf(std::vector<uint8_t> content)
{
        bytes = content;
}


Leaf::Leaf(mpz_class number)
{
        bytes = largeNum2byteVector(number);
}


Leaf::Leaf(mpz_class number, unsigned int size)
{
        bytes.resize(size);
        for (int i = bytes.size()-1; i>=0; i--)
        {
                bytes[i] = number.get_ui() % 0x100;
                number = number / 0x100;
        }
}


Leaf::Leaf(std::string &s)
{
        if ((s.compare(0,2,"01") != 0) || (s.size()%2 !=0))
        {
                std::cout<<"ERROR: in Leaf(string), input does not "
                        "start with '01' or has an odd length"
                         <<std::endl<<"input: '"<<s<<"'"<<std::endl;
                exit(1);
        }
        else
        {
                // reading number of bytes
                unsigned int l = octuple2num(s.substr(2,8));
                if ((10+l*2) > s.size())
                {
                        std::cout<<"ERROR in Leaf::Leaf(string): the "
                                "input is too small! Input: "<<s
                                 <<std::endl;
                        exit(1);
                }
                else
                {
                        bytes.resize(l);
                        // reading actual bytes
                        for (unsigned int i=0; i < l; i++)
                                bytes[i] = doublon2byte(s[10+i*2],
                                                        s[11+i*2]);
                        // cutting what we just read from the string
                        s = s.substr(10+l*2,std::string::npos);
                }
        }
}


Leaf::~Leaf()
{
        bytes.clear();
}


std::string Leaf::toString()
{
        std::string result;
        std::vector<uint8_t> byteRepresentation = toVector();
        for (unsigned int i=0; i<byteRepresentation.size(); i++)
                result += byte2str(byteRepresentation[i]);
        return result;
}


std::vector<uint8_t> Leaf::toVector()
{
        std::vector<uint8_t> result;
        result.push_back(1); // Node starts with a 0x01 byte
        // Adding the number of bytes
        uint32_t byteNumber = bytes.size();
        result.push_back((byteNumber>>24) % 0x100);
        result.push_back((byteNumber>>16) % 0x100);
        result.push_back((byteNumber>> 8) % 0x100);
        result.push_back( byteNumber      % 0x100);
        // Adding the bytes contained in the leaf
        result.insert(result.end(),bytes.begin(), bytes.end());
        return result;
}

bool Leaf::isNode()
{
        return false;
}


bool Leaf::isLeaf()
{
        return true;
}


mpz_class Leaf::toInteger()
{
        mpz_class result = 0;
        for (unsigned int i=0; i<bytes.size(); i++)
                result = (result * 0x100) + bytes[i];
        return result;
}


std::vector<bool> Leaf::toBoolArray()
{
        std::vector<bool> result(bytes.size());
        for (unsigned int i=0; i<bytes.size(); i++)
                result[i] = (bytes[i] == 1);
        return result;
}


unsigned int Leaf::size()
{
        return bytes.size();
}


void Leaf::prettyPrint(std::string indent)
{
        std::cout<<indent;
        for (unsigned int i=0; i<bytes.size(); i++)
                std::cout<<utils::byte2str(bytes[i]);
        std::cout<<std::endl;
}


bool Leaf::compare(std::string s)
{
        bool different = (s.size() != bytes.size());
        if (different)
                return true;
        else
        {
                for (unsigned int i=0; i < s.size(); i++)
                        if ((uint8_t)s[i] != bytes[i])
                        {
                                different = true;
                                break;
                        }
                return different;
        }
}

