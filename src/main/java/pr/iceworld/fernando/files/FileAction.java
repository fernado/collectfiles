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

    public void doWork() {
        String sourcefilesPath = SysConst.getConfig().getSourceFile();
        Path path = Paths.get(sourcefilesPath);
        if (!path.isAbsolute()) {
            sourcefilesPath = Const.CONFIG_PATH + "/" + sourcefilesPath;
        }

        List<String> files = FileUtil.readFileByLine(sourcefilesPath);
        log.debug("chcp 65001 loaded source files " + JSON.toJSONString(files));

        try {
            for (String file: files) {
                // copy java to java
                String srcFile = SysConst.getConfig().getSourceFolder() + "/" + file;
                String targetFile = SysConst.getConfig().getTempFolder() + "/" + file;
                targetFile = filterOutFolder(targetFile, "src");
                FileUtil.copyFile(srcFile, targetFile);
                String srcFileMd5 = Md5Util.getFileMd5Str(new File(srcFile));
                String targetFileMd5 = Md5Util.getFileMd5Str(new File(targetFile));
                if (StringUtils.equals(srcFileMd5, targetFileMd5)) {
                    log.info("chcp 65001 VERIFIED successfully by md5, copy file from " + srcFile  + " to " + targetFile);
                }

                // copy class to class
                srcFile = SysConst.getConfig().getCompiledClassFolder() + "/" + file;
                srcFile = filterOutFolder(srcFile, "src");
                String compiledFile = srcFile.substring(0, srcFile.indexOf(".java")) + ".class";
                targetFile = SysConst.getConfig().getTempFolder() + compiledFile.substring(SysConst.getConfig().getCompiledClassFolder().length());
                FileUtil.copyFile(compiledFile, targetFile);
                String compiledFileMd5 = Md5Util.getFileMd5Str(new File(compiledFile));
                targetFileMd5 = Md5Util.getFileMd5Str(new File(targetFile));
                if (StringUtils.equals(compiledFileMd5, targetFileMd5)) {
                    log.info("chcp 65001 VERIFIED successfully by md5, copy file from " + compiledFile  + " to " + targetFile);
                }
            }
        } catch (IOException ioe) {
            log.error("chcp 65001 File operation error.");
        }

    }

    public String filterOutFolder(String file, String foldername) {
        return file.replace("/" + foldername + "/", "/");
    }
}
