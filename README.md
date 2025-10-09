# Group 53 README

## Project Description

This project forms part of the **Tachi Flight Reservation System** testing framework.

- **Phase 1 (Foundation):**  
  Focused on developing the **DateTimeChecker** program — a basic utility to verify
  the **syntactic** and **semantic** validity of dates and datetimes used in Tachi commands.  
  It established the initial quality-assurance workflow, including JUnit testing,
  GitHub Actions CI, and documentation standards.  

- **Phase 2 (Expansion):**  
  Builds upon Phase 1 by extending validation from individual date/time inputs
  to complete **Tachi command grammars**.  
  It introduces an **EBNF grammar definition**, detailed **constructor analysis**,
  **Input Space Partitioning (ISP)**–based test design, and a comprehensive **JUnit 5 test suite**
  for higher-level components such as `SegmentSubcommand` and `ShopFlightFareCommand`.  
  Together, these stages create a structured and traceable testing process for both
  low-level data validation and high-level command behavior.

---

## Main Functionality

### Phase 1 – Date/Time Validation

| Item                  | Grammar                                  |
| --------------------- | ---------------------------------------- |
| **Date (YYYY-MM-DD)** | digits only (`0`–`9`, excluding hyphens) |
| Semantics             | valid Gregorian date                     |
|                       | must be ≥ today                          |
| **Time (THH:MM)**     | digits only (`0`–`9`, excluding colons)  |
| Semantics             | valid 24-hour clock                      |

### Phase 2 – Command Grammar and Testing

| Component            | Description |
| -------------------- | ------------ |
| **EBNF Grammar**     | Defines the syntax of Tachi CLI commands, such as `shop flight fare` and `air book request`.  
|                      | Implemented in `Group53_tachi_grammer.txt`. |
| **Validation Scope** | Extends testing from individual date/time inputs  
|                      | to complete commands that include parameters such as  
|                      | airport codes, flight numbers, cabin classes, passenger counts, and dates. |
| **Testing Approach** | Applies **Input Space Partitioning (ISP)** and the  
|                      | **AAA (Arrange–Act–Assert)** structure in JUnit 5  
|                      | to ensure systematic and readable test design. |
| **Error Handling**   | Uses `assertThrows` and `assertDoesNotThrow`  
|                      | to verify both **syntactic** and **semantic** correctness  
|                      | under valid and invalid input conditions. |
| **Goal**             | Ensures deterministic and maintainable command-level validation,  
|                      | aligned with the grammar specification and QA criteria. |

---

## Project Directory

```text
2025S2-CITS5501-Group53-Project/
├── .github/workflows/              # GitHub Actions
│   └── starter-checks.yml
├── docs/img                        # Documentation and Flowcharts
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

---

## Development Environment

- **Java 17+**
- **JUnit 5** as the testing framework
- Recommended IDE: VS Code / IntelliJ IDEA
- Build tools: Maven or Gradle (optional)
- Documentation and diagrams are stored in the `docs/img/` folder

---

## Testing

Implemented with **JUnit 5** in `src/DateTimeCheckerTest.java`

- Covers:
  - Valid and invalid syntax
  - Semantic checks (today / future / past)
  - Boundary cases (e.g., leap years, `00:00`, `23:59`)
- Tests run automatically via **GitHub Actions** and provided **starter_tests**

---

## Running the Tests

### Run Java Tests locally

```bash
# If using Maven
mvn test

# If using Gradle
./gradlew test

# Basic
## Windows Powershell:
cd 'Folder path'
javac -cp ".;lib\junit-platform-console-standalone-1.10.0.jar" \
  src\DateTimeChecker.java src\DateTimeCheckerTest.java

java -jar lib\junit-platform-console-standalone-1.10.0.jar \
  -cp src --scan-class-path


## Mac / Linux / WSL (Ubuntu) Terminal:
javac -cp .:lib/junit-platform-console-standalone-1.10.0.jar \
  src/DateTimeChecker.java src/DateTimeCheckerTest.java

java -jar lib/junit-platform-console-standalone-1.10.0.jar \
  -cp src --scan-class-path
```

---

## Support

If you have questions or need help, please:

- Open an **Issue** in this repository
- see **Group member** section for emails

---

## Group member

- **Nafisa Tabassum (24627403@uwa.edu.au)** – Developer

  - Implemented features and core functions

- **Erqian Chen (23421379@uwa.edu.au)** – Developer

  - Wrote JUnit test cases and ensured test coverage

- **Keming Cao (24458695@uwa.edu.au)** – Reviewer

  - Reviewed code contributions and provided feedback

- **Ahmed Shadab (23912137@uwa.edu.au)** – Reviewer

  - Reviewed code contributions and ensured code quality

- **Yanxi Liu (24305445@uwa.edu.au)** – Reporter
  - Prepared project documentation and reports
