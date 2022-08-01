package com.example.simple_pocket_money_entry.add;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ContentView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.simple_pocket_money_entry.R;

public class AddActivity extends AppCompatActivity {

    private static final String TAG = "flo###";

    private RadioButton type1, type2;
    private TextView dateView, categoryView, errorView;
    private EditText editContent, editAmount;
    private Button dateButton, categoryButton;

    private String type, date, content, category, amount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Button okButton = findViewById(R.id.ok_button);
        Button noButton = findViewById(R.id.no_button);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Checked();
                Log.d(TAG, "onClick: type is " + type);
                
                // if 작성이 완료되었으면
                DBHelper helper = new DBHelper(AddActivity.this);
                SQLiteDatabase db = helper.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put(TableInfo.COLUMN_NAME_TYPE, type);
                values.put(TableInfo.COLUMN_NAME_DATE, "08/01");
                values.put(TableInfo.COLUMN_NAME_CONTENT, "ㅁㅁ식당");
                values.put(TableInfo.COLUMN_NAME_CATEGORY, "식비");
                values.put(TableInfo.COLUMN_NAME_AMOUNT, "6,000원");

                long newRowId = db.insert(TableInfo.TABLE_NAME, null, values);
                if (newRowId == -1) {
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "데이터 추가 성공", Toast.LENGTH_SHORT).show();
                }
                db.close();

                Log.d(TAG, "onClick: db.getPath " + db.getPath());
                Log.d(TAG, "onClick: newRowId " + newRowId);
                
                // else if 작성되지 않은 사항이 있으면
                // error메세지 출력 및 애니메이션
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void Checked() {
        type1 = findViewById(R.id.add_type1);
        type2 = findViewById(R.id.add_type2);

        dateView = findViewById(R.id.date_content);
        dateButton = findViewById(R.id.date_picker_button);

        editContent = findViewById(R.id.breakdown_content);

        categoryView = findViewById(R.id.category_content);
        categoryButton = findViewById(R.id.category_picker_button);

        editAmount = findViewById(R.id.amount_content);


        // 수입 or 지출 선택
        if(type1.isChecked()) {
            type = getString(R.string.income);
        } else {
            type = getString(R.string.expense);
        }
    }

}
