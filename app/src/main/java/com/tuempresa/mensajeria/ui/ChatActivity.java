package com.tuempresa.mensajeria.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.tuempresa.mensajeria.R;
import com.tuempresa.mensajeria.db.DatabaseHelper;
import com.tuempresa.mensajeria.model.Message;

import java.util.List;

public class ChatActivity extends AppCompatActivity {
    ListView lvMessages;
    EditText etMessage;
    Button btnSend;
    DatabaseHelper db;
    long meId, otherId;
    MessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        lvMessages = findViewById(R.id.lvMessages);
        etMessage = findViewById(R.id.etMessage);
        btnSend = findViewById(R.id.btnSend);
        db = new DatabaseHelper(this);
        meId = getIntent().getLongExtra("meId", 1);
        otherId = getIntent().getLongExtra("otherId", 1);

        loadMessages();

        btnSend.setOnClickListener(v -> {
            String text = etMessage.getText().toString().trim();
            if (text.isEmpty()) return;
            Message m = new Message();
            m.setSenderId(meId);
            m.setReceiverId(otherId);
            m.setBody(text);
            m.setTimestamp(System.currentTimeMillis());
            db.addMessage(m);
            etMessage.setText("");
            loadMessages();
        });
    }

    private void loadMessages() {
        List<Message> msgs = db.getMessagesBetween(meId, otherId);
        adapter = new MessageAdapter(this, msgs, meId);
        lvMessages.setAdapter(adapter);
    }
}
