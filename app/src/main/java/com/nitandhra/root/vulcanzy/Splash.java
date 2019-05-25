package com.nitandhra.root.vulcanzy;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

import es.dmoral.toasty.Toasty;

public class Splash extends AppCompatActivity{
    FirebaseDatabase database;
    DatabaseReference ref;
    DatabaseReference devref;
    String userid;
    String uname;
    static String deviceid;
    boolean val;
    boolean isConnected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        try {
            //GifImageView gf=(GifImageView)findViewById(R.id.gif);
            //gf.setVisibility(View.GONE);
            //Toast.makeText(getApplicationContext(),"App requires Internet Connection",Toast.LENGTH_LONG).show();
            ConnectivityManager cm =
                    (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            isConnected = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();
            if(!isConnected)
                showSnack(false);
            else
                showSnack(true);
            Log.e("bool",String.valueOf(isConnected));
        }
        catch (Exception e){}
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                notRegistered();
                if(!isConnected)
                    showSnack(false);
            }
        },1000);
    }

   public void notRegistered()
   {
       database = FirebaseDatabase.getInstance();
       ref = database.getReference("register");
       userid=Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);
       devref=database.getReference("devices");
       Splash.deviceid=userid;
       devref.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               if(dataSnapshot.getValue(Devic.class)!=null)
               {
                   Intent spla = new Intent(Splash.this, HomeActivity.class);
                   startActivity(spla);
                   finish();
                   val=false;
               }
               else
               {
                   Intent spla = new Intent(Splash.this, Signup.class);
                   startActivity(spla);
                   finish();
                   val=true;
               }
           }

           @Override
           public void onCancelled(DatabaseError error) {

           }
       });
   }
   /////////
    public void showSnack(boolean val)
    {
        if(val)
        {
            Toasty.success(this,"Connection Establised",Toast.LENGTH_LONG).show();
        }
        else
        {
            Toasty.error(this,"No Internet Connection",Toast.LENGTH_LONG).show();
        }
    }
   @Override
   protected void onResume() {
       super.onResume();
   }
}

