/**
 * @file   exception.hpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2013-04-09 15:25:48 leo>
 * 
 * @brief The header and the inline source code of the Exception class.
 */


#ifndef _MY_EXCEPTION_H_
#define _MY_EXCEPTION_H_

#include <exception>
#include <iostream>
#include <string>


namespace cppVerifier {
namespace utils {

/**
 * @brief A very simple class to handle errors by assigning to each a
 * basic description.
 */
class Exception : public std::exception
{
private:
        /// @brief A short description of the exception.
        std::string description;

public:
        /// @brief Constructs a very simple exception by setting its
        /// description.
        Exception(std::string desc)  throw()
        {
                description = desc;
        }

        /**
         * @brief Constructs a very simple exception by setting its
         * description using the content of two strings.
         *
         * The idea is to be able to very simply concatenate two error
         * messages.
         */
        Exception(std::string desc1, std::string desc2)  throw()
        {
                description = std::string(desc1) + std::string(desc2);
        }

        /// @brief Empty destructor.
        ~Exception() throw() { }

        /// @brief Returns the content of the description.
        const char * what() const throw()
        {
                return description.c_str();
        }
};


/**
 * @brief Throws an exception containing the string desc1 appended
 * before the e.what() of the exception e.
 *
 * It is used to chain throws: if an exception e is caught, we can add
 * the short description desc1 to make its context clearer.
 */
        inline void treatException(std::string desc1, std::exception &e)
{
        std::cerr<<desc1<<std::endl;
        throw Exception((desc1 + "\n").c_str(),e.what());
}


}
} // closing namespaces

#endif
