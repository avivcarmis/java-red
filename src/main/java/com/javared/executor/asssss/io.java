package com.javared.executor.asssss;

import com.javared.executor.RedExecutor;
import com.javared.future.RedFuture;
import com.javared.future.RedFutureOf;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by avivc on 3/22/2017.
 */
public class io extends RedExecutor {

    Future<Boolean> future = new Future<Boolean>() {
        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            return false;
        }

        @Override
        public boolean isCancelled() {
            return false;
        }

        @Override
        public boolean isDone() {
            return false;
        }

        @Override
        public Boolean get() throws InterruptedException, ExecutionException {
            return null;
        }

        @Override
        public Boolean get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return null;
        }
    };

    public void io() {
        RedFutureOf<String> dbRead1Future = RedFuture.futureOf();
        RedFutureOf<String> dbRead2Future = RedFuture.futureOf();
        RedFutureOf<String> fileRead1Future = RedFuture.futureOf();
        RedFutureOf<String> fileRead2Future = RedFuture.futureOf();
        RedFutureOf<Boolean> combine1Future = produce.futureOf(Boolean.class).after(dbRead1Future, fileRead1Future).with((dbRead1Result, fileRead1Result) -> {
            if ((dbRead1Result.toLowerCase() + fileRead1Result.toLowerCase()).equals("")) {
                // bla bla
            }
            return future;
        });
        produce.valueOf(String.class).
    }

}
