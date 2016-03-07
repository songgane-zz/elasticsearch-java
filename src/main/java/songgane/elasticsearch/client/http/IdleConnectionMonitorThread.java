package songgane.elasticsearch.client.http;

import org.apache.http.conn.HttpClientConnectionManager;

import java.util.concurrent.TimeUnit;

/**
 * Created by songgane on 2016. 3. 7.
 */
public class IdleConnectionMonitorThread extends Thread {

    /**
     * The conn mgr.
     */
    private final HttpClientConnectionManager connMgr;

    /**
     * The shutdown.
     */
    private volatile boolean shutdown;

    /**
     * The timeout.
     */
    private int timeout = 30;

    /**
     * The interval.
     */
    private long interval = 6000L;

    /**
     * Instantiates a new idle connection monitor thread.
     *
     * @param connMgr the conn mgr
     */
    public IdleConnectionMonitorThread(HttpClientConnectionManager connMgr) {
        super();
        this.connMgr = connMgr;
    }

    /**
     * Sets the timeout(the maximum time to wait in milliseconds).
     *
     * @param timeout the new timeout
     */
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    /**
     * Sets the interval.
     *
     * @param interval the new interval
     */
    public void setInterval(long interval) {
        this.interval = interval;
    }

    /**
     *
     */
    @Override
    public void run() {
        try {
            while (!shutdown) {
                synchronized (this) {
                    wait(this.interval);
                    // Close expired connections
                    connMgr.closeExpiredConnections();
                    // Optionally, close connections that have been idle longer than 30 sec
                    connMgr.closeIdleConnections(this.timeout, TimeUnit.SECONDS);
                }
            }
        } catch (InterruptedException ex) {
            // terminate
        }
    }

    /**
     * Shutdown.
     */
    public void shutdown() {
        shutdown = true;
        synchronized (this) {
            notifyAll();
        }
    }

}
