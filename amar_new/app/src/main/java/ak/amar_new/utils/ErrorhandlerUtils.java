package ak.amar_new.utils;

/**
 * Created by amar on 10/6/15.
 */

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

public class ErrorhandlerUtils {

    public static final String mSesssionExpiredError = "Session has expired, Please Log in again";
    public static final String mServerTimeOutMessage = "Oops...server timed out, Please try again";
    public static final String mNetworkNotAvailabeMessage = "Please check your Internet connection";
    public static final String mUnknownServerErrorMessage = "Unknown server error. Please try again";
    public static final String mServerOverloadMessage = "Uh oh! Our servers are currently experiencing too many requests. Please try again later";
    public static final String mDataParseError = "Oops...some unexpected error. Please try again";


    public static void handleVolleyError(Context context, VolleyError error) {
        Toast.makeText(context, getVolleyErrorMessage(error), Toast.LENGTH_SHORT).show();
    }

    private static String getVolleyErrorMessage(Object error) {
        if (error instanceof TimeoutError) {
            return mServerTimeOutMessage;
        } else if (isServerProblem(error)) {
            return handleServerError(error);
        } else if (isNetworkProblem(error)) {
            return mNetworkNotAvailabeMessage;
        } else if (error instanceof ParseError) {
            return mDataParseError;
        }
        return mUnknownServerErrorMessage;
    }

    private static boolean isNetworkProblem(Object error) {
        return (error instanceof NetworkError) || (error instanceof NoConnectionError);
    }

    private static boolean isServerProblem(Object error) {
        return (error instanceof ServerError) || (error instanceof AuthFailureError);
    }

    private static String handleServerError(Object err) {
        VolleyError error = (VolleyError) err;
        NetworkResponse response = error.networkResponse;

        if (response != null) {
            switch (response.statusCode) {
                case 500:
                case 503:
                    return mServerOverloadMessage;
                default:
                    return mUnknownServerErrorMessage;
            }
        }
        return mUnknownServerErrorMessage;
    }
}

