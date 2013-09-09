/**
 * @file   elmt.cpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2013-04-09 15:18:33 leo>
 * 
 * @brief  The source code of the Elmt class.
 * 
 * @see elmt.hpp
 */


#include "elmt.hpp"


using namespace cppVerifier;
using namespace cppVerifier::arithm;


Elmt::Elmt()
{        
}


Elmt * Elmt::copy()
{
        return NULL;
}


mpz_class Elmt::getValue()
{
        throw utils::Exception(
                "In Elmt.getValue(): trying to access the value of a "
                "non-LargeNumber Elmt instance."
                );
}


Elmt * & Elmt::getCoord(unsigned int position)
{
        throw utils::Exception(
                "In Elmt.getCoord(): trying to access a coordinate of a"
                " non-ProdGrpElmt Elmt instance."
                );
}


unsigned int Elmt::getWidth()
{
        return 0;
}


bool Elmt::isLargeNumber()
{
        return false;
}


bool Elmt::isProdGrpElmt()
{
        return false;
}

void Elmt::prettyPrint(std::string indent)
{
}


CollectionOfElmts Elmt::toCollection()
{
        return CollectionOfElmts();
}
