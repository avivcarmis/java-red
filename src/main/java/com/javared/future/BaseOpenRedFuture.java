package com.javared.future;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.javared.future.callbacks.Callback;
import com.javared.future.callbacks.EmptyCallback;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An abstract class to implement common functionality of both {@link RedFuture} and {@link RedFutureOf}
 */
abstract public class BaseOpenRedFuture<T> implements RedFuture {

    // Constants

    private static final Logger LOGGER = Logger.getLogger(RedFuture.class.getName());

    // Fields

    /**
     * The underlying Guava future.
     */
    private final SettableFuture<T> _settableFuture;

    // Constructors

    @SuppressWarnings("WeakerAccess")
    protected BaseOpenRedFuture() {
        _settableFuture = SettableFuture.create();
    }

    // Public

    /**
     * Fails the future with given throwable.
     * As result of this method invocation, all registered failure and finally callbacks
     * will be invoked.
     * If the future is already completed, a warning will be logged. @see {@link #fail(Throwable, boolean)}
     *
     * @param throwable cause of the failure
     */
    public void fail(Throwable throwable) {
        fail(throwable, true);
    }

    /**
     * Fails the future with given throwable.
     * As result of this method invocation, all registered failure and finally callbacks
     * will be invoked.
     * If the future is already completed, this call will be ignored.
     *
     * @param throwable cause of the failure
     */
    public void tryFail(Throwable throwable) {
        fail(throwable, false);
    }

    @Override
    public RedFuture addSuccessCallback(EmptyCallback callback) {
        Futures.addCallback(_settableFuture, safeCallback(o -> callback.call(), null));
        return this;
    }

    @Override
    public RedFuture addSuccessCallback(Executor executor, EmptyCallback callback) {
        Futures.addCallback(_settableFuture, safeCallback(t -> callback.call(), null), executor);
        return this;
    }

    @Override
    public RedFuture addFailureCallback(Callback<Throwable> callback) {
        Futures.addCallback(_settableFuture, safeCallback(null, callback));
        return this;
    }

    @Override
    public RedFuture addFailureCallback(Executor executor, Callback<Throwable> callback) {
        Futures.addCallback(_settableFuture, safeCallback(null, callback), executor);
        return this;
    }

    @Override
    public RedFuture addFinallyCallback(EmptyCallback callback) {
        Futures.addCallback(_settableFuture, safeCallback(t -> callback.call(), throwable -> callback.call()));
        return this;
    }

    @Override
    public RedFuture addFinallyCallback(Executor executor, EmptyCallback callback) {
        Futures.addCallback(_settableFuture, safeCallback(t -> callback.call(), throwable -> callback.call()), executor);
        return this;
    }

    @Override
    public ListenableFuture<T> getListenableFuture() {
        return _settableFuture;
    }

    @Override
    public void waitForCompletion() throws ExecutionException, InterruptedException {
        _settableFuture.get();
    }

    @Override
    public boolean isResolved() {
        return _settableFuture.isDone();
    }

    // Private

    /**
     * Resolving the underlying {@link SettableFuture} with given value, either logging or
     * not logging warning in case the future is already complete, according to require parameter.
     *
     * @param value   value to resolve the future with
     * @param require whether or not to log warning in case the future is already complete
     * @return true if successfully resolved, false if future is already complete
     */
    @SuppressWarnings("WeakerAccess")
    protected boolean resolve(T value, boolean require) {
        if (!_settableFuture.set(value)) {
            if (!_settableFuture.isCancelled() && require) {
                LOGGER.log(Level.WARNING, "red future resolved more than once");
            }
            return false;
        }
        return true;
    }

    /**
     * Failing the underlying {@link SettableFuture} with given cause, either logging or
     * not logging warning in case the future is already complete, according to require parameter.
     *
     * @param throwable cause to fail the future with
     * @param require whether or not to log warning in case the future is already complete
     * @return true if successfully failed, false if future is already complete
     */
    @SuppressWarnings("WeakerAccess")
    protected boolean fail(Throwable throwable, boolean require) {
        if (!_settableFuture.setException(throwable)) {
            if (!_settableFuture.isCancelled() && require) {
                LOGGER.log(Level.WARNING, "red future failed more than once");
            }
            return false;
        }
        return true;
    }

    /**
     * Generates a guava {@link FutureCallback} to attach to a {@link ListenableFuture} with given
     * success and failure callbacks. Wraps the invocations such that if an uncaught runtime exception
     * is thrown from a callback invocation, a warning is logged. Such a case must be seriously considered
     * in an asynchronous system, as it can cause the system to freeze if some pending propagation has not
     * reached before the exception was thrown.
     * @param onSuccess callback on be invoked on success, or null to skip success event.
     * @param onFailure callback on be invoked on failure, or null to skip failure event.
     * @param <K>       type of the returned {@link FutureCallback}
     * @return a guava {@link FutureCallback} to attach to a {@link ListenableFuture}
     */
    @SuppressWarnings("WeakerAccess")
    protected <K> FutureCallback<K> safeCallback(Callback<K> onSuccess, Callback<Throwable> onFailure) {
        return new FutureCallback<K>() {

            @Override
            public void onSuccess(K t) {
                call(onSuccess, t);
            }

            @Override
            public void onFailure(Throwable throwable) {
                call(onFailure, throwable);
            }

        };
    }

    private <K> void call(Callback<K> callback, K value) {
        if (callback != null) {
            try {
                callback.call(value);
            } catch (Throwable caught) {
                LOGGER.log(Level.WARNING, "exception thrown during red future callback execution, " +
                        "this may cause system freeze due to callback propagation stop", caught);
            }
        }
    }

}
