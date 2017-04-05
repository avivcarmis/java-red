package io.github.avivcarmis.javared.executor;

/**
 * Represents the indication error that will be thrown in case an
 * execution precondition failed.
 */
abstract public class PreconditionFailedException extends Exception {

    // Constructors

    private PreconditionFailedException(String message) {
        super(message);
    }

    private PreconditionFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    // Static

    /**
     * Will be thrown in case an execution was expecting a precondition to
     * succeed, but failed.
     *
     * Original failure may be retrieved through {@link #getCause()}
     */
    public static class Success extends PreconditionFailedException {

        Success(Throwable cause) {
            super("expected success but failed", cause);
        }

    }

    /**
     * Will be thrown in case an execution was expecting a precondition to
     * fail, but succeeded.
     */
    public static class Failure extends PreconditionFailedException {

        private Failure() {
            super("expected failure but succeeded");
        }

        static final Failure INSTANCE = new Failure();

    }

}
