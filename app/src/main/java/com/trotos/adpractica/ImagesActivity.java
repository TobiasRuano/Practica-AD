package com.trotos.adpractica;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.trotos.adpractica.adapters.ViewPagerAdapter;
import com.trotos.adpractica.models.Album;
import com.trotos.adpractica.models.Image;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImagesActivity extends AppCompatActivity {

    List<Image> images = new ArrayList<>();
    Album album;
    ViewPagerAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        album = (Album) getIntent().getSerializableExtra("album");

        configureUI();
        getData();
    }

    private void configureUI() {
        setContentView(R.layout.activity_images);

        ViewPager viewPager = findViewById(R.id.view_pager);
        adapter = new ViewPagerAdapter(this, images);
        viewPager.setAdapter(adapter);
    }

    private void getData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiUtils api = retrofit.create(ApiUtils.class);
        Call<List<Image>> call = api.getImages(album.getId());
        call.enqueue(new Callback<List<Image>>() {
            @Override
            public void onResponse(Call<List<Image>> call, Response<List<Image>> response) {
                if(response.isSuccessful()) {
                    images.clear();
                    images.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Image>> call, Throwable t) {

            }
        });
    }
}
