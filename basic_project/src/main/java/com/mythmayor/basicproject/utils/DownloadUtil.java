package com.mythmayor.basicproject.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;

/**
 * Created by mythmayor on 2020/6/30.
 * 下载工具类
 */
public class DownloadUtil {
    private String mUrl;
    private String mName;
    private Handler mHandler;
    private Context mContext;
    private DownloadManager mManager;
    private long requestId;

    public DownloadUtil(Context context, String url, Handler handler, String name) {
        this.mContext = context;
        this.mUrl = url;
        this.mHandler = handler;
        this.mName = name;
        //获得DownloadManager对象
        mManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
    }

    public long startDownload() {
        //获得下载id，这是下载任务生成时的唯一id，可通过此id获得下载信息
        requestId = mManager.enqueue(CreateRequest(mUrl, mName));
        //查询下载信息方法
        queryDownloadProgress(requestId, mManager);
        return requestId;
    }

    public int cancelDownload() {
        return mManager.remove(requestId);
    }

    private void queryDownloadProgress(long requestId, DownloadManager downloadManager) {
        DownloadManager.Query query = new DownloadManager.Query();
        //根据任务编号id查询下载任务信息
        query.setFilterById(requestId);
        try {
            boolean isGoging = true;
            while (isGoging) {
                Cursor cursor = downloadManager.query(query);
                if (cursor != null && cursor.moveToFirst()) {

                    //获得下载状态
                    int state = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    switch (state) {
                        case DownloadManager.STATUS_SUCCESSFUL://下载成功
                            isGoging = false;
                            mHandler.obtainMessage(downloadManager.STATUS_SUCCESSFUL).sendToTarget();//发送到主线程，更新ui
                            break;
                        case DownloadManager.STATUS_FAILED://下载失败
                            isGoging = false;
                            mHandler.obtainMessage(downloadManager.STATUS_FAILED).sendToTarget();//发送到主线程，更新ui
                            break;
                        case DownloadManager.STATUS_RUNNING://下载中
                            /**
                             * 计算下载下载率；
                             */
                            int totalSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                            int currentSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                            int progress = (int) (((float) currentSize) / ((float) totalSize) * 100);
                            mHandler.obtainMessage(downloadManager.STATUS_RUNNING, progress).sendToTarget();//发送到主线程，更新ui
                            break;
                        case DownloadManager.STATUS_PAUSED://下载停止
                            mHandler.obtainMessage(DownloadManager.STATUS_PAUSED).sendToTarget();
                            break;
                        case DownloadManager.STATUS_PENDING://准备下载
                            mHandler.obtainMessage(DownloadManager.STATUS_PENDING).sendToTarget();
                            break;
                    }
                }
                if (cursor != null) {
                    cursor.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private DownloadManager.Request CreateRequest(String url, String name) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        //request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);// 隐藏notification
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);//显示notification
        request.setAllowedNetworkTypes(request.NETWORK_MOBILE | request.NETWORK_WIFI);//设置下载网络环境为手机数据流量、wifi
        request.setDestinationInExternalFilesDir(mContext, Environment.DIRECTORY_DOWNLOADS, name);//指定apk缓存路径，默认是在SD卡中的Download文件夹
        return request;
    }
}
