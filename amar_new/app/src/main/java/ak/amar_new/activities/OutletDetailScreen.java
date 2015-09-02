package ak.amar_new.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import ak.amar_new.R;
import ak.amar_new.models.FoodMenu;
import ak.amar_new.models.OutletDetail;
import ak.amar_new.utils.FileUtils;

/**
 * Created by amar on 26/6/15.
 */
public class OutletDetailScreen extends ActionBarActivity {
    private String outletId = "";
    private Toolbar mToolbar;
    private File imgDir;
    File extFile;
    FoodMenu foodMenu;
    ViewPager viewPager;
    ArrayList<String> imgUrls = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.outlet_detail_screen);
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Outlet Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        outletId = getIntent().getStringExtra(getString(R.string.OUTLETID));
        foodMenu = new FoodMenu();
        extFile = Environment.getExternalStorageDirectory();
        Log.i("test", outletId);
        imgDir = new File(extFile, "/" + getString(R.string.base_folder_name) + "/" + outletId);
        viewPager = (ViewPager) findViewById(R.id.outlet_detail_viewPager);
        setDetails();
        viewPager.setAdapter(new OutletImageViewer(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void setDetails() {
        TextView tv;
        foodMenu = FileUtils.ReadFoodMenu(imgDir.getAbsolutePath() + "/" + getString(R.string.DETAILS));
        tv = (TextView) findViewById(R.id.outlet_detail_name);
        tv.setText(foodMenu.getOutletDetail().getOutletName());

        tv = (TextView) findViewById(R.id.outlet_detail_locality);
        tv.setText(foodMenu.getOutletDetail().getLocality());

        tv = (TextView) findViewById(R.id.outlet_detail_city);
        tv.setText(foodMenu.getOutletDetail().getCity());

        tv = (TextView) findViewById(R.id.outlet_detail_address);
        tv.setText(foodMenu.getOutletDetail().getAddress());

        try {
            File fileList[];
            fileList = imgDir.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                if (!fileList[i].isDirectory()) {
                    if (fileList[i].getName().toString().contains("jpg")) {
                        imgUrls.add(fileList[i].getAbsolutePath());
                    }
                }
            }
        } catch (Exception e) {
            Log.i("test", "exception in url add " + e);
        }
    }

    public void EditOutletDetails(View view) {
        Intent intent = new Intent(this, OutletEditScreen.class);
        intent.putExtra(getString(R.string.OUTLETID), outletId);
        Log.i("test 1", outletId);
        startActivity(intent);
    }

    public void AddMenuItems(View view) {
        Intent intent = new Intent(this, OutletItemsEditScreen.class);
        intent.putExtra(getString(R.string.OUTLETID), outletId);
        Log.i("test 2", outletId);
        startActivity(intent);
    }

    public class OutletImageViewer extends PagerAdapter {
        Context mContext;
        LayoutInflater mLayoutInflater;

        OutletImageViewer(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return imgUrls.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == (RelativeLayout) object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //to check for optimization
            View view = mLayoutInflater.inflate(R.layout.outlet_detail_pager_item, container, false);
            ImageView imageView = (ImageView) view.findViewById(R.id.outlet_detail_viewPager_item_image);
            TextView textView = (TextView) view.findViewById(R.id.outlet_detail_viewPager_item_textview);
            Bitmap bitmap = BitmapFactory.decodeFile(imgUrls.get(position));
            imageView.setImageBitmap(bitmap);
            textView.setText((position + 1) + "/" + imgUrls.size());
            container.addView(view);

            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((RelativeLayout) object);
        }
    }
}
