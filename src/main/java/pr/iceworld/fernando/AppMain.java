package pr.iceworld.fernando;

import ch.qos.logback.classic.util.ContextInitializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import pr.iceworld.fernando.consts.Const;
import pr.iceworld.fernando.files.FileAction;

/**
 * Hello world!
 */
@Slf4j
public class AppMain {
    public static void main(String[] args) {
        log.debug("Load logback.xml from " + Const.CONFIG_PATH + "/logback.xml");
        System.setProperty(ContextInitializer.CONFIG_FILE_PROPERTY, Const.CONFIG_PATH + "/logback.xml");

        if (ArrayUtils.isEmpty(args)) {
            log.warn("NORMAL RUNNING");
            new FileAction().doNormal();
        } else {
            for (String arg: args) {
                if ("type=advance".equals(arg)) {
                    log.warn("ADVANCE RUNNING");
                    new FileAction().doAdvanced();
                    break;
                }
            }
        }
    }
}
