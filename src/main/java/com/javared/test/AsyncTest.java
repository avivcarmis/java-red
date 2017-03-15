//package com.javared.tests;
//
//import java.util.LinkedList;
//import java.util.List;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.atomic.AtomicInteger;
//
///**
// * Created by Mamot on 3/15/2017.
// */
//public class AsyncTest {
//
//    private final List<Fork> _forks;
//
//    private final AtomicInteger _pendingForks;
//
//    private Throwable _throwable;
//
//    private CountDownLatch _latch;
//
//    AsyncTest() {
//        _forks = new LinkedList<>();
//        _pendingForks = new AtomicInteger(0);
//        _throwable = null;
//        _latch = null;
//    }
//
//    public Fork fork() {
//        Fork fork = new Fork();
//        _forks.add(fork);
//        return fork;
//    }
//
//    public void fail(Throwable throwable) {
//        setThrowable(throwable);
//    }
//
//    public void fail(String message) {
//        setThrowable(new RuntimeException(message));
//    }
//
//    void test(long timeout, long validationSleep) throws Throwable {
//        synchronized (_pendingForks) {
//            if (_throwable != null) {
//                throw _throwable;
//            }
//            _latch = new CountDownLatch(_forks.size() - _pendingForks.get());
//        }
//        _latch.await(timeout, TimeUnit.MILLISECONDS);
//        if (validationSleep > 0) {
//            Thread.sleep(validationSleep);
//        }
//        if (_throwable != null) {
//            throw _throwable;
//        }
//    }
//
//    private void setThrowable(Throwable throwable) {
//        synchronized (_pendingForks) {
//            if (throwable != null && _throwable == null) {
//                _throwable = throwable;
//            }
//        }
//    }
//
//    public class Fork {
//
//        private Fork() {}
//
//        public void complete() {
//            synchronized (_pendingForks) {
//                if (_latch == null) {
//                    // pending fork
//                    _pendingForks.incrementAndGet();
//                }
//                else {
//                    _latch.countDown();
//                }
//            }
//        }
//
//    }
//
//}
