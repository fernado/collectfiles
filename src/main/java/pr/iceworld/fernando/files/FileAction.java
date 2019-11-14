package pr.iceworld.fernando.files;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import pr.iceworld.fernando.consts.Const;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
public class FileAction {

    public void doWork() {
        String sourcefilesPath = SysConst.getConfig().getSourceFile();
        Path path = Paths.get(sourcefilesPath);
        if (!path.isAbsolute()) {
            sourcefilesPath = Const.CONFIG_PATH + File.separator + sourcefilesPath;
        }
        System.out.println(path.isAbsolute());
        List<String> files = FileUtil.readFileByLine(sourcefilesPath);
        log.info("loaded source files " + JSON.toJSONString(files));

        for (String file: files) {
            String srcFile = SysConst.getConfig().getSourceFolder() + File.separator + file;
            String targetFile = SysConst.getConfig().getTempFolder() + File.separator + file;
            targetFile = filterOutFolder(targetFile, "src");
            FileUtil.copyFile(srcFile, targetFile);

            srcFile = SysConst.getConfig().getCompiledClassFolder() + File.separator + file;
            srcFile = filterOutFolder(srcFile, "src");
            String compiledFile = srcFile.substring(0, srcFile.indexOf(".java")) + ".class";

            targetFile = SysConst.getConfig().getTempFolder() + compiledFile.substring(SysConst.getConfig().getCompiledClassFolder().length());
            FileUtil.copyFile(compiledFile, targetFile);
        }
    }

    public String filterOutFolder(String file, String foldername) {
        return file.replace(File.separator + foldername + File.separator, File.separator);
    }
}
