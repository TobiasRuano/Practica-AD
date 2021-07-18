package com.trotos.adpractica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.trotos.adpractica.adapters.CommentAdapter;
import com.trotos.adpractica.models.Comment;
import com.trotos.adpractica.models.Post;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostActivity extends AppCompatActivity {

    TextView title, body;
    RecyclerView commentsReciclerView;
    CommentAdapter adapter;

    Post post;
    List<Comment> comments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        configureUI();
        getDataFromActivity();
        getCommentsForPost();
    }

    private void configureUI() {
        setContentView(R.layout.activity_post);

        title = findViewById(R.id.postTitle);
        body = findViewById(R.id.postContent);

        commentsReciclerView = findViewById(R.id.commentsReciclerView);
        commentsReciclerView.setHasFixedSize(true);
        commentsReciclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new CommentAdapter(this, comments);
        commentsReciclerView.setAdapter(adapter);
    }

    private void getDataFromActivity() {
        post = (Post) getIntent().getSerializableExtra("post");
        title.setText(post.getTitle());
        body.setText(post.getBody());
    }

    private void getCommentsForPost() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiUtils api = retrofit.create(ApiUtils.class);
        Call<List<Comment>> call = api.getComments(post.getId());

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if(response.isSuccessful()) {
                    comments.clear();
                    comments.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    System.out.println(call.request().toString());
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }
}