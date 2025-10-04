import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

/**
 * Phase 2 Task 5.3 — SegmentSubcommandTest
 * TODO:
 * 1) Fill real constructor args after checking JAR.
 * 2) Map TC IDs to Task 5.2.
 */

public class SegmentSubcommandTest {

    private static final String ORIGIN = "PER";
    private static final String DEST   = "SYD";
    private static final String FLIGHT = "QF123";

    private void assertSegmentFields(Object seg) {
        // TODO: replace Object with SegmentSubcommand and assert getters
    }

    /**
     * Test ID: SS-TC1
     * Baseline valid segment — Arrange/Act/Assert placeholders only.
     */
    @Test
    public void testValidSegment_Baseline_shell() {
        // Arrange
        // TODO: prepare minimal valid args (PER → SYD, QF123, tomorrow, cabin Y)

        // Act
        // TODO: create SegmentSubcommand seg = new SegmentSubcommand(...);

        // Assert
        // TODO: assertSegmentFields(seg);
    }

        /**
     * Test ID: SS-TC2
     * Semantic invalid — origin equals destination.
     */
    @Test
    public void testSemantic_SameOriginDest_shell() {
        // Arrange
        // TODO: use identical airports (e.g., PER → PER)
    
        // Act + Assert
        // TODO: assertThrows(SemanticError.class, () -> new SegmentSubcommand(...));
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
