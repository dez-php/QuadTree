package dez.app;

import dez.quadtree.QuadTree;
import dez.quadtree.QuadTreeLeaf;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainLoop implements Runnable {

    public static boolean isRunning;

    public static float UPDATE_TIME      = 100.0f;
    public static int   THREAD_IDLE_TIME = 1;
    public static float ONE_NANO_SECOND  = 1000000000f;

    private MainFrame mainFrame;
    private Thread    thread;

    private QuadTree<Entity> tree;
    private ArrayList<QuadTreeLeaf> leafs;

    public MainLoop()
    {
        isRunning = false;
        this.mainFrame = new MainFrame(900, 900, "Test QuadTree");
        this.tree = new QuadTree<>(0, 0, MainFrame.WIDTH, MainFrame.HEIGHT);

        Random random = new Random();
        this.leafs = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            int x = random.nextInt(MainFrame.WIDTH);
            int y = random.nextInt(MainFrame.HEIGHT);
            this.leafs.add(new QuadTreeLeaf<>(x, y, new Entity(x, y)));
        }

        for(QuadTreeLeaf l : this.leafs) {
            tree.add(l);
        }

    }

    public void start()
    {
        if (isRunning) {
            return;
        }

        isRunning = true;

        this.thread = new Thread(this);
        this.thread.start();
    }

    public void stop()
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
//        tree.clear();



    }

    private void render()
    {
        this.mainFrame.clearFrame();

        Graphics2D graphics2D = this.mainFrame.getGraphics2D();

        this.tree.draw(graphics2D);

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
