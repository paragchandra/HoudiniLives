cmake_minimum_required(VERSION 3.15.3)

project(HoudiniLivesNative)

set(MANAGED_DIR "${PROJECT_SOURCE_DIR}/../managed")

add_library(nativeliba SHARED
	com_example_managed_NativeLibA.h
	com_example_managed_NativeLibA.c
)

target_link_libraries(nativeliba log)

set_target_properties(nativeliba PROPERTIES
	LIBRARY_OUTPUT_DIRECTORY "${MANAGED_DIR}/app/jniLibs/${CMAKE_ANDROID_ARCH_ABI}"
)

add_custom_command(TARGET nativeliba POST_BUILD
	COMMAND ${CMAKE_COMMAND} -E copy "${MANAGED_DIR}/app/jniLibs/${CMAKE_ANDROID_ARCH_ABI}/libnativeliba.so" "${MANAGED_DIR}/app/src/main/assets/libnativeliba_${CMAKE_ANDROID_ARCH_ABI}.so"
)

add_library(nativelibb SHARED
	com_example_managed_NativeLibB.h
	com_example_managed_NativeLibB.c
)

target_link_libraries(nativelibb log)

set_target_properties(nativelibb PROPERTIES
	LIBRARY_OUTPUT_DIRECTORY "${MANAGED_DIR}/app/jniLibs/${CMAKE_ANDROID_ARCH_ABI}"
)

add_custom_command(TARGET nativelibb POST_BUILD
	COMMAND ${CMAKE_COMMAND} -E copy "${MANAGED_DIR}/app/jniLibs/${CMAKE_ANDROID_ARCH_ABI}/libnativelibb.so" "${MANAGED_DIR}/app/src/main/assets/libnativelibb_${CMAKE_ANDROID_ARCH_ABI}.so"
)
