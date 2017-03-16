package com.javared.future;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.Executor;

/**
 * An implementation of {@link RedFuture}, which represents the settable side of the future.
 * A future of this type may be resolved or failed, as well as attach with callbacks.
 */
public class OpenRedFuture extends BaseOpenRedFuture<Void> {

    // Constructors

    @SuppressWarnings("WeakerAccess")
    protected OpenRedFuture() {}

    // Public

    /**
     * Resolves the future, marking it successfully completed.
     * As result of this method invocation, all registered success and finally callbacks
     * will be invoked.
     * If the future is already completed, a warning will be logged. @see {@link #resolve(Object, boolean)}
     */
    public void resolve() {
        resolve(null, true);
    }

    /**
     * Resolves the future, marking it successfully completed.
     * As result of this method invocation, all registered success and finally callbacks
     * will be invoked.
     * If the future is already completed, this call will be ignored.
     */
    public void tryResolve() {
        resolve(null, false);
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
    public void follow(RedFuture future) {
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
    public void follow(Executor executor, RedFuture future) {
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
    public void follow(ListenableFuture<?> listenableFuture) {
        Futures.addCallback(listenableFuture, safeCallback(o -> resolve(), this::fail));
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
    public void follow(Executor executor, ListenableFuture<?> listenableFuture) {
        Futures.addCallback(listenableFuture, safeCallback(o -> resolve(), this::fail), executor);
    }

}
