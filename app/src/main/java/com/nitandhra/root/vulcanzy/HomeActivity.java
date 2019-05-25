package com.nitandhra.root.vulcanzy;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity
 implements NavigationView.OnNavigationItemSelectedListener {

 DrawerLayout drawer;
 LinearLayout ll;
 Fragment myfrag;

 @Override
 protected void onCreate ( Bundle savedInstanceState ) {
  super.onCreate( savedInstanceState );
  setContentView( R.layout.activity_home );
  Toolbar toolbar = ( Toolbar ) findViewById( R.id.toolbar );
  toolbar.setTitle("Vulcanzy2K19");
  setSupportActionBar( toolbar );
  drawer = ( DrawerLayout ) findViewById( R.id.drawer_layout );
  Display disp=getWindowManager().getDefaultDisplay();
  Point size=new Point();
  disp.getSize(size);
  int height=size.y;

  ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
   this , drawer , toolbar , R.string.navigation_drawer_open , R.string.navigation_drawer_close );
  drawer.addDrawerListener( toggle );
  toggle.syncState();
  TextView dev=(TextView)findViewById(R.id.dev);
  dev.setOnClickListener(new View.OnClickListener() {
   @Override
   public void onClick(View v) {
    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new DevFragment()).commit();
    drawer.closeDrawer(GravityCompat.START);
   }
  });
  try {
   Intent it = getIntent();
   String extra = it.getStringExtra("Event back");
   if (extra.equals("yes"))
    eventBack();
  }
  catch (Exception ex) {
   getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new AboutVulcanzy(),"ABOUT").commit();
  }
  NavigationView navigationView = ( NavigationView ) findViewById( R.id.nav_view );
  navigationView.setNavigationItemSelectedListener( this );
  ViewGroup.LayoutParams param=navigationView.getHeaderView(0).getLayoutParams();
  param.height=height/5;
  navigationView.getHeaderView(0).setLayoutParams(param);
 }
 public void eventBack()
 {
 myfrag=new HomeFragment();
 go();
 }
 @Override
 public void onBackPressed () {
  if (AboutVulcanzy.state.equals("open")) {
   DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
   if (drawer.isDrawerOpen(GravityCompat.START)) {
    drawer.closeDrawer(GravityCompat.START);
   } else {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setMessage("Are you sure you want to Exit");
    builder.setCancelable(false);
    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

     @Override
     public void onClick(DialogInterface dialog, int which) {
      finish();
     }
    });
    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

     @Override
     public void onClick(DialogInterface dialog, int which) {
      dialog.cancel();
     }
    });
    builder.create();
    builder.show();
   }
  }
  else
  {
   getSupportFragmentManager().beginTransaction().replace( R.id.frame_container,new AboutVulcanzy() ).commit();

  }
 }

 @Override
 public boolean onCreateOptionsMenu ( Menu menu ) {
  // Inflate the menu; this adds items to the action bar if it is present.
  getMenuInflater().inflate( R.menu.home , menu );
  return true;
 }

 @Override
 public boolean onOptionsItemSelected ( MenuItem item ) {
  // Handle action bar item clicks here. The action bar will
  // automatically handle clicks on the Home/Up button, so long
  // as you specify a parent activity in AndroidManifest.xml.
  int id = item.getItemId();

  //noinspection SimplifiableIfStatement
  if ( id == R.id.action_logout ) {
  finish();
   return true;
  }

  return super.onOptionsItemSelected( item );
 }

 @SuppressWarnings( "StatementWithEmptyBody" )
 @Override
 public boolean onNavigationItemSelected ( MenuItem item ) {
  // Handle navigation view item clicks here.

  int id = item.getItemId();
  switch ( id )
  {
   case R.id.nav_my_events:
    myfrag=new MyEventsFragment();
    go();
    break;
   case R.id.nav_profile:
    myfrag=new Profile();
    go();
    break;
   case R.id.nav_home :
    myfrag=new HomeFragment();
    go();
    break;
   case R.id.nav_schedule :
    myfrag=new ScheduleFragment();
    go();
    break;
   case R.id.nav_sponsors :
    myfrag=new SponsorsFragment();
    go();
    break;
   case R.id.nav_about_vulcanzy :
    myfrag=new AboutVulcanzyFragment();
    go();
    break;
   case R.id.nav_contact :
    myfrag=new ContactUsFragment();
    go();
    break;
   case R.id.nav_gallery :
    myfrag=new GalleryFragment();
    go();
    break;
  }
    drawer.closeDrawer( GravityCompat.START );
  return true;
 }
 public void go() {
  if (myfrag != null) {
   getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, myfrag).
           setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
           .commit();
   myfrag = null;
  }
 }

}
