package com.fruitybeastsknowledge.mustache.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fruitybeastsknowledge.mustache.R;
import com.fruitybeastsknowledge.mustache.helpers.Order;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by akumanotatsujin on 24/11/17.
 */

public class OrdersCustomersAdapter extends RecyclerView.Adapter<OrdersCustomersAdapter.RecyclerViewHolder> {
private FirebaseDatabase database ;

    private ArrayList<Order> orders = new ArrayList<>();
    Context mContext ;


    public OrdersCustomersAdapter(ArrayList<Order> arrayList , Context context){
        this.orders = arrayList ;
        this.mContext = context ;
        database = FirebaseDatabase.getInstance() ;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_list_rows,parent,false) ;
    RecyclerViewHolder holder = new RecyclerViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
    // setting the data to the layout
        holder.nameOfCustomer.setText(orders.get(position).getFrom());
        holder.nameOfBarber.setText(orders.get(position).getTo());
        holder.state.setText(orders.get(position).getState());

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder  {


// initializing the variables from the layout file >>>
        TextView nameOfCustomer  ;
        TextView nameOfBarber ;
        TextView state ;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            nameOfCustomer= (TextView)itemView.findViewById(R.id.name_of_customer);
            nameOfBarber= (TextView)itemView.findViewById(R.id.name_of_barber);
            state           = (TextView)itemView.findViewById(R.id.state);

        }

    }

}
