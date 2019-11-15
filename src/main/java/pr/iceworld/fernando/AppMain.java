package pr.iceworld.fernando;

import ch.qos.logback.classic.util.ContextInitializer;
import pr.iceworld.fernando.consts.Const;
import pr.iceworld.fernando.files.FileAction;

/**
 * Hello world!
 */
public class AppMain {
    public static void main(String[] args) {
        System.setProperty(ContextInitializer.CONFIG_FILE_PROPERTY, Const.CONFIG_PATH + "/logback.xml");
        new FileAction().doWork();
    }

}
