/**
 * @file   prodfield.hpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2013-04-01 21:54:28 leo>
 * 
 * @brief  The header of the ProdField class.
 * 
 * @see prodfield.cpp
 */


#ifndef _PRODFIELD_H_
#define _PRODFIELD_H_

#include "field.hpp"


namespace cppVerifier{
namespace arithm {

/**
 * @brief Implements the cartesian product of a finite number of
 * identical Fields.
 */
class ProdField : public Field
{
private:
        /// @brief The field this instance is the product of.
        Field * baseField;

        /// @brief The number of coordinates of the elements of this
        /// field.
        unsigned int width;
public:
        /** 
         * @brief Builds a ProdField instance by initialising all of
         * its attributes.
         * 
         * @param f The value to give to baseGroup.
         * @param w The width of this instance.
         */
        ProdField(Field * f, unsigned int w);

        // Bytetree
        Elmt * getElmt(utils::ByteTree * bt);
        utils::ByteTree * getByteTree(Elmt * e);

        // Basic operations
        Elmt * mult(Elmt * e1, Elmt * e2);
        Elmt * exp(Elmt * e, Elmt * s);
        Elmt * multInverse(Elmt * e);
        Elmt * add(Elmt * e1, Elmt * e2);
        Elmt * addInverse(Elmt * e);
        bool compare(Elmt * e1, Elmt * e2);

        // obtaining elements
        Elmt * getOne();
        Elmt * getZero();
        Elmt * getGenerator();

        // data about the group
        bool isIn(Elmt * e);
        std::string getType();
        unsigned int getWidth();

};

}
} // closing namespaces

#endif
