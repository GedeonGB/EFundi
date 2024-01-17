package com.example.android.e_fundi.Lists;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.e_fundi.BaseActivity;
import com.example.android.e_fundi.R;
import com.example.android.e_fundi.Services.fedbackActivity;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by gb on 10/23/18.
 */

public class Recycler_Item extends RecyclerView.Adapter<Recycler_Item.MyViewHolderItem> {


    private Context aContext;

    private final int SPLASH_TIME_OUT = 900;
    private List<items> aData;


    AlertDialog.Builder alert;
    Animation zoomOut;

    public Recycler_Item(Context aContext, List<items> aData) {
        this.aContext = aContext;
        this.aData = aData;
    }


    @Override

    public Recycler_Item.MyViewHolderItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(aContext);
        view = inflater.inflate(R.layout.card_home_page, parent, false);

        alert = new AlertDialog.Builder(aContext);
        zoomOut = AnimationUtils.loadAnimation(aContext, R.anim.zoom_out);

        return new Recycler_Item.MyViewHolderItem(view);
    }


    @Override

    public void onBindViewHolder(final Recycler_Item.MyViewHolderItem holder, final int position) {

        final String itemNameStr = aData.get(position).getItem_name();
        final String itemDateStr = aData.get(position).getItem_date();
        final String itemShopSTr = aData.get(position).getItem_shop();
        final int imgInt = aData.get(position).getItem_image();
        holder.datetxt.setText(itemDateStr);
        holder.nametxt.setText(itemNameStr);
        holder.shoptxt.setText(itemShopSTr);
        Picasso.with(aContext).load(imgInt).into(holder.imageItem);


        holder.btnViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.rel_item_progress.setVisibility(View.VISIBLE);

//                KProgressHUD.create(aContext)
//                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
//                        .setLabel("Please wait")
//                        .setDetailsLabel("loading details for "+itemNameStr)
//                        .setCancellable(true)
//                        .setAnimationSpeed(2)
//                        .setDimAmount(0.5f)
//                        .show();

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        Intent i = new Intent(aContext, itemDescription.class);
                        i.putExtra("itemName",itemNameStr);
                        i.putExtra("itemDate",itemDateStr);
                        i.putExtra("itemShop",itemShopSTr);
                        i.putExtra("itemImg",imgInt);
                        aContext.startActivity(i);
                    }
                }, SPLASH_TIME_OUT);


            }
        });

        holder.cardImtem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(aContext, "Clicked", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(aContext, itemDescription.class);
                i.putExtra("itemName",itemNameStr);
                i.putExtra("itemDate",itemDateStr);
                i.putExtra("itemShop",itemShopSTr);
                i.putExtra("itemImg",imgInt);
                aContext.startActivity(i);

            }
        });



    }

    @Override

    public int getItemCount() {
        return aData.size();
    }

    public static class MyViewHolderItem extends RecyclerView.ViewHolder {

        TextView datetxt;
        TextView nametxt;
        TextView shoptxt;
        ImageView imageItem;
        ProgressBar rel_item_progress;
        //CardView cardView;
        Button btnViewMore;
        CardView cardImtem;


        public MyViewHolderItem(View itemView) {
            super(itemView);

            datetxt = (TextView) itemView.findViewById(R.id.ItemDate);
            btnViewMore = (Button) itemView.findViewById(R.id.btnViewMore);
            rel_item_progress = (ProgressBar) itemView.findViewById(R.id.rel_item_progress);
            nametxt = (TextView) itemView.findViewById(R.id.itemName);
            shoptxt = (TextView) itemView.findViewById(R.id.itemShop);
            imageItem = (ImageView) itemView.findViewById(R.id.ItemImage);
            cardImtem = (CardView) itemView.findViewById(R.id.cardItem);

        }
    }
}



