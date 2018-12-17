package main;

public class Main {
    public static void main(String[] args) {
        Boss boss = new Boss();
        boss.setPriority(10);
        boss.start();
    }
}