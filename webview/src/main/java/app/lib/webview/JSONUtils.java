package app.lib.webview;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtils {
    public static boolean isJSON(String json) {
        if (json == null) {
            return false;
        }

        try {
            new JSONObject(json);
        } catch (JSONException e) {
            return false;
        }

        return true;
    }
}
