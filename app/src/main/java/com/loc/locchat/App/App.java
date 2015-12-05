package com.loc.locchat.App;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseUser;

import java.util.ArrayList;

/**
 * Created by loc on 05/12/2015.
 */
public class App extends Application {
  public  static ArrayList<ParseUser>list;



    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "hVCGs4qW4yQWQi9MwgA0fcLJpaQFDxPNRQlG4Uzp", "vWXoo5zUlEHslREncGAnvMv923csXwc9gp81hG9R");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
