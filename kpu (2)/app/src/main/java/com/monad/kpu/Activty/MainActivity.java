package com.monad.kpu.Activty;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.monad.kpu.Adapter.ResultRecyclerAdapter;
import com.monad.kpu.HttpAsync;
import com.monad.kpu.Model.ResultScoreModel;
import com.monad.kpu.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, AdapterView.OnItemSelectedListener
{
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<ResultScoreModel> myDataset;
    private ResultRecyclerAdapter mAdapter;
    private Spinner spinner;
    private Button left_menu_all_model, left_menu_all_affiliation, left_menu_all_major, search;
    private Button btn2014, btn2015, btn2016;
    private CheckBox left_menu_check_all, left_menu_check_a, left_menu_check_b, left_menu_check_c, left_menu_check_d;
    private Toolbar toolbar;
    private DrawerLayout drawer;

    private TextView headTitle;
    private TextView main_member_count;
    private TextView rate;
    private TextView prev_member_count;
    private TextView additional;

    public static JSONObject data = null;

    private String requestYear = "2014";
    private String requestType = "GENERAL";
    private String requestMajor = "";
    private String _line = "상경계열";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

        setToolbar();
        LayoutBind();
        setListview();
    }

    private void setListview() {
        mRecyclerView = (RecyclerView) findViewById(R.id.main_list);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        myDataset = new ArrayList<>();
        mAdapter = new ResultRecyclerAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);

        if(data != null) {
            try {
                JSONArray arr = data.getJSONArray("result");
                int topGrade = 0;
                for(int i = 0 ; i< arr.length(); i++) {
                    JSONObject v = arr.getJSONObject(i);

                    String title = "정시" + v.getString("part") + "군\n" + v.getString("type") + "\n" + v.getString("major");
                    double myScore = getIntent().getExtras().getDouble("score", 0);
                    double difference = getIntent().getExtras().getDouble("score", 0) - Double.parseDouble(v.getString("last_avr"));
                    String diagnose = v.getString("grade");
                    String content = "최저 " + v.getString("last_min") + " 평균 " + v.getString("last_avr") + " 최고 " + v.getString("last_max");

                    ResultScoreModel model = new ResultScoreModel(
                            title,
                            (float)myScore,
                            (float)difference,
                            diagnose,
                            content);

                    model.major = v.getString("major");
                    model.type = v.getString("type");
                    model.year = v.getString("year");

                    myDataset.add(model);

                    int grade = 0;
                    switch(diagnose) {
                        case "A":
                            grade = 4;
                            break;
                        case "B":
                            grade = 3;
                            break;
                        case "C":
                            grade = 2;
                            break;
                        case "D":
                            grade = 1;
                            break;
                        default:
                            grade = 0;
                    }

                    if(grade > topGrade) {
                        topGrade = grade;
                        headTitle.setText("2016 입시결과 [" + v.getString("type") + "] " + v.getString("major"));
                        main_member_count.setText(v.getString("recruit"));
                        rate.setText(v.getString("compete"));
                        prev_member_count.setText(v.getString("apply"));
                        additional.setText(v.getString("addition"));
                    }
                }

            }catch(Exception e) {
                e.printStackTrace();
            }
        }

        mAdapter.setOnItemClickListener(new ResultRecyclerAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                ResultScoreModel m = myDataset.get(position);

                Intent i = new Intent(v.getContext(), ResultActivity.class);
                i.putExtra("major", m.major);
                i.putExtra("type", m.type);
                i.putExtra("score", m.getMyScore());

                startActivity(i);
            }

            @Override
            public void onItemLongClick(int position, View v) {

            }
        });
    }
    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void LayoutBind() {
        //spinner = (Spinner) findViewById(R.id.planets_spinner);

        search = (Button) findViewById(R.id.search);
        search.setOnClickListener(this);

        headTitle = (TextView) findViewById(R.id.headTitle);
        main_member_count = (TextView) findViewById(R.id.main_member_count);
        rate = (TextView) findViewById(R.id.rate);
        prev_member_count = (TextView) findViewById(R.id.prev_member_count);
        additional = (TextView) findViewById(R.id.additional);

        left_menu_check_all = (CheckBox) findViewById(R.id.left_menu_check_all);
        left_menu_check_a = (CheckBox) findViewById(R.id.left_menu_check_a);
        left_menu_check_b = (CheckBox) findViewById(R.id.left_menu_check_b);
        left_menu_check_c = (CheckBox) findViewById(R.id.left_menu_check_c);
        left_menu_check_d = (CheckBox) findViewById(R.id.left_menu_check_d);

        btn2014 = (Button) findViewById(R.id.btn2014);
        btn2015 = (Button) findViewById(R.id.btn2015);
        btn2016 = (Button) findViewById(R.id.btn2016);

        btn2014.setOnClickListener(this);
        btn2015.setOnClickListener(this);
        btn2016.setOnClickListener(this);


        left_menu_check_all.setOnClickListener(this);
        left_menu_check_a.setOnClickListener(this);
        left_menu_check_b.setOnClickListener(this);
        left_menu_check_c.setOnClickListener(this);
        left_menu_check_d.setOnClickListener(this);


        left_menu_all_model = (Button) findViewById(R.id.left_menu_all_model);
        left_menu_all_affiliation = (Button) findViewById(R.id.left_menu_all_affiliation);
        left_menu_all_major = (Button) findViewById(R.id.left_menu_all_major);
        left_menu_all_model.setOnClickListener(this);
        left_menu_all_affiliation.setOnClickListener(this);
        left_menu_all_major.setOnClickListener(this);
    }

    private void DialogModel() {
        final String items[] = { "일반학생전형", "수능우수자전형" };
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle("전체 전형");
        ab.setSingleChoiceItems(items, 0,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // 각 리스트를 선택했을때
                        if(items[whichButton].equals("일반학생전형"))
                            requestType = "GENERAL";
                        else
                            requestType = "SUSI";
                    }
                }).setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                }).setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Cancel 버튼 클릭시
                    }
                });
        ab.show();
    }

    private void DialogAffiliation() {
        final String items[] = { "상경계열", "디자인계열", "공학계열" };
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle("전체 계열");
        ab.setSingleChoiceItems(items, 0,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // 각 리스트를 선택했을때
                        _line = items[whichButton];
                        requestMajor = "선택 안함";
                    }
                }).setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // OK 버튼 클릭시 , 여기서 선택한 값을 메인 Activity 로 넘기면 된다.
                    }
                }).setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Cancel 버튼 클릭시
                    }
                });
        ab.show();
    }

    private void DialogMajor() {
        final String items[];
        if(_line.equals("상경계열")) {
            items = new String[]{ "선택 안함", "산업경영전공", "IT경영전공" };
        } else if (_line.equals("디자인계열")) {
            items = new String[]{ "선택 안함", "산업디자인전공", "디자인공학전공", "융합디자인전공" };
        } else {
            items = new String[]{ "선택 안함", "기계공학과", "기계설계공학과", "메카트로닉스공학과", "전자공학전공", "IT융합전공", "컴퓨터공학전공", "소프트웨어전공", "게임공학전공", "엔터테인먼트컴퓨팅전공", "신소재공학과", "생명화학공학과", "나노-광공학과", "에너지전기공학과"};
        }

        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle("전체 학과");
        ab.setSingleChoiceItems(items, 0,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // 각 리스트를 선택했을때
                        requestMajor = items[whichButton];

                    }
                }).setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // OK 버튼 클릭시 , 여기서 선택한 값을 메인 Activity 로 넘기면 된다.
                    }
                }).setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Cancel 버튼 클릭시
                    }
                });
        ab.show();
    }

    private void onClickCheckAll() {
        if(!left_menu_check_all.isChecked()) {
            left_menu_check_a.setChecked(false);
            left_menu_check_b.setChecked(false);
            left_menu_check_c.setChecked(false);
            left_menu_check_d.setChecked(false);
        } else {
            left_menu_check_a.setChecked(true);
            left_menu_check_b.setChecked(true);
            left_menu_check_c.setChecked(true);
            left_menu_check_d.setChecked(true);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            /* ------- 학과 필터 -------*/

            case R.id.left_menu_all_model:
                DialogModel();
                break;
            case R.id.left_menu_all_major:
                DialogMajor();
                break;
            case R.id.left_menu_all_affiliation:
                DialogAffiliation();
                break;
            /* ------ check ------ */
            case R.id.left_menu_check_all:
                onClickCheckAll();
                break;
            case R.id.left_menu_check_a:
                break;
            case R.id.left_menu_check_b:
                break;
            case R.id.left_menu_check_c:
                break;
            case R.id.left_menu_check_d:
                break;
            case R.id.btn2014:
                requestYear = "2014";
                Toast.makeText(MainActivity.this, "2014년을 선택하셨습니다", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn2015:
                requestYear = "2015";
                Toast.makeText(MainActivity.this, "2015년을 선택하셨습니다", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn2016:
                requestYear = "2016";
                Toast.makeText(MainActivity.this, "2016년을 선택하셨습니다", Toast.LENGTH_SHORT).show();
                break;
            case R.id.search: // 검색버튼
                search();
                break;
        }
    }

    private void search() {
        if(requestYear.equals("")) {
            Toast.makeText(MainActivity.this, "년도를 선택해주세요", Toast.LENGTH_SHORT).show();
            return;
        } else if(requestType.equals("")) {
            Toast.makeText(MainActivity.this, "전형을 선택해주세요", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestParams params = new RequestParams();
        params.put("score", getIntent().getExtras().getDouble("score", 0));
        params.put("year", requestYear);
        params.put("type", requestType);
        if(!requestMajor.equals("") && !requestMajor.equals("선택 안함"))
            params.put("major", requestMajor);
        params.put("a", left_menu_check_a.isChecked());
        params.put("b", left_menu_check_b.isChecked());
        params.put("c", left_menu_check_c.isChecked());
        params.put("d", left_menu_check_d.isChecked());

        HttpAsync.get("result_filter.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                if (statusCode == 200) {
                    MainActivity.data = response;
                    Intent i = new Intent(MainActivity.this, MainActivity.class);
                    i.putExtra("score", getIntent().getExtras().getDouble("score", 0));
                    //i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    finish();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                // 에러 코드별로 잘 코딩
                Toast.makeText(MainActivity.this, "데이터를 받아오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
