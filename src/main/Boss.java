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
        
        primes.notifyAll();
        
        while (true) {
            for (int i = 0; i < finders.length; i++) {
                finders[i].beginTest(test, root);
            }
        }
    }
}