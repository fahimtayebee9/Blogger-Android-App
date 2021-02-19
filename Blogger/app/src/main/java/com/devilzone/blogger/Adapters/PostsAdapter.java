package com.devilzone.blogger.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devilzone.blogger.Models.Post;
import com.devilzone.blogger.R;
import com.devilzone.blogger.RestApiConstant.Constant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostsHolder>{

    private Context context;
    private ArrayList<Post> post_list;

    public PostsAdapter(Context context, ArrayList<Post> post_list){
        this.context = context;
        this.post_list = post_list;
    }

    @NonNull
    @Override
    public PostsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_posts, parent,false);
        return new PostsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsHolder holder, int position) {
        Post post = post_list.get(position);
        Picasso.get().load(Constant.URL+"storage/user/"+post.getUser().getImage()).into(holder.imgAuthor);
        Picasso.get().load(Constant.URL+"storage/posts/"+post.getImage()).into(holder.img_postImage);
        holder.txtAuthorName.setText(post.getUser().getName());
        holder.txt_postDate.setText(post.getDate());
        holder.txt_postDesc.setText(post.getDesc().substring(0,30));
        holder.txt_likes.setText(String.valueOf(post.getLikes()));
        holder.txt_comments.setText(String.valueOf(post.getComments()));
    }

    @Override
    public int getItemCount() {
        return post_list.size();
    }


    class PostsHolder extends RecyclerView.ViewHolder{
        private TextView txtAuthorName, txt_postDate, txt_postDesc, txt_likes, txt_comments;
        private CircleImageView imgAuthor;
        private ImageView img_postImage;
        private ImageView btn_postOption, btn_postLikes, btn_postComment;

        public PostsHolder(@NonNull View itemView) {
            super(itemView);

            txtAuthorName = itemView.findViewById(R.id.txt_postAuthorName);
            txt_postDate = itemView.findViewById(R.id.txt_postDate);
            txt_postDesc = itemView.findViewById(R.id.txt_postDesc);
            txt_likes = itemView.findViewById(R.id.txt_postLikes);
            txt_comments = itemView.findViewById(R.id.txt_postComments);
            imgAuthor = itemView.findViewById(R.id.imgPostProfile);
            img_postImage = itemView.findViewById(R.id.img_postImage);
            btn_postOption = itemView.findViewById(R.id.btn_postOption);
            btn_postLikes = itemView.findViewById(R.id.btn_postLikes);
            btn_postComment = itemView.findViewById(R.id.btn_postComment);
        }

    }
}
