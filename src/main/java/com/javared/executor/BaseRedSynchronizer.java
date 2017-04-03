package com.javared.executor;

import com.google.common.util.concurrent.ListenableFuture;
import com.javared.future.OpenRedFuture;
import com.javared.future.OpenRedFutureOf;
import com.javared.future.RedFuture;
import com.javared.future.RedFutureOf;

import java.util.Arrays;
import java.util.concurrent.Future;

/**
 * An abstract class to implement common functionality of both
 * {@link RedSynchronizer} and {@link RedVoidSynchronizer}.
 *
 * The Synchronizers instances use a Synchronizer Syntax to define the different
 * aspects of the execution.
 * Synchronizer Syntax is separated into statements. Each statement constructs a single
 * execution which can either be resulted in a value, or void.
 * The phase of declaring an execution is called The Construction Chain.
 * The different stages of the Construction Chain are called middleware.
 * Each middleware is responsible for a different aspect of the resulted execution.
 * The different middleware are:
 * <ul>
 *     <li>{@link FutureTransformer} which receives and forwards the number of preconditions
 *     of the execution, and responsible for manipulating the expectation from those preconditions.</li>
 *     <li>{@link ReturnClassifier} which receives and forwards the number of preconditions and
 *     the expectation from them, and responsible to set the method of returning values of the execution.</li>
 *     <li>{@link Runner} which receives and forwards the number of preconditions, the
 *     expectation from them and the method of returning values of the execution, and responsible
 *     for invoking the {@link Function} or {@link Command} given to it.</li>
 * </ul>
 * Once a {@link Function} or a {@link Command} is invoked, the Construction Chain is completed,
 * and an tracking object is returned.
 * In case the execution returns a value, the tracking object will be a {@link Result},
 * in case the execution returns void, the tracking object will be a {@link Marker}.
 * Both {@link Result} and {@link Marker} can be passed back as precondition to
 * another Construction Chain in any following statement of the Synchronizer Syntax.
 */
abstract public class BaseRedSynchronizer {

    // Constants

    /**
     * An instance of {@link ReturnClassifier} with 0 parameters to be used for direct
     * produce and execute calls (i.e. {@link #execute(Command.Command0)}, {@link #produce(Class)}, etc...)
     */
    private static final ReturnClassifier.Classifier0 RETURN_CLASSIFIER_0 =
            new ReturnClassifier.Classifier0();

    // Private

    /**
     * Execute the given command directly, without waiting for any result or markers,
     * and return a marker of the execution.
     *
     * This call skips the entire Construction Chain, executes the given command returns a marker.
     *
     * @param command command to execute
     * @return marker of the execution
     */
    protected Marker execute(Command.Command0 command) {
        return RETURN_CLASSIFIER_0.execute(command);
    }

    /**
     * Produce value of the given class directly, without waiting for any result or markers,
     * and return a result of the execution
     *
     * Since the are no preconditions and the return method is already defined at this point,
     * this call skips some middleware of the Construction Chain and goes directly to the
     * runner phase.
     *
     * @param tClass class of the result to produce
     * @param <R>    type of the result to produce
     * @return a runner to execute upon
     */
    protected <R> Runner.Runner0<R, R> produce(Class<R> tClass) {
        return RETURN_CLASSIFIER_0.produce(tClass);
    }

    /**
     * Produce {@link Future} of the given class directly, without waiting for any result or markers,
     * and return a result of the execution
     *
     * Since the are no preconditions and the return method is already defined at this point,
     * this call skips some middleware of the Construction Chain and goes directly to the
     * runner phase.
     *
     * @param tClass class of the result to produce
     * @param <R>    type of the result to produce
     * @return a runner to execute upon
     */
    protected <R> Runner.Runner0<Future<R>, R> produceFutureOf(Class<R> tClass) {
        return RETURN_CLASSIFIER_0.produceFutureOf(tClass);
    }

    /**
     * Produce {@link ListenableFuture} of the given class directly, without waiting for any result or markers,
     * and return a result of the execution
     *
     * Since the are no preconditions and the return method is already defined at this point,
     * this call skips some middleware of the Construction Chain and goes directly to the
     * runner phase.
     *
     * @param tClass class of the result to produce
     * @param <R>    type of the result to produce
     * @return a runner to execute upon
     */
    protected <R> Runner.Runner0<ListenableFuture<R>, R> produceListenableFutureOf(Class<R> tClass) {
        return RETURN_CLASSIFIER_0.produceListenableFutureOf(tClass);
    }

    /**
     * Produce {@link RedFutureOf} of the given class directly, without waiting for any result or markers,
     * and return a result of the execution
     *
     * Since the are no preconditions and the return method is already defined at this point,
     * this call skips some middleware of the Construction Chain and goes directly to the
     * runner phase.
     *
     * @param tClass class of the result to produce
     * @param <R>    type of the result to produce
     * @return a runner to execute upon
     */
    protected <R> Runner.Runner0<RedFutureOf<R>, R> produceRedFutureOf(Class<R> tClass) {
        return RETURN_CLASSIFIER_0.produceRedFutureOf(tClass);
    }

    /**
     * Receive markers of various executions, returns a {@link FutureTransformer} to choose which
     * kind of results to expect, and then run a certain function if condition is met.
     *
     * Initiates the Construction Chain with 0 parameters.
     *
     * @param markers markers to be used as preconditions
     * @return {@link FutureTransformer} to choose which kind of results to expect
     */
    protected FutureTransformer.Unlocked.Unlocked0
    ifMarkers(Marker... markers) {
        return new FutureTransformer.Unlocked.Unlocked0(markers);
    }

    /**
     * Receive a result of an execution, returns a {@link FutureTransformer} to choose which
     * kind of completion to expect for given result, and then run a certain function
     * if condition is met.
     *
     * Initiates the Construction Chain with 1 parameter.
     *
     * @param f0   result to be used as precondition
     * @param <T0> type of result0
     * @return {@link FutureTransformer} to choose which kind of completion to expect for given result
     */
    protected <T0>
    FutureTransformer.Unlocked.Unlocked1<T0>
    ifResult(Result<T0> f0) {
        return new FutureTransformer.Unlocked.Unlocked1<>(f0);
    }

    /**
     * Receive a results of various executions, returns a {@link FutureTransformer} to choose which
     * kind of completion to expect for given results, and then run a certain function
     * if condition is met.
     *
     * Initiates the Construction Chain with 2 parameters.
     *
     * @param f0   result to be used as precondition
     * @param f1   result to be used as precondition
     * @param <T0> type of result0
     * @param <T1> type of result1
     * @return {@link FutureTransformer} to choose which kind of completion to expect for given results
     */
    protected <T0, T1>
    FutureTransformer.Unlocked.Unlocked2<T0, T1>
    ifResults(Result<T0> f0, Result<T1> f1) {
        return new FutureTransformer.Unlocked.Unlocked2<>(f0, f1);
    }

    /**
     * Receive a results of various executions, returns a {@link FutureTransformer} to choose which
     * kind of completion to expect for given results, and then run a certain function
     * if condition is met.
     *
     * Initiates the Construction Chain with 3 parameters.
     *
     * @param f0   result to be used as precondition
     * @param f1   result to be used as precondition
     * @param f2   result to be used as precondition
     * @param <T0> type of result0
     * @param <T1> type of result1
     * @param <T2> type of result2
     * @return {@link FutureTransformer} to choose which kind of completion to expect for given results
     */
    protected <T0, T1, T2>
    FutureTransformer.Unlocked.Unlocked3<T0, T1, T2>
    ifResults(Result<T0> f0, Result<T1> f1, Result<T2> f2) {
        return new FutureTransformer.Unlocked.Unlocked3<>(f0, f1, f2);
    }

    /**
     * Receive a results of various executions, returns a {@link FutureTransformer} to choose which
     * kind of completion to expect for given results, and then run a certain function
     * if condition is met.
     *
     * Initiates the Construction Chain with 4 parameters.
     *
     * @param f0   result to be used as precondition
     * @param f1   result to be used as precondition
     * @param f2   result to be used as precondition
     * @param f3   result to be used as precondition
     * @param <T0> type of result0
     * @param <T1> type of result1
     * @param <T2> type of result2
     * @param <T3> type of result3
     * @return {@link FutureTransformer} to choose which kind of completion to expect for given results
     */
    protected <T0, T1, T2, T3>
    FutureTransformer.Unlocked.Unlocked4<T0, T1, T2, T3>
    ifResults(Result<T0> f0, Result<T1> f1, Result<T2> f2, Result<T3> f3) {
        return new FutureTransformer.Unlocked.Unlocked4<>(f0, f1, f2, f3);
    }

    /**
     * Receive a results of various executions, returns a {@link FutureTransformer} to choose which
     * kind of completion to expect for given results, and then run a certain function
     * if condition is met.
     *
     * Initiates the Construction Chain with 5 parameters.
     *
     * @param f0   result to be used as precondition
     * @param f1   result to be used as precondition
     * @param f2   result to be used as precondition
     * @param f3   result to be used as precondition
     * @param f4   result to be used as precondition
     * @param <T0> type of result0
     * @param <T1> type of result1
     * @param <T2> type of result2
     * @param <T3> type of result3
     * @param <T4> type of result4
     * @return {@link FutureTransformer} to choose which kind of completion to expect for given results
     */
    protected <T0, T1, T2, T3, T4>
    FutureTransformer.Unlocked.Unlocked5<T0, T1, T2, T3, T4>
    ifResults(Result<T0> f0, Result<T1> f1, Result<T2> f2, Result<T3> f3, Result<T4> f4) {
        return new FutureTransformer.Unlocked.Unlocked5<>(f0, f1, f2, f3, f4);
    }

    /**
     * Receive a results of various executions, returns a {@link FutureTransformer} to choose which
     * kind of completion to expect for given results, and then run a certain function
     * if condition is met.
     *
     * Initiates the Construction Chain with 6 parameters.
     *
     * @param f0   result to be used as precondition
     * @param f1   result to be used as precondition
     * @param f2   result to be used as precondition
     * @param f3   result to be used as precondition
     * @param f4   result to be used as precondition
     * @param f5   result to be used as precondition
     * @param <T0> type of result0
     * @param <T1> type of result1
     * @param <T2> type of result2
     * @param <T3> type of result3
     * @param <T4> type of result4
     * @param <T5> type of result5
     * @return {@link FutureTransformer} to choose which kind of completion to expect for given results
     */
    protected <T0, T1, T2, T3, T4, T5>
    FutureTransformer.Unlocked.Unlocked6<T0, T1, T2, T3, T4, T5>
    ifResults(Result<T0> f0, Result<T1> f1, Result<T2> f2, Result<T3> f3, Result<T4> f4,
              Result<T5> f5) {
        return new FutureTransformer.Unlocked.Unlocked6<>(f0, f1, f2, f3, f4, f5);
    }

    /**
     * Receive a results of various executions, returns a {@link FutureTransformer} to choose which
     * kind of completion to expect for given results, and then run a certain function
     * if condition is met.
     *
     * Initiates the Construction Chain with 7 parameters.
     *
     * @param f0   result to be used as precondition
     * @param f1   result to be used as precondition
     * @param f2   result to be used as precondition
     * @param f3   result to be used as precondition
     * @param f4   result to be used as precondition
     * @param f5   result to be used as precondition
     * @param f6   result to be used as precondition
     * @param <T0> type of result0
     * @param <T1> type of result1
     * @param <T2> type of result2
     * @param <T3> type of result3
     * @param <T4> type of result4
     * @param <T5> type of result5
     * @param <T6> type of result6
     * @return {@link FutureTransformer} to choose which kind of completion to expect for given results
     */
    protected <T0, T1, T2, T3, T4, T5, T6>
    FutureTransformer.Unlocked.Unlocked7<T0, T1, T2, T3, T4, T5, T6>
    ifResults(Result<T0> f0, Result<T1> f1, Result<T2> f2, Result<T3> f3, Result<T4> f4,
              Result<T5> f5, Result<T6> f6) {
        return new FutureTransformer.Unlocked.Unlocked7<>(f0, f1, f2, f3, f4, f5, f6);
    }

    /**
     * Receive a results of various executions, returns a {@link FutureTransformer} to choose which
     * kind of completion to expect for given results, and then run a certain function
     * if condition is met.
     *
     * Initiates the Construction Chain with 8 parameters.
     *
     * @param f0   result to be used as precondition
     * @param f1   result to be used as precondition
     * @param f2   result to be used as precondition
     * @param f3   result to be used as precondition
     * @param f4   result to be used as precondition
     * @param f5   result to be used as precondition
     * @param f6   result to be used as precondition
     * @param f7   result to be used as precondition
     * @param <T0> type of result0
     * @param <T1> type of result1
     * @param <T2> type of result2
     * @param <T3> type of result3
     * @param <T4> type of result4
     * @param <T5> type of result5
     * @param <T6> type of result6
     * @param <T7> type of result7
     * @return {@link FutureTransformer} to choose which kind of completion to expect for given results
     */
    protected <T0, T1, T2, T3, T4, T5, T6, T7>
    FutureTransformer.Unlocked.Unlocked8<T0, T1, T2, T3, T4, T5, T6, T7>
    ifResults(Result<T0> f0, Result<T1> f1, Result<T2> f2, Result<T3> f3, Result<T4> f4,
              Result<T5> f5, Result<T6> f6, Result<T7> f7) {
        return new FutureTransformer.Unlocked.Unlocked8<>(f0, f1, f2, f3, f4, f5,
                f6, f7);
    }

    /**
     * Receive a results of various executions, returns a {@link FutureTransformer} to choose which
     * kind of completion to expect for given results, and then run a certain function
     * if condition is met.
     *
     * Initiates the Construction Chain with 9 parameters.
     *
     * @param f0   result to be used as precondition
     * @param f1   result to be used as precondition
     * @param f2   result to be used as precondition
     * @param f3   result to be used as precondition
     * @param f4   result to be used as precondition
     * @param f5   result to be used as precondition
     * @param f6   result to be used as precondition
     * @param f7   result to be used as precondition
     * @param f8   result to be used as precondition
     * @param <T0> type of result0
     * @param <T1> type of result1
     * @param <T2> type of result2
     * @param <T3> type of result3
     * @param <T4> type of result4
     * @param <T5> type of result5
     * @param <T6> type of result6
     * @param <T7> type of result7
     * @param <T8> type of result8
     * @return {@link FutureTransformer} to choose which kind of completion to expect for given results
     */
    protected <T0, T1, T2, T3, T4, T5, T6, T7, T8>
    FutureTransformer.Unlocked.Unlocked9<T0, T1, T2, T3, T4, T5, T6, T7, T8>
    ifResults(Result<T0> f0, Result<T1> f1, Result<T2> f2, Result<T3> f3, Result<T4> f4,
              Result<T5> f5, Result<T6> f6, Result<T7> f7, Result<T8> f8) {
        return new FutureTransformer.Unlocked.Unlocked9<>(f0, f1, f2, f3, f4, f5,
                f6, f7, f8);
    }

