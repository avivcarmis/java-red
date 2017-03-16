package com.javared.future;

import com.google.common.util.concurrent.ListenableFuture;
import com.javared.future.callbacks.Callback;
import com.javared.future.callbacks.EmptyCallback;

import java.util.concurrent.*;

/**
 * A simple {@link java.util.concurrent.Future} like interface,
 * which supports trivial, fully asynchronous, Java 8 callbacks.
 *
 * As opposed to {@link RedFutureOf}, this interface represents a future that is not
 * pending value, but only resolved to mark the underlying task complete.
 */
public interface RedFuture {

    /**
     * Attach a no-parameter callback to be invoked when the future is successfully resolved.
     * When a certain thread resolves the future, it will directly invoke the callback.
     * If the future is already resolved, the callback will be directly invoked.
     * 
     * @param callback callback to be invoked when the future is successfully resolved
     * @return this, @see <a href="https://en.wikipedia.org/wiki/Fluent_interface">Fluent interface</a>
     */
    RedFuture addSuccessCallback(EmptyCallback callback);

    /**
     * Attach a no-parameter callback to be invoked when the future is successfully resolved.
     * When a certain thread resolves the future, it will queue the callback invocation to the
     * given executor.
     * If the future is already resolved, the callback will be immediately queued.
     * 
     * @param executor executor to invoke the callback
     * @param callback callback to be invoked when the future is successfully resolved
     * @return this, @see <a href="https://en.wikipedia.org/wiki/Fluent_interface">Fluent interface</a>
     */
    RedFuture addSuccessCallback(Executor executor, EmptyCallback callback);

    /**
     * Attach a callback receiving a throwable to be invoked when the future is failed.
     * When a certain thread fails the future, it will directly invoke the callback.
     * If the future has already failed, the callback will be directly invoked.
     * 
     * @param callback callback receiving a throwable to be invoked when the future is failed
     * @return this, @see <a href="https://en.wikipedia.org/wiki/Fluent_interface">Fluent interface</a>
     */
    RedFuture addFailureCallback(Callback<Throwable> callback);

    /**
     * Attach a callback receiving a throwable to be invoked when the future is failed.
     * When a certain thread fails the future, it will queue the callback invocation to the
     * given executor.
     * If the future has already failed, the callback will be immediately queued.
     *
     * @param executor executor to invoke the callback
     * @param callback callback receiving a throwable to be invoked when the future is failed
     * @return this, @see <a href="https://en.wikipedia.org/wiki/Fluent_interface">Fluent interface</a>
     */
    RedFuture addFailureCallback(Executor executor, Callback<Throwable> callback);

    /**
     * Attach a no-parameter callback to be invoked when the future is either successfully 
     * resolved, or otherwise failed, in resemblance to java finally block.
     * When a certain thread resolves the future, it will directly invoke the callback.
     * If the future is already complete, the callback will be directly invoked.
     *
     * @param callback callback to be invoked when the future completes
     * @return this, @see <a href="https://en.wikipedia.org/wiki/Fluent_interface">Fluent interface</a>
     */
    RedFuture addFinallyCallback(EmptyCallback callback);

    /**
     * Attach a no-parameter callback to be invoked when the future is either successfully
     * resolved, or otherwise failed, in resemblance to java finally block.
     * When a certain thread fails the future, it will queue the callback invocation to the
     * given executor.
     * If the future is already complete, the callback will be immediately queued.
     *
     * @param executor executor to invoke the callback
     * @param callback callback to be invoked when the future completes
     * @return this, @see <a href="https://en.wikipedia.org/wiki/Fluent_interface">Fluent interface</a>
     */
    RedFuture addFinallyCallback(Executor executor, EmptyCallback callback);

    /**
     * Test to see whether or not the future is currently completed, either by success
     * or failure. This method is not blocking, nor is it synchronizing different thread calls.
     * @return true if the future is completed, false otherwise
     */
    boolean isResolved();

    /**
     * Tries to blocks the thread until the future is completed.
     * If the future is already complete, the method returns immediately.
     * @throws ExecutionException if the computation threw an exception
     * @throws InterruptedException if the current thread was interrupted while waiting
     * @see Future#get()
     */
    void waitForCompletion() throws ExecutionException, InterruptedException;

    /**
     * Tries to blocks the thread for at most the given timeout until the future is completed.
     * If the future is already complete, the method returns immediately.
     *
     * @param timeout the maximum time to wait
     * @param unit    the time unit of the timeout argument
     * @throws ExecutionException if the computation threw an exception
     * @throws InterruptedException if the current thread was interrupted while waiting
     * @throws TimeoutException if the wait timed out
     * @see Future#get(long, TimeUnit)
     */
    void waitForCompletion(long timeout, TimeUnit unit) throws ExecutionException, InterruptedException, TimeoutException;

    /**
     * @return the underlying Guava {@link ListenableFuture}
     */
    ListenableFuture<?> getListenableFuture();

    // Package Constructors

    /**
     * @return a new instance of {@link OpenRedFuture}
     */
    static OpenRedFuture future() {
        return new OpenRedFuture();
    }

    /**
     * @param <T> type of the future value
     * @return a new instance of {@link OpenRedFutureOf}
     */
    static <T> OpenRedFutureOf<T> futureOf() {
        return new OpenRedFutureOf<>();
    }

    /**
     * @return a new instance of {@link OpenRedFuture} which is already resolved
     */
    static RedFuture resolved() {
        OpenRedFuture future = future();
        future.resolve();
        return future;
    }

    /**
     * @param value to be resolved with
     * @param <T> type of the future value
     * @return a new instance of {@link OpenRedFutureOf} which is already resolved with given value
     */
    static <T> RedFutureOf<T> resolvedOf(T value) {
        OpenRedFutureOf<T> future = futureOf();
        future.resolve(value);
        return future;
    }

    /**
     * @param t to fail the future with
     * @return a new instance of {@link OpenRedFuture} which is already failed with given throwable
     */
    static RedFuture failed(Throwable t) {
        OpenRedFuture future = future();
        future.fail(t);
        return future;
    }

    /**
     * @param t to fail the future with
     * @param <T> type of the future value
     * @return a new instance of {@link OpenRedFutureOf} which is already failed with given throwable
     */
    static <T> RedFutureOf<T> failedOf(Throwable t) {
        OpenRedFutureOf<T> future = futureOf();
        future.fail(t);
        return future;
    }

    /**
     * @return a new instance of {@link RedFutureHub}
     */
    static RedFutureHub hub() {
        return new RedFutureHub();
    }

}
