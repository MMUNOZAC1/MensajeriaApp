package com.tuempresa.mensajeria.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.tuempresa.mensajeria.R;
import com.tuempresa.mensajeria.db.DatabaseHelper;
import com.tuempresa.mensajeria.model.User;

public class MenuActivity extends AppCompatActivity {
    Button btnContacts, btnOpenChat, btnLogout;
    TextView tvWelcome;
    DatabaseHelper db;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        tvWelcome = findViewById(R.id.tvWelcome);
        btnContacts = findViewById(R.id.btnContacts);
        btnOpenChat = findViewById(R.id.btnOpenChat);
        btnLogout = findViewById(R.id.btnLogout);
        db = new DatabaseHelper(this);

        String username = getIntent().getStringExtra("username");
        currentUser = db.getUserByUsername(username);
        if (currentUser != null) tvWelcome.setText("Bienvenido, " + currentUser.getDisplayName());

        btnContacts.setOnClickListener(v -> startActivity(new Intent(MenuActivity.this, UserManagementActivity.class)));

        btnOpenChat.setOnClickListener(v -> {
            long otherId = 1;
            if (currentUser != null && currentUser.getId() == 1) otherId = 1; 
            Intent i = new Intent(MenuActivity.this, ChatActivity.class);
            i.putExtra("meId", currentUser != null ? currentUser.getId() : 1);
            i.putExtra("otherId", otherId);
            startActivity(i);
        });

        btnLogout.setOnClickListener(v -> {
            finish();
        });
    }
}
