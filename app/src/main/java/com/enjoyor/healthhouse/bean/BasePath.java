package com.enjoyor.healthhouse.bean;

/**
 * Created by YuanYuan on 2016/6/4.
 */
public class BasePath {

    /**
     * basePath : /images/headimg
     * fileName : e80f0047-e822-4c10-80c6-5c59585467b1.JPG
     * fileSize : 980190
     * oldName : beijing1.jpg
     */

    private String basePath;
    private String fileName;
    private int fileSize;
    private String oldName;

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }
}
