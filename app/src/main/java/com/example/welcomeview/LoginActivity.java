package com.example.welcomeview;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.RelativeLayout;

public class LoginActivity extends AppCompatActivity {
    private Go_button button;
    private RelativeLayout relativeLayout;
    private Handler handler;
    private Animator animator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        button=findViewById(R.id.sign_in_btn);
        relativeLayout=findViewById(R.id.mostrela
        );
        relativeLayout.getBackground().setAlpha(0);
        handler=new Handler();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.startAnim();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        gotoNew();
                    }
                },3000);
            }
        });
    }
    private void gotoNew()
    {
        button.gotoNew();
        final Intent intent=new Intent(this,MainActivity.class);
        int xc=(button.getLeft()+button.getRight())/2;
        int yc=(button.getTop()+button.getBottom())/2;
        animator= ViewAnimationUtils.createCircularReveal(relativeLayout,xc,yc,0,1111);
        animator.setDuration(300);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(intent);
                        overridePendingTransition(R.anim.anim_in,R.anim.anim_out);
                    }
                },200);
            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();
        relativeLayout.getBackground().setAlpha(255);
    }
    protected void onStop()
    {
        super.onStop();
        animator.cancel();
        relativeLayout.getBackground().setAlpha(0);
        button.regainBackground();
    }
}
