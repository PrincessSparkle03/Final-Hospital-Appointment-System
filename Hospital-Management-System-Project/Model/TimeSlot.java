package Model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * TimeSlot Class
 * 
 * Represents a specific time slot on a given day with availability tracking.
 * Each TimeSlot has:
 * - date: The date of the slot (YYYY-MM-DD format)
 * - start time: When the slot starts (HH:MM format)
 * - end time: When the slot ends (HH:MM format)
 * - availability: Whether the slot is available for booking
 * 
 * Access Modifiers:
 * - Private: date, start, end, isAvailable (data encapsulation)
 * - Public: Constructor, getters, setters (interface for other classes)
 */
public class TimeSlot implements Displayable, Bookable {
    // Private fields - encapsulated data
    private LocalDate date;       // Using LocalDate for proper date handling
    private LocalTime start;      // Using LocalTime for proper time handling
    private LocalTime end;        // Using LocalTime for proper time handling
    private boolean isAvailable;  // Tracks if this slot can be booked

    /**
     * Constructor: Creates a TimeSlot with date and time range
     * @param date The date of this slot (LocalDate)
     * @param start The start time (LocalTime)
     * @param end The end time (LocalTime)
     */
    public TimeSlot(LocalDate date, LocalTime start, LocalTime end) {
        setDate(date);
        setStart(start);
        setEnd(end);
        this.isAvailable = true; // New slots are available by default
    }

    // --- SETTERS (Mutators) with Business Logic ---

    /**
     * Sets the date for this time slot
     * Logic: Ensures date is valid and not in the past
     * @param date The date (LocalDate)
     */
    public void setDate(LocalDate date) {
        if (date == null || date.isBefore(LocalDate.now())) {
            System.out.println("Warning: Invalid date. Date cannot be in the past.");
            this.date = LocalDate.now();
        } else {
            this.date = date;
        }
    }

    /**
     * Sets the date from a String (alternative constructor for parsing)
     * @param dateString The date as a string (YYYY-MM-DD format)
     */
    public void setDateFromString(String dateString) {
        try {
            LocalDate parsedDate = LocalDate.parse(dateString);
            setDate(parsedDate);
        } catch (Exception e) {
            System.out.println("Warning: Invalid date format. Expected YYYY-MM-DD");
            this.date = LocalDate.now();
        }
    }

    /**
     * Sets the start time for this slot
     * Logic: Ensures time is valid
     * @param start The start time (LocalTime)
     */
    public void setStart(LocalTime start) {
        if (start == null) {
            System.out.println("Warning: Invalid start time.");
            this.start = LocalTime.of(9, 0);
        } else {
            this.start = start;
        }
    }

    /**
     * Sets the start time from a String (alternative for parsing)
     * @param startString The start time as a string (HH:MM format)
     */
    public void setStartFromString(String startString) {
        try {
            LocalTime parsedTime = LocalTime.parse(startString);
            setStart(parsedTime);
        } catch (Exception e) {
            System.out.println("Warning: Invalid start time format. Expected HH:MM");
            this.start = LocalTime.of(9, 0);
        }
    }

    /**
     * Sets the end time for this slot
     * Logic: Ensures time is valid and after start time
     * @param end The end time (LocalTime)
     */
    public void setEnd(LocalTime end) {
        if (end == null || (start != null && end.isBefore(start))) {
            System.out.println("Warning: Invalid end time. Must be after start time.");
            this.end = (start != null) ? start.plusHours(1) : LocalTime.of(17, 0);
        } else {
            this.end = end;
        }
    }

    /**
     * Sets the end time from a String (alternative for parsing)
     * @param endString The end time as a string (HH:MM format)
     */
    public void setEndFromString(String endString) {
        try {
            LocalTime parsedTime = LocalTime.parse(endString);
            setEnd(parsedTime);
        } catch (Exception e) {
            System.out.println("Warning: Invalid end time format. Expected HH:MM");
            this.end = LocalTime.of(17, 0);
        }
    }

    /**
     * Sets the availability of this time slot
     * Logic: Prevents double-booking by marking slots as unavailable when booked
     * @param isAvailable Whether this slot is available for booking
     */
    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    // --- GETTERS (Accessors) ---

    /**
     * Gets the date of this time slot
     * @return The date as LocalDate
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Gets the date formatted as a String
     * @return The date in YYYY-MM-DD format
     */
    public String getDateAsString() {
        return date.toString();
    }

    /**
     * Gets the start time with formatting
     * @return The start time formatted for display
     */
    public String getStart() {
        return start + " hrs";
    }

    /**
     * Gets the start time as LocalTime
     * @return The raw start time (LocalTime)
     */
    public LocalTime getStartTime() {
        return start;
    }

    /**
     * Gets the start time without formatting (raw value)
     * Useful for time comparisons and validations
     * @return The raw start time as string (HH:MM format)
     */
    public String getStartRaw() {
        return start.toString();
    }

    /**
     * Gets the end time with formatting
     * @return The end time formatted for display
     */
    public String getEnd() {
        return end + " hrs";
    }

    /**
     * Gets the end time as LocalTime
     * @return The raw end time (LocalTime)
     */
    public LocalTime getEndTime() {
        return end;
    }

    /**
     * Gets the end time without formatting (raw value)
     * @return The raw end time as string (HH:MM format)
     */
    public String getEndRaw() {
        return end.toString();
    }

    /**
     * Checks if this time slot is available for booking
     * @return true if available, false if already booked
     */
    public boolean isAvailable() {
        return isAvailable;
    }

    /**
     * Gets a complete description of this time slot
     * @return String representation: "Date - StartTime to EndTime (Status)"
     */
    public String getSlotDetails() {
        String status = isAvailable ? "Available" : "Booked";
        return date + " | " + start + " - " + end + " | " + status;
    }

    // --- DISPLAYABLE INTERFACE METHODS ---

    /**
     * Displays full time slot information in a formatted manner
     * Implements Displayable interface
     */
    @Override
    public void display() {
        System.out.println("==== TIME SLOT DETAILS ====");
        System.out.println("Date: " + date);
        System.out.println("Time: " + start + " to " + end);
        System.out.println("Status: " + (isAvailable ? "Available" : "Booked"));
        System.out.println("===========================");
    }

    /**
     * Returns a brief summary of the time slot
     * Implements Displayable interface
     * @return A single-line summary
     */
    @Override
    public String getSummary() {
        return date + " " + start + "-" + end + " (" + (isAvailable ? "Available" : "Booked") + ")";
    }

    // --- BOOKABLE INTERFACE METHODS ---

    /**
     * Books this time slot (marks it as unavailable)
     * Implements Bookable interface
     */
    @Override
    public void book() {
        this.isAvailable = false;
    }

    /**
     * Releases this time slot (marks it as available again)
     * Implements Bookable interface
     */
    @Override
    public void release() {
        this.isAvailable = true;
    }
    public boolean bookSlot() {
        if (isAvailable) {
            isAvailable = false;
            return true;
        }
        return false;
    }

    public void releaseSlot() {
        isAvailable = true;
    }
}