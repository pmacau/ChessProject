package model;

import java.util.Calendar;
import java.util.Date;


/**
 * Represents an event.
 */
public class Event {
    private static final int HASH_CONSTANT = 13;
    private Date dateLogged;
    private String description;

    /**
     * Creates an event with the given description
     * and the current date/time stamp.
     *
     * @param description a description of the event
     */
    // Effects: Sets description with corresponding date.
    // Modifies: This
    public Event(String description) {
        dateLogged = Calendar.getInstance().getTime();
        this.description = description;
    }

    /**
     * Gets the date of this event (includes time).
     *
     * @return the date of the event
     */
    // Effects: Returns date logged
    public Date getDate() {
        return dateLogged;
    }

    /**
     * Gets the description of this event.
     *
     * @return the description of the event
     */
    // Effects: Returns date logged
    public String getDescription() {
        return description;
    }

    // Effects: Handles equal operations.
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }


        if (other.getClass() != this.getClass()) {
            return false;
        }

        Event otherEvent = (Event) other;

        return (this.dateLogged.equals(otherEvent.dateLogged)
                &&
                this.description.equals(otherEvent.description));
    }

    // Effects: Gives the hashCode
    @Override
    public int hashCode() {
        return (HASH_CONSTANT * dateLogged.hashCode() + description.hashCode());
    }

    // Effects: Returns string of event
    @Override
    public String toString() {
        return dateLogged.toString() + "\n" + description;
    }
}
