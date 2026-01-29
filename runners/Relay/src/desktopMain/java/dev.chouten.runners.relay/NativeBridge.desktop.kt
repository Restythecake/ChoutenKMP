package dev.chouten.runners.relay

actual object NativeBridge {
    init { System.loadLibrary("relay") }

    external fun nativeLoadWasm(bytes: ByteArray)
    actual external fun initLogger(logger: Any)

    actual fun load(bytes: ByteArray) {
        nativeLoadWasm(bytes)
    }
    actual external fun add(a: Int, b: Int): Int
}
