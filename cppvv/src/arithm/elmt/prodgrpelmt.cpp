/**
 * @file   prodgrpelmt.cpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2013-04-09 15:17:40 leo>
 * 
 * @brief  The source code of the ProdGrpElmt class.
 * 
 * @see prodgrpelmt.hpp
 */


#include "prodgrpelmt.hpp"
#include "collectionofelmts.hpp"

using namespace cppVerifier;
using namespace cppVerifier::arithm;


ProdGrpElmt::ProdGrpElmt(unsigned int w)
{
        content.resize(w);
        for (unsigned int i=0; i<w; i++)
                content[i] = NULL;
}


ProdGrpElmt::ProdGrpElmt(Elmt * e)
{
        content.push_back(e);
}


ProdGrpElmt::ProdGrpElmt(Elmt * e1, Elmt * e2)
{
        content.resize(2);
        content[0] = e1;
        content[1] = e2;
}


Elmt * ProdGrpElmt::copy()
{
        Elmt * result = new ProdGrpElmt(this->getWidth());
        for (unsigned int i=0; i<content.size(); i++)
                result->getCoord(i) = content[i]->copy();
        return result;
}


Elmt * & ProdGrpElmt::getCoord(unsigned int position)
{
        return content[position];
}


unsigned int ProdGrpElmt::getWidth()
{
        return content.size();
}


bool ProdGrpElmt::isProdGrpElmt()
{
        return true;
}

void ProdGrpElmt::prettyPrint(std::string indent)
{
        std::cout<<indent<<"(\n";
        for (unsigned int i=0; i<content.size(); i++)
                content[i]->prettyPrint(indent+"  ");
        std::cout<<indent<<")\n";
}


CollectionOfElmts ProdGrpElmt::toCollection()
{
        CollectionOfElmts result;
        for (unsigned int i=0; i<content.size(); i++)
                result.addElmt(content[i]);
        return result;
}
