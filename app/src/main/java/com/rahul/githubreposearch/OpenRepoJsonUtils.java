package com.rahul.githubreposearch;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

/**
 * Created by rahulranjansinha on 19-01-2017.
 */

public final class OpenRepoJsonUtils {
    public static String[] getOwnerName(Context context,String Jsondata) throws JSONException
    {
        final String GIT_ITEMS="items";
        final String GIT_OWNER="owner";
        final String GIT_DESCRIPTION="description";
        final String GIT_LOGIN="login";
        final String OWM_MESSAGE_CODE="cod";

        String[] parsedGithubData =null;

        JSONObject githubJSON=new JSONObject(Jsondata);

        if (githubJSON.has(OWM_MESSAGE_CODE)) {
            int errorCode = githubJSON.getInt(OWM_MESSAGE_CODE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    /* Location invalid */
                    return null;
                default:
                    /* Server probably down */
                    return null;
            }
        }
        JSONArray itemList=githubJSON.getJSONArray(GIT_ITEMS);
        parsedGithubData = new String[itemList.length()];


        for (int i = 0; i < itemList.length(); i++) {

            /* These are the values that will be collected */
            String description;

           JSONObject gitobject = itemList.getJSONObject(i);

            String loginName = gitobject.getJSONObject(GIT_OWNER).getString(GIT_LOGIN);
            String repoDescription = gitobject.getString(GIT_DESCRIPTION);

            parsedGithubData[i] = "\nOWNER: "+loginName + " - " +"DESCRIPTION: "+ repoDescription +"\n";
        }

        return parsedGithubData;
    }
    public static String convert1dToString(String[] parsedGithubData)
    {
        String result=null;
        for(int i=0;i<parsedGithubData.length;i++)
            result=result+parsedGithubData[i];
        return result;
    }

}
