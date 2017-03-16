package com.javared.future;

import com.google.common.util.concurrent.ListenableFuture;
import com.javared.future.callbacks.Callback;
import com.javared.future.callbacks.EmptyCallback;

import java.util.concurrent.*;

/**
 * A simple {@link java.util.concurrent.Future} like interface,
 * which supports trivial, fully asynchronous, Java 8 callbacks.
 *
 * As opposed to {@link RedFuture}, this interface represents a future that is pending value,
 * and will be resolved with a given value of the future type.
 *
 * @param <T> type of the future value
 */
public interface RedFutureOf<T> extends RedFuture {

    /**
     * Attach a single parameter callback to be invoked when the future is successfully resolved,
     * with the parameter containing the resulted value of the future.
     * When a certain thread resolves the future, it will directly invoke the callback.
     * If the future is already resolved, the callback will be directly invoked.
     *
     * @param callback callback to be invoked when the future is successfully resolved
     * @return this, @see <a href="https://en.wikipedia.org/wiki/Fluent_interface">Fluent interface</a>
     */
    RedFutureOf<T> addSuccessCallback(Callback<T> callback);

    /**
     * Attach a single parameter callback to be invoked when the future is successfully resolved,
     * with the parameter containing the resulted value of the future.
     * When a certain thread resolves the future, it will queue the callback invocation to the
     * given executor.
     * If the future is already resolved, the callback will be immediately queued.
     *
     * @param executor executor to invoke the callback
     * @param callback callback to be invoked when the future is successfully resolved
     * @return this, @see <a href="https://en.wikipedia.org/wiki/Fluent_interface">Fluent interface</a>
     */
    RedFutureOf<T> addSuccessCallback(Executor executor, Callback<T> callback);

    /**
     * Test to see whether or not the future is currently completed, if it is successfully resolved,
     * the resulted value will be returned.
     * This method is not blocking, nor is it synchronizing different thread calls.
     * Note that a return value of null can either indicate that the future is not yet resolved,
     * or that it's has resolved with a null value. To test whether it is resolved or not,
     * one can call the inherited {@link RedFuture#isResolved()}
     * @return the resulted value if the future is successfully resolved, null otherwise
     */
    T tryGet();

    /**
     * Tries to blocks the thread until the future is completed.
     * If the future is already complete, the method returns immediately.
     *
     * @return the resulted value of the future
     * @throws ExecutionException if the computation threw an exception
     * @throws InterruptedException if the current thread was interrupted while waiting
     * @see Future#get()
     */
    T waitAndGet() throws ExecutionException, InterruptedException;

    /**
     * Tries to blocks the thread for at most the given timeout until the future is completed.
     * If the future is already complete, the method returns immediately.
     *
     * @param timeout the maximum time to wait
     * @param unit    the time unit of the timeout argument
     * @return the resulted value of the future
     * @throws ExecutionException if the computation threw an exception
     * @throws InterruptedException if the current thread was interrupted while waiting
     * @throws TimeoutException if the wait timed out
     * @see Future#get()
     */
    T waitAndGet(long timeout, TimeUnit unit) throws ExecutionException, InterruptedException, TimeoutException;

    // Overrides to update return types

    @Override
    RedFutureOf<T> addSuccessCallback(EmptyCallback callback);

    @Override
    RedFutureOf<T> addSuccessCallback(Executor executor, EmptyCallback callback);

    @Override
    RedFutureOf<T> addFailureCallback(Callback<Throwable> callback);

    @Override
    RedFutureOf<T> addFailureCallback(Executor executor, Callback<Throwable> callback);

    @Override
    RedFutureOf<T> addFinallyCallback(EmptyCallback callback);

    @Override
    RedFutureOf<T> addFinallyCallback(Executor executor, EmptyCallback callback);

    @Override
    ListenableFuture<T> getListenableFuture();

}
