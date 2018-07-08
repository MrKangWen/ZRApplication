package com.zhaorou.zrapplication.utils;

import java.util.Locale;

public class BinaryConversionUtil {

    /**
     * 二进制数组转十六进制字符串
     *
     * @param byteArray
     * @return
     */
    public static String byteArrayToHexString(byte[] byteArray) {
        StringBuilder builder = new StringBuilder(byteArray.length * 2);
        for (byte element : byteArray) {
            int value = element & 0xff;
            if (value < 16) {
                builder.append('0');
            }
            builder.append(Integer.toHexString(value));
        }
        return builder.toString().toUpperCase(Locale.US);
    }

    /**
     * 十六进制字符串转二进制数组
     *
     * @param hexString
     * @return
     */
    public static byte[] hexStringToByteArray(String hexString) {
        int length = hexString.length();
        byte[] bytes = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            bytes[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }
        return bytes;
    }
}
