package com.example.ankush.darkskyclient.events;

public class EventError {
    private final String errorMessage;

    public EventError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
