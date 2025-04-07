package UI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CalendarApp {
    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    // References to views
    private Menu menu;
    private MonthView monthView;
    private DayView dayView;
    private EventView eventView;

    public CalendarApp() {
        // Create the main frame
        frame = new JFrame("Calendar App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Set up CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Initialize views
        menu = new Menu(this);
        monthView = new MonthView(this);
        dayView = new DayView(this);
        eventView = new EventView(this);

        // Add views to the CardLayout
        mainPanel.add(menu.getPanel(), "Menu");
        mainPanel.add(monthView.getPanel(), "MonthView");
        mainPanel.add(dayView.getPanel(), "DayView");
        mainPanel.add(eventView.getPanel(), "EventView");

        // Show the Menu initially
        cardLayout.show(mainPanel, "Menu");

        // Add the main panel to the frame
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    // Methods to navigate between views
    public void showMenu() {
        cardLayout.show(mainPanel, "Menu");
    }

    public void showMonthView() {
        cardLayout.show(mainPanel, "MonthView");
    }

    public void showDayView(String monthYear) {
        try {
            if (monthYear == null || monthYear.trim().isEmpty()) {
                System.err.println("Invalid monthYear format: " + monthYear);
                return; // Do nothing if the monthYear is invalid
            }
            String[] parts = monthYear.split(" ");
            if (parts.length < 2) {
                throw new IllegalArgumentException("Invalid monthYear format: " + monthYear);
            }
            String month = parts[0];
            int year = Integer.parseInt(parts[1]);
            dayView.setMonthAndYear(month, year); // Update DayView with the selected month and year
            cardLayout.show(mainPanel, "DayView");
        } catch (Exception e) {
            System.err.println("Error in showDayView: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void showEventView(String month, int day, int year) {
        eventView.setDay(month, day, year); // Pass the year dynamically
        cardLayout.show(mainPanel, "EventView");
    }

    public static void main(String[] args) {
        new CalendarApp();
    }

}