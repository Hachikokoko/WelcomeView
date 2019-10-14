package com.example.welcomeview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class ViewPagerAdpeter extends PagerAdapter {
    private List<ImageView> mGuids;

    public ViewPagerAdpeter(Context ctx, List<ImageView> guids) {
        this.mGuids = guids;
    }

    @Override
    public int getCount() {
        return mGuids.size();// 返回数据的个数
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;// 过滤和缓存的作用
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);//从viewpager中移除掉
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // container viewpaper
        //获取View
        View child = mGuids.get(position);
        // 添加View
        container.addView(child);
        return child;
    }

}
