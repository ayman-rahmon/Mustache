package com.fruitybeastsknowledge.mustache;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    FirebaseDatabase database ;
    Button regiterBtn ;
    Button loginBtn ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database =   database.getInstance();
//        DatabaseReference mref = database.getReference("User2").child("Name");
//        mref.setValue("khaled");
          mAuth  = FirebaseAuth.getInstance();
          regiterBtn = (Button) findViewById(R.id.regitesr_btn);
          regiterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent startRegisterActivity = new Intent(MainActivity.this, RegisterActivity.class) ;
                startActivity(startRegisterActivity);

            }
        });

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                   DatabaseReference mRef = database.getReference("Users").child(user.getUid());
                   mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(DataSnapshot dataSnapshot) {
                           if(dataSnapshot != null){
                               if(dataSnapshot.child("Type").getValue() != null) {
                                   if (dataSnapshot.child("Type").getValue().equals("Barber")) {
                                       Intent startBarberActivity = new Intent(MainActivity.this, BarberActivity.class);
                                       startActivity(startBarberActivity);
                                       finish();
                                   } else if (dataSnapshot.child("Type").getValue().equals("Customer")) {
                                       Intent startCustomerActivity = new Intent(MainActivity.this, CustomerActivity.class);
                                       startActivity(startCustomerActivity);
                                       finish();
                                   } else {
                                       Toast.makeText(MainActivity.this, "there's been an error getting the data of the user", Toast.LENGTH_SHORT).show();
                                   }
                               }
                       }

                       }

                       @Override
                       public void onCancelled(DatabaseError databaseError) {
                       }
                   });
                }else{
                    Toast.makeText(MainActivity.this,"there is no active user",Toast.LENGTH_SHORT).show();
                }
                return;
            }
        };


        loginBtn = (Button)findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username =  ((EditText)findViewById(R.id.username_edittext)).getText().toString() ;
                String password = ((EditText)findViewById(R.id.password_edittext)).getText().toString() ;

                mAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(!task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "sign in error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });






            }
        });
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