    /**
     * Receive a results of various executions, returns a {@link FutureTransformer} to choose which
     * kind of completion to expect for given results, and then run a certain function
     * if condition is met.
     *
     * Initiates the Construction Chain with 10 parameters.
     *
     * @param f0   result to be used as precondition
     * @param f1   result to be used as precondition
     * @param f2   result to be used as precondition
     * @param f3   result to be used as precondition
     * @param f4   result to be used as precondition
     * @param f5   result to be used as precondition
     * @param f6   result to be used as precondition
     * @param f7   result to be used as precondition
     * @param f8   result to be used as precondition
     * @param f9   result to be used as precondition
     * @param <T0> type of result0
     * @param <T1> type of result1
     * @param <T2> type of result2
     * @param <T3> type of result3
     * @param <T4> type of result4
     * @param <T5> type of result5
     * @param <T6> type of result6
     * @param <T7> type of result7
     * @param <T8> type of result8
     * @param <T9> type of result9
     * @return {@link FutureTransformer} to choose which kind of completion to expect for given results
     */
    protected <T0, T1, T2, T3, T4, T5, T6, T7, T8, T9>
    FutureTransformer.Unlocked.Unlocked10<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9>
    ifResults(Result<T0> f0, Result<T1> f1, Result<T2> f2, Result<T3> f3, Result<T4> f4,
              Result<T5> f5, Result<T6> f6, Result<T7> f7, Result<T8> f8, Result<T9> f9) {
        return new FutureTransformer.Unlocked.Unlocked10<>(f0, f1, f2, f3, f4, f5,
                f6, f7, f8, f9);
    }

    /**
     * Receive a results of various executions, returns a {@link FutureTransformer} to choose which
     * kind of completion to expect for given results, and then run a certain function
     * if condition is met.
     *
     * Initiates the Construction Chain with N parameters.
     *
     * @param results results to be used as precondition
     * @return {@link FutureTransformer} to choose which kind of completion to expect for given results
     */
    protected FutureTransformer.Unlocked.UnlockedN ifResults(Result... results) {
        return new FutureTransformer.Unlocked.UnlockedN(results);
    }

    // Middleware classes

    /**
     * A middleware class to manipulate and transform future status.
     * This middleware will enable the caller to set the type of the expected result
     * of futures by calling either one of the transformation methods:
     * {@link #succeed()}, {@link #finish()} or fail, which expect all preconditions to
     * respectively succeed, finish either by success or failure, or to fail.
     * Preconditions are receive as either {@link Result} or {@link Marker}, and when and
     * transformation method is called, a {@link ReturnClassifier} middleware instance is returned.
     * @param <RETURN_CLASSIFIER> the matching {@link ReturnClassifier} middleware to be returned
     */
    abstract protected static class FutureTransformer<RETURN_CLASSIFIER extends ReturnClassifier> extends Middleware {
        
        // Fields

        /**
         * An array of red futures to from previous calls, these futures should not
         * be altered by the current {@link FutureTransformer}.
         * For example, if one performs:
         * <code>ifResult(someResult).succeed().andMarker(someMarker)</code>
         * then someResult would appear in the _previousLayer, and someMarker would
         * appear in the _currentLayer
         */
        private final RedFuture[] _currentLayer;
        
        // Constructors

        /**
         * @param previousLayer may be null
         * @param currentLayer may be null
         */
        private FutureTransformer(RedFuture[] previousLayer, RedFuture[] currentLayer) {
            super(previousLayer == null ? new RedFuture[0] : previousLayer);
            _currentLayer = currentLayer == null ? new RedFuture[0] : currentLayer;
        }
        
        // Public

        /**
         * Expect the later results or markers to succeed, and return a {@link ReturnClassifier}
         * to choose which kind of value wrapper to produce
         * @return a {@link ReturnClassifier} to choose which kind of value wrapper to produce
         */
        public RETURN_CLASSIFIER succeed() {
            return createClassifier(future -> {
                if (future instanceof RedFutureOf) {
                    RedFutureOf<?> futureOf = (RedFutureOf) future;
                    OpenRedFutureOf<Object> result = RedFuture.futureOf();
                    futureOf.addSuccessCallback(result::resolve);
                    futureOf.addFailureCallback(throwable ->
                            result.fail(new PreconditionFailedException.Success(throwable)));
                    return result;
                }
                OpenRedFuture result = RedFuture.future();
                future.addSuccessCallback(result::resolve);
                future.addFailureCallback(throwable -> result.fail(new PreconditionFailedException.Success(throwable)));
                return result;
            });
        }

        /**
         * Expect the later results or markers to finish, either by success or failure, 
         * and return a {@link ReturnClassifier} to choose which kind of value wrapper to produce
         * @return a {@link ReturnClassifier} to choose which kind of value wrapper to produce
         */
        public RETURN_CLASSIFIER finish() {
            return createClassifier(future -> {
                if (future instanceof RedFutureOf) {
                    RedFutureOf<?> futureOf = (RedFutureOf) future;
                    OpenRedFutureOf<Object> result = RedFuture.futureOf();
                    futureOf.addSuccessCallback(result::resolve);
                    futureOf.addFailureCallback(throwable -> result.resolve(null));
                    return result;
                }
                OpenRedFuture result = RedFuture.future();
                future.addFinallyCallback(result::resolve);
                return result;
            });
        }
        
        // Private

        /**
         * Receive an array of futures to be used as the current layer, and returns
         * the instance of {@link ReturnClassifier}
         * @param transformedPreconditions array of futures to be used as the current layer
         * @return a {@link ReturnClassifier} to choose which kind of value wrapper to produce
         */
        private RETURN_CLASSIFIER createClassifier(RedFuture[] transformedPreconditions) {
            RedFuture[] allPreconditions = new RedFuture[transformedPreconditions.length + preconditions().length];
            System.arraycopy(preconditions(), 0, allPreconditions, 0, preconditions().length);
            System.arraycopy(transformedPreconditions, 0, allPreconditions, preconditions().length,
                    transformedPreconditions.length);
            return classifier(allPreconditions);
        }

        /**
         * Receive a mapper from future to future, and returns an array of futures,
         * transformed from the _currentLayer
         * @param mapper to map the _currentLayer
         * @return an array of transformed futures
         */
        RedFuture[] mapCurrentLayer(java.util.function.Function<RedFuture, RedFuture> mapper) {
            return Arrays.stream(_currentLayer).map(mapper).toArray(RedFuture[]::new);
        }

        /**
         * Receive a mapper from future to future, and returns an array of futures,
         * transformed from the _currentLayer
         * @param mapper to map the _currentLayer
         * @return a {@link ReturnClassifier} to choose which kind of value wrapper to produce
         */
        RETURN_CLASSIFIER createClassifier(java.util.function.Function<RedFuture, RedFuture> mapper) {
            return createClassifier(mapCurrentLayer(mapper));
        }

        /**
         * @param preconditions all futures to be used - merged array of
         *                      both _previousLayer and transformed _currentLayer
         * @return a {@link ReturnClassifier} to choose which kind of value wrapper to produce
         */
        abstract protected RETURN_CLASSIFIER classifier(RedFuture[] preconditions);

        /**
         * A {@link FutureTransformer} which overrides the <RETURN_CLASSIFIER> parameter
         * to {@link ReturnClassifier.Classifier0} in case {@link #fail()} has been called.
         * 
         * This is used for example in a case where one performs:
         * ifResult(someResult1, someResult2).fail()
         * we want to propagate this call to a runner that does not supply parameters, since
         * we know that all results has failed.
         * 
         * This is, as opposed to:
         * ifResult(someResult1, someResult2).succeed().andMarker(someMarker).fail()
         * in this case, the call to fail should not alter the {@link ReturnClassifier}
         * since we do want to receive someResult1, someResult2 results on runner function.
         * 
         * @param <RETURN_CLASSIFIER> the matching {@link ReturnClassifier} to be returned
         */
        abstract protected static class Unlocked<RETURN_CLASSIFIER extends ReturnClassifier>
                extends FutureTransformer<RETURN_CLASSIFIER> {
            
            // Constructors

            private Unlocked(Result... results) {
                super(null, Arrays.stream(results).map(r -> r._future).toArray(RedFuture[]::new));
            }

            private Unlocked(Marker... markers) {
                super(null, Arrays.stream(markers).map(m -> m._future).toArray(RedFuture[]::new));
            }
            
            // Public

            /**
             * Expect the later results or markers to fail, and return a
             * {@link ReturnClassifier.Classifier0} to choose which kind of value wrapper to produce
             * @return a {@link ReturnClassifier.Classifier0} to choose which kind of
             * value wrapper to produce
             */
            public ReturnClassifier.Classifier0 fail() {
                return new ReturnClassifier.Classifier0(mapCurrentLayer(future -> {
                    OpenRedFuture result = RedFuture.future();
                    future.addSuccessCallback(() -> result.fail(PreconditionFailedException.Failure.INSTANCE));
                    future.addFailureCallback(throwable -> result.resolve());
                    return result;
                }));
            }

            // Static

            /**
             * An {@link Unlocked} of 0 parameters
             */
            public static class Unlocked0 extends Unlocked<ReturnClassifier.Classifier0> {

                private Unlocked0(Marker... markers) {
                    super(markers);
                }

                @Override
                protected ReturnClassifier.Classifier0 classifier(RedFuture[] preconditions) {
                    return new ReturnClassifier.Classifier0(preconditions);
                }

            }

            /**
             * An {@link Unlocked} of 1 parameter
             * @param <T0> type of parameter1
             */
            public static class Unlocked1<T0> extends Unlocked<ReturnClassifier.Classifier1<T0>> {

                private Unlocked1(Result... results) {
                    super(results);
                }

                @Override
                protected ReturnClassifier.Classifier1<T0> classifier(RedFuture[] preconditions) {
                    return new ReturnClassifier.Classifier1<>(preconditions);
                }

            }

            /**
             * An {@link Unlocked} of 2 parameters
             * @param <T0> type of parameter1
             * @param <T1> type of parameter2
             */
            public static class Unlocked2<T0, T1> extends Unlocked<ReturnClassifier.Classifier2<T0, T1>> {

                private Unlocked2(Result... results) {
                    super(results);
                }

                @Override
                protected ReturnClassifier.Classifier2<T0, T1> classifier(RedFuture[] preconditions) {
                    return new ReturnClassifier.Classifier2<>(preconditions);
                }

            }

            /**
             * An {@link Unlocked} of 3 parameters
             * @param <T0> type of parameter1
             * @param <T1> type of parameter2
             * @param <T2> type of parameter3
             */
            public static class Unlocked3<T0, T1, T2> extends Unlocked<ReturnClassifier.ReturnClassifier3<T0, T1, T2>> {

                private Unlocked3(Result... results) {
                    super(results);
                }

                @Override
                protected ReturnClassifier.ReturnClassifier3<T0, T1, T2> classifier(RedFuture[] preconditions) {
                    return new ReturnClassifier.ReturnClassifier3<>(preconditions);
                }

            }

            /**
             * An {@link Unlocked} of 4 parameters
             * @param <T0> type of parameter1
             * @param <T1> type of parameter2
             * @param <T2> type of parameter3
             * @param <T3> type of parameter4
             */
            public static class Unlocked4<T0, T1, T2, T3>
                    extends Unlocked<ReturnClassifier.ReturnClassifier4<T0, T1, T2, T3>> {

                private Unlocked4(Result... results) {
                    super(results);
                }

                @Override
                protected ReturnClassifier.ReturnClassifier4<T0, T1, T2, T3> classifier(RedFuture[] preconditions) {
                    return new ReturnClassifier.ReturnClassifier4<>(preconditions);
                }

            }

            /**
             * An {@link Unlocked} of 5 parameters
             * @param <T0> type of parameter1
             * @param <T1> type of parameter2
             * @param <T2> type of parameter3
             * @param <T3> type of parameter4
             * @param <T4> type of parameter5
             */
            public static class Unlocked5<T0, T1, T2, T3, T4>
                    extends Unlocked<ReturnClassifier.ReturnClassifier5<T0, T1, T2, T3, T4>> {

                private Unlocked5(Result... results) {
                    super(results);
                }

                @Override
                protected ReturnClassifier.ReturnClassifier5<T0, T1, T2, T3, T4> classifier(RedFuture[] preconditions) {
                    return new ReturnClassifier.ReturnClassifier5<>(preconditions);
                }

            }

            /**
             * An {@link Unlocked} of 6 parameters
             * @param <T0> type of parameter1
             * @param <T1> type of parameter2
             * @param <T2> type of parameter3
             * @param <T3> type of parameter4
             * @param <T4> type of parameter5
             * @param <T5> type of parameter6
             */
            public static class Unlocked6<T0, T1, T2, T3, T4, T5>
                    extends Unlocked<ReturnClassifier.ReturnClassifier6<T0, T1, T2, T3, T4, T5>> {

                private Unlocked6(Result... results) {
                    super(results);
                }

                @Override
                protected ReturnClassifier.ReturnClassifier6<T0, T1, T2, T3, T4, T5> classifier(RedFuture[] preconditions) {
                    return new ReturnClassifier.ReturnClassifier6<>(preconditions);
                }

            }

            /**
             * An {@link Unlocked} of 7 parameters
             * @param <T0> type of parameter1
             * @param <T1> type of parameter2
             * @param <T2> type of parameter3
             * @param <T3> type of parameter4
             * @param <T4> type of parameter5
             * @param <T5> type of parameter6
             * @param <T6> type of parameter7
             */
            public static class Unlocked7<T0, T1, T2, T3, T4, T5, T6>
                    extends Unlocked<ReturnClassifier.ReturnClassifier7
                                        <T0, T1, T2, T3, T4, T5, T6>> {

                private Unlocked7(Result... results) {
                    super(results);
                }

                @Override
                protected ReturnClassifier.ReturnClassifier7<T0, T1, T2, T3, T4, T5, T6>
                classifier(RedFuture[] preconditions) {
                    return new ReturnClassifier.ReturnClassifier7<>(preconditions);
                }

            }

            /**
             * An {@link Unlocked} of 8 parameters
             * @param <T0> type of parameter1
             * @param <T1> type of parameter2
             * @param <T2> type of parameter3
             * @param <T3> type of parameter4
             * @param <T4> type of parameter5
             * @param <T5> type of parameter6
             * @param <T6> type of parameter7
             * @param <T7> type of parameter8
             */
            public static class Unlocked8<T0, T1, T2, T3, T4, T5, T6, T7>
                    extends Unlocked<ReturnClassifier.ReturnClassifier8
                                        <T0, T1, T2, T3, T4, T5, T6, T7>> {

                private Unlocked8(Result... results) {
                    super(results);
                }

                @Override
                protected ReturnClassifier.ReturnClassifier8<T0, T1, T2, T3, T4, T5, T6, T7>
                classifier(RedFuture[] preconditions) {
                    return new ReturnClassifier.ReturnClassifier8<>(preconditions);
                }

            }

            /**
             * An {@link Unlocked} of 9 parameters
             * @param <T0> type of parameter1
             * @param <T1> type of parameter2
             * @param <T2> type of parameter3
             * @param <T3> type of parameter4
             * @param <T4> type of parameter5
             * @param <T5> type of parameter6
             * @param <T6> type of parameter7
             * @param <T7> type of parameter8
             * @param <T8> type of parameter9
             */
            public static class Unlocked9<T0, T1, T2, T3, T4, T5, T6, T7, T8>
                    extends Unlocked<ReturnClassifier.ReturnClassifier9
                                        <T0, T1, T2, T3, T4, T5, T6, T7, T8>> {

                private Unlocked9(Result... results) {
                    super(results);
                }

                @Override
                protected ReturnClassifier.ReturnClassifier9<T0, T1, T2, T3, T4, T5, T6, T7, T8>
                classifier(RedFuture[] preconditions) {
                    return new ReturnClassifier.ReturnClassifier9<>(preconditions);
                }

            }

            /**
             * An {@link Unlocked} of 10 parameters
             * @param <T0> type of parameter1
             * @param <T1> type of parameter2
             * @param <T2> type of parameter3
             * @param <T3> type of parameter4
             * @param <T4> type of parameter5
             * @param <T5> type of parameter6
             * @param <T6> type of parameter7
             * @param <T7> type of parameter8
             * @param <T8> type of parameter9
             * @param <T9> type of parameter10
             */
            public static class Unlocked10<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9>
                    extends Unlocked<ReturnClassifier.ReturnClassifier10
                                        <T0, T1, T2, T3, T4, T5, T6, T7, T8, T9>> {

                private Unlocked10(Result... results) {
                    super(results);
                }

                @Override
                protected ReturnClassifier.ReturnClassifier10<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9>
                classifier(RedFuture[] preconditions) {
                    return new ReturnClassifier.ReturnClassifier10<>(preconditions);
                }

            }

            /**
             * An {@link Unlocked} of N parameters
             */
            public static class UnlockedN extends Unlocked<ReturnClassifier.ReturnClassifierN> {

                private UnlockedN(Result... results) {
                    super(results);
                }

                @Override
                protected ReturnClassifier.ReturnClassifierN classifier(RedFuture[] preconditions) {
                    return new ReturnClassifier.ReturnClassifierN(preconditions);
                }

            }

        }

