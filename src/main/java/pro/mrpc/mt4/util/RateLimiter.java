package pro.mrpc.mt4.util;

/**
 * Simple blocking rate limiter: at most {@code perSecond} operations per second (Java port of the Python
 * {@code RateLimiter}). {@link #acquire()} blocks to keep a minimum interval between operations.
 */
public class RateLimiter {
    private final double perSecond;
    private long lastNanos;

    public RateLimiter(double perSecond) {
        this.perSecond = perSecond;
    }

    public synchronized void acquire() {
        if (perSecond <= 0) return;
        long minIntervalNanos = (long) (1_000_000_000L / perSecond);
        long now = System.nanoTime();
        long waitNanos = lastNanos + minIntervalNanos - now;
        if (waitNanos > 0) {
            try {
                Thread.sleep(waitNanos / 1_000_000L, (int) (waitNanos % 1_000_000L));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        lastNanos = System.nanoTime();
    }
}
