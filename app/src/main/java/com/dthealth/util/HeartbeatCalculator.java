package com.dthealth.util;

public class HeartbeatCalculator {
    private static long lastTimeMillis = 0;
    private static int heartbeat = 0;

    public int calculate() {
        long currentTimeMillis = System.currentTimeMillis();
        heartbeat = Math.round(60000 / (currentTimeMillis - lastTimeMillis));
        lastTimeMillis = currentTimeMillis;
        return heartbeat;
    }
}
