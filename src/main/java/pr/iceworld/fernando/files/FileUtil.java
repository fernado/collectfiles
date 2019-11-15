package pr.iceworld.fernando.files;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarOutputStream;
import java.util.zip.*;

@Slf4j
public class FileUtil {

    /**
     * 拷贝文件
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
            log.warn("FAILUED when coping file from " + srcfile  + " to " + targetfile);
        }
    }

    /**
     * 拷贝文件
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
            log.warn("FAILUED when coping file from " + srcfile  + " to " + targetfile);
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

    public static void zip(String inputFile, String outputFile, CompressType type) {
        zip(new File(inputFile), new File(outputFile), type);
    }

    /**
     * 初始化压缩包信息并开始进行压缩
     *
     * @param inputFile  需要压缩的文件或文件夹
     * @param outputFile 压缩后的文件
     * @param type       压缩类型
     */
    public static void zip(File inputFile, File outputFile, CompressType type) {
        ZipOutputStream zos = null;
        try {
            if (type == CompressType.ZIP) {
                zos = new ZipOutputStream(new FileOutputStream(outputFile));
            } else if (type == CompressType.JAR) {
                zos = new JarOutputStream(new FileOutputStream(outputFile));
            } else {
                zos = new ZipOutputStream(new FileOutputStream(outputFile));
            }
            zipFile(zos, inputFile, null);
            log.info("FINISHED ZIP");
        } catch (IOException e) {
            log.error("FAILED TO ZIP");
        }
    }

    /**
     * 压缩单个文件到指定压缩流里
     *
     * @param zos       压缩输出流
     * @param inputFile 需要压缩的文件
     * @param path      需要压缩的文件在压缩包里的路径
     * @throws FileNotFoundException
     */
    public static void zipSingleFile(ZipOutputStream zos, File inputFile, String path) throws IOException {
        try {
            InputStream in = new FileInputStream(inputFile);
            zos.putNextEntry(new ZipEntry(path));
            copy(in, zos);
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * 如果是单个文件，那么就直接进行压缩。如果是文件夹，那么递归压缩所有文件夹里的文件
     *
     * @param zos       压缩输出流
     * @param inputFile 需要压缩的文件
     * @param path      需要压缩的文件在压缩包里的路径
     */
    public static void zipFile(ZipOutputStream zos, File inputFile, String path) {
        if (inputFile.isDirectory()) {
            // 记录压缩包中文件的全路径
            String fp;
            File[] fileList = inputFile.listFiles();
            for (File file : fileList) {
                // 如果路径为空，说明是根目录
                if (path == null || path.isEmpty()) {
                   fp = file.getName();
                } else {
                    fp = path + "/" + file.getName();
                }
                // 如果是目录递归调用，直到遇到文件为止
                zipFile(zos, file, fp);
            }
        } else {
            try {
                zipSingleFile(zos, inputFile, path);
            } catch (IOException e) {
                log.warn("ERROR WHEN zipping single file " + inputFile + " to path " + path);
            }
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
        OutputStream output = null;
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

                log.debug("filename = " + fileName);
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