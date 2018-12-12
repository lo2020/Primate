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
        // TODO initialize variables
        
        // everything below is in the big while loop:
        
        // TODO wait until boss notifies you
        // test and upto should be updated at this point
        
        // TODO test for prime-ness until stopped or test finished
        
        // TODO if not interrupted, tell boss test results
        
        while (!this.isInterrupted()) {
        
        }
    }
    
    // this run method is broken
    /*
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
                while (!this.isInterrupted()) {
                    prime = primes.get(index);
                    
                    if (test % prime == 0) {
                        isPrime = false;
                        break;
                    } else if (prime > upto) {
                        break;
                    } else {
                        index += increment;
                    }
                }
            }
        }
    }
    */
    
    public void beginTest(int number, int root) {
        test = number;
        upto = root;
    }
    
    public boolean isRunning() {
        return running;
    }
}