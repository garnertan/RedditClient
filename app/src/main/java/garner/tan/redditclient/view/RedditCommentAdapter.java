package garner.tan.redditclient.view;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import garner.tan.redditclient.R;
import garner.tan.redditclient.RedditViewModel;
import garner.tan.redditclient.databinding.RedditCommentBinding;
import garner.tan.redditclient.model.RedditComment;

/**
 * @author Garner Jervis Tan
 */

public class RedditCommentAdapter extends RecyclerView.Adapter<RedditCommentAdapter.ViewHolder> {

    private RedditViewModel viewModel;

    public RedditCommentAdapter(RedditViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RedditCommentBinding binding = DataBindingUtil.inflate(inflater, R.layout.reddit_comment, parent, false);
        ViewHolder viewHolder = new ViewHolder(binding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RedditCommentBinding binding = holder.binding;
        // this won't work if it is a nested comment thread, should probably get it via ID
        RedditComment comment = viewModel.getActiveArticle().getComments().get(position);
        binding.setRedditComment(comment);
    }

    @Override
    public int getItemCount() {
        return viewModel.getActiveArticle().getComments().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        RedditCommentBinding binding;

        public ViewHolder(RedditCommentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
