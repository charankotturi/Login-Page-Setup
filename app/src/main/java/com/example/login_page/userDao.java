package com.example.login_page;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface userDao {

    @Insert
    void insertSingleUser(User user);

    @Delete
    void deleteSingleUser(User user);

    @Update
    void updateSingleUser(User user);

    @Insert
    void insertMultipleUsers(List<User> manyUsers);

    @Query("SELECT * FROM users_table")
    List<User> getAllUsers();

    @Query("SELECT * FROM users_table WHERE email_id =:name")
    User getUserByEmail_id(String name);



}
