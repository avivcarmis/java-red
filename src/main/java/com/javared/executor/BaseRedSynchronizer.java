package com.javared.executor;

import com.google.common.util.concurrent.ListenableFuture;
import com.javared.future.OpenRedFuture;
import com.javared.future.OpenRedFutureOf;
import com.javared.future.RedFuture;
import com.javared.future.RedFutureOf;

import java.util.Arrays;
import java.util.concurrent.Future;
import java.util.function.Function;

abstract public class BaseRedSynchronizer {

    // Constants

    private static final BaseRedSynchronizer.ReturnClassifier.ReturnClassifier0 RETURN_CLASSIFIER_0 =
            new BaseRedSynchronizer.ReturnClassifier.ReturnClassifier0();

    // Private

    protected Marker execute(Executables.Command0 command) {
        return RETURN_CLASSIFIER_0.execute(command);
    }

    protected <R> Runner.Runner0<R, R> produce(Class<R> tClass) {
        return RETURN_CLASSIFIER_0.produce(tClass);
    }

    protected <R> Runner.Runner0<Future<R>, R> produceFutureOf(Class<R> tClass) {
        return RETURN_CLASSIFIER_0.produceFutureOf(tClass);
    }

    protected <R> Runner.Runner0<ListenableFuture<R>, R> produceListenableFutureOf(Class<R> tClass) {
        return RETURN_CLASSIFIER_0.produceListenableFutureOf(tClass);
    }

    protected <R> Runner.Runner0<RedFutureOf<R>, R> produceRedFutureOf(Class<R> tClass) {
        return RETURN_CLASSIFIER_0.produceRedFutureOf(tClass);
    }

    protected FutureTransformer.UnlockedFutureTransformer0
    onceMarkers(Marker... markers) {
        return new FutureTransformer.UnlockedFutureTransformer0(markers);
    }

    protected <T0>
    FutureTransformer.UnlockedFutureTransformer1<T0>
    onceResult(Result<T0> f0) {
        return new FutureTransformer.UnlockedFutureTransformer1<>(f0);
    }

    protected <T0, T1>
    FutureTransformer.UnlockedFutureTransformer2<T0, T1>
    onceResults(Result<T0> f0, Result<T1> f1) {
        return new FutureTransformer.UnlockedFutureTransformer2<>(f0, f1);
    }

    protected <T0, T1, T2>
    FutureTransformer.UnlockedFutureTransformer3<T0, T1, T2>
    onceResults(Result<T0> f0, Result<T1> f1, Result<T2> f2) {
        return new FutureTransformer.UnlockedFutureTransformer3<>(f0, f1, f2);
    }

    protected <T0, T1, T2, T3>
    FutureTransformer.UnlockedFutureTransformer4<T0, T1, T2, T3>
    onceResults(Result<T0> f0, Result<T1> f1, Result<T2> f2, Result<T3> f3) {
        return new FutureTransformer.UnlockedFutureTransformer4<>(f0, f1, f2, f3);
    }

    protected <T0, T1, T2, T3, T4>
    FutureTransformer.UnlockedFutureTransformer5<T0, T1, T2, T3, T4>
    onceResults(Result<T0> f0, Result<T1> f1, Result<T2> f2, Result<T3> f3, Result<T4> f4) {
        return new FutureTransformer.UnlockedFutureTransformer5<>(f0, f1, f2, f3, f4);
    }

    protected <T0, T1, T2, T3, T4, T5>
    FutureTransformer.UnlockedFutureTransformer6<T0, T1, T2, T3, T4, T5>
    onceResults(Result<T0> f0, Result<T1> f1, Result<T2> f2, Result<T3> f3, Result<T4> f4,
                Result<T5> f5) {
        return new FutureTransformer.UnlockedFutureTransformer6<>(f0, f1, f2, f3, f4, f5);
    }

    protected <T0, T1, T2, T3, T4, T5, T6>
    FutureTransformer.UnlockedFutureTransformer7<T0, T1, T2, T3, T4, T5, T6>
    onceResults(Result<T0> f0, Result<T1> f1, Result<T2> f2, Result<T3> f3, Result<T4> f4,
                Result<T5> f5, Result<T6> f6) {
        return new FutureTransformer.UnlockedFutureTransformer7<>(f0, f1, f2, f3, f4, f5, f6);
    }

    protected <T0, T1, T2, T3, T4, T5, T6, T7>
    FutureTransformer.UnlockedFutureTransformer8<T0, T1, T2, T3, T4, T5, T6, T7>
    onceResults(Result<T0> f0, Result<T1> f1, Result<T2> f2, Result<T3> f3, Result<T4> f4,
                Result<T5> f5, Result<T6> f6, Result<T7> f7) {
        return new FutureTransformer.UnlockedFutureTransformer8<>(f0, f1, f2, f3, f4, f5, f6, f7);
    }

    protected <T0, T1, T2, T3, T4, T5, T6, T7, T8>
    FutureTransformer.UnlockedFutureTransformer9<T0, T1, T2, T3, T4, T5, T6, T7, T8>
    onceResults(Result<T0> f0, Result<T1> f1, Result<T2> f2, Result<T3> f3, Result<T4> f4,
                Result<T5> f5, Result<T6> f6, Result<T7> f7, Result<T8> f8) {
        return new FutureTransformer.UnlockedFutureTransformer9<>(f0, f1, f2, f3, f4, f5, f6, f7, f8);
    }

    protected <T0, T1, T2, T3, T4, T5, T6, T7, T8, T9>
    FutureTransformer.UnlockedFutureTransformer10<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9>
    onceResults(Result<T0> f0, Result<T1> f1, Result<T2> f2, Result<T3> f3, Result<T4> f4,
                Result<T5> f5, Result<T6> f6, Result<T7> f7, Result<T8> f8, Result<T9> f9) {
        return new FutureTransformer.UnlockedFutureTransformer10<>(f0, f1, f2, f3, f4, f5, f6, f7, f8, f9);
    }

    protected FutureTransformer.UnlockedFutureTransformerN onceResults(Result... results) {
        return new FutureTransformer.UnlockedFutureTransformerN(results);
    }

    // Static

    protected static class Result<T> {

        final OpenRedFutureOf<T> _future;

        private Result() {
            _future = RedFuture.futureOf();
        }

    }

    protected static class Marker {

        final OpenRedFuture _future;

        private Marker() {
            _future = RedFuture.future();
        }

    }

    protected static class PendingMarker extends Marker {

        public void complete() {
            _future.resolve();
        }

        public void fail(Throwable t) {
            _future.fail(t);
        }

    }

    protected static class Results extends PrerequisitesHolder {

        private Results(RedFuture[] prerequisites) {
            super(prerequisites);
        }

        public <T> T result(int index, Class<T> tClass) {
            try {
                return result(index);
            } catch (Throwable t) {
                throw new IllegalArgumentException("result " + index + " is not of type " + tClass.getName(), t);
            }
        }

    }

    abstract protected static class FutureTransformer<RETURN_CLASSIFIER> {

        private static final Throwable FLIPPED_EXCEPTION = new Exception("flipped future");

        private final RedFuture[] _previousLayer;

        private final RedFuture[] _currentLayer;

        private FutureTransformer(RedFuture[] previousLayer, RedFuture[] currentLayer) {
            _previousLayer = previousLayer == null ? new RedFuture[0] : previousLayer;
            _currentLayer = currentLayer == null ? new RedFuture[0] : currentLayer;
        }

