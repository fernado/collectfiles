package pr.iceworld.fernando.files;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import pr.iceworld.fernando.consts.Const;
import pr.iceworld.fernando.utils.Md5Util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
public class FileAction {

    public void doNormal() {
        String sourcefilesPath = SysConst.getConfig().getSourceFile();
        Path path = Paths.get(sourcefilesPath);
        if (!path.isAbsolute()) {
            sourcefilesPath = Const.CONFIG_PATH + "/" + sourcefilesPath;
        }

        List<String> files = FileUtil.readFileByLine(sourcefilesPath);
        log.debug("loaded source files " + JSON.toJSONString(files));
        String tempFolder = SysConst.getConfig().getTempFolder();
        // 清除之前的文件
        if (deleteFolder(tempFolder)) {
            try {
                for (String file: files) {
                    doSourceFileAction(file);
                    doClassFileAction(file);
                }
            } catch (IOException ioe) {
                log.error("File operation error.");
            }
        } else {
            log.error("File DELETE ERROR.");
        }
    }

    public void doAdvanced() {
        String sourcefilesPath = SysConst.getConfig().getSourceFile();
        Path path = Paths.get(sourcefilesPath);
        if (!path.isAbsolute()) {
            sourcefilesPath = Const.CONFIG_PATH + "/" + sourcefilesPath;
        }

        List<String> files = FileUtil.readFileByLine(sourcefilesPath);
        log.debug("loaded source files " + JSON.toJSONString(files));
        String tempFolder = SysConst.getConfig().getTempFolder();
        // 清除之前的文件
        if (deleteFolder(tempFolder)) {
            String fileJar = SysConst.getConfig().getOriginalJarFolder() + "/" + SysConst.getConfig().getOriginalJarFilename();
            try {
                doExectJarAction(fileJar, tempFolder);
                for (String file: files) {
                    doSourceFileAction(file);
                    doClassFileAction(file);
                }
                String targetFile = SysConst.getConfig().getTempFolder() + "./../" + SysConst.getConfig().getTargetJarFilename();
                File fTargetFile = new File(targetFile);
                if (fTargetFile.exists()) {
                    fTargetFile.delete();
                }

                doCompressJarAction(SysConst.getConfig().getTempFolder(), targetFile);
            } catch (IOException ioe) {
                log.error("File operation error.");
            }
        } else {
            log.error("File DELETE ERROR.");
        }
    }


    private boolean deleteFolder(String file) {
        return FileUtil.delFile(file + "/");
    }

    protected void doExectJarAction(String file, String path) throws IOException {
        FileUtil.unZip(file, path);
    }

    protected void doCompressJarAction(String file, String targetFile) {
        FileUtil.zip(file, targetFile, CompressType.JAR);
    }

    /**
     * copy java to java
     * @param file
     * @throws IOException
     */
    private void doSourceFileAction(String file) throws IOException {
        String srcFile = SysConst.getConfig().getSourceFolder() + "/" + file;
        String targetFile = SysConst.getConfig().getTempFolder() + "/" + file;
        targetFile = filterOutFolder(targetFile, "src");
        File fSrcFile = new File(srcFile);
        if (!fSrcFile.exists()) {
            log.warn("file IS NOT EXISTS - " + srcFile);
        }

        FileUtil.delFile(targetFile);
        FileUtil.copyFile(srcFile, targetFile);
        File fTargetFile = new File(targetFile);
        if (!fTargetFile.exists()) {
            log.warn("file IS NOT EXISTS - " + fTargetFile);
        }
        String srcFileMd5 = Md5Util.getFileMd5Str(fSrcFile);
        String targetFileMd5 = Md5Util.getFileMd5Str(fTargetFile);
        if (StringUtils.equals(srcFileMd5, targetFileMd5)) {
            log.info("VERIFIED successfully by md5, copy file from " + srcFile  + " to " + targetFile);
        }
    }

    /**
     * copy class to class
     * @param file
     * @throws IOException
     */
    private void doClassFileAction(String file) throws IOException {
        String srcFile = SysConst.getConfig().getCompiledClassFolder() + "/" + file;
        srcFile = filterOutFolder(srcFile, "src");
        String compiledFile = srcFile.substring(0, srcFile.indexOf(".java")) + ".class";
        String targetFile = SysConst.getConfig().getTempFolder() + compiledFile.substring(SysConst.getConfig().getCompiledClassFolder().length());
        File fSrcFile = new File(compiledFile);
        if (!fSrcFile.exists()) {
            log.warn("file IS NOT EXISTS - " + compiledFile);
        }
        FileUtil.delFile(targetFile);
        FileUtil.copyFile(compiledFile, targetFile);
        File fTargetFile = new File(targetFile);
        if (!fTargetFile.exists()) {
            log.warn("file IS NOT EXISTS - " + fTargetFile);
        }
        String compiledFileMd5 = Md5Util.getFileMd5Str(fSrcFile);
        String targetFileMd5 = Md5Util.getFileMd5Str(fTargetFile);
        if (StringUtils.equals(compiledFileMd5, targetFileMd5)) {
            log.info("VERIFIED successfully by md5, copy file from " + compiledFile  + " to " + targetFile);
        }
    }

    public String filterOutFolder(String file, String foldername) {
        return file.replace("/" + foldername + "/", "/");
    }
}
