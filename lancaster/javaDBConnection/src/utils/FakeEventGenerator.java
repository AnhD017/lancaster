package utils;

import models.Event; // Import your custom Event class
import java.util.*; // For List, ArrayList, Random, etc.

public class FakeEventGenerator {
        private static final String[] ROOM_TYPES = {
                        "Main Hall", "Secondary Hall", "Rehearsal Room", "Theatre", "Meeting Room 1", "Meeting Room 2"
        };

        private static final String[] EVENT_NAMES = {
                        "Music Rehearsal", "Corporate Meeting", "Theatre Play", "Dance Practice", "Workshop", "Seminar"
        };

        private static final String[] CUSTOMER_NAMES = {
                        "John Doe", "Jane Smith", "Alice Johnson", "Bob Brown", "Charlie Davis"
        };

        private static final String[] DESCRIPTIONS = {
                        "Band practice for upcoming concert",
                        "Quarterly business meeting",
                        "Rehearsal for the upcoming play",
                        "Dance group preparing for competition",
                        "Hands-on workshop for beginners",
                        "Educational seminar on technology"
        };

        public static List<Event> generateEventsForDay(int day) {
                List<Event> events = new ArrayList<>();
                Random random = new Random();

                // Generate 3-5 events for the day
                int numberOfEvents = 3 + random.nextInt(3);

                Set<String> usedRooms = new HashSet<>();

                for (int i = 0; i < numberOfEvents; i++) {
                        // Generate event details
                        String name = EVENT_NAMES[random.nextInt(EVENT_NAMES.length)];
                        String description = DESCRIPTIONS[random.nextInt(DESCRIPTIONS.length)];
                        String customer = CUSTOMER_NAMES[random.nextInt(CUSTOMER_NAMES.length)];

                        // Generate start and end times (between 8:00 AM and 8:00 PM)
                        int startHour = 8 + random.nextInt(12);
                        int startMinute = random.nextInt(2) * 30; // 0 or 30 minutes
                        int endHour = startHour + random.nextInt(3); // Event lasts 1-3 hours
                        int endMinute = startMinute;

                        // Assign a room that is not already in use
                        String room;
                        do {
                                room = ROOM_TYPES[random.nextInt(ROOM_TYPES.length)];
                        } while (usedRooms.contains(room));
                        usedRooms.add(room);

                        
                        
                }

                return events;
        }
}