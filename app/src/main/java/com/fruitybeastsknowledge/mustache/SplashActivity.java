package com.fruitybeastsknowledge.mustache;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        try {
            Thread.sleep(3000);
             // for now it will luanch what ever i want it to launch
            // main Activity will be login / register activity ...
            if(isConnectedToInternet()){
     // check if there is an account or not and then check the account type and download the wanted data ..

                Intent startMainActivity = new Intent(SplashActivity.this,MainActivity.class) ;
                finish();
                startActivity(startMainActivity);
            }else {
                connectToInternetPrompt();
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


private boolean isConnectedToInternet(){
    ConnectivityManager connectivityManager
            = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
}


    private void connectToInternetPrompt(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                System.exit(1);
                /*
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                */
            }
        });
        builder.setTitle("sequrıty prompt");
        builder.setMessage("please connect to the internet.");
        AlertDialog dlg = builder.create();
        dlg.show();
    }



}
