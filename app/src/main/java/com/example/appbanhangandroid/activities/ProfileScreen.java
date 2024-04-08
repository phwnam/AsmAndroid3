package com.example.appbanhangandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appbanhangandroid.R;
import com.example.appbanhangandroid.model.Product;
import com.example.appbanhangandroid.model.Response;
import com.example.appbanhangandroid.services.ApiServices;
import com.example.appbanhangandroid.services.HttpRequest;

import retrofit2.Call;
import retrofit2.Callback;

public class ProfileScreen extends AppCompatActivity {
    TextView btnBack;
    HttpRequest httpRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        btnBack = findViewById(R.id.btnBack);

        // Initialize HttpRequest for API requests
        httpRequest = new HttpRequest();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Toast.makeText(ProfileScreen.this, "Back", Toast.LENGTH_SHORT).show();
            }
        });

        // Example usage: Fetch product details by ID
    }


}
