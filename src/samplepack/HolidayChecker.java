// HolidayChecker.java
package samplepack;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class HolidayChecker {
    public static boolean isOperatingDay(LocalDate date, boolean isNightRoute) {
        DayOfWeek day = date.getDayOfWeek();
        int month = date.getMonthValue();
        int dayOfMonth = date.getDayOfMonth();

        if (isNightRoute) {
            if (day == DayOfWeek.SUNDAY) return false;
            if (month == 1 && dayOfMonth == 1) return false;
            if (month == 5 && (dayOfMonth == 1 || dayOfMonth == 31)) return false;
            if (month == 12 && dayOfMonth == 25) return false;
            if (month == 9 && (dayOfMonth >= 15 && dayOfMonth <= 17)) return false;
            if ((month == 7 && dayOfMonth >= 20) || (month == 8 && dayOfMonth <= 20)) return false;
            if ((month == 12 && dayOfMonth >= 20) || (month == 1 && dayOfMonth <= 10)) return false;
        }

        return !(day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY);
    }
}