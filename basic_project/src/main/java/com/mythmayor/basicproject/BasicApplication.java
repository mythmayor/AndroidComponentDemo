package com.mythmayor.basicproject;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import androidx.multidex.MultiDex;

import com.alibaba.android.arouter.BuildConfig;
import com.alibaba.android.arouter.launcher.ARouter;
import com.mythmayor.basicproject.base.BaseActivity;
import com.mythmayor.basicproject.database.AppDatabase;
import com.mythmayor.basicproject.database.DataRepository;
import com.mythmayor.basicproject.utils.CrashHandler;
import com.mythmayor.basicproject.utils.LogUtil;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import me.jessyan.autosize.AutoSize;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

/**
 * Created by mythmayor on 2020/7/6.
 */
public class BasicApplication {

    private static final String TAG = BasicApplication.class.getSimpleName();
    private static volatile BasicApplication instance;
    private PushAgent mPushAgent;

    private BasicApplication() {
    }

    public static BasicApplication getInstance() {
        if (instance == null) {
            synchronized (BasicApplication.class) {
                if (instance == null) {
                    instance = new BasicApplication();
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
    private Map<String, Activity> mDestroyMap;
    //全局的线程池
    private AppExecutors mAppExecutors;

    public void init(Application application) {
        //当App中出现多进程, 并且您需要适配所有的进程, 就需要在App初始化时调用initCompatMultiProcess()
        AutoSize.initCompatMultiProcess(application);
        //解决方法数超过65535的报错问题
        MultiDex.install(application);
        //初始化数据
        initData(application);
        //初始化网络请求框架
        initHttp();
    }

    private void initData(Application application) {
        if (com.mythmayor.basicproject.BuildConfig.DEBUG) {//调试模式
            //【性能优化工具】开启严格模式，用于检测主线程违规的情况并进行弹窗。
            //StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().penaltyDialog().build());
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
        } else {//非调试模式
            //开启Crash日志监听
            CrashHandler.getInstance().init(application);
        }
        mContext = application;
        mApplication = application;
        mActivities = new LinkedList<>();
        mDestroyMap = new HashMap<>();
        mAppExecutors = new AppExecutors();
        if (BuildConfig.DEBUG) {
            ARouter.openLog();//打印日志
            ARouter.openDebug();//开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        mAppExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                //初始化ARouter
                ARouter.init(application);
                //初始化友盟消息推送
                initUmengPush();
            }
        });
    }

    private void initHttp() {
        OkHttpClient mOkHttpCLient = new OkHttpClient.Builder()
                .connectTimeout(60000L, TimeUnit.MILLISECONDS)
                .readTimeout(60000L, TimeUnit.MILLISECONDS)
                .writeTimeout(60000L, TimeUnit.MILLISECONDS)
                .connectionPool(new ConnectionPool(5, 5, TimeUnit.SECONDS))
                .build();
        OkHttpUtils.initClient(mOkHttpCLient);
    }

    private void initUmengPush() {
        // 在此处调用基础组件包提供的初始化函数 相应信息可在应用管理 -> 应用信息 中找到 http://message.umeng.com/list/apps
        // 参数一：当前上下文context；
        // 参数二：应用申请的Appkey（需替换）；
        // 参数三：渠道名称；
        // 参数四：设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机；
        // 参数五：Push推送业务的secret 填充Umeng Message Secret对应信息（需替换）
        UMConfigure.init(mContext, MyConstant.UMENG_APP_KAY, "Umeng", UMConfigure.DEVICE_TYPE_PHONE, MyConstant.UMENG_MESSAGE_SECRET);
        //获取消息推送代理示例
        mPushAgent = PushAgent.getInstance(mContext);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                LogUtil.i("注册成功：deviceToken：-------->  " + deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                LogUtil.e("注册失败：-------->  " + "s:" + s + ",s1:" + s1);
            }
        });
        //自定义通知栏打开动作
        mPushAgent.setNotificationClickHandler(new UmengNotificationClickHandler() {
            @Override
            public void dealWithCustomAction(Context context, UMessage uMessage) {

            }
        });
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
    public void addDestroyActivity(Activity activity, String activityName) {
        mDestroyMap.put(activityName, activity);
    }

    /**
     * 销毁指定Activity
     *
     * @param activityName 要销毁的Activity
     */
    public void destroyActivity(String activityName) {
        Set<String> keySet = mDestroyMap.keySet();
        if (keySet.size() > 0) {
            for (String key : keySet) {
                if (activityName.equals(key)) {
                    mDestroyMap.get(key).finish();
                }
            }
        }
        mDestroyMap.remove(activityName);
    }

    public AppExecutors getAppExecutors() {
        return mAppExecutors;
    }

    public AppDatabase getAppDatabase() {
        return AppDatabase.getInstance(mContext, mAppExecutors);
    }

    public DataRepository getDataRepository() {
        return DataRepository.getInstance(getAppDatabase());
    }

    public PushAgent getPushAgent() {
        return mPushAgent;
    }
}
