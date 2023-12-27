package com.jaychang.generatemock

/**
 * The processor will generate a mock class implementing required methods with empty implementation
 * which throws [NotMockedException] and a factory class named with Factory suffix(e.g. SomeApiMockFactory).
 *
 * The annotated class must be an abstract class.
 *
 * Example:
 *
 * ```
 * interface SomeApi {
 *     fun someFunction1(): String
 *
 *     fun someFunction2(): String
 * }
 *
 * @GenerateMock
 * abstract class SomeApiMock : SomeApi {
 *     fun someFunction1(): String {
 *          return "some function 1"
 *     }
 * }
 * ```
 *
 * val someApiMock = SomeApiMockFactory.create()
 * someApiMock.someFunction1() // return "some function 1"
 * someApiMock.someFunction2() // throw NotMockedException("The function someFunction2 is not mocked yet.")
 * */
@Target(AnnotationTarget.CLASS)
annotation class GenerateMock
