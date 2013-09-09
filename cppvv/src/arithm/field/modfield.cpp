/**
 * @file   modfield.cpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2013-04-12 23:55:08 leo>
 * 
 * @brief  The source code of the Elmt class.
 * 
 * @see modfield.hpp
 */


#include "modfield.hpp"

using namespace cppVerifier;
using namespace cppVerifier::arithm;


ModField::ModField(mpz_class aOrder) : 
        Field(
                aOrder,   // additive order
                aOrder-1, // multiplicative order
                new LargeNumber(2) // generator of the mult. subgroup
                )
{
        leafSize = (mpz_sizeinbase(addOrder.get_mpz_t(), 16)+1)/2;
}



// !SECTION! Bytetree
// ===================================================================


Elmt * ModField::getElmt(utils::ByteTree * bt)
{
        if (!bt->isLeaf())
        {
                throw utils::Exception("In ModField.getElmt(bt), bt is not"
                                       " a Leaf.\nbt=" + bt->toString() +"\n");
        }
        else
                return new LargeNumber(bt->toInteger());
}


utils::ByteTree * ModField::getByteTree(Elmt * e)
{
        try {
                return new utils::Leaf(e->getValue(), leafSize);
        }
        catch(utils::Exception e) {
                utils::treatException("In ModField.getByteTree.\n",e);
                return NULL;
        }
}


// !SECTION! Operations
// ===================================================================


Elmt * ModField::mult(Elmt * e1, Elmt * e2)
{
        return new LargeNumber((e1->getValue() * e2->getValue()) % addOrder);
}


Elmt * ModField::multInverse(Elmt * e)
{
        mpz_class inverseValue;
        mpz_invert(inverseValue.get_mpz_t(),
                   e->getValue().get_mpz_t(),
                   addOrder.get_mpz_t());
        return new LargeNumber(inverseValue);
}


Elmt * ModField::exp(Elmt * e, Elmt * s)
{
        if (!e->isLargeNumber())
        {
                throw utils::Exception("In ModField.exp(e,s): e is not a "
                                       "number.");
        }
        if (!s->isLargeNumber())
        {
                throw utils::Exception("In ModField.exp(e,s): s is not a "
                                       "number.");
        }
        mpz_class expValue;
        mpz_powm(expValue.get_mpz_t(),
                 e->getValue().get_mpz_t(),
                 s->getValue().get_mpz_t(),
                 addOrder.get_mpz_t());
        return new LargeNumber(expValue);
}


Elmt * ModField::add(Elmt * e1, Elmt * e2)
{
        return new LargeNumber((e1->getValue() + e2->getValue()) % addOrder);
}


Elmt * ModField::addInverse(Elmt * e)
{
        return new LargeNumber(addOrder - e->getValue());
}


bool ModField::compare(Elmt * e1, Elmt * e2)
{
        return (e1->getValue() == e2->getValue());
}


// !SECTION! Elements
// ===================================================================


Elmt * ModField::getZero()
{
        return new LargeNumber(mpz_class(0));
}


Elmt * ModField::getOne()
{
        return new LargeNumber(mpz_class(1));
}


// !SECTION! Data
// ===================================================================


std::string ModField::getType()
{
        return "ModField";
}
