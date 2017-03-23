package com.javared.executor.asssss;

import com.google.common.util.concurrent.SettableFuture;
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

    Future<String> future = new Future<String>() {
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
        public String get() throws InterruptedException, ExecutionException {
            return null;
        }

        @Override
        public String get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return null;
        }
    };

    public static class User {}

    public void io() {
        Result<User> userResult = produce.valueOf(User.class).after().byRunning(User::new);
        Result<String> lastVisitResult = produce.futureOf(String.class).after().byRunning(() -> future);
        Result<Boolean> shouldReportResult = produce.listenableFutureOf(Boolean.class).after(userResult, lastVisitResult).byRunning((user, lastVisit) -> {
            SettableFuture<Boolean> future = SettableFuture.create();
            if (lastVisit.equals("")) {
                future.set(true);
            } else {
                future.set(false);
            }
            return future;
        });

    }

}
