/**
 * @file   group.hpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2013-05-06 11:31:43 leo>
 * 
 * @brief  The header of the Group class.
 * 
 * @see group.cpp
 */

#ifndef _GROUP_H_
#define _GROUP_H_


#include <vector>
#include <cstdint>
#include <iostream>
#include <gmp.h>
#include <gmpxx.h>

#include "../../utils/utils.hpp"
#include "../../crypto/crypto.hpp"

#include "../elmt/elmt.hpp"
#include "../elmt/largenumber.hpp"
#include "../elmt/prodgrpelmt.hpp"
#include "../elmt/collectionofelmts.hpp"


namespace cppVerifier {
namespace arithm {


/**
 * @brief Models a mathematical multiplicative group.
 */
class Group
{

// !SECTION! Protected attributes
protected:
        /** 
         * @brief The order of this multiplicative group.
         */
        mpz_class multOrder;

        /** 
         * @brief The generator to use for this group.
         */
        Elmt * generator;


// !SECTION! Public attributes
public:
        /** 
         * @brief Sets the attributes of a new group instance.
         * 
         * @param order The value to give to multOrder
         * @param gen   The value to give to generator
         */
        Group(mpz_class order, Elmt * gen);


        // !SUBSECTION! ByteTree operations
        /// @name ByteTree operations
        /// @{

        /**
         * @brief Returns the bytetree representation of this group.
         */
        virtual utils::ByteTree * toByteTree();

        /** 
         * @brief Returns the element of this group which bt as a
         * bytetree representation.
         */
        virtual Elmt * getElmt(utils::ByteTree * bt);        

        /**
         * @brief  Returns the bytetree representation of the element.
         */
        virtual utils::ByteTree * getByteTree(Elmt * e);

        /**
         * @brief Returns the bytetree representation of the
         * collection of elements.
         */
        virtual utils::ByteTree * getByteTree(CollectionOfElmts c);

        /** 
         * @brief Returns the array of elements of this group which
         * has bt as a bytetree representation.
         */
        CollectionOfElmts getCollection(utils::ByteTree * bt);

        /** 
         * @brief Returns the array of ciphers \f$(u,v)\f$ where
         * \f$(u,v)\f$ is in this group.
         *
         * bt is supposed to have 2 children, both of which are arrays
         * of elmts in the base group of a prodgrp elmts.
         *
         * @throw If bt is not a Node, is not of size 2 or if its two
         * children are not of the same size, throws an exception.
         */
        virtual CollectionOfElmts getCiphers(utils::ByteTree * bt);


        /// @}
        // !SUBSECTION! Element operations
        /// @name Element operations
        /// @{

        /**
         * @brief Returns the product of the two elements.
         */
        virtual Elmt * mult(Elmt * e1, Elmt * e2);

        /**
         * @brief Returns the square of the element.
         */
        virtual Elmt * square(Elmt * e);

        /** 
         * @brief Returns \f$e^{-1}\f$ as an element of this group.
         */
        virtual Elmt * multInverse(Elmt * e);

        /**
         * @brief Returns the quotient of the two elements.
         */
        virtual Elmt * div(Elmt * e1, Elmt * e2);

        /** 
         * @brief Returns the \f$e^s\f$ as an element of this group (s
         * has to be a LargeNumber).
         *
         * Provides a good implementation of fast exponentiation using
         * the square function but it can be over-ridden in classes
         * inheriting from this one.
         */
        virtual Elmt * exp(Elmt * e, Elmt * s);

        /**
         * @brief Returns true if e1 and e2 have identical values,
         * false otherwise.
         */
        virtual bool compare(Elmt * e1, Elmt * e2);


        /// @}
        // !SUBSECTION! Array operations
        /// @name Array operations
        /// @{

        /**
         * @brief Returns \f$R ~|~ R_i = e_{1,i} \times e_{2,i}\f$ as
         * an array of elements of this group.
         */
        CollectionOfElmts mult(CollectionOfElmts e1, CollectionOfElmts e2);

        /**
         * @brief Returns \f$R ~|~ R_i = e_{i} \times e_{i}\f$ as
         * a collection of elements of this group.
         */
        CollectionOfElmts square(CollectionOfElmts e);

        /** 
         * @brief Returns \f$R ~|~ R_i = e_i^{-1}\f$ as a collection
         * of elements.
         */
        CollectionOfElmts multInverse(CollectionOfElmts e);

        /**
         * @brief Returns \f$R ~|~ R_i = e_{1,i} \times
         * e_{2,i}^{-1}\f$ as a collection of elements.
         */
        CollectionOfElmts div(CollectionOfElmts e1, CollectionOfElmts e2);

        /** 
         * @brief Returns \f$R ~|~ R_i = e_i^{s_i}\f$ as an array of
         * elements of this group.
         */
        CollectionOfElmts exp(CollectionOfElmts e, CollectionOfElmts s);

        /** 
         * @brief Returns \f$r = \prod e_i\f$ as an element of this
         * group.
         */
        Elmt * product(CollectionOfElmts e);

        /** 
         * @brief Returns \f$r = \prod_{i=0}^n e_i\f$ as an element of
         * this group or \f$r = \prod e_i\f$ if \f$n\f$ is greater
         * than the size of \f$e\f$.
         */
        Elmt * partialProduct(CollectionOfElmts e, unsigned int n);

        /** 
         * @brief Returns \f$r = \prod e_i^{s_i}\f$ as an element of
         * this group.
         */
        Elmt * expProduct(CollectionOfElmts e, CollectionOfElmts s);

        /** 
         * @brief Returns \f$r = \prod_{i=0}^n e_i^{s_i}\f$ as an
         * element of this group. If \f$n>e.size()\f$, returns the
         * same as expProduct.
         */
        Elmt * partialExpProduct(CollectionOfElmts e,
                                 CollectionOfElmts s, unsigned int n);

        /**
         * @brief Returns true if e1 and e2 have identical values
         * component-wise, false if at least one of the component is
         * different.
         */
        bool compare(CollectionOfElmts e1, CollectionOfElmts e2);


        /// @}
        // !SUBSECTION! Obtaining element
        /// @name Obtaining element
        /// @{

        /** 
         * @brief Returns an element containing the unit of this
         * group.
         */
        virtual Elmt * getOne();

        /** 
         * @brief Returns an array containing n copies of the unit of
         * this group.
         */
        CollectionOfElmts getOnes(unsigned int n);

        /** 
         * @brief Returns an array of elements of size n0 derived
         * using a prg.
         * 
         * @param prg The pseudo-random generator to use.
         * @param nr  The statistical distance to use.
         * @param n0  The size of the array.
         */
        virtual CollectionOfElmts getRandArray(
                crypto::PRG * prg, unsigned int nr, unsigned int n0);

        /// @}
        // !SUBSECTION! Data about the group
        /// @name Data about the group
        /// @{

        /// @brief Returns the multiplicative generator of this group.
        mpz_class getMultOrder();

        /// @brief Returns the multiplicative generator of this group
        /// as an element of this group.
        Elmt * getGenerator();

        /// @brief Returns an array containing n copies of the
        /// multiplicative order of this group.
        CollectionOfElmts getGenerators(unsigned int n);

        /// @brief Returns true if the element e is in this group.
        virtual bool isIn(Elmt * e);

        /// @brief Returns a string containing the name of this Group.
        virtual std::string getType();

        /// @brief Returns the width of the group.
        virtual unsigned int getWidth();
        
        /// @}

};

}
} // closing namespaces

#endif
