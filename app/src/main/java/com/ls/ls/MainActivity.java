package com.ls.ls;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ls.ls.audiopush.AudioStream;
import com.ls.ls.player.VitamioVideo;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_vitamio).setOnClickListener(this);
        findViewById(R.id.btn_audiostream).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_vitamio:
                i = new Intent(this,VitamioVideo.class);
                startActivity(i);
                break;
            case R.id.btn_audiostream:
                i = new Intent(this,AudioStream.class);
                startActivity(i);
                break;
        }
    }
}
