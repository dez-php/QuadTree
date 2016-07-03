package dezbyte.app;

import java.util.Arrays;

abstract public class Loop implements Runnable {

    public static boolean isRunning;
    public static float UPDATE_TIME      = 70.0F;
    public static int   THREAD_IDLE_TIME = 1;
    public static float ONE_NANO_SECOND  = 1000000000F;

    protected Thread    thread;
    protected MainFrame mainFrame;

    public Loop()
    {
        isRunning = false;
        this.mainFrame = new MainFrame(800, 600, "Test QuadTree");
    }

    public synchronized void start()
    {
        if (isRunning) {
            return;
        }

        isRunning = true;

        this.thread = new Thread(this);
        this.thread.start();
    }

    public synchronized void stop()
    {
        if (!isRunning) {
            return;
        }

        isRunning = false;

        try {
            this.thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.close();
    }

    private void close()
    {

    }

    @Override
    public void run()
    {
        long  lastTime         = System.nanoTime();
        float lostTime         = 0;
        float totalElapsedTime = 0;
        int[] counters         = {0, 0, 0};

        this.initialize();

        while (isRunning) {

            long nowTime     = System.nanoTime();
            long elapsedTime = nowTime - lastTime;
            lastTime = nowTime;

            lostTime += (elapsedTime / (ONE_NANO_SECOND / UPDATE_TIME));
            totalElapsedTime += elapsedTime;

            boolean hasToRender = false;

            while (lostTime > 1) {
                this.update();
                lostTime--;

                counters[1]++;
                if (hasToRender) {
                    counters[2]++;
                } else {
                    hasToRender = true;
                }
            }

            if (hasToRender) {
                this.render();
                counters[0]++;
            } else {
                try {
                    Thread.sleep(THREAD_IDLE_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (totalElapsedTime >= ONE_NANO_SECOND) {
                this.mainFrame.setTitle(
                        String.format(" [FPS: %d, UPD: %d, UPD LOST: %d]", counters[0], counters[1], counters[2]));
                totalElapsedTime = 0;
                Arrays.fill(counters, 0);
            }

        }
    }

    abstract protected void initialize();

    abstract protected void update();

    abstract protected void render();

}
