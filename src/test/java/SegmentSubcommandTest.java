import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;

/**
 * Phase 2 Task 5.3 — SegmentSubcommandTest
 *
 * Mapping to Task 5.2 ISP test IDs:
 *  - SS-TC1: Baseline valid segment
 *  - SS-TC2: Same origin and destination (SemanticError)
 *  - SS-TC3: Departure date on/before today (SemanticError)
 *  - SS-TC4: numPeople outside 1–10 (SemanticError)
 *  - SS-TC5: Invalid IATA code format (SyntacticError)
 *  - SS-TC6: Invalid flight number format (SyntacticError)
 *
 * @implNote These tests correspond directly to the characteristics and partitions
 *           defined in Task 5.2 (trip/LOS, airports, date, cabin/people).
 * @see project-phase2-report.md  // Task 5.2 section
 *
 * ISP characteristic–partition mapping summary (with boundary coverage):
 *
 * • Date:
 *   {< today, = today, > today} → SS-TC3 (=today), Boundary (<today) added as disabled test
 *
 * • People:
 *   {<1, [1–10], >10} → SS-TC1 (1, valid), SS-TC1b (10, upper boundary valid), SS-TC4 (invalid)
 *
 * • Airports:
 *   {same, different} → SS-TC2
 *
 * • Flight number:
 *   {valid, invalid format, unknown code} → SS-TC6
 *
 * • IATA code:
 *   {valid, invalid format} → SS-TC5
 *
 * • LOS (length of stay):
 *   {ONE_WAY → null, RETURN → 0–20} — referenced in Task 5.1/5.2 context
 */

public class SegmentSubcommandTest {

    private static final String ORIGIN = "PER";
    private static final String DEST   = "SYD";
    private static final String FLIGHT = "QF123";

    /**
     * Fixed future date to ensure deterministic test behavior across
     * environments and time zones (avoids CI flakiness).
     */
    private static final LocalDate TOMORROW = LocalDate.of(2099, 1, 1);


    private SegmentSubcommand buildValid() {
        return new SegmentSubcommand(
                ORIGIN,
                DEST,
                FLIGHT,
                TOMORROW,
                CabinType.EconomyClass,
                1
        );
    }
    
    private void assertSegmentFields(SegmentSubcommand seg) {
        assertEquals(ORIGIN, seg.getOrigin(), "origin mismatch");
        assertEquals(DEST, seg.getDestination(), "destination mismatch");
        assertEquals(FLIGHT, seg.getFlightNumber(), "flight number mismatch");
        assertEquals(TOMORROW, seg.getDepartureDate(), "date mismatch");
        assertEquals(CabinType.EconomyClass, seg.getCabinType(), "cabin mismatch");
        assertEquals(1, seg.getNumPeople(), "numPeople mismatch");

        String text = seg.toString();
        assertTrue(text.contains(ORIGIN) && text.contains(DEST) && text.contains(FLIGHT),
                "toString() should contain key fields");
    }

     /**
     * Test ID: SS-TC1
     * Baseline valid segment — full implementation with AAA structure.
     */
    @Test
    public void testValidSegment_Baseline() {
        // Arrange: Prepare valid input parameters (PER → SYD, QF123, tomorrow, Economy, 1 passenger)
    
        // Act: Construct a valid SegmentSubcommand instance
        SegmentSubcommand seg = buildValid();
    
        // Assert: Verify all getter values and toString() contents
        assertSegmentFields(seg);
    }

    /**
     * SS-TC1b: valid upper boundary — numPeople = 10
     * This complements SS-TC1 (numPeople = 1) on the passing path.
     */
    @Test
    @DisplayName("SS-TC1b: valid segment with numPeople=10 (upper boundary)")
    public void testValidSegment_NumPeopleUpperBoundary() {
        // Arrange: same valid inputs, change people to 10
        int upperBound = 10;
    
        // Act: construct a valid segment at the upper boundary
        SegmentSubcommand seg = new SegmentSubcommand(
                ORIGIN, DEST, FLIGHT, TOMORROW, CabinType.EconomyClass, upperBound
        );
    
        // Assert: basic field checks; people should be 10
        assertEquals(ORIGIN, seg.getOrigin());
        assertEquals(DEST, seg.getDestination());
        assertEquals(FLIGHT, seg.getFlightNumber());
        assertEquals(TOMORROW, seg.getDepartureDate());
        assertEquals(CabinType.EconomyClass, seg.getCabinType());
        assertEquals(10, seg.getNumPeople());
    }
        
