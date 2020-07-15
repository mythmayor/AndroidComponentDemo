package com.mythmayor.basicproject.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.mythmayor.basicproject.AppExecutors;
import com.mythmayor.basicproject.database.dao.UserDao;
import com.mythmayor.basicproject.database.entity.UserEntity;

import java.util.List;

/**
 * Created by mythmayor on 2020/7/14.
 */
@Database(entities = {UserEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase sInstance;
    private static AppExecutors mAppExecutors;

    @VisibleForTesting
    public static final String DATABASE_NAME = "MyApp-db";

    public abstract UserDao userDao();

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static AppDatabase getInstance(final Context context, final AppExecutors executors) {
        mAppExecutors = executors;
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext(), executors);
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                    executors.diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                //执行了具体操作后，数据库才会被真正创建出来
                                sInstance.userDao().allUsers();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        }
        return sInstance;
    }

    /**
     * Build the database. {@link Builder#build()} only sets up the database configuration and
     * creates a new instance of the database.
     * The SQLite database is only created when it's accessed for the first time.
     */
    private static AppDatabase buildDatabase(final Context appContext,
                                             final AppExecutors executors) {
        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        executors.diskIO().execute(() -> {
                            // Generate the data for pre-population
                            AppDatabase database = AppDatabase.getInstance(appContext, executors);
                            List<UserEntity> users = DataGenerator.generateUsers();
                            insertData(database, users);
                            // notify that the database was created and it's ready to be used
                            database.setDatabaseCreated();
                        });
                    }
                })
                //.allowMainThreadQueries()//在主线程操作，不建议开启
                //.addMigrations(MIGRATION_1_2)//设置数据库升级(迁移)的逻辑
                .build();
    }

    private static void insertData(AppDatabase database, List<UserEntity> users) {
        database.runInTransaction(new Runnable() {
            @Override
            public void run() {
                database.userDao().insertAllUser(users);
            }
        });
    }

    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true);
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }

    public void clearAllTables(Context context) {
        mAppExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                //从所有注册为该数据库的表中删除所有行
                Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME).build().clearAllTables();
            }
        });
    }

    public AppExecutors getAppExecutors() {
        return mAppExecutors;
    }

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            //数据库版本 1->2 user表格新增了id列
            database.execSQL("ALTER TABLE UserEntity ADD COLUMN id INTEGER NOT NULL DEFAULT 0");
        }
    };

    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            //数据库版本 2->3 新增book表格
            database.execSQL("CREATE TABLE IF NOT EXISTS `book` (`uid` INTEGER PRIMARY KEY autoincrement, `name` TEXT , `userId` INTEGER, 'time' INTEGER)");
        }
    };
}
