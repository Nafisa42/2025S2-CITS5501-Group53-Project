# Project Phase 2 Report

## Task 5.1 – ShopFlightFareCommand constructor analysis

### Preconditions

- **Origin and Destination Codes**  
  Both parameters must be valid IATA airport codes: exactly three uppercase
  letters (e.g., `PER`, `SYD`). Anything else is syntactically invalid.

- **Origin and Destination Uniqueness**  
  Origin and destination codes must not be the same. If they are equal, this is semantically invalid.

- **Trip Type**  
  Must be either `OneWay` or `Return`. These are the only two options defined in the command syntax.

- **Length of Stay (LOS)**  
  Required only when the trip type is `Return`. LOS must be an integer between 0 and 20 inclusive. If trip type is `OneWay`, LOS must be absent.

- **Cabin Type**  
  Must be one of the six permitted codes: `P`, `F`, `J`, `C`, `S`, or `Y`. These map to the `CabinType` enumeration in the specification.

- **Departure Date**  
  Must be in `YYYY-MM-DD` format. Semantically, it must represent a valid calendar date that is **not earlier than today** and **no more than 100 days
  in the future**.

- **Error Handling**  
  If inputs break format rules (e.g., a 2-letter code), a `SyntacticError` should be raised. If inputs break meaning rules (e.g., a past date, origin=destination, or LOS out of range), a `SemanticError` should be raised.

### Postconditions

- **Valid Object Construction**  
  When all preconditions are satisfied, a `ShopFlightFareCommand` instance is
  created with consistent internal state:
  - Stores origin and destination codes.
  - Records trip type. For OneWay trips, LOS is absent. For Return trips, a
    valid LOS (0–20) is stored.
  - Stores cabin type.
  - Records departure date within the allowed window.

- **Exception on Violation**  
  If any precondition is violated, the constructor does not create an object. Instead, it throws the correct exception (`SyntacticError` or
  `SemanticError`).

### Justification

These preconditions and postconditions are taken directly from the
**Tachi command specification** for `shop flight fare`. They align with the unit’s error model, which distinguishes between syntactic and semantic errors.
The constructor must guarantee that any constructed object represents a valid, executable command. This ensures downstream components can safely assume the
command’s parameters are correct without performing redundant checks.

---

## Task 5.2 – Input Space Partitioning

### Error Model (Fixture Assumptions)  
- **SyntacticError** – raised when inputs break **format rules**. Examples:  
  - Airport code not exactly three uppercase letters.  
  - Flight number not matching regex `[A-Z]{2}\d{3,4}`.  
  - Cabin type not one of `{P,F,J,C,S,Y}`.  

- **SemanticError** – raised when inputs are well-formed but violate business rules. Examples:  
  - Origin = destination.  
  - Passengers outside 1–10.  
  - Date ≤ today or > today+100.  
  - Flight number not existing (passes format but refers to no known service).  
  - Length of Stay > 20.  

**Fixture:** Inputs of type `LocalDate` are already guaranteed calendar-valid by upstream parsing. No string format errors for dates occur at this level.

---

### ISP Steps
The Input Space Partitioning (ISP) process followed here is:  
1. Identify input objects (e.g., origin, destination, passengers, date, flight number, cabin type).  
2. Define characteristics for each input object (format, range, uniqueness, etc.).  
3. Derive partitions (valid vs invalid cases).  
4. Select a coverage criterion (Base Choice Coverage).  
5. Generate test cases from the chosen partitions.

---

### SegmentSubcommand Constructor – ISP Analysis

#### Coverage Level
For this analysis the **Base Choice Coverage** strategy was applied. This method selects one representative test from each input partition rather than attempting all possible combinations. Since the constructor has multiple parameters (origin,
destination, number of passengers, date, flight number, and cabin type), full combinatorial testing would be infeasible. Base Choice provides a balanced
approach: it keeps the test suite small while ensuring that every important input characteristic is exercised at least once, including boundary and error partitions.

