package main;

import java.util.ArrayList;

public class Boss extends Thread {
    public void run() {
        final ArrayList<Integer> primes = new ArrayList<Integer>();
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
            synchronized (this) { // FIXME remove/optimize the synchronized block here
                for (Finder finder : finders) {
                    finder.beginTest(test, root);
                }
                
                primes.notifyAll(); // this may need to be changed later
                
                waitLoop: while (true) {
                    try {
                        this.wait(); // FIXME should be primes.wait()
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    
                    allPassed = true;
                    
                    // TODO optimize this loop so that you don't check finders you don't need to (maybe)
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
            }
            
            test += 2;
            
            if (root * root > test) {
                root++;
            }
        }
    }
}