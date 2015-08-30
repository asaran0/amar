package ak.amar_new.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ak.amar_new.R;
import ak.amar_new.models.OutletDetail;

/**
 * Created by amar on 26/6/15.
 */

public class OutletItemsEditScreen extends ActionBarActivity {
    private Toolbar mToolbar;
    OutletDetail od;
    private RecyclerView menuRecyclerView;
    OutletMenuRecyclerAdapter adapter;
    OutletItemRecyclerAdapter adapter1;

    private RecyclerView.LayoutManager layoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.outlet_items_edit_screen);
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        od = new OutletDetail();
        setSupportActionBar(mToolbar);
        String outletName = od.getOutletName() == null ? "" : od.getOutletName();
        getSupportActionBar().setTitle("Add Items for " + outletName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        menuRecyclerView = (RecyclerView) findViewById(R.id.outlet_item_edit_recycler_view);
        String[] item_array = this.getResources().getStringArray(R.array.Category);
        List<String> items = Arrays.asList(item_array);
        ArrayList<String> itemsList = new ArrayList<>(items);
        adapter = new OutletMenuRecyclerAdapter(this, itemsList);
        menuRecyclerView.setAdapter(adapter);
        menuRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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

    public class OutletMenuRecyclerAdapter extends RecyclerView.Adapter<OutletMenuRecyclerAdapter.MenuHolder> {
        public ArrayList<String> menuList;
        public Context context;

        public OutletMenuRecyclerAdapter(Context context,ArrayList<String> menuList){
            this.context = context;
            this.menuList=menuList;
        }

        @Override
        public MenuHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View menuView = LayoutInflater.from(context).inflate(R.layout.outlet_item_menu_item,viewGroup,false);
            return new MenuHolder(menuView);
        }

        @Override
        public void onBindViewHolder(final MenuHolder menuHolder, int position) {
            menuHolder.menu_name.setText(menuList.get(position));
            //String[] item_array = OutletItemsEditScreen.this.getResources().getStringArray(R.array.indian_bread);
            //List<String> items = Arrays.asList(item_array);
            ArrayList<String> itemsList = new ArrayList<>();
            itemsList.add("amar");
            itemsList.add("deep");
            adapter1 = new OutletItemRecyclerAdapter(OutletItemsEditScreen.this, itemsList);
            menuHolder.recyclerView.setAdapter(adapter1);
            menuHolder.recyclerView.setLayoutManager(new LinearLayoutManager(OutletItemsEditScreen.this));
            /*menuHolder.menu_options.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (menuHolder.menu_options.getText().toString().equals("Add")) {
                        menuHolder.menu_options.setText("Added");
                        //menuHolder.recyclerView.setVisibility(View.VISIBLE);
                    } else {
                        menuHolder.menu_options.setText("Add");
                        //menuHolder.recyclerView.setVisibility(View.GONE);
                    }
                }
            });*/
        }

        @Override
        public int getItemCount() {
            return menuList.size();
        }

        public class MenuHolder extends RecyclerView.ViewHolder {
            public TextView menu_name;
            public TextView menu_options;
            public RecyclerView recyclerView;
            public MenuHolder(View itemView) {
                super(itemView);
                menu_name = (TextView)itemView.findViewById(R.id.outlet_item_recycler_menu_name);
                menu_options = (TextView)itemView.findViewById(R.id.outlet_item_recycler_menu_option);
                recyclerView = (RecyclerView)itemView.findViewById(R.id.outlet_item_edit_menu_recycler_view);
            }
        }
    }

    public class OutletItemRecyclerAdapter extends RecyclerView.Adapter<OutletItemRecyclerAdapter.ItemHolder> {
        private ArrayList<String> itemList;
        private Context mContext;

        public OutletItemRecyclerAdapter(Context context, ArrayList<String> mItem) {
            mContext = context;
            itemList = mItem;
        }

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View viewItem = LayoutInflater.from(mContext).inflate(R.layout.outlet_item_recycler_item, viewGroup, false);
            return new ItemHolder(viewItem);

        }

        @Override
        public void onBindViewHolder(final ItemHolder itemHolder, int position) {
            itemHolder.itemName.setText(itemList.get(position));
            /*itemHolder.option.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(itemHolder.option.getText().toString().equals("Add")) {
                        itemHolder.option.setText("Added");
                        itemHolder.hiddenLayout.setVisibility(View.VISIBLE);
                    } else {
                        itemHolder.option.setText("Add");
                        itemHolder.hiddenLayout.setVisibility(View.GONE);
                    }
                }
            });*/
        }

        @Override
        public int getItemCount() {
            return itemList.size();
        }

        public class ItemHolder extends RecyclerView.ViewHolder {

            public TextView itemName;
            public TextView option;
            public LinearLayout hiddenLayout;
            public EditText price;
            public EditText description;


            public ItemHolder(View itemView) {
                super(itemView);
                this.itemName = (TextView) itemView.findViewById(R.id.outlet_item_recycler_item_name);
                option = (TextView) itemView.findViewById(R.id.outlet_item_recycler_item_option);
                this.hiddenLayout = (LinearLayout) itemView.findViewById(R.id.recycler_hidden_layout);
                price = (EditText) itemView.findViewById(R.id.outlet_item_recycler_item_price);
                description = (EditText) itemView.findViewById(R.id.outlet_item_recycler_item_description);

            }
        }
    }
}
