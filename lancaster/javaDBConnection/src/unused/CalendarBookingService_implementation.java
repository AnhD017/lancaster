import java.sql.*;

public class CalendarBookingService_implementation
        implements CalendarBookingService {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/bookings_schema";
    private static final String USER = "root";
    private static final String PASSWORD = "sakib2005"; // Change if needed

    @Override
    public void updateEventSchedule(String eventName, String date, String time,
            int availableSeats) {
        // Updates an event’s available seats based on its name and date.
        String sql = "UPDATE bookings SET availableSeats = ? WHERE eventName = ? " +
                "AND startDate = ? AND startTime = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, availableSeats);
            pstmt.setString(2, eventName);
            pstmt.setDate(3, Date.valueOf(date));
            pstmt.setTime(4, Time.valueOf(time));

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Seats for " + eventName + " on " + date + " at " +
                        time + " have been updated.");
            } else {
                System.out.println("Event not found or no updates made.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // for the method, we are checking if there are available seat >=
    // requestedSeats if yes then return true or return false requestedSeats means
    // can we have this many more additional participants
    @Override
    public boolean checkAvailability(String eventName, String date,
            int requestedSeats) {
        /*
         * Checks if an event has enough available seats for the requested number.
         */
        String sql = "SELECT availableSeats FROM bookings WHERE eventName = ? " +
                "AND startDate = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, eventName);
            pstmt.setDate(2, Date.valueOf(date));

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int availableSeats = rs.getInt("availableSeats");
                    return availableSeats >= requestedSeats;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Return false if event is not found or if there aren't
                      // enough seats
    }

    @Override
    public void reserveSeats(String eventName, String date, int seatCount,
            String groupName) {
        /*
         * Reserves 'seatCount' number of seats for a given event, by the
         * 'groupName' group. The available seats are updated accordingly.
         */
        String sql = "UPDATE bookings SET reservedSeats = reservedSeats + ?, groupName = " +
                "?, availableSeats = availableSeats - ? "
                + "WHERE eventName = ? AND startDate = ? AND availableSeats >= ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, seatCount);
            pstmt.setString(2, groupName);
            pstmt.setInt(3, seatCount);
            pstmt.setString(4, eventName);
            pstmt.setDate(5, Date.valueOf(date));
            pstmt.setInt(6, seatCount); // Ensure there are enough seats

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println(seatCount + " seats have been reserved for " +
                        eventName + " on " + date + " for " + groupName);
            } else {
                System.out.println("Reservation failed: Not enough available seats.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
