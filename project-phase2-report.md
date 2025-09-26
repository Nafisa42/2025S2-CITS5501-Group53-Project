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
