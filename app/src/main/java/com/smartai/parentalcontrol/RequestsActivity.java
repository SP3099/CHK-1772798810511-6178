package com.smartai.parentalcontrol;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class RequestsActivity extends AppCompatActivity {

    private RecyclerView requestsRecyclerView;
    private TextView emptyTextView;
    private RequestsAdapter requestsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);

        requestsRecyclerView = findViewById(R.id.requestsRecyclerView);
        emptyTextView = findViewById(R.id.emptyTextView);

        setupRecyclerView();
        loadRequests();
    }

    private void setupRecyclerView() {
        requestsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        requestsAdapter = new RequestsAdapter(new RequestsAdapter.OnRequestActionListener() {
            @Override
            public void onApprove(TimeRequest request) {
                handleApprove(request);
            }

            @Override
            public void onDeny(TimeRequest request) {
                handleDeny(request);
            }
        });
        requestsRecyclerView.setAdapter(requestsAdapter);
    }

    private void loadRequests() {
        // TODO: Fetch actual requests from database
        List<TimeRequest> requests = new ArrayList<>();
        requests.add(new TimeRequest("1", "John Doe", "Need to finish homework assignment", 30, "2 hours ago"));
        requests.add(new TimeRequest("2", "Jane Smith", "Working on school project", 60, "1 hour ago"));
        requests.add(new TimeRequest("3", "Mike Johnson", "Video call with study group", 45, "30 minutes ago"));

        requestsAdapter.setRequests(requests);
        updateEmptyState(requests.isEmpty());
    }

    private void handleApprove(TimeRequest request) {
        // TODO: Implement approval logic
        requestsAdapter.removeRequest(request);
        updateEmptyState(requestsAdapter.getItemCount() == 0);
    }

    private void handleDeny(TimeRequest request) {
        // TODO: Implement denial logic
        requestsAdapter.removeRequest(request);
        updateEmptyState(requestsAdapter.getItemCount() == 0);
    }

    private void updateEmptyState(boolean isEmpty) {
        if (isEmpty) {
            requestsRecyclerView.setVisibility(View.GONE);
            emptyTextView.setVisibility(View.VISIBLE);
        } else {
            requestsRecyclerView.setVisibility(View.VISIBLE);
            emptyTextView.setVisibility(View.GONE);
        }
    }

    public static class TimeRequest {
        private String id;
        private String studentName;
        private String reason;
        private int extraMinutes;
        private String timestamp;

        public TimeRequest(String id, String studentName, String reason, int extraMinutes, String timestamp) {
            this.id = id;
            this.studentName = studentName;
            this.reason = reason;
            this.extraMinutes = extraMinutes;
            this.timestamp = timestamp;
        }

        public String getId() {
            return id;
        }

        public String getStudentName() {
            return studentName;
        }

        public String getReason() {
            return reason;
        }

        public int getExtraMinutes() {
            return extraMinutes;
        }

        public String getTimestamp() {
            return timestamp;
        }
    }
}
