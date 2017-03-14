package com.javared.future;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.javared.future.callbacks.EmptyCallback;
import com.javared.future.callbacks.TypedCallback;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Mamot on 3/14/2017.
 */
abstract public class BaseOpenRedFuture<T> implements RedFuture {

    // Constants

    private static final Logger LOGGER = Logger.getLogger(RedFuture.class.getName());

    // Fields

    private final SettableFuture<T> _settableFuture;

    // Constructors

    protected BaseOpenRedFuture() {
        _settableFuture = SettableFuture.create();
    }

    // Public

    public void fail(Throwable throwable) {
        fail(throwable, true);
    }

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
    public RedFuture addFailureCallback(TypedCallback<Throwable> callback) {
        Futures.addCallback(_settableFuture, safeCallback(null, callback));
        return this;
    }

    @Override
    public RedFuture addFailureCallback(Executor executor, TypedCallback<Throwable> callback) {
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

    protected boolean resolve(T value, boolean require) {
        if (!_settableFuture.set(value)) {
            if (!_settableFuture.isCancelled() && require) {
                LOGGER.log(Level.WARNING, "red future resolved more than once");
            }
            return false;
        }
        return true;
    }

    protected boolean fail(Throwable throwable, boolean require) {
        if (!_settableFuture.setException(throwable)) {
            if (!_settableFuture.isCancelled() && require) {
                LOGGER.log(Level.WARNING, "red future failed more than once");
            }
            return false;
        }
        return true;
    }

    protected <K> FutureCallback<K> safeCallback(TypedCallback<K> onSuccess, TypedCallback<Throwable> onFailure) {
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

    private <K> void call(TypedCallback<K> callback, K value) {
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
