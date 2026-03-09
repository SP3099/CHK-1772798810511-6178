package com.smartai.parentalcontrol;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class AppUsageAdapter extends RecyclerView.Adapter<AppUsageAdapter.ViewHolder> {

    private List<StudentDashboardActivity.AppUsage> appUsageList = new ArrayList<>();

    public void setAppUsageList(List<StudentDashboardActivity.AppUsage> appUsageList) {
        this.appUsageList = appUsageList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_app_usage, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StudentDashboardActivity.AppUsage appUsage = appUsageList.get(position);
        holder.appNameTextView.setText(appUsage.getAppName());
        holder.usageTimeTextView.setText(appUsage.getUsageTime());
        
        if (appUsage.getAppIcon() != null) {
            holder.appIconImageView.setImageDrawable(appUsage.getAppIcon());
        } else {
            holder.appIconImageView.setImageResource(android.R.drawable.sym_def_app_icon);
        }
    }

    @Override
    public int getItemCount() {
        return appUsageList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView appIconImageView;
        TextView appNameTextView;
        TextView usageTimeTextView;

        ViewHolder(View itemView) {
            super(itemView);
            appIconImageView = itemView.findViewById(R.id.appIconImageView);
            appNameTextView = itemView.findViewById(R.id.appNameTextView);
            usageTimeTextView = itemView.findViewById(R.id.usageTimeTextView);
        }
    }
}
