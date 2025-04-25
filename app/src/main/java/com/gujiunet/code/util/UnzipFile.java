package com.gujiunet.code.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnzipFile {
    public static void unzip(String zipFilePath, String destDirectory) throws IOException {
        // 创建ZipInputStream对象，传入压缩文件路径  
        FileInputStream fis = new FileInputStream(zipFilePath);
        ZipInputStream zis = new ZipInputStream(fis);
        ZipEntry entry = zis.getNextEntry();
        // 循环读取ZipEntry，解压文件  
        while (entry != null) {
            String fileName = entry.getName();
            // 构造解压后的文件路径  
            File newFile = new File(destDirectory + File.separator + fileName);
            // 如果解压的是文件夹，则创建文件夹  
            if (entry.isDirectory()) {
                newFile.mkdirs();
            } else {
                // 如果解压的是文件，则创建父文件夹，并写入文件内容  
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
            }
            // 关闭当前ZipEntry，继续读取下一个ZipEntry  
            zis.closeEntry();
            entry = zis.getNextEntry();
        }
        // 关闭ZipInputStream对象  
        zis.close();
        fis.close();
    }
}