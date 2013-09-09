/**
 * @file   node.hpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2012-08-09 18:16:50 leo>
 * 
 * @brief  The header of the Node class.
 * 
 * @see node.cpp
 */
#ifndef _NODE_H_
#define _NODE_H_

#include "bytetree.hpp"

namespace cppVerifier {
namespace utils {

/**
 * @class Node
 *
 * @brief Inherits from the ByteTree and implements the case where the
 * ByteTree has children.
 *
 * @see btrtest.cpp
 */
class Node : public ByteTree
{
private:
        /**
         * @brief The ByteTree contained in this node. They can be
         * both Node's or Leaf's.
         * 
         */
        std::vector<ByteTree *> children;

public:
        /**
         * @brief Constructs an empty Node instance.
         */
        Node();

        /**
         * @brief Parses a string to generate the corresponding Node
         * and removes the data read from it.
         * 
         * @param[in,out] s The string to parse. It must start with
         * "00".
         */
        Node(std::string &s);

        /** 
         * @brief Creates a Node from the corresponding vector of
         * bytes and removes the data read from it.
         * 
         * @param[in,out] v The byte representation of the node we
         * want to create. Must start with 0x0.
         */
        Node(std::vector<uint8_t> &v);

        /** 
         * @brief Destroys each element in children.
         */
        ~Node();

        // Those are inherited from ByteTree:
        std::string toString();

        std::vector<uint8_t> toVector();

        bool isNode();

        bool isLeaf();

        unsigned int size();

        void prettyPrint(std::string indent);

        void addChild(ByteTree * bt);

        ByteTree * getChild(unsigned int i);
};

}
} // closing namespace

#endif
