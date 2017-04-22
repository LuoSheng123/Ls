package com.ls.ls.player;

import android.content.Context;

/**
 * 罗升 Luo Sheng
 * 763723720@qq.com
 * 湖南农业大学/物联网工程
 */

public class PixelUtils {
    public static int dip2px(Context context, int dip){
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dip*density+0.5f);
    }
    public static int px2dip(Context context,int px){
        float density = context.getResources().getDisplayMetrics().density;
        return (int)(px/density+0.5f);
    }
}
