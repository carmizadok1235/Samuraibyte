package com.example.SamuraiByte.main;

public class StopWatch implements Runnable{
//    private Thread thread;
    private volatile boolean running, computed;
    private long startTime;
    private long elapsedTime;
    public StopWatch(){
//        this.thread = new Thread(this);
        this.running = false;
        this.elapsedTime = -1;
    }
    public void start(){
        if (!this.running){
            this.startTime = System.currentTimeMillis();
            this.running = true;
            this.computed = false;
            new Thread(this).start();
        }
    }
    public double stop(){
        this.running = false;
        System.out.println("computing elapsed time");
        return (System.currentTimeMillis() - this.startTime)/1000d;
    }
//    public double getLastElapsedTime(){
//        return this.elapsedTime/100d;
//    }
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
//        System.out.println("computing elapsed time");
//        this.elapsedTime = System.currentTimeMillis() - this.startTime;
    }
}
