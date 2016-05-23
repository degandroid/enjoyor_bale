package com.enjoyor.healthhouse.bean;

/**
 * Created by YuanYuan on 2016/5/23.
 */
public class PhotoId {

    /**
     * basePath : /files/unknow
     * fileName : 7f9345e6-3021-47c8-9289-a5e3d8f9973e.JPG
     * fileSize : 1326537
     * id : 242
     * oldName : 5625429353.jpg
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