        /**
         * A {@link FutureTransformer} which leaves the original <RETURN_CLASSIFIER> parameter
         * in case {@link #fail()} has been called.
         *
         * This is used for example in a case where one performs:
         * ifResult(someResult1, someResult2).succeed().andMarker(someMarker).fail()
         * in this case, the call to fail should not alter the {@link ReturnClassifier}
         * since we do want to receive someResult1, someResult2 results on runner function.
         *
         * This is, as opposed to:
         * ifResult(someResult1, someResult2).fail()
         * we want to propagate this call to a runner that does not supply parameters, since
         * we know that all results has failed.
         *
         * @param <RETURN_CLASSIFIER> the matching {@link ReturnClassifier} to be returned
         */
        abstract protected static class Locked<RETURN_CLASSIFIER extends ReturnClassifier>
                extends FutureTransformer<RETURN_CLASSIFIER> {

            // Constructors

            private Locked(RedFuture[] oldPreconditions, Marker... markers) {
                super(oldPreconditions, Arrays.stream(markers).map(marker -> marker._future).toArray(RedFuture[]::new));
            }

            // Public

            /**
             * Expect the later results or markers to fail, and return a
             * {@link ReturnClassifier} to choose which kind of value wrapper to produce
             * @return a {@link ReturnClassifier} to choose which kind of value wrapper to produce
             */
            public RETURN_CLASSIFIER fail() {
                return createClassifier(future -> {
                    OpenRedFuture result = RedFuture.future();
                    future.addSuccessCallback(() -> result.fail(PreconditionFailedException.Failure.INSTANCE));
                    future.addFailureCallback(throwable -> result.resolve());
                    return result;
                });
            }

            /**
             * A {@link Locked} with no parameters
             */
            public static class Locked0 extends Locked<ReturnClassifier.Classifier0> {

                private Locked0(RedFuture[] oldPreconditions, Marker... markers) {
                    super(oldPreconditions, markers);
                }

                @Override
                protected ReturnClassifier.Classifier0 classifier(RedFuture[] preconditions) {
                    return new ReturnClassifier.Classifier0(preconditions);
                }

            }

            /**
             * A {@link Locked} of 1 parameter
             * @param <T0> type of parameter1
             */
            public static class Locked1<T0>
                    extends Locked<ReturnClassifier.Classifier1<T0>> {

                private Locked1(RedFuture[] oldPreconditions, Marker... markers) {
                    super(oldPreconditions, markers);
                }

                @Override
                protected ReturnClassifier.Classifier1<T0> classifier(RedFuture[] preconditions) {
                    return new ReturnClassifier.Classifier1<>(preconditions);
                }

            }

            /**
             * A {@link Locked} of 2 parameters
             * @param <T0> type of parameter1
             * @param <T1> type of parameter2
             */
            public static class Locked2<T0, T1> extends Locked<ReturnClassifier.Classifier2<T0, T1>> {

                private Locked2(RedFuture[] oldPreconditions, Marker... markers) {
                    super(oldPreconditions, markers);
                }

                @Override
                protected ReturnClassifier.Classifier2<T0, T1> classifier(RedFuture[] preconditions) {
                    return new ReturnClassifier.Classifier2<>(preconditions);
                }

            }

            /**
             * A {@link Locked} of 3 parameters
             * @param <T0> type of parameter1
             * @param <T1> type of parameter2
             * @param <T2> type of parameter2
             */
            public static class Locked3<T0, T1, T2>
                    extends Locked<ReturnClassifier.ReturnClassifier3<T0, T1, T2>> {

                private Locked3(RedFuture[] oldPreconditions, Marker... markers) {
                    super(oldPreconditions, markers);
                }

                @Override
                protected ReturnClassifier.ReturnClassifier3<T0, T1, T2> classifier(RedFuture[] preconditions) {
                    return new ReturnClassifier.ReturnClassifier3<>(preconditions);
                }

            }

            /**
             * A {@link Locked} of 4 parameters
             * @param <T0> type of parameter1
             * @param <T1> type of parameter2
             * @param <T2> type of parameter2
             * @param <T3> type of parameter4
             */
            public static class Locked4<T0, T1, T2, T3>
                    extends Locked<ReturnClassifier.ReturnClassifier4<T0, T1, T2, T3>> {

                private Locked4(RedFuture[] oldPreconditions, Marker... markers) {
                    super(oldPreconditions, markers);
                }

                @Override
                protected ReturnClassifier.ReturnClassifier4<T0, T1, T2, T3> classifier(RedFuture[] preconditions) {
                    return new ReturnClassifier.ReturnClassifier4<>(preconditions);
                }

            }

            /**
             * A {@link Locked} of 5 parameters
             * @param <T0> type of parameter1
             * @param <T1> type of parameter2
             * @param <T2> type of parameter2
             * @param <T3> type of parameter4
             * @param <T4> type of parameter5
             */
            public static class Locked5<T0, T1, T2, T3, T4>
                    extends Locked<ReturnClassifier.ReturnClassifier5<T0, T1, T2, T3, T4>> {

                private Locked5(RedFuture[] oldPreconditions, Marker... markers) {
                    super(oldPreconditions, markers);
                }

                @Override
                protected ReturnClassifier.ReturnClassifier5<T0, T1, T2, T3, T4> classifier(RedFuture[] preconditions) {
                    return new ReturnClassifier.ReturnClassifier5<>(preconditions);
                }

            }

            /**
             * A {@link Locked} of 6 parameters
             * @param <T0> type of parameter1
             * @param <T1> type of parameter2
             * @param <T2> type of parameter2
             * @param <T3> type of parameter4
             * @param <T4> type of parameter5
             * @param <T5> type of parameter6
             */
            public static class Locked6<T0, T1, T2, T3, T4, T5>
                    extends Locked<ReturnClassifier.ReturnClassifier6<T0, T1, T2, T3, T4, T5>> {

                private Locked6(RedFuture[] oldPreconditions, Marker... markers) {
                    super(oldPreconditions, markers);
                }

                @Override
                protected ReturnClassifier.ReturnClassifier6<T0, T1, T2, T3, T4, T5> classifier(RedFuture[] preconditions) {
                    return new ReturnClassifier.ReturnClassifier6<>(preconditions);
                }

            }

            /**
             * A {@link Locked} of 7 parameters
             * @param <T0> type of parameter1
             * @param <T1> type of parameter2
             * @param <T2> type of parameter2
             * @param <T3> type of parameter4
             * @param <T4> type of parameter5
             * @param <T5> type of parameter6
             * @param <T6> type of parameter7
             */
            public static class Locked7<T0, T1, T2, T3, T4, T5, T6>
                    extends Locked<ReturnClassifier.ReturnClassifier7<T0, T1, T2, T3, T4, T5, T6>> {

                private Locked7(RedFuture[] oldPreconditions, Marker... markers) {
                    super(oldPreconditions, markers);
                }

                @Override
                protected ReturnClassifier.ReturnClassifier7<T0, T1, T2, T3, T4, T5, T6>
                classifier(RedFuture[] preconditions) {
                    return new ReturnClassifier.ReturnClassifier7<>(preconditions);
                }

            }

            /**
             * A {@link Locked} of 8 parameters
             * @param <T0> type of parameter1
             * @param <T1> type of parameter2
             * @param <T2> type of parameter2
             * @param <T3> type of parameter4
             * @param <T4> type of parameter5
             * @param <T5> type of parameter6
             * @param <T6> type of parameter7
             * @param <T7> type of parameter8
             */
            public static class Locked8<T0, T1, T2, T3, T4, T5, T6, T7>
                    extends Locked<ReturnClassifier.ReturnClassifier8<T0, T1, T2, T3, T4, T5, T6, T7>> {

                private Locked8(RedFuture[] oldPreconditions, Marker... markers) {
                    super(oldPreconditions, markers);
                }

                @Override
                protected ReturnClassifier.ReturnClassifier8<T0, T1, T2, T3, T4, T5, T6, T7>
                classifier(RedFuture[] preconditions) {
                    return new ReturnClassifier.ReturnClassifier8<>(preconditions);
                }

            }

            /**
             * A {@link Locked} of 9 parameters
             * @param <T0> type of parameter1
             * @param <T1> type of parameter2
             * @param <T2> type of parameter2
             * @param <T3> type of parameter4
             * @param <T4> type of parameter5
             * @param <T5> type of parameter6
             * @param <T6> type of parameter7
             * @param <T7> type of parameter8
             * @param <T8> type of parameter9
             */
            public static class Locked9<T0, T1, T2, T3, T4, T5, T6, T7, T8>
                    extends Locked<ReturnClassifier.ReturnClassifier9
                                        <T0, T1, T2, T3, T4, T5, T6, T7, T8>> {

                private Locked9(RedFuture[] oldPreconditions, Marker... markers) {
                    super(oldPreconditions, markers);
                }

                @Override
                protected ReturnClassifier.ReturnClassifier9<T0, T1, T2, T3, T4, T5, T6, T7, T8>
                classifier(RedFuture[] preconditions) {
                    return new ReturnClassifier.ReturnClassifier9<>(preconditions);
                }

            }

            /**
             * A {@link Locked} of 10 parameters
             * @param <T0> type of parameter1
             * @param <T1> type of parameter2
             * @param <T2> type of parameter2
             * @param <T3> type of parameter4
             * @param <T4> type of parameter5
             * @param <T5> type of parameter6
             * @param <T6> type of parameter7
             * @param <T7> type of parameter8
             * @param <T8> type of parameter9
             * @param <T9> type of parameter10
             */
            public static class Locked10<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9>
                    extends Locked<ReturnClassifier.ReturnClassifier10
                                        <T0, T1, T2, T3, T4, T5, T6, T7, T8, T9>> {

                private Locked10(RedFuture[] oldPreconditions, Marker... markers) {
                    super(oldPreconditions, markers);
                }

                @Override
                protected ReturnClassifier.ReturnClassifier10<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9>
                classifier(RedFuture[] preconditions) {
                    return new ReturnClassifier.ReturnClassifier10<>(preconditions);
                }

            }

            /**
             * A {@link Locked} of N parameters
             */
            public static class LockedN extends Locked<ReturnClassifier.ReturnClassifierN> {

                private LockedN(RedFuture[] oldPreconditions, Marker... markers) {
                    super(oldPreconditions, markers);
                }

                @Override
                protected ReturnClassifier.ReturnClassifierN classifier(RedFuture[] preconditions) {
                    return new ReturnClassifier.ReturnClassifierN(preconditions);
                }

            }

        }

    }

