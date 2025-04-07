public interface CalendarBookingService {
    void updateEventSchedule(String eventName, String date, String time, int availableSeats);
    boolean checkAvailability(String eventName, String date, int requestedSeats);
    void reserveSeats(String eventName, String date, int seatCount, String groupName);
//    void cancelEvent(String eventName, String date);

}
