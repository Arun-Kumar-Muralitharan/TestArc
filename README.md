# TestArc
## Overview
TestArc is a custom Test Automation Framework tailor-made for plug-and-play Automation.
The framework is written on Java which implements Playwright.
The Framework employs Allure for Reporting and runs on JUnit5.

## Source
The source code for the framework is available on the Arun's GitHub repository.

GitHub URL: (https://github.com/Arun-Kumar-Muralitharan/TestArc)

## Prerequisites
1.  Java 23
2. Updated JDK

### Setting Up The Test Repo
Create a New Java Gradle project in IntelliJ IDEA.
Add the following dependencies in the `build.gradle` file:

```
dependencies {
    implementation 'com.TestArc'
    }
```

> [!WARNING] 
> The version number may vary depending on the latest release.

## Parallel Test Runs
TestArc supports parallel test runs. Enabling/Disabling parallel test runs are controlled in the local repository.
The parallel test runs are controlled by the `junit-platform.properties` file.
In Order to trigger a  parallel test run, the following steps has to be followed.
Users can enable or disable parallel test runs by modifying the properties file.

> [!IMPORTANT]
> Create a file under `src/test/resources` named `junit-platform.properties` and add the following lines:

```
# Enable parallel execution
junit.jupiter.execution.parallel.enabled = true

# Configure parallel mode for classes and methods
junit.jupiter.execution.parallel.mode.default = concurrent
junit.jupiter.execution.parallel.mode.classes.default = concurrent

# Set parallelism (number of threads)
junit.jupiter.execution.parallel.config.strategy = fixed
junit.jupiter.execution.parallel.config.fixed.parallelism = 4
```

## Installation
To install the framework, add the TestArc dependency in your local repository's `build.gradle` file.

## Usage
To use the framework, create a new test class and extend the TestArc methods and classes. 

>[!TIP]
> An example implementation for ui and api is provided under the example package of the test in the Framework.

## Running Tests
Once the ui/api tests are written, they can be run using the following commands:

- To trigger a test run use the command `gradle test`
- To Trigger a Test Run on a specific class use `gradle test --tests example/uiTest.java`
- To generate Test Results use `allure generate allure-results --clean`
- To open the test results in a browser and view them use `allure open`

## Contributions
Everyone who see's this is welcome to contribute to the framework.