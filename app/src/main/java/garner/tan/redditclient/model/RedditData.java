package garner.tan.redditclient.model;

/**
 * @author Garner Jervis Tan
 */

public class RedditData {

    private RedditType kind;

    private RedditData data;

    public RedditType getKind() {
        return kind;
    }

    public void setKind(RedditType kind) {
        this.kind = kind;
    }

    public RedditData getData() {
        return data;
    }

    public void setData(RedditData data) {
        this.data = data;
    }
}
