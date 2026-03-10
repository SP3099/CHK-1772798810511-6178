package com.smartai.parentalcontrol;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.ViewHolder> {

    private List<RequestsActivity.TimeRequest> requests = new ArrayList<>();
    private OnRequestActionListener listener;

    public interface OnRequestActionListener {
        void onApprove(RequestsActivity.TimeRequest request);
        void onDeny(RequestsActivity.TimeRequest request);
    }

    public RequestsAdapter(OnRequestActionListener listener) {
        this.listener = listener;
    }

    public void setRequests(List<RequestsActivity.TimeRequest> requests) {
        this.requests = requests;
        notifyDataSetChanged();
    }

    public void removeRequest(RequestsActivity.TimeRequest request) {
        int position = requests.indexOf(request);
        if (position != -1) {
            requests.remove(position);
            notifyItemRemoved(position);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_request, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RequestsActivity.TimeRequest request = requests.get(position);
        
        holder.studentNameTextView.setText(request.getStudentName());
        holder.reasonTextView.setText(request.getReason());
        holder.timestampTextView.setText(request.getTimestamp());
        
        String timeText = "+" + request.getExtraMinutes() + " min";
        holder.timeRequestedTextView.setText(timeText);

        holder.approveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onApprove(request);
                }
            }
        });

        holder.denyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onDeny(request);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView studentNameTextView;
        TextView reasonTextView;
        TextView timestampTextView;
        TextView timeRequestedTextView;
        Button approveButton;
        Button denyButton;

        ViewHolder(View itemView) {
            super(itemView);
            studentNameTextView = itemView.findViewById(R.id.studentNameTextView);
            reasonTextView = itemView.findViewById(R.id.reasonTextView);
            timestampTextView = itemView.findViewById(R.id.timestampTextView);
            timeRequestedTextView = itemView.findViewById(R.id.timeRequestedTextView);
            approveButton = itemView.findViewById(R.id.approveButton);
            denyButton = itemView.findViewById(R.id.denyButton);
        }
    }
}
