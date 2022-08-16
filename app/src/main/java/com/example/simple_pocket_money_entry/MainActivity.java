package com.example.simple_pocket_money_entry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;

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

    private void showSetting() {
//        SharedPreferences setting;
//        setting = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
//        setting.registerOnSharedPreferenceChangeListener((sp, key) -> {
//            Log.d("flo####", "클릭된 Preference의 key는 " + key);
//
//            if (sp.getBoolean(key, false)) {
//                Log.d("@@@", key + " on");
//            } else {
//                Log.d("@@@", key + " off");
//            }
//        });
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.home_ly, fragment).commit();      // Fragment로 사용할 MainActivity내의 layout공간을 선택합니다.
    }
}