package com.zsy.android.api.alarm;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Title: ZsyAndroid
 * <p>
 * Description:
 * </p>
 *
 * @author Zsy
 * @date 2019/6/27 17:00
 */

public class Test {

    public static ExecutorService newFixedThreadPool(int nThreads) {
        return new ThreadPoolExecutor(
                nThreads,
                nThreads,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
    }

    public static void main(String[] args) {
        System.out.println("hello");
        ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
        long initialDelay = 1;
        long period = 1;
        // 从现在开始1秒钟之后，每隔1秒钟执行一次job1
        service.scheduleAtFixedRate(new MyScheduledExecutor("job1"), 3, 3, TimeUnit.SECONDS);

        // 从现在开始2秒钟之后，每隔2秒钟执行一次job2
        service.scheduleWithFixedDelay(new MyScheduledExecutor("job2"), 5, 5, TimeUnit.SECONDS);
    }

    public static class MyScheduledExecutor implements Runnable {
        private String jobName;
        MyScheduledExecutor(String jobName) {
            this.jobName = jobName;
        }
        @Override
        public void run() {
            System.out.println(jobName + " is running");
        }
    }
}
