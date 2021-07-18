package com.trotos.adpractica;

import com.trotos.adpractica.models.Album;
import com.trotos.adpractica.models.Comment;
import com.trotos.adpractica.models.Image;
import com.trotos.adpractica.models.Post;
import com.trotos.adpractica.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiUtils {

    @GET("posts")
    Call<List<Post>> getPosts();

    @HTTP(method = "POST", path = "posts", hasBody = true)
    Call<Post> createPost(@Body Post post);

    @HTTP(method = "DELETE", path = "posts/{id}", hasBody = true)
    Call<Post> deletePost(@Path("id") String id);

    @HTTP(method = "GET", path = "users", hasBody = false)
    Call<List<User>> getUsers();

    //@HTTP(method = "GET", path = "posts/{id}/comments")
    //Call<List<Comment>> getComments(@Path("id") int id);

    @HTTP(method = "GET", path = "comments")
    Call<List<Comment>> getComments(@Query("postId") int id);

    @HTTP(method = "GET", path = "albums", hasBody = false)
    Call<List<Album>> getAlbums();

    @HTTP(method = "GET", path = "albums/{id}/photos", hasBody = false)
    Call<List<Image>> getImages(@Path("id") int id);

}
