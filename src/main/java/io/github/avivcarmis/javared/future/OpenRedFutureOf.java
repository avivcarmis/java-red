package io.github.avivcarmis.javared.future;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import io.github.avivcarmis.javared.future.callbacks.Callback;
import io.github.avivcarmis.javared.future.callbacks.EmptyCallback;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * An implementation of {@link RedFutureOf}, which represents the settable side of the typed future.
 * A future of this type may be resolved or failed, as well as attach with callbacks.
 *
 * @param <T> the type of the future value
 */
public class OpenRedFutureOf<T> extends BaseOpenRedFuture<T> implements RedFutureOf<T> {

    // Fields

    /**
     * the resulted value of the future
     */
    private final AtomicReference<T> _value;

    // Constructors

    @SuppressWarnings("WeakerAccess")
    protected OpenRedFutureOf() {
        _value = new AtomicReference<>(null);
    }

    // Public

    /**
     * Resolves the future, marking it successfully completed.
     * As result of this method invocation, all registered success and finally callbacks
     * will be invoked.
     * If the future is already completed, a warning will be logged. @see {@link #resolve(Object, boolean)}
     *
     * @param value the value to resolve the future with
     */
    public void resolve(T value) {
        _value.compareAndSet(null, value);
        resolve(value, true);
    }

    /**
     * Resolves the future, marking it successfully completed.
     * As result of this method invocation, all registered success and finally callbacks
     * will be invoked.
     * If the future is already completed, this call will be ignored.
     *
     * @param value the value to resolve the future with
     */
    public void tryResolve(T value) {
        _value.compareAndSet(null, value);
        resolve(value, false);
    }

    /**
     * Tells the current open future to follow the given future status.
     * When the given future will be resolved or failed, the current future will respectively
     * be directly resolved or failed by the same thread.
     * If the given future is already resolved or failed, the current future will respectively
     * be directly resolved or failed by the current thread.
     * Note that if the future is already completed when trying to follow the given future status,
     * a warning will be logged and the second completion invocation will be ignored.
     * Thus, trying to follow more than one future is illegal.
     *
     * @param future future to follow
     */
    public void follow(RedFutureOf<T> future) {
        future.addSuccessCallback(this::resolve).addFailureCallback(this::fail);
    }

    /**
     * Tells the current open future to follow the given future status.
     * When the given future will be resolved or failed, the current future will respectively
     * queued to be resolved or failed with the given executor.
     * If the given future is already resolved or failed, the current future will respectively
     * be queued to be resolved or failed with the given executor.
     * Note that if the future is already completed when trying to follow the given future status,
     * a warning will be logged and the second completion invocation will be ignored.
     * Thus, trying to follow more than one future is illegal.
     *
     * @param executor to execute the completion of this future
     * @param future   future to follow
     */
    public void follow(Executor executor, RedFutureOf<T> future) {
        future.addSuccessCallback(executor, this::resolve).addFailureCallback(executor, this::fail);
    }

    /**
     * Tells the current open future to follow the given future status.
     * When the given future will be resolved or failed, the current future will respectively
     * be directly resolved or failed by the same thread.
     * If the given future is already resolved or failed, the current future will respectively
     * be directly resolved or failed by the current thread.
     * Note that if the future is already completed when trying to follow the given future status,
     * a warning will be logged and the second completion invocation will be ignored.
     * Thus, trying to follow more than one future is illegal.
     *
     * @param listenableFuture future to follow
     */
    public void follow(ListenableFuture<T> listenableFuture) {
        Futures.addCallback(listenableFuture, safeCallback(this::resolve, this::fail));
    }

    /**
     * Tells the current open future to follow the given future status.
     * When the given future will be resolved or failed, the current future will respectively
     * queued to be resolved or failed with the given executor.
     * If the given future is already resolved or failed, the current future will respectively
     * be queued to be resolved or failed with the given executor.
     * Note that if the future is already completed when trying to follow the given future status,
     * a warning will be logged and the second completion invocation will be ignored.
     * Thus, trying to follow more than one future is illegal.
     *
     * @param executor           to execute the completion of this future
     * @param listenableFuture   future to follow
     */
    public void follow(Executor executor, ListenableFuture<T> listenableFuture) {
        Futures.addCallback(listenableFuture, safeCallback(this::resolve, this::fail), executor);
    }

    @Override
    public RedFutureOf<T> addSuccessCallback(Callback<T> callback) {
        Futures.addCallback(getListenableFuture(), safeCallback(callback, null));
        return this;
    }

    @Override
    public RedFutureOf<T> addSuccessCallback(Executor executor, Callback<T> callback) {
        Futures.addCallback(getListenableFuture(), safeCallback(callback, null), executor);
        return this;
    }

    @Override
    public T tryGet() {
        return _value.get();
    }

    @Override
    public T waitAndGet() throws ExecutionException, InterruptedException {
        return getListenableFuture().get();
    }

    @Override
    public T waitAndGet(long timeout, TimeUnit unit) throws ExecutionException, InterruptedException, TimeoutException {
        return getListenableFuture().get(timeout, unit);
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
    public RedFutureOf<T> addFailureCallback(Callback<Throwable> callback) {
        super.addFailureCallback(callback);
        return this;
    }

    @Override
    public RedFutureOf<T> addFailureCallback(Executor executor, Callback<Throwable> callback) {
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
