#pragma once
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void relay_set_logger(void (*logger)(const char*, size_t));
void* relay_create_module(const uint8_t* bytes, size_t size);
int relay_add(void* modulePtr, int a, int b);
void relay_destroy_module(void* modulePtr);

#ifdef __cplusplus
}
#endif