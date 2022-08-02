package com.example.simple_pocket_money_entry.add;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ContentView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.simple_pocket_money_entry.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "flo###";

    private TextView dateView, categoryView;
    private boolean isChecked = false;
    private String type, date, content, category, amount;

    Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener myDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDate();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        
        dateView = findViewById(R.id.date_content);
        categoryView = findViewById(R.id.category_content);
        Button dateButton = findViewById(R.id.date_picker_button);
        Button categoryButton = findViewById(R.id.category_picker_button);
        Button okButton = findViewById(R.id.ok_button);
        Button noButton = findViewById(R.id.no_button);

        Calendar calendar = Calendar.getInstance();
        DateFormat format = DateFormat.getDateInstance(DateFormat.DEFAULT);
        format.setTimeZone(calendar.getTimeZone());
        String todayDate = format.format(calendar.getTime());

        dateView.setText(todayDate);
        date = dateView.getText().toString().trim();
        category = "식비";

        dateButton.setOnClickListener(this);
        categoryButton.setOnClickListener(this);
        okButton.setOnClickListener(this);
        noButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.date_picker_button:       // 날짜 선택
                new DatePickerDialog(AddActivity.this,
                        myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH))
                        .show();
                break;
            case R.id.category_picker_button:   // 카테고리 선택
                showChooseCategoryDialog(this);
                break;
            case R.id.ok_button:
                Checked();
                Log.d(TAG, "onClick: " + type + date + content + category + amount);

                if(isChecked == true && (content != null) && (amount != null)) {
                    DBHelper helper = new DBHelper(AddActivity.this);
                    SQLiteDatabase db = helper.getWritableDatabase();

                    ContentValues values = new ContentValues();
                    values.put(TableInfo.COLUMN_NAME_TYPE, type);
                    values.put(TableInfo.COLUMN_NAME_DATE, date);
                    values.put(TableInfo.COLUMN_NAME_CONTENT, content);
                    values.put(TableInfo.COLUMN_NAME_CATEGORY, category);
                    values.put(TableInfo.COLUMN_NAME_AMOUNT, amount);

                    long newRowId = db.insert(TableInfo.TABLE_NAME, null, values);
                    if (newRowId == -1) {
                        customToastView("내역을 추가하지 못했습니다.");
                    } else {
                        customToastView("내역을 추가하였습니다.");
                    }
                    db.close();
                    onBackPressed();
                } else {
                    customToastView("작성되지 않은 사항이 있습니다.");
                }
                break;
            case R.id.no_button:
                onBackPressed();
                break;
        }
    }

    public void Checked() {
        RadioButton type1 = findViewById(R.id.add_type1);
        RadioButton type2 = findViewById(R.id.add_type2);
        EditText editContent = findViewById(R.id.breakdown_content);
        EditText editAmount = findViewById(R.id.amount_content);

        // 수입 or 지출 선택
        if(type1.isChecked()) {
            type = getString(R.string.income);
            isChecked = true;
        } else if(type2.isChecked()) {
            type = getString(R.string.expense);
            isChecked = true;
        }

        // 내용 입력
        if (!editContent.getText().toString().equals("")) {
            content = editContent.getText().toString().trim();
        } else {
            content = null;
        }

        // 금액 입력
        if (!editAmount.getText().toString().equals("")) {
            amount = editAmount.getText().toString().trim();
        } else {
            amount = null;
        }
    }

    private void updateDate() {
        String myFormat = "yyyy.MM.dd.";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);
        dateView.setText(sdf.format(myCalendar.getTime()));
        date = dateView.getText().toString().trim();
    }

    private void showChooseCategoryDialog(Context context) {
        GridView gridView = new GridView(context);
        ChooseCategoryDialogAdapter adapter = new ChooseCategoryDialogAdapter(context);
        gridView.setAdapter(new ChooseCategoryDialogAdapter(context));
        gridView.setNumColumns(4);
        gridView.setGravity(Gravity.CENTER);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(gridView);
        builder.setTitle(R.string.choose_category_dialog_title);
        AlertDialog dialog = builder.show();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                category = adapter.getItem(position).toString().trim();
                categoryView.setText(category);
                dialog.dismiss();
            }
        });
    }

    private void customToastView(String text) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_board, (ViewGroup) findViewById(R.id.toast_layout_root));
        TextView textView = layout.findViewById(R.id.textboard);
        textView.setText(text);

        Toast toastView = Toast.makeText(getApplicationContext(),text, Toast.LENGTH_SHORT);
        toastView.setView(layout);
        toastView.show();
    }

    // 포커스를 가진 뷰 외의 영역을 터치하면 키보드가 내려감
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View focusView = getCurrentFocus();
        if (focusView != null) {
            Rect rect = new Rect();
            focusView.getGlobalVisibleRect(rect);
            int x = (int) ev.getX(), y = (int) ev.getY();
            if (!rect.contains(x, y)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null)
                    imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
                focusView.clearFocus();
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}
