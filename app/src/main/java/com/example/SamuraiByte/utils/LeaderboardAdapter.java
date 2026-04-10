package com.example.SamuraiByte.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.SamuraiByte.R;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.ViewHolder> {

    private NameAndScore[] leaderboardValues;

    public LeaderboardAdapter(NameAndScore[] leaderboardValues) {
        this.leaderboardValues = leaderboardValues;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_leaderboard, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NameAndScore entry = leaderboardValues[position];
        holder.tvRank.setText(String.valueOf(position + 1));
        holder.tvName.setText(entry.getName());

        double score = entry.getScore();
        int decimals;
        if (score > 10000) decimals = 0;
        else if (score > 1000) decimals = 1;
        else if (score > 100) decimals = 2;
        else if (score > 10) decimals = 3;
        else decimals = 4;

        String format = "%." + decimals + "f s";
        holder.tvScore.setText(String.format(format, score));
    }

    @Override
    public int getItemCount() {
        return leaderboardValues != null ? leaderboardValues.length : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRank, tvName, tvScore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRank  = itemView.findViewById(R.id.tv_rank);
            tvName  = itemView.findViewById(R.id.tv_name);
            tvScore = itemView.findViewById(R.id.tv_score);
        }
    }
}