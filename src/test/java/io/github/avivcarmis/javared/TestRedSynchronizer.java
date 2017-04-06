package io.github.avivcarmis.javared;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import io.github.avivcarmis.javared.executor.PreconditionFailedException;
import io.github.avivcarmis.javared.executor.RedSynchronizer;
import io.github.avivcarmis.javared.executor.RedVoidSynchronizer;
import io.github.avivcarmis.javared.future.OpenRedFutureOf;
import io.github.avivcarmis.javared.future.RedFuture;
import io.github.avivcarmis.javared.future.RedFutureOf;
import io.github.avivcarmis.javared.test.RedTestContext;
import io.github.avivcarmis.javared.test.RedTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Runs the different combinations of synchronizer execution to test behavior of both
 * {@link RedSynchronizer} and {@link RedVoidSynchronizer} classes.
 */
@RunWith(RedTestRunner.class)
public class TestRedSynchronizer {

    /**
     * Millisecond delay time for resolving of all test futures.
     */
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
     * -one layer of markers with failure status
     * -function result
     */
    @Test
    public void explicitDependenciesTest4(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runFunctionTest(redTestContext, TestSynchronizers.ExplicitDependenciesTest4Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -5 result dependencies with failure status
     * -one layer of markers with success status
     * -function result
     */
    @Test
    public void explicitDependenciesTest5(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runFunctionTest(redTestContext, TestSynchronizers.ExplicitDependenciesTest5Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -6 result dependencies with finish status
     * -one layer of markers with finish status
     * -function result
     */
    @Test
    public void explicitDependenciesTest6(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runFunctionTest(redTestContext, TestSynchronizers.ExplicitDependenciesTest6Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -7 result dependencies with success status
     * -one layer of markers with finish status
     * -another layer of markers with fail status
     * -function result
     */
    @Test
    public void explicitDependenciesTest7(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runFunctionTest(redTestContext, TestSynchronizers.ExplicitDependenciesTest7Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -8 result dependencies with failure status
     * -one layer of markers with fail status
     * -another layer of markers with success status
     * -function result
     */
    @Test
    public void explicitDependenciesTest8(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runFunctionTest(redTestContext, TestSynchronizers.ExplicitDependenciesTest8Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -9 result dependencies with finish status
     * -one layer of markers with success status
     * -another layer of markers with success status
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
     * -command result
     */
    @Test
    public void explicitDependenciesTest10(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runCommandTest(redTestContext, TestSynchronizers.ExplicitDependenciesTest10Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -1 result dependencies with failure status
     * -no markers
     * -command result
     */
    @Test
    public void explicitDependenciesTest11(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runCommandTest(redTestContext, TestSynchronizers.ExplicitDependenciesTest11Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -2 result dependencies with finish status
     * -no markers
     * -command result
     */
    @Test
    public void explicitDependenciesTest12(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runCommandTest(redTestContext, TestSynchronizers.ExplicitDependenciesTest12Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -3 result dependencies with finish status
     * -one layer of markers with success status
     * -command result
     */
    @Test
    public void explicitDependenciesTest13(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runCommandTest(redTestContext, TestSynchronizers.ExplicitDependenciesTest13Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -4 result dependencies with failure status
     * -one layer of markers with finish status
     * -command result
     */
    @Test
    public void explicitDependenciesTest14(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runCommandTest(redTestContext, TestSynchronizers.ExplicitDependenciesTest14Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -5 result dependencies with finish status
     * -one layer of markers with failure status
     * -command result
     */
    @Test
    public void explicitDependenciesTest15(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runCommandTest(redTestContext, TestSynchronizers.ExplicitDependenciesTest15Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -6 result dependencies with success status
     * -one layer of markers with finish status
     * -another layer of markers with failure status
     * -command result
     */
    @Test
    public void explicitDependenciesTest16(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runCommandTest(redTestContext, TestSynchronizers.ExplicitDependenciesTest16Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -7 result dependencies with failure status
     * -one layer of markers with failure status
     * -another layer of markers with failure status
     * -command result
     */
    @Test
    public void explicitDependenciesTest17(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runCommandTest(redTestContext, TestSynchronizers.ExplicitDependenciesTest17Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -8 result dependencies with finish status
     * -one layer of markers with success status
     * -another layer of markers with finish status
     * -command result
     */
    @Test
    public void explicitDependenciesTest18(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runCommandTest(redTestContext, TestSynchronizers.ExplicitDependenciesTest18Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -11 result dependency with success status
     * -no markers
     * -function result
     */
    @Test
    public void nDependenciesTest1(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runFunctionTest(redTestContext, TestSynchronizers.NDependenciesTest1Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -11 result dependencies with failure status
     * -no markers
     * -function result
     */
    @Test
    public void nDependenciesTest2(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runFunctionTest(redTestContext, TestSynchronizers.NDependenciesTest2Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -11 result dependencies with finish status
     * -no markers
     * -function result
     */
    @Test
    public void nDependenciesTest3(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runFunctionTest(redTestContext, TestSynchronizers.NDependenciesTest3Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -11 result dependency with success status
     * -one layer of markers with success status
     * -function result
     */
    @Test
    public void nDependenciesTest4(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runFunctionTest(redTestContext, TestSynchronizers.NDependenciesTest4Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -11 result dependencies with failure status
     * -one layer of markers with finish status
     * -function result
     */
    @Test
    public void nDependenciesTest5(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runFunctionTest(redTestContext, TestSynchronizers.NDependenciesTest5Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -11 result dependencies with finish status
     * -one layer of markers with failure status
     * -function result
     */
    @Test
    public void nDependenciesTest6(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runFunctionTest(redTestContext, TestSynchronizers.NDependenciesTest6Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -11 result dependency with success status
     * -one layer of markers with finish status
     * -another layer of markers with success status
     * -function result
     */
    @Test
    public void nDependenciesTest7(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runFunctionTest(redTestContext, TestSynchronizers.NDependenciesTest7Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -11 result dependencies with failure status
     * -one layer of markers with failure status
     * -another layer of markers with finish status
     * -function result
     */
    @Test
    public void nDependenciesTest8(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runFunctionTest(redTestContext, TestSynchronizers.NDependenciesTest8Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -11 result dependencies with finish status
     * -one layer of markers with failure status
     * -another layer of markers with failure status
     * -function result
     */
    @Test
    public void nDependenciesTest9(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runFunctionTest(redTestContext, TestSynchronizers.NDependenciesTest9Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -11 result dependency with success status
     * -no markers
     * -command result
     */
    @Test
    public void nDependenciesTest10(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runCommandTest(redTestContext, TestSynchronizers.NDependenciesTest10Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -11 result dependencies with failure status
     * -no markers
     * -command result
     */
    @Test
    public void nDependenciesTest11(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runCommandTest(redTestContext, TestSynchronizers.NDependenciesTest11Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -11 result dependencies with finish status
     * -no markers
     * -command result
     */
    @Test
    public void nDependenciesTest12(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runCommandTest(redTestContext, TestSynchronizers.NDependenciesTest12Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -11 result dependency with success status
     * -one layer of markers with success status
     * -command result
     */
    @Test
    public void nDependenciesTest13(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runCommandTest(redTestContext, TestSynchronizers.NDependenciesTest13Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -11 result dependencies with failure status
     * -one layer of markers with finish status
     * -command result
     */
    @Test
    public void nDependenciesTest14(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runCommandTest(redTestContext, TestSynchronizers.NDependenciesTest14Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -11 result dependencies with finish status
     * -one layer of markers with failure status
     * -command result
     */
    @Test
    public void nDependenciesTest15(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runCommandTest(redTestContext, TestSynchronizers.NDependenciesTest15Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -11 result dependency with success status
     * -one layer of markers with finish status
     * -another layer of markers with success status
     * -command result
     */
    @Test
    public void nDependenciesTest16(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runCommandTest(redTestContext, TestSynchronizers.NDependenciesTest16Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -11 result dependencies with failure status
     * -one layer of markers with failure status
     * -another layer of markers with finish status
     * -command result
     */
    @Test
    public void nDependenciesTest17(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runCommandTest(redTestContext, TestSynchronizers.NDependenciesTest17Synchronizer.class);
    }

    /**
     * Test synchronization of execution with:
     * -11 result dependencies with finish status
     * -one layer of markers with failure status
     * -another layer of markers with failure status
     * -command result
     */
    @Test
    public void nDependenciesTest18(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runCommandTest(redTestContext, TestSynchronizers.NDependenciesTest18Synchronizer.class);
    }

    /**
     * Test synchronization execution with N results, check to see failures of wrong typing and
     * wrong indexing of results
     */
    @Test
    public void testNDependenciesIllegalResults(RedTestContext redTestContext)
            throws InstantiationException, IllegalAccessException {
        runCommandTest(redTestContext, TestSynchronizers.TestNDependenciesIllegalResults.class);
    }

    /**
     * Runs a {@link RedSynchronizer} that fails precondition to validate failure on result future
     */
    @Test
    public void preconditionFailingSynchronizerTest(RedTestContext redTestContext) {
        RedTestContext.Fork fork = redTestContext.fork();
        TestSynchronizers.PreconditionFailingSynchronizer synchronizer =
                new TestSynchronizers.PreconditionFailingSynchronizer();
        synchronizer
                .execute(redTestContext)
                .addSuccessCallback(() -> fork.fail("should have failed"))
                .addFailureCallback(throwable -> {
                    if (throwable instanceof PreconditionFailedException.Success &&
                            throwable.getCause() == TestException.INSTANCE) {
                        fork.complete();
                    }
                    else {
                        fork.fail(throwable);
                    }
                });
    }

    /**
     * Runs a {@link RedVoidSynchronizer} that fails precondition to validate failure on result future
     */
    @Test
    public void preconditionFailingVoidSynchronizerTest(RedTestContext redTestContext) {
        RedTestContext.Fork fork = redTestContext.fork();
        TestSynchronizers.PreconditionFailingVoidSynchronizer synchronizer =
                new TestSynchronizers.PreconditionFailingVoidSynchronizer();
        synchronizer
                .execute(redTestContext)
                .addSuccessCallback(() -> fork.fail("should have failed"))
                .addFailureCallback(throwable -> {
                    if (throwable instanceof PreconditionFailedException.Success &&
                            throwable.getCause() == TestException.INSTANCE) {
                        fork.complete();
                    }
                    else {
                        fork.fail(throwable);
                    }
                });
    }

    /**
     * Runs a {@link RedSynchronizer} that throws exception on main execution method
     * to validate failure on result future
     */
    @Test
    public void topLevelExceptionSynchronizerTest(RedTestContext redTestContext) {
        RedTestContext.Fork fork = redTestContext.fork();
        TestSynchronizers.TopLevelExceptionSynchronizer synchronizer =
                new TestSynchronizers.TopLevelExceptionSynchronizer();
        synchronizer
                .execute(redTestContext)
                .addSuccessCallback(() -> fork.fail("should have failed"))
                .addFailureCallback(throwable -> {
                    if (throwable == TestException.INSTANCE) {
                        fork.complete();
                    }
                    else {
                        fork.fail(throwable);
                    }
                });
    }

    /**
     * Runs a {@link RedVoidSynchronizer} that throws exception on main execution method
     * to validate failure on result future
     */
    @Test
    public void topLevelExceptionVoidSynchronizerTest(RedTestContext redTestContext) {
        RedTestContext.Fork fork = redTestContext.fork();
        TestSynchronizers.TopLevelExceptionVoidSynchronizer synchronizer =
                new TestSynchronizers.TopLevelExceptionVoidSynchronizer();
        synchronizer
                .execute(redTestContext)
                .addSuccessCallback(() -> fork.fail("should have failed"))
                .addFailureCallback(throwable -> {
                    if (throwable == TestException.INSTANCE) {
                        fork.complete();
                    }
                    else {
                        fork.fail(throwable);
                    }
                });
    }

    /**
     * Runs a {@link RedSynchronizer} that throws exception on result function
     * to validate failure on result future
     */
    @Test
    public void exceptionInResultFunctionSynchronizerTest(RedTestContext redTestContext) {
        RedTestContext.Fork fork = redTestContext.fork();
        TestSynchronizers.ExceptionInResultFunctionSynchronizer synchronizer =
                new TestSynchronizers.ExceptionInResultFunctionSynchronizer();
        synchronizer
                .execute(redTestContext)
                .addSuccessCallback(() -> fork.fail("should have failed"))
                .addFailureCallback(throwable -> {
                    if (throwable == TestException.INSTANCE) {
                        fork.complete();
                    }
                    else {
                        fork.fail(throwable);
                    }
                });
    }

    /**
     * Runs a {@link RedVoidSynchronizer} that throws exception on result command
     * to validate failure on result future
     */
    @Test
    public void exceptionInResultCommandVoidSynchronizerTest(RedTestContext redTestContext) {
        RedTestContext.Fork fork = redTestContext.fork();
        TestSynchronizers.ExceptionInResultCommandVoidSynchronizer synchronizer =
                new TestSynchronizers.ExceptionInResultCommandVoidSynchronizer();
        synchronizer
                .execute(redTestContext)
                .addSuccessCallback(() -> fork.fail("should have failed"))
                .addFailureCallback(throwable -> {
                    if (throwable == TestException.INSTANCE) {
                        fork.complete();
                    }
                    else {
                        fork.fail(throwable);
                    }
                });
    }

    /**
     * Runs a {@link RedSynchronizer} that fails {@link Future} result function
     * to validate failure on result future
     */
    @Test
    public void failingFutureSynchronizerTest(RedTestContext redTestContext) {
        RedTestContext.Fork fork = redTestContext.fork();
        TestSynchronizers.FailingFutureSynchronizer synchronizer =
                new TestSynchronizers.FailingFutureSynchronizer();
        synchronizer
                .execute(redTestContext)
                .addSuccessCallback(() -> fork.fail("should have failed"))
                .addFailureCallback(throwable -> {
                    if (throwable == TestException.INSTANCE) {
                        fork.complete();
                    }
                    else {
                        fork.fail(throwable);
                    }
                });
    }

    /**
     * Runs a {@link RedSynchronizer} that fails {@link ListenableFuture} result function
     * to validate failure on result future
     */
    @Test
    public void failingListenableFutureSynchronizerTest(RedTestContext redTestContext) {
        RedTestContext.Fork fork = redTestContext.fork();
        TestSynchronizers.FailingListenableFutureSynchronizer synchronizer =
                new TestSynchronizers.FailingListenableFutureSynchronizer();
        synchronizer
                .execute(redTestContext)
                .addSuccessCallback(() -> fork.fail("should have failed"))
                .addFailureCallback(throwable -> {
                    if (throwable == TestException.INSTANCE) {
                        fork.complete();
                    }
                    else {
                        fork.fail(throwable);
                    }
                });
    }

    /**
     * Runs a {@link RedSynchronizer} that fails {@link RedFutureOf} result function
     * to validate failure on result future
     */
    @Test
    public void failingRedFutureSynchronizerTest(RedTestContext redTestContext) {
        RedTestContext.Fork fork = redTestContext.fork();
        TestSynchronizers.FailingRedFutureSynchronizer synchronizer =
                new TestSynchronizers.FailingRedFutureSynchronizer();
        synchronizer
                .execute(redTestContext)
                .addSuccessCallback(() -> fork.fail("should have failed"))
                .addFailureCallback(throwable -> {
                    if (throwable == TestException.INSTANCE) {
                        fork.complete();
                    }
                    else {
                        fork.fail(throwable);
                    }
                });
    }

    /**
     * Runs a {@link RedVoidSynchronizer} that fails result command
     * to validate failure on result future
     */
    @Test
    public void failingResultCommandSynchronizerTest(RedTestContext redTestContext) {
        RedTestContext.Fork fork = redTestContext.fork();
        TestSynchronizers.FailingResultCommandSynchronizer synchronizer =
                new TestSynchronizers.FailingResultCommandSynchronizer();
        synchronizer
                .execute(redTestContext)
                .addSuccessCallback(() -> fork.fail("should have failed"))
                .addFailureCallback(throwable -> {
                    if (throwable == TestException.INSTANCE) {
                        fork.complete();
                    }
                    else {
                        fork.fail(throwable);
                    }
                });
    }

    /**
     * Runs a synchronizer with 2 preconditions - one failing and one succeeding, test to see
     * fail requirement on both is failing
     */
    @Test
    public void testPartialTransformer1(RedTestContext redTestContext) {
        RedTestContext.Fork fork = redTestContext.fork();
        TestSynchronizers.TestPartialTransformer1 synchronizer =
                new TestSynchronizers.TestPartialTransformer1();
        synchronizer
                .execute(redTestContext)
                .addSuccessCallback(() -> fork.fail("should have failed"))
                .addFailureCallback(throwable -> {
                    if (throwable instanceof PreconditionFailedException.Failure) {
                        fork.complete();
                    }
                    else {
                        fork.fail(throwable);
                    }
                });
    }

    /**
     * Runs a synchronizer with 2 preconditions - one failing and one succeeding, test to see
     * success requirement on both is failing
     */
    @Test
    public void testPartialTransformer2(RedTestContext redTestContext) {
        RedTestContext.Fork fork = redTestContext.fork();
        TestSynchronizers.TestPartialTransformer2 synchronizer =
                new TestSynchronizers.TestPartialTransformer2();
        synchronizer
                .execute(redTestContext)
                .addSuccessCallback(() -> fork.fail("should have failed"))
                .addFailureCallback(throwable -> {
                    if (throwable instanceof PreconditionFailedException.Success &&
                            throwable.getCause() == TestException.INSTANCE) {
                        fork.complete();
                    }
                    else {
                        fork.fail(throwable);
                    }
                });
    }

    /**
     * Runs a synchronizer with 2 marker preconditions - one failing and one succeeding, test to see
     * fail requirement on both is failing
     */
    @Test
    public void testPartialTransformer3(RedTestContext redTestContext) {
        RedTestContext.Fork fork = redTestContext.fork();
        TestSynchronizers.TestPartialTransformer3 synchronizer =
                new TestSynchronizers.TestPartialTransformer3();
        synchronizer
                .execute(redTestContext)
                .addSuccessCallback(() -> fork.fail("should have failed"))
                .addFailureCallback(throwable -> {
                    if (throwable instanceof PreconditionFailedException.Failure) {
                        fork.complete();
                    }
                    else {
                        fork.fail(throwable);
                    }
                });
    }

    /**
     * Runs a synchronizer with 2 marker preconditions - one failing and one succeeding, test to see
     * success requirement on both is failing
     */
    @Test
    public void testPartialTransformer4(RedTestContext redTestContext) {
        RedTestContext.Fork fork = redTestContext.fork();
        TestSynchronizers.TestPartialTransformer4 synchronizer =
                new TestSynchronizers.TestPartialTransformer4();
        synchronizer
                .execute(redTestContext)
                .addSuccessCallback(() -> fork.fail("should have failed"))
                .addFailureCallback(throwable -> {
                    if (throwable instanceof PreconditionFailedException.Success &&
                            throwable.getCause() == TestException.INSTANCE) {
                        fork.complete();
                    }
                    else {
                        fork.fail(throwable);
                    }
                });
    }

    /**
     * Test nested preconditions
     */
    @Test
    public void bangTest(RedTestContext redTestContext) throws InstantiationException, IllegalAccessException {
        runFunctionTest(redTestContext, TestSynchronizers.BangTestSynchronizer.class);
    }

    // Utils

    /**
     * Runs the given {@link RedSynchronizer} and validates result
     * @param redTestContext test context to operate within
     * @param syncClass      class to run
     * @param <T>            type of the result
     * @throws IllegalAccessException if couldn't instantiate given class
     * @throws InstantiationException if couldn't instantiate given class
     */
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

    /**
     * Runs the given {@link RedSynchronizer} and validates status
     * @param redTestContext test context to operate within
     * @param syncClass      class to run
     * @throws IllegalAccessException if couldn't instantiate given class
     * @throws InstantiationException if couldn't instantiate given class
     */
    private static void runCommandTest(RedTestContext redTestContext,
                                       Class<? extends RedVoidSynchronizer<CommandTestContext>> syncClass)
            throws IllegalAccessException, InstantiationException {
        RedTestContext.Fork fork = redTestContext.fork();
        RedVoidSynchronizer<CommandTestContext> synchronizer = syncClass.newInstance();
        CommandTestContext commandTestContext = new CommandTestContext(redTestContext);
        synchronizer.execute(commandTestContext).addFailureCallback(redTestContext::fail).addSuccessCallback(() -> {
            if (commandTestContext._finished.get()) {
                fork.complete();
            }
            else {
                fork.fail("not finished executing");
            }
        });
    }

    /**
     * @return the value of a successful boolean test
     */
    private static Boolean booleanSuccess() {
        return true;
    }

    /**
     * @return the value of a successful string test
     */
    private static String stringSuccess() {
        return "success";
    }

    /**
     * @return the value of a successful {@link TestObject} test
     */
    private static TestObject testObjectSuccess() {
        return TestObject.INSTANCE;
    }

    /**
     * @param result object to validate
     * @return true if given object equals the successful boolean test result
     */
    private static boolean checkBooleanSuccess(Object result) {
        return result != null && result instanceof Boolean && result == booleanSuccess();
    }

    /**
     * @param result object to validate
     * @return true if given object equals the successful string test result
     */
    private static boolean checkStringSuccess(Object result) {
        return result != null && result instanceof String && result.equals(stringSuccess());
    }

    /**
     * @param result object to validate
     * @return true if given object equals the successful {@link TestObject} test result
     */
    private static boolean checkTestObjectSuccess(Object result) {
        return result != null && result instanceof TestObject && result == testObjectSuccess();
    }

    /**
     * @return a {@link Future} that will be resolved in FUTURE_DELAY milliseconds with given value
     */
    private static <T> Future<T> futureOf(T value, RedTestContext redTestContext) {
        CompletableFuture<T> future = new CompletableFuture<>();
        redTestContext.scheduleTask(FUTURE_DELAY, () -> future.complete(value));
        return future;
    }

    /**
     * @return a {@link Future} that will be failed in FUTURE_DELAY milliseconds with given throwable
     */
    private static <T> Future<T> futureOf(@SuppressWarnings("unused") Class<T> tClass,
                                          Throwable t, RedTestContext redTestContext) {
        CompletableFuture<T> future = new CompletableFuture<>();
        redTestContext.scheduleTask(FUTURE_DELAY, () -> future.completeExceptionally(t));
        return future;
    }

    /**
     * @return a {@link ListenableFuture} that will be resolved in FUTURE_DELAY milliseconds with given value
     */
    private static <T> ListenableFuture<T> listenableFutureOf(T value, RedTestContext redTestContext) {
        SettableFuture<T> future = SettableFuture.create();
        redTestContext.scheduleTask(FUTURE_DELAY, () -> future.set(value));
        return future;
    }

    /**
     * @return a {@link ListenableFuture} that will be failed in FUTURE_DELAY milliseconds with given throwable
     */
    private static <T> ListenableFuture<T> listenableFutureOf(@SuppressWarnings("unused") Class<T> tClass,
                                                              Throwable t, RedTestContext redTestContext) {
        SettableFuture<T> future = SettableFuture.create();
        redTestContext.scheduleTask(FUTURE_DELAY, () -> future.setException(t));
        return future;
    }

    /**
     * @return a {@link RedFutureOf} that will be resolved in FUTURE_DELAY milliseconds with given value
     */
    private static <T> RedFutureOf<T> redFutureOf(T value, RedTestContext redTestContext) {
        OpenRedFutureOf<T> future = RedFuture.futureOf();
        redTestContext.scheduleTask(FUTURE_DELAY, () -> future.resolve(value));
        return future;
    }

    /**
     * @return a {@link RedFutureOf} that will be failed in FUTURE_DELAY milliseconds with given throwable
     */
    private static <T> RedFutureOf<T> redFutureOf(@SuppressWarnings("unused") Class<T> tClass,
                                                  Throwable t, RedTestContext redTestContext) {
        OpenRedFutureOf<T> future = RedFuture.futureOf();
        redTestContext.scheduleTask(FUTURE_DELAY, () -> future.fail(t));
        return future;
    }

    /**
     * A wrapper for command tests to allow marking that the last execution finished
     */
    private static class CommandTestContext {

        private final RedTestContext _redTestContext;

        private final AtomicBoolean _finished;

        private CommandTestContext(RedTestContext redTestContext) {
            _redTestContext = redTestContext;
            _finished = new AtomicBoolean();
        }

    }

    /**
     * An object to test custom object instances along side with primitive booleans and Strings
     */
    private static class TestObject {

        private static final TestObject INSTANCE = new TestObject();

    }

    /**
     * An instance of exception to be thrown
     */
    private static class TestException extends Exception {

        private static final TestException INSTANCE = new TestException();

    }

    // Test synchronizer

    /**
     * All {@link RedSynchronizer} and {@link RedVoidSynchronizer} classes to run for each test case
     */
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
                RedTestContext.TimingValidator timingValidator = context.timingValidator();
                Marker marker1 = execute(pendingMarker -> context.scheduleTask(FUTURE_DELAY, pendingMarker::complete));
                Marker marker2 = execute(pendingMarker -> context.scheduleTask(FUTURE_DELAY, pendingMarker::complete));
                return ifMarkers(marker1, marker2).succeed().produceFutureOf(String.class).byExecuting(() -> {
                    timingValidator.validatePassed(FUTURE_DELAY);
                    return futureOf(stringSuccess(), context);
                });
            }

        }

        public static class NoDependenciesTest3Synchronizer extends RedSynchronizer<RedTestContext, TestObject> {

            @Override
            protected Result<TestObject> handle(RedTestContext context) {
                RedTestContext.TimingValidator timingValidator = context.timingValidator();
                Marker marker1 = execute(pendingMarker -> context.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                Marker marker2 = execute(pendingMarker -> context.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                return ifMarkers(marker1, marker2).fail().produceFutureOf(TestObject.class).byExecuting(() -> {
                    timingValidator.validatePassed(FUTURE_DELAY);
                    return listenableFutureOf(testObjectSuccess(), context);
                });
            }

        }

        public static class NoDependenciesTest4Synchronizer extends RedSynchronizer<RedTestContext, Boolean> {

            @Override
            protected Result<Boolean> handle(RedTestContext context) {
                RedTestContext.TimingValidator timingValidator = context.timingValidator();
                Marker marker1 = execute(pendingMarker -> context.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                Marker marker2 = execute(pendingMarker -> context.scheduleTask(FUTURE_DELAY, pendingMarker::complete));
                return ifMarkers(marker1, marker2).finish().produceFutureOf(Boolean.class).byExecuting(() -> {
                    timingValidator.validatePassed(FUTURE_DELAY);
                    return redFutureOf(booleanSuccess(), context);
                });
            }

        }

        public static class NoDependenciesTest5Synchronizer extends RedSynchronizer<RedTestContext, String> {

            @Override
            protected Result<String> handle(RedTestContext context) {
                RedTestContext.TimingValidator timingValidator = context.timingValidator();
                Marker marker1 = execute(pendingMarker -> context.scheduleTask(FUTURE_DELAY, pendingMarker::complete));
                Marker marker2 = execute(pendingMarker -> context.scheduleTask(FUTURE_DELAY, pendingMarker::complete));
                return ifMarkers(marker1).succeed().andMarkers(marker2).finish().produce(String.class)
                        .byExecuting(() -> {
                            timingValidator.validatePassed(FUTURE_DELAY);
                            return stringSuccess();
                        });
            }

        }

        public static class NoDependenciesTest6Synchronizer extends RedSynchronizer<RedTestContext, TestObject> {

            @Override
            protected Result<TestObject> handle(RedTestContext context) {
                RedTestContext.TimingValidator timingValidator = context.timingValidator();
                Marker marker1 = execute(pendingMarker -> context.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                Marker marker2 = execute(pendingMarker -> context.scheduleTask(FUTURE_DELAY, pendingMarker::complete));
                return ifMarkers(marker1).fail().andMarkers(marker2).succeed().produceFutureOf(TestObject.class)
                        .byExecuting(() -> {
                            timingValidator.validatePassed(FUTURE_DELAY);
                            return futureOf(testObjectSuccess(), context);
                        });
            }

        }

        public static class NoDependenciesTest7Synchronizer extends RedSynchronizer<RedTestContext, Boolean> {

            @Override
            protected Result<Boolean> handle(RedTestContext context) {
                RedTestContext.TimingValidator timingValidator = context.timingValidator();
                Marker marker1 = execute(pendingMarker -> context.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                Marker marker2 = execute(pendingMarker -> context.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                return ifMarkers(marker1).finish().andMarkers(marker2).fail().produceFutureOf(Boolean.class)
                        .byExecuting(() -> {
                            timingValidator.validatePassed(FUTURE_DELAY);
                            return listenableFutureOf(booleanSuccess(), context);
                        });
            }

        }

        public static class NoDependenciesTest8Synchronizer extends RedVoidSynchronizer<CommandTestContext> {

            @Override
            protected Marker handle(CommandTestContext context) {
                return execute(pendingMarker -> context._redTestContext.scheduleTask(FUTURE_DELAY, () -> {
                    context._finished.set(true);
                    pendingMarker.complete();
                }));
            }

        }

        public static class NoDependenciesTest9Synchronizer extends RedVoidSynchronizer<CommandTestContext> {

            @Override
            protected Marker handle(CommandTestContext context) {
                RedTestContext.TimingValidator timingValidator = context._redTestContext.timingValidator();
                Marker marker1 = execute(pendingMarker ->
                        context._redTestContext.scheduleTask(FUTURE_DELAY, pendingMarker::complete));
                Marker marker2 = execute(pendingMarker ->
                        context._redTestContext.scheduleTask(FUTURE_DELAY, pendingMarker::complete));
                return ifMarkers(marker1, marker2).succeed().execute(pendingMarker -> {
                    timingValidator.validatePassed(FUTURE_DELAY);
                    context._finished.set(true);
                    pendingMarker.complete();
                });
            }

        }

        public static class NoDependenciesTest10Synchronizer extends RedVoidSynchronizer<CommandTestContext> {

            @Override
            protected Marker handle(CommandTestContext context) {
                RedTestContext.TimingValidator timingValidator = context._redTestContext.timingValidator();
                Marker marker1 = execute(pendingMarker -> context._redTestContext.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                Marker marker2 = execute(pendingMarker -> context._redTestContext.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                return ifMarkers(marker1, marker2).fail().execute(pendingMarker -> {
                    timingValidator.validatePassed(FUTURE_DELAY);
                    context._finished.set(true);
                    pendingMarker.complete();
                });
            }

        }

        public static class NoDependenciesTest11Synchronizer extends RedVoidSynchronizer<CommandTestContext> {

            @Override
            protected Marker handle(CommandTestContext context) {
                RedTestContext.TimingValidator timingValidator = context._redTestContext.timingValidator();
                Marker marker1 = execute(pendingMarker -> context._redTestContext.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                Marker marker2 = execute(pendingMarker -> context._redTestContext.scheduleTask(FUTURE_DELAY, pendingMarker::complete));
                return ifMarkers(marker1, marker2).finish().execute(pendingMarker -> {
                    timingValidator.validatePassed(FUTURE_DELAY);
                    context._finished.set(true);
                    pendingMarker.complete();
                });
            }

        }

        public static class NoDependenciesTest12Synchronizer extends RedVoidSynchronizer<CommandTestContext> {

            @Override
            protected Marker handle(CommandTestContext context) {
                RedTestContext.TimingValidator timingValidator = context._redTestContext.timingValidator();
                Marker marker1 = execute(pendingMarker -> context._redTestContext.scheduleTask(FUTURE_DELAY, pendingMarker::complete));
                Marker marker2 = execute(pendingMarker -> context._redTestContext.scheduleTask(FUTURE_DELAY, pendingMarker::complete));
                return ifMarkers(marker1).succeed().andMarkers(marker2).finish().execute(pendingMarker -> {
                    timingValidator.validatePassed(FUTURE_DELAY);
                    context._finished.set(true);
                    pendingMarker.complete();
                });
            }

        }

        public static class NoDependenciesTest13Synchronizer extends RedVoidSynchronizer<CommandTestContext> {

            @Override
            protected Marker handle(CommandTestContext context) {
                RedTestContext.TimingValidator timingValidator = context._redTestContext.timingValidator();
                Marker marker1 = execute(pendingMarker -> context._redTestContext.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                Marker marker2 = execute(pendingMarker -> context._redTestContext.scheduleTask(FUTURE_DELAY, pendingMarker::complete));
                return ifMarkers(marker1).fail().andMarkers(marker2).succeed().execute(pendingMarker -> {
                    timingValidator.validatePassed(FUTURE_DELAY);
                    context._finished.set(true);
                    pendingMarker.complete();
                });
            }

        }

        public static class NoDependenciesTest14Synchronizer extends RedVoidSynchronizer<CommandTestContext> {

            @Override
            protected Marker handle(CommandTestContext context) {
                RedTestContext.TimingValidator timingValidator = context._redTestContext.timingValidator();
                Marker marker1 = execute(pendingMarker -> context._redTestContext.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                Marker marker2 = execute(pendingMarker -> context._redTestContext.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                return ifMarkers(marker1).finish().andMarkers(marker2).fail().execute(pendingMarker -> {
                    timingValidator.validatePassed(FUTURE_DELAY);
                    context._finished.set(true);
                    pendingMarker.complete();
                });
            }

        }

        public static class ExplicitDependenciesTest1Synchronizer extends RedSynchronizer<RedTestContext, String> {

            @Override
            protected Result<String> handle(RedTestContext redTestContext) {
                Result<Boolean> booleanResult = produce(Boolean.class).byExecuting(TestRedSynchronizer::booleanSuccess);
                return ifResult(booleanResult).succeed().produce(String.class).byExecuting(f0 -> {
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
                Result<String> result2 = produceFutureOf(String.class).byExecuting(() ->
                        listenableFutureOf(String.class, TestException.INSTANCE, redTestContext));
                return ifResults(result1, result2).fail().produceFutureOf(TestObject.class)
                        .byExecuting(() -> futureOf(testObjectSuccess(), redTestContext));
            }

        }

        public static class ExplicitDependenciesTest3Synchronizer extends RedSynchronizer<RedTestContext, Boolean> {

            @Override
            protected Result<Boolean> handle(RedTestContext redTestContext) {
                Result<Boolean> result1 = produceFutureOf(Boolean.class).byExecuting(() ->
                        redFutureOf(Boolean.class, TestException.INSTANCE, redTestContext));
                Result<String> result2 = produce(String.class).byExecuting(TestRedSynchronizer::stringSuccess);
                Result<TestObject> result3 = produceFutureOf(TestObject.class).byExecuting(() ->
                        futureOf(testObjectSuccess(), redTestContext));
                return ifResults(result1, result2, result3).finish().produceFutureOf(Boolean.class)
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
                Result<TestObject> result3 = produceFutureOf(TestObject.class).byExecuting(() ->
                        listenableFutureOf(testObjectSuccess(), redTestContext));
                Result<Boolean> result4 = produceFutureOf(Boolean.class).byExecuting(() ->
                        redFutureOf(booleanSuccess(), redTestContext));
                Marker marker1 = execute(pendingMarker ->
                        redTestContext.scheduleTask(FUTURE_DELAY, () -> pendingMarker.fail(TestException.INSTANCE)));
                Marker marker2 = execute(pendingMarker ->
                        redTestContext.scheduleTask(FUTURE_DELAY, () -> pendingMarker.fail(TestException.INSTANCE)));
                return ifResults(result1, result2, result3, result4).succeed().andMarkers(marker1, marker2).fail()
                        .produceFutureOf(String.class).byExecuting((f0, f1, f2, f3) -> {
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
                Result<TestObject> result3 = produceFutureOf(TestObject.class).byExecuting(() ->
                        listenableFutureOf(TestObject.class, TestException.INSTANCE, redTestContext));
                Result<Boolean> result4 = produceFutureOf(Boolean.class).byExecuting(() ->
                        redFutureOf(Boolean.class, TestException.INSTANCE, redTestContext));
                Result<String> result5 = produce(String.class).byExecuting(() -> {
                    throw TestException.INSTANCE;
                });
                Marker marker1 = execute(pendingMarker ->
                        redTestContext.scheduleTask(FUTURE_DELAY, pendingMarker::complete));
                Marker marker2 = execute(pendingMarker ->
                        redTestContext.scheduleTask(FUTURE_DELAY, pendingMarker::complete));
                return ifResults(result1, result2, result3, result4, result5).fail().andMarkers(marker1, marker2)
                        .succeed().produce(TestObject.class).byExecuting(TestRedSynchronizer::testObjectSuccess);
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
                Result<TestObject> result3 = produceFutureOf(TestObject.class).byExecuting(() ->
                        listenableFutureOf(TestObject.class, TestException.INSTANCE, redTestContext));
                Result<Boolean> result4 = produceFutureOf(Boolean.class).byExecuting(() ->
                        redFutureOf(Boolean.class, TestException.INSTANCE, redTestContext));
                Result<String> result5 = produce(String.class).byExecuting(() -> {
                    throw TestException.INSTANCE;
                });
                Result<TestObject> result6 = produceFutureOf(TestObject.class).byExecuting(() ->
                        futureOf(TestObject.class, TestException.INSTANCE, redTestContext));
                Marker marker1 = execute(pendingMarker ->
                        redTestContext.scheduleTask(FUTURE_DELAY, () -> pendingMarker.fail(TestException.INSTANCE)));
                Marker marker2 = execute(pendingMarker ->
                        redTestContext.scheduleTask(FUTURE_DELAY, pendingMarker::complete));
                return ifResults(result1, result2, result3, result4, result5, result6).finish()
                        .andMarkers(marker1, marker2).finish().produceFutureOf(Boolean.class)
                        .byExecuting((f0, f1, f2, f3, f4, f5) -> {
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
                RedTestContext.TimingValidator timingValidator = redTestContext.timingValidator();
                Result<Boolean> result1 = produce(Boolean.class).byExecuting(TestRedSynchronizer::booleanSuccess);
                Result<String> result2 = produceFutureOf(String.class).byExecuting(() ->
                        futureOf(stringSuccess(), redTestContext));
                Result<TestObject> result3 = produceFutureOf(TestObject.class).byExecuting(() ->
                        listenableFutureOf(testObjectSuccess(), redTestContext));
                Result<Boolean> result4 = produceFutureOf(Boolean.class).byExecuting(() ->
                        redFutureOf(booleanSuccess(), redTestContext));
                Result<String> result5 = produce(String.class).byExecuting(TestRedSynchronizer::stringSuccess);
                Result<TestObject> result6 = produceFutureOf(TestObject.class).byExecuting(() ->
                        futureOf(testObjectSuccess(), redTestContext));
                Result<Boolean> result7 = produceFutureOf(Boolean.class).byExecuting(() ->
                        listenableFutureOf(booleanSuccess(), redTestContext));
                Marker marker1 = execute(pendingMarker -> redTestContext.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                Marker marker2 = execute(pendingMarker -> redTestContext.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                return ifResults(result1, result2, result3, result4, result5, result6, result7).succeed()
                        .andMarkers(marker1).finish().andMarkers(marker2).fail()
                        .produceFutureOf(String.class).byExecuting((f0, f1, f2, f3, f4, f5, f6) -> {
                            timingValidator.validatePassed(FUTURE_DELAY);
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
                RedTestContext.TimingValidator timingValidator = redTestContext.timingValidator();
                Result<Boolean> result1 = produce(Boolean.class).byExecuting(() -> {
                    throw TestException.INSTANCE;
                });
                Result<String> result2 = produceFutureOf(String.class).byExecuting(() ->
                        futureOf(String.class, TestException.INSTANCE, redTestContext));
                Result<TestObject> result3 = produceFutureOf(TestObject.class).byExecuting(() ->
                        listenableFutureOf(TestObject.class, TestException.INSTANCE, redTestContext));
                Result<Boolean> result4 = produceFutureOf(Boolean.class).byExecuting(() ->
                        redFutureOf(Boolean.class, TestException.INSTANCE, redTestContext));
                Result<String> result5 = produce(String.class).byExecuting(() -> {
                    throw TestException.INSTANCE;
                });
                Result<TestObject> result6 = produceFutureOf(TestObject.class).byExecuting(() ->
                        futureOf(TestObject.class, TestException.INSTANCE, redTestContext));
                Result<Boolean> result7 = produceFutureOf(Boolean.class).byExecuting(() ->
                        listenableFutureOf(Boolean.class, TestException.INSTANCE, redTestContext));
                Result<String> result8 = produceFutureOf(String.class).byExecuting(() ->
                        redFutureOf(String.class, TestException.INSTANCE, redTestContext));
                Marker marker1 = execute(pendingMarker -> redTestContext.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                Marker marker2 = execute(pendingMarker -> redTestContext.scheduleTask(FUTURE_DELAY,
                        pendingMarker::complete));
                return ifResults(result1, result2, result3, result4, result5, result6, result7, result8).fail()
                        .andMarkers(marker1).fail().andMarkers(marker2).succeed()
                        .produceFutureOf(TestObject.class).byExecuting(() -> {
                            timingValidator.validatePassed(FUTURE_DELAY);
                            return redFutureOf(testObjectSuccess(), redTestContext);
                        });
            }

        }

        public static class ExplicitDependenciesTest9Synchronizer extends RedSynchronizer<RedTestContext, Boolean> {

            @Override
            protected Result<Boolean> handle(RedTestContext redTestContext) {
                RedTestContext.TimingValidator timingValidator = redTestContext.timingValidator();
                Result<Boolean> result1 = produce(Boolean.class).byExecuting(() -> {
                    throw TestException.INSTANCE;
                });
                Result<String> result2 = produceFutureOf(String.class).byExecuting(() ->
                        futureOf(String.class, TestException.INSTANCE, redTestContext));
                Result<TestObject> result3 = produceFutureOf(TestObject.class).byExecuting(() ->
                        listenableFutureOf(TestObject.class, TestException.INSTANCE, redTestContext));
                Result<Boolean> result4 = produceFutureOf(Boolean.class).byExecuting(() ->
                        redFutureOf(Boolean.class, TestException.INSTANCE, redTestContext));
                Result<String> result5 = produce(String.class).byExecuting(() -> {
                    throw TestException.INSTANCE;
                });
                Result<TestObject> result6 = produceFutureOf(TestObject.class).byExecuting(() ->
                        futureOf(TestObject.class, TestException.INSTANCE, redTestContext));
                Result<Boolean> result7 = produceFutureOf(Boolean.class).byExecuting(() ->
                        listenableFutureOf(Boolean.class, TestException.INSTANCE, redTestContext));
                Result<String> result8 = produceFutureOf(String.class).byExecuting(() ->
                        redFutureOf(String.class, TestException.INSTANCE, redTestContext));
                Result<TestObject> result9 = produce(TestObject.class)
                        .byExecuting(TestRedSynchronizer::testObjectSuccess);
                Marker marker1 = execute(pendingMarker -> redTestContext.scheduleTask(FUTURE_DELAY,
                        pendingMarker::complete));
                Marker marker2 = execute(pendingMarker -> redTestContext.scheduleTask(FUTURE_DELAY,
                        pendingMarker::complete));
                return ifResults(result1, result2, result3, result4, result5, result6, result7, result8, result9)
                        .finish().andMarkers(marker1).succeed().andMarkers(marker2).succeed()
                        .produce(Boolean.class).byExecuting((f0, f1, f2, f3, f4, f5, f6, f7, f8) -> {
                            timingValidator.validatePassed(FUTURE_DELAY);
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
                RedTestContext.TimingValidator timingValidator = context._redTestContext.timingValidator();
                Result<Boolean> result1 = produce(Boolean.class).byExecuting(TestRedSynchronizer::booleanSuccess);
                Result<String> result2 = produceFutureOf(String.class).byExecuting(() ->
                        futureOf(stringSuccess(), context._redTestContext));
                Result<TestObject> result3 = produceFutureOf(TestObject.class).byExecuting(() ->
                        listenableFutureOf(testObjectSuccess(), context._redTestContext));
                Result<Boolean> result4 = produceFutureOf(Boolean.class).byExecuting(() ->
                        redFutureOf(booleanSuccess(), context._redTestContext));
                Result<String> result5 = produce(String.class).byExecuting(TestRedSynchronizer::stringSuccess);
                Result<TestObject> result6 = produceFutureOf(TestObject.class).byExecuting(() ->
                        futureOf(testObjectSuccess(), context._redTestContext));
                Result<Boolean> result7 = produceFutureOf(Boolean.class).byExecuting(() ->
                        listenableFutureOf(booleanSuccess(), context._redTestContext));
                Result<String> result8 = produceFutureOf(String.class).byExecuting(() ->
                        redFutureOf(stringSuccess(), context._redTestContext));
                Result<TestObject> result9 = produce(TestObject.class)
                        .byExecuting(TestRedSynchronizer::testObjectSuccess);
                Result<Boolean> result10 = produceFutureOf(Boolean.class).byExecuting(() ->
                        futureOf(booleanSuccess(), context._redTestContext));
                return ifResults(result1, result2, result3, result4, result5, result6, result7, result8, result9,
                        result10).succeed().execute((pendingMarker, f0, f1, f2, f3, f4, f5, f6, f7, f8, f9) -> {
                    timingValidator.validatePassed(FUTURE_DELAY);
                    context._redTestContext.assertions.assertTrue(checkBooleanSuccess(f0));
                    context._redTestContext.assertions.assertTrue(checkStringSuccess(f1));
                    context._redTestContext.assertions.assertTrue(checkTestObjectSuccess(f2));
                    context._redTestContext.assertions.assertTrue(checkBooleanSuccess(f3));
                    context._redTestContext.assertions.assertTrue(checkStringSuccess(f4));
                    context._redTestContext.assertions.assertTrue(checkTestObjectSuccess(f5));
                    context._redTestContext.assertions.assertTrue(checkBooleanSuccess(f6));
                    context._redTestContext.assertions.assertTrue(checkStringSuccess(f7));
                    context._redTestContext.assertions.assertTrue(checkTestObjectSuccess(f8));
                    context._redTestContext.assertions.assertTrue(checkBooleanSuccess(f9));
                    context._finished.set(true);
                    pendingMarker.complete();
                });
            }

        }

        public static class ExplicitDependenciesTest11Synchronizer extends RedVoidSynchronizer<CommandTestContext> {

            @Override
            protected Marker handle(CommandTestContext context) {
                RedTestContext.TimingValidator timingValidator = context._redTestContext.timingValidator();
                Result<Boolean> result1 = produceFutureOf(Boolean.class).byExecuting(() ->
                        futureOf(Boolean.class, TestException.INSTANCE, context._redTestContext));
                return ifResults(result1).fail().execute(pendingMarker -> {
                    timingValidator.validatePassed(FUTURE_DELAY);
                    context._finished.set(true);
                    pendingMarker.complete();
                });
            }

        }

        public static class ExplicitDependenciesTest12Synchronizer extends RedVoidSynchronizer<CommandTestContext> {

            @Override
            protected Marker handle(CommandTestContext context) {
                RedTestContext.TimingValidator timingValidator = context._redTestContext.timingValidator();
                Result<Boolean> result1 = produceFutureOf(Boolean.class).byExecuting(() ->
                        futureOf(Boolean.class, TestException.INSTANCE, context._redTestContext));
                Result<String> result2 = produceFutureOf(String.class).byExecuting(() ->
                        listenableFutureOf(stringSuccess(), context._redTestContext));
                return ifResults(result1, result2).finish().execute((pendingMarker, f0, f1) -> {
                    timingValidator.validatePassed(FUTURE_DELAY);
                    context._redTestContext.assertions.assertNull(f0);
                    context._redTestContext.assertions.assertTrue(checkStringSuccess(f1));
                    context._finished.set(true);
                    pendingMarker.complete();
                });
            }

        }

        public static class ExplicitDependenciesTest13Synchronizer extends RedVoidSynchronizer<CommandTestContext> {

            @Override
            protected Marker handle(CommandTestContext context) {
                RedTestContext.TimingValidator timingValidator = context._redTestContext.timingValidator();
                Result<Boolean> result1 = produceFutureOf(Boolean.class).byExecuting(() ->
                        futureOf(booleanSuccess(), context._redTestContext));
                Result<String> result2 = produceFutureOf(String.class).byExecuting(() ->
                        listenableFutureOf(stringSuccess(), context._redTestContext));
                Result<TestObject> result3 = produceFutureOf(TestObject.class).byExecuting(() ->
                        redFutureOf(testObjectSuccess(), context._redTestContext));
                Marker marker1 = execute(pendingMarker -> context._redTestContext.scheduleTask(FUTURE_DELAY,
                        pendingMarker::complete));
                Marker marker2 = execute(pendingMarker -> context._redTestContext.scheduleTask(FUTURE_DELAY,
                        pendingMarker::complete));
                return ifResults(result1, result2, result3).succeed().andMarkers(marker1, marker2).succeed()
                        .execute((pendingMarker, f0, f1, f2) -> {
                            timingValidator.validatePassed(FUTURE_DELAY);
                            context._redTestContext.assertions.assertTrue(checkBooleanSuccess(f0));
                            context._redTestContext.assertions.assertTrue(checkStringSuccess(f1));
                            context._redTestContext.assertions.assertTrue(checkTestObjectSuccess(f2));
                            context._finished.set(true);
                            pendingMarker.complete();
                        });
            }

        }

        public static class ExplicitDependenciesTest14Synchronizer extends RedVoidSynchronizer<CommandTestContext> {

            @Override
            protected Marker handle(CommandTestContext context) {
                RedTestContext.TimingValidator timingValidator = context._redTestContext.timingValidator();
                Result<Boolean> result1 = produceFutureOf(Boolean.class).byExecuting(() ->
                        futureOf(Boolean.class, TestException.INSTANCE, context._redTestContext));
                Result<String> result2 = produceFutureOf(String.class).byExecuting(() ->
                        listenableFutureOf(String.class, TestException.INSTANCE, context._redTestContext));
                Result<TestObject> result3 = produceFutureOf(TestObject.class).byExecuting(() ->
                        redFutureOf(TestObject.class, TestException.INSTANCE, context._redTestContext));
                Result<Boolean> result4 = produce(Boolean.class).byExecuting(() -> {
                    throw TestException.INSTANCE;
                });
                Marker marker1 = execute(pendingMarker -> context._redTestContext.scheduleTask(FUTURE_DELAY,
                        pendingMarker::complete));
                Marker marker2 = execute(pendingMarker -> context._redTestContext.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                return ifResults(result1, result2, result3, result4).fail().andMarkers(marker1, marker2).finish()
                        .execute((pendingMarker) -> {
                            timingValidator.validatePassed(FUTURE_DELAY);
                            context._finished.set(true);
                            pendingMarker.complete();
                        });
            }

        }

        public static class ExplicitDependenciesTest15Synchronizer extends RedVoidSynchronizer<CommandTestContext> {

            @Override
            protected Marker handle(CommandTestContext context) {
                RedTestContext.TimingValidator timingValidator = context._redTestContext.timingValidator();
                Result<Boolean> result1 = produceFutureOf(Boolean.class).byExecuting(() ->
                        futureOf(Boolean.class, TestException.INSTANCE, context._redTestContext));
                Result<String> result2 = produceFutureOf(String.class).byExecuting(() ->
                        listenableFutureOf(String.class, TestException.INSTANCE, context._redTestContext));
                Result<TestObject> result3 = produceFutureOf(TestObject.class).byExecuting(() ->
                        redFutureOf(TestObject.class, TestException.INSTANCE, context._redTestContext));
                Result<Boolean> result4 = produce(Boolean.class).byExecuting(() -> {
                    throw TestException.INSTANCE;
                });
                Result<String> result5 = produceFutureOf(String.class).byExecuting(() ->
                        futureOf(stringSuccess(), context._redTestContext));
                Marker marker1 = execute(pendingMarker -> context._redTestContext.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                Marker marker2 = execute(pendingMarker -> context._redTestContext.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                return ifResults(result1, result2, result3, result4, result5).finish().andMarkers(marker1, marker2)
                        .fail().execute((pendingMarker, f0, f1, f2, f3, f4) -> {
                            timingValidator.validatePassed(FUTURE_DELAY);
                            context._redTestContext.assertions.assertNull(f0);
                            context._redTestContext.assertions.assertNull(f1);
                            context._redTestContext.assertions.assertNull(f2);
                            context._redTestContext.assertions.assertNull(f3);
                            context._redTestContext.assertions.assertTrue(checkStringSuccess(f4));
                            context._finished.set(true);
                            pendingMarker.complete();
                        });
            }

        }

        public static class ExplicitDependenciesTest16Synchronizer extends RedVoidSynchronizer<CommandTestContext> {

            @Override
            protected Marker handle(CommandTestContext context) {
                RedTestContext.TimingValidator timingValidator = context._redTestContext.timingValidator();
                Result<Boolean> result1 = produceFutureOf(Boolean.class).byExecuting(() ->
                        futureOf(booleanSuccess(), context._redTestContext));
                Result<String> result2 = produceFutureOf(String.class).byExecuting(() ->
                        listenableFutureOf(stringSuccess(), context._redTestContext));
                Result<TestObject> result3 = produceFutureOf(TestObject.class).byExecuting(() ->
                        redFutureOf(testObjectSuccess(), context._redTestContext));
                Result<Boolean> result4 = produce(Boolean.class).byExecuting(TestRedSynchronizer::booleanSuccess);
                Result<String> result5 = produceFutureOf(String.class).byExecuting(() ->
                        futureOf(stringSuccess(), context._redTestContext));
                Result<TestObject> result6 = produceFutureOf(TestObject.class).byExecuting(() ->
                        listenableFutureOf(testObjectSuccess(), context._redTestContext));
                Marker marker1 = execute(pendingMarker -> context._redTestContext.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                Marker marker2 = execute(pendingMarker -> context._redTestContext.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                return ifResults(result1, result2, result3, result4, result5, result6).succeed()
                        .andMarkers(marker1).finish().andMarkers(marker2).fail()
                        .execute((pendingMarker, f0, f1, f2, f3, f4, f5) -> {
                            timingValidator.validatePassed(FUTURE_DELAY);
                            context._redTestContext.assertions.assertTrue(checkBooleanSuccess(f0));
                            context._redTestContext.assertions.assertTrue(checkStringSuccess(f1));
                            context._redTestContext.assertions.assertTrue(checkTestObjectSuccess(f2));
                            context._redTestContext.assertions.assertTrue(checkBooleanSuccess(f3));
                            context._redTestContext.assertions.assertTrue(checkStringSuccess(f4));
                            context._redTestContext.assertions.assertTrue(checkTestObjectSuccess(f5));
                            context._finished.set(true);
                            pendingMarker.complete();
                        });
            }

        }

        public static class ExplicitDependenciesTest17Synchronizer extends RedVoidSynchronizer<CommandTestContext> {

            @Override
            protected Marker handle(CommandTestContext context) {
                RedTestContext.TimingValidator timingValidator = context._redTestContext.timingValidator();
                Result<Boolean> result1 = produceFutureOf(Boolean.class).byExecuting(() ->
                        futureOf(Boolean.class, TestException.INSTANCE, context._redTestContext));
                Result<String> result2 = produceFutureOf(String.class).byExecuting(() ->
                        listenableFutureOf(String.class, TestException.INSTANCE, context._redTestContext));
                Result<TestObject> result3 = produceFutureOf(TestObject.class).byExecuting(() ->
                        redFutureOf(TestObject.class, TestException.INSTANCE, context._redTestContext));
                Result<Boolean> result4 = produce(Boolean.class).byExecuting(() -> {
                    throw TestException.INSTANCE;
                });
                Result<String> result5 = produceFutureOf(String.class).byExecuting(() ->
                        futureOf(String.class, TestException.INSTANCE, context._redTestContext));
                Result<TestObject> result6 = produceFutureOf(TestObject.class).byExecuting(() ->
                        listenableFutureOf(TestObject.class, TestException.INSTANCE, context._redTestContext));
                Result<Boolean> result7 = produceFutureOf(Boolean.class).byExecuting(() ->
                        redFutureOf(Boolean.class, TestException.INSTANCE, context._redTestContext));
                Marker marker1 = execute(pendingMarker -> context._redTestContext.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                Marker marker2 = execute(pendingMarker -> context._redTestContext.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                return ifResults(result1, result2, result3, result4, result5, result6, result7).fail()
                        .andMarkers(marker1).fail().andMarkers(marker2).fail()
                        .execute((pendingMarker) -> {
                            timingValidator.validatePassed(FUTURE_DELAY);
                            context._finished.set(true);
                            pendingMarker.complete();
                        });
            }

        }

        public static class ExplicitDependenciesTest18Synchronizer extends RedVoidSynchronizer<CommandTestContext> {

            @Override
            protected Marker handle(CommandTestContext context) {
                RedTestContext.TimingValidator timingValidator = context._redTestContext.timingValidator();
                Result<Boolean> result1 = produceFutureOf(Boolean.class).byExecuting(() ->
                        futureOf(Boolean.class, TestException.INSTANCE, context._redTestContext));
                Result<String> result2 = produceFutureOf(String.class).byExecuting(() ->
                        listenableFutureOf(String.class, TestException.INSTANCE, context._redTestContext));
                Result<TestObject> result3 = produceFutureOf(TestObject.class).byExecuting(() ->
                        redFutureOf(TestObject.class, TestException.INSTANCE, context._redTestContext));
                Result<Boolean> result4 = produce(Boolean.class).byExecuting(TestRedSynchronizer::booleanSuccess);
                Result<String> result5 = produceFutureOf(String.class).byExecuting(() ->
                        futureOf(String.class, TestException.INSTANCE, context._redTestContext));
                Result<TestObject> result6 = produceFutureOf(TestObject.class).byExecuting(() ->
                        listenableFutureOf(TestObject.class, TestException.INSTANCE, context._redTestContext));
                Result<Boolean> result7 = produceFutureOf(Boolean.class).byExecuting(() ->
                        redFutureOf(Boolean.class, TestException.INSTANCE, context._redTestContext));
                Result<String> result8 = produce(String.class).byExecuting(TestRedSynchronizer::stringSuccess);
                Marker marker1 = execute(pendingMarker -> context._redTestContext.scheduleTask(FUTURE_DELAY,
                        pendingMarker::complete));
                Marker marker2 = execute(pendingMarker -> context._redTestContext.scheduleTask(FUTURE_DELAY,
                        pendingMarker::complete));
                return ifResults(result1, result2, result3, result4, result5, result6, result7, result8).finish()
                        .andMarkers(marker1).succeed().andMarkers(marker2).finish()
                        .execute((pendingMarker, f0, f1, f2, f3, f4, f5, f6, f7) -> {
                            timingValidator.validatePassed(FUTURE_DELAY);
                            context._redTestContext.assertions.assertNull(f0);
                            context._redTestContext.assertions.assertNull(f1);
                            context._redTestContext.assertions.assertNull(f2);
                            context._redTestContext.assertions.assertTrue(checkBooleanSuccess(f3));
                            context._redTestContext.assertions.assertNull(f4);
                            context._redTestContext.assertions.assertNull(f5);
                            context._redTestContext.assertions.assertNull(f6);
                            context._redTestContext.assertions.assertTrue(checkStringSuccess(f7));
                            context._finished.set(true);
                            pendingMarker.complete();
                        });
            }

        }

        public static class NDependenciesTest1Synchronizer extends RedSynchronizer<RedTestContext, Boolean> {

            @Override
            protected Result<Boolean> handle(RedTestContext redTestContext) {
                RedTestContext.TimingValidator timingValidator = redTestContext.timingValidator();
                Result<Boolean> result1 = produce(Boolean.class).byExecuting(TestRedSynchronizer::booleanSuccess);
                Result<String> result2 = produceFutureOf(String.class).byExecuting(() ->
                        futureOf(stringSuccess(), redTestContext));
                Result<TestObject> result3 = produceFutureOf(TestObject.class).byExecuting(() ->
                        listenableFutureOf(testObjectSuccess(), redTestContext));
                Result<Boolean> result4 = produceFutureOf(Boolean.class).byExecuting(() ->
                        redFutureOf(booleanSuccess(), redTestContext));
                Result<String> result5 = produce(String.class).byExecuting(TestRedSynchronizer::stringSuccess);
                Result<TestObject> result6 = produceFutureOf(TestObject.class).byExecuting(() ->
                        futureOf(testObjectSuccess(), redTestContext));
                Result<Boolean> result7 = produceFutureOf(Boolean.class).byExecuting(() ->
                        listenableFutureOf(booleanSuccess(), redTestContext));
                Result<String> result8 = produceFutureOf(String.class).byExecuting(() ->
                        redFutureOf(stringSuccess(), redTestContext));
                Result<TestObject> result9 = produce(TestObject.class)
                        .byExecuting(TestRedSynchronizer::testObjectSuccess);
                Result<Boolean> result10 = produceFutureOf(Boolean.class).byExecuting(() ->
                        futureOf(booleanSuccess(), redTestContext));
                Result<String> result11 = produceFutureOf(String.class).byExecuting(() ->
                        listenableFutureOf(stringSuccess(), redTestContext));
                return ifResults(result1, result2, result3, result4, result5, result6, result7, result8, result9,
                        result10, result11).succeed().produce(Boolean.class).byExecuting(results -> {
                    timingValidator.validatePassed(FUTURE_DELAY);
                    redTestContext.assertions.assertTrue(checkBooleanSuccess(results.result(0, Boolean.class)));
                    redTestContext.assertions.assertTrue(checkStringSuccess(results.result(1, String.class)));
                    redTestContext.assertions.assertTrue(
                            checkTestObjectSuccess(results.result(2, TestObject.class)));
                    redTestContext.assertions.assertTrue(checkBooleanSuccess(results.result(3, Boolean.class)));
                    redTestContext.assertions.assertTrue(checkStringSuccess(results.result(4, String.class)));
                    redTestContext.assertions.assertTrue(
                            checkTestObjectSuccess(results.result(5, TestObject.class)));
                    redTestContext.assertions.assertTrue(checkBooleanSuccess(results.result(6, Boolean.class)));
                    redTestContext.assertions.assertTrue(checkStringSuccess(results.result(7, String.class)));
                    redTestContext.assertions.assertTrue(
                            checkTestObjectSuccess(results.result(8, TestObject.class)));
                    redTestContext.assertions.assertTrue(checkBooleanSuccess(results.result(9, Boolean.class)));
                    redTestContext.assertions.assertTrue(checkStringSuccess(results.result(10, String.class)));
                    try {
                        results.result(0, String.class);
                        redTestContext.fail("should have failed result 0 as string");
                    } catch (IllegalArgumentException ignored) {}
                    return booleanSuccess();
                });
            }

        }

        public static class NDependenciesTest2Synchronizer extends RedSynchronizer<RedTestContext, String> {

            @Override
            protected Result<String> handle(RedTestContext redTestContext) {
                RedTestContext.TimingValidator timingValidator = redTestContext.timingValidator();
                Result<Boolean> result1 = produce(Boolean.class).byExecuting(() -> {
                    throw TestException.INSTANCE;
                });
                Result<String> result2 = produceFutureOf(String.class).byExecuting(() ->
                        futureOf(String.class, TestException.INSTANCE, redTestContext));
                Result<TestObject> result3 = produceFutureOf(TestObject.class).byExecuting(() ->
                        listenableFutureOf(TestObject.class, TestException.INSTANCE, redTestContext));
                Result<Boolean> result4 = produceFutureOf(Boolean.class).byExecuting(() ->
                        redFutureOf(Boolean.class, TestException.INSTANCE, redTestContext));
                Result<String> result5 = produce(String.class).byExecuting(() -> {
                    throw TestException.INSTANCE;
                });
                Result<TestObject> result6 = produceFutureOf(TestObject.class).byExecuting(() ->
                        futureOf(TestObject.class, TestException.INSTANCE, redTestContext));
                Result<Boolean> result7 = produceFutureOf(Boolean.class).byExecuting(() ->
                        listenableFutureOf(Boolean.class, TestException.INSTANCE, redTestContext));
                Result<String> result8 = produceFutureOf(String.class).byExecuting(() ->
                        redFutureOf(String.class, TestException.INSTANCE, redTestContext));
                Result<TestObject> result9 = produce(TestObject.class).byExecuting(() -> {
                    throw TestException.INSTANCE;
                });
                Result<Boolean> result10 = produceFutureOf(Boolean.class).byExecuting(() ->
                        futureOf(Boolean.class, TestException.INSTANCE, redTestContext));
                Result<String> result11 = produceFutureOf(String.class).byExecuting(() ->
                        listenableFutureOf(String.class, TestException.INSTANCE, redTestContext));
                return ifResults(result1, result2, result3, result4, result5, result6, result7, result8, result9,
                        result10, result11).fail().produce(String.class).byExecuting(() -> {
                    timingValidator.validatePassed(FUTURE_DELAY);
                    return stringSuccess();
                });
            }

        }

        public static class NDependenciesTest3Synchronizer extends RedSynchronizer<RedTestContext, TestObject> {

            @Override
            protected Result<TestObject> handle(RedTestContext redTestContext) {
                RedTestContext.TimingValidator timingValidator = redTestContext.timingValidator();
                Result<Boolean> result1 = produce(Boolean.class).byExecuting(TestRedSynchronizer::booleanSuccess);
                Result<String> result2 = produceFutureOf(String.class).byExecuting(() ->
                        futureOf(stringSuccess(), redTestContext));
                Result<TestObject> result3 = produceFutureOf(TestObject.class).byExecuting(() ->
                        listenableFutureOf(testObjectSuccess(), redTestContext));
                Result<Boolean> result4 = produceFutureOf(Boolean.class).byExecuting(() ->
                        redFutureOf(booleanSuccess(), redTestContext));
                Result<String> result5 = produce(String.class).byExecuting(TestRedSynchronizer::stringSuccess);
                Result<TestObject> result6 = produceFutureOf(TestObject.class).byExecuting(() ->
                        futureOf(testObjectSuccess(), redTestContext));
                Result<Boolean> result7 = produceFutureOf(Boolean.class).byExecuting(() ->
                        listenableFutureOf(booleanSuccess(), redTestContext));
                Result<String> result8 = produceFutureOf(String.class).byExecuting(() ->
                        redFutureOf(String.class, TestException.INSTANCE, redTestContext));
                Result<TestObject> result9 = produce(TestObject.class).byExecuting(() -> {
                    throw TestException.INSTANCE;
                });
                Result<Boolean> result10 = produceFutureOf(Boolean.class).byExecuting(() ->
                        futureOf(Boolean.class, TestException.INSTANCE, redTestContext));
                Result<String> result11 = produceFutureOf(String.class).byExecuting(() ->
                        listenableFutureOf(String.class, TestException.INSTANCE, redTestContext));
                return ifResults(result1, result2, result3, result4, result5, result6, result7, result8, result9,
                        result10, result11).finish().produce(TestObject.class).byExecuting(results -> {
                    timingValidator.validatePassed(FUTURE_DELAY);
                    redTestContext.assertions.assertTrue(checkBooleanSuccess(results.result(0, Boolean.class)));
                    redTestContext.assertions.assertTrue(checkStringSuccess(results.result(1, String.class)));
                    redTestContext.assertions.assertTrue(
                            checkTestObjectSuccess(results.result(2, TestObject.class)));
                    redTestContext.assertions.assertTrue(checkBooleanSuccess(results.result(3, Boolean.class)));
                    redTestContext.assertions.assertTrue(checkStringSuccess(results.result(4, String.class)));
                    redTestContext.assertions.assertTrue(
                            checkTestObjectSuccess(results.result(5, TestObject.class)));
                    redTestContext.assertions.assertTrue(checkBooleanSuccess(results.result(6, Boolean.class)));
                    redTestContext.assertions.assertNull(results.result(7, String.class));
                    redTestContext.assertions.assertNull(results.result(8, TestObject.class));
                    redTestContext.assertions.assertNull(results.result(9, Boolean.class));
                    redTestContext.assertions.assertNull(results.result(10, String.class));
                    try {
                        results.result(0, String.class);
                        redTestContext.fail("should have failed result 0 as string");
                    } catch (IllegalArgumentException ignored) {}
                    return testObjectSuccess();
                });
            }

        }

        public static class NDependenciesTest4Synchronizer extends RedSynchronizer<RedTestContext, Boolean> {

            @Override
            protected Result<Boolean> handle(RedTestContext redTestContext) {
                RedTestContext.TimingValidator timingValidator = redTestContext.timingValidator();
                Result<Boolean> result1 = produce(Boolean.class).byExecuting(TestRedSynchronizer::booleanSuccess);
                Result<String> result2 = produceFutureOf(String.class).byExecuting(() ->
                        futureOf(stringSuccess(), redTestContext));
                Result<TestObject> result3 = produceFutureOf(TestObject.class).byExecuting(() ->
                        listenableFutureOf(testObjectSuccess(), redTestContext));
                Result<Boolean> result4 = produceFutureOf(Boolean.class).byExecuting(() ->
                        redFutureOf(booleanSuccess(), redTestContext));
                Result<String> result5 = produce(String.class).byExecuting(TestRedSynchronizer::stringSuccess);
                Result<TestObject> result6 = produceFutureOf(TestObject.class).byExecuting(() ->
                        futureOf(testObjectSuccess(), redTestContext));
                Result<Boolean> result7 = produceFutureOf(Boolean.class).byExecuting(() ->
                        listenableFutureOf(booleanSuccess(), redTestContext));
                Result<String> result8 = produceFutureOf(String.class).byExecuting(() ->
                        redFutureOf(stringSuccess(), redTestContext));
                Result<TestObject> result9 = produce(TestObject.class)
                        .byExecuting(TestRedSynchronizer::testObjectSuccess);
                Result<Boolean> result10 = produceFutureOf(Boolean.class).byExecuting(() ->
                        futureOf(booleanSuccess(), redTestContext));
                Result<String> result11 = produceFutureOf(String.class).byExecuting(() ->
                        listenableFutureOf(stringSuccess(), redTestContext));
                Marker marker1 = execute(pendingMarker -> redTestContext.scheduleTask(FUTURE_DELAY,
                        pendingMarker::complete));
                Marker marker2 = execute(pendingMarker -> redTestContext.scheduleTask(FUTURE_DELAY,
                        pendingMarker::complete));
                return ifResults(result1, result2, result3, result4, result5, result6, result7, result8, result9,
                        result10, result11).succeed().andMarkers(marker1, marker2).succeed().produce(Boolean.class)
                        .byExecuting(results -> {
                            timingValidator.validatePassed(FUTURE_DELAY);
                            redTestContext.assertions.assertTrue(checkBooleanSuccess(results.result(0, Boolean.class)));
                            redTestContext.assertions.assertTrue(checkStringSuccess(results.result(1, String.class)));
                            redTestContext.assertions.assertTrue(
                                    checkTestObjectSuccess(results.result(2, TestObject.class)));
                            redTestContext.assertions.assertTrue(checkBooleanSuccess(results.result(3, Boolean.class)));
                            redTestContext.assertions.assertTrue(checkStringSuccess(results.result(4, String.class)));
                            redTestContext.assertions.assertTrue(
                                    checkTestObjectSuccess(results.result(5, TestObject.class)));
                            redTestContext.assertions.assertTrue(checkBooleanSuccess(results.result(6, Boolean.class)));
                            redTestContext.assertions.assertTrue(checkStringSuccess(results.result(7, String.class)));
                            redTestContext.assertions.assertTrue(
                                    checkTestObjectSuccess(results.result(8, TestObject.class)));
                            redTestContext.assertions.assertTrue(checkBooleanSuccess(results.result(9, Boolean.class)));
                            redTestContext.assertions.assertTrue(checkStringSuccess(results.result(10, String.class)));
                            try {
                                results.result(0, String.class);
                                redTestContext.fail("should have failed result 0 as string");
                            } catch (IllegalArgumentException ignored) {}
                            return booleanSuccess();
                        });
            }

        }

        public static class NDependenciesTest5Synchronizer extends RedSynchronizer<RedTestContext, String> {

            @Override
            protected Result<String> handle(RedTestContext redTestContext) {
                RedTestContext.TimingValidator timingValidator = redTestContext.timingValidator();
                Result<Boolean> result1 = produce(Boolean.class).byExecuting(() -> {
                    throw TestException.INSTANCE;
                });
                Result<String> result2 = produceFutureOf(String.class).byExecuting(() ->
                        futureOf(String.class, TestException.INSTANCE, redTestContext));
                Result<TestObject> result3 = produceFutureOf(TestObject.class).byExecuting(() ->
                        listenableFutureOf(TestObject.class, TestException.INSTANCE, redTestContext));
                Result<Boolean> result4 = produceFutureOf(Boolean.class).byExecuting(() ->
                        redFutureOf(Boolean.class, TestException.INSTANCE, redTestContext));
                Result<String> result5 = produce(String.class).byExecuting(() -> {
                    throw TestException.INSTANCE;
                });
                Result<TestObject> result6 = produceFutureOf(TestObject.class).byExecuting(() ->
                        futureOf(TestObject.class, TestException.INSTANCE, redTestContext));
                Result<Boolean> result7 = produceFutureOf(Boolean.class).byExecuting(() ->
                        listenableFutureOf(Boolean.class, TestException.INSTANCE, redTestContext));
                Result<String> result8 = produceFutureOf(String.class).byExecuting(() ->
                        redFutureOf(String.class, TestException.INSTANCE, redTestContext));
                Result<TestObject> result9 = produce(TestObject.class).byExecuting(() -> {
                    throw TestException.INSTANCE;
                });
                Result<Boolean> result10 = produceFutureOf(Boolean.class).byExecuting(() ->
                        futureOf(Boolean.class, TestException.INSTANCE, redTestContext));
                Result<String> result11 = produceFutureOf(String.class).byExecuting(() ->
                        listenableFutureOf(String.class, TestException.INSTANCE, redTestContext));
                Marker marker1 = execute(pendingMarker -> redTestContext.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                Marker marker2 = execute(pendingMarker -> redTestContext.scheduleTask(FUTURE_DELAY,
                        pendingMarker::complete));
                return ifResults(result1, result2, result3, result4, result5, result6, result7, result8, result9,
                        result10, result11).fail().andMarkers(marker1, marker2).finish().produce(String.class)
                        .byExecuting(() -> {
                            timingValidator.validatePassed(FUTURE_DELAY);
                            return stringSuccess();
                        });
            }

        }

        public static class NDependenciesTest6Synchronizer extends RedSynchronizer<RedTestContext, TestObject> {

            @Override
            protected Result<TestObject> handle(RedTestContext redTestContext) {
                RedTestContext.TimingValidator timingValidator = redTestContext.timingValidator();
                Result<Boolean> result1 = produce(Boolean.class).byExecuting(TestRedSynchronizer::booleanSuccess);
                Result<String> result2 = produceFutureOf(String.class).byExecuting(() ->
                        futureOf(stringSuccess(), redTestContext));
                Result<TestObject> result3 = produceFutureOf(TestObject.class).byExecuting(() ->
                        listenableFutureOf(testObjectSuccess(), redTestContext));
                Result<Boolean> result4 = produceFutureOf(Boolean.class).byExecuting(() ->
                        redFutureOf(booleanSuccess(), redTestContext));
                Result<String> result5 = produce(String.class).byExecuting(TestRedSynchronizer::stringSuccess);
                Result<TestObject> result6 = produceFutureOf(TestObject.class).byExecuting(() ->
                        futureOf(testObjectSuccess(), redTestContext));
                Result<Boolean> result7 = produceFutureOf(Boolean.class).byExecuting(() ->
                        listenableFutureOf(booleanSuccess(), redTestContext));
                Result<String> result8 = produceFutureOf(String.class).byExecuting(() ->
                        redFutureOf(String.class, TestException.INSTANCE, redTestContext));
                Result<TestObject> result9 = produce(TestObject.class).byExecuting(() -> {
                    throw TestException.INSTANCE;
                });
                Result<Boolean> result10 = produceFutureOf(Boolean.class).byExecuting(() ->
                        futureOf(Boolean.class, TestException.INSTANCE, redTestContext));
                Result<String> result11 = produceFutureOf(String.class).byExecuting(() ->
                        listenableFutureOf(String.class, TestException.INSTANCE, redTestContext));
                Marker marker1 = execute(pendingMarker -> redTestContext.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                Marker marker2 = execute(pendingMarker -> redTestContext.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                return ifResults(result1, result2, result3, result4, result5, result6, result7, result8, result9,
                        result10, result11).finish().andMarkers(marker1, marker2).fail().produce(TestObject.class)
                        .byExecuting(results -> {
                            timingValidator.validatePassed(FUTURE_DELAY);
                            redTestContext.assertions.assertTrue(checkBooleanSuccess(results.result(0, Boolean.class)));
                            redTestContext.assertions.assertTrue(checkStringSuccess(results.result(1, String.class)));
                            redTestContext.assertions.assertTrue(
                                    checkTestObjectSuccess(results.result(2, TestObject.class)));
                            redTestContext.assertions.assertTrue(checkBooleanSuccess(results.result(3, Boolean.class)));
                            redTestContext.assertions.assertTrue(checkStringSuccess(results.result(4, String.class)));
                            redTestContext.assertions.assertTrue(
                                    checkTestObjectSuccess(results.result(5, TestObject.class)));
                            redTestContext.assertions.assertTrue(checkBooleanSuccess(results.result(6, Boolean.class)));
                            redTestContext.assertions.assertNull(results.result(7, String.class));
                            redTestContext.assertions.assertNull(results.result(8, TestObject.class));
                            redTestContext.assertions.assertNull(results.result(9, Boolean.class));
                            redTestContext.assertions.assertNull(results.result(10, String.class));
                            try {
                                results.result(0, String.class);
                                redTestContext.fail("should have failed result 0 as string");
                            } catch (IllegalArgumentException ignored) {}
                            return testObjectSuccess();
                        });
            }

        }

        public static class NDependenciesTest7Synchronizer extends RedSynchronizer<RedTestContext, Boolean> {

            @Override
            protected Result<Boolean> handle(RedTestContext redTestContext) {
                RedTestContext.TimingValidator timingValidator = redTestContext.timingValidator();
                Result<Boolean> result1 = produce(Boolean.class).byExecuting(TestRedSynchronizer::booleanSuccess);
                Result<String> result2 = produceFutureOf(String.class).byExecuting(() ->
                        futureOf(stringSuccess(), redTestContext));
                Result<TestObject> result3 = produceFutureOf(TestObject.class).byExecuting(() ->
                        listenableFutureOf(testObjectSuccess(), redTestContext));
                Result<Boolean> result4 = produceFutureOf(Boolean.class).byExecuting(() ->
                        redFutureOf(booleanSuccess(), redTestContext));
                Result<String> result5 = produce(String.class).byExecuting(TestRedSynchronizer::stringSuccess);
                Result<TestObject> result6 = produceFutureOf(TestObject.class).byExecuting(() ->
                        futureOf(testObjectSuccess(), redTestContext));
                Result<Boolean> result7 = produceFutureOf(Boolean.class).byExecuting(() ->
                        listenableFutureOf(booleanSuccess(), redTestContext));
                Result<String> result8 = produceFutureOf(String.class).byExecuting(() ->
                        redFutureOf(stringSuccess(), redTestContext));
                Result<TestObject> result9 = produce(TestObject.class)
                        .byExecuting(TestRedSynchronizer::testObjectSuccess);
                Result<Boolean> result10 = produceFutureOf(Boolean.class).byExecuting(() ->
                        futureOf(booleanSuccess(), redTestContext));
                Result<String> result11 = produceFutureOf(String.class).byExecuting(() ->
                        listenableFutureOf(stringSuccess(), redTestContext));
                Marker marker1 = execute(pendingMarker -> redTestContext.scheduleTask(FUTURE_DELAY,
                        pendingMarker::complete));
                Marker marker2 = execute(pendingMarker -> redTestContext.scheduleTask(FUTURE_DELAY,
                        pendingMarker::complete));
                return ifResults(result1, result2, result3, result4, result5, result6, result7, result8, result9,
                        result10, result11).succeed().andMarkers(marker1).finish().andMarkers(marker2).succeed()
                        .produce(Boolean.class).byExecuting(results -> {
                            timingValidator.validatePassed(FUTURE_DELAY);
                            redTestContext.assertions.assertTrue(checkBooleanSuccess(results.result(0, Boolean.class)));
                            redTestContext.assertions.assertTrue(checkStringSuccess(results.result(1, String.class)));
                            redTestContext.assertions.assertTrue(
                                    checkTestObjectSuccess(results.result(2, TestObject.class)));
                            redTestContext.assertions.assertTrue(checkBooleanSuccess(results.result(3, Boolean.class)));
                            redTestContext.assertions.assertTrue(checkStringSuccess(results.result(4, String.class)));
                            redTestContext.assertions.assertTrue(
                                    checkTestObjectSuccess(results.result(5, TestObject.class)));
                            redTestContext.assertions.assertTrue(checkBooleanSuccess(results.result(6, Boolean.class)));
                            redTestContext.assertions.assertTrue(checkStringSuccess(results.result(7, String.class)));
                            redTestContext.assertions.assertTrue(
                                    checkTestObjectSuccess(results.result(8, TestObject.class)));
                            redTestContext.assertions.assertTrue(checkBooleanSuccess(results.result(9, Boolean.class)));
                            redTestContext.assertions.assertTrue(checkStringSuccess(results.result(10, String.class)));
                            try {
                                results.result(0, String.class);
                                redTestContext.fail("should have failed result 0 as string");
                            } catch (IllegalArgumentException ignored) {}
                            return booleanSuccess();
                        });
            }

        }

        public static class NDependenciesTest8Synchronizer extends RedSynchronizer<RedTestContext, String> {

            @Override
            protected Result<String> handle(RedTestContext redTestContext) {
                RedTestContext.TimingValidator timingValidator = redTestContext.timingValidator();
                Result<Boolean> result1 = produce(Boolean.class).byExecuting(() -> {
                    throw TestException.INSTANCE;
                });
                Result<String> result2 = produceFutureOf(String.class).byExecuting(() ->
                        futureOf(String.class, TestException.INSTANCE, redTestContext));
                Result<TestObject> result3 = produceFutureOf(TestObject.class).byExecuting(() ->
                        listenableFutureOf(TestObject.class, TestException.INSTANCE, redTestContext));
                Result<Boolean> result4 = produceFutureOf(Boolean.class).byExecuting(() ->
                        redFutureOf(Boolean.class, TestException.INSTANCE, redTestContext));
                Result<String> result5 = produce(String.class).byExecuting(() -> {
                    throw TestException.INSTANCE;
                });
                Result<TestObject> result6 = produceFutureOf(TestObject.class).byExecuting(() ->
                        futureOf(TestObject.class, TestException.INSTANCE, redTestContext));
                Result<Boolean> result7 = produceFutureOf(Boolean.class).byExecuting(() ->
                        listenableFutureOf(Boolean.class, TestException.INSTANCE, redTestContext));
                Result<String> result8 = produceFutureOf(String.class).byExecuting(() ->
                        redFutureOf(String.class, TestException.INSTANCE, redTestContext));
                Result<TestObject> result9 = produce(TestObject.class).byExecuting(() -> {
                    throw TestException.INSTANCE;
                });
                Result<Boolean> result10 = produceFutureOf(Boolean.class).byExecuting(() ->
                        futureOf(Boolean.class, TestException.INSTANCE, redTestContext));
                Result<String> result11 = produceFutureOf(String.class).byExecuting(() ->
                        listenableFutureOf(String.class, TestException.INSTANCE, redTestContext));
                Marker marker1 = execute(pendingMarker -> redTestContext.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                Marker marker2 = execute(pendingMarker -> redTestContext.scheduleTask(FUTURE_DELAY,
                        pendingMarker::complete));
                return ifResults(result1, result2, result3, result4, result5, result6, result7, result8, result9,
                        result10, result11).fail().andMarkers(marker1).fail().andMarkers(marker2).finish()
                        .produce(String.class).byExecuting(() -> {
                            timingValidator.validatePassed(FUTURE_DELAY);
                            return stringSuccess();
                        });
            }

        }

        public static class NDependenciesTest9Synchronizer extends RedSynchronizer<RedTestContext, TestObject> {

            @Override
            protected Result<TestObject> handle(RedTestContext redTestContext) {
                RedTestContext.TimingValidator timingValidator = redTestContext.timingValidator();
                Result<Boolean> result1 = produce(Boolean.class).byExecuting(TestRedSynchronizer::booleanSuccess);
                Result<String> result2 = produceFutureOf(String.class).byExecuting(() ->
                        futureOf(stringSuccess(), redTestContext));
                Result<TestObject> result3 = produceFutureOf(TestObject.class).byExecuting(() ->
                        listenableFutureOf(testObjectSuccess(), redTestContext));
                Result<Boolean> result4 = produceFutureOf(Boolean.class).byExecuting(() ->
                        redFutureOf(booleanSuccess(), redTestContext));
                Result<String> result5 = produce(String.class).byExecuting(TestRedSynchronizer::stringSuccess);
                Result<TestObject> result6 = produceFutureOf(TestObject.class).byExecuting(() ->
                        futureOf(testObjectSuccess(), redTestContext));
                Result<Boolean> result7 = produceFutureOf(Boolean.class).byExecuting(() ->
                        listenableFutureOf(booleanSuccess(), redTestContext));
                Result<String> result8 = produceFutureOf(String.class).byExecuting(() ->
                        redFutureOf(String.class, TestException.INSTANCE, redTestContext));
                Result<TestObject> result9 = produce(TestObject.class).byExecuting(() -> {
                    throw TestException.INSTANCE;
                });
                Result<Boolean> result10 = produceFutureOf(Boolean.class).byExecuting(() ->
                        futureOf(Boolean.class, TestException.INSTANCE, redTestContext));
                Result<String> result11 = produceFutureOf(String.class).byExecuting(() ->
                        listenableFutureOf(String.class, TestException.INSTANCE, redTestContext));
                Marker marker1 = execute(pendingMarker -> redTestContext.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                Marker marker2 = execute(pendingMarker -> redTestContext.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                return ifResults(result1, result2, result3, result4, result5, result6, result7, result8, result9,
                        result10, result11).finish().andMarkers(marker1).fail().andMarkers(marker2).fail()
                        .produce(TestObject.class).byExecuting(results -> {
                            timingValidator.validatePassed(FUTURE_DELAY);
                            redTestContext.assertions.assertTrue(checkBooleanSuccess(results.result(0, Boolean.class)));
                            redTestContext.assertions.assertTrue(checkStringSuccess(results.result(1, String.class)));
                            redTestContext.assertions.assertTrue(
                                    checkTestObjectSuccess(results.result(2, TestObject.class)));
                            redTestContext.assertions.assertTrue(checkBooleanSuccess(results.result(3, Boolean.class)));
                            redTestContext.assertions.assertTrue(checkStringSuccess(results.result(4, String.class)));
                            redTestContext.assertions.assertTrue(
                                    checkTestObjectSuccess(results.result(5, TestObject.class)));
                            redTestContext.assertions.assertTrue(checkBooleanSuccess(results.result(6, Boolean.class)));
                            redTestContext.assertions.assertNull(results.result(7, String.class));
                            redTestContext.assertions.assertNull(results.result(8, TestObject.class));
                            redTestContext.assertions.assertNull(results.result(9, Boolean.class));
                            redTestContext.assertions.assertNull(results.result(10, String.class));
                            try {
                                results.result(0, String.class);
                                redTestContext.fail("should have failed result 0 as string");
                            } catch (IllegalArgumentException ignored) {}
                            return testObjectSuccess();
                        });
            }

        }

        public static class NDependenciesTest10Synchronizer extends RedVoidSynchronizer<CommandTestContext> {

            @Override
            protected Marker handle(CommandTestContext context) {
                RedTestContext.TimingValidator timingValidator = context._redTestContext.timingValidator();
                Result<Boolean> result1 = produce(Boolean.class).byExecuting(TestRedSynchronizer::booleanSuccess);
                Result<String> result2 = produceFutureOf(String.class).byExecuting(() ->
                        futureOf(stringSuccess(), context._redTestContext));
                Result<TestObject> result3 = produceFutureOf(TestObject.class).byExecuting(() ->
                        listenableFutureOf(testObjectSuccess(), context._redTestContext));
                Result<Boolean> result4 = produceFutureOf(Boolean.class).byExecuting(() ->
                        redFutureOf(booleanSuccess(), context._redTestContext));
                Result<String> result5 = produce(String.class).byExecuting(TestRedSynchronizer::stringSuccess);
                Result<TestObject> result6 = produceFutureOf(TestObject.class).byExecuting(() ->
                        futureOf(testObjectSuccess(), context._redTestContext));
                Result<Boolean> result7 = produceFutureOf(Boolean.class).byExecuting(() ->
                        listenableFutureOf(booleanSuccess(), context._redTestContext));
                Result<String> result8 = produceFutureOf(String.class).byExecuting(() ->
                        redFutureOf(stringSuccess(), context._redTestContext));
                Result<TestObject> result9 = produce(TestObject.class)
                        .byExecuting(TestRedSynchronizer::testObjectSuccess);
                Result<Boolean> result10 = produceFutureOf(Boolean.class).byExecuting(() ->
                        futureOf(booleanSuccess(), context._redTestContext));
                Result<String> result11 = produceFutureOf(String.class).byExecuting(() ->
                        listenableFutureOf(stringSuccess(), context._redTestContext));
                return ifResults(result1, result2, result3, result4, result5, result6, result7, result8, result9,
                        result10, result11).succeed().execute((pendingMarker, results) -> {
                    timingValidator.validatePassed(FUTURE_DELAY);
                    context._redTestContext.assertions.assertTrue(checkBooleanSuccess(results.result(0, Boolean.class)));
                    context._redTestContext.assertions.assertTrue(checkStringSuccess(results.result(1, String.class)));
                    context._redTestContext.assertions.assertTrue(
                            checkTestObjectSuccess(results.result(2, TestObject.class)));
                    context._redTestContext.assertions.assertTrue(checkBooleanSuccess(results.result(3, Boolean.class)));
                    context._redTestContext.assertions.assertTrue(checkStringSuccess(results.result(4, String.class)));
                    context._redTestContext.assertions.assertTrue(
                            checkTestObjectSuccess(results.result(5, TestObject.class)));
                    context._redTestContext.assertions.assertTrue(checkBooleanSuccess(results.result(6, Boolean.class)));
                    context._redTestContext.assertions.assertTrue(checkStringSuccess(results.result(7, String.class)));
                    context._redTestContext.assertions.assertTrue(
                            checkTestObjectSuccess(results.result(8, TestObject.class)));
                    context._redTestContext.assertions.assertTrue(checkBooleanSuccess(results.result(9, Boolean.class)));
                    context._redTestContext.assertions.assertTrue(checkStringSuccess(results.result(10, String.class)));
                    try {
                        results.result(0, String.class);
                        context._redTestContext.fail("should have failed result 0 as string");
                    } catch (IllegalArgumentException ignored) {}
                    context._finished.set(true);
                    pendingMarker.complete();
                });
            }

        }

        public static class NDependenciesTest11Synchronizer extends RedVoidSynchronizer<CommandTestContext> {

            @Override
            protected Marker handle(CommandTestContext context) {
                RedTestContext.TimingValidator timingValidator = context._redTestContext.timingValidator();
                Result<Boolean> result1 = produce(Boolean.class).byExecuting(() -> {
                    throw TestException.INSTANCE;
                });
                Result<String> result2 = produceFutureOf(String.class).byExecuting(() ->
                        futureOf(String.class, TestException.INSTANCE, context._redTestContext));
                Result<TestObject> result3 = produceFutureOf(TestObject.class).byExecuting(() ->
                        listenableFutureOf(TestObject.class, TestException.INSTANCE, context._redTestContext));
                Result<Boolean> result4 = produceFutureOf(Boolean.class).byExecuting(() ->
                        redFutureOf(Boolean.class, TestException.INSTANCE, context._redTestContext));
                Result<String> result5 = produce(String.class).byExecuting(() -> {
                    throw TestException.INSTANCE;
                });
                Result<TestObject> result6 = produceFutureOf(TestObject.class).byExecuting(() ->
                        futureOf(TestObject.class, TestException.INSTANCE, context._redTestContext));
                Result<Boolean> result7 = produceFutureOf(Boolean.class).byExecuting(() ->
                        listenableFutureOf(Boolean.class, TestException.INSTANCE, context._redTestContext));
                Result<String> result8 = produceFutureOf(String.class).byExecuting(() ->
                        redFutureOf(String.class, TestException.INSTANCE, context._redTestContext));
                Result<TestObject> result9 = produce(TestObject.class).byExecuting(() -> {
                    throw TestException.INSTANCE;
                });
                Result<Boolean> result10 = produceFutureOf(Boolean.class).byExecuting(() ->
                        futureOf(Boolean.class, TestException.INSTANCE, context._redTestContext));
                Result<String> result11 = produceFutureOf(String.class).byExecuting(() ->
                        listenableFutureOf(String.class, TestException.INSTANCE, context._redTestContext));
                return ifResults(result1, result2, result3, result4, result5, result6, result7, result8, result9,
                        result10, result11).fail().execute(pendingMarker -> {
                    timingValidator.validatePassed(FUTURE_DELAY);
                    context._finished.set(true);
                    pendingMarker.complete();
                });
            }

        }

        public static class NDependenciesTest12Synchronizer extends RedVoidSynchronizer<CommandTestContext> {

            @Override
            protected Marker handle(CommandTestContext context) {
                RedTestContext.TimingValidator timingValidator = context._redTestContext.timingValidator();
                Result<Boolean> result1 = produce(Boolean.class).byExecuting(TestRedSynchronizer::booleanSuccess);
                Result<String> result2 = produceFutureOf(String.class).byExecuting(() ->
                        futureOf(stringSuccess(), context._redTestContext));
                Result<TestObject> result3 = produceFutureOf(TestObject.class).byExecuting(() ->
                        listenableFutureOf(testObjectSuccess(), context._redTestContext));
                Result<Boolean> result4 = produceFutureOf(Boolean.class).byExecuting(() ->
                        redFutureOf(booleanSuccess(), context._redTestContext));
                Result<String> result5 = produce(String.class).byExecuting(TestRedSynchronizer::stringSuccess);
                Result<TestObject> result6 = produceFutureOf(TestObject.class).byExecuting(() ->
                        futureOf(testObjectSuccess(), context._redTestContext));
                Result<Boolean> result7 = produceFutureOf(Boolean.class).byExecuting(() ->
                        listenableFutureOf(booleanSuccess(), context._redTestContext));
                Result<String> result8 = produceFutureOf(String.class).byExecuting(() ->
                        redFutureOf(String.class, TestException.INSTANCE, context._redTestContext));
                Result<TestObject> result9 = produce(TestObject.class).byExecuting(() -> {
                    throw TestException.INSTANCE;
                });
                Result<Boolean> result10 = produceFutureOf(Boolean.class).byExecuting(() ->
                        futureOf(Boolean.class, TestException.INSTANCE, context._redTestContext));
                Result<String> result11 = produceFutureOf(String.class).byExecuting(() ->
                        listenableFutureOf(String.class, TestException.INSTANCE, context._redTestContext));
                return ifResults(result1, result2, result3, result4, result5, result6, result7, result8, result9,
                        result10, result11).finish().execute((pendingMarker, results) -> {
                    timingValidator.validatePassed(FUTURE_DELAY);
                    context._redTestContext.assertions.assertTrue(checkBooleanSuccess(results.result(0, Boolean.class)));
                    context._redTestContext.assertions.assertTrue(checkStringSuccess(results.result(1, String.class)));
                    context._redTestContext.assertions.assertTrue(
                            checkTestObjectSuccess(results.result(2, TestObject.class)));
                    context._redTestContext.assertions.assertTrue(checkBooleanSuccess(results.result(3, Boolean.class)));
                    context._redTestContext.assertions.assertTrue(checkStringSuccess(results.result(4, String.class)));
                    context._redTestContext.assertions.assertTrue(
                            checkTestObjectSuccess(results.result(5, TestObject.class)));
                    context._redTestContext.assertions.assertTrue(checkBooleanSuccess(results.result(6, Boolean.class)));
                    context._redTestContext.assertions.assertNull(results.result(7, String.class));
                    context._redTestContext.assertions.assertNull(results.result(8, TestObject.class));
                    context._redTestContext.assertions.assertNull(results.result(9, Boolean.class));
                    context._redTestContext.assertions.assertNull(results.result(10, String.class));
                    try {
                        results.result(0, String.class);
                        context._redTestContext.fail("should have failed result 0 as string");
                    } catch (IllegalArgumentException ignored) {}
                    context._finished.set(true);
                    pendingMarker.complete();
                });
            }

        }

        public static class NDependenciesTest13Synchronizer extends RedVoidSynchronizer<CommandTestContext> {

            @Override
            protected Marker handle(CommandTestContext context) {
                RedTestContext.TimingValidator timingValidator = context._redTestContext.timingValidator();
                Result<Boolean> result1 = produce(Boolean.class).byExecuting(TestRedSynchronizer::booleanSuccess);
                Result<String> result2 = produceFutureOf(String.class).byExecuting(() ->
                        futureOf(stringSuccess(), context._redTestContext));
                Result<TestObject> result3 = produceFutureOf(TestObject.class).byExecuting(() ->
                        listenableFutureOf(testObjectSuccess(), context._redTestContext));
                Result<Boolean> result4 = produceFutureOf(Boolean.class).byExecuting(() ->
                        redFutureOf(booleanSuccess(), context._redTestContext));
                Result<String> result5 = produce(String.class).byExecuting(TestRedSynchronizer::stringSuccess);
                Result<TestObject> result6 = produceFutureOf(TestObject.class).byExecuting(() ->
                        futureOf(testObjectSuccess(), context._redTestContext));
                Result<Boolean> result7 = produceFutureOf(Boolean.class).byExecuting(() ->
                        listenableFutureOf(booleanSuccess(), context._redTestContext));
                Result<String> result8 = produceFutureOf(String.class).byExecuting(() ->
                        redFutureOf(stringSuccess(), context._redTestContext));
                Result<TestObject> result9 = produce(TestObject.class)
                        .byExecuting(TestRedSynchronizer::testObjectSuccess);
                Result<Boolean> result10 = produceFutureOf(Boolean.class).byExecuting(() ->
                        futureOf(booleanSuccess(), context._redTestContext));
                Result<String> result11 = produceFutureOf(String.class).byExecuting(() ->
                        listenableFutureOf(stringSuccess(), context._redTestContext));
                Marker marker1 = execute(pendingMarker -> context._redTestContext.scheduleTask(FUTURE_DELAY,
                        pendingMarker::complete));
                Marker marker2 = execute(pendingMarker -> context._redTestContext.scheduleTask(FUTURE_DELAY,
                        pendingMarker::complete));
                return ifResults(result1, result2, result3, result4, result5, result6, result7, result8, result9,
                        result10, result11).succeed().andMarkers(marker1, marker2).succeed()
                        .execute((pendingMarker, results) -> {
                            timingValidator.validatePassed(FUTURE_DELAY);
                            context._redTestContext.assertions.assertTrue(checkBooleanSuccess(results.result(0, Boolean.class)));
                            context._redTestContext.assertions.assertTrue(checkStringSuccess(results.result(1, String.class)));
                            context._redTestContext.assertions.assertTrue(
                                    checkTestObjectSuccess(results.result(2, TestObject.class)));
                            context._redTestContext.assertions.assertTrue(checkBooleanSuccess(results.result(3, Boolean.class)));
                            context._redTestContext.assertions.assertTrue(checkStringSuccess(results.result(4, String.class)));
                            context._redTestContext.assertions.assertTrue(
                                    checkTestObjectSuccess(results.result(5, TestObject.class)));
                            context._redTestContext.assertions.assertTrue(checkBooleanSuccess(results.result(6, Boolean.class)));
                            context._redTestContext.assertions.assertTrue(checkStringSuccess(results.result(7, String.class)));
                            context._redTestContext.assertions.assertTrue(
                                    checkTestObjectSuccess(results.result(8, TestObject.class)));
                            context._redTestContext.assertions.assertTrue(checkBooleanSuccess(results.result(9, Boolean.class)));
                            context._redTestContext.assertions.assertTrue(checkStringSuccess(results.result(10, String.class)));
                            try {
                                results.result(0, String.class);
                                context._redTestContext.fail("should have failed result 0 as string");
                            } catch (IllegalArgumentException ignored) {}
                            context._finished.set(true);
                            pendingMarker.complete();
                        });
            }

        }

        public static class NDependenciesTest14Synchronizer extends RedVoidSynchronizer<CommandTestContext> {

            @Override
            protected Marker handle(CommandTestContext context) {
                RedTestContext.TimingValidator timingValidator = context._redTestContext.timingValidator();
                Result<Boolean> result1 = produce(Boolean.class).byExecuting(() -> {
                    throw TestException.INSTANCE;
                });
                Result<String> result2 = produceFutureOf(String.class).byExecuting(() ->
                        futureOf(String.class, TestException.INSTANCE, context._redTestContext));
                Result<TestObject> result3 = produceFutureOf(TestObject.class).byExecuting(() ->
                        listenableFutureOf(TestObject.class, TestException.INSTANCE, context._redTestContext));
                Result<Boolean> result4 = produceFutureOf(Boolean.class).byExecuting(() ->
                        redFutureOf(Boolean.class, TestException.INSTANCE, context._redTestContext));
                Result<String> result5 = produce(String.class).byExecuting(() -> {
                    throw TestException.INSTANCE;
                });
                Result<TestObject> result6 = produceFutureOf(TestObject.class).byExecuting(() ->
                        futureOf(TestObject.class, TestException.INSTANCE, context._redTestContext));
                Result<Boolean> result7 = produceFutureOf(Boolean.class).byExecuting(() ->
                        listenableFutureOf(Boolean.class, TestException.INSTANCE, context._redTestContext));
                Result<String> result8 = produceFutureOf(String.class).byExecuting(() ->
                        redFutureOf(String.class, TestException.INSTANCE, context._redTestContext));
                Result<TestObject> result9 = produce(TestObject.class).byExecuting(() -> {
                    throw TestException.INSTANCE;
                });
                Result<Boolean> result10 = produceFutureOf(Boolean.class).byExecuting(() ->
                        futureOf(Boolean.class, TestException.INSTANCE, context._redTestContext));
                Result<String> result11 = produceFutureOf(String.class).byExecuting(() ->
                        listenableFutureOf(String.class, TestException.INSTANCE, context._redTestContext));
                Marker marker1 = execute(pendingMarker -> context._redTestContext.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                Marker marker2 = execute(pendingMarker -> context._redTestContext.scheduleTask(FUTURE_DELAY,
                        pendingMarker::complete));
                return ifResults(result1, result2, result3, result4, result5, result6, result7, result8, result9,
                        result10, result11).fail().andMarkers(marker1, marker2).finish().execute(pendingMarker -> {
                    timingValidator.validatePassed(FUTURE_DELAY);
                    context._finished.set(true);
                    pendingMarker.complete();
                });
            }

        }

        public static class NDependenciesTest15Synchronizer extends RedVoidSynchronizer<CommandTestContext> {

            @Override
            protected Marker handle(CommandTestContext context) {
                RedTestContext.TimingValidator timingValidator = context._redTestContext.timingValidator();
                Result<Boolean> result1 = produce(Boolean.class).byExecuting(TestRedSynchronizer::booleanSuccess);
                Result<String> result2 = produceFutureOf(String.class).byExecuting(() ->
                        futureOf(stringSuccess(), context._redTestContext));
                Result<TestObject> result3 = produceFutureOf(TestObject.class).byExecuting(() ->
                        listenableFutureOf(testObjectSuccess(), context._redTestContext));
                Result<Boolean> result4 = produceFutureOf(Boolean.class).byExecuting(() ->
                        redFutureOf(booleanSuccess(), context._redTestContext));
                Result<String> result5 = produce(String.class).byExecuting(TestRedSynchronizer::stringSuccess);
                Result<TestObject> result6 = produceFutureOf(TestObject.class).byExecuting(() ->
                        futureOf(testObjectSuccess(), context._redTestContext));
                Result<Boolean> result7 = produceFutureOf(Boolean.class).byExecuting(() ->
                        listenableFutureOf(booleanSuccess(), context._redTestContext));
                Result<String> result8 = produceFutureOf(String.class).byExecuting(() ->
                        redFutureOf(String.class, TestException.INSTANCE, context._redTestContext));
                Result<TestObject> result9 = produce(TestObject.class).byExecuting(() -> {
                    throw TestException.INSTANCE;
                });
                Result<Boolean> result10 = produceFutureOf(Boolean.class).byExecuting(() ->
                        futureOf(Boolean.class, TestException.INSTANCE, context._redTestContext));
                Result<String> result11 = produceFutureOf(String.class).byExecuting(() ->
                        listenableFutureOf(String.class, TestException.INSTANCE, context._redTestContext));
                Marker marker1 = execute(pendingMarker -> context._redTestContext.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                Marker marker2 = execute(pendingMarker -> context._redTestContext.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                return ifResults(result1, result2, result3, result4, result5, result6, result7, result8, result9,
                        result10, result11).finish().andMarkers(marker1, marker2).fail()
                        .execute((pendingMarker, results) -> {
                            timingValidator.validatePassed(FUTURE_DELAY);
                            context._redTestContext.assertions.assertTrue(checkBooleanSuccess(results.result(0, Boolean.class)));
                            context._redTestContext.assertions.assertTrue(checkStringSuccess(results.result(1, String.class)));
                            context._redTestContext.assertions.assertTrue(
                                    checkTestObjectSuccess(results.result(2, TestObject.class)));
                            context._redTestContext.assertions.assertTrue(checkBooleanSuccess(results.result(3, Boolean.class)));
                            context._redTestContext.assertions.assertTrue(checkStringSuccess(results.result(4, String.class)));
                            context._redTestContext.assertions.assertTrue(
                                    checkTestObjectSuccess(results.result(5, TestObject.class)));
                            context._redTestContext.assertions.assertTrue(checkBooleanSuccess(results.result(6, Boolean.class)));
                            context._redTestContext.assertions.assertNull(results.result(7, String.class));
                            context._redTestContext.assertions.assertNull(results.result(8, TestObject.class));
                            context._redTestContext.assertions.assertNull(results.result(9, Boolean.class));
                            context._redTestContext.assertions.assertNull(results.result(10, String.class));
                            try {
                                results.result(0, String.class);
                                context._redTestContext.fail("should have failed result 0 as string");
                            } catch (IllegalArgumentException ignored) {}
                            context._finished.set(true);
                            pendingMarker.complete();
                        });
            }

        }

        public static class NDependenciesTest16Synchronizer extends RedVoidSynchronizer<CommandTestContext> {

            @Override
            protected Marker handle(CommandTestContext context) {
                RedTestContext.TimingValidator timingValidator = context._redTestContext.timingValidator();
                Result<Boolean> result1 = produce(Boolean.class).byExecuting(TestRedSynchronizer::booleanSuccess);
                Result<String> result2 = produceFutureOf(String.class).byExecuting(() ->
                        futureOf(stringSuccess(), context._redTestContext));
                Result<TestObject> result3 = produceFutureOf(TestObject.class).byExecuting(() ->
                        listenableFutureOf(testObjectSuccess(), context._redTestContext));
                Result<Boolean> result4 = produceFutureOf(Boolean.class).byExecuting(() ->
                        redFutureOf(booleanSuccess(), context._redTestContext));
                Result<String> result5 = produce(String.class).byExecuting(TestRedSynchronizer::stringSuccess);
                Result<TestObject> result6 = produceFutureOf(TestObject.class).byExecuting(() ->
                        futureOf(testObjectSuccess(), context._redTestContext));
                Result<Boolean> result7 = produceFutureOf(Boolean.class).byExecuting(() ->
                        listenableFutureOf(booleanSuccess(), context._redTestContext));
                Result<String> result8 = produceFutureOf(String.class).byExecuting(() ->
                        redFutureOf(stringSuccess(), context._redTestContext));
                Result<TestObject> result9 = produce(TestObject.class)
                        .byExecuting(TestRedSynchronizer::testObjectSuccess);
                Result<Boolean> result10 = produceFutureOf(Boolean.class).byExecuting(() ->
                        futureOf(booleanSuccess(), context._redTestContext));
                Result<String> result11 = produceFutureOf(String.class).byExecuting(() ->
                        listenableFutureOf(stringSuccess(), context._redTestContext));
                Marker marker1 = execute(pendingMarker -> context._redTestContext.scheduleTask(FUTURE_DELAY,
                        pendingMarker::complete));
                Marker marker2 = execute(pendingMarker -> context._redTestContext.scheduleTask(FUTURE_DELAY,
                        pendingMarker::complete));
                return ifResults(result1, result2, result3, result4, result5, result6, result7, result8, result9,
                        result10, result11).succeed().andMarkers(marker1).finish().andMarkers(marker2).succeed()
                        .execute((pendingMarker, results) -> {
                            timingValidator.validatePassed(FUTURE_DELAY);
                            context._redTestContext.assertions.assertTrue(checkBooleanSuccess(results.result(0, Boolean.class)));
                            context._redTestContext.assertions.assertTrue(checkStringSuccess(results.result(1, String.class)));
                            context._redTestContext.assertions.assertTrue(
                                    checkTestObjectSuccess(results.result(2, TestObject.class)));
                            context._redTestContext.assertions.assertTrue(checkBooleanSuccess(results.result(3, Boolean.class)));
                            context._redTestContext.assertions.assertTrue(checkStringSuccess(results.result(4, String.class)));
                            context._redTestContext.assertions.assertTrue(
                                    checkTestObjectSuccess(results.result(5, TestObject.class)));
                            context._redTestContext.assertions.assertTrue(checkBooleanSuccess(results.result(6, Boolean.class)));
                            context._redTestContext.assertions.assertTrue(checkStringSuccess(results.result(7, String.class)));
                            context._redTestContext.assertions.assertTrue(
                                    checkTestObjectSuccess(results.result(8, TestObject.class)));
                            context._redTestContext.assertions.assertTrue(checkBooleanSuccess(results.result(9, Boolean.class)));
                            context._redTestContext.assertions.assertTrue(checkStringSuccess(results.result(10, String.class)));
                            try {
                                results.result(0, String.class);
                                context._redTestContext.fail("should have failed result 0 as string");
                            } catch (IllegalArgumentException ignored) {}
                            context._finished.set(true);
                            pendingMarker.complete();
                        });
            }

        }

        public static class NDependenciesTest17Synchronizer extends RedVoidSynchronizer<CommandTestContext> {

            @Override
            protected Marker handle(CommandTestContext context) {
                RedTestContext.TimingValidator timingValidator = context._redTestContext.timingValidator();
                Result<Boolean> result1 = produce(Boolean.class).byExecuting(() -> {
                    throw TestException.INSTANCE;
                });
                Result<String> result2 = produceFutureOf(String.class).byExecuting(() ->
                        futureOf(String.class, TestException.INSTANCE, context._redTestContext));
                Result<TestObject> result3 = produceFutureOf(TestObject.class).byExecuting(() ->
                        listenableFutureOf(TestObject.class, TestException.INSTANCE, context._redTestContext));
                Result<Boolean> result4 = produceFutureOf(Boolean.class).byExecuting(() ->
                        redFutureOf(Boolean.class, TestException.INSTANCE, context._redTestContext));
                Result<String> result5 = produce(String.class).byExecuting(() -> {
                    throw TestException.INSTANCE;
                });
                Result<TestObject> result6 = produceFutureOf(TestObject.class).byExecuting(() ->
                        futureOf(TestObject.class, TestException.INSTANCE, context._redTestContext));
                Result<Boolean> result7 = produceFutureOf(Boolean.class).byExecuting(() ->
                        listenableFutureOf(Boolean.class, TestException.INSTANCE, context._redTestContext));
                Result<String> result8 = produceFutureOf(String.class).byExecuting(() ->
                        redFutureOf(String.class, TestException.INSTANCE, context._redTestContext));
                Result<TestObject> result9 = produce(TestObject.class).byExecuting(() -> {
                    throw TestException.INSTANCE;
                });
                Result<Boolean> result10 = produceFutureOf(Boolean.class).byExecuting(() ->
                        futureOf(Boolean.class, TestException.INSTANCE, context._redTestContext));
                Result<String> result11 = produceFutureOf(String.class).byExecuting(() ->
                        listenableFutureOf(String.class, TestException.INSTANCE, context._redTestContext));
                Marker marker1 = execute(pendingMarker -> context._redTestContext.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                Marker marker2 = execute(pendingMarker -> context._redTestContext.scheduleTask(FUTURE_DELAY,
                        pendingMarker::complete));
                return ifResults(result1, result2, result3, result4, result5, result6, result7, result8, result9,
                        result10, result11).fail().andMarkers(marker1).fail().andMarkers(marker2).finish()
                        .execute(pendingMarker -> {
                            timingValidator.validatePassed(FUTURE_DELAY);
                            context._finished.set(true);
                            pendingMarker.complete();
                        });
            }

        }

        public static class NDependenciesTest18Synchronizer extends RedVoidSynchronizer<CommandTestContext> {

            @Override
            protected Marker handle(CommandTestContext context) {
                RedTestContext.TimingValidator timingValidator = context._redTestContext.timingValidator();
                Result<Boolean> result1 = produce(Boolean.class).byExecuting(TestRedSynchronizer::booleanSuccess);
                Result<String> result2 = produceFutureOf(String.class).byExecuting(() ->
                        futureOf(stringSuccess(), context._redTestContext));
                Result<TestObject> result3 = produceFutureOf(TestObject.class).byExecuting(() ->
                        listenableFutureOf(testObjectSuccess(), context._redTestContext));
                Result<Boolean> result4 = produceFutureOf(Boolean.class).byExecuting(() ->
                        redFutureOf(booleanSuccess(), context._redTestContext));
                Result<String> result5 = produce(String.class).byExecuting(TestRedSynchronizer::stringSuccess);
                Result<TestObject> result6 = produceFutureOf(TestObject.class).byExecuting(() ->
                        futureOf(testObjectSuccess(), context._redTestContext));
                Result<Boolean> result7 = produceFutureOf(Boolean.class).byExecuting(() ->
                        listenableFutureOf(booleanSuccess(), context._redTestContext));
                Result<String> result8 = produceFutureOf(String.class).byExecuting(() ->
                        redFutureOf(String.class, TestException.INSTANCE, context._redTestContext));
                Result<TestObject> result9 = produce(TestObject.class).byExecuting(() -> {
                    throw TestException.INSTANCE;
                });
                Result<Boolean> result10 = produceFutureOf(Boolean.class).byExecuting(() ->
                        futureOf(Boolean.class, TestException.INSTANCE, context._redTestContext));
                Result<String> result11 = produceFutureOf(String.class).byExecuting(() ->
                        listenableFutureOf(String.class, TestException.INSTANCE, context._redTestContext));
                Marker marker1 = execute(pendingMarker -> context._redTestContext.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                Marker marker2 = execute(pendingMarker -> context._redTestContext.scheduleTask(FUTURE_DELAY, () ->
                        pendingMarker.fail(TestException.INSTANCE)));
                return ifResults(result1, result2, result3, result4, result5, result6, result7, result8, result9,
                        result10, result11).finish().andMarkers(marker1).fail().andMarkers(marker2).fail()
                        .execute((pendingMarker, results) -> {
                            timingValidator.validatePassed(FUTURE_DELAY);
                            context._redTestContext.assertions.assertTrue(checkBooleanSuccess(results.result(0, Boolean.class)));
                            context._redTestContext.assertions.assertTrue(checkStringSuccess(results.result(1, String.class)));
                            context._redTestContext.assertions.assertTrue(
                                    checkTestObjectSuccess(results.result(2, TestObject.class)));
                            context._redTestContext.assertions.assertTrue(checkBooleanSuccess(results.result(3, Boolean.class)));
                            context._redTestContext.assertions.assertTrue(checkStringSuccess(results.result(4, String.class)));
                            context._redTestContext.assertions.assertTrue(
                                    checkTestObjectSuccess(results.result(5, TestObject.class)));
                            context._redTestContext.assertions.assertTrue(checkBooleanSuccess(results.result(6, Boolean.class)));
                            context._redTestContext.assertions.assertNull(results.result(7, String.class));
                            context._redTestContext.assertions.assertNull(results.result(8, TestObject.class));
                            context._redTestContext.assertions.assertNull(results.result(9, Boolean.class));
                            context._redTestContext.assertions.assertNull(results.result(10, String.class));
                            try {
                                results.result(0, String.class);
                                context._redTestContext.fail("should have failed result 0 as string");
                            } catch (IllegalArgumentException ignored) {}
                            context._finished.set(true);
                            pendingMarker.complete();
                        });
            }

        }

        public static class TestNDependenciesIllegalResults extends RedVoidSynchronizer<CommandTestContext> {

            @Override
            protected Marker handle(CommandTestContext context) {
                Result<Boolean> result0 = produce(Boolean.class).byExecuting(TestRedSynchronizer::booleanSuccess);
                Result<Boolean> result1 = produce(Boolean.class).byExecuting(TestRedSynchronizer::booleanSuccess);
                Result<Boolean> result2 = produce(Boolean.class).byExecuting(TestRedSynchronizer::booleanSuccess);
                Result<Boolean> result3 = produce(Boolean.class).byExecuting(TestRedSynchronizer::booleanSuccess);
                Result<Boolean> result4 = produce(Boolean.class).byExecuting(TestRedSynchronizer::booleanSuccess);
                Result<Boolean> result5 = produce(Boolean.class).byExecuting(TestRedSynchronizer::booleanSuccess);
                Result<Boolean> result6 = produce(Boolean.class).byExecuting(TestRedSynchronizer::booleanSuccess);
                Result<Boolean> result7 = produce(Boolean.class).byExecuting(TestRedSynchronizer::booleanSuccess);
                Result<Boolean> result8 = produce(Boolean.class).byExecuting(TestRedSynchronizer::booleanSuccess);
                Result<Boolean> result9 = produce(Boolean.class).byExecuting(TestRedSynchronizer::booleanSuccess);
                Result<Boolean> result10 = produce(Boolean.class).byExecuting(TestRedSynchronizer::booleanSuccess);
                Result<Boolean> result11 = produce(Boolean.class).byExecuting(TestRedSynchronizer::booleanSuccess);
                Result<Boolean> result12 = produce(Boolean.class).byExecuting(TestRedSynchronizer::booleanSuccess);
                return ifResults(result0, result1, result2, result3, result4, result5, result6, result7, result8,
                        result9, result10, result11, result12).succeed().execute((pendingMarker, results) -> {
                    context._redTestContext.assertions
                            .assertTrue(checkBooleanSuccess(results.result(0, Boolean.class)));
                    context._redTestContext.assertions
                            .assertTrue(checkBooleanSuccess(results.result(1, Boolean.class)));
                    context._redTestContext.assertions
                            .assertTrue(checkBooleanSuccess(results.result(2, Boolean.class)));
                    context._redTestContext.assertions
                            .assertTrue(checkBooleanSuccess(results.result(3, Boolean.class)));
                    context._redTestContext.assertions
                            .assertTrue(checkBooleanSuccess(results.result(4, Boolean.class)));
                    context._redTestContext.assertions
                            .assertTrue(checkBooleanSuccess(results.result(5, Boolean.class)));
                    context._redTestContext.assertions
                            .assertTrue(checkBooleanSuccess(results.result(6, Boolean.class)));
                    context._redTestContext.assertions
                            .assertTrue(checkBooleanSuccess(results.result(7, Boolean.class)));
                    context._redTestContext.assertions
                            .assertTrue(checkBooleanSuccess(results.result(8, Boolean.class)));
                    context._redTestContext.assertions
                            .assertTrue(checkBooleanSuccess(results.result(9, Boolean.class)));
                    context._redTestContext.assertions
                            .assertTrue(checkBooleanSuccess(results.result(10, Boolean.class)));
                    context._redTestContext.assertions
                            .assertTrue(checkBooleanSuccess(results.result(11, Boolean.class)));
                    context._redTestContext.assertions
                            .assertTrue(checkBooleanSuccess(results.result(12, Boolean.class)));
                    try {
                        results.result(0, String.class);
                        context._redTestContext.fail("should have failed result 0 as string");
                    } catch (IllegalArgumentException ignored) {}
                    try {
                        results.result(-1, Boolean.class);
                        context._redTestContext.fail("should have failed result -1");
                    } catch (IllegalArgumentException ignored) {}
                    try {
                        results.result(15, Boolean.class);
                        context._redTestContext.fail("should have failed result 15");
                    } catch (IllegalArgumentException ignored) {}
                    context._finished.set(true);
                    pendingMarker.complete();
                });
            }

        }

        public static class PreconditionFailingSynchronizer extends RedSynchronizer<RedTestContext, Boolean> {

            @Override
            protected Result<Boolean> handle(RedTestContext redTestContext) {
                Result<String> precondition = produce(String.class).byExecuting(() -> {
                    throw TestException.INSTANCE;
                });
                return ifResult(precondition).succeed().produce(Boolean.class).byExecuting(f0 -> booleanSuccess());
            }

        }

        public static class PreconditionFailingVoidSynchronizer extends RedVoidSynchronizer<RedTestContext> {

            @Override
            protected Marker handle(RedTestContext redTestContext) {
                Result<String> precondition = produce(String.class).byExecuting(() -> {
                    throw TestException.INSTANCE;
                });
                return ifResult(precondition).succeed().execute((pendingMarker, f0) -> pendingMarker.complete());
            }

        }

        public static class TopLevelExceptionSynchronizer extends RedSynchronizer<RedTestContext, Boolean> {

            @Override
            protected Result<Boolean> handle(RedTestContext redTestContext) throws Throwable {
                throw TestException.INSTANCE;
            }

        }

        public static class TopLevelExceptionVoidSynchronizer extends RedVoidSynchronizer<RedTestContext> {

            @Override
            protected Marker handle(RedTestContext redTestContext) throws Throwable {
                throw TestException.INSTANCE;
            }

        }

        public static class ExceptionInResultFunctionSynchronizer extends RedSynchronizer<RedTestContext, Boolean> {

            @Override
            protected Result<Boolean> handle(RedTestContext redTestContext) {
                Result<String> precondition = produce(String.class).byExecuting(TestRedSynchronizer::stringSuccess);
                return ifResult(precondition).succeed().produce(Boolean.class).byExecuting(f0 -> {
                    throw TestException.INSTANCE;
                });
            }

        }

        public static class ExceptionInResultCommandVoidSynchronizer extends RedVoidSynchronizer<RedTestContext> {

            @Override
            protected Marker handle(RedTestContext redTestContext) {
                Result<String> precondition = produce(String.class).byExecuting(TestRedSynchronizer::stringSuccess);
                return ifResult(precondition).succeed().execute((pendingMarker, f0) -> {
                    throw TestException.INSTANCE;
                });
            }

        }

        public static class FailingFutureSynchronizer extends RedSynchronizer<RedTestContext, Boolean> {

            @Override
            protected Result<Boolean> handle(RedTestContext redTestContext) {
                Result<String> precondition = produce(String.class).byExecuting(TestRedSynchronizer::stringSuccess);
                return ifResult(precondition).succeed().produceFutureOf(Boolean.class).byExecuting(f0 ->
                        futureOf(Boolean.class, TestException.INSTANCE, redTestContext));
            }

        }

        public static class FailingListenableFutureSynchronizer extends RedSynchronizer<RedTestContext, Boolean> {

            @Override
            protected Result<Boolean> handle(RedTestContext redTestContext) {
                Result<String> precondition = produce(String.class).byExecuting(TestRedSynchronizer::stringSuccess);
                return ifResult(precondition).succeed().produceFutureOf(Boolean.class).byExecuting(f0 ->
                        listenableFutureOf(Boolean.class, TestException.INSTANCE, redTestContext));
            }

        }

        public static class FailingRedFutureSynchronizer extends RedSynchronizer<RedTestContext, Boolean> {

            @Override
            protected Result<Boolean> handle(RedTestContext redTestContext) {
                Result<String> precondition = produce(String.class).byExecuting(TestRedSynchronizer::stringSuccess);
                return ifResult(precondition).succeed().produceFutureOf(Boolean.class).byExecuting(f0 ->
                        redFutureOf(Boolean.class, TestException.INSTANCE, redTestContext));
            }

        }

        public static class FailingResultCommandSynchronizer extends RedVoidSynchronizer<RedTestContext> {

            @Override
            protected Marker handle(RedTestContext redTestContext) {
                Result<String> precondition = produce(String.class).byExecuting(TestRedSynchronizer::stringSuccess);
                return ifResult(precondition).succeed().execute((pendingMarker, f0) ->
                        pendingMarker.fail(TestException.INSTANCE));
            }

        }

        public static class TestPartialTransformer1 extends RedVoidSynchronizer<RedTestContext> {

            @Override
            protected Marker handle(RedTestContext redTestContext) {
                Result<String> precondition1 = produce(String.class).byExecuting(() -> {
                    throw TestException.INSTANCE;
                });
                Result<String> precondition2 = produce(String.class).byExecuting(TestRedSynchronizer::stringSuccess);
                return ifResults(precondition1, precondition2).fail().execute(PendingMarker::complete);
            }

        }

        public static class TestPartialTransformer2 extends RedVoidSynchronizer<RedTestContext> {

            @Override
            protected Marker handle(RedTestContext redTestContext) {
                Result<String> precondition1 = produce(String.class).byExecuting(() -> {
                    throw TestException.INSTANCE;
                });
                Result<String> precondition2 = produce(String.class).byExecuting(TestRedSynchronizer::stringSuccess);
                return ifResults(precondition1, precondition2).succeed().execute((pendingMarker, f0, f1) ->
                        pendingMarker.complete());
            }

        }

        public static class TestPartialTransformer3 extends RedVoidSynchronizer<RedTestContext> {

            @Override
            protected Marker handle(RedTestContext redTestContext) {
                Marker precondition1 = execute(PendingMarker::complete);
                Marker precondition2 = execute(pendingMarker -> {
                    throw TestException.INSTANCE;
                });
                return ifMarkers(precondition1, precondition2).fail().execute(PendingMarker::complete);
            }

        }

        public static class TestPartialTransformer4 extends RedVoidSynchronizer<RedTestContext> {

            @Override
            protected Marker handle(RedTestContext redTestContext) {
                Marker precondition1 = execute(PendingMarker::complete);
                Marker precondition2 = execute(pendingMarker -> {
                    throw TestException.INSTANCE;
                });
                return ifMarkers(precondition1, precondition2).succeed().execute(PendingMarker::complete);
            }

        }

        public static class BangTestSynchronizer extends RedSynchronizer<RedTestContext, Boolean> {

            @Override
            protected Result<Boolean> handle(RedTestContext redTestContext) throws Throwable {
                RedTestContext.TimingValidator timingValidator = redTestContext.timingValidator();
                Result<Boolean> result1 = produceFutureOf(Boolean.class).byExecuting(() ->
                        futureOf(booleanSuccess(), redTestContext));

                ifResult(result1).succeed().produce(Boolean.class).byExecuting(f0 -> {
                    timingValidator.validatePassed(FUTURE_DELAY);
                    return booleanSuccess();
                });
                Result<Boolean> result2 = ifResult(result1).succeed().produceFutureOf(Boolean.class).byExecuting(f0 -> {
                    timingValidator.validatePassed(FUTURE_DELAY);
                    return futureOf(booleanSuccess(), redTestContext);
                });
                ifResult(result1).succeed().produceFutureOf(Boolean.class)
                        .byExecuting(f0 -> {
                            timingValidator.validatePassed(FUTURE_DELAY);
                            return listenableFutureOf(booleanSuccess(), redTestContext);
                        });
                ifResult(result1).succeed().produceFutureOf(Boolean.class)
                        .byExecuting(f0 -> {
                            timingValidator.validatePassed(FUTURE_DELAY);
                            return redFutureOf(booleanSuccess(), redTestContext);
                        });

                ifResults(result1, result2).succeed().produce(Boolean.class).byExecuting((f0, f1) -> {
                    timingValidator.validatePassed(FUTURE_DELAY * 2);
                    return booleanSuccess();
                });
                Result<Boolean> result3 = ifResults(result1, result2).succeed().produceFutureOf(Boolean.class)
                        .byExecuting((f0, f1) -> {
                            timingValidator.validatePassed(FUTURE_DELAY * 2);
                            return futureOf(booleanSuccess(), redTestContext);
                        });
                ifResults(result1, result2).succeed().produceFutureOf(Boolean.class)
                        .byExecuting((f0, f1) -> {
                            timingValidator.validatePassed(FUTURE_DELAY * 2);
                            return listenableFutureOf(booleanSuccess(), redTestContext);
                        });
                ifResults(result1, result2).succeed().produceFutureOf(Boolean.class)
                        .byExecuting((f0, f1) -> {
                            timingValidator.validatePassed(FUTURE_DELAY * 2);
                            return redFutureOf(booleanSuccess(), redTestContext);
                        });

                ifResults(result1, result2, result3).succeed().produce(Boolean.class).byExecuting((f0, f1, f2) -> {
                    timingValidator.validatePassed(FUTURE_DELAY * 3);
                    return booleanSuccess();
                });
                Result<Boolean> result4 = ifResults(result1, result2, result3).succeed().produceFutureOf(Boolean.class)
                        .byExecuting((f0, f1, f2) -> {
                            timingValidator.validatePassed(FUTURE_DELAY * 3);
                            return futureOf(booleanSuccess(), redTestContext);
                        });
                ifResults(result1, result2, result3).succeed()
                        .produceFutureOf(Boolean.class).byExecuting((f0, f1, f2) -> {
                            timingValidator.validatePassed(FUTURE_DELAY * 3);
                            return listenableFutureOf(booleanSuccess(), redTestContext);
                        });
                ifResults(result1, result2, result3).succeed().produceFutureOf(Boolean.class)
                        .byExecuting((f0, f1, f2) -> {
                            timingValidator.validatePassed(FUTURE_DELAY * 3);
                            return redFutureOf(booleanSuccess(), redTestContext);
                        });

                ifResults(result1, result2, result3, result4).succeed().produce(Boolean.class)
                        .byExecuting((f0, f1, f2, f3) -> {
                            timingValidator.validatePassed(FUTURE_DELAY * 4);
                            return booleanSuccess();
                        });
                Result<Boolean> result5 = ifResults(result1, result2, result3, result4).succeed()
                        .produceFutureOf(Boolean.class).byExecuting((f0, f1, f2, f3) -> {
                            timingValidator.validatePassed(FUTURE_DELAY * 4);
                            return futureOf(booleanSuccess(), redTestContext);
                        });
                ifResults(result1, result2, result3, result4).succeed().produceFutureOf(Boolean.class)
                        .byExecuting((f0, f1, f2, f3) -> {
                            timingValidator.validatePassed(FUTURE_DELAY * 4);
                            return listenableFutureOf(booleanSuccess(), redTestContext);
                        });
                ifResults(result1, result2, result3, result4).succeed().produceFutureOf(Boolean.class)
                        .byExecuting((f0, f1, f2, f3) -> {
                            timingValidator.validatePassed(FUTURE_DELAY * 4);
                            return redFutureOf(booleanSuccess(), redTestContext);
                        });

                ifResults(result1, result2, result3, result4, result5).succeed()
                        .produce(Boolean.class).byExecuting((f0, f1, f2, f3, f4) -> {
                            timingValidator.validatePassed(FUTURE_DELAY * 5);
                            return booleanSuccess();
                        });
                Result<Boolean> result6 = ifResults(result1, result2, result3, result4, result5).succeed()
                        .produceFutureOf(Boolean.class).byExecuting((f0, f1, f2, f3, f4) -> {
                            timingValidator.validatePassed(FUTURE_DELAY * 5);
                            return futureOf(booleanSuccess(), redTestContext);
                        });
                ifResults(result1, result2, result3, result4, result5).succeed()
                        .produceFutureOf(Boolean.class).byExecuting((f0, f1, f2, f3, f4) -> {
                            timingValidator.validatePassed(FUTURE_DELAY * 5);
                            return listenableFutureOf(booleanSuccess(), redTestContext);
                        });
                ifResults(result1, result2, result3, result4, result5).succeed().produceFutureOf(Boolean.class)
                        .byExecuting((f0, f1, f2, f3, f4) -> {
                            timingValidator.validatePassed(FUTURE_DELAY * 5);
                            return redFutureOf(booleanSuccess(), redTestContext);
                        });

                ifResults(result1, result2, result3, result4, result5, result6).succeed()
                        .produce(Boolean.class).byExecuting((f0, f1, f2, f3, f4, f5) -> {
                            timingValidator.validatePassed(FUTURE_DELAY * 6);
                            return booleanSuccess();
                        });
                Result<Boolean> result7 = ifResults(result1, result2, result3, result4, result5, result6).succeed()
                        .produceFutureOf(Boolean.class).byExecuting((f0, f1, f2, f3, f4, f5) -> {
                            timingValidator.validatePassed(FUTURE_DELAY * 6);
                            return futureOf(booleanSuccess(), redTestContext);
                        });
                ifResults(result1, result2, result3, result4, result5, result6).succeed()
                        .produceFutureOf(Boolean.class).byExecuting((f0, f1, f2, f3, f4, f5) -> {
                            timingValidator.validatePassed(FUTURE_DELAY * 6);
                            return listenableFutureOf(booleanSuccess(), redTestContext);
                        });
                ifResults(result1, result2, result3, result4, result5, result6).succeed()
                        .produceFutureOf(Boolean.class).byExecuting((f0, f1, f2, f3, f4, f5) -> {
                            timingValidator.validatePassed(FUTURE_DELAY * 6);
                            return redFutureOf(booleanSuccess(), redTestContext);
                        });

                ifResults(result1, result2, result3, result4, result5, result6, result7)
                        .succeed().produce(Boolean.class).byExecuting((f0, f1, f2, f3, f4, f5, f6) -> {
                            timingValidator.validatePassed(FUTURE_DELAY * 7);
                            return booleanSuccess();
                        });
                Result<Boolean> result8 = ifResults(result1, result2, result3, result4, result5, result6, result7)
                        .succeed().produceFutureOf(Boolean.class).byExecuting((f0, f1, f2, f3, f4, f5, f6) -> {
                            timingValidator.validatePassed(FUTURE_DELAY * 7);
                            return futureOf(booleanSuccess(), redTestContext);
                        });
                ifResults(result1, result2, result3, result4, result5, result6, result7).succeed()
                        .produceFutureOf(Boolean.class).byExecuting((f0, f1, f2, f3, f4, f5, f6) -> {
                            timingValidator.validatePassed(FUTURE_DELAY * 7);
                            return listenableFutureOf(booleanSuccess(), redTestContext);
                        });
                ifResults(result1, result2, result3, result4, result5, result6, result7).succeed()
                        .produceFutureOf(Boolean.class).byExecuting((f0, f1, f2, f3, f4, f5, f6) -> {
                            timingValidator.validatePassed(FUTURE_DELAY * 7);
                            return redFutureOf(booleanSuccess(), redTestContext);
                        });

                ifResults(result1, result2, result3, result4, result5, result6, result7, result8).succeed()
                        .produce(Boolean.class).byExecuting((f0, f1, f2, f3, f4, f5, f6, f7) -> {
                            timingValidator.validatePassed(FUTURE_DELAY * 8);
                            return booleanSuccess();
                        });
                Result<Boolean> result9 = ifResults(result1, result2, result3, result4, result5, result6, result7,
                        result8).succeed().produceFutureOf(Boolean.class)
                        .byExecuting((f0, f1, f2, f3, f4, f5, f6, f7) -> {
                            timingValidator.validatePassed(FUTURE_DELAY * 8);
                            return futureOf(booleanSuccess(), redTestContext);
                        });
                ifResults(result1, result2, result3, result4, result5, result6, result7, result8).succeed()
                        .produceFutureOf(Boolean.class).byExecuting((f0, f1, f2, f3, f4, f5, f6, f7) -> {
                            timingValidator.validatePassed(FUTURE_DELAY * 8);
                            return listenableFutureOf(booleanSuccess(), redTestContext);
                        });
                ifResults(result1, result2, result3, result4, result5, result6, result7, result8).succeed()
                        .produceFutureOf(Boolean.class).byExecuting((f0, f1, f2, f3, f4, f5, f6, f7) -> {
                            timingValidator.validatePassed(FUTURE_DELAY * 8);
                            return redFutureOf(booleanSuccess(), redTestContext);
                        });

                ifResults(result1, result2, result3, result4, result5, result6, result7, result8, result9).succeed()
                        .produce(Boolean.class).byExecuting((f0, f1, f2, f3, f4, f5, f6, f7, f8) -> {
                            timingValidator.validatePassed(FUTURE_DELAY * 9);
                            return booleanSuccess();
                        });
                Result<Boolean> result10 = ifResults(result1, result2, result3, result4, result5, result6, result7,
                        result8, result9).succeed().produceFutureOf(Boolean.class)
                        .byExecuting((f0, f1, f2, f3, f4, f5, f6, f7, f8) -> {
                            timingValidator.validatePassed(FUTURE_DELAY * 9);
                            return futureOf(booleanSuccess(), redTestContext);
                        });
                ifResults(result1, result2, result3, result4, result5, result6, result7, result8, result9).succeed()
                        .produceFutureOf(Boolean.class).byExecuting((f0, f1, f2, f3, f4, f5, f6, f7, f8) -> {
                            timingValidator.validatePassed(FUTURE_DELAY * 9);
                            return listenableFutureOf(booleanSuccess(), redTestContext);
                        });
                ifResults(result1, result2, result3, result4, result5, result6, result7, result8, result9).succeed()
                        .produceFutureOf(Boolean.class).byExecuting((f0, f1, f2, f3, f4, f5, f6, f7, f8) -> {
                            timingValidator.validatePassed(FUTURE_DELAY * 9);
                            return redFutureOf(booleanSuccess(), redTestContext);
                        });

                ifResults(result1, result2, result3, result4, result5, result6, result7, result8, result9, result10)
                        .succeed().produce(Boolean.class).byExecuting((f0, f1, f2, f3, f4, f5, f6, f7, f8, f9) -> {
                            timingValidator.validatePassed(FUTURE_DELAY * 10);
                            return booleanSuccess();
                        });
                Result<Boolean> result11 = ifResults(result1, result2, result3, result4, result5, result6, result7,
                        result8, result9, result10).succeed().produceFutureOf(Boolean.class)
                        .byExecuting((f0, f1, f2, f3, f4, f5, f6, f7, f8, f9) -> {
                            timingValidator.validatePassed(FUTURE_DELAY * 10);
                            return futureOf(booleanSuccess(), redTestContext);
                        });
                ifResults(result1, result2, result3, result4, result5, result6, result7, result8, result9, result10)
                        .succeed().produceFutureOf(Boolean.class)
                        .byExecuting((f0, f1, f2, f3, f4, f5, f6, f7, f8, f9) -> {
                            timingValidator.validatePassed(FUTURE_DELAY * 10);
                            return listenableFutureOf(booleanSuccess(), redTestContext);
                        });
                ifResults(result1, result2, result3, result4, result5, result6, result7, result8, result9, result10)
                        .succeed().produceFutureOf(Boolean.class)
                        .byExecuting((f0, f1, f2, f3, f4, f5, f6, f7, f8, f9) -> {
                            timingValidator.validatePassed(FUTURE_DELAY * 10);
                            return redFutureOf(booleanSuccess(), redTestContext);
                        });

                ifResults(result1, result2, result3, result4, result5, result6, result7, result8, result9, result10,
                        result11).succeed().produce(Boolean.class).byExecuting(results -> {
                            timingValidator.validatePassed(FUTURE_DELAY * 11);
                            return booleanSuccess();
                        });
                ifResults(result1, result2, result3, result4, result5, result6, result7, result8, result9, result10,
                        result11).succeed().produceFutureOf(Boolean.class).byExecuting(results -> {
                            timingValidator.validatePassed(FUTURE_DELAY * 11);
                            return futureOf(booleanSuccess(), redTestContext);
                        });
                ifResults(result1, result2, result3, result4, result5, result6, result7, result8, result9, result10,
                        result11).succeed().produceFutureOf(Boolean.class).byExecuting(results -> {
                            timingValidator.validatePassed(FUTURE_DELAY * 11);
                            return listenableFutureOf(booleanSuccess(), redTestContext);
                        });
                return ifResults(result1, result2, result3, result4, result5, result6, result7, result8, result9,
                        result10, result11).succeed().produceFutureOf(Boolean.class).byExecuting(results -> {
                            timingValidator.validatePassed(FUTURE_DELAY * 11);
                            return redFutureOf(booleanSuccess(), redTestContext);
                        });
            }

        }

    }

}
