
package app.lib.common.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by chenhao on 15/11/17.
 */
public class MD5Util {

    public static String getMd5Digest(final String pInput) {
        try {
            final MessageDigest lDigest = MessageDigest.getInstance("MD5");
            lDigest.update(getBytes(pInput));
            final BigInteger lHashInt = new BigInteger(1, lDigest.digest());
            return String.format("%1$032X", lHashInt);
        } catch (final NoSuchAlgorithmException lException) {
            throw new RuntimeException(lException);
        }
    }

    public static byte[] getBytes(final String s) {
        try {
            return s.getBytes("UTF-8");
        } catch (final UnsupportedEncodingException e) {
            return s.getBytes();
        }
    }

    public static String getFileMD5(File file) {
        try {
            InputStream fis = new FileInputStream(file);

            MessageDigest digest = MessageDigest.getInstance("MD5");

            byte[] buffer = new byte[4096];
            for (int n = 0; (n = fis.read(buffer)) > 0;) {
                digest.update(buffer, 0, n);
            }

            fis.close();

            BigInteger bigInt = new BigInteger(1, digest.digest());
            String output = bigInt.toString(16);
            return String.format("%32s", output).replace(' ', '0');
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return "";
    }
}
