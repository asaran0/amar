package ak.amar_new.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import ak.amar_new.R;

/*
 * Created by amar on 8/7/15.
 */
public class OutletEditScreen extends ActionBarActivity {

    Toolbar mToolbar;
    Menu mMenu;
    private Button submitButton;
    private static final int FILE_SELECT_CODE = 220;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.outlet_edit_screen);
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Create New Outlet");
        //getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }
    public void select_image(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("choose already saved image or take it from camera");
        Button bt1 = new Button(this);
        bt1.setText("Capture from Camera");
        Button bt2 = new Button(this);
        bt2.setText("Brows image");
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.addView(bt1);
        ll.addView(bt2);
        builder.setView(ll);
        builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final Dialog dialog = builder.create();
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "camera", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                try {
                    startActivityForResult(Intent.createChooser(intent, "Select image to add"), FILE_SELECT_CODE);
                } catch (Exception e){
                    Toast.makeText(getApplicationContext(), "The file chooser not found exception " + e.toString(), Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(getApplicationContext(), "Brows", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
        dialog.show();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode)
        {
            case FILE_SELECT_CODE:
                if(resultCode == RESULT_OK)
                {

                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
          if(item.getItemId() == android.R.id.home)
          {
              onBackPressed();
          }
//        if (item.getItemId() == R.id.property_search) {
//            Intent intent = new Intent(this, SearchActivity.class);
//            intent.putExtra(SearchActivity.CITY_NAME, mSpinner.getText());
//            startActivity(intent);
//        }
        return super.onOptionsItemSelected(item);
    }


}