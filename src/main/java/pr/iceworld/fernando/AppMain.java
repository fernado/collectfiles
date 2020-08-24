package pr.iceworld.fernando;

import ch.qos.logback.classic.util.ContextInitializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import pr.iceworld.fernando.consts.Const;
import pr.iceworld.fernando.files.FileAction;

/**
 * Hello world!
 */
@Slf4j
@ComponentScan
@PropertySource("file:${user.dir}/conf/config.properties")
public class AppMain {


    public static void main(String[] args) {
        log.debug("Load logback.xml from " + Const.CONFIG_PATH + "/logback.xml");
        System.setProperty(ContextInitializer.CONFIG_FILE_PROPERTY, Const.CONFIG_PATH + "/logback.xml");

        AnnotationConfigApplicationContext applicationContext = null;
        try {
            applicationContext = new AnnotationConfigApplicationContext(AppMain.class);//初始化IOC容器

            if (ArrayUtils.isEmpty(args)) {
                log.warn("NORMAL RUNNING");
                ((FileAction) applicationContext.getBean("fileAction")).doNormal();
            } else {
                for (String arg: args) {
                    if ("type=advance".equals(arg)) {
                        log.warn("ADVANCE RUNNING");
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
