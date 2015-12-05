package com.loc.locchat.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.loc.locchat.R;
import com.parse.ParseUser;

import java.util.ArrayList;

/**
 * Created by loc on 05/12/2015.
 */
public class UserAdapter extends BaseAdapter {
    ArrayList<ParseUser> list;

    public UserAdapter(ArrayList<ParseUser> list) {
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
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        Viewhole h = new Viewhole();
        h.status = (ImageView) v.findViewById(R.id.status);
        h.username = (TextView) v.findViewById(R.id.User);
        h.txtDateTime = (TextView) v.findViewById(R.id.textView);
        h.username.setText(list.get(position).getUsername());
        h.status.setImageResource(list.get(position).getBoolean("online") ? R.drawable.ic_online : R.drawable.ic_offline);
        return v;
    }

    class Viewhole {
        TextView username;
        TextView txtDateTime;
        ImageView status;


    }
}
