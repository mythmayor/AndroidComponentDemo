package com.mythmayor.basicproject.utils;


import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.mythmayor.basicproject.BasicApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
/**
 * Created by mythmayor on 2020/6/30.
 * 打印错误日志工具类
 */
public class PrintErrorLogUtil {

    private static final String TAG = PrintErrorLogUtil.class.getSimpleName();

    /**
     * 保存错误信息到文件中
     *
     * @param ex Exception
     */
    public static void saveCrashInfo2File(Throwable ex) {
        //存储相关的字符串信息
        StringBuffer sb = new StringBuffer();
        sb.append("====================================ERROR====================================\n");
        sb.append(collectDeviceInfo());
        //将 StringBuffer sb 中的字符串写出到文件中
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        try {
            String fileName = DateUtil.getYMD(System.currentTimeMillis()) + ".log";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                //获取文件输出路径
                String path = BasicApplication.getInstance().getContext().getExternalFilesDir("") + "/crashlogs/";
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
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file...", e);
        }
    }

    /**
     * 收集设备参数信息, 将手机到的信息存储到
     */
    private static String collectDeviceInfo() {
        StringBuffer sb = new StringBuffer();
        try {
            //获取包管理器
            PackageManager pm = BasicApplication.getInstance().getContext().getPackageManager();
            //获取包信息
            PackageInfo pi = pm.getPackageInfo(BasicApplication.getInstance().getContext().getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                //版本号
                String versionName = pi.versionName == null ? "null"
                        : pi.versionName;
                //版本代码
                String versionCode = pi.versionCode + "";
                sb.append("versionName = " + versionName + "\n" + "versionCode = " + versionCode + "\n");
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
                sb.append(field.getName() + " = " + field.get(null).toString() + "\n");
                Log.d(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
        return sb.toString();
    }

    public static void clearLogs() {
        String path = BasicApplication.getInstance().getContext().getExternalFilesDir("") + "/crashlogs/";
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length != 0) {
                for (File logFile : files) {
                    deleteDir(logFile);
                }
            }
        }
    }

    //递归删除
    private static void deleteDir(File dir) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (int i = 0; i < files.length; i++) {
                deleteDir(files[i]);
            }
        }
        dir.delete();
    }
}
