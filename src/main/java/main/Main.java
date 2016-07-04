package main;

import application.App;

public class Main {
    public static void main(String[] args) {
        System.out.println("Please enter a string of integers separated with comma(,) for example: 7,9,4,2");
        new App(System.in, System.out).run();
    }
}
