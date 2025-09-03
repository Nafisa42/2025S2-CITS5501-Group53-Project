# Project Phase 1 Report

## 1. Plan Purpose

### Purpose

Ensure that the command line can correctly parse dates and times,  
including both syntax and semantic checks.

### Scope

At this stage, SQA mainly focuses on the `DateTimeChecker` class.

---

## 2. Type

| Item                  | Grammar                                  |
| --------------------- | ---------------------------------------- |
| **Date (YYYY-MM-DD)** | digits only (`0`–`9`, excluding hyphens) |
| Semantics             | valid Gregorian date                     |
|                       | must be ≥ today                          |
| **Time (THH:MM)**     | digits only (`0`–`9`, excluding colons)  |
| Semantics             | valid 24-hour clock                      |

## 3. Control Flow and Test Paths

### 3.1 isValidDate

![Control Flow Diagram for isValidDate](docs/img/isValidDate.drawio.png)

| Test Type  | Scenario                    | Path                | Result |
| ---------- | --------------------------- | ------------------- | ------ |
| Basic path | Invalid format              | A→B→D→E             | false  |
|            | (wrong separators)          |                     |        |
|            | Non-digit in year/month/day | A→B→D→F→G           | false  |
|            | Month out of range          | A→B→D→F→H→I         | false  |
|            | (0 or 13)                   |                     |        |
|            | Day overflow                | A→B→D→F→H→J→L→M→N   | false  |
|            | (e.g., 2025-04-31)          |                     |        |
|            | Valid future date           | A→B→D→F→H→J→L→M→O→P | true   |
|            | (non-Feb)                   |                     |        |
| Boundary   | Date = today                | A→B→D→F→H→J→L→M→O→P | true   |
|            | (boundary on compare)       |                     |        |
|            | Date < today                | A→B→D→F→H→J→L→M→O→Q | false  |
|            | (just before boundary)      |                     |        |
|            | Leap day valid              | A→B→D→F→H→J→K→M→O→P | true   |
|            | (2024-02-29)                |                     |        |
|            | Leap day invalid            | A→B→D→F→H→J→L→M→N   | false  |
|            | (2025-02-29)                |                     |        |

---

### 3.2 isValidDateTime

![Control Flow Diagram for isValidDateTime](docs/img/isValidDateTime.drawio.png)

| Test Type  | Scenario                      | Path              | Result |
| ---------- | ----------------------------- | ----------------- | ------ |
| Basic path | Null or wrong length          | A→B→C             | false  |
|            | (not 19 chars)                |                   |        |
|            | Wrong separators              | A→B→D→E           | false  |
|            | (e.g., `2025/09/03T12:00:00`) |                   |        |
|            | Invalid date part             | A→B→D→F→G         | false  |
|            | (e.g., 2025-04-31)            |                   |        |
|            | Non-digit in time fields      | A→B→D→F→H→I       | false  |
|            | (e.g., `12:5a:00`)            |                   |        |
|            | Time out of range             | A→B→D→F→H→J→K     | false  |
|            | (e.g., 24:00:00)              |                   |        |
|            | Valid datetime and after now  | A→B→D→F→H→J→L→M→N | true   |
| Boundary   | Datetime exactly equal to now | A→B→D→F→H→J→L→M→N | true   |
|            | Datetime just before now      | A→B→D→F→H→J→L→M→O | false  |
|            | (now − 1 second)              |                   |        |
|            | Lower bound valid             | A→B→D→F→H→J→L→M→N | true   |
|            | (00:00:00)                    |                   |        |

Our implementation only accepts datetime in the format 
`YYYY-MM-DDTHH:MM` (without seconds).

---

## 4. Workout

- GitHub repository
- `README.md`
- `CheckDateTimeStringTest.java`
- JUnit test cases
- Pull Requests
- Report

---

## 5. Standards

1. Follow Java coding standards and JUnit 5 specification.
2. GitHub PR workflow: use branches, require ≥ 2 reviewers,  
   merge only after approval.
3. Quality measures: maintainable code complexity; tests cover  
   normal, illegal, and boundary cases.

---

## 6. SQA Actions, Methods and Findings

### Developer — Nafisa Tabassum

#### Nafisa: Actions and Tasks

- Write `DateTimeChecker.java`

#### Nafisa: Methods and Tools

- Implement syntax and semantic verification
- Prohibit System.out/err, use logs instead
- Write Javadoc for public methods

---

### Developer — Erqian Chen

#### Erqian: Actions and Tasks

- Write JUnit test cases

#### Erqian: Methods and Tools

- Use JUnit 5
- Apply equivalence partitioning and boundary value analysis
- Use parameterized testing to reduce duplication

---

### Reviewer — Keming Cao, Ahmed Shadab

#### Reviewer: Actions and Tasks

- Review and merge pull requests

#### Reviewer: Methods and Tools

- Require PRs from feature branches
- Adopt Conventional Commits
- Resolve all review conversations before merging

---

### Reporter — Yanxi Liu

#### Reporter: Actions and Tasks

- Write and maintain the SQA plan

#### Reporter: Methods and Tools

- Keep the report in Markdown format
- Ensure the report passes lint and formatting checks

---

## 7. Evaluation

### Code Quality

- 100% PR merge rate after review.

### Test Quality

- Use parameterized tests to reduce duplication.
- Achieve 100% path coverage in unit and integration tests.
- All tests executed automatically through CI pipeline.

### Process Quality

- Each PR reviewed by ≥ 2 reviewers.

### Defect Tracking

- Use GitHub Issues with ≥ 90% closure rate for critical defects.
