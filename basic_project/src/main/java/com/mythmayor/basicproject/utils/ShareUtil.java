package com.mythmayor.basicproject.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.mythmayor.basicproject.R;

/**
 * Created by mythmayor on 2020/6/30.
 * 分享工具类
 */
public class ShareUtil {
    /**
     * 短信分享
     *
     * @param context
     * @param smstext 短信分享内容
     * @return
     */
    public static Boolean sendSms(Context context, String smstext) {
        Uri smsToUri = Uri.parse("smsto:");
        Intent mIntent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        mIntent.putExtra("sms_body", smstext);
        context.startActivity(mIntent);
        return null;
    }

    /**
     * 邮件分享
     *
     * @param context
     * @param title   邮件的标题
     * @param text    邮件的内容
     * @return
     */
    public static void sendMail(Context context, String title, String text) {
        // 调用系统发邮件
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        // 设置文本格式
        emailIntent.setType("text/plain");
        // 设置对方邮件地址
        emailIntent.putExtra(Intent.EXTRA_EMAIL, "");
        // 设置标题内容
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, title);
        // 设置邮件文本内容
        emailIntent.putExtra(Intent.EXTRA_TEXT, text);
        context.startActivity(Intent.createChooser(emailIntent, CommonUtil.getString(R.string.select_email_client)));
    }

    /**
     * 邮件分享
     *
     * @param context
     * @param title   分享的标题
     * @param content 分享的内容
     */
    public static void sendEmail(Context context, String title, String content) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_SUBJECT, title);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        context.startActivity(Intent.createChooser(intent, CommonUtil.getString(R.string.select_email_client)));
    }
}
