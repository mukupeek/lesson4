import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static Object key = new Object();
    private static volatile char curChar = 'A';
    private static volatile char nextChar = 'B';

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
       executorService.submit(()-> {
           threadFor(curChar,nextChar);
           });
       nextChar = 'C';
        executorService.submit(()-> {
            threadFor(curChar,nextChar);
        });
        nextChar = 'A';
        executorService.submit(()-> {
            threadFor(curChar,nextChar);
        });
        nextChar = 'B';
    }
    public static void threadFor(char curChar, char nextChar){
        try {
            for (int i = 0; i < 5; i++) {
                synchronized (key) {
                    while (curChar != 'A') {
                            key.wait();
                        }
                    System.out.println(curChar);
                    curChar = nextChar;
                    key.notifyAll();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}


