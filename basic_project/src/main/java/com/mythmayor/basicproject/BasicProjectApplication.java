package com.mythmayor.basicproject;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.mythmayor.basicproject.base.BaseActivity;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

/**
 * Created by mythmayor on 2020/7/6.
 */
public class BasicProjectApplication {

    private static final String TAG = BasicProjectApplication.class.getSimpleName();
    private static volatile BasicProjectApplication instance;

    private BasicProjectApplication() {
    }

    public static BasicProjectApplication getInstance() {
        if (instance == null) {
            synchronized (BasicProjectApplication.class) {
                if (instance == null) {
                    instance = new BasicProjectApplication();
                }
            }
        }
        return instance;
    }

    //全局Context对象
    private Context mContext;
    //全局Application对象
    private Application mApplication;
    //Activity集合，用来管理所有的Activity
    private List<BaseActivity> mActivities;
    //要销毁的Activity的集合
    private Map<String, Activity> mDestoryMap;

    public void init(Application application) {
        initData(application);
        initNetwork();
        //CrashHandler.getInstance().init(application);
    }

    private void initData(Application application) {
        mContext = application;
        mApplication = application;
        mActivities = new LinkedList<>();
        mDestoryMap = new HashMap<>();
        if(BuildConfig.DEBUG){
            ARouter.openLog();//打印日志
            ARouter.openDebug();//开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(application);

    }

    private void initNetwork() {
        OkHttpClient mOkHttpCLient = new OkHttpClient.Builder()
                .connectTimeout(60000L, TimeUnit.MILLISECONDS)
                .readTimeout(60000L, TimeUnit.MILLISECONDS)
                .writeTimeout(60000L, TimeUnit.MILLISECONDS)
                .connectionPool(new ConnectionPool(5, 5, TimeUnit.SECONDS))
                .build();
        OkHttpUtils.initClient(mOkHttpCLient);
    }
    /**
     * 获取全局的Context
     *
     * @return 全局的Context对象
     */
    public Context getContext() {
        return mContext;
    }
    /**
     * 获取全局的Application
     *
     * @return 全局的Application对象
     */
    public Application getApplication() {
        return mApplication;
    }

    /**
     * 添加一个Activity
     *
     * @param activity 要添加的Activity
     */
    public void addActivity(BaseActivity activity) {
        mActivities.add(activity);
    }

    /**
     * 移除一个Activity
     *
     * @param activity 要移除的Activity
     */
    public void removeActivity(BaseActivity activity) {
        mActivities.remove(activity);
    }

    /**
     * 移除当前所有Activity
     */
    public void clearAllActivity() {
        ListIterator<BaseActivity> iterator = mActivities.listIterator();
        BaseActivity activity;
        while (iterator.hasNext()) {
            activity = iterator.next();
            if (null != activity) {
                activity.finish();
            }
        }
    }

    /**
     * 添加到销毁队列
     *
     * @param activity 要销毁的activity
     */
    public void addDestoryActivity(Activity activity, String activityName) {
        mDestoryMap.put(activityName, activity);
    }

    /**
     * 销毁指定Activity
     *
     * @param activityName 要销毁的Activity
     */
    public void destoryActivity(String activityName) {
        Set<String> keySet = mDestoryMap.keySet();
        if (keySet.size() > 0) {
            for (String key : keySet) {
                if (activityName.equals(key)) {
                    mDestoryMap.get(key).finish();
                }
            }
        }
    }
}
