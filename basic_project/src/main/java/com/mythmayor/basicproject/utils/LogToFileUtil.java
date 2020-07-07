package com.mythmayor.basicproject.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by mythmayor on 2020/6/30.
 * 将Log日志写入文件中（使用单例模式是因为要初始化文件存放位置）
 */
public class LogToFileUtil {

    private static boolean LOGV = false;
    private static boolean LOGD = false;
    private static boolean LOGI = false;
    private static boolean LOGW = false;
    private static boolean LOGE = false;

    private static final String VERBOSE = "LOG_VERBOSE";
    private static final String DEBUG = "LOG_DEBUG";
    private static final String INFO = "LOG_INFO";
    private static final String WARN = "LOG_WARN";
    private static final String ERROR = "LOG_ERROR";

    private static String TAG = "❤驭霖·骏泊☆Myth.Mayor❤: ";

    private static String logPath = null;//log日志存放路径

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);//日期格式;
    private static SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);//时间格式;

    private static Date date = new Date();//因为log日志是使用日期命名的，使用静态成员变量主要是为了在整个程序运行期间只存在一个.log文件中;

    /**
     * 初始化，须在使用之前设置，最好在Application创建时调用
     *
     * @param context
     */
    public static void init(Context context) {
        logPath = getFilePath(context) + "/Logs";//获得文件储存路径,在后面加"/Logs"建立子文件夹
    }

    /**
     * 获得文件存储路径
     *
     * @return
     */
    private static String getFilePath(Context context) {
        String path = "";
        if (Environment.MEDIA_MOUNTED.equals(Environment.MEDIA_MOUNTED) || !Environment.isExternalStorageRemovable()) {//如果外部储存可用
            path = context.getExternalFilesDir(null).getPath();//获得外部存储路径,默认路径为 /storage/emulated/0/Android/data/com.waka.workspace.logtofile/files/Logs/log_2016-03-14_16-15-09.log
            //path = Environment.getExternalStorageDirectory() + File.separator + Constant.CACHE_PATH;
        } else {
            path = context.getFilesDir().getPath();//直接存在/data/data里，非root手机是看不到的
        }
        return path;
    }

    public static void v(String tag, String msg) {
        if (LOGV) {
            writeToFile(VERBOSE, tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (LOGE) {
            writeToFile(DEBUG, tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (LOGI) {
            writeToFile(INFO, tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (LOGW) {
            writeToFile(WARN, tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (LOGE) {
            writeToFile(ERROR, tag, msg);
        }
    }

    /**
     * 将log信息写入文件中
     *
     * @param type
     * @param tag
     * @param msg
     */
    private static void writeToFile(String type, String tag, String msg) {
        if (null == logPath) {
            Log.e(TAG, "logPath == null ，未初始化LogToFile");
            return;
        }
        String fileName = logPath + "/log_" + dateFormat.format(new Date()) + ".log";//log日志名，使用时间命名，保证不重复
        //String log = timeFormat.format(date) + " " + type + " " + tag + " " + msg + "\n";//log日志内容，可以自行定制
        String log = timeFormat.format(new Date(System.currentTimeMillis())) + " " + type + " " + tag + " " + msg + "\n";//log日志内容，可以自行定制
        //如果父路径不存在
        File file = new File(logPath);
        boolean exists = file.exists();
        if (!exists) {
            file.mkdirs();//创建父路径
        }
        FileOutputStream fos = null;//FileOutputStream会自动调用底层的close()方法，不用关闭
        BufferedWriter bw = null;
        try {
            fos = new FileOutputStream(fileName, exists);//这里的第二个参数代表追加还是覆盖，true为追加，flase为覆盖
            bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write(log);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();//关闭缓冲流
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void clearLogs() {
        File file = new File(logPath);
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
