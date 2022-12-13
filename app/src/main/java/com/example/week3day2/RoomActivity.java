package com.example.week3day2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
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

    AppBarLayout appBarLayout;
    androidx.appcompat.widget.Toolbar toolbar;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appbarlayout);

        /*username = (EditText) findViewById(R.id.username);
        userpassword = (EditText) findViewById(R.id.userpassword);
        usersubmit = (Button) findViewById(R.id.userbutton);*/

        appBarLayout = (AppBarLayout)findViewById(R.id.appbarlayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        roomDatabaseUsers = RoomDatabaseUsers.getInstance(this);
        roomDAO = roomDatabaseUsers.getDAO();

        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        roomAdapter = new RoomAdapter(this, this);

        recyclerView.setAdapter(roomAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fetchAll();

        ViewGroup.MarginLayoutParams marginLayoutParams =
                new ViewGroup.MarginLayoutParams(recyclerView.getLayoutParams());

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == toolbar.getHeight()) {
                    marginLayoutParams.setMargins(0, 50, 0, 0);
                    recyclerView.setLayoutParams(marginLayoutParams);
                }
            }
        });

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


        /*final LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //no items in the RecyclerView
                if (recyclerView.getAdapter().getItemCount() == 0)
                    recyclerView.setNestedScrollingEnabled(false);
                    //if the first and the last item is visible
                else if (layoutManager.findFirstCompletelyVisibleItemPosition() == 0
                        && layoutManager.findLastCompletelyVisibleItemPosition() == recyclerView.getAdapter().getItemCount() - 1)
                    recyclerView.setNestedScrollingEnabled(false);
                else
                    recyclerView.setNestedScrollingEnabled(true);}},5);*/

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