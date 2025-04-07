package UI;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.*;

public class MonthFinancialRecord extends JFrame {

    private int currentMonth = 1; // Start at January

    public MonthFinancialRecord() {
        setTitle("Monthly Financial Records");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Monthly Financial Records - " + getMonthName(currentMonth), SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 1, 10, 10));

        // Income Table
        Object[][] incomeData = fetchMonthlyIncomeData(currentMonth);
        String[] incomeColumns = {"Month", "Venue Hire Income", "Ticket Sales Income"};
        JTable incomeTable = new JTable(incomeData, incomeColumns);
        JScrollPane incomeScrollPane = new JScrollPane(incomeTable);
        mainPanel.add(incomeScrollPane);

        // Venue Usage Bar Chart Panel
        JPanel chartPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawMonthlyComparisonChart(g, currentMonth);
            }
        };
        chartPanel.setBackground(Color.WHITE);
        mainPanel.add(chartPanel);

        add(mainPanel, BorderLayout.CENTER);

        // Navigation Buttons
        JPanel navigationPanel = new JPanel();
        JButton prevMonthButton = new JButton("Previous Month");
        prevMonthButton.addActionListener(e -> {
            currentMonth = (currentMonth == 1) ? 12 : currentMonth - 1; // Wrap to December if January
            title.setText("Monthly Financial Records - " + getMonthName(currentMonth));
            mainPanel.repaint();
        });

        JButton nextMonthButton = new JButton("Next Month");
        nextMonthButton.addActionListener(e -> {
            currentMonth = (currentMonth == 12) ? 1 : currentMonth + 1; // Wrap to January if December
            title.setText("Monthly Financial Records - " + getMonthName(currentMonth));
            mainPanel.repaint();
        });

        navigationPanel.add(prevMonthButton);
        navigationPanel.add(nextMonthButton);
        add(navigationPanel, BorderLayout.SOUTH);
    }

    private String getMonthName(int monthIndex) {
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return months[monthIndex - 1];
    }

    private Object[][] fetchMonthlyIncomeData(int month) {
        String url = "jdbc:postgresql://localhost:5432/your_database_name";
        String user = "your_username";
        String password = "your_password";

        Object[][] data = new Object[1][3]; // Data for one month

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT venue_hire_income, ticket_sales_income FROM monthly_data WHERE month = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, month);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                data[0][0] = getMonthName(month);
                data[0][1] = "$" + rs.getDouble("venue_hire_income");
                data[0][2] = "$" + rs.getDouble("ticket_sales_income");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching monthly income data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        return data;
    }

    private void drawMonthlyComparisonChart(Graphics g, int month) {
        String url = "jdbc:postgresql://localhost:5432/your_database_name";
        String user = "your_username";
        String password = "your_password";

        int currentUsage = 0;
        int previousYearUsage = 0;

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            // Current Year Query
            String currentQuery = "SELECT venue_usage_hours FROM monthly_data WHERE month = ? AND year = 2025";
            PreparedStatement currentStmt = conn.prepareStatement(currentQuery);
            currentStmt.setInt(1, month);
            ResultSet currentRs = currentStmt.executeQuery();
            if (currentRs.next()) {
                currentUsage = currentRs.getInt("venue_usage_hours");
            }

            // Previous Year Query
            String previousQuery = "SELECT venue_usage_hours FROM monthly_data WHERE month = ? AND year = 2024";
            PreparedStatement previousStmt = conn.prepareStatement(previousQuery);
            previousStmt.setInt(1, month);
            ResultSet previousRs = previousStmt.executeQuery();
            if (previousRs.next()) {
                previousYearUsage = previousRs.getInt("venue_usage_hours");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching monthly usage data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        // Draw the bar chart
        int barWidth = 100;
        int barHeightCurrent = currentUsage * 5; // Scale usage data
        int barHeightPrevious = previousYearUsage * 5;

        g.setColor(Color.BLUE);
        g.fillRect(100, 400 - barHeightCurrent, barWidth, barHeightCurrent);
        g.drawString("Current Year", 100, 420);

        g.setColor(Color.RED);
        g.fillRect(250, 400 - barHeightPrevious, barWidth, barHeightPrevious);
        g.drawString("Previous Year", 250, 420);

        g.setColor(Color.BLACK);
        g.drawString("Venue Usage Comparison", 150, 450);
    }
}
