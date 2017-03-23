package com.javared.executor;


import com.google.common.util.concurrent.ListenableFuture;
import com.javared.future.OpenRedFuture;
import com.javared.future.OpenRedFutureOf;
import com.javared.future.RedFuture;
import com.javared.future.RedFutureOf;

import java.util.concurrent.Future;

/**
 * Created by avivc on 3/22/2017.
 */
public class RedExecutor {

    protected final Produce produce = new Produce();

    protected static class Produce {

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

    protected static class ProduceValue<OF> {

        public After<F0Direct<OF>, OF, OF> after() {
            return new After<>(F0Direct::call, RedFuture::resolvedOf);
        }

        public interface F0Direct<R> {
            R call();
        }

        public <T0> After<F1Direct<OF, T0>, OF, OF> after(Result<T0> f0) {
            return new After<>(f -> f.call(f0.tryGet()), RedFuture::resolvedOf, f0);
        }

        public interface F1Direct<R, T0> {
            R call(T0 t0);
        }

    }

    protected static class ProduceFuture<OF> {

        public After<F0Future<OF>, Future<OF>, OF> after() {
            return new After<>(F0Future::call, RedFuture::convert);
        }

        public interface F0Future<R> {
            Future<R> call();
        }

        public <T0> After<F1Future<OF, T0>, Future<OF>, OF> after(Result<T0> f0) {
            return new After<>(f -> f.call(f0.tryGet()), RedFuture::convert, f0);
        }

        public interface F1Future<R, T0> {
            Future<R> call(T0 t0);
        }

    }

    protected static class ProduceListenableFuture<OF> {

        public After<F0ListenableFuture<OF>, ListenableFuture<OF>, OF> after() {
            return new After<>(F0ListenableFuture::call, RedFuture::convert);
        }

        public interface F0ListenableFuture<R> {
            ListenableFuture<R> call();
        }

        public <T0> After<F1ListenableFuture<OF, T0>, ListenableFuture<OF>, OF> after(Result<T0> f0) {
            return new After<>(f -> f.call(f0.tryGet()), RedFuture::convert, f0);
        }

        public interface F1ListenableFuture<R, T0> {
            ListenableFuture<R> call(T0 t0);
        }

        public <T0, T1> After<F2ListenableFuture<OF, T0, T1>, ListenableFuture<OF>, OF> after(Result<T0> f0, Result<T1> f1) {
            return new After<>(f -> f.call(f0.tryGet(), f1.tryGet()), RedFuture::convert, f0, f1);
        }

        public interface F2ListenableFuture<R, T0, T1> {
            ListenableFuture<R> call(T0 t0, T1 t1);
        }

    }

    protected static class ProduceRedFuture<OF> {

        public After<F0RedFuture<OF>, RedFutureOf<OF>, OF> after() {
            return new After<>(F0RedFuture::call, o -> o);
        }

        public interface F0RedFuture<R> {
            Result<R> call();
        }

        public <T0> After<F1RedFuture<OF, T0>, RedFutureOf<OF>, OF> after(Result<T0> f0) {
            return new After<>(f -> f.call(f0.tryGet()), o -> o, f0);
        }

        public interface F1RedFuture<R, T0> {
            Result<R> call(T0 t0);
        }

    }

    public static class After<FUNCTION_INTERFACE, MID_TYPE, R> {

        private final Caller<FUNCTION_INTERFACE, MID_TYPE> _caller;

        private final Converter<MID_TYPE, R> _converter;

        private final RedFuture[] _prerequisites;

        private After(Caller<FUNCTION_INTERFACE, MID_TYPE> caller,
                      Converter<MID_TYPE, R> converter,
                      RedFuture... prerequisites) {
            _caller = caller;
            _converter = converter;
            _prerequisites = prerequisites;
        }

