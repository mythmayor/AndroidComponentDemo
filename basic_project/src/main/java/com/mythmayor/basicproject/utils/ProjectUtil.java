package com.mythmayor.basicproject.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaMetadataRetriever;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.SystemClock;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.mythmayor.basicproject.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mythmayor on 2020/6/30.
 * 项目常用工具类
 */
public class ProjectUtil {

    /**
     * 获取版本号
     *
     * @param context 上下文
     * @return 当前应用的版本号，获取失败返回"0"
     */
    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "1.0.0";
        }
    }

    /**
     * 判断当前是否是debug模式
     *
     * @param context 上下文
     * @return true或false
     */
    public static boolean isApkInDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return 0 != (info.flags & ApplicationInfo.FLAG_DEBUGGABLE);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断程序是否在后台运行
     */
    public static boolean isRunBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND == appProcess.importance) {
                    // 表明程序在后台运行
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 判断程序是否在前台运行（当前运行的程序）
     */
    public static boolean isRunForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = context.getPackageName();
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (null == appProcesses)
            return false;
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName)
                    && ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND == appProcess.importance) {
                return true;// 程序运行在前台
            }
        }
        return false;
    }

    /**
     * 设置Dialog窗口属性，在dialog.show()之后调用
     *
     * @param dialog 要显示的Dialog
     */
    public static void setDialogWindowAttr(Dialog dialog) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        dialog.getWindow().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        //lp.gravity = Gravity.CENTER;
        //lp.width = LinearLayout.LayoutParams.MATCH_PARENT;//宽高可设置具体大小
        lp.width = (displaymetrics.widthPixels / 8) * 7;//占屏幕宽度的八分之七
        //lp.height = LinearLayout.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(lp);
    }

    /**
     * 隐藏软键盘
     *
     * @param activity 当前界面
     * @param editText 当前输入框
     * @return true未隐藏 false隐藏
     */
    public static boolean hideSoftInput(Activity activity, EditText editText) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != imm) {
            return imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
        return false;
    }

    /**
     * 显示软键盘
     *
     * @param view
     */
    public static void showKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != imm) {
            view.requestFocus();
            imm.showSoftInput(view, 0);
        }
    }

    /**
     * 隐藏软键盘
     *
     * @param view
     */
    public static void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != imm) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 模拟点击控件的事件
     *
     * @param view 要模拟的控件
     * @param x    事件的x坐标
     * @param y    事件的y坐标
     */
    public static void simulateClick(View view, float x, float y) {
        long downTime = SystemClock.uptimeMillis();
        final MotionEvent downEvent = MotionEvent.obtain(downTime, downTime, MotionEvent.ACTION_DOWN, x, y, 0);
        downTime += 1000;
        final MotionEvent upEvent = MotionEvent.obtain(downTime, downTime, MotionEvent.ACTION_UP, x, y, 0);
        view.onTouchEvent(downEvent);
        view.onTouchEvent(upEvent);
        downEvent.recycle();
        upEvent.recycle();
    }

    /**
     * 判断当前国际化环境是否为简体中文
     *
     * @param activity 当前界面
     * @return true为简体中文，false相反
     */
    public static boolean isZh(Activity activity) {
        Locale locale = activity.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        if (language.endsWith("zh"))
            return true;
        else
            return false;
    }

    /**
     * 设置NumberPicker分隔线的样式
     *
     * @param numberPicker 要设置的NumberPicker控件
     * @param activity     当前界面
     */
    public static void setNumberPickerDividerColor(NumberPicker numberPicker, Activity activity) {
        //设置picker分割线的颜色
        NumberPicker picker = numberPicker;
        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    //pf.set(picker.getHeight(), 10);
                    //设置分割线的颜色值 透明
                    pf.set(picker, new ColorDrawable(activity.getResources().getColor(R.color.colorAccent)));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        //设置picker分割线的宽度
        for (Field pf2 : pickerFields) {
            if (pf2.getName().equals("mSelectionDividerHeight")) {
                pf2.setAccessible(true);
                try {
                    int result = 3;
                    pf2.set(picker, result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            if (pf2.getName().equals("mSelectionDividersDistance")) {
                pf2.setAccessible(true);
                try {
                    //pf2.set(picker.getHeight(), 20);
                    pf2.set(picker, 48);//按照需求在此处修改
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 验证手机号码格式
     *
     * @param mobile 要验证的手机号码
     * @return true格式正确，false不正确
     */
    public static boolean isMobileNO(String mobile) {
        /**
         * 移动：134 135 136 137 138 139 147 150 151 152 157 158 159 172 178 182 183 184 187 188 198
         * 联通：130 131 132 145 155 156 166 171 175 176 185 186
         * 电信：133 149 153 173 177 180 181 189 199
         * 虚拟运营商：170
         * 总结起来就是第一位必定为1，第二位必定为3、4、5、6、7、8、9，其他位置的可以为0-9
         */
        //"[1]"代表第1位为数字1，"[3456789]"代表第二位可以为3、4、5、6、7、8、9中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        String telRegex = "[1][3456789]\\d{9}";
        if (TextUtils.isEmpty(mobile)) {
            return false;
        } else {
            return mobile.matches(telRegex);
        }
    }

    /**
     * 验证邮箱格式
     *
     * @param email 要验证的邮箱
     * @return true格式正确，false不正确
     */
    public static boolean isEmail(String email) {
        if (null == email || "".equals(email)) return false;
        //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 判断当前网络环境是WiFi还是移动数据
     *
     * @param activity 要调用方法的Activity
     * @return 网络类型，1为WiFi、2为移动数据、3为其它
     */
    public static int checkNetType(Activity activity) {
        //判断是连接的内网还是外网主要用到ConnectivityManager
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isWifiNet = false;
        boolean isGprsNet = false;
        try {
            isWifiNet = NetworkInfo.State.CONNECTED == cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            isGprsNet = NetworkInfo.State.CONNECTED == cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (isWifiNet) {//连接的WiFi
            return 1;
        } else if (isGprsNet) {//连接的数据网络
            return 2;
        } else {
            return 3;
        }
    }

    /**
     * 获取设备IMEI
     * @param context Context
     * @return IMEI
     */
    @SuppressLint("MissingPermission")
    public static String getIMEI(Context context) {
        TelephonyManager telephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = telephonyMgr.getDeviceId();
        return imei;
    }

    /**
     * 获取设备唯一标识，将ANDROID_ID与序列号拼接后转成MD5格式进行返回
     *
     * @param context 上下文
     * @return 设备唯一标识
     */
    public static String getUniqueId(Context context) {
        String androidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        String id = androidID + Build.SERIAL;
        try {
            return toMD5(id);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return id;
        }
    }

    /**
     * 获取设备MAC地址
     * 需要添加的权限：INTERNET、ACCESS_WIFI_STATE
     *
     * @return 设备的Mac地址
     */
    public static String getMacAddress() {
        String macAddress = "";
        StringBuffer buf = new StringBuffer();
        NetworkInterface networkInterface;
        try {
            networkInterface = NetworkInterface.getByName("eth1");
            if (null == networkInterface) {
                networkInterface = NetworkInterface.getByName("wlan0");
            }
            if (null == networkInterface) {
                macAddress = "02:00:00:00:00:02";
            } else {
                byte[] addr = networkInterface.getHardwareAddress();
                for (byte b : addr) {
                    buf.append(String.format("%02X:", b));
                }
                if (buf.length() > 0) {
                    buf.deleteCharAt(buf.length() - 1);
                    macAddress = buf.toString();
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
            macAddress = "02:00:00:00:00:02";
        }
        return macAddress;
    }

    /**
     * 检测并获取权限
     *
     * @param activity    Activity
     * @param permission  权限名称，如Manifest.permission.READ_EXTERNAL_STORAGE
     * @param requestCode 请求码
     */
    public static void checkPermission(Activity activity, String permission, int requestCode) {
        if (Build.VERSION.SDK_INT >= 23) {//如果系统是6.0及以上则去申请
            int checkPermission = ContextCompat.checkSelfPermission(activity, permission);
            //检测是否已经申请
            if (PackageManager.PERMISSION_GRANTED != checkPermission) {
                //动态申请权限
                ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
            }
        }
    }

    /**
     * 将字符串转为MD5
     *
     * @param text 要转换的字符串
     * @return 转换后的字符串
     * @throws NoSuchAlgorithmException
     */
    private static String toMD5(String text) throws NoSuchAlgorithmException {
        //获取摘要器 MessageDigest
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        //通过摘要器对字符串的二进制字节数组进行hash计算
        byte[] digest = messageDigest.digest(text.getBytes());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < digest.length; i++) {
            //循环每个字符 将计算结果转化为正整数;
            int digestInt = digest[i] & 0xff;
            //将10进制转化为较短的16进制
            String hexString = Integer.toHexString(digestInt);
            //转化结果如果是个位数会省略0,因此判断并补0
            if (hexString.length() < 2) {
                sb.append(0);
            }
            //将循环结果添加到缓冲区
            sb.append(hexString);
        }
        //返回整个结果
        return sb.toString();
    }

    /**
     * Bitmap转为Base64
     *
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (null != bitmap) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != baos) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * Base64转为Bitmap
     *
     * @param base64Data
     * @return
     */
    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * SHA1算法
     *
     * @param data
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String sha1(String data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA1");
        md.update(data.getBytes());
        StringBuffer buf = new StringBuffer();
        byte[] bits = md.digest();
        for (int i = 0; i < bits.length; i++) {
            int a = bits[i];
            if (a < 0) a += 256;
            if (a < 16) buf.append("0");
            buf.append(Integer.toHexString(a));
        }
        return buf.toString();
    }

    /**
     * 给EditText添加监听方法
     *
     * @param activity
     * @param editText
     */
    public static void addEditTextObserver(final Activity activity, EditText editText) {
        editText.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            //当键盘弹出隐藏的时候会 调用此方法。
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                //获取当前界面可视部分
                activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                //获取屏幕的高度
                int screenHeight = activity.getWindow().getDecorView().getRootView().getHeight();
                //此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
                int heightDifference = screenHeight - r.bottom;
                BottomUIMenuUtil.hideBottomUIMenu1(activity);
            }
        });
    }

    /**
     * 安装APK
     *
     * @param context
     * @param filePath
     */
    public static void installApk(Context context, String filePath) {
        try {
            /**
             * provider
             * 处理android 7.0 及以上系统安装异常问题
             */
            File file = new File(filePath);
            Intent install = new Intent();
            install.setAction(Intent.ACTION_VIEW);
            install.addCategory(Intent.CATEGORY_DEFAULT);
            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Uri apkUri = FileProvider.getUriForFile(context, "com.zkpsych.sifaassistant.fileprovider", file);//在AndroidManifest中的android:authorities值
                install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//添加这一句表示对目标应用临时授权该Uri所代表的文件
                install.setDataAndType(apkUri, "application/vnd.android.package-archive");
            } else {
                install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            }
            context.startActivity(install);
        } catch (Exception e) {
            //文件解析失败
        }
    }

    /**
     * 版本号比较：主版本号和朱版本号比较，次版本号和次版本号比较等等
     *
     * @param version1
     * @param version2
     * @return 0代表相等，1代表version1大于version2，-1代表version1小于version2
     */
    public static int compareVersion(String version1, String version2) {
        if (version1.equals(version2)) {
            return 0;
        }
        String[] version1Array = version1.split("\\.");
        String[] version2Array = version2.split("\\.");
        int index = 0;
        // 获取最小长度值
        int minLen = Math.min(version1Array.length, version2Array.length);
        int diff = 0;
        // 循环判断每位的大小
        while (index < minLen
                && 0 == (diff = Integer.parseInt(version1Array[index])
                - Integer.parseInt(version2Array[index]))) {
            index++;
        }
        if (0 == diff) {
            // 如果位数不一致，比较多余位数
            for (int i = index; i < version1Array.length; i++) {
                if (Integer.parseInt(version1Array[i]) > 0) {
                    return 1;
                }
            }
            for (int i = index; i < version2Array.length; i++) {
                if (Integer.parseInt(version2Array[i]) > 0) {
                    return -1;
                }
            }
            return 0;
        } else {
            return diff > 0 ? 1 : -1;
        }
    }

    /**
     * 字符串过滤：例如正则表达式[^a-zA-Z0-9\u4E00-\u9FA5]{0,20}表示以小写字母开头，包含大小写字母，数字，汉字，长度为0-20位
     *
     * @param regexType 正则表达式类型
     * @param content
     * @return
     */
    public static String stringFilter(int regexType, String content) {
        String regex = "";
        if (1 == regexType) {//字母大小写、数字{0,20} - 绑定码
            regex = "[^a-zA-Z0-9]";
        } else if (2 == regexType) {//字母大小写、数字、汉字、横线、下划线{0,20} - 设备名称、入会昵称
            regex = "[^a-zA-Z0-9\u4E00-\u9FA5-_]";
        } else if (3 == regexType) {//字母大小写、数字、横线、下划线、@符、*符{0,10} - 会议密码
            regex = "[^a-zA-Z0-9-_@*]";
        } else if (4 == regexType) {//数字{5,11} - 会议号
            regex = "[^0-9]";
        }
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(content);
        return m.replaceAll("").trim();
    }

    /**
     * 给EditText添加TextWatcher
     *
     * @param regexType 正则表达式类型
     * @param editText
     */
    public static void addTextWatcher(final int regexType, final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String content = editText.getText().toString();
                String str = stringFilter(regexType, content);
                if (!content.equals(str)) {
                    editText.setText(str);
                    //设置新的光标所在位置
                    editText.setSelection(str.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 处理文本，将文本位数限制为maxLen，中文两个字符，英文一个字符
     *
     * @param content 要处理的文本
     * @param maxLen  限制文本字符数，中文两个字符，英文一个字符。例如：a啊b吧，则maxLen为6
     * @return
     */
    public static String handleText(String content, int maxLen) {
        if (TextUtils.isEmpty(content)) {
            return content;
        }
        int count = 0;
        int endIndex = 0;
        for (int i = 0; i < content.length(); i++) {
            char item = content.charAt(i);
            if (item < 128) {
                count = count + 1;
            } else {
                count = count + 2;
            }
            if (maxLen == count || (item >= 128 && maxLen + 1 == count)) {
                endIndex = i;
            }
        }
        if (count <= maxLen) {
            return content;
        } else {
            //return content.substring(0, endIndex) + "...";
            return content.substring(0, endIndex + 1);
        }
    }

    public static String getIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (null != info && info.isConnected()) {
            if (ConnectivityManager.TYPE_MOBILE == info.getType()) {    // 当前使用2G/3G/4G网络
                try {
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }
            } else if (ConnectivityManager.TYPE_WIFI == info.getType()) {    // 当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());    // 得到IPV4地址
                return ipAddress;
            }
        } else {
            // 当前无网络连接,请在设置中打开网络
            Toast.makeText(context, "当前无网络连接,请在设置中打开网络", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }

    /**
     * 格式化字符串，字符串为空或者null则返回--
     *
     * @param content
     * @return
     */
    public static String formatString(String content) {
        String format = "--";
        if (!TextUtils.isEmpty(content)) {
            format = content;
        }
        return format;
    }

    public static long getVideoTime(String uri) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        try {
            if (uri != null) {
                HashMap<String, String> headers = null;
                if (headers == null) {
                    headers = new HashMap<String, String>();
                    headers.put("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.4.2; zh-CN; MW-KW-001 Build/JRO03C) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 UCBrowser/1.0.0.001 U4/0.8.0 Mobile Safari/533.1");
                }
                mmr.setDataSource(uri, headers);
            } else {
                //mmr.setDataSource(mFD, mOffset, mLength);
            }
            //注意地址 uri = /storage/emulated/0/youshixiu/rectools/录屏专家190629113518.mp4    本地
            String duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);//时长(毫秒)
            String width = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);//宽
            String height = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);//高
            long videoTime = Long.parseLong(duration);
            return videoTime;
        } catch (Exception ex) {
            return 0;
        } finally {
            mmr.release();
        }
    }

    public static int getVideoWidth(String uri) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        try {
            if (uri != null) {
                HashMap<String, String> headers = null;
                if (headers == null) {
                    headers = new HashMap<String, String>();
                    headers.put("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.4.2; zh-CN; MW-KW-001 Build/JRO03C) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 UCBrowser/1.0.0.001 U4/0.8.0 Mobile Safari/533.1");
                }
                mmr.setDataSource(uri, headers);
            } else {
                //mmr.setDataSource(mFD, mOffset, mLength);
            }
            //注意地址 uri = /storage/emulated/0/youshixiu/rectools/录屏专家190629113518.mp4    本地
            String duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);//时长(毫秒)
            String width = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);//宽
            String height = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);//高
            int videoWidth = Integer.parseInt(width);
            return videoWidth;
        } catch (Exception ex) {
            return 0;
        } finally {
            mmr.release();
        }
    }

    public static int getVideoHeight(String uri) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        try {
            if (uri != null) {
                HashMap<String, String> headers = null;
                if (headers == null) {
                    headers = new HashMap<String, String>();
                    headers.put("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.4.2; zh-CN; MW-KW-001 Build/JRO03C) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 UCBrowser/1.0.0.001 U4/0.8.0 Mobile Safari/533.1");
                }
                mmr.setDataSource(uri, headers);
            } else {
                //mmr.setDataSource(mFD, mOffset, mLength);
            }
            //注意地址 uri = /storage/emulated/0/youshixiu/rectools/录屏专家190629113518.mp4    本地
            String duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);//时长(毫秒)
            String width = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);//宽
            String height = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);//高
            int videoHeight = Integer.parseInt(height);
            return videoHeight;
        } catch (Exception ex) {
            return 0;
        } finally {
            mmr.release();
        }
    }
}
