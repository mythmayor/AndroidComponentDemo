package com.mythmayor.basicproject.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.mythmayor.basicproject.database.entity.UserEntity;

import java.util.List;

/**
 * Created by mythmayor on 2020/7/15.
 */
public class DataRepository {

    private static DataRepository sInstance;
    private final AppDatabase mDatabase;

    public DataRepository(final AppDatabase database) {
        mDatabase = database;
    }

    public static DataRepository getInstance(final AppDatabase database) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database);
                }
            }
        }
        return sInstance;
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mDatabase.getDatabaseCreated();
    }

    public List<UserEntity> allUsers() {
        return mDatabase.userDao().allUsers();
    }

    public LiveData<List<UserEntity>> getAllUsers() {
        return mDatabase.userDao().getAllUsers();
    }

    public LiveData<List<UserEntity>> getAllByUserIds(String[] userIds) {
        return mDatabase.userDao().getAllByUserIds(userIds);
    }

    public LiveData<UserEntity> getUserByName(String name) {
        return mDatabase.userDao().getUserByName(name);
    }

    public void insertAllUsers(List<UserEntity> users) {
        mDatabase.userDao().insertAllUsers(users);
    }

    public void insertUser(UserEntity user) {
        mDatabase.userDao().insertUser(user);
    }

    public void deleteUser(UserEntity user) {
        mDatabase.userDao().deleteUser(user);
    }

    public void clearAllTables(Context context) {
        mDatabase.clearAllTables(context);
    }

    public AppDatabase getAppDatabase() {
        return mDatabase;
    }
}
