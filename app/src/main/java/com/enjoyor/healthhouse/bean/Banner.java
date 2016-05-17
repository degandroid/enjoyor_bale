package com.enjoyor.healthhouse.bean;

/**
 * Created by Administrator on 2016/5/12.
 */
public class Banner {
    private Long id;//数据标示 （用不到）
    private String name;//名称
    private Long fileId;//文件ID （用不到）
    private Integer sort;//顺序
    private String status;//1:显示 2：隐藏
    private String type;//1：app启动页 2：首页banner（用不到）
    //    private Date createtime; （用不到）
    private String action;//图片点击链接
    private String filePath;//文件路径
    private Integer fileType;//文件类型，0未知1图片2文档3视频4音频5压缩文件

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

}
