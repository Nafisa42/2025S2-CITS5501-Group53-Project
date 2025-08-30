import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DateTimeCheckerTest {

    // Tests for isValidDate

    @Test
    public void testValidFutureDate() {
        assertTrue(DateTimeChecker.isValidDate("2099-12-31")); // Far future
    }

    @Test
    public void testPastDate() {
        assertFalse(DateTimeChecker.isValidDate("2000-01-01")); // Way in the past
    }

    @Test
    public void testInvalidDateFormat() {
        assertFalse(DateTimeChecker.isValidDate("2025/12/31")); // wrong separator
    }

    @Test
    public void testTodayDate() {
        // Should return true if date is today
        String today = java.time.LocalDate.now().toString();
        assertTrue(DateTimeChecker.isValidDate(today));
    }

    @Test
    public void testInvalidDayInMonth() {
        assertFalse(DateTimeChecker.isValidDate("2025-04-31")); // April has only 30 days
    }

    @Test
    public void testLeapYearValidDate() {
        assertTrue(DateTimeChecker.isValidDate("2032-02-29")); // Valid leap year date
    }

    // Tests for isValidDateTime

    @Test
    public void testValidFutureDateTime() {
        assertTrue(DateTimeChecker.isValidDateTime("2099-12-31T14:30:00")); // future time
    }

    @Test
    public void testPastDateTime() {
        assertFalse(DateTimeChecker.isValidDateTime("2000-01-01T10:00:00")); // past
    }

    @Test
    public void testInvalidDateTimeFormat() {
        assertFalse(DateTimeChecker.isValidDateTime("2025-12-31 14:30")); // missing T and seconds
    }

    @Test
    public void testValidLeapSecondTime() {
        assertTrue(DateTimeChecker.isValidDateTime("2099-12-31T23:59:59")); // edge of day
    }

    @Test
    public void testInvalidHourOutOfRange() {
        assertFalse(DateTimeChecker.isValidDateTime("2025-10-10T25:00:00")); // hour > 23
    }

    @Test
    public void testTodayDateTimeJustBeforeNow() {
        // even 1 sec in the past should fail
        long now = System.currentTimeMillis() / 1000;
        long oneSecAgo = now - 1;

        java.time.LocalDateTime past = java.time.Instant.ofEpochSecond(oneSecAgo)
            .atZone(java.time.ZoneId.systemDefault())
            .toLocalDateTime();

        String s = past.toString(); // ISO format with seconds
        assertFalse(DateTimeChecker.isValidDateTime(s));
    }
}
