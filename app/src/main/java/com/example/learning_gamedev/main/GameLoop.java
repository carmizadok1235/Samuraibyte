package com.example.learning_gamedev.main;

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
        while (true){
            long nowDeltaTime = System.nanoTime();
            double timeSinceLastDelta = nowDeltaTime - lastDeltaTime;
            double deltaTime = timeSinceLastDelta/NANOSEC;

            this.game.update(deltaTime);
            this.game.render();

            lastDeltaTime = nowDeltaTime;

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
