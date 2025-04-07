public interface RevenueReportingService {

    void generateSalesReport(String fromDate, String toDate);

    double getEventRevenue(String eventID, String date);

    void SendAudienceDemographicReport(String eventID, String date,
            String demographicsData);
}
