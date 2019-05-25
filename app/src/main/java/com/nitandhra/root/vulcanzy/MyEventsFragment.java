package com.nitandhra.root.vulcanzy;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import es.dmoral.toasty.Toasty;
public class MyEventsFragment extends Fragment
{
    ExpandableListView lv;
    ExpandableListAdapter expandableListAdapter;
    HashMap<String, List<String>> expandableListDetail;
    List<String> expandableListTitle;
    TextView refresh;
    FirebaseDatabase database;
    DatabaseReference ref;
    String uid;
    DatabaseReference eref,devref;
    List<List<String>> l;
    List<String> biotech,civil,cse,eee,ece,mech,mme,chem,music,magazine,dsh,dnd,pnp;
    List<String> lll;
    int i=0;
    boolean isConnected;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.fragment_my_events, container,false);
        AboutVulcanzy.state="closed";
        lv=(ExpandableListView) view.findViewById(R.id.exp_list_view);
        expandableListDetail = new HashMap<String, List<String>>();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("register");
        refresh=(TextView)view.findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.frame_container,new MyEventsFragment()).commit();
            }
        });
        ConnectivityManager cm =
                (ConnectivityManager)view.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if(!isConnected)
            showSnack(false);
        add();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter=new CustomExpandableListAdapter(this.getContext(),expandableListTitle,expandableListDetail);
        setListener();
        lv.setAdapter(expandableListAdapter);
        lv.setGroupIndicator(null);
        return view;
    }
    public void add()
    {
        biotech=new ArrayList<>();
        civil=new ArrayList<>();
        cse=new ArrayList<String>();
        ece=new ArrayList<String>();
        eee=new ArrayList<String>();
        mech=new ArrayList<String>();
        mme=new ArrayList<>();
        chem=new ArrayList<>();
        music=new ArrayList<>();
        magazine=new ArrayList<>();
        dsh=new ArrayList<>();
        dnd=new ArrayList<>();
        pnp=new ArrayList<>();
        l=new ArrayList<List<String>>();
        l.add(biotech);l.add(civil);l.add(cse);l.add(eee);l.add(ece);l.add(mech);l.add(mme);
        l.add(chem);l.add(music);l.add(magazine);l.add(dsh);l.add(dnd);l.add(pnp);
        String branch[]=new String[]{"biotech","civil","cse","eee","ece","mech","mme","chem","music","magazine","dsh","dd","pnp"};
        devref=database.getReference("devices");
        String  userid=Settings.Secure.getString(getContext().getContentResolver(),Settings.Secure.ANDROID_ID);
        devref.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Devic de=dataSnapshot.getValue(Devic.class);
                uid=de.getUsername();
                ref.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User u=dataSnapshot.getValue(User.class);
                        for(i=0;i<branch.length;i++)
                            addall(u.getUsername(),branch[i],l.get(i));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        for(i=0;i<l.size();i++)
        {
            expandableListDetail.put(branch[i],l.get(i));
        }

    }
    public void addall(String n,String b,List<String> li)
    {
            eref = database.getReference(b);
            eref.child(n).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot uniqueKeySnapshot : dataSnapshot.getChildren()){
                        String s=uniqueKeySnapshot.getKey().toString();
                        if(!s.equals("username"))
                        li.add(s);
                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            });


    }
    public void showSnack(boolean val)
    {
        if(val)
        {
            Toasty.success(getContext(),"Connection Establised",Toast.LENGTH_LONG).show();
        }
        else
        {
            Toasty.error(getContext(),"No Internet Connection",Toast.LENGTH_LONG).show();
        }
    }
    void setListener() {
        // This listener will show toast on group click
        lv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView listview, View view,
                                        int group_pos, long id) {

                return false;
            }
        });

        // This listener will expand one group at one time
        // You can remove this listener for expanding all groups
        lv
                .setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

                    // Default position
                    int previousGroup = -1;

                    @Override
                    public void onGroupExpand(int groupPosition) {
                        if (groupPosition != previousGroup)

                            // Collapse the expanded group
                            lv.collapseGroup(previousGroup);
                        previousGroup = groupPosition;
                    }

                });

        // This listener will show toast on child click
        lv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView listview, View view,
                                        int groupPos, int childPos, long id) {
                return false;
            }
        });
    }
}
