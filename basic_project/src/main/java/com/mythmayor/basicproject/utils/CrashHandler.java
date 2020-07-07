package com.mythmayor.basicproject.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mythmayor on 2020/6/30.
 * 输出错误日志工具类
 * <p>
 * UncaughtExceptionHanlder 作用 : 处理 线程被未捕获的异常终止 的情况, 一旦出现了未捕获异常崩溃, 系统就会回调该类的
 * uncaughtException 方法;
 * <p>
 * 使用：在MainApplication的onCreate方法中执行初始化即可   CrashHandler.getInstance().init(this);
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    // 用于打印日志的 TAG 标识符
    public static final String TAG = "mythmayor.CrashHandler";

    // 系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    // 程序的Context对象
    private Context mContext;
    // 用来存储设备信息和异常信息
    private Map<String, String> mInfos = new HashMap<String, String>();
    // 用于格式化日期,作为日志文件名的一部分
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    // 单例模式
    private static CrashHandler INSTANCE = new CrashHandler();

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    /**
     * 初始化该类, 向系统中注册
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;
        // 获取系统默认的 UncaughtException 处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该 CrashHandler 为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /*
     * 出现未捕获的异常时, 会自动回调该方法
     * (non-Javadoc)
     * @see java.lang.Thread.UncaughtExceptionHandler#uncaughtException(java.lang.Thread, java.lang.Throwable)
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        /*
         * 调用 handleException() 方法处理该线程
		 * 如果返回 true 说明处理成功, 如果返回 false 则调用默认的异常处理器来处理
		 * 一般情况下该方法都会成功处理
		 */

        Log.e("ex", ex + "");
        if (!handleException(ex) && null != mDefaultHandler) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Log.e(TAG, "error : ", e);
            }
            // 退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex 异常信息
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (null == ex) {
            return false;
        }
        /*
         * 使用Toast来显示异常信息,
         * 由于在主线程会阻塞,
         * 不能实时出现 Toast 信息,
         * 这里我们在子线程中处理 Toast 信息
         */
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "抱歉，程序出现异常，即将退出...", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();
        // 收集设备参数信息
        collectDeviceInfo(mContext);
        // 保存日志文件
        saveCrashInfo2File(ex);
        return true;
    }

    /**
     * 收集设备参数信息, 将手机到的信息存储到
     *
     * @param ctx 上下文对象
     */
    public void collectDeviceInfo(Context ctx) {
        try {
            //获取包管理器
            PackageManager pm = ctx.getPackageManager();
            //获取包信息
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            if (null != pi) {
                //版本号
                String versionName = null == pi.versionName ? "null"
                        : pi.versionName;
                //版本代码
                String versionCode = pi.versionCode + "";
                //将版本信息存放到 成员变量 Map<String, String> mInfos 中
                this.mInfos.put("versionName", versionName);
                this.mInfos.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info", e);
        }

        //获取 Build 中定义的变量, 使用反射方式获取, 该类中定义了设备相关的变量信息
        Field[] fields = Build.class.getDeclaredFields();
        //遍历获取额变量, 将这些信息存放到成员变量 Map<String, String> mInfos 中
        for (Field field : fields) {
            try {
                //设置 Build 成员变量可访问
                field.setAccessible(true);
                //将 设备相关的信息存放到 mInfos 成员变量中
                mInfos.put(field.getName(), field.get(null).toString());
                Log.d(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private String saveCrashInfo2File(Throwable ex) {
        //存储相关的字符串信息
        StringBuffer sb = new StringBuffer();
        sb.append("====================================ERROR====================================\n");
        //将成员变量 Map<String, String> mInfos  中的数据 存储到 StringBuffer sb 中
        for (Map.Entry<String, String> entry : this.mInfos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + " = " + value + "\n");
        }

        //将 StringBuffer sb 中的字符串写出到文件中
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (null != cause) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        try {
            long timestamp = System.currentTimeMillis();
            String time = formatter.format(new Date());
            //String fileName = "crash-" + time + "-" + timestamp + ".txt";
            String fileName = DateUtil.getYMD(System.currentTimeMillis()) + ".log";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                //获取文件输出路径
                //String path = Environment.getExternalStorageDirectory() + "/crashinfo/";
                String path = mContext.getExternalFilesDir("") + "/crashlogs/";
                //创建文件夹和文件
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                File crashFile = new File(path, fileName);
                if (crashFile.exists()) {
                    //创建输出流
                    FileOutputStream fos = new FileOutputStream(path + fileName, true);
                    //向文件中写出数据
                    fos.write(sb.toString().getBytes());
                    fos.close();
                } else {
                    //创建输出流
                    FileOutputStream fos = new FileOutputStream(path + fileName);
                    //向文件中写出数据
                    fos.write(sb.toString().getBytes());
                    fos.close();
                }

            }
            return fileName;
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file...", e);
        }
        return null;
    }

}
