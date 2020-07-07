package com.mythmayor.basicproject.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mythmayor on 2020/6/30.
 * 权限工具类
 * <p>
 * 可参考文章：https://blog.csdn.net/mythmayor/article/details/81388482
 * <p>
 * 自从安卓6.0系统开始，Android中引入了运行时权限，需要在代码中动态申请，下面是需要动态申请的所有运行时权限：
 * 这里有两点需要注意：
 * 1.运行时权限在安卓6.0系统及以上版本中只在AndroidManifest.xml配置中是不行的，还需要在代码中动态申请。
 * 2.每个运行时权限都属于一个权限组，我们在申请某一权限并通过用户的授权后，该权限所在的权限组中其它的权限也会被授权。
 * <p>
 * 运行时权限（危险权限）一共是9组24个权限：
 * <p>
 * 权限组名              权限名
 * ----------------------------------------------
 * CALENDAR             READ_CALENDAR / WRITE_CALENDAR
 * ----------------------------------------------
 * CAMERA               CAMERA
 * ----------------------------------------------
 * CONTACTS             READ_CONTACTS / WRITE_CONTACTS / GET_ACCOUNTS
 * ----------------------------------------------
 * LOCATION             ACCESS_FINE_LOCATION / ACCESS_COARSE_LOCATION
 * ----------------------------------------------
 * MICROPHONE           RECORD_AUDIO
 * ----------------------------------------------
 * PHONE                READ_PHONE_STATE / CALL_PHONE / READ_CALL_LOG / WRITE_CALL_LOG / ADD_VOICEMAIL / USE_SIP / PROCESS_OUTGOING_CALLS
 * -----------------------------------------------
 * SENSORS              BODY_SENSORS
 * -----------------------------------------------
 * SMS                  SEND_SMS / RECEIVE_SMS / READ_SMS / RECEIVE_WAP_PUSH / RECEIVE_MMS
 * -----------------------------------------------
 * STORAGE              READ_EXTERNAL_STORAGE / WRITE_EXTERNAL_STORAGE
 * -----------------------------------------------
 * <p>
 * 该工具类提供：1.申请所有权限；2.申请单个权限；3.申请自定义权限组。以3为例：
 * String[] permissionArray = new String[]{PermissionManager.PERMISSION_CAMERA,PermissionManager.PERMISSION_MICROPHONE,PermissionManager.PERMISSION_PHONE,PermissionManager.PERMISSION_STORAGE};
 * PermissionManager.getInstance(this).getCustomPermission(permissionArray);
 */
/*
    在Activity中可通过onRequestPermissionsResult来获取申请权限的结果，示例代码：

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //注意：此处返回的grantResults数组与上述申请自定义权限组传入的permissionArray数组是对应的。
        switch (requestCode) {
            case PermissionManager.REQUEST_CODE_CUSTOM:
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        int grantResult = grantResults[i];
                        boolean success = grantResult == PackageManager.PERMISSION_GRANTED;
                        if (success) {
                            //单项权限申请成功
                        } else {
                            //单项权限申请失败
                        }
                    }
                }
                break;
            default:

                break;
    }
}*/
public class PermissionManager {

