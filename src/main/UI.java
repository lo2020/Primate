package main;

import java.util.ArrayList;

public class UI extends Thread {
    private ArrayList primes;
    
    public UI(ArrayList primes) {
        this.primes = primes;
    }
    
    public void run() {
        while (true) {
            try {
                System.in.read();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
            
            System.out.println(primes.get(primes.size() - 1));
        }
    }
}