package com.smartai.parentalcontrol.network;

import com.smartai.parentalcontrol.models.AppLimit;
import com.smartai.parentalcontrol.models.Request;
import com.smartai.parentalcontrol.models.UsageLog;
import com.smartai.parentalcontrol.models.User;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    // User endpoints
    @POST("auth/register")
    Call<ApiResponse<User>> register(@Body User user);

    @POST("auth/login")
    Call<ApiResponse<User>> login(@Body LoginRequest loginRequest);

    @GET("users/{userId}")
    Call<ApiResponse<User>> getUser(@Path("userId") String userId);

    @PUT("users/{userId}")
    Call<ApiResponse<User>> updateUser(@Path("userId") String userId, @Body User user);

    // App Limit endpoints
    @POST("limits")
    Call<ApiResponse<AppLimit>> createLimit(@Body AppLimit appLimit);

    @GET("limits/parent/{parentId}")
    Call<ApiResponse<List<AppLimit>>> getLimitsByParent(@Path("parentId") String parentId);

    @GET("limits/student/{studentId}")
    Call<ApiResponse<List<AppLimit>>> getLimitsByStudent(@Path("studentId") String studentId);

    @PUT("limits/{limitId}")
    Call<ApiResponse<AppLimit>> updateLimit(@Path("limitId") String limitId, @Body AppLimit appLimit);

    @DELETE("limits/{limitId}")
    Call<ApiResponse<Void>> deleteLimit(@Path("limitId") String limitId);

    // Usage Log endpoints
    @POST("usage")
    Call<ApiResponse<UsageLog>> logUsage(@Body UsageLog usageLog);

    @GET("usage/student/{studentId}")
    Call<ApiResponse<List<UsageLog>>> getUsageByStudent(@Path("studentId") String studentId);

    @GET("usage/student/{studentId}/date")
    Call<ApiResponse<List<UsageLog>>> getUsageByDate(
            @Path("studentId") String studentId,
            @Query("date") long date
    );

    // Request endpoints
    @POST("requests")
    Call<ApiResponse<Request>> createRequest(@Body Request request);

    @GET("requests/parent/{parentId}")
    Call<ApiResponse<List<Request>>> getRequestsByParent(@Path("parentId") String parentId);

    @GET("requests/student/{studentId}")
    Call<ApiResponse<List<Request>>> getRequestsByStudent(@Path("studentId") String studentId);

    @PUT("requests/{requestId}/approve")
    Call<ApiResponse<Request>> approveRequest(@Path("requestId") String requestId);

    @PUT("requests/{requestId}/deny")
    Call<ApiResponse<Request>> denyRequest(@Path("requestId") String requestId);

    // Device Lock endpoints
    @POST("device/lock")
    Call<ApiResponse<Void>> lockDevice(@Body DeviceLockRequest lockRequest);

    @POST("device/unlock")
    Call<ApiResponse<Void>> unlockDevice(@Body DeviceLockRequest lockRequest);

    @GET("device/status/{studentId}")
    Call<ApiResponse<DeviceLockStatus>> getDeviceLockStatus(@Path("studentId") String studentId);

    // Helper classes
    class LoginRequest {
        private String email;
        private String password;

        public LoginRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }
    }

    class DeviceLockRequest {
        private String studentId;
        private String parentId;

        public DeviceLockRequest(String studentId, String parentId) {
            this.studentId = studentId;
            this.parentId = parentId;
        }

        public String getStudentId() {
            return studentId;
        }

        public String getParentId() {
            return parentId;
        }
    }

    class DeviceLockStatus {
        private boolean isLocked;
        private String lockedBy;
        private long lockedAt;

        public boolean isLocked() {
            return isLocked;
        }

        public String getLockedBy() {
            return lockedBy;
        }

        public long getLockedAt() {
            return lockedAt;
        }
    }
}
