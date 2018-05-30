package com.liwenpeng.customview;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.vp)
    ViewPager viewPager;
    @BindView(R.id.ll_container)
    LinearLayout llContainer;
    @BindView(R.id.tv_des)
    TextView tvDes;
    @BindView(R.id.ll_neirong)
    LinearLayout llNeirong;
    private int[] imgResIds;
    private String[] texts;
    private ArrayList<ImageView> imageViewArrayList = new ArrayList<>();
    private int pos = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        imgResIds = new int[]{R.mipmap.a, R.mipmap.b, R.mipmap.c, R.mipmap.d, R.mipmap.e};
        texts = new String[]{
                "界面1",
                "界面2",
                "界面3",
                "界面4",
                "界面5"
        };
        ImageView imageView;
        View view;
        for (int i = 0; i < imgResIds.length; i++) {
            imageView = new ImageView(this);
            imageView.setBackgroundResource(imgResIds[i]);
            imageViewArrayList.add(imageView);
            view = new View(this);
            view.setBackgroundResource(R.drawable.view_selector);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(10,10);
            layoutParams.leftMargin=5;
            view.setEnabled(false);
            llContainer.addView(view,layoutParams);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        llContainer.getChildAt(0).setEnabled(true);
        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.setOnPageChangeListener(new MyOnpagerChangedListener());
        Timer timer = new Timer();
        timer.schedule(timerTask,2000,2000);


    }

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageViewArrayList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, final int position) {
            ImageView imageView = imageViewArrayList.get(position);
            container.addView(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this,"点击了我:"+position,Toast.LENGTH_SHORT).show();
                }
            });
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

    class MyOnpagerChangedListener implements ViewPager.OnPageChangeListener{


        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            llContainer.getChildAt(pos).setEnabled(false);
            tvDes.setText(texts[position]);
            llContainer.getChildAt(position).setEnabled(true);
            pos=position;

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private TimerTask timerTask =new TimerTask() {
        @Override
        public void run() {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("lwp","runnable:viewPager.getCurrentItem():"+viewPager.getCurrentItem());
                    if (viewPager.getCurrentItem()+1 == imageViewArrayList.size()){
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        viewPager.setCurrentItem(0);

                    }else {
                        viewPager.setCurrentItem(viewPager.getCurrentItem()+1);

                    }
                }
            });
        }
    };
}
