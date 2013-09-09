/**
 * @file   collectionofelmts.cpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2013-04-09 15:35:25 leo>
 * 
 * @brief  The source code of the CollectionOfElmts class.
 * 
 * @see collectionofelmts.hpp
 */


#include "prodgrpelmt.hpp"
#include "collectionofelmts.hpp"


using namespace cppVerifier;
using namespace cppVerifier::arithm;


CollectionOfElmts::CollectionOfElmts()
{
}


CollectionOfElmts CollectionOfElmts::subArray(std::vector<bool> keepList)
{
        if (keepList.size() != elmts.size())
        {
                std::cout<<"ERROR: in CollectionOfElmts.subArray(keepList),"
                         <<" keepList and this are not of the same "
                         <<"size.\nthis.size()="<<elmts.size()
                         <<"\nkeepList.size()="<<keepList.size()
                         <<std::endl;
                exit(1);
        }
        CollectionOfElmts result;
        for (unsigned int i=0; i<keepList.size(); i++)
                if (keepList[i])
                        result.addElmt(elmts[i]);
        return result;
}


void CollectionOfElmts::addElmt(Elmt * e)
{
        elmts.push_back(e);
}


Elmt * CollectionOfElmts::operator[](unsigned int i)
{
        if (i < elmts.size())
                return elmts[i];
        else
        {
                std::cout<<"ERROR: in CollectionOfElmts["<<i<<"].\n"
                         <<i<<" is larger than the collection size, "
                         <<elmts.size()<<".\nAborting.";
                exit(1);
        }
}


unsigned int CollectionOfElmts::size()
{
        return elmts.size();
}


Elmt * CollectionOfElmts::toElmt()
{
        Elmt * result = new ProdGrpElmt(elmts.size());
        for (unsigned int i=0; i<elmts.size(); i++)
                result->getCoord(i) = elmts[i];
        return result;
}
