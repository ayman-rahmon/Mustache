package com.fruitybeastsknowledge.mustache;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class OrderDetailsActivity extends AppCompatActivity {

    private Button changeStateBtn;
    private TextView nameTextView;
    private String currentState;
    private final String PREFERENCES_NAME = "prefs";
    private String to ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        changeStateBtn = (Button) findViewById(R.id.change_state_btn);
        nameTextView = (TextView) findViewById(R.id.name_label);
        nameTextView.setText(getIntent().getExtras().getString("name"));
        currentState = getIntent().getExtras().getString("state", "nothing");
        final String source = getIntent().getExtras().getString("unique");

        if (source.equals("new")) {
            changeStateBtn.setText("Accept");
            currentState = "new";
        } else if (source.equals("accepted")) {
            changeStateBtn.setText("complete");
            currentState = "accepted";
        } else if (source.equals("completed")) {
            changeStateBtn.setVisibility(View.GONE);
        } else {
            Log.d("AKUMA", "problem in setting the text of the button ");
        }


        changeStateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentState.equals("new")) {

                    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(getIntent().getExtras().getString("id")).child("state");
                    mRef.setValue("accepted");
                    SharedPreferences sp = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
                    DatabaseReference mRef2 = FirebaseDatabase.getInstance().getReference().child("Orders").child(getIntent().getExtras().getString("id")).child("to");
                    mRef2.setValue( sp.getString("name",""));

                    OrderDetailsActivity.this.finish();

                    /*
                    mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                                if(dataSnapshot1.child("id").getValue().toString().equals(getIntent().getExtras().getString("id"))){
                                  DatabaseReference reference =   FirebaseDatabase.getInstance().getReference().child("Orders").;
                                }

                            }



                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
*/
                } else if (currentState.equals("accepted")) {

                    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(getIntent().getExtras().getString("id")).child("state");
                    mRef.setValue("completed");
                    OrderDetailsActivity.this.finish();
                } else {
                    Log.d("AKUMA", "updating the database...");
                    OrderDetailsActivity.this.finish();
                }
            }
        });


    }


}

