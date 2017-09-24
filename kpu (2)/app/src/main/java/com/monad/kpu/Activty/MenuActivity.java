package com.monad.kpu.Activty;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.monad.kpu.R;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener{
    private Button guideline, score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_menu);

        init();
    }

    private void init() {
        LayoutBind();
    }

    private void LayoutBind() {
        guideline = (Button) findViewById(R.id.guideline);
        score = (Button) findViewById(R.id.score);
        guideline.setOnClickListener(this);
        score.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.guideline:
                Intent introduction = new Intent(this, introductionActivity.class);
                startActivity(introduction);
                break;
            case R.id.score:
                Intent i = new Intent(this, UserInfoActivity.class);
                startActivity(i);
                break;
        }
    }
}
