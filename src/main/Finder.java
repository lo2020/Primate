package main;

import java.util.ArrayList;

public class Finder extends Thread {
    private ArrayList<Integer> primes;
    private TestStatus status; // maybe be rebellious and make this public
    
    public Finder(ArrayList<Integer> list, int threadID, int threadCount) {
        primes = list;
    }
    
    public void run() {
        // TODO complete Finder's main run method
    }
    
    public void beginTest(int number, int root) {
        // TODO complete beginTest method
    }
    
    public void endTest() {
        // TODO complete endTest method
    }
    
    public TestStatus getTestStatus() {
        return status;
    }
}

enum TestStatus {
    RUNNING, PASS, FAIL
}