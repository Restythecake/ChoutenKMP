package dev.chouten.runners.relay


object RelayLogger {
    var logs: List<String> = listOf()

    // This will be called from native C++/WASM
    fun log(message: String) {
        // You can use Log.d, println, or whatever you want
        println("RelayWASM -> $message")
        logs += message
    }
}

expect object NativeBridge {
    fun initLogger(logger: Any)
    fun load(bytes: ByteArray)
    fun add(a: Int, b: Int): Int
}