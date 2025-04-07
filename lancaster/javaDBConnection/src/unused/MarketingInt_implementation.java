import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

// Implementation of the MarketingRequirements interface
public class MarketingInt_implementation implements MarketingRequirements {
    // Database connection details
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/bookings_schema";
    private static final String USER = "root";
    private static final String PASSWORD = "sakib2005"; // Change if needed

    @Override
    public List<FilmBooking> getFilmSchedule(LocalDateTime from, LocalDateTime to) {
        List<FilmBooking> films = new ArrayList<>();
        String sql = "SELECT * FROM Bookings WHERE eventType = 'Film' AND " +
                "(startDate >= ? AND endDate <= ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Setting parameters for the query
            stmt.setDate(1, Date.valueOf(from.toLocalDate()));
            stmt.setDate(2, Date.valueOf(to.toLocalDate()));
            ResultSet rs = stmt.executeQuery();

            // Processing result set
            while (rs.next()) {
                films.add(new FilmBooking(
                        rs.getInt("eventID"),
                        rs.getString("eventName"),
                        rs.getDate("startDate").toLocalDate(),
                        rs.getDate("endDate").toLocalDate(),
                        rs.getTime("startTime").toLocalTime(),
                        rs.getTime("endTime").toLocalTime(),
                        rs.getInt("expectedParticipants"),
                        rs.getInt("availableSeats")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exception
        }
        return films;
    }

    @Override
    public List<MeetingBooking> getMarketingMeetings(LocalDateTime from, LocalDateTime to) {
        List<MeetingBooking> meetings = new ArrayList<>();
        String sql = "SELECT * FROM Bookings WHERE eventType = 'Meeting' AND " +
                "(startDate >= ? AND endDate <= ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Setting parameters for the query
            stmt.setDate(1, Date.valueOf(from.toLocalDate()));
            stmt.setDate(2, Date.valueOf(to.toLocalDate()));
            ResultSet rs = stmt.executeQuery();

            // Processing result set
            while (rs.next()) {
                meetings.add(new MeetingBooking(
                        rs.getInt("eventID"),
                        rs.getString("eventName"),
                        rs.getString("groupName"),
                        rs.getDate("startDate").toLocalDate(),
                        rs.getDate("endDate").toLocalDate(),
                        rs.getTime("startTime").toLocalTime(),
                        rs.getTime("endTime").toLocalTime(),
                        rs.getInt("reservedSeats")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exception
        }
        return meetings;
    }




    //WE NEED MORE DETAILS OF HOW A ROOM SETUP OBJECT SHOULD BE PRESENTED AS
//    WE DONT REALLY KNOW HOW IT SHOULD LOOK LIKE OR WHATS THE FORMAT OF THE DATA RETURNED.
    @Override
    public List<RoomSetup> getRoomSetupDetails(String roomID, LocalDateTime date) {
        String sql = "SQL string";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // sql statement
            // get RoomID
            // get date
            // return both
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<RevenueReport> getRevenueData(LocalDateTime from, LocalDateTime to) {
        String sql = "SQL string";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // sql statement
            // get fromDate
            // get toDate
            // return all required revenue data between dates
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }





//    THIS PART IS JUST TESTING PURPOSES AND ALSO AN EXAMPLE OF HOW THE METHODS ABOVE ARE USED.
    public static void main(String[] args) {
        // Instantiate the implementation
        MarketingRequirements marketing = new MarketingInt_implementation();

        // Define the date range for filtering events
        LocalDateTime from = LocalDateTime.of(2025, 3, 1, 0, 0);
        LocalDateTime to = LocalDateTime.of(2025, 3, 31, 23, 59);

        // Retrieve and display film schedule
        System.out.println("Film Schedule:");
        List<FilmBooking> films = marketing.getFilmSchedule(from, to);
        if (films.isEmpty()) {
            System.out.println("No films found in the given date range.");
        } else {
            for (FilmBooking film : films) {
                System.out.println("Event ID: " + film.getEventID());
                System.out.println("Event Name: " + film.getEventName());
                System.out.println("Start Date: " + film.getStartDate());
                System.out.println("End Date: " + film.getEndDate());
                System.out.println("Start Time: " + film.getStartTime());
                System.out.println("End Time: " + film.getEndTime());
                System.out.println("Expected Participants: " + film.getExpectedParticipants());
                System.out.println("Available Seats: " + film.getAvailableSeats());
                System.out.println("----------------------------");
            }
        }

        // Retrieve and display marketing meetings
        System.out.println("\nMarketing Meetings:");
        List<MeetingBooking> meetings = marketing.getMarketingMeetings(from, to);
        if (meetings.isEmpty()) {
            System.out.println("No marketing meetings found in the given date range.");
        } else {
            for (MeetingBooking meeting : meetings) {
                System.out.println("Event ID: " + meeting.getEventID());
                System.out.println("Event Name: " + meeting.getEventName());
                System.out.println("Group Name: " + meeting.getGroupName());
                System.out.println("Start Date: " + meeting.getStartDate());
                System.out.println("End Date: " + meeting.getEndDate());
                System.out.println("Start Time: " + meeting.getStartTime());
                System.out.println("End Time: " + meeting.getEndTime());
                System.out.println("Reserved Seats: " + meeting.getReservedSeats());
                System.out.println("----------------------------");
            }
        }
    }
}
