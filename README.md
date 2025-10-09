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
├── docs/img/                       # Documentation and Flowcharts
│   ├── isValidDate.drawio.png
│   └── isValidDateTime.drawio.png
├── src/                            # Java source code and unit tests
│   ├── DateTimeChecker.java        # Phase 1: date/time verification tools
│   ├── DateTimeCheckerTest.java    # Phase 1: JUnit tests
│   └── SegmentSubcommandTest.java  # Phase 2: ISP-based JUnit tests
├── starter_tests/                  # Provided Python starter tests
│   └── test_repository.py
├── tachi_grammer.txt               # Phase 2: EBNF grammar definition
├── project-phase1-report.md        # Phase 1 report (Markdown)
├── project-phase2-report.md        # Phase 2 report (Markdown)
├── .gitignore                      # Git ignore rules
└── README.md                       # Project description
```

---

## Development Environment

- **Java 17 (LTS)** – compatible with all project phases; Java 21 also supported  
- **JUnit 5** as the testing framework (Jupiter API 5.9 or above)  
- Recommended IDE: VS Code / IntelliJ IDEA  
- Build tools: Maven or Gradle (optional)  
- **BNF Playground** used in Phase 2 for grammar validation  
- Documentation and diagrams are stored in `docs/img/`

---

## Testing

Implemented with **JUnit 5** in `src/DateTimeCheckerTest.java`

- Covers:
  - Valid and invalid syntax
  - Semantic checks (today / future / past)
  - Boundary cases (e.g., leap years, `00:00`, `23:59`)
- Tests run automatically via **GitHub Actions** and provided **starter_tests**

### Phase 2 Additions
- Adds **ISP-based JUnit 5 tests** for full Tachi command validation.  
- Follows the **AAA** testing pattern and uses `assertThrows` for error handling.  
- Some tests use fixed **future dates** to ensure deterministic CI results.
---

## Running the Tests

### Run Java Tests locally

```bash
# If using Maven
mvn test

# If using Gradle
./gradlew test

# Basic
## Windows PowerShell:
cd 'Folder path'
javac -cp ".;lib\junit-platform-console-standalone-1.10.0.jar" \
  src\DateTimeChecker.java \
  src\DateTimeCheckerTest.java \
  src\SegmentSubcommandTest.java

java -jar lib\junit-platform-console-standalone-1.10.0.jar \
  -cp src --scan-class-path


## Mac / Linux / WSL (Ubuntu) Terminal:
javac -cp .:lib/junit-platform-console-standalone-1.10.0.jar \
  src/DateTimeChecker.java \
  src/DateTimeCheckerTest.java \
  src/SegmentSubcommandTest.java

java -jar lib\junit-platform-console-standalone-1.10.0.jar \
  -cp src --scan-class-path
```

> **Note:**  
> In **Phase 2**, all JUnit 5 tests (e.g., `SegmentSubcommandTest`) follow the  
> **AAA (Arrange–Act–Assert)** pattern and are derived from **Input Space Partitioning (ISP)**.  
> Some test cases use fixed **future dates** to ensure deterministic CI results.

---

## Support

If you have any questions or need assistance, please:

- Open an **Issue** in this repository, or  
- Refer to the **Group Members** section below for contact details.

---

## Group Members

- **Nafisa Tabassum (24627403@uwa.edu.au)** – Developer  
  - Phase 1: Implemented core features and functions.  
  - Phase 2: Developed the base framework for Task 5 (especially 5.1 and 5.2) and participated in reviewing Task 4.

- **Erqian Chen (23421379@uwa.edu.au)** – Developer  
  - Phase 1: Wrote JUnit test cases and ensured test coverage.  
  - Phase 2: Refined the implementation of Tasks 5.1 and 5.2, co-reviewed Task 4 and collaborated on **Task 5.4**

- **Keming Cao (24458695@uwa.edu.au)** – Developer  
  - Phase 1: Reviewed code contributions and provided feedback.  
  - Phase 2: Implemented **Task 5.3** (`SegmentSubcommandTest.java`) and collaborated on **Task 5.4** (test quality review).  
    Also updated and maintained the **README** and documentation structure.

- **Ahmed Shadab (23912137@uwa.edu.au)** – Reviewer  
  - Phase 1: Reviewed code contributions and ensured code quality.  
  - Phase 2: Handled **Task 4** (grammar and grammar quality) and participated in the review of Task 5.

- **Yanxi Liu (24305445@uwa.edu.au)** – Reporter  
  - Phase 1: Prepared project documentation and reports.  
  - Phase 2: Led **Task 4 documentation** (grammar and grammar quality) and assisted in final report editing.
