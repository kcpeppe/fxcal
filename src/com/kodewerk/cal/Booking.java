package com.kodewerk.cal;

import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class Booking {

    private int week;
    private String activity;
    private String dates;
    private String customer;
    private String location;
    private String notes;
    private BookingStatus bookingStatus;

    public Booking(LocalDate weekStart, String activity, String customer, String location, String notes, BookingStatus bookingStatus) {
        this(weekStart);
        this.activity = activity;
        this.customer = customer;
        this.location = location;
        this.notes = notes;
        this.bookingStatus = bookingStatus;
    }

    public Booking(LocalDate weekStart) {
        TemporalField woy = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();
        this.week = weekStart.get(woy);
        this.dates = weekStart.toString();
        this.bookingStatus = BookingStatus.TENTATIVE;
    }

    public String getDates() {
        return dates;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public int getWeek() {
        return week;
    }

    public String getActivity() {
        return activity;
    }

    public String getCustomer() {
        return customer;
    }

    public String getLocation() {
        return location;
    }

    public String getNotes() {
        return notes;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

}
