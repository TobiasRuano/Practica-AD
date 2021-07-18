package com.trotos.adpractica;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.trotos.adpractica.models.Post;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreatePost extends AppCompatActivity {

    Button newPostButton;
    TextView newTitle, newContent;
    Post post;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_post_activity);
        configureUI();
    }

    private void configureUI() {
        newTitle = findViewById(R.id.newTitleText);
        newContent = findViewById(R.id.newContentText);
        newPostButton = findViewById(R.id.newPostButton);

        newPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkFieldsStatus()) {
                    post = new Post();
                    post.setTitle(newTitle.getText().toString());
                    post.setBody(newContent.getText().toString());
                    post.setUserId(1);
                    createPost();
                } else {
                    showAlert("Error", "Debes completar los campos para continuar.");
                }
            }
        });
    }

    private Boolean checkFieldsStatus() {
        return !newTitle.getText().toString().isEmpty() && !newContent.getText().toString().isEmpty();
    }

    private void createPost() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiUtils api = retrofit.create(ApiUtils.class);
        Call<Post> call = api.createPost(post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(response.isSuccessful()) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Post Created!", Toast.LENGTH_LONG);
                    toast.show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                showAlert("Upss", "Parece que hubo un error, intenta mas tarde.");
            }
        });
    }

    private void showAlert(String titulo, String mensaje) {
        new AlertDialog.Builder(CreatePost.this)
                .setTitle(titulo)
                .setMessage(mensaje)
                .setPositiveButton("Aceptar", null)
                .show();
    }
}
