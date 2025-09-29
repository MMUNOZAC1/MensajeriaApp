package com.tuempresa.mensajeria.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tuempresa.mensajeria.model.User;
import com.tuempresa.mensajeria.model.Message;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "mensajeria.db";
    private static final int DB_VERSION = 1;

    public static final String TABLE_USERS = "usuarios";
    public static final String U_ID = "id";
    public static final String U_USERNAME = "username";
    public static final String U_PASSWORD = "password";
    public static final String U_DISPLAY = "displayName";

    public static final String TABLE_MESSAGES = "mensajes";
    public static final String M_ID = "id";
    public static final String M_SENDER = "senderId";
    public static final String M_RECEIVER = "receiverId";
    public static final String M_BODY = "body";
    public static final String M_TIME = "timestamp";

    public DatabaseHelper(Context context) { super(context, DB_NAME, null, DB_VERSION); }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUsers = "CREATE TABLE " + TABLE_USERS + " ("
                + U_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + U_USERNAME + " TEXT UNIQUE,"
                + U_PASSWORD + " TEXT,"
                + U_DISPLAY + " TEXT" + ");";
        db.execSQL(createUsers);

        String createMessages = "CREATE TABLE " + TABLE_MESSAGES + " ("
                + M_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + M_SENDER + " INTEGER,"
                + M_RECEIVER + " INTEGER,"
                + M_BODY + " TEXT,"
                + M_TIME + " INTEGER" + ");";
        db.execSQL(createMessages);

        ContentValues cv = new ContentValues();
        cv.put(U_USERNAME, "admin");
        cv.put(U_PASSWORD, "admin");
        cv.put(U_DISPLAY, "Administrador");
        db.insert(TABLE_USERS, null, cv);

        ContentValues mcv = new ContentValues();
        mcv.put(M_SENDER, 1);
        mcv.put(M_RECEIVER, 1);
        mcv.put(M_BODY, "Bienvenido a la app de mensajería");
        mcv.put(M_TIME, System.currentTimeMillis());
        db.insert(TABLE_MESSAGES, null, mcv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
        onCreate(db);
    }

    public long addUser(User u) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(U_USERNAME, u.getUsername());
        cv.put(U_PASSWORD, u.getPassword());
        cv.put(U_DISPLAY, u.getDisplayName());
        long id = db.insert(TABLE_USERS, null, cv);
        db.close();
        return id;
    }

    public User getUserByUsername(String username) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLE_USERS, null, U_USERNAME + "=?", new String[]{username}, null, null, null);
        if (c != null && c.moveToFirst()) {
            User u = new User(
                    c.getLong(c.getColumnIndexOrThrow(U_ID)),
                    c.getString(c.getColumnIndexOrThrow(U_USERNAME)),
                    c.getString(c.getColumnIndexOrThrow(U_PASSWORD)),
                    c.getString(c.getColumnIndexOrThrow(U_DISPLAY))
            );
            c.close(); db.close(); return u;
        }
        if (c != null) c.close(); db.close(); return null;
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLE_USERS, null, null, null, null, null, U_ID + " DESC");
        if (c.moveToFirst()) {
            do {
                User u = new User(
                        c.getLong(c.getColumnIndexOrThrow(U_ID)),
                        c.getString(c.getColumnIndexOrThrow(U_USERNAME)),
                        c.getString(c.getColumnIndexOrThrow(U_PASSWORD)),
                        c.getString(c.getColumnIndexOrThrow(U_DISPLAY))
                );
                list.add(u);
            } while (c.moveToNext());
        }
        c.close(); db.close(); return list;
    }

    public int updateUser(User u) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(U_USERNAME, u.getUsername());
        cv.put(U_PASSWORD, u.getPassword());
        cv.put(U_DISPLAY, u.getDisplayName());
        int rows = db.update(TABLE_USERS, cv, U_ID + "=?", new String[]{String.valueOf(u.getId())});
        db.close(); return rows;
    }

    public int deleteUser(long id) {
        SQLiteDatabase db = getWritableDatabase();
        int rows = db.delete(TABLE_USERS, U_ID + "=?", new String[]{String.valueOf(id)});
        db.close(); return rows;
    }

    public boolean checkLogin(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLE_USERS, null, U_USERNAME + "=? AND " + U_PASSWORD + "=?",
                new String[]{username, password}, null, null, null);
        boolean exists = (c != null && c.getCount() > 0);
        if (c != null) c.close(); db.close(); return exists;
    }

    public long addMessage(Message m) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(M_SENDER, m.getSenderId());
        cv.put(M_RECEIVER, m.getReceiverId());
        cv.put(M_BODY, m.getBody());
        cv.put(M_TIME, m.getTimestamp());
        long id = db.insert(TABLE_MESSAGES, null, cv);
        db.close(); return id;
    }

    public List<Message> getMessagesBetween(long userA, long userB) {
        List<Message> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String where = "(" + M_SENDER + "=? AND " + M_RECEIVER + "=?) OR (" + M_SENDER + "=? AND " + M_RECEIVER + "=?)";
        String[] args = new String[]{String.valueOf(userA), String.valueOf(userB), String.valueOf(userB), String.valueOf(userA)};
        Cursor c = db.query(TABLE_MESSAGES, null, where, args, null, null, M_TIME + " ASC");
        if (c.moveToFirst()) {
            do {
                Message m = new Message(
                        c.getLong(c.getColumnIndexOrThrow(M_ID)),
                        c.getLong(c.getColumnIndexOrThrow(M_SENDER)),
                        c.getLong(c.getColumnIndexOrThrow(M_RECEIVER)),
                        c.getString(c.getColumnIndexOrThrow(M_BODY)),
                        c.getLong(c.getColumnIndexOrThrow(M_TIME))
                );
                list.add(m);
            } while (c.moveToNext());
        }
        c.close(); db.close(); return list;
    }

    public int deleteMessage(long id) {
        SQLiteDatabase db = getWritableDatabase();
        int rows = db.delete(TABLE_MESSAGES, M_ID + "=?", new String[]{String.valueOf(id)});
        db.close(); return rows;
    }
}
