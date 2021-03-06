package com.example.midsemesterproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.util.ArrayList;
import java.util.Objects;

public class FillInfoActivity extends AppCompatActivity {

    TextInputLayout days_layout;
    AutoCompleteTextView days_list;
    ArrayList<String> weekDays;
    ArrayAdapter<String> daysAdapter;
    RadioButton morningHrs;
    RadioButton noonHrs;

    @SuppressLint({"ResourceType", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.fill_info_activity);

        TextView textView = findViewById(R.id.info_activity_header);
        String header = getString(R.string.selected_service_tv) + " " + getIntent().getStringExtra("serviceSelected");
        textView.setText(header);

        days_layout = findViewById(R.id.day_layout);
        days_list = findViewById(R.id.day_tv);

        // Setting the drop down menu
        weekDays = new ArrayList<>();
        weekDays.add(getString(R.string.sunday));
        weekDays.add(getString(R.string.monday));
        weekDays.add(getString(R.string.tuesday));
        weekDays.add(getString(R.string.wednesday));
        weekDays.add(getString(R.string.thursday));
        weekDays.add(getString(R.string.friday));
        weekDays.add(getString(R.string.saturday));

        daysAdapter = new ArrayAdapter<>(getApplicationContext(),R.layout.drop_down_item, weekDays);
        days_list.setAdapter(daysAdapter);

        days_list.setOnItemClickListener((adapterView, view, i, l) -> {
            String daySelection = adapterView.getItemAtPosition(i).toString();
            String dayToast = daySelection + " " + getString(R.string.spinner_toast);
            Toast.makeText(FillInfoActivity.this, dayToast, Toast.LENGTH_SHORT).show();
        });

        TextInputLayout phoneLayout = findViewById(R.id.mobile_field);
        TextInputLayout nameLayout = findViewById(R.id.name_field);
        ScrollView scrollView = findViewById(R.id.scroll_view);
        AppCompatButton okBtn = findViewById(R.id.ok_btn);
        AppCompatButton finishBtn = findViewById(R.id.finish_btn);
        AppCompatButton cancelBtn = findViewById(R.id.cancel_btn);

        TextInputEditText notes = findViewById(R.id.notes_et);
        TextInputEditText nameET = findViewById(R.id.name_et);
        TextInputEditText phoneET = findViewById(R.id.phone_et);

        RadioGroup hrsGroup = findViewById(R.id.hrs_rg);
        morningHrs = findViewById(R.id.morning_radio);
        noonHrs = findViewById(R.id.noon_radio);

        MyRadioButtonListener myRadioButtonListener = new MyRadioButtonListener();
        morningHrs.setOnClickListener(myRadioButtonListener);
        noonHrs.setOnClickListener(myRadioButtonListener);

        /* Dynamically creating EditTexts */
        okBtn.setOnClickListener(view -> {
            String numStr = Objects.requireNonNull(notes.getText()).toString();

            if(numStr.length() > 0) {
                LinearLayout extraNotes = findViewById(R.id.extra_notes_layout);
                extraNotes.removeAllViews();

                int notesCount = Integer.parseInt(numStr);

                for (int i = 0; i < notesCount; i++) {
                    TextInputEditText newET = new TextInputEditText(FillInfoActivity.this);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                    params.topMargin = 20;
                    params.bottomMargin = 20;
                    newET.setLayoutParams(params);
                    newET.setHint(getString(R.string.new_note_hint));

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        newET.setTextAppearance(R.style.TextAppearance_MaterialComponents_Headline1);
                    }
                    newET.setAllCaps(false);
                    newET.setTextSize(15);
                    newET.setBackground(getDrawable(R.drawable.rounded_et_corners));
                    newET.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    extraNotes.addView(newET);
                }
            }
        });

        cancelBtn.setOnClickListener(view -> {
            Intent intent = new Intent(FillInfoActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
        
        finishBtn.setOnClickListener(view -> {
            String name = Objects.requireNonNull(nameET.getText()).toString();
            String phoneNum = Objects.requireNonNull(phoneET.getText()).toString();
            String daySelected = days_list.getText().toString();
            int radioChecked = hrsGroup.getCheckedRadioButtonId();
            boolean legalName, legalPhone, legalDay, legalHrs;

            if(!name.matches("[a-zA-Z??-?? ]+")) { //Validating name
                if (name.equals(""))
                    nameLayout.setError(getString(R.string.required));
                else
                    nameLayout.setError(getString(R.string.name_error));

                scrollView.fullScroll(ScrollView.FOCUS_UP);
                legalName = false;
            }
            else {
                legalName = true;
                nameLayout.setErrorEnabled(false);
            }

            if(phoneNum.equals("")) { //Validating phone entered
                phoneLayout.setError(getString(R.string.required));
                scrollView.fullScroll(ScrollView.FOCUS_UP);
                legalPhone = false;
            }
            else {
                legalPhone = true;
                phoneLayout.setErrorEnabled(false);
            }

            if(daySelected.equals("")) { //Validating day selection
                days_layout.setError(getString(R.string.required));
                scrollView.fullScroll(ScrollView.FOCUS_UP);
                legalDay = false;
            }
            else {
                legalDay = true;
                days_layout.setErrorEnabled(false);
            }

            if(radioChecked == -1){ //Validating time selection
                legalHrs = false;
                Toast.makeText(FillInfoActivity.this, getString(R.string.missing_hrs_toast), Toast.LENGTH_SHORT).show();
            }
            else legalHrs = true;

            if(legalName && legalPhone && legalDay && legalHrs){
                Intent intent = new Intent(FillInfoActivity.this, FinalScreenActivity.class);
                intent.putExtra("contactName", name);
                intent.putExtra("contactPhone", phoneNum);
                startActivity(intent);
                finish(); // prevents from coming back to this screen from final screen
            }
        });
    }

    private class MyRadioButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            int id = view.getId();
            String toastText;

            if(id == R.id.morning_radio)
                toastText = getString(R.string.morning_radio_selection);
            else
                toastText = getString(R.string.noon_radio_selected);

            Toast.makeText(FillInfoActivity.this, toastText, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent backPressedIntent = new Intent(FillInfoActivity.this, MainActivity.class);
        startActivity(backPressedIntent);
        finish();
    }
}