    /**
     * A middleware class to choose which kind of method the result of the calculation
     * is returned in.
     *
     * Currently supporting returning of direct values, {@link Future} of values,
     * {@link ListenableFuture} of value, or {@link RedFutureOf} of value.
     * Additionally, the {@link ReturnClassifier} supports calling directly to execute
     * a command by calling {@link #execute(Command)}, and this way determine the returned result
     * to be a {@link Marker} instead of a {@link Result}.
     *
     * The {@link ReturnClassifier} middleware also support adding additional preconditions
     * in the form of {@link Marker} which return a {@link FutureTransformer.Locked} instance.
     *
     * @param <COMMAND>     the type of command to {@link #execute(Command)}
     * @param <TRANSFORMER> the type of transformer to return when calling {@link #andMarkers(Marker...)}
     */
    abstract protected static class ReturnClassifier<COMMAND extends Command,
            TRANSFORMER extends FutureTransformer.Locked> extends Middleware {

        // Constructors

        private ReturnClassifier(RedFuture[] preconditions) {
            super(preconditions);
        }

        // Public

        /**
         * Execute the given command, and return a marker of the execution.
         * @param c command to execute
         * @return a marker of the execution
         */
        public Marker execute(COMMAND c) {
            Marker marker = new Marker();
            PendingMarker pendingMarker = new PendingMarker(marker);
            RedFuture
                    .hub()
                    .adoptFutures(preconditions())
                    .uniteOptimistically()
                    .addFailureCallback(marker._future::fail)
                    .addSuccessCallback(() -> {
                        try {
                            call(c, pendingMarker);
                        } catch (Throwable t) {
                            marker._future.fail(t);
                        }
                    });
            return marker;
        }

        /**
         * Add additional markers as preconditions of the current construction chain
         * @param markers markers to add as preconditions
         * @return {@link FutureTransformer.Locked} middleware instance
         */
        public TRANSFORMER andMarkers(Marker... markers) {
            return transformer(preconditions(), markers);
        }

        // Private

        /**
         * An implementation of each concrete {@link ReturnClassifier} of calling
         * the given command. This method is abstract since the concrete type of the command
         * and the number of parameters passed to it depends on the concrete types.
         * @param c             command to execute
         * @param pendingMarker pending marker to mark the task as finished
         * @throws Throwable to enable throwable catching
         */
        abstract protected void call(COMMAND c, PendingMarker pendingMarker) throws Throwable;

        /**
         * Generates the concrete instance of the {@link ReturnClassifier} transformer.
         * @param oldPreconditions an array of locked preconditions -
         *                         futures that are already determined and should be altered.
         * @param markers          new markers to transform
         * @return {@link FutureTransformer.Locked} middleware instance
         */
        abstract protected TRANSFORMER transformer(RedFuture[] oldPreconditions, Marker... markers);

        // Static

        /**
         * A {@link ReturnClassifier} of 0 parameters
         */
        public static class Classifier0 extends ReturnClassifier<
                Command.Command0,
                FutureTransformer.Locked.Locked0
                > {

            // Constructors

            private Classifier0() {
                super(new RedFuture[0]);
            }

            private Classifier0(RedFuture[] preconditions) {
                super(preconditions);
            }

            // Public

            /**
             * Sets the expected result of the execution to be a direct value of the given class
             * @param tClass class of the result
             * @param <R>    type of the result
             * @return a middleware {@link Runner} instance
             */
            public <R> Runner.Runner0<R, R>
            produce(@SuppressWarnings("unused") Class<R> tClass) {
                return new Runner.Runner0<>(preconditions(), Converter.value());
            }

            /**
             * Sets the expected result of the execution to be a {@link Future} value of the given class
             * @param tClass class of the result
             * @param <R>    type of the result
             * @return a middleware {@link Runner} instance
             */
            public <R> Runner.Runner0<Future<R>, R>
            produceFutureOf(@SuppressWarnings("unused") Class<R> tClass) {
                return new Runner.Runner0<>(preconditions(), Converter.future());
            }

            /**
             * Sets the expected result of the execution to be a {@link ListenableFuture} value of the given class
             * @param tClass class of the result
             * @param <R>    type of the result
             * @return a middleware {@link Runner} instance
             */
            public <R> Runner.Runner0<ListenableFuture<R>, R>
            produceListenableFutureOf(@SuppressWarnings("unused") Class<R> tClass) {
                return new Runner.Runner0<>(preconditions(), Converter.listenableFuture());
            }

            /**
             * Sets the expected result of the execution to be a {@link RedFutureOf} value of the given class
             * @param tClass class of the result
             * @param <R>    type of the result
             * @return a middleware {@link Runner} instance
             */
            public <R> Runner.Runner0<RedFutureOf<R>, R>
            produceRedFutureOf(@SuppressWarnings("unused") Class<R> tClass) {
                return new Runner.Runner0<>(preconditions(), Converter.redFuture());
            }

            // Private

            @Override
            protected void call(Command.Command0 c, PendingMarker pendingMarker) throws Throwable {
                c.call(pendingMarker);
            }

            @Override
            protected FutureTransformer.Locked.Locked0
            transformer(RedFuture[] oldPreconditions, Marker... markers) {
                return new FutureTransformer.Locked.Locked0(oldPreconditions, markers);
            }

        }

        /**
         * A {@link ReturnClassifier} of 1 parameter
         * @param <T0> type of parameter1
         */
        public static class Classifier1<T0> extends ReturnClassifier<
                Command.Command1<T0>,
                FutureTransformer.Locked.Locked1<T0>
                > {

            // Constructors

            private Classifier1(RedFuture[] preconditions) {
                super(preconditions);
            }

            // Public

            /**
             * Sets the expected result of the execution to be a direct value of the given class
             * @param tClass class of the result
             * @param <R>    type of the result
             * @return a middleware {@link Runner} instance
             */
            public <R> Runner.Runner1<R, R, T0>
            produce(@SuppressWarnings("unused") Class<R> tClass) {
                return new Runner.Runner1<>(preconditions(), Converter.value());
            }

            /**
             * Sets the expected result of the execution to be a {@link Future} value of the given class
             * @param tClass class of the result
             * @param <R>    type of the result
             * @return a middleware {@link Runner} instance
             */
            public <R> Runner.Runner1<Future<R>, R, T0>
            produceFutureOf(@SuppressWarnings("unused") Class<R> tClass) {
                return new Runner.Runner1<>(preconditions(), Converter.future());
            }

            /**
             * Sets the expected result of the execution to be a {@link ListenableFuture} value of the given class
             * @param tClass class of the result
             * @param <R>    type of the result
             * @return a middleware {@link Runner} instance
             */
            public <R> Runner.Runner1<ListenableFuture<R>, R, T0>
            produceListenableFutureOf(@SuppressWarnings("unused") Class<R> tClass) {
                return new Runner.Runner1<>(preconditions(), Converter.listenableFuture());
            }

            /**
             * Sets the expected result of the execution to be a {@link RedFutureOf} value of the given class
             * @param tClass class of the result
             * @param <R>    type of the result
             * @return a middleware {@link Runner} instance
             */
            public <R> Runner.Runner1<RedFutureOf<R>, R, T0>
            produceRedFutureOf(@SuppressWarnings("unused") Class<R> tClass) {
                return new Runner.Runner1<>(preconditions(), Converter.redFuture());
            }

            // Private

            @Override
            protected void call(Command.Command1<T0> c, PendingMarker pendingMarker) throws Throwable {
                c.call(pendingMarker, typedResult(0));
            }

            @Override
            protected FutureTransformer.Locked.Locked1<T0>
            transformer(RedFuture[] oldPreconditions, Marker... markers) {
                return new FutureTransformer.Locked.Locked1<>(oldPreconditions, markers);
            }

        }

        /**
         * A {@link ReturnClassifier} of 2 parameters
         * @param <T0> type of parameter1
         * @param <T1> type of parameter2
         */
        public static class Classifier2<T0, T1> extends ReturnClassifier<
                Command.Command2<T0, T1>,
                FutureTransformer.Locked.Locked2<T0, T1>
                > {

            // Constructors

            private Classifier2(RedFuture[] preconditions) {
                super(preconditions);
            }

            // Public

            /**
             * Sets the expected result of the execution to be a direct value of the given class
             * @param tClass class of the result
             * @param <R>    type of the result
             * @return a middleware {@link Runner} instance
             */
            public <R> Runner.Runner2<R, R, T0, T1>
            produce(@SuppressWarnings("unused") Class<R> tClass) {
                return new Runner.Runner2<>(preconditions(), Converter.value());
            }

            /**
             * Sets the expected result of the execution to be a {@link Future} value of the given class
             * @param tClass class of the result
             * @param <R>    type of the result
             * @return a middleware {@link Runner} instance
             */
            public <R> Runner.Runner2<Future<R>, R, T0, T1>
            produceFutureOf(@SuppressWarnings("unused") Class<R> tClass) {
                return new Runner.Runner2<>(preconditions(), Converter.future());
            }

            /**
             * Sets the expected result of the execution to be a {@link ListenableFuture} value of the given class
             * @param tClass class of the result
             * @param <R>    type of the result
             * @return a middleware {@link Runner} instance
             */
            public <R> Runner.Runner2<ListenableFuture<R>, R, T0, T1>
            produceListenableFutureOf(@SuppressWarnings("unused") Class<R> tClass) {
                return new Runner.Runner2<>(preconditions(), Converter.listenableFuture());
            }

            /**
             * Sets the expected result of the execution to be a {@link RedFutureOf} value of the given class
             * @param tClass class of the result
             * @param <R>    type of the result
             * @return a middleware {@link Runner} instance
             */
            public <R> Runner.Runner2<RedFutureOf<R>, R, T0, T1>
            produceRedFutureOf(@SuppressWarnings("unused") Class<R> tClass) {
                return new Runner.Runner2<>(preconditions(), Converter.redFuture());
            }

            // Private

            @Override
            protected void call(Command.Command2<T0, T1> c, PendingMarker pendingMarker) throws Throwable {
                c.call(pendingMarker, typedResult(0), typedResult(1));
            }

            @Override
            protected FutureTransformer.Locked.Locked2<T0, T1>
            transformer(RedFuture[] oldPreconditions, Marker... markers) {
                return new FutureTransformer.Locked.Locked2<>(oldPreconditions, markers);
            }

        }

        /**
         * A {@link ReturnClassifier} of 3 parameters
         * @param <T0> type of parameter1
         * @param <T1> type of parameter2
         * @param <T2> type of parameter3
         */
        public static class ReturnClassifier3<T0, T1, T2> extends ReturnClassifier<
                Command.Command3<T0, T1, T2>,
                FutureTransformer.Locked.Locked3<T0, T1, T2>
                > {

            // Constructors

            private ReturnClassifier3(RedFuture[] preconditions) {
                super(preconditions);
            }

            // Public

            /**
             * Sets the expected result of the execution to be a direct value of the given class
             * @param tClass class of the result
             * @param <R>    type of the result
             * @return a middleware {@link Runner} instance
             */
            public <R> Runner.Runner3<R, R, T0, T1, T2>
            produce(@SuppressWarnings("unused") Class<R> tClass) {
                return new Runner.Runner3<>(preconditions(), Converter.value());
            }

            /**
             * Sets the expected result of the execution to be a {@link Future} value of the given class
             * @param tClass class of the result
             * @param <R>    type of the result
             * @return a middleware {@link Runner} instance
             */
            public <R> Runner.Runner3<Future<R>, R, T0, T1, T2>
            produceFutureOf(@SuppressWarnings("unused") Class<R> tClass) {
                return new Runner.Runner3<>(preconditions(), Converter.future());
            }

            /**
             * Sets the expected result of the execution to be a {@link ListenableFuture} value of the given class
             * @param tClass class of the result
             * @param <R>    type of the result
             * @return a middleware {@link Runner} instance
             */
            public <R> Runner.Runner3<ListenableFuture<R>, R, T0, T1, T2>
            produceListenableFutureOf(@SuppressWarnings("unused") Class<R> tClass) {
                return new Runner.Runner3<>(preconditions(), Converter.listenableFuture());
            }

            /**
             * Sets the expected result of the execution to be a {@link RedFutureOf} value of the given class
             * @param tClass class of the result
             * @param <R>    type of the result
             * @return a middleware {@link Runner} instance
             */
            public <R> Runner.Runner3<RedFutureOf<R>, R, T0, T1, T2>
            produceRedFutureOf(@SuppressWarnings("unused") Class<R> tClass) {
                return new Runner.Runner3<>(preconditions(), Converter.redFuture());
            }

            // Private

            @Override
            protected void call(Command.Command3<T0, T1, T2> c, PendingMarker pendingMarker) throws Throwable {
                c.call(pendingMarker, typedResult(0), typedResult(1), typedResult(2));
            }

            @Override
            protected FutureTransformer.Locked.Locked3<T0, T1, T2>
            transformer(RedFuture[] oldPreconditions, Marker... markers) {
                return new FutureTransformer.Locked.Locked3<>(oldPreconditions, markers);
            }

        }

        /**
         * A {@link ReturnClassifier} of 4 parameters
         * @param <T0> type of parameter1
         * @param <T1> type of parameter2
         * @param <T2> type of parameter3
         * @param <T3> type of parameter4
         */
        public static class ReturnClassifier4<T0, T1, T2, T3> extends ReturnClassifier<
                Command.Command4<T0, T1, T2, T3>,
                FutureTransformer.Locked.Locked4<T0, T1, T2, T3>
                > {

            // Constructors

            private ReturnClassifier4(RedFuture[] preconditions) {
                super(preconditions);
            }

            // Public

            /**
             * Sets the expected result of the execution to be a direct value of the given class
             * @param tClass class of the result
             * @param <R>    type of the result
             * @return a middleware {@link Runner} instance
             */
            public <R> Runner.Runner4<R, R, T0, T1, T2, T3>
            produce(@SuppressWarnings("unused") Class<R> tClass) {
                return new Runner.Runner4<>(preconditions(), Converter.value());
            }

            /**
             * Sets the expected result of the execution to be a {@link Future} value of the given class
             * @param tClass class of the result
             * @param <R>    type of the result
             * @return a middleware {@link Runner} instance
             */
            public <R> Runner.Runner4<Future<R>, R, T0, T1, T2, T3>
            produceFutureOf(@SuppressWarnings("unused") Class<R> tClass) {
                return new Runner.Runner4<>(preconditions(), Converter.future());
            }

            /**
             * Sets the expected result of the execution to be a {@link ListenableFuture} value of the given class
             * @param tClass class of the result
             * @param <R>    type of the result
             * @return a middleware {@link Runner} instance
             */
            public <R> Runner.Runner4<ListenableFuture<R>, R, T0, T1, T2, T3>
            produceListenableFutureOf(@SuppressWarnings("unused") Class<R> tClass) {
                return new Runner.Runner4<>(preconditions(), Converter.listenableFuture());
            }

            /**
             * Sets the expected result of the execution to be a {@link RedFutureOf} value of the given class
             * @param tClass class of the result
             * @param <R>    type of the result
             * @return a middleware {@link Runner} instance
             */
            public <R> Runner.Runner4<RedFutureOf<R>, R, T0, T1, T2, T3>
            produceRedFutureOf(@SuppressWarnings("unused") Class<R> tClass) {
                return new Runner.Runner4<>(preconditions(), Converter.redFuture());
            }

            // Private

            @Override
            protected void call(Command.Command4<T0, T1, T2, T3> c, PendingMarker pendingMarker) throws Throwable {
                c.call(pendingMarker, typedResult(0), typedResult(1), typedResult(2), typedResult(3));
            }

            @Override
            protected FutureTransformer.Locked.Locked4<T0, T1, T2, T3>
            transformer(RedFuture[] oldPreconditions, Marker... markers) {
                return new FutureTransformer.Locked.Locked4<>(oldPreconditions, markers);
            }

        }

        /**
         * A {@link ReturnClassifier} of 5 parameters
         * @param <T0> type of parameter1
         * @param <T1> type of parameter2
         * @param <T2> type of parameter3
         * @param <T3> type of parameter4
         * @param <T4> type of parameter5
         */
        public static class ReturnClassifier5<T0, T1, T2, T3, T4> extends ReturnClassifier<
                Command.Command5<T0, T1, T2, T3, T4>,
                FutureTransformer.Locked.Locked5<T0, T1, T2, T3, T4>
                > {

            // Constructors

            private ReturnClassifier5(RedFuture[] preconditions) {
                super(preconditions);
            }

            // Public

            /**
             * Sets the expected result of the execution to be a direct value of the given class
             * @param tClass class of the result
             * @param <R>    type of the result
             * @return a middleware {@link Runner} instance
             */
            public <R> Runner.Runner5<R, R, T0, T1, T2, T3, T4>
            produce(@SuppressWarnings("unused") Class<R> tClass) {
                return new Runner.Runner5<>(preconditions(), Converter.value());
            }

            /**
             * Sets the expected result of the execution to be a {@link Future} value of the given class
             * @param tClass class of the result
             * @param <R>    type of the result
             * @return a middleware {@link Runner} instance
             */
            public <R> Runner.Runner5<Future<R>, R, T0, T1, T2, T3, T4>
            produceFutureOf(@SuppressWarnings("unused") Class<R> tClass) {
                return new Runner.Runner5<>(preconditions(), Converter.future());
            }

            /**
             * Sets the expected result of the execution to be a {@link ListenableFuture} value of the given class
             * @param tClass class of the result
             * @param <R>    type of the result
             * @return a middleware {@link Runner} instance
             */
            public <R> Runner.Runner5<ListenableFuture<R>, R, T0, T1, T2, T3, T4>
            produceListenableFutureOf(@SuppressWarnings("unused") Class<R> tClass) {
                return new Runner.Runner5<>(preconditions(), Converter.listenableFuture());
            }

            /**
             * Sets the expected result of the execution to be a {@link RedFutureOf} value of the given class
             * @param tClass class of the result
             * @param <R>    type of the result
             * @return a middleware {@link Runner} instance
             */
            public <R> Runner.Runner5<RedFutureOf<R>, R, T0, T1, T2, T3, T4>
            produceRedFutureOf(@SuppressWarnings("unused") Class<R> tClass) {
                return new Runner.Runner5<>(preconditions(), Converter.redFuture());
            }

            // Private

            @Override
            protected void call(Command.Command5<T0, T1, T2, T3, T4> c, PendingMarker pendingMarker)
                    throws Throwable {
                c.call(pendingMarker, typedResult(0), typedResult(1), typedResult(2), typedResult(3), typedResult(4));
            }

            @Override
            protected FutureTransformer.Locked.Locked5<T0, T1, T2, T3, T4>
            transformer(RedFuture[] oldPreconditions, Marker... markers) {
                return new FutureTransformer.Locked.Locked5<>(oldPreconditions, markers);
            }

        }

        /**
         * A {@link ReturnClassifier} of 6 parameters
         * @param <T0> type of parameter1
         * @param <T1> type of parameter2
         * @param <T2> type of parameter3
         * @param <T3> type of parameter4
         * @param <T4> type of parameter5
         * @param <T5> type of parameter6
         */
        public static class ReturnClassifier6<T0, T1, T2, T3, T4, T5> extends ReturnClassifier<
                Command.Command6<T0, T1, T2, T3, T4, T5>,
                FutureTransformer.Locked.Locked6<T0, T1, T2, T3, T4, T5>
                > {

            // Constructors

            private ReturnClassifier6(RedFuture[] preconditions) {
                super(preconditions);
            }

            // Public

            /**
             * Sets the expected result of the execution to be a direct value of the given class
             * @param tClass class of the result
             * @param <R>    type of the result
             * @return a middleware {@link Runner} instance
             */
            public <R> Runner.Runner6<R, R, T0, T1, T2, T3, T4, T5>
            produce(@SuppressWarnings("unused") Class<R> tClass) {
                return new Runner.Runner6<>(preconditions(), Converter.value());
            }

            /**
             * Sets the expected result of the execution to be a {@link Future} value of the given class
             * @param tClass class of the result
             * @param <R>    type of the result
             * @return a middleware {@link Runner} instance
             */
            public <R> Runner.Runner6<Future<R>, R, T0, T1, T2, T3, T4, T5>
            produceFutureOf(@SuppressWarnings("unused") Class<R> tClass) {
                return new Runner.Runner6<>(preconditions(), Converter.future());
            }

            /**
             * Sets the expected result of the execution to be a {@link ListenableFuture} value of the given class
             * @param tClass class of the result
             * @param <R>    type of the result
             * @return a middleware {@link Runner} instance
             */
            public <R> Runner.Runner6<ListenableFuture<R>, R, T0, T1, T2, T3, T4, T5>
            produceListenableFutureOf(@SuppressWarnings("unused") Class<R> tClass) {
                return new Runner.Runner6<>(preconditions(), Converter.listenableFuture());
            }

            /**
             * Sets the expected result of the execution to be a {@link RedFutureOf} value of the given class
             * @param tClass class of the result
             * @param <R>    type of the result
             * @return a middleware {@link Runner} instance
             */
            public <R> Runner.Runner6<RedFutureOf<R>, R, T0, T1, T2, T3, T4, T5>
            produceRedFutureOf(@SuppressWarnings("unused") Class<R> tClass) {
                return new Runner.Runner6<>(preconditions(), Converter.redFuture());
            }

            // Private

            @Override
            protected void call(Command.Command6<T0, T1, T2, T3, T4, T5> c, PendingMarker pendingMarker)
                    throws Throwable {
                c.call(pendingMarker, typedResult(0), typedResult(1), typedResult(2), typedResult(3), typedResult(4),
                        typedResult(5));
            }

            @Override
            protected FutureTransformer.Locked.Locked6<T0, T1, T2, T3, T4, T5>
            transformer(RedFuture[] oldPreconditions, Marker... markers) {
                return new FutureTransformer.Locked.Locked6<>(oldPreconditions, markers);
            }

        }

        /**
         * A {@link ReturnClassifier} of 7 parameters
         * @param <T0> type of parameter1
         * @param <T1> type of parameter2
         * @param <T2> type of parameter3
         * @param <T3> type of parameter4
         * @param <T4> type of parameter5
         * @param <T5> type of parameter6
         * @param <T6> type of parameter7
         */
        public static class ReturnClassifier7<T0, T1, T2, T3, T4, T5, T6> extends ReturnClassifier<
                Command.Command7<T0, T1, T2, T3, T4, T5, T6>,
                FutureTransformer.Locked.Locked7<T0, T1, T2, T3, T4, T5, T6>
                > {

            // Constructors

            private ReturnClassifier7(RedFuture[] preconditions) {
                super(preconditions);
            }

            // Public

            /**
             * Sets the expected result of the execution to be a direct value of the given class
             * @param tClass class of the result
             * @param <R>    type of the result
             * @return a middleware {@link Runner} instance
             */
            public <R> Runner.Runner7<R, R, T0, T1, T2, T3, T4, T5, T6>
            produce(@SuppressWarnings("unused") Class<R> tClass) {
                return new Runner.Runner7<>(preconditions(), Converter.value());
            }

            /**
             * Sets the expected result of the execution to be a {@link Future} value of the given class
             * @param tClass class of the result
             * @param <R>    type of the result
             * @return a middleware {@link Runner} instance
             */
            public <R> Runner.Runner7<Future<R>, R, T0, T1, T2, T3, T4, T5, T6>
            produceFutureOf(@SuppressWarnings("unused") Class<R> tClass) {
                return new Runner.Runner7<>(preconditions(), Converter.future());
            }

            /**
             * Sets the expected result of the execution to be a {@link ListenableFuture} value of the given class
             * @param tClass class of the result
             * @param <R>    type of the result
             * @return a middleware {@link Runner} instance
             */
            public <R> Runner.Runner7<ListenableFuture<R>, R, T0, T1, T2, T3, T4, T5, T6>
            produceListenableFutureOf(@SuppressWarnings("unused") Class<R> tClass) {
                return new Runner.Runner7<>(preconditions(), Converter.listenableFuture());
            }

            /**
             * Sets the expected result of the execution to be a {@link RedFutureOf} value of the given class
             * @param tClass class of the result
             * @param <R>    type of the result
             * @return a middleware {@link Runner} instance
             */
            public <R> Runner.Runner7<RedFutureOf<R>, R, T0, T1, T2, T3, T4, T5, T6>
            produceRedFutureOf(@SuppressWarnings("unused") Class<R> tClass) {
                return new Runner.Runner7<>(preconditions(), Converter.redFuture());
            }

            // Private

            @Override
            protected void call(Command.Command7<T0, T1, T2, T3, T4, T5, T6> c, PendingMarker pendingMarker)
                    throws Throwable {
                c.call(pendingMarker, typedResult(0), typedResult(1), typedResult(2), typedResult(3), typedResult(4),
                        typedResult(5), typedResult(6));
            }

            @Override
            protected FutureTransformer.Locked.Locked7<T0, T1, T2, T3, T4, T5, T6>
            transformer(RedFuture[] oldPreconditions, Marker... markers) {
                return new FutureTransformer.Locked.Locked7<>(oldPreconditions, markers);
            }

        }

        /**
         * A {@link ReturnClassifier} of 8 parameters
         * @param <T0> type of parameter1
         * @param <T1> type of parameter2
         * @param <T2> type of parameter3
         * @param <T3> type of parameter4
         * @param <T4> type of parameter5
         * @param <T5> type of parameter6
         * @param <T6> type of parameter7
         * @param <T7> type of parameter8
         */
        public static class ReturnClassifier8<T0, T1, T2, T3, T4, T5, T6, T7> extends ReturnClassifier<
                Command.Command8<T0, T1, T2, T3, T4, T5, T6, T7>,
                FutureTransformer.Locked.Locked8<T0, T1, T2, T3, T4, T5, T6, T7>
                > {

            // Constructors

            private ReturnClassifier8(RedFuture[] preconditions) {
                super(preconditions);
            }

            // Public

            /**
             * Sets the expected result of the execution to be a direct value of the given class
             * @param tClass class of the result
             * @param <R>    type of the result
             * @return a middleware {@link Runner} instance
             */
            public <R> Runner.Runner8<R, R, T0, T1, T2, T3, T4, T5, T6, T7>
            produce(@SuppressWarnings("unused") Class<R> tClass) {
                return new Runner.Runner8<>(preconditions(), Converter.value());
            }

            /**
             * Sets the expected result of the execution to be a {@link Future} value of the given class
             * @param tClass class of the result
             * @param <R>    type of the result
             * @return a middleware {@link Runner} instance
             */
            public <R> Runner.Runner8<Future<R>, R, T0, T1, T2, T3, T4, T5, T6, T7>
            produceFutureOf(@SuppressWarnings("unused") Class<R> tClass) {
                return new Runner.Runner8<>(preconditions(), Converter.future());
            }

            /**
             * Sets the expected result of the execution to be a {@link ListenableFuture} value of the given class
             * @param tClass class of the result
             * @param <R>    type of the result
             * @return a middleware {@link Runner} instance
             */
            public <R> Runner.Runner8<ListenableFuture<R>, R, T0, T1, T2, T3, T4, T5, T6, T7>
            produceListenableFutureOf(@SuppressWarnings("unused") Class<R> tClass) {
                return new Runner.Runner8<>(preconditions(), Converter.listenableFuture());
            }

            /**
             * Sets the expected result of the execution to be a {@link RedFutureOf} value of the given class
             * @param tClass class of the result
             * @param <R>    type of the result
             * @return a middleware {@link Runner} instance
             */
            public <R> Runner.Runner8<RedFutureOf<R>, R, T0, T1, T2, T3, T4, T5, T6, T7>
            produceRedFutureOf(@SuppressWarnings("unused") Class<R> tClass) {
                return new Runner.Runner8<>(preconditions(), Converter.redFuture());
            }

            // Private

            @Override
            protected void call(Command.Command8<T0, T1, T2, T3, T4, T5, T6, T7> c, PendingMarker pendingMarker)
                    throws Throwable {
                c.call(pendingMarker, typedResult(0), typedResult(1), typedResult(2), typedResult(3), typedResult(4),
                        typedResult(5), typedResult(6), typedResult(7));
            }

            @Override
            protected FutureTransformer.Locked.Locked8<T0, T1, T2, T3, T4, T5, T6, T7>
            transformer(RedFuture[] oldPreconditions, Marker... markers) {
                return new FutureTransformer.Locked.Locked8<>(oldPreconditions, markers);
            }

        }

        /**
         * A {@link ReturnClassifier} of 9 parameters
         * @param <T0> type of parameter1
         * @param <T1> type of parameter2
         * @param <T2> type of parameter3
         * @param <T3> type of parameter4
         * @param <T4> type of parameter5
         * @param <T5> type of parameter6
         * @param <T6> type of parameter7
         * @param <T7> type of parameter8
         * @param <T8> type of parameter9
         */
        public static class ReturnClassifier9<T0, T1, T2, T3, T4, T5, T6, T7, T8> extends ReturnClassifier<
                Command.Command9<T0, T1, T2, T3, T4, T5, T6, T7, T8>,
                FutureTransformer.Locked.Locked9<T0, T1, T2, T3, T4, T5, T6, T7, T8>
                > {

            // Constructors

            private ReturnClassifier9(RedFuture[] preconditions) {
                super(preconditions);
            }

            // Public

            /**
             * Sets the expected result of the execution to be a direct value of the given class
             * @param tClass class of the result
             * @param <R>    type of the result
             * @return a middleware {@link Runner} instance
             */
            public <R> Runner.Runner9<R, R, T0, T1, T2, T3, T4, T5, T6, T7, T8>
            produce(@SuppressWarnings("unused") Class<R> tClass) {
                return new Runner.Runner9<>(preconditions(), Converter.value());
            }

            /**
             * Sets the expected result of the execution to be a {@link Future} value of the given class
             * @param tClass class of the result
             * @param <R>    type of the result
             * @return a middleware {@link Runner} instance
             */
            public <R> Runner.Runner9<Future<R>, R, T0, T1, T2, T3, T4, T5, T6, T7, T8>
            produceFutureOf(@SuppressWarnings("unused") Class<R> tClass) {
                return new Runner.Runner9<>(preconditions(), Converter.future());
            }

            /**
             * Sets the expected result of the execution to be a {@link ListenableFuture} value of the given class
             * @param tClass class of the result
             * @param <R>    type of the result
             * @return a middleware {@link Runner} instance
             */
            public <R> Runner.Runner9<ListenableFuture<R>, R, T0, T1, T2, T3, T4, T5, T6, T7, T8>
            produceListenableFutureOf(@SuppressWarnings("unused") Class<R> tClass) {
                return new Runner.Runner9<>(preconditions(), Converter.listenableFuture());
            }

            /**
             * Sets the expected result of the execution to be a {@link RedFutureOf} value of the given class
             * @param tClass class of the result
             * @param <R>    type of the result
             * @return a middleware {@link Runner} instance
             */
            public <R> Runner.Runner9<RedFutureOf<R>, R, T0, T1, T2, T3, T4, T5, T6, T7, T8>
            produceRedFutureOf(@SuppressWarnings("unused") Class<R> tClass) {
                return new Runner.Runner9<>(preconditions(), Converter.redFuture());
            }

            // Private

            @Override
            protected void call(Command.Command9<T0, T1, T2, T3, T4, T5, T6, T7, T8> c,
                                PendingMarker pendingMarker) throws Throwable {
                c.call(pendingMarker, typedResult(0), typedResult(1), typedResult(2), typedResult(3), typedResult(4),
                        typedResult(5), typedResult(6), typedResult(7), typedResult(8));
            }

            @Override
            protected FutureTransformer.Locked.Locked9<T0, T1, T2, T3, T4, T5, T6, T7, T8>
            transformer(RedFuture[] oldPreconditions, Marker... markers) {
                return new FutureTransformer.Locked.Locked9<>(oldPreconditions, markers);
            }

        }

        /**
         * A {@link ReturnClassifier} of 10 parameters
         * @param <T0> type of parameter1
         * @param <T1> type of parameter2
         * @param <T2> type of parameter3
         * @param <T3> type of parameter4
         * @param <T4> type of parameter5
         * @param <T5> type of parameter6
         * @param <T6> type of parameter7
         * @param <T7> type of parameter8
         * @param <T8> type of parameter9
         * @param <T9> type of parameter10
         */
        public static class ReturnClassifier10<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9> extends ReturnClassifier<
                Command.Command10<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9>,
                FutureTransformer.Locked.Locked10<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9>
                > {

            // Constructors

            private ReturnClassifier10(RedFuture[] preconditions) {
                super(preconditions);
            }

            // Public

            /**
             * Sets the expected result of the execution to be a direct value of the given class
             * @param tClass class of the result
             * @param <R>    type of the result
             * @return a middleware {@link Runner} instance
             */
            public <R> Runner.Runner10<R, R, T0, T1, T2, T3, T4, T5, T6, T7, T8, T9>
            produce(@SuppressWarnings("unused") Class<R> tClass) {
                return new Runner.Runner10<>(preconditions(), Converter.value());
            }

            /**
             * Sets the expected result of the execution to be a {@link Future} value of the given class
             * @param tClass class of the result
             * @param <R>    type of the result
             * @return a middleware {@link Runner} instance
             */
            public <R> Runner.Runner10<Future<R>, R, T0, T1, T2, T3, T4, T5, T6, T7, T8, T9>
            produceFutureOf(@SuppressWarnings("unused") Class<R> tClass) {
                return new Runner.Runner10<>(preconditions(), Converter.future());
            }

            /**
             * Sets the expected result of the execution to be a {@link ListenableFuture} value of the given class
             * @param tClass class of the result
             * @param <R>    type of the result
             * @return a middleware {@link Runner} instance
             */
            public <R> Runner.Runner10<ListenableFuture<R>, R, T0, T1, T2, T3, T4, T5, T6, T7, T8, T9>
            produceListenableFutureOf(@SuppressWarnings("unused") Class<R> tClass) {
                return new Runner.Runner10<>(preconditions(), Converter.listenableFuture());
            }

            /**
             * Sets the expected result of the execution to be a {@link RedFutureOf} value of the given class
             * @param tClass class of the result
             * @param <R>    type of the result
             * @return a middleware {@link Runner} instance
             */
            public <R> Runner.Runner10<RedFutureOf<R>, R, T0, T1, T2, T3, T4, T5, T6, T7, T8, T9>
            produceRedFutureOf(@SuppressWarnings("unused") Class<R> tClass) {
                return new Runner.Runner10<>(preconditions(), Converter.redFuture());
            }

            // Private

            @Override
            protected void call(Command.Command10<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9> c,
                                PendingMarker pendingMarker) throws Throwable {
                c.call(pendingMarker, typedResult(0), typedResult(1), typedResult(2), typedResult(3), typedResult(4),
                        typedResult(5), typedResult(6), typedResult(7), typedResult(8), typedResult(9));
            }

            @Override
            protected FutureTransformer.Locked.Locked10<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9>
            transformer(RedFuture[] oldPreconditions, Marker... markers) {
                return new FutureTransformer.Locked.Locked10<>(oldPreconditions, markers);
            }

        }

        /**
         * A {@link ReturnClassifier} of N parameters
         */
        public static class ReturnClassifierN extends ReturnClassifier<
                Command.CommandN, FutureTransformer.Locked.LockedN> {

            // Constructors

            private ReturnClassifierN(RedFuture[] preconditions) {
                super(preconditions);
            }

            // Public

            /**
             * Sets the expected result of the execution to be a direct value of the given class
             * @param tClass class of the result
             * @param <R>    type of the result
             * @return a middleware {@link Runner} instance
             */
            public <R> Runner.RunnerN<R, R>
            produce(@SuppressWarnings("unused") Class<R> tClass) {
                return new Runner.RunnerN<>(preconditions(), Converter.value());
            }

            /**
             * Sets the expected result of the execution to be a {@link Future} value of the given class
             * @param tClass class of the result
             * @param <R>    type of the result
             * @return a middleware {@link Runner} instance
             */
            public <R> Runner.RunnerN<Future<R>, R>
            produceFutureOf(@SuppressWarnings("unused") Class<R> tClass) {
                return new Runner.RunnerN<>(preconditions(), Converter.future());
            }

            /**
             * Sets the expected result of the execution to be a {@link ListenableFuture} value of the given class
             * @param tClass class of the result
             * @param <R>    type of the result
             * @return a middleware {@link Runner} instance
             */
            public <R> Runner.RunnerN<ListenableFuture<R>, R>
            produceListenableFutureOf(@SuppressWarnings("unused") Class<R> tClass) {
                return new Runner.RunnerN<>(preconditions(), Converter.listenableFuture());
            }

            /**
             * Sets the expected result of the execution to be a {@link RedFutureOf} value of the given class
             * @param tClass class of the result
             * @param <R>    type of the result
             * @return a middleware {@link Runner} instance
             */
            public <R> Runner.RunnerN<RedFutureOf<R>, R>
            produceRedFutureOf(@SuppressWarnings("unused") Class<R> tClass) {
                return new Runner.RunnerN<>(preconditions(), Converter.redFuture());
            }

            // Private

            @Override
            protected void call(Command.CommandN c, PendingMarker pendingMarker) throws Throwable {
                c.call(pendingMarker, new Results(this));
            }

            @Override
            protected FutureTransformer.Locked.LockedN
            transformer(RedFuture[] oldPreconditions, Marker... markers) {
                return new FutureTransformer.Locked.LockedN(oldPreconditions, markers);
            }

        }

    }

