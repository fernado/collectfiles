package pr.iceworld.fernando;

import pr.iceworld.fernando.files.FileAction;

/**
 * Hello world!
 */
public class AppMain {
    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        new FileAction().doWork();
    }

}