    private static Context mContext;
    /**
     * 权限
     */
    public static final String PERMISSION_CALENDAR = Manifest.permission.READ_CALENDAR;//日历权限
    public static final String PERMISSION_CAMERA = Manifest.permission.CAMERA;//摄像头权限
    public static final String PERMISSION_CONTACTS = Manifest.permission.READ_CONTACTS;//联系人权限
    public static final String PERMISSION_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;//定位权限
    public static final String PERMISSION_MICROPHONE = Manifest.permission.RECORD_AUDIO;//麦克风权限
    public static final String PERMISSION_PHONE = Manifest.permission.READ_PHONE_STATE;//手机状态权限
    public static final String PERMISSION_SENSORS = Manifest.permission.BODY_SENSORS;//传感器权限
    public static final String PERMISSION_SMS = Manifest.permission.SEND_SMS;//短信权限
    public static final String PERMISSION_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;//存储权限
    /**
     * 请求码
     */
    public static final int REQUEST_CODE_ALL = 0;
    public static final int REQUEST_CODE_CALENDAR = 1;
    public static final int REQUEST_CODE_CAMERA = 2;
    public static final int REQUEST_CODE_CONTACTS = 3;
    public static final int REQUEST_CODE_LOCATION = 4;
    public static final int REQUEST_CODE_MICROPHONE = 5;
    public static final int REQUEST_CODE_PHONE = 6;
    public static final int REQUEST_CODE_SENSORS = 7;
    public static final int REQUEST_CODE_SMS = 8;
    public static final int REQUEST_CODE_STORAGE = 9;
    public static final int REQUEST_CODE_CUSTOM = 10;
    /**
     * 权限标记，true为已获取该权限，false为未获取该权限
     */
    private boolean isAllPermissionOk = false;
    private boolean isCalendarPermissionOk = false;
    private boolean isCameraPermissionOk = false;
    private boolean isContactsPermissionOk = false;
    private boolean isLocationPermissionOk = false;
    private boolean isMicrophonePermissionOk = false;
    private boolean isPhonePermissionOk = false;
    private boolean isSensorsPermissionOk = false;
    private boolean isSmsPermissionOk = false;
    private boolean isStoragePermissionOk = false;
    private boolean isCustomPermissionOk = false;
    //当前未获取的权限列表
    private List<String> mPermissionList = new ArrayList<>();
    //自定义权限数组
    private String[] mCustomPermissionArray;
    //所有权限数组
    private String[] mAllPermissionArray = new String[]{
            PERMISSION_CALENDAR,
            PERMISSION_CAMERA,
            PERMISSION_CONTACTS,
            PERMISSION_LOCATION,
            PERMISSION_MICROPHONE,
            PERMISSION_PHONE,
            PERMISSION_SENSORS,
            PERMISSION_SMS,
            PERMISSION_STORAGE
    };

    private static class SingletonHolder {
        public static PermissionManager instance = new PermissionManager();
    }

    private PermissionManager() {
    }

    public static PermissionManager getInstance(Context context) {
        mContext = context;
        return SingletonHolder.instance;
    }

