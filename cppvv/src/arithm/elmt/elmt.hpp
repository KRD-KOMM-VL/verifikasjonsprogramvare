/**
 * @file   elmt.hpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2013-04-09 15:18:20 leo>
 * 
 * @brief  The header of the Elmt class.
 * 
 * @see elmt.cpp
 */


#ifndef _ELMT_H_
#define _ELMT_H_


#include <cstdlib>
#include <iostream>
#include <string>
#include <gmp.h>
#include <gmpxx.h>

#include "../../utils/utils.hpp"
#include "collectionofelmts.hpp"



namespace cppVerifier {
namespace arithm {

        class CollectionOfElmts;

/**
 * @brief Implements an element of any group.
 */
class Elmt
{
public:
        /// @brief Empty constructor to make the compiler happy.
        Elmt();

        /// @brief Returns a new copy of this instance.
        virtual Elmt * copy();

        /// @brief If this instance has a numerical value, returns it.
        virtual mpz_class getValue();

        /**
         * @brief If this Elmt is a ProductGroupElmt instance, returns
         * the coordinate at the given position (the said coordinate
         * can be set too).
         *
         * If there is no such coordinate, exits with exitcode 1.
         */
        virtual Elmt  * & getCoord(unsigned int position);

        /**
         * @brief Returns the number of Elmts in this instance
         * (i.e. the width of a ProdGrpElmt or 0 if it is a
         * LargeNumber).
         */
        virtual unsigned int getWidth();

        /**
         * @brief Returns true if this instance is a LargeNumber,
         * false otherwise.
         */
        virtual bool isLargeNumber();

        /**
         * @brief Returns true if this instance is a ProdGrpElmt false
         * otherwise.
         */
        virtual bool isProdGrpElmt();

        /**
         * @brief Prints on the screen a nice indented version of this
         * element where numbers are represented using hexadecimals.
         */
        virtual void prettyPrint(std::string indent);

        /// @brief Returns an array of Elmts containing the element in
        /// this instance, provided that this instance is a
        /// ProdGrpElmt.
        virtual CollectionOfElmts toCollection();
};


}
} // closing namespace

#endif
