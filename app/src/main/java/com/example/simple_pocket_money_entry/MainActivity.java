package com.example.simple_pocket_money_entry;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.simple_pocket_money_entry.chart.ChartFragment;
import com.example.simple_pocket_money_entry.home.HomeFragment;
import com.example.simple_pocket_money_entry.list.ListFragment;
import com.example.simple_pocket_money_entry.setting.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    LinearLayout homeLayout;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeLayout = findViewById(R.id.home_ly);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        BottomNavigationListener();
        bottomNavigationView.setSelectedItemId(R.id.tab_home);  //맨 처음 시작할 탭 설정
    }

    // 하단 네비게이션 바의 아이콘을 선택하면 해당 페이지로 이동
    private void BottomNavigationListener() {
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.tab_home: {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.home_ly, new HomeFragment())
                                .commit();
                        return true;
                    }
                    case R.id.tab_list: {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.home_ly, new ListFragment())
                                .commit();
                        return true;
                    }
                    case R.id.tab_chart: {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.home_ly, new ChartFragment())
                                .commit();
                        return true;
                    }
                    case R.id.tab_setting: {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.home_ly, new SettingFragment())
                                .commit();                        return true;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("정말 종료하시겠습니까?");
        builder.setPositiveButton("종료", new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("취소", new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

        Button nButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        nButton.setTextColor(Color.parseColor("#104E82"));
        Button pButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        pButton.setTextColor(Color.parseColor("#104E82"));
    }
}