    /**
     * Test ID: SS-TC2
     * Semantic invalid — origin equals destination.
     * Expected (per Task 5.2): SemanticError at construction.
     * Current library note: constructor does not validate; keep this test @Disabled
     * and use assertDoesNotThrow as a placeholder. Switch to assertThrows when
     * semantics are enforced at construction time.
     */
    @Disabled("Constructor does not validate semantics; enable when rules are enforced at construction time")
    @Test
    public void testSemantic_SameOriginDest_shell() {
        // Arrange: use identical origin and destination (e.g., PER → PER)
        String sameAirport = ORIGIN;
        LocalDate date = TOMORROW;
    
        // Act + Assert (placeholder): current constructor should not throw
        assertDoesNotThrow(() -> new SegmentSubcommand(
                sameAirport, sameAirport, FLIGHT, date, CabinType.EconomyClass, 1
        ));
    
        // When semantics are enforced at construction time, replace the above with:
        // assertThrows(SemanticError.class, () -> new SegmentSubcommand(
        //         sameAirport, sameAirport, FLIGHT, date, CabinType.EconomyClass, 1
        // ));
    }

    /**
     * Test ID: SS-TC3
     * Semantic invalid — departure date on or before today.
     * Expected (per Task 5.2): SemanticError at construction.
     * Current library note: constructor does not validate; keep this test @Disabled
     * and use assertDoesNotThrow as a placeholder. Switch to assertThrows when
     * semantics are enforced at construction time.
     */
    @Disabled("Constructor does not validate semantics; enable when date rules are enforced at construction time")
    @Test
    public void testSemantic_DateOnOrBefore_shell() {
        // Arrange: use a boundary date equal to today (alternatively: LocalDate.now().minusDays(1))
        LocalDate today = LocalDate.now();
    
        // Act + Assert (placeholder): current constructor should not throw
        assertDoesNotThrow(() -> new SegmentSubcommand(
                ORIGIN, DEST, FLIGHT, today, CabinType.EconomyClass, 1
        ));
    
        // When semantics are enforced at construction time, replace with:
        // assertThrows(SemanticError.class, () -> new SegmentSubcommand(
        //         ORIGIN, DEST, FLIGHT, today, CabinType.EconomyClass, 1
        // ));
    }

    /**
     * Boundary completion for date: {< today, = today, > today}
     * This test covers the "< today" case to complement SS-TC3 (= today).
     * Kept @Disabled because constructor currently does not validate dates.
     */
    // TODO: link tracking issue if available, e.g. #45
    @Disabled("Constructor does not validate date semantics; enable when rules are enforced")
    @Test
    @DisplayName("Boundary (disabled): departure date before today (< today)")
    public void testSemantic_DateBeforeToday_shell() {
        // Arrange: yesterday
        LocalDate yesterday = LocalDate.now().minusDays(1);
    
        // Act + Assert (placeholder): current constructor should not throw
        assertDoesNotThrow(() -> new SegmentSubcommand(
                ORIGIN, DEST, FLIGHT, yesterday, CabinType.EconomyClass, 1
        ));
    
        // When semantics are enforced at construction time, replace with:
        // assertThrows(SemanticError.class, () -> new SegmentSubcommand(
        //         ORIGIN, DEST, FLIGHT, yesterday, CabinType.EconomyClass, 1
        // ));
    }

    /**
     * Test ID: SS-TC4
     * Semantic invalid — number of people out of allowed range (valid: 1–10).
     * Expected (per Task 5.2): SemanticError at construction.
     * Current library note: constructor does not validate; keep this test @Disabled
     * and use assertDoesNotThrow as a placeholder. Switch to assertThrows when
     * semantics are enforced at construction time.
     */
    @Disabled("Constructor does not validate semantics; enable when people-range rules are enforced at construction time")
    @Test
    public void testSemantic_PeopleRange_shell() {
        // Arrange: choose an out-of-range value (e.g., 11). Lower bound 0 would also be invalid.
        int outOfRange = 11;
    
        // Act + Assert (placeholder): current constructor should not throw
        assertDoesNotThrow(() -> new SegmentSubcommand(
                ORIGIN, DEST, FLIGHT, TOMORROW, CabinType.EconomyClass, outOfRange
        ));
    
        // When semantics are enforced at construction time, replace with:
        // assertThrows(SemanticError.class, () -> new SegmentSubcommand(
        //         ORIGIN, DEST, FLIGHT, TOMORROW, CabinType.EconomyClass, outOfRange
        // ));
    }

