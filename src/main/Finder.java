package main;

import java.util.ArrayList;

public class Finder extends Thread {
    private ArrayList<Integer> primes;
    private TestStatus status; // maybe be rebellious and make this public
    
    public Finder(ArrayList<Integer> list, int threadID, int threadCount) {
        primes = list;
    }
    
    public void run() {
    
    }
    
    public void beginTest(int number, int root) {
    
    }
    
    public TestStatus getTestStatus() {
        return status;
    }
}

enum TestStatus {
    RUNNING, PASS, FAIL
}