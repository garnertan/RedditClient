package garner.tan.redditclient.dataaccess;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import garner.tan.redditclient.model.RedditArticle;
import garner.tan.redditclient.model.RedditComment;
import garner.tan.redditclient.model.RedditData;
import garner.tan.redditclient.model.RedditPost;

/**
 * Created by Jervis on 8/20/2017.
 */

public class RedditArticleDeserializer implements JsonDeserializer<RedditArticle>{
    @Override
    public RedditArticle deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        RedditPost post;

        JsonArray jsonArray = json.getAsJsonArray();

        // get post
        JsonObject obj = jsonArray.get(0).getAsJsonObject();
        JsonObject dataJson = obj.getAsJsonObject("data").getAsJsonArray("children").get(0).getAsJsonObject()
                .getAsJsonObject("data");
        post = context.deserialize(dataJson, RedditPost.class);

        // get comments
        JsonArray childrenArray = jsonArray.get(1).getAsJsonObject().
                getAsJsonObject("data").getAsJsonArray("children");

        List<RedditComment> comments = new ArrayList<>();
        for (JsonElement arrElem : childrenArray) {
            RedditData data = context.deserialize(arrElem, RedditData.class);
            // only get top level comments for now
            if(data instanceof RedditComment) {
                comments.add((RedditComment) data);
            }
            // instances of RedditMore can also be returned
        }

        RedditArticle article = new RedditArticle();
        article.setPost(post);
        article.setComments(comments);
        return article;
    }

}
