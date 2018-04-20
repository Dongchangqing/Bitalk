package com.duozhuan.bitalk.views.browser;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.duozhuan.bitalk.MainActivity;
import com.duozhuan.bitalk.app.Constants;
import com.duozhuan.bitalk.event.DiscoverLabelEvent;
import com.duozhuan.bitalk.event.JumpUrlEvent;
import com.duozhuan.bitalk.event.MessageCountEvent;
import com.duozhuan.bitalk.event.UserExitEvent;
import com.duozhuan.bitalk.event.UserInfoEvent;
import com.duozhuan.bitalk.util.ToastUtil;
import com.google.gson.Gson;
import com.hwangjr.rxbus.RxBus;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * JS 注入接口
 */
public class JSInterface extends Object{

    private final Context mContext;

    public JSInterface(Context context) {
        mContext = context;
    }



    //显示底部tab
    @JavascriptInterface
    public void showBottom(){
        RxBus.get().post(Constants.EVENT_SHOW_BOTTOM,"");
    }
    //隐藏底部tab
    @JavascriptInterface
    public void hiddenBottom(){
        RxBus.get().post(Constants.EVENT_HIDDEN_BOTTOM,"");
    }
    //显示底部tab和顶部导航
    @JavascriptInterface
    public void showBottomAndTop(){
        RxBus.get().post(Constants.EVENT_SHOW_BOTTOM_AND_TOP,"");
    }
    //隐藏底部tab和顶部导航
    @JavascriptInterface
    public void hiddenBottomAndTop(){
        RxBus.get().post(Constants.EVENT_HIDDEN_BOTTOM_AND_TOP,"");
    }
    //显示顶部导航
    @JavascriptInterface
    public void showNavigationBar(){
        RxBus.get().post(Constants.EVENT_SHOW_TOP,"");
    }
    //隐藏顶部导航
    @JavascriptInterface
    public void hiddenNavigationBar(){
        RxBus.get().post(Constants.EVENT_HIDDEN_TOP,"");
    }

    // 跳转用户简介( 打开用户个人中心 url(连接 )  isOpenNew(是否跳转新的页面))
    @JavascriptInterface
    public void openUserIntroduction(String json) {
        JumpUrlEvent event=new Gson().fromJson(json,JumpUrlEvent.class);
        ToastUtil.shortShow(event.getUrl());
        if (event!=null)
            RxBus.get().post(Constants.EVENT_USER_INTRODUCTION, event);
    }


    // 跳转帖子详情(打开帖子详情  url(连接)  isOpenNew(是否跳转新的页面))
    @JavascriptInterface
    public void openInvitationDetail(String json) {
        JumpUrlEvent event=new Gson().fromJson(json,JumpUrlEvent.class);
        if (event!=null)
            RxBus.get().post(Constants.EVENT_INVITATION_DETAIL, event);
    }


    // 跳转发现label( 点击了话题标签 label(标签内容)  isJump(是否跳转))
    @JavascriptInterface
    public void openDiscoverLabel(String json) {
        DiscoverLabelEvent event=new Gson().fromJson(json,DiscoverLabelEvent.class);
        if (event!=null)
            RxBus.get().post(Constants.EVENT_DISCOVER_LABEL, event);
    }

    //保存用户信息 name(用户名)  token(用户token)
//    @JavascriptInterface
//    public void saveUserInfo(String name , String token){
//        UserInfoEvent event=new UserInfoEvent();
//        event.setName(name);
//        event.setToken(token);
//        RxBus.get().post(Constants.EVENT_SAVE_USERINFO,event);
//    }
    @JavascriptInterface
    public void saveUserInfo(String json){
        UserInfoEvent event=new Gson().fromJson(json,UserInfoEvent.class);
        Log.i("dcqing",event.getName());
        RxBus.get().post(Constants.EVENT_SAVE_USERINFO,event);
    }
    //用户退出通知 userName(用户名) isClearUserInfo(是否清空用户信息)
    @JavascriptInterface
    public void userExit(String userName , boolean isClearUserInfo){
        UserExitEvent event=new UserExitEvent();
        event.setUserName(userName);
        event.setClearUserInfo(isClearUserInfo);
        RxBus.get().post(Constants.EVENT_USER_EXIT,event);
    }
    //用户登录通知 name(用户名)  token(用户token)
    @JavascriptInterface
    public void userLogin(String name , String token){
        UserInfoEvent event=new UserInfoEvent();
        event.setName(name);
        event.setToken(token);
        RxBus.get().post(Constants.EVENT_USER_LOGIN,event);
    }

    @JavascriptInterface
    public void messageCount(String json){
        MessageCountEvent event=new Gson().fromJson(json,MessageCountEvent.class);
        if (event!=null) {
            MainActivity.notificationCount = event.getNotificationCount();
            MainActivity.replyCount = event.getReplyCount();
            MainActivity.dynamicCount = event.getDynamicCount();
            RxBus.get().post(Constants.EVENT_MESSAGE_COUNT, event);
        }
    }



    @JavascriptInterface
    public void setTitle(String title){
        RxBus.get().post(Constants.EVENT_SET_WEB_TITLE, title);
    }

}
