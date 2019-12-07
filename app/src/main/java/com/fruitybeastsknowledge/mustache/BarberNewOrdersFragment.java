package com.fruitybeastsknowledge.mustache;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fruitybeastsknowledge.mustache.Adapters.OrdersBarberAdapter;
import com.fruitybeastsknowledge.mustache.Adapters.OrdersCustomersAdapter;
import com.fruitybeastsknowledge.mustache.helpers.Order;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by akumanotatsujin on 23/11/17.
 */

public class BarberNewOrdersFragment extends Fragment {


    private RecyclerView recyclerView ;
    private OrdersBarberAdapter adapter ;
    private ArrayList<Order> orders  = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_barber_new_orders,container,false);

        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview);
        adapter = new OrdersBarberAdapter(orders,getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        getNewOrders();




        return view;
    }

private void getNewOrders(){


            DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("Orders");

            mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot != null){
                        orders.clear();
                        Log.d("AKUMA", "getting orders ...");
                        Log.d("AKUMA", "current name :: " + FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            if (dataSnapshot1.child("state").getValue() != null) {
                                if (dataSnapshot1.child("state").getValue().toString().equals("new")) {
                                    Order order = new Order(dataSnapshot1.child("ID").getValue().toString(), dataSnapshot1.child("from").getValue().toString(), dataSnapshot1.child("to").getValue().toString(), dataSnapshot1.child("state").getValue().toString());
                                    orders.add(order);
                                }
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }else{
                        Toast.makeText(getActivity(),"there is no data..." ,Toast.LENGTH_SHORT ).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });



    }



}
