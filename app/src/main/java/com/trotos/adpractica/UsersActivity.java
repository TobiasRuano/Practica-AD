package com.trotos.adpractica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.trotos.adpractica.adapters.UserAdapter;
import com.trotos.adpractica.models.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UsersActivity extends AppCompatActivity {

    RecyclerView reciclerView;
    UserAdapter adapter;
    List<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        configureUI();
        getUsersData();
    }

    private void configureUI() {
        reciclerView = findViewById(R.id.reciclerViewUsers);
        reciclerView.setHasFixedSize(true);
        reciclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new UserAdapter(this, users);
        reciclerView.setAdapter(adapter);
    }

    private void getUsersData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiUtils api = retrofit.create(ApiUtils.class);
        Call<List<User>> call = api.getUsers();

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful()) {
                    users.clear();
                    users.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
    }
}