package com.example.payment.config;

import feign.RetryableException;
import feign.Retryer;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class FeignRetryer implements Retryer {
    private final int maxAttempts; // 시도 가능한 총 횟수
    private final long period; // 시도 간격
    private final long maxPeriod; // 최대 시도 간격
    private final long maxTotalSleepMillis; // 총 대기 시간의 최대 값

    private final ThreadPoolExecutor threadPoolExecutor;
    private int attempt; // 시도 횟수
    private long sleptForMillis; // 각 재시도 간 대기 시간

    public FeignRetryer(int maxAttempts, long period, long maxPeriod, long maxTotalSleepMillis, ThreadPoolExecutor threadPoolExecutor) {
        this.maxAttempts = maxAttempts;
        this.period = period;
        this.maxPeriod = maxPeriod;
        this.maxTotalSleepMillis = maxTotalSleepMillis;
        this.threadPoolExecutor = threadPoolExecutor;
        this.attempt = 1;
    }

    @Override
    public void continueOrPropagate(RetryableException e) {
        // 시도 횟수 or 대기 시간 초과 시 재시도 중단
        if (attempt >= maxAttempts || sleptForMillis >= maxTotalSleepMillis) {
            throw e;
        }

        int activeCount = threadPoolExecutor.getActiveCount();
        int poolSize = threadPoolExecutor.getPoolSize();
        double usage = (double) activeCount / poolSize;

        // 스레드풀 사용량 70% 초과 시 중단
        if (usage > 0.7) {
            throw e;
        }

        long interval = nextMaxInterval();
        try {
            TimeUnit.MILLISECONDS.sleep(interval);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
            return;
        }

        sleptForMillis += interval;
        attempt++;
    }

    private long nextMaxInterval() {
        return Math.min(period * (long) Math.pow(1.5, attempt - 1), maxPeriod);
    }

    @Override
    public Retryer clone() {
        return new FeignRetryer(maxAttempts, period, maxPeriod, maxTotalSleepMillis, threadPoolExecutor);
    }
}
