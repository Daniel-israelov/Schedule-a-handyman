package com.example.midsemesterproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
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

        daysAdapter = new ArrayAdapter<>(getApplicationContext(),R.layout.drop_down_item, weekDays);
        days_list.setAdapter(daysAdapter);

        AppCompatButton okBtn = findViewById(R.id.ok_btn);
        AppCompatButton finishBtn = findViewById(R.id.finish_btn);
        AppCompatButton cancelBtn = findViewById(R.id.cancel_btn);

        TextInputEditText notes = findViewById(R.id.notes_et);
        TextInputEditText nameET = findViewById(R.id.name_et);
        TextInputEditText phoneET = findViewById(R.id.phone_et);

        RadioGroup hrsGroup = findViewById(R.id.hrs_rg);

        /* Dynamically creating EditTexts */
        okBtn.setOnClickListener(view -> {
            String numStr = Objects.requireNonNull(notes.getText()).toString();

            if(numStr.length() > 0) {
                LinearLayout extraNotes = findViewById(R.id.extra_notes_layout);
                extraNotes.removeAllViews();

                int notesCount = Integer.parseInt(numStr);

                for (int i = 0; i < notesCount; i++) {
                    TextInputEditText newET = new TextInputEditText(FillInfoActivity.this);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                    params.topMargin = 20;
                    params.bottomMargin = 20;
                    newET.setLayoutParams(params);
                    newET.setHint(getString(R.string.new_note_hint));

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        newET.setTextAppearance(R.style.TextAppearance_MaterialComponents_Headline1);
                    }
                    newET.setAllCaps(false);
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

            if(name.equals("") || phoneNum.equals("") || daySelected.equals("") || radioChecked == -1){
                Toast.makeText(FillInfoActivity.this, getString(R.string.missing_fields_toast), Toast.LENGTH_SHORT).show();
            }
            else{
                Intent intent = new Intent(FillInfoActivity.this, FinalScreenActivity.class);
                intent.putExtra("contactName", name);
                intent.putExtra("contactPhone", phoneNum);
                startActivity(intent);
                finish(); // prevents from coming back to this screen from final screen
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent backPressedIntent = new Intent(FillInfoActivity.this, MainActivity.class);
        startActivity(backPressedIntent);
        finish();
    }
}