#### Characteristics and Partitions
| Input Object       | Characteristic       | Partitions (Valid / Invalid)                        | Error Type      |
|--------------------|----------------------|----------------------------------------------------|-----------------|
| Origin code        | Format legality      | 3-letter uppercase / wrong length or invalid chars | SyntacticError  |
| Destination code   | Relation to origin   | Different from origin / same as origin             | SemanticError   |
| Number of passengers | Range              | 1–10 inclusive / <1 or >10                         | SemanticError   |
| Date (LocalDate)   | Time semantics       | > today and ≤ today+100 / today, < today, >100 days| SemanticError   |
| Flight number      | Regex format         | `[A-Z]{2}\d{3,4}` / wrong format                   | SyntacticError  |
| Cabin type         | Membership           | P,F,J,C,S,Y / anything else                        | SyntacticError  |

#### Justification

- **Origin code**  
  - *Source*: Tachi spec requires IATA 3-letter uppercase codes.  
  - *Method*: Functionality-based partitioning (valid vs invalid string format).  
  - *Why chosen*: Airport code validity is foundational; if incorrect, no segment can be created.

- **Date**  
  - *Source*: Business rule restricts travel date to after today and within 100 days.  
  - *Method*: Boundary-value partitioning (today, <today, >today+100).  
  - *Why chosen*: Dates are highly error-prone; boundaries often cause failures.

- **Passengers**  
  - *Source*: System constraint of 1–10 passengers per booking.  
  - *Method*: Range-based partitioning (valid range, underflow, overflow).  
  - *Why chosen*: Passenger count limits are critical to prevent invalid or unrealistic requests.

Together, these ensure coverage of the most failure-prone characteristics, while the other features (destination, flight number, cabin type) are still captured in the table and tested.

#### Test Cases  
| ID  | Variation            | Input (relative to base)                                  | Expected Outcome |
|-----|----------------------|-----------------------------------------------------------|------------------|
| TC1 | Base                 | ORIGIN=PER, DEST=SYD, NUM=1, DATE=tomorrow, FL=QF123, CABIN=Y | Success |
| TC2 | Invalid Origin       | ORIGIN=xx, DEST=SYD, NUM=1, DATE=tomorrow, FL=QF123, CABIN=Y | SyntacticError |
| TC3 | Too Many Passengers  | ORIGIN=PER, DEST=SYD, NUM=20, DATE=tomorrow, FL=QF123, CABIN=Y | SemanticError |
| TC4 | Date = today         | ORIGIN=PER, DEST=SYD, NUM=1, DATE=today, FL=QF123, CABIN=Y   | SemanticError |

Each characteristic’s invalid partition is exercised at least once, while keeping tests simple and
non-overlapping.

---

### Additional Constructor – ShopFlightFareCommand

#### Coverage Level
The same **Base Choice Coverage** strategy was used. The constructor has multiple parameters (origin, destination, trip type, length of stay, date, and cabin type). A full combinatorial approach would be excessive, so Base Choice ensures each important characteristic is tested at least once.

#### Characteristics and Partitions  
| Input Object   | Characteristic     | Partitions (Valid / Invalid)            | Error Type      |
|----------------|--------------------|-----------------------------------------|-----------------|
| Trip type      | Allowed values     | OneWay, Return / anything else          | SyntacticError  |
| Length of Stay | Range (Return only)| 0–20 / absent or >20                    | SemanticError   |
| Departure date | Time semantics     | > today and ≤ today+100 / today, <today, >100 days | SemanticError |
| Cabin type     | Membership         | P,F,J,C,S,Y / anything else             | SyntacticError  |

#### Justification
These characteristics were chosen because they reflect the main business rules governing flight fare searches. The trip type (OneWay or Return) is central to the command’s behaviour, while the length of stay only applies when a return trip is requested. The departure date is bounded to avoid expired or unrealistic queries, and the cabin type ensures class codes conform to airline conventions. Testing these partitions ensures that the constructor either produces a valid command or fails with the correct exception.

#### Test Cases  
| ID  | Input                                               | Expected Outcome |
|-----|-----------------------------------------------------|------------------|
| SFC1 | ORIGIN=PER, DEST=SYD, TRIP=OneWay, DATE=tomorrow, CABIN=Y | Success |
| SFC2 | ORIGIN=PER, DEST=SYD, TRIP=Return, LOS=25, DATE=tomorrow, CABIN=Y | SemanticError |
| SFC3 | ORIGIN=PER, DEST=SYD, TRIP=OneWay, DATE=today, CABIN=Y | SemanticError |

This minimal ISP set ensures that both syntactic and semantic invalids are exercised for `ShopFlightFareCommand`.