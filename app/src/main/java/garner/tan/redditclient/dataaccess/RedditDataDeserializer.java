package garner.tan.redditclient.dataaccess;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import garner.tan.redditclient.model.RedditComment;
import garner.tan.redditclient.model.RedditData;
import garner.tan.redditclient.model.RedditMore;
import garner.tan.redditclient.model.RedditPost;
import garner.tan.redditclient.model.RedditType;

/**
 * Created by Jervis on 8/20/2017.
 */

public class RedditDataDeserializer implements JsonDeserializer<RedditData>{
    @Override
    public RedditData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonObject obj = json.getAsJsonObject();
        JsonObject dataObj = obj.getAsJsonObject("data");

        RedditType type = RedditType.fromTypeCode(obj.get("kind").getAsString());
        switch (type) {
            case POST:
                return context.deserialize(dataObj, RedditPost.class);
            case COMMENT:
                return context.deserialize(dataObj, RedditComment.class);
            case MORE:
                return context.deserialize(dataObj, RedditMore.class);
            case LISTING:
                return context.deserialize(dataObj, RedditData.class);
            default:
                throw new JsonParseException("Cannot find reddit type");
        }
    }
}
