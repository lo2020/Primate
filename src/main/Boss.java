package main;

import java.util.ArrayList;

// look into the class ThreadGroup to see if it might give any advantages

public class Boss extends Thread {
    private final ArrayList<Integer> primes = new ArrayList<>(100000000); // initial capacity is 100 million
    
    private final int PROCESSORS = Runtime.getRuntime().availableProcessors();
    private final Finder[] threads = new Finder[PROCESSORS];
    
    private int test;
    private int root;
    
    @Override
    public void run() {
        // TODO replace these private methods with the real stuff when it all works
        setupThreads();
        setupPrimes();
        setupVars(); // set up test, root, and other things that need setting up
        
        while (true) {
            waitForReadyThreads(); // wait until all threads are waiting
            prepThreads();
            startThreads();
            
            while (true) {
                waitForNotification();
                
                if (testFailed()) {
                    stopThreads();
                    break;
                } else if (testPassed()) {
                    stopThreads();
                    updatePrimes();
                    break;
                }
            }
            
            updateVars();
        }
    }
    
    // all of the methods below are helper methods just for organization
    
    private void setupThreads() {
    
    }
    
    private void setupPrimes() {
    
    }
    
    private void setupVars() {
    
    }
    
    private void waitForReadyThreads() {
    
    }
    
    private void prepThreads() {
    
    }
    
    private void startThreads() {
    
    }
    
    private void stopThreads() {
    
    }
    
    private void waitForNotification() {
    
    }
    
    private boolean testFailed() {
        return false;
    }
    private boolean testPassed() {
        return false;
    }
    
    private void updatePrimes() {
    
    }
    
    private void updateVars() {
    
    }
}

// below is the old and broken file. above is the real stuffs.
/*
package main;

import java.util.ArrayList;

public class Boss extends Thread {
    public void run() {
        final ArrayList<Integer> primes = new ArrayList<Integer>();
        // exclude 2 because even integers are not tested for primeness
        primes.add(3);
        primes.add(5);
        primes.add(7);
        primes.add(11);
        primes.add(13);
        primes.add(17);
        primes.add(19);
        primes.add(23);
        // FIXME added too many initial values
        
        int processors = Runtime.getRuntime().availableProcessors();
        Finder[] finders = new Finder[processors];
        
        for (int i = 0; i < processors; i++) {
            finders[i] = new Finder(primes, i, processors, this);
            finders[i].setPriority(10);
            finders[i].start();
        }
        
        // wait until all finders are waiting
        initialLoop: while (true) {
            Thread.yield(); // TODO check to see if there are better ways of waiting
            
            for (Finder finder : finders) {
                if (finder.getTestStatus() != TestStatus.WAITING) {
                    continue initialLoop;
                }
            }
            
            break; // because all finders must be waiting
        }
        
        int test = primes.get(primes.size() - 1) + 2;
        int root = (int) Math.ceil(Math.sqrt(test));
        boolean allPassed;
        
        // TODO let ui thread interfere/interface with boss
        // TODO redo the synchronization stuffs
        synchronized (this) {
            while (true) {
                for (Finder finder : finders) {
                    finder.beginTest(test, root);
                }
                
                waitLoop: while (true) {
                    try {
                        this.wait(); // reminder: by calling 'wait()', you release the lock on 'this'
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    
                    // when you reach this point, at least one finder has passed or failed its test
                    
                    allPassed = true;
                    
                    for (Finder finder : finders) {
                        switch (finder.getTestStatus()) {
                            case RUNNING:
                                allPassed = false;
                                continue; // to look for failed tests
                            
                            case FAIL:
                                break waitLoop;
                            
                            // you don't need to do anything if a thread has passed the test
                        }
                    }
                    
                    if (allPassed) {
                        primes.add(test);
                        System.out.println(test); // FIXME remove this line when UI is finished
                        break; // out of waitLoop, of course
                    }
                }
                
                // stops finders that don't need stopping, probably not worth fixing
                for (Finder finder : finders) {
                    finder.endTest();
                }
                
                test += 2;
                
                if (root * root > test) {
                    root++;
                }
            }
        }
    }
}
*/