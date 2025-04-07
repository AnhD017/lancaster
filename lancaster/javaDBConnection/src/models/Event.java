package models;

import java.util.ArrayList;
import java.util.List;

public class Event {
    private String name;
    private String description;
    private String customer;
    private int startDay;
    private String startMonth;
    private int endDay;
    private String endMonth;
    private int year; // Year is the same for the entire event
    private int startHour;
    private int startMinute;
    private int endHour;
    private int endMinute;
    private String room;

    public Event(String name, String description, String customer, int startDay, String startMonth, int endDay,
            String endMonth, int year, int startHour, int startMinute, int endHour, int endMinute, String room) {
        this.name = name;
        this.description = description;
        this.customer = customer;
        this.startDay = startDay;
        this.startMonth = startMonth;
        this.endDay = endDay;
        this.endMonth = endMonth;
        this.year = year;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
        this.room = room;
    }

    public String getEventName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCustomerName() {
        return customer;
    }

    public int getStartDay() {
        return startDay;
    }

    public String getStartMonth() {
        return startMonth;
    }

    public int getEndDay() {
        return endDay;
    }

    public String getEndMonth() {
        return endMonth;
    }

    public int getYear() {
        return year;
    }

    public String getStartTime() {
        return String.format("%02d:%02d", startHour, startMinute);
    }

    public String getEndTime() {
        return String.format("%02d:%02d", endHour, endMinute);
    }

    public String getRooms() {
        return room;
    }

    public boolean isWithinTimeSlot(int hour) {
        return hour >= startHour && hour < endHour;
    }

    /**
     * Checks if the event is visible on a specific day and month.
     */
    public boolean isVisibleOn(int day, String month) {
        if (!startMonth.equals(endMonth)) {
            // Handle multi-month events
            if (month.equals(startMonth) && day >= startDay) {
                return true;
            } else if (month.equals(endMonth) && day <= endDay) {
                return true;
            }
        } else {
            // Single-month event
            if (month.equals(startMonth) && day >= startDay && day <= endDay) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        List<Event> events = new ArrayList<>();
        events.add(new Event(
                "Conference",
                "Annual Tech Conference",
                "John Doe",
                28, "April", 30, "April", 2025, // Multi-day event
                9, 0, 17, 0, "Main Hall"));

        events.add(new Event(
                "Workshop",
                "Software Development Workshop",
                "Jane Smith",
                29, "April", 2, "May", 2025, // Multi-month event
                10, 0, 15, 0, "Room 101"));

        // Check visibility of events on specific days
        for (int day = 28; day <= 30; day++) {
            for (Event event : events) {
                if (event.isVisibleOn(day, "April")) {
                    System.out.println("Event on " + day + " April: " + event.getEventName());
                }
            }
        }

        for (int day = 1; day <= 2; day++) {
            for (Event event : events) {
                if (event.isVisibleOn(day, "May")) {
                    System.out.println("Event on " + day + " May: " + event.getEventName());
                }
            }
        }
    }
}