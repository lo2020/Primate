package main;

import java.util.ArrayList;

public class Finder extends Thread {
    private final ArrayList<Integer> primes;
    private TestStatus status = TestStatus.RUNNING; // maybe be rebellious and make this public
    private boolean testActive;
    private Object boss;
    
    private int testNum, root;
    private final int START, INCREMENT;
    
    public Finder(ArrayList<Integer> list, int threadID, int threadCount, Object lock) {
        this.primes = list;
        this.START = threadID;
        this.INCREMENT = threadCount;
        this.boss = lock;
    }
    
    public void run() {
        int prime, index;
        
        mainLoop: while (true) {
            try {
                synchronized (primes) {
                    primes.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            // at this point, testNum and root should be set, and boss should be ready to be notified.
            
            index = START;
            prime = primes.get(index);
            
            while (testActive && prime <= root) {
                if (testNum % prime == 0) { // here's the money maker
                    status = TestStatus.FAIL; // all caps for enhanced drama
                    
                    synchronized (boss) {
                        boss.notify();
                    }
                    
                    continue mainLoop;
                }
                
                index += INCREMENT;
                prime = primes.get(index);
            }
            
            // if you get to this point, it means that all of the tests have passed
            
            status = TestStatus.PASS;
            synchronized (boss) {
                boss.notify();
            }
        }
    }
    
    public void beginTest(int test, int root) {
        this.testNum = test;
        this.root = root;
        testActive = true;
    }
    
    public void endTest() {
        testActive = false;
    }
    
    public TestStatus getTestStatus() {
        return status;
    }
}

enum TestStatus { // perhaps add an IDLE status
    RUNNING, PASS, FAIL
}