package com.mythmayor.mainproject.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mythmayor.basicproject.BasicApplication;
import com.mythmayor.basicproject.MyConstant;
import com.mythmayor.basicproject.base.BaseTitleBarActivity;
import com.mythmayor.basicproject.base.LifecycleHandler;
import com.mythmayor.basicproject.database.DataGenerator;
import com.mythmayor.basicproject.database.DataRepository;
import com.mythmayor.basicproject.database.entity.UserEntity;
import com.mythmayor.basicproject.ui.view.TopTitleBar;
import com.mythmayor.basicproject.utils.ProjectUtil;
import com.mythmayor.basicproject.utils.ToastUtil;
import com.mythmayor.basicproject.utils.http.HttpUtil;
import com.mythmayor.mainproject.R;

import java.util.List;
import java.util.Random;

/**
 * Created by mythmayor on 2020/7/14.
 * 用以测试Room数据库访问
 */
@Route(path = MyConstant.AROUTER_TestRoomDatabaseActivity)
public class TestRoomDatabaseActivity extends BaseTitleBarActivity {

    private Button btncreate;
    private Button btnqueryall;
    private EditText etquerybyuid;
    private Button btnquerybyuid;
    private EditText etquerybyname;
    private Button btnquerybyname;
    private Button btninsertall;
    private Button btninsert;
    private Button btndelete;
    private Button btncleartable;
    private TextView tvresult;
    private DataRepository mDataRepository;
    private static final int WHAT_CREATE_TABLE = 1;
    private LifecycleOwner mLifecycleOwner = new LifecycleOwner() {
        @NonNull
        @Override
        public Lifecycle getLifecycle() {
            return TestRoomDatabaseActivity.this.getLifecycle();
        }
    };
    @SuppressLint("HandlerLeak")
    private LifecycleHandler mHandler = new LifecycleHandler(new LifecycleOwner() {
        @NonNull
        @Override
        public Lifecycle getLifecycle() {
            return TestRoomDatabaseActivity.this.getLifecycle();
        }
    }) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case WHAT_CREATE_TABLE:
                    initDatabase();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public int getSubLayoutResId() {
        return R.layout.activity_test_room_database;
    }

    @Override
    public void initSubView(View view) {
        btncreate = (Button) findViewById(R.id.btn_create);
        btnqueryall = (Button) findViewById(R.id.btn_queryall);
        etquerybyuid = (EditText) findViewById(R.id.et_querybyuid);
        btnquerybyuid = (Button) findViewById(R.id.btn_querybyuid);
        etquerybyname = (EditText) findViewById(R.id.et_querybyname);
        btnquerybyname = (Button) findViewById(R.id.btn_querybyname);
        btninsertall = (Button) findViewById(R.id.btn_insertall);
        btninsert = (Button) findViewById(R.id.btn_insert);
        btndelete = (Button) findViewById(R.id.btn_delete);
        btncleartable = (Button) findViewById(R.id.btn_cleartable);
        tvresult = (TextView) findViewById(R.id.tv_result);
    }

    @Override
    public void initSubEvent() {
        btncreate.setOnClickListener(this);
        btnqueryall.setOnClickListener(this);
        btnquerybyuid.setOnClickListener(this);
        btnquerybyname.setOnClickListener(this);
        btninsertall.setOnClickListener(this);
        btninsert.setOnClickListener(this);
        btndelete.setOnClickListener(this);
        btncleartable.setOnClickListener(this);
    }

    @Override
    public void initSubData(Intent intent) {
        initDatabase();
    }

