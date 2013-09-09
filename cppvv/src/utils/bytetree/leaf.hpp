/**
 * @file   leaf.hpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2012-08-12 21:38:05 leo>
 * 
 * @brief  The header for the Leaf class.
 * 
 * @see leaf.cpp
 */
#ifndef _LEAF_H_
#define _LEAF_H_

#include "bytetree.hpp"

namespace cppVerifier {
namespace utils {

/**
 * @class Leaf
 *
 * @brief Implements the ByteTree virtual class in the case when the
 * data stored is not other bytetrees but actual bytes.
 *
 * See the verificatum verifier documentation (page 5) for more
 * details on how this is supposed to work.
 *
 * @see btrtest.cpp
 */
class Leaf : public ByteTree
{
private:
        /**
         * @brief The actual bytes stored in this leaf.
         */
        std::vector<uint8_t> bytes;

public:
        /**
         * @brief Initializes the bytes attribute of a new Leaf
         * instance directly.
         * 
         * @param content The bytes this Leaf must contain.
         */
        Leaf(std::vector<uint8_t> content);
        
        /**
         * @brief Reads a leaf from a string containing a byte
         * representation and removes the data read from it.
         *
         * For s="010000000201010000000001AA", creates a leaf
         * containing two bytes equal to 1 and, at the end of the
         * call, s="010000000001AA". It is assumed that the string is
         * a correctly formatted Leaf. No verifications are made!
         * 
         * @param[in,out] s The string to parse. It must start with
         * "01".
         */
        Leaf(std::string &s);

        /**
         * @brief Turns a number into the Leaf of a ByteTree.
         * 
         * @param number The number to store in this leaf
         */
        Leaf(mpz_class number);

        /**
         * @brief Turns a number into the Leaf of a ByteTree with a
         * fixed number of bytes.
         * 
         * @param number The number to store in this leaf.
         * @param size The number of bytes which will actually be
         * stored.
         */
        Leaf(mpz_class number, unsigned int size);

        /** 
         * @brief Empties the content of bytes during destruction.
         */
        ~Leaf();

        // Those are inherited from ByteTree:

        std::string toString();
        
        std::vector<uint8_t> toVector();
        
        mpz_class toNumber();

        std::vector<bool> toBoolArray();
        
        bool isNode();
        
        bool isLeaf();
        
        mpz_class toInteger();
        
        unsigned int size();
        
        void prettyPrint(std::string indent);
        
        bool compare(std::string s);
};

}
} // closing namespace

#endif
