import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
/**
 * Utility class for validating date and datetime strings.
 * <p>
 * Conforms to the project specification:
 * - Date syntax: YYYY-MM-DD (digits and hyphens only)
 * - Date semantic: a valid Gregorian calendar date AND >= today
 * - Datetime syntax: YYYY-MM-DDTHH:MM:SS
 * - Datetime semantic: time is a valid 24-hour clock time
 *
 */
public class DateTimeChecker {

    private DateTimeChecker() {
        /* utility class */
    }

    /**
     * Check whether a date string has valid SYNTAX "YYYY-MM-DD".
     * This method is used to avoid non-year descriptions such as 0001-01-01
     * @param dateString
     * @return true if the string matches the syntax pattern; false otherwise.
     */
    public static boolean hasValidDateSyntax(String dateString) {
        if (dateString == null) return false;
        // Only syntax check: digits + hyphens at the right spots
        // This allows strings like "0001-00-00" to pass SYNTAX (spec says it's syntactically valid).
        return dateString.matches("\\d{4}-\\d{2}-\\d{2}");
    }

    /**
     * Semantic check of the date: the time is the Gregorian calendar time and is >= today.
     * @param dateString
     * @param today
     * @return
     */
    public static boolean isGregorianValidDate(String dateString, LocalDate today) {
        if (!hasValidDateSyntax(dateString)) {
            return false;
        }
        try {
            LocalDate d = LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);
            return !d.isBefore(today);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     *With no today element, normal business calls, the default system day is today
     * @param dateString
     * @return
     */
    public static boolean isGregorianValidDate(String dateString) {
        return isGregorianValidDate(dateString, LocalDate.now());
    }

    /**
     * Check dateString has correct syntax: YYYY-MM-DD
     * Also check the date is later than today's date.
     * 
     * @param dateString
     * @return true if the date is valid, false otherwise.
     */
    public static boolean isValidDate(String dateString) {
        try {
            // Format: YYYY-MM-DD
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
            LocalDate date = LocalDate.parse(dateString, formatter);
            return date.isAfter(LocalDate.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Check datetimeString has correct syntax: YYYY-MM-DDTHH:MM
     * Also check the date part of date time is later than today's date.
     * Datetiems are used for airline flight times.
     * They are in UTC, use 24 hour clock and do not include seconds.
     * 
     * @param dateTimeString
     * @return true if the datetime is valid, false otherwise.
     */
    public static boolean isValidDateTime(String dateTimeString) {
        if (dateTimeString == null) {
            return false;
        }
        try {
            // Format: YYYY-MM-DDTHH:MM (e.g., 2025-09-03T14:30) Update: add :ss because the request ask for.
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            //To do: this written method is bound together of valid time and future time.May better
            // add a new method like checkFutureTime to identify if the time is future time or not.
            // No change this time.
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
            return dateTime.isAfter(LocalDateTime.now());
        } catch (DateTimeParseException e) {
            return false;
        }
        
    }

}
