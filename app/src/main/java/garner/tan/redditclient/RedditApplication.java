package garner.tan.redditclient;

import android.app.Application;
import android.os.StrictMode;

import garner.tan.redditclient.dataaccess.RedditClient;

/**
 * @author Garner Jervis Tan
 */

public class RedditApplication extends Application {

    private RedditClient client;

    @Override
    public void onCreate() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());
        super.onCreate();
        client = new RedditClient();
    }

    public RedditClient getRedditClient() {
        return client;
    }
}
