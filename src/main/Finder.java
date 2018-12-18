package main;

import java.util.ArrayList;

public class Finder extends Thread {
    private final ArrayList<Integer> primes;
    private TestStatus status; // maybe be rebellious and make this public
    private boolean testActive;
    
    private int test, root;
    private final int START, INCREMENT;
    
    public Finder(ArrayList<Integer> list, int threadID, int threadCount) {
        primes = list;
        START = threadID;
        INCREMENT = threadCount;
    }
    
    public void run() {
        int prime, index;
        
        while (true) {
            try {
                synchronized (primes) {
                    primes.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            // at this point, test and root should be set, and boss should be ready to be notified.
            
            index = START;
            
            try {
                prime = primes.get(index);
            } catch (IndexOutOfBoundsException e) {
                status = TestStatus.PASS; // FIXME executing this line means that boss will never be notified (undesirable)
                continue; // to the top of this loop
            }
            
            while (testActive && prime <= root) {
            
            }
        }
    }
    
    public void beginTest(int test, int root) {
        this.test = test;
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