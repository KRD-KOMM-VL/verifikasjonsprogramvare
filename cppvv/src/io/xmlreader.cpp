/**
 * @file   xmlreader.cpp
 * @author Léo Perrin
 * @date   Time-stamp: <2013-03-15 17:12:48 leo>
 * 
 * @brief  Contains the source code of the XmlReader class.
 * 
 * @see xmlreader.h
 */

#include <cstring>

#include "xmlreader.hpp"


using namespace cppVerifier;
using namespace cppVerifier::io;



XmlReader::XmlReader(std::string path2file)
{
        doc.LoadFile(path2file.c_str());

        // Checking if we could open it; otherwise we stop everything.
        if(doc.Error())
                throw utils::Exception("In XmlReader(): no such file.");
}

        

std::string XmlReader::retrieveText(std::string node)
{
        tinyxml2::XMLElement * elem;
        try
        {
                elem = doc.FirstChildElement("protocol")->FirstChildElement(node.c_str());
                std::string
                        result = "",
                        query(elem->GetText());

                // We remove new lines from the string
                for (unsigned int i=0; i<query.size(); i++)
                        if (query[i] != '\n')
                                result.push_back(query[i]);
                return result;
        }
        catch (void * e)
        {
                throw utils::Exception("In XmlReader.retrieveText("+node+"): "
                                       "an error as occurred while reading.");
        }
}
        

int XmlReader::retrieveInteger(std::string node)
{
        int result;
        try {
                result = atoi(retrieveText(node).c_str());
        } catch(utils::Exception exc) {
                utils::treatException("In XmlReader.retrieveInteger().\n",exc);
        }
        return result;
}


