package com.mythmayor.basicproject.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.mythmayor.basicproject.database.entity.UserEntity;

import java.util.List;

/**
 * Created by mythmayor on 2020/7/14.
 * 数据库数据访问对象
 */
@Dao
public interface UserDao {

    @Query("SELECT * FROM UserEntity")
    List<UserEntity> allUsers();

    @Query("SELECT * FROM UserEntity")
    LiveData<List<UserEntity>> getAllUsers();

    @Query("SELECT * FROM UserEntity WHERE uid IN (:userIds)")
    LiveData<List<UserEntity>> getAllByUserIds(String[] userIds);

    @Query("SELECT * FROM UserEntity WHERE name LIKE :name LIMIT 1")
    LiveData<UserEntity> getUserByName(String name);

    @Insert
    void insertAllUsers(List<UserEntity> users);

    @Insert
    void insertAllUser(List<UserEntity> users);

    @Insert
    void insertUser(UserEntity user);

    @Delete
    void deleteUser(UserEntity user);
}
