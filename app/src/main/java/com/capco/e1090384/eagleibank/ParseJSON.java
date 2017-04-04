package com.capco.e1090384.eagleibank;



        import android.util.Log;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

/**
 * Created by Belal on 9/22/2015.
 */
public class ParseJSON {
    public static String[] ids;
    public static String[] custrefs;
    public static String[] trandates;
    public static String[] tranamounts;

    public static final String JSON_ARRAY = "result";
    public static final String KEY_ID = "id";
    public static final String KEY_CUSTREF = "custref";
    public static final String KEY_TRANDATE = "trandate";
    public static final String KEY_TRANAMOUNT = "tranamount";

    static String TAG = "ParseJSON";

    private JSONArray users = null;

    private String json;

    public ParseJSON(String json){
        this.json = json;
    }

    protected void parseJSON(){
        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(json);
            users = jsonObject.getJSONArray(JSON_ARRAY);
            ids = new String[users.length()];
            custrefs = new String[users.length()];
            trandates = new String[users.length()];
            tranamounts = new String[users.length()];

            for(int i=0;i<users.length();i++){
                JSONObject jo = users.getJSONObject(i);
                ids[i] = jo.getString(KEY_ID);
                custrefs[i] = jo.getString(KEY_CUSTREF);
                trandates[i] = jo.getString(KEY_TRANDATE);
                tranamounts[i] = jo.getString(KEY_TRANAMOUNT);          }
            Log.i(TAG, "  = " + tranamounts);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}