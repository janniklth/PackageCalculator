# Developer Documentation

## Table of Contents
1. [Introduction](#introduction)
2. [JavaDoc](#javadoc)
3. [Test Results](#test-results)
4. [Code Coverage](#code-coverage)
5. [Code Metrics](#code-metrics)

## Introduction

These file provides the main source of documentation for the project, especially the required documentation for the 
final submission (Javadoc, test results, code coverage, and code metrics).

## JavaDoc

The JavaDoc for the project is available in the following directory: [JavaDoc](doc/JavaDoc/index.html). For the best
experience, please download the directory and open the index.html file in a browser.

## Test Results

This project contains 52 tests, which are all passing. The following tests classes are available:
- CalculatorTest
- CalculatorRandomTest
- CurrencyTest
- ErrorDisplayTest
- MeasurementSystemTest
- SettingsManagerTest
- ShippingRuleLoaderTest
- ShippingRuleTest

The test results are available in the following directory: 
- [Test Results (HTML)](doc/testResults.html)
- [Test Results (PDF)](doc/testResults.pdf)


## Code Coverage

The following image gives an overview of the code coverage for the project:

![Code Coverage](/doc/CodeCoverageOverview.png)

The important parts of this project (especially logic and data handling) are covered 100% by tests. Only the GUI and 
some Getter/Setter methods are not covered by tests, as this is not common practice in such projects.

For more detailed information, please use the generate Code Coverage report, available from the following directory:
[Code Coverage Report](doc/coverageReport/index.html)

Note: The Code Coverage report is only available in HTML format and can be opened in a browser when downloaded.


## Code Metrics

The following image gives an overview of the code metrics for the project:

![Code Metrics](/doc/MetricsOverview.png)

For more detailed information, please use the generate CodeMR report, available from the following directory: 
[CodeMR Report](doc/codemr/PackageCalculator/html/main_report/index.html)

Note: The CodeMR report is only available in HTML format and can be opened in a browser when downloaded.