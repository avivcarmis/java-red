package com.javared.executor;

/**
 * Created by avivc on 3/30/2017.
 */
public class PreconditionFailedException extends Exception {

    private PreconditionFailedException(String message) {
        super(message);
    }

    public static final PreconditionFailedException FLIPPED_EXCEPTION =
            new PreconditionFailedException("future should have failed but succeeded");

}
