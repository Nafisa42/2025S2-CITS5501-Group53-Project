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
    // Days in months (non-leap year)
    private static final int[] DAYS_IN_MONTH = {
        31, 28, 31, 30, 31, 30,
        31, 31, 30, 31, 30, 31
    };

    public static boolean isValidDate(String input) {
        if (input == null || input.length() != 10) return false;
        if (input.charAt(4) != '-' || input.charAt(7) != '-') return false;

        String yearStr = input.substring(0, 4);
        String monthStr = input.substring(5, 7);
        String dayStr = input.substring(8, 10);

        if (!isAllDigits(yearStr) || !isAllDigits(monthStr) || !isAllDigits(dayStr)) return false;

        int year = Integer.parseInt(yearStr);
        int month = Integer.parseInt(monthStr);
        int day = Integer.parseInt(dayStr);

        if (month < 1 || month > 12) return false;
        int maxDay = DAYS_IN_MONTH[month - 1];

        // Leap year check
        if (month == 2 && isLeapYear(year)) maxDay = 29;
        if (day < 1 || day > maxDay) return false;

        // Compare with today's date
        int[] now = getCurrentDateParts(); // {year, month, day}
        int cmp = compareDateParts(year, month, day, now[0], now[1], now[2]);
        return cmp >= 0;
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
    public static boolean isValidDateTime(String input) {
        if (input == null || input.length() != 19) return false;
        if (input.charAt(4) != '-' || input.charAt(7) != '-' || input.charAt(10) != 'T'
            || input.charAt(13) != ':' || input.charAt(16) != ':') return false;

        String datePart = input.substring(0, 10);
        String hourStr = input.substring(11, 13);
        String minuteStr = input.substring(14, 16);
        String secondStr = input.substring(17, 19);

        if (!isValidDate(datePart)) return false;
        if (!isAllDigits(hourStr) || !isAllDigits(minuteStr) || !isAllDigits(secondStr)) return false;

        int hour = Integer.parseInt(hourStr);
        int minute = Integer.parseInt(minuteStr);
        int second = Integer.parseInt(secondStr);

        if (hour < 0 || hour > 23 || minute < 0 || minute > 59 || second < 0 || second > 59)
            return false;

        long inputEpoch = toEpochSeconds(input);
        long nowEpoch = System.currentTimeMillis() / 1000;

        return inputEpoch >= nowEpoch;
    }


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
