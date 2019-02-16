package com.example.android.brickhackapp;

public class Event {
    /** Title of the alert event */
    public final String title;


    /**
     * Constructs a new {@link Event}.
     *
     * @param eventTitle is the title of the alert event
     */
    public Event(String eventTitle) {
        title = eventTitle;
    }

    public String getTitle() {
        return title;
    }
}
