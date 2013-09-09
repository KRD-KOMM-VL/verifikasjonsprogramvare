/**
 * @file   psubgroup.hpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2012-11-18 18:52:07 leo>
 * 
 * @brief  The header of the PSubGroup class.
 * 
 * @see psubgroup.cppp
 */


#ifndef _PSUBGROUP_H_
#define _PSUBGROUP_H_


#include "../group/group.hpp"


namespace cppVerifier {
namespace arithm {


/**
 * @brief Implements a p-subgroup (as defined in Sylow theory) of any
 * multiplicative group.
 */
class PSubGroup : public Group
{
protected:
        /**
         * @brief The group this instance is a p-subgroup of.
         */
        Group * baseGroup;

        /**
         * @brief The order of baseGroup divided by that of this one.
         */
        mpz_class coOrder;

public:
        /** 
         * @brief Initializes a p-subgroup instance
         * 
         * @param bgrp  The group this instance is a subgroup of.
         * @param order The order of this subgroup.
         * @param gen   A generator of this subgroup.
         */
        PSubGroup(Group * bgrp, mpz_class order, Elmt * gen);

        // bytetree
        Elmt * getElmt(utils::ByteTree * bt);        
        utils::ByteTree * getByteTree(Elmt * e);        
        
        // operations
        Elmt * mult(Elmt * e1, Elmt * e2);
        Elmt * multInverse(Elmt * e);
        Elmt * exp(Elmt * e, Elmt * s);
        bool compare(Elmt * e1, Elmt * e2);
        // elements
        Elmt * getOne();

        // data
        /**
         * @brief Returns true if and only if \f$r^{order}\f$ is equal
         * to 1.
         */
        bool isIn(Elmt * r);        
        std::string getType();
};

}
} // closing namespace


#endif
