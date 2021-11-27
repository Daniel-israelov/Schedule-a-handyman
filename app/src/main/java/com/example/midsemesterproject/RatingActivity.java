package com.example.midsemesterproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.util.Objects;

public class RatingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.rating_activity);

        final AppCompatButton back_home_btn = findViewById(R.id.back_home_btn);

        back_home_btn.setOnClickListener(view->{
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        });

        RatingBar ratingBar = findViewById(R.id.rating_bar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            final ImageView ratingImg = findViewById(R.id.rating_img);
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                if(v >= 3){
                    ratingImg.setImageResource(R.drawable.happy_emoji);
                    ratingImg.setVisibility(View.VISIBLE);
                }
                else if((v < 3) && (v > 0)){
                    ratingImg.setImageResource(R.drawable.sad_emoji);
                    ratingImg.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(RatingActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
