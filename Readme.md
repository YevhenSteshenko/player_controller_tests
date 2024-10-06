# API Player Tests Guide

Project presents framework for Backend testing which use as a Example tests for Player API. 

## Table of Contents


### [Tech Stack](#tech-stack)
### [Preparations](#preparations)
### [Run Tests](#run-tests)
### [Custom Test Run](#custom-test-run)

## Project Features
#### Technical stack:
* Java 11
* RestAssured 5.5.0
* TestNG 7.10.2
* Allure 2.29.0
* Lombok 1.18.34
* Gson 2.10.1
* Gradle 8.10.2



## Preparations
### Install Java

```groovy
sudo apt-get update && sudo apt-get upgrade
```
```groovy
sudo apt-get install openjdk-11-jdk
```
Check that Java install correct:
```groovy
java -version
```
*Example:*
```groovy
java -version
java version "11.0.13" 2021-10-19 LTS
Java(TM) SE Runtime Environment 18.9 (build 11.0.13+10-LTS-370)
Java HotSpot(TM) 64-Bit Server VM 18.9 (build 11.0.13+10-LTS-370, mixed mode)
```
### Install Gradle:
For this action you could use [Official Installation Guide](https://gradle.org/install/)
Check *Gradle* has been install correct:
```groovy
gradle -v
```
*Response example:*
```groovy
gradle -v

------------------------------------------------------------
Gradle 8.10.2
------------------------------------------------------------

Build time:    2024-09-23 21:28:39 UTC
Revision:      415adb9e06a516c44b391edff552fd42139443f7

Kotlin:        1.9.24
Groovy:        3.0.22
Ant:           Apache Ant(TM) version 1.10.14 compiled on August 16 2023
Launcher JVM:  11.0.24 (Ubuntu 11.0.24+8-post-Ubuntu-1ubuntu322.04)
Daemon JVM:    /usr/lib/jvm/java-11-openjdk-amd64 (no JDK specified, using current Java home)
OS:            Linux 6.8.0-45-generic amd64
```



### Download Project
Download project from Git repository:
```groovy
git clone https://github.com/YevhenSteshenko/player_controller_tests.git
```


## Run Tests
To run tests use command from Project root:
```groovy
./gradlew clean test
```
For more details use parameter `--info`

By default Framework use next `Run Parameters` values:
* `env=rel` 
* `api_version=v1`
* `thread=3`
* `suites=src/test/resources/test_suites/api.player.suite.xml`
* `parallel=classes`

This parameters set in [default.properties](./src/test/resources/default.properties) file.

### Parameter Env
`env` expected values: `rel, prod` now. Depends on `env` set will be chosen file which will be setup as `*.properties`

*Example:*
For `env=rel` will be set or [rel.v1.properties](./src/test/resources/rel.v1.properties) or [rel.v2.properties](./src/test/resources/rel.v2.properties)
depends on which `api_version` set in [default.properties](./src/test/resources/default.properties)

### Parameter api_version
Parameters `env` and `api_version` working together and set which `.properties` file will be chosen for framework configuration

### Parameter thread
`thread` expected values: int value from 1 to ... . `thread` set how many threads will be used for test run.

### Parameter suites
`suites` expected relative path to *.xml file which described testing strategy based on `testng` rules. `suites` pass one path or array with whitespace as spliterator.

*Examples:*
```groovy
suites=src/test/resources/test_suites/api.player.suite.xml
```
```groovy
suites=src/test/resources/test_suites/api.player.suite.xml src/test/resources/test_suites/api.player.create.suite.xml
```
All configured suites located here [test_suites](./src/test/resources/test_suites)

### Parameter parallel
`parallel` set entity which will be used for parallel run. 

*Expected values:*
`methods` , `tests`, `classes` or `instances`

*Note:*
Now tests will show correct results only for `parallel=classes`. If need to use other parallel run then need to remove @BeforeMethod and split logic into @Test entities.

### Custom Test Run 

If you need to run tests with other parameters then you have few options:
* Change parameters in [default.properties](./src/test/resources/default.properties)
* Set Parameters by default in [build.gradle](./build.gradle). 
* Set Parameters in Commandline

 **Note:**  
 If you want to make changes in [build.gradle](./build.gradle) then you need to switch of this parameter/-rs [default.properties](./src/test/resources/default.properties)
Because Framework use next priority: Commandline -> default.properties -> Default value

### Change Parameters in [default.properties](./src/test/resources/default.properties)
Open [default.properties](./src/test/resources/default.properties) and set needed parameters as described above.

### Change Parameters in [build.gradle](./build.gradle)
Open [build.gradle](./build.gradle) find blocks responsible for needed Parameter logic.

*Example:*
For `env` parameter
```groovy
        //ENV CONFIG:
        //Commandline -> default.properties env -> Default value
        def commandLineEnv = System.getProperty('env')
        // Default value 'rel' if not provided in properties file
        def defaultPropertiesFileEnv = properties.getProperty('env', 'rel')
        def env = commandLineEnv
                ? commandLineEnv
                : defaultPropertiesFileEnv
        systemProperty 'env', env
        println("ENVIRONMENT NAME: " + env)
```
In Line `def defaultPropertiesFileEnv = properties.getProperty('env', 'rel')`
change second argument into `prod`

Then open [default.properties](./src/test/resources/default.properties) find and switch of `env` parameter like this `#env=rel`

Now after test run will be used `env=prod`

### Change Parameter in Commandline
Set any parameter with flag `-D` to set it.

*Example:*
```groovy
./gradlew clean test -Dsuites="src/test/resources/test_suites/api.player.create.suite.xml src/test/resources/test_suites/api.suite.xml" -Dthread=5 -Denv=rel -Dapi_version=v1 -Dparallel=methods --info
```
### Report
Report data saved in [build](./build) directory.

Report could be generated and open with command:
```groovy
./gradlew allureServe
```
Here you find information about `Environment` setup which used for this test run. Also information about test statuses, how many threads using and details in test steps.


