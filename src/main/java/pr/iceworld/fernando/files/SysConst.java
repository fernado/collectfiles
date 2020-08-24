//package pr.iceworld.fernando.files;
//
//import com.alibaba.fastjson.JSON;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import pr.iceworld.fernando.consts.Const;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Properties;
//
//@Slf4j
//@Component
//public class SysConst {
//
//    private
//
//    static Config config = null;
//    public static final Config getConfig() {
//        if (null == config) {
//            config = new Config();
//            Properties properties = loadProperties();
//            Map<String, String> all = new HashMap<>();
//            all.put("source-file", properties.getProperty("source-file"));
//            all.put("source-folder", properties.getProperty("source-folder"));
//            all.put("compiled-class-folder", properties.getProperty("compiled-class-folder"));
//            all.put("temp-folder", properties.getProperty("temp-folder"));
//            all.put("target-jar-folder", properties.getProperty("target-jar-folder"));
//            all.put("target-jar-filename", properties.getProperty("target-jar-filename"));
//            all.put("original-jar-filename", properties.getProperty("original-jar-filename"));
//            all.put("original-jar-folder", properties.getProperty("original-jar-folder"));
//            all.put("copy-temp-jar-2-target-folder", properties.getProperty("copy-temp-jar-2-target-folder"));
//
//            for (Map.Entry<String, String> me: all.entrySet()) {
//
//            }
//
//            config.setSourceFile(properties.getProperty("source-file"));
//            config.setSourceFolder(properties.getProperty("source-folder"));
//            config.setCompiledClassFolder(properties.getProperty("compiled-class-folder"));
//            config.setTempFolder(properties.getProperty("temp-folder"));
//            config.setTargetJarFolder(properties.getProperty("target-jar-folder"));
//            config.setTargetJarFilename(properties.getProperty("target-jar-filename"));
//            config.setOriginalJarFilename(properties.getProperty("original-jar-filename"));
//            config.setOriginalJarFolder(properties.getProperty("original-jar-folder"));
//            config.setCopyTempJar2TargetFolder(Boolean.valueOf(properties.getProperty("copy-temp-jar-2-target-folder")));
//            log.debug("config " + JSON.toJSONString(config));
//        }
//        return config;
//    }
//
//    private static Properties loadProperties() {
//        Properties properties = new Properties();
//        try {
//            properties.load(new FileInputStream(Const.CONFIG_FILE_PATH));
//        } catch (IOException e) {
//            log.error("Load config file error!");
//        }
//        return properties;
//    }
//}
