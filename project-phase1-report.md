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

| Test Type         | Scenario                            | Path                                  | Result |
| ----------------- | ----------------------------------- | ------------------------------------- | ------ |
| Basic path        | Invalid format (wrong separators)   | A → B → D → E                         | false  |
|                   | Non-digit in year/month/day         | A → B → D → F → G                     | false  |
|                   | Month out of range (0 or 13)        | A → B → D → F → H → I                 | false  |
|                   | Day overflow (e.g., 2025-04-31)     | A → B → D → F → H → J → L → M → N     | false  |
|                   | Valid future date (non-Feb)         | A → B → D → F → H → J → L → M → O → P | true   |
| Boundary analysis | Date = today (boundary on compare)  | A → B → D → F → H → J → L → M → O → P | true   |
|                   | Date < today (just before boundary) | A → B → D → F → H → J → L → M → O → Q | false  |
|                   | Leap day valid (2024-02-29)         | A → B → D → F → H → J → K → M → O → P | true   |
|                   | Leap day invalid (2025-02-29)       | A → B → D → F → H → J → L → M → N     | false  |

---

### 3.2 isValidDateTime

![Control Flow Diagram for isValidDateTime](docs/img/isValidDateTime.drawio.png)

| Test Type         | Scenario                                       | Path                              | Result |
| ----------------- | ---------------------------------------------- | --------------------------------- | ------ |
| Basic path        | Null or wrong length (not 19 chars)            | A → B → C                         | false  |
|                   | Wrong separators (e.g., `2025/09/03T12:00:00`) | A → B → D → E                     | false  |
|                   | Invalid date part (e.g., 2025-04-31)           | A → B → D → F → G                 | false  |
|                   | Non-digit in time fields (e.g., `12:5a:00`)    | A → B → D → F → H → I             | false  |
|                   | Time out of range (e.g., 24:00:00)             | A → B → D → F → H → J → K         | false  |
|                   | Valid datetime and after now                   | A → B → D → F → H → J → L → M → N | true   |
| Boundary analysis | Datetime exactly equal to now                  | A → B → D → F → H → J → L → M → N | true   |
|                   | Datetime just before now (now − 1 second)      | A → B → D → F → H → J → L → M → O | false  |
|                   | Lower bound valid (00:00:00)                   | A → B → D → F → H → J → L → M → N | true   |

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
