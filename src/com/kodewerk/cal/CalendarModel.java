package com.kodewerk.cal;


import java.time.LocalDate;

public class CalendarModel {

    WeeklyCalendar calendar;

    public CalendarModel(LocalDate startDate) {
        calendar = WeeklyCalendar.calendarForYear( startDate);
    }

    public WeeklyCalendar getWeeklyCalendar() { return calendar; }

}
