package com.parse.avantmobilechallenge;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.ParseUser;
import com.parse.ui.ParseLoginBuilder;
/**
 * Created by Wyatt on 1/9/2016.
 */

public class LogIn extends Activity{
    private ParseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParseLoginBuilder builder = new ParseLoginBuilder(LogIn.this);
        startActivityForResult(builder.build(), 0);
    }

    @Override
    protected void onStart(){
        super.onStart();
        currentUser = ParseUser.getCurrentUser();
        LinearLayout linearLayout = new LinearLayout(this);
        setContentView(linearLayout);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        TextView textView = new TextView(this);
        textView.setText(currentUser.getString("name"));
        linearLayout.addView(textView);

    }



}

