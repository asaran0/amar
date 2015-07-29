package ak.amar_new.activities;


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import ak.amar_new.R;

/**
 * Created by amar on 25/6/15.
 */
public class MainScreen extends ActionBarActivity {
    private DrawerLayout mainDrawer;
    private Toolbar mToolbar;
    private ListView drawerListView;
    private ActionBarDrawerToggle mToggle;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        mToolbar = (Toolbar)findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("amar_first");
        //getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mainDrawer = (DrawerLayout)findViewById(R.id.main_drawer);
        drawerListView = (ListView)findViewById(R.id.main_drawer_listView);
        drawerListView.setAdapter(new DrawableMenuAdapter(this));
        mToggle = new ActionBarDrawerToggle(this,mainDrawer,mToolbar,R.string.app_name,R.string.app_name){
            @Override
            public void onDrawerClosed(View drawerView) {
                getSupportActionBar().setDisplayShowTitleEnabled(true);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setDisplayShowTitleEnabled(false);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
    }
    public void new_entry(View view)
    {
        startActivity(new Intent(this,OutletEditScreen.class));
    }
    public void update_existing(View view)
    {
        startActivity(new Intent(this,OutletListScreen.class));
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mToggle.onConfigurationChanged(newConfig);

    }

    class DrawableMenuAdapter extends ArrayAdapter<String> {

        private Context context;
        private LayoutInflater mLayoutInflater;
        private String values[] = {"User Profile","Order History","Notifications","Pending Orders"};


        public DrawableMenuAdapter(Context context){
            super(context,R.layout.drawer_item_list);
            this.context = context;
            mLayoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            return values.length;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rawView = mLayoutInflater.inflate(R.layout.drawer_item_list,null);
            TextView tValue = (TextView)rawView.findViewById(R.id.main_drawer_list_itemName);
            tValue.setText(values[position]);
            return rawView;
        }
    }
}