    private void initDatabase() {
        mDataRepository = BasicApplication.getInstance().getDataRepository();
        mDataRepository.getDatabaseCreated().observe(mLifecycleOwner, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean exist) {
                if (exist != null && exist) {
                    btncreate.setText("数据库已存在，无需创建");
                } else {
                    btncreate.setText("数据库不存在，立即创建");
                }
            }
        });
    }

    @Override
    public void setTitleBar(TopTitleBar topTitleBar) {
        topTitleBar.setLeftImage(true, R.mipmap.arrow_left);
        topTitleBar.setTopTitle(true, "Room数据库测试");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == btncreate) {
            createTable();
        } else if (v == btnqueryall) {
            queryAllUsers();
        } else if (v == btnquerybyuid) {
            ProjectUtil.hideKeyboard(etquerybyuid);
            queryByUid();
        } else if (v == btnquerybyname) {
            ProjectUtil.hideKeyboard(etquerybyname);
            queryByName();
        } else if (v == btninsertall) {
            insertAll();
        } else if (v == btninsert) {
            insertOne();
        } else if (v == btndelete) {
            deleteOne();
        } else if (v == btncleartable) {
            mDataRepository.clearAllTables(this);
            tvresult.setText("");
            ToastUtil.showToast("删除所有数据库表成功！");
        }
    }

    private void createTable() {
        Boolean exist = mDataRepository.getDatabaseCreated().getValue();
        if (exist != null && exist) {//数据库已存在，无需创建
            btncreate.setText("数据库已存在，无需创建");
            ToastUtil.showToast("数据库已存在，无需创建");
        } else {
            ToastUtil.showToast("正在创建数据库，请稍后...");
            mHandler.sendEmptyMessage(WHAT_CREATE_TABLE);
        }
    }

    private void queryAllUsers() {
        mDataRepository.getAllUsers().observe(mLifecycleOwner, new Observer<List<UserEntity>>() {
            @Override
            public void onChanged(List<UserEntity> userEntities) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < userEntities.size(); i++) {
                    UserEntity user = userEntities.get(i);
                    String s = HttpUtil.mGson.toJson(user);
                    sb.append(s);
                    sb.append("\n");
                }
                tvresult.setText(sb.toString());
            }
        });
    }

    private void queryByUid() {
        String uid = etquerybyuid.getText().toString().trim();
        String[] split = uid.split(",");
        mDataRepository.getAllByUserIds(split).observe(mLifecycleOwner, new Observer<List<UserEntity>>() {
            @Override
            public void onChanged(List<UserEntity> userEntities) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < userEntities.size(); i++) {
                    UserEntity user = userEntities.get(i);
                    String s = HttpUtil.mGson.toJson(user);
                    sb.append(s);
                    sb.append("\n");
                }
                tvresult.setText(sb.toString());
            }
        });
    }

    private void queryByName() {
        String name = etquerybyname.getText().toString().trim();
        mDataRepository.getUserByName(name).observe(mLifecycleOwner, new Observer<UserEntity>() {
            @Override
            public void onChanged(UserEntity userEntity) {
                tvresult.setText(HttpUtil.mGson.toJson(userEntity));
            }
        });
    }

    private void insertAll() {
        List<UserEntity> users = DataGenerator.generateUsers();
        BasicApplication.getInstance().getAppExecutors().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDataRepository.getAppDatabase().runInTransaction(new Runnable() {
                    @Override
                    public void run() {
                        mDataRepository.insertAllUsers(users);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.showToast("插入全部成功！");
                                queryAllUsers();
                            }
                        });
                    }
                });
            }
        });
    }

    private void insertOne() {
        BasicApplication.getInstance().getAppExecutors().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                int index = mDataRepository.allUsers().size() + 1;
                Random random = new Random();
                String[] names = {"新增人员", "New"};
                UserEntity user = new UserEntity("" + index, names[random.nextInt(2)] + index, random.nextInt(100), random.nextBoolean() ? "male" : "female");
                mDataRepository.insertUser(user);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast("插入单个成功！");
                        queryAllUsers();
                    }
                });
            }
        });
    }

    private void deleteOne() {
        BasicApplication.getInstance().getAppExecutors().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<UserEntity> list = mDataRepository.allUsers();
                UserEntity user = list.get(list.size() - 1);
                mDataRepository.deleteUser(user);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast("删除单个成功！");
                        queryAllUsers();
                    }
                });
            }
        });
    }
}