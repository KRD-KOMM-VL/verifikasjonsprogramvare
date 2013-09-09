/**
 * @file   arithm.cpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2013-09-03 22:55:49 leo>
 * 
 * @brief The source code of the functions of the arithm module that
 * do not belong in any class.
 * 
 * @see arithm.hpp
 */


#include "arithm.hpp"

using namespace cppVerifier;
using namespace cppVerifier::arithm;


Group * arithm::unmarshal(std::string repr)
{
        // getting rid of the comment
        unsigned int index = 0;
        std::string groupByteTree = "";
        while (repr[index] != ':')
                index++;
        index += 2; // taking care of the second ':'

        // reading the ByteTree corresponding to the group
        while (index < repr.size())
        {
                // to suppress spaces
                if (repr[index] != ' ')
                        groupByteTree.push_back(repr[index]);
                index++;
        }

        // Returning the group corresponding to the bytetree.
        try {
                return arithm::getGroupFromByteTree(
                        utils::ByteTree::parseString(groupByteTree));
        } catch(utils::Exception exc) {
                utils::treatException("In arithm::unmarshal(repr).\n",exc);
                return NULL;
        }
}


Group * arithm::getGroupFromByteTree(utils::ByteTree * bt)
{
        if (bt->getChild(0)->compare("verificatum.arithm.ModPGroup") == 0)
                return new ModPGrp(bt->getChild(1));
        else
                throw utils::Exception("in arithm::getGroupFromByteTree(bt), bt does "
                                       "not correspond to a known Group.\n");
}
