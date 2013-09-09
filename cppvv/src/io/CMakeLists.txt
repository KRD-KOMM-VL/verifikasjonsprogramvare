CMAKE_MINIMUM_REQUIRED(VERSION 2.6)


PROJECT(${LIB_IO_NAME})

# tinyxml is used by xmlconfig
ADD_LIBRARY(tinyxml
  tinyxml2/tinyxml2.cpp
)


# the files we are interested in
ADD_LIBRARY(${PROJECT_NAME}
  log.cpp
  xmlreader.cpp
  arguments.cpp
  )

# the dependencies
TARGET_LINK_LIBRARIES(
  ${PROJECT_NAME}
  gmp
  gmpxx
  tinyxml
  ${LIB_ARITHM_NAME}
  )
