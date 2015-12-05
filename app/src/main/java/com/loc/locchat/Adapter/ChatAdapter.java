package com.loc.locchat.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.loc.locchat.R;
import com.loc.locchat.User.Mgs;

import java.util.ArrayList;

/**
 * Created by loc on 05/12/2015.
 */
public class ChatAdapter extends BaseAdapter {
    ArrayList<Mgs> list;

    public ChatAdapter(ArrayList<Mgs> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    View v;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.)

        if (list.get(position).isSent()) {
//            v =get.inflate(R.layout.chat_item_sent, null);



        }
        return v;
    }
}
