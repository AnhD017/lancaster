package UI;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class WeekFinancialRecord extends JFrame {

    private int currentWeek = 1; // Start at Week 1
    private LocalDate startDate; // Start date of the year (e.g., 1/1/2025)

    public WeekFinancialRecord() {
        setTitle("Weekly Financial Records");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize the start date (e.g., January 1, 2025)
        startDate = LocalDate.of(2025, 1, 1);

        JLabel title = new JLabel("Weekly Financial Records - " + getDateRange(currentWeek), SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 1, 10, 10));

        // Income Table
        Object[][] incomeData = fetchWeeklyIncomeData(currentWeek);
        String[] incomeColumns = {"Week Range", "Venue Hire Income", "Ticket Sales Income"};
        JTable incomeTable = new JTable(incomeData, incomeColumns);
        JScrollPane incomeScrollPane = new JScrollPane(incomeTable);
        mainPanel.add(incomeScrollPane);

        // Venue Usage Bar Chart Panel
        JPanel chartPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawWeeklyComparisonChart(g, currentWeek);
            }
        };
        chartPanel.setBackground(Color.WHITE);
        mainPanel.add(chartPanel);

        add(mainPanel, BorderLayout.CENTER);

        // Navigation Buttons
        JPanel navigationPanel = new JPanel();
        JButton prevWeekButton = new JButton("Previous Week");
        prevWeekButton.addActionListener(e -> {
            currentWeek = (currentWeek == 1) ? 52 : currentWeek - 1; // Wrap to Week 52 if Week 1
            title.setText("Weekly Financial Records - " + getDateRange(currentWeek));
            mainPanel.repaint();
        });

        JButton nextWeekButton = new JButton("Next Week");
        nextWeekButton.addActionListener(e -> {
            currentWeek = (currentWeek == 52) ? 1 : currentWeek + 1; // Wrap to Week 1 if Week 52
            title.setText("Weekly Financial Records - " + getDateRange(currentWeek));
            mainPanel.repaint();
        });

        navigationPanel.add(prevWeekButton);
        navigationPanel.add(nextWeekButton);
        add(navigationPanel, BorderLayout.SOUTH);
    }

    private String getDateRange(int week) {
        // Calculate the start and end dates for the given week
        LocalDate weekStart = startDate.plusDays((week - 1) * 7);
        LocalDate weekEnd = weekStart.plusDays(6);

        // Format the dates as "1/1/2025 - 8/1/2025"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        return weekStart.format(formatter) + " - " + weekEnd.format(formatter);
    }

    private Object[][] fetchWeeklyIncomeData(int week) {
        String url = "jdbc:postgresql://localhost:5432/your_database_name";
        String user = "your_username";
        String password = "your_password";

        Object[][] data = new Object[1][3]; // Data for one week

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            // Query to calculate income data for the given week
            String query = "SELECT income_type, SUM(amount) AS total_income " +
                    "FROM FinancialRecord " +
                    "WHERE week = ? " +
                    "GROUP BY income_type";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, week);
            ResultSet rs = stmt.executeQuery();

            // Initialize data
            data[0][0] = getDateRange(week); // Week range as a string
            double venueHireIncome = 0.0;
            double ticketSalesIncome = 0.0;

            // Process results
            while (rs.next()) {
                String incomeType = rs.getString("income_type");
                double totalIncome = rs.getDouble("total_income");

                if ("venue_hire".equalsIgnoreCase(incomeType)) {
                    venueHireIncome = totalIncome;
                } else if ("ticket_sales".equalsIgnoreCase(incomeType)) {
                    ticketSalesIncome = totalIncome;
                }
            }

            // Populate data array
            data[0][1] = "$" + venueHireIncome;
            data[0][2] = "$" + ticketSalesIncome;

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching weekly income data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        return data;
    }


    private void drawWeeklyComparisonChart(Graphics g, int week) {
        String url = "jdbc:postgresql://localhost:5432/your_database_name";
        String user = "your_username";
        String password = "your_password";

        int currentUsage = 0;
        int previousYearUsage = 0;

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            // Current Year Query
            String currentQuery = "SELECT venue_usage_hours FROM weekly_data WHERE week = ? AND year = 2025";
            PreparedStatement currentStmt = conn.prepareStatement(currentQuery);
            currentStmt.setInt(1, week);
            ResultSet currentRs = currentStmt.executeQuery();
            if (currentRs.next()) {
                currentUsage = currentRs.getInt("venue_usage_hours");
            }

            // Previous Year Query
            String previousQuery = "SELECT venue_usage_hours FROM weekly_data WHERE week = ? AND year = 2024";
            PreparedStatement previousStmt = conn.prepareStatement(previousQuery);
            previousStmt.setInt(1, week);
            ResultSet previousRs = previousStmt.executeQuery();
            if (previousRs.next()) {
                previousYearUsage = previousRs.getInt("venue_usage_hours");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching weekly usage data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
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
