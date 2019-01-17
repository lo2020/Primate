package main;

import java.util.ArrayList;

public class Finder extends Thread {
    private final ArrayList<Integer> primes;
    
    private int prime;
    private int index;
    
    private int testNum;
    private int rootCeil;
    
    private boolean testRunning;
    
    private final int START;
    private final int INCREMENT;
    
    public Finder(ArrayList<Integer> list, int start, int increment) {
        this.primes = list;
        this.START = start;
        this.INCREMENT = increment;
    }
    
    @Override
    public void run() {
        // TODO get rid of the helper methods and replace them with real stuff
        
        while (true) {
            waitForBoss();
            
            setupVars(); // different from the one in Boss.java, of course
            
            while (testRunning) {
                if (primeInRange()) {
                    if (modFailed()) { // if the testing number % the current prime is zero
                        tellBossFailed();
                        break;
                    } else {
                        updateVars();
                    }
                } else {
                    tellBossPassed();
                    break;
                }
            }
        }
    }
    
    private void waitForBoss() {
    
    }
    
    private void setupVars() {
    
    }
    
    private boolean primeInRange() {
        return false; // unfinished, of course
    }
    
    private boolean modFailed() {
        return false; // also unfinished, of course
    }
    
    private void tellBossFailed() {
    
    }
    
    private void tellBossPassed() {
    
    }
    
    private void updateVars() {
    
    }
}

// the code below is broken. above is the real file.
/*
package main;

import java.util.ArrayList;

public class Finder extends Thread {
    private final ArrayList<Integer> primes;
    private TestStatus status = TestStatus.RUNNING; // FIXME make this public to improve performance by a bit
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
                synchronized (boss) { // can only execute this block if boss is waiting
                    status = TestStatus.WAITING;
                    primes.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            // at this point, testNum and root should be set, and boss should be ready to be notified.
            
            index = START;
            prime = primes.get(index);
            
            while (testActive && prime <= root) { // TODO potentially reverse the order of these operands
                if (testNum % prime == 0) { // here's the money maker
                    status = TestStatus.FAIL; // all caps for enhanced drama
                    
                    boss.notify();
                    
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

enum TestStatus {
    RUNNING, PASS, FAIL, WAITING
}
*/