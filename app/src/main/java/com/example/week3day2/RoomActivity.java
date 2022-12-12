package com.example.week3day2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class RoomActivity extends AppCompatActivity implements RoomAdapterListener {
    /*EditText username, userpassword;
    Button usersubmit;*/

    private RoomDatabaseUsers roomDatabaseUsers;
    private RoomDAO roomDAO;

    RecyclerView recyclerView;

    RoomAdapter roomAdapter;

    FloatingActionButton floatingActionButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        /*username = (EditText) findViewById(R.id.username);
        userpassword = (EditText) findViewById(R.id.userpassword);
        usersubmit = (Button) findViewById(R.id.userbutton);*/

        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingactionbutton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(RoomActivity.this);
                dialog.setContentView(R.layout.fabview);
                EditText username, userpassword;
                Button usersubmit;
                username = dialog.findViewById(R.id.username);
                userpassword = dialog.findViewById(R.id.userpassword);
                usersubmit = dialog.findViewById(R.id.userbutton);

                usersubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RoomUsers roomUsers = new RoomUsers(0, username.getText().toString(),
                                userpassword.getText().toString());
                        roomDAO.insert(roomUsers);

                        Toast.makeText(getApplicationContext(), "Data inserted", Toast.LENGTH_SHORT).show();
                        roomAdapter.addDetailsInList(roomUsers);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        roomDatabaseUsers = RoomDatabaseUsers.getInstance(this);
        roomDAO = roomDatabaseUsers.getDAO();

        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        roomAdapter = new RoomAdapter(this, this);

        recyclerView.setAdapter(roomAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fetchAll();

        /*usersubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RoomUsers roomUsers = new RoomUsers(0, username.getText().toString(),
                        userpassword.getText().toString());
                roomDAO.insert(roomUsers);

                Toast.makeText(getApplicationContext(), "Data inserted", Toast.LENGTH_SHORT).show();

                roomAdapter.addDetailsInList(roomUsers);
            }
        });*/
    }

    private void fetchAll() {
        List<RoomUsers> wholeData = roomDAO.getAllUsers();

        for (int i = 0; i < wholeData.size(); i++) {
            RoomUsers roomUsers = wholeData.get(i);
            roomAdapter.addDetailsInList(roomUsers);
        }
    }

    @Override
    public void OnUpdate(int id, int position) {
        List<RoomUsers> wholeData = roomDAO.getAllUsers();
        RoomUsers roomUsers = wholeData.get(position);
        Dialog dialog = new Dialog(RoomActivity.this);
        dialog.setContentView(R.layout.fabview);

        EditText username, userpassword;
        Button usersubmit;
        username = dialog.findViewById(R.id.username);
        userpassword = dialog.findViewById(R.id.userpassword);
        usersubmit = dialog.findViewById(R.id.userbutton);

        usersubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roomUsers.setUserName(username.getText().toString());
                roomUsers.setUserPassword(userpassword.getText().toString());
                RoomUsers roomUsers = new RoomUsers(id, username.getText().toString(),
                        userpassword.getText().toString());
                roomDAO.update(roomUsers);
                Toast.makeText(getApplicationContext(), "Data updated", Toast.LENGTH_SHORT).show();
                //roomAdapter.addDetailsInList(roomUsers);
                roomAdapter.updateFromList(position, roomUsers);
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    @Override
    public void OnDelete(int id, int position) {
        roomDAO.delete(id);
        roomAdapter.removeFromList(position);
    }
}