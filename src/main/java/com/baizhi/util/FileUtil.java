package com.baizhi.util;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;

import java.io.File;
import java.text.DecimalFormat;

public class FileUtil {
    //字节转换为mb
    public static String readableFileSize(long size) {
        if (size <= 0) return "0";
        final String[] units = new String[]{"B", "kB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    //获取音频时长
    //获取音频的时间
    public static String getDuration(File source) {
        Encoder encoder = new Encoder();
        long ls = 0;
        MultimediaInfo m;
        String gapTime = null;
        try {
            m = encoder.getInfo(source);
            ls = m.getDuration() / 1000;  //这里将结果转换了秒
            gapTime = getGapTime(ls * 1000);  //上面获取的是秒 要转换为豪秒
        } catch (Exception e) {
            new RuntimeException("音频时间异常");
        }
        return gapTime;
    }

    //音频时间转换
    public static String getGapTime(long time) {
        String min = (time / (1000 * 60)) + "";
        String second = (time % (1000 * 60) / 1000) + "";
        if (min.length() < 2) {
            min = 0 + min;
        }
        if (second.length() < 2) {
            second = 0 + second;
        }
        return min + ":" + second;
    }
}
