package ru.babak.unlike;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        LinksGetter linksGetter = new LinksGetter();
        linksGetter.start();

        System.in.read();

    }

}
