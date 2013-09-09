/**
 * @file   prodgrpelmt.hpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2013-04-09 15:19:08 leo>
 * 
 * @brief  The header of the ProdGrpElmt class.
 * 
 * @see prodgrpelmt.cpp
 */


#ifndef _PRODGRPELMT_H_
#define _PRODGRPELMT_H_

#include <vector>

#include "elmt.hpp"


namespace cppVerifier {
namespace arithm {


/**
 * @brief Implements an element of a product group, i.e. a tuple of
 * Elmt's of fixed width.
 *
 * Note that these elements may very well be ProdGrpElmt's themselves.
 */
class ProdGrpElmt : public Elmt
{
private:
        /// @brief The Coordinates of this ProdGrpElmt; its size is
        /// the width of the Elmt.
        std::vector<Elmt *> content;        

public:
        /// @brief Constructs an instance of width w.
        ProdGrpElmt(unsigned int w);

        /// @brief Constructs a ProdGrpElmt of width 1 and sets the
        /// unique element.
        ProdGrpElmt(Elmt * e);

        /// @brief Construcs a ProdGrpElmt of width 2 and sets its two
        /// coordinates.
        ProdGrpElmt(Elmt * e1, Elmt * e2);

        Elmt * copy();
        Elmt * & getCoord(unsigned int position);
        unsigned int getWidth();
        bool isProdGrpElmt();
        void prettyPrint(std::string indent);
        virtual CollectionOfElmts toCollection();
};

}
} // closing namespaces

#endif
