package com.chromicle.amritaResource.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Uploads")
public class UploadDocumentModel {
    private String title, sem, branch, subject, url, userName;
    @PrimaryKey private long timestamp;

    @Ignore
    public UploadDocumentModel() {}

    public UploadDocumentModel(
            String title,
            String sem,
            String branch,
            String subject,
            String url,
            String userName,
            long timestamp) {
        this.title = title;
        this.sem = sem;
        this.branch = branch;
        this.subject = subject;
        this.url = url;
        this.userName = userName;
        this.timestamp = timestamp;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSem() {
        return sem;
    }

    public void setSem(String sem) {
        this.sem = sem;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
