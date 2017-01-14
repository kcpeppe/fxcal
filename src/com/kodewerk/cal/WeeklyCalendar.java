package com.kodewerk.cal;

import java.time.LocalDate;
import java.util.ArrayList;


public class WeeklyCalendar extends ArrayList {

    Week[] weeks = new Week[52];

    public WeeklyCalendar() {

    }

    public static WeeklyCalendar calendarForYear(LocalDate year) {
        WeeklyCalendar calendar = new WeeklyCalendar();
        for ( int i = 0; i < 52; i++) {
            calendar.add( i, new Week(year, i));
        }
        return calendar;
    }

    public void add( int index, Week week) {
        weeks[index] = week;
    }

    public Week getWeek( int weekOfYearIndex) {
        return weeks[ weekOfYearIndex];
    }
}
