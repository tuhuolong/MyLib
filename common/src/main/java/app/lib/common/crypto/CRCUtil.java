
package app.lib.common.crypto;

import java.util.zip.CRC32;

/**
 * Created by chenhao on 2017/6/27.
 */

public class CRCUtil {
    public static int getCRC32(byte[] bytes) {
        CRC32 crc32 = new CRC32();
        crc32.update(bytes);
        crc32.getValue();

        return (int) (crc32.getValue() & 0x7FFFFFFF);
    }
}
