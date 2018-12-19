package main;

import java.util.ArrayList;

public class Boss extends Thread {
    public void run() {
        final ArrayList<Integer> primes = new ArrayList<Integer>();
        primes.add(3);
        primes.add(5);
        primes.add(7);
        primes.add(11);
        primes.add(13);
        primes.add(17);
        // FIXME added too many initial values
        
        int processors = Runtime.getRuntime().availableProcessors();
        Finder[] finders = new Finder[processors];
        
        for (int i = 0; i < processors; i++) {
            finders[i] = new Finder(primes, i, processors, this);
            finders[i].setPriority(10);
            finders[i].start();
        }
        
        int test = primes.get(primes.size() - 1) + 2;
        int root = (int) Math.ceil(Math.sqrt(test)); // CHECKME should work
        boolean allPassed;
        
        // TODO let ui thread interfere/interface with boss
        while (true) {
            for (Finder finder : finders) {
                finder.beginTest(test, root);
            }
            
            synchronized (primes) {
                primes.notifyAll(); // this may need to be changed later
            }
            
            waitLoop: while (true) {
                try {
                    synchronized (this) {
                        this.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
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