        public RETURN_CLASSIFIER succeed() {
            return createClassifier(_currentLayer);
        }

        public RETURN_CLASSIFIER finish() {
            return createClassifier(future -> {
                if (future instanceof RedFutureOf) {
                    RedFutureOf<?> futureOf = (RedFutureOf) future;
                    OpenRedFutureOf<Object> open = RedFuture.futureOf();
                    futureOf.addSuccessCallback(open::resolve);
                    future.addFailureCallback(throwable -> open.resolve(null));
                    return open;
                }
                OpenRedFuture open = RedFuture.future();
                future.addFinallyCallback(open::resolve);
                return open;
            });
        }

        private RETURN_CLASSIFIER createClassifier(RedFuture[] transformedPrerequisites) {
            RedFuture[] allPrerequisites = new RedFuture[transformedPrerequisites.length + _previousLayer.length];
            System.arraycopy(_previousLayer, 0, allPrerequisites, 0, _previousLayer.length);
            System.arraycopy(transformedPrerequisites, 0, allPrerequisites, _previousLayer.length,
                    transformedPrerequisites.length);
            return classifier(allPrerequisites);
        }

        RedFuture[] mapCurrentLayer(Function<RedFuture, RedFuture> mapper) {
            return Arrays.stream(_currentLayer).map(mapper).toArray(RedFuture[]::new);
        }

        RETURN_CLASSIFIER createClassifier(Function<RedFuture, RedFuture> mapper) {
            return createClassifier(mapCurrentLayer(mapper));
        }

        abstract protected RETURN_CLASSIFIER classifier(RedFuture[] prerequisites);

        abstract protected static class UnlockedFutureTransformer<RETURN_CLASSIFIER>
                extends FutureTransformer<RETURN_CLASSIFIER> {

            private UnlockedFutureTransformer(Result... results) {
                super(null, Arrays.stream(results).map(r -> r._future).toArray(RedFuture[]::new));
            }

            private UnlockedFutureTransformer(Marker... markers) {
                super(null, Arrays.stream(markers).map(m -> m._future).toArray(RedFuture[]::new));
            }

            public ReturnClassifier.ReturnClassifier0 fail() {
                return new ReturnClassifier.ReturnClassifier0(mapCurrentLayer(future -> {
                    OpenRedFuture result = RedFuture.future();
                    future.addSuccessCallback(() -> result.fail(FLIPPED_EXCEPTION));
                    future.addFailureCallback(throwable -> result.resolve());
                    return result;
                }));
            }

        }

        public static class UnlockedFutureTransformer0
                extends UnlockedFutureTransformer<ReturnClassifier.ReturnClassifier0> {

            private UnlockedFutureTransformer0(Marker... markers) {
                super(markers);
            }

            @Override
            protected ReturnClassifier.ReturnClassifier0 classifier(RedFuture[] prerequisites) {
                return new ReturnClassifier.ReturnClassifier0(prerequisites);
            }

        }

        public static class UnlockedFutureTransformer1<T0>
                extends UnlockedFutureTransformer<ReturnClassifier.ReturnClassifier1<T0>> {

            private UnlockedFutureTransformer1(Result... results) {
                super(results);
            }

            @Override
            protected ReturnClassifier.ReturnClassifier1<T0> classifier(RedFuture[] prerequisites) {
                return new ReturnClassifier.ReturnClassifier1<>(prerequisites);
            }

        }

        public static class UnlockedFutureTransformer2<T0, T1>
                extends UnlockedFutureTransformer<ReturnClassifier.ReturnClassifier2<T0, T1>> {

            private UnlockedFutureTransformer2(Result... results) {
                super(results);
            }

            @Override
            protected ReturnClassifier.ReturnClassifier2<T0, T1> classifier(RedFuture[] prerequisites) {
                return new ReturnClassifier.ReturnClassifier2<>(prerequisites);
            }

        }

        public static class UnlockedFutureTransformer3<T0, T1, T2>
                extends UnlockedFutureTransformer<ReturnClassifier.ReturnClassifier3<T0, T1, T2>> {

            private UnlockedFutureTransformer3(Result... results) {
                super(results);
            }

            @Override
            protected ReturnClassifier.ReturnClassifier3<T0, T1, T2> classifier(RedFuture[] prerequisites) {
                return new ReturnClassifier.ReturnClassifier3<>(prerequisites);
            }

        }

        public static class UnlockedFutureTransformer4<T0, T1, T2, T3>
                extends UnlockedFutureTransformer<ReturnClassifier.ReturnClassifier4<T0, T1, T2, T3>> {

            private UnlockedFutureTransformer4(Result... results) {
                super(results);
            }

            @Override
            protected ReturnClassifier.ReturnClassifier4<T0, T1, T2, T3> classifier(RedFuture[] prerequisites) {
                return new ReturnClassifier.ReturnClassifier4<>(prerequisites);
            }

        }

        public static class UnlockedFutureTransformer5<T0, T1, T2, T3, T4>
                extends UnlockedFutureTransformer<ReturnClassifier.ReturnClassifier5<T0, T1, T2, T3, T4>> {

            private UnlockedFutureTransformer5(Result... results) {
                super(results);
            }

            @Override
            protected ReturnClassifier.ReturnClassifier5<T0, T1, T2, T3, T4> classifier(RedFuture[] prerequisites) {
                return new ReturnClassifier.ReturnClassifier5<>(prerequisites);
            }

        }

        public static class UnlockedFutureTransformer6<T0, T1, T2, T3, T4, T5>
                extends UnlockedFutureTransformer<ReturnClassifier.ReturnClassifier6<T0, T1, T2, T3, T4, T5>> {

            private UnlockedFutureTransformer6(Result... results) {
                super(results);
            }

            @Override
            protected ReturnClassifier.ReturnClassifier6<T0, T1, T2, T3, T4, T5> classifier(RedFuture[] prerequisites) {
                return new ReturnClassifier.ReturnClassifier6<>(prerequisites);
            }

        }

        public static class UnlockedFutureTransformer7<T0, T1, T2, T3, T4, T5, T6>
                extends UnlockedFutureTransformer<ReturnClassifier.ReturnClassifier7
                <T0, T1, T2, T3, T4, T5, T6>> {

            private UnlockedFutureTransformer7(Result... results) {
                super(results);
            }

            @Override
            protected ReturnClassifier.ReturnClassifier7<T0, T1, T2, T3, T4, T5, T6>
            classifier(RedFuture[] prerequisites) {
                return new ReturnClassifier.ReturnClassifier7<>(prerequisites);
            }

        }

        public static class UnlockedFutureTransformer8<T0, T1, T2, T3, T4, T5, T6, T7>
                extends UnlockedFutureTransformer<ReturnClassifier.ReturnClassifier8
                <T0, T1, T2, T3, T4, T5, T6, T7>> {

            private UnlockedFutureTransformer8(Result... results) {
                super(results);
            }

            @Override
            protected ReturnClassifier.ReturnClassifier8<T0, T1, T2, T3, T4, T5, T6, T7>
            classifier(RedFuture[] prerequisites) {
                return new ReturnClassifier.ReturnClassifier8<>(prerequisites);
            }

        }

        public static class UnlockedFutureTransformer9<T0, T1, T2, T3, T4, T5, T6, T7, T8>
                extends UnlockedFutureTransformer<ReturnClassifier.ReturnClassifier9
                <T0, T1, T2, T3, T4, T5, T6, T7, T8>> {

            private UnlockedFutureTransformer9(Result... results) {
                super(results);
            }

            @Override
            protected ReturnClassifier.ReturnClassifier9<T0, T1, T2, T3, T4, T5, T6, T7, T8>
            classifier(RedFuture[] prerequisites) {
                return new ReturnClassifier.ReturnClassifier9<>(prerequisites);
            }

        }

