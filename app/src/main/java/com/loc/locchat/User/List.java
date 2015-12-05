package com.loc.locchat.User;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.loc.locchat.Adapter.UserAdapter;
import com.loc.locchat.App.App;
import com.loc.locchat.App.Conts;
import com.loc.locchat.Chat;
import com.loc.locchat.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;

public class List extends AppCompatActivity {
    ArrayList<ParseUser> ulist;
    public static ParseUser user;
    ListView listView;
    UserAdapter AUserAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        listView = (ListView) findViewById(R.id.listView);
        Update(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Update(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LoadUserList();

    }

    public void LoadUserList() {
        final ProgressDialog dia = new ProgressDialog(this);
        dia.setMessage("Loading...");
        dia.show();
        ParseUser.getQuery().whereNotEqualTo("username", user.getUsername()).findInBackground(
                new FindCallback<ParseUser>() {
                    @Override
                    public void done(java.util.List<ParseUser> objects, ParseException e) {
                        dia.dismiss();

                        if (objects != null) {

                            if (objects.size() == 0) {
                                Log.e("lis", "khong co nguoi dung");

                            } else {
                                ulist = new ArrayList<ParseUser>();

                                ulist = (ArrayList<ParseUser>) objects;
                                UserAdapter userAdapter = new UserAdapter(ulist);
                                listView.setAdapter(userAdapter);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        startActivity(new Intent(List.this, Chat.class).putExtra(Conts.EXTRA_DATA, ulist.get(position).getUsername()));
                                    }
                                });
                            }

                        }


                    }
                }


        );


    }

    /* Update user status.
    *
            * @param online true if user is online
    */
    public void Update(boolean on) {
        user.put("online", on);
        user.saveEventually();
    }
}
