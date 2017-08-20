package garner.tan.redditclient.view;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import garner.tan.redditclient.R;
import garner.tan.redditclient.RedditViewModel;
import garner.tan.redditclient.databinding.ActivityArticleBinding;
import garner.tan.redditclient.model.RedditArticle;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Garner Jervis Tan
 */

public class RedditArticleActivity extends AppCompatActivity {

    private static final String TAG = RedditArticleActivity.class.getSimpleName();

    public static final String EXTRA_ARTICLE_ID = "articleId";

    private RedditViewModel viewModel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "RedditArticleActivity onCreate");

        ActivityArticleBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_article);

        viewModel = ViewModelProviders.of(this).get(RedditViewModel.class);

        binding.setRedditArticle(viewModel.getActiveArticle());

        RedditCommentAdapter adapter = new RedditCommentAdapter(viewModel);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.article_recycler);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,
                false));

        String articleId = getIntent().getStringExtra(EXTRA_ARTICLE_ID);

        Single.fromCallable(() -> viewModel.getArticle(articleId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<RedditArticle>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull RedditArticle redditArticle) {
                        Log.i(TAG, "Finished loading article " + articleId);
                        viewModel.setActiveArticle(redditArticle);
                        adapter.notifyDataSetChanged();
                        binding.setRedditArticle(redditArticle);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(TAG, "Error in loading article " + articleId);
                    }
                });



    }
}
