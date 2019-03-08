import lejos.nxt.Button;

public class HelloWorld {
    public static void main (String[] args) {
        String helloWorld = "Hello world!";
        System.out.println(helloWorld);
        System.gc();
        Button.waitForAnyPress();
    }
}