import java.util.List;

public interface MarketingDataAccess {
    List<FilmBooking> fetchFilmSchedule(Object placeholder);

    List<MeetingBooking> fetchMeetingWorkshops(Object placeholder);

    List<FriendsHold> fetchFriendsHoldsForShow(String showID, Object otherData);

}
