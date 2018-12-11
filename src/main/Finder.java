package main;

import java.util.ArrayList;

public class Finder extends Thread {
    private ArrayList<Integer> primes;
    private int start;
    private int increment;
    private boolean running;
    private int test;
    private int upto;
    
    public Finder(ArrayList list, int threadID, int threadCount) {
        this.primes = list;
        this.start = threadID;
        this.increment = threadCount;
    }
    
    public void run() {
        int prime;
        boolean isPrime;
        int index = start;
        
        while (true) {
            try {
                primes.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            running = true;
            isPrime = true;
            
            while (!this.isInterrupted()) {
                prime = primes.get(index);
                
                if (prime > upto) {
                    break;
                }
                
                if (test % prime == 0) {
                    isPrime = false;
                    break;
                }
            }
        }
    }
    
    public void beginTest(int number, int root) {
        test = number;
        upto = root;
    }
    
    public boolean isRunning() {
        return running;
    }
}
