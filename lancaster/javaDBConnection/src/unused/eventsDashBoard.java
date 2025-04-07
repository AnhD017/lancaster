

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;

public class eventsDashBoard implements ActionListener {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/bookings_schema";
    private static final String USER = "root";
    private static final String PASSWORD = "sakib2005";

    private JButton todayBtn, searchBtn, resetBtn;
    private JTextField searchField;
    private JPanel mainPanel, eventsPanel;
    private JFrame frame;

    public eventsDashBoard() {
        // Create frame
        frame = new JFrame("Events Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(850, 850);
        frame.setLayout(new BorderLayout());

        // Title
        JLabel titleLabel = new JLabel("This is the Events Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setOpaque(true);  // Make the label's background visible
        titleLabel.setBackground(Color.red);
        titleLabel.setForeground(Color.WHITE); // Optional: Change text color for better contrast
        frame.add(titleLabel, BorderLayout.NORTH);


        // **Control Panel (Search & Filter Section)**
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        controlPanel.setPreferredSize(new Dimension(800, 100));

        todayBtn = new JButton("Show Ongoing Events");
        todayBtn.addActionListener(this);

        searchField = new JTextField(20);
        searchBtn = new JButton("Search");
        searchBtn.addActionListener(this);

        resetBtn = new JButton("Reset");
        resetBtn.addActionListener(this);

        controlPanel.add(todayBtn);
        controlPanel.add(searchField);
        controlPanel.add(searchBtn);
        controlPanel.add(resetBtn);

        frame.add(controlPanel, BorderLayout.CENTER);

        // **Events Display Section**
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        eventsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        eventsPanel.setBackground(Color.yellow);
        eventsPanel.setPreferredSize(new Dimension(700, 700));

        JScrollPane scrollPane = new JScrollPane(eventsPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        frame.add(mainPanel, BorderLayout.SOUTH);

        // Load all events initially
        loadEvents(null, null);

        frame.setVisible(true);
    }

    // **Load events (supports date & name filtering)**
    private void loadEvents(String dateFilter, String nameFilter) {
        eventsPanel.removeAll(); // Clear old content

        // Build SQL query based on filters
        String sql = "SELECT * FROM Bookings";
        if (dateFilter != null) {
            sql = "SELECT * FROM Bookings WHERE startDate <= ? AND endDate >= ?";
        } else if (nameFilter != null) {
            sql = "SELECT * FROM Bookings WHERE eventName LIKE ?";
        }

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (dateFilter != null) {
                pstmt.setString(1, dateFilter);
                pstmt.setString(2, dateFilter);
            } else if (nameFilter != null) {
                pstmt.setString(1, "%" + nameFilter + "%");
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int bookingID = rs.getInt("eventID");
                    String startDate = rs.getString("startDate");
                    String endDate = rs.getString("endDate");
                    int expectedParticipants = rs.getInt("expectedParticipants");
                    String eventType = rs.getString("eventType");
                    String eventName = rs.getString("eventName");

                    JPanel eventPanel = new JPanel();
                    eventPanel.setLayout(new GridLayout(3, 1));
                    eventPanel.setBackground(Color.GREEN);
                    eventPanel.setPreferredSize(new Dimension(400, 100));
                    eventPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

                    eventPanel.add(new JLabel("ID: " + bookingID));
                    eventPanel.add(new JLabel("Event Name: " + eventName));
                    eventPanel.add(new JLabel("Event Type: " + eventType));
                    eventPanel.add(new JLabel("Start: " + startDate));
                    eventPanel.add(new JLabel("End: " + endDate));
                    eventPanel.add(new JLabel("Participants: " + expectedParticipants));

                    eventsPanel.add(eventPanel);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Refresh UI
        eventsPanel.revalidate();
        eventsPanel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == todayBtn) {
            String todayDate = LocalDate.now().toString();
            loadEvents(todayDate, null);
        } else if (e.getSource() == searchBtn) {
            String searchText = searchField.getText().trim();
            if (!searchText.isEmpty()) {
                loadEvents(null, searchText);
            }
        } else if (e.getSource() == resetBtn) {
            searchField.setText("");
            loadEvents(null, null);
        }
    }

    public static void main(String[] args) {
        new eventsDashBoard();
    }
}
