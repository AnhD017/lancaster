package UI;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class YearFinancialRecord extends JFrame {

    private int currentYear = 2025; // Start at Year 2025

    public YearFinancialRecord() {
        setTitle("Yearly Financial Records");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Yearly Financial Records - Year " + currentYear, SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 1, 10, 10));

        // Income Table
        Object[][] incomeData = fetchYearlyIncomeData(currentYear);
        String[] incomeColumns = {"Year", "Venue Hire Income", "Ticket Sales Income"};
        JTable incomeTable = new JTable(incomeData, incomeColumns);
        JScrollPane incomeScrollPane = new JScrollPane(incomeTable);
        mainPanel.add(incomeScrollPane);

        // Venue Usage Bar Chart Panel
        JPanel chartPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawYearlyComparisonChart(g, currentYear);
            }
        };
        chartPanel.setBackground(Color.WHITE);
        mainPanel.add(chartPanel);

        add(mainPanel, BorderLayout.CENTER);

        // Navigation Buttons
        JPanel navigationPanel = new JPanel();
        JButton prevYearButton = new JButton("Previous Year");
        prevYearButton.addActionListener(e -> {
            currentYear--;
            title.setText("Yearly Financial Records - Year " + currentYear);
            mainPanel.repaint();
        });

        JButton nextYearButton = new JButton("Next Year");
        nextYearButton.addActionListener(e -> {
            currentYear++;
            title.setText("Yearly Financial Records - Year " + currentYear);
            mainPanel.repaint();
        });

        navigationPanel.add(prevYearButton);
        navigationPanel.add(nextYearButton);
        add(navigationPanel, BorderLayout.SOUTH);
    }

    private Object[][] fetchYearlyIncomeData(int year) {
        String url = "jdbc:postgresql://localhost:5432/your_database_name";
        String user = "your_username";
        String password = "your_password";

        Object[][] data = new Object[1][3]; // Data for one year

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT venue_hire_income, ticket_sales_income FROM yearly_data WHERE year = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, year);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                data[0][0] = "Year " + year;
                data[0][1] = "$" + rs.getDouble("venue_hire_income");
                data[0][2] = "$" + rs.getDouble("ticket_sales_income");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching yearly income data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        return data;
    }

    private void drawYearlyComparisonChart(Graphics g, int year) {
        String url = "jdbc:postgresql://localhost:5432/your_database_name";
        String user = "your_username";
        String password = "your_password";

        int currentUsage = 0;
        int previousYearUsage = 0;

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            // Current Year Query
            String currentQuery = "SELECT venue_usage_hours FROM yearly_data WHERE year = ?";
            PreparedStatement currentStmt = conn.prepareStatement(currentQuery);
            currentStmt.setInt(1, year);
            ResultSet currentRs = currentStmt.executeQuery();
            if (currentRs.next()) {
                currentUsage = currentRs.getInt("venue_usage_hours");
            }

            // Previous Year Query
            String previousQuery = "SELECT venue_usage_hours FROM yearly_data WHERE year = ?";
            PreparedStatement previousStmt = conn.prepareStatement(previousQuery);
            previousStmt.setInt(1, year - 1); // Previous year
            ResultSet previousRs = previousStmt.executeQuery();
            if (previousRs.next()) {
                previousYearUsage = previousRs.getInt("venue_usage_hours");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching yearly usage data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
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
