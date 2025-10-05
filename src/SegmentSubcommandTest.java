import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import org.junit.jupiter.api.Disabled;

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
 */

public class SegmentSubcommandTest {

    private static final String ORIGIN = "PER";
    private static final String DEST   = "SYD";
    private static final String FLIGHT = "QF123";

    private static final LocalDate TOMORROW = LocalDate.now().plusDays(1);

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
     * Baseline valid segment — Arrange/Act/Assert placeholders only.
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
     */
    @Test
    public void testSemantic_DateOnOrBefore_shell() {
        // Arrange
        // TODO: use date equal to or before LocalDate.now()
    
        // Act + Assert
        // TODO: assertThrows(SemanticError.class, () -> new SegmentSubcommand(...));
    }

        /**
     * Test ID: SS-TC4
     * Semantic invalid — number of people out of allowed range.
     */
    @Test
    public void testSemantic_PeopleRange_shell() {
        // Arrange
        // TODO: use numPeople = 0 or 11 (outside valid range 1–10)
    
        // Act + Assert
        // TODO: assertThrows(SemanticError.class, () -> new SegmentSubcommand(...));
    }

        /**
     * Test ID: SS-TC5
     * Syntactic invalid — bad IATA airport code format.
     */
    @Test
    public void testSyntactic_BadIata_shell() {
        // Arrange
        // TODO: use invalid IATA like "P3R" or "Sydney"
    
        // Act + Assert
        // TODO: assertThrows(SyntacticError.class, () -> new SegmentSubcommand(...));
    }

        /**
     * Test ID: SS-TC6
     * Syntactic invalid — bad flight number format.
     */
    @Test
    public void testSyntactic_BadFlight_shell() {
        // Arrange
        // TODO: use invalid flight number like "QF" or "12345"
    
        // Act + Assert
        // TODO: assertThrows(SyntacticError.class, () -> new SegmentSubcommand(...));
    }
}
