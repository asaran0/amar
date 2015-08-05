package ak.amar_new.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import ak.amar_new.R;
import ak.amar_new.models.OutletDetailModel;

/**
 * Created by amar on 26/6/15.
 */

public class OutletItemsEditScreen extends ActionBarActivity {
    private Toolbar mToolbar;
    OutletDetailModel od;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.outlet_items_edit_screen);
        mToolbar = (Toolbar)findViewById(R.id.main_toolbar);
        od = new OutletDetailModel();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Add Items for " + od.outletName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
