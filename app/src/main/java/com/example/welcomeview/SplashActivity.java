package com.example.welcomeview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;
/*
* 创建者：hachi
* 创建时间2019-9-25
* 类的描述：向导界面ViewPager配合Adpter来滑动处理，点跟随滑动
* */

public class SplashActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager Myviewpager_Guide;/*视图翻页工具，提供多个页面切换的功能*/
    private View Guide_RedPoints;/*下方红点*/
    private LinearLayout Load_GuidePoints;/*页面加载点*/
    private Button goto_btn;/*跳过按钮*/

    private int disPoints;/*点与点之间的间距*/
    private int currentItem;/*当前页码*/

    private ViewPagerAdpeter viewPagerAdpeter;
    private List<ImageView> guids;/*ViewPager适配器*/
    private int[] Setimage=new int[]{R.mipmap.icon_1, R.mipmap.icon_2, R.mipmap.icon_3, R.mipmap.icon_4, R.mipmap.icon_5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        UIUtils.init(getApplicationContext());
        init_GetResource();
        init_GetData();
        initViewListen();
        goto_btn.setOnClickListener(this);
    }
    private void init_GetResource()
    {
        Myviewpager_Guide=findViewById(R.id.MyViewPager);
        Guide_RedPoints=findViewById(R.id.Guide_RedPoints);
        Load_GuidePoints=findViewById(R.id.Guide_Points);
        goto_btn=findViewById(R.id.goto_btn);
    }
    private void init_GetData()
    {
        guids=new ArrayList<ImageView>();/*定义ViewPager适配器，实例化适配器*/
        for(int i=0;i<Setimage.length;i++)/*\创建ViewPager的适配器*/
        {
            ImageView imageView_temp=new ImageView(getApplicationContext());
            imageView_temp.setBackgroundResource(Setimage[i]);

            guids.add(imageView_temp);/*设置适配图片,添加图片数据*/

            View viewpoints=new View(getApplicationContext());/*获取点，灰色的点在LinearLayout中绘制*/
            viewpoints.setBackgroundResource(R.drawable.point_simple);/*drawable里面创建的灰色点xml文件*/
            int dip=10;//设置灰色点的大小
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(UIUtils.dp_to_px(dip),UIUtils.dp_to_px(dip));
            //设置点与点之间的间距，第一个不用
            if(i!=0){
                params.leftMargin=47;}
            viewpoints.setLayoutParams(params);
            Load_GuidePoints.addView(viewpoints);
        }
        viewPagerAdpeter=new ViewPagerAdpeter(getApplicationContext(),guids);
        Myviewpager_Guide.setAdapter(viewPagerAdpeter);
    }
    private void initViewListen()//监听界面绘制的完成
    {
        Guide_RedPoints.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Guide_RedPoints.getViewTreeObserver().removeGlobalOnLayoutListener(this);//取消注册界面而产生的回调接口
                disPoints=(Load_GuidePoints.getChildAt(1).getLeft()-Load_GuidePoints.getChildAt(0).getLeft());//计算点与点1之间的间距
            }
        });
        Myviewpager_Guide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            /*
             * 页面滑动调用，拿到滑动距离设置视图的滑动状态
             * position当前页面位置
             * positionOffset移动的比例值
             * positionOffsetPixels当前页面滑动像素值
             * */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                float leftMargin=disPoints*(position+positionOffset);
                RelativeLayout.LayoutParams layoutParams=(RelativeLayout.LayoutParams) Guide_RedPoints.getLayoutParams();
                layoutParams.leftMargin=Math.round(leftMargin);
                Guide_RedPoints.setLayoutParams(layoutParams);
            }

            @Override
            public void onPageSelected(int position) {

                if(position==guids.size()-1)
                {

                }
                currentItem=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        Myviewpager_Guide.setOnTouchListener(new View.OnTouchListener() {
            float startX;
            float endX;
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        startX=motionEvent.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        endX=motionEvent.getX();
                        WindowManager windowManager= (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
                        Point size=new Point();
                        windowManager.getDefaultDisplay().getSize(size);
                        int width=size.x;
                        /*首先确定的是，是否到了最后一页，然后判断是否向左滑动，并且滑动距离是否符合，我在这里判断的距离是屏幕宽度的四分之一
                        * */
                        if(currentItem==(guids.size()-1)&&startX-endX>=(width/4))
                        {
                            Intent intent=new Intent(SplashActivity.this,LoginActivity.class);
                            startActivity(intent);
                            //部分是切换Activity时的动画，看起来就不会很僵硬
                            overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);//滑入滑出
                            finish();
                        }
                        break;
                }
                return false;
            }
        });
    }
/*
* 跳过按钮的点击事件
* */
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(SplashActivity.this,LoginActivity.class);
        startActivity(intent);
        //部分是切换Activity时的动画，看起来就不会很僵硬
        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);//滑入滑出
        finish();
    }
}
