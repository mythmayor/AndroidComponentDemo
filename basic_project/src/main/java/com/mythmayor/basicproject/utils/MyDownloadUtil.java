package com.mythmayor.basicproject.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;

import com.mythmayor.basicproject.BasicApplication;
import com.mythmayor.basicproject.itype.BaseListener;

/**
 * Created by mythmayor on 2020/6/30.
 * 下载工具类
 * 支持异步下载 {@link #startDownload}
 * 支持同步下载 {@link #startDownloadSync}
 * 支持取消下载 {@link #cancelDownload}
 */
public class MyDownloadUtil {

    private Context mContext;
    private String mDownloadUrl;//文件下载地址
    private String fileSubPath;//文件下载二级路径
    private DownloadListener mDownloadListener;//下载监听
    private boolean isSavePathPrivate;//是否保存至私有目录

    private DownloadManager mManager;
    private boolean isDownloading;//是否在下载中

    public MyDownloadUtil(Context context, String url, String subPath, DownloadListener listener) {
        this(context,url,subPath,false,listener);
    }

    public MyDownloadUtil(Context context, String url, String subPath, boolean isSavePathPrivate, DownloadListener listener) {
        this.mContext = context;
        this.mDownloadUrl = url;
        this.fileSubPath = subPath;
        this.mDownloadListener = listener;
        this.isSavePathPrivate = isSavePathPrivate;
        //获得DownloadManager对象
        mManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
    }

    /**
     * 开始下载-异步
     *
     * @return 下载请求ID
     */
    public long startDownload() {
        //获得下载id，这是下载任务生成时的唯一id，可通过此id获得下载信息
        long requestId = mManager.enqueue(createRequest(mDownloadUrl, fileSubPath));
        BasicApplication.getInstance().getAppExecutors().diskIO().execute(new Runnable() {//使用线程池管理子线程
            @Override
            public void run() {
                //查询下载信息方法
                queryDownloadProgress(requestId, mManager);
            }
        });
        return requestId;
    }

    /**
     * 开始下载-同步(调用需开启子线程)
     *
     * @return 下载请求ID
     */
    public long startDownloadSync() {
        //获得下载id，这是下载任务生成时的唯一id，可通过此id获得下载信息
        long requestId = mManager.enqueue(createRequest(mDownloadUrl, fileSubPath));
        //查询下载信息方法
        queryDownloadProgress(requestId, mManager);
        return requestId;
    }

    /**
     * 取消下载
     *
     * @param requestId 下载请求ID
     * @return 实际删除的下载数量
     */
    public int cancelDownload(int requestId) {
        isDownloading = false;
        return mManager.remove(requestId);
    }

    /**
     * 查询下载信息
     *
     * @param requestId 下载请求ID
     * @param manager   下载管理器
     */
    private void queryDownloadProgress(long requestId, DownloadManager manager) {
        DownloadManager.Query query = new DownloadManager.Query();
        //根据任务编号id查询下载任务信息
        query.setFilterById(requestId);
        try {
            isDownloading = true;
            while (isDownloading) {
                Cursor cursor = manager.query(query);
                if (cursor != null && cursor.moveToFirst()) {
                    //获得下载状态
                    int state = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    switch (state) {
                        case DownloadManager.STATUS_SUCCESSFUL://下载成功
                            isDownloading = false;
                            mDownloadListener.onDownloadStatusListener(DownloadManager.STATUS_SUCCESSFUL, null);
                            break;
                        case DownloadManager.STATUS_FAILED://下载失败
                            isDownloading = false;
                            mDownloadListener.onDownloadStatusListener(DownloadManager.STATUS_FAILED, null);
                            break;
                        case DownloadManager.STATUS_RUNNING://下载中
                            int totalSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));//计算下载率
                            int currentSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                            int progress = (int) (((float) currentSize) / ((float) totalSize) * 100);
                            mDownloadListener.onDownloadStatusListener(DownloadManager.STATUS_RUNNING, progress);
                            break;
                        case DownloadManager.STATUS_PAUSED://下载暂停
                            isDownloading = false;
                            mDownloadListener.onDownloadStatusListener(DownloadManager.STATUS_PAUSED, null);
                            break;
                        case DownloadManager.STATUS_PENDING://准备下载
                            mDownloadListener.onDownloadStatusListener(DownloadManager.STATUS_PENDING, null);
                            break;
                    }
                } else {
                    isDownloading = false;
                }
                if (cursor != null) {
                    cursor.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建下载请求对象
     *
     * @param url     下载地址
     * @param subPath 保存目录子路径
     * @return 下载请求对象
     */
    private DownloadManager.Request createRequest(String url, String subPath) {
        LogUtil.d(url);
        //uri是你的下载地址，可以使用Uri.parse("http://")包装成Uri对象
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        //此方法表示在下载过程中通知栏会一直显示该下载，在下载完成后仍然会显示，直到用户点击该通知或者消除该通知。还有其他参数可供选择
        //request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);// 隐藏notification
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);//显示notification
        // 通过setAllowedNetworkTypes方法可以设置允许在何种网络下下载，也可以使用setAllowedOverRoaming方法，它更加灵活
        request.setAllowedNetworkTypes(request.NETWORK_MOBILE | request.NETWORK_WIFI);//设置下载网络环境为手机数据流量、wifi
        // 设置一些基本显示信息
        //request.setTitle("this is title");
        //request.setDescription("下载完成后请点击打开");
        //request.setMimeType("application/pdf");
        //设置下载文件存放的路径，同样你可以选择以下方法存放在你想要的位置：setDestinationUri、setDestinationInExternalPublicDir
        if (isSavePathPrivate) {
            //保存至私有目录(/storage/Android/data/<package-name>/files/Download/<subPath>)
            request.setDestinationInExternalFilesDir(mContext, Environment.DIRECTORY_DOWNLOADS, subPath);
        } else {
            //保存至公有目录(/storage/Download/<subPath>)
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, subPath);
        }
        return request;
    }

    /**
     * 用于监听下载状态变化的接口
     */
    public interface DownloadListener extends BaseListener {
        void onDownloadStatusListener(int status, Object obj);
    }
}