    /**
     * 检查所有权限
     *
     * @return true已获取所有权限，false未获取所有权限
     */
    public boolean checkAllPermission() {
        isAllPermissionOk = true;
        mPermissionList.clear();
        for (int i = 0; i < mAllPermissionArray.length; i++) {
            if (ContextCompat.checkSelfPermission(mContext, mAllPermissionArray[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(mAllPermissionArray[i]);
                isAllPermissionOk = false;
            }
        }
        return isAllPermissionOk;
    }

    /**
     * 获取所有权限
     */
    public void getAllPermission() {
        checkAllPermission();
        if (mPermissionList.isEmpty()) {
            //未授予的权限为空，表示都授予了
        } else {
            //请求权限方法
            String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
            ActivityCompat.requestPermissions((Activity) mContext, permissions, REQUEST_CODE_ALL);
        }
    }

    /**
     * 检查自定义权限
     *
     * @param customPermissionArray 自定义权限数组
     * @return true已获取自定义权限，false未获取自定义权限
     */
    public boolean checkCustomPermission(String[] customPermissionArray) {
        mCustomPermissionArray = customPermissionArray;
        if (mCustomPermissionArray == null || mCustomPermissionArray.length == 0) {
            return false;
        }
        isCustomPermissionOk = true;
        mPermissionList.clear();
        for (int i = 0; i < mCustomPermissionArray.length; i++) {
            if (ContextCompat.checkSelfPermission(mContext, mCustomPermissionArray[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(mCustomPermissionArray[i]);
                isCustomPermissionOk = false;
            }
        }
        return isCustomPermissionOk;
    }

    /**
     * 获取自定义权限
     *
     * @param customPermissionArray 自定义权限数组
     */
    public void getCustomPermission(String[] customPermissionArray) {
        checkCustomPermission(customPermissionArray);
        if (mPermissionList.isEmpty()) {
            //未授予的权限为空，表示都授予了
        } else {
            //请求权限方法
            String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
            ActivityCompat.requestPermissions((Activity) mContext, permissions, REQUEST_CODE_CUSTOM);
        }
    }

    /**
     * 检查日历权限
     *
     * @return true已获取日历权限，false未获取日历权限
     */
    public boolean checkCalendarPermission() {
        int permission = ActivityCompat.checkSelfPermission(mContext, PERMISSION_CALENDAR);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            isCalendarPermissionOk = false;
        } else {
            isCalendarPermissionOk = true;
        }
        return isCalendarPermissionOk;
    }

    /**
     * 获取日历权限-拒绝权限后仍然弹出权限窗
     */
    public void getCalendarPermission1() {
        if (!checkCalendarPermission()) {
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{PERMISSION_CALENDAR}, REQUEST_CODE_CALENDAR);
        }
    }

    /**
     * 获取日历权限-拒绝权限后不再弹出权限窗
     * shouldShowRequestPermissionRationale方法，如果用户已经拒绝过则返回true，即不再弹出权限窗
     */
    public void getCalendarPermission2() {
        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext, PERMISSION_CALENDAR)) {
            // Show an expanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.
        } else {
            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{PERMISSION_CALENDAR}, REQUEST_CODE_CALENDAR);
            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }
    }

    /**
     * 检查摄像头权限
     *
     * @return true已获取摄像头权限，false未获取摄像头权限
     */
    public boolean checkCameraPermission() {
        int permission = ActivityCompat.checkSelfPermission(mContext, PERMISSION_CAMERA);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            isCameraPermissionOk = false;
        } else {
            isCameraPermissionOk = true;
        }
        return isCameraPermissionOk;
    }

    /**
     * 获取摄像头权限-拒绝权限后仍然弹出权限窗
     */
    public void getCameraPermission1() {
        if (!checkCameraPermission()) {
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{PERMISSION_CAMERA}, REQUEST_CODE_CAMERA);
        }
    }

    /**
     * 获取摄像头权限-拒绝权限后不再弹出权限窗
     */
    public void getCameraPermission2() {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext, PERMISSION_CAMERA)) {
        } else {
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{PERMISSION_CAMERA}, REQUEST_CODE_CAMERA);
        }
    }

    /**
     * 检查联系人权限
     *
     * @return true已获取联系人权限，false未获取联系人权限
     */
    public boolean checkContactsPermission() {
        int permission = ActivityCompat.checkSelfPermission(mContext, PERMISSION_CONTACTS);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            isContactsPermissionOk = false;
        } else {
            isContactsPermissionOk = true;
        }
        return isContactsPermissionOk;
    }

    /**
     * 获取联系人权限-拒绝权限后仍然弹出权限窗
     */
    public void getContactsPermission1() {
        if (!checkContactsPermission()) {
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{PERMISSION_CONTACTS}, REQUEST_CODE_CONTACTS);
        }
    }

    /**
     * 获取联系人权限-拒绝权限后不再弹出权限窗
     */
    public void getContactsPermission2() {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext, PERMISSION_CONTACTS)) {
        } else {
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{PERMISSION_CONTACTS}, REQUEST_CODE_CONTACTS);
        }
    }

    /**
     * 检查定位权限
     *
     * @return true已获取定位权限，false未获取定位权限
     */
    public boolean checkLocationPermission() {
        int permission = ActivityCompat.checkSelfPermission(mContext, PERMISSION_LOCATION);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            isLocationPermissionOk = false;
        } else {
            isLocationPermissionOk = true;
        }
        return isLocationPermissionOk;
    }

    /**
     * 获取定位权限-拒绝权限后仍然弹出权限窗
     */
    public void getLocationPermission1() {
        if (!checkLocationPermission()) {
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{PERMISSION_LOCATION}, REQUEST_CODE_LOCATION);
        }
    }

    /**
     * 获取定位权限-拒绝权限后不再弹出权限窗
     */
    public void getLocationPermission2() {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext, PERMISSION_LOCATION)) {
        } else {
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{PERMISSION_LOCATION}, REQUEST_CODE_LOCATION);
        }
    }

    /**
     * 检查麦克风权限
     *
     * @return true已获取麦克风权限，false未获取麦克风权限
     */
    public boolean checkMicrophonePermission() {
        int permission = ActivityCompat.checkSelfPermission(mContext, PERMISSION_MICROPHONE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            isMicrophonePermissionOk = false;
        } else {
            isMicrophonePermissionOk = true;
        }
        return isMicrophonePermissionOk;
    }

    /**
     * 获取麦克风权限-拒绝权限后仍然弹出权限窗
     */
    public void getMicrophonePermission1() {
        if (!checkMicrophonePermission()) {
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{PERMISSION_MICROPHONE}, REQUEST_CODE_MICROPHONE);
        }
    }

    /**
     * 获取麦克风权限-拒绝权限后不再弹出权限窗
     */
    public void getMicrophonePermission2() {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext, PERMISSION_MICROPHONE)) {
        } else {
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{PERMISSION_MICROPHONE}, REQUEST_CODE_MICROPHONE);
        }
    }

    /**
     * 检查手机状态权限
     *
     * @return true已获取手机状态权限，false未获取手机状态权限
     */
    public boolean checkPhonePermission() {
        int permission = ActivityCompat.checkSelfPermission(mContext, PERMISSION_PHONE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            isPhonePermissionOk = false;
        } else {
            isPhonePermissionOk = true;
        }
        return isPhonePermissionOk;
    }

    /**
     * 获取手机状态权限-拒绝权限后仍然弹出权限窗
     */
    public void getPhonePermission1() {
        if (!checkPhonePermission()) {
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{PERMISSION_PHONE}, REQUEST_CODE_PHONE);
        }
    }

    /**
     * 获取手机状态权限-拒绝权限后不再弹出权限窗
     */
    public void getPhonePermission2() {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext, PERMISSION_PHONE)) {
        } else {
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{PERMISSION_PHONE}, REQUEST_CODE_PHONE);
        }
    }

    /**
     * 检查传感器权限
     *
     * @return true已获取传感器权限，false未获取传感器权限
     */
    public boolean checkSensorsPermission() {
        int permission = ActivityCompat.checkSelfPermission(mContext, PERMISSION_SENSORS);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            isSensorsPermissionOk = false;
        } else {
            isSensorsPermissionOk = true;
        }
        return isSensorsPermissionOk;
    }

    /**
     * 获取传感器权限-拒绝权限后仍然弹出权限窗
     */
    public void getSensorsPermission1() {
        if (!checkSensorsPermission()) {
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{PERMISSION_SENSORS}, REQUEST_CODE_SENSORS);
        }
    }

    /**
     * 获取传感器权限-拒绝权限后不再弹出权限窗
     */
    public void getSensorsPermission2() {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext, PERMISSION_SENSORS)) {
        } else {
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{PERMISSION_SENSORS}, REQUEST_CODE_SENSORS);
        }
    }

    /**
     * 检查短信权限
     *
     * @return true已获取短信权限，false未获取短信权限
     */
    public boolean checkSmsPermission() {
        int permission = ActivityCompat.checkSelfPermission(mContext, PERMISSION_SMS);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            isSmsPermissionOk = false;
        } else {
            isSmsPermissionOk = true;
        }
        return isSmsPermissionOk;
    }

    /**
     * 获取短信权限-拒绝权限后仍然弹出权限窗
     */
    public void getSmsPermission1() {
        if (!checkSmsPermission()) {
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{PERMISSION_SMS}, REQUEST_CODE_SMS);
        }
    }

    /**
     * 获取短信权限-拒绝权限后不再弹出权限窗
     */
    public void getSmsPermission2() {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext, PERMISSION_SMS)) {
        } else {
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{PERMISSION_SMS}, REQUEST_CODE_SMS);
        }
    }

    /**
     * 检查存储权限
     *
     * @return true已获取存储权限，false未获取存储权限
     */
    public boolean checkStoragePermission() {
        int permission = ActivityCompat.checkSelfPermission(mContext, PERMISSION_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            isStoragePermissionOk = false;
        } else {
            isStoragePermissionOk = true;
        }
        return isStoragePermissionOk;
    }

    /**
     * 获取存储权限-拒绝权限后仍然弹出权限窗
     */
    public void getStoragePermission1() {
        if (!checkStoragePermission()) {
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{PERMISSION_STORAGE}, REQUEST_CODE_STORAGE);
        }
    }

    /**
     * 获取存储权限-拒绝权限后不再弹出权限窗
     */
    public void getStoragePermission2() {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext, PERMISSION_STORAGE)) {
        } else {
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{PERMISSION_STORAGE}, REQUEST_CODE_STORAGE);
        }
    }

    public boolean isAllPermissionOk() {
        return isAllPermissionOk;
    }

    public boolean isCalendarPermissionOk() {
        return isCalendarPermissionOk;
    }

    public boolean isCameraPermissionOk() {
        return isCameraPermissionOk;
    }

    public boolean isContactsPermissionOk() {
        return isContactsPermissionOk;
    }

    public boolean isLocationPermissionOk() {
        return isLocationPermissionOk;
    }

    public boolean isMicrophonePermissionOk() {
        return isMicrophonePermissionOk;
    }

    public boolean isPhonePermissionOk() {
        return isPhonePermissionOk;
    }

    public boolean isSensorsPermissionOk() {
        return isSensorsPermissionOk;
    }

    public boolean isSmsPermissionOk() {
        return isSmsPermissionOk;
    }

    public boolean isStoragePermissionOk() {
        return isStoragePermissionOk;
    }

    public boolean isCustomPermissionOk() {
        return isCustomPermissionOk;
    }
}