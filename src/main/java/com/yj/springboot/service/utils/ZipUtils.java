package com.yj.springboot.service.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

    /**
     * 根据输入的文件与输出流对文件进行打包
     */
    public static void zipFile(String fileName, InputStream inputStream, ZipOutputStream outputStream) {
        try {
            BufferedInputStream bins = new BufferedInputStream(inputStream, 1024);
            ZipEntry entry = new ZipEntry(fileName);
            outputStream.putNextEntry(entry);
            // 向压缩文件中输出数据
            int nNumber;
            byte[] buffer = new byte[1024];
            while ((nNumber = bins.read(buffer)) != -1) {
                outputStream.write(buffer, 0, nNumber);
            }
            // 关闭创建的流对象
            bins.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}