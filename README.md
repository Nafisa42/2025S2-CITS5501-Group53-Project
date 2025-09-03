# Group 53 README

## Project Description

This project is a beginner's version of the **DateTimeChecker** program.  
The goal is to ensure the **syntactical** and **semantic** validation of dates  
and datetimes used in the **Tachi flight reservation system**.

---

## Main Functionality

| Item                  | Grammar                                  |
| --------------------- | ---------------------------------------- |
| **Date (YYYY-MM-DD)** | digits only (`0`–`9`, excluding hyphens) |
| Semantics             | valid Gregorian date                     |
|                       | must be ≥ today                          |
| **Time (THH:MM)**     | digits only (`0`–`9`, excluding colons)  |
| Semantics             | valid 24-hour clock                      |

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