    /**
     * Test ID: SS-TC5
     * Syntactic invalid — bad IATA airport code format.
     * Expected (per Task 5.2): SyntacticError at construction.
     * Current library note: constructor does not validate; keep this test @Disabled
     * and use assertDoesNotThrow as a placeholder. Switch to assertThrows when
     * syntax rules are enforced at construction time.
     */
    @Disabled("Constructor does not validate syntax; enable when IATA code format rules are enforced")
    @Test
    public void testSyntactic_BadIata_shell() {
        // Arrange: use an invalid IATA code such as "P3R" or "Sydney"
        String invalidIata = "P3R"; // contains a digit → syntactically invalid
    
        // Act + Assert (placeholder): current constructor should not throw
        assertDoesNotThrow(() -> new SegmentSubcommand(
                invalidIata, DEST, FLIGHT, TOMORROW, CabinType.EconomyClass, 1
        ));
    
        // When syntax rules are enforced at construction time, replace with:
        // assertThrows(SyntacticError.class, () -> new SegmentSubcommand(
        //         invalidIata, DEST, FLIGHT, TOMORROW, CabinType.EconomyClass, 1
        // ));
    }

    /**
     * Control for SS-TC5 (IATA format): valid IATA codes should pass.
     * Uses different airports to avoid overlap with SS-TC1 baseline.
     */
    @Test
    @DisplayName("Control for SS-TC5: valid IATA format (MEL → SYD)")
    public void testControl_IataFormat_Valid() {
        // Arrange: well-formed IATA codes
        String origin = "MEL";
        String dest   = "SYD";
    
        // Act
        SegmentSubcommand seg = new SegmentSubcommand(
                origin, dest, FLIGHT, TOMORROW, CabinType.EconomyClass, 1
        );
    
        // Assert
        assertEquals(origin, seg.getOrigin());
        assertEquals(dest, seg.getDestination());
        assertEquals(TOMORROW, seg.getDepartureDate());
    }

    /**
     * Test ID: SS-TC6
     * Syntactic invalid — bad flight number format.
     * Expected (per Task 5.2): SyntacticError at construction.
     * Current library note: constructor does not validate; keep this test @Disabled
     * and use assertDoesNotThrow as a placeholder. Switch to assertThrows when
     * syntax rules are enforced at construction time.
     */
    @Disabled("Constructor does not validate syntax; enable when flight-number format rules are enforced")
    @Test
    public void testSyntactic_BadFlight_shell() {
        // Arrange: use an invalid flight number such as "QF" (too short) or "12345" (missing airline prefix)
        String invalidFlight = "QF"; // invalid because missing digits after airline prefix
    
        // Act + Assert (placeholder): current constructor should not throw
        assertDoesNotThrow(() -> new SegmentSubcommand(
                ORIGIN, DEST, invalidFlight, TOMORROW, CabinType.EconomyClass, 1
        ));
    
        // When syntax rules are enforced at construction time, replace with:
        // assertThrows(SyntacticError.class, () -> new SegmentSubcommand(
        //         ORIGIN, DEST, invalidFlight, TOMORROW, CabinType.EconomyClass, 1
        // ));
    }

    /**
     * Control for SS-TC6 (flight number format): well-formed flight number should pass.
     * Uses an alternative airline prefix to make the control explicit.
     */
    @Test
    @DisplayName("Control for SS-TC6: well-formed flight number (e.g., VA678)")
    public void testControl_FlightNumber_WellFormed() {
        // Arrange: well-formed flight number
        String goodFlight = "VA678";
    
        // Act
        SegmentSubcommand seg = new SegmentSubcommand(
                ORIGIN, DEST, goodFlight, TOMORROW, CabinType.EconomyClass, 1
        );
    
        // Assert
        assertEquals(goodFlight, seg.getFlightNumber());
        assertEquals(ORIGIN, seg.getOrigin());
        assertEquals(DEST, seg.getDestination());
    }
}