        public static class UnlockedFutureTransformer10<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9>
                extends UnlockedFutureTransformer<ReturnClassifier.ReturnClassifier10
                <T0, T1, T2, T3, T4, T5, T6, T7, T8, T9>> {

            private UnlockedFutureTransformer10(Result... results) {
                super(results);
            }

            @Override
            protected ReturnClassifier.ReturnClassifier10<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9>
            classifier(RedFuture[] prerequisites) {
                return new ReturnClassifier.ReturnClassifier10<>(prerequisites);
            }

        }

        public static class UnlockedFutureTransformerN
                extends UnlockedFutureTransformer<ReturnClassifier.ReturnClassifierN> {

            private UnlockedFutureTransformerN(Result... results) {
                super(results);
            }

            @Override
            protected ReturnClassifier.ReturnClassifierN classifier(RedFuture[] prerequisites) {
                return new ReturnClassifier.ReturnClassifierN(prerequisites);
            }

        }

        abstract protected static class LockedFutureTransformer<RETURN_CLASSIFIER>
                extends FutureTransformer<RETURN_CLASSIFIER> {

            private LockedFutureTransformer(RedFuture[] oldPrerequisites, Marker... markers) {
                super(oldPrerequisites, Arrays.stream(markers).map(marker -> marker._future).toArray(RedFuture[]::new));
            }

            public RETURN_CLASSIFIER fail() {
                return createClassifier(future -> {
                    OpenRedFuture result = RedFuture.future();
                    future.addSuccessCallback(() -> result.fail(FLIPPED_EXCEPTION));
                    future.addFailureCallback(throwable -> result.resolve());
                    return result;
                });
            }

        }

        public static class LockedFutureTransformer0
                extends LockedFutureTransformer<ReturnClassifier.ReturnClassifier0> {

            private LockedFutureTransformer0(RedFuture[] oldPrerequisites, Marker... markers) {
                super(oldPrerequisites, markers);
            }

            @Override
            protected ReturnClassifier.ReturnClassifier0 classifier(RedFuture[] prerequisites) {
                return new ReturnClassifier.ReturnClassifier0(prerequisites);
            }

        }

        public static class LockedFutureTransformer1<T0>
                extends LockedFutureTransformer<ReturnClassifier.ReturnClassifier1<T0>> {

            private LockedFutureTransformer1(RedFuture[] oldPrerequisites, Marker... markers) {
                super(oldPrerequisites, markers);
            }

            @Override
            protected ReturnClassifier.ReturnClassifier1<T0> classifier(RedFuture[] prerequisites) {
                return new ReturnClassifier.ReturnClassifier1<>(prerequisites);
            }

        }

        public static class LockedFutureTransformer2<T0, T1>
                extends LockedFutureTransformer<ReturnClassifier.ReturnClassifier2<T0, T1>> {

            private LockedFutureTransformer2(RedFuture[] oldPrerequisites, Marker... markers) {
                super(oldPrerequisites, markers);
            }

            @Override
            protected ReturnClassifier.ReturnClassifier2<T0, T1> classifier(RedFuture[] prerequisites) {
                return new ReturnClassifier.ReturnClassifier2<>(prerequisites);
            }

        }

        public static class LockedFutureTransformer3<T0, T1, T2>
                extends LockedFutureTransformer<ReturnClassifier.ReturnClassifier3<T0, T1, T2>> {

            private LockedFutureTransformer3(RedFuture[] oldPrerequisites, Marker... markers) {
                super(oldPrerequisites, markers);
            }

            @Override
            protected ReturnClassifier.ReturnClassifier3<T0, T1, T2> classifier(RedFuture[] prerequisites) {
                return new ReturnClassifier.ReturnClassifier3<>(prerequisites);
            }

        }

        public static class LockedFutureTransformer4<T0, T1, T2, T3>
                extends LockedFutureTransformer<ReturnClassifier.ReturnClassifier4<T0, T1, T2, T3>> {

            private LockedFutureTransformer4(RedFuture[] oldPrerequisites, Marker... markers) {
                super(oldPrerequisites, markers);
            }

            @Override
            protected ReturnClassifier.ReturnClassifier4<T0, T1, T2, T3> classifier(RedFuture[] prerequisites) {
                return new ReturnClassifier.ReturnClassifier4<>(prerequisites);
            }

        }

        public static class LockedFutureTransformer5<T0, T1, T2, T3, T4>
                extends LockedFutureTransformer<ReturnClassifier.ReturnClassifier5<T0, T1, T2, T3, T4>> {

            private LockedFutureTransformer5(RedFuture[] oldPrerequisites, Marker... markers) {
                super(oldPrerequisites, markers);
            }

            @Override
            protected ReturnClassifier.ReturnClassifier5<T0, T1, T2, T3, T4> classifier(RedFuture[] prerequisites) {
                return new ReturnClassifier.ReturnClassifier5<>(prerequisites);
            }

        }

        public static class LockedFutureTransformer6<T0, T1, T2, T3, T4, T5>
                extends LockedFutureTransformer<ReturnClassifier.ReturnClassifier6<T0, T1, T2, T3, T4, T5>> {

            private LockedFutureTransformer6(RedFuture[] oldPrerequisites, Marker... markers) {
                super(oldPrerequisites, markers);
            }

            @Override
            protected ReturnClassifier.ReturnClassifier6<T0, T1, T2, T3, T4, T5> classifier(RedFuture[] prerequisites) {
                return new ReturnClassifier.ReturnClassifier6<>(prerequisites);
            }

        }

        public static class LockedFutureTransformer7<T0, T1, T2, T3, T4, T5, T6>
                extends LockedFutureTransformer<ReturnClassifier.ReturnClassifier7<T0, T1, T2, T3, T4, T5, T6>> {

            private LockedFutureTransformer7(RedFuture[] oldPrerequisites, Marker... markers) {
                super(oldPrerequisites, markers);
            }

            @Override
            protected ReturnClassifier.ReturnClassifier7<T0, T1, T2, T3, T4, T5, T6>
            classifier(RedFuture[] prerequisites) {
                return new ReturnClassifier.ReturnClassifier7<>(prerequisites);
            }

        }

        public static class LockedFutureTransformer8<T0, T1, T2, T3, T4, T5, T6, T7>
                extends LockedFutureTransformer<ReturnClassifier.ReturnClassifier8<T0, T1, T2, T3, T4, T5, T6, T7>> {

            private LockedFutureTransformer8(RedFuture[] oldPrerequisites, Marker... markers) {
                super(oldPrerequisites, markers);
            }

            @Override
            protected ReturnClassifier.ReturnClassifier8<T0, T1, T2, T3, T4, T5, T6, T7>
            classifier(RedFuture[] prerequisites) {
                return new ReturnClassifier.ReturnClassifier8<>(prerequisites);
            }

        }

        public static class LockedFutureTransformer9<T0, T1, T2, T3, T4, T5, T6, T7, T8>
                extends LockedFutureTransformer<ReturnClassifier.ReturnClassifier9
                <T0, T1, T2, T3, T4, T5, T6, T7, T8>> {

            private LockedFutureTransformer9(RedFuture[] oldPrerequisites, Marker... markers) {
                super(oldPrerequisites, markers);
            }

            @Override
            protected ReturnClassifier.ReturnClassifier9<T0, T1, T2, T3, T4, T5, T6, T7, T8>
            classifier(RedFuture[] prerequisites) {
                return new ReturnClassifier.ReturnClassifier9<>(prerequisites);
            }

        }

