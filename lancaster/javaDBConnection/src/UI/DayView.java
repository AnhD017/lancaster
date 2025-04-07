package UI;

import javax.swing.*;
import java.awt.*;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class DayView {
    private JPanel panel;
    private String month;
    private int year;
    private JLabel titleLabel;
    private CalendarApp app;

    public DayView(CalendarApp app) {
        this.app = app;
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.decode("#142524")); // Aztec color (background)

        titleLabel = new JLabel();
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(Color.decode("#CCD1D2"));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);
    }

    public void setMonthAndYear(String month, int year) {
        this.month = month;
        this.year = year;
        titleLabel.setText("Days in " + month + " " + year);

        // Clear previous content
        panel.removeAll();

        // Add the title label back
        panel.add(titleLabel, BorderLayout.NORTH);

        // Create a panel for the days
        JPanel daysPanel = new JPanel();
        daysPanel.setLayout(new GridLayout(0, 7, 5, 5)); // 7 columns for days of the week
        daysPanel.setBackground(Color.decode("#142524"));

        // Add day labels (Monday, Tuesday, etc.)
        String[] dayLabels = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
        for (String dayLabel : dayLabels) {
            JLabel label = new JLabel(dayLabel, SwingConstants.CENTER);
            label.setForeground(Color.decode("#30C142")); // Apple color
            label.setFont(new Font("Arial", Font.BOLD, 14));
            daysPanel.add(label);
        }

        // Calculate the starting day of the week for the given month and year
        int daysInMonth = getDaysInMonth(month, year);
        LocalDate firstDayOfMonth = LocalDate.of(year, getMonthNumber(month), 1);
        DayOfWeek startingDay = firstDayOfMonth.getDayOfWeek();
        int startColumn = (startingDay.getValue() % 7); // Convert to 0-based index (Monday = 0, Sunday = 6)

        // Add empty spaces for days before the first day of the month
        for (int i = 0; i < startColumn; i++) {
            daysPanel.add(new JLabel()); // Empty cell
        }

        // Add buttons for each day of the month
        for (int day = 1; day <= daysInMonth; day++) {
            final int currentDay = day;
            JButton dayButton = new JButton(String.valueOf(currentDay));
            dayButton.setBackground(Color.decode("#848D94")); // Gray color (button background)
            dayButton.setForeground(Color.decode("#CCD1D2")); // Iron color (text color)
            dayButton.setFont(new Font("Arial", Font.BOLD, 14));
            dayButton.setFocusPainted(false);

            // Add action listener to navigate to EventView
            dayButton.addActionListener(e -> {
                if (month == null || year == 0) {
                    System.err.println("Error: Month or Year is not set in DayView.");
                    return;
                }
                System.out.println("Navigating to EventView for " + month + " " + currentDay + ", " + year);
                app.showEventView(month, currentDay, year); // Pass the year dynamically
            });

            daysPanel.add(dayButton);
        }

        // Add a back button
        JButton backButton = new JButton("Back to Months");
        backButton.setBackground(Color.decode("#30C142"));
        backButton.setForeground(Color.decode("#142524"));
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.addActionListener(e -> app.showMonthView());

        panel.add(daysPanel, BorderLayout.CENTER);
        panel.add(backButton, BorderLayout.SOUTH);

        // Refresh the panel
        panel.revalidate();
        panel.repaint();
    }

    private int getDaysInMonth(String month, int year) {
        int monthNumber = getMonthNumber(month);
        return LocalDate.of(year, monthNumber, 1).lengthOfMonth();
    }

    private int getMonthNumber(String month) {
        switch (month.toLowerCase()) {
            case "january":
                return 1;
            case "february":
                return 2;
            case "march":
                return 3;
            case "april":
                return 4;
            case "may":
                return 5;
            case "june":
                return 6;
            case "july":
                return 7;
            case "august":
                return 8;
            case "september":
                return 9;
            case "october":
                return 10;
            case "november":
                return 11;
            case "december":
                return 12;
            default:
                throw new IllegalArgumentException("Invalid month: " + month);
        }
    }

    public JPanel getPanel() {
        return panel;
    }
}