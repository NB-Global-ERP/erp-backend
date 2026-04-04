package com.nb.globalerp.training.sitebackendglobalerp.utils;

import java.time.*;
import java.util.Set;

public class WorkCalendarService {
    private static final Set<MonthDay> HOLIDAYS = Set.of(
            MonthDay.of(1, 1),
            MonthDay.of(1, 2),
            MonthDay.of(1, 3),
            MonthDay.of(1, 4),
            MonthDay.of(1, 5),
            MonthDay.of(1, 6),
            MonthDay.of(1, 7),
            MonthDay.of(2, 23),
            MonthDay.of(3, 8),
            MonthDay.of(5, 1),
            MonthDay.of(5, 9),
            MonthDay.of(6, 12),
            MonthDay.of(11, 4)
    );

    private static boolean isWorkingDay(LocalDate date) {
        return !isWeekend(date) && !isHoliday(date);
    }

    private static boolean isWeekend(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }

    private static boolean isHoliday(LocalDate date) {
        return HOLIDAYS.contains(MonthDay.from(date));
    }

    public static Instant calculateEndDate(Instant start, int workingDays) {
        ZoneId zone = ZoneId.of("Europe/Moscow");
        if (workingDays <= 0) {
            throw new IllegalArgumentException("workingDays must be > 0");
        }

        LocalDate date = start.atZone(zone).toLocalDate();

        int addedDays = 0;

        while (addedDays < workingDays) {
            date = date.plusDays(1);

            if (isWorkingDay(date)) {
                addedDays++;
            }
        }

        return date.atStartOfDay(zone).toInstant();
    }
}