        public static class LockedFutureTransformer10<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9>
                extends LockedFutureTransformer<ReturnClassifier.ReturnClassifier10
                <T0, T1, T2, T3, T4, T5, T6, T7, T8, T9>> {

            private LockedFutureTransformer10(RedFuture[] oldPrerequisites, Marker... markers) {
                super(oldPrerequisites, markers);
            }

            @Override
            protected ReturnClassifier.ReturnClassifier10<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9>
            classifier(RedFuture[] prerequisites) {
                return new ReturnClassifier.ReturnClassifier10<>(prerequisites);
            }

        }

        public static class LockedFutureTransformerN
                extends LockedFutureTransformer<ReturnClassifier.ReturnClassifierN> {

            private LockedFutureTransformerN(RedFuture[] oldPrerequisites, Marker... markers) {
                super(oldPrerequisites, markers);
            }

            @Override
            protected ReturnClassifier.ReturnClassifierN classifier(RedFuture[] prerequisites) {
                return new ReturnClassifier.ReturnClassifierN(prerequisites);
            }

        }

    }

    abstract protected static class ReturnClassifier<COMMAND, TRANSFORMER> extends PrerequisitesHolder {

        private ReturnClassifier(RedFuture[] prerequisites) {
            super(prerequisites);
        }

        public Marker execute(COMMAND c) {
            PendingMarker marker = new PendingMarker();
            RedFuture
                    .hub()
                    .adoptFutures(prerequisites())
                    .uniteOptimistically()
                    .addFailureCallback(marker._future::fail)
                    .addSuccessCallback(() -> {
                        try {
                            call(c, marker);
                        } catch (Throwable t) {
                            marker._future.fail(t);
                        }
                    });
            return marker;
        }

        public TRANSFORMER andMarkers(Marker... markers) {
            return transformer(prerequisites(), markers);
        }

        abstract protected void call(COMMAND c, PendingMarker pendingMarker) throws Throwable;

        abstract protected TRANSFORMER transformer(RedFuture[] oldPrerequisites, Marker... markers);

        @SuppressWarnings("unused")
        public static class ReturnClassifier0
                extends ReturnClassifier<
                Executables.Command0,
                FutureTransformer.LockedFutureTransformer0
                > {

            private ReturnClassifier0() {
                super(new RedFuture[0]);
            }

            private ReturnClassifier0(RedFuture[] prerequisites) {
                super(prerequisites);
            }

            @Override
            protected void call(Executables.Command0 c, PendingMarker pendingMarker) throws Throwable {
                c.call(pendingMarker);
            }

            @Override
            protected FutureTransformer.LockedFutureTransformer0
            transformer(RedFuture[] oldPrerequisites, Marker... markers) {
                return new FutureTransformer.LockedFutureTransformer0(oldPrerequisites, markers);
            }

            public <R> Runner.Runner0<R, R>
            produce(Class<R> tClass) {
                return new Runner.Runner0<>(prerequisites(), valueConverter());
            }

            public <R> Runner.Runner0<Future<R>, R>
            produceFutureOf(Class<R> tClass) {
                return new Runner.Runner0<>(prerequisites(), futureConverter());
            }

            public <R> Runner.Runner0<ListenableFuture<R>, R>
            produceListenableFutureOf(Class<R> tClass) {
                return new Runner.Runner0<>(prerequisites(), listenableFutureConverter());
            }

            public <R> Runner.Runner0<RedFutureOf<R>, R>
            produceRedFutureOf(Class<R> tClass) {
                return new Runner.Runner0<>(prerequisites(), redFutureConverter());
            }

        }

        @SuppressWarnings("unused")
        public static class ReturnClassifier1<T0>
                extends ReturnClassifier<
                Executables.Command1<T0>,
                FutureTransformer.LockedFutureTransformer1<T0>
                > {

            private ReturnClassifier1(RedFuture[] prerequisites) {
                super(prerequisites);
            }

            @Override
            protected void call(Executables.Command1<T0> c, PendingMarker pendingMarker) throws Throwable {
                c.call(pendingMarker, result(0));
            }

            @Override
            protected FutureTransformer.LockedFutureTransformer1<T0>
            transformer(RedFuture[] oldPrerequisites, Marker... markers) {
                return new FutureTransformer.LockedFutureTransformer1<>(oldPrerequisites, markers);
            }

            public <R> Runner.Runner1<R, R, T0>
            produce(Class<R> tClass) {
                return new Runner.Runner1<>(prerequisites(), valueConverter());
            }

            public <R> Runner.Runner1<Future<R>, R, T0>
            produceFutureOf(Class<R> tClass) {
                return new Runner.Runner1<>(prerequisites(), futureConverter());
            }

            public <R> Runner.Runner1<ListenableFuture<R>, R, T0>
            produceListenableFutureOf(Class<R> tClass) {
                return new Runner.Runner1<>(prerequisites(), listenableFutureConverter());
            }

            public <R> Runner.Runner1<RedFutureOf<R>, R, T0>
            produceRedFutureOf(Class<R> tClass) {
                return new Runner.Runner1<>(prerequisites(), redFutureConverter());
            }

        }

        @SuppressWarnings("unused")
        public static class ReturnClassifier2<T0, T1>
                extends ReturnClassifier<
                Executables.Command2<T0, T1>,
                FutureTransformer.LockedFutureTransformer2<T0, T1>
                > {

            private ReturnClassifier2(RedFuture[] prerequisites) {
                super(prerequisites);
            }

            @Override
            protected void call(Executables.Command2<T0, T1> c, PendingMarker pendingMarker) throws Throwable {
                c.call(pendingMarker, result(0), result(1));
            }

            @Override
            protected FutureTransformer.LockedFutureTransformer2<T0, T1>
            transformer(RedFuture[] oldPrerequisites, Marker... markers) {
                return new FutureTransformer.LockedFutureTransformer2<>(oldPrerequisites, markers);
            }

            public <R> Runner.Runner2<R, R, T0, T1>
            produce(Class<R> tClass) {
                return new Runner.Runner2<>(prerequisites(), valueConverter());
            }

            public <R> Runner.Runner2<Future<R>, R, T0, T1>
            produceFutureOf(Class<R> tClass) {
                return new Runner.Runner2<>(prerequisites(), futureConverter());
            }

            public <R> Runner.Runner2<ListenableFuture<R>, R, T0, T1>
            produceListenableFutureOf(Class<R> tClass) {
                return new Runner.Runner2<>(prerequisites(), listenableFutureConverter());
            }

            public <R> Runner.Runner2<RedFutureOf<R>, R, T0, T1>
            produceRedFutureOf(Class<R> tClass) {
                return new Runner.Runner2<>(prerequisites(), redFutureConverter());
            }

        }

