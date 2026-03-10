package com.smartai.parentalcontrol.models;

public class Request {
    private String id;
    private String studentId;
    private String studentName;
    private String parentId;
    private String reason;
    private int extraMinutes;
    private String status; // "pending", "approved", "denied"
    private long createdAt;
    private long respondedAt;

    public Request() {
    }

    public Request(String id, String studentId, String studentName, String parentId, String reason, int extraMinutes) {
        this.id = id;
        this.studentId = studentId;
        this.studentName = studentName;
        this.parentId = parentId;
        this.reason = reason;
        this.extraMinutes = extraMinutes;
        this.status = "pending";
        this.createdAt = System.currentTimeMillis();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getExtraMinutes() {
        return extraMinutes;
    }

    public void setExtraMinutes(int extraMinutes) {
        this.extraMinutes = extraMinutes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        if (!"pending".equals(status)) {
            this.respondedAt = System.currentTimeMillis();
        }
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getRespondedAt() {
        return respondedAt;
    }

    public void setRespondedAt(long respondedAt) {
        this.respondedAt = respondedAt;
    }

    public boolean isPending() {
        return "pending".equals(status);
    }

    public boolean isApproved() {
        return "approved".equals(status);
    }

    public boolean isDenied() {
        return "denied".equals(status);
    }

    public String getFormattedTime() {
        if (extraMinutes < 60) {
            return extraMinutes + " minutes";
        } else {
            int hours = extraMinutes / 60;
            int minutes = extraMinutes % 60;
            if (minutes == 0) {
                return hours + " hour" + (hours > 1 ? "s" : "");
            } else {
                return hours + "h " + minutes + "m";
            }
        }
    }

    public String getTimestamp() {
        long diff = System.currentTimeMillis() - createdAt;
        long minutes = diff / (1000 * 60);
        long hours = minutes / 60;
        long days = hours / 24;

        if (days > 0) {
            return days + " day" + (days > 1 ? "s" : "") + " ago";
        } else if (hours > 0) {
            return hours + " hour" + (hours > 1 ? "s" : "") + " ago";
        } else if (minutes > 0) {
            return minutes + " minute" + (minutes > 1 ? "s" : "") + " ago";
        } else {
            return "Just now";
        }
    }
}
