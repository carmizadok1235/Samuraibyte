package com.example.SamuraiByte.main;

public class GameLoop implements Runnable{
    private Thread gameThread;
    private Game game;
    public GameLoop(Game game){
        this.gameThread = new Thread(this);
        this.game = game;
    }
    @Override
    public void run() {
        long lastFPScheck = System.currentTimeMillis();
        int fps = 0;
        long lastDeltaTime = System.nanoTime();
        long NANOSEC = 1_000_000_000;
        long TARGET_FPS = 60;
        long OPTIMAL_TIME = NANOSEC / TARGET_FPS;
        while (true){

            long nowDeltaTime = System.nanoTime();
            double timeSinceLastDelta = nowDeltaTime - lastDeltaTime;
            double deltaTime = timeSinceLastDelta/NANOSEC;

            this.game.update(deltaTime);
            this.game.render();

            lastDeltaTime = nowDeltaTime;

            long sleepTime = (OPTIMAL_TIME - (System.nanoTime() - nowDeltaTime)) / 1_000_000;
            if (sleepTime > 0){
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
//            java.io.File fdFolder = new java.io.File("/proc/self/fd");
//            if (fdFolder.listFiles() != null)
//                System.out.println("Open FDs: " + fdFolder.listFiles().length);

            fps++;
            long now = System.currentTimeMillis();
            if (now - lastFPScheck >= 1000){
                System.out.println("fps: " + fps);
                fps = 0;
                lastFPScheck += 1000;
            }
        }
    }
    public void startLoop(){
        this.gameThread.start();
    }
}