        @SuppressWarnings("unused")
        public static class ReturnClassifier3<T0, T1, T2>
                extends ReturnClassifier<
                Executables.Command3<T0, T1, T2>,
                FutureTransformer.LockedFutureTransformer3<T0, T1, T2>
                > {

            private ReturnClassifier3(RedFuture[] prerequisites) {
                super(prerequisites);
            }

            @Override
            protected void call(Executables.Command3<T0, T1, T2> c, PendingMarker pendingMarker) throws Throwable {
                c.call(pendingMarker, result(0), result(1), result(2));
            }

            @Override
            protected FutureTransformer.LockedFutureTransformer3<T0, T1, T2>
            transformer(RedFuture[] oldPrerequisites, Marker... markers) {
                return new FutureTransformer.LockedFutureTransformer3<>(oldPrerequisites, markers);
            }

            public <R> Runner.Runner3<R, R, T0, T1, T2>
            produce(Class<R> tClass) {
                return new Runner.Runner3<>(prerequisites(), valueConverter());
            }

            public <R> Runner.Runner3<Future<R>, R, T0, T1, T2>
            produceFutureOf(Class<R> tClass) {
                return new Runner.Runner3<>(prerequisites(), futureConverter());
            }

            public <R> Runner.Runner3<ListenableFuture<R>, R, T0, T1, T2>
            produceListenableFutureOf(Class<R> tClass) {
                return new Runner.Runner3<>(prerequisites(), listenableFutureConverter());
            }

            public <R> Runner.Runner3<RedFutureOf<R>, R, T0, T1, T2>
            produceRedFutureOf(Class<R> tClass) {
                return new Runner.Runner3<>(prerequisites(), redFutureConverter());
            }

        }

        @SuppressWarnings("unused")
        public static class ReturnClassifier4<T0, T1, T2, T3>
                extends ReturnClassifier<
                Executables.Command4<T0, T1, T2, T3>,
                FutureTransformer.LockedFutureTransformer4<T0, T1, T2, T3>
                > {

            private ReturnClassifier4(RedFuture[] prerequisites) {
                super(prerequisites);
            }

            @Override
            protected void call(Executables.Command4<T0, T1, T2, T3> c, PendingMarker pendingMarker) throws Throwable {
                c.call(pendingMarker, result(0), result(1), result(2), result(3));
            }

            @Override
            protected FutureTransformer.LockedFutureTransformer4<T0, T1, T2, T3>
            transformer(RedFuture[] oldPrerequisites, Marker... markers) {
                return new FutureTransformer.LockedFutureTransformer4<>(oldPrerequisites, markers);
            }

            public <R> Runner.Runner4<R, R, T0, T1, T2, T3>
            produce(Class<R> tClass) {
                return new Runner.Runner4<>(prerequisites(), valueConverter());
            }

            public <R> Runner.Runner4<Future<R>, R, T0, T1, T2, T3>
            produceFutureOf(Class<R> tClass) {
                return new Runner.Runner4<>(prerequisites(), futureConverter());
            }

            public <R> Runner.Runner4<ListenableFuture<R>, R, T0, T1, T2, T3>
            produceListenableFutureOf(Class<R> tClass) {
                return new Runner.Runner4<>(prerequisites(), listenableFutureConverter());
            }

            public <R> Runner.Runner4<RedFutureOf<R>, R, T0, T1, T2, T3>
            produceRedFutureOf(Class<R> tClass) {
                return new Runner.Runner4<>(prerequisites(), redFutureConverter());
            }

        }

        @SuppressWarnings("unused")
        public static class ReturnClassifier5<T0, T1, T2, T3, T4>
                extends ReturnClassifier<
                Executables.Command5<T0, T1, T2, T3, T4>,
                FutureTransformer.LockedFutureTransformer5<T0, T1, T2, T3, T4>
                > {

            private ReturnClassifier5(RedFuture[] prerequisites) {
                super(prerequisites);
            }

            @Override
            protected void call(Executables.Command5<T0, T1, T2, T3, T4> c, PendingMarker pendingMarker)
                    throws Throwable {
                c.call(pendingMarker, result(0), result(1), result(2), result(3), result(4));
            }

            @Override
            protected FutureTransformer.LockedFutureTransformer5<T0, T1, T2, T3, T4>
            transformer(RedFuture[] oldPrerequisites, Marker... markers) {
                return new FutureTransformer.LockedFutureTransformer5<>(oldPrerequisites, markers);
            }

            public <R> Runner.Runner5<R, R, T0, T1, T2, T3, T4>
            produce(Class<R> tClass) {
                return new Runner.Runner5<>(prerequisites(), valueConverter());
            }

            public <R> Runner.Runner5<Future<R>, R, T0, T1, T2, T3, T4>
            produceFutureOf(Class<R> tClass) {
                return new Runner.Runner5<>(prerequisites(), futureConverter());
            }

            public <R> Runner.Runner5<ListenableFuture<R>, R, T0, T1, T2, T3, T4>
            produceListenableFutureOf(Class<R> tClass) {
                return new Runner.Runner5<>(prerequisites(), listenableFutureConverter());
            }

            public <R> Runner.Runner5<RedFutureOf<R>, R, T0, T1, T2, T3, T4>
            produceRedFutureOf(Class<R> tClass) {
                return new Runner.Runner5<>(prerequisites(), redFutureConverter());
            }

        }

        @SuppressWarnings("unused")
        public static class ReturnClassifier6<T0, T1, T2, T3, T4, T5>
                extends ReturnClassifier<
                Executables.Command6<T0, T1, T2, T3, T4, T5>,
                FutureTransformer.LockedFutureTransformer6<T0, T1, T2, T3, T4, T5>
                > {

            private ReturnClassifier6(RedFuture[] prerequisites) {
                super(prerequisites);
            }

            @Override
            protected void call(Executables.Command6<T0, T1, T2, T3, T4, T5> c, PendingMarker pendingMarker)
                    throws Throwable {
                c.call(pendingMarker, result(0), result(1), result(2), result(3), result(4), result(5));
            }

            @Override
            protected FutureTransformer.LockedFutureTransformer6<T0, T1, T2, T3, T4, T5>
            transformer(RedFuture[] oldPrerequisites, Marker... markers) {
                return new FutureTransformer.LockedFutureTransformer6<>(oldPrerequisites, markers);
            }

            public <R> Runner.Runner6<R, R, T0, T1, T2, T3, T4, T5>
            produce(Class<R> tClass) {
                return new Runner.Runner6<>(prerequisites(), valueConverter());
            }

            public <R> Runner.Runner6<Future<R>, R, T0, T1, T2, T3, T4, T5>
            produceFutureOf(Class<R> tClass) {
                return new Runner.Runner6<>(prerequisites(), futureConverter());
            }

            public <R> Runner.Runner6<ListenableFuture<R>, R, T0, T1, T2, T3, T4, T5>
            produceListenableFutureOf(Class<R> tClass) {
                return new Runner.Runner6<>(prerequisites(), listenableFutureConverter());
            }

            public <R> Runner.Runner6<RedFutureOf<R>, R, T0, T1, T2, T3, T4, T5>
            produceRedFutureOf(Class<R> tClass) {
                return new Runner.Runner6<>(prerequisites(), redFutureConverter());
            }

        }

