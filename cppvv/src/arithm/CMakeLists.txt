CMAKE_MINIMUM_REQUIRED(VERSION 2.6)


PROJECT(${LIB_ARITHM_NAME})

# the main library
ADD_LIBRARY(${PROJECT_NAME}
  elmt/elmt.cpp
  elmt/largenumber.cpp
  elmt/prodgrpelmt.cpp
  elmt/collectionofelmts.cpp
  group/group.cpp
  group/prodgrp.cpp
  field/field.cpp
  field/prodfield.cpp
  field/modfield.cpp
  psubgroup/psubgroup.cpp
  psubgroup/modpgrp.cpp
  elgamal/elgamal.cpp
  arithm.cpp
  )


# the tests for each class
ADD_EXECUTABLE(arithm_test arithmtest.cpp)

# the dependencies
TARGET_LINK_LIBRARIES(
  ${PROJECT_NAME}
  gmp
  gmpxx
  ${LIB_UTILS_NAME}
  ${LIB_CRYPTO_NAME}
  )

TARGET_LINK_LIBRARIES(
  arithm_test
  ${PROJECT_NAME}
  )
