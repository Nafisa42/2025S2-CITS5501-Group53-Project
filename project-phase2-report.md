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

### SegmentSubcommand constructor

#### Coverage Level
For this analysis the base choice coverage was applied. This approach selects one
representative test from each input partition rather than attempting all possible
combinations. Since the constructor has multiple parameters (origin, destination,
number of passengers, and date), full combinatorial testing would quickly become
unmanageable. Base choice strikes a balance by ensuring that each important input
characteristic is tested at least once.

#### Characteristics and Partitions
From the specification, the following characteristics were identified as critical:

1. **Origin code** – must be a three-letter uppercase airport code.  
   - Valid (e.g., `PER`)  
   - Invalid (wrong length, lowercase, or non-letter characters)

2. **Number of passengers** – must be an integer between 1 and 10 inclusive.  
   - Valid range (1–10)  
   - Too few (<1)  
   - Too many (>10)

3. **Date** – must be in `YYYY-MM-DD` format and must not be in the past.  
   - Valid (>= today)  
   - Invalid format (e.g., `2025/09/01`)  
   - Past date (before today)

These characteristics were chosen because they directly influence whether the
constructor will succeed or throw exceptions. For instance, airport codes are essential
to identify flight segments, the passenger count is bounded for system constraints, and
the date check prevents creating requests for flights that cannot exist.

#### Test Cases
| ID  | Fixtures | Input                                                    | Expected Outcome |
|-----|----------|----------------------------------------------------------|------------------|
| TC1 | —        | ORIGIN=PER, DEST=SYD, NUM_PEOPLE=1, DATE=today           | Success          |
| TC2 | —        | ORIGIN=xx, DEST=SYD, NUM_PEOPLE=1, DATE=today            | SyntacticError   |
| TC3 | —        | ORIGIN=PER, DEST=SYD, NUM_PEOPLE=20, DATE=last week      | SemanticError    |

These three cases together ensure that each chosen partition is exercised at least once.

