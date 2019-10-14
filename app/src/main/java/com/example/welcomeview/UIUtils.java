package com.example.welcomeview;

import android.content.Context;

public class UIUtils
{
    private static Context mcontext;
    public static void init(Context context)
    {
        mcontext=context;
    }
    /*
    * dp转换为px,px转化为dp
    * */
    public static int dp_to_px(int dp)
    {
        float density=mcontext.getResources().getDisplayMetrics().density;//获取手机系统的屏幕密度
        int px=(int)(dp*density+.5f);
        return px;
    }
    public static int px_to_dp(int px)
    {
        float density=mcontext.getResources().getDisplayMetrics().density;
        int dp=(int)(px/density+.5f);
        return dp;
    }
}
