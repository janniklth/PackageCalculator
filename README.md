# Package Calculator

## Overview
The Package Calculator is a Java-based application designed to calculate shipping costs based on package dimensions and weight. 
It includes a graphical user interface (GUI) for user interaction and supports loading custom shipping rules from JSON files.
The project was part of the lecture advanced software engineering at DHBW Stuttgart.

Please note: A detailed documentation especially for the final submission (Javadoc, code coverage, and code metrics) is 
available in the [documentation](documentation.md).

## Features

### Required Features
- Calculate shipping costs based on package dimensions and weight.
- Display error messages and alerts to the user.
- Maintain a message area for logging important information.

### Additional Features
- Load custom shipping rules from JSON files.
- Redesigned user interface with improved usability.


## Project Structure

- `/control`: Contains all logic for the application.
- `/data`: Contains the data model classes and necessary data structures.
- `/doc`: Contains the javadoc documentation.
- `/exceptions`: Contains custom exceptions for error handling.
- `/gui`: Contains the graphical user interface (GUI) classes.
- `/test`: Contains the tests for the application.


## Getting Started

### Prerequisites

- Java Development Kit (JDK) 23 or higher
- IntelliJ IDEA or another Java IDE


### Running the Application

1. Clone the repository.
2. Open the project in IntelliJ IDEA.
3. Build and run the `PackageCalculator` class from the `gui` package.

### Running Tests
1. Open the project in IntelliJ IDEA.
2. Navigate to `test/`.
3. Run the `ShippingRuleLoaderTest` class to execute the tests for the `ShippingRuleLoader` class.
4. Run the `CalculatorTest` class to execute the tests for the `Calculator` class.

Note: The test results and code coverage are already documented in the [extended documentation](documentation.md).

## Usage of the tool
1. Enter the package dimensions and weight in the provided text fields. Respect the table with possible package dimensions.
2. Click the "Calculate" button to compute the shipping cost.
3. View the result in the shipping cost label and possible errors in the message area.

## License
This project is licensed under the MIT License.