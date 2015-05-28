package com.commonfloor.login;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




public class MainActivity extends Activity {
    private Pattern pattern,pattern1;
    private Matcher matcher,matcher1;
    private static final String USERNAME_PATTERN = "^[a-z0-9_-]{4,15}$";
    private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
    EditText un, pw;
    Button ok;
    private String resp;
    private String errorMsg;

    /** Called when the activity is first created. */
    public void Validator(){
        pattern = Pattern.compile(USERNAME_PATTERN);
        pattern1 = Pattern.compile(PASSWORD_PATTERN);
    }

    public boolean validate(final String username){

        matcher = pattern.matcher(username);
        return matcher.matches();

    }
    public boolean validatePwd(final String username){

        matcher1 = pattern1.matcher(username);
        return matcher1.matches();

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        un = (EditText) findViewById(R.id.et_un);
        pw = (EditText) findViewById(R.id.et_pw);
        ok = (Button) findViewById(R.id.btn_login);

        ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Validator();
                String ss1 = un.getText().toString();

               /* if(ss1.length()<4)
                {
                    Toast toast = Toast.makeText(getApplicationContext(),"User name should be Atleast 4 char big", Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }*/
                boolean a = validate(ss1);
                if(a==false) {
                    Toast toast = Toast.makeText(getApplicationContext(),"The username is invalid minmum 4 charectors.", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }/* else {
                    Toast toast = Toast.makeText(getApplicationContext(),ss1+" amar"+a, Toast.LENGTH_LONG);
                    toast.show();
                }*/
                String ss2 = pw.getText().toString();
                Log.e("Amardeep", ss1+" "+ss2);
                /*if(ss2.length()<6) {
                    Toast toast = Toast.makeText(getApplicationContext(),"password should be more than 6 character long", Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }*/
                if(validatePwd(ss2)==false) {
                    Toast toast = Toast.makeText(getApplicationContext(),"The Password is invalid minimum 6 charectors atleast 1 special char,1 numeric, 1 capital letter", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                Toast toast = Toast.makeText(getApplicationContext(),"Login Done", Toast.LENGTH_LONG);
                toast.show();

            }
        });
    }
}