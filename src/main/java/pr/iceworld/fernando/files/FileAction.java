package pr.iceworld.fernando.files;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pr.iceworld.fernando.config.Config;
import pr.iceworld.fernando.consts.Const;
import pr.iceworld.fernando.utils.Md5Util;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class FileAction {
    private static final Logger logger = LoggerFactory.getLogger(FileAction.class);

    @Resource
    private Config config;

    public void doNormal() {
        logger.debug("config -- " + JSONObject.toJSONString(config));
        String sourcefilesPath = config.getSourceFile();
        sourcefilesPath = Const.CONFIG_PATH + "/" + sourcefilesPath;

        List<String> files = FileUtil.readFileByLine(sourcefilesPath);
        logger.debug("loaded source files " + JSON.toJSONString(files));
        File fTempFolder = new File(config.getTempFolder());
        FileUtil.createFileIfNotExist(fTempFolder);
        // 清除之前的文件
        if (FileUtil.clearFolder(fTempFolder)) {
            try {
                for (String file: files) {
                    doSourceFileAction(file);
                    doClassFileAction(file);
                }
            } catch (IOException ioe) {
                logger.error("File operation error.");
            }
        } else {
            logger.error("File DELETE ERROR.");
        }
    }

    public void doAdvanced() {
        logger.debug("config -- " + JSONObject.toJSONString(config));
        String sourcefilesPath = config.getSourceFile();
        sourcefilesPath = Const.CONFIG_PATH + "/" + sourcefilesPath;

        List<String> files = FileUtil.readFileByLine(sourcefilesPath);
        logger.debug("loaded source files " + JSON.toJSONString(files));
        String tempFolder = config.getTempFolder();
        logger.debug("tempFolder = " + tempFolder);
        File fTempFolder = new File(tempFolder);
        FileUtil.createFileIfNotExist(fTempFolder);
        // 清除之前的文件
        if (FileUtil.clearFolder(fTempFolder)) {
            String fileJar = config.getOriginalJarFolder() + "/" + config.getOriginalJarFilename();
            try {
                doExectJarAction(fileJar, tempFolder);
                for (String file: files) {
                    doSourceFileAction(file);
                    doClassFileAction(file);
                }
                String tempJarFile = config.getTempFolder() + "./../" + config.getTargetJarFilename();
                FileUtil.deleteFileIfExist(tempJarFile);
                doCompressJarAction(config.getTempFolder(), tempJarFile);
                doCopyJar2TargetFolder(tempJarFile);
            } catch (IOException ioe) {
                logger.error("File operation error.");
            }
        } else {
            logger.error("File DELETE ERROR.");
        }
    }


    private void doCopyJar2TargetFolder(String jarFile) {
        if (config.isCopyTempJar2TargetFolder()) {
            String finalTargetFile = config.getTargetJarFolder() + "/" + config.getTargetJarFilename();
            FileUtil.copyFile(jarFile, finalTargetFile);
            logger.info("ALREADY COPIED jar from temp folder to target jar folder.");
        }
    }

    protected void doExectJarAction(String file, String path) throws IOException {
        FileUtil.unZip(file, path);
    }

    protected void doCompressJarAction(String file, String targetFile) {
        //FileUtil.zip(targetFile, CompressType.JAR, file);
        NewFileUtil.zipFile(new File(targetFile), CompressType.JAR, new File(file));
    }

    /**
     * copy java to java
     * @param file
     * @throws IOException
     */
    private void doSourceFileAction(String file) throws IOException {
        String srcFile = config.getSourceFolder() + "/" + file;
        String targetFile = config.getTempFolder() + "/" + file;
        targetFile = filterOutFolder(targetFile, "src");
        File fSrcFile = new File(srcFile);
        if (!fSrcFile.exists()) {
            logger.warn("file IS NOT EXISTS - " + srcFile);
        }

        FileUtil.delFile(targetFile);
        FileUtil.copyFile(srcFile, targetFile);
        File fTargetFile = new File(targetFile);
        if (!fTargetFile.exists()) {
            logger.warn("file IS NOT EXISTS - " + fTargetFile);
        }
        String srcFileMd5 = Md5Util.getFileMd5Str(fSrcFile);
        String targetFileMd5 = Md5Util.getFileMd5Str(fTargetFile);
        if (StringUtils.equals(srcFileMd5, targetFileMd5)) {
            logger.info("VERIFIED successfully by md5, copy file from " + srcFile  + " to " + targetFile);
        }
    }

    /**
     * copy class to class
     * @param file
     * @throws IOException
     */
    private void doClassFileAction(String file) throws IOException {
        String srcFile = config.getCompiledClassFolder() + "/" + file;
        srcFile = filterOutFolder(srcFile, "src");
        String compiledFile = srcFile.substring(0, srcFile.indexOf(".java")) + ".class";
        String targetFile = config.getTempFolder() + compiledFile.substring(config.getCompiledClassFolder().length());
        File fSrcFile = new File(compiledFile);
        if (!fSrcFile.exists()) {
            logger.warn("file IS NOT EXISTS - " + compiledFile);
        }
        FileUtil.delFile(targetFile);
        FileUtil.copyFile(compiledFile, targetFile);
        File fTargetFile = new File(targetFile);
        if (!fTargetFile.exists()) {
            logger.warn("file IS NOT EXISTS - " + fTargetFile);
        }
        String compiledFileMd5 = Md5Util.getFileMd5Str(fSrcFile);
        String targetFileMd5 = Md5Util.getFileMd5Str(fTargetFile);
        if (StringUtils.equals(compiledFileMd5, targetFileMd5)) {
            logger.info("VERIFIED successfully by md5, copy file from " + compiledFile  + " to " + targetFile);
        }
    }

    public String filterOutFolder(String file, String foldername) {
        return file.replace("/" + foldername + "/", "/");
    }
}
