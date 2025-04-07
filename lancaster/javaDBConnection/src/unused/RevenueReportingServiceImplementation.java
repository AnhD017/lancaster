import java.sql.*;

public class RevenueReportingServiceImplementation
        implements RevenueReportingService {
    private static final String URL = "temp";
    private static final String USER = "temp";
    private static final String PASSWORD = "temp"; // to be modified

    @Override
    public void generateSalesReport(String fromDate, String toDate) {
        String sql = "SQL string";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // get fromDate and toDate
            // generate a standardised sales report using database fields
            // return report
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public double getEventRevenue(String eventID, String date) {
        double eventRevenue = 0;
        String sql = "SQL string";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // get an event by ID and date (CPK?)
            // find the total revenue (after counting refunds)
            // return revenue
            eventRevenue = 0; // update eventRevenue here
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return eventRevenue;
    }

    @Override
    public void SendAudienceDemographicReport(String eventID, String date,
            String demographicsData) {
        String sql = "SQL string";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // get event using eventID, date, and then provide the demographicsData
            // do something with the demoData?
            // return nothing?
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
