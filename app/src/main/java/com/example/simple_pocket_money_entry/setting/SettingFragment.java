package com.example.simple_pocket_money_entry.setting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.simple_pocket_money_entry.DBHelper;
import com.example.simple_pocket_money_entry.R;
import com.example.simple_pocket_money_entry.TableInfo;
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity;

public class SettingFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preference_setting, rootKey);
    }

    @Override
    public boolean onPreferenceTreeClick(@NonNull Preference preference) {
        String key = preference.getKey();

        switch(key) {
            case "init_key":
                // 내역 초기화 다이얼로그 띄우기
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("내역 초기화를 진행하시겠습니까?");
                builder.setMessage("모든 내역이 삭제되며 다시 복구할 수 없습니다.");

                builder.setPositiveButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setNegativeButton("초기화", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 내역을 초기화하고 뒤로가기
                        DBHelper helper = new DBHelper(getActivity());
                        SQLiteDatabase db = helper.getReadableDatabase();
                        db.execSQL("DELETE FROM " + TableInfo.TABLE_NAME);

                        Toast.makeText(getActivity().getApplicationContext(), "내역이 초기화되었습니다.", Toast.LENGTH_SHORT).show();

                        dialog.dismiss();
                    }
                });

                AlertDialog initDialog = builder.create();
                initDialog.show();

                Button nButton = initDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                nButton.setTextColor(Color.parseColor("#104E82"));
                Button pButton = initDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                pButton.setTextColor(Color.parseColor("#104E82"));
                break;
            case "license_key":
                // 오픈소스 라이선스 화면으로 이동
                Intent intent = new Intent(getActivity(), OssLicensesMenuActivity.class);
                startActivity(intent);
                break;
            case "privacy_policy_key":
                // 개인정보처리방침 화면으로 이동
                Intent intent2 = new Intent(Intent.ACTION_VIEW);
                intent2.setData(Uri.parse("https://sites.google.com/view/simplepockeymoneyappbyeeeun/%ED%99%88"));
                startActivity(intent2);
                break;
        }
        return super.onPreferenceTreeClick(preference);
    }
}