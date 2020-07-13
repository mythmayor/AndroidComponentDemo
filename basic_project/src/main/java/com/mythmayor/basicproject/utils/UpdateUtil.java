package com.mythmayor.basicproject.utils;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Message;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.bumptech.glide.Glide;
import com.mythmayor.basicproject.BasicProjectApplication;
import com.mythmayor.basicproject.MyConstant;
import com.mythmayor.basicproject.R;
import com.mythmayor.basicproject.base.LifecycleHandler;
import com.mythmayor.basicproject.itype.OnMyClickListener;
import com.mythmayor.basicproject.ui.dialog.UpdateDialog01;
import com.mythmayor.basicproject.ui.dialog.UpdateDialog02;

import java.io.File;
import java.util.List;

/**
 * Created by mythmayor on 2020/6/30.
 */
public class UpdateUtil {

    private DownloadDialog mDialog;
    private AppCompatActivity mActivity;
    private DownloadUtil mUtil;
    private String mVersion;
    private boolean mForceUpdate;
    private List<String> mUpdateContent;
    private LifecycleHandler mHandler;
    private String mApkSubPath;//Apk的二级路径
    private String mApkAbsolutePath;//Apk的完整路径
    private boolean isApkFileExists;//Apk文件是否存在

    @SuppressLint("HandlerLeak")
    public UpdateUtil(AppCompatActivity activity, String version, boolean forceUpdate, List<String> updateContent, String url) {
        mActivity = activity;
        mVersion = version;
        mForceUpdate = forceUpdate;
        mUpdateContent = updateContent;
        mApkSubPath = "/Apk/" + MyConstant.APK_NAME + "_" + mVersion + ".apk";
        mApkAbsolutePath = BasicProjectApplication.getInstance().getContext().getExternalFilesDir("") + "/Download" + mApkSubPath;
        isApkFileExists = new File(mApkAbsolutePath).exists();
        mHandler = new LifecycleHandler(new LifecycleOwner() {
            @NonNull
            @Override
            public Lifecycle getLifecycle() {
                return activity.getLifecycle();
            }
        }) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case DownloadManager.STATUS_SUCCESSFUL:
                        mDialog.setProgress(100);
                        canceledDialog();
                        install(mApkAbsolutePath);
                        break;
                    case DownloadManager.STATUS_RUNNING:
                        int progress = (int) msg.obj;
                        mDialog.setProgress(progress);
                        //canceledDialog();
                        break;
                    case DownloadManager.STATUS_FAILED:
                        canceledDialog();
                        break;
                    case DownloadManager.STATUS_PENDING:
                        showDialog();
                        break;
                }
            }
        };
        mUtil = new DownloadUtil(mActivity, url, mHandler, mApkSubPath);
    }

    private void showDialog() {
        if (mDialog == null) {
            mDialog = new DownloadDialog(mActivity, mForceUpdate, new OnMyClickListener() {
                @Override
                public void onMyClick(View view) {
                    int i = mUtil.cancelDownload();
                    if (i == 1) {
                        canceledDialog();
                        ToastUtil.showToast(mActivity, CommonUtil.getString(R.string.updateutil01));
                    }
                }
            });
        }
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
    }

    private void canceledDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    public void alertUpdateAppDialog() {
        final UpdateDialog01 dialog = new UpdateDialog01(mActivity, "v" + mVersion, mForceUpdate, mUpdateContent);
        dialog.setYesOnclickListener(new UpdateDialog01.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                dialog.dismiss();
                if (isApkFileExists) {
                    install(mApkAbsolutePath);
                } else {
                    checkNetType();
                }
            }
        });
        dialog.setNoOnclickListener(new UpdateDialog01.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                dialog.dismiss();
            }
        });
        dialog.show();
        ProjectUtil.setDialogWindowAttr(dialog);
    }

    private void checkNetType() {
        //先清除目录下的旧安装包
        File old = new File(mActivity.getExternalFilesDir("") + "/Download/Apk/");
        //删除webview 缓存目录
        if (old.exists()) {
            FileUtil.deleteFile(old);
        }
        //判断是连接的内网还是外网主要用到ConnectivityManager
        ConnectivityManager cm = (ConnectivityManager) mActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isWifiNet = false;
        boolean isGprsNet = false;
        try {
            isWifiNet = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            isGprsNet = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (isWifiNet) {//连接的WiFi
            download();
        } else if (isGprsNet) {//连接的数据网络
            alertTrafficWarn();
        }
    }

    private void alertTrafficWarn() {
        final UpdateDialog02 dialog = new UpdateDialog02(mActivity, CommonUtil.getString(R.string.updateutil06), !mForceUpdate);
        dialog.setYesOnclickListener(new UpdateDialog02.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                dialog.dismiss();
                download();
            }
        });
        dialog.setNoOnclickListener(new UpdateDialog02.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                dialog.dismiss();
                alertUpdateAppDialog();
            }
        });
        dialog.show();
        ProjectUtil.setDialogWindowAttr(dialog);
    }

    private void download() {
        showDialog();
        //最好是用单线程池，或者intentService
        //new Thread(new DownloadRunnable(this, mUrl, handler, "Test.apk")).start();
        new Thread() {
            @Override
            public void run() {
                long l = mUtil.startDownload();
                //ToastUtil.showToast(MeetActivity.this, "requestId = " + l);
            }
        }.start();
    }

    private void install(String filePath) {
        /**  清除缓存 */
        new ClearCacheTask().execute();
        /** 清除Pref缓存 */
        //PrefUtil.clear(MeetActivity.this);
        ProjectUtil.installApk(mActivity, filePath);
        //mActivity.finish();
    }

    class ClearCacheTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            GlideCacheUtil.getInstance().clearImageAllCache(BasicProjectApplication.getInstance().getContext());
            return getCacheSize();
        }

        @Override
        protected void onPostExecute(String result) {
            //ToastUtil.showToast(MainActivity.this, "完成清除缓存");
        }
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public String getVersion() {
        try {
            PackageManager manager = mActivity.getPackageManager();
            PackageInfo info = manager.getPackageInfo(mActivity.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            PrintErrorLogUtil.saveCrashInfo2File(e);
            return null;
        }
    }

    public String getCacheSize() {
        try {
            return FileUtil.FormetFileSize(FileUtil.getFileSize(Glide.getPhotoCacheDir(mActivity)));
        } catch (Exception e) {
            e.printStackTrace();
            PrintErrorLogUtil.saveCrashInfo2File(e);
        }
        return null;
    }
}
