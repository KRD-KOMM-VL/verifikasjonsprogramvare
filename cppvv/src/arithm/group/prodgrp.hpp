/**
 * @file   prodgrp.hpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2013-09-07 16:27:30 leo>
 * 
 * @brief  The header of the ProdGrp class.
 * 
 * @see prodgrp.cpp
 */


#ifndef _PRODGRP_H_
#define _PRODGRP_H_

#include "group.hpp"


namespace cppVerifier{
namespace arithm {

/**
 * @brief Implements the cartesian product of a finite number of
 * identical groups.
 */
class ProdGrp : public Group
{

// !SECTION! Private attributes
private:
        /// @brief The group this instance is the product of.
        Group * baseGroup;

        /// @brief The number of coordinates of the elements of this
        /// group.
        unsigned int width;


// !SECTION! Public attributes
public:
        /** 
         * @brief Builds a ProdGrp instance by initialising all of its attributes.
         * 
         * @param b The value to give to baseGroup.
         * @param w The width of this instance.
         */
        ProdGrp(Group * b, unsigned int w);

        // !SUBSECTION! Bytetree
        Elmt * getElmt(utils::ByteTree * bt);
        utils::ByteTree * getByteTree(Elmt * e);
        utils::ByteTree * getByteTree(CollectionOfElmts c);
        CollectionOfElmts getCiphers(utils::ByteTree * bt);

        // !SUBSECTION! Basic operations
        Elmt * mult(Elmt * e1, Elmt * e2);

        /**
         * @brief If \f$s\f$ is a number, then returns
         * \f$(e_1^s,...,e_{\omega}^s)\f$. Otherwise, returns
         * \f$(e_1^{s_1},...,e_{\omega}^{s_{\omega}})\f$.
         */
        Elmt * exp(Elmt * e, Elmt * s);
        Elmt * multInverse(Elmt * e);
        bool compare(Elmt * e1, Elmt * e2);

        // !SUBSECTION! Obtaining elements
        Elmt * getOne();
        Elmt * getGenerator();

        // !SUBSECTION! Data about the group
        bool isIn(Elmt * e);
        std::string getType();
        unsigned int getWidth();

};

}
} // closing namespaces

#endif
