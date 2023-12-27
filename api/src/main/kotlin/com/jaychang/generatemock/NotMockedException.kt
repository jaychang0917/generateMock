package com.jaychang.generatemock

class NotMockedException(private val functionName: String) : RuntimeException() {
    override fun toString(): String {
        return "The function $functionName is not mocked yet."
    }
}