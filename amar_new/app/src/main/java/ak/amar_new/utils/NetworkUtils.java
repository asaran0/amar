package ak.amar_new.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import ak.amar_new.App.AkApplication;
import ak.amar_new.models.LoginModel;
import com.google.gson.Gson;

import org.json.JSONObject;

//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;

/*
 * Created by amar on 9/6/15.
 */

public class NetworkUtils {
    private static NetworkUtils mInstance;
    private static RequestQueue mRequestQueue;
    private static Context mCtx;
    private final static String baseUrl1 = "http://172.16.123.53:8080";
    private final static String greetingUrl = baseUrl1+"/greeting";

    public interface NetworkListener<T> {

        public void onSuccess(T data);
        public void onError();
    }
    public static void getPassword(final Context context, final NetworkListener<LoginModel> loginListener,String email){
       String url = "http://www.akmeal.com/android_connect/login.php";
        Log.i("amar 6", url);
        JsonObjectRequest request = new JsonObjectRequest(url,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LoginModel loginData = (LoginModel) new Gson().fromJson(jsonObject.toString(),LoginModel.class);
                Log.i("amar 7", loginData.getId() + " " + loginData.getPassword());
                loginListener.onSuccess(loginData);

            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("amar erro", "error");
                ErrorhandlerUtils.handleVolleyError(context,error);
                loginListener.onError();

            }
        });
        //addToRequestQueue(request);
        //addToRequestQueue(request);
        request.setShouldCache(false);
        AkApplication.getInstance().addToRequestQueue(request);
    }
    public static boolean isInternetAvailable(Context context){
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

}
