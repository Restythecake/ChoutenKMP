#import <Foundation/Foundation.h>
#import "relay_native.hpp"

static void (*gHostLogger)(const char*, size_t) = nullptr;

// Called from Swift to register logger
extern "C" void relay_set_logger(void (*logger)(const char*, size_t)) {
    gHostLogger = logger;
}

// This is the function Wasm3 calls
void host_log(const char* msg, size_t len) {
    if (gHostLogger) {
        gHostLogger(msg, len);
    }
}

extern "C" void* relay_create_module(const uint8_t* bytes, size_t size) {
    return new Wasm3Module(bytes, size);
}

extern "C" int relay_add(void* modulePtr, int a, int b) {
    Wasm3Module* module = (Wasm3Module*)modulePtr;
    return module->add(a, b);
}

extern "C" void relay_destroy_module(void* modulePtr) {
    delete (Wasm3Module*)modulePtr;
}