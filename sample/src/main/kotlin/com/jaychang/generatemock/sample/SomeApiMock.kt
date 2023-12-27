package com.jaychang.generatemock.sample

import com.jaychang.generatemock.GenerateMock
import com.somewhere.SomeApi

@GenerateMock
abstract class SomeApiMock : SomeApi {
    override fun normalFunction1(): String {
        return "from some mock"
    }
}