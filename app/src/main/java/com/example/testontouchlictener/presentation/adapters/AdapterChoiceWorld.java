package com.example.testontouchlictener.presentation.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testontouchlictener.App;
import com.example.testontouchlictener.R;
import com.example.testontouchlictener.data.SharedGameSetting;
import com.example.testontouchlictener.data.Worlds;
import com.example.testontouchlictener.data.room.dao.WorldDao;

import java.util.List;

public class AdapterChoiceWorld extends RecyclerView.Adapter<AdapterChoiceWorld.ViewHolder> {

    private final List<Worlds> worlds = Worlds.getWorlds();
    private final Context mContext;

    public AdapterChoiceWorld(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_choice_world, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Worlds world = worlds.get(position);
        WorldDao dao = App.getInstance().getDatabase().worldDao();

        // todo переделать на переменную в базе данных
        if (SharedGameSetting.getPrefMaxWorld(mContext) < world.getIdWorld()) {
            holder.blockLock.setVisibility(View.VISIBLE);
        } else {
            holder.blockLock.setVisibility(View.GONE);
        }

        holder.worldName.setText(world.getNameWorld());
        holder.bgImageWorld.setBackgroundResource(world.getBgImage());
        holder.currentProgress.setText("Прогесс мира: " + dao.getWorldById(world.getIdWorld()).levelInWorld + " / " + world.getMaxLevelInWorld());
    }

    @Override
    public int getItemCount() {
        return worlds.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final RelativeLayout blockLock;
        private final TextView worldName;
        private final TextView currentProgress;
        private final ImageView bgImageWorld;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            blockLock = itemView.findViewById(R.id.block_lock);
            worldName = itemView.findViewById(R.id.text_world_name);
            currentProgress = itemView.findViewById(R.id.text_current_progress);
            bgImageWorld = itemView.findViewById(R.id.bg_img_world);

        }
    }

}