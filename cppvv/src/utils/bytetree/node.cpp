/**
 * @file   node.cpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2012-08-09 19:13:08 leo>
 * 
 * @brief  The source code of the Node class.
 * 
 * @see node.hpp
 */

#include <sstream>
#include <iomanip>
#include <cstdlib>

#include "node.hpp"
#include "../utils.hpp"


using namespace cppVerifier;
using namespace cppVerifier::utils;


Node::Node()
{
}


Node::~Node()
{
        for (unsigned int i=0; i<children.size(); i++)
                free(children[i]);
}


Node::Node(std::string &s)
{
        if ((s.compare(0,2,"00") != 0) || (s.size()%2 !=0))
        {
                std::cout<<"ERROR: in Node(string), input does not "
                        "start with '00' or has an odd length"
                         <<std::endl<<"input: '"<<s<<"'"<<std::endl;
                exit(1);
        }
        else
        {
                // reading number of children
                unsigned int l = octuple2num(s.substr(2,8));
                children.resize(l);
                s = s.substr(10,std::string::npos);
                for (unsigned int i=0; i<l; i++)
                        children[i] = ByteTree::parseString(s);
        }
}


Node::Node(std::vector<uint8_t> &v)
{
        if (v[0] != 0)
        {
                std::cout<<"ERROR: in Node(vector), input does not "
                        "start with 0x0.\nv= " <<std::endl;
                for (unsigned int i=0; i<v.size(); i++)
                        std::cout<<std::hex<<v[i]<<",";
                std::cout<<std::endl;
                exit(1);
        }
        else
        {
                // reading number of children
                unsigned int l = v[1]*0x1000000
                               + v[2]*0x10000
                               + v[3]*0x100
                               + v[4];
                children.resize(l);
                v.assign(v.begin()+5, v.end());
                for (unsigned int i=0; i<l; i++)
                        children[i] = ByteTree::parseVector(v);
        }
}               


std::string Node::toString()
{
        std::string result;
        std::vector<uint8_t> bytes = toVector();
        for (unsigned int i=0; i<bytes.size(); i++)
                result += byte2str(bytes[i]);
        return result;
}


std::vector<uint8_t> Node::toVector()
{
        std::vector<uint8_t> result;
        result.push_back(0); // Node starts with a 0x00 byte
        // Adding the number of children
        uint32_t byteNumber = children.size();
        result.push_back((byteNumber>>24) % 0x100);
        result.push_back((byteNumber>>16) % 0x100);
        result.push_back((byteNumber>> 8) % 0x100);
        result.push_back( byteNumber      % 0x100);
        // Adding the byte representation of the children
        for (unsigned int i=0; i<children.size(); i++)
        {
                std::vector<uint8_t> bytesChild = children[i]->toVector();
                result.insert(result.end(),bytesChild.begin(), bytesChild.end());
        }
        return result;
}


bool Node::isNode()
{
        return true;
}


bool Node::isLeaf()
{
        return false;
}


unsigned int Node::size()
{
        return children.size();
}


void Node::prettyPrint(std::string indent)
{
        std::cout<<indent<<num2str(children.size())<<std::endl;
        for (unsigned int i=0; i<children.size(); i++)
                children[i]->prettyPrint(indent+"  ");
        std::cout<<std::endl;
}


void Node::addChild(ByteTree * bt)
{
        children.push_back(bt);
}


ByteTree * Node::getChild(unsigned int i)
{
        return children[i];
}
