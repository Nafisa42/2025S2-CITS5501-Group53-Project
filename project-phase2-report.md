# Project Phase 2 Report

## Task 5.1 – ShopFlightFareCommand constructor analysis

### Preconditions

- **Origin and Destination Codes**  
  Both parameters must be valid IATA airport codes: exactly three uppercase
  letters (e.g., `PER`, `SYD`). Anything else is syntactically invalid.

- **Trip Type**  
  Must be either `OneWay` or `Return`. These are the only two options defined
  in the command syntax.

- **Length of Stay (LOS)**  
  Required only when the trip type is `Return`. LOS must be an integer between
  0 and 20 inclusive.  
  If trip type is `OneWay`, LOS must be absent.

- **Cabin Type**  
  Must be one of the six permitted codes: `P`, `F`, `J`, `C`, `S`, or `Y`.
  These map to the `CabinType` enumeration in the specification.

- **Departure Date**  
  Must be in `YYYY-MM-DD` format. Semantically, it must represent a valid
  calendar date that is **not earlier than today** and **no more than 100 days
  in the future**.

- **Error Handling**  
  If inputs break format rules (e.g., a 2-letter code), a `SyntacticError`
  should be raised.  
  If inputs break meaning rules (e.g., a past date or LOS out of range), a
  `SemanticError` should be raised.

### Postconditions

- **Valid Object Construction**  
  When all preconditions are satisfied, a `ShopFlightFareCommand` instance is
  created with consistent internal state:
  - Stores origin and destination codes.
  - Records trip type, and LOS if relevant.
  - Stores cabin type.
  - Records departure date within the allowed window.

- **Exception on Violation**  
  If any precondition is violated, the constructor does not create an object.
  Instead, it throws the correct exception (`SyntacticError` or
  `SemanticError`).

### Justification

These preconditions and postconditions are taken directly from the
**Tachi command specification** for `shop flight fare`. They align with the
unit’s error model, which distinguishes between syntactic and semantic errors.
The constructor must guarantee that any constructed object represents a valid,
executable command. This ensures downstream components can safely assume the
command’s parameters are correct without performing redundant checks.

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
  - Flight number not existing (passes format but     refers to no known service).  
  - Length of Stay > 20.  

**Fixture:** Inputs of type `LocalDate` are already guaranteed calendar-valid by upstream parsing. No string format errors for dates occur at this level.

---

### SegmentSubcommand Constructor – ISP Analysis

#### Coverage Level
For this analysis the **Base Choice Coverage** strategy was applied. This method
selects one representative test from each input partition rather than attempting
all possible combinations. Since the constructor has multiple parameters (origin,
destination, number of passengers, date, flight number, and cabin type), full
combinatorial testing would be infeasible. Base Choice provides a balanced
approach: it keeps the test suite small while ensuring that every important input
characteristic is exercised at least once, including boundary and error partitions.

#### Characteristics and Partitions
| Characteristic       | Partition 1 (Valid)               | Partition 2 (Invalid/Special)                     | Error Type        |
|----------------------|-----------------------------------|--------------------------------------------------|------------------|
| Origin code          | 3-letter uppercase (e.g. PER)     | wrong length / lowercase / non-letters           | SyntacticError   |
| Destination code     | 3-letter uppercase (≠ origin)     | same as origin                                   | SemanticError    |
| Number of passengers | 1–10 inclusive                   | <1 or >10                                        | SemanticError    |
| Date (LocalDate)     | > today and ≤ today+100          | today or < today / > today+100                   | SemanticError    |
| Flight number        | matches `[A-Z]{2}\d{3,4}`        | wrong format (e.g., AA12, A12345)                | SyntacticError   |
| Cabin type           | P,F,J,C,S,Y                      | anything else (e.g., Z)                          | SyntacticError   |

#### Justification
These characteristics were selected because they each determine whether the
constructor can create a valid flight segment or must raise an exception. The
airport codes (origin and destination) are essential for identifying valid routes,
and errors in their format or equality would invalidate the request. The passenger
count is bounded by system limits (1–10), so violations indicate an impossible
booking. The date must be in the future but within 100 days, which prevents
unrealistic or expired flight requests. The flight number must follow the required
airline code and numeric pattern, since this identifies the specific service. The
cabin type enforces business rules on class of travel. Together, these features
capture the core constraints defined in the specification, so testing their
partitions ensures both success and error outcomes are covered.

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
The same **Base Choice Coverage** strategy was used. The constructor has multiple
parameters (origin, destination, trip type, length of stay, date, and cabin type).
A full combinatorial approach would be excessive, so Base Choice ensures each
important characteristic is tested at least once.

#### Characteristics and Partitions  
| Characteristic | Partition 1 (Valid)                | Partition 2 (Invalid/Special)         | Error Type      |
|----------------|------------------------------------|---------------------------------------|-----------------|
| Trip type      | OneWay, Return                     | anything else                         | SyntacticError  |
| Length of Stay | 0–20 (Return only)                 | absent (when Return) / >20            | SemanticError   |
| Departure date | > today and ≤ today+100            | today / < today / > today+100         | SemanticError   |
| Cabin type     | P,F,J,C,S,Y                        | anything else                         | SyntacticError  |

#### Justification
These characteristics were chosen because they reflect the main business rules
governing flight fare searches. The trip type (OneWay or Return) is central to the
command’s behaviour, while the length of stay only applies when a return trip is
requested. The departure date is bounded to avoid expired or unrealistic queries,
and the cabin type ensures class codes conform to airline conventions. Testing
these partitions ensures that the constructor either produces a valid command or
fails with the correct exception.

#### Test Cases  
| ID  | Input                                               | Expected Outcome |
|-----|-----------------------------------------------------|------------------|
| SFC1 | ORIGIN=PER, DEST=SYD, TRIP=OneWay, DATE=tomorrow, CABIN=Y | Success |
| SFC2 | ORIGIN=PER, DEST=SYD, TRIP=Return, LOS=25, DATE=tomorrow, CABIN=Y | SemanticError |
| SFC3 | ORIGIN=PER, DEST=SYD, TRIP=OneWay, DATE=today, CABIN=Y | SemanticError |

This minimal ISP set ensures that both syntactic and semantic invalids are
exercised for `ShopFlightFareCommand`.