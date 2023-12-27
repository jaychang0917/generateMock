package com.jaychang.generatemock.sample

fun main() {
    val generateMock = SomeApiMockFactory.create()
    val normalFunction1 = generateMock.normalFunction1()
    println("normalFunction1:$normalFunction1")
    val normalFunction2 = generateMock.normalFunction2()
    println("normalFunction2:$normalFunction2")
}

