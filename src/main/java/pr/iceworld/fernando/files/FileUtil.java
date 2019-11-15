package pr.iceworld.fernando.files;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarOutputStream;
import java.util.zip.*;

@Slf4j
public class FileUtil {

    public static final int BUFFER = 8192;

    public static boolean clearFolder(File file) {
        return FileUtil.delFile(file);
    }

    public static void createFileIfNotExist(File file) {
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static void deleteFileIfExist(String file) {
        File fTempJarFile = new File(file);
        if (fTempJarFile.exists()) fTempJarFile.delete();
    }

    /**
     * 拷贝文件
     *
     * @param srcfile
     * @param targetfile
     */
    public static void copyFile(String srcfile, String targetfile) {
        createParentFolders(targetfile);
        try (
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcfile));
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(targetfile));
        ) {
            int len;
            while ((len = bis.read()) != -1) {
                bos.write(len);
            }
            bos.flush();
            log.debug("copy file from " + srcfile + " to " + targetfile);
        } catch (Exception e) {
            log.warn("FAILUED when coping file from " + srcfile + " to " + targetfile);
        }
    }

    /**
     * 拷贝文件
     *
     * @param srcfile
     * @param targetfile
     */
    public static void copyFile(File srcfile, File targetfile) {
        createParentFolders(targetfile);
        try (
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcfile));
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(targetfile));
        ) {
            int len;
            while ((len = bis.read()) != -1) {
                bos.write(len);
            }
            bos.flush();
            log.debug("copy file from " + srcfile + " to " + targetfile);
        } catch (Exception e) {
            log.warn("FAILUED when coping file from " + srcfile + " to " + targetfile);
        }
    }

    private static void copy(InputStream input, OutputStream output) {
        try (
                BufferedInputStream bis = new BufferedInputStream(input);
                BufferedOutputStream bos = new BufferedOutputStream(output);
        ) {
            int len;
            while ((len = bis.read()) != -1) {
                bos.write(len);
            }
            bos.flush();
            log.debug("copy stream from input to output");
        } catch (Exception e) {
            log.warn("FAILUED when copy stream from input to output");
        }
    }

    /**
     * 通过文件创建父级目录
     *
     * @param targetfile
     */
    public static void createParentFolders(String targetfile) {
        File fTargetFile = new File(targetfile.substring(0, targetfile.lastIndexOf("/")));
        if (!fTargetFile.isDirectory()) {
            fTargetFile.mkdirs();
        }
    }

    /**
     * 通过文件创建父级目录
     *
     * @param targetfile
     */
    public static void createParentFolders(File targetfile) {
        File pTargetFile = targetfile.getParentFile();
        if (!pTargetFile.isDirectory()) {
            pTargetFile.mkdirs();
        }
    }

    public static boolean delFile(String file) {
        return delFile(new File(file));
    }

    public static boolean delFile(File file) {
        if (!file.exists()) {
            return false;
        }

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                delFile(f);
            }
        }
        return file.delete();
    }

    /**
     * 按行读取文件
     */
    public static List<String> readFileByLine(String filename) {
        File file = new File(filename);
        List<String> lines = new ArrayList<>();
        try (InputStream is = new FileInputStream(file);
             Reader reader = new InputStreamReader(is);
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            log.error("Read file by line ERROR - " + filename);
        }
        return lines;
    }

    public static void zip(String zipFile, CompressType compressType, String... pathName) {
        ZipOutputStream out;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
            CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream,
                    new CRC32());
            if (CompressType.JAR == compressType) {
                out = new JarOutputStream(cos);
            } else {
                out = new ZipOutputStream(cos);
            }
            String basedir = "";
            for (int i = 0; i < pathName.length; i++) {
                zip(new File(pathName[i]), out, basedir);
            }
            out.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void zip(String zipFile, CompressType compressType, String srcPathName) {
        File file = new File(srcPathName);
        if (!file.exists()) {
            throw new RuntimeException(srcPathName + "不存在！");
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
            CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream,
                    new CRC32());
            ZipOutputStream out;
            if (CompressType.JAR == compressType) {
                out = new JarOutputStream(cos);
            } else {
                out = new ZipOutputStream(cos);
            }
            String basedir = "";
            zip(file, out, basedir);
            out.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void zip(File file, ZipOutputStream out, String basedir) {
        /* 判断是目录还是文件 */
        if (file.isDirectory()) {
            log.debug("zip file - " + basedir + file.getName());
            zipDirectory(file, out, basedir);
        } else {
            log.debug("zip file - " + basedir + file.getName());
            zipFile(file, out, basedir);
        }
    }

    /**
     * 压缩一个目录
     */
    private static void zipDirectory(File dir, ZipOutputStream out, String basedir) {
        if (!dir.exists())
            return;

        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            /* 递归 */
            zip(files[i], out, basedir + dir.getName() + "/");
        }
    }

    /**
     * 压缩一个文件
     */
    private static void zipFile(File file, ZipOutputStream out, String basedir) {
        if (!file.exists()) {
            return;
        }
        try {
            BufferedInputStream bis = new BufferedInputStream(
                    new FileInputStream(file));
            ZipEntry entry = new ZipEntry(basedir + file.getName());
            out.putNextEntry(entry);
            int count;
            byte data[] = new byte[BUFFER];
            while ((count = bis.read(data, 0, BUFFER)) != -1) {
                out.write(data, 0, count);
            }
            bis.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 解压压缩包到指定目录
     *
     * @param inputFile
     * @param outputFile
     */
    public static void unZip(String inputFile, String outputFile) throws IOException {
        unZip(new File(inputFile), new File(outputFile));
    }

    /**
     * 解压压缩包到指定目录
     *
     * @param inputFile
     * @param outputFile
     */
    public static void unZip(File inputFile, File outputFile) throws IOException {
        if (!outputFile.exists()) {
            outputFile.mkdirs();
        }

        ZipEntry entry = null;
        OutputStream output;
        InputStream input = null;
        File file = null;
        try (
                ZipFile zipFile = new ZipFile(inputFile);
                ZipInputStream zipInput = new ZipInputStream(new FileInputStream(inputFile));
        ) {
            String path = outputFile.getAbsolutePath() + "/";
            while ((entry = zipInput.getNextEntry()) != null) {
                // 拼装压缩后真实文件路径
                String fileName = path + entry.getName();

                log.debug("unZip filename = " + fileName);
                // 创建文件缺失的目录（不然会报异常：找不到指定文件）
                file = new File(fileName);
                // 可能是文件夹
                if (!file.exists()) {
                    if (entry.getName().substring(entry.getName().length() - 1).equals("/")) {
                        file.mkdirs();
                    } else {
                        if (!file.getParentFile().exists()) {
                            createParentFolders(fileName);
                        }
                        file.createNewFile();
                    }
                }
                if (!file.isDirectory()) {
                    // 从压缩文件里获取指定已压缩文件的输入流
                    input = zipFile.getInputStream(entry);
                    // 创建文件输出流
                    output = new FileOutputStream(fileName);
                    // 创建新文件
                    copy(input, output);
                    close(output);
                }
            }
        } catch (ZipException e) {
            log.error("ERROR WHEN ZIP file. " + (null != entry ? entry.getName() : ""));
            throw e;
        } catch (IOException e) {
            log.error("ERROR WHEN ZIP file - file might not exists. " + (null != file ? file.getAbsolutePath() : ""));
            throw e;
        } finally {
            try {
                close(input);
            } catch (IOException e) {
                log.error("ERROR CLOSE stream.");
            }
        }
    }

    public static void close(OutputStream os) throws IOException {
        if (os != null) {
            os.close();
        }
    }

    public static void close(InputStream is) throws IOException {
        if (is != null) {
            is.close();
        }
    }

}