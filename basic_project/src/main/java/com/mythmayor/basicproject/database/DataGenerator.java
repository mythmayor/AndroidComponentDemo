/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mythmayor.basicproject.database;

import com.mythmayor.basicproject.database.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by mythmayor on 2020/7/14.
 * Generates data to pre-populate the database
 */
public class DataGenerator {

    private static final String[] names = new String[]{
            "张三", "李四", "王五", "赵六", "周七"
    };

    public static List<UserEntity> generateUsers() {
        List<UserEntity> users = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < names.length; i++) {
            UserEntity user = new UserEntity();
            user.setUid("" + (i + 1));
            user.setName(names[i]);
            user.setAge(random.nextInt(100));
            user.setGender(random.nextBoolean() ? "male" : "female");
            users.add(user);
        }
        return users;
    }
}
