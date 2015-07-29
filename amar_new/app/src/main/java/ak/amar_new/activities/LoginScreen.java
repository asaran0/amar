package ak.amar_new.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import ak.amar_new.App.AkApplication;
import ak.amar_new.R;
import ak.amar_new.models.LoginModel;
import ak.amar_new.utils.NetworkUtils;
import ak.amar_new.utils.Validator;

import org.json.JSONException;
import org.json.JSONObject;

//import ak.amar_new.utils.NetworkUtils;

public class LoginScreen extends Activity {
    private EditText email, pwd;
    private static int flag = 0;
    private static Context context;
    private String tag_json_obj = "jobj_req";
    private TextView msgResponse;
    private ProgressDialog pDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        context = this;
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        email = (EditText) findViewById(R.id.email_login);
        pwd = (EditText) findViewById(R.id.pwd_login);
        Log.i("amar", flag + "");
        //int f1 = validate_login(email.getText().toString(),pwd.getText().toString());
        //Log.i("amar f1",f1+ "");
        msgResponse = (TextView) findViewById(R.id.msgResponse);

        TextView login = (TextView) findViewById(R.id.btn_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginScreen.this, MainScreen.class);
                i.putExtra("Name", "amar");
                startActivity(i);
                String url = "?email=";
                //new LongOperation().execute(url,null,null);

                if (!Validator.emailValidate(email.getText().toString())) {
                    TextView tv = (TextView) findViewById(R.id.login_alert);
                    tv.setText("The email id is not valid");
                } else if (false) {
                    //!Validator.passwordValidate(pwd.getText().toString())
                    TextView tv = (TextView) findViewById(R.id.login_alert);
                    tv.setText("The Password is not valid");
                } else {
                    //Log.i("amar 2", flag+" ");
                    flag = validate_login(email.getText().toString(), pwd.getText().toString());
                    Log.i("amar 3", flag + " ");
                    //LoginModel model = new LoginModel();
                    //model.setId(email.getText().toString());
                    //model.setPassword(pwd.getText().toString());
                    if (flag == 0) {
                        TextView tv = (TextView) findViewById(R.id.login_alert);
                        tv.setText("Invalid password");
                    } else if (flag == 1) {

                        startActivity(new Intent(LoginScreen.this, RegisterScreen.class));
                        finish();
                    } else {
                        TextView tv = (TextView) findViewById(R.id.login_alert);
                        tv.setText("Email does not exists");
                    }
                }
            }
        });
        TextView signUp = (TextView) findViewById(R.id.sign_up);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //makeJsonObjReq();
                startActivity(new Intent(LoginScreen.this, RegisterScreen.class));
                finish();
            }
        });

    }

    public static int validate_login1(String email, final String password) {
        Log.i("amar 1", email + " " + password);
        NetworkUtils.getPassword(context, new NetworkUtils.NetworkListener<LoginModel>() {
            @Override
            public void onSuccess(LoginModel data) {
                Log.i("amar 4", data.getPassword() + " " + data.getId());
                if (data == null) {
                    Log.i("amar 10", data.getId() + " " + data.getPassword());
                    flag = -1;
                }
                if (password.equals(data.getPassword())) {
                    Log.i("amar 11", data.getId() + " " + data.getPassword());
                    flag = 1;
                } else {
                    Log.i("amar 12", data.getId() + " " + data.getPassword());
                    flag = 0;
                }
            }

            @Override
            public void onError() {
                Log.i("amar error2", "amar");

            }
        }, email);
        return 0;
    }

    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }

    private int validate_login(final String email, final String password) {
        showProgressDialog();
        JSONObject params = new JSONObject();
        try {
            params.put("id", email);
            params.put("password", password);
            params.put("name", "amar");
            Log.i("amar 12", email + " " + password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.POST,
                "http://www.akmeal.com/android_connect/login.php", params,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("amar 3", response.toString());
                        msgResponse.setText(response.toString());
                        try {

                            flag = response.get("status").toString().charAt(0) - '0';
                            Log.i("amar f", flag + "");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        hideProgressDialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("amar", "Error: " + error.getMessage());
                hideProgressDialog();
            }
        });

        // Adding request to request queue
        AkApplication.getInstance().addToRequestQueue(jsonObjReq,
                tag_json_obj);

        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_obj);
        return flag;
    }
}