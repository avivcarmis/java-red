package com.javared.future;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.javared.future.callbacks.EmptyCallback;
import com.javared.future.callbacks.TypedCallback;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

/**
 * Created by Mamot on 3/14/2017.
 */
public class OpenRedFutureOf<T> extends BaseOpenRedFuture<T> implements RedFutureOf<T> {

    // Fields

    private T _value;

    // Constructors

    protected OpenRedFutureOf() {}

    // Public

    public void resolve(T value) {
        if (resolve(value, true)) {
            _value = value;
        }
    }

    public void tryResolve(T value) {
        if (resolve(value, false)) {
            _value = value;
        }
    }

    public OpenRedFutureOf<T> follow(OpenRedFutureOf<T> future) {
        future.addSuccessCallback(this::resolve).addFailureCallback(this::fail);
        return this;
    }

    public OpenRedFutureOf<T> follow(Executor executor, OpenRedFutureOf<T> future) {
        future.addSuccessCallback(executor, this::resolve).addFailureCallback(executor, this::fail);
        return this;
    }

    public OpenRedFutureOf<T> follow(ListenableFuture<T> listenableFuture) {
        Futures.addCallback(listenableFuture, safeCallback(this::resolve, this::fail));
        return this;
    }

    public OpenRedFutureOf<T> follow(Executor executor, ListenableFuture<T> listenableFuture) {
        Futures.addCallback(listenableFuture, safeCallback(this::resolve, this::fail), executor);
        return this;
    }

    @Override
    public RedFutureOf<T> addSuccessCallback(TypedCallback<T> callback) {
        Futures.addCallback(getListenableFuture(), safeCallback(callback, null));
        return this;
    }

    @Override
    public RedFutureOf<T> addSuccessCallback(Executor executor, TypedCallback<T> callback) {
        Futures.addCallback(getListenableFuture(), safeCallback(callback, null), executor);
        return this;
    }

    @Override
    public T tryGet() {
        return _value;
    }

    @Override
    public T waitAndGet() throws ExecutionException, InterruptedException {
        return getListenableFuture().get();
    }

    @Override
    public RedFutureOf<T> addSuccessCallback(EmptyCallback callback) {
        super.addSuccessCallback(callback);
        return this;
    }

    @Override
    public RedFutureOf<T> addSuccessCallback(Executor executor, EmptyCallback callback) {
        super.addSuccessCallback(executor, callback);
        return this;
    }

    @Override
    public RedFutureOf<T> addFailureCallback(TypedCallback<Throwable> callback) {
        super.addFailureCallback(callback);
        return this;
    }

    @Override
    public RedFutureOf<T> addFailureCallback(Executor executor, TypedCallback<Throwable> callback) {
        super.addFailureCallback(executor, callback);
        return this;
    }

    @Override
    public RedFutureOf<T> addFinallyCallback(EmptyCallback callback) {
        super.addFinallyCallback(callback);
        return this;
    }

    @Override
    public RedFutureOf<T> addFinallyCallback(Executor executor, EmptyCallback callback) {
        super.addFinallyCallback(executor, callback);
        return this;
    }

}
