package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MonthView {
    private JPanel panel;
    private int currentYear;
    private JLabel yearLabel; // Declare yearLabel as a class-level variable

    public MonthView(CalendarApp app) {
        currentYear = 2025; // Default year
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.decode("#142524")); // Aztec color (background)

        // Year navigation bar
        JPanel yearPanel = new JPanel();
        yearPanel.setLayout(new BorderLayout());
        yearPanel.setBackground(Color.decode("#142524"));

        JButton backButton = new JButton("< Back");
        backButton.setBackground(Color.decode("#30C142"));
        backButton.setForeground(Color.decode("#142524"));
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.addActionListener(e -> {
            currentYear--;
            updateYearLabel();
        });

        JButton forwardButton = new JButton("Forward >");
        forwardButton.setBackground(Color.decode("#30C142"));
        forwardButton.setForeground(Color.decode("#142524"));
        forwardButton.setFont(new Font("Arial", Font.BOLD, 14));
        forwardButton.addActionListener(e -> {
            currentYear++;
            updateYearLabel();
        });

        yearLabel = new JLabel(String.valueOf(currentYear), SwingConstants.CENTER); // Initialize yearLabel
        yearLabel.setFont(new Font("Arial", Font.BOLD, 18));
        yearLabel.setForeground(Color.decode("#30C142"));

        yearPanel.add(backButton, BorderLayout.WEST);
        yearPanel.add(yearLabel, BorderLayout.CENTER);
        yearPanel.add(forwardButton, BorderLayout.EAST);

        // Month buttons
        JPanel monthsPanel = new JPanel();
        monthsPanel.setLayout(new GridLayout(3, 4, 10, 10)); // 3 rows, 4 columns for months
        monthsPanel.setBackground(Color.decode("#142524"));

        String[] months = {
                "January", "February", "March", "April",
                "May", "June", "July", "August",
                "September", "October", "November", "December"
        };

        for (String month : months) {
            JButton monthButton = new JButton(month);
            monthButton.setBackground(Color.decode("#30C142")); // Apple color (button background)
            monthButton.setForeground(Color.decode("#142524")); // Aztec color (text color)
            monthButton.setFont(new Font("Arial", Font.BOLD, 16));
            monthButton.setFocusPainted(false);

            // Add action listener to navigate to DayView
            monthButton.addActionListener(e -> {
                String monthYear = month + " " + currentYear; // Properly format monthYear
                app.showDayView(monthYear); // Pass the formatted monthYear to showDayView
            });

            monthsPanel.add(monthButton);
        }

        // Add a "Return to Menu" button
        JButton returnToMenuButton = new JButton("Return to Menu");
        returnToMenuButton.setBackground(Color.decode("#30C142"));
        returnToMenuButton.setForeground(Color.decode("#142524"));
        returnToMenuButton.setFont(new Font("Arial", Font.BOLD, 14));
        returnToMenuButton.addActionListener(e -> app.showMenu());

        panel.add(yearPanel, BorderLayout.NORTH);
        panel.add(monthsPanel, BorderLayout.CENTER);
        panel.add(returnToMenuButton, BorderLayout.SOUTH);
    }

    private void updateYearLabel() {
        yearLabel.setText(String.valueOf(currentYear));
    }

    public JPanel getPanel() {
        return panel;
    }
}