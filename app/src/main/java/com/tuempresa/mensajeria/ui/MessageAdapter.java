package com.tuempresa.mensajeria.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tuempresa.mensajeria.R;
import com.tuempresa.mensajeria.model.Message;

import java.util.List;

public class MessageAdapter extends ArrayAdapter<Message> {
    Context ctx;
    long meId;
    public MessageAdapter(Context context, List<Message> messages, long meId) {
        super(context, 0, messages);
        this.ctx = context; this.meId = meId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message m = getItem(position);
        if (m.getSenderId() == meId) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.row_message_out, parent, false);
            TextView tv = convertView.findViewById(R.id.tvMsgOut);
            tv.setText(m.getBody());
        } else {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.row_message_in, parent, false);
            TextView tv = convertView.findViewById(R.id.tvMsgIn);
            tv.setText(m.getBody());
        }
        return convertView;
    }
}
