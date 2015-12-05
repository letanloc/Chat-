package com.loc.locchat;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.DateUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.loc.locchat.App.Conts;
import com.loc.locchat.User.List;
import com.loc.locchat.User.Mgs;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Date;

public class Chat extends AppCompatActivity implements View.OnClickListener {
    ArrayList<Mgs> Mlslist;

    //    private
    ListView listView;

    Button btnsent;
    EditText edtmsg;
    private String buddy;
    ChatAdapter chatAdapter;
    // sau khi gui
    private Date lastMsgDate;
    private boolean isRunning;

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Mlslist = new ArrayList<>();
        listView = (ListView) findViewById(R.id.lvchat);

        btnsent = (Button) findViewById(R.id.send);
        btnsent.setOnClickListener(this);
        edtmsg = (EditText) findViewById(R.id.gms);
        chatAdapter = new ChatAdapter();
        listView.setAdapter(chatAdapter);
        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setStackFromBottom(true);
        buddy = getIntent().getStringExtra(Conts.EXTRA_DATA);
        ;
        edtmsg.setInputType(InputType.TYPE_CLASS_TEXT
                | InputType.TYPE_TEXT_FLAG_MULTI_LINE);

        handler = new Handler();
    }


    @Override
    protected void onResume() {
        super.onResume();
        isRunning = true;
        loadConversationList();
    }


    /*---*/


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send:
                sendMessage();

                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        isRunning = false;
    }

    private void sendMessage() {
        if (edtmsg.length() == 0)
            return;

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtmsg.getWindowToken(), 0);

        String s = edtmsg.getText().toString();
        final Mgs c = new Mgs();
        c.setDate(new Date());
        c.setMsg(s);
        c.setSender(List.user.getUsername());


//
//        c.setStatus(Conversation.STATUS_SENDING);


        Mlslist.add(c);
        chatAdapter.notifyDataSetChanged();
        edtmsg.setText(null);

        ParseObject po = new ParseObject("Chat");
        po.put("sender", List.user.getUsername());
        po.put("receiver", buddy);
        // po.put("createdAt", "");
        po.put("message", s);
        po.saveEventually(new SaveCallback() {

            @Override
            public void done(ParseException e) {
                if (e == null)
                    c.setStatus(Mgs.Dagui);
                else
                    c.setStatus(Mgs.ThatBai);
                chatAdapter.notifyDataSetChanged();
            }
        });
    }


    class ChatAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return Mlslist.size();
        }

        @Override
        public Mgs getItem(int arg0) {
            return Mlslist.get(arg0);
        }


        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public View getView(int pos, View v, ViewGroup arg2) {
            Mgs c = getItem(pos);
            if (c.isSent())
                v = getLayoutInflater().inflate(R.layout.chat_item_sent, null);
            else
                v = getLayoutInflater().inflate(R.layout.chat_item_rcv, null);

            TextView lbl = (TextView) v.findViewById(R.id.lbl1);
            lbl.setText(DateUtils.getRelativeDateTimeString(Chat.this, c
                            .getDate().getTime(), DateUtils.SECOND_IN_MILLIS,
                    DateUtils.DAY_IN_MILLIS, 0));

            lbl = (TextView) v.findViewById(R.id.lbl2);
            lbl.setText(c.getMsg());

            lbl = (TextView) v.findViewById(R.id.lbl3);
            if (c.isSent()) {
                if (c.getStatus() == Mgs.Dagui)
                    lbl.setText("Gửi");
                else if (c.getStatus() == Mgs.Danggui)
                    lbl.setText("Đang gửi...");
                else
                    lbl.setText("Failde");
            } else
                lbl.setText("");

            return v;
        }

    }

    /**
     * Load the conversation list from Parse server and save the date of last
     * message that will be used to load only recent new messages
     */
    private void loadConversationList() {
        ParseQuery<ParseObject> q = ParseQuery.getQuery("Chat");
        if (Mlslist.size() == 0) {
            // load all messages...
            ArrayList<String> al = new ArrayList<String>();
            al.add(buddy);
            al.add(List.user.getUsername());
            q.whereContainedIn("sender", al);
            q.whereContainedIn("receiver", al);
        } else {

            if (lastMsgDate != null)
                q.whereGreaterThan("createdAt", lastMsgDate);
            q.whereEqualTo("sender", buddy);
            q.whereEqualTo("receiver", List.user.getUsername());
        }
        q.orderByDescending("createdAt");
        q.setLimit(30);
        q.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(java.util.List<ParseObject> li, ParseException e) {
                if (li != null && li.size() > 0) {
                    for (int i = li.size() - 1; i >= 0; i--) {
                        ParseObject po = li.get(i);
//                        Conversation c = new Conversation(po
//                                .getString("message"), po.getCreatedAt(), po
//                                .getString("sender"));

                        Mgs mgs = new Mgs();
                        mgs.setMsg(po.getString("message"));
                        mgs.setDate(po.getCreatedAt());
                        mgs.setSender(po.getString("sender"));

                        Mlslist.add(mgs);
//                        mgs.setSender();

                        if (lastMsgDate == null
                                || lastMsgDate.before(mgs.getDate()))
                            lastMsgDate = mgs.getDate();
                        chatAdapter.notifyDataSetChanged();
                    }
                }
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        if (isRunning)
                            loadConversationList();
                    }
                }, 1000);
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
