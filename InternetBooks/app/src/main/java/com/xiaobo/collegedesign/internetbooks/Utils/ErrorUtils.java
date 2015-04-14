package com.xiaobo.collegedesign.internetbooks.Utils;

/**
 * Created by chen on 14-9-29.
 */
public class ErrorUtils {
    public static void setError(int code){
        switch (code) {
            //第一次登陆 不报错
            case 416:
                break;
            case 401:
//                ToastUtils.setToast(R.string.toast_overdue_login);
                break;
            case 404:
//                ToastUtils.setToast(R.string.toast_not_have_message);
                break;
            case 402:
//                ToastUtils.setToast(R.string.toast_is_have_name);
                break;
            case 410:
//                ToastUtils.setToast(R.string.toast_is_collect);
                break;
            case 414:
//                ToastUtils.setToast(R.string.toast_is_foucs);
                break;
            case 2:
//                ToastUtils.setToast(R.string.toast_network_failed);
                break;
            case 500:
//                ToastUtils.setToast(R.string.toast_server_failed);
                break;
            case 502:
//                ToastUtils.setToast(R.string.toast_server_repair);
                break;
            case 406:
//                ToastUtils.setToast(R.string.toast_password_ago_dailed);
                break;
                    //在2.2版时移除407

            case 407:
            case 408:
//                ToastUtils.setToast(R.string.toast_password_or_name_error);
                break;
            default:
//                ToastUtils.setToast(ApplicationRunTime.getAppContext().getResources().getString(R.string.toast_unknown_error) + code);
                break;
        }
    }
}
