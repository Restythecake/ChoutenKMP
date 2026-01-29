#include "relay_native.hpp"

m3ApiRawFunction(logFunc) {
    m3ApiGetArgMem(const char*, msg);
    m3ApiGetArg(i32, len);
    host_log(msg, len);
    m3ApiSuccess();
}

Wasm3Module::Wasm3Module(const uint8_t* data, size_t size) {
    env = m3_NewEnvironment();
    runtime = m3_NewRuntime(env, 64 * 1024, nullptr);

    m3_ParseModule(env, &module, data, size);
    m3_LoadModule(runtime, module);
    m3_LinkRawFunction(module, "env", "log_host", "v(ii)", &logFunc);


    M3Result result = m3_FindFunction(&func, runtime, "add");
    if (result) {
        host_log(result, strlen(result));
        return;
    }
}

Wasm3Module::~Wasm3Module() {
    if (runtime) m3_FreeRuntime(runtime);
    if (env) m3_FreeEnvironment(env);
}

int32_t Wasm3Module::add(int32_t a, int32_t b) {
    if (!env || !runtime || !module || !func) {
        host_log("Wasm3Module not properly initialized", strlen("Wasm3Module not properly initialized"));
        return 0;
    }
    M3Result res = m3_CallV(func, a, b);
    if (res) return 0;

    int32_t value = 0;
    const void* retPtrs[1] = { &value };
    m3_GetResults(func, 1, retPtrs);

    return value;
}