package garner.tan.redditclient.dataaccess;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import garner.tan.redditclient.model.RedditArticle;
import garner.tan.redditclient.model.RedditData;
import garner.tan.redditclient.model.RedditPage;
import garner.tan.redditclient.model.RedditPost;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Jervis on 8/15/2017.
 */

public class RedditClient {

    private static final String TAG = RedditClient.class.getSimpleName();
    private OkHttpClient client;
    private Gson gson;


    public RedditClient() {
        gson = new GsonBuilder().registerTypeAdapter(RedditPage.class, new RedditPageDeserializer())
                .registerTypeAdapter(RedditArticle.class, new RedditArticleDeserializer())
                .registerTypeAdapter(RedditData.class, new RedditDataDeserializer()).create();
        client = new OkHttpClient();
    }

    public RedditPage getPage(String after) {
        String params = "";
        RedditPage page = null;
        if (after != null && after.length() > 0) {
            params = "?after=" + after;
        }
        Request request = new Request.Builder().url("http://www.reddit.com/.json" + params).build();
        try {
            Response response = client.newCall(request).execute();
            String res = response.body().string();
            page = gson.fromJson(res, RedditPage.class);
        } catch (IOException e) {
            Log.e(TAG, "error in getPage " + e.getMessage());
        }

        return page;
    }

    public RedditArticle getArticle(String postId) {

        RedditArticle article = null;
        Request request = new Request.Builder().url("http://www.reddit.com/" + postId + "/.json").build();
        try {
            Response response = client.newCall(request).execute();
            String res = response.body().string();
            article = gson.fromJson(res, RedditArticle.class);
        } catch (IOException e) {
            Log.e(TAG, "error in getArticle " + e.getMessage());
        }
        return article;
    }
}
