import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DateTimeCheckerTest {

    // Tests for isValidDate

    @Test
    public void testValidFutureDate() {
        // should return true for a valid future date
        assertTrue(DateTimeChecker.isValidDate("2099-12-31"));
    }

    @Test
    public void testPastDate() {
        // should return false for a past date
        assertFalse(DateTimeChecker.isValidDate("2000-01-01"));
    }

    @Test
    public void testInvalidDateFormat() {
        // wrong format (slashes instead of dashes), should return false
        assertFalse(DateTimeChecker.isValidDate("2025/12/31"));
    }

    // Tests for isValidDateTime 

    @Test
    public void testValidFutureDateTime() {
        // correct format and future datetime, should return true
        assertTrue(DateTimeChecker.isValidDateTime("2099-12-31T14:30"));
    }

    @Test
    public void testPastDateTime() {
        // datetime with past date part, should return false
        assertFalse(DateTimeChecker.isValidDateTime("2000-01-01T10:00"));
    }

    @Test
    public void testInvalidDateTimeFormat() {
        // wrong format (missing 'T' between date and time), should return false
        assertFalse(DateTimeChecker.isValidDateTime("2025-12-31 14:30"));
    }

    // Datetime contains seconds: should fail
    @Test
    public void testDateTimeContainsSeconds_ShouldFail() {
        assertFalse(DateTimeChecker.isValidDateTime("2025-09-03T13:45:59"));
    }

    // Missing 'T' separator: should fail
    @Test
    public void testMissingTSeparator_ShouldFail() {
        assertFalse(DateTimeChecker.isValidDateTime("2025-09-03 13:45"));
    }

    // Wrong separators in date: should fail
    @Test
    public void testWrongDateSeparators_ShouldFail() {
        assertFalse(DateTimeChecker.isValidDateTime("2025/09/03T13:45"));
    }

    // Too short (missing minutes): should fail
    @Test
    public void testTooShort_ShouldFail() {
        assertFalse(DateTimeChecker.isValidDateTime("2025-09-03T13"));
    }

    // Non-digit character in time: should fail
    @Test
    public void testNonDigitInTime_ShouldFail() {
        assertFalse(DateTimeChecker.isValidDateTime("2025-09-03T12:4a"));
    }

    // Out-of-range time values: should fail
    @Test
    public void testOutOfRangeTime_ShouldFail() {
        assertFalse(DateTimeChecker.isValidDateTime("2025-09-03T24:00"));
        assertFalse(DateTimeChecker.isValidDateTime("2025-09-03T23:60"));
    }

    // Valid future datetime (far future, to avoid dependency on current time): should pass
    @Test
    public void testValidFutureDateTime_ShouldPass() {
        assertTrue(DateTimeChecker.isValidDateTime("2099-12-31T23:59"));
    }
}
