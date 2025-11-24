package online.ecommerce.platform;


/**
 * Background daemon thread that auto-saves the cache to DB every interval.
 * Demonstrates multithreading + synchronized access to service.
 */
public class AutoSaveThread extends Thread {
    private final ECommerceService service;
    private volatile boolean running = true;
    private final long intervalMs;

    public AutoSaveThread(ECommerceService service, long intervalMs) {
        this.service = service;
        this.intervalMs = intervalMs;
        setDaemon(true);
        setName("AutoSaveThread");
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(intervalMs);
                System.out.println("🔄 Auto-saving product cache to DB...");
                service.syncCacheToDB();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                System.out.println("AutoSaveThread exception: " + e.getMessage());
            }
        }
    }

    public void stopThread() {
        running = false;
        this.interrupt();
    }
}

