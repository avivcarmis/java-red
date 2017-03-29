package com.javared;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.javared.executor.BaseRedSynchronizer;
import com.javared.executor.RedSynchronizer;
import com.javared.executor.RedVoidSynchronizer;
import com.javared.future.OpenRedFutureOf;
import com.javared.future.RedFuture;
import com.javared.future.RedFutureOf;
import com.javared.test.RedTestContext;
import com.javared.test.RedTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by avivc on 3/27/2017.
 */
@RunWith(RedTestRunner.class)
public class TestRedSynchronizer {

    private static final long FUTURE_DELAY = 100;

    /**
     * Test synchronization of execution with:
     * -no result dependencies
     * -no markers
     * -function result
     */
    @Test
    public void noDependenciesTest1(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runFunctionTest(redTestContext, TestSynchronizers.NoDependenciesTest1Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -no result dependencies
     * -one layer of markers with success status
     * -function result
     */
    @Test
    public void noDependenciesTest2(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runFunctionTest(redTestContext, TestSynchronizers.NoDependenciesTest2Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -no result dependencies
     * -one layer of markers with fail status
     * -function result
     */
    @Test
    public void noDependenciesTest3(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runFunctionTest(redTestContext, TestSynchronizers.NoDependenciesTest3Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -no result dependencies
     * -one layer of markers with finish status
     * -function result
     */
    @Test
    public void noDependenciesTest4(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runFunctionTest(redTestContext, TestSynchronizers.NoDependenciesTest4Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -no result dependencies
     * -one layer of markers with success status
     * -another layer of markers with finish status
     * -function result
     */
    @Test
    public void noDependenciesTest5(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runFunctionTest(redTestContext, TestSynchronizers.NoDependenciesTest5Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -no result dependencies
     * -one layer of markers with fail status
     * -another layer of markers with success status
     * -function result
     */
    @Test
    public void noDependenciesTest6(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runFunctionTest(redTestContext, TestSynchronizers.NoDependenciesTest6Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -no result dependencies
     * -one layer of markers with finish status
     * -another layer of markers with fail status
     * -function result
     */
    @Test
    public void noDependenciesTest7(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runFunctionTest(redTestContext, TestSynchronizers.NoDependenciesTest7Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -no result dependencies
     * -no markers
     * -command result
     */
    @Test
    public void noDependenciesTest8(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runCommandTest(redTestContext, TestSynchronizers.NoDependenciesTest8Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -no result dependencies
     * -one layer of markers with success status
     * -command result
     */
    @Test
    public void noDependenciesTest9(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runCommandTest(redTestContext, TestSynchronizers.NoDependenciesTest9Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -no result dependencies
     * -one layer of markers with fail status
     * -command result
     */
    @Test
    public void noDependenciesTest10(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runCommandTest(redTestContext, TestSynchronizers.NoDependenciesTest10Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -no result dependencies
     * -one layer of markers with finish status
     * -command result
     */
    @Test
    public void noDependenciesTest11(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runCommandTest(redTestContext, TestSynchronizers.NoDependenciesTest11Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -no result dependencies
     * -one layer of markers with success status
     * -another layer of markers with finish status
     * -command result
     */
    @Test
    public void noDependenciesTest12(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runCommandTest(redTestContext, TestSynchronizers.NoDependenciesTest12Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -no result dependencies
     * -one layer of markers with fail status
     * -another layer of markers with success status
     * -command result
     */
    @Test
    public void noDependenciesTest13(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runCommandTest(redTestContext, TestSynchronizers.NoDependenciesTest13Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -no result dependencies
     * -one layer of markers with finish status
     * -another layer of markers with fail status
     * -command result
     */
    @Test
    public void noDependenciesTest14(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runCommandTest(redTestContext, TestSynchronizers.NoDependenciesTest14Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -1 result dependency with success status
     * -no markers
     * -function result
     */
    @Test
    public void explicitDependenciesTest1(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runFunctionTest(redTestContext, TestSynchronizers.ExplicitDependenciesTest1Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -2 result dependencies with failure status
     * -no markers
     * -function result
     */
    @Test
    public void explicitDependenciesTest2(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runFunctionTest(redTestContext, TestSynchronizers.ExplicitDependenciesTest2Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -3 result dependencies with finish status
     * -no markers
     * -function result
     */
    @Test
    public void explicitDependenciesTest3(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runFunctionTest(redTestContext, TestSynchronizers.ExplicitDependenciesTest3Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -4 result dependencies with success status
     * -no markers
     * -function result
     */
    @Test
    public void explicitDependenciesTest4(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runFunctionTest(redTestContext, TestSynchronizers.ExplicitDependenciesTest4Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -5 result dependencies with failure status
     * -no markers
     * -function result
     */
    @Test
    public void explicitDependenciesTest5(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runFunctionTest(redTestContext, TestSynchronizers.ExplicitDependenciesTest5Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -6 result dependencies with finish status
     * -no markers
     * -function result
     */
    @Test
    public void explicitDependenciesTest6(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runFunctionTest(redTestContext, TestSynchronizers.ExplicitDependenciesTest6Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -7 result dependencies with success status
     * -no markers
     * -function result
     */
    @Test
    public void explicitDependenciesTest7(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runFunctionTest(redTestContext, TestSynchronizers.ExplicitDependenciesTest7Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -8 result dependencies with failure status
     * -no markers
     * -function result
     */
    @Test
    public void explicitDependenciesTest8(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runFunctionTest(redTestContext, TestSynchronizers.ExplicitDependenciesTest8Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -9 result dependencies with finish status
     * -no markers
     * -function result
     */
    @Test
    public void explicitDependenciesTest9(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runFunctionTest(redTestContext, TestSynchronizers.ExplicitDependenciesTest9Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -10 result dependencies with success status
     * -no markers
     * -function result
     */
    @Test
    public void explicitDependenciesTest9(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runFunctionTest(redTestContext, TestSynchronizers.ExplicitDependenciesTest9Synchronizer.class);
    }

    // Utils

    private static <T> void runFunctionTest(RedTestContext redTestContext,
                                            Class<? extends RedSynchronizer<RedTestContext, T>> syncClass)
            throws IllegalAccessException, InstantiationException {
        RedTestContext.Fork fork = redTestContext.fork();
        RedSynchronizer<RedTestContext, T> synchronizer = syncClass.newInstance();
        synchronizer.execute(redTestContext).addFailureCallback(redTestContext::fail).addSuccessCallback(result -> {
            if (checkBooleanSuccess(result) || checkStringSuccess(result) || checkTestObjectSuccess(result)) {
                fork.complete();
            }
            else {
                fork.fail("unexpected result");
            }
        });
    }

    private static void runCommandTest(RedTestContext redTestContext,
                                       Class<? extends RedVoidSynchronizer<CommandTestContext>> syncClass)
            throws IllegalAccessException, InstantiationException {
        RedTestContext.Fork fork = redTestContext.fork();
        RedVoidSynchronizer<CommandTestContext> synchronizer = syncClass.newInstance();
        CommandTestContext commandTestContext = new CommandTestContext(redTestContext);
        synchronizer.execute(commandTestContext).addFailureCallback(redTestContext::fail).addSuccessCallback(() -> {
            if (commandTestContext.finished.get()) {
                fork.complete();
            }
            else {
                fork.fail("not finished executing");
            }
        });
    }

    private static Boolean booleanSuccess() {
        return true;
    }

    private static String stringSuccess() {
        return "success";
    }

    private static TestObject testObjectSuccess() {
        return TestObject.INSTANCE;
    }

    private static boolean checkBooleanSuccess(Object result) {
        return result != null && result instanceof Boolean && result == booleanSuccess();
    }

    private static boolean checkStringSuccess(Object result) {
        return result != null && result instanceof String && result.equals(stringSuccess());
    }

    private static boolean checkTestObjectSuccess(Object result) {
        return result != null && result instanceof TestObject && result == testObjectSuccess();
    }

    private static <T> Future<T> futureOf(T value, RedTestContext redTestContext) {
        CompletableFuture<T> future = new CompletableFuture<>();
        redTestContext.scheduleTask(FUTURE_DELAY, () -> future.complete(value));
        return future;
    }

    private static <T> Future<T> futureOf(@SuppressWarnings("unused") Class<T> tClass,
                                          Throwable t, RedTestContext redTestContext) {
        CompletableFuture<T> future = new CompletableFuture<>();
        redTestContext.scheduleTask(FUTURE_DELAY, () -> future.completeExceptionally(t));
        return future;
    }

    private static <T> ListenableFuture<T> listenableFutureOf(T value, RedTestContext redTestContext) {
        SettableFuture<T> future = SettableFuture.create();
        redTestContext.scheduleTask(FUTURE_DELAY, () -> future.set(value));
        return future;
    }

    private static <T> ListenableFuture<T> listenableFutureOf(@SuppressWarnings("unused") Class<T> tClass,
                                                              Throwable t, RedTestContext redTestContext) {
        SettableFuture<T> future = SettableFuture.create();
        redTestContext.scheduleTask(FUTURE_DELAY, () -> future.setException(t));
        return future;
    }

    private static <T> RedFutureOf<T> redFutureOf(T value, RedTestContext redTestContext) {
        OpenRedFutureOf<T> future = RedFuture.futureOf();
        redTestContext.scheduleTask(FUTURE_DELAY, () -> future.resolve(value));
        return future;
    }

    private static <T> RedFutureOf<T> redFutureOf(@SuppressWarnings("unused") Class<T> tClass,
                                                  Throwable t, RedTestContext redTestContext) {
        OpenRedFutureOf<T> future = RedFuture.futureOf();
        redTestContext.scheduleTask(FUTURE_DELAY, () -> future.fail(t));
        return future;
    }

    private static class CommandTestContext {

        private final RedTestContext redTestContext;

        private final AtomicBoolean finished;

        private CommandTestContext(RedTestContext redTestContext) {
            this.redTestContext = redTestContext;
            this.finished = new AtomicBoolean();
        }

    }

    private static class TestObject {

        private static final TestObject INSTANCE = new TestObject();

    }

    private static class TestException extends Exception {

        private static final TestException INSTANCE = new TestException();

    }

    // Test synchronizer

    public static class TestSynchronizers {

        public static class NoDependenciesTest1Synchronizer extends RedSynchronizer<RedTestContext, Boolean> {

            @Override
            protected Result<Boolean> handle(RedTestContext redTestContext) {
                return produce(Boolean.class).byExecuting(TestRedSynchronizer::booleanSuccess);
            }

        }

        public static class NoDependenciesTest2Synchronizer extends RedSynchronizer<RedTestContext, String> {

            @Override
            protected Result<String> handle(RedTestContext context) {
                RedTestContext.TimingTester timingTester = context.timingTester();
                Marker marker1 = execute(pendingMarker -> context.scheduleTask(FUTURE_DELAY, pendingMarker::complete));
                Marker marker2 = execute(pendingMarker -> context.scheduleTask(FUTURE_DELAY, pendingMarker::complete));
                return onceMarkers(marker1, marker2).succeed().produceFutureOf(String.class).byExecuting(() -> {
                    timingTester.validatePassed(FUTURE_DELAY);
                    return futureOf(stringSuccess(), context);
                });
            }

        }

        public static class NoDependenciesTest3Synchronizer extends RedSynchronizer<RedTestContext, TestObject> {

            @Override
            protected Result<TestObject> handle(RedTestContext context) {
                RedTestContext.TimingTester timingTester = context.timingTester();
                Marker marker1 = execute(pendingMarker -> context.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                Marker marker2 = execute(pendingMarker -> context.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                return onceMarkers(marker1, marker2).fail().produceListenableFutureOf(TestObject.class).byExecuting(() -> {
                    timingTester.validatePassed(FUTURE_DELAY);
                    return listenableFutureOf(testObjectSuccess(), context);
                });
            }

        }

        public static class NoDependenciesTest4Synchronizer extends RedSynchronizer<RedTestContext, Boolean> {

            @Override
            protected Result<Boolean> handle(RedTestContext context) {
                RedTestContext.TimingTester timingTester = context.timingTester();
                Marker marker1 = execute(pendingMarker -> context.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                Marker marker2 = execute(pendingMarker -> context.scheduleTask(FUTURE_DELAY, pendingMarker::complete));
                return onceMarkers(marker1, marker2).finish().produceRedFutureOf(Boolean.class).byExecuting(() -> {
                    timingTester.validatePassed(FUTURE_DELAY);
                    return redFutureOf(booleanSuccess(), context);
                });
            }

        }

        public static class NoDependenciesTest5Synchronizer extends RedSynchronizer<RedTestContext, String> {

            @Override
            protected Result<String> handle(RedTestContext context) {
                RedTestContext.TimingTester timingTester = context.timingTester();
                Marker marker1 = execute(pendingMarker -> context.scheduleTask(FUTURE_DELAY, pendingMarker::complete));
                Marker marker2 = execute(pendingMarker -> context.scheduleTask(FUTURE_DELAY, pendingMarker::complete));
                return onceMarkers(marker1).succeed().andMarkers(marker2).finish().produce(String.class)
                        .byExecuting(() -> {
                            timingTester.validatePassed(FUTURE_DELAY);
                            return stringSuccess();
                        });
            }

        }

        public static class NoDependenciesTest6Synchronizer extends RedSynchronizer<RedTestContext, TestObject> {

            @Override
            protected Result<TestObject> handle(RedTestContext context) {
                RedTestContext.TimingTester timingTester = context.timingTester();
                Marker marker1 = execute(pendingMarker -> context.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                Marker marker2 = execute(pendingMarker -> context.scheduleTask(FUTURE_DELAY, pendingMarker::complete));
                return onceMarkers(marker1).fail().andMarkers(marker2).succeed().produceFutureOf(TestObject.class)
                        .byExecuting(() -> {
                            timingTester.validatePassed(FUTURE_DELAY);
                            return futureOf(testObjectSuccess(), context);
                        });
            }

        }

        public static class NoDependenciesTest7Synchronizer extends RedSynchronizer<RedTestContext, Boolean> {

            @Override
            protected Result<Boolean> handle(RedTestContext context) {
                RedTestContext.TimingTester timingTester = context.timingTester();
                Marker marker1 = execute(pendingMarker -> context.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                Marker marker2 = execute(pendingMarker -> context.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                return onceMarkers(marker1).finish().andMarkers(marker2).fail().produceListenableFutureOf(Boolean.class)
                        .byExecuting(() -> {
                            timingTester.validatePassed(FUTURE_DELAY);
                            return listenableFutureOf(booleanSuccess(), context);
                        });
            }

        }

        public static class NoDependenciesTest8Synchronizer extends RedVoidSynchronizer<CommandTestContext> {

            @Override
            protected Marker handle(CommandTestContext context) {
                return execute(pendingMarker -> context.redTestContext.scheduleTask(FUTURE_DELAY, () -> {
                    context.finished.set(true);
                    pendingMarker.complete();
                }));
            }

        }

        public static class NoDependenciesTest9Synchronizer extends RedVoidSynchronizer<CommandTestContext> {

            @Override
            protected Marker handle(CommandTestContext context) {
                RedTestContext.TimingTester timingTester = context.redTestContext.timingTester();
                Marker marker1 = execute(pendingMarker ->
                        context.redTestContext.scheduleTask(FUTURE_DELAY, pendingMarker::complete));
                Marker marker2 = execute(pendingMarker ->
                        context.redTestContext.scheduleTask(FUTURE_DELAY, pendingMarker::complete));
                return onceMarkers(marker1, marker2).succeed().execute(pendingMarker -> {
                    timingTester.validatePassed(FUTURE_DELAY);
                    context.finished.set(true);
                    pendingMarker.complete();
                });
            }

        }

        public static class NoDependenciesTest10Synchronizer extends RedVoidSynchronizer<CommandTestContext> {

            @Override
            protected Marker handle(CommandTestContext context) {
                RedTestContext.TimingTester timingTester = context.redTestContext.timingTester();
                Marker marker1 = execute(pendingMarker -> context.redTestContext.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                Marker marker2 = execute(pendingMarker -> context.redTestContext.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                return onceMarkers(marker1, marker2).fail().execute(pendingMarker -> {
                    timingTester.validatePassed(FUTURE_DELAY);
                    context.finished.set(true);
                    pendingMarker.complete();
                });
            }

        }

        public static class NoDependenciesTest11Synchronizer extends RedVoidSynchronizer<CommandTestContext> {

            @Override
            protected Marker handle(CommandTestContext context) {
                RedTestContext.TimingTester timingTester = context.redTestContext.timingTester();
                Marker marker1 = execute(pendingMarker -> context.redTestContext.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                Marker marker2 = execute(pendingMarker -> context.redTestContext.scheduleTask(FUTURE_DELAY, pendingMarker::complete));
                return onceMarkers(marker1, marker2).finish().execute(pendingMarker -> {
                    timingTester.validatePassed(FUTURE_DELAY);
                    context.finished.set(true);
                    pendingMarker.complete();
                });
            }

        }

        public static class NoDependenciesTest12Synchronizer extends RedVoidSynchronizer<CommandTestContext> {

            @Override
            protected Marker handle(CommandTestContext context) {
                RedTestContext.TimingTester timingTester = context.redTestContext.timingTester();
                Marker marker1 = execute(pendingMarker -> context.redTestContext.scheduleTask(FUTURE_DELAY, pendingMarker::complete));
                Marker marker2 = execute(pendingMarker -> context.redTestContext.scheduleTask(FUTURE_DELAY, pendingMarker::complete));
                return onceMarkers(marker1).succeed().andMarkers(marker2).finish().execute(pendingMarker -> {
                    timingTester.validatePassed(FUTURE_DELAY);
                    context.finished.set(true);
                    pendingMarker.complete();
                });
            }

        }

        public static class NoDependenciesTest13Synchronizer extends RedVoidSynchronizer<CommandTestContext> {

            @Override
            protected Marker handle(CommandTestContext context) {
                RedTestContext.TimingTester timingTester = context.redTestContext.timingTester();
                Marker marker1 = execute(pendingMarker -> context.redTestContext.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                Marker marker2 = execute(pendingMarker -> context.redTestContext.scheduleTask(FUTURE_DELAY, pendingMarker::complete));
                return onceMarkers(marker1).fail().andMarkers(marker2).succeed().execute(pendingMarker -> {
                    timingTester.validatePassed(FUTURE_DELAY);
                    context.finished.set(true);
                    pendingMarker.complete();
                });
            }

        }

        public static class NoDependenciesTest14Synchronizer extends RedVoidSynchronizer<CommandTestContext> {

            @Override
            protected Marker handle(CommandTestContext context) {
                RedTestContext.TimingTester timingTester = context.redTestContext.timingTester();
                Marker marker1 = execute(pendingMarker -> context.redTestContext.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                Marker marker2 = execute(pendingMarker -> context.redTestContext.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                return onceMarkers(marker1).finish().andMarkers(marker2).fail().execute(pendingMarker -> {
                    timingTester.validatePassed(FUTURE_DELAY);
                    context.finished.set(true);
                    pendingMarker.complete();
                });
            }

        }

        public static class ExplicitDependenciesTest1Synchronizer extends RedSynchronizer<RedTestContext, String> {

            @Override
            protected Result<String> handle(RedTestContext redTestContext) {
                Result<Boolean> booleanResult = produce(Boolean.class).byExecuting(TestRedSynchronizer::booleanSuccess);
                return onceResult(booleanResult).succeed().produce(String.class).byExecuting(f0 -> {
                    redTestContext.assertions.assertTrue(checkBooleanSuccess(f0));
                    return stringSuccess();
                });
            }

        }

        public static class ExplicitDependenciesTest2Synchronizer extends RedSynchronizer<RedTestContext, TestObject> {

            @Override
            protected Result<TestObject> handle(RedTestContext redTestContext) {
                Result<Boolean> result1 = produceFutureOf(Boolean.class).byExecuting(() ->
                        futureOf(Boolean.class, TestException.INSTANCE, redTestContext));
                Result<String> result2 = produceListenableFutureOf(String.class).byExecuting(() ->
                        listenableFutureOf(String.class, TestException.INSTANCE, redTestContext));
                return onceResults(result1, result2).fail().produceFutureOf(TestObject.class)
                        .byExecuting(() -> futureOf(testObjectSuccess(), redTestContext));
            }

        }

        public static class ExplicitDependenciesTest3Synchronizer extends RedSynchronizer<RedTestContext, Boolean> {

            @Override
            protected Result<Boolean> handle(RedTestContext redTestContext) {
                Result<Boolean> result1 = produceRedFutureOf(Boolean.class).byExecuting(() ->
                        redFutureOf(Boolean.class, TestException.INSTANCE, redTestContext));
                Result<String> result2 = produce(String.class).byExecuting(TestRedSynchronizer::stringSuccess);
                Result<TestObject> result3 = produceFutureOf(TestObject.class).byExecuting(() ->
                        futureOf(testObjectSuccess(), redTestContext));
                return onceResults(result1, result2, result3).finish().produceListenableFutureOf(Boolean.class)
                        .byExecuting((f0, f1, f2) -> {
                            redTestContext.assertions.assertNull(f0);
                            redTestContext.assertions.assertTrue(checkStringSuccess(f1));
                            redTestContext.assertions.assertTrue(checkTestObjectSuccess(f2));
                            return listenableFutureOf(booleanSuccess(), redTestContext);
                        });
            }

        }

        public static class ExplicitDependenciesTest4Synchronizer extends RedSynchronizer<RedTestContext, String> {

            @Override
            protected Result<String> handle(RedTestContext redTestContext) {
                Result<Boolean> result1 = produce(Boolean.class).byExecuting(TestRedSynchronizer::booleanSuccess);
                Result<String> result2 = produceFutureOf(String.class).byExecuting(() ->
                        futureOf(stringSuccess(), redTestContext));
                Result<TestObject> result3 = produceListenableFutureOf(TestObject.class).byExecuting(() ->
                        listenableFutureOf(testObjectSuccess(), redTestContext));
                Result<Boolean> result4 = produceRedFutureOf(Boolean.class).byExecuting(() ->
                        redFutureOf(booleanSuccess(), redTestContext));
                Marker marker1 = execute(pendingMarker ->
                        redTestContext.scheduleTask(FUTURE_DELAY, () -> pendingMarker.fail(TestException.INSTANCE)));
                Marker marker2 = execute(pendingMarker ->
                        redTestContext.scheduleTask(FUTURE_DELAY, () -> pendingMarker.fail(TestException.INSTANCE)));
                FutureTransformer.FutureTransformer4<Boolean, String, TestObject, Boolean> z = onceResults(result1, result2, result3, result4).succeed().andMarkers(marker1, marker2);
                return onceResults(result1, result2, result3, result4).succeed().andMarkers(marker1, marker2).fail()
                        .produceRedFutureOf(String.class).byExecuting((f0, f1, f2, f3) -> {
                            redTestContext.assertions.assertTrue(checkBooleanSuccess(f0));
                            redTestContext.assertions.assertTrue(checkStringSuccess(f1));
                            redTestContext.assertions.assertTrue(checkTestObjectSuccess(f2));
                            redTestContext.assertions.assertTrue(checkBooleanSuccess(f3));
                            return redFutureOf(stringSuccess(), redTestContext);
                        });
            }

        }

        public static class ExplicitDependenciesTest5Synchronizer extends RedSynchronizer<RedTestContext, TestObject> {

            @Override
            protected Result<TestObject> handle(RedTestContext redTestContext) {
                Result<Boolean> result1 = produce(Boolean.class).byExecuting(() -> {
                    throw TestException.INSTANCE;
                });
                Result<String> result2 = produceFutureOf(String.class).byExecuting(() ->
                        futureOf(String.class, TestException.INSTANCE, redTestContext));
                Result<TestObject> result3 = produceListenableFutureOf(TestObject.class).byExecuting(() ->
                        listenableFutureOf(TestObject.class, TestException.INSTANCE, redTestContext));
                Result<Boolean> result4 = produceRedFutureOf(Boolean.class).byExecuting(() ->
                        redFutureOf(Boolean.class, TestException.INSTANCE, redTestContext));
                Result<String> result5 = produce(String.class).byExecuting(() -> {
                    throw TestException.INSTANCE;
                });
                Marker marker1 = execute(pendingMarker ->
                        redTestContext.scheduleTask(FUTURE_DELAY, () -> pendingMarker.fail(TestException.INSTANCE)));
                Marker marker2 = execute(pendingMarker ->
                        redTestContext.scheduleTask(FUTURE_DELAY, pendingMarker::complete));
                return onceResults(result1, result2, result3, result4, result5).fail()
                        .produce(TestObject.class).byExecuting(TestRedSynchronizer::testObjectSuccess);
            }

        }

        public static class ExplicitDependenciesTest6Synchronizer extends RedSynchronizer<RedTestContext, Boolean> {

            @Override
            protected Result<Boolean> handle(RedTestContext redTestContext) {
                Result<Boolean> result1 = produce(Boolean.class).byExecuting(() -> {
                    throw TestException.INSTANCE;
                });
                Result<String> result2 = produceFutureOf(String.class).byExecuting(() ->
                        futureOf(String.class, TestException.INSTANCE, redTestContext));
                Result<TestObject> result3 = produceListenableFutureOf(TestObject.class).byExecuting(() ->
                        listenableFutureOf(TestObject.class, TestException.INSTANCE, redTestContext));
                Result<Boolean> result4 = produceRedFutureOf(Boolean.class).byExecuting(() ->
                        redFutureOf(Boolean.class, TestException.INSTANCE, redTestContext));
                Result<String> result5 = produce(String.class).byExecuting(() -> {
                    throw TestException.INSTANCE;
                });
                Result<TestObject> result6 = produceFutureOf(TestObject.class).byExecuting(() ->
                        futureOf(TestObject.class, TestException.INSTANCE, redTestContext));
                return onceResults(result1, result2, result3, result4, result5, result6).finish()
                        .produceFutureOf(Boolean.class).byExecuting((f0, f1, f2, f3, f4, f5) -> {
                            redTestContext.assertions.assertNull(f0);
                            redTestContext.assertions.assertNull(f1);
                            redTestContext.assertions.assertNull(f2);
                            redTestContext.assertions.assertNull(f3);
                            redTestContext.assertions.assertNull(f4);
                            redTestContext.assertions.assertNull(f5);
                            return futureOf(booleanSuccess(), redTestContext);
                        });
            }

        }

        public static class ExplicitDependenciesTest7Synchronizer extends RedSynchronizer<RedTestContext, String> {

            @Override
            protected Result<String> handle(RedTestContext redTestContext) {
                Result<Boolean> result1 = produce(Boolean.class).byExecuting(TestRedSynchronizer::booleanSuccess);
                Result<String> result2 = produceFutureOf(String.class).byExecuting(() ->
                        futureOf(stringSuccess(), redTestContext));
                Result<TestObject> result3 = produceListenableFutureOf(TestObject.class).byExecuting(() ->
                        listenableFutureOf(testObjectSuccess(), redTestContext));
                Result<Boolean> result4 = produceRedFutureOf(Boolean.class).byExecuting(() ->
                        redFutureOf(booleanSuccess(), redTestContext));
                Result<String> result5 = produce(String.class).byExecuting(TestRedSynchronizer::stringSuccess);
                Result<TestObject> result6 = produceFutureOf(TestObject.class).byExecuting(() ->
                        futureOf(testObjectSuccess(), redTestContext));
                Result<Boolean> result7 = produceListenableFutureOf(Boolean.class).byExecuting(() ->
                        listenableFutureOf(booleanSuccess(), redTestContext));
                return onceResults(result1, result2, result3, result4, result5, result6, result7).succeed()
                        .produceListenableFutureOf(String.class).byExecuting((f0, f1, f2, f3, f4, f5, f6) -> {
                            redTestContext.assertions.assertTrue(checkBooleanSuccess(f0));
                            redTestContext.assertions.assertTrue(checkStringSuccess(f1));
                            redTestContext.assertions.assertTrue(checkTestObjectSuccess(f2));
                            redTestContext.assertions.assertTrue(checkBooleanSuccess(f3));
                            redTestContext.assertions.assertTrue(checkStringSuccess(f4));
                            redTestContext.assertions.assertTrue(checkTestObjectSuccess(f5));
                            redTestContext.assertions.assertTrue(checkBooleanSuccess(f6));
                            return listenableFutureOf(stringSuccess(), redTestContext);
                        });
            }

        }

        public static class ExplicitDependenciesTest8Synchronizer extends RedSynchronizer<RedTestContext, TestObject> {

            @Override
            protected Result<TestObject> handle(RedTestContext redTestContext) {
                Result<Boolean> result1 = produce(Boolean.class).byExecuting(() -> {
                    throw TestException.INSTANCE;
                });
                Result<String> result2 = produceFutureOf(String.class).byExecuting(() ->
                        futureOf(String.class, TestException.INSTANCE, redTestContext));
                Result<TestObject> result3 = produceListenableFutureOf(TestObject.class).byExecuting(() ->
                        listenableFutureOf(TestObject.class, TestException.INSTANCE, redTestContext));
                Result<Boolean> result4 = produceRedFutureOf(Boolean.class).byExecuting(() ->
                        redFutureOf(Boolean.class, TestException.INSTANCE, redTestContext));
                Result<String> result5 = produce(String.class).byExecuting(() -> {
                    throw TestException.INSTANCE;
                });
                Result<TestObject> result6 = produceFutureOf(TestObject.class).byExecuting(() ->
                        futureOf(TestObject.class, TestException.INSTANCE, redTestContext));
                Result<Boolean> result7 = produceListenableFutureOf(Boolean.class).byExecuting(() ->
                        listenableFutureOf(Boolean.class, TestException.INSTANCE, redTestContext));
                Result<String> result8 = produceRedFutureOf(String.class).byExecuting(() ->
                        redFutureOf(String.class, TestException.INSTANCE, redTestContext));
                return onceResults(result1, result2, result3, result4, result5, result6, result7, result8).fail()
                        .produceRedFutureOf(TestObject.class).byExecuting(() ->
                                redFutureOf(testObjectSuccess(), redTestContext));
            }

        }

        public static class ExplicitDependenciesTest9Synchronizer extends RedSynchronizer<RedTestContext, Boolean> {

            @Override
            protected Result<Boolean> handle(RedTestContext redTestContext) {
                Result<Boolean> result1 = produce(Boolean.class).byExecuting(() -> {
                    throw TestException.INSTANCE;
                });
                Result<String> result2 = produceFutureOf(String.class).byExecuting(() ->
                        futureOf(String.class, TestException.INSTANCE, redTestContext));
                Result<TestObject> result3 = produceListenableFutureOf(TestObject.class).byExecuting(() ->
                        listenableFutureOf(TestObject.class, TestException.INSTANCE, redTestContext));
                Result<Boolean> result4 = produceRedFutureOf(Boolean.class).byExecuting(() ->
                        redFutureOf(Boolean.class, TestException.INSTANCE, redTestContext));
                Result<String> result5 = produce(String.class).byExecuting(() -> {
                    throw TestException.INSTANCE;
                });
                Result<TestObject> result6 = produceFutureOf(TestObject.class).byExecuting(() ->
                        futureOf(TestObject.class, TestException.INSTANCE, redTestContext));
                Result<Boolean> result7 = produceListenableFutureOf(Boolean.class).byExecuting(() ->
                        listenableFutureOf(Boolean.class, TestException.INSTANCE, redTestContext));
                Result<String> result8 = produceRedFutureOf(String.class).byExecuting(() ->
                        redFutureOf(String.class, TestException.INSTANCE, redTestContext));
                Result<TestObject> result9 = produce(TestObject.class)
                        .byExecuting(TestRedSynchronizer::testObjectSuccess);
                return onceResults(result1, result2, result3, result4, result5, result6, result7, result8, result9)
                        .finish().produce(Boolean.class).byExecuting((f0, f1, f2, f3, f4, f5, f6, f7, f8) -> {
                            redTestContext.assertions.assertNull(f0);
                            redTestContext.assertions.assertNull(f1);
                            redTestContext.assertions.assertNull(f2);
                            redTestContext.assertions.assertNull(f3);
                            redTestContext.assertions.assertNull(f4);
                            redTestContext.assertions.assertNull(f5);
                            redTestContext.assertions.assertNull(f6);
                            redTestContext.assertions.assertNull(f7);
                            redTestContext.assertions.assertTrue(checkTestObjectSuccess(f8));
                            return booleanSuccess();
                        });
            }

        }

        public static class ExplicitDependenciesTest10Synchronizer extends RedVoidSynchronizer<CommandTestContext> {

            @Override
            protected Marker handle(CommandTestContext context) {
                RedTestContext.TimingTester timingTester = context.redTestContext.timingTester();
                Result<Boolean> result1 = produce(Boolean.class).byExecuting(TestRedSynchronizer::booleanSuccess);
                Result<String> result2 = produceFutureOf(String.class).byExecuting(() ->
                        futureOf(stringSuccess(), context.redTestContext));
                Result<TestObject> result3 = produceListenableFutureOf(TestObject.class).byExecuting(() ->
                        listenableFutureOf(testObjectSuccess(), context.redTestContext));
                Result<Boolean> result4 = produceRedFutureOf(Boolean.class).byExecuting(() ->
                        redFutureOf(booleanSuccess(), context.redTestContext));
                Result<String> result5 = produce(String.class).byExecuting(TestRedSynchronizer::stringSuccess);
                Result<TestObject> result6 = produceFutureOf(TestObject.class).byExecuting(() ->
                        futureOf(testObjectSuccess(), context.redTestContext));
                Result<Boolean> result7 = produceListenableFutureOf(Boolean.class).byExecuting(() ->
                        listenableFutureOf(booleanSuccess(), context.redTestContext));
                Result<String> result8 = produceRedFutureOf(String.class).byExecuting(() ->
                        redFutureOf(stringSuccess(), context.redTestContext));
                Result<TestObject> result9 = produce(TestObject.class)
                        .byExecuting(TestRedSynchronizer::testObjectSuccess);
                Result<Boolean> result10 = produceFutureOf(Boolean.class).byExecuting(() ->
                        futureOf(booleanSuccess(), context.redTestContext));
                return onceResults(result1, result2, result3, result4, result5, result6, result7, result8, result9,
                        result10).succeed().execute((pendingMarker, f0, f1, f2, f3, f4, f5, f6, f7, f8, f9) -> {
                            timingTester.validatePassed(FUTURE_DELAY);
                            context.redTestContext.assertions.assertTrue(checkBooleanSuccess(f0));
                            context.redTestContext.assertions.assertTrue(checkStringSuccess(f1));
                            context.redTestContext.assertions.assertTrue(checkTestObjectSuccess(f2));
                            context.redTestContext.assertions.assertTrue(checkBooleanSuccess(f3));
                            context.redTestContext.assertions.assertTrue(checkStringSuccess(f4));
                            context.redTestContext.assertions.assertTrue(checkTestObjectSuccess(f5));
                            context.redTestContext.assertions.assertTrue(checkBooleanSuccess(f6));
                            context.redTestContext.assertions.assertTrue(checkStringSuccess(f7));
                            context.redTestContext.assertions.assertTrue(checkTestObjectSuccess(f8));
                            context.redTestContext.assertions.assertTrue(checkBooleanSuccess(f9));
                            context.finished.set(true);
                            pendingMarker.complete();
                        });
            }

        }

    }

}
