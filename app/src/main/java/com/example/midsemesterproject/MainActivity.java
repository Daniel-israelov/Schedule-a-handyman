package com.example.midsemesterproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);

        final AppCompatButton electrician_btn = findViewById(R.id.electrician_acb);
        final AppCompatButton plumber_btn = findViewById(R.id.plumber_acb);
        final AppCompatButton exterminator_btn = findViewById(R.id.exterminator_acb);
        final AppCompatButton mechanic_btn = findViewById(R.id.mechanic_acb);

        final ImageButton electrician_ib = findViewById(R.id.electrician_ib);
        final ImageButton plumber_ib = findViewById(R.id.plumber_ib);
        final ImageButton exterminator_ib = findViewById(R.id.exterminator_ib);
        final ImageButton mechanic_ib = findViewById(R.id.mechanic_ib);

        AcbListener acbListener = new AcbListener();
        electrician_btn.setOnClickListener(acbListener);
        plumber_btn.setOnClickListener(acbListener);
        exterminator_btn.setOnClickListener(acbListener);
        mechanic_btn.setOnClickListener(acbListener);

        electrician_ib.setOnClickListener(acbListener);
        plumber_ib.setOnClickListener(acbListener);
        exterminator_ib.setOnClickListener(acbListener);
        mechanic_ib.setOnClickListener(acbListener);
    }

    private class AcbListener implements View.OnClickListener{
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View view) {
            int id = 0;
            String service_name = "";

            if(view instanceof AppCompatButton)
                id = view.getId();
            else if(view instanceof ImageButton)
                id = view.getId();

            switch (id){
                case R.id.electrician_acb:
                case R.id.electrician_ib:
                    service_name = getString(R.string.electrician_tv);
                    break;
                case R.id.plumber_acb:
                case R.id.plumber_ib:
                    service_name = getString(R.string.plumber_tv);
                    break;
                case R.id.exterminator_acb:
                case R.id.exterminator_ib:
                    service_name = getString(R.string.exterminator_tv);
                    break;
                case R.id.mechanic_acb:
                case R.id.mechanic_ib:
                    service_name = getString(R.string.mechanic_tv);
                    break;
            }
            Intent intent = new Intent(MainActivity.this, FillInfoActivity.class);
            intent.putExtra("serviceSelected", service_name);
            startActivity(intent);
            finish();
        }
    }
}