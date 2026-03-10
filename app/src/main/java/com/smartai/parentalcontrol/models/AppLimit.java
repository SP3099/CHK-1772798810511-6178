package com.smartai.parentalcontrol.models;

public class AppLimit {
    private String id;
    private String parentId;
    private String studentId;
    private String appName;
    private String packageName;
    private int limitMinutes;
    private boolean isActive;
    private long createdAt;
    private long updatedAt;

    public AppLimit() {
    }

    public AppLimit(String id, String parentId, String studentId, String appName, String packageName, int limitMinutes) {
        this.id = id;
        this.parentId = parentId;
        this.studentId = studentId;
        this.appName = appName;
        this.packageName = packageName;
        this.limitMinutes = limitMinutes;
        this.isActive = true;
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = System.currentTimeMillis();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getLimitMinutes() {
        return limitMinutes;
    }

    public void setLimitMinutes(int limitMinutes) {
        this.limitMinutes = limitMinutes;
        this.updatedAt = System.currentTimeMillis();
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
        this.updatedAt = System.currentTimeMillis();
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getFormattedLimit() {
        if (limitMinutes == 0) {
            return "No limit";
        } else if (limitMinutes < 60) {
            return limitMinutes + " minutes";
        } else {
            int hours = limitMinutes / 60;
            int minutes = limitMinutes % 60;
            if (minutes == 0) {
                return hours + " hour" + (hours > 1 ? "s" : "");
            } else {
                return hours + "h " + minutes + "m";
            }
        }
    }
}
