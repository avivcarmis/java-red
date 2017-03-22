package com.javared.executor;


import com.google.common.util.concurrent.ListenableFuture;
import com.javared.future.OpenRedFutureOf;
import com.javared.future.RedFuture;
import com.javared.future.RedFutureOf;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * Created by avivc on 3/22/2017.
 */
public class RedExecutor {

    protected final Produce produce = new Produce();

    protected interface Anchor<T> extends RedFutureOf<T> {}

    protected class OpenAnchor<T> extends OpenRedFutureOf<T> implements Anchor<T> {

        private OpenAnchor() {}

    }

    protected class Produce {

        public <OF> ProduceValue<OF> valueOf(Class<OF> ofClass) {
            return new ProduceValue<>();
        }

        public <OF> ProduceFuture<OF> futureOf(Class<OF> ofClass) {
            return new ProduceFuture<>();
        }

        public <OF> ProduceListenableFuture<OF> listenableFutureOf(Class<OF> ofClass) {
            return new ProduceListenableFuture<>();
        }

        public <OF> ProduceRedFuture<OF> redFutureOf(Class<OF> ofClass) {
            return new ProduceRedFuture<>();
        }

    }

    protected class ProduceValue<OF> {



    }

    protected class ProduceFuture<OF> {

        private <R> Converter<Future<R>> converter

        public After<F0Future<OF>, Future<OF>, OF> after() {
            return new After<F0Future<OF>, Future<OF>, OF>(new Caller<F0Future<OF>, Future<OF>>() {
                @Override
                public Future<OF> call(F0Future<OF> f) {
                    return f.call();
                }
            }, new Converter<Future<OF>>() {
                @Override
                public <R> RedFutureOf<R> convert(Future<OF> midType) {
                    return null;
                }
            })
//            return new After<>(f -> doExecute(() -> RedFuture.convert(f.call())));
        }

        public <T0> After<OF, F1Future<OF, T0>> after(Anchor<T0> f0) {
//            return new After<>(f -> doExecute(() -> RedFuture.convert(f.call(f0.tryGet()))), f0);
        }

        public <T0, T1> After<OF, F2Future<OF, T0, T1>> after(Anchor<T0> f0, Anchor<T1> f1) {
//            return new After<>(f -> doExecute(() -> RedFuture.convert(f.call(f0.tryGet(), f1.tryGet()))), f0, f1);
        }

    }

    protected class ProduceListenableFuture<OF> {



    }

    protected class ProduceRedFuture<OF> {



    }

    private interface Caller<FUNCTION_INTERFACE, MID_TYPE> {
        MID_TYPE call(FUNCTION_INTERFACE f);
    }

    private interface Converter<T> {
        <R> RedFutureOf<R> convert(T midType);
    }

    public class After<FUNCTION_INTERFACE, MID_TYPE, R> {

        private final Caller<FUNCTION_INTERFACE, MID_TYPE> _caller;

        private final Converter<MID_TYPE> _converter;

        private final RedFuture[] _prerequisites;

        private After(Caller<FUNCTION_INTERFACE, MID_TYPE> caller,
                      Converter<MID_TYPE> converter,
                      RedFuture... prerequisites) {
            _caller = caller;
            _converter = converter;
            _prerequisites = prerequisites;
        }

        public Anchor<R> with(FUNCTION_INTERFACE function) {
            OpenAnchor<R> anchor = new OpenAnchor<>();
            RedFuture
                    .hub()
                    .adoptFutures(_prerequisites)
                    .uniteOptimistically()
                    .addFailureCallback(anchor::fail)
                    .addSuccessCallback(() -> {
                        try {
                            MID_TYPE midType = _caller.call(function);
                            RedFutureOf<R> futureToFollow = _converter.convert(midType);
                            anchor.follow(futureToFollow);
                        } catch (Throwable t) {
                            anchor.fail(t);
                        }
                    });
            return anchor;
        }

    }

    // 0 parameters

    protected <R> Anchor<R> execute(F0Direct<R> f) {
        return doExecute(() -> RedFuture.resolvedOf(f.call()));
    }

    protected interface F0Direct<R> {
        R call();
    }

    protected <R> Anchor<R> execute(F0Future<R> f) {
        return doExecute(() -> RedFuture.convert(f.call()));
    }

    protected interface F0Future<R> {
        Future<R> call();
    }

    protected <R> Anchor<R> execute(F0ListenableFuture<R> f) {
        return doExecute(() -> RedFuture.convert(f.call()));
    }

    protected interface F0ListenableFuture<R> {
        ListenableFuture<R> call();
    }

    protected <R> Anchor<R> execute(F0RedFuture<R> f) {
        return doExecute(f::call);
    }

    protected interface F0RedFuture<R> {
        Anchor<R> call();
    }

    // 1 parameters

    protected <R, T0> Anchor<R> execute(Anchor<T0> f0,
                                             F1Direct<R, T0> f) {
        return doExecute(() -> RedFuture.resolvedOf(f.call(f0.tryGet())));
    }

    protected interface F1Direct<R, T0> {
        R call(T0 t0);
    }

    protected <R, T0> Anchor<R> execute(Anchor<T0> f0,
                                             F1Future<R, T0> f) {
        return doExecute(() -> RedFuture.convert(f.call(f0.tryGet())));
    }

    protected interface F1Future<R, T0> {
        Future<R> call(T0 t0);
    }

    protected <R, T0> Anchor<R> execute(Anchor<T0> f0,
                                             F1ListenableFuture<R, T0> f) {
        return doExecute(() -> RedFuture.convert(f.call(f0.tryGet())));
    }

    protected interface F1ListenableFuture<R, T0> {
        ListenableFuture<R> call(T0 t0);
    }

    protected <R, T0> Anchor<R> execute(Anchor<T0> f0,
                                             F1RedFuture<R, T0> f) {
        return doExecute(() -> f.call(f0.tryGet()));
    }

    protected interface F1RedFuture<R, T0> {
        Anchor<R> call(T0 t0);
    }





}
