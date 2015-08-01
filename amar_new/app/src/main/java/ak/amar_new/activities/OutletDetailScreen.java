package ak.amar_new.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;

import ak.amar_new.R;

/**
 * Created by amar on 26/6/15.
 */
public class OutletDetailScreen extends ActionBarActivity {
    private String outletId="";
    private Toolbar mToolbar;
    private File imgDir;
    File extFile;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.outlet_detail_screen);
        mToolbar = (Toolbar)findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Outlet Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        outletId = getIntent().getStringExtra(getString(R.string.OUTLETID));
        extFile = Environment.getExternalStorageDirectory();
        imgDir = new File(extFile,"/"+getString(R.string.OUTLETID)+"/"+outletId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
