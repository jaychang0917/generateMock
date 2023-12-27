A KSP processor that generates a mock class implementing required methods with empty implementation and a factory class.

# Download
generateMock is available on `mavenCentral()`.
   

# Quick Start
1. Annotated the abstract class with `@GenerateMock`.
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
2. Use the Generated factory class.
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
