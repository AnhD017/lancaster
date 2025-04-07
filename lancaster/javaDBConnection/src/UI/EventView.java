package UI;

import models.Event;
import utils.FakeEventGenerator;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EventView {
    private JPanel panel;
    private JLabel titleLabel;
    private JPanel eventsPanel;

    // Store the current month and year
    private String currentMonth;
    private int currentYear;

    public EventView(CalendarApp app) {
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.decode("#142524")); // Aztec color (background)

        titleLabel = new JLabel("", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.decode("#30C142")); // Apple color

        eventsPanel = new JPanel();
        eventsPanel.setLayout(new BoxLayout(eventsPanel, BoxLayout.Y_AXIS));
        eventsPanel.setBackground(Color.decode("#142524"));

        // Add a back button
        JButton backButton = new JButton("Back to Days");
        backButton.setBackground(Color.decode("#30C142"));
        backButton.setForeground(Color.decode("#142524"));
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.addActionListener(e -> {
            // Pass the correct month and year to navigate back to DayView
            app.showDayView(currentMonth + " " + currentYear);
        });

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(new JScrollPane(eventsPanel), BorderLayout.CENTER);
        panel.add(backButton, BorderLayout.SOUTH);
    }

    public void setDay(String month, int day, int year) {
        // Store the current month and year for navigation
        this.currentMonth = month;
        this.currentYear = year; // Dynamically update the year

        titleLabel.setText("Events for " + month + " " + day + ", " + year);

        // Clear previous events
        eventsPanel.removeAll();

        // Get all static events
        List<Event> allEvents = getAllStaticEvents();

        // Filter events for the selected date
        List<Event> filteredEvents = new ArrayList<>();
        for (Event event : allEvents) {
            if (event.isVisibleOn(day, month) && event.getYear() == year) {
                filteredEvents.add(event);
            }
        }

        // Display filtered events
        if (!filteredEvents.isEmpty()) {
            for (Event event : filteredEvents) {
                JPanel eventPanel = new JPanel();
                eventPanel.setLayout(new GridLayout(5, 1));
                eventPanel.setBackground(Color.decode("#30C142")); // Apple color
                eventPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding around the event
                                                                                       // panel

                JLabel nameLabel = new JLabel("Name: " + event.getEventName());
                nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
                nameLabel.setForeground(Color.WHITE); // Set text color to white for better contrast

                JLabel startLabel = new JLabel("Start: " + event.getStartTime());
                startLabel.setForeground(Color.WHITE);

                JLabel endLabel = new JLabel("End: " + event.getEndTime());
                endLabel.setForeground(Color.WHITE);

                JLabel roomLabel = new JLabel("Room: " + event.getRooms());
                roomLabel.setForeground(Color.WHITE);

                eventPanel.add(nameLabel);
                eventPanel.add(startLabel);
                eventPanel.add(endLabel);
                eventPanel.add(roomLabel);

                // Add spacing between events
                JPanel wrapperPanel = new JPanel(new BorderLayout());
                wrapperPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Add vertical spacing
                wrapperPanel.setBackground(Color.decode("#142524")); // Match the background color of the main panel
                wrapperPanel.add(eventPanel, BorderLayout.CENTER);

                eventsPanel.add(wrapperPanel);
            }
        } else {
            // Show a message if no events are available
            JLabel noEventsLabel = new JLabel("No events scheduled for this day", SwingConstants.CENTER);
            noEventsLabel.setFont(new Font("Arial", Font.BOLD, 16));
            noEventsLabel.setForeground(Color.decode("#CCD1D2")); // Iron color
            eventsPanel.add(noEventsLabel);
        }

        // Refresh the panel
        eventsPanel.revalidate();
        eventsPanel.repaint();
    }

    private List<Event> getAllStaticEvents() {
        List<Event> events = new ArrayList<>();

        // Single-day Event 1
        events.add(new Event(
                "Morning Meeting",
                "Team meeting to discuss project updates",
                "John Doe",
                1, "April", 1, "April", 2025, // Single-day event
                9, 0, 10, 0, "Main Hall"));

        // Single-day Event 2
        events.add(new Event(
                "Times and Sorting",
                "Testing sorting functionality",
                "John Doe",
                1, "April", 1, "April", 2025, // Single-day event
                9, 0, 10, 0, "Main Hall"));

        // Multi-day Event 1
        events.add(new Event(
                "Workshop",
                "Hands-on workshop on software development",
                "Jane Smith",
                1, "April", 5, "April", 2025, // Multi-day event spanning 5 days
                11, 0, 13, 0, "Meeting Room 1"));

        // Multi-day Event 2
        events.add(new Event(
                "Wedding by Blabl Blah",
                "Some wedding description",
                "Jane Smith",
                1, "April", 7, "April", 2026, // Multi-day event spanning a week
                11, 0, 13, 0, "Meeting Room 1"));

        // Single-day Event 3
        events.add(new Event(
                "Valentine's Day Celebration",
                "Special Valentine's Day event",
                "Jane Smith",
                14, "February", 14, "February", 2026, // Single-day event
                18, 0, 22, 0, "Banquet Hall"));

        // Multi-day Event 3
        events.add(new Event(
                "Movie Marathon",
                "Spider-Man movie marathon",
                "Jane Smith",
                10, "May", 16, "May", 2027, // Multi-day event spanning a week
                11, 0, 13, 0, "Meeting Room 1"));

        return events;
    }

    public JPanel getPanel() {
        return panel;
    }
}