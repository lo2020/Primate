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
        boolean allPassed;
        
        // TODO let ui thread interfere with boss
        while (true) {
            synchronized (this) { // not so sure about the need for a synchronized block here
                for (Finder finder : finders) {
                    finder.beginTest(test, root);
                }
                
                // this loop is supposed to wait until a failed test or all finders passed
                // should be notified by finders
                waitLoop: while (true) {
                    try {
                        this.wait(); // should release this guy's monitor for the finders (or something like that) (probably) (?)
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    
                    allPassed = true;
                    
                    // at this point, boss has its own monitor back
                    for (Finder finder : finders) {
                        switch (finder.getTestStatus()) {
                            case RUNNING:
                                allPassed = false;
                                continue; // continue this for-each loop
                            
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
            }
            
            test += 2;
            
            if (root * root > test) {
                root++;
            }
        }
    }
}