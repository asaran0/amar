package ak.amar_new.activities;


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ak.amar_new.R;

/**
 * Created by amar on 25/6/15.
 */
public class MainScreen extends ActionBarActivity {
    private DrawerLayout mainDrawer;
    private Toolbar mToolbar;
    private ListView drawerListView;
    private ActionBarDrawerToggle mToggle;
    private int prevList = -1;

    public ExpandableListAdapter listAdapter;
    public ExpandableListView expListView;
    public List<String> listDataHeader;
    public HashMap<String, List<String>> listDataChild;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        mToolbar = (Toolbar)findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("amar_first");
        prepareListData();
        //getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        expListView = (ExpandableListView) findViewById(R.id.main_Expendable_drawer_listView);
        mainDrawer = (DrawerLayout)findViewById(R.id.main_drawer);
        expListView.setAdapter(new ExpendableDrawableMenuAdapter(this,listDataHeader,listDataChild));
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
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Toast.makeText(getApplicationContext(),listDataHeader.get(groupPosition)+" clicked.",Toast.LENGTH_SHORT).show();

                return false;
            }
        });
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {


            }
        });
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

                if(prevList != -1 && prevList != groupPosition)
                {
                    expListView.collapseGroup(prevList);
                }
                prevList = groupPosition;

            }
        });
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(getApplicationContext(),listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition)+" clicked.",Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    public void new_entry(View view)
    {
        Intent intent = new Intent(this,OutletEditScreen.class);
        intent.putExtra(getString(R.string.OUTLETID),"");
        startActivity(intent);
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

    class ExpendableDrawableMenuAdapter extends BaseExpandableListAdapter {

        private Context context;
        private LayoutInflater mLayoutInflater;

        private List<String> listDataHeader; // header titles
        // child data in format of header title, child title
        private HashMap<String, List<String>> listDataChild;
        //private String values[] = {"User Profile","Order History","Notifications","Pending Orders"};


        public ExpendableDrawableMenuAdapter(Context context, List<String> listDataHeader, HashMap<String,List<String> > listDataChild){

            this.context = context;
            this.listDataHeader = listDataHeader;
            this.listDataChild = listDataChild;
            //mLayoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        }

      /*  @Override
        public int getCount() {
            return values.length;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rawView = mLayoutInflater.inflate(R.layout.drawer_item_list,null);
            TextView tValue = (TextView)rawView.findViewById(R.id.main_drawer_list_itemName);
            tValue.setText(values[position]);
            return rawView;
        }*/

        @Override
        public int getGroupCount() {
            return listDataHeader.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return listDataChild.get(listDataHeader.get(groupPosition)).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return listDataHeader.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            String headerTitle = (String)getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.drawer_item_list,null);
            }
            ImageView icon = (ImageView)convertView.findViewById(R.id.main_drawer_icon);
            TextView header = (TextView)convertView.findViewById(R.id.main_drawer_list_itemName);
            header.setText(headerTitle);
            if(isExpanded){

                icon.setImageResource(R.drawable.drawer_up);
                header.setTypeface(null, Typeface.BOLD);
            } else {
                icon.setImageResource(R.drawable.drawer_down);
                header.setTypeface(null, Typeface.NORMAL);
            }
            if(listDataChild.get(listDataHeader.get(groupPosition)).size() ==0)
                icon.setImageResource(R.drawable.sub_menu);
                return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            String childTitle = (String)getChild(groupPosition,childPosition);
            if(convertView == null)
            {
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.drawer_sub_item_list,null);
            }
            TextView child = (TextView) convertView.findViewById(R.id.main_drawer_list_sub_itemName);
            child.setText(childTitle);
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("User Info");
        listDataHeader.add("Notifications");
        listDataHeader.add("Order History");
        listDataHeader.add("Pending Orders");
        listDataHeader.add("Reviews");
        listDataHeader.add("Our Sevices");


        // Adding child data
        List<String> orders = new ArrayList<String>();
        orders.add("All");
        orders.add("Vender A");
        orders.add("Vender B");

        List<String> pendingOrders = new ArrayList<String>();
        pendingOrders.add("All");
        pendingOrders.add("Vender A");
        pendingOrders.add("Vender B");

        List<String> services = new ArrayList<String>();
        services.add("Analytics");
        services.add("Suggestions");
        services.add("Experts help");

        listDataChild.put(listDataHeader.get(0), new ArrayList<String>()); // Header, Child data
        listDataChild.put(listDataHeader.get(1), new ArrayList<String>());
        listDataChild.put(listDataHeader.get(2), orders);
        listDataChild.put(listDataHeader.get(3), pendingOrders);
        listDataChild.put(listDataHeader.get(4), new ArrayList<String>());
        listDataChild.put(listDataHeader.get(5), services);
    }

}
