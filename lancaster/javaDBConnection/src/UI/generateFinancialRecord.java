package UI;

import javax.swing.*;
import java.awt.*;

public class generateFinancialRecord extends JFrame {

    public generateFinancialRecord() {
        setTitle("Financial Records");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 1, 10, 10)); // Three buttons vertically aligned

        // "Week View" Button
        JButton weekViewButton = new JButton("Weekly View");
        weekViewButton.setFont(new Font("Arial", Font.BOLD, 16));
        weekViewButton.addActionListener(e -> {
            WeekFinancialRecord weekWindow = new WeekFinancialRecord();
            weekWindow.setVisible(true);
        });
        add(weekViewButton);

        // "Month View" Button
        JButton monthViewButton = new JButton("Monthly View");
        monthViewButton.setFont(new Font("Arial", Font.BOLD, 16));
        monthViewButton.addActionListener(e -> {
            MonthFinancialRecord monthWindow = new MonthFinancialRecord();
            monthWindow.setVisible(true);
        });
        add(monthViewButton);

        // "Year View" Button
        JButton yearViewButton = new JButton("Yearly View");
        yearViewButton.setFont(new Font("Arial", Font.BOLD, 16));
        yearViewButton.addActionListener(e -> {
            YearFinancialRecord yearWindow = new YearFinancialRecord();
            yearWindow.setVisible(true);
        });
        add(yearViewButton);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            generateFinancialRecord frame = new generateFinancialRecord();
            frame.setVisible(true);
        });
    }
}
