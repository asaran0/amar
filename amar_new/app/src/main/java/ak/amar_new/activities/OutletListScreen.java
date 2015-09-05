package ak.amar_new.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.DonutProgress;

import java.io.File;
import java.util.ArrayList;

import ak.amar_new.R;
import ak.amar_new.models.FoodDetail;

/**
 * Created by amar on 26/6/15.
 */
public class OutletListScreen extends ActionBarActivity {
    private Toolbar mToolbar;
    private ListView listView;
    private OutletListAdapter outletListAdapter;
    //private File
    private ArrayList<FoodDetail> foodDetailList = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.outlet_list_screen);
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("My Outlets");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView = (ListView)findViewById(R.id.outlet_list_listView);
        outletListAdapter = new OutletListAdapter(this,foodDetailList);
        listView.setAdapter(outletListAdapter);
        foodDetailList.addAll(getFoodDetailList());
        outletListAdapter.notifyDataSetChanged();

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
    public class OutletListAdapter extends ArrayAdapter<FoodDetail> {
        LayoutInflater layoutInflater;
        ArrayList<FoodDetail>  data = new ArrayList<>();

        public OutletListAdapter(Context context, ArrayList<FoodDetail> data) {

            super(context, R.layout.outlet_list_items,data);
            layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            this.data = data;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final Holder holder;
            if(convertView == null)
            {
                convertView = layoutInflater.inflate(R.layout.outlet_list_items,null);
                holder = new Holder();
                holder.vendorName = (TextView)convertView.findViewById(R.id.outlet_list_item_vendor_name);
                holder.outletName = (TextView)convertView.findViewById(R.id.outlet_list_item_outlet_name);
                holder.statusComplete = (CheckBox)convertView.findViewById(R.id.outlet_list_item_upload);
                holder.statusPending = (TextView)convertView.findViewById(R.id.outlet_list_item_status_pending);
                holder.statusDone = (ImageView)convertView.findViewById(R.id.outlet_list_item_upload_done);
                holder.statusProgress = (DonutProgress)convertView.findViewById(R.id.outlet_list_item_uploading_progress);
                convertView.setTag(holder);
            } else {
                holder = (Holder)convertView.getTag();
            }
            FoodDetail data = new FoodDetail();
            data = getItem(position);
            holder.vendorName.setText(data.getVendorName());
            holder.outletName.setText(data.getOutletName());
            if(data.getUploadStatus().equals("PENDING")) {
                holder.statusPending.setText("Start");
                holder.statusPending.setVisibility(View.VISIBLE);
                holder.statusProgress.setVisibility(View.GONE);
                holder.statusDone.setVisibility(View.GONE);
                holder.statusComplete.setVisibility(View.GONE);

            } else if(data.getUploadStatus().equals("UPLOADING")) {

                holder.statusComplete.setVisibility(View.GONE);
                holder.statusProgress.setVisibility(View.VISIBLE);
                holder.statusDone.setVisibility(View.GONE);
                holder.statusPending.setVisibility(View.GONE);

            } else if(data.getUploadStatus().equals("COMPLETED")) {

                holder.statusComplete.setVisibility(View.VISIBLE);
                holder.statusProgress.setVisibility(View.GONE);
                holder.statusDone.setVisibility(View.GONE);
                holder.statusPending.setVisibility(View.GONE);

            } else if(data.getUploadStatus().equals("UPLOADED")) {

                holder.statusComplete.setVisibility(View.GONE);
                holder.statusProgress.setVisibility(View.GONE);
                holder.statusDone.setVisibility(View.VISIBLE);
                holder.statusPending.setVisibility(View.GONE);
            }

            return convertView;
        }
        public class Holder {
            public TextView vendorName;
            public TextView outletName;
            public CheckBox statusComplete;
            public TextView statusPending;
            public ImageView statusDone;
            public DonutProgress statusProgress;
        }
    }
    public ArrayList<FoodDetail> getFoodDetailList(){
        ArrayList<FoodDetail> data = new ArrayList<>();
        FoodDetail tempData = new FoodDetail();

        return data;
    }
    public void SyncData(View view) {

    }
}