        @SuppressWarnings("unused")
        public static class ReturnClassifier7<T0, T1, T2, T3, T4, T5, T6>
                extends ReturnClassifier<
                Executables.Command7<T0, T1, T2, T3, T4, T5, T6>,
                FutureTransformer.LockedFutureTransformer7<T0, T1, T2, T3, T4, T5, T6>
                > {

            private ReturnClassifier7(RedFuture[] prerequisites) {
                super(prerequisites);
            }

            @Override
            protected void call(Executables.Command7<T0, T1, T2, T3, T4, T5, T6> c, PendingMarker pendingMarker)
                    throws Throwable {
                c.call(pendingMarker, result(0), result(1), result(2), result(3), result(4), result(5), result(6));
            }

            @Override
            protected FutureTransformer.LockedFutureTransformer7<T0, T1, T2, T3, T4, T5, T6>
            transformer(RedFuture[] oldPrerequisites, Marker... markers) {
                return new FutureTransformer.LockedFutureTransformer7<>(oldPrerequisites, markers);
            }

            public <R> Runner.Runner7<R, R, T0, T1, T2, T3, T4, T5, T6>
            produce(Class<R> tClass) {
                return new Runner.Runner7<>(prerequisites(), valueConverter());
            }

            public <R> Runner.Runner7<Future<R>, R, T0, T1, T2, T3, T4, T5, T6>
            produceFutureOf(Class<R> tClass) {
                return new Runner.Runner7<>(prerequisites(), futureConverter());
            }

            public <R> Runner.Runner7<ListenableFuture<R>, R, T0, T1, T2, T3, T4, T5, T6>
            produceListenableFutureOf(Class<R> tClass) {
                return new Runner.Runner7<>(prerequisites(), listenableFutureConverter());
            }

            public <R> Runner.Runner7<RedFutureOf<R>, R, T0, T1, T2, T3, T4, T5, T6>
            produceRedFutureOf(Class<R> tClass) {
                return new Runner.Runner7<>(prerequisites(), redFutureConverter());
            }

        }

        @SuppressWarnings("unused")
        public static class ReturnClassifier8<T0, T1, T2, T3, T4, T5, T6, T7>
                extends ReturnClassifier<
                Executables.Command8<T0, T1, T2, T3, T4, T5, T6, T7>,
                FutureTransformer.LockedFutureTransformer8<T0, T1, T2, T3, T4, T5, T6, T7>
                > {

            private ReturnClassifier8(RedFuture[] prerequisites) {
                super(prerequisites);
            }

            @Override
            protected void call(Executables.Command8<T0, T1, T2, T3, T4, T5, T6, T7> c, PendingMarker pendingMarker)
                    throws Throwable {
                c.call(pendingMarker, result(0), result(1), result(2), result(3), result(4), result(5),
                        result(6), result(7));
            }

            @Override
            protected FutureTransformer.LockedFutureTransformer8<T0, T1, T2, T3, T4, T5, T6, T7>
            transformer(RedFuture[] oldPrerequisites, Marker... markers) {
                return new FutureTransformer.LockedFutureTransformer8<>(oldPrerequisites, markers);
            }

            public <R> Runner.Runner8<R, R, T0, T1, T2, T3, T4, T5, T6, T7>
            produce(Class<R> tClass) {
                return new Runner.Runner8<>(prerequisites(), valueConverter());
            }

            public <R> Runner.Runner8<Future<R>, R, T0, T1, T2, T3, T4, T5, T6, T7>
            produceFutureOf(Class<R> tClass) {
                return new Runner.Runner8<>(prerequisites(), futureConverter());
            }

            public <R> Runner.Runner8<ListenableFuture<R>, R, T0, T1, T2, T3, T4, T5, T6, T7>
            produceListenableFutureOf(Class<R> tClass) {
                return new Runner.Runner8<>(prerequisites(), listenableFutureConverter());
            }

            public <R> Runner.Runner8<RedFutureOf<R>, R, T0, T1, T2, T3, T4, T5, T6, T7>
            produceRedFutureOf(Class<R> tClass) {
                return new Runner.Runner8<>(prerequisites(), redFutureConverter());
            }

        }

        @SuppressWarnings("unused")
        public static class ReturnClassifier9<T0, T1, T2, T3, T4, T5, T6, T7, T8>
                extends ReturnClassifier<
                Executables.Command9<T0, T1, T2, T3, T4, T5, T6, T7, T8>,
                FutureTransformer.LockedFutureTransformer9<T0, T1, T2, T3, T4, T5, T6, T7, T8>
                > {

            private ReturnClassifier9(RedFuture[] prerequisites) {
                super(prerequisites);
            }

            @Override
            protected void call(Executables.Command9<T0, T1, T2, T3, T4, T5, T6, T7, T8> c,
                                PendingMarker pendingMarker) throws Throwable {
                c.call(pendingMarker, result(0), result(1), result(2), result(3), result(4),
                        result(5), result(6), result(7), result(8));
            }

            @Override
            protected FutureTransformer.LockedFutureTransformer9<T0, T1, T2, T3, T4, T5, T6, T7, T8>
            transformer(RedFuture[] oldPrerequisites, Marker... markers) {
                return new FutureTransformer.LockedFutureTransformer9<>(oldPrerequisites, markers);
            }

            public <R> Runner.Runner9<R, R, T0, T1, T2, T3, T4, T5, T6, T7, T8>
            produce(Class<R> tClass) {
                return new Runner.Runner9<>(prerequisites(), valueConverter());
            }

            public <R> Runner.Runner9<Future<R>, R, T0, T1, T2, T3, T4, T5, T6, T7, T8>
            produceFutureOf(Class<R> tClass) {
                return new Runner.Runner9<>(prerequisites(), futureConverter());
            }

            public <R> Runner.Runner9<ListenableFuture<R>, R, T0, T1, T2, T3, T4, T5, T6, T7, T8>
            produceListenableFutureOf(Class<R> tClass) {
                return new Runner.Runner9<>(prerequisites(), listenableFutureConverter());
            }

            public <R> Runner.Runner9<RedFutureOf<R>, R, T0, T1, T2, T3, T4, T5, T6, T7, T8>
            produceRedFutureOf(Class<R> tClass) {
                return new Runner.Runner9<>(prerequisites(), redFutureConverter());
            }

        }

        @SuppressWarnings("unused")
        public static class ReturnClassifier10<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9>
                extends ReturnClassifier<
                Executables.Command10<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9>,
                FutureTransformer.LockedFutureTransformer10<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9>
                > {

            private ReturnClassifier10(RedFuture[] prerequisites) {
                super(prerequisites);
            }

            @Override
            protected void call(Executables.Command10<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9> c,
                                PendingMarker pendingMarker) throws Throwable {
                c.call(pendingMarker, result(0), result(1), result(2), result(3), result(4),
                        result(5), result(6), result(7), result(8), result(9));
            }

            @Override
            protected FutureTransformer.LockedFutureTransformer10<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9>
            transformer(RedFuture[] oldPrerequisites, Marker... markers) {
                return new FutureTransformer.LockedFutureTransformer10<>(oldPrerequisites, markers);
            }

            public <R> Runner.Runner10<R, R, T0, T1, T2, T3, T4, T5, T6, T7, T8, T9>
            produce(Class<R> tClass) {
                return new Runner.Runner10<>(prerequisites(), valueConverter());
            }

            public <R> Runner.Runner10<Future<R>, R, T0, T1, T2, T3, T4, T5, T6, T7, T8, T9>
            produceFutureOf(Class<R> tClass) {
                return new Runner.Runner10<>(prerequisites(), futureConverter());
            }

            public <R> Runner.Runner10<ListenableFuture<R>, R, T0, T1, T2, T3, T4, T5, T6, T7, T8, T9>
            produceListenableFutureOf(Class<R> tClass) {
                return new Runner.Runner10<>(prerequisites(), listenableFutureConverter());
            }

            public <R> Runner.Runner10<RedFutureOf<R>, R, T0, T1, T2, T3, T4, T5, T6, T7, T8, T9>
            produceRedFutureOf(Class<R> tClass) {
                return new Runner.Runner10<>(prerequisites(), redFutureConverter());
            }

        }

