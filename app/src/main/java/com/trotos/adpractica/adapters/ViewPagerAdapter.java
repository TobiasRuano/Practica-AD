package com.trotos.adpractica.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;
import com.trotos.adpractica.models.Image;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    Context context;
    List<Image> images;

    public ViewPagerAdapter(Context c, List<Image> i) {
        this.context = c;
        this.images = i;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        Picasso.get()
                .load(images.get(position).getUrl())
                .fit()
                .centerCrop()
                .into(imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}