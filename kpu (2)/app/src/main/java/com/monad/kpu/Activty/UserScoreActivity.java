package com.monad.kpu.Activty;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.monad.kpu.HttpAsync;
import com.monad.kpu.Model.MyInfo;
import com.monad.kpu.Model.ResultScoreModel;
import com.monad.kpu.R;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class UserScoreActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_score;
    private String phone, name, email;
    private RadioButton science, science2, job, job2, society, society2, math, math1;
    private boolean sex;

    private EditText etKorean, etEnglish, etMath, etEtc1, etEtc2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_userscore);
        Intent i = getIntent();
        phone = i.getExtras().getString("name");
        name = i.getExtras().getString("phone");
        email = i.getExtras().getString("email");
        sex = i.getExtras().getBoolean("sex");
        init();

    }

    private void init() {
        LayoutBind();
    }

    private void LayoutBind() {
        btn_score = (Button) findViewById(R.id.btn_score);
        btn_score.setOnClickListener(this);
        science = (RadioButton) findViewById(R.id.science);
        science2 = (RadioButton) findViewById(R.id.science2);
        job = (RadioButton) findViewById(R.id.job);
        job2 = (RadioButton) findViewById(R.id.job2);
        society = (RadioButton) findViewById(R.id.society);
        society2 = (RadioButton) findViewById(R.id.society2);
        math = (RadioButton) findViewById(R.id.math_1);
        math1 = (RadioButton) findViewById(R.id.math_2);

        etKorean = (EditText) findViewById(R.id.etKorean);
        etEnglish = (EditText) findViewById(R.id.etEnglish);
        etMath = (EditText) findViewById(R.id.etMath);
        etEtc1 = (EditText) findViewById(R.id.etEtc1);
        etEtc2 = (EditText) findViewById(R.id.etEtc2);

        science.setOnClickListener(this);
        science2.setOnClickListener(this);
        job.setOnClickListener(this);
        job2.setOnClickListener(this);
        society.setOnClickListener(this);
        society2.setOnClickListener(this);
        math1.setOnClickListener(this);
        math.setOnClickListener(this);
    }

    private String getMathOption() {
        String result = "";
        if(math.isChecked()) {
            result = "math";
        } else if (math1.isChecked()) {
            result = "math1";
        }

        return result;
    }

    private String getEtcOption1() {
        String result = "";

        if(science.isChecked()) {
            result = "science";
        } else if(job.isChecked()) {
            result = "job";
        } else if(math.isChecked()) {
            result = "math";
        }

        return result;
    }

    private String getEtcOption2() {
        String result = "";
        if(science.isChecked()) {
            result = "science1";
        } else if(job.isChecked()) {
            result = "job1";
        } else if(math1.isChecked()) {
            result = "math1";
        }

        return result;
    }

    private void setData() {

        // TODO : validation

        MyInfo data = new MyInfo();
        data.setName(name);
        data.setPhone(phone);
        data.setEmail(email);
        data.setMath(getMathOption());
        data.setEtc2(getEtcOption1());
        data.setEtc2(getEtcOption2());
        data.setSex(sex);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btn_score:
                RequestParams params = new RequestParams();

                final double nor_engineer;
                int kor = Integer.parseInt(etKorean.getText().toString());
                int math = Integer.parseInt(etMath.getText().toString());
                int eng = Integer.parseInt(etEnglish.getText().toString());
                int etc1 = Integer.parseInt(etEtc1.getText().toString());
                int etc2 = Integer.parseInt(etEtc2.getText().toString());
                if(getMathOption().equals("math")) { //수리가나로 나눠졌기때문에 각각 나눠야함..
                    nor_engineer = (kor * 0.8) + ((math * 1.2) * 1.1) + (eng * 1.2) + (etc1 * 0.4) + (etc2 * 0.4);
                } else {
                    nor_engineer = (kor * 0.8) + (math * 1.2) + (eng * 1.2) + (etc1 * 0.4) + (etc2 * 0.4);
                }
                params.put("score", nor_engineer); //nor_engineer값 저장하구

                HttpAsync.get("result.php", params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                        if (statusCode == 200) {
                            MainActivity.data = response;
                            Intent i = new Intent(UserScoreActivity.this, MainActivity.class);
                            i.putExtra("score", nor_engineer); //사용자 점수를 넘겨줘야하는부분
                            startActivity(i);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        // 에러 코드별로 잘 코딩
                        Toast.makeText(UserScoreActivity.this, "데이터를 받아오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
                    }
                });

                break;
        }
    }
}
