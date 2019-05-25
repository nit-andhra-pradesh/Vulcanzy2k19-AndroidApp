package com.nitandhra.root.vulcanzy;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ScheduleFragment extends Fragment {
FirebaseDatabase db;
DatabaseReference ref;
ProgressBar pb;
int i=0;
 @Nullable
 @Override
 public View onCreateView ( @NonNull LayoutInflater inflater , @Nullable ViewGroup container , @Nullable Bundle savedInstanceState ) {
  View view=inflater.inflate(R.layout.fragment_schedule,container,false );
  TextView tv1=(TextView)view.findViewById(R.id.date1);
     AboutVulcanzy.state="closed";
     TextView tv2=(TextView)view.findViewById(R.id.date2);
     TextView tv3=(TextView)view.findViewById(R.id.date3);
     db=FirebaseDatabase.getInstance();
     ref=db.getReference("schedule");
     pb=(ProgressBar)view.findViewById(R.id.schid);
     Typeface myTypeface = Typeface.createFromAsset(view.getContext().getAssets(), "CaviarDreams.ttf");
     tv1.setTypeface(myTypeface);
     tv2.setTypeface(myTypeface);
     tv3.setTypeface(myTypeface);
     TextView tv[]=new TextView[]{tv1,tv2,tv3};
     ref.addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
             for(DataSnapshot d:dataSnapshot.getChildren())
             {
                 tv[i].setText(Html.fromHtml("<pre><b>"+d.getKey()+"</b>"+   "<i>"+d.getValue()+"</i>"));
                 i=i+1;
             }
             pb.setVisibility(View.INVISIBLE);
         }

         @Override
         public void onCancelled(@NonNull DatabaseError databaseError) {

         }
     });


  return view;
 }

}
