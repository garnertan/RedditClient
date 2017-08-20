package garner.tan.redditclient.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Garner Jervis Tan
 */

public class RedditArticle {

    private RedditPost post;

    private List<RedditComment> comments;

    public RedditArticle() {
        comments = new ArrayList<>();
    }

    public RedditPost getPost() {
        return post;
    }

    public void setPost(RedditPost post) {
        this.post = post;
    }

    public List<RedditComment> getComments() {
        return comments;
    }

    public void setComments(List<? extends RedditData> comments) {
        if(comments.size() == 0) {
            // do nothing
            return;
        }
        // possible dangerous casting here
        if (comments.size() > 0 && comments.get(0) instanceof RedditComment) {
            this.comments.addAll((Collection<? extends RedditComment>) comments);
        } else {
            throw new IllegalArgumentException("Adding of a non comment to a comment list");
        }
    }
}
