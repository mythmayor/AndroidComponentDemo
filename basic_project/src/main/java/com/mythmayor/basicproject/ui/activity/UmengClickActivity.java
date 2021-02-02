package com.mythmayor.basicproject.ui.activity;

import android.content.Intent;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mythmayor.basicproject.MyConstant;
import com.mythmayor.basicproject.utils.LogUtil;
import com.umeng.message.UmengNotifyClickActivity;

import org.android.agoo.common.AgooConstants;

/**
 * Created by mythmayor on 2020/7/23.
 * 离线模式使用
 */
@Route(path = MyConstant.AROUTER_UmengClickActivity)
public class UmengClickActivity extends UmengNotifyClickActivity {

    @Override
    public void onMessage(Intent intent) {
        LogUtil.i("onMessage");
        super.onMessage(intent);  //此方法必须调用，否则无法统计打开数
        if (intent == null) {
            LogUtil.i("intent = null");
        } else {
            String body = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
            LogUtil.i("MESSAGE_BODY=" + body);
        }
        /*Gson gson = new Gson();
        UmengClickBean bean = gson.fromJson(body, UmengClickBean.class);
        //ExtraBean包含三个字段：type,id,title,根据type跳转相应界面
        ExtraBean extraBean = bean.getExtra();
        if (null != bean) {
            Intent intent = new Intent(this, SplashActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//必须
            intent.putExtra("bean", extraBean);
            intent.putExtra("tag", 1);//标志位，点击跳过去的，区别于正常逻辑跳转
            startActivity(intent);
            finish();
        }*/
    }
}
