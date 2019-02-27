package com.companyhr.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Objects;

/**
 * class CustomDate defines a custom date used for calendar operations
 * date - stores the date
 * bankHoliday - true date is a holiday; false - date is working day
 * dayOfWeek - the day of the week
 * contains getter and setter hashCode and equals methods
 */
public class CustomDate {
    private java.util.Date date;
    private Boolean bankHoliday;
    private String dayOfWeek;

    /**
     * constructor for CustomDate class
     *
     * @param dateAsString the date in a string format
     */
    public CustomDate(String dateAsString) {

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            this.date = format.parse(dateAsString);

            GregorianCalendar calendar = new GregorianCalendar(date.getYear(), date.getMonth(), date.getDay());
            switch (this.date.getDay()) {
                case 0:
                    this.dayOfWeek = "Duminica";
                    this.bankHoliday = true;
                    break;
                case 1:
                    this.dayOfWeek = "Luni";
                    this.bankHoliday = false;
                    break;
                case 2:
                    this.dayOfWeek = "Marti";
                    this.bankHoliday = false;
                    break;
                case 3:
                    this.dayOfWeek = "Miercuri";
                    this.bankHoliday = false;
                    break;
                case 4:
                    this.dayOfWeek = "Joi";
                    this.bankHoliday = false;
                    break;
                case 5:
                    this.dayOfWeek = "Vineri";
                    this.bankHoliday = false;
                    break;
                default:
                    this.dayOfWeek = "Sambata";
                    this.bankHoliday = true;
            }

        } catch (
                ParseException e) {
            System.out.println(e.getMessage() + "- Invalid date");
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomDate)) return false;
        CustomDate that = (CustomDate) o;
        return Objects.equals(date, that.date);
    }

    public java.util.Date getDate() {
        return date;
    }

    public void setDate(java.util.Date date) {
        this.date = date;
    }

    public Boolean getBankHoliday() {
        return bankHoliday;
    }

    public void setBankHoliday(Boolean bankHoliday) {
        this.bankHoliday = bankHoliday;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    @Override
    public int hashCode() {

        return Objects.hash(date);
    }

    @Override
    public String toString() {
        return "CustomDate{" +
                "date=" + date +
                ", bankHoliday=" + bankHoliday +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                '}';
    }
}
