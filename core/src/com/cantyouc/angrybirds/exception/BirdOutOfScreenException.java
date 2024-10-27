package com.cantyouc.angrybirds.exception;

public class BirdOutOfScreenException extends Exception {
    public BirdOutOfScreenException(String message) {
        super(message);
        // This exception needs to be handled and a label will be displayed on the screen
    }
}
