import java.time.LocalDateTime;
import java.util.List;

public interface MarketingRequirements {
    List<FilmBooking> getFilmSchedule(LocalDateTime from, LocalDateTime to);
    List<MeetingBooking> getMarketingMeetings(LocalDateTime from, LocalDateTime to);
    List<RoomSetup> getRoomSetupDetails(String roomID, LocalDateTime date);
    List<RevenueReport> getRevenueData(LocalDateTime from, LocalDateTime to);
}
