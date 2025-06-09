package com.example.chartboost.mediation.sdk.demo.java;

import androidx.annotation.Nullable;

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 * An event is an action that should be consumed only once, e.g., showing a Toast.
 */
public class Event<T> {

    private final T content;
    private boolean hasBeenHandled = false;

    public Event(final T content) {
        if (content == null) {
            throw new IllegalArgumentException("Event content cannot be null");
        }
        this.content = content;
    }

    /**
     * Returns the content and prevents its use again.
     */
    @Nullable
    public T getContentIfNotHandled() {
        if (hasBeenHandled) {
            return null;
        } else {
            hasBeenHandled = true;
            return content;
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     * Useful for cases where you just want to see the content without consuming the event.
     */
    public T peekContent() {
        return content;
    }

    /**
     * Returns true if the event has already been handled.
     */
    public boolean hasBeenHandled() {
        return hasBeenHandled;
    }
}
