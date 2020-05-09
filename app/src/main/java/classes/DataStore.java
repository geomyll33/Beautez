package classes;

import android.content.Context;
import android.content.res.Resources;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import gr.teicm.msc.beautez.R;

public class DataStore {

    public static String KEY_POSITION = "POSITION";
    public static String KEY_ID = "ID";
    public static String KEY_TITLE = "TITLE";
    public static String KEY_PRODUCT = "PRODUCT";
    public static String KEY_GENREID = "GENREID";
    public static String KEY_GENRENAME = "GENRENAME";
    public static String KEY_PRODUCTURL = "PRODUCTURL";
    public static String KEY_IMAGEURL = "IMAGEURL";

    public static Context AppContext = null;
    public static Resources AppResources = null;
    public static String[] Genres = null;
    public static ArrayList<HashMap<String, Object>> Cosmetics = new ArrayList<HashMap<String, Object>>();

    public static void Init(Context context){
        AppContext = context;
        AppResources = AppContext.getResources();
        Genres = AppResources.getStringArray(R.array.cosmetics_genre);
    }

    public static void LoadCosmetics(String filterProduct, String filterTitle, int filterGenreId) {
        DataStore.Cosmetics.clear();
        //Read from file in Assets
        String contents = AssetsUtils.getFileContentsFromAssets(AppContext, "cosmetics.json");


        //Read from URL
        //filterProduct = NetworkUtils.UrlEncode(filterProduct);
        //filterTitle = NetworkUtils.UrlEncode(filterTitle);
        //String urlString = String.format("https://www.pythonanywhere.com/user/georgem/files/home/georgem/cosmetics.json?edit?product=%s&title=%s&genreid=%d", filterProduct, filterTitle, filterGenreId);
        //String contents = NetworkUtils.getFileContentsFromFromUrl(urlString);

        JSONObject json = JsonParser.getJsonObject(contents);
        JSONArray jCosmetics = json.optJSONArray("Cosmetics");
        if (jCosmetics == null) return;
        int nCosmetics = jCosmetics.length();
        for (int i=0; i<nCosmetics; i++){
            JSONObject jCurCosmetic = jCosmetics.optJSONObject(i);
            int cosmeticID = jCurCosmetic.optInt(DataStore.KEY_ID, 0);
            String cosmeticTitle = jCurCosmetic.optString(DataStore.KEY_TITLE);
            String cosmeticProduct = jCurCosmetic.optString(DataStore.KEY_PRODUCT);
            int cosmeticGenreId = jCurCosmetic.optInt(DataStore.KEY_GENREID, 0);
            String cosmeticProductUrl = jCurCosmetic.optString(DataStore.KEY_PRODUCTURL);
            String cosmeticImageUrl = jCurCosmetic.optString(DataStore.KEY_IMAGEURL);

            //get Genre name by ID
            String cosmeticGenreName = DataStore.Genres[cosmeticGenreId];

            // hold each cosmetic in a HashMap (Associative Array)
            HashMap<String, Object> cosmetic = new HashMap<String, Object>();
            cosmetic.put(DataStore.KEY_ID, cosmeticID);
            cosmetic.put(DataStore.KEY_TITLE, cosmeticTitle);
            cosmetic.put(DataStore.KEY_PRODUCT, cosmeticProduct);
            cosmetic.put(DataStore.KEY_GENREID, cosmeticGenreId);
            cosmetic.put(DataStore.KEY_GENRENAME, cosmeticGenreName);
            cosmetic.put(DataStore.KEY_PRODUCTURL, cosmeticProductUrl);
            cosmetic.put(DataStore.KEY_IMAGEURL, cosmeticImageUrl);

            DataStore.Cosmetics.add(cosmetic);
        }
    }


}
