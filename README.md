# Group 53 README

# CITS5501 Project 2025 - Phase 1

## Project Description

This project is a beginner's version of the **DateTimeChecker** program.
The goal is to ensure the **syntactical** and **semantic** validation of dates and datetimes used in the **Tachi flight reservation system**.

## Project Directory

```
2025S2-CITS5501-Group53-Project/
├── .github/workflows/              # GitHub Actions
│   └── starter-checks.yml
├── docs/                           # Documentation and Flowcharts
│   ├── isValidDate.drawio.png
│   └── isValidDateTime.drawio.png
├── src/                            # Java source code and unit tests
│   ├── DateTimeChecker.java        # Date/time verification tools
│   └── DateTimeCheckerTest.java    # JUnit tests
├── starter_tests/                  # Provided Python tests
│   └── test_repository.py
├── .gitignore                      # Git ignore rules
├── README.md                       # Project description
└── project-phase1-report.md        # Stage 1 report (Markdown format)
```

## Function Description

- **Date** validation (`isValidDate`)  
  - Format: `YYYY-MM-DD`  
  - Must represent a valid Gregorian date  
  - Must be today or a future date

- **Datetime** validation (`isValidDateTime`)  
  - Formats:  
    - `YYYY-MM-DD` (date only)  
    - `YYYY-MM-DDTHH:MM` (no seconds)  
    - `YYYY-MM-DDTHH:MM:SS` (full time)  
  - Date must be ≥ today  
  - Time fields must be within valid ranges  
  - For full datetimes, must not be before the current system time  

## Testing

Implemented with **JUnit 5** in `src/DateTimeCheckerTest.java`
- Covers:
  - Valid and invalid syntax
  - Semantic checks (today / future / past)
  - Boundary cases (e.g., leap years, `00:00:00`, `23:59:59`)
- Tests run automatically via **GitHub Actions** and teacher-provided **starter_tests**

## Control Flow Diagrams

-`isValidDate`  
  ![Control Flow Diagram for isValidDate](docs/img/isValidDate.drawio.png)
  
-`isValidDateTime`  
  ![Control Flow Diagram for isValidDateTime](docs/img/isValidDateTime.drawio.png)

## How to Run

### Run Java Tests locally
```bash
# If using Maven
mvn test

# If using Gradle
./gradlew test

# Basic
## Windows Powershell:
cd 'Folder path'
javac -cp ".;lib\junit-platform-console-standalone-1.10.0.jar" src\DateTimeChecker.java src\DateTimeCheckerTest.java
java -jar lib\junit-platform-console-standalone-1.10.0.jar -cp src --scan-class-path

## Mac / Linux / WSL (Ubuntu) Terminal:
javac -cp .:lib/junit-platform-console-standalone-1.10.0.jar src/DateTimeChecker.java src/DateTimeCheckerTest.java
java -jar lib/junit-platform-console-standalone-1.10.0.jar -cp src --scan-class-path

 
  
