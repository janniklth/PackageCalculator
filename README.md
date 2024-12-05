# Package Calculator

## Overview
The Package Calculator is a Java-based application designed to calculate shipping costs based on package dimensions and weight. 
It includes a graphical user interface (GUI) for user interaction and supports loading custom shipping rules from JSON files.
The project was part of the lecture advanced software engineering at DHBW Stuttgart.

Please note: A detailed documentation especially for the final submission (Javadoc, test results, code coverage, and code metrics) is 
available in the [documentation](documentation.md).

## Features

### Required Features
- Calculate shipping costs based on package dimensions and weight (automatically taking the best shipping rule/fare).
- Display error messages and alerts to the user.
- Maintain a message area for showing important information.
- Provide tests (JUnit, Glassbox and Random-based) for the application logic.

### Additional Features
- Load custom shipping rules/fares from JSON files.
- Changeable settings with dynamic updates to the user interface.
- Support different measurement systems (metric and imperial).
- Support different currencies (USD, EUR, GBP).
- Change the level/state of error messages displayed to the user.
- Advanced message area with time information and all error messages.
- Redesigned user interface with improved usability (focus on usability, not very beautiful design).


## Project Structure

- `/control`: Contains all logic for the application.
- `/data`: Contains the data model classes and necessary data structures.
- `/doc`: Contains the javadoc documentation, test results, code coverage and code metrics.
- `/exceptions`: Contains custom exceptions for error handling.
- `/gui`: Contains the graphical user interface (GUI) classes.
- `/test`: Contains the tests for all important classes.


## Getting Started

### Prerequisites

- Java Development Kit (JDK) 23 or higher
- IntelliJ IDEA or another Java IDE
- JavaFX SDK 21 or higher


### Running the Application

1. Clone the repository.
2. Open the project in IntelliJ IDEA.
3. Configure the library pathes for JavaFX SDK and the shipped libraries.
4. Build and run the `PackageCalculator` class from the `gui` package.

### Running Tests
1. Open the project in IntelliJ IDEA.
2. Navigate to `test/`.
3. Run the `CalculatorTest` class to execute the tests for the `Calculator` class or run any other test class.

Note: The test results and code coverage are already documented in the [extended documentation](documentation.md).

## Usage of the tool
1. Enter the package dimensions and weight in the provided text fields. Respect the table with possible package dimensions.
2. Click the "Calculate" button to compute the shipping cost.
3. View the result in the shipping cost label and possible errors in the message area.

## License
This project is licensed under the MIT License.