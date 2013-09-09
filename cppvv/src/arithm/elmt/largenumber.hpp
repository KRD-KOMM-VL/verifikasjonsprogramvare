/**
 * @file   largenumber.hpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2012-12-20 21:35:53 leo>
 * 
 * @brief  The header of the LargeNumber class.
 * 
 * @see largenumber.cpp
 */


#ifndef _LARGENUMBER_H_
#define _LARGENUMBER_H_


#include "elmt.hpp"


namespace cppVerifier {
namespace arithm {

/**
 * @brief Implements arbitrarily large numbers using gmp. This type of
 * element has a value but no coordinates.
 */
class LargeNumber : public Elmt
{
private:
        /// @brief The value of this instance.
        mpz_class value;
public:
        /// @brief Constructs a LargeNumber from a number.
        /// 
        /// @param v What the value attribute will be assigned to.
        LargeNumber(mpz_class v);

        Elmt * copy();
        mpz_class getValue();
        bool isLargeNumber();
        void prettyPrint(std::string indent);
};

}
} // closing namespaces

#endif
