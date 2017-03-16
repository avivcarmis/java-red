package com.javared.future;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * An object to track operations of multiple {@link RedFuture} instances.
 */
public class RedFutureHub {

    // Fields

    /**
     * All the hub's tracked futures.
     */
    private final List<ListenableFuture<?>> listenableFutures;

    // Constructors

    @SuppressWarnings("WeakerAccess")
    protected RedFutureHub() {
        listenableFutures = new LinkedList<>();
    }

    // Public

    /**
     * Requests the hub to provide and track a single instance of {@link OpenRedFuture}.
     * The instance may be resolved, failed or follow other futures, and will be tracked
     * by the current hub.
     *
     * @return a new instance of {@link OpenRedFuture}
     */
    public OpenRedFuture provideFuture() {
        OpenRedFuture future = RedFuture.future();
        listenableFutures.add(future.getListenableFuture());
        return future;
    }

    /**
     * Requests the hub to provide and track a single instance of {@link OpenRedFutureOf}.
     * The instance may be resolved, failed or follow other futures, and will be tracked
     * by the current hub.
     *
     * @param <T> type of the future to return
     * @return a new instance of {@link OpenRedFutureOf}
     */
    public <T> OpenRedFutureOf<T> provideFutureOf() {
        OpenRedFutureOf<T> future = RedFuture.futureOf();
        listenableFutures.add(future.getListenableFuture());
        return future;
    }

    /**
     * Requests the hub to adopt and track an instance of {@link RedFuture}.
     * This means that the given instance will be tracked by the hub.
     *
     * @param future future to adopt
     * @return this, @see <a href="https://en.wikipedia.org/wiki/Fluent_interface">Fluent interface</a>
     */
    public RedFutureHub adoptFuture(RedFuture future) {
        listenableFutures.add(future.getListenableFuture());
        return this;
    }

    /**
     * Requests the hub to adopt and track multiple instances of {@link RedFuture}.
     * This means that the given instances will be tracked by the hub.
     *
     * @param futures a collection of futures to adopt
     * @return this, @see <a href="https://en.wikipedia.org/wiki/Fluent_interface">Fluent interface</a>
     */
    public RedFutureHub adoptFutures(Collection<RedFuture> futures) {
        for (RedFuture future : futures) {
            listenableFutures.add(future.getListenableFuture());
        }
        return this;
    }

    /**
     * Requests the hub to adopt and track multiple instances of {@link RedFuture}.
     * This means that the given instances will be tracked by the hub.
     *
     * @param futures a collection of futures to adopt
     * @return this, @see <a href="https://en.wikipedia.org/wiki/Fluent_interface">Fluent interface</a>
     */
    public RedFutureHub adoptFutures(RedFuture... futures) {
        for (RedFuture future : futures) {
            listenableFutures.add(future.getListenableFuture());
        }
        return this;
    }

    /**
     * Requests the hub to adopt and track an instance of {@link ListenableFuture}.
     * This means that the given instance will be tracked by the hub.
     *
     * @param future future to adopt
     * @return this, @see <a href="https://en.wikipedia.org/wiki/Fluent_interface">Fluent interface</a>
     */
    public RedFutureHub adoptListenableFuture(ListenableFuture future) {
        listenableFutures.add(future);
        return this;
    }

    /**
     * Requests the hub to adopt and track multiple instances of {@link ListenableFuture}.
     * This means that the given instances will be tracked by the hub.
     *
     * @param futures a collection of futures to adopt
     * @return this, @see <a href="https://en.wikipedia.org/wiki/Fluent_interface">Fluent interface</a>
     */
    public RedFutureHub adoptListenableFutures(Collection<ListenableFuture> futures) {
        for (ListenableFuture future : futures) {
            listenableFutures.add(future);
        }
        return this;
    }

    /**
     * Requests the hub to adopt and track multiple instances of {@link ListenableFuture}.
     * This means that the given instances will be tracked by the hub.
     *
     * @param futures a collection of futures to adopt
     * @return this, @see <a href="https://en.wikipedia.org/wiki/Fluent_interface">Fluent interface</a>
     */
    public RedFutureHub adoptListenableFutures(ListenableFuture<?>... futures) {
        Collections.addAll(listenableFutures, futures);
        return this;
    }

    /**
     * Requests the hub to return a single united, optimistic {@link RedFuture} instance.
     * Optimistic means that the returned future expects all the hub's tracked futures to resolve successfully,
     * namely, it will be:
     * Successfully resolved if and when all the hub's tracked futures are successfully resolved.
     * Failed if and when the first of the hub's tracked futures is failed.
     *
     * @return the united future.
     */
    public RedFuture uniteOptimistically() {
        RedFuture validated = validate();
        if (validated != null) {
            return validated;
        }
        ListenableFuture<List<Object>> collection = Futures.allAsList(listenableFutures);
        OpenRedFuture future = RedFuture.future();
        future.follow(collection);
        return future;
    }

    /**
     * Requests the hub to return a single united, pessimistic {@link RedFuture} instance.
     * Pessimistic means that the returned future expects all the hub's tracked futures to complete at some point,
     * namely, it will be successfully resolved once all the hub's tracked futures are completed.
     * It will not be failed in any case.
     *
     * @return the united future.
     */
    public RedFuture unitePessimistically() {
        RedFuture validated = validate();
        if (validated != null) {
            return validated;
        }
        ListenableFuture<List<Object>> collection = Futures.successfulAsList(listenableFutures);
        OpenRedFuture future = RedFuture.future();
        future.follow(collection);
        return future;
    }

    /**
     * Requests the hub to return a single united, cautious {@link RedFuture} instance.
     * Cautious means that the returned future expects all the hub's tracked futures to complete at some point,
     * but it still tracks their status. Namely, it will be:
     * Successfully resolved if and when all the hub's tracked futures are successfully resolved.
     * Failed if and when all the hub's tracked futures are completed, but at least one of them has failed,
     * in such a case, the failure callback throwable will be the cause of the first tracked future to fail.
     *
     * @return the united future.
     */
    public RedFuture uniteCautiously() {
        RedFuture validated = validate();
        if (validated != null) {
            return validated;
        }
        RedFuture optimistic = uniteOptimistically();
        RedFuture pessimistic = unitePessimistically();
        OpenRedFuture future = RedFuture.future();
        pessimistic.addSuccessCallback(() -> future.follow(optimistic));
        return future;
    }

    // Private

    private RedFuture validate() {
        if (listenableFutures.size() == 0) {
            return RedFuture.resolved();
        }
        if (listenableFutures.size() == 1) {
            OpenRedFuture future = RedFuture.future();
            future.follow(listenableFutures.get(0));
            return future;
        }
        return null;
    }

}
