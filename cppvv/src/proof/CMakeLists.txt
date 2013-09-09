CMAKE_MINIMUM_REQUIRED(VERSION 2.6)


PROJECT(${LIB_PROOF_NAME})

# the main library
ADD_LIBRARY(${PROJECT_NAME}
  algorithm_17.cpp # Proof of shuffle
  algorithm_18.cpp # Verifier of shuffle
  algorithm_19.cpp # Verifier
  )

# the dependencies
TARGET_LINK_LIBRARIES(
  ${PROJECT_NAME}
  ${LIB_UTILS_NAME}
  ${LIB_CRYPTO_NAME}
  ${LIB_IO_NAME}
  ${LIB_ARITHM_NAME}
  )



