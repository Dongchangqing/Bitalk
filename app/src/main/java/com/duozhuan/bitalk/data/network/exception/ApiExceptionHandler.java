package com.duozhuan.bitalk.data.network.exception;

import android.content.Context;

import com.duozhuan.bitalk.data.network.okhttputils.NetworkToast;
import retrofit2.HttpException;

/**
 * 网络请求异常处理类
 *
 * Created by hanliontien on 2017/11/7.
 */
public class ApiExceptionHandler {

    public static boolean handlerApiException(Context context, Throwable e) {
        e.printStackTrace();

        if (e instanceof HttpException){
            NetworkToast.netWorkError(context).show();
            return true;
        }else{
            NetworkToast.otherError(context, e.getMessage()).show();
        }

//        if (e instanceof ApiException){
//            ApiException apiException = (ApiException) e;
//            Toast.makeText(context, apiException.message, Toast.LENGTH_LONG).show();
//            switch (apiException.errorCode) {
//                case 400://由于明显的客户端错误（例如，格式错误的请求语法），服务器不能或不会处理该请求。
//                    return true;
//                case 401://未认证 或 认证失败
//                    return true;
//                case 403: //没有权限
//                    return true;
//                case 404: //资源或内容不存在
//                    return true;
//                case 405: //请求的方法不允许（需要POST结果使用GET）
//                    return true;
//                case 408: //请求超时
//                    return true;
//                case 409: //请求冲突（内容有冲突）
//                    return true;
//                case 410: //资源已删除
//                    return true;
//                case 413: //提交内容过大
//                    return true;
//                case 423: //资源被锁定
//                    return true;
//                case 429: //请求过多
//                    return true;
//                case 500: //服务器遇到了一个未曾预料的状况，导致了它无法完成对请求的处理。没有给出具体错误信息。
//                    return true;
//                case 501: //服务器不支持当前请求所需要的某个功能。
//                    return true;
//                case 502: //作为网关或者代理工作的服务器尝试执行请求时，从上游服务器接收到无效的响应。
//                    return true;
//                case 503: //由于临时的服务器维护或者过载，服务器当前无法处理请求。这个状况是暂时的。
//                    return true;
//                case 504: //作为网关或者代理工作的服务器尝试执行请求时，未能及时从第三方服务器收到响应。
//                    return true;
//                case 510: //获取资源所需要的策略并没有被满足。
//                    return true;
//
//                default:
//                    return false;
//            }
//        }

        return false;
    }
}
