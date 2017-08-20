package garner.tan.redditclient.dataaccess;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

import java.lang.reflect.Type;

import garner.tan.redditclient.model.RedditPage;
import garner.tan.redditclient.model.RedditPost;

/**
 * Created by Jervis on 8/16/2017.
 */

public class RedditPageDeserializer implements JsonDeserializer<RedditPage> {
    @Override
    public RedditPage deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        RedditPage page = new RedditPage();

        // get data
        JsonObject obj = json.getAsJsonObject();
        JsonObject topData = obj.getAsJsonObject("data");

        page.setAfter(topData.get("after").getAsString());

        JsonArray children = topData.getAsJsonArray("children");

        for(JsonElement je : children) {
            RedditPost post = parseRedditPost(je.getAsJsonObject(), context);
//            RedditPost post = context.deserialize(je, RedditPost.class);
            page.addPost(post);
        }

        return page;
    }

    private RedditPost parseRedditPost(JsonObject redditPostJson, JsonDeserializationContext context) {
        RedditPost post;
        post = context.deserialize(redditPostJson.get("data"), RedditPost.class);
        return post;
    }
}
