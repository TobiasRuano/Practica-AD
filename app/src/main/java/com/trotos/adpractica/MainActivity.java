package com.trotos.adpractica;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.trotos.adpractica.adapters.PostAdapter;
import com.trotos.adpractica.models.Post;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    List<Post> posts = new ArrayList<>();
    PostAdapter adapter;

    FloatingActionButton fab;
    FloatingActionButton userButton;
    FloatingActionButton toImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureUI();
        getPosts();
    }

    private void configureUI() {
        RecyclerView reciclerView = findViewById(R.id.reciclerView);
        reciclerView.setHasFixedSize(true);
        reciclerView.setLayoutManager( new LinearLayoutManager(getApplicationContext()));
        adapter = new PostAdapter(this, posts);
        reciclerView.setAdapter(adapter);

        fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toNewPostActivity();
            }
        });
        userButton = findViewById(R.id.userButton);
        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toUsersActivity();
            }
        });

        toImageButton = findViewById(R.id.toImageActivity);
        toImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toImageActivity();
            }
        });
    }

    private void toImageActivity() {
        Intent intent = new Intent(MainActivity.this, AlbumActivity.class);
        startActivity(intent);
    }

    private void toUsersActivity() {
        Intent intent = new Intent(MainActivity.this, UsersActivity.class);
        startActivity(intent);
    }

    private void toNewPostActivity() {
        Intent intent = new Intent(MainActivity.this, CreatePost.class);
        startActivity(intent);
    }

    private void getPosts() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiUtils api = retrofit.create(ApiUtils.class);
        Call<List<Post>> call = api.getPosts();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(response.isSuccessful()) {
                    posts.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }
}