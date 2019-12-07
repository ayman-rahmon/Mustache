package com.fruitybeastsknowledge.mustache;

import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fruitybeastsknowledge.mustache.Adapters.OrdersCustomersAdapter;
import com.fruitybeastsknowledge.mustache.helpers.Order;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CustomerActivity extends AppCompatActivity {


    private RecyclerView listOfOrders ;
    private ArrayList<Order> orders = new ArrayList<>() ;
    private OrdersCustomersAdapter adapter ;
    private int count = 0 ;
    private final String PREFERENCES_NAME = "prefs" ;
    private FloatingActionButton fab  ;
    private SharedPreferences prefs ;
    private FirebaseAuth mAuth ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        listOfOrders = (RecyclerView)findViewById(R.id.recyclerview);
        adapter = new OrdersCustomersAdapter(orders,this);
        listOfOrders.setLayoutManager(new LinearLayoutManager(this));
        listOfOrders.setAdapter(adapter);
        getOrders();


        mAuth = FirebaseAuth.getInstance();
        prefs  = getSharedPreferences(PREFERENCES_NAME ,MODE_PRIVATE);
        count = prefs.getInt("count",0);
    Button addRequest  = (Button)findViewById(R.id.add_btn);
    addRequest.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("AKUMA","the current user ID is :: " + mAuth.getCurrentUser().getUid() );
            String newID = getSaltString();
//                DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(String.valueOf(count));


            DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(newID);
            Map values = new HashMap();
            values.put("ID",newID);
            values.put("from", prefs.getString("name","anonymous"));
            values.put("to", "");
            values.put("state", "new");

            SharedPreferences.Editor sp = getSharedPreferences(PREFERENCES_NAME,MODE_PRIVATE).edit();
            count = ++count ;

            sp.putInt("count",count);
            sp.commit();


            mRef.setValue(values);
            Log.d("AKUMA","the count is :: " + count );
        }
    });

/*
        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("AKUMA","the current user ID is :: " + mAuth.getCurrentUser().getUid() );
                String newID = getSaltString();
//                DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(String.valueOf(count));


                DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(newID);
                Map values = new HashMap();
                values.put("ID",newID);
                values.put("from", prefs.getString("name","anonymous"));
                values.put("to", "");
                values.put("state", "new");

                SharedPreferences.Editor sp = getSharedPreferences(PREFERENCES_NAME,MODE_PRIVATE).edit();
                count = ++count ;

                sp.putInt("count",count);
                sp.commit();


                mRef.setValue(values);
                Log.d("AKUMA","the count is :: " + count );
            }
        });
*/

    }

    private String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }


    private void getOrders() {

       DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("Orders");

       mRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               if(dataSnapshot != null) {
                   orders.clear();
                   Log.d("AKUMA", "getting orders ...");
                   Log.d("AKUMA", "current name :: " + FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

                   for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                       if(prefs.getString("name","").equals(dataSnapshot1.child("from").getValue().toString())) {
                           Order order = new Order( dataSnapshot1.child("ID").getValue().toString(), dataSnapshot1.child("from").getValue().toString(), dataSnapshot1.child("to").getValue().toString(), dataSnapshot1.child("state").getValue().toString());
                           orders.add(order);
                       }
                   }

                   adapter.notifyDataSetChanged();
               }else {
                   Toast.makeText(CustomerActivity.this  , "there is no data ..." ,Toast.LENGTH_SHORT).show();
               }
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });




    }


}
