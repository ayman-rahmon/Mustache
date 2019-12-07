package com.fruitybeastsknowledge.mustache;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.fruitybeastsknowledge.mustache.Adapters.SpinnerCustomAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private Spinner typesSpinner ;
    private Button submitBtn ;
    private final String PREFERENCES_NAME = "prefs" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        typesSpinner = (Spinner) findViewById(R.id.account_type_spinner);
        setUpAccountTypeSpinner();


        submitBtn = (Button)findViewById(R.id.submit_btn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ((EditText) findViewById(R.id.username_edittext)).getText().toString();
                String password = ((EditText) findViewById(R.id.password_edittext)).getText().toString();


                mAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Log.e("AKUMA", task.getException().getMessage());
                            Toast.makeText(RegisterActivity.this, "sign up error", Toast.LENGTH_SHORT).show();
                        } else {
                            String user_id = mAuth.getCurrentUser().getUid();
                            DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);

                            String name = ((EditText) findViewById(R.id.name_edittext)).getText().toString();
                            String mobile = ((EditText) findViewById(R.id.mobile_number_edittext)).getText().toString();
                            String accountType = (typesSpinner.getSelectedItemPosition() == 0) ? "Barber" : "Customer";

                            SharedPreferences.Editor sp = getSharedPreferences(PREFERENCES_NAME,MODE_PRIVATE).edit();
                            sp.putString("name",name) ;
                            sp.putString("ID",user_id);
                            sp.commit() ;

                            Map data = new HashMap();
                            data.put("ID",user_id);
                            data.put("name", name);
                            data.put("mobile", mobile);
                            data.put("Type", accountType);

                            current_user_db.setValue(data);
                        }
                    }
                });


            }
        });


        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
                    mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot != null) {
                                if (dataSnapshot.child("Type").getValue() != null) {
                                    if (dataSnapshot.child("Type").getValue().equals("Barber")) {
                                        Intent startBarberActivity = new Intent(RegisterActivity.this, BarberActivity.class);
                                        startActivity(startBarberActivity);
                                        finish();
                                    } else if (dataSnapshot.child("Type").getValue().equals("Customer")) {
                                        Intent startCustomerActivity = new Intent(RegisterActivity.this, CustomerActivity.class);
                                        startActivity(startCustomerActivity);
                                        finish();
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "there's been an error getting the data of the user", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {


                        }
                    });
                } else {
                    return;
                }
            }
        };

    }


    private void setUpAccountTypeSpinner() {
        ArrayList<String> types = new ArrayList<>();
        types.add("Barber");
        types.add("Customer");
        SpinnerCustomAdapter adapter = new SpinnerCustomAdapter(this,types ) ;
        typesSpinner.setAdapter(adapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListener);
    }
}
