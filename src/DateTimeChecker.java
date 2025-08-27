import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utilty class to check date and datetime strings
 * correctly formmatted for the Taohi Flight Booking System commands.
 */
public class DateTimeChecker {

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
            return false;
        
    }

}
