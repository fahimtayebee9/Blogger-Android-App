package com.devilzone.blogger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.devilzone.blogger.Adapters.ViewPagerAdapter;


public class OnBoardActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private Button btnPrev, btnNext;
    private com.devilzone.blogger.Adapters.ViewPagerAdapter viewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_board);
        init();
    }

    public void init(){
        viewPager = findViewById(R.id.view_pager);
        btnPrev   = findViewById(R.id.btnPrev);
        btnNext   = findViewById(R.id.btnNext);
        dotsLayout   = findViewById(R.id.dotsLayout);
        viewPagerAdapter = new ViewPagerAdapter(this);
        addDots(0);
        viewPager.addOnPageChangeListener(listner);
        viewPager.setAdapter(viewPagerAdapter);

        btnNext.setOnClickListener(v->{
            if(btnNext.getText().toString().equals("Next")){
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            }
            else{
                startActivity(new Intent(OnBoardActivity.this,AuthActivity.class));
                finish();
            }
        });

        btnPrev.setOnClickListener(v->{
            viewPager.setCurrentItem(viewPager.getCurrentItem()+2);
        });
    }

    private void addDots(int position){
        dotsLayout.removeAllViews();
        dots = new TextView[3];
        for (int i = 0; i < dots.length; i++){
            dots[i] = new TextView(this);

            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.colorLightGrey));
            dotsLayout.addView(dots[i]);
        }

        if(dots.length>0){
            dots[position].setTextColor(getResources().getColor(R.color.colorGrey));
        }
    }

    private ViewPager.OnPageChangeListener listner = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);

            if(position == 0){
                btnPrev.setVisibility(View.VISIBLE);
                btnPrev.setEnabled(true);
                btnNext.setText("Next");
            }
            else if(position == 1){
                btnPrev.setVisibility(View.GONE);
                btnPrev.setEnabled(false);
                btnNext.setText("Next");
            }
            else{
                btnPrev.setVisibility(View.GONE);
                btnPrev.setEnabled(false);
                btnNext.setText("Finish");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}