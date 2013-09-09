/**
 * @file   log.hpp
 * @author Léo Perrin <leoperrin@picarresursix.fr>
 * @date   Time-stamp: <2013-03-15 14:07:11 leo>
 * 
 * @brief  The header of the Log class.
 * 
 * @see log.cpp
 */


#ifndef _LOG_H_
#define _LOG_H_


#include "../utils/utils.hpp"


namespace cppVerifier {
namespace io {

/**
 * @brief Prints useful information on stdout, a logfile or nowhere.
 */
class Log
{
private:
        /// @brief The stream to which data must be printed.
        std::ostream * output;
public:
        /**
         * @brief Constructs a Log instance.
         *
         * - If place is "stdout", prints on standard output.
         * - If place is "none", prints nowhere.
         * - Otherwise, create a file named place and prints in it.
         */
        Log(std::string place);

        /// @brief Appends logLine to the log.
        void write(std::string logLine);

        /// @brief Deletes the output attribute.
        ~Log();
        
};

}
} // closing namespaces

#endif
