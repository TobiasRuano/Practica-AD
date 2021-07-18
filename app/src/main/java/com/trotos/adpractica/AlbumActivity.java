package com.trotos.adpractica;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trotos.adpractica.adapters.AlbumAdapter;
import com.trotos.adpractica.models.Album;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AlbumActivity extends AppCompatActivity {

    RecyclerView albumReciclerView;
    AlbumAdapter adapter;
    List<Album> albums = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        configureUI();
        getData();
    }

    private void configureUI() {
        setContentView(R.layout.activity_albums);

        albumReciclerView = findViewById(R.id.albumReciclerView);
        albumReciclerView.setHasFixedSize(true);
        albumReciclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new AlbumAdapter(this, albums);
        albumReciclerView.setAdapter(adapter);
    }

    private void getData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiUtils api = retrofit.create(ApiUtils.class);
        Call<List<Album>> call = api.getAlbums();

        call.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                if(response.isSuccessful()) {
                    albums.clear();
                    albums.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    System.out.println(response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }
}
