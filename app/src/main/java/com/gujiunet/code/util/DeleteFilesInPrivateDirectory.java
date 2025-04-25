package com.gujiunet.code.util;

import android.annotation.SuppressLint;

import java.io.File;
import java.util.Objects;

public class DeleteFilesInPrivateDirectory {
    public static void main(String[] args) {
        @SuppressLint("SdCardPath") String privateDirPath = "/data/user/0/com.gujiunet.code/files"; // 这里需要将"包名"替换为真正的应用程序包名

        File directory = new File(privateDirPath);
        if (directory.exists() && directory.isDirectory()) {
            deleteAllFiles(directory);
        } else {
            System.out.println("指定路径不存在或者不是一个目录");
        }
    }

    private static void deleteAllFiles(File fileOrDirectory) {
        if (fileOrDirectory != null && fileOrDirectory.exists()) {
            if (fileOrDirectory.isDirectory()) {
                for (File child : Objects.requireNonNull(fileOrDirectory.listFiles())) {
                    deleteAllFiles(child);
                }
            }

            boolean isDeleted = fileOrDirectory.delete();
            if (!isDeleted) {
                System.out.println("无法删除文件 " + fileOrDirectory.getAbsolutePath());
            }
        }
    }
}