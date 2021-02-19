package com.devilzone.blogger.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.devilzone.blogger.R;

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;

    public ViewPagerAdapter(Context context) {
        this.context = context;
    }

    private int images[] = {
            R.drawable.img_1,
            R.drawable.img_2,
            R.drawable.img_3
    };

    private String titles[] ={
            "Learn",
            "Create",
            "Enjoy"
    };

    private String descriptions[] ={
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
    };


    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.view_pager,container,false);

        ImageView imgViewPager = v.findViewById(R.id.imgViewPager);
        TextView title_pager = v.findViewById(R.id.title_pager);
        TextView description = v.findViewById(R.id.descriptionViewPager );

        imgViewPager.setImageResource(images[position]);
        title_pager.setText(titles[position]);
        description.setText(descriptions[position]);

        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
