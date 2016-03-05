package dez.app;

import dez.quadtree.Object2D;
import dez.quadtree.QuadTree;

import java.awt.*;
import java.util.Arrays;
import java.util.Random;

public class MainLoop implements Runnable, QuadTree.Executor {

    public static boolean isRunning;

    public static float UPDATE_TIME      = 70.0f;
    public static int   THREAD_IDLE_TIME = 1;
    public static float ONE_NANO_SECOND  = 1000000000f;

    private MainFrame mainFrame;
    private Thread    thread;
    private Random    random;

    private QuadTree<Entity> tree;

    public MainLoop()
    {
        isRunning = false;
        this.mainFrame = new MainFrame(800, 600, "Test QuadTree");
        this.tree = new QuadTree<>(0, 0, MainFrame.WIDTH, MainFrame.HEIGHT);

        this.random = new Random();

        for (int i = 0; i < (MainFrame.WIDTH / 16) * (MainFrame.HEIGHT / 16); i++) {
            int x = this.random.nextInt(MainFrame.WIDTH);
            int y = this.random.nextInt(MainFrame.HEIGHT);
            this.tree.add(new Entity(x, y));
        }

    }

    @Override
    public void execute(Object2D object2D)
    {
        object2D.setX(object2D.minX() + random.nextInt(3));
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

    private void update()
    {

        for (Object2D object2D : this.tree.values()) {
            if(MainFrame.WIDTH > object2D.minX()) {
                object2D.setX(object2D.minX() + 1);
            } else {
                object2D.setX(object2D.minX() - 1);
            }
        }


    }

    private void render()
    {
        this.mainFrame.clearFrame();

//        for (Object2D object2D : this.tree.values()) {
//            this.mainFrame.getGraphics2D().fillOval((int) object2D.minX(), (int) object2D.minY(), 3, 3);
//        }

        this.mainFrame.swapBuffer();
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
                        " [FPS: " + counters[0] + ", UPD: " + counters[1] + ", UPD LOST: " + counters[2] + "]");
                totalElapsedTime = 0;
                Arrays.fill(counters, 0);
            }

        }
    }

}
