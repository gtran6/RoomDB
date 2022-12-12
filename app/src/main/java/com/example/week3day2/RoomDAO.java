package com.example.week3day2;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RoomDAO {

    @Insert
    void insert(RoomUsers roomUsers);

    @Update
    void update(RoomUsers roomUsers);

    @Query("delete from RoomUsers where id=:id")
    void delete(int id);

    @Query("select * from RoomUsers")
    List<RoomUsers> getAllUsers();
}