    /**
     * A middleware class to enable calling the actual function to execute.
     * At this point of the construction chain, all parameters are already defined,
     * and the chain is just pending an actual function to execute.
     * @param <FUNCTION> type of the function to expect
     * @param <WRAPPER>  type of the object to be returned from the function
     *                   the wrapper may be <R>, {@link Future} of <R>,
     *                   {@link ListenableFuture} of <R> or {@link RedFutureOf} of <R>.
     * @param <R>        the type of the result of the execution
     */
    abstract protected static class Runner<FUNCTION extends Function, WRAPPER, R> extends Middleware {

        // Fields

        /**
         * A converter to be used to convert from the wrapper type
         * to the actual result type
         */
        private final Converter<WRAPPER, R> _converter;

        // Constructors

        private Runner(RedFuture[] preconditions, Converter<WRAPPER, R> converter) {
            super(preconditions);
            _converter = converter;
        }

        // Public

        /**
         * Receive a function to execute and invokes it
         * @param f function to execute
         * @return a result of the execution
         */
        public Result<R> byExecuting(FUNCTION f) {
            Result<R> result = new Result<>();
            RedFuture
                    .hub()
                    .adoptFutures(preconditions())
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

        // Private

        /**
         * Receive the function to call and calls it.
         * This method is abstract since calling the function depends on the concrete
         * type of the function.
         * @param f function to call
         * @return the function result
         * @throws Throwable to enable throwable catching
         */
        abstract protected WRAPPER call(FUNCTION f) throws Throwable;

        // Static

        /**
         * A {@link Runner} of 0 parameters
         * @param <WRAPPER>  type of the object to be returned from the function
         * @param <R>        the type of the result of the execution
         */
        public static class Runner0<WRAPPER, R>
                extends Runner<Function.Function0<WRAPPER>, WRAPPER, R> {

            private Runner0(RedFuture[] preconditions, Converter<WRAPPER, R> converter) {
                super(preconditions, converter);
            }

            @Override
            protected WRAPPER call(Function.Function0<WRAPPER> f) throws Throwable {
                return f.call();
            }

        }

        /**
         * A {@link Runner} of 1 parameter
         * @param <WRAPPER> type of the object to be returned from the function
         * @param <R>       the type of the result of the execution
         * @param <T0>      type of parameter1
         */
        public static class Runner1<WRAPPER, R, T0>
                extends Runner<Function.Function1<WRAPPER, T0>, WRAPPER, R> {

            private Runner1(RedFuture[] preconditions, Converter<WRAPPER, R> converter) {
                super(preconditions, converter);
            }

            @Override
            protected WRAPPER call(Function.Function1<WRAPPER, T0> f) throws Throwable {
                return f.call(typedResult(0));
            }

        }

        /**
         * A {@link Runner} of 2 parameter
         * @param <WRAPPER> type of the object to be returned from the function
         * @param <R>       the type of the result of the execution
         * @param <T0>      type of parameter1
         * @param <T1>      type of parameter2
         */
        public static class Runner2<WRAPPER, R, T0, T1>
                extends Runner<Function.Function2<WRAPPER, T0, T1>, WRAPPER, R> {

            private Runner2(RedFuture[] preconditions, Converter<WRAPPER, R> converter) {
                super(preconditions, converter);
            }

            @Override
            protected WRAPPER call(Function.Function2<WRAPPER, T0, T1> f) throws Throwable {
                return f.call(typedResult(0), typedResult(1));
            }

        }

        /**
         * A {@link Runner} of 3 parameter
         * @param <WRAPPER> type of the object to be returned from the function
         * @param <R>       the type of the result of the execution
         * @param <T0>      type of parameter1
         * @param <T1>      type of parameter2
         * @param <T2>      type of parameter3
         */
        public static class Runner3<WRAPPER, R, T0, T1, T2>
                extends Runner<Function.Function3<WRAPPER, T0, T1, T2>, WRAPPER, R> {

            private Runner3(RedFuture[] preconditions, Converter<WRAPPER, R> converter) {
                super(preconditions, converter);
            }

            @Override
            protected WRAPPER call(Function.Function3<WRAPPER, T0, T1, T2> f) throws Throwable {
                return f.call(typedResult(0), typedResult(1), typedResult(2));
            }

        }

        /**
         * A {@link Runner} of 4 parameter
         * @param <WRAPPER> type of the object to be returned from the function
         * @param <R>       the type of the result of the execution
         * @param <T0>      type of parameter1
         * @param <T1>      type of parameter2
         * @param <T2>      type of parameter3
         * @param <T3>      type of parameter4
         */
        public static class Runner4<WRAPPER, R, T0, T1, T2, T3>
                extends Runner<Function.Function4<WRAPPER, T0, T1, T2, T3>, WRAPPER, R> {

            private Runner4(RedFuture[] preconditions, Converter<WRAPPER, R> converter) {
                super(preconditions, converter);
            }

            @Override
            protected WRAPPER call(Function.Function4<WRAPPER, T0, T1, T2, T3> f) throws Throwable {
                return f.call(typedResult(0), typedResult(1), typedResult(2), typedResult(3));
            }

        }

        /**
         * A {@link Runner} of 5 parameter
         * @param <WRAPPER> type of the object to be returned from the function
         * @param <R>       the type of the result of the execution
         * @param <T0>      type of parameter1
         * @param <T1>      type of parameter2
         * @param <T2>      type of parameter3
         * @param <T3>      type of parameter4
         * @param <T4>      type of parameter5
         */
        public static class Runner5<WRAPPER, R, T0, T1, T2, T3, T4>
                extends Runner<Function.Function5<WRAPPER, T0, T1, T2, T3, T4>, WRAPPER, R> {

            private Runner5(RedFuture[] preconditions, Converter<WRAPPER, R> converter) {
                super(preconditions, converter);
            }

            @Override
            protected WRAPPER call(Function.Function5<WRAPPER, T0, T1, T2, T3, T4> f) throws Throwable {
                return f.call(typedResult(0), typedResult(1), typedResult(2), typedResult(3), typedResult(4));
            }

        }

        /**
         * A {@link Runner} of 6 parameter
         * @param <WRAPPER> type of the object to be returned from the function
         * @param <R>       the type of the result of the execution
         * @param <T0>      type of parameter1
         * @param <T1>      type of parameter2
         * @param <T2>      type of parameter3
         * @param <T3>      type of parameter4
         * @param <T4>      type of parameter5
         * @param <T5>      type of parameter6
         */
        public static class Runner6<WRAPPER, R, T0, T1, T2, T3, T4, T5>
                extends Runner<Function.Function6<WRAPPER, T0, T1, T2, T3, T4, T5>, WRAPPER, R> {

            private Runner6(RedFuture[] preconditions, Converter<WRAPPER, R> converter) {
                super(preconditions, converter);
            }

            @Override
            protected WRAPPER call(Function.Function6<WRAPPER, T0, T1, T2, T3, T4, T5> f) throws Throwable {
                return f.call(typedResult(0), typedResult(1), typedResult(2), typedResult(3), typedResult(4),
                        typedResult(5));
            }

        }

        /**
         * A {@link Runner} of 7 parameter
         * @param <WRAPPER> type of the object to be returned from the function
         * @param <R>       the type of the result of the execution
         * @param <T0>      type of parameter1
         * @param <T1>      type of parameter2
         * @param <T2>      type of parameter3
         * @param <T3>      type of parameter4
         * @param <T4>      type of parameter5
         * @param <T5>      type of parameter6
         * @param <T6>      type of parameter7
         */
        public static class Runner7<WRAPPER, R, T0, T1, T2, T3, T4, T5, T6>
                extends Runner<Function.Function7<WRAPPER, T0, T1, T2, T3, T4, T5, T6>, WRAPPER, R> {

            private Runner7(RedFuture[] preconditions, Converter<WRAPPER, R> converter) {
                super(preconditions, converter);
            }

            @Override
            protected WRAPPER call(Function.Function7<WRAPPER, T0, T1, T2, T3, T4, T5, T6> f) throws Throwable {
                return f.call(typedResult(0), typedResult(1), typedResult(2), typedResult(3), typedResult(4),
                        typedResult(5), typedResult(6));
            }

        }

        /**
         * A {@link Runner} of 8 parameter
         * @param <WRAPPER> type of the object to be returned from the function
         * @param <R>       the type of the result of the execution
         * @param <T0>      type of parameter1
         * @param <T1>      type of parameter2
         * @param <T2>      type of parameter3
         * @param <T3>      type of parameter4
         * @param <T4>      type of parameter5
         * @param <T5>      type of parameter6
         * @param <T6>      type of parameter7
         * @param <T7>      type of parameter8
         */
        public static class Runner8<WRAPPER, R, T0, T1, T2, T3, T4, T5, T6, T7>
                extends Runner<Function.Function8<WRAPPER, T0, T1, T2, T3, T4, T5, T6, T7>, WRAPPER, R> {

            private Runner8(RedFuture[] preconditions, Converter<WRAPPER, R> converter) {
                super(preconditions, converter);
            }

            @Override
            protected WRAPPER call(Function.Function8<WRAPPER, T0, T1, T2, T3, T4, T5, T6, T7> f) throws Throwable {
                return f.call(typedResult(0), typedResult(1), typedResult(2), typedResult(3), typedResult(4),
                        typedResult(5), typedResult(6), typedResult(7));
            }

        }

        /**
         * A {@link Runner} of 9 parameter
         * @param <WRAPPER> type of the object to be returned from the function
         * @param <R>       the type of the result of the execution
         * @param <T0>      type of parameter1
         * @param <T1>      type of parameter2
         * @param <T2>      type of parameter3
         * @param <T3>      type of parameter4
         * @param <T4>      type of parameter5
         * @param <T5>      type of parameter6
         * @param <T6>      type of parameter7
         * @param <T7>      type of parameter8
         * @param <T8>      type of parameter9
         */
        public static class Runner9<WRAPPER, R, T0, T1, T2, T3, T4, T5, T6, T7, T8>
                extends Runner<Function.Function9<WRAPPER, T0, T1, T2, T3, T4, T5, T6, T7, T8>, WRAPPER, R> {

            private Runner9(RedFuture[] preconditions, Converter<WRAPPER, R> converter) {
                super(preconditions, converter);
            }

            @Override
            protected WRAPPER call(Function.Function9<WRAPPER, T0, T1, T2, T3, T4, T5, T6, T7, T8> f)
                    throws Throwable {
                return f.call(typedResult(0), typedResult(1), typedResult(2), typedResult(3), typedResult(4),
                        typedResult(5), typedResult(6), typedResult(7), typedResult(8));
            }

        }

        /**
         * A {@link Runner} of 10 parameter
         * @param <WRAPPER> type of the object to be returned from the function
         * @param <R>       the type of the result of the execution
         * @param <T0>      type of parameter1
         * @param <T1>      type of parameter2
         * @param <T2>      type of parameter3
         * @param <T3>      type of parameter4
         * @param <T4>      type of parameter5
         * @param <T5>      type of parameter6
         * @param <T6>      type of parameter7
         * @param <T7>      type of parameter8
         * @param <T8>      type of parameter9
         * @param <T9>      type of parameter10
         */
        public static class Runner10<WRAPPER, R, T0, T1, T2, T3, T4, T5, T6, T7, T8, T9>
                extends Runner<Function.Function10<WRAPPER, T0, T1, T2, T3, T4, T5, T6, T7, T8, T9>, WRAPPER, R> {

            private Runner10(RedFuture[] preconditions, Converter<WRAPPER, R> converter) {
                super(preconditions, converter);
            }

            @Override
            protected WRAPPER call(Function.Function10<WRAPPER, T0, T1, T2, T3, T4, T5, T6, T7, T8, T9> f)
                    throws Throwable {
                return f.call(typedResult(0), typedResult(1), typedResult(2), typedResult(3), typedResult(4),
                        typedResult(5), typedResult(6), typedResult(7), typedResult(8), typedResult(9));
            }

        }

        /**
         * A {@link Runner} of N parameter
         * @param <WRAPPER> type of the object to be returned from the function
         * @param <R>       the type of the result of the execution
         */
        public static class RunnerN<WRAPPER, R> extends Runner<Function.FunctionN<WRAPPER>, WRAPPER, R> {

            private RunnerN(RedFuture[] preconditions, Converter<WRAPPER, R> converter) {
                super(preconditions, converter);
            }

            @Override
            protected WRAPPER call(Function.FunctionN<WRAPPER> f) throws Throwable {
                return f.call(new Results(this));
            }
        }

    }

