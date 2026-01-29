#include "relay_native.hpp"
#include <string>
#ifdef __ANDROID__
#include <jni.h>

JavaVM* gJvm;       // set when your library initializes
jobject gLoggerObj;  // Kotlin object with a log(String) method
#endif

#include <cstdio>
#include <cstring>

void host_log(const char* msg, size_t len) {
#ifdef __ANDROID__
    if (!gJvm || !gLoggerObj) return;

    JNIEnv* env = nullptr;
    gJvm->AttachCurrentThread(&env, nullptr);

    jstring jmsg = env->NewStringUTF(std::string(msg, len).c_str());
    jclass cls = env->GetObjectClass(gLoggerObj);
    jmethodID logId = env->GetMethodID(cls, "log", "(Ljava/lang/String;)V");

    env->CallVoidMethod(gLoggerObj, logId, jmsg);
    env->DeleteLocalRef(jmsg);
#else
    // Desktop fallback
    fwrite(msg, 1, len, stdout);
    fputc('\n', stdout);
#endif
}


// Keep a static module instance for now
static Wasm3Module* wasmModule = nullptr;

extern "C" {

    JNIEXPORT void JNICALL
Java_dev_chouten_runners_relay_NativeBridge_initLogger(JNIEnv* env, jobject thiz, jobject logger) {
    env->GetJavaVM(&gJvm);
    gLoggerObj = env->NewGlobalRef(logger);
}

// Load WASM module from byte array
JNIEXPORT void JNICALL
Java_dev_chouten_runners_relay_NativeBridge_nativeLoadWasm(JNIEnv* env, jobject, jbyteArray wasmBytes) {
    jsize len = env->GetArrayLength(wasmBytes);
    jbyte* data = env->GetByteArrayElements(wasmBytes, nullptr);

    if (wasmModule) delete wasmModule;
    wasmModule = new Wasm3Module((uint8_t*)data, len);

    env->ReleaseByteArrayElements(wasmBytes, data, JNI_ABORT);
}

// Call add function
JNIEXPORT jint JNICALL
Java_dev_chouten_runners_relay_NativeBridge_add(JNIEnv*, jobject, jint a, jint b) {
    if (!wasmModule) return 0;
    return wasmModule->add(a, b);
}

}
