package com.tuempresa.mensajeria.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.tuempresa.mensajeria.R;
import com.tuempresa.mensajeria.db.DatabaseHelper;
import com.tuempresa.mensajeria.model.User;

import java.util.List;

public class UserManagementActivity extends AppCompatActivity {
    EditText etUser, etPass, etDisplay;
    Button btnAdd;
    ListView lvUsers;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);
        etUser = findViewById(R.id.etUser);
        etPass = findViewById(R.id.etPass);
        etDisplay = findViewById(R.id.etDisplay);
        btnAdd = findViewById(R.id.btnAdd);
        lvUsers = findViewById(R.id.lvUsers);
        db = new DatabaseHelper(this);

        loadUsers();

        btnAdd.setOnClickListener(v -> {
            String u = etUser.getText().toString().trim();
            String p = etPass.getText().toString().trim();
            String d = etDisplay.getText().toString().trim();
            if (u.isEmpty() || p.isEmpty()) { Toast.makeText(this, "Usuario y contraseña obligatorios", Toast.LENGTH_SHORT).show(); return; }
            User user = new User(); user.setUsername(u); user.setPassword(p); user.setDisplayName(d);
            long id = db.addUser(user);
            if (id > 0) { Toast.makeText(this, "Usuario creado", Toast.LENGTH_SHORT).show(); etUser.setText(""); etPass.setText(""); etDisplay.setText(""); loadUsers(); }
            else Toast.makeText(this, "Error al crear usuario (posible duplicado)", Toast.LENGTH_SHORT).show();
        });
    }

    private void loadUsers() {
        List<User> list = db.getAllUsers();
        UserListAdapter adapter = new UserListAdapter(this, list, db);
        lvUsers.setAdapter(adapter);
    }
}
