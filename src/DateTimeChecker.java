import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utility class to check date and datetime strings
 * for the Tachi Flight Booking System commands.
 */
public final class DateTimeChecker {

    private DateTimeChecker() {
        /* utility class - prevent instantiation */
    }

    /**
     * Check dateString has correct syntax: YYYY-MM-DD
     * Also check the date is later than today's date.
     * 
     * @param dateString
     * @return true if the date is valid, false otherwise.
     */
    // Days in months (non-leap year)
    public static boolean hasValidDateSyntax(String dateString) {

        if (dateString == null){
            return false;
        }
        return dateString.matches("\\d{4}-\\d{2}-\\d{2}");
    }

    /**
     * Semantic check for date: Gregorian-valid AND >= today.
     * @param dateString
     * @param today
     * @return returns false on parse error or date<today
     */
    public static boolean isGregorianValidDate(String dateString, LocalDate today) {

        if (!hasValidDateSyntax(dateString)){
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
     * Overload: use system "today" (system default zone) for semantic date check.
     */
     public static boolean isGregorianValidDate(String dateString) {

         return isGregorianValidDate(dateString, LocalDate.now(ZoneId.systemDefault()));
     }


    /** Check datetimeString has correct syntax: YYYY-MM-DDTHH:MM:SS
     * Also check the date part of date time is later than today's date.
     * Datetiems are used for airline flight times.
     * They are in UTC, use 24 hour clock and do not include seconds.
     *
     * @param dateTimeString
     * @return true if the datetime is valid, false otherwise.
     */
    public static boolean isValidDateTime(String dateTimeString) {

        if (dateTimeString == null){
            return false;
        }
        if (dateTimeString.length() == 10 && hasValidDateSyntax(dateTimeString)) {
            return isGregorianValidDate(dateTimeString);
        }

        if (dateTimeString.length() == 16
                && dateTimeString.charAt(4) == '-'
                && dateTimeString.charAt(7) == '-'
                && dateTimeString.charAt(10) == 'T'
                && dateTimeString.charAt(13) == ':') {

            String datePart = dateTimeString.substring(0, 10);
            String hourStr = dateTimeString.substring(11, 13);
            String minStr  = dateTimeString.substring(14, 16);
            // Verify date syntax and semantics (≥ today)
            if (!isGregorianValidDate(datePart)){
                return false;
            }
            // Verification time range
            if (!hourStr.chars().allMatch(Character::isDigit)){
                return false;
            }
            if (!minStr.chars().allMatch(Character::isDigit)){
                return false;
            }
            int h = Integer.parseInt(hourStr);
            int m = Integer.parseInt(minStr);
            return (0 <= h && h <= 23) && (0 <= m && m <= 59);
        }

        if (dateTimeString.length() == 19
                && dateTimeString.charAt(4) == '-'
                && dateTimeString.charAt(7) == '-'
                && dateTimeString.charAt(10) == 'T'
                && dateTimeString.charAt(13) == ':'
                && dateTimeString.charAt(16) == ':') {

            String datePart = dateTimeString.substring(0, 10);
            if (!isGregorianValidDate(datePart)){
                return false; // If date ≥ today
            }
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                LocalDateTime.parse(dateTimeString, formatter); // If the analysis is passed, the time is considered valid
                return true;
            } catch (DateTimeParseException ex) {
                return false;
            }
        }
        return false;
    }

    /**
     * Brief: Check if datetime >= now.
     * @param : dateTimeString ("YYYY-MM-DDTHH:MM:SS"), zoneId
     * @return: true/false
     */
    public static boolean isFutureDateTime(String dateTimeString, ZoneId zoneId) {
        if (!isValidDateTime(dateTimeString)){
            return false;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime dt = LocalDateTime.parse(dateTimeString, formatter);
        return !dt.isBefore(LocalDateTime.now(Clock.system(zoneId)));
    }

    /**
     * Brief: Overload using isFutureDateTime function.
     * @param : dateTimeString
     * @return: true/false
     */
    public static boolean isFutureDateTime(String dateTimeString) {

        return isFutureDateTime(dateTimeString, ZoneId.systemDefault());
    }

    private static int[] getCurrentDateParts() {
        LocalDate today = LocalDate.now(ZoneId.systemDefault());
        return new int[] { today.getYear(), today.getMonthValue(), today.getDayOfMonth() };
    }

    private static long toEpochSeconds(String input) {

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime dt = LocalDateTime.parse(input, fmt);
        return dt.atZone(ZoneId.systemDefault()).toEpochSecond();
    }
}

/**
    // Utility to check if a string contains only digits
    private static boolean isAllDigits(String s) {
        for (char c : s.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    // Check leap year
    private static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    // Get current date parts: year, month, day
    private static int[] getCurrentDateParts() {
        long millis = System.currentTimeMillis();
        long daysSinceEpoch = millis / (1000 * 60 * 60 * 24);
        int year = 1970;
        int month = 1;

        while (true) {
            int daysInYear = isLeapYear(year) ? 366 : 365;
            if (daysSinceEpoch < daysInYear) break;
            daysSinceEpoch -= daysInYear;
            year++;
        }

        int[] dim = DAYS_IN_MONTH.clone();
        if (isLeapYear(year)) dim[1] = 29;

        while (month <= 12) {
            int daysInMonth = dim[month - 1];
            if (daysSinceEpoch < daysInMonth) break;
            daysSinceEpoch -= daysInMonth;
            month++;
        }

        int day = (int) daysSinceEpoch + 1;
        return new int[] {year, month, day};
    }

    // Compare two dates: returns <0, 0, or >0
    private static int compareDateParts(int y1, int m1, int d1, int y2, int m2, int d2) {
        if (y1 != y2) return y1 - y2;
        if (m1 != m2) return m1 - m2;
        return d1 - d2;
    }

    // Parse datetime to seconds since epoch (simplified)
    private static long toEpochSeconds(String input) {
        String[] dateTimeParts = input.split("T");
        String[] ymd = dateTimeParts[0].split("-");
        String[] hms = dateTimeParts[1].split(":");

        int year = Integer.parseInt(ymd[0]);
        int month = Integer.parseInt(ymd[1]);
        int day = Integer.parseInt(ymd[2]);
        int hour = Integer.parseInt(hms[0]);
        int min = Integer.parseInt(hms[1]);
        int sec = Integer.parseInt(hms[2]);

        long totalDays = 0;
        for (int y = 1970; y < year; y++) {
            totalDays += isLeapYear(y) ? 366 : 365;
        }

        int[] dim = DAYS_IN_MONTH.clone();
        if (isLeapYear(year)) dim[1] = 29;

        for (int m = 1; m < month; m++) {
            totalDays += dim[m - 1];
        }

        totalDays += day - 1;

        return totalDays * 86400 + hour * 3600 + min * 60 + sec;
    }
}
 */
