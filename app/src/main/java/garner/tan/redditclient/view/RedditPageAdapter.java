package garner.tan.redditclient.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import garner.tan.redditclient.R;
import garner.tan.redditclient.RedditViewModel;
import garner.tan.redditclient.databinding.RedditItemBinding;
import garner.tan.redditclient.model.RedditPost;

/**
 * @author Garner Jervis Tan
 */

public class RedditPageAdapter extends RecyclerView.Adapter<RedditPageAdapter.ViewHolder> {

    private RedditViewModel viewModel;

    private Context context;

    private static final String TAG = RedditPageAdapter.class.getSimpleName();

    public RedditPageAdapter(Context context, RedditViewModel viewModel) {
        this.context = context;
        this.viewModel = viewModel;
        Log.i(TAG, "created with viewModel count " + viewModel.getPostCount());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflator = LayoutInflater.from(parent.getContext());
        RedditItemBinding binding = DataBindingUtil.inflate(inflator, R.layout.reddit_item, parent, false);

        ViewHolder vh = new ViewHolder(binding);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RedditPost post = viewModel.getPost(position);
        Glide.with(holder.itemView)
                .load(post.getThumbnail())
                .into(holder.binding.imageView);
        holder.binding.setRedditPost(post);
        holder.binding.setHandler(this);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(post);
            }
        });
    }

    @Override
    public int getItemCount() {
        return viewModel.getPostCount();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public RedditItemBinding binding;
        public ViewHolder(RedditItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

    public void onItemClick(RedditPost post) {
        Log.i(TAG, "Item clicked, post id " + post.getId());
        viewModel.getActiveArticle().setPost(post);
//        RedditArticle article = viewModel.getArticle(post.getId());
        Intent intent = new Intent(context, RedditArticleActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(RedditArticleActivity.EXTRA_ARTICLE_ID, post.getId());
        context.startActivity(intent);

    }
}
