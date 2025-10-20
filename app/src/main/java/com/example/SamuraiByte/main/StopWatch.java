package com.example.SamuraiByte.main;

public class StopWatch implements Runnable{
//    private Thread thread;
    private volatile boolean running;
    private long startTime;
    public StopWatch(){
//        this.thread = new Thread(this);
        this.running = false;
    }
    public void start(){
        if (!this.running){
            this.startTime = System.currentTimeMillis();
            this.running = true;
            new Thread(this).start();
        }
    }
    public double stop(){
        this.running = false;
//        System.out.println("computing elapsed time");
        return (System.currentTimeMillis() - this.startTime)/1000d;
    }
    @Override
    public void run() {
        while (running){
//            System.out.println("time: " + (System.currentTimeMillis() - this.startTime));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
//        this.elapsedTime = System.currentTimeMillis() - this.startTime;
    }
}
