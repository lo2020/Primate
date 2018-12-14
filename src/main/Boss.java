package main;

import java.util.ArrayList;

public class Boss extends Thread {
    public void run() {
        ArrayList<Integer> primes = new ArrayList<Integer>();
        primes.add(2);
        
        int processors = Runtime.getRuntime().availableProcessors();
        Finder[] finders = new Finder[processors];
        
        for (int i = 0; i < processors; i++) {
            finders[i] = new Finder(primes, i, processors);
            finders[i].setPriority(10);
            finders[i].start();
        }
        
        int test = 3;
        int root = 2;
        boolean isPrime;
        
        // TODO let ui thread interface with boss
        while (true) {
            for (Finder finder : finders) {
                finder.beginTest(test, root);
            }
            
            isPrime = true;
            
            /*
            // FIXME replace this with a way to wait until a finder notifies it
            waitForFinders: while (true) {
                for (Finder finder : finders) {
                    // FIXME no Thread.yield() call while waiting
                    // FIXME can only break out of waitForFinders on a failed test
                    
                    switch (finder.getTestStatus()) { // make sure to look up why you don't need the path to the enum item
                        // TODO reorder these cases to optimize
                        
                        case RUNNING:
                            continue; // should continue with the for loop
                        
                        case FAIL:
                            isPrime = false;
                            
                            for (Finder f : finders) {
                                f.endTest();
                            }
                            
                            break waitForFinders; // for the while loop named above
                        
                        case PASS:
                            break; // break out of the switch statement, not the for loop;
                        
                    }
                }
            }
            */
            
            // this loop is supposed to wait until a failed test or all finders passed
            while (true) {
            
            }
            
            if (isPrime) {
                primes.add(test);
            }
            
            test += 2;
            
            if (root * root > test) {
                root++;
            }
        }
    }
}