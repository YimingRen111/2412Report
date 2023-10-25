package lab10_group4_assignment2;

import java.util.Date;

public class DigitalScroll {
    /**
     * 卷轴名
     */
    private String name;
    /**
     * 卷轴ID
     */
    private String id;
    /**
     * 卷轴内容
     */
    private String content;
    /**
     * 上传者ID
     */
    private String userID;

    /**
     * 上传者名字
     */
    private String uploaderName;
    /**
     * 上传时间
     */
    private String uploadtime;
    /**
     * 状态 0：正常 1：删除
     */
    private String status;
    /**
     * 修改时间
     */
    private String modifytime;
    /**
     * 版本号
     */
    private int version;
    /**
     * 下载次数
     */
    private int download;

    public String getUploaderName() {
        return uploaderName;
    }

    public void setUploaderName(String uploaderName) {
        this.uploaderName = uploaderName;
    }

    public DigitalScroll(String name, String id, String content, String userID) {
        this.name = name;
        this.id = id;
        this.content = content;
        this.userID = userID;
        this.status = "0";
        this.version = 0;
        this.download = 0;
    }

    public DigitalScroll() {
    }


    public int getDownload() {
        return download;
    }

    public void setDownload(int download) {
        this.download = download;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAllInfo() {
        StringBuilder sb = new StringBuilder();

        sb.append(getName()).append(",").append(getId()).append(",").append(getContent()).append(",").append(getUserID());

        return sb.toString();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUploadtime() {
        return uploadtime;
    }

    public void setUploadtime(String uploadtime) {
        this.uploadtime = uploadtime;
    }

    public String getModifytime() {
        return modifytime;
    }

    public void setModifytime(String modifytime) {
        this.modifytime = modifytime;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserID(){return userID;}

}
