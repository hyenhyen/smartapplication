package com.monad.kpu.Activty;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.monad.kpu.Model.ResultScoreModel;
import com.monad.kpu.R;

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_info_ok;
    private EditText name, phone, email;
    private RadioButton boy, girl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_userinfo);

        init();
    }

    private void init() {
        LayoutBind();
    }

    private void LayoutBind() {
        btn_info_ok = (Button) findViewById(R.id.btn_info_ok);
        btn_info_ok.setOnClickListener(this);

        name = (EditText) findViewById(R.id.userinfo_name_edit);
        phone = (EditText) findViewById(R.id.userinfo_phone_edit);
        email = (EditText) findViewById(R.id.userinfo_email_edit);

        boy = (RadioButton) findViewById(R.id.boy);
        girl = (RadioButton) findViewById(R.id.girl);
    }

    private boolean getSex() {
        boolean result = true;
        if(boy.isChecked()) {
            result = true;
        }
        else if(girl.isChecked()) {
            result  = false;
        }

        return result;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_info_ok:
                Intent i = new Intent(this, UserScoreActivity.class);
                i.putExtra("name" , name.getText().toString());
                i.putExtra("phone" , phone.getText().toString());
                i.putExtra("email" , email.getText().toString());
                i.putExtra("sex", getSex());
                startActivity(i);
                finish();
                break;
        }
    }
}
