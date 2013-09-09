/**
 * @file   psubgroup.cpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2012-11-18 22:55:27 leo>
 * 
 * @brief  The source code of the PSubGroup class.
 * 
 * @see psubgroup.hpp
 */


#include "psubgroup.hpp"

using namespace cppVerifier;
using namespace cppVerifier::arithm;


PSubGroup::PSubGroup(Group * bgrp, mpz_class order, Elmt * gen) :
        Group(order, gen)
{
        baseGroup = bgrp;
        coOrder = baseGroup->getMultOrder() / order;
        if (!isIn(generator))
        {
                std::cout<<"ERROR: in PSubGroup(bgrp, order, gen), the "
                         <<"generator is not of the correct order."
                         <<"\ngen="<<gen->getValue().get_str(16)
                         <<"\norder="<<order.get_str(16)<<std::endl;
        }
}



// =========================== BYTETREE ===============================

Elmt * PSubGroup::getElmt(utils::ByteTree * bt)
{
        return baseGroup->getElmt(bt);
}


utils::ByteTree * PSubGroup::getByteTree(Elmt * e)
{
        return baseGroup->getByteTree(e);
}


// ========================== OPERATIONS ==============================


Elmt * PSubGroup::mult(Elmt * e1, Elmt * e2)
{
        return baseGroup->mult(e1,e2);
}


Elmt * PSubGroup::multInverse(Elmt * e)
{
        return baseGroup->multInverse(e);
}


Elmt * PSubGroup::exp(Elmt * e, Elmt * s)
{
        return baseGroup->exp(e,s);
}


bool PSubGroup::compare(Elmt * e1, Elmt * e2)
{
        return baseGroup->compare(e1,e2);
}

// =========================== ELEMENTS ===============================


Elmt * PSubGroup::getOne()
{
        return baseGroup->getOne();
}


// ============================= DATA =================================


bool PSubGroup::isIn(Elmt * r)
{
        Elmt * trial = exp(r, new LargeNumber(multOrder));
        return compare(trial,getOne());
}


std::string PSubGroup::getType()
{
        return "PSubGroup of a " + baseGroup->getType();
}
