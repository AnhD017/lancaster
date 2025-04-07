import java.time.LocalDate;
import java.time.LocalTime;

public class MeetingBooking {
    private int eventID;
    private String eventName;
    private String groupName;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private int reservedSeats;

    public MeetingBooking(int eventID, String eventName, String groupName, LocalDate startDate, LocalDate endDate,
                          LocalTime startTime, LocalTime endTime, int reservedSeats) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.groupName = groupName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.reservedSeats = reservedSeats;
    }

    // Getters
    public int getEventID() { return eventID; }
    public String getEventName() { return eventName; }
    public String getGroupName() { return groupName; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public LocalTime getStartTime() { return startTime; }
    public LocalTime getEndTime() { return endTime; }
    public int getReservedSeats() { return reservedSeats; }
}
