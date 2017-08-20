package garner.tan.redditclient.model;

import java.util.List;

/**
 * @author Garner Jervis Tan
 */

public class RedditMore extends RedditData {

    private Integer count;

    private String parent_id;

    private List<String> children;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public List<String> getChildren() {
        return children;
    }

    public void setChildren(List<String> children) {
        this.children = children;
    }
}
