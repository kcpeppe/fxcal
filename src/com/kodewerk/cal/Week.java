package com.kodewerk.cal;


import java.time.LocalDate;

public class Week {

    private LocalDate startofWeekDate;
    private int weekOfYearIndex;
    private Availability availability;
    private Booking booking;

    public Week(LocalDate startOfYear, int index) {
        this.weekOfYearIndex = index;
        this.startofWeekDate = startOfYear.plusDays( ( this.weekOfYearIndex * 7) - 1);
        availability = Availability.AVAILABLE;
    }

    public boolean isAvailable() {
        return ! (isBooked() || isBlocked());
    }

    public boolean isBooked() {
        return availability == Availability.BOOKED;
    }

    public boolean isBlocked() {
        return availability == Availability.BLOCKED;
    }

    public boolean book( Booking booking) {
        if ( ! isAvailable())
            return false;
        booking = booking;
        availability = Availability.BOOKED;
        return true;
    }

    public boolean block() {
        if ( ! isAvailable())
            return false;
        availability = Availability.BLOCKED;
        return true;
    }

    public boolean cancelBooking() {
        if ( isBlocked())
            return false;
        booking = null;
        availability = Availability.AVAILABLE;
        return true;
    }

    public boolean unBlock() {
        if ( ! isBlocked())
            return false;
        availability = Availability.AVAILABLE;
        return true;
    }

    public LocalDate getStartofWeekDate() { return startofWeekDate; }
    public int getWeekOfYearIndex() { return weekOfYearIndex; }
    public Booking getBooking() { return booking; }
    public Availability getAvailability() { return availability; }
}
