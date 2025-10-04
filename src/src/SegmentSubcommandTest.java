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
}
