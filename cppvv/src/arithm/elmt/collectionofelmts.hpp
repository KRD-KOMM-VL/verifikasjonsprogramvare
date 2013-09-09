/**
 * @file   collectionofelmts.hpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2013-03-15 22:02:13 leo>
 * 
 * @brief  The header of the CollectionOfElmts class.
 * 
 * @see collectionofelmts.hpp
 */


#ifndef _ARRAYOFELMTS_H_
#define _ARRAYOFELMTS_H_

#include <vector>
#include <iostream>
#include "elmt.hpp"


namespace cppVerifier {
namespace arithm {

class Elmt;

/**
 * @brief Implements an collection of group elements, a simple class
 * to contain group elements.
 *
 * Semantical note: we use the term "collection" because this class is
 * used to implement to concepts describe in the specification, namely
 * product group elements and array of elements. Hence, we did not
 * want to use either term.
 */
class CollectionOfElmts
{
private:        
        /** 
         * @brief The actual content of this array.
         */
        std::vector<Elmt*> elmts;
        
public:
        /** 
         * @brief Creates an empty instance
         */
        CollectionOfElmts();

        /**
         * @brief Returns an collectionOfElmts \f$R\f$ built from this
         * instance \f$A\f$ such that \f$A_i \in R\f$ if and only if
         * the ith element of the keep list is set to true.
         */
        CollectionOfElmts subArray(std::vector<bool> keepList);
        
        /// @brief Appends an element to the end of the collection.
        void addElmt(Elmt * e);

        /// @brief Returns the ith element of the collection.
        Elmt * operator[](unsigned int i);

        
        /// @brief Returns the size of this collection.
         unsigned int size();

        /// @brief Returns a ProdGrpElmt of the same size and
        /// containing the same elements.
        Elmt * toElmt();
};


}
} // closing namespace

#endif
