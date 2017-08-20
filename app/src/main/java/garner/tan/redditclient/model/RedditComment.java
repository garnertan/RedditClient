package garner.tan.redditclient.model;

/**
 * @author Garner Jervis Tan
 */

public class RedditComment extends RedditData {

    private String id;

    private String body;

    private Integer score;

    private String author;

    public RedditComment() {
        id = "";
        body = "";
        score = 0;
        author = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
