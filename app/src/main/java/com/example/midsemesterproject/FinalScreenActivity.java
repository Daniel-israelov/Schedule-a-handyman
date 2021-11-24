package com.example.midsemesterproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.util.Objects;

public class FinalScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.final_screen_activity);

        TextView nameView = findViewById(R.id.name_header);
        String nameHeader = getString(R.string.summery_name) + " " + getIntent().getStringExtra("contactName");
        nameView.setText(nameHeader);

        TextView phoneView = findViewById(R.id.phone_header);
        String phoneHeader = getString(R.string.summery_phone) + " " + getIntent().getStringExtra("contactPhone");
        phoneView.setText(phoneHeader);

        AppCompatButton finalBtn = findViewById(R.id.back_home_btn);

        finalBtn.setOnClickListener(view -> {
            Intent intent = new Intent(FinalScreenActivity.this,MainActivity.class);
            startActivity(intent);
            finish(); // killing the activity so the user can't return to the previous screen by pressing the back button
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent backPressedIntent = new Intent(FinalScreenActivity.this, MainActivity.class);
        startActivity(backPressedIntent);
        finish();
    }

}
