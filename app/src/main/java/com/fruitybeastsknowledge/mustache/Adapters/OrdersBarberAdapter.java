package com.fruitybeastsknowledge.mustache.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fruitybeastsknowledge.mustache.OrderDetailsActivity;
import com.fruitybeastsknowledge.mustache.R;
import com.fruitybeastsknowledge.mustache.helpers.Order;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by akumanotatsujin on 24/11/17.
 */

public class OrdersBarberAdapter extends RecyclerView.Adapter<OrdersBarberAdapter.RecyclerViewHolder> {
    private FirebaseDatabase database ;

    private ArrayList<Order> orders = new ArrayList<>();
    Context mContext ;


    public OrdersBarberAdapter(ArrayList<Order> arrayList , Context context){
        this.orders = arrayList ;
        this.mContext = context ;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_list_rows,parent,false) ;
        RecyclerViewHolder holder = new RecyclerViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(RecyclerViewHolder holder,  int position) {
        // setting the data to the layout
        holder.nameOfCustomer.setText(orders.get(position).getFrom());
        holder.nameOfBarber.setText(orders.get(position).getTo());
        holder.state.setText(orders.get(position).getState());
        final int pos = position ;
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                start the details Activity...

                if(orders.get(pos).getState().equals("new")){
//                    start the new order details ...
                    Intent intent = new Intent(mContext, OrderDetailsActivity.class);
                    intent.putExtra("unique","new");
                    intent.putExtra("name",orders.get(pos).getFrom());
                    intent.putExtra("to",orders.get(pos).getTo());
                    intent.putExtra("id",orders.get(pos).getId());
                    mContext.startActivity(intent) ;

                }else if (orders.get(pos).getState().equals("accepted")) {
//                    start the accepted order details ...
                    Intent intent = new Intent(mContext, OrderDetailsActivity.class);
                    intent.putExtra("unique","accepted");
                    intent.putExtra("name",orders.get(pos).getFrom());
                    intent.putExtra("to",orders.get(pos).getTo());
                    intent.putExtra("id",orders.get(pos).getId());

                    mContext.startActivity(intent) ;

                } else if (orders.get(pos).getState().equals("completed")) {
//                    start the completed order details ...
/*
                    Intent intent = new Intent(mContext, OrderDetailsActivity.class);
                    intent.putExtra("unique","completed");
                    intent.putExtra("name",orders.get(pos).getFrom());
                    intent.putExtra("to",orders.get(pos).getTo());
                    intent.putExtra("id",orders.get(pos).getId());
                    intent.putExtra("state",orders.get(pos).getState());

                    mContext.startActivity(intent) ;
*/
                }else {
                    Toast.makeText(mContext, "something went wrong in the data ...", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder  {


        // initializing the variables from the layout file >>>
        RelativeLayout relativeLayout ;
        TextView nameOfCustomer  ;
        TextView nameOfBarber ;
        TextView state ;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            nameOfCustomer= (TextView)itemView.findViewById(R.id.name_of_customer);
            nameOfBarber= (TextView)itemView.findViewById(R.id.name_of_barber);
            state           = (TextView)itemView.findViewById(R.id.state);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relative_layout);

        }

    }

}
