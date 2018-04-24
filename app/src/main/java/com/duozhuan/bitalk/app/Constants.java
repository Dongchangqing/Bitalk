package com.duozhuan.bitalk.app;


public class Constants {
    //首页
    //public static String Host="https://www.bitalk.cc/";
    public static String Host="https://steem.duozhuan.cn/";
    //public static String Host="http://192.168.0.101:3000/";

    //发帖
    public static String SEND="https://steem.duozhuan.cn/editor";

    //发现页面
    public static String READ="https://steem.duozhuan.cn/trending/cn-reader";
    public static String ASK="https://steem.duozhuan.cn/trending/cn-ask";
    public static String FOOD="https://steem.duozhuan.cn/trending/food";
    public static String TRAVEL="https://steem.duozhuan.cn/trending/travel";
    public static String MONEY="https://steem.duozhuan.cn/trending/cn-money";
    public static String ALL="https://steem.duozhuan.cn/trending";

    //通知页面
    public static String NOTIFICATION="https://steem.duozhuan.cn/notifications";
    public static String REPLY="https://steem.duozhuan.cn/replies";
    public static String DYNAMIC="https://steem.duozhuan.cn/activity";

    //我的
    public static String PERSONALEDIT=Constants.Host+"@";
    public static String DRAFTS="https://steem.duozhuan.cn/drafts";
    public static String BOOKMARKS="https://steem.duozhuan.cn/bookmarks";
    public static String WALLET="https://steem.duozhuan.cn/wallet";
    public static String SETTING="https://steem.duozhuan.cn/settings";

    //登录相关
    public static String REMOVETOKEN="https://steemconnect.com/api/";
    public static String LOGINURL="https://steemconnect.com/oauth2/authorize?client_id=duozhuan&redirect_uri=https%3A%2F%2Fsteem.duozhuan.cn%2Fcallback%2F&scope=";

    //注册
    public static String REGISTERURL="https://signup.steemit.com/?ref=duozhuan";
    //版本号
    public static final String APP_VERSION = "1.0.0";
    public static String LOGINOUTURL="https://steemconnect.com/api/oauth2/token/revoke";
    //退出

    //webview
    public static final int STATUS_DISCONNECT = -3;// 网络连接断开
    public static final int STATUS_TIMEOUT = -2;// 网络超时
    public static final int STATUS_ERROR = 0;  //访问网络失败
    public static final int STATUS_SUCCESS = 1;  //访问网络成功

    public static final int STATUS_UPDATE_TOKEN = 401; // 需要刷新token

    public final static String EVENT_WEBVIEW_PROGRESS_REFRESH = "event_webview_progress_refresh"; // web view 加载进度
    public final static String EVENT_WEBVIEW_PAGE_START = "event_webview_page_start"; // webview page start 调用
    public final static String EVENT_WEBVIEW_PAGE_FINISH = "event_webview_page_finish"; // webview page finish 调用
    public final static String EVENT_WEBVIEW_PAGE_ERROR = "event_webview_page_error"; // webview page error 调用
    public final static String EVENT_SET_WEB_TITLE = "event_set_web_title";  // 设置网页的标题
    public final static String EVENT_WEBVIEW_PAGE_LOADRESOURCE = "event_webview_page_loadresource";


    //sp
    public static final String LOGIN_USERNAME = "login_username";
    public static final String LOGIN_ACCENTTOKEN = "login_accenttoken";

    //发现页面传值
    public static final String IT_DISCOVER_URL = "discover_url";
    public static final String IT_NOTIFICATION_URL = "notification_url";


    //关注页面点击跳转
    public static final String EVENT_USER_INTRODUCTION="open_user_introduction";
    public static final String EVENT_INVITATION_DETAIL="open_invitation_detail";
    public static final String EVENT_DISCOVER_LABEL="open_discover_label";

    //登录-退出
    public static final String EVENT_LOGIN_SUCCESS="login_success";
    public static final String EVENT_LOGOUT_SUCCESS="logout_success";

    //备用
    //显示和隐藏底部tab
    public static final String EVENT_SHOW_BOTTOM="show_bottom";
    public static final String EVENT_HIDDEN_BOTTOM="hidden_bottom";
    //显示和隐藏底部tab和顶部导航
    public static final String EVENT_SHOW_BOTTOM_AND_TOP="show_bottom_and_top";
    public static final String EVENT_HIDDEN_BOTTOM_AND_TOP="hidden_bottom_and_top";
    //显示和隐藏顶部导航
    public static final String EVENT_SHOW_TOP="show_top";
    public static final String EVENT_HIDDEN_TOP="hidden_top";
    //保存用户信息
    public static final String EVENT_SAVE_USERINFO="save_userinfo";
    //用户退出
    public static final String EVENT_USER_EXIT="user_exit";
    //用户登录
    public static final String EVENT_USER_LOGIN="user_login";
    //消息数量
    public static final String EVENT_MESSAGE_COUNT="message_count";


    public static final String EVENT_WEBVIEW_NEW_PAGE="open_webview_new_page";
    public static final String EVENT_ATTENTION_RELODAD="attention_reload";
    public static final String EVENT_SELECT_IMAGE5="select_image5";
    public static final String EVENT_SELECT_IMAGE6="select_image6";

}
