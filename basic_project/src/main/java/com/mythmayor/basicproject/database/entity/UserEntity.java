package com.mythmayor.basicproject.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Created by mythmayor on 2020/7/14.
 * 数据库实体类
 */
@Entity
public class UserEntity {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;//主键自增ID
    @NonNull
    @ColumnInfo(name = "uid")
    private String uid;//用户唯一ID
    @ColumnInfo(name = "name")
    private String name;//用户名
    @ColumnInfo(name = "age")
    private int age;//年龄
    @ColumnInfo(name = "gender")
    private String gender;//性别

    public UserEntity() {
    }

    @Ignore
    public UserEntity(String uid, String name, int age, String gender) {
        this.uid = uid;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
