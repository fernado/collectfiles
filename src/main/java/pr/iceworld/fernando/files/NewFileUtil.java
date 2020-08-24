package pr.iceworld.fernando.files;

import org.apache.commons.compress.archivers.jar.JarArchiveEntry;
import org.apache.commons.compress.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.jar.JarOutputStream;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;

public class NewFileUtil {
    public static final int BUFFER = 4096;

    private static final Logger logger = LoggerFactory.getLogger(NewFileUtil.class);

    public static void zip(String name) {
        File file = new File("C:\\Users\\dell\\Desktop\\20200818-BCM\\ApacheHttpUtil.class");
        JarOutputStream jarOutput;
        try {
            JarArchiveEntry entry = new JarArchiveEntry("ApacheHttpUtil.class");
            FileOutputStream outputStream = new FileOutputStream("C:\\Users\\dell\\Desktop\\20200818-BCM\\aa.jar");
            jarOutput = new JarOutputStream(outputStream);
            jarOutput.putNextEntry(entry);
            IOUtils.copy(new FileInputStream(file), jarOutput);
            jarOutput.flush();
            jarOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        File file = new File("C:\\Users\\dell\\Desktop\\20200818-BCM\\ApacheHttpUtil.class");

        String filename = file.getName();
        System.out.println("filename = " + filename);
    }

    public static void zipFile(File targetFile, CompressType compressType, File srcPath) {
        if (CompressType.JAR == compressType) {
            FileOutputStream outputStream = null;
            JarOutputStream jarOutput = null;
            try {
                outputStream = new FileOutputStream(targetFile);
                CheckedOutputStream cos = new CheckedOutputStream(outputStream,
                        new CRC32());
                jarOutput = new JarOutputStream(cos);
                zipFile(targetFile, jarOutput, srcPath, srcPath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IOUtils.closeQuietly(jarOutput);
            }
        } else {
            throw new RuntimeException("Not Support compress type");
        }
    }

    public static void zipFile(File targetFile, JarOutputStream jarOutputStream, File baseDir, File srcPath) {
        if (srcPath.isDirectory()) {
            zipDirectory(targetFile, jarOutputStream, baseDir, srcPath);
        } else {
            zipFile(jarOutputStream, baseDir, srcPath);
        }
    }


    public static void zipFile(JarOutputStream jarOutputStream, File basedir, File srcFile) {
        if (!srcFile.exists()) {
            throw new RuntimeException(srcFile + "不存在！");
        }
        if (srcFile.isDirectory()) {
            return;
        }
        String filename = srcFile.getAbsolutePath().substring(basedir.getAbsolutePath().length() + 1);
        String[] ss = filename.split("/");
        try {
            logger.debug("filename = {}", filename);
            JarArchiveEntry entry = new JarArchiveEntry(filename);
            jarOutputStream.putNextEntry(entry);
            entry.setTime(srcFile.lastModified());
            IOUtils.copy(new FileInputStream(srcFile), jarOutputStream);
            jarOutputStream.setLevel(ss.length);
            jarOutputStream.closeEntry();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 压缩一个目录
     */
    private static void zipDirectory(File targetFile, JarOutputStream jarOutputStream, File baseDir, File srcPath) {
        if (!srcPath.exists()) {
            return;
        }
        File[] files = srcPath.listFiles();
        for (int i = 0; i < files.length; i++) {
            zipFile(targetFile, jarOutputStream, baseDir, files[i]);
        }
    }
}