        @SuppressWarnings("unused")
        public static class ReturnClassifierN extends
                ReturnClassifier<Executables.CommandN, FutureTransformer.LockedFutureTransformerN> {

            private ReturnClassifierN(RedFuture[] prerequisites) {
                super(prerequisites);
            }

            @Override
            protected void call(Executables.CommandN c, PendingMarker pendingMarker) throws Throwable {
                c.call(pendingMarker, new Results(prerequisites()));
            }

            @Override
            protected FutureTransformer.LockedFutureTransformerN
            transformer(RedFuture[] oldPrerequisites, Marker... markers) {
                return new FutureTransformer.LockedFutureTransformerN(oldPrerequisites, markers);
            }

            public <R> Runner.RunnerN<R, R>
            produce(Class<R> tClass) {
                return new Runner.RunnerN<>(prerequisites(), valueConverter());
            }

            public <R> Runner.RunnerN<Future<R>, R>
            produceFutureOf(Class<R> tClass) {
                return new Runner.RunnerN<>(prerequisites(), futureConverter());
            }

            public <R> Runner.RunnerN<ListenableFuture<R>, R>
            produceListenableFutureOf(Class<R> tClass) {
                return new Runner.RunnerN<>(prerequisites(), listenableFutureConverter());
            }

            public <R> Runner.RunnerN<RedFutureOf<R>, R>
            produceRedFutureOf(Class<R> tClass) {
                return new Runner.RunnerN<>(prerequisites(), redFutureConverter());
            }

        }

    }

    abstract protected static class Runner<FUNCTION, WRAPPER, R> extends PrerequisitesHolder {

        private final Converter<WRAPPER, R> _converter;

        private Runner(RedFuture[] prerequisites, Converter<WRAPPER, R> converter) {
            super(prerequisites);
            _converter = converter;
        }

        public Result<R> byExecuting(FUNCTION f) {
            Result<R> result = new Result<>();
            RedFuture
                    .hub()
                    .adoptFutures(prerequisites())
                    .uniteOptimistically()
                    .addFailureCallback(result._future::fail)
                    .addSuccessCallback(() -> {
                        try {
                            WRAPPER wrapper = call(f);
                            RedFutureOf<R> toFollow = _converter.convert(wrapper);
                            result._future.follow(toFollow);
                        } catch (Throwable t) {
                            result._future.fail(t);
                        }
                    });
            return result;
        }

        abstract protected WRAPPER call(FUNCTION f) throws Throwable;

        public static class Runner0<WRAPPER, R>
                extends Runner<Executables.Function0<WRAPPER>, WRAPPER, R> {

            private Runner0(RedFuture[] prerequisites, Converter<WRAPPER, R> converter) {
                super(prerequisites, converter);
            }

            @Override
            protected WRAPPER call(Executables.Function0<WRAPPER> f) throws Throwable {
                return f.call();
            }

        }

        public static class Runner1<WRAPPER, R, T0>
                extends Runner<Executables.Function1<WRAPPER, T0>, WRAPPER, R> {

            private Runner1(RedFuture[] prerequisites, Converter<WRAPPER, R> converter) {
                super(prerequisites, converter);
            }

            @Override
            protected WRAPPER call(Executables.Function1<WRAPPER, T0> f) throws Throwable {
                return f.call(result(0));
            }

        }

        public static class Runner2<WRAPPER, R, T0, T1>
                extends Runner<Executables.Function2<WRAPPER, T0, T1>, WRAPPER, R> {

            private Runner2(RedFuture[] prerequisites, Converter<WRAPPER, R> converter) {
                super(prerequisites, converter);
            }

            @Override
            protected WRAPPER call(Executables.Function2<WRAPPER, T0, T1> f) throws Throwable {
                return f.call(result(0), result(1));
            }

        }

        public static class Runner3<WRAPPER, R, T0, T1, T2>
                extends Runner<Executables.Function3<WRAPPER, T0, T1, T2>, WRAPPER, R> {

            private Runner3(RedFuture[] prerequisites, Converter<WRAPPER, R> converter) {
                super(prerequisites, converter);
            }

            @Override
            protected WRAPPER call(Executables.Function3<WRAPPER, T0, T1, T2> f) throws Throwable {
                return f.call(result(0), result(1), result(2));
            }

        }

        public static class Runner4<WRAPPER, R, T0, T1, T2, T3>
                extends Runner<Executables.Function4<WRAPPER, T0, T1, T2, T3>, WRAPPER, R> {

            private Runner4(RedFuture[] prerequisites, Converter<WRAPPER, R> converter) {
                super(prerequisites, converter);
            }

            @Override
            protected WRAPPER call(Executables.Function4<WRAPPER, T0, T1, T2, T3> f) throws Throwable {
                return f.call(result(0), result(1), result(2), result(3));
            }

        }

        public static class Runner5<WRAPPER, R, T0, T1, T2, T3, T4>
                extends Runner<Executables.Function5<WRAPPER, T0, T1, T2, T3, T4>, WRAPPER, R> {

            private Runner5(RedFuture[] prerequisites, Converter<WRAPPER, R> converter) {
                super(prerequisites, converter);
            }

            @Override
            protected WRAPPER call(Executables.Function5<WRAPPER, T0, T1, T2, T3, T4> f) throws Throwable {
                return f.call(result(0), result(1), result(2), result(3), result(4));
            }

        }

        public static class Runner6<WRAPPER, R, T0, T1, T2, T3, T4, T5>
                extends Runner<Executables.Function6<WRAPPER, T0, T1, T2, T3, T4, T5>, WRAPPER, R> {

            private Runner6(RedFuture[] prerequisites, Converter<WRAPPER, R> converter) {
                super(prerequisites, converter);
            }

            @Override
            protected WRAPPER call(Executables.Function6<WRAPPER, T0, T1, T2, T3, T4, T5> f) throws Throwable {
                return f.call(result(0), result(1), result(2), result(3), result(4), result(5));
            }

        }

        public static class Runner7<WRAPPER, R, T0, T1, T2, T3, T4, T5, T6>
                extends Runner<Executables.Function7<WRAPPER, T0, T1, T2, T3, T4, T5, T6>, WRAPPER, R> {

            private Runner7(RedFuture[] prerequisites, Converter<WRAPPER, R> converter) {
                super(prerequisites, converter);
            }

            @Override
            protected WRAPPER call(Executables.Function7<WRAPPER, T0, T1, T2, T3, T4, T5, T6> f) throws Throwable {
                return f.call(result(0), result(1), result(2), result(3), result(4), result(5), result(6));
            }

        }

        public static class Runner8<WRAPPER, R, T0, T1, T2, T3, T4, T5, T6, T7>
                extends Runner<Executables.Function8<WRAPPER, T0, T1, T2, T3, T4, T5, T6, T7>, WRAPPER, R> {

            private Runner8(RedFuture[] prerequisites, Converter<WRAPPER, R> converter) {
                super(prerequisites, converter);
            }

            @Override
            protected WRAPPER call(Executables.Function8<WRAPPER, T0, T1, T2, T3, T4, T5, T6, T7> f) throws Throwable {
                return f.call(result(0), result(1), result(2), result(3), result(4), result(5), result(6), result(7));
            }

        }

