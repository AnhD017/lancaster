import java.time.LocalDateTime;
import java.util.List;

public interface BoxOfficeDataAccess {
    List<TicketSales> fetchTicketSalesData(String showID, Object placeholder);
    List<SeatRestriction> fetchSeatRestrictions(String eventID, Object seatData);
    List<RefundInfo> fetchRefundsForEvent(String eventID);

}