    // Invoke-ables

    /**
     * A function to be invoked with expected number of parameters,
     * and return the result wrapper
     */
    protected interface Function {

        /**
         * A function of 0 result parameters
         * @param <WRAPPER> type of the object to be returned from the function
         */
        interface Function0<WRAPPER> extends Function {

            /**
             * A function of 0 result parameters
             * @return the wrapper of the result
             * @throws Throwable to enable throwable catching
             */
            WRAPPER call() throws Throwable;

        }

        /**
         * A function of 1 result parameter
         * @param <WRAPPER> type of the object to be returned from the function
         * @param <T0>      type of parameter1
         */
        interface Function1<WRAPPER, T0> extends Function {

            /**
             * A function of 1 result parameter
             * @param f0 result of precondition 1
             * @return the wrapper of the result
             * @throws Throwable to enable throwable catching
             */
            WRAPPER call(T0 f0) throws Throwable;

        }

        /**
         * A function of 2 result parameters
         * @param <WRAPPER> type of the object to be returned from the function
         * @param <T0>      type of parameter1
         * @param <T1>      type of parameter2
         */
        interface Function2<WRAPPER, T0, T1> extends Function {

            /**
             * A function of 2 result parameters
             * @param f0 result of precondition 1
             * @param f1 result of precondition 2
             * @return the wrapper of the result
             * @throws Throwable to enable throwable catching
             */
            WRAPPER call(T0 f0, T1 f1) throws Throwable;

        }

