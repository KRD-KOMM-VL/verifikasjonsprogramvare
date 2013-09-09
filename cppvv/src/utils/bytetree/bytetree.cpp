/**
 * @file   bytetree.cpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2013-03-17 12:25:54 leo>
 * 
 * @brief  The source code of the ByteTree virtual class.
 * 
 * @see bytetree.hpp
 */

#include <cstdlib>
#include <fstream>

#include "bytetree.hpp"
#include "leaf.hpp"
#include "node.hpp"

using namespace cppVerifier;
using namespace cppVerifier::utils;


ByteTree::ByteTree()
{
}


ByteTree::~ByteTree()
{
}


bool ByteTree::isLeaf()
{
        return false;
}


bool ByteTree::isNode()
{
        return false;
}


std::string ByteTree::toString()
{
        throw Exception("In ByteTree.toString: cannot turn instance "
                        "into a string");
}


mpz_class ByteTree::toInteger()
{
        throw Exception("In ByteTree: cannot turn instance into a "
                        "number");
}


std::vector<uint8_t> ByteTree::toVector()
{
        throw Exception("In ByteTree.toVector: cannot turn instance "
                        "into a vector");
}


std::vector<bool> ByteTree::toBoolArray()
{
        throw Exception("In ByteTree.toBoolArray: cannot turn instance "
                        "into a Boolean array");
}


ByteTree * ByteTree::parseString(std::string &s)
{
        ByteTree * res;
        if (s.compare(0,2,"01") == 0)
                res = new Leaf(s);
        else if (s.compare(0,2,"00") == 0)
                res = new Node(s);
        else
        {
                throw Exception("In ByteTree::parseString(): string "
                                "does not correspond to a bytetree!");
        }         
        return res;
}


ByteTree * ByteTree::parseVector(std::vector<uint8_t> &v)
{
        ByteTree * res;
        if (v[0] == 1)
        {
                unsigned int l = v[1] * 0x1000000
                               + v[2] * 0x10000
                               + v[3] * 0x100
                               + v[4];
                std::vector<uint8_t> content(v.begin()+5,v.begin()+l+5);
                res = new Leaf(content);
                v.assign(v.begin()+l+5,v.end());
        }
        else if (v[0] == 0)
                res = new Node(v);
        else
        {
                throw Exception("In ByteTree::parseVector(): vector "
                                "does not correspond to a bytetree!");
        }
        return res;
}


ByteTree * ByteTree::parseFile(std::string path)
{
        std::vector<uint8_t> content;
        std::ifstream file(path);
        if (file.fail())
        {
                throw Exception("In ByteTree::parseFile(): File does "
                                "not exist!");
        }
        
        uint8_t c;
        while (file.good())
        {
                c = file.get();
                if (file.good())
                        content.push_back(c);
        }

        try {
                return parseVector(content);
        }
        catch(Exception exc) {
                treatException("In ByteTree::parseFile(): File parsed found "
                               "but problem with its content.", exc);
                return NULL; // To please the compiler
        }
}


ByteTree * ByteTree::string2ByteTree(std::string s)
{
        std::vector<uint8_t> content(s.size());
        for (unsigned int i=0; i<s.size(); i++)
                content[i] = s[i];
        return new Leaf(content);
}


unsigned int ByteTree::size()
{
        return 0;
}


void ByteTree::prettyPrint(std::string indent)
{
}


bool ByteTree::compare(std::string s)
{
        return true;
}


void ByteTree::addChild(ByteTree * bt)
{
        throw Exception("In ByteTree::addChild(): trying to access "
                        "the children of a non-Node ByteTree.");
}


ByteTree * ByteTree::getChild(unsigned int i)
{
        throw Exception("ERROR: in ByteTree::getChild(): trying to "
                        "access the children of a non-Node ByteTree.");
}
