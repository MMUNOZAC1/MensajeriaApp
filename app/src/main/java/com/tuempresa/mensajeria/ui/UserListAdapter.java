package com.tuempresa.mensajeria.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tuempresa.mensajeria.R;
import com.tuempresa.mensajeria.db.DatabaseHelper;
import com.tuempresa.mensajeria.model.User;

import java.util.List;

public class UserListAdapter extends ArrayAdapter<User> {
    Context ctx;
    DatabaseHelper db;
    public UserListAdapter(Context context, List<User> users, DatabaseHelper db) {
        super(context, 0, users);
        this.ctx = context; this.db = db;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User u = getItem(position);
        if (convertView == null) convertView = LayoutInflater.from(ctx).inflate(R.layout.row_user, parent, false);
        TextView tvName = convertView.findViewById(R.id.tvUserName);
        Button btnEdit = convertView.findViewById(R.id.btnEdit);
        Button btnDelete = convertView.findViewById(R.id.btnDelete);
        tvName.setText(u.getUsername() + (u.getDisplayName() != null && !u.getDisplayName().isEmpty() ? " ("+u.getDisplayName()+")" : ""));

        btnEdit.setOnClickListener(v -> {
            // show simple dialog to edit password/displayName
            View dlgView = LayoutInflater.from(ctx).inflate(android.R.layout.simple_list_item_2, null);
            EditText etPass = new EditText(ctx);
            etPass.setHint("Nueva contraseña");
            EditText etDisplay = new EditText(ctx);
            etDisplay.setHint("Nuevo nombre visible");
            AlertDialog.Builder b = new AlertDialog.Builder(ctx);
            b.setTitle("Editar usuario");
            b.setView(etPass);
            b.setPositiveButton("Guardar", (dialog, which) -> {
                String np = etPass.getText().toString().trim();
                String nd = etDisplay.getText().toString().trim();
                if (!np.isEmpty()) u.setPassword(np);
                if (!nd.isEmpty()) u.setDisplayName(nd);
                int r = db.updateUser(u);
                if (r>0) Toast.makeText(ctx, "Usuario actualizado", Toast.LENGTH_SHORT).show();
                else Toast.makeText(ctx, "Error al actualizar", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            });
            b.setNegativeButton("Cancelar", null);
            b.show();
        });

        btnDelete.setOnClickListener(v -> {
            AlertDialog.Builder b = new AlertDialog.Builder(ctx);
            b.setTitle("Eliminar usuario");
            b.setMessage("Eliminar usuario " + u.getUsername() + "?");
            b.setPositiveButton("Sí", (dialog, which) -> {
                int r = db.deleteUser(u.getId());
                if (r>0) {
                    remove(u);
                    notifyDataSetChanged();
                    Toast.makeText(ctx, "Usuario eliminado", Toast.LENGTH_SHORT).show();
                } else Toast.makeText(ctx, "Error al eliminar", Toast.LENGTH_SHORT).show();
            });
            b.setNegativeButton("No", null);
            b.show();
        });

        return convertView;
    }
}
