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

    @Test
    public void testLeapYear_Feb29_Valid() {
        //Check leap year determination
        assertTrue(DateTimeChecker.isValidDateTime("2096-02-29"));
    }

    @Test
    public void testNonLeapYear_Feb29_Invalid() {
        // Tests whether the 29th day of a non-leap year is valid
        assertFalse(DateTimeChecker.isValidDateTime("2025-02-29"));
    }

    @Test
    public void testFutureDateTimeWithSecond() {
        // correct format and future datetime, should return true
        assertFalse(DateTimeChecker.isValidDateTime("2099-12-31T14:30:33"));
    }

}
