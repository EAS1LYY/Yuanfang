package com.gujiunet.code.util;

import java.io.File;

import java.util.HashMap;

public class Files {


    static HashMap<String,String> fileTypeMap = new HashMap();

    static {
        fileTypeMap.put(".3gp", "video/3gpp");
        fileTypeMap.put(".apk", "application/vnd.android.package-archive");
        fileTypeMap.put(".asf", "video/x-ms-asf");
        fileTypeMap.put(".flv", "video/x-flv");
        fileTypeMap.put(".rar", "application/x-rar");
        fileTypeMap.put(".avi", "video/x-msvideo");
        fileTypeMap.put(".ico", "image/x-ico");
        fileTypeMap.put(".bin", "application/octet-stream");
        fileTypeMap.put(".bmp", "image/bmp");
        fileTypeMap.put(".c", "text/plain");
        fileTypeMap.put(".class", "application/octet-stream");
        fileTypeMap.put(".conf", "text/plain");
        fileTypeMap.put(".cpp", "text/plain");
        fileTypeMap.put(".doc", "application/msword");
        fileTypeMap.put(".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        fileTypeMap.put(".xls", "application/vnd.ms-excel");
        fileTypeMap.put(".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        fileTypeMap.put(".exe", "application/octet-stream");
        fileTypeMap.put(".gif", "image/gif");
        fileTypeMap.put(".gtar", "application/x-gtar");
        fileTypeMap.put(".gz", "application/x-gzip");
        fileTypeMap.put(".h", "text/plain");
        fileTypeMap.put(".htm", "text/html");
        fileTypeMap.put(".html", "text/html");
        fileTypeMap.put(".jar", "application/java-archive");
        fileTypeMap.put(".java", "text/plain");
        fileTypeMap.put(".jpeg", "image/jpeg");
        fileTypeMap.put(".jpg", "image/jpeg");
        fileTypeMap.put(".js", "application/x-javascript");
        fileTypeMap.put(".log", "text/plain");
        fileTypeMap.put(".m3u", "audio/x-mpegurl");
        fileTypeMap.put(".m4a", "audio/mp4a-latm");
        fileTypeMap.put(".m4b", "audio/mp4a-latm");
        fileTypeMap.put(".m4p", "audio/mp4a-latm");
        fileTypeMap.put(".m4u", "video/vnd.mpegurl");
        fileTypeMap.put(".m4v", "video/x-m4v");
        fileTypeMap.put(".mov", "video/quicktime");
        fileTypeMap.put(".mp2", "audio/x-mpeg");
        fileTypeMap.put(".mp3", "audio/x-mpeg");
        fileTypeMap.put(".mp4", "video/mp4");
        fileTypeMap.put(".mpc", "application/vnd.mpohun.certificate");
        fileTypeMap.put(".mpe", "video/mpeg");
        fileTypeMap.put(".mpeg", "video/mpeg");
        fileTypeMap.put(".mpg", "video/mpeg");
        fileTypeMap.put(".mpg4", "video/mp4");
        fileTypeMap.put(".mpga", "audio/mpeg");
        fileTypeMap.put(".msg", "application/vnd.ms-outlook");
        fileTypeMap.put(".ogg", "audio/ogg");
        fileTypeMap.put(".pdf", "application/pdf");
        fileTypeMap.put(".png", "image/png");
        fileTypeMap.put(".pps", "application/vnd.ms-powerpoint");
        fileTypeMap.put(".ppt", "application/vnd.ms-powerpoint");
        fileTypeMap.put(".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
        fileTypeMap.put(".prop", "text/plain");
        fileTypeMap.put(".rc", "text/plain");
        fileTypeMap.put(".rmvb", "audio/x-pn-realaudio");
        fileTypeMap.put(".rtf", "application/rtf");
        fileTypeMap.put(".sh", "text/plain");
        fileTypeMap.put(".tar", "application/x-tar");
        fileTypeMap.put(".tgz", "application/x-compressed");
        fileTypeMap.put(".txt", "text/plain");
        fileTypeMap.put(".wav", "audio/x-wav");
        fileTypeMap.put(".wma", "audio/x-ms-wma");
        fileTypeMap.put(".wmv", "audio/x-ms-wmv");
        fileTypeMap.put(".wps", "application/vnd.ms-works");
        fileTypeMap.put(".xml", "text/plain");
        fileTypeMap.put(".z", "application/x-compress");
        fileTypeMap.put(".zip", "application/x-zip-compressed");
        fileTypeMap.put("", "*/*");
    }

    public static String getFileType(String fileName) {
        String fileNameType = fileName.substring(fileName.indexOf("."), fileName.length());
        if (fileTypeMap.containsKey(fileNameType)) {
            return fileTypeMap.get(fileNameType);
        } else {
            return "*/*";
        }
    }


    private File file;

    public Files(String path) {

    }

    public String getPath() {
        return file.getPath();
    }
}