        public static class Runner9<WRAPPER, R, T0, T1, T2, T3, T4, T5, T6, T7, T8>
                extends Runner<Executables.Function9<WRAPPER, T0, T1, T2, T3, T4, T5, T6, T7, T8>, WRAPPER, R> {

            private Runner9(RedFuture[] prerequisites, Converter<WRAPPER, R> converter) {
                super(prerequisites, converter);
            }

            @Override
            protected WRAPPER call(Executables.Function9<WRAPPER, T0, T1, T2, T3, T4, T5, T6, T7, T8> f)
                    throws Throwable {
                return f.call(result(0), result(1), result(2), result(3), result(4), result(5), result(6), result(7),
                        result(8));
            }

        }

        public static class Runner10<WRAPPER, R, T0, T1, T2, T3, T4, T5, T6, T7, T8, T9>
                extends Runner<Executables.Function10<WRAPPER, T0, T1, T2, T3, T4, T5, T6, T7, T8, T9>, WRAPPER, R> {

            private Runner10(RedFuture[] prerequisites, Converter<WRAPPER, R> converter) {
                super(prerequisites, converter);
            }

            @Override
            protected WRAPPER call(Executables.Function10<WRAPPER, T0, T1, T2, T3, T4, T5, T6, T7, T8, T9> f)
                    throws Throwable {
                return f.call(result(0), result(1), result(2), result(3), result(4), result(5), result(6), result(7),
                        result(8), result(9));
            }

        }

        public static class RunnerN<WRAPPER, R> extends Runner<Executables.FunctionN<WRAPPER>, WRAPPER, R> {

            private RunnerN(RedFuture[] prerequisites, Converter<WRAPPER, R> converter) {
                super(prerequisites, converter);
            }

            @Override
            protected WRAPPER call(Executables.FunctionN<WRAPPER> f) throws Throwable {
                return f.call(new Results(prerequisites()));
            }
        }

    }

    protected static class Executables {

        public interface Function0<WRAPPER> {

            WRAPPER call() throws Throwable;

        }

        public interface Function1<WRAPPER, T0> {

            WRAPPER call(T0 f0) throws Throwable;

        }

        public interface Function2<WRAPPER, T0, T1> {

            WRAPPER call(T0 f0, T1 f1) throws Throwable;

        }

        public interface Function3<WRAPPER, T0, T1, T2> {

            WRAPPER call(T0 f0, T1 f1, T2 f2) throws Throwable;

        }

        public interface Function4<WRAPPER, T0, T1, T2, T3> {

            WRAPPER call(T0 f0, T1 f1, T2 f2, T3 f3) throws Throwable;

        }

        public interface Function5<WRAPPER, T0, T1, T2, T3, T4> {

            WRAPPER call(T0 f0, T1 f1, T2 f2, T3 f3, T4 f4) throws Throwable;

        }

        public interface Function6<WRAPPER, T0, T1, T2, T3, T4, T5> {

            WRAPPER call(T0 f0, T1 f1, T2 f2, T3 f3, T4 f4, T5 f5) throws Throwable;

        }

        public interface Function7<WRAPPER, T0, T1, T2, T3, T4, T5, T6> {

            WRAPPER call(T0 f0, T1 f1, T2 f2, T3 f3, T4 f4, T5 f5, T6 f6) throws Throwable;

        }

        public interface Function8<WRAPPER, T0, T1, T2, T3, T4, T5, T6, T7> {

            WRAPPER call(T0 f0, T1 f1, T2 f2, T3 f3, T4 f4, T5 f5, T6 f6, T7 f7) throws Throwable;

        }

        public interface Function9<WRAPPER, T0, T1, T2, T3, T4, T5, T6, T7, T8> {

            WRAPPER call(T0 f0, T1 f1, T2 f2, T3 f3, T4 f4, T5 f5, T6 f6, T7 f7, T8 f8) throws Throwable;

        }

        public interface Function10<WRAPPER, T0, T1, T2, T3, T4, T5, T6, T7, T8, T9> {

            WRAPPER call(T0 f0, T1 f1, T2 f2, T3 f3, T4 f4, T5 f5, T6 f6, T7 f7, T8 f8, T9 f9) throws Throwable;

        }

        public interface FunctionN<WRAPPER> {

            WRAPPER call(Results results) throws Throwable;

        }

        public interface Command0 {

            void call(PendingMarker pendingMarker) throws Throwable;

        }

        public interface Command1<T0> {

            void call(PendingMarker pendingMarker, T0 f0) throws Throwable;

        }

        public interface Command2<T0, T1> {

            void call(PendingMarker pendingMarker, T0 f0, T1 f1) throws Throwable;

        }

        public interface Command3<T0, T1, T2> {

            void call(PendingMarker pendingMarker, T0 f0, T1 f1, T2 f2) throws Throwable;

        }

        public interface Command4<T0, T1, T2, T3> {

            void call(PendingMarker pendingMarker, T0 f0, T1 f1, T2 f2, T3 f3) throws Throwable;

        }

        public interface Command5<T0, T1, T2, T3, T4> {

            void call(PendingMarker pendingMarker, T0 f0, T1 f1, T2 f2, T3 f3, T4 f4) throws Throwable;

        }

        public interface Command6<T0, T1, T2, T3, T4, T5> {

            void call(PendingMarker pendingMarker, T0 f0, T1 f1, T2 f2, T3 f3, T4 f4, T5 f5) throws Throwable;

        }

        public interface Command7<T0, T1, T2, T3, T4, T5, T6> {

            void call(PendingMarker pendingMarker, T0 f0, T1 f1, T2 f2, T3 f3, T4 f4, T5 f5, T6 f6) throws Throwable;

        }

        public interface Command8<T0, T1, T2, T3, T4, T5, T6, T7> {

            void call(PendingMarker pendingMarker, T0 f0, T1 f1, T2 f2, T3 f3, T4 f4, T5 f5, T6 f6, T7 f7)
                    throws Throwable;

        }

        public interface Command9<T0, T1, T2, T3, T4, T5, T6, T7, T8> {

            void call(PendingMarker pendingMarker, T0 f0, T1 f1, T2 f2, T3 f3, T4 f4, T5 f5, T6 f6, T7 f7, T8 f8)
                    throws Throwable;

        }

        public interface Command10<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9> {

            void call(PendingMarker pendingMarker, T0 f0, T1 f1, T2 f2, T3 f3, T4 f4, T5 f5, T6 f6, T7 f7, T8 f8, T9 f9)
                    throws Throwable;

        }

        public interface CommandN {

            void call(PendingMarker pendingMarker, Results results) throws Throwable;

        }

    }

    private static <R> Converter<R, R> valueConverter() {
        return RedFuture::resolvedOf;
    }

    private static <R> Converter<Future<R>, R> futureConverter() {
        return RedFuture::convert;
    }

    private static <R> Converter<ListenableFuture<R>, R> listenableFutureConverter() {
        return RedFuture::convert;
    }

    private static <R> Converter<RedFutureOf<R>, R> redFutureConverter() {
        return BaseRedSynchronizer::id;
    }

    private static <T> T id(T t) {
        return t;
    }

    private interface Converter<WRAPPER, OF> {

        RedFutureOf<OF> convert(WRAPPER midType);

    }

    abstract private static class PrerequisitesHolder {

        private final RedFuture[] _prerequisites;

        private PrerequisitesHolder(RedFuture[] prerequisites) {
            _prerequisites = prerequisites;
        }

        RedFuture[] prerequisites() {
            return _prerequisites;
        }

        <T> T result(int index) {
            RedFuture future = _prerequisites[index];
            //noinspection unchecked
            return ((RedFutureOf<T>) future).tryGet();
        }

    }
    
}
