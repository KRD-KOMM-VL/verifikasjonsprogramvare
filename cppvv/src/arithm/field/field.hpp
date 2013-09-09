/**
 * @file   field.hpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2012-11-24 23:57:28 leo>
 * 
 * @brief  Contains the header of the Field class.
 * 
 * @see field.cpp
 */


#ifndef _FIELD_H_
#define _FIELD_H_

#include <gmp.h>
#include <gmpxx.h>

#include "../group/group.hpp"

namespace cppVerifier {
namespace arithm {

/**
 * @class Field
 * 
 * @brief Models a mathematical field with a given additive order,
 * multiplicative order, addition, multiplication, unit and zero.
 *
 * It is a virtual class providing an interface that has to be
 * implemented by every field used in verificatum.
 */
class Field : public Group
{
protected:
        /**
         * @brief The additive order (characteristic) of the field.
         */
        mpz_class addOrder;

public:
        /**
         * @brief Constructs a Field instance by setting only the
         * value of addOrder, generator and multOrder being set to 1
         * and 0.
         */
        Field(mpz_class aOrder);

        /**
         * @brief Constructs a Field instance by setting the values of
         * addOrder, multOrder and generator.
         */
        Field(mpz_class aOrder, mpz_class mOrder, Elmt * gen);


        /// @name Additive element operations
        /// @{

        /**
         * @brief Returns \f$e_1 + e_2\f$.
         */
        virtual Elmt * add(Elmt * e1, Elmt * e2);

        /** 
         * @brief Returns \f$-e\f$.
         */
        virtual Elmt * addInverse(Elmt * e);

        /**
         * @brief Returns \f$e_1 - e_2\f$.
         */
        virtual Elmt * soust(Elmt * e1, Elmt * e2);

        /// @}

        /// @name Additive array operations
        /// @{

        /** 
         * @brief Returns \f$R ~|~ R_i = e_{1,i} + e_{2,i}\f$. If the
         * sizes mismatch, exits with exitcode 1.
         */
        CollectionOfElmts add(CollectionOfElmts e1, CollectionOfElmts e2);
        
        /** 
         * @brief Returns \f$R ~|~ R_i = (-1) \times a_i\f$ as an
         * array of elements of this field.
         */
        CollectionOfElmts addInverse(CollectionOfElmts a);

        /** 
         * @brief Returns \f$R ~|~ R_i = e_{1,i} - e_{2,i}\f$. If the
         * sizes mismatch, exits with exitcode 1.
         */
        CollectionOfElmts soust(CollectionOfElmts e1, CollectionOfElmts e2);

        /** 
         * @brief Returns \f$s = \sum a_i\f$ as an element of this
         * field.
         */
        Elmt * sum(CollectionOfElmts a);

        /// @}

        /// @name Obtaining field elements
        /// @{

        /**
         * @brief Returns the neutral element for addition.
         */
        virtual Elmt * getZero();

        /**
         * @brief Returns an array of field elements containging n
         * copies of the neutral element for addition.
         */
        CollectionOfElmts getZero(unsigned int n);
        
        /// @}

        /// @name Data about the field.
        /// @{

        /**
         * @return The addOrder attribute.
         */
        mpz_class getAddOrder();

        virtual std::string getType();  

        /// @}
};


}
} // closing namespace

#endif
