/**
 * @file   largenumber.cpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2012-12-20 21:35:42 leo>
 * 
 * @brief  The source code of the LargeNumber class.
 * 
 * @see largenumber.hpp
 */


#include "largenumber.hpp"

using namespace cppVerifier;
using namespace cppVerifier::arithm;


LargeNumber::LargeNumber(mpz_class v)
{
        value = v;
}


Elmt * LargeNumber::copy()
{
        return new LargeNumber(this->getValue());
}


mpz_class LargeNumber::getValue()
{
        return value;
}


bool LargeNumber::isLargeNumber()
{
        return true;
}


void LargeNumber::prettyPrint(std::string indent)
{
        std::cout<<indent<<value<<"\n";
}