        /**
         * A function of 3 result parameters
         * @param <WRAPPER> type of the object to be returned from the function
         * @param <T0>      type of parameter1
         * @param <T1>      type of parameter2
         * @param <T2>      type of parameter2
         */
        interface Function3<WRAPPER, T0, T1, T2> extends Function {

            /**
             * A function of 3 result parameters
             * @param f0 result of precondition 1
             * @param f1 result of precondition 2
             * @param f2 result of precondition 3
             * @return the wrapper of the result
             * @throws Throwable to enable throwable catching
             */
            WRAPPER call(T0 f0, T1 f1, T2 f2) throws Throwable;

        }

        /**
         * A function of 4 result parameters
         * @param <WRAPPER> type of the object to be returned from the function
         * @param <T0>      type of parameter1
         * @param <T1>      type of parameter2
         * @param <T2>      type of parameter2
         * @param <T3>      type of parameter3
         */
        interface Function4<WRAPPER, T0, T1, T2, T3> extends Function {

            /**
             * A function of 4 result parameters
             * @param f0 result of precondition 1
             * @param f1 result of precondition 2
             * @param f2 result of precondition 3
             * @param f3 result of precondition 4
             * @return the wrapper of the result
             * @throws Throwable to enable throwable catching
             */
            WRAPPER call(T0 f0, T1 f1, T2 f2, T3 f3) throws Throwable;

        }

        /**
         * A function of 5 result parameters
         * @param <WRAPPER> type of the object to be returned from the function
         * @param <T0>      type of parameter1
         * @param <T1>      type of parameter2
         * @param <T2>      type of parameter2
         * @param <T3>      type of parameter3
         * @param <T4>      type of parameter4
         */
        interface Function5<WRAPPER, T0, T1, T2, T3, T4> extends Function {

            /**
             * A function of 5 result parameters
             * @param f0 result of precondition 1
             * @param f1 result of precondition 2
             * @param f2 result of precondition 3
             * @param f3 result of precondition 4
             * @param f4 result of precondition 5
             * @return the wrapper of the result
             * @throws Throwable to enable throwable catching
             */
            WRAPPER call(T0 f0, T1 f1, T2 f2, T3 f3, T4 f4) throws Throwable;

        }

        /**
         * A function of 6 result parameters
         * @param <WRAPPER> type of the object to be returned from the function
         * @param <T0>      type of parameter1
         * @param <T1>      type of parameter2
         * @param <T2>      type of parameter2
         * @param <T3>      type of parameter3
         * @param <T4>      type of parameter4
         * @param <T5>      type of parameter5
         */
        interface Function6<WRAPPER, T0, T1, T2, T3, T4, T5> extends Function {

            /**
             * A function of 6 result parameters
             * @param f0 result of precondition 1
             * @param f1 result of precondition 2
             * @param f2 result of precondition 3
             * @param f3 result of precondition 4
             * @param f4 result of precondition 5
             * @param f5 result of precondition 6
             * @return the wrapper of the result
             * @throws Throwable to enable throwable catching
             */
            WRAPPER call(T0 f0, T1 f1, T2 f2, T3 f3, T4 f4, T5 f5) throws Throwable;

        }

        /**
         * A function of 7 result parameters
         * @param <WRAPPER> type of the object to be returned from the function
         * @param <T0>      type of parameter1
         * @param <T1>      type of parameter2
         * @param <T2>      type of parameter2
         * @param <T3>      type of parameter3
         * @param <T4>      type of parameter4
         * @param <T5>      type of parameter5
         * @param <T6>      type of parameter6
         */
        interface Function7<WRAPPER, T0, T1, T2, T3, T4, T5, T6> extends Function {

            /**
             * A function of 7 result parameters
             * @param f0 result of precondition 1
             * @param f1 result of precondition 2
             * @param f2 result of precondition 3
             * @param f3 result of precondition 4
             * @param f4 result of precondition 5
             * @param f5 result of precondition 6
             * @param f6 result of precondition 7
             * @return the wrapper of the result
             * @throws Throwable to enable throwable catching
             */
            WRAPPER call(T0 f0, T1 f1, T2 f2, T3 f3, T4 f4, T5 f5, T6 f6) throws Throwable;

        }

        /**
         * A function of 8 result parameters
         * @param <WRAPPER> type of the object to be returned from the function
         * @param <T0>      type of parameter1
         * @param <T1>      type of parameter2
         * @param <T2>      type of parameter2
         * @param <T3>      type of parameter3
         * @param <T4>      type of parameter4
         * @param <T5>      type of parameter5
         * @param <T6>      type of parameter6
         * @param <T7>      type of parameter7
         */
        interface Function8<WRAPPER, T0, T1, T2, T3, T4, T5, T6, T7> extends Function {

            /**
             * A function of 8 result parameters
             * @param f0 result of precondition 1
             * @param f1 result of precondition 2
             * @param f2 result of precondition 3
             * @param f3 result of precondition 4
             * @param f4 result of precondition 5
             * @param f5 result of precondition 6
             * @param f6 result of precondition 7
             * @param f7 result of precondition 8
             * @return the wrapper of the result
             * @throws Throwable to enable throwable catching
             */
            WRAPPER call(T0 f0, T1 f1, T2 f2, T3 f3, T4 f4, T5 f5, T6 f6, T7 f7) throws Throwable;

        }

        /**
         * A function of 9 result parameters
         * @param <WRAPPER> type of the object to be returned from the function
         * @param <T0>      type of parameter1
         * @param <T1>      type of parameter2
         * @param <T2>      type of parameter2
         * @param <T3>      type of parameter3
         * @param <T4>      type of parameter4
         * @param <T5>      type of parameter5
         * @param <T6>      type of parameter6
         * @param <T7>      type of parameter7
         * @param <T8>      type of parameter8
         */
        interface Function9<WRAPPER, T0, T1, T2, T3, T4, T5, T6, T7, T8> extends Function {

            /**
             * A function of 9 result parameters
             * @param f0 result of precondition 1
             * @param f1 result of precondition 2
             * @param f2 result of precondition 3
             * @param f3 result of precondition 4
             * @param f4 result of precondition 5
             * @param f5 result of precondition 6
             * @param f6 result of precondition 7
             * @param f7 result of precondition 8
             * @param f8 result of precondition 9
             * @return the wrapper of the result
             * @throws Throwable to enable throwable catching
             */
            WRAPPER call(T0 f0, T1 f1, T2 f2, T3 f3, T4 f4, T5 f5, T6 f6, T7 f7, T8 f8) throws Throwable;

        }

        /**
         * A function of 10 result parameters
         * @param <WRAPPER> type of the object to be returned from the function
         * @param <T0>      type of parameter1
         * @param <T1>      type of parameter2
         * @param <T2>      type of parameter2
         * @param <T3>      type of parameter3
         * @param <T4>      type of parameter4
         * @param <T5>      type of parameter5
         * @param <T6>      type of parameter6
         * @param <T7>      type of parameter7
         * @param <T8>      type of parameter8
         * @param <T9>      type of parameter9
         */
        interface Function10<WRAPPER, T0, T1, T2, T3, T4, T5, T6, T7, T8, T9> extends Function {

            /**
             * A function of 10 result parameters
             * @param f0 result of precondition 1
             * @param f1 result of precondition 2
             * @param f2 result of precondition 3
             * @param f3 result of precondition 4
             * @param f4 result of precondition 5
             * @param f5 result of precondition 6
             * @param f6 result of precondition 7
             * @param f7 result of precondition 8
             * @param f8 result of precondition 9
             * @param f9 result of precondition 10
             * @return the wrapper of the result
             * @throws Throwable to enable throwable catching
             */
            WRAPPER call(T0 f0, T1 f1, T2 f2, T3 f3, T4 f4, T5 f5, T6 f6, T7 f7, T8 f8, T9 f9) throws Throwable;

        }

        /**
         * A function of N result parameters
         * @param <WRAPPER> type of the object to be returned from the function
         */
        interface FunctionN<WRAPPER> extends Function {

            /**
             * A function of N result parameters
             * @param results an object containing the index based results of the preconditions
             * @return the wrapper of the result
             * @throws Throwable to enable throwable catching
             */
            WRAPPER call(Results results) throws Throwable;

        }

    }

    /**
     * A function to be invoked with expected number of parameters,
     * and a {@link PendingMarker} to mark the completion of the execution
     */
    protected interface Command {

        /**
         * A command of 0 result parameters
         */
        interface Command0 extends Command {

            /**
             * A command of 0 result parameters
             * @param pendingMarker to be used with either {@link PendingMarker#complete()}
             *                      or {@link PendingMarker#fail(Throwable)} to mark async
             *                      execution as completed.
             * @throws Throwable to enable throwable catching
             */
            void call(PendingMarker pendingMarker) throws Throwable;

        }

        /**
         * A command of 1 result parameter
         * @param <T0> type of parameter1
         */
        interface Command1<T0> extends Command {

            /**
             * A command of 1 result parameter
             * @param pendingMarker to be used with either {@link PendingMarker#complete()}
             *                      or {@link PendingMarker#fail(Throwable)} to mark async
             *                      execution as completed.
             * @param f0            result of precondition 1
             * @throws Throwable to enable throwable catching
             */
            void call(PendingMarker pendingMarker, T0 f0) throws Throwable;

        }

        /**
         * A command of 2 result parameter
         * @param <T0> type of parameter1
         * @param <T1> type of parameter2
         */
        interface Command2<T0, T1> extends Command {

