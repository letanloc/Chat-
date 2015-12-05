package com.loc.locchat.User;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loc.locchat.App.App;
import com.loc.locchat.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class Login extends AppCompatActivity implements View.OnClickListener {
    EditText user, pwd;
    Button btnLogin, btnRese;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user = (EditText) findViewById(R.id.user);
        pwd = (EditText) findViewById(R.id.pwd);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRese = (Button) findViewById(R.id.btnRe);
        btnLogin.setOnClickListener(this);

    }


    public void Login() {
        String stusername = user.getText() + "";
        String swd = pwd.getText() + "";

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading....");
        dialog.show();
        ParseUser.logInInBackground(stusername, swd, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                dialog.dismiss();


                if (user != null) {

                    List.user = user;

                    startActivity(new Intent(Login.this, List.class));
                    finish();
                } else {
                    Toast.makeText(Login.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.btnLogin:
                Login();

                break;
            case R.id.btnRe:
                startActivity(new Intent(Login.this, Regesterr.class));

                break;

        }
    }
}
