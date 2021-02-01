import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static Object key = new Object();
    private static volatile char curChar;
    private static volatile char nextChar;
    private static volatile char printChar = 'A';
    private static ExecutorService executorService;

    public static void main(String[] args) {
        executorService = Executors.newFixedThreadPool(3);
        createThread('A', 'B');
        createThread('B', 'C');
        createThread('C', 'A');
    }

    public static void createThread(char curChar, char nextChar) {
        executorService.submit(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    synchronized (key) {
                        while (printChar != curChar) {
                            key.wait();
                        }
                        System.out.println(curChar);
                        printChar = nextChar;
                        key.notifyAll();
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

}
