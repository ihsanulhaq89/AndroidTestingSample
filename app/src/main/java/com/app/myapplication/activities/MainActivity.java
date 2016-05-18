package com.app.myapplication.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.app.myapplication.R;
import com.app.myapplication.adapters.SampleAdapter;
import com.app.myapplication.util.CartData;
import com.app.myapplication.util.MyConstants;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SampleAdapter mAdapter;
    private int count;
    private TextView cartCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        cartCount = (TextView) findViewById(R.id.cart);

        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("One");
        arrayList.add("Two");
        arrayList.add("Three");
        arrayList.add("Four");
        arrayList.add("SampleAdapter");
        arrayList.add("RecyclerView");
        arrayList.add("ArrayList");
        arrayList.add("Bundle");
        arrayList.add("MainActivity");
        arrayList.add("AppCompatActivity");
        arrayList.add("String");
        arrayList.add("void");
        arrayList.add("LinearLayoutManager");
        arrayList.add("boolean");


        mAdapter = new SampleAdapter(this, arrayList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);

        LocalBroadcastManager.getInstance(this).registerReceiver(mSelectionReceiver,
                new IntentFilter(MyConstants.BROADCAST_SELECTION));

        cartCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
            }
        });

    }
    private BroadcastReceiver mSelectionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            String data = bundle.getString(MyConstants.B_DATA);
            CartData.getInstance().add(data);
            count++;


            cartCount.setText(count+"");
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
