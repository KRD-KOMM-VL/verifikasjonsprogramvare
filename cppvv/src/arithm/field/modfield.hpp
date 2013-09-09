/**
 * @file   modfield.hpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2013-04-12 23:54:10 leo>
 * 
 * @brief  The header of the ModField class.
 * 
 * @see modfield.cpp
 */


#ifndef _MODFIELD_H_
#define _MODFIELD_H_

#include "field.hpp"

namespace cppVerifier {
namespace arithm {


/**
 * @brief Implements a modular field \f$Z_q\f$ (i.e. where \f$q\f$ is
 * prime).
 */
class ModField : public Field
{
// !SECTION! Private
private:
        /**
         * @brief The leaf size necessary used to turn this field and
         * its elements into bytetrees.
         */
        unsigned int leafSize;

// !SECTION! Public
public:
        /**
         * @brief Assigns addOrder to aOrder; assumes it is prime and
         * thus assigns multOrder to addOrder-1. Generator gets
         * assigned to 2.
         */
        ModField(mpz_class aOrder);

        // !SUBSECTION! Bytetree functions
        Elmt * getElmt(utils::ByteTree * bt);        
        utils::ByteTree * getByteTree(Elmt * e);

        // !SUBSECTION! Operations
        Elmt * mult(Elmt * e1, Elmt * e2);
        Elmt * multInverse(Elmt * e);
        Elmt * exp(Elmt * e, Elmt * s);
        Elmt * add(Elmt * e1, Elmt * e2);
        Elmt * addInverse(Elmt * e);
        bool compare(Elmt * e1, Elmt * e2);

        // !SUBSECTION! Elements
        Elmt * getZero();
        Elmt * getOne();

        // !SUBSECTION! Data about the field
        std::string getType();
};

}
} // closing namespaces


#endif
