package sakenov.cryptoanalyzer.controllers;

import sakenov.cryptoanalyzer.commands.*;
import sakenov.cryptoanalyzer.exceptions.AppException;

public enum Activity {

    ENCRYPT(new Encrypt()),
    DECRYPT(new Decrypt()),
    BRUTEFORCE(new BruteForce());

    private final Action action;

    Activity(Action action) {
        this.action = action;
    }

    public static Action find (String actionName) {
        try {
            Activity value = Activity.valueOf( actionName.toUpperCase() );
            return value.action;
        } catch (IllegalArgumentException e) {
            throw new AppException( "not found " + actionName, e );
        }
    }
}
