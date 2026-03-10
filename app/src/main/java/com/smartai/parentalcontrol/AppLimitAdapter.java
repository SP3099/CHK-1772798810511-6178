package com.smartai.parentalcontrol;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class AppLimitAdapter extends RecyclerView.Adapter<AppLimitAdapter.ViewHolder> {

    private List<LimitManagementActivity.AppLimit> appLimits = new ArrayList<>();
    private OnRemoveLimitListener listener;

    public interface OnRemoveLimitListener {
        void onRemove(LimitManagementActivity.AppLimit appLimit);
    }

    public AppLimitAdapter(OnRemoveLimitListener listener) {
        this.listener = listener;
    }

    public void setAppLimits(List<LimitManagementActivity.AppLimit> appLimits) {
        this.appLimits = appLimits;
        notifyDataSetChanged();
    }

    public void removeLimit(LimitManagementActivity.AppLimit appLimit) {
        int position = appLimits.indexOf(appLimit);
        if (position != -1) {
            appLimits.remove(position);
            notifyItemRemoved(position);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_app_limit, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LimitManagementActivity.AppLimit appLimit = appLimits.get(position);
        
        holder.appNameTextView.setText(appLimit.getAppName());
        
        String limitText = formatTimeLimit(appLimit.getLimitMinutes());
        holder.limitTextView.setText("Limit: " + limitText);
        
        if (appLimit.getAppIcon() != null) {
            holder.appIconImageView.setImageDrawable(appLimit.getAppIcon());
        } else {
            holder.appIconImageView.setImageResource(android.R.drawable.sym_def_app_icon);
        }

        holder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onRemove(appLimit);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return appLimits.size();
    }

    private String formatTimeLimit(int minutes) {
        if (minutes == 0) {
            return "No limit";
        } else if (minutes < 60) {
            return minutes + " minutes";
        } else {
            int hours = minutes / 60;
            int remainingMinutes = minutes % 60;
            if (remainingMinutes == 0) {
                return hours + " hour" + (hours > 1 ? "s" : "");
            } else {
                return hours + "h " + remainingMinutes + "m";
            }
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView appIconImageView;
        TextView appNameTextView;
        TextView limitTextView;
        Button removeButton;

        ViewHolder(View itemView) {
            super(itemView);
            appIconImageView = itemView.findViewById(R.id.appIconImageView);
            appNameTextView = itemView.findViewById(R.id.appNameTextView);
            limitTextView = itemView.findViewById(R.id.limitTextView);
            removeButton = itemView.findViewById(R.id.removeButton);
        }
    }
}
