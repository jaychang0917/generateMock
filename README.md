A KSP processor that generates a mock class implementing interface required methods with empty implementation.

# Download
generateMock is available on `mavenCentral()`.
```groovy
implementation "io.github.jaychang0917:generateMock-api:0.0.1"
ksp "io.github.jaychang0917:generateMock-compiler:0.0.1"
```   

# Quick Start
### 1. Apply the KSP plugin. 
PS: use the compatible KSP version with your current kotlin gradle plugin version
```groovy
plugins {
    id 'com.google.devtools.ksp' version "1.9.21-1.0.15"
}
```
### 2. Annotate the abstract class with `@GenerateMock`.
```kotlin
interface SomeApi {
    fun someFunction1(): String

    fun someFunction2(): String
}

@GenerateMock
abstract class SomeApiMock : SomeApi {
    fun someFunction1(): String {
         return "some function 1"
    }
}
```
### 3. Use the generated factory class.
```kotlin
val someApiMock = SomeApiMockFactory.create()
someApiMock.someFunction1() // return "some function 1"
someApiMock.someFunction2() // throw NotMockedException("The function someFunction2 is not mocked yet.")
``` 
 
# License
```
 Copyright (C) 2023. Jay Chang
 
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 
     http://www.apache.org/licenses/LICENSE-2.0
 
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
```
