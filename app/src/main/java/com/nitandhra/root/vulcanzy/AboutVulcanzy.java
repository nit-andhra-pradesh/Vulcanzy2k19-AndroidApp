package com.nitandhra.root.vulcanzy;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class AboutVulcanzy extends Fragment {
    WebView wb;
    static String state;
    private float[] lastTouchDownXY = new float[2];
    View.OnTouchListener touchListener = new View.OnTouchListener() {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getActionMasked() == 0) {
                AboutVulcanzy.this.lastTouchDownXY[0] = motionEvent.getX();
                AboutVulcanzy.this.lastTouchDownXY[1] = motionEvent.getY();
                float f = AboutVulcanzy.this.lastTouchDownXY[0];
                f = AboutVulcanzy.this.lastTouchDownXY[1];
            }
            return false;
        }
    };
    @Nullable
    @Override
    public View onCreateView ( @NonNull LayoutInflater inflater , @Nullable ViewGroup container , @Nullable Bundle savedInstanceState ) {
        View view=inflater.inflate(R.layout.about_vulcanzy,container,false );
        Typeface myTypeface = Typeface.createFromAsset(view.getContext().getAssets(), "CaviarDreams.ttf");
        Button gov=(Button)view.findViewById(R.id.events);
        wb=(WebView)view.findViewById(R.id.part);
        wb.loadUrl("file:///android_asset/www/index.html");
        wb.getSettings().setJavaScriptEnabled(true);
        wb.setOnTouchListener(this.touchListener);
        AboutVulcanzy.state="open";
        gov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm=getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.frame_container,new HomeFragment()).commit();
                AboutVulcanzy.state="closed";
            }
        });
        return view;
    }

}
