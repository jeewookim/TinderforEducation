package com.example.jeewoo.tinderforeducation;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class ProfileActivity extends ActionBarActivity {
String st1;
    String st2;
    String st3;
    String st4;
    User person;
    String check;
    String myString;
    SharedPreferences mSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        try {

            ArrayList<User> totalusers = (ArrayList<User>) getIntent().getSerializableExtra("key");
            String email = getIntent().getStringExtra("user");
            int target =0;
            for (int x = 0; x < totalusers.size(); x++){
                if (totalusers.get(x).getEmail().equals(email)){
                    target = x;
                }
            }
            person = totalusers.get(target);



        } catch (Exception e) {
            Log.i("status is ", "bad");
        }
        mSettings = this.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = mSettings.edit();

        //The problem is that we are creating a SharedPref with the person's email... which means we can't check whether they have entered prev

        myString = mSettings.getString(person.getEmail(), "help");
        Log.i("settings" + mSettings.getString(person.getEmail(), "default"), "default");
        String temp = (myString+" ").split(" ")[1];
        Log.i("lOOK HERE" +myString, person.getName() + temp);

        Map<String,?> keys = mSettings.getAll();

        for(Map.Entry<String,?> entry : keys.entrySet()){
            Log.i("map values",entry.getKey() + ": " +
                    entry.getValue().toString());
        }


        if(temp!=null) {
            check = (myString+" ").split(" ")[0];
            Log.i("THIS WORKS ", check);
        }
        else{
            check = "p";
            Log.i("Checking ", check);
            }

        //Spinner code
        Spinner spinner1 = (Spinner) findViewById(R.id.statsp);

        final ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.status_array));
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter1);
        st1 = spinner1.getSelectedItem() + "";
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                st1 = parent.getSelectedItem() + "";
                person.setStatus(st1);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Spinner spinner2 = (Spinner) findViewById(R.id.subjectsp);
        final ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.subject_array));
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter2);
        st2 = spinner2.getSelectedItem() + "";
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                st2 = parent.getSelectedItem() + "";
                person.setSubject(st2);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        Spinner spinner3 = (Spinner) findViewById(R.id.whensp);
        final ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.when_array));
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(dataAdapter3);
        st3 = spinner3.getSelectedItem() + "";
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                st3 = parent.getSelectedItem() + "";
                person.setAvail(st3);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        Spinner spinner4 = (Spinner) findViewById(R.id.sizesp);
        final ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.size_array));
        dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(dataAdapter4);
        st4 = spinner4.getSelectedItem() + "";
        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                st4 = parent.getSelectedItem() + "";
                person.setSize(st4);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //EditText
        final EditText editN = (EditText)findViewById(R.id.nameet);
        final EditText editE = (EditText)findViewById(R.id.emailet);

        //display original profile
    if(check.equals(person.getEmail()))
     {
         editE.setText(check);
        editN.setText(temp);
//            //Find position of spinner of SharedPreferences
            for(int pos=0; pos < dataAdapter1.getCount(); pos++)
            {
                if((dataAdapter1.getItem(pos)+"").equals((myString+" ").split(" ")[2])) {
                    spinner1.setSelection(pos);
                    break;
                }
            }
         for(int pos=0; pos < dataAdapter2.getCount(); pos++)
         {
             if((dataAdapter2.getItem(pos)+"").equals((myString+" ").split(" ")[3])) {
                 spinner2.setSelection(pos);
                 break;
             }
         }
         for(int pos=0; pos < dataAdapter3.getCount(); pos++)
         {
             if((dataAdapter3.getItem(pos)+"").equals((myString+" ").split(" ")[4])) {
                 spinner3.setSelection(pos);
                 break;
             }
         }
         for(int pos=0; pos < dataAdapter4.getCount(); pos++)
         {
             if((dataAdapter4.getItem(pos)+"").equals((myString+" ").split(" ")[5])) {
                 spinner4.setSelection(pos);
                 break;
             }
         }
}
        //get rid of???? Buttons
        Button button1 = (Button) findViewById(R.id.save);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //edit info
                person.setName((editN.getText() + "").toLowerCase());
                person.setEmail((editE.getText() + "").toLowerCase());

                //updates editor
                editor.putString(person.getEmail(), person.getEmail() + " " + person.getName() + " " + person.getStatus() + " " + person.getSubject() + " " + person.getAvail() + " " + person.getSize());
                editor.commit();
                editor.apply();
                myString = mSettings.getString(person.getEmail(), "blank");
                Log.i("settings1" , myString);


                Log.i("settings1" + mSettings.getString(person.getEmail(), "default"), "default");


                Log.i("name1" + person.getName(), "email" + person.getEmail());
                Log.i("status1" + person.getStatus(), "subject" + person.getSubject());
                Log.i("avail1" + person.getAvail(), "size" + person.getSize());
                Log.i("editor1" + mSettings.getString((person.getEmail()), "default"), "lll");
            }
        });
        Button button2 = (Button) findViewById(R.id.register);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //make intent and pass it
                person.setName((editN.getText() + "").toLowerCase());
                person.setEmail((editE.getText() + "").toLowerCase());

                editor.putString(person.getEmail(), person.getEmail() + " " + person.getName() + " " + person.getStatus() + " " + person.getSubject() + " " + person.getAvail() + " " + person.getSize());
                editor.commit();
                editor.apply();
                myString = mSettings.getString(person.getEmail(), "blank");
                Log.i("settings2", myString);

                Log.i("settings2" + mSettings.getString(person.getEmail(), "default"), "default");

                Log.i("name2" + person.getName(), "email" + person.getEmail());
                Log.i("status2" + person.getStatus(), "subject" + person.getSubject());
                Log.i("avail2" + person.getAvail(), "size" + person.getSize());
                Log.i("editor2" + mSettings.getString((person.getEmail()), "default"), "lll");

                Intent intent = new Intent(ProfileActivity.this, MatchesActivity.class);
                intent.putExtra("currentuser", person.getEmail());
                startActivity(intent);

            }
        });
    }
}
