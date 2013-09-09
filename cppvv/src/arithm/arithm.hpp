/**
 * @file   arithm.hpp
 * @author Léo Perrin
 * @date   Time-stamp: <2013-09-03 22:56:03 leo>
 * 
 * @brief Allows other parts of the program to include only one file
 * when using classes from the arithm library.
 */
#ifndef _ARITHM_H_
#define _ARITHM_H_

// iostream should be included before gmp
#include <iostream>

// These classes all use gmp intensively
#include <gmpxx.h>
#include <gmp.h>

// We need integers of known size
#include <cstdint>

// These are always useful
#include <vector>

// The files we are actually interested in.
#include "elmt/elmt.hpp"
#include "elmt/largenumber.hpp"
#include "elmt/prodgrpelmt.hpp"
#include "elmt/collectionofelmts.hpp"
#include "group/group.hpp"
#include "group/prodgrp.hpp"
#include "field/field.hpp"
#include "field/prodfield.hpp"
#include "field/modfield.hpp"
#include "psubgroup/psubgroup.hpp"
#include "psubgroup/modpgrp.hpp"
#include "elgamal/elgamal.hpp"



namespace cppVerifier
{

/**
 * @namespace cppVerifier::arithm 
 * 
 * @brief Implements useful algebraic structures such as groups,
 * fields, p-subgroups, etc.
 *
 * The simplest classes in this module are Elmt and
 * CollectionOfElmts. These are used to store and read arbitrarily
 * large integers from bytetrees (class LargeNumber), elements of
 * product groups (class ProdGrpElmt) and collections of such elements
 * (CollectionOfElmts).
 *
 * Then, there are algebraic structures; namely (multiplicative)
 * Group, Field...). These provide operations as they are performed in
 * these mathematical objects over Elmt's.
 *
 * An Elmt is essentially a tree with large numbers as its
 * leaves. Large integers are provided by mpz_class instances from the
 * <a
 * href="http://gmplib.org/manual/C_002b_002b-Interface-Integers.html">gmp
 * library</a>).
 *
 * If an element is an element of a product group (i.e. a
 * ProdGrpElmt), then operations are performed componentwise.
 **/
namespace arithm
{
        /**
         * @brief Returns the Group encoded in the string.
         *
         * A group representation is a string which must contain the
         * name of the class, then parenthesis containing human
         * readable data we are not interested in, then two ':' and
         * then the bytetree representation of the group:
         * 
         * < class name>(...)::< bytetree representation of the group>
         *
         * Depending on < class name>, the correct Group child is
         * instanciated with its constructor taking a
         * utils::ByteTree as its input.
         * 
         *
         * @param repr The string representing the Group.
         */
        Group * unmarshal(std::string repr);
        
        /** 
         * @brief Returns the Group implementation whose name is
         * encoded in the bytetree.
         *
         * @throw Throws an exception if group name is not recognized
         * of if the elliptic curve type is unkown.
         * 
         * @param bt The bytetree representation of a string
         * containing the name of a group and the ByteTree
         * representation of the Group implementation itself.
         * 
         * @return An instance of the Group implementation wanted.
         */
        Group * getGroupFromByteTree(utils::ByteTree * bt);
}

}
        
#endif
