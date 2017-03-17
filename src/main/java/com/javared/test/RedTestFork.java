package com.javared.test;

import com.javared.future.OpenRedFuture;

/**
 * A fork of a {@link RedTestContext}
 * see {@link RedTestContext#fork()}
 */
public class RedTestFork {

    // Fields

    /**
     * The underlying future to resolve or fail
     */
    private final OpenRedFuture _future;

    // Constructors

    RedTestFork(OpenRedFuture future) {
        _future = future;
    }

    // Public

    /**
     * Mark the fork as completed, allowing the test to successfully complete
     */
    public void complete() {
        _future.resolve();
    }

    /**
     * Fails the entire test, calling this method is logically identical to
     * calling {@link RedTestContext#fail(Throwable)}
     * @param throwable cause of failure
     */
    public void fail(Throwable throwable) {
        _future.fail(throwable);
    }

    /**
     * Fails the entire test, calling this method is logically identical to
     * calling {@link RedTestContext#fail(String)}
     * @param message reason of failure
     */
    public void fail(String message) {
        _future.fail(new RuntimeException(message));
    }

    /**
     * Fails the entire test, calling this method is logically identical to
     * calling {@link RedTestContext#fail()}
     */
    public void fail() {
        _future.fail(new RuntimeException());
    }

}
