package pr.iceworld.fernando.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class Md5Util {

    protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    protected static MessageDigest messagedigest = null;
    static {
        try {
            messagedigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException nsaex) {
            log.error("MessageDigest not support MD5");
            nsaex.printStackTrace();
        }
    }

    /**
     * 生成字符串的md5校验值
     * @param s
     * @return
     */
    public static String getMd5Str(String s) {
        return getMd5Str(s.getBytes());
    }

    /**
     * 判断字符串的md5校验码是否与一个已知的md5码相匹配
     *
     * @param password 要校验的字符串
     * @param md5PwdStr 已知的md5校验码
     * @return
     */
    public static boolean checkPassword(String password, String md5PwdStr) {
        String s = getMd5Str(password);
        return s.equals(md5PwdStr);
    }

    /**
     * 生成文件的md5校验值
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static String getFileMd5Str(File file) throws IOException {
        try(InputStream fis = new FileInputStream(file);) {
            byte[] buffer = new byte[1024];
            int numRead = 0;
            while ((numRead = fis.read(buffer)) > 0) {
                messagedigest.update(buffer, 0, numRead);
            }
        } catch (IOException ioe) {
            log.error("file - " + file + " md5 caculates error.");
            throw ioe;
        }

        return bufferToHex(messagedigest.digest());
    }

    /**
     * 生成字节 的 md5 校验
     * @param bytes
     * @return
     */
    public static String getMd5Str(byte[] bytes) {
        messagedigest.update(bytes);
        return bufferToHex(messagedigest.digest());
    }

    private static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    /**
     * 取字节中高 4 位的数字转换, >>> 为逻辑右移，将符号位一起右移,此处未发现两种符号有何不同
     * 取字节中低 4 位的数字转换
     * @param bt
     * @param stringbuffer
     */
    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        // 取字节中高 4 位的数字转换, >>> 为逻辑右移，将符号位一起右移,此处未发现两种符号有何不同
        char c0 = hexDigits[(bt & 0xf0) >> 4];
        // 取字节中低 4 位的数字转换
        char c1 = hexDigits[bt & 0xf];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }

    public static void main(String[] args) throws IOException {
        long begin = System.currentTimeMillis();

        File file = new File(args[0]);
        String md5 = getFileMd5Str(file);
        long end = System.currentTimeMillis();
        System.out.println("MD5: "+ md5);
        System.out.println("time:" + ((end - begin) / 1000) + "s");
    }

}
