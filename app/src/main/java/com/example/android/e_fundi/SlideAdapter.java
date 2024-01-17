package com.example.android.e_fundi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by gb on 10/20/18.
 */

public class SlideAdapter extends PagerAdapter {

    Context context;
    LayoutInflater inflater;

    // list of images used in an array

    public int[] lst_image = {R.drawable.imgphone2, R.drawable.imgphone3, R.drawable.imgphone4, R.drawable.imgphone5
    };

    //list of titles
    public String[] lst_titles = {
            "In need of Help",
            "In need of Mechanics",
            "In need of Petrol Station",
            "In need of Spear Shop"
    };

    //LIST OF description

    public String[] lst_desc = {
            "e-fundi provides in a quick way help to all drive with only one click",
            "e-fundi provides in a quick way help to all drive with only one click",
            "e-fundi provides in a quick way help to all drive with only one click",
            "e-fundi provides in a quick way help to all drive with only one click"

    };

    //list of background

    public int[] lst_bcgd = {
            Color.rgb(55, 55, 55),
            Color.rgb(239, 85, 85),
            Color.rgb(110, 49, 89),
            Color.rgb(1, 188, 212)


    };


    public SlideAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        //takes the number of slides
        return lst_titles.length;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slide, container, false);
        RelativeLayout layoutslide = view.findViewById(R.id.slideRelLayout);
        ImageView imgslide = (ImageView) view.findViewById(R.id.slideimg);
        TextView txttitle = (TextView) view.findViewById(R.id.txtTitle);
        TextView txtDesc = (TextView) view.findViewById(R.id.txttitle2);

        //layoutslide.setBackgroundColor(lst_bcgd[position]);
        imgslide.setImageResource(lst_image[position]);
        txttitle.setText(lst_titles[position]);
        txtDesc.setText(lst_desc[position]);
        container.addView(view);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        context.startActivity(new Intent(context, BaseActivity.class));
        container.removeView((RelativeLayout) object);


    }
}

