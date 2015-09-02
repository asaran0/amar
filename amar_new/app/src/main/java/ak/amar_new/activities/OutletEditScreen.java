package ak.amar_new.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import ak.amar_new.R;
import ak.amar_new.models.FoodMenu;
import ak.amar_new.models.OutletDetail;
import ak.amar_new.utils.FileUtils;

/*
 * Created by amar on 8/7/15.
 */
public class OutletEditScreen extends ActionBarActivity {
    Toolbar mToolbar;
    Menu mMenu;
    private Button submitButton;
    private static final int FILE_SELECT_CODE = 220;
    private static final int CAMERA_REQUEST = 221;
    ImageView imageView;
    private File extFile;
    private File imgDir;
    private FoodMenu foodMenu;
    private OutletDetail om;
    private static String outletId = "";  //we made it static because if we go to camera then the outletid informasion gone.

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.outlet_edit_screen);
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Create New Outlet");
        //getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imageView = (ImageView) findViewById(R.id.outlet_edit_imageView);
        extFile = Environment.getExternalStorageDirectory();
        outletId = getIntent().getStringExtra(getString(R.string.OUTLETID));
        om = new OutletDetail();
        foodMenu = new FoodMenu();
        if (outletId == null) outletId = "";
        if (outletId.equals("")) {
            unique_name();
        } else {
            RenderOutletDetails();
        }

    }

    /* @Override
     protected void onResume() {
         outletId = getIntent().getStringExtra(getString(R.string.OUTLETID));
         super.onResume();
     }
 */
    public void RenderOutletDetails() {
        imgDir = new File(extFile, "/" + getString(R.string.base_folder_name) + "/" + outletId);

        String path = extFile.getAbsolutePath() + "/" + getString(R.string.base_folder_name) + "/" + outletId + "/" + getString(R.string.DETAILS);
        foodMenu = FileUtils.ReadFoodMenu(path);

        EditText et;
        et = (EditText) findViewById(R.id.outlet_edit_name);
        et.setText(foodMenu.getOutletDetail().getOutletName());

        et = (EditText) findViewById(R.id.outlet_edit_locality);
        et.setText(foodMenu.getOutletDetail().getLocality());

        et = (EditText) findViewById(R.id.outlet_edit_city);
        et.setText(foodMenu.getOutletDetail().getCity());

        et = (EditText) findViewById(R.id.outlet_edit_pin);
        et.setText(foodMenu.getOutletDetail().getPinCode());

        et = (EditText) findViewById(R.id.outlet_edit_address);
        et.setText(foodMenu.getOutletDetail().getAddress());
    }

    public void select_image(View view) {
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
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
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
                } catch (Exception e) {
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
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    String filePath = data.getData().getPath().toString();
                    Log.i("amar", "path = " + filePath);

                    Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                    imageView.setImageBitmap(bitmap);
                    imageView.setVisibility(View.VISIBLE);
                    saveImage(bitmap);
                }
                break;
            case CAMERA_REQUEST:
                if (resultCode == RESULT_OK) {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    imageView.setImageBitmap(bitmap);
                    imageView.setVisibility(View.VISIBLE);
                    saveImage(bitmap);
                }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void saveImage(Bitmap bitmap) {
        if (bitmap != null) {

            int n = 10000;
            Random generator = new Random();
            n = generator.nextInt(n);
            String fName = "ak-" + n + ".jpg";
            File file1 = new File(imgDir, fName);
            if (file1.exists()) file1.delete();
            try {
                Log.i("amar", "Creating the image file");
                FileOutputStream out = new FileOutputStream(file1);

                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.flush();
                out.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void unique_name() {

        Log.i("test", "unique name");
        long sysTime = System.nanoTime();
        outletId = String.format("%08x", (sysTime / 100000) & 0xffffffff) + String.format("%05x", (sysTime % 100000) & 0xfffff);

        Log.i("test", "unique name " + outletId);
        File file = new File(extFile, "/" + getString(R.string.base_folder_name));
        if (!file.exists()) {
            Log.i("test", "Directory does not exists ak_projects ");
            file.mkdir();
        }

        imgDir = new File(file, "/" + outletId);
        if (!imgDir.exists()) {
            Log.i("test", "Directory does not exists " + outletId);

            imgDir.mkdir();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
//        if (item.getItemId() == R.id.property_search) {
//            Intent intent = new Intent(this, SearchActivity.class);
//            intent.putExtra(SearchActivity.CITY_NAME, mSpinner.getText());
//            startActivity(intent);
//        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Log.i("amar1 onbackpress", outletId);
        if (!outletId.equals("")) {
            File imgDir = new File(extFile, "/" + getString(R.string.base_folder_name) + "/" + outletId);
            if (imgDir.exists()) {
                //FileUtils.cleanDirectory(imgDir);
                deleteContents(imgDir);
                imgDir.delete();
                Log.i("amar1 onbackpress 1", outletId + " " + imgDir.getAbsolutePath());
            }
        }
        super.onBackPressed();
    }

    public void OutletEditSubmit(View view) {
        validate_data();
        FoodMenu foodMenu = new FoodMenu();
        foodMenu.setOutletDetail(om);
        String path = extFile.getAbsolutePath() + "/" + getString(R.string.base_folder_name) + "/" + outletId + "/" + getString(R.string.DETAILS);
        FileUtils.WriteFoodMenu(path, foodMenu);

        Intent i = new Intent(this, OutletDetailScreen.class);
        i.putExtra(getString(R.string.OUTLETID), outletId);
        startActivity(i);
        finish();


    }

    public boolean deleteContents(File dir) {
        File[] files = dir.listFiles();
        boolean success = true;
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    success &= deleteContents(file);
                }
                if (!file.delete()) {
                    Log.i("Failed to delete ", file.getAbsolutePath());
                    success = false;
                }
            }
        }
        return success;
    }

    public void validate_data() {
        EditText et;
        om.setOutletId(outletId);
        et = (EditText) findViewById(R.id.outlet_edit_name);
        om.setOutletName(et.getText().toString());

        et = (EditText) findViewById(R.id.outlet_edit_locality);
        om.setLocality(et.getText().toString());

        et = (EditText) findViewById(R.id.outlet_edit_city);
        om.setCity(et.getText().toString());

        et = (EditText) findViewById(R.id.outlet_edit_pin);
        om.setPinCode(et.getText().toString());

        et = (EditText) findViewById(R.id.outlet_edit_address);
        om.setAddress(et.getText().toString());
    }
}