            /**
             * A command of 2 result parameters
             * @param pendingMarker to be used with either {@link PendingMarker#complete()}
             *                      or {@link PendingMarker#fail(Throwable)} to mark async
             *                      execution as completed.
             * @param f0            result of precondition 1
             * @param f1            result of precondition 2
             * @throws Throwable to enable throwable catching
             */
            void call(PendingMarker pendingMarker, T0 f0, T1 f1) throws Throwable;

        }

        /**
         * A command of 3 result parameter
         * @param <T0> type of parameter1
         * @param <T1> type of parameter2
         * @param <T2> type of parameter3
         */
        interface Command3<T0, T1, T2> extends Command {

            /**
             * A command of 3 result parameters
             * @param pendingMarker to be used with either {@link PendingMarker#complete()}
             *                      or {@link PendingMarker#fail(Throwable)} to mark async
             *                      execution as completed.
             * @param f0            result of precondition 1
             * @param f1            result of precondition 2
             * @param f2            result of precondition 3
             * @throws Throwable to enable throwable catching
             */
            void call(PendingMarker pendingMarker, T0 f0, T1 f1, T2 f2) throws Throwable;

        }

        /**
         * A command of 4 result parameter
         * @param <T0> type of parameter1
         * @param <T1> type of parameter2
         * @param <T2> type of parameter3
         * @param <T3> type of parameter4
         */
        interface Command4<T0, T1, T2, T3> extends Command {

            /**
             * A command of 4 result parameters
             * @param pendingMarker to be used with either {@link PendingMarker#complete()}
             *                      or {@link PendingMarker#fail(Throwable)} to mark async
             *                      execution as completed.
             * @param f0            result of precondition 1
             * @param f1            result of precondition 2
             * @param f2            result of precondition 3
             * @param f3            result of precondition 4
             * @throws Throwable to enable throwable catching
             */
            void call(PendingMarker pendingMarker, T0 f0, T1 f1, T2 f2, T3 f3) throws Throwable;

        }

        /**
         * A command of 5 result parameter
         * @param <T0> type of parameter1
         * @param <T1> type of parameter2
         * @param <T2> type of parameter3
         * @param <T3> type of parameter4
         * @param <T4> type of parameter5
         */
        interface Command5<T0, T1, T2, T3, T4> extends Command {

            /**
             * A command of 5 result parameters
             * @param pendingMarker to be used with either {@link PendingMarker#complete()}
             *                      or {@link PendingMarker#fail(Throwable)} to mark async
             *                      execution as completed.
             * @param f0            result of precondition 1
             * @param f1            result of precondition 2
             * @param f2            result of precondition 3
             * @param f3            result of precondition 4
             * @param f4            result of precondition 5
             * @throws Throwable to enable throwable catching
             */
            void call(PendingMarker pendingMarker, T0 f0, T1 f1, T2 f2, T3 f3, T4 f4) throws Throwable;

        }

        /**
         * A command of 6 result parameter
         * @param <T0> type of parameter1
         * @param <T1> type of parameter2
         * @param <T2> type of parameter3
         * @param <T3> type of parameter4
         * @param <T4> type of parameter5
         * @param <T5> type of parameter6
         */
        interface Command6<T0, T1, T2, T3, T4, T5> extends Command {

            /**
             * A command of 6 result parameters
             * @param pendingMarker to be used with either {@link PendingMarker#complete()}
             *                      or {@link PendingMarker#fail(Throwable)} to mark async
             *                      execution as completed.
             * @param f0            result of precondition 1
             * @param f1            result of precondition 2
             * @param f2            result of precondition 3
             * @param f3            result of precondition 4
             * @param f4            result of precondition 5
             * @param f5            result of precondition 6
             * @throws Throwable to enable throwable catching
             */
            void call(PendingMarker pendingMarker, T0 f0, T1 f1, T2 f2, T3 f3, T4 f4, T5 f5) throws Throwable;

        }

        /**
         * A command of 7 result parameter
         * @param <T0> type of parameter1
         * @param <T1> type of parameter2
         * @param <T2> type of parameter3
         * @param <T3> type of parameter4
         * @param <T4> type of parameter5
         * @param <T5> type of parameter6
         * @param <T6> type of parameter7
         */
        interface Command7<T0, T1, T2, T3, T4, T5, T6> extends Command {

            /**
             * A command of 7 result parameters
             * @param pendingMarker to be used with either {@link PendingMarker#complete()}
             *                      or {@link PendingMarker#fail(Throwable)} to mark async
             *                      execution as completed.
             * @param f0            result of precondition 1
             * @param f1            result of precondition 2
             * @param f2            result of precondition 3
             * @param f3            result of precondition 4
             * @param f4            result of precondition 5
             * @param f5            result of precondition 6
             * @param f6            result of precondition 7
             * @throws Throwable to enable throwable catching
             */
            void call(PendingMarker pendingMarker, T0 f0, T1 f1, T2 f2, T3 f3, T4 f4, T5 f5, T6 f6) throws Throwable;

        }

        /**
         * A command of 8 result parameter
         * @param <T0> type of parameter1
         * @param <T1> type of parameter2
         * @param <T2> type of parameter3
         * @param <T3> type of parameter4
         * @param <T4> type of parameter5
         * @param <T5> type of parameter6
         * @param <T6> type of parameter7
         * @param <T7> type of parameter8
         */
        interface Command8<T0, T1, T2, T3, T4, T5, T6, T7> extends Command {

            /**
             * A command of 8 result parameters
             * @param pendingMarker to be used with either {@link PendingMarker#complete()}
             *                      or {@link PendingMarker#fail(Throwable)} to mark async
             *                      execution as completed.
             * @param f0            result of precondition 1
             * @param f1            result of precondition 2
             * @param f2            result of precondition 3
             * @param f3            result of precondition 4
             * @param f4            result of precondition 5
             * @param f5            result of precondition 6
             * @param f6            result of precondition 7
             * @param f7            result of precondition 8
             * @throws Throwable to enable throwable catching
             */
            void call(PendingMarker pendingMarker, T0 f0, T1 f1, T2 f2, T3 f3, T4 f4, T5 f5, T6 f6, T7 f7)
                    throws Throwable;

        }

        /**
         * A command of 9 result parameter
         * @param <T0> type of parameter1
         * @param <T1> type of parameter2
         * @param <T2> type of parameter3
         * @param <T3> type of parameter4
         * @param <T4> type of parameter5
         * @param <T5> type of parameter6
         * @param <T6> type of parameter7
         * @param <T7> type of parameter8
         * @param <T8> type of parameter9
         */
        interface Command9<T0, T1, T2, T3, T4, T5, T6, T7, T8> extends Command {

            /**
             * A command of 9 result parameters
             * @param pendingMarker to be used with either {@link PendingMarker#complete()}
             *                      or {@link PendingMarker#fail(Throwable)} to mark async
             *                      execution as completed.
             * @param f0            result of precondition 1
             * @param f1            result of precondition 2
             * @param f2            result of precondition 3
             * @param f3            result of precondition 4
             * @param f4            result of precondition 5
             * @param f5            result of precondition 6
             * @param f6            result of precondition 7
             * @param f7            result of precondition 8
             * @param f8            result of precondition 9
             * @throws Throwable to enable throwable catching
             */
            void call(PendingMarker pendingMarker, T0 f0, T1 f1, T2 f2, T3 f3, T4 f4, T5 f5, T6 f6, T7 f7, T8 f8)
                    throws Throwable;

        }

        /**
         * A command of 10 result parameter
         * @param <T0> type of parameter1
         * @param <T1> type of parameter2
         * @param <T2> type of parameter3
         * @param <T3> type of parameter4
         * @param <T4> type of parameter5
         * @param <T5> type of parameter6
         * @param <T6> type of parameter7
         * @param <T7> type of parameter8
         * @param <T8> type of parameter9
         * @param <T9> type of parameter10
         */
        interface Command10<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9> extends Command {

            /**
             * A command of 10 result parameters
             * @param pendingMarker to be used with either {@link PendingMarker#complete()}
             *                      or {@link PendingMarker#fail(Throwable)} to mark async
             *                      execution as completed.
             * @param f0            result of precondition 1
             * @param f1            result of precondition 2
             * @param f2            result of precondition 3
             * @param f3            result of precondition 4
             * @param f4            result of precondition 5
             * @param f5            result of precondition 6
             * @param f6            result of precondition 7
             * @param f7            result of precondition 8
             * @param f8            result of precondition 9
             * @param f9            result of precondition 10
             * @throws Throwable to enable throwable catching
             */
            void call(PendingMarker pendingMarker, T0 f0, T1 f1, T2 f2, T3 f3, T4 f4, T5 f5, T6 f6, T7 f7, T8 f8, T9 f9)
                    throws Throwable;

        }

        /**
         * A command of N result parameter
         */
        interface CommandN extends Command {

            /**
             * A command of N result parameters
             * @param pendingMarker to be used with either {@link PendingMarker#complete()}
             *                      or {@link PendingMarker#fail(Throwable)} to mark async
             *                      execution as completed.
             * @param results       an object containing the index based results of the preconditions
             * @throws Throwable to enable throwable catching
             */
            void call(PendingMarker pendingMarker, Results results) throws Throwable;

        }

    }

    // Results classes

    /**
     * Class representing the result of an async execution, when completed
     * producing either a value of <T> or a {@link Throwable}
     * @param <T> type of the produced value
     */
    protected static class Result<T> {

        // Fields

        /**
         * The underlying {@link RedFutureOf}
         */
        final OpenRedFutureOf<T> _future;

        // Constructors

        private Result() {
            _future = RedFuture.futureOf();
        }

    }

    /**
     * Class Representing the void-result of an async execution, which can either
     * successfully complete, or fail with a {@link Throwable}
     */
    protected static class Marker {

        // Fields

        /**
         * The underlying {@link RedFuture}
         */
        final OpenRedFuture _future;

        // Constructors

        private Marker() {
            _future = RedFuture.future();
        }

    }

    /**
     * Class representing a {@link Marker} pending the execution completion,
     * and can be invoked to mark either success or failure completions
     */
    protected static class PendingMarker {

        // Fields

        /**
         * The Underlying marker to manipulate
         */
        private final Marker _marker;

        // Constructors

        private PendingMarker(Marker marker) {
            _marker = marker;
        }

        // Public

        /**
         * Marks the execution as successfully completed
         */
        public void complete() {
            _marker._future.resolve();
        }

        /**
         * Marks the execution as failed with given cause
         * @param t cause of failure
         */
        public void fail(Throwable t) {
            _marker._future.fail(t);
        }

    }

    /**
     * Class representing the results of N different preconditions
     * It can be used to retrieve values of index based precondition results
     */
    protected static class Results {

        /**
         * To hold the array of preconditions
         */
        private final Middleware _middleware;

        // Constructors

        private Results(Middleware middleware) {
            _middleware = middleware;
        }

        // Public

        /**
         * Returns the matching precondition result.
         * The index is zero-based (<a>https://en.wikipedia.org/wiki/Zero-based_numbering</a>).
         * @param index  the zero-based index of the precondition to return the value of
         * @param tClass the type of the result
         * @param <T>    the type of the result
         * @return the result of the matching precondition
         * @throws IllegalArgumentException in case the given class does not fit the type
         * of the matching precondition, or the given index is out of bounds
         */
        public <T> T result(int index, Class<T> tClass) {
            Object result = _middleware.objectResult(index);
            if (result == null) {
                return null;
            }
            if (!tClass.isInstance(result)) {
                throw new IllegalArgumentException("result " + index + " is not of type " + tClass.getName());
            }
            return tClass.cast(result);
        }

    }

    // Static

    /**
     * An interface for simple conversion of the different wrapper options
     * (direct value, {@link Future}, {@link ListenableFuture} or {@link RedFutureOf})
     * to {@link RedFutureOf}
     * @param <WRAPPER> the wrapper option
     * @param <R>       the type of the result
     */
    private interface Converter<WRAPPER, R> {

        /**
         * Convert the given wrapper to a {@link RedFutureOf} of R
         * @param wrapper the wrapper to convert
         * @return a matching {@link RedFutureOf}
         */
        RedFutureOf<R> convert(WRAPPER wrapper);

        // Static

        /**
         * Returns a converter which receives a value and returns a resolved
         * future of the value
         *
         * @param <R> type of the converter value
         * @return a converter converting direct values
         */
        static <R> Converter<R, R> value() {
            return RedFuture::resolvedOf;
        }

        /**
         * Returns a converter which receives a {@link Future} of value and returns a
         * {@link RedFuture} of the value
         *
         * @param <R> type of the converter value
         * @return a converter converting {@link Future} values
         */
        static <R> Converter<Future<R>, R> future() {
            return RedFuture::convert;
        }

        /**
         * Returns a converter which receives a {@link ListenableFuture} of value and returns a
         * {@link RedFuture} of the value
         *
         * @param <R> type of the converter value
         * @return a converter converting {@link ListenableFuture} values
         */
        static <R> Converter<ListenableFuture<R>, R> listenableFuture() {
            return RedFuture::convert;
        }

        /**
         * Returns a converter which receives a {@link RedFutureOf} of value and returns a
         * {@link RedFuture} of the value
         *
         * @param <R> type of the converter value
         * @return a converter converting {@link RedFutureOf} values
         */
        static <R> Converter<RedFutureOf<R>, R> redFuture() {
            return r -> r;
        }

    }

    /**
     * Holds an array of precondition Futures.
     * Provides an interface to extract values from the precondition Futures.
     */
    abstract private static class Middleware {

        // Private

        /**
         * An array of precondition Futures of the construction chain
         */
        private final RedFuture[] _preconditions;

        // Constructors

        private Middleware(RedFuture[] preconditions) {
            _preconditions = preconditions;
        }

        // Private

        /**
         * @return the array of preconditions
         */
        RedFuture[] preconditions() {
            return _preconditions;
        }

        /**
         * @param index of requested precondition result
         * @return the resulted object of the future of the precondition in the given index
         * @throws IllegalArgumentException in case given index contains no result
         */
        Object objectResult(int index) {
            if (index < 0 || index >= _preconditions.length) {
                throw new IllegalArgumentException("no result at index " + index);
            }
            RedFuture future = _preconditions[index];
            if (!(future instanceof RedFutureOf)) {
                throw new IllegalArgumentException("no result at index " + index);
            }
            return ((RedFutureOf) future).tryGet();
        }

        /**
         * @param index of requested precondition result
         * @param <T>   type of the result
         * @return the resulted object of the future of the precondition in the given index
         * @throws IllegalArgumentException in case given index contains no result, or the type is wrong
         */
        <T> T typedResult(int index) {
            Object result = objectResult(index);
            try {
                //noinspection unchecked
                return result == null ? null : (T) result;
            } catch (Exception e) {
                throw new IllegalArgumentException("result at index " + index + " is not of expected type");
            }
        }

    }

}
