package garner.tan.redditclient.view;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import garner.tan.redditclient.R;
import garner.tan.redditclient.RedditViewModel;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Garner Jervis Tan
 */
public class RedditFrontPageActivity extends AppCompatActivity {

    private static final String TAG = RedditFrontPageActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private RedditViewModel viewModel = null;
    private RedditPageAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        setContentView(R.layout.activity_reddit);

        viewModel = ViewModelProviders.of(this).get(RedditViewModel.class);

        recyclerView = (RecyclerView) findViewById(R.id.redditRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RedditPageAdapter(this.getApplicationContext(), viewModel);
        recyclerView.setAdapter(adapter);

        if(viewModel.getPostCount() == 0) {
            Log.i(TAG, "viewModel post count" + viewModel.getPostCount());
            Completable task = Completable.fromAction(() -> viewModel.loadPosts()).observeOn(AndroidSchedulers.mainThread()).subscribeOn
                    (Schedulers.io());

            task.subscribe(new CompletableObserver() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {
                }

                @Override
                public void onComplete() {
                    Log.i(TAG, "completed");
                    Log.i(TAG, "adapter item count " + adapter.getItemCount());
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    Log.i(TAG, "error on task " + e.getMessage());
                }
            });
        }

        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            Completable completable = Completable.fromAction(() -> {
                viewModel.clearPosts();
                recyclerView.removeAllViews();
                viewModel.loadPosts();
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            completable.subscribe(() -> {
                Log.i(TAG, "refreshing");
                swipeRefreshLayout.setRefreshing(false);
                adapter.notifyDataSetChanged();
            });
        });


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
