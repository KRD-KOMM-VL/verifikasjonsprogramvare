CMAKE_MINIMUM_REQUIRED(VERSION 2.6)


PROJECT(${LIB_CRYPTO_NAME})

# the main library
ADD_LIBRARY(${PROJECT_NAME}
  sha/shax.cpp
  sha/sha256.cpp
  sha/sha2_64bits.cpp
  sha/sha384.cpp
  sha/sha512.cpp
  prg/prg.cpp
  ro/ro.cpp
  crypto.cpp
  )

# the dependencies
TARGET_LINK_LIBRARIES(
  ${PROJECT_NAME}
  ${LIB_UTILS_NAME}
  )


# the tests for each class
ADD_EXECUTABLE(sha_test shatest.cpp)
ADD_EXECUTABLE(prg_test prgtest.cpp)
ADD_EXECUTABLE(ro_test  rotest.cpp)

# the dependencies
TARGET_LINK_LIBRARIES(sha_test ${PROJECT_NAME})
TARGET_LINK_LIBRARIES(prg_test ${PROJECT_NAME})
TARGET_LINK_LIBRARIES(ro_test  ${PROJECT_NAME})


