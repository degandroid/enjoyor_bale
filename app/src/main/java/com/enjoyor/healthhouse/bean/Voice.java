package com.enjoyor.healthhouse.bean;

/**
 * Created by YuanYuan on 2016/5/23.
 */
public class Voice {

    /**
     * basePath : /files/unknow
     * fileName : 6a711335-d6ae-45f0-bba7-c81880fad19f.AMR
     * fileSize : 4966
     * id : 194
     * oldName : voice.amr
     */

    private String basePath;
    private String fileName;
    private int fileSize;
    private int id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }
}
