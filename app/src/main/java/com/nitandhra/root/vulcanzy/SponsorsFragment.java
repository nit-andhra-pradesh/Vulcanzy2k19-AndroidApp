package com.nitandhra.root.vulcanzy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SponsorsFragment extends Fragment {


 @Nullable
 @Override
 public View onCreateView ( @NonNull LayoutInflater inflater , @Nullable ViewGroup container , @Nullable Bundle savedInstanceState )
 {

  View vi=inflater.inflate(R.layout.fragment_sponsors,container,false );
  AboutVulcanzy.state="closed";
  String[] titles = new String[]{};
  Integer[] images = new Integer[]{};
  ListView listView;
  List<RowItem> rowItems;
  ArrayAdapter<String> listAdapter;
  listView=(ListView)vi.findViewById(R.id.notif_list);
  ArrayList<String> li=new ArrayList<>();
  FirebaseDatabase db=FirebaseDatabase.getInstance();
  listAdapter = new ArrayAdapter<String>(vi.getContext(), R.layout.simplerow,li);
  DatabaseReference ref=db.getReference("notif");
  ref.addListenerForSingleValueEvent(new ValueEventListener() {
   @Override
   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
    for(DataSnapshot d:dataSnapshot.getChildren())
    {
     String msg=d.getValue().toString();
     StringBuilder key=new StringBuilder();
     String val="";
     int i=0;
     for(Character c:msg.toCharArray())
     {
      if(c!=':')
      {
       key.append(c);
      }
      else
      {
       break;
      }
      i=i+1;
     }
     val=msg.substring(i+1);
     listAdapter.add(Html.fromHtml("<b>"+key+"</b><br><br>"+val).toString());
    }
   }
   @Override
   public void onCancelled(@NonNull DatabaseError databaseError) {
   }
  });
listView.setAdapter(listAdapter);
  return vi;
 }

}
