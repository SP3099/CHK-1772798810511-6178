package com.smartai.parentalcontrol.models;

public class UsageLog {
    private String id;
    private String userId;
    private String appName;
    private String packageName;
    private long usageTimeMillis;
    private long date; // Timestamp for the day
    private long timestamp; // When the log was created

    public UsageLog() {
    }

    public UsageLog(String id, String userId, String appName, String packageName, long usageTimeMillis, long date) {
        this.id = id;
        this.userId = userId;
        this.appName = appName;
        this.packageName = packageName;
        this.usageTimeMillis = usageTimeMillis;
        this.date = date;
        this.timestamp = System.currentTimeMillis();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public long getUsageTimeMillis() {
        return usageTimeMillis;
    }

    public void setUsageTimeMillis(long usageTimeMillis) {
        this.usageTimeMillis = usageTimeMillis;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getUsageMinutes() {
        return usageTimeMillis / (1000 * 60);
    }

    public String getFormattedUsageTime() {
        long minutes = getUsageMinutes();
        long hours = minutes / 60;
        minutes = minutes % 60;
        
        if (hours > 0) {
            return hours + "h " + minutes + "m";
        } else {
            return minutes + "m";
        }
    }
}
