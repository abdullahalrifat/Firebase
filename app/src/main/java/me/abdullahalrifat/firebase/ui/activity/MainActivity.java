package me.abdullahalrifat.firebase.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.abdullahalrifat.firebase.R;
import me.abdullahalrifat.firebase.model.Item;
import me.abdullahalrifat.firebase.ui.adapter.itemsAdapter;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "TAG:";
    RecyclerView recyclerView;
    itemsAdapter adapter;
    List<Item> itemsList = new ArrayList<Item>();

    String strUni;

    View emptyView;
    private DatabaseReference mDatabase;



    // Progress Dialog
    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        emptyView = findViewById(R.id.layout_empty_event);
        adapter = new itemsAdapter(itemsList, this);

        addData();

        recyclerView = (RecyclerView) findViewById(R.id.rv_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuLogout:
                // User chose the "Settings" item, show the app settings UI...
                Intent intent = new Intent(MainActivity.this, loginActivity.class);
                startActivity(intent);
                finish();
                return true;


            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
    private void addData() {
        pDialog = new ProgressDialog(
                this);
        pDialog.setMessage("Loading Data ...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        mDatabase.child("item").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Item item = postSnapshot.getValue(Item.class);

                    itemsList.add(item);
                    Log.d(TAG, "Item name: " + item.getName() + ", Image Link " + item.getImageUrl());
                    adapter.notifyDataSetChanged();
                    pDialog.dismiss();
                    emptyView.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }
}
