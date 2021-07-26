package com.github.glassmc.clarity.v1_8_9;

public class Hook {

    private static long previousFrustumUpdate;

    public static boolean skipFrustumUpdate() {
        long time = System.currentTimeMillis();
        if(time - previousFrustumUpdate < 67) {
            return true;
        }
        previousFrustumUpdate = time;
        return false;
    }

    public static void applyChunkCooldown() {
        try {
            Thread.sleep(12);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void test1() {

    }

}
