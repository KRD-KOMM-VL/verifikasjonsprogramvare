CMAKE_MINIMUM_REQUIRED(VERSION 2.6)

# the complete project
PROJECT(CPP-VERIFICATUM-VERIFIER)
SET(BIN_NAME cppvv)

# compiler options
ADD_DEFINITIONS(-Wall -pedantic -std=c++0x -O3 -g)

# We use boost for parsing options
FIND_PACKAGE( Boost COMPONENTS program_options REQUIRED)
INCLUDE_DIRECTORIES(${Boost_INCLUDE_DIR})

# the names of my modules
SET(LIB_CRYPTO_NAME crypto)
SET(LIB_UTILS_NAME utils)
SET(LIB_ARITHM_NAME arithm)
SET(LIB_IO_NAME io)
SET(LIB_PROOF_NAME proof)

# building the modules
ADD_SUBDIRECTORY(src/crypto)
ADD_SUBDIRECTORY(src/utils)
ADD_SUBDIRECTORY(src/arithm)
ADD_SUBDIRECTORY(src/io)
ADD_SUBDIRECTORY(src/proof)

# building main executable
ADD_EXECUTABLE(${BIN_NAME} src/main.cpp)
TARGET_LINK_LIBRARIES(
  ${BIN_NAME}
  ${LIB_CRYPTO_NAME}
  ${LIB_UTILS_NAME}
  ${LIB_ARITHM_NAME}
  ${LIB_IO_NAME}
  ${LIB_PROOF_NAME}
  ${Boost_LIBRARIES}
  )
  
