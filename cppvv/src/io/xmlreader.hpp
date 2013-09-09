/**
 * @file   xmlreader.hpp
 * @author Léo Perrin
 * @date   Time-stamp: <2013-03-15 16:43:53 leo>
 *
 * @brief Header for the XmlReader class.
 *
 * @see xmlreader.cpp
 */

#ifndef _XMLREADER_H_
#define _XMLREADER_H_

#include <string>
#include <cstdlib>
#include <iostream>

#include "../utils/utils.hpp"

#include "./tinyxml2/tinyxml2.h"


namespace cppVerifier {
namespace io {

/**
 * @class XmlReader
 *
 * @brief Encapsulates the <a
 * href="https://github.com/leethomason/tinyxml2">TinyXml2</a> library
 * to retrieve easily information from the xml configuration file
 * described in the verificatum verifier specification.
 */
class XmlReader
{
private:
        /// @brief A class from the tinyxml2 library used to store a
        /// xml document and access it.
        tinyxml2::XMLDocument doc;

public:
        /**
         * @brief Class contructor.
         *
         * @param path2file The path to the file to parse.
         */
	XmlReader(std::string path2file);

        /**
         * @brief Returns the text at the node whose name is given as
         * an argument. If there is no such node, stops everything by
         * exiting with code 1.
         *
         * @param node The name of the node we are interested in.
         *
         * @return A string containing the content of the text of
         * this node.
         */
        std::string retrieveText(std::string node);

        /**
         * @brief Gets the content of the node specified and returns
         * its value as an integer.
         *
         * @param node The name of the node we want to read.
         *
         * @return The integer contained in the node node. If it is
         * not an integer, returns zero (uses the atoi function from
         * the C standard library).
         */
        int retrieveInteger(std::string node);
};

}
} // closing namespaces

#endif
