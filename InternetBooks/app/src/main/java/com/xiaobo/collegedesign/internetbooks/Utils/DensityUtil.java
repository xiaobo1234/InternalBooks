package com.xiaobo.collegedesign.internetbooks.Utils;

import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by xiaobo on 15/1/15.
 */
public class DensityUtil {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static Display getScreenDisplay(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        Display display = wm.getDefaultDisplay();
//        ATLog.e("屏幕信息", "宽=d " + display.getWidth() + " $ " + width + ", 高=d " + display.getHeight() + " $ " + height);

        return display;
    }

    public static int getWindowWidth(Context context){
      return getScreenDisplay(context).getWidth();
    }

    public static int getWindowHeight(Context context){
      return getScreenDisplay(context).getHeight();
    }
}
