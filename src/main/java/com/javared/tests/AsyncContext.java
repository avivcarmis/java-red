package com.javared.tests;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by Mamot on 3/15/2017.
 */
public class AsyncContext {

    private final List<Fork> _forks;

    private Throwable _throwable;

    private CountDownLatch _latch;

    public AsyncContext() {
        _forks = new LinkedList<>();
        _throwable = null;
        _latch = null;
    }

    public Fork fork() {
        Fork fork = new Fork();
        _forks.add(fork);
        return fork;
    }

    public void test(long timeout, long validationSleep) throws Throwable {
        _latch = new CountDownLatch(_forks.size());
        _latch.await(timeout, TimeUnit.MILLISECONDS);
        if (validationSleep > 0) {
            Thread.sleep(validationSleep);
        }
        for (Fork fork : _forks) {
            if (fork._throwable != null) {
                throw fork._throwable;
            }
        }
    }

    public void test(long timeout) throws Throwable {
        test(timeout, 0);
    }

    public class Fork {

        private Throwable _throwable;

        private Fork() {
            _throwable = null;
        }

        void complete() {
            _latch.countDown();
        }

        void fail(Throwable throwable) {
            _throwable = throwable;
            _latch.countDown();
        }

    }

}
