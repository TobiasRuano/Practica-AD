package com.trotos.adpractica.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AlertDialog;

import com.trotos.adpractica.ApiUtils;
import com.trotos.adpractica.PostActivity;
import com.trotos.adpractica.R;
import com.trotos.adpractica.models.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    Context context;
    List<Post> posts;

    public PostAdapter(Context c, List<Post> posts) {
        this.context = c;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.post_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {
        holder.bindData(posts.get(position));

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showActionAlert(holder, position);
                return true;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toPostActivity(posts.get(position));
            }
        });
    }

    private void toPostActivity(Post post) {
        Intent intent = new Intent(context, PostActivity.class);
        intent.putExtra("post", post);
        context.startActivity(intent);
    }

    private void showActionAlert(ViewHolder holder, int position) {
        new AlertDialog.Builder(holder.card.getContext())
                .setTitle("Eliminar")
                .setMessage("Estas seguro que deseas eliminar esta tarjeta?")
                .setPositiveButton("Cancelar", null)
                .setNegativeButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deletePost(posts.get(position));
                    }
                })
                .show();
    }

    private void deletePost(Post post) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiUtils api = retrofit.create(ApiUtils.class);
        Call<Post> call = api.deletePost(String.valueOf(post.getId()));

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                System.out.println(call.request().toString());
                if(response.isSuccessful()) {
                    Toast toast = Toast.makeText(context.getApplicationContext(), "post deleted!", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(context.getApplicationContext(), String.valueOf(response.code()), Toast.LENGTH_LONG);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast toast = Toast.makeText(context.getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, body;
        CardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleText);
            body = itemView.findViewById(R.id.bodyText);
            card = itemView.findViewById(R.id.card);
        }

        public void bindData(Post item) {
            title.setText(item.getTitle());
            body.setText(item.getBody());
        }
    }
}
