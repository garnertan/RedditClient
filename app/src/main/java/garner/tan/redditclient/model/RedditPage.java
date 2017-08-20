package garner.tan.redditclient.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Garner Jervis Tan
 */

public class RedditPage extends RedditData {

    private List<RedditPost> posts;

    private String after;

    private String before;

    public RedditPage() {
        posts = new ArrayList<>();
    }

    public void addPost(RedditPost post) {
        posts.add(post);
    }

    public String getPageId () {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public List<RedditPost> getPosts() {
        return posts;
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }
}
