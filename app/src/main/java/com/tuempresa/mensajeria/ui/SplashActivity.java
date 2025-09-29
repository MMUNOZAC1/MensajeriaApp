package com.tuempresa.mensajeria.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Aquí puedes agregar animación de splash si quieres
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
