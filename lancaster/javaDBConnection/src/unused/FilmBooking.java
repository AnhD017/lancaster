import java.time.LocalDate;
import java.time.LocalTime;

public class FilmBooking {
    private int eventID;
    private String eventName;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private int expectedParticipants;
    private int availableSeats;

    public FilmBooking(int eventID, String eventName, LocalDate startDate, LocalDate endDate,
                       LocalTime startTime, LocalTime endTime, int expectedParticipants, int availableSeats) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.expectedParticipants = expectedParticipants;
        this.availableSeats = availableSeats;
    }

    // Getters
    public int getEventID() { return eventID; }
    public String getEventName() { return eventName; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public LocalTime getStartTime() { return startTime; }
    public LocalTime getEndTime() { return endTime; }
    public int getExpectedParticipants() { return expectedParticipants; }
    public int getAvailableSeats() { return availableSeats; }
}
