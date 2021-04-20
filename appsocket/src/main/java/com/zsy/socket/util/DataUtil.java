package com.zsy.socket.util;

/**
 * @Title com.zsy.socket.util
 * @date 2021/4/16
 * @autor Zsy
 */

public class DataUtil {

    public static byte[] getBytes() {
        byte[] bytes = new byte[938];

        bytes[0] = 0x77;          //报头
        bytes[1] = (byte) 0xcc;   //报头

        bytes[2] = 0x00;
        bytes[3] = 0x00;
        bytes[4] = 0x00;
        bytes[5] = 0x01;

        bytes[6] = 0x00;   //报计数
        bytes[7] = 0x00;
        bytes[8] = 0x01;

        bytes[9] = 0x09;  //电池电量


        bytes[10] = (byte) 0xff;   //导联
        bytes[11] = (byte) 0xff;   //导联
        int index = 12;
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 66; j++) {
                if (j > 2) {
                    bytes[index] = 0x00;
                } else {
                    bytes[index] = 0x01;
                }
                index++;
            }
        }
        bytes[936] = (byte) 0xff;
        bytes[937] = (byte) 0xa3;

        return bytes;
    }

}
