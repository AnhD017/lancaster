package UI;

import javax.swing.*;
import java.awt.*;

public class Menu {
    private JPanel panel;

    public Menu(CalendarApp app) {
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.decode("#142524")); // Aztec color (background)

        // Title
        JLabel titleLabel = new JLabel("Future Menu made by Albesja and Innaya", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.decode("#30C142")); // Apple color
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // Add padding
        panel.add(titleLabel, BorderLayout.NORTH);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(3, 1, 10, 10)); // 3 rows, 1 column, spacing of 10px
        buttonsPanel.setBackground(Color.decode("#142524"));

        // "Make a Booking" Button
        JButton makeBookingButton = new JButton("Make a Booking");
        makeBookingButton.setBackground(Color.decode("#30C142")); // Apple color
        makeBookingButton.setForeground(Color.decode("#142524")); // Aztec color
        makeBookingButton.setFont(new Font("Arial", Font.BOLD, 16));
        buttonsPanel.add(makeBookingButton);


        // "Financial Records" Button
        JButton financialRecordsButton = new JButton("Financial Records");
        financialRecordsButton.setBackground(Color.decode("#30C142")); // Apple color
        financialRecordsButton.setForeground(Color.decode("#142524")); // Aztec color
        financialRecordsButton.setFont(new Font("Arial", Font.BOLD, 16));
        financialRecordsButton.addActionListener(e -> {
            // Call the generateFinancialRecord class to display options
            generateFinancialRecord financialRecordWindow = new generateFinancialRecord();
            financialRecordWindow.setVisible(true); // Display the window
        });
        buttonsPanel.add(financialRecordsButton);


        // "View Calendar" Button
        JButton viewCalendarButton = new JButton("View Calendar");
        viewCalendarButton.setBackground(Color.decode("#30C142")); // Apple color
        viewCalendarButton.setForeground(Color.decode("#142524")); // Aztec color
        viewCalendarButton.setFont(new Font("Arial", Font.BOLD, 16));
        viewCalendarButton.addActionListener(e -> app.showMonthView()); // Redirect to MonthView
        buttonsPanel.add(viewCalendarButton);

        panel.add(buttonsPanel, BorderLayout.CENTER);
    }

    public JPanel getPanel() {
        return panel;
    }

    public static void main(String[] args) {
        new CalendarApp();
    }
}
