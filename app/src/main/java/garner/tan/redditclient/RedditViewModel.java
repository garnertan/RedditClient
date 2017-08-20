package garner.tan.redditclient;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import garner.tan.redditclient.dataaccess.RedditClient;
import garner.tan.redditclient.model.RedditArticle;
import garner.tan.redditclient.model.RedditPage;
import garner.tan.redditclient.model.RedditPost;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Garner Jervis Tan
 */

public class RedditViewModel extends AndroidViewModel {

    private static final String TAG = RedditViewModel.class.getSimpleName();

    private RedditClient client;

    private String lastPageId = null;

    private List<RedditPost> redditPosts;

    private RedditArticle activeArticle;

    public RedditViewModel(Application application) {
        super(application);
        RedditApplication app = (RedditApplication) application;
        client = app.getRedditClient();
        redditPosts = new ArrayList<>();
        activeArticle = new RedditArticle();
        Log.i(TAG, "created");
    }

    public void loadPosts() {
        RedditPage page = client.getPage(lastPageId);
        io.reactivex.Flowable.fromIterable(page.getPosts())
                .filter((post) -> post.isOver_18() == false)
                .subscribe(redditPost -> redditPosts.add(redditPost));

        lastPageId = page.getPageId();
    }

    public void clearPosts() {
        redditPosts.clear();
        lastPageId = null;
    }

    public RedditPost getPost(int index) {
        return redditPosts.get(index);
    }

    public int getPostCount() {
        return redditPosts.size();
    }

    // load article in background
    public void loadArticle(String id) {
        Single.fromCallable(() -> client.getArticle(id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<RedditArticle>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull RedditArticle redditArticle) {
                        Log.i(TAG, "retrieved article with id " + id);
                        activeArticle = redditArticle;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "error in loading article with id " + id);
                    }
                });
    }

    public RedditArticle getActiveArticle() {
        return activeArticle;
    }

    public void setActiveArticle(RedditArticle activeArticle) {
        this.activeArticle = activeArticle;
    }

    public RedditArticle getArticle(String id) {
        return client.getArticle(id);
    }
}
