package com.example.jeewoo.tinderforeducation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.parse.Parse;

import java.util.ArrayList;
import java.util.Map;

public class MatchesActivity extends ActionBarActivity {
    SharedPreferences mSettings;
    String myString;
    ArrayList<User> sortedmatches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        // Construct the data source
        //final ArrayList<User> arrayOfUsers = new ArrayList<User>();
        final ArrayList<String[]> arrayOfUsers = new ArrayList<String[]>();
        // Create the adapter to convert the array to views
        //final UsersAdapter adapter = new UsersAdapter(this, arrayOfUsers);
        final ArrayAdapter adapter = new ArrayAdapter(this, R.layout.linear_layout, arrayOfUsers);
        // Attach the adapter to a ListView
        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

//        String[] array = new String[]{};
//
//        for (User x :totalusers){
//            array[array.length-1] = (x.getEmail());
//        }
//        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.linear_layout, array);
//
//        ListView listView = (ListView) findViewById(R.id.listView);
//        listView.setAdapter(adapter);

        ArrayList<User> totalusers = new ArrayList<User>();

        String email = getIntent().getStringExtra("currentuser");
        mSettings = this.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = mSettings.edit();

        myString = mSettings.getString(email, "help");

        String status = (myString+" ").split(" ")[2];
        String name = (myString+" ").split(" ")[1];
        String subject = (myString+" ").split(" ")[3];
        String avail = (myString+" ").split(" ")[4];
        String size = (myString+" ").split(" ")[5];



        Map<String,?> keys = mSettings.getAll();
        for(Map.Entry<String,?> entry : keys.entrySet()){
            User temp = new User(entry.getKey());
            String ms = mSettings.getString(entry.getKey(), "help");
            temp.setName((ms + " ").split(" ")[1]);
            temp.setStatus((ms + " ").split(" ")[2]);
            temp.setSubject((ms + " ").split(" ")[3]);
            temp.setAvail((ms + " ").split(" ")[4]);
            temp.setSize((ms + " ").split(" ")[5]);
            totalusers.add(temp);

        }

        ArrayList<User> temparray = new ArrayList<User>();
        for (User a : totalusers){
            if (email.equals(a.getEmail())){

            }
            else if (status.equals("Tutor")){
                if (a.getStatus().equals("Tutee")){
                    temparray.add(a);
                }
            }
            else if(status.equals("Tutee")){
                if(a.getStatus().equals("Tutor")){
                    temparray.add(a);
                }
            }
            else if(status.equals("Study")){
                if (a.getStatus().equals("Study")){
                    temparray.add(a);
                }
            }
            Log.i("students status", status);

        }

        ArrayList<User> matches = new ArrayList<User>();
        for (User b:temparray){
            if (subject.equals(b.getSubject()))
                matches.add(b);
        }

       for (User c:matches){
           if(avail.equals(c.getAvail())){
               c.setScore(c.getScore()+1);
           }
           if(size.equals(c.getSize()))
           {
               c.setScore(c.getScore()+1);
           }
       }

        sortedmatches = new ArrayList<User>();

            for(User x : matches)
            {
                if(x.getScore()==2)
                {
                    sortedmatches.add(x);
                }

            }
            for(User x : matches)
            {
                if(x.getScore()==1)
                {
                    sortedmatches.add(x);
                }
            }
            for(User x : matches)
            {
                if(x.getScore()==0)
                {
                    sortedmatches.add(x);
                }
            }
        Log.i("size of matches " , matches.size() +"");


        //for (User x:sortedmatches) {
       //     adapter.add(x);
        //}
        for(User x: sortedmatches) {
            Log.i("sorted matches", x.getName());
            adapter.addAll(x.getName(), x.getEmail(), x.getScore() + "/3");
            Log.i("size", sortedmatches.size() + "");
        }

        Button logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

           Intent intent = new Intent(MatchesActivity.this, WelcomeActivity.class);
               startActivity(intent);
            }
        });

    }



public class UsersAdapter extends ArrayAdapter<User> {
    public UsersAdapter(Context context, ArrayList<User> users) {
        super(context, R.layout.linear_layout, users);
    }
}}