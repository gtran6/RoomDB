package com.example.week3day2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {

    private Context context;
    private List<RoomUsers> userdata;
    private RoomAdapterListener roomAdapterListener;

    public RoomAdapter(Context context, RoomAdapterListener listener) {
        this.context = context;
        userdata = new ArrayList<RoomUsers>();
        roomAdapterListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_row_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        RoomUsers roomUsers = userdata.get(position);
        holder.name.setText(roomUsers.getUserName());
        holder.password.setText(roomUsers.getUserPassword());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roomAdapterListener.OnDelete(roomUsers.getId(), position);
            }
        });

        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roomAdapterListener.OnUpdate(roomUsers.getId(), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userdata.size();
    }

    public void addDetailsInList(RoomUsers roomUsers) {
        userdata.add(roomUsers);
        notifyDataSetChanged();
    }

    public void removeFromList(int position) {
        userdata.remove(position);
        notifyDataSetChanged();
    }

    public void updateFromList(int position, RoomUsers roomUsers) {
        userdata.set(position, roomUsers);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, password;
        ImageView update, delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.namefetched);
            password = (TextView) itemView.findViewById(R.id.passwordfetched);
            update = (ImageView) itemView.findViewById(R.id.edit);
            delete = (ImageView) itemView.findViewById(R.id.delete);
        }
    }
}
