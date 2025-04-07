
import java.sql.*;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        showTable(); // Call the method to display the table
    }

    /**
     * Method to display all records from the bookings table
     */
    public static void showTable() {
        String URL = "jdbc:mysql://sst-stuproj.city.ac.uk:3306/in2033t20";
        String USER = "in2033t20_a";
        String PASSWORD = "seodztXABQo"; // Replace with your MySQL password

        // SQL Query to retrieve all records
        String sql = "SELECT * FROM Booking";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            System.out.println(
                    "ID | Event Name | Event Type | Start Date | End Date | Start Time | End Time | Participants | Max Seats | Available Seats | Reserved Seats | Group Name");

            while (resultSet.next()) {
                // Full texts booking_id client_id start_date end_date status total_cost
                // created_at updated_at

                int bookingID = resultSet.getInt("booking_id");
                int clientID = resultSet.getInt("client_id");
                String startDate = resultSet.getString("start_date");
                String endDate = resultSet.getString("end_date");
                String status = resultSet.getString("status");
                double totalCost = resultSet.getDouble("total_cost");
                String createdAt = resultSet.getString("created_at");
                String updatedAt = resultSet.getString("updated_at");

                // Print the retrieved data
                System.out.printf("%d | %d | %s | %s | %s | %.2f | %s | %s%n",
                        bookingID, clientID, startDate, endDate, status, totalCost, createdAt, updatedAt);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to display events that start on today's date
     */
    public static void displayEventsToday() {
        String URL = "jdbc:mysql://127.0.0.1:3306/bookings_schema";
        String USER = "root";
        String PASSWORD = "sakib2005"; // Replace with your MySQL password

        LocalDate today = LocalDate.now(); // Get current date

        // SQL Query to find events happening today
        String sql = "SELECT eventID, eventName, eventType, startTime, endTime, expectedParticipants, reservedSeats, groupName "
                +
                "FROM bookings WHERE startDate = ?";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setDate(1, Date.valueOf(today)); // Convert LocalDate to SQL Date

            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println("Today's Events: ");
                while (rs.next()) {
                    int eventID = rs.getInt("eventID");
                    String eventName = rs.getString("eventName");
                    String eventType = rs.getString("eventType");
                    String startTime = rs.getString("startTime");
                    String endTime = rs.getString("endTime");
                    int expectedParticipants = rs.getInt("expectedParticipants");
                    int reservedSeats = rs.getInt("reservedSeats");
                    String groupName = rs.getString("groupName"); // May be null

                    // Print details
                    System.out.printf(
                            "ID: %d | Name: %s | Type: %s | Time: %s - %s | Participants: %d | Reserved: %d | Group: %s%n",
                            eventID, eventName, eventType, startTime, endTime, expectedParticipants, reservedSeats,
                            (groupName != null ? groupName : "No Reservations"));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error retrieving today's events!");
            e.printStackTrace();
        }
    }
}
