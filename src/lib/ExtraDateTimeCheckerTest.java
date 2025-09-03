import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExtraDateTimeCheckerTest {

    @Test
    public void testContainsSeconds_ShouldFail() {
        assertFalse(DateTimeChecker.isValidDateTime("2025-09-03T13:45:59"));
    }

    @Test
    public void testMissingTSeparator_ShouldFail() {
        assertFalse(DateTimeChecker.isValidDateTime("2025-09-03 13:45"));
    }

    @Test
    public void testWrongDateSeparators_ShouldFail() {
        assertFalse(DateTimeChecker.isValidDateTime("2025/09/03T13:45"));
    }

    @Test
    public void testTooShort_ShouldFail() {
        assertFalse(DateTimeChecker.isValidDateTime("2025-09-03T13"));
    }

    @Test
    public void testNonDigitInTime_ShouldFail() {
        assertFalse(DateTimeChecker.isValidDateTime("2025-09-03T12:4a"));
    }

    @Test
    public void testOutOfRangeTime_ShouldFail() {
        assertFalse(DateTimeChecker.isValidDateTime("2025-09-03T24:00"));
        assertFalse(DateTimeChecker.isValidDateTime("2025-09-03T23:60"));
    }

    @Test
    public void testTrailingTimezoneLikeZ_ShouldFail() {
        assertFalse(DateTimeChecker.isValidDateTime("2025-09-03T13:45Z"));
    }

    @Test
    public void testValidFutureDateTime_ShouldPass() {
        assertTrue(DateTimeChecker.isValidDateTime("2099-12-31T23:59"));
    }
}