        public Result<R> byRunning(FUNCTION_INTERFACE function) {
            OpenResult<R> anchor = new OpenResult<>();
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

    protected interface Result<T> extends RedFutureOf<T> {}

    private static class OpenResult<T> extends OpenRedFutureOf<T> implements Result<T> {

        private OpenResult() {}

    }

    private interface Caller<FUNCTION_INTERFACE, MID_TYPE> {

        MID_TYPE call(FUNCTION_INTERFACE f);

    }

    private interface Converter<MID_TYPE, OF> {

        RedFutureOf<OF> convert(MID_TYPE midType);

    }




    public void x() {
        Result<String> stringResult = new OpenResult<>();
        Runner1Future<Boolean> z = once(stringResult).finish().produceAFutureOf(Boolean.class);
        z.

    }




    protected FutureTransformer0 once() {
        return new FutureTransformer0();
    }

    protected <T0> FutureTransformer1 once(Result<T0> f0) {
        return new FutureTransformer1();
    }

//    protected <R, T0, T1> FutureTransformer once(Result<T0> f0, Result<T1> f1) {
//        return new FutureTransformer(f0, f1);
//    }

    abstract protected static class FutureTransformer<T extends ReturnClassifier> {

        private static final Throwable FLIPPED_EXCEPTION = new Exception("flipped future");

        private final RedFuture[] _prerequisites;

        public FutureTransformer(RedFuture... prerequisites) {
            _prerequisites = prerequisites;
        }

        abstract protected T returnClassifier(RedFuture[] prerequisites);

        public T succeed() {
            return returnClassifier(_prerequisites);
        }

        public T fail() {
            return returnClassifier(mapPrerequisites(future -> {
                OpenRedFuture result = RedFuture.future();
                future.addSuccessCallback(() -> result.fail(FLIPPED_EXCEPTION));
                future.addFailureCallback(throwable -> result.resolve());
                return result;
            }));
        }

        public T finish() {
            return returnClassifier(mapPrerequisites(future -> {
                OpenRedFuture result = RedFuture.future();
                future.addFinallyCallback(result::resolve);
                return result;
            }));
        }

        private RedFuture[] mapPrerequisites(Mapper mapper) {
            RedFuture[] result = new RedFuture[_prerequisites.length];
            for (int i = 0; i < _prerequisites.length; i++) {
                result[i] = mapper.map(_prerequisites[i]);
            }
            return result;
        }

        private interface Mapper {

            RedFuture map(RedFuture future);

        }

    }

    abstract protected static class ReturnClassifier<T extends Runner> {

        protected final RedFuture[] _prerequisites;

        private ReturnClassifier(RedFuture[] prerequisites) {
            _prerequisites = prerequisites;
        }

    }

    abstract protected static class Runner<RETURN_TYPE> {

        protected final RedFuture[] _prerequisites;

        protected Runner(RedFuture[] prerequisites) {
            _prerequisites = prerequisites;
        }
    }

    protected static class FutureTransformer0 extends FutureTransformer<ReturnClassifier0> {
        @Override
        protected ReturnClassifier0 returnClassifier(RedFuture[] prerequisites) {
            return new ReturnClassifier0(prerequisites);
        }
    }

    protected static class ReturnClassifier0 extends ReturnClassifier<Runner0> {

        private ReturnClassifier0(RedFuture[] prerequisites) {
            super(prerequisites);
        }

        public <OF> Runner0Future<OF> produceAFutureOf(Class<OF> ofClass) {
            return new Runner0Future<>(_prerequisites);
        }

    }

    protected static class Runner0<RETURN_TYPE> extends Runner<RETURN_TYPE> {
        protected Runner0(RedFuture[] prerequisites) {
            super(prerequisites);
        }
    }

    protected static class Runner0Value<RETURN_TYPE> extends Runner0<RETURN_TYPE> {

        protected Runner0Value(RedFuture[] prerequisites) {
            super(prerequisites);
        }

        public void byRunning(ProduceValue.F0Direct<RETURN_TYPE> bla) {

        }

    }

    protected static class Runner0Future<RETURN_TYPE> extends Runner0<RETURN_TYPE> {

        protected Runner0Future(RedFuture[] prerequisites) {
            super(prerequisites);
        }

        public void byRunning(ProduceFuture.F0Future<RETURN_TYPE> bla) {

        }

    }

    protected static class Runner0ListenableFuture<RETURN_TYPE> extends Runner0<RETURN_TYPE> {

        protected Runner0ListenableFuture(RedFuture[] prerequisites) {
            super(prerequisites);
        }

        public void byRunning(ProduceListenableFuture.F0ListenableFuture<RETURN_TYPE> bla) {

        }

    }

    protected static class Runner0RedFuture<RETURN_TYPE> extends Runner0<RETURN_TYPE> {

        protected Runner0RedFuture(RedFuture[] prerequisites) {
            super(prerequisites);
        }

        public void byRunning(ProduceRedFuture.F0RedFuture<RETURN_TYPE> bla) {

        }

    }








    protected static class FutureTransformer1 extends FutureTransformer<ReturnClassifier1> {
        @Override
        protected ReturnClassifier1 returnClassifier(RedFuture[] prerequisites) {
            return new ReturnClassifier1(prerequisites);
        }
    }

    protected static class ReturnClassifier1 extends ReturnClassifier<Runner1> {

        private ReturnClassifier1(RedFuture[] prerequisites) {
            super(prerequisites);
        }

        public <OF> Runner1Future<OF> produceAFutureOf(Class<OF> ofClass) {
            return new Runner1Future<>(_prerequisites);
        }

    }

    protected static class Runner1<RETURN_TYPE> extends Runner<RETURN_TYPE> {
        protected Runner1(RedFuture[] prerequisites) {
            super(prerequisites);
        }
    }

    protected static class Runner1Value<RETURN_TYPE> extends Runner1<RETURN_TYPE> {

        protected Runner1Value(RedFuture[] prerequisites) {
            super(prerequisites);
        }

        public void byRunning(ProduceValue.F0Direct<RETURN_TYPE> bla) {

        }

    }

    protected static class Runner1Future<RETURN_TYPE> extends Runner1<RETURN_TYPE> {

        protected Runner1Future(RedFuture[] prerequisites) {
            super(prerequisites);
        }

        public void byRunning(ProduceFuture.F1Future<RETURN_TYPE> bla) {

        }

    }

    protected static class Runner1ListenableFuture<RETURN_TYPE> extends Runner1<RETURN_TYPE> {

        protected Runner1ListenableFuture(RedFuture[] prerequisites) {
            super(prerequisites);
        }

        public void byRunning(ProduceListenableFuture.F0ListenableFuture<RETURN_TYPE> bla) {

        }

    }

    protected static class Runner1RedFuture<RETURN_TYPE> extends Runner1<RETURN_TYPE> {

        protected Runner1RedFuture(RedFuture[] prerequisites) {
            super(prerequisites);
        }

        public void byRunning(ProduceRedFuture.F0RedFuture<RETURN_TYPE> bla) {

        }

    }



}
