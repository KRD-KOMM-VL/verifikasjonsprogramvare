/**
 * @file   bytetree.hpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2013-09-07 16:24:40 leo>
 * 
 * @brief  The header of the ByteTree virtual class.
 *
 * @see bytetree.cpp
 */
#ifndef _BYTE_TREE_H_
#define _BYTE_TREE_H_

#include <iostream>
#include <gmpxx.h>
#include <gmp.h>
#include <vector>
#include <string>
#include <cstdint>

#include "../exception.hpp"


namespace cppVerifier {
namespace utils {

/**
 * @class ByteTree
 *
 * @brief A virtual class providing a partial common interface for
 * Node and Leaf.
 *
 * Look at the verificatum verifier specification (page 5) for a
 * detailed, well, specification of how the ByteTree should behave. To
 * stick close to the spec', we use to distinct classes for the nodes
 * and the leaves but, in order for them to be easily used, we provide
 * a common interface for them via the virtual ByteTree class.
 *
 * @see Node
 * @see Leaf
 * @see btrtest.cpp
 */
class ByteTree{

public:
        /**
         * @brief Empty constructor to please the compiler.
         */
	ByteTree();

        /** 
         * @brief Empty destructor to please the compiler.
         */
        virtual ~ByteTree();
        
        /**
         * @brief Parses a string by constructing a ByteTree of the
         * correct kind.
         *
         * Returns a pointer to a Node if the input starts with "00"
         * or a pointer to a Leaf otherwise. The data actually read
         * from the string is removed from it, the idea being to
         * easily go through it recursively.
         * 
         * @param[in,out] s The string to parse.
         * 
         * @return A pointer to a ByteTree containing the same
         * information as in the input.
         */
        static ByteTree * parseString(std::string &s);

        /** 
         * @brief Parses a vector of bytes by constructing a ByteTree
         * of the correct kind; returns Leaf(0) if the vector is
         * empty.
         *
         * Returns a pointer to a Node if the input starts with 0x0
         * or a pointer to a Leaf otherwise. The data actually read
         * from the vector is removed from it, the idea being to
         * easily go through it recursively.
         * 
         * @param[in,out] v The vector to parse.
         * 
         * @return A pointer to a ByteTree containing the same
         * information as in the input.
         */
        static ByteTree * parseVector(std::vector<uint8_t> &v);

        /** 
         * @brief Parses a file containing the string representation
         * of a bytetree and returns the said bytetree. If the file
         * does not exist, throws an exception.
         * 
         * @param path The path to the file storing the ByteTree.
         */
        static ByteTree * parseFile(std::string path);

        /**
         * @brief Turns a string into its ByteTree representation. Not
         * to be mistaken with parseString!
         *
         * Returns a pointer to Leaf containing the bytes of the
         * string. For instance, string2ByteTree("ABCD") returns a
         * leaf containing [0x65,0x66,0x67,0x68].
         * 
         * @param s The string to turn into a ByteTree
         * 
         * @return A pointer to the ByteTree representing s.
         */
        static ByteTree * string2ByteTree(std::string s);

        /**
         * @brief To know if the instance used is a Leaf.
         *
         * @return true if the type of this object is an instance of
         * the class Leaf, false otherwise.
         */
        virtual bool isLeaf();

        /**
         * @brief To know if the instance used is a Node.
         *
         * @return true if the type of this object is an instance of
         * the class Node, false otherwise.
         */
        virtual bool isNode();

        /**
         * @brief Turns this object into a string.
         * 
         * @return The string representation of this instance.
         */
        virtual std::string toString();

        /**
         * @brief Turns this object into a vector of bytes.
         * 
         * This aim is to allow an easy hashing of the ByteTree's. 
         *
         * @return The byte representation of the ByteTree.
         */
        virtual std::vector<uint8_t> toVector();

        /**
         * @brief Turns this object into an arbitrarily large integer.
         * 
         * @return The integer stored in the bytes if it is a Leaf; 0
         * otherwise.
         */
        virtual mpz_class toInteger();

        /** 
         * @brief Turns this object into a vector of boolean.
         * 
         * @return The boolean stored in the bytes if it is a Leaf,
         * exits with exit code 1 otherwise.
         */
        virtual std::vector<bool> toBoolArray();
        
        /**
         * @brief The number of bytes in a Leaf and the number of
         * ByteTrees in a Node.
         * 
         * @return The size of this instance.
         */
        virtual unsigned int size();

        /**
         * @brief Recursively prints an indented version of the
         * ByteTree (mainly for debugging)
         *
         * @param indent The characters to be printed in the
         * indentation.
         */
        virtual void prettyPrint(std::string indent);
        
        /**
         * @brief Compare the bytes contained in this leaf with those
         * in the input.
         *
         * Functions (almost) like std::string.compare(): outputs
         * false if the bytes in the leaf correspond to the string
         * given in the input, true otherwise. For instance,
         * Leaf(65,66,67).compare("ABC") == false.
         * 
         * @param s The string to compare.
         * 
         * @return False if the string and the bytes are identical,
         * true otherwise. In particular, always return true if we
         * don't have a Leaf.
         */
        virtual bool compare(std::string s);

        /**
         * @brief Adds the ByteTree given in the argument to the
         * children attribute in a Node; exits with exit code 1 if the
         * ByteTree is a Leaf.
         * 
         * @param bt The ByteTree to add.
         */
        virtual void addChild(ByteTree * bt);

        /**
         * @brief Returns the child of index i if we have a Node;
         * exits with exit code 1 if the ByteTree is a Leaf.
         *
         * @return The ith element of the children attribute.
         */
        virtual ByteTree * getChild(unsigned int i);
};


}
} // closing namespaces

#endif
