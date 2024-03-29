package com.example.simple_pocket_money_entry.add;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.simple_pocket_money_entry.DBHelper;
import com.example.simple_pocket_money_entry.R;
import com.example.simple_pocket_money_entry.TableInfo;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView fullDateView, categoryView;
    private String id, type, date, fullDate, content, category, amount, fullAmount;
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
        setContentView(R.layout.activity_edit);

        fullDateView = findViewById(R.id.date_content);
        categoryView = findViewById(R.id.category_content);
        EditText editContent = findViewById(R.id.breakdown_content);
        EditText editAmount = findViewById(R.id.amount_content);
        RadioButton type1 = findViewById(R.id.add_type1);
        RadioButton type2 = findViewById(R.id.add_type2);
        Button dateButton = findViewById(R.id.date_picker_button);
        Button categoryButton = findViewById(R.id.category_picker_button);
        Button okButton = findViewById(R.id.ok_button);
        Button noButton = findViewById(R.id.no_button);
        Button deleteButton = findViewById(R.id.delete_button);
        
        // ListAdapter에서 넣어 전한 intent 받기
        id = getIntent().getStringExtra("id");
        date = getIntent().getStringExtra("date");
        fullDate = getIntent().getStringExtra("fullDate");
        content = getIntent().getStringExtra("content");
        category = getIntent().getStringExtra("category");
        amount = getIntent().getStringExtra("amount");
        fullAmount = getIntent().getStringExtra("fullAmount");
        
        fullDateView.setText(fullDate);
        categoryView.setText(category);
        editContent.setText(content);
        editAmount.setText(amount);

        // 금액에 '-'가 있으면 지출, 아니면 수입 선택
        if(fullAmount.contains("-")) {
            type2.setChecked(true);
        } else {
            type1.setChecked(true);
        }

        dateButton.setOnClickListener(this);
        categoryButton.setOnClickListener(this);
        okButton.setOnClickListener(this);
        noButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);

        editAmount.addTextChangedListener(new CustomTextWatcher(editAmount));   // 천단위 콤마 설정
    }

    @Override
    public void onClick(View view) {
        DBHelper helper = new DBHelper(EditActivity.this);
        SQLiteDatabase db = helper.getWritableDatabase();

        switch(view.getId()) {
            case R.id.date_picker_button:
                new DatePickerDialog(EditActivity.this,
                        myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH))
                        .show();
                break;
            case R.id.category_picker_button:
                showChooseCategoryDialog(this);
                break;
            case R.id.ok_button:
                Checked();

                if((content != null) && (fullAmount != null)) {
                    if(type.equals("수입")) {
                        amount = fullAmount.replaceAll(",", "");    // ex) 6000
                        fullAmount = fullAmount + "원";          // ex) 6,000원
                    } else if(type.equals("지출")) {
                        amount = fullAmount.replaceAll(",", "");
                        fullAmount = "- " + fullAmount + "원";
                    }

                    ContentValues values = new ContentValues();
                    values.put(TableInfo.COLUMN_NAME_TYPE, type);
                    values.put(TableInfo.COLUMN_NAME_DATE, date);
                    values.put(TableInfo.COLUMN_NAME_FULL_DATE, fullDate);
                    values.put(TableInfo.COLUMN_NAME_CONTENT, content);
                    values.put(TableInfo.COLUMN_NAME_CATEGORY, category);
                    values.put(TableInfo.COLUMN_NAME_AMOUNT, amount);
                    values.put(TableInfo.COLUMN_NAME_FULL_AMOUNT, fullAmount);

                    boolean isUpdated = helper.updateData(id, type, date, fullDate, content, category, amount, fullAmount);
                    if (isUpdated == true) {
                        Toast.makeText(getApplicationContext(), "내역을 수정하였습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "내역을 수정하지 못했습니다.", Toast.LENGTH_SHORT).show();
                    }
                    db.close();
                    onBackPressed();
                } else {
                    Toast.makeText(getApplicationContext(), "작성되지 않은 사항이 있습니다.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.no_button:
                onBackPressed();
                break;
            case R.id.delete_button:
                boolean isDeleted = helper.deleteData(id);
                if (isDeleted == true) {
                    Toast.makeText(getApplicationContext(), "해당 내역이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "내역을 삭제하지 못했습니다.", Toast.LENGTH_SHORT).show();
                }
                db.close();
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
        } else if(type2.isChecked()) {
            type = getString(R.string.expense);
        }

        // 내용 입력
        if (!editContent.getText().toString().equals("")) {
            content = editContent.getText().toString().trim();
        } else {
            content = null;
        }

        // 금액 입력
        if (!editAmount.getText().toString().equals("")) {
            fullAmount = editAmount.getText().toString().trim();
        } else {
            fullAmount = null;
        }
    }

    private void updateDate() {
        String dateViewFormat = "yyyy/MM/dd EE";
        String dbViewFormat = "MM/dd EE";
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateViewFormat, Locale.KOREA);
        SimpleDateFormat dbFormat = new SimpleDateFormat(dbViewFormat, Locale.KOREA);
        fullDateView.setText(dateFormat.format(myCalendar.getTime()));
        fullDate = fullDateView.getText().toString().trim();
        date = dbFormat.format(myCalendar.getTime()).trim();
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

    public class CustomTextWatcher implements TextWatcher {
        private EditText editText;
        String strAmount = "";

        CustomTextWatcher(EditText et) {
            editText = et;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(!TextUtils.isEmpty(s.toString()) && !s.toString().equals(strAmount)) {
                strAmount = makeStringComma(s.toString().replace(",", ""));
                editText.setText(strAmount);
                Editable editable = editText.getText();
                Selection.setSelection(editable, strAmount.length());
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }

        protected String makeStringComma(String str) {    // 천단위 콤마설정.
            if (str.length() == 0) {
                return "";
            }
            long value = Long.parseLong(str);
            DecimalFormat format = new DecimalFormat("###,###");
            return format.format(value);
        }
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
