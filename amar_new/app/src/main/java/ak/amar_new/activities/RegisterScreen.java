package ak.amar_new.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import ak.amar_new.App.AkApplication;
import ak.amar_new.R;
import ak.amar_new.models.RegisterModel;
import ak.amar_new.utils.Validator;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterScreen extends Activity {
    EditText editText,et;
    RegisterModel rm = new RegisterModel();
    private String tag_json_obj = "jobj_req";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);
        editText =(EditText)findViewById(R.id.reg_email);
        //editText.on
        Button button = (Button)findViewById(R.id.but_register);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateData()) {
                    //DoRegistration();

                }
            }
        });
    }
    public boolean validateData(){
        boolean flag = true;
        et = (EditText)findViewById(R.id.reg_name);
        if(et.getText().toString().trim().length()==0)
        {
            TextView tv = (TextView)findViewById(R.id.reg_alert);
            tv.setText("Enter Proper Name");
            return false;
        } else {
            rm.setName(et.getText().toString());
        }
        et = (EditText)findViewById(R.id.reg_email);
        if(!Validator.emailValidate(et.getText().toString()))
        {
            TextView tv = (TextView)findViewById(R.id.reg_alert);
            tv.setText("Enter correct email id");
            return false;
        } else {
            rm.setEmail(et.getText().toString());
        }
        String pwd1="";
        et = (EditText)findViewById(R.id.reg_password);
        if(!Validator.passwordValidate(et.getText().toString()))
        {
            TextView tv = (TextView)findViewById(R.id.reg_alert);
            tv.setText("Password is not valid enter 1 capital and 1small letter,1 special charecter, 1 numeric minimum lenght 8");
            return false;
        } else {
            pwd1 = et.getText().toString();
        }
        et = (EditText)findViewById(R.id.reg_re_password);
        if(!pwd1.equals(et.getText().toString()))
        {
            TextView tv = (TextView)findViewById(R.id.reg_alert);
            tv.setText("Enter same password");
            return false;
        } else {
            rm.setPassword(pwd1);
        }
        et = (EditText)findViewById(R.id.reg_mobile);
        if(!Validator.mobileValidate(et.getText().toString()))
        {
            TextView tv = (TextView)findViewById(R.id.reg_alert);
            tv.setText("Enter The correct mobile number");
            return false;
        } else {
            rm.setMobileNo(et.getText().toString());
        }
        /* ToDo amar make a scroll list(spinner) for selecting states
        Then for a state we will provide autocomplete text view with giving suggession of city.
        then on the basis of city it will provide the pin code.
         */
        et = (EditText)findViewById(R.id.reg_address);
        rm.setAddress(et.getText().toString());
        et = (EditText)findViewById(R.id.reg_state);
        rm.setState(et.getText().toString());
        et = (EditText)findViewById(R.id.reg_city);
        rm.setCity(et.getText().toString());
        et = (EditText)findViewById(R.id.reg_pin_code);
        rm.setPinCode(et.getText().toString());
        return true;
    }
    private void DoRegistration() {

        JSONObject params = new JSONObject();
        try {
            params.put("name", rm.getName());
            params.put("email",rm.getEmail());
            params.put("mobile",rm.getMobileNo());
            params.put("password",rm.getPassword());
            params.put("address",rm.getAddress());
            params.put("city",rm.getCity());
            params.put("state",rm.getState());
            params.put("pincode",rm.getPinCode());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                "http://www.akmeal.com/android_connect/login.php",params,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("amar 3", response.toString());

                        try {

                            int flag = response.get("status").toString().charAt(0)-'0';
                            Log.i("amar f", flag + "");
                            if(flag == 1){
                                startActivity(new Intent(RegisterScreen.this, LoginScreen.class));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("amar", "Error: " + error.getMessage());

            }
        });

        // Adding request to request queue
        AkApplication.getInstance().addToRequestQueue(jsonObjReq,
                tag_json_obj);

        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_obj);

    }
//    public void Login(View view)
//    {
//        startActivity(new Intent(this, LoginScreen.class));
//    }
}
