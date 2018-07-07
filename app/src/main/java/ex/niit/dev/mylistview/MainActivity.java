package ex.niit.dev.mylistview;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> itemNames = new ArrayList<>();
    private ArrayList<String> itemQts = new ArrayList<>();
    private ArrayList<String> itemPrices = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView itemsView = (ListView)findViewById(R.id.listView);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Shopping App");
        actionBar.setSubtitle("Manage Items");


        itemsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.w("Index",position+"");
                Intent intent = new Intent(MainActivity.this,AddEditActivity.class);
                intent.putExtra(Util.INDEX,position);
                intent.putExtra(Util.ITEM_NAME,itemNames.get(position));
                intent.putExtra(Util.ITEM_QTY,itemQts.get(position));
                intent.putExtra(Util.ITEM_PRICE,itemPrices.get(position));
                startActivityForResult(intent,Util.REQ_CODE);
            }
        });

        adapter = new ArrayAdapter<String>(this,R.layout.item_row,itemNames);
        itemsView.setAdapter(adapter);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_add){
            Intent intent = new Intent(this,AddEditActivity.class);
            intent.putExtra(Util.INDEX,Util.NEW_ITEM);
            startActivityForResult(intent,Util.REQ_CODE);
        }else if (item.getItemId() == R.id.action_register){

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Util.REQ_CODE){
            if (resultCode == Util.RES_CODE){
                Bundle bundle = data.getExtras();
                int index = bundle.getInt(Util.INDEX);
                Log.w("ItemName",bundle.getString(Util.ITEM_NAME));

                if (index == Util.NEW_ITEM){
                    //add
                    itemNames.add(bundle.getString(Util.ITEM_NAME));
                    itemPrices.add(bundle.getString(Util.ITEM_PRICE));
                    itemQts.add(bundle.getString(Util.ITEM_QTY));
                }else{
                    //edit
                    itemNames.set(index,bundle.getString(Util.ITEM_NAME));
                    itemPrices.set(index,bundle.getString(Util.ITEM_PRICE));
                    itemQts.set(index,bundle.getString(Util.ITEM_QTY));
                }
                adapter.notifyDataSetChanged();

            }
        }
    }
}
