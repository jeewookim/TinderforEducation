package com.example.jeewoo.tinderforeducation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class WelcomeActivity extends ActionBarActivity {
    SharedPreferences mSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mSettings = this.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = mSettings.edit();




        Button button = (Button) findViewById(R.id.start);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //make intent and pass it
                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        ImageView iv = (ImageView)findViewById(R.id.logo);
        iv.setImageResource(R.mipmap.openbook);

    }

}
