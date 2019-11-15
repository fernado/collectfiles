package pr.iceworld.fernando;

import ch.qos.logback.classic.util.ContextInitializer;
import org.apache.commons.lang3.ArrayUtils;
import pr.iceworld.fernando.consts.Const;
import pr.iceworld.fernando.files.FileAction;

/**
 * Hello world!
 */
public class AppMain {
    public static void main(String[] args) {
        System.setProperty(ContextInitializer.CONFIG_FILE_PROPERTY, Const.CONFIG_PATH + "/logback.xml");
        if (ArrayUtils.isEmpty(args)) {
            new FileAction().doNormal();
        } else {
            for (String arg: args) {
                if ("type=advanced".equals(arg)) {
                    new FileAction().doAdvanced();
                }
                break;
            }
        }
    }

}
