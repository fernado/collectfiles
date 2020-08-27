package pr.iceworld.fernando;

import ch.qos.logback.classic.util.ContextInitializer;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import pr.iceworld.fernando.consts.Const;
import pr.iceworld.fernando.files.FileAction;

/**
 * Hello world!
 */
@ComponentScan(basePackages = "pr.iceworld.fernando.**")
public class AppMain {

    private static final Logger logger = LoggerFactory.getLogger(AppMain.class);
    public static void main(String[] args) {
        logger.debug("Load logback.xml from " + Const.CONFIG_PATH + "/logback.xml");
        System.setProperty(ContextInitializer.CONFIG_FILE_PROPERTY, Const.CONFIG_PATH + "/logback.xml");

        AnnotationConfigApplicationContext applicationContext = null;
        try {
            applicationContext = new AnnotationConfigApplicationContext(AppMain.class);//初始化IOC容器

            if (ArrayUtils.isEmpty(args)) {
                logger.warn("NORMAL RUNNING");
                ((FileAction) applicationContext.getBean("fileAction")).doNormal();
            } else {
                for (String arg: args) {
                    if ("type=advance".equals(arg)) {
                        logger.warn("ADVANCE RUNNING");
                        ((FileAction) applicationContext.getBean("fileAction")).doAdvanced();
                        break;
                    }
                }
            }
        } finally {
            if (applicationContext != null) {
                applicationContext.close();
                System.out.println("普通java程序执行完成,IOC容器关闭。。。");
            }
        }
    }
}
