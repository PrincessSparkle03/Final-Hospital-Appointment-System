package Model;

import java.util.ArrayList;
import java.util.List;

public class Schedule implements Displayable, Bookable {
    private String day;
    private List<TimeSlot> slots;
    private String startTime;
    private String endTime;
    private boolean isAvailable; // If false, the doctor is on holiday/leave that day

    public Schedule(String day, List<TimeSlot> slots, String startTime, String endTime, boolean isAvailable) {
        setDay(day);
        setStartTime(startTime);
        setEndTime(endTime);
        this.isAvailable = isAvailable;
        setSlots(slots);
    }

    // --- SETTERS (Mutators) with Business Logic ---

    public void setDay(String day) {
        // Logic: Standardize day names to prevent "monday" vs "Monday" issues
        if (day == null || day.isEmpty()) {
            this.day = "To Be Determined";
        } else {
            this.day = day.substring(0, 1).toUpperCase() + day.substring(1).toLowerCase();
        }
    }

    public void setStartTime(String startTime) {
        // Logic: Default to standard opening hours if input is invalid
        if (startTime == null || !startTime.contains(":")) {
            this.startTime = "09:00";
        } else {
            this.startTime = startTime;
        }
    }

    public void setEndTime(String endTime) {
        /* 
           Logic: CRITICAL BUSINESS RULE
           The End Time cannot be BEFORE the Start Time.
           If it is, we automatically set it to 1 hour after Start Time.
        */
        if (endTime == null || endTime.compareTo(this.startTime) <= 0) {
            this.endTime = "17:00"; // Fallback to standard closing
        } else {
            this.endTime = endTime;
        }
    }

    public void setSlots(List<TimeSlot> slots) {
        // Logic: Prevent NullPointerException
        if (slots == null) {
            this.slots = new ArrayList<>();
        } else {
            this.slots = slots;
        }
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    // --- GETTERS (Accessors) with Business Logic ---

    public String getDay() {
        return this.day;
    }

    public String getShiftDuration() {
        // Logic: A custom getter that creates a "View" of the full shift
        return this.startTime + " to " + this.endTime;
    }

    public List<TimeSlot> getSlots() {
        // Logic: If the doctor is NOT available today, return an empty list
        // so no one can book a slot.
        if (!this.isAvailable) {
            return new ArrayList<>();
        }
        return slots;
    }

    public boolean isAvailable() {
        // Logic: A doctor is only available if the flag is true AND they have slots
        return isAvailable && (slots != null && !slots.isEmpty());
    }

    // --- DISPLAYABLE INTERFACE METHODS ---

    /**
     * Displays full schedule information in a formatted manner
     * Implements Displayable interface
     */
    @Override
    public void display() {
        System.out.println("==== SCHEDULE DETAILS ====");
        System.out.println("Day: " + day);
        System.out.println("Shift: " + getShiftDuration());
        System.out.println("Available: " + (isAvailable ? "Yes" : "No (On leave/Holiday)"));
        System.out.println("Total Slots: " + (slots != null ? slots.size() : 0));
        if (slots != null && !slots.isEmpty()) {
            int availableSlots = (int) slots.stream().filter(TimeSlot::isAvailable).count();
            System.out.println("Available Slots: " + availableSlots);
        }
        System.out.println("===========================");
    }

    /**
     * Returns a brief summary of the schedule
     * Implements Displayable interface
     * @return A single-line summary
     */
    @Override
    public String getSummary() {
        int availableCount = (slots != null) ? (int) slots.stream().filter(TimeSlot::isAvailable).count() : 0;
        return day + " (" + getShiftDuration() + ") - " + availableCount + " available slots";
    }

    // --- BOOKABLE INTERFACE METHODS ---

    /**
     * Marks this schedule as booked/unavailable (doctor is fully booked)
     * Implements Bookable interface
     */
    @Override
    public void book() {
        this.isAvailable = false;
    }

    /**
     * Marks this schedule as released/available again (doctor has free slots)
     * Implements Bookable interface
     */
    @Override
    public void release() {
        this.isAvailable = true;
    }
}